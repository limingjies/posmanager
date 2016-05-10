Ext.onReady(function() {
	
//	==================================主配置-名称====================================
	// 数据集
	var feeCtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhFeeCtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discId',mapping: 'discId'},
			{name: 'discName',mapping: 'discName'},
			{name: 'lstUpdTime',mapping: 'lstUpdTime'},
			{name: 'lstUpdTlr',mapping: 'lstUpdTlr'},
			{name: 'createTime',mapping: 'createTime'}
		]),
		autoLoad: true
	}); 
	
	var feeCtlExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=500>',
		'<tr><td><font color=gray>最近更新日期：</font>{lstUpdTime:this.dateFromt()}</td></tr>',
		'<tr><td><font color=gray>最近更新操作员：</font>{lstUpdTlr}</td></tr>',
		'<tr><td><font color=gray>创建日期：</font>{createTime:this.dateFromt()}</td></tr>',
		'</table>',	
		{
			dateFromt: function(val) {
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'+ val.substring(6, 8)
//						+ ' ' + val.substring(8, 10) + ':'+ val.substring(10, 12) + ':' + val.substring(12, 14);
					}else {
						return val;
					}
				}	
		}
		)
	});
	var feeCtlColModel = new Ext.grid.ColumnModel([
		feeCtlExpander,
		new Ext.grid.RowNumberer(),
		{header: '费率代码',dataIndex: 'discId',align: 'center',width: 80},
		{header: '费率名称',dataIndex: 'discName',align: 'left',width: 120,id:'discName' }
	]);
	
	var tbar1 = new Ext.Toolbar({  
    	renderTo: Ext.grid.EditorGridPanel.tbar,  
			items:['-',	'费率代码：',{
				xtype: 'textfield',
				id: 'queryDiscId',
				name: 'queryDiscId',
				vtype:'isEngNum',
				maxLength: 5,
				width: 120
			},'-','费率名称：',{
				xtype: 'textfield',
				id: 'queryDiscName',
				name: 'queryDiscName',
				maxLength: 30,
				width: 120
			}]  
	}) 
         
         
    var feeCtlGrid = new Ext.grid.GridPanel({
		width: 430,
		collapsible: true,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
//		region:'center',
		region: 'west',
		clicksToEdit: true,
		autoExpandColumn: 'discName',
		plugins: feeCtlExpander,
		store: feeCtlStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeCtlColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载分润费率信息......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				feeCtlStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryDiscId').setValue('');
				Ext.getCmp('queryDiscName').setValue('');
				feeCtlStore.load();
			}
		},'-',{
			xtype : 'button',
			text : '新增',
			name : 'addCtl',
			id : 'addCtl',
			iconCls : 'add',
			width : 70,
			handler : function() {
				addCtlWin.show();
			}
		},'-',{
			xtype : 'button',
			text : '修改',
			name : 'editCtl',
			id : 'editCtl',
			disabled : true,
			iconCls : 'edit',
			width : 70,
			handler : function() {
				var select = feeCtlGrid.getSelectionModel().getSelected();
				if (select == null) {
					showMsg("请选择一条记录后再进行操作。", feeCtlGrid);
					return;
				}
				var data = select.data;
				updCtlForm.getForm().findField('tblBrhFeeCtlUpd.discId').setValue(data.discId);
				updCtlForm.getForm().findField('tblBrhFeeCtlUpd.discName').setValue(data.discName);
				
				updCtlForm.getForm().clearInvalid();

				updCtlWin.show();
//						showCmpProcess(updForm, '正在加载路由信息，请稍后......');
//						hideCmpProcess(updForm, 1000);
			}
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'deleteCtl',
			id : 'deleteCtl',
			disabled : true,
			iconCls : 'delete',
			width : 70,
			handler : function() {
				showConfirm('确定要删除该费率信息吗？', feeCtlGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10103Action.asp?method=deleteCtl',
							params : {
								discId : feeCtlGrid.getSelectionModel().getSelected().get('discId'),
								txnId : '10103',
								subTxnId : '03'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, feeCtlGrid);
									feeCtlGrid.getStore().reload();

								} else {
//									showErrorMsg(rspObj.msg, feeCtlGrid);
									showAlertMsg(rspObj.msg, feeCtlGrid);
								}
							}
						});
					}
				})
			}
		}],
		listeners : {  
            'render' : function() {  
					tbar1.render(this.tbar); 
         		}
        }  ,
		bbar: new Ext.PagingToolbar({
			store: feeCtlStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	
//	==============================主配置-名称结束=======================================
	
	
	
	
//	==============================详细费率配置=======================================
	// 数据集
	var feeCfgStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhFeeCfg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discIdCfg',mapping: 'discId'},
			{name: 'seq',mapping: 'seq'},
			{name: 'name',mapping: 'name'},
			{name: 'enableFlag',mapping: 'enableFlag'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtGrpNm',mapping: 'mchtGrpNm'},
//			{name: 'joinType',mapping: 'joinType'},
//			{name: 'joinBegDate',mapping: 'joinBegDate'},
//			{name: 'joinEndDate',mapping: 'joinEndDate'},
//			{name: 'mchtFeeLow',mapping: 'mchtFeeLow'},
//			{name: 'mchtFeeUp',mapping: 'mchtFeeUp'},
//			{name: 'mchtCapFeeLow',mapping: 'mchtCapFeeLow'},
//			{name: 'mchtCapFeeUp',mapping: 'mchtCapFeeUp'},
			{name: 'promotionBegDate',mapping: 'promotionBegDate'},
			{name: 'promotionEndDate',mapping: 'promotionEndDate'},
			{name: 'baseAmtMonth',mapping: 'baseAmtMonth'},
			{name: 'gradeAmtMonth',mapping: 'gradeAmtMonth'},
			{name: 'promotionRate',mapping: 'promotionRate'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'capFeeValue',mapping: 'capFeeValue'},
			{name: 'allotRate',mapping: 'allotRate'},
			{name: 'lstUpdTimeCfg',mapping: 'lstUpdTime'},
			{name: 'lstUpdTlrCfg',mapping: 'lstUpdTlr'},
			{name: 'createTimeCfg',mapping: 'createTime'}
		])
	});
	
	var feeCfgExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=500>',
		'<tr><td><font color=gray>最近更新日期：</font>{lstUpdTimeCfg:this.dateFromt}</td></tr>',
		'<tr><td><font color=gray>最近更新操作员：</font>{lstUpdTlrCfg}</td></tr>',
		'<tr><td><font color=gray>创建日期：</font>{createTimeCfg:this.dateFromt}</td></tr>',
		'</table>',	
			{dateFromt: function(val) {
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'+ val.substring(6, 8)
//						+ ' ' + val.substring(8, 10) + ':'+ val.substring(10, 12) + ':' + val.substring(12, 14);
					}else {
						return val;
					}
				}	
			}
		)
	});
	var feeCfgColModel = new Ext.grid.ColumnModel([
		feeCfgExpander,
		new Ext.grid.RowNumberer(),
		{header: '名称描述',dataIndex: 'name',id:'name',width: 180 ,align: 'left'},
//		{header: '入网类型',dataIndex: 'joinType',width: 70 ,renderer:joinType,align: 'center'},
		{header: '启用状态',dataIndex: 'enableFlag',width: 70 ,align: 'center',renderer:feeCfgStatus},
		{header: '商户编号',dataIndex: 'mchtNoNm',id:'mchtNoNm',width: 200 ,renderer:feeCtl},
		{header: '商户组别',dataIndex: 'mchtGrpNm',width: 100,align: 'center',renderer:feeCtl },
		{header: '激励起始日期',dataIndex: 'promotionBegDate',align: 'center',width: 100,renderer:feeCtl },
		{header: '激励终止日期',dataIndex: 'promotionEndDate',align: 'center',width: 100 ,renderer:feeCtl},
//		{header: '商户费率下限',dataIndex: 'mchtFeeLow',width: 100 ,align: 'right',renderer:feeCtl},
//		{header: '商户费率上限',dataIndex: 'mchtFeeUp',width: 100 ,align: 'right',renderer:feeCtl},
//		{header: '封顶费率下限',dataIndex: 'mchtCapFeeLow',width: 100 ,align: 'right',renderer:feeCtl},
//		{header: '封顶费率上限',dataIndex: 'mchtCapFeeUp',width: 100 ,align: 'right',renderer:feeCtl},
		{header: '月交易额',dataIndex: 'baseAmtMonth',width: 100 ,align: 'right',renderer:feeCtl},
		{header: '提档交易额',dataIndex: 'gradeAmtMonth',width: 100 ,align: 'right',renderer:feeCtl},
		{header: '激励系数',dataIndex: 'promotionRate',width: 100 ,align: 'right',renderer:feeCtl},
		{header: '成本费率',dataIndex: 'feeRate',width: 90 ,align: 'right',renderer:feeCtl},
		{header: '成本封顶值',dataIndex: 'capFeeValue',width: 90 ,align: 'right',renderer:feeCtl},
		{header: '分润费率',dataIndex: 'allotRate',width: 90 ,align: 'right'}
	]);
	
	
	var feeCfgGrid = new Ext.grid.GridPanel({
//		region: 'east',
//		collapsible: true,
		region:'center',
		width: 480,
		split: true,
//		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
//		autoExpandColumn: 'mchtNoNm',
		plugins: feeCfgExpander,
		store: feeCfgStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: feeCfgColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载分润费率信息......'
		},
		tbar: 	['-', {
			xtype : 'button',
			text : '启用',
			name : 'start',
			id : 'start',
			disabled : true,
			iconCls : 'accept',
			width : 80,
			handler : function() {
				showConfirm('确定要启用该费率项吗？', feeCfgGrid, function(bt) {
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10103Action.asp?method=start',
							params : {
								discId : feeCfgGrid.getSelectionModel().getSelected().get('discIdCfg'),
								seq : feeCfgGrid.getSelectionModel().getSelected().get('seq'),
								txnId : '10103',
								subTxnId : '07'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, feeCfgGrid);
									feeCfgGrid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, feeCfgGrid);
								}
							}
						});
					}
				})
			}
		},'-', {
			xtype : 'button',
			text : '停用',
			name : 'stop',
			id : 'stop',
			disabled : true,
			iconCls : 'stop',
			width : 80,
			handler : function() {
				showConfirm('确定要停用该费率项吗？', feeCfgGrid, function(bt) {
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10103Action.asp?method=stop',
							params : {
								discId : feeCfgGrid.getSelectionModel().getSelected().get('discIdCfg'),
								seq : feeCfgGrid.getSelectionModel().getSelected().get('seq'),
								txnId : '10103',
								subTxnId : '08'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, feeCfgGrid);
									feeCfgGrid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, feeCfgGrid);
								}
							}
						});
					}
				})
			}
		},'-',{
			xtype : 'button',
			text : '新增',
			name : 'addCfg',
			id : 'addCfg',
			disabled : true,
			iconCls : 'add',
			width : 70,
			handler : function() {
				addCfgWin.show();
			}
		},'-',{
			xtype : 'button',
			text : '修改',
			name : 'editCfg',
			id : 'editCfg',
			disabled : true,
			iconCls : 'edit',
			width : 70,
			handler : function() {
				var select = feeCfgGrid.getSelectionModel().getSelected();
				if (select == null) {
					showMsg("请选择一条记录后再进行操作。", feeCfgGrid);
					return;
				}
				var data = select.data;
				updCfgForm.getForm().findField('discIdUpd').setValue(data.discIdCfg);
//				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.joinType').setValue(data.joinType);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.name').setValue(data.name);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.mchtNo').setValue(data.mchtNo);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.mchtGrp').setValue(data.mchtGrp);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.promotionBegDate').setValue(data.promotionBegDate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.promotionEndDate').setValue(data.promotionEndDate);
//				updCfgForm.getForm().findField('mchtFeeLowUpd').setValue(data.mchtFeeLow);
//				updCfgForm.getForm().findField('mchtFeeUpUpd').setValue(data.mchtFeeUp);
//				updCfgForm.getForm().findField('mchtCapFeeLowUpd').setValue(data.mchtCapFeeLow);
//				updCfgForm.getForm().findField('mchtCapFeeUpUpd').setValue(data.mchtCapFeeUp);
				updCfgForm.getForm().findField('baseAmtMonthUpd').setValue(data.baseAmtMonth);
				updCfgForm.getForm().findField('gradeAmtMonthUpd').setValue(data.gradeAmtMonth);
				updCfgForm.getForm().findField('promotionRateUpd').setValue(data.promotionRate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.feeRate').setValue(data.feeRate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.capFeeValue').setValue(data.capFeeValue);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.allotRate').setValue(data.allotRate);
				updCfgForm.getForm().clearInvalid();
				updCfgWin.show();
//						showCmpProcess(updForm, '正在加载路由信息，请稍后......');
//						hideCmpProcess(updForm, 1000);
			}
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'deleteCfg',
			id : 'deleteCfg',
			disabled : true,
			iconCls : 'delete',
			width : 70,
			handler : function() {
				showConfirm('确定要删除该费率信息吗？', feeCfgGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10103Action.asp?method=deleteCfg',
							params : {
								discId : feeCfgGrid.getSelectionModel().getSelected().get('discIdCfg'),
								seq : feeCfgGrid.getSelectionModel().getSelected().get('seq'),
								txnId : '10103',
								subTxnId : '06'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, feeCfgGrid);
									feeCfgGrid.getStore().reload();

								} else {
//									showErrorMsg(rspObj.msg, feeCtlGrid);
									showAlertMsg(rspObj.msg, feeCfgGrid);
								}
							}
						});
					}
				})
			}
		}],
		bbar: new Ext.PagingToolbar({
			store: feeCfgStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
//	====================================详细费率配置结束=================================================
	
	
	
//	====================================加载=================================================
	feeCtlGrid.getStore().on('beforeload',function() {
			feeCfgGrid.getStore().removeAll();
			Ext.getCmp('deleteCtl').disable();
			Ext.getCmp('editCtl').disable();
			Ext.getCmp('addCfg').disable();
	});
	
	feeCtlGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(feeCtlGrid.getView().getRow(feeCtlGrid.getSelectionModel().last)).frame();
			feeCfgStore.load();
			Ext.getCmp('deleteCtl').enable();
			Ext.getCmp('editCtl').enable();
			Ext.getCmp('addCfg').enable();
		}
	});
	
	
	feeCtlStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryDiscId: Ext.getCmp('queryDiscId').getValue(),
			queryDiscName: Ext.getCmp('queryDiscName').getValue()
		});
	});
	
