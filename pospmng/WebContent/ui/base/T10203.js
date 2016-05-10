Ext.onReady(function() {
	
	var yesOrNot = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	//机构级别
	SelectOptionsDWR.getComboData('YES_NOT',function(ret){
		yesOrNot.loadData(Ext.decode(ret));
	});
	
	/************************************交易类型维护信息*******************************************/
	var funcInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblTxnName'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'txnDsp',mapping: 'txnDsp'},
			{name: 'dcFlag',mapping: 'dcFlag'}
		])
	});
	
	funcInfStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'txnNum',header: '交易码',dataIndex: 'txnNum',sortable: true},		
		{header: '交易名称',dataIndex: 'txnName',
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			maxLength: 30,
			vtype: 'isOverMax'
		 })},
		{header: '交易描述',dataIndex: 'txnDsp',id:'txnDsp',
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 60,
			vtype: 'isOverMax'
		 })},
		{header: '是否启用',dataIndex: 'dcFlag',renderer: yesOrNo,
		  editor: new Ext.form.ComboBox({
		 	store: yesOrNot,
			displayField: 'displayField',
			valueField: 'valueField',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true
		 })}
	]);
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			paramWin.show();
			paramWin.center();
		}
	};
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条记录吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10203Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								id: rec.get('txnNum'),
								txnId: '10203',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	
	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			showConfirm('确认修改保存吗？',grid,function(bt) {
				if(bt == 'yes') {
					var modifiedRecords = grid.getStore().getModifiedRecords();
					if(modifiedRecords.length == 0) {
						return;
					}
			}
				var store = grid.getStore();
				var len = store.getCount();
				for(var i = 0; i < len; i++) {
					var record = store.getAt(i);
				}
			showProcessMsg('正在保存交易类型维护信息，请稍后......');
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('txnNum'),
					txnName : record.get('txnName'),
					txnDsp: record.get('txnDsp'),
					dcFlag: record.get('dcFlag')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10203Action.asp?method=update',
				method: 'post',
				params: {
				    txnNameList : Ext.encode(array),
					txnId: '10203',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
			grid.getTopToolbar().items.items[2].disable();
			hideProcessMsg();
		
		});
	}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	
	// 交易类型维护列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '交易类型维护信息',
		iconCls: 'system',
		region: 'center',
		autoExpandColumn:'txnDsp',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: funcInfStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载交易类型维护信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: funcInfStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[4].disable();
	});
	
	grid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			//激活菜单
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	// 可选机构下拉列表
	var canUse = new Ext.form.ComboBox({
		store: yesOrNot,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择是否启用',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择是否启用',
		fieldLabel: '是否启用*',
		id:"dcFlagId",
		hiddenName: 'dcFlag'
	});
	
	//添加交易类型维护表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '交易码*',
			id: 'id',
			name: 'id',
			width: 200,
			maxLength: 4,
			allowBlank: false,
			blankText: '交易码不能为空',
			emptyText: '请输入交易码',
			maxLengthText: '交易码最多可以输入4个数字',
			maskRe:/^[0-9]$/,
			blankText: '该输入项只能包含数字'
		},{
			fieldLabel: '交易名称*',
			id: 'txnName',
			name: 'txnName',
			width: 200,
			maxLength: 30,
			allowBlank: false,
			blankText: '交易名称不能为空',
			emptyText: '请输入交易名称',
			vtype: 'isOverMax'
		},{
			fieldLabel: '交易描述*',
			id: 'txnDsp',
			name: 'txnDsp',
			width: 200,
			maxLength: 60,
			allowBlank: false,
			blankText: '交易描述不能为空',
			emptyText: '请输入交易描述',
			vtype: 'isOverMax'
		},canUse]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '交易类型添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [paramInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(paramInfoForm.getForm().isValid()) {
					paramInfoForm.getForm().submit({
						url: 'T10203Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,paramInfoForm);
							//重置表单
							paramInfoForm.getForm().reset();
							//重新加载参数列表
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,paramInfoForm);
						},
						params: {
							txnId: '10203',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				paramInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				paramWin.hide(grid);
			}
		}]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	// 主面板
	var tabPanel = new Ext.TabPanel({
		items: [grid],
		renderTo: Ext.getBody(),
		activeTab: 0
	});
});