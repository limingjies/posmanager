Ext.onReady(function() {
	
	// 数据集
	var riskWhiteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskWhite'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'riskId',mapping: 'riskId'},
			{name: 'resved',mapping: 'resved'},
			{name: 'resved1',mapping: 'resved1'}
			
			
		]),
	autoLoad: true
	}); 
	
	var riskWhiteColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户号',dataIndex: 'cardAccpId',align: 'center',width: 150},
		{header: '商户名',dataIndex: 'mchtName',id:'mchtName',width: 200,align: 'left' },
		{header: '风控模型',dataIndex: 'riskId',width: 100 ,align: 'center'},
		{header: '模型类别',dataIndex: 'riskId',width: 200,renderer:riskName,align: 'left' },
		{header: '备注信息',dataIndex: 'resved',width: 250 ,align: 'left'}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'商户编号：',{
					xtype: 'basecomboselect',
					baseParams: 'MCHNT_NO',
//					xtype : 'dynamicCombo',
//					methodName : 'getMchntId',
					hiddenName: 'queryCardAccpId',
					width: 250,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
//					typeAhead: true,
					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true,
					listeners : {  
			            'beforequery':function(e){  
			                   
			                var combo = e.combo;    
			                if(!e.forceAll){    
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }  
			            }  
			        } },'-',	'风控模型：',{
					xtype: 'basecomboselect',
					id: 'idqueryRiskId',
					baseParams: 'KIND',
					hiddenName: 'queryRiskId',
					mode:'local',
					triggerAction: 'all',
					editable: false,
					lazyRender: true,
					width: 250
				}
	            ]  
         }) 
	
	
	
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '商户白名单管理',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mchtName',
		store: riskWhiteStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskWhiteColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载商户白名单列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				riskWhiteStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
//				Ext.getCmp('queryRisklvl').setValue('');
				Ext.getCmp('idqueryRiskId').setValue('');
				Ext.getCmp('idqueryCardAccpId').setValue('');
				riskWhiteStore.load();
				
				Ext.getCmp('idqueryCardAccpId').getStore().reload();
			
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
						var select = grid.getSelectionModel().getSelected();
						if (select == null) {
							showMsg("请选择一条记录后再进行操作。", grid);
							return;
						}
						var data = select.data;
						updForm.getForm().findField('tblRiskWhiteUpd.id.cardAccpId')
								.setValue(data.cardAccpId);
						updForm.getForm().findField('tblRiskWhiteUpd.id.riskId')
								.setValue(data.riskId);
						updForm.getForm().findField('tblRiskWhiteUpd.resved')
								.setValue(data.resved);
						
						updWin.show();
						updWin.center();
//						showCmpProcess(updForm, '正在加载规则商户映射信息，请稍后......');
//						hideCmpProcess(updForm, 1000);
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
						showConfirm('确定要删除该该商户白名单吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40207Action.asp?method=delete',
									params : {
										riskId : grid.getSelectionModel()
												.getSelected().get('riskId'),
										cardAccpId  : grid.getSelectionModel()
												.getSelected().get('cardAccpId'),
										txnId : '40207',
										subTxnId : '03'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);

										} else {
											showErrorMsg(rspObj.msg, grid);
										}
										grid.getStore().reload();
										Ext.getCmp('edit').disable();
										Ext.getCmp('delete').disable();
									}
								});
							}
						})

					}

		}
		],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: riskWhiteStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
			Ext.getCmp('edit').disable();
			Ext.getCmp('delete').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			Ext.getCmp('detail').enable();
//			if (grid.getSelectionModel().getSelected().get('msc2') == '0') {
				Ext.getCmp('delete').enable();
				Ext.getCmp('edit').enable();
//			} else {
//				Ext.getCmp('delete').disable();
//				Ext.getCmp('edit').disable();
//			}
		}
	});
	
	
	riskWhiteStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryCardAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
			queryRiskId: Ext.getCmp('idqueryRiskId').getValue()
		
		});
	});
	
	
	// 添加
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
							xtype: 'basecomboselect',
							baseParams: 'MCHNT_NO',
							fieldLabel : '商户编号*',
							blankText: '商户编号不能为空',
							emptyText : '请选择商户编号',
		//					xtype : 'dynamicCombo',
		//					methodName : 'getMchntId',
							hiddenName: 'tblRiskWhiteAdd.id.cardAccpId',
							width: 250,
