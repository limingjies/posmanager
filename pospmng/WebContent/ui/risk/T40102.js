Ext.onReady(function() {
	
	// 数据集
	var runRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=runRisk'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'riskType',mapping: 'riskType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'txnCount',mapping: 'txnCount'},
			{name: 'regTime',mapping: 'regTime'},
			{name: 'updTime',mapping: 'updTime'},
			{name: 'onFlag',mapping: 'onFlag'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'txnNo',mapping: 'txnNo'}
			
		]),
	autoLoad: true
	}); 
	
	var runRiskColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '风控序号',dataIndex: 'ruleId',align: 'center',width: 60},
		{header: '风控级别',dataIndex: 'riskLvl',align: 'center',width: 80,renderer:routeRule},
		{id:'cardAccpId',header: '商户编号',dataIndex: 'cardAccpId',width: 240 ,renderer:routeRule},
		{header: '风控类型',dataIndex: 'riskType',width:100,renderer:runRiskType},
		{header: '交易类型',dataIndex: 'txnNum',width: 100,align: 'left',renderer:routeTxnTp},
		{header: '卡类型',dataIndex: 'cardTp',width: 100,align: 'center',renderer:routeCardTp},
		{header: '交易金额',dataIndex: 'txnAmt',width: 100 ,align: 'right'},
		{header: '风控状态',dataIndex: 'onFlag',width: 100,align: 'center',renderer:routeStatus}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			{
					xtype: 'textfield',
					id: 'queryRuleId',
					name: 'queryRuleId',
					vtype:'isNumber',
					hidden:true,
					maxLength: 6,
					width: 140
				},'-',	'风控类型：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','单笔限额'],['1','日累计限额']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryRiskType',
					id:'queryRiskTypeId',
					editable: false,
					emptyText: '请选择',
					width: 140
				},	'-','风控状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryOnFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryOnFlagId',
					width: 140
				},'-','卡类型：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['*','无限制'],['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡'],['04','公务卡']],
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
					baseParams: 'routeTxnTp',
					hiddenName: 'queryTxnNum',
					width: 140
				}
	            ]  
         }) 
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','商户编号：',
					{
//					xtype: 'basecomboselect',
//					baseParams: 'routeMchtNo',
//					baseParams: 'MCHNT_NO',
					xtype: 'dynamicCombo',
					methodName: 'getMchntIdRoute',
					hiddenName: 'queryAccpId',
//					hidden:true,
					width: 300,
					id: 'queryAccpIdId',
					mode:'local',
					lazyRender: true
					/*listeners : {  
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
			            }  }*/
				},	'-','风控级别：',
					{
					xtype: 'basecomboselect',
					baseParams: 'RISK_LVL',
					hiddenName: 'queryRiskLvl',
//					hidden:true,
					width: 150,
					id: 'queryRiskLvlId',
					mode:'local',
					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
				}
	            ]  
         }) 
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '事中风控配置',
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'cardAccpId',
		store: runRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: runRiskColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风控信息列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				runRiskStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
				Ext.getCmp('queryRuleId').setValue('');
				Ext.getCmp('queryRiskTypeId').setValue('');
				Ext.getCmp('queryOnFlagId').setValue('');
				Ext.getCmp('queryCardTpId').setValue('');
				
				Ext.getCmp('queryAccpIdId').setValue('');
				Ext.getCmp('queryRiskLvlId').setValue('');
				Ext.getCmp('queryTxnNumId').setValue('');
				
				runRiskStore.load();
