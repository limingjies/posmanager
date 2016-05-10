Ext.onReady(function() {
	
    var batchStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data',
        idProperty: 'valueField'
    });
    
    SelectOptionsDWR.getComboData('TERM_BATCH',function(ret){
        batchStore.loadData(Ext.decode(ret));
    });
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfoErrMain'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'appId',mapping: 'appId'},
			{name: 'appDate',mapping: 'appDate'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'backOprId',mapping: 'backOprId'},
			{name: 'stat',mapping: 'stat'},
			{name: 'brhIdName',mapping: 'brhIdName'}
		]),
		autoLoad: true
	});
	
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
		gridStore2.removeAll();
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
      {header: '申请单号',dataIndex: 'appId'},
	  {header: '申请日期',dataIndex: 'appDate',renderer:formatDt },
      {header: '申请机构',dataIndex: 'brhId',hidden:true,width: 180},
      {header: '审核状态',dataIndex: 'stat',renderer:appSta,width: 80},
      {id: 'termId2',header: '申请机构',dataIndex: 'brhIdName'},
	  {header: '审核操作员',dataIndex: 'backOprId'}
	]);
	
	function appSta(val) {
		switch(val) {
			case '0' : return '<font color="red">未审核</font>';
			case '1' : return '<font color="green">已审核</font>';
			case '2' : return '<font color="gray">已取消</font>';
			case '3' : return '<font color="green">请求完成</font>';
			default  : return val;
		}
	}

	var gridStore2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfoErr'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'sonAppId',mapping: 'sonAppId'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'amount',mapping: 'amount'},
			{name: 'accAmount',mapping: 'accAmount'},
			{name: 'inAmount',mapping: 'inAmount'},
			{name: 'errType',mapping: 'errType'},
			{name: 'errAmount',mapping: 'errAmount'},
			{name: 'stat',mapping: 'stat'}
		])
	});
		
	gridStore2.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    appNo: gridPanel.getSelectionModel().getSelected().get('appId')
		});
	});

	
	var gridColumnModel2 = new Ext.grid.ColumnModel([
      {header: '子单号',dataIndex: 'sonAppId'},
	  {header: '终端厂商',dataIndex: 'manufacturer',width: 150},
	  {header: '终端型号',dataIndex: 'terminalType'},
	  {header: '终端类型',dataIndex: 'termType',renderer: termType},
	  {header: '申请数量',dataIndex: 'amount'},
	  {header: '审核结果',dataIndex: 'stat',renderer: stat},
	  {header: '通过数量',dataIndex: 'accAmount'},
	  {header: '入库数量',dataIndex: 'inAmount'},
	  {header: '差错类型',dataIndex: 'errType',renderer: errType},
	  {header: '差错数量',dataIndex: 'errAmount'}
	]);
	
	function errType(val) {
		switch(val) {
			case 'M' : return '<font color="red">多出</font>';
			case 'L' : return '<font color="red">少发</font>';
			default  : return '<font color="green">无差错</font>';;
		}
	}
	
	function stat(val) {
		switch(val) {
			case '0' : return '未审核';
			case '1' : return '<font color="green">通过</font>';
			case '2' : return '<font color="green">部分通过</font>';
			case '3' : return '<font color="gray">拒绝</font>';
			default  : return val;
		}
	}
	
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
		items: [{
				xtype: 'combo',
				store: batchStore,
                id: 'qryBatchNo',
                hiddenname: 'qryBatchNoH',
                width: 150,
                fieldLabel: '申请单号'
            },{
				xtype: 'dynamicCombo',
				fieldLabel: '申请机构',
				methodName: 'getBranchId',
				id: 'brhIdQ',
				width: 200,
				editable: true
			},{
				width: 150,
				xtype: 'datefield',
				fieldLabel: '申请起始时间',
				format : 'Y-m-d',
				name :'startTime',
				id :'startTime'	
		   },{											
				width: 150,
				xtype: 'datefield',
				fieldLabel: '申请截止时间',
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
	
    //差错同步
	var compareMenu = {
        id: 'compareButton',
		text: '差错同步',
		width: 85,
		iconCls: 'accept',
		handler:function() {
			
			showConfirm('确认同步数据？',gridPanel,function(bt) {
					if(bt == 'yes') {
			
						Ext.Ajax.request({
			                url: 'T30310Action.asp?method=compare',
			                waitMsg: '正在提交，请稍后......',
			                success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,gridPanel);
								} else {
									showErrorMsg(rspObj.msg,gridPanel);
								}
								// 重新加载信息
								gridPanel.getStore().reload();
								gridStore2.removeAll();
							},
			                params: {
			                    txnId: '30310',
			                    subTxnId: '01'
			                }
			            });
			            hideProcessMsg();
					}
			});
		}
	};
		
    var menuArr = new Array();

    menuArr.push(compareMenu);
    menuArr.push('-');
    menuArr.push(queryMenu);
    
	//母菜单信息
	var gridPanel = new Ext.grid.GridPanel({
		title: '机具差错管理主菜单',
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
		stripeRows: true,
//		autoExpandColumn: 'termId2',
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
	
	//子菜单信息
	var gridPanel2 = new Ext.grid.GridPanel({
		title: '机具差错管理',
		region:'east',
		iconCls: 'T30301',
		frame: true,
		border: true,
		width: 650,
		columnLines: true,
		store: gridStore2,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel2,
		clicksToEdit: true,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		stripeRows: true,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore2,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
	
	

	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			
			gridPanel2.getStore().reload();
		}
	});
	
	gridPanel2.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel2.getView().getRow(gridPanel2.getSelectionModel().last)).frame();
		}
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[gridPanel,gridPanel2]
	});
});