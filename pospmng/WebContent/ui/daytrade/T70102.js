Ext.onReady(function() {
	
	// 商户参数数据集
	var acctMchtParamsStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=acctMchtParams'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhIdNm',mapping: 'brhIdNm'},
			{name: 'merchantIdNm',mapping: 'merchantIdNm'},
			{name: 'code0101',mapping: 'code0101'},
			{name: 'code0102',mapping: 'code0102'},
			{name: 'code0103',mapping: 'code0103'},
			{name: 'code0104',mapping: 'code0104'},
			{name: 'code0201',mapping: 'code0201'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'merchantId',mapping: 'merchantId'}
		]),
		autoLoad: true
	}); 
	
	var acctMchtParamsColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		{header: '机构编号',dataIndex: 'brhIdNm',align: 'left',width: 120,renderer : routeRule},
		{header: '商户编号',dataIndex: 'merchantIdNm',width: 250,align: 'left'},
		{header: '提现时段',dataIndex: 'code0101',width:100 ,align: 'center'},
		{header: '提现限额[百分比]',dataIndex: 'code0102',width: 120,align: 'right' },
		{header: '提现限额[日累额]',dataIndex: 'code0103',width: 120,align: 'right' },
		{header: '提现限额[单笔额]',dataIndex: 'code0104',width: 120,align: 'right' },
		{header: '科目代码',dataIndex: 'code0201',width: 100,align: 'center' }
	]);
	
	// 详细信息数据集
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=acctMchtParamsDtl'
		}),
		reader: new Ext.data.JsonReader({
			id: 'paramCode',
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'paramName',mapping: 'paramName'},
			{name: 'paramCode',mapping: 'paramCode'},
			{name: 'paramValue',mapping: 'paramValue'},
			{name: 'createDate',mapping: 'createDate'},
			{name: 'createBy',mapping: 'createBy'},
			{name: 'updateDate',mapping: 'updateDate'},
			{name: 'updateBy',mapping: 'updateBy'}
		])
	}); 
	
	var detailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '参数名称',dataIndex: 'paramName',align: 'left',width: 120},
		{header: '参数值',dataIndex: 'paramValue',width: 120,align: 'right',
		 editor: new Ext.form.TextField({
				id:'paramValuesUpd'
			})
		},
		{header: '修改时间',dataIndex: 'updateDate',width: 150,align: 'center'},
		{header: '修改人员',dataIndex: 'updateBy',width:80 ,align: 'center'},
		{header: '创建时间',dataIndex: 'createDate',width: 150,align: 'center'},
		{header: '创建人员',dataIndex: 'createBy',width:80 ,align: 'center'}
	]);
	
	// 新增参数数据集
	var addParamsGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=acctMchtParamsDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'paramName',mapping: 'paramName'},
			{name: 'paramCode',mapping: 'paramCode'},
			{name: 'paramValue',mapping: 'paramValue'}
		])
	});
	
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
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					merchantId: mchtParamsGrid.getSelectionModel().getSelected().data.merchantId,
					brhId: mchtParamsGrid.getSelectionModel().getSelected().data.brhId,
					paramCode : record.get('paramCode'),
					paramValue: record.get('paramValue')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T70102Action.asp?method=update',
				method: 'post',
				params: {
					dataList : Ext.encode(array),
					txnId: '70102',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						detailGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,detailGrid);
					} else {
						detailGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,detailGrid);
					}
					acctMchtParamsStore.reload();
				}
			});
		}
	};
	
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,
        items:['-','商户编号：',{
			xtype : 'dynamicCombo',
			methodName : 'getHaveAcctMchntId',
			hiddenName: 'queryAcctMchtId',
			width: 250,
			editable: true,
			id: 'idqueryAcctMchtId',
			mode:'local',
			triggerAction: 'all',
			forceSelection: true,
			selectOnFocus: true,
			lazyRender: true
/*		},'-','机构编号：',{
			xtype : 'dynamicCombo',
			methodName : 'getBrhId',
			hiddenName: 'queryBrhId',
		    editable: true,
			id: 'idqueryBrhId',
		    mode:'local',
			triggerAction: 'all',
			forceSelection: true,
			selectOnFocus: true,
			lazyRender: true*/
		}]
     });
	
	var detailGrid = new Ext.grid.EditorGridPanel({
		title: '商户参数详细信息',
		region: 'east',
		width: 750,
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [update],
		loadMask: {
			msg: '正在加载商户参数详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	var mchtParamsGrid = new Ext.grid.GridPanel({
		title: '商户参数信息',
		iconCls: 'logo',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: acctMchtParamsStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: acctMchtParamsColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户参数信息列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				acctMchtParamsStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryAcctMchtId').setValue('');
//				Ext.getCmp('idqueryBrhId').setValue('');
				acctMchtParamsStore.load();
			}
		}, '-', {
			xtype : 'button',
			text : '新增',
			name : 'add',
			id : 'add',
			iconCls : 'add',
			width : 80,
			handler : function() {
				addWin.show();
				addWin.center();
				addStoreReset();
			}
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'delete',
			id : 'delete',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该商户参数信息吗？', mchtParamsGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T70102Action.asp?method=delete',
							params : {
								brhId: mchtParamsGrid.getSelectionModel().getSelected().data.brhId,
								merchantId: mchtParamsGrid.getSelectionModel().getSelected().data.merchantId,
								txnId : '70102',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, mchtParamsGrid);
								} else {
									showErrorMsg(rspObj.msg, mchtParamsGrid);
								}
								mchtParamsGrid.getStore().reload();
								Ext.getCmp('delete').disable();
							}
						});
					}
				})
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
				tbar1.render(this.tbar); 
            }  
        },
		bbar: new Ext.PagingToolbar({
			store: acctMchtParamsStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	// 添加参数信息
	var addCol = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '参数名称',dataIndex: 'paramName',id: 'paramNameId',align: 'left',width: 120},
		{header: '参数值',dataIndex: 'paramValue',width: 120,align: 'right',
		 editor: new Ext.form.TextField({
				id:'paramValuesAdd'
			})
		}
	]);
	
	function addStoreReset() {
		paramsForm.getForm().reset();
		addParamsGridStore.removeAll();
		addParamsGridStore.load({
			params: {
				start: 0,
				queryMchtNo: '*',
				queryBrhId: '*'
			}
		});
	}
	
	var grid = new Ext.grid.EditorGridPanel({
		region: 'center',
		store: addParamsGridStore,
        autoExpandColumn: 'paramNameId',
        cm: addCol,
		width: 350,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        frame: true,
        columnLines: true,
		border: true,
        stripeRows: true,
        clicksToEdit: true
    });
	
    
	var paramsForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 60,
		waitMsgTarget: true,
		labelAlign: 'center',
		items: [{
			xtype: 'panel',
			layout: 'column',
			items: [{
/*	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype : 'dynamicCombo',
						methodName : 'getBrhId',
						id: 'brhIdAdd',
						fieldLabel: '机构编号',
					    editable: true,
					    mode:'local',
					    width: 300,
						triggerAction: 'all',
						forceSelection: true,
						selectOnFocus: true,
						lazyRender: true,
		                listeners:{
		                    'select': function() {
								Ext.getCmp('mchntIdAdd').setValue('');
		                    }
		                }
					}]
	        	}]
			},{*/
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype : 'dynamicCombo',
						methodName : 'getHaveAcctMchntId',
						id: 'mchntIdAdd',
						fieldLabel: '商户编号*',
						editable: true,
						allowblank: false,
						mode:'local',
					    width: 300,
						triggerAction: 'all',
						forceSelection: true,
						selectOnFocus: true,
						lazyRender: true/*,
		                listeners:{
		                    'select': function() {
								Ext.getCmp('brhIdAdd').setValue('');
		                    }
		                }*/
					}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 250,
			layout: 'border',
			items: [grid]
		}]
	});
	
	var addWin = new Ext.Window({
		title : '新增参数信息',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width: 400,
		autoHeight : true,
		layout : 'fit',
		items : [paramsForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				//存放要新增的参数信息
				var addArray = new Array();
				var merchantIdAdd = Ext.getCmp('mchntIdAdd').getValue();
/*				var brhIdAdd = Ext.getCmp('brhIdAdd').getValue();
				if((merchantIdAdd==null||merchantIdAdd=='')&&(brhIdAdd==null||brhIdAdd=='')){
					showMsg('机构编号和商户编号不能同时为空！',paramsForm);
					return;
				}*/
				if(merchantIdAdd==null||merchantIdAdd==''){
					return;
				}
				var addRecords = grid.getStore().getModifiedRecords();
				if(addRecords.length == 0) {
					showMsg('未添加相关参数！',paramsForm);
					return;
				}
				for(var index = 0; index < addRecords.length; index++) {
					var record = addRecords[index];
					if(record.get('paramValue')!=null&&record.get('paramValue')!=''){
						var data = {
							merchantId: merchantIdAdd,
							brhId: '*',
							paramCode : record.get('paramCode'),
							paramValue: record.get('paramValue')
						};
						addArray.push(data);
					}
				}
				if(addArray.length != 0){
					Ext.Ajax.request({
						url: 'T70102Action.asp?method=add',
						method: 'post',
						params: {
							dataList : Ext.encode(addArray),
							txnId: '70102',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								grid.getStore().commitChanges();
								addWin.hide();
								acctMchtParamsStore.reload();
								showSuccessMsg(rspObj.msg,paramsForm);
							} else {
								showErrorMsg(rspObj.msg,paramsForm);
							}
							addStoreReset();
						}
					});
				}else{
					showMsg('未添加相关参数！',paramsForm);
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addStoreReset();
			}
		}]
	});
	
	acctMchtParamsStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
