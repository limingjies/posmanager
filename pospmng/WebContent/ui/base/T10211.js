Ext.onReady(function() {
	
//	==================================主规则====================================
	// 数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ruleMchtMap'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ruleCode',mapping: 'ruleCode'},
			{name: 'srvId',mapping: 'srvId'},
			{name: 'msc1',mapping: 'msc1'},
			{name: 'srvIdName',mapping: 'srvIdName'}
		]),
		autoLoad: true
	}); 
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '规则代码',dataIndex: 'ruleCode',align: 'center',width: 100},
		{header: '规则名称',dataIndex: 'msc1',width: 200,id:'msc1' },
		{header: '通道机构',dataIndex: 'srvIdName',id:'srvId',width: 190 }
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'规则代码：',{
					xtype: 'textfield',
					id: 'queryRuleCode',
					name: 'queryRuleCode',
					vtype:'isEngNum',
					maxLength: 10,
					width: 140
				},'-',	'通道机构：',{
					xtype: 'basecomboselect',
					id: 'querySrvIdId',
					baseParams: 'SETTLE_BRH',
					hiddenName: 'querySrvId',
					mode:'local',
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				}
	            ]  
         }) 
         
         
    var routeRuleGrid = new Ext.grid.GridPanel({
		width: 460,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'msc1',
		store: routeRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeRuleStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryRuleCode').setValue('');
				Ext.getCmp('querySrvIdId').setValue('');
				routeRuleStore.load();
			}
		},'-', {
				xtype : 'button',
				text : '新增',
				name : 'add',
				id : 'add',
				iconCls : 'add',
				width : 80,
				handler : function() {
					addWin.show();
					addWin.center();
				}
		},'-', {
				xtype : 'button',
				text : '修改',
				name : 'edit',
				id : 'edit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					if (!routeRuleGrid.getSelectionModel().hasSelection()) {
						showMsg("请选择一条记录后再进行操作。", routeRuleGrid);
						return;
					}
					var select = routeRuleGrid.getSelectionModel().getSelected();
					var data = select.data;
					updForm.getForm().findField('tblRuleMchtRelUpd.tblRuleMchtRelPK.ruleCode').setValue(data.ruleCode.trim());
					updForm.getForm().findField('tblRuleMchtRelUpd.msc1').setValue(data.msc1);
//					updForm.getForm().findField('tblRuleMchtRelUpd.srvId').setValue(data.srvId);
					
					updWin.show();
					updWin.center();
				}
		},'-', {
				xtype : 'button',
				text : '删除',
				name : 'delete',
				id : 'delete',
				disabled : true,
				iconCls : 'delete',
				width : 80,
				handler : function() {
					showConfirm('确定要删除该规则吗？', routeRuleGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T10211Action.asp?method=delete',
								params : {
									ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
									txnId : '10211',
									subTxnId : '03'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, routeRuleGrid);
										routeRuleGrid.getStore().reload();
									} else {
										showErrorMsg(rspObj.msg, routeRuleGrid);
									}
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
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	
//	==============================主规则结束=======================================
	
	
	
	
//	==============================详细规则商户=======================================
	// 数据集
	var routeRuleDtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ruleMchtMapContl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'firstMchtNo',mapping: 'firstMchtNo'},
			{name: 'msc2',mapping: 'msc2'}
		])
	}); 
	
	var routeRuleDtlColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '一级商户号',dataIndex: 'firstMchtNm',id:'firstMchtNm',width: 190 },
		{header: '状态',dataIndex: 'msc2',width: 120 ,align: 'center',renderer:routeStatus}
	]);
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			/*'-',	'一级商户号：',{
					xtype: 'basecomboselect',
					id: 'queryFirstMchtCdId',
					baseParams: 'routeFirstMchtCd',
					hiddenName: 'queryFirstMchtCd',
					mode:'local',
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				},*/
				'-','状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryMsc2',
					editable: false,
					emptyText: '请选择',
					id:'queryMsc2Id',
					width: 140
				}
	            ]  
         }) 
	
	
	var grid = new Ext.grid.GridPanel({
		region: 'east',
		width: 480,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		autoExpandColumn: 'firstMchtNm',
		store: routeRuleDtlStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleDtlColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'queryDtl',
			id: 'queryDtl',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeRuleDtlStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetDtl',
			id: 'resetDtl',
			iconCls: 'reset',
			width: 80,
			handler:function() {
//				Ext.getCmp('queryFirstMchtCdId').setValue('');
				Ext.getCmp('queryMsc2Id').setValue('');
				routeRuleDtlStore.load();
			}

		
		
		},'-', {
			xtype : 'button',
			text : '新增',
			name : 'addDtl',
			id : 'addDtl',
			disabled : true,
			iconCls : 'add',
			width : 80,
			handler : function() {
				
				addDtlWin.show();
				addDtlWin.center();
				Ext.getCmp('ruleCodeDtl').setValue(routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'));
			}
		},'-', {
			xtype : 'button',
			text : '删除',
			name : 'deleteDtl',
			id : 'deleteDtl',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除此规则下的一级商户吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10211Action.asp?method=deleteDtl',
							params : {
								ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
								firstMchtCdDtl : grid.getSelectionModel().getSelected().get('firstMchtNo'),
								txnId : '10211',
								subTxnId : '05'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									if(rspObj.msg=="00"){
										showSuccessMsg('规则商户删除成功', grid);
										routeRuleGrid.getStore().reload();
									}else{
										showSuccessMsg(rspObj.msg, grid);
										grid.getStore().reload();
									}
								} else {
									showErrorMsg(rspObj.msg, grid);
								}
							}
						});
					}
				})
			}
		}
		],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleDtlStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