//	-----------------------------------------------------------------------------------------
	
	feeCfgGrid.getStore().on('beforeload',function() {
			Ext.getCmp('stop').disable();
			Ext.getCmp('start').disable();
			Ext.getCmp('editCfg').disable();
			Ext.getCmp('deleteCfg').disable();
	});
	feeCfgGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(feeCfgGrid.getView().getRow(feeCfgGrid.getSelectionModel().last)).frame();
			
			var onFlag=feeCfgGrid.getSelectionModel().getSelected().get('enableFlag');
			if(onFlag=='0'){
				Ext.getCmp('start').enable();
				Ext.getCmp('stop').disable();
				Ext.getCmp('deleteCfg').enable();
			}else{
				Ext.getCmp('stop').enable();
				Ext.getCmp('start').disable();
				Ext.getCmp('deleteCfg').disable();
			}
			Ext.getCmp('editCfg').enable();
		}
	});
	
	
	feeCfgStore.on('beforeload', function(){
		feeCfgStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			discId: feeCtlGrid.getSelectionModel().getSelected().get('discId')
		});
	});
	
//	==============================================================================================
	
	
	
//	========================================addCtlWin开始==========================================
	var addCtlForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 80,
//		height : 190,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			columnWidth : .99,
			layout : 'form',
			// width: 150,
			items : [{
				xtype : 'textfield',
				fieldLabel : '费率代码*',
				id:'tblBrhFeeCtlAdd.discId',
				name : 'tblBrhFeeCtlAdd.discId',
				maxLength : 5,
				minLength : 5,
				allowBlank : false,
				blankText : '费率代码不能为空',
				width : 200
			}]
		}, {
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '费率名称*',
				id:'tblBrhFeeCtlAdd.discName',
				name : 'tblBrhFeeCtlAdd.discName',
				allowBlank : false,
				maxLength : 30,
				blankText : '费率名称不能为空',
				width : 200
			}]
		}]
	});
	
	var addCtlWin = new Ext.Window({
		title : '合作伙伴分润费率代码新增',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 350,
//		autoHeight : true,
		animateTarget: 'addCtl',
		layout : 'fit',
		items : [addCtlForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if(addCtlForm.getForm().isValid()) {
					addCtlForm.getForm().submit({
						url : 'T10103Action.asp?method=addCtl',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addCtlForm);
							addCtlForm.getForm().reset();
							feeCtlGrid.getStore().reload();
							addCtlWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addCtlForm);
						},
						params : {
							txnId : '10103',
							subTxnId : '01'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addCtlForm.getForm().reset();
			}
		}]
	});
