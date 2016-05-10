Ext.onReady(function() {
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
    var brhStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
        brhStore.loadData(Ext.decode(ret));
    });
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 100,
        items: [new Ext.form.TextField({
                id: 'termId',
                name: 'termId',
                fieldLabel: '终端号'
            }),{
            	fieldLabel: '商户编号',
				xtype: 'dynamicCombo',
				methodName: 'getMchntNo',
				id: 'mchntNoQ',
				hiddenName: 'mchntNo',
				editable: true,
				width: 320
			},{ 
                xtype: 'combo',
                fieldLabel: '申请状态',
                hiddenName: 'state',
                id: 'stateQ',
                displayField: 'displayField',
                valueField: 'valueField',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['2','未申请'],['1','已审核']]
                })
            },{
				xtype: 'datefield',
				name: 'startDate',
				id: 'startDate',
				fieldLabel: '起始创建日期'
			},{
				xtype: 'datefield',
				name: 'endDate',
				id: 'endDate',
				fieldLabel: '结束创建日期'
			},{
                xtype: 'combo',
                fieldLabel: '所属合作伙伴',
                id: 'termBranchQ',
                hiddenName: 'termBranch',
                store: brhStore,
                displayField: 'displayField',
                valueField: 'valueField'
            },{
                xtype: 'hidden',
                id: 'batchNo',
                name: 'batchNo'
        }],
        buttons: [{
            text: '查询',
            handler: function(){
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
            url: 'gridPanelStoreAction.asp?storeId=termTmkInfo'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'state',mapping: 'state'},
            {name: 'batchNo',mapping: 'batchNo'},
            {name: 'reqOpr',mapping: 'reqOpr'},
            {name: 'reqDate',mapping: 'reqDate'},
            {name: 'chkOpr',mapping: 'chkOpr'},
            {name: 'chkDate',mapping: 'chkDate'},
            {name: 'recCrtTs',mapping: 'recCrtTs'}
        ])
    });
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            state: topQueryPanel.getForm().findField('stateQ').getValue(),
            termId: topQueryPanel.getForm().findField('termId').getValue(),
            termSta: 1,
            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
            endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
            mchnNo: topQueryPanel.getForm().findField('mchntNoQ').getValue(),
            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue()
        });
    });
    termStore.load();

    
    
    var termColModel = new Ext.grid.ColumnModel([
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {header: '商户号',dataIndex: 'mchntNo',width: 150,id:'mchntNo'},
        {header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termSta},
        {header: '所属机构号',dataIndex: 'termBranch',renderer: termSignSta},
        {header: '申请状态',dataIndex: 'state',renderer: termState},
        {header: '批次号',dataIndex: 'batchNo'},
        {header: '申请人',dataIndex: 'reqOpr'},
        {header: '申请时间',dataIndex: 'reqDate'},
        {header: '审核人',dataIndex: 'chkOpr'},
        {header: '审核时间',dataIndex: 'chkDate'}
    ]);
    
    
    var reqMenu = {
        text: '单笔申请',
        width: 85,
        iconCls: 'accept',
        disabled: true,
        handler:function() {
            showConfirm('确认申请吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.request({
                        url: 'T30202Action.asp',
                        params: {
                            termId: rec.get('termId'),
                            recCrtTs: rec.get('recCrtTs'),
                            txnId: '30202',
                            subTxnId: '01'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
        }
    }
    
    var queryMenu = {
        text: '查询',
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
    
    
    //终端型号
    var terminalTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
 
	var batchMenu = {
        text: '批量申请',
        width: 85,
        id: 'batch',
        iconCls: 'accept',
        handler:function() {
            showConfirm('确认批量处理吗？',grid,function(bt) {
                if(bt == 'yes') {
                    showProcessMsg('正在提交信息，请稍后......');
                    rec = grid.getSelectionModel().getSelected();
                    Ext.Ajax.requestNeedAuthorise({
                        url: 'T30202Action.asp',
                        params: {
                            termNo: Ext.getCmp('termId').getValue(),
                            mchtCd: topQueryPanel.getForm().findField('mchntNoQ').getValue(),
                            termBranch: topQueryPanel.getForm().findField('termBranchQ').getValue(), 
                            state: topQueryPanel.getForm().findField('stateQ').getValue(),  
                            startDate: typeof(topQueryPanel.findById('startDate').getValue()) == 'string' ? '' : topQueryPanel.findById('startDate').getValue().dateFormat('Ymd'),
            				endDate: typeof(topQueryPanel.findById('endDate').getValue()) == 'string' ? '' : topQueryPanel.findById('endDate').getValue().dateFormat('Ymd'),
                            txnId: '30202',
                            subTxnId: '02'
                        },
                        success: function(rsp,action) {
                            var rspObj = Ext.decode(rsp.responseText);
                            if(rspObj.success) {
                                showSuccessDtl(rspObj.msg,grid);
                            } else {
                                showErrorMsg(rspObj.msg,grid);
                            }
                            grid.getStore().reload();
                        }
                    });
                    termStore.load();
                    grid.getTopToolbar().items.items[0].disable();
                    hideProcessMsg();
                }
            });
		}
    };
    
    var menuArr = new Array();
    
    menuArr.push(reqMenu);     
    menuArr.push(batchMenu);
    menuArr.push(queryMenu);
    
    // 终端信息列表
    var grid = new Ext.grid.GridPanel({
        title: '终端密钥下发申请',
        iconCls: 'T30202',
        region:'center',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termColModel,
        autoExpandColumn: 'mchntNo',
        clicksToEdit: true,
        forceValidation: true,
        tbar: menuArr,
        renderTo: Ext.getBody(),
        loadMask: {
            msg: '正在加载终端信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: termStore,
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
})
