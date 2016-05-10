Ext.onReady(function() {
	
	// 数据集
	var payChannelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=payChannelInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'channelId',mapping: 'channelId'},
			{name: 'channelFlag',mapping: 'channelFlag'},
			{name: 'channelName',mapping: 'channelName'},
			{name: 'channelSta',mapping: 'channelSta'},
			{name: 'belowCnapsNum',mapping: 'belowCnapsNum'},
			{name: 'acctNo',mapping: 'acctNo'},
			{name: 'acctNm',mapping: 'acctNm'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'}
			
		]),
		autoLoad: true
	}); 
	
	var payChannelColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '通道编号',dataIndex: 'channelId',align: 'center',width: 100},
		{header: '通道类型',dataIndex: 'channelFlag',align: 'center',width: 100,renderer:function(val){
			if(val == '0') {
				return '<font color="green">交易通道</font>';
			} else if(val == '1') {
				return '<font color="blue">结算通道</font>';
			}
			return '状态未知';} 
		},
		{header: '通道名称',dataIndex: 'channelName',id:'channelName',width: 150 },
		{header: '通道账号',dataIndex: 'acctNo',width: 150},
		{header: '通道账户名称',dataIndex: 'acctNm',width: 250 },
		{header: '通道商户号',dataIndex: 'mchtNo',width: 150},
		{header: '通道商户名称',dataIndex: 'mchtNm',width: 250 },
