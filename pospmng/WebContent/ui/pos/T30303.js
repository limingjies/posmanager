Ext.onReady(function() {
    
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
	
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
        border: true,
        width: 360,
        autoHeight: true,
        labelWidth: 100,
//        buttonAlign: 'center',
		items: [new Ext.form.TextField({
				id: 'appNoQ',
				name: 'appNoQ',
				width: 220,
				fieldLabel: '申请单编号'
			}),
//				{											
//				xtype: 'combo',
//				fieldLabel: '申请状态',
//				id: 'stateQ',
//				hidden:true,
////				name: 'state',
////				width: 200,
//				store: new Ext.data.ArrayStore({
//	                 fields: ['valueField','displayField'],
//	                 data: [['0','0-未审核'],['1','1-已审核'],['2','2-已取消']]
//	                // ['3','3-部分通过待下发'],['4','4-拒绝退回'],['5','5-通过已下发'],['6','6-部分通过已下发']]
//	            })
//		  },
		  	{
				xtype: 'dynamicCombo',
				fieldLabel: '申请机构',
				methodName: 'getBranchId',
				id: 'brhIdQ',
				editable: true,
				width: 220
			},{
				width: 220,
				xtype: 'datefield',
				fieldLabel: '申请起始时间',
				format : 'Y-m-d',
				name :'startTimeQ',
				id :'startTimeQ'	
		  },{											
				width: 220,
				xtype: 'datefield',
				fieldLabel: '申请截止时间',
				format : 'Y-m-d',
				name :'endTimeQ',
				id :'endTimeQ'	
		}],
		buttons: [{
			text: '查询',
			handler: function() 
			{
				gridStore2.load();
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
			url: 'gridPanelStoreAction.asp?storeId=termManagementCheckInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'appIdS',mapping: 'appId'},
			{name: 'sonAppIdS',mapping: 'sonAppId'},
			{name: 'manufaturerS',mapping: 'manufaturer'},
			{name: 'terminalTypeS',mapping: 'terminalType'},
			{name: 'termTypeS',mapping: 'termType'},
			{name: 'amountS',mapping: 'amount'},
			{name: 'accAmountS',mapping: 'accAmount'},
			{name: 'statS',mapping: 'stat'},
			{name: 'micS',mapping: 'mic'},
			{name: 'misc2S',mapping: 'misc2'}
		])
//		autoLoad: true
	});
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    appNo: gridPanel2.getSelectionModel().getSelected().get('appId')
		});
	});
	
	
	
	var Fee = Ext.data.Record.create([{
//        name: 'terminalTypeS3',
        type: 'string'
	},{
//        name: 'terminalTypeS3',
        type: 'string'
//        id: 'terminalTypeS3'
    }, {
//        name: 'termTypeS3',
        type: 'string'
    }, {
//        name: 'misc2S3',
        type: 'string'
    },{
//        name: 'amountS3',
        type: 'int'
    },{
//        name: 'micS3',
        type: 'string'
    }]);
	
	var store3 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementCheckInfo3s'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'appIdS3',mapping: 'appId'},
			{name: 'sonAppIdS3',mapping: 'sonAppId'},
			{name: 'manufaturerS3',mapping: 'manufaturer'},
			{name: 'terminalTypeS3',mapping: 'terminalType'},
			{name: 'termTypeS3',mapping: 'termType'},
			{name: 'amountS3',mapping: 'amount'},
			{name: 'accAmountS3',mapping: 'accAmount'},
			{name: 'statS3',mapping: 'stat'},
			{name: 'micS3',mapping: 'mic'},
			{name: 'misc2S3',mapping: 'misc2'}
		])
//		autoLoad: true
	});
	store3.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
	});
	
	
	var cm3 = new Ext.grid.ColumnModel({
		 columns: [
		new Ext.grid.RowNumberer(),
		 {header: '申请单编号',dataIndex: 'appIdS3',width: 100,hidden:true},
	  {header: '子申请单编号',dataIndex: 'sonAppIdS3',width: 100},
     {header: '机具厂商',dataIndex: 'manufaturerS3',width: 140},
	  {header: '机具型号',dataIndex: 'terminalTypeS3',width: 90},
	  {header: '机具类型',dataIndex: 'termTypeS3',renderer:termType,width: 80},
	  {header: '键盘连接',dataIndex: 'misc2S3',hidden:true,renderer:termPadType},
	  {header: '申请数量',dataIndex: 'amountS3',width: 80},
	  {header: '审核状态',dataIndex: 'statS3',renderer: appStatus,width: 100,
	  			editor:{
					xtype: 'basecomboselect',
			       store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','通过'],['2','部分通过'],['3','拒绝']]
						}),
					id: 'idStatS3',
//					allowBlank: false,
					hiddenName: 'statS3'
//					listeners:{
//                        'select': function() {
//	                        var args = Ext.getCmp('idStatS3').getValue();
//	                        alert("ctz");
//	                        if(args != '2')
//	                        {
//	                        	alert("曹铁铮");
////	                        	cm3.findById('idAccAmountS3').setValue("");
//	                            Ext.getCmp("idAccAmountS3").setValue('-');
//                                Ext.getCmp('idAccAmountS3').readOnly = true;
//                            }
//                        }
//                  	}
		       }},
	  {header: '通过数量',dataIndex: 'accAmountS3',width: 80,
	  			 editor: new Ext.form.NumberField({
//                    allowBlank: false,
                    allowNegative: false,
                    id:'idAccAmountS3',
                    maxValue: 100000000
                })},
	  {id:'floorMount',header: '备注描述',dataIndex: 'micS3',width: 120,
	  			editor: new Ext.form.TextField({
//                    allowBlank: false,
                    allowNegative: false
//                    maxValue: 100000000
                })}
	]});
	
	
	var gridStore2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termManagementAppMainInfos'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'appId',mapping: 'appId'},
			{name: 'stat',mapping: 'stat'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhIdName',mapping: 'brhIdName'},
			{name: 'misc',mapping: 'misc'},
			{name: 'appOprId',mapping: 'app_opr_id'},
			{name: 'appDate',mapping: 'app_date'},
			{name: 'backOprId',mapping: 'back_opr_id'},
			{name: 'backDate',mapping: 'back_date'},
			{name: 'backOprMobile',mapping: 'backOprMobile'}
		])
