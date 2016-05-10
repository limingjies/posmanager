Ext.onReady(function() {
	
	
    //界面显示
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termCupInfoCheck'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	
	termStore.load({
		params: {
			start: 0
		}
	});
	
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{header: '终端类型',dataIndex: 'termTp',renderer:termCupType},
		{header: '所属商户',dataIndex: 'mchntNo',width: 280,id:'mchntNo'},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termCupSta},
		{header: '入网日期',dataIndex: 'recCrtTs',width: 140}
	]);
	
	var menuArr = new Array();
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',termGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = termGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T30402Action.asp?method=accept',
						params: {
							termId: rec.get('termId'),
							txnId: '30402',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,termGrid);
							} else {
								showErrorMsg(rspObj.msg,termGrid);
							}
							// 重新加载商户待审核信息
							termGrid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',termGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = termGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T30402Action.asp?method=refuse',
						params: {
							termId: rec.get('termId'),
							txnId: '30402',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,termGrid);
							} else {
								showErrorMsg(rspObj.msg,termGrid);
							}
							// 重新加载商户待审核信息
							termGrid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	var qryMenu = {
            text: '详细信息',
            width: 85,
            iconCls: 'detail',
            disabled: true,
            handler:function() {
                var selectedRecord = termGrid.getSelectionModel().getSelected();
                if(selectedRecord == null)
                {
                    showAlertMsg("没有选择记录",termGrid);
                    return;
                }
//              bt.disable();
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
	
	menuArr.push(acceptMenu);  	//[0]
	menuArr.push('-');         	//[1]
	menuArr.push(refuseMenu);   //[2]
	menuArr.push('-'); 		//[3]
    menuArr.push(qryMenu);      //[4]详细
    menuArr.push('-'); 		//[5]
	menuArr.push(queryMenu);	//[6]查询
	
	var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getTermCupNo',
                fieldLabel: '终端编号',
                hiddenName: 'termNo',
                id: 'termNoQ',
                editable: true,
                anchor: '70%'
           },{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getMchntCupIdAll',
                fieldLabel: '商户编号',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                editable: true,
                anchor: '70%'
           },{                                         
            xtype: 'combo',
			id:'stateQ',
			hiddenName: 'state',
			fieldLabel: '终端状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['9','新增待审核'],['7','修改待审核'],['5','冻结待审核'],['3','注销待审核'],['H','恢复待审核']],
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
              fieldLabel: '起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor: '70%'      
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
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
	
	
	// 直联终端审核列表
	var termGrid = new Ext.grid.EditorGridPanel({
		title: '直联终端审核',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载直联终端审核列表......'
		},
		tbar: menuArr,
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	var rec;
	
		// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
		termGrid.getTopToolbar().items.items[2].disable();
		termGrid.getTopToolbar().items.items[4].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(termGrid.getView().getRow(termGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = termGrid.getSelectionModel().getSelected();
			if(rec != null){
				termGrid.getTopToolbar().items.items[0].enable();
				termGrid.getTopToolbar().items.items[2].enable();
                termGrid.getTopToolbar().items.items[4].enable();
            }else {
            	termGrid.getTopToolbar().items.items[0].disable();
				termGrid.getTopToolbar().items.items[2].disable();
            	termGrid.getTopToolbar().items.items[4].disable();
            }
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
            endTime: topQueryPanel.getForm().findField('endTime').getValue()
            
        });
    }); 
	
	termGrid.getStore().on('beforeload',function() {
		termGrid.getStore().rejectChanges();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [termGrid],
		renderTo: Ext.getBody()
	});
});