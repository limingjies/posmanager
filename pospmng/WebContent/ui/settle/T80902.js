Ext.onReady(function() {
	
	// 开户行数据集
	var cnapsStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('CNAPS_INFO', function(ret) {
				cnapsStore.loadData(Ext.decode(ret));
			});
	
	// 开户行数据集(不包括已进行关联的开户行)
	var cnapsLStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
			
	// 数据集
	var channelCnapsStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=channelCnapsMap'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cnapsId',mapping: 'cnapsId'},
			{name: 'cnapsName',mapping: 'cnapsName'},
			{name: 'channelId',mapping: 'channelId'},
			{name: 'channelName',mapping: 'channelName'}
			
		]),
		autoLoad: true
	});
	
	var channelCnapsColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '开户行号',dataIndex: 'cnapsId',align: 'center',width: 150,renderer:cnapsIdName},
		{header: '开户行名称',dataIndex: 'cnapsName',width: 300,renderer:cnapsIdName},
		{header: '结算通道编号',dataIndex: 'channelId',align: 'center',width: 150},
		{header: '结算通道名称',dataIndex: 'channelName',width: 300 }
	]);
	
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,
                items:['开户行：',{
					xtype: 'combo',
					store : cnapsStore, 
					hiddenName: 'queryCnapsId',
					width: 250,
					id: 'idqueryCnapsId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true,
					editable:false,
					emptyText: '检索开户行'/*,
					listeners : {
	                    'select': function(){
							channelCnapsStore.load();
	                    }
			        }*/
		        },'-','结算通道：',{
					xtype: 'basecomboselect',
					baseParams: 'PAY_CHANNEL_INFO',
					id: 'idqueryChannelId',
					hiddenName: 'queryChannelId',
					width: 180,
					emptyText : '检索结算通道'/*,
					listeners : {
	                    'select': function(){
							channelCnapsStore.load();
	                    }
			        }*/
				}]
         })
         
	var grid = new Ext.grid.GridPanel({
		title: '开户行通道关联维护',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: channelCnapsStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: channelCnapsColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar:[{
				xtype: 'button',
				text: '查询',
				name: 'query',
				id: 'query',
				iconCls: 'query',
				width: 80,
				handler:function() {
					channelCnapsStore.load();
				}
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('idqueryCnapsId').setValue('');
					Ext.getCmp('idqueryChannelId').setValue('');
					channelCnapsStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '新增关联',
				name : 'add',
				id : 'add',
				iconCls : 'add',
				width : 80,
				handler : function() {
					addWin.show();
					addWin.center();
					SelectOptionsDWR.getComboDataWithParameter('CNAPS_INFO','L',function(ret){
				        cnapsLStore.loadData(Ext.decode(ret));
				    });
				}
			},'-',{
				xtype : 'button',
				text : '修改关联',
				name : 'edit',
				id : 'edit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					var rec = grid.getSelectionModel().getSelected().data;
					updForm.getForm().findField('tblChannelCnapsMapUpd.cnapsId').setValue(rec.cnapsId);
					updForm.getForm().findField('tblChannelCnapsMapUpd.channelId').setValue(rec.channelId);
					updWin.show();
					updWin.center();
				}
			},'-',{
				xtype : 'button',
				text : '解除关联',
				name : 'delete',
				id : 'delete',
				disabled : true,
				iconCls : 'delete',
				width : 80,
				handler : function() {
					Ext.Ajax.request({
						url : 'T80902Action.asp?method=delete',
						params : {
							cnapsId : grid.getSelectionModel().getSelected().get('cnapsId'),
							txnId : '80902',
							subTxnId : '02'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, grid);
							} else {
								showErrorMsg(rspObj.msg, grid);
							}
							channelCnapsStore.load();
						}
					});
				}
			},'-',{
				xtype : 'button',
				text : '解除通道关联',
				name : 'batchDel',
				id : 'batchDel',
				iconCls : 'del',
				hidden : true,
				width : 80,
				handler : function() {
					batchDelWin.show();
					batchDelWin.center();
				}
			}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: channelCnapsStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[6].enable();
			grid.getTopToolbar().items.items[8].enable();
		}
	});
	
	
	channelCnapsStore.on('beforeload', function(){
		if(passChannelId != 'null'){
			Ext.getCmp('idqueryChannelId').setValue(passChannelId);
			Ext.getCmp('idchannelId').setValue(passChannelId);
			batchDelWin.show();
			batchDelWin.center();
		}
		
		Ext.apply(this.baseParams, {
			start: 0,
			queryCnapsId:Ext.getCmp('idqueryCnapsId').getValue(),
			queryChannelId: Ext.getCmp('idqueryChannelId').getValue()
		});
		// 禁用编辑按钮
		grid.getTopToolbar().items.items[6].disable();
		grid.getTopToolbar().items.items[8].disable();
	});
	
	
	// 添加
	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 100,
		height : 430,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [
			{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'combo',
					store : cnapsLStore, 
					fieldLabel : '开户行*',
					allowBlank : false,
					blankText: '开户行不能为空',
					emptyText : '请选择开户行',
					hiddenName: 'tblChannelCnapsMapAdd.cnapsId',
					id: 'cnapsIdAdd',
					width: 250,
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true,
					editable:false
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'basecomboselect',
					fieldLabel : '结算通道*',
					baseParams: 'PAY_CHANNEL_INFO',
					allowBlank : false,
					blankText: '结算通道不能为空',
					emptyText : '请选择结算通道',
					id: 'channelIdAdd',
					hiddenName: 'tblChannelCnapsMapAdd.channelId',
					width: 250
				}]
			}
		]
	})
	
	
	var addWin = new Ext.Window({
		title : '新增关联',
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
						url : 'T80902Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							channelCnapsStore.load();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
							txnId : '80901',
							subTxnId : '01'
						}
					});
				}
			}
		},{
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
			}
		}]
	});
			
	var updForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		labelWidth : 100,
		layout : 'column',
		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [
			{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel : '开户行*',
					allowBlank : false,
					blankText: '开户行不能为空',
					emptyText : '请选择开户行',
					id: 'cnapsIdUpd',
					name: 'tblChannelCnapsMapUpd.cnapsId',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'basecomboselect',
					fieldLabel : '结算通道*',
					baseParams: 'PAY_CHANNEL_INFO',
					allowBlank : false,
					blankText: '结算通道不能为空',
					emptyText : '请选择结算通道',
					id: 'channelIdUpd',
					hiddenName: 'tblChannelCnapsMapUpd.channelId',
					width: 250
				}]
			}
		]
	});
			
	var updWin = new Ext.Window({
		title : '修改结算通道',
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
						url : 'T80902Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							channelCnapsStore.load();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
							cnapsId : grid.getSelectionModel().getSelected().get('cnapsId'),
							txnId : '80902',
							subTxnId : '04'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
			}
		}]
	});
	
	var batchDelWin = new Ext.Window({
		title : '解除通道关联',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [new Ext.form.FormPanel({
					frame : true,
					autoHeight: true,
					labelWidth: 100,
					height : 430,
					id : 'batchDelForm',
					autoScroll : true,
					layout: 'column',
					waitMsgTarget : true,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [
						{
							columnWidth : .99,
							layout : 'form',
							items : [{
								xtype: 'basecomboselect',
								fieldLabel : '结算通道*',
								baseParams: 'PAY_CHANNEL_INFO',
								allowBlank : false,
								blankText: '结算通道不能为空',
								emptyText : '请选择结算通道',
								id: 'idchannelId',
								hiddenName: 'channelId',
								width: 250
							}]
						}
					]
				})
		],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (Ext.getCmp('batchDelForm').getForm().isValid()) {
					Ext.Ajax.request({
						url : 'T80902Action.asp?method=batchDel',
						params : {
							channelId : Ext.getCmp('idchannelId').getValue(),
							txnId : '80902',
							subTxnId : '03'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, grid);
							} else {
								showErrorMsg(rspObj.msg, grid);
							}
							// 重新加载参数列表
							channelCnapsStore.load();
							batchDelWin.hide();
							if(passChannelId != 'null'){
								window.location.href = Ext.contextPath + '/page/settle/T80901.jsp';
							}
						}
					});
				}
			}
		}]
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});