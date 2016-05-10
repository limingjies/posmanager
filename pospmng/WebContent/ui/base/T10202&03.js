Ext.onReady(function() {
	
		// 可选合作伙伴数据集
	var brhCityStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	SelectOptionsDWR.getComboData('CITY_CODE',function(ret){
	brhCityStore.loadData(Ext.decode(ret));
	});
	
	/************************************商户地区码信息*******************************************/
	var sysParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cityCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cityCodeNew',mapping: 'cityCodeNew'},
			{name: 'cityCodeOld',mapping: 'cityCodeOld'},
			{name: 'cityName',mapping: 'cityName'},
			{name: 'initTime',mapping: 'initTime'},
			{name: 'modiTime',mapping: 'modiTime'}
		])
	});
	
	sysParamStore.load({
		params:{start: 0}
	});
	
	var paramColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
    		{id: 'cityCodeNew',header: '商户地区代',dataIndex: 'cityCodeNew',sortable: true},
    		{header: '合作伙伴地区代码',dataIndex: 'cityCodeOld',
    			editor: new Ext.form.ComboBox({
    			 	store: brhCityStore,
    				displayField: 'displayField',
    				valueField: 'valueField',
    				mode: 'local',
    				triggerAction: 'all',
    				forceSelection: true,
    				typeAhead: true,
    				selectOnFocus: true,
    				editable: true
    			 })},
    		{header: '地区名称',dataIndex: 'cityName',
    		 editor: new Ext.form.TextField({
    		 	maxLength: 30,
    			allowBlank: false,
    			vtype: 'isOverMax'
    		 })},
    		{header: '创建时间',dataIndex: 'initTime',align: 'center',renderer: formatTs,width: 200},
    		{header: '最近修改时间',dataIndex: 'modiTime',align: 'center',renderer: formatTs,width: 200}
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
							url: 'T10201Action.asp?method=delete',
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
								cityCodeNew: rec.get('cityCodeNew'),
								txnId: '10201',
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
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			showProcessMsg('正在保存系统参数信息，请稍后......');
			//存放要修改的参数信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					cityCodeNew : record.get('cityCodeNew'),
					cityCodeOld : record.get('cityCodeOld'),
					cityName: record.get('cityName')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10201Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
					  cityCodeList: Ext.encode(array),
					txnId: '10201',
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
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	
	// 系统参数列表
	var grid = new Ext.grid.EditorGridPanel({
		id: 'SystemParameter',
		title: '商户地区码信息',
		iconCls: 'system',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: sysParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paramColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载系统参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sysParamStore,
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
	
	// 可选合作伙伴下拉列表
	var brhCityCombo = new Ext.form.ComboBox({
		store: brhCityStore,
		displayField: 'displayField',
		valueField: 'valueField',
		emptyText: '请选择合作伙伴',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		blankText: '请选择合作伙伴地区代码',
		fieldLabel: '合作伙伴地区代码*',
		hiddenName: 'cityCodeOld'
	});
	
	//添加系统参数表单
	var paramInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户地区代码(4位数字)*',
			id: 'cityCodeNew',
			name: 'cityCodeNew',
			width: 100,
			maxLength: 4,
			allowBlank: false,
			blankText: '商户地区代码不能为空',
			emptyText: '',
			vtype: 'isOverMax'
		},brhCityCombo,{
			fieldLabel: '地区名称*',
			id: 'cityName',
			name: 'cityName',
			width: 300,
			maxLength: 30,
			allowBlank: false,
			blankText: '地区名称不能为空',
			emptyText: '请输入地区名称',
			vtype: 'isOverMax'
		}]
	});
	
	//参数信息添加窗口
	var paramWin = new Ext.Window({
		title: '参数信息添加',
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
						url: 'T10201Action.asp?method=add',
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
							txnId: '10201',
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
	
	/************************************地区码信息*******************************************/
	
	var cityCodeStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oldCityCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cityCode',mapping: 'cityCode'},
			{name: 'cityName',mapping: 'cityName'}
		])
	});
	
	cityCodeStore.load({
		params:{start: 0}
	});
	
	var cityColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'cityCode',header: '地区码',dataIndex: 'cityCode',sortable: true},
		{header: '地区名称',dataIndex: 'cityName',
		 editor: new Ext.form.TextField({
		 	maxLength: 30,
			allowBlank: false,
			vtype: 'isOverMax'
		 })}
	]);
	
	var addMenuCity = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			cityCodeWin.show();
			cityCodeWin.center();
		}
	};
	var delMenuCity = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(cityCodeGrid.getSelectionModel().hasSelection()) {
				var rec = cityCodeGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条记录吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10203Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									cityCodeGrid.getStore().reload();
									cityCodeGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								cityCode: rec.get('cityCode'),
								txnId: '10203',
								subTxnId: '02'
							}
						});
					}
				});
			}
		}
	};
	
	var upMenuCity = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = cityCodeGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			showProcessMsg('正在保存地区码信息，请稍后......');
			//存放要修改的地区信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					cityCode : record.get('cityCode'),
					cityName: record.get('cityName')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10203Action.asp?method=update',
				method: 'post',
				params: {
					cityCodeList : Ext.encode(array),
					txnId: '10203',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					cityCodeGrid.enable();
					if(rspObj.success) {
						cityCodeGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						cityCodeGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					cityCodeGrid.getTopToolbar().items.items[4].disable();
					cityCodeGrid.getStore().reload();
					hideProcessMsg();
				}
			});
			cityCodeGrid.getTopToolbar().items.items[2].disable();
		}
	};
	
	var menuArrCity = new Array();
	menuArrCity.push(addMenuCity);
	menuArrCity.push('-');
	menuArrCity.push(delMenuCity);
	menuArrCity.push('-');
	menuArrCity.push(upMenuCity);
	
	// 地区码参数列表
	var cityCodeGrid = new Ext.grid.EditorGridPanel({
		id: 'CityCode',
		title: '地区码信息',
		iconCls: 'cityCode',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		autoHeight: true,
		store: cityCodeStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cityColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArrCity,
		loadMask: {
			msg: '正在加载地区码信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: cityCodeStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//每次在列表信息加载前都将保存按钮屏蔽
	cityCodeGrid.getStore().on('beforeload',function() {
		cityCodeGrid.getTopToolbar().items.items[4].disable();
	});
	
	cityCodeGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			cityCodeGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	cityCodeGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(cityCodeGrid.getView().getRow(cityCodeGrid.getSelectionModel().last)).frame();
			cityCodeGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	// 地区码表单
	var cityCodeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '地区码*',
			id: 'cityCode',
			name: 'cityCode',
			width: 100,
			maxLength: 4,
			allowBlank: false,
			blankText: '地区码不能为空',
			emptyText: '请输入地区码',
			vtype: 'isOverMax'
		},{
			fieldLabel: '地区名称*',
			id: 'cityName',
			name: 'cityName',
			width: 300,
			maxLength: 30,
			allowBlank: false,
			blankText: '地区名称不能为空',
			emptyText: '请输入地区名称',
			vtype: 'isOverMax'
		}]
	});
	
	// 地区码信息添加窗口
	var cityCodeWin = new Ext.Window({
		title: '地区码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(cityCodeForm.getForm().isValid()) {
					cityCodeForm.getForm().submit({
						url: 'T10203Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cityCodeForm);
							//重置表单
							cityCodeForm.getForm().reset();
							//重新加载参数列表
							cityCodeGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cityCodeForm);
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
				cityCodeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin.hide(cityCodeGrid);
			}
		}]
	});
	
	
	
	// 主面板
	var tabPanel = new Ext.TabPanel({
		items: [grid,cityCodeGrid],
		renderTo: Ext.getBody(),
		activeTab: 0
	});
});