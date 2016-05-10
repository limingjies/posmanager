Ext.onReady(function() {
	
	var termStateStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
	
    SelectOptionsDWR.getComboData('TERM_STATE',function(ret){
        termStateStore.loadData(Ext.decode(ret));
    });
    
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
		items: [new Ext.form.TextField({
				id: 'batchNo',
				name: 'batchNo',
				fieldLabel: '申请单号'
			}),
			new Ext.form.TextField({
				id: 'termNo',
				name: 'termNo',
				fieldLabel: '机具库存编号'
			}),
			new Ext.form.TextField({
				id: 'productCd',
				name: 'productCd',
				fieldLabel: '机具序列号'
			})
		],
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
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo5'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termIdId',mapping: 'termIdId'},
			{name: 'equipInvId',mapping: 'equipInvId'},
			{name: 'equipInvNm',mapping: 'equipInvNm'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'mchnNo',mapping: 'mchnNo'},
			{name: 'batchNo',mapping: 'batchNo'},
            {name: 'misc',mapping: 'misc'},
            {name: 'pinPad',mapping: 'pinPad'}
		]),
		autoLoad: true
	});
		
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    termNo: Ext.getCmp('termNo').getValue(),
		    batchNo: Ext.getCmp('batchNo').getValue(),
		    productCd:Ext.getCmp('productCd').getValue()
		});
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
	  {id: 'termId',header: '终端号',dataIndex: 'termId'},
	  {header: '所属商户',dataIndex: 'mchnNo',width: 240,renderer:function(val){return getRemoteTrans(val, "mchntName");}},
	  {header: '机具键盘连接',dataIndex: 'pinPad',renderer: pinFlag},
	  {header: '密码键盘编号',dataIndex: 'equipInvNm'},
	  {header: '机具编号',dataIndex: 'termIdId',width: 100},
      {header: '机具所属申请单号',dataIndex: 'batchNo',width: 120},
      {header: '机具序列号',dataIndex: 'productCd'},
	  {header: '机具维护状态',dataIndex: 'state',renderer: vindState},
	  {header: '机具厂商',dataIndex: 'manufacturer',width: 100},
	  {header: '机具型号',dataIndex: 'terminalType'},
	  {header: '机具类型',dataIndex: 'termType',renderer: termType},
	  {header: '绑定状态',dataIndex: 'equipInvId',hidden:true},
      {header: '备注',dataIndex: 'misc'}
	]);
	
	function pinFlag(val) {
		switch(val) {
			case 'N' : return '内置';
			case 'W' : return '外接';
			default  : return '-';;
		}
	}
	
	function vindState(val) {
		switch(val) {
			case '01' : return '<font color="red">申请维修</font>';
			case '02' : return '<font color="red">申请报废</font>';
			case '03' : return '<font color="gray">维修拒绝</font>';
			case '04' : return '<font color="gray">报废拒绝</font>';
			default  : return '<font color="green">正常</font>';;
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
    
    var unbindAll={
    	text:'解绑全部'	,
    	width:85,
    	id:'unbindA',
    	iconCls:'T30301',
    	handler:function() {
			showConfirm('确认解除全部绑定？',gridPanel,function(bt) {
				if(bt == 'yes') {
					
					var rec = gridPanel.getSelectionModel().getSelected();
				
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",gridPanel);
		                return;
		            }  
//                    showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30307Action.asp?method=unbindAll',
						params: {
							termIdId:rec.get('termIdId'),
							pinId:rec.get('equipInvNm'),
							txnId: '30307',
							subTxnId: '00'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,gridPanel);
							} else {
								showErrorMsg(rspObj.msg,gridPanel);
							}
							// 重新加载机具待审核信息
							gridPanel.getStore().reload();
							gridPanel.getTopToolbar().items.items[2].disable();
							gridPanel.getTopToolbar().items.items[4].disable();
							gridPanel.getTopToolbar().items.items[6].disable();
						}
					});
//					hideProcessMsg();
                    
				}
			});
		}
    };
    
    var unbindMana={
    	text:'解绑机具'	,
    	width:85,
    	id:'unbindM',
    	iconCls:'T30301',
    	handler:function() {
			showConfirm('确认解除已绑定的机具吗？',gridPanel,function(bt) {
				if(bt == 'yes') {
					
					var rec = gridPanel.getSelectionModel().getSelected();
				
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",gridPanel);
		                return;
		            }  
//                    showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30307Action.asp?method=unbindMana',
						params: {
							termIdId:rec.get('termIdId'),
							txnId: '30307',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,gridPanel);
							} else {
								showErrorMsg(rspObj.msg,gridPanel);
							}
							// 重新加载机具待审核信息
							gridPanel.getStore().reload();
							gridPanel.getTopToolbar().items.items[2].disable();
							gridPanel.getTopToolbar().items.items[4].disable();
							gridPanel.getTopToolbar().items.items[6].disable();
						}
					});
