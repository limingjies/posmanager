Ext.onReady(function() {
	var selectedRecord ;

	// 直联商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('CUP_MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });     
    
    //界面显示
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termCupInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updDate',mapping: 'updDate'},
			{name: 'insetFlag',mapping: 'insetFlag'}
		])
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{header: '终端类型',dataIndex: 'termTp',renderer:termCupType},
		{header: '所属商户',dataIndex: 'mchntNo',width: 280,id:'mchntNo'},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termCupSta},
		{header: '入网日期',dataIndex: 'recCrtTs',width: 140},
		{header: '变更日期',dataIndex: 'updDate',width: 140},
		{header: '变更类型',dataIndex: 'insetFlag',renderer: insetFlag}
	]);
	

     
    var qryMenu = {
            text: '详细信息',
            width: 85,
            iconCls: 'detail',
            disabled: true,
            handler:function() {
                var selectedRecord = grid.getSelectionModel().getSelected();
                if(selectedRecord == null)
                {
                    showAlertMsg("没有选择记录",grid);
                    return;
                }
//                bt.disable();
//    			setTimeout(function(){bt.enable();},2000);
                selectTermCupInfo(selectedRecord.get('termId'));	
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
	
    var report = {
			text: '导出直联终端信息',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T3040101Action.asp',
					params: {
						termId: Ext.getCmp('termNoQ').getValue(),
			            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
			            termTp: Ext.getCmp('termTypeQ').getValue(),
			            termSta: Ext.getCmp('stateQ').getValue(),
			            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
			            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
				        startTimeU: topQueryPanel.getForm().findField('startTimeU').getValue(),
				        endTimeU: topQueryPanel.getForm().findField('endTimeU').getValue(),
						txnId: '3040101',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		}
	
	var menuArr = new Array();
	


    menuArr.push(qryMenu);      //[0]详细
    menuArr.push('-'); 		//[1]
	menuArr.push(queryMenu);	//[2]查询
	menuArr.push('-'); 		//[3]
	menuArr.push(report);	//[4]导出
	
	
	
	var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getTermCupNoInfo',
                fieldLabel: '终端编号',
                hiddenName: 'termNo',
                id: 'termNoQ',
                editable: true,
                anchor: '90%'
           },{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getMchntCupIdAllInfo',
                fieldLabel: '商户编号',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                editable: true,
                anchor: '90%'
           },{                                         
            xtype: 'combo',
			id:'stateQ',
			hiddenName: 'state',
			fieldLabel: '终端状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','正常'],['0','注销'],['2','冻结']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'TERM_CUP_TYPE',
			fieldLabel: '终端类型',
			id: 'termTypeQ',
			hiddenName: 'termType',
			anchor: '70%'
		},{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '入网起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor: '70%'      
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '入网截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
              anchor: '70%'   
        },{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '变更起始时间',
              format : 'Y-m-d',
              name :'startTimeU',
              id :'startTimeU',
              anchor: '70%'      
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '变更截止时间',
              format : 'Y-m-d',
              name :'endTimeU',
              id :'endTimeU',
              anchor: '70%'  
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
    
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息维护',
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
	
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[0].disable();
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			
			grid.getTopToolbar().items.items[0].enable();
		}
	});
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            termTp: Ext.getCmp('termTypeQ').getValue(),
            termSta: Ext.getCmp('stateQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            startTimeU: topQueryPanel.getForm().findField('startTimeU').getValue(),
            endTimeU: topQueryPanel.getForm().findField('endTimeU').getValue()
            
        });
    }); 
	termStore.load();

	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});