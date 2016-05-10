Ext.onReady(function() {
	
	// 数据集
	var riskWhiteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskBlackMcht'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'crtDateTime',mapping: 'crtDateTime'},
			{name: 'resved',mapping: 'resved'}
			
			
		]),
	autoLoad: true
	}); 
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=700>',
		
		'<tr><td><font color=gray>法人代表：</font>{manager}</td>',
		'<td><font color=gray>法人证件号：</font>{identityNo}</td></tr>',

		'<tr><td><font color=gray>录入日期： </font>{crtDateTime:this.formatDate}</td>',
		'<td><font color=gray>录入操作员：</font>{crtOprId}</td></tr>'
//		'<tr><td colspan=2><font color=gray>差错处理备注：</font>{misc1}</td></tr>' 

			,{
			formatDate : function(val){
				 if(val!=null &&val.length == 8){
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
				+ val.substring(6, 8);
				}if(val!=null &&val.length == 6){
					return val.substring(0, 2) + ':' + val.substring(2, 4) + ':'
				+ val.substring(4);
				}else if (val.length == 14) {
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
							+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
							+ val.substring(10, 12) + ':' + val.substring(12, 14);
				} else{
					return val;
				}
			 }
			}
		)
	});
	var riskWhiteColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
//		{header: '商户号',dataIndex: 'cardAccpId',align: 'center',width: 150},
		{header: '商户名',dataIndex: 'mchtName',id:'mchtName',width: 200,align: 'left' },
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 140 ,align: 'center'},
		{header: '税务登记证号',dataIndex: 'faxNo',width: 140,align: 'center' },
		{header: '结算账号',dataIndex: 'settleAcct',width: 170,align: 'left' },
		{header: '备注信息',dataIndex: 'resved',width: 250 ,align: 'left'}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'商户编号：',{
//					xtype: 'basecomboselect',
//					baseParams: 'MCHNT_NO',
					xtype : 'dynamicCombo',
					methodName : 'getBlackMchtId',
					hiddenName: 'queryCardAccpId',
					width: 300,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
//					typeAhead: true,
					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
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
			            }  
			        } */
			        }
	            ]  
         }) 
	
	
	
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '商户黑名单管理',
		iconCls: 'logo',
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
		plugins: rowExpander,
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
//				Ext.getCmp('idqueryRiskId').setValue('');
				Ext.getCmp('idqueryCardAccpId').setValue('');
				riskWhiteStore.load();
				
//				Ext.getCmp('idqueryCardAccpId').getStore().reload();
			
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
					text : '删除',
					name : 'delete',
					id : 'delete',
					disabled : true,
					iconCls : 'delete',
					width : 80,
					handler : function() {
						showConfirm('确定要删除该该商户黑名单吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40208Action.asp?method=delete',
									params : {
										
										mchtNo  : grid.getSelectionModel()
												.getSelected().get('mchtNo'),
										txnId : '40208',
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
                } ,
            'cellclick':selectableCell
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
			Ext.getCmp('delete').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			Ext.getCmp('detail').enable();
//			if (grid.getSelectionModel().getSelected().get('msc2') == '0') {
				Ext.getCmp('delete').enable();
//			} else {
//				Ext.getCmp('delete').disable();
//				Ext.getCmp('edit').disable();
//			}
		}
	});
	
	
	riskWhiteStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: Ext.getCmp('idqueryCardAccpId').getValue()
		
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
//							xtype: 'basecomboselect',
//							baseParams: 'MCHNT_NO',
							xtype : 'dynamicCombo',
							methodName : 'getMchntId',
							fieldLabel : '商户编号*',
							blankText: '商户编号不能为空',
							emptyText : '请选择商户编号',
							hiddenName: 'tblRiskBlackMcht.mchtNo',
							width: 250,
//							id: 'idqueryCardAccpId',
							mode:'local',
							triggerAction: 'all',
							forceSelection: true,
		//					typeAhead: true,
							selectOnFocus: true,
							editable: true,
							allowBlank: false,
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
					            }  
					        } */
					        }]
				},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'textarea',
							fieldLabel : '备注信息',
							allowBlank : true,
//							blankText: '级别名称不能为空',
							emptyText : '请输入50个汉字或100个字符',
							id: 'tblRiskBlackMcht.resved',
							name: 'tblRiskBlackMcht.resved',
							vtype: 'length100B',
							value : '',
							//maxLength: 50,
							width: 250,
							listeners : {
								
							}
						}]
				}
			
		]
	})
	
	
	var addWin = new Ext.Window({
				title : '新增商户黑名单',
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
		                    if(Ext.getCmp('tblRiskBlackMcht.resved').getValue() == ''){
		                    	Ext.getCmp('tblRiskBlackMcht.resved').setValue(' ');
		                    }
							addForm.getForm().submit({
										url : 'T40208Action.asp?method=add',
										waitMsg : '正在提交，请稍后......',
										success : function(form, action) {
											showSuccessMsg(action.result.msg,
													addForm);
											// 重置表单
											addForm.getForm().reset();
											// 重新加载参数列表
											grid.getStore().reload();
											addForm.getForm().reset();
											addWin.hide();
										},
										failure : function(form, action) {
											showErrorMsg(action.result.msg,
													addForm);
										},
										params : {
											txnId : '40208',
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
			
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});