//		{header: '关联开户行数',dataIndex: 'belowCnapsNum',align: 'center',width: 150},
		{header: '通道状态',dataIndex: 'channelSta',width: 120 ,align: 'center',renderer:payChanlStatus}
	]);
	
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['结算通道：',{
					/*xtype: 'basecomboselect',
					baseParams: 'PAY_CHANNEL_INFO',
					id: 'idqueryChannelId',
					hiddenName: 'queryChannelId',
					width: 180,
					listeners : {
	                    'select': function(){
							payChannelStore.load();
	                    }
			        }*/
					xtype: 'textfield',
					emptyText : '检索通道编号',
					id: 'idqueryChannelId',
					name: 'queryChannelId',
					width: 180/*,
					enableKeyEvents:true,
					listeners:{
						'keyup':function(){
							payChannelStore.load();
						}
					}*/
				},'-','通道类型：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','交易通道'],['1','结算通道']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryChannelFlag',
					editable: false,
					emptyText: '请选择通道类型',
					id:'idqueryChannelFlag',
					width: 140
				},'-','通道状态：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryChannelSta',
					editable: false,
					emptyText: '检索通道状态',
					id:'idqueryChannelSta',
					width: 140/*,
					listeners : {
	                    'select': function(){
							payChannelStore.load();
	                    }
			        }*/
				}]
         })
         
	var grid = new Ext.grid.GridPanel({
		title: '结算通道维护',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'channelName',
		store: payChannelStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: payChannelColModel,
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
					payChannelStore.load();
				}
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('idqueryChannelId').setValue('');
					Ext.getCmp('idqueryChannelFlag').setValue('');
					Ext.getCmp('idqueryChannelSta').setValue('');
					payChannelStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '新增通道',
				name : 'add',
				id : 'add',
				iconCls : 'add',
				width : 80,
				handler : function() {
					addWin.show();
					addWin.center();
				}
			},'-',{
				xtype : 'button',
				text : '修改通道',
				name : 'edit',
				id : 'edit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					var rec = grid.getSelectionModel().getSelected().data;
					updForm.getForm().findField('tblPayChannelInfoUpd.channelFlag').setValue(rec.channelFlag);
					updForm.getForm().findField('tblPayChannelInfoUpd.channelId').setValue(rec.channelId);
					updForm.getForm().findField('tblPayChannelInfoUpd.channelName').setValue(rec.channelName);
					updForm.getForm().findField('tblPayChannelInfoUpd.acctNo').setValue(rec.acctNo);
					updForm.getForm().findField('tblPayChannelInfoUpd.acctNm').setValue(rec.acctNm);
					updForm.getForm().findField('tblPayChannelInfoUpd.mchtNo').setValue(rec.mchtNo);
					updForm.getForm().findField('tblPayChannelInfoUpd.mchtNm').setValue(rec.mchtNm);
					updWin.show();
					updWin.center();
				}
			},'-',{
				xtype : 'button',
				text : '删除通道',
				name : 'delete',
				id : 'delete',
				disabled : true,
				iconCls : 'delete',
				width : 80,
				handler : function() {
					showConfirm('确定要删除该通道吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T80901Action.asp?method=delete',
								params : {
									channelId : grid.getSelectionModel().getSelected().get('channelId'),
									txnId : '80901',
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
									payChannelStore.load();
								}
							});
						}
					})
				}
			},'-',{
				xtype : 'button',
				text : '启用通道',
				name: 'start',
				id: 'start',
				iconCls: 'accept',
//					disabled : true,
				width : 80,
				handler : function() {
					Ext.Ajax.request({
						url : 'T80901Action.asp?method=switch',
						params : {
							channelId : grid.getSelectionModel().getSelected().get('channelId'),
							txnId : '80901',
							subTxnId : '04'
						},
						success : function(rsp, opt) {
							var rspObj = Ext
									.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, grid);

							} else {
								showErrorMsg(rspObj.msg, grid);
							}
							payChannelStore.load();
						}
					});
				}
			},'-',{
				xtype : 'button',
				text : '停用通道',
				/*name : 'switch',
				id : 'switch',
				iconCls : 'switch',*/
				name: 'stop',
				id: 'stop',
				iconCls: 'stop',
//					disabled : true,
				width : 80,
				handler : function() {
					var rec = grid.getSelectionModel().getSelected().data;
					if (rec.belowCnapsNum != '0') {
						/*showConfirm('此通道关联了'+rec.belowCnapsNum+'家开户行，需要去解除关联吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								window.location.href = Ext.contextPath + '/page/settle/T80902.jsp?passChannelId='+rec.channelId;
							}
						})*/
						showMsg("此通道关联了"+rec.belowCnapsNum+"家开户行，请先解除关联", grid);
						return;
					}
					Ext.Ajax.request({
						url : 'T80901Action.asp?method=switch',
						params : {
							channelId : grid.getSelectionModel().getSelected().get('channelId'),
							txnId : '80901',
							subTxnId : '05'
						},
						success : function(rsp, opt) {
							var rspObj = Ext
									.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, grid);

							} else {
								showErrorMsg(rspObj.msg, grid);
							}
							payChannelStore.load();
						}
					});
				}
			}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: payChannelStore,
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
			if (grid.getSelectionModel().getSelected().get('channelSta') == '0') {
				grid.getTopToolbar().items.items[8].enable();
				grid.getTopToolbar().items.items[10].enable();
				grid.getTopToolbar().items.items[12].disable();
			} else {
				grid.getTopToolbar().items.items[8].disable();
				grid.getTopToolbar().items.items[10].disable();
				grid.getTopToolbar().items.items[12].enable();
			}
		}
	});
	
	
	payChannelStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryChannelId: Ext.getCmp('idqueryChannelId').getValue(),
			queryChannelFlag: Ext.getCmp('idqueryChannelFlag').getValue(),
			queryChannelSta:Ext.getCmp('idqueryChannelSta').getValue()
		});
		// 禁用编辑按钮
		grid.getTopToolbar().items.items[6].disable();
		grid.getTopToolbar().items.items[8].disable();
		grid.getTopToolbar().items.items[10].disable();
		grid.getTopToolbar().items.items[12].disable();
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
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'combo',
					fieldLabel : '通道类型*',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','交易通道'],['1','结算通道']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'tblPayChannelInfoAdd.channelFlag',
					allowBlank : false,
					editable: false,
					emptyText: '请选择通道类型',
					id:'channelFlagAdd',
					width: 250,
					listeners : {
	                    'select': function(){
	                    	if(this.getValue()=='0'){
	                    		Ext.getCmp('channelIdAdd').minLength = '2';
	                    		Ext.getCmp('channelIdAdd').maxLength = '2';
	                    	}else{
	                    		Ext.getCmp('channelIdAdd').minLength = '4';
	                    		Ext.getCmp('channelIdAdd').maxLength = '4';
	                    	}
	                    }
			        }
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道编号*',
					allowBlank : false,
					blankText: '通道编号不能为空',
					emptyText : '请输入通道编号',
					id: 'channelIdAdd',
					name: 'tblPayChannelInfoAdd.channelId',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道名称*',
					allowBlank : false,
					blankText: '通道名称不能为空',
					emptyText : '请输入通道名称',
					id: 'channelNameAdd',
					name: 'tblPayChannelInfoAdd.channelName',
					maxLength: 100,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道帐号',
                    maxLength: 20,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					id: 'acctNoAdd',
					name: 'tblPayChannelInfoAdd.acctNo',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道账户名称',
                    maxLength: 40,
					vtype: 'isOverMax',
					id: 'acctNmAdd',
					name: 'tblPayChannelInfoAdd.acctNm',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道商户号',
                    minLength: 15,
                    maxLength: 15,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					id: 'mchtNoAdd',
					name: 'tblPayChannelInfoAdd.mchtNo',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道商户名称',
                    maxLength: 40,
					vtype: 'isOverMax',
					id: 'mchtNmAdd',
					name: 'tblPayChannelInfoAdd.mchtNm',
					width: 250
				}]
			}
		]
	})
	
	
	var addWin = new Ext.Window({
		title : '新增结算通道',
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
						url : 'T80901Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							payChannelStore.load();
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
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
                    xtype: 'combofordispaly',
                    fieldLabel: '通道类型*',
					allowBlank : false,
					displayField: 'displayField',
					valueField: 'valueField',
                    id: 'channelFlagUpd',
                    hiddenName: 'tblPayChannelInfoUpd.channelFlag',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','交易通道'],['1','结算通道']]
                    }),
					width: 250
                }]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel : '通道编号*',
					allowBlank : false,
					blankText: '通道编号不能为空',
					emptyText : '请输入通道编号',
					id: 'channelIdUpd',
					name: 'tblPayChannelInfoUpd.channelId',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道名称*',
					allowBlank : false,
					blankText: '通道名称不能为空',
					emptyText : '请输入通道名称',
					id: 'channelNameUpd',
					name: 'tblPayChannelInfoUpd.channelName',
					maxLength: 100,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道帐号',
                    maxLength: 20,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					id: 'acctNoUpd',
					name: 'tblPayChannelInfoUpd.acctNo',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道账户名称',
                    maxLength: 40,
					vtype: 'isOverMax',
					id: 'acctNmUpd',
					name: 'tblPayChannelInfoUpd.acctNm',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道商户号',
                    minLength: 15,
                    maxLength: 15,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					id: 'mchtNoUpd',
					name: 'tblPayChannelInfoUpd.mchtNo',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道商户名称',
                    maxLength: 40,
					vtype: 'isOverMax',
					id: 'mchtNmUpd',
					name: 'tblPayChannelInfoUpd.mchtNm',
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
						url : 'T80901Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							payChannelStore.load();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
							channelId : grid.getSelectionModel().getSelected().get('channelId'),
							txnId : '80901',
							subTxnId : '02'
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
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});