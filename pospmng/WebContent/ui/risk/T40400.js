Ext.onReady(function() {
	
	//商户灰名单类别数据集
	var greySortStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=greyMchtSort'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'sortNo',mapping: 'sortNo'},
			{name: 'sortName',mapping: 'sortName'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'crtTime',mapping: 'crtTime'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'updTime',mapping: 'updTime'}
		]),
		autoLoad: true
	});
	
	//灰名单商户信息数据集
	var greyMchtStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskGreyMcht'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'remarkInfo',mapping: 'remarkInfo'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'crtTime',mapping: 'crtTime'}
		])
	});
	
	var greyMchtColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户编号',dataIndex: 'mchtNo',width: 150 ,align: 'center'},
		{header: '商户名称',dataIndex: 'mchtName',width: 200},
		{id:'remarkInfoId',header : '备注信息',dataIndex : 'remarkInfo',width : 250}
	]);

	var greySortColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '类别名称',dataIndex: 'sortName',align: 'center',width: 300 }
	]);
	
//#############################      灰名单商户  开始      ##############################
	    

	// 灰名单商户添加表单
	var addForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		labelWidth: 100,
		layout: 'column',
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items: [{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'displayfield',
				fieldLabel : '所属类别',
				allowBlank : false,
				id: 'sortName',
				width: 250
			}]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype : 'dynamicCombo',
				methodName : 'getMchntId',
				fieldLabel : '商户编号*',
				blankText: '商户编号不能为空',
				emptyText : '请选择商户',
				hiddenName: 'mchtNo',
				width: 250,
				mode:'local',
				triggerAction: 'all',
				forceSelection: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: false,
				lazyRender: true
	        }]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textarea',
				fieldLabel : '备注信息',
				allowBlank : true,
				id: 'remarkInfo',
				name: 'remarkInfo',
				maxLength: 200,
				width: 250
			}]
		}]
	});
	
	var addWin = new Ext.Window({
		title: '<center>新增灰名单商户</center>',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width : 420,
		autoHeight : true,
		layout: 'fit',
		items: [addForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url: 'T40400Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addForm);
							//重新加载列表
							greyMchtStoreLoad();
							// 重置表单
							addForm.getForm().reset();
							//关闭新增窗口
							addWin.hide(greyMchtGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params: {
							txnId: '40400',
							subTxnId: '11',
							sortNo: greySortGrid.getSelectionModel().getSelected().get('sortNo')
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addForm.getForm().reset();
				var rec = greySortGrid.getSelectionModel().getSelected();
				addForm.getForm().findField('sortName').setValue(rec.data.sortName);
			}
		
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(greyMchtGrid);
			}
		}]
	});
	
	var greyMchtGrid = new Ext.grid.GridPanel({
		title: '灰名单商户列表',
		region: 'center',
		autoExpandColumn:'remarkInfoId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: greyMchtStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: greyMchtColModel,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '新增',
			iconCls: 'add',
			width: 80,
			disabled: true,
			handler:function() {
				addWin.show();
				addWin.center();
				var rec = greySortGrid.getSelectionModel().getSelected();
				addForm.getForm().findField('sortName').setValue(rec.data.sortName);
			}
/*		 },'-',{
		    xtype: 'button',
			text: '修改',
			iconCls: 'edit',
			width: 80,
			handler:function() {
				updateWin.show();
				updateWin.center();
			}*/
		 },'-', {
			xtype: 'button',
			text: '删除',
			iconCls: 'delete',
			width: 80,
			disabled: true,
			handler:function() {
				showConfirm('此灰名单商户删除后不可恢复,确定删除吗？',greyMchtGrid,function(bt) {
					if(bt == 'yes') {
						Ext.Ajax.request({
							url: 'T40400Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,greyMchtGrid);
									greyMchtStoreLoad();
								} else {
									showErrorMsg(rspObj.msg,greyMchtGrid);
								}
							},
							params: {
								txnId: '40400',
								subTxnId: '13',
								mchtNo: greyMchtGrid.getSelectionModel().getSelected().get('mchtNo'),
								sortNo: greySortGrid.getSelectionModel().getSelected().get('sortNo')
							}
							
						});
					}
				});
			}
		}],
		loadMask: {
			msg: '正在加载灰名单商户列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: greyMchtStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
	
//#############################      灰名单商户  结束      ##############################
	
//#############################      商户灰名单类别  开始      ##############################

	// 商户灰名单类别修改表单
	var updateSortForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		autoWidth: true,
        labelWidth: 70,
		items: [{
			xtype: 'textfield',
			fieldLabel: '类别名称*',
			allowBlank: false,
			blankText: '类别名称不能为空',
			emptyText: '请输入类别名称',
			id: 'sortNameUpd',
			name: 'sortNameUpd',
			maxLength: 40,
			width: 200
		}]
	});
	
	var updateSortWin = new Ext.Window({
		title: '<center>修改商户灰名单类别</center>',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		layout: 'fit',
		items: [updateSortForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(updateSortForm.getForm().isValid()) {
					updateSortForm.getForm().submit({
						url: 'T40400Action.asp?method=updateSort',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,updateSortForm);
							//重新加载列表
							greySortGrid.getStore().reload();
							//关闭新增窗口
							updateSortWin.hide(greySortGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,updateSortForm);
						},
						params: {
							txnId: '40400',
							subTxnId: '02',
							sortNo: greySortGrid.getSelectionModel().getSelected().get('sortNo')
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				var rec = greySortGrid.getSelectionModel().getSelected();
				updateSortForm.getForm().findField('sortNameUpd').setValue(rec.data.sortName);
			}
		
		},{
			text: '关闭',
			handler: function() {
				updateSortWin.hide(greySortGrid);
			}
		}]
	});

	// 商户灰名单类别添加表单
	var addSortForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		autoWidth: true,
        labelWidth: 70,
		items: [{
			xtype: 'textfield',
			fieldLabel: '类别名称*',
			allowBlank: false,
			blankText: '类别名称不能为空',
			emptyText: '请输入类别名称',
			id: 'sortNameAdd',
			name: 'sortNameAdd',
			maxLength: 40,
			width: 200
		}]
	});
	
	var addSortWin = new Ext.Window({
		title: '<center>新增商户灰名单类别</center>',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		layout: 'fit',
		items: [addSortForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addSortForm.getForm().isValid()) {
					addSortForm.getForm().submit({
						url: 'T40400Action.asp?method=addSort',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addSortForm);
							//重新加载列表
							greySortGrid.getStore().reload();
							// 重置表单
							addSortForm.getForm().reset();
							//关闭新增窗口
							addSortWin.hide(greySortGrid);
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addSortForm);
						},
						params: {
							txnId: '40400',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				addSortForm.getForm().reset();
			}
		
		},{
			text: '关闭',
			handler: function() {
				addSortWin.hide(greySortGrid);
			}
		}]
	});
	
	var greySortGrid = new Ext.grid.GridPanel({
		title: '商户灰名单类别',
		region:'west',
		width: 350,
		frame: true,
		border: true,
		split: true,
		columnLines: true,
		stripeRows: true,
		store: greySortStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: greySortColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '新增',
			iconCls: 'add',
			width: 80,
			handler:function() {
				addSortWin.show();
				addSortWin.center();
			}
		 },'-',{
		    xtype: 'button',
			text: '修改',
			iconCls: 'edit',
			width: 80,
			handler:function() {
				updateSortWin.show();
				updateSortWin.center();
				var rec = greySortGrid.getSelectionModel().getSelected();
				updateSortForm.getForm().findField('sortNameUpd').setValue(rec.data.sortName);
			}
		 },'-', {
			xtype: 'button',
			text: '删除',
			iconCls: 'delete',
			width: 80,
			handler:function() {
				showConfirm('灰名单类别删除后不可恢复,确定删除吗？',greySortGrid,function(bt) {
					if(bt == 'yes') {
						Ext.Ajax.request({
							url: 'T40400Action.asp?method=deleteSort',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,greySortGrid);
									greySortGrid.getStore().reload();				
									greyMchtGrid.getStore().removeAll();
								} else {
									showErrorMsg(rspObj.msg,greySortGrid);
								}
							},
							params: {
								txnId: '40400',
								subTxnId: '03',
								sortNo: greySortGrid.getSelectionModel().getSelected().get('sortNo')
							}
							
						});
					}
				});
			}
		}],
		loadMask: {
			msg: '正在加载商户灰名单类别列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: greySortStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
	
//#############################      商户灰名单类别  结束      ##############################
	
	function greyMchtStoreLoad() {
		greyMchtStore.load({
			params: {
				start: 0,
				sortNoQuery: greySortGrid.getSelectionModel().getSelected().data.sortNo
			},
			callback: function() {
				if(greyMchtStore.getTotalCount() == '0'){
					greySortGrid.getTopToolbar().items.items[4].enable();
				}else{
					greySortGrid.getTopToolbar().items.items[4].disable();
				}
			}
		});
	}
	
	greySortGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(greySortGrid.getView().getRow(greySortGrid.getSelectionModel().last)).frame();
			greyMchtStoreLoad();
			greyMchtGrid.getTopToolbar().items.items[0].enable();
			greySortGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	greyMchtGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(greyMchtGrid.getView().getRow(greyMchtGrid.getSelectionModel().last)).frame();
			greyMchtGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	greyMchtStore.on('beforeload', function() {
		greyMchtStore.removeAll();
		greyMchtGrid.getTopToolbar().items.items[2].disable();
	});

	
	greySortStore.on('beforeload', function(){
		greyMchtStore.removeAll();
		greySortGrid.getTopToolbar().items.items[2].disable();
		greySortGrid.getTopToolbar().items.items[4].disable();
		greyMchtGrid.getTopToolbar().items.items[0].disable();
		greyMchtGrid.getTopToolbar().items.items[2].disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [greySortGrid,greyMchtGrid],
		renderTo: Ext.getBody()
	});
});