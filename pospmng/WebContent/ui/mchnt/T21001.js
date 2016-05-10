Ext.onReady(function() {
	
	
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoTl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'mchtBrhFlag',mapping: 'mchtBrhFlag'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'roleId',mapping: 'roleId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhIdName',mapping: 'brhIdName'},
			{name: 'oprRoleName',mapping: 'oprRoleName'},
			{name: 'oprStatus',mapping: 'oprStatus'},
			{name: 'oprSex',mapping: 'oprSex'},
			{name: 'cerType',mapping: 'cerType'},
			{name: 'cerNo',mapping: 'cerNo'},
			{name: 'contactPhone',mapping: 'contactPhone'},
			{name: 'email',mapping: 'email'},
			{name: 'pwdOutDate',mapping: 'pwdOutDate'},
			{name: 'mchtNoName',mapping: 'mchtNoName'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	
	
	
	var oprColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'left'},
		{header: '所属方式',dataIndex: 'mchtBrhFlag',renderer:mchntBrhState,width: 100,align: 'left'},
		{header: '操作员名称',dataIndex: 'oprName',width: 120,align: 'left'},
		{header: '所属商户',dataIndex: 'mchtNoName',width: 250,id:'mchtNoName'},
		{header: '合作伙伴',dataIndex: 'brhIdName',width: 250,align: 'left'},
		{header: '角色',dataIndex: 'oprRoleName',id:'oprRoleName',align: 'left'},
//		{header: '性别',dataIndex: 'oprSex',align: 'center',renderer: gender},
//		{header: '邮箱',dataIndex: 'email',width: 130,align: 'center'},
//		{header: '联系电话',dataIndex: 'contactPhone',align: 'center'},
//		{header: '密码有效期',dataIndex: 'pwdOutDate',renderer: formatDt,align: 'center'},
		{header: '状态',dataIndex: 'oprStatus',renderer: oprState_new,align: 'center'}
		
	]);
	
	
	// 菜单集合
	var menuArr = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
		menuArr.push(queryCondition);  //[1]
	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		//title: '操作员信息查询',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'mchtNoName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载操作员信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody(),
		listeners:{
			'cellclick':selectableCell,
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
			//激活菜单
//			oprGrid.getTopToolbar().items.items[2].enable();
//			oprGrid.getTopToolbar().items.items[6].enable();
//			var rec = oprGrid.getSelectionModel().getSelected();
//			if(rec.get('oprSta') == '1') {
//				oprGrid.getTopToolbar().items.items[8].enable();
//			} else {
//				oprGrid.getTopToolbar().items.items[8].disable();
//			}
		}
	});
	
/***************************查询条件*************************/

	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 370,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			width: 200,
			id: 'queryOprId',
			name: 'queryOprId',
			//vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['1','注销'],['2','初始化']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 200,
			id: 'queryOprStatus',
			name: 'queryOprStatus',
			//vtype: 'alphanum',
			fieldLabel: '状态'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','商户'],['1','代理']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 200,
			id: 'queryMchtBrhFlag',
			name: 'queryMchtBrhFlag',
			//vtype: 'alphanum',
			fieldLabel: '所属方式'
		},{
			xtype: 'textfield',
			width: 200,
			id: 'queryOprName',
			name: 'queryOprName',
			//vtype: 'alphanum',
			fieldLabel: '操作员名称'
		},{
			fieldLabel: '商户号',
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdAll',
			id: 'queryMchtNo',
			lazyRender: true,
			width: 300
		}
		,{
//			xtype: 'basecomboselect',
//			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '合作伙伴',
			xtype : 'dynamicCombo',
			methodName : 'getBrhId',
			id: 'queryBrhId',
			lazyRender: true,
			width: 300
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 420,
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
			oprGridStore.load();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryOprId: queryForm.findById('queryOprId').getValue(),
			queryOprStatus: queryForm.findById('queryOprStatus').getValue(),
			queryMchtNo:queryForm.findById('queryMchtNo').getValue(),
			queryBrhId:queryForm.findById('queryBrhId').getValue(),
			queryOprName: queryForm.findById('queryOprName').getValue(),
			queryMchtBrhFlag:queryForm.findById('queryMchtBrhFlag').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});