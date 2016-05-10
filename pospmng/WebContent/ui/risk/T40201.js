Ext.onReady(function() {
	// 当前选择记录
	var record;
	
	var riskAlarmLvl = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('ALARM_LVL',function(ret){
		riskAlarmLvl.loadData(Ext.decode(ret));
	});
	

	// 风险模型数据集
	var riskModelStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=getRiskModelInfo'
				}),
		reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [{
							name : 'saModelKind',
							mapping : 'saModelKind'
						}, {
							name : 'saModelGroup',
							mapping : 'saModelGroup'
						}, {
							name : 'saModelDesc',
							mapping : 'saModelDesc'
						}, {
							name : 'saBeUse',
							mapping : 'saBeUse'
						}, {
							name : 'misc',
							mapping : 'misc'
						}]),
		autoLoad : true
	});

	// 风险模型数据集
	var RriskModelStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'gridPanelStoreAction.asp?storeId=getRisParamInfo'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data',
							totalProperty : 'totalCount'
						}, [{
									name : 'riskLvl',
									mapping : 'riskLvl'
								},{
									name : 'modelKind',
									mapping : 'modelKind'
								}, {
									name : 'modelSeq',
									mapping : 'modelSeq'
								}, {
									name : 'paramLen',
									mapping : 'paramLen'
								}, {
									name : 'paramValue',
									mapping : 'paramValue'
								}, {
									name : 'paramName',
									mapping : 'paramName'
								}])
//				autoLoad : true
			});

	// var sm = new
	// Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn});
	var riskColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			// sm,
			{header : '监控模型',dataIndex : 'saModelKind',width : 60,align: 'center'},
			{header : '警报级别',dataIndex : 'misc',width : 60,renderer : alarmLvl,align: 'center'},
			{header : '受控组别',dataIndex : 'saModelGroup',width : 120,renderer : ModelGroup},
			{id:'saModelDesc',header : '模型名称',dataIndex : 'saModelDesc',minWidth : 400},
			{header : '启用状态',dataIndex : 'saBeUse',width : 100,renderer : BeUse,align: 'center'}
			]);
	var riskParmColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
			// sm,
//			{header : '监控模型',dataIndex : 'modelKind',hidden:true},
//			{header : '序列',dataIndex : 'modelSeq',hidden:true},
			{header : '风控等级',dataIndex : 'riskLvl',width : 80,align: 'center',renderer:riskLvl},
			/*editor: new Ext.form.ComboBox({
			allowBlank: false,
//			id:'paramValues',
			store: riskLvlStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'riskLvl',
//			emptyText: '请选择用户角色',
			blankText: '请选择风控级别'
		})},*/
			{header : '参数名称',dataIndex : 'paramName',width : 150},
			{header : '参数长度上限',dataIndex : 'paramLen',width : 80},
			{header : '参数值',dataIndex : 'paramValue',width : 100,editor: new Ext.form.TextField({
			allowBlank: false,
			id:'paramValues',
			blankText: '请输入参数值',
			regex: /^(([1-9]\d*)|0)(\.\d{1,2})?$/,
//			regex:/^((([1-9]\d*)|0)(\.\d{1,2}))|([01]\d|2[0123])?$/,
			regexText: '只能输入整数或者最多保留2位的小数'
		})}
			]);
	
			//渠道商户	
	function riskLvl(val) {
		if(val == null||val=='') 
			return '*';
		
		return val;
	}
	
	
	//================================================修改警报级别==开始=================================================
	var updForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth : 100,
		// width : 900,
		// height : 250,
		layout : 'column',
//		height : 430,
		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
