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
        width: 350,
        buttonAlign: 'center',
        autoHeight: true,
        labelWidth: 100,
		items: [new Ext.form.TextField({
				id: 'qryTermNo',
				name: 'qryTermNo',
				width: 150,
				fieldLabel: '终端库存编号'
			}),new Ext.form.TextField({
                id: 'qryBatchNo',
                name: 'qryBatchNo',
                width: 150,
                fieldLabel: '批次号'
            }),{											
				xtype: 'combo',
				fieldLabel: '库存状态',
				id: 'qryState',
				name: 'qryState',
				width: 150,
				store: termStateStore
		    },{
				width: 150,
				xtype: 'datefield',
				fieldLabel: '起始时间',
				format : 'Y-m-d',
				name :'startTime',
				id :'startTime'
		   },{											
				width: 150,
				xtype: 'datefield',
				fieldLabel: '截止时间',
				format : 'Y-m-d',
				name :'endTime',
				id :'endTime'
//				anchor :'60%'		
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
			url: 'gridPanelStoreAction.asp?storeId=termManagementInfo'
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
            {name: 'misc',mapping: 'misc'}
		])
	});
	
	var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>入库操作员：{storOprId}</p>',
			'<p>入库时间：{storDate}</p>'
			/*'<p>出库操作员：{reciOprId}</p>',
			'<p>出库时间：{reciDate}</p>',
			'<p>绑定操作员：{bankOprId}</p>',
			'<p>绑定时间：{bankDate}</p>',
			'<p>作废操作员：{invalidOprId}</p>',
			'<p>作废时间：{invalidDate}</p>',
			'<p>签收操作员：{signOprId}</p>',
			'<p>签收时间：{signDate}</p>'*/
		)
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		termRowExpander,
		{id: 'termId',header: '机具编号',dataIndex: 'termId',width: 100},
        {header: '批次号',dataIndex: 'batchNo',width: 150},
		{header: '机具状态',dataIndex: 'state',renderer: translateState},
		{header: '机具厂商',dataIndex: 'manufacturer',width: 150},
		{header: '机具序列号',dataIndex: 'productCd'},
		{header: '机具型号',dataIndex: 'terminalType'},
		{header: '机具类型',dataIndex: 'termType',renderer: termType},
