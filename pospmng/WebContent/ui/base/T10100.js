Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
Ext.onReady(function() {// 可选合作伙伴数据集
	//xxx	
	var brhLvlStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	var brhNewNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	//下级合作伙伴编号
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH_SHORT',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	//下级合作伙伴号
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH_NEW_NO',function(ret){
		brhNewNoStore.loadData(Ext.decode(ret));
	});
	//合作伙伴级别
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO',function(ret){
		brhLvlStore.loadData(Ext.decode(ret));
	});
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'createNewNo',mapping :'createNewNo'},
			{name: 'brhLvl',mapping: 'brhLvl'},
//			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'upCreateNewNo',mapping :'upCreateNewNo'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'resv1',mapping: 'resv1'},
			{name: 'cupBrhId',mapping: 'cupBrhId'},
			{name: 'resv2',mapping: 'resv2'},
			{name: 'discName',mapping: 'discName'},
			{name: 'regDate',mapping: 'regDate'},
			{name : 'status',mapping : 'status'}
		]),
		autoLoad: true
	});
	
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'brhId',header: '编号',dataIndex: 'brhId',sortable: true,width: 70},
		{header : '合作伙伴号',dataIndex : 'createNewNo',width : 100},
		{header: '级别',dataIndex: 'brhLvl',renderer: brhLvlRender,hidden:true,width: 100},
//		{header: '上级编号',dataIndex: 'upBrhId',width: 80},
		{header: '上级合作伙伴号',dataIndex: 'upCreateNewNo',hidden:true,width: 100},
		{header: '合作伙伴名称',dataIndex: 'brhName',width: 150},
		{id:'brhAddr',header: '合作伙伴地址',dataIndex: 'brhAddr',width: 108},
		{header: '联系方式',dataIndex: 'brhTelNo',width: 110},
		{header: '联系人',dataIndex: 'brhContName',width: 130},
		{header: '所在地区',dataIndex: 'resv1',width: 80},
//		{header: '银联编号',dataIndex: 'cupBrhId',width: 80},
//		{header: '密钥索引',dataIndex: 'resv2',width: 60},
		{header: '费率名称',dataIndex: 'discName',hidden:true,width: 80},
		{header : '审核状态',dataIndex : 'status',width : 100},
		{header: '申请日期',dataIndex: 'regDate',width: 80,renderer:formatDt},
		{header: '操作',dataIndex:'detail',width:80,
			renderer: function(){return "<button type='button' onclick=''>详细信息</button>";}
		}
	]);

//	var menuArr = new Array();
//
//	var queryCondition = {
//			text: '录入查询条件',
//			width: 85,
//			id: 'query',
//			iconCls: 'query',
//			handler:function() {
//				queryWin.show();
//			}
//		};
//	var detail = {
//		text: '查看详细信息',
//		width: 85,
//		id: 'detail',
//		iconCls: 'detail',
//		handler:function() {
////			queryWin.show();
//			var brhId = grid.getSelectionModel().getSelected().get('brhId');
//			showBrhInfDetailS( brhId, grid);
//		}
//	};
//	menuArr.push(queryCondition);  //[1]
//	menuArr.push('-');  //[1]
//	menuArr.push(detail);  //[1]
	
	
	// 可选合作伙伴下拉列表
	var brhCombo = new Ext.form.ComboBox({
		store: brhStore,
		displayField: 'displayField',
		valueField: 'valueField',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: true,
		allowBlank: true,
		width: 200,
		fieldLabel: '合作伙伴编号',
		id: 'editBrh',
		name: 'editBrh',
		hiddenName: 'brhIdEdit'
	});
	// 可选合作伙伴下拉列表
	var brhNewNoCombo = new Ext.form.ComboBox({
		store: brhNewNoStore,
		displayField: 'displayField',
		valueField: 'valueField',
		mode: 'local',
		triggerAction: 'all',
		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: true,
		allowBlank: true,
		width: 200,
		fieldLabel: '合作伙伴号',
		id: 'editBrhNewNo',
		name: 'editBrhNewNo',
		hiddenName: 'brhNewNoIdEdit'
	});
	
