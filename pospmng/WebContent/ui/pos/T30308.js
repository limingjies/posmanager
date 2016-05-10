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
        labelWidth: 120,
		items: [new Ext.form.TextField({
				id: 'termNoQ',
				name: 'termNoQ',
				width: 200,
				fieldLabel: '机具库存编号'
			}),new Ext.form.TextField({
				id: 'batchNoQ',
				name: 'batchNoQ',
				width: 200,
				fieldLabel: '申请单号'
			}),new Ext.form.TextField({
				id: 'productCdQ',
				name: 'productCdQ',
				width: 200,
				fieldLabel: '机具序列号'
			}),new Ext.form.TextField({
				id: 'termType1Q',
				name: 'termType1Q',
				width: 200,
				fieldLabel: '机具型号'
			}),{
				xtype: 'combo',
				fieldLabel: '机具类型',
//				hiddenName: 'termType',
				id: 'termTypeQ',
				width: 200,
				store: new Ext.data.ArrayStore({
	                 fields: ['valueField','displayField'],
	                 data: [['P','pos'],['E','epos']]
	            })
			}
//				{											
//				xtype: 'combo',
//				fieldLabel: '库存状态',
//				id: 'state',
//				name: 'state',
//				store: termStateStore
//		  },
//		  	{
//				width: 150,
//				xtype: 'datefield',
//				fieldLabel: '起始时间',
//				format : 'Y-m-d',
//				name :'startTime',
//				id :'startTime',
//				anchor :'60%'		
//		  },{											
//				width: 150,
//				xtype: 'datefield',
//				fieldLabel: '截止时间',
//				format : 'Y-m-d',
//				name :'endTime',
//				id :'endTime',
//				anchor :'60%'		
//		}
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
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfoApp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhIdName',mapping: 'brhIdName'},
			{name: 'termIdUpd',mapping: 'termId'},
			{name: 'stateUpd',mapping: 'state'},
			{name: 'manufacturerUpd',mapping: 'manufacturer'},
			{name: 'productCdUpd',mapping: 'productCd'},
			{name: 'terminalTypeUpd',mapping: 'terminalType'},
			{name: 'termTypeUpd',mapping: 'termType'},
			{name: 'mchnNoUpd',mapping: 'mchnNo'},
			{name: 'batchNoUpd',mapping: 'batchNo'},
			{name: 'storOprIdUpd',mapping: 'storOprId'},
			{name: 'storDateUpd',mapping: 'storDate'},
			{name: 'reciOprIdUpd',mapping: 'reciOprId'},
			{name: 'reciDateUpd',mapping: 'reciDate'},
			{name: 'bankOprIdUpd',mapping: 'bankOprId'},
			{name: 'bankDateUpd',mapping: 'bankDate'},
			{name: 'invalidOprIdUpd',mapping: 'invalidOprId'},
			{name: 'invalidDateUpd',mapping: 'invalidDate'},
			{name: 'signOprIdUpd',mapping: 'signOprId'},
			{name: 'signDateUpd',mapping: 'signDate'},
            {name: 'miscUpd',mapping: 'misc'},
            {name: 'lastUpdOprIdUpd',mapping: 'lastUpdOprId'},
            {name: 'lastUpdTsUpd',mapping: 'lastUpdTs'}
		]),
		autoLoad: true
	});
		
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    termNo: Ext.getCmp('termNoQ').getValue(),
		    batchNo: Ext.getCmp('batchNoQ').getValue(),
		    productCd: Ext.getCmp('productCdQ').getValue(),
		    terminalType: Ext.getCmp('termType1Q').getValue(),
		    termType: Ext.getCmp('termTypeQ').getValue()
//		    brhId: Ext.getCmp('brhIdQ').getValue()
//		    state: Ext.getCmp('state').getValue(),
//		    startTime: Ext.getCmp('startTime').getValue(),
//		    endTime: Ext.getCmp('endTime').getValue()
		});
		
		gridPanel.getTopToolbar().items.items[2].disable();
		gridPanel.getTopToolbar().items.items[4].disable();
		gridPanel.getTopToolbar().items.items[0].disable();
	});
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		
			'<table width=600>',
			'<tr><td><font color=green>入库操作员：</font> {storOprIdUpd}</td>',
			'<td><font color=green>入库时间：</font>{storDateUpd:this.date}</td></tr>',
			
			'<tr><td><font color=green>最近操作员：</font>{lastUpdOprIdUpd}</td>',
			'<td><font color=green>最近操作时间：</font>{lastUpdTsUpd:this.date}</td></tr>',
			
			'<tr><td><font color=green>备注：</font>{miscUpd}</td></tr>',
			{date : function(val){return formatDt(val);}}
			)
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		termRowExpander,
	  {header: '机具库存编号',dataIndex: 'termIdUpd',width: 100},
      {header: '申请单号',dataIndex: 'batchNoUpd',width: 130},
      {header: '所属机构',dataIndex: 'brhIdName',id:'mchnNoUpd'},
	  {header: '库存状态',dataIndex: 'stateUpd',renderer: translateState,width: 120},
	  {header: '申请状态',dataIndex: 'signOprIdUpd',renderer: appState,width: 110},
	  {header: '机具厂商',dataIndex: 'manufacturerUpd',width: 150},
	  {header: '机具序列号',dataIndex: 'productCdUpd',width: 120},
	  {header: '机具型号',dataIndex: 'terminalTypeUpd',width: 120},
	  {header: '机具类型',dataIndex: 'termTypeUpd',renderer: termType,width: 100}
