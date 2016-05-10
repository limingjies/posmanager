Ext.onReady(function() {

	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	
	//取档位名称下拉列表
	var discNmStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('DISC_NM','',function(ret){
		discNmStore.loadData(Ext.decode(ret));
	});
	

	// 成本扣率配置窗口内容
	var addOprForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
					xtype: 'hidden',
					id:'brhId_config',
					name: 'brhId',
					fieldLabel : '性质ID',
					width: 180
				},{
					xtype: 'displayfield',
					id:'channelNm_config',
					name: 'channelNm',
					fieldLabel : '支付渠道',
					width: 180
				},{
					xtype: 'displayfield',
					id: 'businessNm_config',
					name: 'businessNm',
					fieldLabel : '业务',
					width: 180
				},{
					xtype: 'basecomboselect',
					id: 'discNm_config',
					name: 'discNm',
            		allowBlank: false,
					hiddenName: 'discNm_hidden',
					store: discNmStore,
					fieldLabel : '档位名称',
					emptyText:'',
					displayField: 'displayField',
					valueField: 'valueField',
					width: 180
				}]
	});

//	==================================主规则====================================
	// 左边页面数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=costDiscount'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'channelNm',mapping: 'channelNm'},
			{name: 'businessNm',mapping: 'businessNm'},
			{name: 'isfee',mapping: 'isfee'}
		]),
		autoLoad: true
	}); 
	
	//左边页面列表
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '渠道ID',dataIndex: 'brhId',align: 'center',hidden:true},
		{header: '支付渠道',dataIndex: 'channelNm',align: 'center',width: 100},
		{header: '业务',dataIndex: 'businessNm',width: 200,id:'businessNm' },
		{header: '是否已配置',dataIndex: 'isfee',id:'isfee',width: 190,renderer:isFeeStatus}
	]);
	
	//工具栏查询条件的输入框
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','支付渠道：',{
        					xtype: 'basecomboselect',
        					baseParams: 'CHANNEL_ALL',
        					fieldLabel: '支付渠道',
        					hiddenName: 'channelNm',
        					id:'queryChannelNm',
        					width: 100,
        					listeners: {
        						'select': function() {
        							busiStore.removeAll();
        							var chlId = Ext.getCmp('queryChannelNm').getValue();
        							Ext.getCmp('queryBusinessNm').setValue('');
        							Ext.getDom(Ext.getDoc()).getElementById('businessNm').value = '';
        							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
        							busiStore.loadData(Ext.decode(ret));
        							});
        						},
        						'change': function() {
        							busiStore.removeAll();
        							var chlId = Ext.getCmp('queryChannelNm').getValue();
        							Ext.getCmp('queryBusinessNm').setValue('');
        							Ext.getDom(Ext.getDoc()).getElementById('businessNm').value = '';
        							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
        							busiStore.loadData(Ext.decode(ret));
        							});
        						}
        					}
				},'-',	'业务：',{
					xtype: 'basecomboselect',
					store: busiStore,
					displayField: 'displayField',
					valueField: 'valueField',
					id: 'queryBusinessNm',
					hiddenName: 'businessNm',
					value:'',
					width: 140
				},'-',	'是否已配置：',{
					xtype : 'combo',
					id : 'queryIsfee',
					name: 'isfee',
					width: 60,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '0', '未配置' ], [ '1', '已配置' ] ]
					})
				} ]  
         }) 
         
    //左边页面的查询和重置
    var routeRuleGrid = new Ext.grid.GridPanel({
		width: 520,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'isfee',
		store: routeRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载成本扣率配置列表......'
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
				Ext.getCmp('queryChannelNm').setValue('');
				Ext.getCmp('queryBusinessNm').setValue('');
				Ext.getCmp('queryIsfee').setValue('');
				routeRuleStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
                } ,
             'cellclick':selectableCell
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
	// 成本扣率配置右边页面数据源
	var routeRuleDtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=costDiscountDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discId',mapping: 'discId'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'status',mapping: 'status'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'}
		])
	}); 
	
	//右边页面列表
	var routeRuleDtlColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '档位编号',dataIndex: 'discId',width:80},
		{header: '档位名称',dataIndex: 'discNm',id:'discNm'},
		{header: '状态',dataIndex: 'status',align: 'center',renderer:feeStatus},
		{header: '最后修改时间',dataIndex: 'uptTime',renderer:formatDt,width:140},
		{header: '修改人',dataIndex: 'uptOpr',align: 'center'}
	]);

	//右边页面操作栏
	var grid = new Ext.grid.GridPanel({
		region: 'east',
		width: 620,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		autoExpandColumn: 'discNm',
		store: routeRuleDtlStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleDtlColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载成本扣率配置列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '启用',
			name: 'startBut',
			id: 'startBut',
			iconCls: 'accept',
			width: 80,
			handler : function(bt) {
				showConfirm('确认启用吗？', grid, function(bt) {
					if (bt == 'yes') {
						var selectedRecord = grid.getSelectionModel().getSelected();
						var dId = selectedRecord.get('discId');
						Ext.Ajax.request({
							url : 'T110121Action_update.asp',
							params : {
								//flag : "publish",
								discId : dId
							},
							success : function(response) {
								Ext.Msg.alert('提示', '启用成功！');
								routeRuleDtlStore.reload();
							}
						});
					}
				});
			}
		},'-',{
			xtype: 'button',
			text: '停用',
			name: 'endBut',
			id: 'endBut',
			iconCls: 'stop',
			width: 80,
			handler : function(bt) {
				showConfirm('确认停用吗？', grid, function(bt) {
					if (bt == 'yes') {
						var selectedRecord = grid.getSelectionModel().getSelected();
						var dId = selectedRecord.get('discId');
						Ext.Ajax.request({
							url : 'T110121Action_update.asp',
							params : {
								//flag : "publish",
								discId : dId
							},
		                    //成功时回调
		                    success: function(response, options) {
		                       //获取响应的json字符串
		                      var responseArray = Ext.util.JSON.decode(response.responseText); 
		                         if(responseArray.msg=="failure"){
										showErrorMsg("有渠道商户对应该档位，不可停用！", grid);
										routeRuleDtlStore.reload();
		                         }else if(responseArray.msg=="00"){
										showSuccessMsg('停用成功', grid);
										routeRuleDtlStore.reload();
		                         }
		                     }
						});
					}
				});
			}
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'deleteDtl',
			id : 'deleteDtl',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该条记录吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						var selectedRecord = grid.getSelectionModel().getSelected();
						var dId = selectedRecord.get('discId');
						Ext.Ajax.request({
							url : 'T110121Action_delete.asp',
							params : {
								discId : dId
							},
		                    //成功时回调
		                    success: function(response, options) {
		                       //获取响应的json字符串
		                      var responseArray = Ext.util.JSON.decode(response.responseText); 
		                         if(responseArray.msg=="failure"){
										showErrorMsg("有渠道商户对应该档位，不可删除！", grid);
										routeRuleDtlStore.reload();
		                         }else if(responseArray.msg=="00"){
										showSuccessMsg('删除成功', grid);
										routeRuleDtlStore.reload();
										routeRuleStore.reload();
		                         }
		                     }
							
						});
					}
				})
			}
		},'-',{
			xtype: 'button',
			text: '配置',
			name: 'addBut',
			id: 'addBut',
			iconCls: 'add',
			width: 80,
			handler:function() {
				Ext.getCmp("brhId_config").setValue(routeRuleGrid.getSelectionModel().getSelected().get("brhId"));
				Ext.getCmp("channelNm_config").setValue(routeRuleGrid.getSelectionModel().getSelected().get("channelNm"));
				Ext.getCmp("businessNm_config").setValue(routeRuleGrid.getSelectionModel().getSelected().get("businessNm"));
				addOprWin.show();
			}
		}
		],
		bbar: new Ext.PagingToolbar({
			store: routeRuleDtlStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
	

	// 成本扣率信息配置窗口
	var addOprWin = new Ext.Window({
		title : '成本扣率信息配置',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		width : 400,
		autoHeight : true,
		layout : 'fit',
		items : [ addOprForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'add',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				if (addOprForm.getForm().isValid()) {
					addOprForm.getForm().submit({
						url : 'T110121Action_config.asp',
						waitMsg : '正在配置信息，请稍后......',
						params : {
							txnId : 'T110121',
							subTxnId : '01'
						},
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addOprForm);
							// 重置表单
							addOprForm.getForm().reset();
							// 重新加载参数列表
							routeRuleDtlStore.reload();
							routeRuleStore.reload();
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM','',function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});
							addOprWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addOprForm);
							// 重置表单
							addOprForm.getForm().reset();
							// 重新加载参数列表
							routeRuleDtlStore.reload();
							routeRuleStore.reload();
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM','',function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});
							addOprWin.hide();
						}
					});
				}
			}
		},{
			text : '重置',
			handler : function() {
				//Ext.getCmp('discNm_config').setValue('');
				addOprForm.getForm().findField("discNm_config").reset();
				//addOprForm.getForm().reset();
			}
		} ]
	});