//							id: 'firstMchtCdId',
							fieldLabel : '风控模型',
							baseParams: 'KIND',
							hiddenName: 'saModelKindUpd',
							mode:'local',
							blankText: '风控模型不能为空',
							emptyText : '请选择风控模型',
							allowBlank : false,
							triggerAction: 'all',
							editable: false,
							lazyRender: true,
							width: 150
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'combo',
//							id: 'firstMchtCdId',
							fieldLabel : '警报级别*',
							store: riskAlarmLvl,
							hiddenName: 'misc',
							displayField: 'displayField',
							valueField: 'valueField',
							mode:'local',
							blankText: '警报级别不能为空',
							emptyText : '请选择警报级别',
							allowBlank : false,
							triggerAction: 'all',
							editable: false,
							lazyRender: true,
							width: 150
						}]
				}
		]
	});
			
	var updWin = new Ext.Window({
				title : '修改警报级别',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 420,
				autoHeight : true,
				layout : 'fit',
				items : [updForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [{
					text : '确定',
					handler : function() {
						if (updForm.getForm().isValid()) {
							updForm.getForm().submit({
										url : 'T40201Action.asp?method=updateLvl',
										waitMsg : '正在提交，请稍后......',
										success : function(form, action) {
											showSuccessMsg(action.result.msg,
													updForm);
											// 重置表单
											updForm.getForm().reset();
											// 重新加载参数列表
											grid.getStore().reload();
											updWin.hide();
										},
										failure : function(form, action) {
											showErrorMsg(action.result.msg,
													updForm);
										},
										params : {
											saModelKind: grid.getSelectionModel().getSelected().data.saModelKind,
											txnId : '40201',
											subTxnId : '04'
										}
									});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						updForm.getForm().reset();
//						Ext.getCmp('tblRiskLvlAdd.resved').getEl().dom.readOnly=false;
						var select = grid.getSelectionModel().getSelected();
						updForm.getForm().findField('saModelKindUpd').setValue(select.data.saModelKind);
						updForm.getForm().findField('misc').setValue(select.data.misc);
					}
				}
				
				]
			});
	//================================================修改警报级别==结束=================================================
			
	
	var menuArr = new Array();

	var start = {
		text : '启用模型',
		width : 85,
		iconCls : 'accept',//recover
		disabled : true,
		handler : function() {
			showConfirm('确定要启用该风控模型吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T40201Action.asp?method=start',
										params : {
											saModelKind: grid.getSelectionModel().getSelected().data.saModelKind,
											txnId : '40201',
											subTxnId : '01'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											grid.getTopToolbar().items.items[0].disable();
											grid.getTopToolbar().items.items[2].disable();
										}
									});
						}
					})
		}
	};

	var stop = {
		text : '停用模型',
		width : 85,
		iconCls : 'stop',
		disabled : true,
		handler : function() {

			showConfirm('确定要停用该风控模型吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T40201Action.asp?method=stop',
										params : {
											saModelKind: grid.getSelectionModel().getSelected().data.saModelKind,
											txnId : '40201',
											subTxnId : '02'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											grid.getTopToolbar().items.items[0].disable();
											grid.getTopToolbar().items.items[2].disable();
										}
									});
						}
					})
		}
	};
	var editLvl = {
		text : '修改警报级别',
		width : 85,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			updWin.show();
			updWin.center();
			var select = grid.getSelectionModel().getSelected();
			updForm.getForm().findField('saModelKindUpd').setValue(select.data.saModelKind);
			updForm.getForm().findField('misc').setValue(select.data.misc);
			
		}
	};
	menuArr.push(start);
	menuArr.push("-");
	menuArr.push(stop);
	menuArr.push("-");
	menuArr.push(editLvl);
	

	var detailMenu = new Array();
	var update = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = detailGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var store = detailGrid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
					//record.get(''))
			}
			//存放要修改的监控模型
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					modelKind : record.get('modelKind'),
					modelSeq: record.get('modelSeq'),
					paramLen: record.get('paramLen'),
					paramName: record.get('paramName'),
					paramValue: record.get('paramValue'),
					riskLvl: record.get('riskLvl')
				};
				array.push(data);
			}
			detailGrid.getTopToolbar().items.items[0].disable();
			Ext.Ajax.request({
				url: 'T40201Action.asp?method=update',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40201',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					detailGrid.enable();
					if(rspObj.success) {
						detailGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,detailGrid);
					} else {
						detailGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,detailGrid);
					}
					detailGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	detailMenu.push(update);
	var detailGrid = new Ext.grid.EditorGridPanel({
		title: '模型参数设置',
		region: 'east',
		width: 460,
//		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
//		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: RriskModelStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskParmColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [detailMenu],
		loadMask: {
			msg: '正在加载风控模型参数......'
		},
		bbar: new Ext.PagingToolbar({
			store: RriskModelStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	RriskModelStore.on('beforeload', function() {
				detailGrid.getStore().rejectChanges();
		RriskModelStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			saModelKind: grid.getSelectionModel().getSelected().data.saModelKind
			
		});
		detailGrid.getTopToolbar().items.items[0].disable();
			});

	detailGrid.on({
	
	
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(detailGrid.getTopToolbar().items.items[0] != undefined) {
				detailGrid.getTopToolbar().items.items[0].enable();
			}
		}
	});
			
	detailGrid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
//			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last))
//					.frame();
//			detailGrid.getTopToolbar().items.items[0].enable();
			
			
		}
	});

	
	
	// 风险模型列表
	var grid = new Ext.grid.EditorGridPanel({
//		plugins: rowExpander,
				title : '风险模型',
				iconCls : 'risk',
				frame : true,
				border : true,
				columnLines : true,
				stripeRows : true,
				region : 'center',
				sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
				clicksToEdit : true,
				store : riskModelStore,
				// sm: sm,
				autoExpandColumn : 'saModelDesc',
				cm : riskColModel,
				forceValidation : true,
				renderTo : Ext.getBody(),
				loadMask : {
					msg : '正在加载风险模型列表......'
				},
				tbar : menuArr,
				bbar : new Ext.PagingToolbar({
							store : riskModelStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
			});

	grid.getStore().on('beforeload', function() {
//				grid.getStore().rejectChanges();
//		RriskModelStore.removeAll();
		detailGrid.getStore().removeAll();
		detailGrid.getTopToolbar().items.items[0].disable();
		
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[0].disable();
		grid.getTopToolbar().items.items[4].disable();
	});


	grid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
					.frame();
			var saBeUse= grid.getSelectionModel().getSelected().data.saBeUse;
			if(saBeUse=='1'){
				grid.getTopToolbar().items.items[2].enable();
				grid.getTopToolbar().items.items[0].disable();
			}else if(saBeUse=='0'){
				grid.getTopToolbar().items.items[0].enable();
				grid.getTopToolbar().items.items[2].disable();
			}
			grid.getTopToolbar().items.items[4].enable();
//			RriskModelStore.removeAll();
			detailGrid.getStore().load();
//			RriskModelStore.load({
//				params: {
//					start: 0,
//					saModelKind: grid.getSelectionModel().getSelected().data.saModelKind
//				}
//			})
			if(grid.getSelectionModel().getSelected().data.saModelKind=='A10'){
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0\d)$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者00-23';
			}else{
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0)(\.\d{1,2})?$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者最多保留2位的小数';
			}
			
			/*riskLvlStore.removeAll();
			SelectOptionsDWR.getComboDataWithParameter('RISK_LVL',grid.getSelectionModel().getSelected().data.saModelKind,function(ret) {
						riskLvlStore.loadData(Ext.decode(ret));

			});*/
			
				detailGrid.getTopToolbar().items.items[0].disable();
		}
	});

	

	var mainView = new Ext.Viewport({
				layout : 'border',
				items : [grid,detailGrid],
				renderTo : Ext.getBody()
			});
});