//	========================================addCtlWin结束==========================================
	
	
//	========================================updCtlWin开始==========================================
	var updCtlForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 80,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			columnWidth : .99,
			layout : 'form',
			// width: 150,
			items : [{
				xtype : 'displayfield',
				fieldLabel : '费率代码*',
				id:'tblBrhFeeCtlUpd.discId',
				name : 'tblBrhFeeCtlUpd.discId',
				width : 200
			}]
		}, {
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '费率名称*',
				id:'tblBrhFeeCtlUpd.discName',
				name : 'tblBrhFeeCtlUpd.discName',
				allowBlank : false,
				maxLength : 30,
				blankText : '费率名称不能为空',
				width : 200
			}]
		}]
	});
	
	var updCtlWin = new Ext.Window({
		title : '合作伙伴分润费率名称修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 350,
		autoHeight : true,
		layout : 'fit',
		animateTarget: 'updCtl',
		items : [updCtlForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if(updCtlForm.getForm().isValid()) {
					updCtlForm.getForm().submit({
						url : 'T10103Action.asp?method=updateCtl',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,updCtlForm);
							updCtlForm.getForm().reset();
							feeCtlGrid.getStore().reload();
							updCtlWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,updCtlForm);
						},
						params : {
							discId : feeCtlGrid.getSelectionModel().getSelected().get('discId'),
							txnId : '10103',
							subTxnId : '02'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updCtlForm.getForm().reset();
				var select = feeCtlGrid.getSelectionModel().getSelected();
				var data = select.data;
				updCtlForm.getForm().findField('tblBrhFeeCtlUpd.discId').setValue(data.discId);
				updCtlForm.getForm().findField('tblBrhFeeCtlUpd.discName').setValue(data.discName);
				
				updCtlForm.getForm().clearInvalid();
			}
		}]
	});