//	  {header: '入库操作员',dataIndex: 'storOprIdUpd'},
//	  {header: '入库时间',dataIndex: 'storDateUpd',renderer: formatDt,id:'mchnNoUpd'}
//	  {header: '所属商户号',dataIndex: 'mchnNoUpd',id:'mchnNoUpd',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName");}}
//      {header: '备注',dataIndex: 'miscUpd'}
	]);
	
	function appState(val) {
		switch(val) {
			case '01' : return '<font color="red">申请维修中</font>';
			case '02' : return '<font color="red">申请报废中</font>';
			case '03' : return '<font color="gray">维修拒绝</font>';
			case '04' : return '<font color="gray">报废拒绝</font>';
			case '05' : return '<font color="red">维修中</font>';
			case '06' : return '<font color="gray">已报废</font>';
			case '00' : return '<font color="green">正常</font>';
			default  : return val;

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
	
     var repair = {
			text: '维修申请',
			width: 85,
			iconCls: 'advanced',
			disabled: true,
			handler: function() {
				if(gridPanel.getSelectionModel().hasSelection()) {
					var rec = gridPanel.getSelectionModel().getSelected();
					
					showConfirm('确定要维修申请吗？',gridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T30308Action.asp?method=repair',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,gridPanel);
									} else {
										showErrorMsg(rspObj.msg,gridPanel);
									}
									gridPanel.getStore().reload();
									gridPanel.getTopToolbar().items.items[0].disable();
									gridPanel.getTopToolbar().items.items[2].disable();
									gridPanel.getTopToolbar().items.items[4].disable();
								},
								params: { 
									termNo: rec.get('termIdUpd'),
									txnId: '30308',
									subTxnId: '01'
								}
							});
						}
					});
				}
			}
		};
    
		 var broken = {
			text: '报废申请',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler: function() {
				if(gridPanel.getSelectionModel().hasSelection()) {
					var rec = gridPanel.getSelectionModel().getSelected();
					
					
					showConfirm('确定要报废申请吗？',gridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T30308Action.asp?method=broken',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,gridPanel);
									} else {
										showErrorMsg(rspObj.msg,gridPanel);
									}
									gridPanel.getStore().reload();
									gridPanel.getTopToolbar().items.items[0].disable();
									gridPanel.getTopToolbar().items.items[2].disable();
									gridPanel.getTopToolbar().items.items[4].disable();
								},
								params: { 
									termNo: rec.get('termIdUpd'),
									txnId: '30308',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
		
		
		 var delMenu = {
			text: '取消申请',
			width: 85,
			iconCls: 'edit',
			disabled: true,
			handler: function() {
				if(gridPanel.getSelectionModel().hasSelection()) {
					var rec = gridPanel.getSelectionModel().getSelected();
					
					if(rec.get('signOprIdUpd')!='01' && rec.get('signOprIdUpd')!='02'){
						showErrorMsg('只有正在申请中的才能取消！',gridPanel,function(){
							gridPanel.getTopToolbar().items.items[2].disable();
							gridPanel.getTopToolbar().items.items[4].disable();
							gridPanel.getTopToolbar().items.items[0].disable();
							return;
						});
					}
					else{
					showConfirm('确定要取消该申请信息吗？',gridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T30308Action.asp?method=update',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,gridPanel);
									} else {
										showErrorMsg(rspObj.msg,gridPanel);
									}
									gridPanel.getStore().reload();
									gridPanel.getTopToolbar().items.items[0].disable();
									gridPanel.getTopToolbar().items.items[4].disable();
									gridPanel.getTopToolbar().items.items[2].disable();
								},
								params: { 
									termNo: rec.get('termIdUpd'),
									txnId: '30308',
									subTxnId: '03'
								}
							});
						}
					});
				}
				}
			}
		};
    
		
    var menuArr = new Array();
    menuArr.push(repair);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(broken);
	menuArr.push('-');	
	menuArr.push(delMenu);
	menuArr.push('-');	
    menuArr.push(queryMenu);
    
	//应用信息
	var gridPanel = new Ext.grid.GridPanel({
		title: '机具维修报申请',
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
		autoExpandColumn: 'mchnNoUpd',
		stripeRows: true,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
	
	
	gridPanel.getTopToolbar().items.items[2].disable();
	gridPanel.getTopToolbar().items.items[4].disable();
	gridPanel.getTopToolbar().items.items[0].disable();
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(rec != '' && rec.get('signOprIdUpd')!=('01')&&rec.get('signOprIdUpd')!=('02'))  {
				gridPanel.getTopToolbar().items.items[0].enable();
				gridPanel.getTopToolbar().items.items[2].enable();
				gridPanel.getTopToolbar().items.items[4].disable();
			} else if(rec != '' && (rec.get('signOprIdUpd')==('01')||rec.get('signOprIdUpd')==('02'))){
				gridPanel.getTopToolbar().items.items[4].enable();
				gridPanel.getTopToolbar().items.items[0].disable();
				gridPanel.getTopToolbar().items.items[2].disable();
			}else {
				gridPanel.getTopToolbar().items.items[0].disable();
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