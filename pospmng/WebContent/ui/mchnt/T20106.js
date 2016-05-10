Ext.onReady(function() {
	
	// 数据集
	var mchtNameStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblMchtName'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'accpId',mapping: 'accpId'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'msc1',mapping: 'msc1'}
			
			
		]),
	autoLoad: true
	}); 
	
	var mchtNameColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户号',dataIndex: 'accpId',id:'accpId',align: 'left',width: 150},
		{header: '卡类型',dataIndex: 'cardTp',width: 150 ,align: 'center',renderer:routeCardTp},
		{header: '交易类型',dataIndex: 'txnNum',width: 150,align: 'left' },
		{header: '商户别名',dataIndex: 'mchtNm',id:'mchtNm',width: 250,align: 'left' },
		{header: '更新日期',dataIndex: 'msc1',id:'msc1',width: 120,align: 'center' ,renderer:formatDt}
//		{header: '备注信息',dataIndex: 'resved',width: 250 ,align: 'left'}
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
			        } },'-','卡类型：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡'],['04','公务卡']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					editable: false,
					emptyText: '请选择',
					hiddenName: 'queryCardTp',
					id:'queryCardTpId',
					width: 140
				},'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'queryTxnNumId',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnNum',
					width: 140
				}
	            ]  
         }) 
	
	
	
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '商户别名维护',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'accpId',
		store: mchtNameStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchtNameColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风控级别记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				mchtNameStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
				Ext.getCmp('queryTxnNumId').setValue('');
				Ext.getCmp('queryCardTpId').setValue('');
				Ext.getCmp('idqueryCardAccpId').setValue('');
				mchtNameStore.load();
				
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
						updForm.getForm().findField('tblMchtNameUpd.id.accpId')
								.setValue(data.accpId);
						updForm.getForm().findField('tblMchtNameUpd.id.cardTp')
								.setValue(data.cardTp);
						updForm.getForm().findField('tblMchtNameUpd.id.txnNum')
								.setValue(data.txnNum);
						updForm.getForm().findField('tblMchtNameUpd.mchtNm')
								.setValue(data.mchtNm);
						
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
						showConfirm('确定要删除该商户别名吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T20106Action.asp?method=delete',
									params : {
										accpId : grid.getSelectionModel()
												.getSelected().get('accpId').substring(0,15),
										cardTp  : grid.getSelectionModel()
												.getSelected().get('cardTp'),
										txnNum  : grid.getSelectionModel()
												.getSelected().get('txnNum').substring(0,4),
										txnId : '20106',
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
			store: mchtNameStore,
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
	
	
	mchtNameStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
			queryCardTp: Ext.get('queryCardTp').getValue(),
			queryTxnNum:Ext.get('queryTxnNum').getValue()
		
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
							hiddenName: 'tblMchtNameAdd.id.accpId',
							width: 250,
//							id: 'idqueryCardAccpId',
							mode:'local',
							triggerAction: 'all',
							forceSelection: true,
		//					typeAhead: true,
							selectOnFocus: true,
							editable: true,
							allowBlank: false,
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
							xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡'],['04','公务卡']],
								reader: new Ext.data.ArrayReader()
							}),
							displayField: 'displayField',
							valueField: 'valueField',
							editable: false,
							fieldLabel : '卡类型*',
							hiddenName: 'tblMchtNameAdd.id.cardTp',
//							id:'queryCardTpId',
							mode:'local',
							blankText: '卡类型不能为空',
							emptyText : '请选择卡类型',
							allowBlank : false,
							triggerAction: 'all',
							lazyRender: true,
							width: 250
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'basecomboselect',
//							id: 'firstMchtCdId',
							fieldLabel : '交易类型*',
							baseParams: 'TXN_TYPES',
							hiddenName: 'tblMchtNameAdd.id.txnNum',
							mode:'local',
							blankText: '交易类型不能为空',
							emptyText : '请选择交易类型',
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
							fieldLabel : '商户别名',
							allowBlank : false,
							blankText: '商户别名不能为空',
							emptyText : '请输入商户别名',
							id: 'tblMchtNameAdd.mchtNm',
							name: 'tblMchtNameAdd.mchtNm',
							maxLength: 50,
							width: 250
						}]
				}
			
		]
	})
	
	
	var addWin = new Ext.Window({
				title : '新增商户别名',
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
										url : 'T20106Action.asp?method=add',
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
											txnId : '20106',
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
							hiddenName: 'tblMchtNameUpd.id.accpId',
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
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡'],['04','公务卡']],
								reader: new Ext.data.ArrayReader()
							}),
							displayField: 'displayField',
							valueField: 'valueField',
							editable: false,
							fieldLabel : '卡类型*',
							hiddenName: 'tblMchtNameUpd.id.cardTp',
//							id:'queryCardTpId',
							mode:'local',
							blankText: '卡类型不能为空',
							emptyText : '请选择卡类型',
							allowBlank : false,
							triggerAction: 'all',
							lazyRender: true,
							width: 250
						}]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
//							id: 'firstMchtCdId',
							fieldLabel : '交易类型*',
							baseParams: 'TXN_TYPES',
							hiddenName: 'tblMchtNameUpd.id.txnNum',
							mode:'local',
							blankText: '交易类型不能为空',
							emptyText : '请选择交易类型',
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
							fieldLabel : '商户别名',
							allowBlank : false,
							blankText: '商户别名不能为空',
							emptyText : '请输入商户别名',
							id: 'tblMchtNameUpd.mchtNm',
							name: 'tblMchtNameUpd.mchtNm',
							maxLength: 50,
							width: 250
						}]
				}
		]
	});
			
	var updWin = new Ext.Window({
		title : '商户别名修改',
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
						url : 'T20106Action.asp?method=update',
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
							accpId : grid.getSelectionModel()
												.getSelected().get('accpId').substring(0,15),
							cardTp  : grid.getSelectionModel()
									.getSelected().get('cardTp'),
							txnNum  : grid.getSelectionModel()
									.getSelected().get('txnNum').substring(0,4),
							txnId : '20106',
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
						
				updForm.getForm().findField('tblMchtNameUpd.id.accpId')
								.setValue(data.accpId);
				updForm.getForm().findField('tblMchtNameUpd.id.cardTp')
						.setValue(data.cardTp);
				updForm.getForm().findField('tblMchtNameUpd.id.txnNum')
						.setValue(data.txnNum);
				updForm.getForm().findField('tblMchtNameUpd.mchtNm')
						.setValue(data.mchtNm);
				
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