//	========================================updCtlWin结束==========================================
			
	
//	========================================addCfgWin开始==========================================
	var addCfgForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 80,
//		height : 190,
		autoScroll : true,
//		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '基本信息',
			layout : 'column',
			// collapsible : true,
			labelWidth : 110,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				// width: 150,
				items : [{
					xtype : 'displayfield',
					fieldLabel : '费率代码',
					id:'tblBrhFeeCfgZlfAdd.tblBrhFeeCfgPK.discId',
					name : 'tblBrhFeeCfgZlfAdd.tblBrhFeeCfgPK.discId',
					width : 200
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '规则名称*',
					id:'tblBrhFeeCfgZlfAdd.name',
					name : 'tblBrhFeeCfgZlfAdd.name',
					allowBlank : false,
					maxLength : 30,
					blankText : '费率名称不能为空',
					width : 659
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'dynamicCombo',
					methodName : 'getMchntId',
					fieldLabel : '商户编号',
//					id:'tblBrhFeeCfgZlfAdd.mchtNo',
					hiddenName : 'tblBrhFeeCfgZlfAdd.mchtNo',
					allowBlank : true,
//					maxLength : 30,
//					blankText : '费率名称不能为空',
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'MCHNT_GRP_ALL',
					fieldLabel : '商户组别',
//					id:'tblBrhFeeCfgZlfAdd.mchtNo',
					hiddenName : 'tblBrhFeeCfgZlfAdd.mchtGrp',
					allowBlank : true,
					editable: true,
//					maxLength : 30,
//					blankText : '费率名称不能为空',
					width : 250
				}]
			}]
		},{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '费率信息',
			layout : 'column',
			// collapsible : true,
			labelWidth : 110,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'datefield',
					fieldLabel : '激励开始日期',
					id:'tblBrhFeeCfgZlfAdd.promotionBegDate',
					name : 'tblBrhFeeCfgZlfAdd.promotionBegDate',
					vtype : 'daterange',
					endDateField : 'tblBrhFeeCfgZlfAdd.promotionEndDate',
					format : 'Y-m-d',
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'datefield',
					fieldLabel : '激励结束日期',
					id:'tblBrhFeeCfgZlfAdd.promotionEndDate',
					name : 'tblBrhFeeCfgZlfAdd.promotionEndDate',
					vtype : 'daterange',
					startDateField : 'tblBrhFeeCfgZlfAdd.promotionBegDate',
					format : 'Y-m-d',
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '月交易额(万元)*',
					id:'baseAmtMonth',
					name : 'baseAmtMonth',
					allowBlank : false,
					blankText : '月交易额不能为空',
//					maxLength : 15,
					maxValue : 9999999999999.99,
					minValue : 0, 
					maxText : '不能超过9999999999999.99',
					minText : '请输入大于0的值',
					decimalPrecision : 2, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '提档交易额(万元)*',
					id:'gradeAmtMonth',
					name : 'gradeAmtMonth',
					allowBlank : false,
					blankText : '提档交易额不能为空',
//					maxLength : 15,
					maxValue : 9999999999999.99,
					minValue : 0, 
					maxText : '不能超过9999999999999.99',
					minText : '请输入大于0的值',
					decimalPrecision : 2, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '激励系数*',
					id:'promotionRate',
					name : 'promotionRate',
					allowBlank : false,
					blankText : '激励系数不能为空',
//					maxLength : 12,
					maxValue : 1,
					minValue : 0, 
					maxText : '不能超过1',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '成本费率(%)*',
					id:'tblBrhFeeCfgZlfAdd.feeRate',
					name : 'tblBrhFeeCfgZlfAdd.feeRate',
					allowBlank : false,
					blankText : '成本费率不能为空',
//					maxLength : 12,
					maxValue : 100,
					minValue : 0, 
					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '成本封顶值*',
					id:'tblBrhFeeCfgZlfAdd.capFeeValue',
					name : 'tblBrhFeeCfgZlfAdd.capFeeValue',
					allowBlank : false,
					blankText : '成本费率不能为空',
//					maxLength : 12,
					maxValue : 999999999999.99,
					minValue : 0, 
					maxText : '不能超过999999999999.99',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '分润费率*',
					id:'tblBrhFeeCfgZlfAdd.allotRate',
					name : 'tblBrhFeeCfgZlfAdd.allotRate',
					allowBlank : false,
//					maxLength : 12,
					maxValue : 1,
					minValue : 0, 
					maxText : '不能超过1',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			}]
		}]
	});
	
	var addCfgWin = new Ext.Window({
		title : '分润费率规则新增',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
//		width : 350,
		width : 900,
//		autoHeight : true,
		animateTarget: 'addCfg',
		layout : 'fit',
		items : [addCfgForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if(addCfgForm.getForm().isValid()) {
					addCfgForm.getForm().submit({
						url : 'T10103Action.asp?method=addCfg',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addCfgForm);
							addCfgForm.getForm().reset();
							feeCfgGrid.getStore().reload();
							addCfgWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addCfgForm);
						},
						params : {
							discId : feeCtlGrid.getSelectionModel().getSelected().get('discId'),
							txnId : '10103',
							subTxnId : '04'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addCfgForm.getForm().reset();
			}
		}]
	});
