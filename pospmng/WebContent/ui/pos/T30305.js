Ext.onReady(function() {
	
    var termInfo;
    var termManagementInfo;
    
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    var batchStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data',
        idProperty: 'valueField'
    });
    
    SelectOptionsDWR.getComboData('TERM_BATCH',function(ret){
        batchStore.loadData(Ext.decode(ret));
    });
    
//    var termStateStore = new Ext.data.JsonStore({
//        fields: ['valueField','displayField'],
//        root: 'data'
//    });
//    SelectOptionsDWR.getComboData('TERM_STATE',function(ret){
//        termStateStore.loadData(Ext.decode(ret));
//    });
    
    //终端信息
	var termInfoStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termInfoBind'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'equipInvId',mapping: 'equipInvId'},
            {name: 'equipInvNm',mapping: 'equipInvNm'},
            {name: 'manaBind',mapping: 'manaBind'}
        ])
    });
   
    var termInfoColModel = new Ext.grid.ColumnModel([
    	new Ext.grid.RowNumberer(),
        {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
        {header: '终端类型',dataIndex: 'termTp',renderer:settleType},
        {header: '商户号',dataIndex: 'mchntNo',width: 320,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
        {header: '终端状态',dataIndex: 'termSta',width: 100,renderer: termSta},
        {header: '键盘绑定',dataIndex: 'equipInvId',hidden:true},
        {header: '密码键盘',dataIndex: 'equipInvNm'},
        {header: '机具绑定',dataIndex: 'manaBind',hidden:true},
        {header: '机具库存编号',dataIndex: 'termIdId'},
        {header: '机具厂商',dataIndex: 'termFactory',width: 150},
        {header: '机具型号',dataIndex: 'termMachTp',width: 100}
    ]);
	
    function settleType(val){
			switch(val){
				case '0':return '普通POS';
				case '1':return '财务POS';
				case '2':return '签约POS';
				case '3':return '电话POS';
				case '4':return 'MISPOS';
				case '5':return '移动POS';
				case '6':return '网络POS';
				case '7':return 'MPOS';
				default : return val;
		}
    }		
    
    
    //机具信息
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo3'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'mchnNo',mapping: 'mchnNo'},
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
            {name: 'pinPad',mapping: 'pinPad'}
		])
	});
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
//			'<p>入库操作员：{storOprId}</p>',
//			'<p>入库时间：{storDate}</p>'
			'<table width=300>',
			'<tr><td><font color=green>入库操作员：</font>{storOprId}</td></tr>',
			'<tr><td><font color=green>入库时间：</font>{storDate:this.date}</td></tr>',
			
			{
				date : function(val){return formatDt(val);}
			}
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
	    termRowExpander,
	    new Ext.grid.RowNumberer(),
	    {id: 'termId',header: '机具库存编号',dataIndex: 'termId',width: 100},
	    {header: '申请单号',dataIndex: 'batchNo'},
        {header: '键盘连接',id:'pinPad',dataIndex: 'pinPad',renderer: pinFlag},
	    {header: '库存状态',dataIndex: 'state',renderer: translateState,hidden:true},
	    {header: '机具厂商',dataIndex: 'manufacturer',width: 150},
	    {header: '机具序列号',dataIndex: 'productCd'},
	    {header: '机具型号',dataIndex: 'terminalType'},
	    {header: '机具类型',dataIndex: 'termType',renderer: termType}
//	    {header: '所属商户号',dataIndex: 'mchnNo',width: 150,renderer:function(val){return getRemoteTrans(val, "mchntName")}}
	]);
	
	function pinFlag(val) {
		switch(val) {
			case 'N' : return '内置';
			case 'W' : return '外接';
			default  : return '-';;
		}
	}
	
	
	
	//只显示PINPAD
	var pinStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfoPin'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'state',mapping: 'state'},
			{name: 'manufacturer',mapping: 'manufacturer'},
			{name: 'productCd',mapping: 'productCd'},
			{name: 'terminalType',mapping: 'terminalType'},
			{name: 'termType',mapping: 'termType'},
			{name: 'mchnNo',mapping: 'mchnNo'},
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
            {name: 'pinPad',mapping: 'pinPad'}
		])
	});
	
	
	var termRowExpander3 = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