//	var queryForm = new Ext.form.FormPanel({
//		frame: true,
//		border: true,
//		width: 300,
//		autoHeight: true,
//		labelWidth : 100,
//		items: [brhCombo,{
//			xtype: 'textfield',
//			id: 'searchBrhName',
//			width: 160,
//			name: 'searchBrhName',
//			fieldLabel: '合作伙伴名称'
//		},{xtype: 'combo',
//			store: brhLvlStore,
//			displayField: 'displayField',
//			valueField: 'valueField',
//			hiddenName: 'brhLvl',
//			fieldLabel: '合作伙伴级别*',
//			mode: 'local',
//			width: 160,
//			id:'searchBrhLevel',
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: false
//			}]
//	});
//	
//	
//	var queryWin = new Ext.Window({
//		title: '查询条件',
//		layout: 'fit',
//		width: 300,
//		autoHeight: true,
//		items: [queryForm],
//		buttonAlign: 'center',
//		closeAction: 'hide',
//		resizable: false,
//		closable: true,
//		animateTarget: 'query',
//		tools: [{
//			id: 'minimize',
//			handler: function(event,toolEl,panel,tc) {
//				panel.tools.maximize.show();
//				toolEl.hide();
//				queryWin.collapse();
//				queryWin.getEl().pause(1);
//				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
//			},
//			qtip: '最小化',
//			hidden: false
//		},{
//			id: 'maximize',
//			handler: function(event,toolEl,panel,tc) {
//				panel.tools.minimize.show();
//				toolEl.hide();
//				queryWin.expand();
//				queryWin.center();
//			},
//			qtip: '恢复',
//			hidden: true
//		}],
//		buttons: [{
//			text: '查询',
//			handler: function() {
//			gridStore.load();
//			}
//		},{
//			text: '清除查询条件',
//			handler: function() {
//				queryForm.getForm().reset();
//			}
//		}]
//	});

	 var tbar1 = new Ext.Toolbar({
           renderTo: Ext.grid.EditorGridPanel.tbar,  
           items: ['-','合作伙伴编号：',brhCombo,
                   '-','合作伙伴号：',brhNewNoCombo,
                   '-','合作伙伴名称：',{
		   			xtype: 'textfield',
		   			id: 'searchBrhName',
		   			width: 160,
		   			name: 'searchBrhName',
		   			fieldLabel: '合作伙伴名称'
		   		}
           		/*--yww20160324  合作伙伴查询页面-  检索条件隐藏合作伙伴级别
		   		,
		   		'-','合作伙伴级别：',{xtype: 'combo',
		   			store: brhLvlStore,
		   			displayField: 'displayField',
		   			valueField: 'valueField',
		   			hiddenName: 'brhLvl',
		   			fieldLabel: '合作伙伴级别*',
		   			mode: 'local',
		   			width: 160,
		   			id:'searchBrhLevel',
		   			triggerAction: 'all',
		   			forceSelection: true,
		   			typeAhead: true,
		   			selectOnFocus: true,
		   			editable: false
		   			}*/]
    });
	
	//合作伙伴列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '合作伙伴信息查询',
		iconCls: 'T10100',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
//		tbar: menuArr,
		loadMask: {
			msg: '正在加载合作伙伴信息列表......'
		},
		tbar: ['-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				gridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('editBrh').setValue('');
				Ext.getCmp('editBrhNewNo').setValue('');
				Ext.getCmp('searchBrhName').setValue('');
				//Ext.getCmp('searchBrhLevel').setValue('');
			}	
		},'-',{
			xtype: 'button',
			text: '查看审批过程',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			disabled: true,
			width: 80,
			handler:function() {
				var brhId = grid.getSelectionModel().getSelected().get('brhId');
				showApproveInfo(brhId);
			}
		},'-',{
			xtype: 'button',
			text: '查看修改历史',
			name: 'history',
			id: 'history',
			iconCls: 'detail',
			disabled: true,
			width: 80,
			handler:function() {
				var brhId = grid.getSelectionModel().getSelected().get('brhId');
				showBrhHistoryRecord(brhId);
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
				tbar1.render(this.tbar);
            },
            'cellclick':selectableCell,
        },
		bbar: new Ext.PagingToolbar({
			store: gridStore,
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
			Ext.getCmp('detail').enable();
			Ext.getCmp('history').enable();
		}
	});
	
    grid.addListener('cellclick',cellclick); //添加触发的函数     
    function cellclick(grid, rowIndex, columnIndex, e) {
    	//这里得到的是一个对象，即你点击的某一行的一整行数据
        //var record = grid.getStore().getAt(rowIndex);
        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);    
        //通过加判断条件限制当点击某个列表内容的时候触发，不然只要你点列表的随便一个地方就触发了。  
        if (fieldName == 'detail'){
			var brhId = grid.getSelectionModel().getSelected().get('brhId');
			showBrhInfDetailS( brhId, grid);
        }
    }   
	
	/************************************************以下是合作伙伴相关操作员信息************************************************************/
	
	
	
	/********************************主界面*************************************************/
	
	
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,						
			brhId: Ext.getCmp('editBrh').getValue(),
			brhNewNoId: Ext.getCmp('editBrhNewNo').getValue(),
			brhName: Ext.getCmp('searchBrhName').getValue(),
			brhLevel:""//Ext.getCmp('searchBrhLevel').getValue()
		});
//		Ext.getCmp('detail').disable();
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});