//	========================================addCtlWin结束==========================================
	
	//	========================================updCfgWin开始==========================================
	var updCfgForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 80,
//		height : 190,
		autoScroll : true,
//		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '基本信息',
			layout : 'column',
			// collapsible : true,
			labelWidth : 110,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				// width: 150,
				items : [{
					xtype : 'displayfield',
					fieldLabel : '费率代码',
					id:'discIdUpd',
					name : 'discIdUpd',
					width : 200
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '规则名称*',
					id:'tblBrhFeeCfgZlfUpd.name',
					name : 'tblBrhFeeCfgZlfUpd.name',
					allowBlank : false,
					maxLength : 30,
					blankText : '费率名称不能为空',
					width : 659
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'dynamicCombo',
					methodName : 'getMchntId',
					fieldLabel : '商户编号',
//					id:'tblBrhFeeCfgAdd.mchtNo',
					hiddenName : 'tblBrhFeeCfgZlfUpd.mchtNo',
					allowBlank : true,
//					maxLength : 30,
//					blankText : '费率名称不能为空',
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'MCHNT_GRP_ALL',
					fieldLabel : '商户组别',
//					id:'tblBrhFeeCfgAdd.mchtNo',
					hiddenName : 'tblBrhFeeCfgZlfUpd.mchtGrp',
					allowBlank : true,
					editable: true,
//					maxLength : 30,
//					blankText : '费率名称不能为空',
					width : 250
				}]
			}]
		},{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '费率信息',
			layout : 'column',
			// collapsible : true,
			labelWidth :110,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'datefield',
						fieldLabel : '激励开始日期',
						id:'tblBrhFeeCfgZlfUpd.promotionBegDate',
						name : 'tblBrhFeeCfgZlfUpd.promotionBegDate',
						allowBlank : true,
//						vtype : 'daterange',
//						endDateField : 'tblBrhFeeCfgUpd.joinEndDate',
						format : 'Y-m-d',
						width : 250
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'datefield',
						fieldLabel : '激励结束日期',
						id:'tblBrhFeeCfgZlfUpd.promotionEndDate',
						name : 'tblBrhFeeCfgZlfUpd.promotionEndDate',
//						vtype : 'daterange',
//						startDateField : 'tblBrhFeeCfgUpd.joinBegDate',
						format : 'Y-m-d',
						width : 250
					}]
				},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '月交易额(万元)*',
					id:'baseAmtMonthUpd',
					name : 'baseAmtMonthUpd',
					allowBlank : true,