//		autoLoad: true
	});
		
	
	var termRowExpander2 = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		
			'<table width=400>',
			'<tr><td><font color=green>申请操作员：</font> {appOprId}</td>',
//			'<td><font color=green>申请日期： </font>{appDate:this.date}</td></tr>',
			
			'<td><font color=green>审核操作员：</font>{backOprId}</td></tr>',
			'<tr><td><font color=green>审核日期：</font>{backDate:this.date}</td>',
			
			'<td><font color=green>审核操作员手机：</font>{backOprMobile}</td></tr>',
//			'<tr><td><font color=green>备注描述：</font>{misc}</td></tr>',
			'</table>',
			{
				date : function(val){return formatDt(val);}
			}
		)
	});
	var gridColumnModel2 = new Ext.grid.ColumnModel([
		termRowExpander2,
		new Ext.grid.RowNumberer(),
	  {header: '申请单编号',dataIndex: 'appId',width: 120},
	  {header: '申请日期',dataIndex: 'appDate',renderer:formatDt },
      {header: '申请状态',dataIndex: 'stat',width: 80,renderer:appSta},
	  {header: '申请机构',dataIndex: 'brhId',hidden:true},
	  {id: 'termId2',header: '申请机构',dataIndex: 'brhIdName'}
//	  {header: '备注描述',dataIndex: 'misc'}
//	  {header: '申请操作员',dataIndex: 'appOprId'},
//	  {header: '审核操作员',dataIndex: 'backOprId'},
//	   {header: '审核日期',dataIndex: 'backDate'},
//	   {header: '审核操作员手机',dataIndex: 'backOprMobile'}
	]);
	
	function appSta(val) {
		switch(val) {
			case '0' : return '<font color="red">未审核</font>';
			case '1' : return '<font color="green">已审核</font>';
			case '2' : return '<font color="gray">已取消</font>';
			default  : return val;
		}
	}
	


	var gridColumnModel = new Ext.grid.ColumnModel([
//		termRowExpander,
		new Ext.grid.RowNumberer(),
	  {header: '子申请单编号',dataIndex: 'sonAppIdS',width: 100},
	  {header: '审核结果',dataIndex: 'statS',renderer: appStatus,width: 100},
	  {header: '机具类型',dataIndex: 'termTypeS',renderer:termType},
      {header: '机具厂商',dataIndex: 'manufaturerS',width: 150},
	  {header: '机具型号',dataIndex: 'terminalTypeS',width: 110},
	  {header: '申请数量',dataIndex: 'amountS'},
	  {header: '通过数量',dataIndex: 'accAmountS'},
	  {header: '备注描述',dataIndex: 'micS',width: 120}
	]);
	
	function appStatus(val) {
		switch(val) {
			case '0' : return '未审核';
			case '1' : return '<font color="green">通过</font>';
			case '2' : return '<font color="green">部分通过</font>';
			case '3' : return '<font color="gray">拒绝</font>';
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
    

    
	var detailGrid = new Ext.grid.EditorGridPanel({
		region: 'center',
		height: 300,
//        title: '机具申请新增',
        store: store3,
        cm: cm3,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: 'floorMount', 
//        tbar: menuArrs,
        frame: true,
        clicksToEdit: 1
    });
	

	var detailWin = new Ext.Window({
		title: '机具审核',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 950,
		height: 280,
		autoHeight: true,
		items: [detailGrid],
		buttonAlign: 'center',
		closeAction: 'hide',
		animateTarget: 'adds',
//		closable: true,
		resizable: false,
		buttons: [{
			text: '保存',
			handler: function() {
				
				detailGrid.focus();
				detailGrid.stopEditing();
				var s,n;
				var dataArray = new Array();
				for(var i=0;i<store3.getCount();i++){
					var re = store3.getAt(i);
					
					if(re.data.statS3 == null ||re.data.statS3==''){showMsg("第" + (i+1) + "行的[审核状态]不能为空",detailGrid);return;}
//					var stat=re.data.statS3;
					if(re.data.statS3=='2'){
						if(re.data.accAmountS3 == null ||re.data.accAmountS3==''){showMsg("第" + (i+1) + "行的[通过数量]不能为空",detailGrid);return;}
						n=re.data.accAmountS3;
						if(Number(re.data.amountS3)<=Number(re.data.accAmountS3)){
								showMsg("第" + (i+1) + "行的[通过数量]值不正确",detailGrid);return;
						}
					}else{
						n="";
					}
					
					if(re.data.micS3 == null){
						s="";
					}else{
						s=re.data.micS3;
					}
					
					var data = {
							appIdS:re.data.appIdS3,
							sonAppIdS:re.data.sonAppIdS3,
							statS: re.data.statS3,
							accAmountS: n,
							micS: s
						};
						dataArray.push(data);
				}
				
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30303Action.asp?method=pass',
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,gridPanel2);
								detailWin.hide();
							} else {
								showErrorMsg(rspObj.msg,gridPanel2);
							}
							gridPanel2.getStore().reload();
							gridStore.removeAll();
//							gridStore.on('beforeload', function() {
//							    Ext.apply(this.baseParams, {
//								    start: 0,
//								    appNo: ''
//								});
//							});
//							gridPanel.getStore().reload();
//										gridPanel.getTopToolbar().items.items[0].disable();
							gridPanel2.getTopToolbar().items.items[2].disable();
						},
						params: { 
							txnId: '30303',
							subTxnId: '01',
							data: Ext.encode(dataArray)
						}
					});
			}
		},{
			text: '取消',
			handler: function() {
				detailWin.hide();
//				store3.removeAll();
//				store.insert(0, new Fee({
//    			}));
			}
		}]
	});
    
	var detail = {
		text: '机具审核',
		width: 85,
		id:'adds',
		iconCls: 'accept',
		handler:function() {
					store3.load({
						params: {
							start: 0,
							appNo: gridPanel2.getSelectionModel().getSelected().get('appId')
							}
					});
//					feeForm.getForm().findField("discCd").disable();
					detailWin.show();
			}
//		handler:function() {
//			store3.removeAll();
//			store3.insert(0, new Fee());
//			detailWin.show();
//			detailGrid.focus();
////			detailWin.show();
////			oprWin.center();
//		}
	};
    
    
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 450,
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
	
   
    
	//应用信息
	var gridPanel = new Ext.grid.GridPanel({
		title: '机具申请子订单查询',
//		region:'center',
		region: 'east',
		
		width: 650,
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
//		clicksToEdit: true,
		forceValidation: true,
//		tbar: menuArr,
//		renderTo: Ext.getBody(),
//		plugins: termRowExpander,
//		autoExpandColumn: 'termId',
		stripeRows: true,
		loadMask: {
			msg: '正在加载机具子订单信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
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
			// 根据商户状态判断哪个编辑按钮可用
//			rec = gridPanel.getSelectionModel().getSelected();
//			if(rec != '' && (rec.get('stat')=='0') ) {
//					gridPanel2.getTopToolbar().items.items[2].enable();
//			} else {
//				gridPanel2.getTopToolbar().items.items[2].disable();
//			}
		}
	});
	
	
	var menuArr = new Array();
