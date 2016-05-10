Ext.onReady(function() {
	
	// 数据集
	var smallPayStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=smallPay'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'acctNo',mapping: 'acctNo'},
			{name: 'acctNm',mapping: 'acctNm'},
			{name: 'cnapsId',mapping: 'cnapsId'},
			{name: 'cnapsName',mapping: 'cnapsName'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'crtTime',mapping: 'crtTime'},
			{name: 'reserved',mapping: 'reserved'}
		]),
		autoLoad: true
	}); 
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=700>',
		'<tr><td><font color=gray>录入日期： </font>{crtTime:this.formatDate}</td>',
		'<td><font color=gray>录入操作员：</font>{crtOpr}</td></tr></table>',
		{
			formatDate : function(val){
				if(val!=null &&val.length == 8){
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-' + val.substring(6, 8);
				}else if(val!=null &&val.length == 6){
					return val.substring(0, 2) + ':' + val.substring(2, 4) + ':' + val.substring(4);
				}else if (val.length == 14) {
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-' + val.substring(6, 8) + ' ' 
							+ val.substring(8, 10) + ':' + val.substring(10, 12) + ':' + val.substring(12, 14);
				} else{
					return val;
				}
			}
		})
	});
	var smallPayColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '账户账号',dataIndex: 'acctNo',width: 180,align: 'center' },
		{header: '账户名称',dataIndex: 'acctNm',width: 200 ,align: 'left'},
		{header: '开户行号',dataIndex: 'cnapsId',width: 150,align: 'center' },
		{header: '开户行名称',dataIndex: 'cnapsName',width: 320,align: 'left' },
		{header: '备注信息',dataIndex: 'reserved',id:'reserved',width: 250 ,align: 'left'}
	]);
	
	
	var tbar1 = new Ext.Toolbar({
		renderTo: Ext.grid.EditorGridPanel.tbar,  
		items:['-','账户账号：',{
			xtype : 'dynamicCombo',
			methodName : 'getMchtAcctNo',
			hiddenName: 'queryAcctNo',
			width: 250,
			id: 'idqueryAcctNo',
			mode:'local',
			triggerAction: 'all',
			forceSelection: true,
			selectOnFocus: true,
			editable: true,
			lazyRender: true
		    }
		]
	})
	
	var grid = new Ext.grid.GridPanel({
		title: '中信小额支付维护',
		iconCls: 'logo',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'reserved',
		store: smallPayStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: smallPayColModel,
		plugins: rowExpander,
		forceValidation: true,
		loadMask: {
			msg: '正在加载中信小额支付账户信息列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				smallPayStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryAcctNo').setValue('');
				smallPayStore.load();
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
			text : '删除',
			name : 'delete',
			id : 'delete',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该账户信息吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T80904Action.asp?method=delete',
							params : {
								acctNo  : grid.getSelectionModel().getSelected().get('acctNo'),
								txnId : '80904',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
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
		listeners : {//將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
				tbar1.render(this.tbar); 
            }  
        },
		bbar: new Ext.PagingToolbar({
			store: smallPayStore,
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
				Ext.getCmp('delete').enable();
		}
	});
	
	
	smallPayStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryAcctNo: Ext.getCmp('idqueryAcctNo').getValue()
		});
	});
	
	
	// 添加
	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 70,
		height : 350,
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
					xtype : 'dynamicCombo',
					methodName : 'getMchtAcctNo',
					fieldLabel : '账户帐号*',
					blankText: '账户帐号不能为空',
					emptyText : '请选择账户帐号',
					hiddenName: 'tblPayTypeSmallAdd.acctNo',
					id : 'acctNoAdd',
					width: 250,
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true,
					allowBlank: false
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '账户名称',
					id : 'acctNmAdd',
					name : 'tblPayTypeSmallAdd.acctNm',
					allowBlank: false,
					blankText: '账户名称不能为空',
					readOnly : true,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '开户行号',
					id : 'cnapsIdAdd',
					name : 'tblPayTypeSmallAdd.cnapsId',
					readOnly : true,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype : 'textfield',
					fieldLabel : '开户行名称',
					id : 'cnapsNameAdd',
					name : 'tblPayTypeSmallAdd.cnapsName',
					allowBlank: false,
					blankText: '开户行名称不能为空',
					readOnly : true,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textarea',
					fieldLabel : '备注信息',
					allowBlank : true,
					id: 'reservedAdd',
					name: 'tblPayTypeSmallAdd.reserved',
					maxLength: 50,
					width: 250
				}]
			}
		]
	});
	
	var addWin = new Ext.Window({
		title : '新增账户信息',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 390,
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
						url : 'T80904Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							grid.getStore().reload();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
							txnId : '80904',
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
			
	Ext.getCmp('acctNoAdd').on('select',function(){
 		T80904.getSettleInf(this.getValue(),function(ret){
 			var retMsg = Ext.decode(ret)
			if(retMsg.success){
				Ext.getCmp('acctNmAdd').setValue(retMsg.settleAcctNm);
				Ext.getCmp('cnapsIdAdd').setValue(retMsg.openStlno);
				Ext.getCmp('cnapsNameAdd').setValue(retMsg.settleBankNm);
			}else{
				showErrorMsg('加载账户信息失败，请刷新！',addForm);
			}
    	})
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});