//					maxLength : 15,
					maxValue : 100,
					minValue : 0, 
					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 2, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '提档交易额(万元)*',
					id:'gradeAmtMonthUpd',
					name : 'gradeAmtMonthUpd',
					allowBlank : true,
//					maxLength : 15,
					maxValue : 100,
					minValue : 0, 
					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 2, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '激励系数*',
					id:'promotionRateUpd',
					name : 'promotionRateUpd',
					allowBlank : true,
					maxLength : 12,
//					maxValue : 100,
					minValue : 0, 
//					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 2, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '成本费率(%)*',
					id:'tblBrhFeeCfgZlfUpd.feeRate',
					name : 'tblBrhFeeCfgZlfUpd.feeRate',
					allowBlank : false,
//					maxLength : 12,
					maxValue : 100,
					minValue : 0, 
					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '成本封顶值*',
					id:'tblBrhFeeCfgZlfUpd.capFeeValue',
					name : 'tblBrhFeeCfgZlfUpd.capFeeValue',
					allowBlank : false,
					maxLength : 12,
//					maxValue : 100,
					minValue : 0, 
//					maxText : '不能超过100',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype: 'numberfield',
					fieldLabel : '分润费率*',
					id:'tblBrhFeeCfgZlfUpd.allotRate',
					name : 'tblBrhFeeCfgZlfUpd.allotRate',
					allowBlank : false,