//	====================================详细规则商户结束=================================================
	
	
	
//	====================================加载=================================================
	// 左边数据源加载时的处理--禁用右边的编辑按钮
	routeRuleGrid.getStore().on('beforeload',function() {
		Ext.getCmp('startBut').disable();
		Ext.getCmp('endBut').disable();
		Ext.getCmp('deleteDtl').disable();
		Ext.getCmp('addBut').disable();
		grid.getStore().removeAll();
	});
	
	routeRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			Ext.getCmp('addBut').enable();
			routeRuleDtlStore.load({
				params: {
					start: 0,
					brhId: routeRuleGrid.getSelectionModel().getSelected().get('brhId')
				}
			})
		}
	});
	
	
	routeRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			channelNm: Ext.getCmp('queryChannelNm').getValue(),
			businessNm:Ext.getCmp('queryBusinessNm').getValue(),
			isfee:Ext.getCmp('queryIsfee').getValue()
			//isfee:Ext.get('queryIsfee').getValue()
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
			var onFlag=grid.getSelectionModel().getSelected().get('status');
			if(onFlag=='1'){
				Ext.getCmp('startBut').enable();
				Ext.getCmp('endBut').disable();
				Ext.getCmp('deleteDtl').enable();
			}else{
				Ext.getCmp('startBut').disable();
				Ext.getCmp('endBut').enable();
				Ext.getCmp('deleteDtl').enable();
			}
		}
	});


//	=======================================华丽的分割线=======================================================

	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [routeRuleGrid,grid],
		renderTo: Ext.getBody()
	});
});