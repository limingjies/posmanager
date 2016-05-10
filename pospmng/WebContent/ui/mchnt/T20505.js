Ext.onReady(function() {
	
	// 数据集
	var firstMchtTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=firstMchtTxn'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'firstMchtCd',mapping: 'firstMchtCd'},
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'txnDate',mapping: 'txnDate'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'amtLimit',mapping: 'amtLimit'}
		]),
	autoLoad: true
	}); 
	
	var firstMchtTxnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '一级商户号',dataIndex: 'firstMchtCd',width: 180,align: 'center'},
		{header: '一级商户名称',dataIndex: 'firstMchtNm',id:'firstMchtNm',width: 250 },
		{header: '交易日期',dataIndex: 'txnDate',width: 120 ,align: 'center',renderer:formatDt},
		{header: '交易笔数',dataIndex: 'txnNum',width: 120 ,align: 'center'},
		{header: '交易金额',dataIndex: 'txnAmt',width: 120 ,align: 'right'},
		{header: '交易限额',dataIndex: 'amtLimit',width: 120 ,align: 'right'}
	]);
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
       			'-','一级商户号：',{
			xtype: 'basecomboselect',
			id: 'queryFirstMchtCdId',
			baseParams: 'routeFirstMchtCd',
			hiddenName: 'queryFirstMchtCd',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 250
		}]  
     })
	
	var grid = new Ext.grid.GridPanel({
//		title: '一级商户限额维护',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'firstMchtNm',
		store: firstMchtTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: firstMchtTxnColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载一级商户限额信息列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				firstMchtTxnStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryFirstMchtCdId').setValue('');
			}
		},'-',{
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
		},'-',{
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
				Ext.getCmp('firstMchtCdId').setValue(select.get('firstMchtCd')+' - '+select.get('firstMchtNm'));
				Ext.getCmp('amtLimitUpd').setValue(select.get('amtLimit').replaceAll(',',''));
				updWin.show();
				updWin.center();
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
				showConfirm('确定要删除该一级商户限额信息吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T20505Action.asp?method=delete',
							params : {
								firstMchtCd : grid.getSelectionModel().getSelected().get('firstMchtCd'),
								txnId : '20505',
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
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
				tbar1.render(this.tbar); 
            }
        },
		bbar: new Ext.PagingToolbar({
			store: firstMchtTxnStore,
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
			Ext.getCmp('delete').enable();
			Ext.getCmp('edit').enable();
		}
	});
	
	
	firstMchtTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryFirstMchtCd: Ext.get('queryFirstMchtCd').getValue()
		});	
		// 禁用编辑按钮
		Ext.getCmp('edit').disable();
		Ext.getCmp('delete').disable();
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
				xtype: 'basecomboselect',
				fieldLabel : '一级商户号*',
				baseParams: 'routeFirstMchtCd',
				hiddenName: 'firstMchtCd',
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
				xtype : 'numberfield',
				fieldLabel : '交易限额*',
				maxLength : 13,
				vtype : 'isMoney12',
				id : 'amtLimitAdd',
				name : 'amtLimit',
				blankText : '交易金额不能为空',
				allowBlank : false,
				width : 250
			}]
		}]
	})
	
	var addWin = new Ext.Window({
				title : '新增一级商户限额信息',
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
										url : 'T20505Action.asp?method=add',
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
											txnId : '20505',
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
				xtype: 'displayfield',
				id: 'firstMchtCdId',
				fieldLabel : '一级商户号*',
				allowBlank : false,
				width: 250
			}]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype : 'numberfield',
				fieldLabel : '交易限额*',
				maxLength : 13,
				vtype : 'isMoney12',
				id : 'amtLimitUpd',
				name : 'amtLimit',
				blankText : '交易金额不能为空',
				allowBlank : false,
				width : 250
			}]
		}]
	});
			
	var updWin = new Ext.Window({
		title : '修改一级商户限额信息',
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
						url : 'T20505Action.asp?method=update',
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
							firstMchtCd : grid.getSelectionModel().getSelected().get('firstMchtCd'),
							txnId : '20505',
							subTxnId : '02'
						}
					});
				}
			}
		},{
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
				var select = grid.getSelectionModel().getSelected();
				Ext.getCmp('firstMchtCdId').setValue(select.get('firstMchtCd')+' - '+select.get('firstMchtNm'));
				Ext.getCmp('amtLimitUpd').setValue(select.get('amtLimit').replaceAll(',',''));
			}
		}]
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});