Ext.onReady(function() {
	// 机具状态数据集
	var termStateStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
    SelectOptionsDWR.getComboData('TERM_STATE',function(ret){
        termStateStore.loadData(Ext.decode(ret));
    });
    
    // 可选机构数据集
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('BRH_BELOW_HD',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
    
	var batchStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data',
        idProperty: 'valueField'
    });
    
    SelectOptionsDWR.getComboData('TERM_BATCH',function(ret){
        batchStore.loadData(Ext.decode(ret));
    });
    
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
		items: [{											
				xtype: 'combo',
				store: brhStore,
				width: 150,
				fieldLabel: '所属机构',
				id: 'brhId',
				hiddenname: 'brhIdH'
		    },{											
				xtype: 'combo',
				store: brhStore,
				width: 150,
				fieldLabel: '目标机构',
				id: 'aimBrh',
				hiddenname: 'aimBrhH'
		    },{
				xtype: 'combo',
				store: batchStore,
                id: 'qryBatchNo',
                hiddenname: 'qryBatchNoH',
                width: 150,
                fieldLabel: '申请单号'
            },new Ext.form.TextField({
				id: 'termNo',
				name: 'termNo',
				width: 150,
				fieldLabel: '机具库存编号'
			}),{											
				xtype: 'combo',
				fieldLabel: '库存状态',
				id: 'state',
				name: 'state',
				width: 150,
				store: termStateStore
		  },
//		  ,{				
////				xtype: 'basecomboselect',
//		  		xtype: 'combo',
//				fieldLabel: '维护状态',
//				id: 'states',
//				name: 'states',
//				width: 150,
//				store: new Ext.data.ArrayStore({
//	                 fields: ['valueField','displayField'],
//	                 data: [['00','正常'],['01','申请维修中'],['02','申请报废中'],['03','维修拒绝'],['04','报废拒绝'],
//	                 ['05','维修中'],['06','已报废']]
//	            })
//		  },
		  	{
				width: 150,
				xtype: 'datefield',
				fieldLabel: '入库起始时间',
				format : 'Y-m-d',
				name :'startTime',
				id :'startTime'	
		  },{											
				width: 150,
				xtype: 'datefield',
				fieldLabel: '入库截止时间',
				format : 'Y-m-d',
				name :'endTime',
				id :'endTime'		
		}],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				gridStore.load();
                queryWin.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel.getForm().reset();
			}
		}]
	});

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termNo',mapping: 'termNo'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'targetBrhId',mapping: 'targetBrhId'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'mchnNo',mapping: 'mchnNo'},
			{name: 'mchnNoName',mapping: 'mchnNoName'},
			{name: 'batchNo',mapping: 'batchNo'},
			{name: 'storOprId',mapping: 'storOprId'},
			{name: 'storDate',mapping: 'storDate'},
			{name: 'reciOprId',mapping: 'reciOprId'},
			{name: 'reciDate',mapping: 'reciDate'},
			{name: 'bankOprId',mapping: 'bankOprId'},
			{name: 'bankDate',mapping: 'bankDate'},
			{name: 'invalidOprId',mapping: 'invalidOprId'},
			{name: 'invalidDate',mapping: 'invalidDate'},
			{name: 'signOprId',mapping: 'signOprId'},
			{name: 'signDate',mapping: 'signDate'},
            {name: 'misc',mapping: 'misc'},
            {name: 'pinId',mapping: 'pinId'},
            {name: 'pinPad',mapping: 'pinPad'},
            {name: 'lastUpdOprId',mapping: 'lastUpdOprId'},
            {name: 'lastUpdTs',mapping: 'lastUpdTs'},
            {name: 'signOprIdUpd',mapping: 'signOprId'}
		]),
		autoLoad: true
	});
		
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    brhId: Ext.getCmp('brhId').getValue(),
		    aimBrh: Ext.getCmp('aimBrh').getValue(),
		    termNo: Ext.getCmp('termNo').getValue(),
		    batchNo: Ext.getCmp('qryBatchNo').getValue(),
		    state: Ext.getCmp('state').getValue(),
//		    states: Ext.getCmp('states').getValue(),
		    startTime:typeof(topQueryPanel.findById('startTime').getValue()) =='string'?'':topQueryPanel.findById('startTime').getValue().format('Ymd'),
		    endTime:typeof(topQueryPanel.findById('endTime').getValue()) =='string'?'':topQueryPanel.findById('endTime').getValue().format('Ymd')
		});
	});
	
	var template = new Ext.Template(
			
			'<table width=600>',
			
			'<tr><td><font color=green>入库操作员：</font> {storOprId}</td>',
			'<td><font color=green>入库时间： </font>{storDate:this.date}</td></tr>',

			'<tr><td><font color=green>最近操作员：</font>{lastUpdOprId}</td>',
			'<td><font color=green>最近操作时间：</font>{lastUpdTs:this.date}</td></tr>',
			
			'<tr><td><font color=green>备注：</font>{misc}</td></tr>',
			
			{
				
				date : function(val){return formatDt(val);}
			}
		);
		var termRowExpander = new Ext.ux.grid.RowExpander({
			tpl: template
		});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		termRowExpander,
		new Ext.grid.RowNumberer(),
	  {id: 'termId',header: '机具库存编号',dataIndex: 'termId',width: 100},
		{header: '申请单号',dataIndex: 'batchNo',width: 100},
		{id: 'brhId',header: '机具所属机构',dataIndex: 'brhId',width: 100},
		{header: '库存状态',dataIndex: 'state',renderer: translateState},
		{header: '目标机构',dataIndex: 'targetBrhId',width: 80},
		{header: '维护状态',dataIndex: 'signOprIdUpd',renderer: vindState},
		{header: '机具序列号',dataIndex: 'productCd'},
		{header: '机具厂商',dataIndex: 'manufacturer',width: 120},
		{header: '机具型号',dataIndex: 'terminalType'},
		{header: '机具类型',dataIndex: 'termType',renderer: termType,width: 80},
		{header: '键盘连接',dataIndex: 'pinPad',renderer: pinFlag},
		{header: '密码键盘编号',dataIndex: 'pinId',renderer:formatTypess},
		{header: '绑定终端',dataIndex: 'termNo',renderer:formatTypess},