//					hideProcessMsg();
                    
				}
			});
		}
    };
    
    var unbindPin={
    	text:'解绑密码键盘',  	
    	width:85,
    	id:'unbindP',
    	iconCls:'T30301',
    	handler:function() {
			showConfirm('确认解除已绑定的密码键盘吗？',gridPanel,function(bt) {
				if(bt == 'yes') {
					
					var rec = gridPanel.getSelectionModel().getSelected();
				
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",gridPanel);
		                return;
		            }  
//                    showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30307Action.asp?method=unbindPin',
						params: {
							pinId:rec.get('equipInvNm'),
							txnId: '30307',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,gridPanel);
							} else {
								showErrorMsg(rspObj.msg,gridPanel);
							}
							// 重新加载机具待审核信息
							gridPanel.getStore().reload();
							gridPanel.getTopToolbar().items.items[2].disable();
							gridPanel.getTopToolbar().items.items[4].disable();
							gridPanel.getTopToolbar().items.items[6].disable();
						}
					});
//					hideProcessMsg();
                    
				}
			});
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
    menuArr.push('-');	
    menuArr.push(unbindAll);
    menuArr.push('-');
    menuArr.push(unbindMana);	
    menuArr.push('-');	
    menuArr.push(unbindPin);	
    
	//应用信息
	var gridPanel = new Ext.grid.EditorGridPanel({
		title: '机具库存解绑',
		store: gridStore,
		region:'center',
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		tbar: menuArr,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
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
    
	var brhStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboData('BRH_ID',function(ret){
        brhStore.loadData(Ext.decode(ret));
    });
	

	gridPanel.getTopToolbar().items.items[2].disable();
	gridPanel.getTopToolbar().items.items[4].disable();
	gridPanel.getTopToolbar().items.items[6].disable();
				
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			
			if(rec.get('equipInvId') == 'YY'){
				if(rec.get('pinPad') == 'N'){
					gridPanel.getTopToolbar().items.items[2].disable();
					gridPanel.getTopToolbar().items.items[4].enable();
					gridPanel.getTopToolbar().items.items[6].disable();
				}else {
					gridPanel.getTopToolbar().items.items[2].enable();
					gridPanel.getTopToolbar().items.items[4].enable();
					gridPanel.getTopToolbar().items.items[6].enable();
				}
			}else if(rec.get('equipInvId') == 'YN'){
				gridPanel.getTopToolbar().items.items[4].enable();
				gridPanel.getTopToolbar().items.items[2].disable();
				gridPanel.getTopToolbar().items.items[6].disable();
			}else if(rec.get('equipInvId') == 'NY'){
				gridPanel.getTopToolbar().items.items[6].enable();
				gridPanel.getTopToolbar().items.items[2].disable();
				gridPanel.getTopToolbar().items.items[4].disable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[gridPanel]
	});
});