//	====================================详细规则商户结束=================================================
	
	
	
//	====================================加载=================================================
	// 禁用编辑按钮
	routeRuleGrid.getStore().on('beforeload',function() {
			Ext.getCmp('edit').disable();
			Ext.getCmp('delete').disable();
			grid.getStore().removeAll();
			Ext.getCmp('addDtl').disable();
	});
	
	routeRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			Ext.getCmp('edit').enable();
			Ext.getCmp('delete').enable();
			routeRuleDtlStore.load();
			Ext.getCmp('addDtl').enable();
			/*routeRuleDtlStore.load({
				params: {
					start: 0,
					queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
				}
			})*/
		}
	});
	
	
	routeRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleCode: Ext.getCmp('queryRuleCode').getValue(),
			querySrvId:Ext.get('querySrvId').getValue()
		});
	});
	
//	-----------------------------------------------------------------------------------------
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
			
			Ext.getCmp('deleteDtl').disable();
			
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			Ext.getCmp('detail').enable();
			var onFlag=grid.getSelectionModel().getSelected().get('msc2');
			if(onFlag=='0'){
				Ext.getCmp('deleteDtl').enable();
			}else{
				Ext.getCmp('deleteDtl').disable();
			}
		}
	});
	
	
	routeRuleDtlStore.on('beforeload', function(){
		routeRuleDtlStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
//			queryFirstMchtCd: Ext.get('queryFirstMchtCd').getValue(),
			queryMsc2:Ext.get('queryMsc2').getValue()
		});
	});
	
//	=======================================华丽的分割线=======================================================

			
	
//	=============================================主规则新增=================================================
	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth: 100,
		// width: 900,
		height : 430,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '规则代码*',
					allowBlank : false,
					blankText: '规则代码不能为空',
					emptyText : '请输入规则代码',
					id: 'tblRuleMchtRelAdd.tblRuleMchtRelPK.ruleCode',
					name: 'tblRuleMchtRelAdd.tblRuleMchtRelPK.ruleCode',
					vtype:'isEngNum',
					maxLength: 10,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '规则名称*',
					allowBlank : false,
					blankText: '规则名称不能为空',
					emptyText : '请输入规则名称',
					id: 'tblRuleMchtRelAdd.msc1',
					name: 'tblRuleMchtRelAdd.msc1',
					maxLength: 25,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'basecomboselect',
					id: 'firstMchtCdId',
					fieldLabel : '一级商户号*',
					baseParams: 'routeFirstMchtCd',
					hiddenName: 'tblRuleMchtRelAdd.tblRuleMchtRelPK.firstMchtCd',
					mode:'local',
					blankText: '一级商户号不能为空',
					emptyText : '请选择一级商户号',
					allowBlank : false,
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					fieldLabel : '通道机构*',
					xtype: 'basecomboselect',
					id: 'srvIdId',
					baseParams: 'SETTLE_BRH',
					hiddenName: 'tblRuleMchtRelAdd.srvId',
					mode:'local',
					triggerAction: 'all',
					allowBlank : false,
					editable: true,
					blankText: '通道机构不能为空',
					emptyText : '请选择通道机构',
					lazyRender: true,
					width: 250
				}]
			}
		]
	})
	var addWin = new Ext.Window({
		title : '新增规则商户映射',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [addForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url : 'T10211Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							routeRuleGrid.getStore().reload();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
							txnId : '10211',
							subTxnId : '01'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
			}
		}
		
		]
	});