//		{header: '所属商户号',dataIndex: 'mchnNo',width: 250,renderer:function(val){return getRemoteTrans(val, "mchntName")}},
//        {header: '所属商户号',dataIndex: 'mchnNo',width: 250},
        {header: '备注',dataIndex: 'misc'}
	]);
	
	
	var addMenu = {
		text: '入库',
		width: 85,
		iconCls: 'add',
		handler:function() {
			termWin.show();
			termWin.center();
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
        width: 350,
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
	
	menuArr.push(addMenu);		//[0]
	menuArr.push('-');		//[1]
	menuArr.push(queryMenu);
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '机具入库',
		region:'center',
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		plugins: termRowExpander,
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
	
	grid.getTopToolbar().items.items[1].disable();

	//每次在列表信息加载前都将保存按钮屏蔽
	termStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
			termNo: Ext.getCmp('qryTermNo').getValue(),
		    state: Ext.getCmp('qryState').getValue(),
            batchNo: Ext.getCmp('qryBatchNo').getValue(),
		    startTime: Ext.getCmp('startTime').getValue(),
		    endTime: Ext.getCmp('endTime').getValue()
		});
	});
	termStore.load();
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			
		}
	});
	
	// 终端厂商
	var manufacturerStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MANUFACTURER',function(ret){
		manufacturerStore.loadData(Ext.decode(ret));
	});
	var terminalTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	
	
	
	
	//添加终端表单
	var termForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		labelWidth: 160,
		waitMsgTarget: true,
		layout: 'column',
		items: [
		    	{
		 	       layout: 'form',
		 	       width: 389,
		 	       items: [{
		 		        	xtype: 'combo',
		 					fieldLabel: '机具厂商*',
		 					name: 'manufacturerNew',
		 					hiddenName: 'manufacturerNew',
		 					id: 'manufacturerNewId',
		 					store: manufacturerStore,
		 					displayField: 'displayField',
		 					valueField: 'valueField',
		 					allowBlank: false,
		 					blankText: '机具厂商不能为空',
	                        listeners:{
	                            'select': function() {
		 	    	                Ext.getCmp('terminalTypeNewId').setValue('');
		                            SelectOptionsDWR.getComboDataWithParameter('TERMINALTYPE',Ext.getCmp('manufacturerNewId').getValue(),function(ret){
		                                terminalTypeStore.loadData(Ext.decode(ret));
		                            });
	                            }
	                        }
		 		        }]
		 		},{
		 			layout: 'form',
		 			width: 389,
		         	items: [{
		 	        	xtype: 'combo',
		 				fieldLabel: '机具型号*',
		 				id: 'terminalTypeNewId',
		 				hiddenName: 'terminalTypeNew',
		 				allowBlank: false,
		 				name: 'terminalTypeNew',
		 				store: terminalTypeStore,
		 				displayField: 'displayField',
		 				valueField: 'valueField',
		 				blankText: '机具型号不能为空'
		 	        }]
		         },{
		        	 layout: 'form',
			 			width: 389,
			         	items: [{
			 	        	xtype: 'combo',
			 				fieldLabel: '机具类型*',
			 				hiddenName: 'termTypeNew',
			 				allowBlank: false,
			 				id: 'termTypeN',
			 				store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['P','POS'],['E','EPOS']]
							}),
			 				blankText: '机具类型不能为空'
			 	        }]
		         },{
			         	layout: 'form',
			         	width: 389,
			         	items: [{	
			         		xtype: 'textfield',
			 				fieldLabel: '入库数量*',
			 				id: 'number',
			 				name: 'number',
                            vtype: 'isNumber',
                            maxLength: 3,
			 				allowBlank: false,
			 				blankText: '入库数量不能为空',
			 				listeners: {
			         		change: function ()
			 				{	
			         			var number = Ext.getCmp('number').getValue();
			         			if(number >= 2)
			         			{
			         				Ext.getCmp('flagBox1').show();
			         		        Ext.getCmp('flagBox2').hide();
			         			}else
			         			{
			         				Ext.getCmp('flagBox1').hide();
                                    Ext.getCmp('flagBox2').show();
			         			}
			         			
			 				}
			 				}
			 	        }]
			         },{
		         	layout: 'form',
		         	width: 389,
                    id: 'flagBox1',
                    hidden: true,
		         	items: [{	
		         		xtype: 'textfield',
		 				fieldLabel: '起始机具序列号*',
		 				id: 'startProductCd',
		 				name: 'startProductCd',
		 				maxLength: 40,
                        vtype: 'isNumber',
		 				blankText: '起始机具序列号不能为空'
		 	        }
//		 	        ,{	
//		 	        	fieldLabel: '结束终端序列号',
//		 	        	xtype: 'textfield',
//		 				id: 'endProductCd',
//                        name: 'endProductCd',
//                        maxLength: 40,
//                        vtype: 'isNumber',
//                        blankText: '结束终端序列号不能为空'
//		 	        }
		 	        ]
		         },{
                    layout: 'form',
                    width: 389,
                    id: 'flagBox2',
                    items: [{   xtype: 'textfield',
                        fieldLabel: '机具序列号*',
                        id: 'productCdNew',
                        name: 'productCdNew',
                        maxLength: 40,
                        vtype: 'isAlphanum',
                        blankText: '机具序列号不能为空'
                    }]
                  },{
		         	layout: 'form',
		         	width: 389,
		         	items: [{	
		         		xtype: 'textarea',
		 				fieldLabel: '备注描述',
		 				id: 'miscNew',
		 				name: 'miscNew',
		 				width: 200,
		 				maxLength: 40
		 			}]
		         }
		]
	});

	var terminalType = Ext.getCmp('terminalTypeNewId').getValue();
	var manufacturer = Ext.getCmp('manufacturerNewId').getValue();


	//终端信息窗口
	var termWin = new Ext.Window({
		title: '终端入库',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 500,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(termForm.getForm().isValid()) {
					termForm.getForm().submitNeedAuthorise({
						url: 'T30301Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
						showSuccessMsg(action.result.msg,grid);
							termWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '30301',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
					termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
			}
		}]
	});
	
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
})