Ext.onReady(function() {
	
	// 读取MCC信息
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntMccInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'roleId'
		},[
			{name: 'mchntTp',mapping: 'mchntTp'},
			{name: 'mchntTpGrp',mapping: 'mchntTpGrp'},
			{name: 'descr',mapping: 'descr'},
			{name: 'recSt',mapping: 'recSt'},
			{name: 'discCd',mapping: 'discCd'}
		])
	});
	
	gridStore.load({
		params:{start: 0}
	});
	
	var mchntMccColModel = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
	   {id: 'mchntTp',header: '商户MCC',dataIndex: 'mchntTp',sortable: true,width: 100},
	   {header: '商户MCC描述',dataIndex: 'descr',id:'descr',width: 300},
	   {header: '商户组别',dataIndex: 'mchntTpGrp',id:'mchntTpGrp',width: 300},
	   {header: '费率代码集合',dataIndex: 'discCd',id:'discCd',width: 300}
	]);
	
	var edit = {
		text: '编辑',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			editWin.show();
			editWin.center();
		}
	};
	
	var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(edit);
	menuArr.push('-');
	menuArr.push(queryCondition);
	
	// MCC列表
	var grid = new Ext.grid.GridPanel ({
		title: 'MCC信息',
		iconCls: 'T207',
		region:'center',
		frame: true,
		border: true,
		autoExpandColumn:'discCd',
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntMccColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载MCC信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			grid.getTopToolbar().items.items[0].enable();
		}
	});
	
	// 费率信息数据集
	var feeAllStore = new Ext.data.JsonStore ({
		root: 'data',
		fields: ['valueField','displayField']
	});
	
	SelectOptionsDWR.getComboData('MCC_FEE_INFO',function(ret) {
		feeAllStore.loadData(Ext.decode(ret));
	});
	
	// 已选费率数据集
	var feeSelectStore = new Ext.data.JsonStore ({
		root: 'data',
		fields: ['valueField','displayField','op']
	});
	
	var feeAllCol = new Ext.grid.ColumnModel ([
		{id: 'valueField',header: '费率代码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>费率信息</center>',dataIndex: 'displayField',width: 300,menuDisabled: true,resizable: false,align: 'left'}
	]);
	
	var selectedCol = new Ext.grid.ColumnModel ([
		{id: 'valueField',header: '费率代码',hidden: true,dataIndex: 'valueField'},
		{header: '<center>已选费率</center>',dataIndex: 'displayField',width: 300,menuDisabled: true,resizable: false,align: 'left'},
		{header: '',dataIndex: 'op',width: 35,menuDisabled: true,resizable: false,align: 'center',
		 renderer: renderDelete}
	]);
	
	function renderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
				       		'title="删除此菜单" onclick=deleteMenu(\''+ val + '\') onmouseover="this.style.cursor=\'pointer\'"/>';
	}
	
	// 移除一条已选费率
	window.deleteMenu = function(val) {
		var rec = selectedMenuGrid.getStore().getById(val);
		selectedMenuGrid.getStore().remove(rec);
	};
	
	// 所有费率信息
	var feeGrid = new Ext.grid.GridPanel ({
		store: feeAllStore,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		width: 350,
		cm: feeAllCol,
		loadMask: {
			msg: '正在加载费率信息......'
		},
		keys: [{
			key: Ext.EventObject.ENTER,
			handler: addSelectedMenu
		}],
		lastSelectedRowIndex: -1,
		ddGroup: 'menu',
		enableDragDrop: true
	});
	
	feeGrid.getSelectionModel().on('rowselect',function() {
		if(feeGrid.lastSelectedRowIndex != feeGrid.getSelectionModel().last) {
			feeGrid.lastSelectedRowIndex = feeGrid.getSelectionModel().last;
		}
	});
	
	feeGrid.on('rowdblclick', addSelectedMenu);
	
	var SelectedMenuRecord = new Ext.data.Record.create([
		{name: 'valueField',type: 'string'},
		{name: 'displayField',type: 'string'},
		{name: 'op',type: 'string'}
	]);
	
	function addSelectedMenu() {
		var rec = feeGrid.getSelectionModel().getSelected();
		var itemIndex = selectedMenuGrid.getStore().findExact("valueField",rec.data.valueField);
		if(itemIndex == -1) {
			var selectedRec = new SelectedMenuRecord();
			selectedRec.id = rec.get('valueField');
			selectedRec.set('valueField',rec.get('valueField'));
			selectedRec.set('displayField',rec.get('displayField'));
			selectedRec.set('op',rec.get('valueField'));
			selectedMenuGrid.getStore().add(selectedRec);
			selectedMenuGrid.getStore().sort('valueField','asc');
		}
	}
	
	// 已选菜单列表
	var selectedMenuGrid = new Ext.grid.GridPanel({
		store: feeSelectStore,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 360,
		cm: selectedCol
	});
	
	selectedMenuGrid.on('render',function() {
		var selectedMenuDDEL = selectedMenuGrid.getView().el.dom;
		var selectedMenuDDTarget = new Ext.dd.DropTarget(selectedMenuDDEL,{
			ddGroup: 'menu',
			notifyDrop: function(ddSource,e,data) {
				function addRow(record,index,allItem) {
					var itemIndex = selectedMenuGrid.getStore().findExact("valueField",record.data.valueField);
					if(itemIndex == -1) {
						var selectedRec = new SelectedMenuRecord();
						selectedRec.id = record.get('valueField');
						selectedRec.set('valueField',record.get('valueField'));
						selectedRec.set('displayField',record.get('displayField'));
						selectedRec.set('op',record.get('valueField'));
						selectedMenuGrid.getStore().add(selectedRec);
						selectedMenuGrid.getStore().sort("valueField","ASC");
					}
				}
				Ext.each(ddSource.dragData.selections,addRow);
				return (true);
			}
		});
	});
	
	// 编辑表单
	var editMenuForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		defaultType: 'textfield',
		labelWidth: 60,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '商户MCC',
			id: 'mccId',
			width: '100',
			readOnly: true
		},{
			fieldLabel: 'MCC描述',
			width: '300',
			readOnly: true
		},new Ext.Panel({
			title: '<center>请为该MCC编辑计费算法</center>',
			layout: 'table',
			items: [feeGrid,selectedMenuGrid]
		})]
	});
	
	// 编辑窗口
	var editWin = new Ext.Window({
		title: 'MCC-费率关系维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 740,
		autoHeight: true,
		layout: 'fit',
		items: [editMenuForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: editSubmit
		},{
			text: '重置',
			handler: editFormReset
		},{
			text: '关闭',
			handler: function() {
				editWin.hide(grid);
			}
		}]
	});
		
	editWin.on('show',function(){
		
		var selectedRec = grid.getSelectionModel().getSelected();
		editMenuForm.items.items[0].setValue(selectedRec.get('mchntTp'));
		editMenuForm.items.items[1].setValue(selectedRec.get('descr'));
		editFormReset();
		editWin.getEl().mask('正在加载信息......');
		setTimeout(function() {
			editWin.getEl().unmask();
		},600);
    });
	
	// 	编辑提交
	function editSubmit() {

		var menuArray = new Array();
		
		for(var i = 0,n = selectedMenuGrid.getStore().getCount(); i < n; i++) {
			var rec = selectedMenuGrid.getStore().getAt(i);
			var data = {
				valueId: rec.get('valueField')
			};
			menuArray.push(data);
			
		}
		editMenuForm.getForm().submit ({
			
			url: 'T20702Action.asp?method=edit',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,editMenuForm);
				editWin.hide(grid);
				grid.getStore().reload();
				grid.getTopToolbar().items.items[0].disable();
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,editMenuForm);
			},
			params: {
				txnId: '20702',
				subTxnId: '01',
				mccId: grid.getSelectionModel().getSelected().get('mchntTp'),
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 编辑重置
	function editFormReset() {
		
		var selectedRec = grid.getSelectionModel().getSelected();
		SelectOptionsDWR.getComboDataWithParameter('MCC_FEE',selectedRec.get('mchntTp'),function(ret) {
			var json = Ext.decode(ret);
			feeSelectStore.removeAll();
			if(json.data[0].valueField == "") {
				return;
			}
			
			var recordCreator = new Ext.data.Record.create([
				{name: 'valueField',type: 'string'},
				{name: 'displayField',type: 'string'},
				{name: 'op',type: 'string'}
			]);
			
			for(var i = 0,n = json.data.length; i < n; i++) {
				var rec = new recordCreator();
				rec.set('valueField',json.data[i].valueField);
				rec.set('displayField',json.data[i].displayField);
				rec.set('op',json.data[i].valueField);
				rec.id = json.data[i].valueField;
				feeSelectStore.add(rec);
			}
		});
	}
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
	
/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						id: 'mchtGrpId',
						hiddenName: 'mchtGrp',
						width: 400,
						listeners: {
							'select': function() {
								mchntMccStore.removeAll();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',queryForm.getForm().findField('mchtGrp').getValue(),function(ret){
									mchntMccStore.loadData(Ext.decode(ret));
								});
							}
						}
		        	}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: true,
						lazyRender: true,
						width: 400,
						blankText: '请选择商户MCC',
						id: 'idmcc',
						hiddenName: 'mcc'
		        	}]
			}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 600,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				gridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtGrp: queryForm.findById('mchtGrpId').getValue(),
			mcc:  queryForm.findById('idmcc').getValue()
		});
	});
	
});