//							id: 'idqueryCardAccpId',
							mode:'local',
							triggerAction: 'all',
							forceSelection: true,
		//					typeAhead: true,
							selectOnFocus: true,
							editable: true,
							allowBlank: true,
							lazyRender: true,
							listeners : {  
					            'beforequery':function(e){  
					                   
					                var combo = e.combo;    
					                if(!e.forceAll){    
					                    var input = e.query;    
					                    // 检索的正则  
					                    var regExp = new RegExp(".*" + input + ".*");  
					                    // 执行检索  
					                    combo.store.filterBy(function(record,id){    
					                        // 得到每个record的项目名称值  
					                        var text = record.get(combo.displayField);    
					                        return regExp.test(text);   
					                    });  
					                    combo.expand();    
					                    return false;  
					                }  
					            }  
					        } }]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'basecomboselect',
//							id: 'firstMchtCdId',
							fieldLabel : '风控模型*',
							baseParams: 'KIND',
							hiddenName: 'tblRiskWhiteAdd.id.riskId',
							mode:'local',
							blankText: '风控模型不能为空',
							emptyText : '请选择风控模型',
							allowBlank : false,
							triggerAction: 'all',
							editable: false,
							lazyRender: true,
							width: 250
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '备注信息',
							allowBlank : true,
//							blankText: '级别名称不能为空',
//							emptyText : '请输入级别名称',
							id: 'tblRiskWhiteAdd.resved',
							name: 'tblRiskWhiteAdd.resved',
							maxLength: 50,
							width: 250
						}]
				}
			
		]
	})
	
	
	var addWin = new Ext.Window({
				title : '新增商户白名单',
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
										url : 'T40207Action.asp?method=add',
										waitMsg : '正在提交，请稍后......',
										success : function(form, action) {
											showSuccessMsg(action.result.msg,
													addForm);
											// 重置表单
											addForm.getForm().reset();
											// 重新加载参数列表
											grid.getStore().reload();
											addWin.hide();
										},
										failure : function(form, action) {
											showErrorMsg(action.result.msg,
													addForm);
										},
										params : {
											txnId : '40207',
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
							baseParams: 'MCHNT_NO',
							fieldLabel : '商户编号*',
							blankText: '商户编号不能为空',
							emptyText : '请选择商户编号',
		//					xtype : 'dynamicCombo',
		//					methodName : 'getMchntId',
							hiddenName: 'tblRiskWhiteUpd.id.cardAccpId',
							width: 250,
//							id: 'idqueryCardAccpId',
							mode:'local',
							triggerAction: 'all',
							forceSelection: true,
		//					typeAhead: true,
							selectOnFocus: true,
							editable: true,
							allowBlank: true,
							lazyRender: true
							
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
//							id: 'firstMchtCdId',
							fieldLabel : '风控模型*',
							baseParams: 'KIND',
							hiddenName: 'tblRiskWhiteUpd.id.riskId',
							mode:'local',
							blankText: '风控模型不能为空',
							emptyText : '请选择风控模型',
							allowBlank : false,
							triggerAction: 'all',
							editable: false,
							lazyRender: true,
							width: 250
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '备注信息',
							allowBlank : true,
//							blankText: '级别名称不能为空',
//							emptyText : '请输入级别名称',
							id: 'tblRiskWhiteUpd.resved',
							name: 'tblRiskWhiteUpd.resved',
							maxLength: 50,
							width: 250
						}]
				}
		]
	});
			
	var updWin = new Ext.Window({
		title : '商户白名单修改',
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
						url : 'T40207Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							grid.getStore().reload();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
//							ruleCode : grid.getSelectionModel()
//												.getSelected().get('ruleCode'),
							riskId : grid.getSelectionModel()
												.getSelected().get('riskId'),
							cardAccpId  : grid.getSelectionModel()
												.getSelected().get('cardAccpId'),
							txnId : '40207',
							subTxnId : '02'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
				var select = grid.getSelectionModel().getSelected();
				
				var data = select.data;
//				updForm.getForm().findField('tblRiskWhiteUpd.resved')
//						.setValue(data.resved);
						
				updForm.getForm().findField('tblRiskWhiteUpd.id.cardAccpId')
						.setValue(data.cardAccpId);
				updForm.getForm().findField('tblRiskWhiteUpd.id.riskId')
						.setValue(data.riskId);
				updForm.getForm().findField('tblRiskWhiteUpd.resved')
						.setValue(data.resved);
				
			}
		}
		
		]
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});