//	=============================================主规则新增结束=================================================	
	
	
//	=============================================主规则修改=================================================
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
					xtype: 'displayfield',
					fieldLabel : '规则代码*',
					allowBlank : false,
					blankText: '规则代码不能为空',
					emptyText : '请输入规则代码',
					id: 'tblRuleMchtRelUpd.tblRuleMchtRelPK.ruleCode',
					name: 'tblRuleMchtRelUpd.tblRuleMchtRelPK.ruleCode',
					vtype:'isEngNum',
					maxLength: 10,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '规则名称*',
					allowBlank : false,
					blankText: '规则名称不能为空',
					emptyText : '请输入规则名称',
					id: 'tblRuleMchtRelUpd.msc1',
					name: 'tblRuleMchtRelUpd.msc1',
					maxLength: 25,
					width: 250
				}]
			}
		]
	});
			
	var updWin = new Ext.Window({
		title : '规则商户映射修改',
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
						url : 'T10211Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							routeRuleGrid.getStore().reload();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
							ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
							txnId : '10602',
							subTxnId : '02'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
				var select = routeRuleGrid.getSelectionModel().getSelected();
				
				var data = select.data;
				updForm.getForm().findField('tblRuleMchtRelUpd.tblRuleMchtRelPK.ruleCode').setValue(data.ruleCode.trim());
				updForm.getForm().findField('tblRuleMchtRelUpd.msc1').setValue(data.msc1);
//				updForm.getForm().findField('tblRuleMchtRelUpd.srvId').setValue(data.srvId);
			}
		}
		
		]
	});
//	=============================================主规则修改结束=================================================
	
	
//	=============================================主规则新增=================================================
	var addDtlForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth: 100,
		// width: 900,
		height : 430,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel : '规则代码',
					allowBlank : false,
					blankText: '规则代码不能为空',
					emptyText : '请输入规则代码',
					id: 'ruleCodeDtl',
					name: 'ruleCodeDtl',
					vtype:'isEngNum',
					maxLength: 10,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'basecomboselect',
					id: 'firstMchtCdDtlId',
					fieldLabel : '一级商户号*',
					baseParams: 'routeFirstMchtCd',
					hiddenName: 'firstMchtCdDtl',
					mode:'local',
					blankText: '一级商户号不能为空',
					emptyText : '请选择一级商户号',
					allowBlank : false,
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				}]
			}
		]
	})
	var addDtlWin = new Ext.Window({
		title : '新增规则一级商户',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [addDtlForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (addDtlForm.getForm().isValid()) {
					addDtlForm.getForm().submit({
						url : 'T10211Action.asp?method=addDtl',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addDtlForm);
							// 重置表单
							addDtlForm.getForm().reset();
							// 重新加载参数列表
							grid.getStore().reload();
							addDtlWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addDtlForm);
						},
						params : {
							ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
							msc1 : routeRuleGrid.getSelectionModel().getSelected().get('msc1'),
							srvId : routeRuleGrid.getSelectionModel().getSelected().get('srvId'),
							txnId : '10211',
							subTxnId : '04'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addDtlForm.getForm().reset();
				Ext.getCmp('ruleCodeDtl').setValue(routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'));
			}
		}
		
		]
	});
//	=============================================规则商户新增结束=================================================	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [routeRuleGrid,grid],
		renderTo: Ext.getBody()
	});
});