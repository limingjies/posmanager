Ext.onReady(function() {
    // 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号',
                anchor :'60%'    
            }),{
	        	xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				fieldLabel: '商户编号',
				hiddenName: 'mchtCd',
				id: 'mchnNoQ',
				allowBlank: false,
				editable: true,
				width: 300
            },{                                         
                xtype: 'combo',
                fieldLabel: '终端状态',
                id: 'state',
                name: 'state',
                anchor :'60%',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['1','启用'],['2','修改未审核'],['3','冻结未审核'],['4','冻结'],['5','恢复未审核'],['6','注销未审核'],['7','注销']]
                })
            },{
    			xtype: 'basecomboselect',
    			baseParams: 'TERM_TYPE',
    			fieldLabel: '终端类型',
    			id: 'idtermtpsearch',
    			hiddenName: 'termtpsearch',
    			anchor: '60%'
    		},{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '创建起始时间',
                format : 'Y-m-d',
                name :'startTime',
                id :'startTime',
                anchor :'60%'       
            },{
                width: 150,
                xtype: 'datefield',
                fieldLabel: '创建截止时间',
                format : 'Y-m-d',
                name :'endTime',
                id :'endTime',
                anchor :'60%'       
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                termStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
    // 列表当前选择记录
    var rec;
    
    var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'mchntName',mapping: 'mchntName'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'},
            {name: 'productCd',mapping: 'productCd'}
        ]),
        autoLoad: true
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            termSta: Ext.getCmp('state').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            termTp: Ext.getCmp('idtermtpsearch').getValue()
        });
    }); 
    
    
    var termColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 80},
        {header: '商户号',dataIndex: 'mchntNo',width: 130},
        {id: 'mchntName',header: '商户名',dataIndex: 'mchntName',width: 130,id:'mchntName'},
        {header: '终端状态',dataIndex: 'termSta',width: 120,renderer: termSta},
        {header: '终端所属合作伙伴',dataIndex: 'termBranch',width : 180},
        {header : '终端SN号',dataIndex : 'productCd',width: 100},
//        {header: '终端库存编号',dataIndex: 'termIdId'},
//        {header: '终端厂商',dataIndex: 'termFactory',width: 100},
//        {header: '终端型号',dataIndex: 'termMachTp',width: 100},
        {header: '录入日期',dataIndex: 'recCrtTs',width: 150,renderer: formatDt}
    ]);
    
    
    
    var qryMenu = {
        text: '详细信息',
        width: 85,
        iconCls: 'detail',
        disabled: true,
        handler:function(bt) {
            var selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            } 
            bt.disable();
			setTimeout(function(){bt.enable();},2000);
			selectTermInfoNew(selectedRecord.get('termId'),selectedRecord.get('recCrtTs'));	
        }
    };
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
        width: 500,
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
    
    menuArr.push(qryMenu);     
    menuArr.push(queryMenu); 
    
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端信息查询',
        iconCls: 'T301',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        clicksToEdit: true,
        autoExpandColumn: 'mchntName',
        forceValidation: true,
        tbar: menuArr,
        loadMask: {
            msg: '正在加载终端信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: termStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        }),
		listeners:{
			'cellclick':selectableCell,
		}
    });
    
    
    grid.getSelectionModel().on({
        'rowselect': function() {
            //行高亮
            Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
            // 根据商户状态判断哪个编辑按钮可用
            rec = grid.getSelectionModel().getSelected();
            if(rec != null) {
                grid.getTopToolbar().items.items[0].enable();
            } else {
                grid.getTopToolbar().items.items[0].disable();
            }
        }
    });

	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

});