//					maxLength : 12,
					maxValue : 1,
					minValue : 0, 
					maxText : '不能超过1',
					minText : '请输入大于0的值',
					decimalPrecision : 3, 
					width : 250
				}]
			}]
		}]
	});
	
	var updCfgWin = new Ext.Window({
		title : '分润费率规则修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
//		width : 350,
		width : 900,
//		autoHeight : true,
		animateTarget: 'updCfg',
		layout : 'fit',
		items : [updCfgForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if(updCfgForm.getForm().isValid()) {
					updCfgForm.getForm().submit({
						url : 'T10103Action.asp?method=updateCfg',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,updCfgForm);
							updCfgForm.getForm().reset();
							feeCfgGrid.getStore().reload();
							updCfgWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,updCfgForm);
						},
						params : {
							discId : feeCfgGrid.getSelectionModel().getSelected().get('discIdCfg'),
							seq : feeCfgGrid.getSelectionModel().getSelected().get('seq'),
							txnId : '10103',
							subTxnId : '04'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updCfgForm.getForm().reset();
				var data = feeCfgGrid.getSelectionModel().getSelected().data;
				
				updCfgForm.getForm().findField('discIdUpd').setValue(data.discIdCfg);
//				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.joinType').setValue(data.joinType);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.name').setValue(data.name);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.mchtNo').setValue(data.mchtNo);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.mchtGrp').setValue(data.mchtGrp);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.promotionBegDate').setValue(data.promotionBegDate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.promotionEndDate').setValue(data.promotionEndDate);
//				updCfgForm.getForm().findField('mchtFeeLowUpd').setValue(data.mchtFeeLow);
//				updCfgForm.getForm().findField('mchtFeeUpUpd').setValue(data.mchtFeeUp);
//				updCfgForm.getForm().findField('mchtCapFeeLowUpd').setValue(data.mchtCapFeeLow);
//				updCfgForm.getForm().findField('mchtCapFeeUpUpd').setValue(data.mchtCapFeeUp);
				updCfgForm.getForm().findField('baseAmtMonthUpd').setValue(data.baseAmtMonth);
				updCfgForm.getForm().findField('gradeAmtMonthUpd').setValue(data.gradeAmtMonth);
				updCfgForm.getForm().findField('promotionRateUpd').setValue(data.promotionRate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.feeRate').setValue(data.feeRate);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.capFeeValue').setValue(data.capFeeValue);
				updCfgForm.getForm().findField('tblBrhFeeCfgZlfUpd.allotRate').setValue(data.allotRate);
				
				updCfgForm.getForm().clearInvalid();
			}
		}]
	});
//	========================================updCtlWin结束==========================================
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [feeCtlGrid,feeCfgGrid],
		renderTo: Ext.getBody()
	});
});