//		{header: '所属商户',dataIndex: 'mchnNo',width: 400,renderer:function(val){return getRemoteTrans(val, "mchntName");}}
		{header: '所属商户',dataIndex: 'mchnNoName',width: 400,renderer:formatTypess}
	]);
	
		
	function formatTypess(val) {
		if(val==null || val==""){
			return 	"-";
		}
		return val;
		
	};
	
	function vindState(val) {
		switch(val) {
			case '01' : return '<font color="red">申请维修中</font>';
			case '02' : return '<font color="red">申请报废中</font>';
			case '03' : return '<font color="green">正常</font>';
			case '04' : return '<font color="green">正常</font>';
			
//			case '03' : return '<font color="gray">维修拒绝</font>';
//			case '04' : return '<font color="gray">报废拒绝</font>';
			case '05' : return '<font color="red">维修中</font>';
			case '06' : return '<font color="gray">已报废</font>';
			case '00' : return '<font color="green">正常</font>';
			default  : return val;
		}
	}
	
	function pinFlag(val) {
		switch(val) {
			case 'N' : return '<font color="green">内置</font>';
			case 'W' : return '<font color="green">外接</font>';
			default  : return '<font color="green">-</font>';;
		}
	}
	
    var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 300,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
	
    var menuArr = new Array();
    menuArr.push(queryMenu);
    
	//应用信息
	var gridPanel = new Ext.grid.GridPanel({
		title: '机具库存查询',
		region:'center',
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		plugins: termRowExpander,
		stripeRows: true,
		loadMask: {
			msg: '正在加载机具信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 详细信息
	var modifyBrhForm = new Ext.FormPanel({
		frame: true,
		width: 450,
		autoHeight: true,
		waitMsgTarget: true,
		labelWidth: 120,
		defaults: {
			bodyStyle: 'padding-left: 20px'
		},
		items: [{					
			columnWidth: .4,
			layout: 'form',
			items:[{
				xtype: 'displayfield',
				id: 'termIdUpd',
				name: 'termIdUpd',
				width: 100,
				fieldLabel: '机具库存编号'
			}]
		},{
            columnWidth: .4,
            layout: 'form',
            items:[{
            	xtype: 'displayfield',
                fieldLabel: '申请单号',
                id: 'batchNoUpd',
                name: 'batchNoUpd'
            }]
        },{
            columnWidth: .4,
            layout: 'form',
            items:[{
            	xtype: 'displayfield',
                fieldLabel: '机具序列号',
                id: 'productCdUpd',
                name: 'productCdUpd'
            }]
        },{
            columnWidth: .4,
            layout: 'form',
            items: [{
            	xtype: 'displayfield',
                fieldLabel: '所属商户号',
                id: 'mchnNoUpd',
                name: 'mchnNoUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'combofordispaly',
                fieldLabel: '机具类型',
                id: 'termTypeUpd',
                readOnly: true,
                store: new Ext.data.ArrayStore({
                      fields: ['valueField','displayField'],
                      data: [['P','POS'],['E','EPOS']]
                })
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '机具厂商',
                name: 'manufacturerUpd',
                id: 'manufacturerUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '机具型号',
                id: 'terminalTypeUpd',
                name: 'terminalTypeUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '入库操作员',
                id: 'storOprIdUpd',
                name: 'storOprIdUpd'
            }]
        },{
            layout: 'form',
            columnWidth: .4,
            items: [{
                xtype: 'displayfield',
                fieldLabel: '入库日期',
                format: 'Y-m-d',
                id: 'storDateUpd',
                name: 'storDateUpd'
            }]
        }],
		buttonAlign: 'center',
		buttons: [{
			text: '关闭',
			handler: function() {
				modifyBrhWin.hide();
			}
		}]
	});
	// 详细信息 窗口
	var modifyBrhWin = new Ext.Window({
		title: '详细信息',
		animateTarget: 'modifyBt',
		layout: 'fit',
		width: 450,
		closeAction: 'hide',
		resizable: false,
		closable: true,
		modal: true,
		autoHeight: true,
		items: [modifyBrhForm]
	});
//	gridPanel.getTopToolbar().items.items[0].disable();
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(rec != ''  ) {
				gridPanel.getTopToolbar().items.items[0].enable();
			} else {
				gridPanel.getTopToolbar().items.items[0].disable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[gridPanel]
	});
});