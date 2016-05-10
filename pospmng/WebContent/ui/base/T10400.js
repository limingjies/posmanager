Ext.onReady(function() {
	
	// 可选合作伙伴数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	var roleForLook = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ROLE_FOR_LOOK',function(ret){
		roleForLook.loadData(Ext.decode(ret));
	});

	
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
	brhStore.loadData(Ext.decode(ret));
});
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'dispBrhId',mapping: 'dispBrhId'},
			{name: 'oprDegree',mapping: 'oprDegree'},
			{name: 'oprMchtPrivi',mapping: 'oprMchtPrivi'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'oprGender',mapping: 'oprGender'},
			{name: 'registerDt',mapping: 'registerDt'},
			{name: 'oprTel',mapping: 'oprTel'},
			{name: 'oprMobile',mapping: 'oprMobile'},
			{name: 'pwdOutDate',mapping: 'pwdOutDate'},
			{name: 'oprSta',mapping: 'oprSta'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
//	oprGridStore.load();
	
	
	var oprColModel = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId', align: 'center',sortable: true},
		{header: '所属合作伙伴编号',dataIndex: 'brhId' ,hidden: true ,align: 'center'},
		{header: '所属合作伙伴号',dataIndex: 'dispBrhId',sortable: true ,align: 'center'},
		{header: '角色',dataIndex: 'oprDegree',align: 'left',width: 150,renderer:function(data){
	    	if(null == data) return '';
	    	var record = roleForLook.getById(data);
	    	if(null != record){
	    		return record.data.displayField;
	    	}else{
	    		return '';
	    	}
	    }},
//		{header: '审核权限',dataIndex: 'oprMchtPrivi',align: 'center',renderer:roleLevel1},
		{header: '操作员名称',dataIndex: 'oprName',align: 'left',id:'oprName'},
		{header: '性别',dataIndex: 'oprGender',align: 'center',renderer: gender},
		{header: '注册日期',dataIndex: 'registerDt',width: 120,renderer: formatDt,align: 'center'},
		{header: '联系电话',dataIndex: 'oprTel',align: 'left'},
		{header: '手机',dataIndex: 'oprMobile',align: 'center'},
//		{header: '密码有效期',dataIndex: 'pwdOutDate',renderer: formatDt,align: 'center'},
		{header: '状态',dataIndex: 'oprSta',renderer: oprState,align: 'center'}
	]);
	
	/**
	 * 操作员状态
	 */
	function oprState(val) {
		if(val == '0'||val == '2') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/active_16.png" title="可用"/>';
		} else if(val == '1') {
			return '<img src="' + Ext.contextPath + '/ext/resources/images/stop_16.png" title="锁定"/>';
		}
		return val;
	}
	/**
	 * 审核权限
	 */
//	function roleLevel1(val) {
//		switch(val) {
//			case '0' : return '录入';
//			case '1' : return '初审';
//			case '2' : return '终审';
//			case '': return '无权限';
//		}
//	}
	
	
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
		title: '操作员信息查询',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'oprName',
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
		renderTo: Ext.getBody()
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		}
	});
/***************************查询条件*************************/
	
	// 可选合作伙伴下拉列表
	var brhCombo = new Ext.form.ComboBox({
		//store: brhStore,
		store: new Ext.data.ArrayStore({
			fields: ['valueField','displayField'],
			data: [['00001','10000001-钱宝支付']],
			reader: new Ext.data.ArrayReader()
		}),
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
		blankText: '请选择一个合作伙伴',
		fieldLabel: '合作伙伴*',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit',
		value: '00001'
	});
	
	
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			width: 160,
			id: 'oprId',
			name: 'oprId',
			vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},brhCombo]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
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
			oprId: queryForm.findById('oprId').getValue(),
			brhId: queryForm.findById('editBrh').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});