//			queryBrhId: Ext.getCmp('idqueryBrhId').getValue(),
			queryMchtNo: Ext.getCmp('idqueryAcctMchtId').getValue()
		});
		detailGridStore.removeAll();
		Ext.getCmp('delete').disable();
	});
	
	mchtParamsGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchtParamsGrid.getView().getRow(mchtParamsGrid.getSelectionModel().last)).frame();
			detailGridStore.load({
				params: {
					start: 0,
					queryMchtNo: mchtParamsGrid.getSelectionModel().getSelected().data.merchantId,
					queryBrhId: mchtParamsGrid.getSelectionModel().getSelected().data.brhId
				}
			});
			Ext.getCmp('delete').enable();
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		detailGrid.getTopToolbar().items.items[0].disable();
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: mchtParamsGrid.getSelectionModel().getSelected().data.merchantId,
			queryBrhId: mchtParamsGrid.getSelectionModel().getSelected().data.brhId
		});
	});
	
	detailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last)).frame();
			var code = detailGrid.getSelectionModel().getSelected().data.paramCode;
			if(code=='0101'){
				Ext.getCmp('paramValuesUpd').regex=/^((([0-1][0-9])|2[0-3])[0-5][0-9]){2}$/;
				Ext.getCmp('paramValuesUpd').regexText= '提现时段值精确到分,如:06301859';
			}else if(code=='0102'){
				Ext.getCmp('paramValuesUpd').regex=/^(([1-9][0-9]|[0-9])(\.[0-9]{1,2})?|100)$/;
				Ext.getCmp('paramValuesUpd').regexText= '提现限额[百分比]值:1.00-100.00';
			}else if(code=='0103'){
				Ext.getCmp('paramValuesUpd').regex=/^([1-9][0-9]*|0)(\.[0-9]{1,2})?$/;
				Ext.getCmp('paramValuesUpd').regexText= '提现限额[日累额]值:整数或者最多保留2位的小数';
			}else if(code=='0104'){
				Ext.getCmp('paramValuesUpd').regex=/^([1-9][0-9]*|0)(\.[0-9]{1,2})?$/;
				Ext.getCmp('paramValuesUpd').regexText= '提现限额[单笔额]值:整数或者最多保留2位的小数';
			}else if(code=='0201'){
				Ext.getCmp('paramValuesUpd').regex=/^[0-9]{1,12}$/;
				Ext.getCmp('paramValuesUpd').regexText= '科目代码值:数字组合';
			}
		}
	});

	detailGrid.on({'afteredit': function(e) {
			if(e.record.data.paramCode=='0101'){
				if(e.value!=''&&e.value!=null&&e.value.substr(0, 4)>=e.value.substr(4, 4)){
					showMsg('开始时间必须小于截止时间！',detailGrid);
					e.record.set(e.field, e.originalValue);
				}
			}
			// 提现限额[百分比]与提现限额[日累额]这两个参数同一商户只能选择配置一种
			if(e.record.data.paramCode=='0102'){
				if(e.value!=null&&e.value!=''){
					detailGridStore.getById('0103').set(e.field, '');
				}
			}
			if(e.record.data.paramCode=='0103'){
				if(e.value!=null&&e.value!=''){
					detailGridStore.getById('0102').set(e.field, '');
				}
			}
			detailGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			var code = grid.getSelectionModel().getSelected().data.paramCode;
			if(code=='0101'){
				Ext.getCmp('paramValuesAdd').regex=/^((([0-1][0-9])|2[0-3])[0-5][0-9]){2}$/;
				Ext.getCmp('paramValuesAdd').regexText= '提现时段值精确到分,如:06301859';
			}else if(code=='0102'){
				Ext.getCmp('paramValuesAdd').regex=/^(([1-9][0-9]|[0-9])(\.[0-9]{1,2})?|100)$/;
				Ext.getCmp('paramValuesAdd').regexText= '提现限额[百分比]值:1.00-100.00';
			}else if(code=='0103'){
				Ext.getCmp('paramValuesAdd').regex=/^([1-9][0-9]*|0)(\.[0-9]{1,2})?$/;
				Ext.getCmp('paramValuesAdd').regexText= '提现限额[日累额]值:整数或者最多保留2位的小数';
			}else if(code=='0104'){
				Ext.getCmp('paramValuesAdd').regex=/^([1-9][0-9]*|0)(\.[0-9]{1,2})?$/;
				Ext.getCmp('paramValuesAdd').regexText= '提现限额[单笔额]值:整数或者最多保留2位的小数';
			}else if(code=='0201'){
				Ext.getCmp('paramValuesAdd').regex=/^[0-9]{1,12}$/;
				Ext.getCmp('paramValuesAdd').regexText= '科目代码值:数字组合';
			}
		}
	});

	grid.on({'afteredit': function(e) {
			if(e.record.data.paramCode=='0101'){
				if(e.value!=''&&e.value!=null&&e.value.substr(0, 4)>=e.value.substr(4, 4)){
					showMsg('开始时间必须小于截止时间！',grid);
					e.record.set(e.field, e.originalValue);
				}
			}
			if(e.record.data.paramCode=='0102'){
				if(e.value!=null&&e.value!=''){
					detailGridStore.getById('0103').set(e.field, '');
				}
			}
			if(e.record.data.paramCode=='0103'){
				if(e.value!=null&&e.value!=''){
					detailGridStore.getById('0102').set(e.field, '');
				}
			}
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchtParamsGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});