//    menuArr.push(addMenu3);		// [0]
    menuArr.push(queryMenu);
	menuArr.push('-');			// [1]
 	menuArr.push(detail);
	//应用信息
	var gridPanel2 = new Ext.grid.GridPanel({
		title: '机具申请订单查询',
		region:'center',
//		region: 'west',
		iconCls: 'T30301',
		frame: true,
		border: true,
		columnLines: true,
		store: gridStore2,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel2,
//		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
//		renderTo: Ext.getBody(),
		plugins: termRowExpander2,
		autoExpandColumn: 'termId2',
		stripeRows: true,
		loadMask: {
			msg: '正在加载机具申请单信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore2,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridStore2.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0,
		    appNo: Ext.getCmp('appNoQ').getValue(),
//		    state: Ext.getCmp('stateQ').getValue(),
		    brhId: Ext.getCmp('brhIdQ').getValue(),
		    startDate: Ext.getCmp('startTimeQ').getValue(),
		    endDate: Ext.getCmp('endTimeQ').getValue()
		});
		gridPanel2.getTopToolbar().items.items[2].disable();
		gridStore.removeAll();
	});
	gridStore2.load();
	
	gridPanel2.getTopToolbar().items.items[2].disable();
	gridPanel2.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel2.getView().getRow(gridPanel2.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel2.getSelectionModel().getSelected();
			gridPanel.getStore().reload();
			if(rec != '' && (rec.get('stat')=='0') ) {
					gridPanel2.getTopToolbar().items.items[2].enable();
			} else {
				gridPanel2.getTopToolbar().items.items[2].disable();
			}
		}
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[gridPanel2,gridPanel]
	});
});