//				Ext.getCmp('queryAccpIdId').getStore().reload();
			}

		
		
		}, '-', {
					xtype : 'button',
					text : '风控信息新增',
					name : 'add',
					id : 'add',
					iconCls : 'add',
					width : 80,
					handler : function() {
						
						
						addWin.show();
						addWin.center();
//						Ext.getCmp('cardAccpIdAdd').getStore().reload();
						
						Ext.getCmp('txnNumAddId').show();
	                   	Ext.getCmp('cardTpAddId').show();

					}

				}, '-', {
					xtype : 'button',
					text : '风控信息修改',
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
//						alert(data.riskLvl);
						updForm.getForm().findField('riskLvlUpd').setValue(data.riskLvl);
						updForm.getForm().findField('cardAccpIdUpd').setValue(data.mchtNo);
//						updForm.getForm().findField('ruleIdUpd').setValue(data.ruleId);
//						updForm.getForm().findField('tblrunRiskUpd.id.cardAccpId').setValue(data.mchtNo);
						
						updForm.getForm().findField('riskTypeUpd').setValue(data.riskType);
						updForm.getForm().findField('txnNumUpd').setValue(data.txnNo);
						updForm.getForm().findField('cardTpUpd').setValue(data.cardTp);
						updForm.getForm().findField('tblRunRiskUpd.txnAmt').setValue(data.txnAmt.replaceAll(',',''));
							
						updWin.show();
						updWin.center();
					 	
					 	if(data.riskType=='0'){
	                   		Ext.getCmp('txnNumUpdId').show();
	                   		Ext.getCmp('cardTpUpdId').show();
	                   		
	                   }else if (data.riskType=='1'){
	                   		Ext.getCmp('txnNumUpdId').hide();
	                   		Ext.getCmp('cardTpUpdId').hide();
	                   		updForm.getForm().findField('txnNumUpd').setValue('*');
	                   		updForm.getForm().findField('cardTpUpd').setValue('*');
	                   		
	                   }


						
						

//						Ext.getCmp('cardAccpIdUpd').getStore().reload();
						
//						showCmpProcess(updForm, '正在加载风控信息，请稍后......');
//						hideCmpProcess(updForm, 1000);

					}

				}, '-', {
					xtype : 'button',
					text : '风控信息删除',
					name : 'delete',
					id : 'delete',
					disabled : true,
					iconCls : 'delete',
					width : 80,
					handler : function() {
						showConfirm('确定要删除该风控吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40102Action.asp?method=delete',
									params : {
										riskLvl: grid.getSelectionModel().getSelected().get('riskLvl').substring(0,1),
			 							cardAccpId: grid.getSelectionModel().getSelected().get('mchtNo'),
										ruleId: grid.getSelectionModel().getSelected().data.ruleId,
										txnId : '40102',
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
//										Ext.getCmp('detail').disable();
										Ext.getCmp('edit').disable();
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
					tbar2.render(this.tbar); 
                } ,
             'cellclick':selectableCell,
        }  ,
		bbar: new Ext.PagingToolbar({
			store: runRiskStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	// 添加
	var addForm = new Ext.form.FormPanel({
		frame : true,
//		 autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		 labelWidth: 100,
		// width: 900,
		height : 190,
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
									xtype : 'basecomboselect',
									baseParams : 'runRiskLvl',
									fieldLabel : '风控级别*',
									id:'riskLvlAddId',
									hiddenName : 'tblRunRiskAdd.id.riskLvl',
									allowBlank : false,
									blankText : '风控级别不能为空',
									emptyText : '请选择风控级别',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250,
									value : '*'
								}]
					}, {
						columnWidth : .99,
						layout : 'form',
						items : [{
									xtype: 'basecomboselect',
//									baseParams: 'MCHNT_NO',
									baseParams : 'routeMchtNo',
//									xtype : 'dynamicCombo',
//									methodName : 'getMchntIdRoute',
									fieldLabel : '商户编号*',
									id:'cardAccpIdAdd',
									hiddenName : 'tblRunRiskAdd.id.cardAccpId',
									allowBlank : false,
									blankText : '商户编号不能为空',
									emptyText : '请选择商户编号',
									lazyRender : true,
									width : 250,
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									value : '*',
									listeners : {
						            'select':function(){
						                   if(this.value == '*'){
						                   		Ext.getCmp('riskLvlAddId').enable();
						                   }else {
						                   		Ext.getCmp('riskLvlAddId').disable();
						                   		Ext.getCmp('riskLvlAddId').setValue('*');
						                   }
					                   },
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
						            }
								}]
					}, {
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data: [['0','单笔限额'],['1','日累计限额']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '风控类型*',
							hiddenName : 'tblRunRiskAdd.riskType',
							allowBlank : false,
							blankText : '风控类型不能为空',
							emptyText : '请选择风控类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250,
							listeners : {
						            'select':function(){
						                   if(this.value=='0'){
						                   		Ext.getCmp('txnNumAddId').show();
						                   		Ext.getCmp('cardTpAddId').show();
						                   		
						                   }else if (this.value=='1'){
						                   		Ext.getCmp('txnNumAddId').hide();
						                   		Ext.getCmp('cardTpAddId').hide();
						                   		addForm.getForm().findField('txnNumAdd').setValue('*');
						                   		addForm.getForm().findField('cardTpAdd').setValue('*');
						                   		
						                   }
						                
						            }  
						     }
								// value: '*'
						}]
					},{
						columnWidth : .99,
						layout : 'form',
						id:'txnNumAddId',
						items : [{
									xtype : 'basecomboselect',
									baseParams : 'routeTxnTp',
									fieldLabel : '交易类型*',
									hiddenName : 'tblRunRiskAdd.txnNum',
									id:'txnNumAdd',
									allowBlank : false,
									blankText : '交易类型不能为空',
									emptyText : '请选择交易类型',
									mode : 'local',
									triggerAction : 'all',
									editable : false,
									lazyRender : true,
									width : 250,
									value : '*'
								}]
					},  {
						columnWidth : .99,
						layout : 'form',
						id:'cardTpAddId',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['*', '*-无限制'], ['00', '借记卡'],
												['01', '贷记卡'], ['02', '准借记卡'],
												['03', '预付费卡'],['04','公务卡']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '卡类型*',
							id:'cardTpAdd',
							hiddenName : 'tblRunRiskAdd.cardTp',
							allowBlank : false,
							blankText : '卡类型不能为空',
							emptyText : '请选择卡类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250,
							value : '*'
						}]
					},{
							columnWidth : .99,
							layout : 'form',
//							id : 'amtBegId',
							items : [{
										xtype : 'numberfield',
										fieldLabel : '交易金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRunRiskAdd.txnAmt',
										name : 'tblRunRiskAdd.txnAmt',
										blankText : '交易金额不能为空',
										allowBlank : false,
										// emptyText: '请输入起始金额',
										width : 250
									}]
						}

			]
	});
	
	var addWin = new Ext.Window({
				title : '风控信息配置',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
//				width : 900,
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
										url : 'T40102Action.asp?method=add',
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
											txnId : '40102',
											subTxnId : '01'
										}
									});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						Ext.getCmp('riskLvlAddId').enable();
						addForm.getForm().reset();
					}
				}
				
				]
			});
			
			
			
			
	// 修改
	var updForm = new Ext.form.FormPanel({
		frame : true,
//		autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth: 100,
		// width: 900,
		height : 190,
		autoScroll : true,
		ayout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
						columnWidth : .99,
						layout : 'form',
						// width: 150,
						items : [{
									xtype : 'combofordispaly',
									baseParams : 'runRiskLvl',
									fieldLabel : '风控级别*',
									hiddenName : 'tblRunRiskUpd.id.riskLvl',
									id:'riskLvlUpd',
									allowBlank : false,
									blankText : '风控级别不能为空',
									emptyText : '请选择风控级别',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250
//									value : '*'
								}]
					}, {
						columnWidth : .99,
						layout : 'form',
						items : [{
									xtype: 'combofordispaly',
//									baseParams: 'MCHNT_NO',
									baseParams : 'routeMchtNo',
//									xtype : 'dynamicCombo',
//									methodName : 'getMchntIdRoute',
									fieldLabel : '商户编号*',
									id:'cardAccpIdUpd',
									hiddenName : 'tblRunRiskUpd.id.cardAccpId',
									allowBlank : false,
									blankText : '商户编号不能为空',
									emptyText : '请选择商户编号',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250,
//									value : '*',
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
						            }  }
								}]
					}, {
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data: [['0','单笔限额'],['1','日累计限额']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '风控类型*',
							id:'riskTypeUpd',
							hiddenName : 'tblRunRiskUpd.riskType',
							allowBlank : false,
							blankText : '风控类型不能为空',
							emptyText : '请选择风控类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250,
							listeners : {  
						            'select':function(){  
						                   if(this.value=='0'){
						                   		Ext.getCmp('txnNumUpdId').show();
						                   		Ext.getCmp('cardTpUpdId').show();
						                   		
						                   }else if (this.value=='1'){
						                   		Ext.getCmp('txnNumUpdId').hide();
						                   		Ext.getCmp('cardTpUpdId').hide();
						                   		updForm.getForm().findField('txnNumUpd').setValue('*');
						                   		updForm.getForm().findField('cardTpUpd').setValue('*');
						                   		
						                   }
						                
						            }  
						     }
								// value: '*'
						}]
					},{
						columnWidth : .99,
						layout : 'form',
						id:'txnNumUpdId',
						items : [{
									xtype : 'basecomboselect',
									baseParams : 'routeTxnTp',
									fieldLabel : '交易类型*',
									hiddenName : 'tblRunRiskUpd.txnNum',
									id:'txnNumUpd',
									allowBlank : false,
									blankText : '交易类型不能为空',
									emptyText : '请选择交易类型',
									mode : 'local',
									triggerAction : 'all',
									editable : false,
									lazyRender : true,
									width : 250
//									value : '*'
								}]
					},  {
						columnWidth : .99,
						layout : 'form',
						id:'cardTpUpdId',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['*', '*-无限制'], ['00', '借记卡'],
												['01', '贷记卡'], ['02', '准借记卡'],
												['03', '预付费卡'],['04','公务卡']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '卡类型*',
							hiddenName : 'tblRunRiskUpd.cardTp',
							id:'cardTpUpd',
							allowBlank : false,
							blankText : '卡类型不能为空',
							emptyText : '请选择卡类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250
//							value : '*'
						}]
					},{
							columnWidth : .99,
							layout : 'form',
//							id : 'amtBegId',
							items : [{
										xtype : 'numberfield',
										fieldLabel : '交易金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRunRiskUpd.txnAmt',
										name : 'tblRunRiskUpd.txnAmt',
										blankText : '交易金额不能为空',
										allowBlank : false,
										// emptyText: '请输入起始金额',
										width : 250
									}]
						}

			]
	});		
			
	var updWin = new Ext.Window({
		title : '风控信息修改',
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
						url : 'T40102Action.asp?method=update',
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
							riskLvl: grid.getSelectionModel().getSelected().get('riskLvl').substring(0,1),
 							cardAccpId: grid.getSelectionModel().getSelected().get('mchtNo'),
							ruleId: grid.getSelectionModel().getSelected().data.ruleId,
							txnId : '40102',
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
						
						
//						updForm.getForm().findField('tblrunRiskUpd.id.riskLvl').setValue(data.riskLvl.substring(0,1));
//						updForm.getForm().findField('tblrunRiskUpd.id.cardAccpId').setValue(data.mchtNo);
//						updForm.getForm().findField('tblrunRiskUpd.id.ruleId').setValue(data.ruleId);
//						
//						updForm.getForm().findField('tblrunRiskUpd.riskType').setValue(data.riskType);
//						updForm.getForm().findField('tblrunRiskUpd.txnNum').setValue(data.txnNo);
//						updForm.getForm().findField('tblrunRiskUpd.cardTp').setValue(data.cardTp);
//						updForm.getForm().findField('tblrunRiskUpd.txnAmt').setValue(data.txnAmt);
						
						
						updForm.getForm().findField('riskLvlUpd').setValue(data.riskLvl);
						updForm.getForm().findField('cardAccpIdUpd').setValue(data.mchtNo);
//						updForm.getForm().findField('ruleIdUpd').setValue(data.ruleId);
//						updForm.getForm().findField('tblrunRiskUpd.id.cardAccpId').setValue(data.mchtNo);
						
						updForm.getForm().findField('riskTypeUpd').setValue(data.riskType);
						updForm.getForm().findField('txnNumUpd').setValue(data.txnNo);
						updForm.getForm().findField('cardTpUpd').setValue(data.cardTp);
						updForm.getForm().findField('tblRunRiskUpd.txnAmt').setValue(data.txnAmt.replaceAll(',',''));
						
						
						 if(data.riskType=='0'){
	                   		Ext.getCmp('txnNumUpdId').show();
	                   		Ext.getCmp('cardTpUpdId').show();
	                   		
	                   }else if (data.riskType=='1'){
	                   		Ext.getCmp('txnNumUpdId').hide();
	                   		Ext.getCmp('cardTpUpdId').hide();
	                   		updForm.getForm().findField('txnNumUpd').setValue('*');
	                   		updForm.getForm().findField('cardTpUpd').setValue('*');
	                   		
	                   }
			}
		}
		
		]
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
//			mchntGrid.getTopToolbar().items.items[0].disable();
//			Ext.getCmp('detail').enable();
			if (grid.getSelectionModel().getSelected().get('onFlag') == '0') {
				Ext.getCmp('delete').enable();
				Ext.getCmp('edit').enable();
			} else {
				Ext.getCmp('delete').disable();
				Ext.getCmp('edit').disable();
			}
		}
	});
	
	
	runRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleId: Ext.getCmp('queryRuleId').getValue(),
			queryRiskLvl: Ext.get('queryRiskLvl').getValue(),
			queryOnFlag:Ext.get('queryOnFlag').getValue(),
			queryCardTp:Ext.get('queryCardTp').getValue(),
			
			queryAccpId:Ext.get('queryAccpId').getValue(),
			queryRiskType:Ext.get('queryRiskType').getValue(),
			queryTxnNum:Ext.get('queryTxnNum').getValue()
			
				
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});