//			'<p>入库操作员：{storOprId}</p>',
//			'<p>入库时间：{storDate}</p>'
			'<table width=300>',
			'<tr><td><font color=green>入库操作员：</font>{storOprId}</td></tr>',
			'<tr><td><font color=green>入库时间：</font>{storDate:this.date}</td></tr>',
			
			{
				date : function(val){return formatDt(val);}
			}
		)
	});
	
	var pinColModel = new Ext.grid.ColumnModel([
	    termRowExpander3,
	    new Ext.grid.RowNumberer(),
	    {id: 'termId',header: '机具库存编号',dataIndex: 'termId',width: 100},
        {header: '申请单号',dataIndex: 'batchNo'},
	    {header: '库存状态',dataIndex: 'state',renderer: translateState,hidden:true},
	    {header: '机具厂商',dataIndex: 'manufacturer',width: 150},
	    {header: '机具序列号',dataIndex: 'productCd'},
	    {header: '机具型号',dataIndex: 'terminalType'},
	    {header: '机具类型',dataIndex: 'termType',renderer: termType}
	]);
	
	
	
	termInfoStore.load({
		params: {
			start: 0
		}
	});
	termStore.load({
		params: {
			start: 0
		}
	});
	pinStore.load({
		params: {
			start: 0
		}
	});
	
	//机具绑定
	var acceptMenu = {
        id: 'acceptButton',
		text: '绑定机具',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			
			var mana = grid.getSelectionModel().getSelected();
			var termInfo = grid2.getSelectionModel().getSelected();
			
			
			var termMana = mana.get('termId');
			var term = termInfo.get('termId');
			var pinFlag = mana.get('pinPad');
			var pinBindFlag = termInfo.get('equipInvId');
			
			if(mana == null){
				showAlertMsg('请选择机具',mainView);
			}
			if(termInfo == null){
				showAlertMsg('请选择终端',mainView);
			}
			
			if(pinFlag == 'N' && pinBindFlag.substring(1,2) == 'Y'){
				showAlertMsg('终端已经绑定密码键盘,无法绑定内置键盘的机具！',mainView);
				return;
			}
			
			T30305.isExistMana(termMana,term,function(ret){
				if(ret == "03"){
					showAlertMsg('该机具已经绑定其他终端,请先解绑后再继续！',mainView);
				}
				if(ret == "00"){
					showConfirm('确定绑定？',mainView,
					function(bt) {
						if(bt == "yes") {
							showProcessMsg('正在绑定，请稍后......');
							
							Ext.Ajax.request({
		                        url: 'T30305Action.asp?method=managementBind',
		                        waitMsg: '正在提交，请稍后......',
		                        success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,mainView);
									} else {
										showErrorMsg(rspObj.msg,mainView);
									}
									// 重新加载信息
									grid.getStore().reload();
		                            grid2.getStore().reload();
		                            grid.getTopToolbar().items.items[0].disable();
								},
		                        params: {
		                        	txnId: '30305',
		                            subTxnId: '01',
	                                termId: term,
	                                termIdId: termMana,
	                                pinFlag: pinFlag
		                        }
		                    });
		                    hideProcessMsg();
						}
					});
				}
			});
		}
	};
	
	//密码键盘绑定
	var acceptMenuP = {
        id: 'acceptButtonP',
		text: '绑定键盘',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			
			var termInfo = grid2.getSelectionModel().getSelected();
			var pinInfo = grid3.getSelectionModel().getSelected();
			
			
			var term = termInfo.get('termId');
			var pinId = pinInfo.get('termId');
			var pinBindFlag = termInfo.get('equipInvId');
			var managementBindFlag = termInfo.get('manaBind');

			if(termInfo == null){
				showAlertMsg('请选择终端',mainView);
			}
			
			if(managementBindFlag == 'N'){
				showAlertMsg('终端已经绑定内置键盘的机具，无法再绑定密码键盘！',mainView);
				return;
			}
			
			T30305.isExistPinPad(pinId,term,function(ret){
				if(ret == "02"){
					showAlertMsg('该密码键盘已经绑定其他终端,请先解绑后再继续！',mainView);
				}
				if(ret == "00"){
					showConfirm('确定绑定？',mainView,
					function(bt) {
						if(bt == "yes") {
							showProcessMsg('正在绑定，请稍后......');
							
							Ext.Ajax.request({
		                        url: 'T30305Action.asp?method=pinPadBind',
		                        waitMsg: '正在提交，请稍后......',
		                        success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,mainView);
									} else {
										showErrorMsg(rspObj.msg,mainView);
									}
									// 重新加载信息
		                            grid2.getStore().reload();
		                            grid3.getStore().reload();
		                            grid3.getTopToolbar().items.items[0].disable();
								},
		                        params: {
		                        	txnId: '30305',
		                            subTxnId: '01',
	                                termId: term,
	                                pinId: pinId
		                        }
		                    });
		                    hideProcessMsg();
						}
					});
				}
			});
		}
	};
    
    var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 360,
        autoHeight: true,
        labelWidth: 120,
        items: [new Ext.form.TextField({
				id: 'termNo',
                name: 'termNo',
                fieldLabel: '机具库存编号',
				width: 200
			}),new Ext.form.TextField({
				fieldLabel: '机具序列号',
                name: 'pcd',
                id: 'pcd',
				width: 200
			}),{
				fieldLabel: '申请单号',
				xtype: 'combo',
				store: batchStore,
                name: 'batchNo',
                id: 'batchNo',
				width: 200
			}
			],
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
    
    var topQueryPanel3 = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 360,
        autoHeight: true,
        labelWidth: 120,
        items: [new Ext.form.TextField({
				id: 'termNoT',
                name: 'termNoT',
                fieldLabel: '键盘编号',
				width: 200
			}),new Ext.form.TextField({
				fieldLabel: '键盘序列号',
                name: 'pcdT',
                id: 'pcdT',
				width: 200
			}),{
				fieldLabel: '申请单号',
				xtype: 'combo',
				store: batchStore,
                name: 'batchNoT',
                id: 'batchNoT',
				width: 200
			}
			],
        buttons: [{
			text: '查询',
			handler: function() 
			{
				pinStore.load();
                queryWin3.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel3.getForm().reset();
			}
		}]
	});
	
     var queryWin3 = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel3],
        buttonAlign: 'center',
        closeAction: 'hide',
        resizable: false,
        closable: true,
        animateTarget: 'query3',
        tools: [{
            id: 'minimize',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.maximize.show();
                toolEl.hide();
                queryWin3.collapse();
                queryWin3.getEl().pause(1);
                queryWin3.setPosition(10,Ext.getBody().getViewSize().height - 30);
            },
            qtip: '最小化',
            hidden: false
        },{
            id: 'maximize',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.minimize.show();
                toolEl.hide();
                queryWin3.expand();
                queryWin3.center();
            },
            qtip: '恢复',
            hidden: true
        }]
    });
	
    
     var queryMenu1 = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    
    var queryMenu3 = {
        text: '录入查询条件',
        width: 85,
        id: 'query3',
        iconCls: 'query',
        handler:function() {
            queryWin3.show();
        }
    };
    
    
	var menuArr1 = new Array();
	var menuArr3 = new Array();
    menuArr1.push(acceptMenu);	
    menuArr1.push('-');	
    menuArr1.push(queryMenu1);
    
    menuArr3.push(acceptMenuP);	
    menuArr3.push('-');	
    menuArr3.push(queryMenu3);
	// 机具信息列表
	var grid = new Ext.grid.GridPanel({
		title: '机具库存信息',
		region: 'center',
		iconCls: 'pos',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		tbar: menuArr1,
		forceValidation: true,
		plugins: [termRowExpander],
		loadMask: {
			msg: '正在加载机具信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	var grid3 = new Ext.grid.GridPanel({
		title: '密码键盘库存信息',
		region: 'east',
		iconCls: 'pos',
		width: 480,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: pinStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: pinColModel,
		tbar: menuArr3,
		forceValidation: true,
		plugins: [termRowExpander3],
		loadMask: {
			msg: '正在加载机具信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: pinStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
   
	
	 var topQueryPanel2 = new Ext.form.FormPanel({
    	frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 120,
        items: [new Ext.form.TextField({
				id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号',
				width: 300
			}),{
        		xtype: 'dynamicCombo',
	        	methodName: 'getMchntNo',
                fieldLabel: '商户号',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                displayField: 'displayField',
                valueField: 'valueField',
                width: 300
        	},{
        		width: 300,
                xtype: 'datefield',
                fieldLabel: '入网起始时间',
                format : 'Y-m-d',
                name :'startTime',
                id :'startTime',
                anchor :'60%'   
       		},{
       			width: 300,
                xtype: 'datefield',
                fieldLabel: '入网截止时间',
                format : 'Y-m-d',
                name :'endTime',
                id :'endTime',
                anchor :'60%' 
       		}],
			buttons: [{
			text: '查询',
			handler: function() 
			{
				termInfoStore.load();
                queryWin2.hide();
			}
		},{
			text: '重填',
			handler: function() {
				topQueryPanel2.getForm().reset();
			}
		}]
    });
	 
    
     var queryWin2 = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel2],
        buttonAlign: 'center',
        closeAction: 'hide',
        resizable: false,
        closable: true,
        animateTarget: 'query2',
        tools: [{
            id: 'minimize2',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.maximize2.show();
                toolEl.hide();
                queryWin2.collapse();
                queryWin2.getEl().pause(1);
                queryWin2.setPosition(10,Ext.getBody().getViewSize().height - 30);
            },
            qtip: '最小化',
            hidden: false
        },{
            id: 'maximize2',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.minimize2.show();
                toolEl.hide();
                queryWin2.expand();
                queryWin2.center();
            },
            qtip: '恢复',
            hidden: true
        }]
    });
    
    var queryMenu2 = {
        text: '录入查询条件',
        width: 85,
        id: 'query2',
        iconCls: 'query',
        handler:function() {
            queryWin2.show();
        }
    };
    
	var menuArr2 = new Array();
    menuArr2.push(queryMenu2);	
    var grid2 = new Ext.grid.GridPanel({
        title: '终端信息',
        region: 'south',
        iconCls: 'pos',
        frame: true,
        height: 200,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: termInfoStore,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        cm: termInfoColModel,
        tbar: menuArr2,
        forceValidation: true,
        loadMask: {
            msg: '正在加载终端信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: termInfoStore,
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
			rec1 = grid.getSelectionModel().getSelected();
			rec2 = grid2.getSelectionModel().getSelected();
			
			if(rec1 != null && rec2 != null)  {
				grid.getTopToolbar().items.items[0].enable();
			}else {
				grid.getTopToolbar().items.items[0].disable();
			}
		}
	});
	
    grid2.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid2.getView().getRow(grid2.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec1 = grid.getSelectionModel().getSelected();
			rec2 = grid2.getSelectionModel().getSelected();
			rec3 = grid3.getSelectionModel().getSelected();
			
					
			if(rec1 != null && rec2 != null)  {
				grid.getTopToolbar().items.items[0].enable();
			}else {
				grid.getTopToolbar().items.items[0].disable();
			}
			
			if(rec2 != null && rec3 != null)  {
				grid3.getTopToolbar().items.items[0].enable();
			}else {
				grid3.getTopToolbar().items.items[0].disable();
			}

			
		}
	});
	
	grid3.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid3.getView().getRow(grid3.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用

			rec2 = grid2.getSelectionModel().getSelected();
			rec3 = grid3.getSelectionModel().getSelected();
			
			if(rec2 != null && rec3 != null)  {
				grid3.getTopToolbar().items.items[0].enable();
			}else {
				grid3.getTopToolbar().items.items[0].disable();
			}
	
			
		}
	});
	
    
    termInfoStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            startTime: Ext.getCmp('startTime').getValue(),
            endTime: Ext.getCmp('endTime').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue()
        });
        grid.getTopToolbar().items.items[0].disable();
    }); 
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termNo: Ext.getCmp('termNo').getValue(),
            productCd: Ext.getCmp('pcd').getValue(),
            batchNo: Ext.getCmp('batchNo').getValue()
        });
        grid.getTopToolbar().items.items[0].disable();
    });
    
    pinStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termNo: Ext.getCmp('termNoT').getValue(),
            productCd: Ext.getCmp('pcdT').getValue(),
            batchNo: Ext.getCmp('batchNoT').getValue()
        });
        grid.getTopToolbar().items.items[0].disable();
    });
    
	var mainView = new Ext.Viewport({
        title: '机具绑定',
		layout: 'border',
		items: [grid3,grid,grid2],
		renderTo: Ext.getBody()
	});
    
});