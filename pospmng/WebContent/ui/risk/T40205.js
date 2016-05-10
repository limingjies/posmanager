Ext.onReady(function() {
	
	
	var riskDataStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskData'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instDateTime',mapping: 'instDateTime'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'riskDate',mapping: 'riskDate'},
			{name: 'riskId',mapping: 'riskId'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'transState',mapping: 'transState'},
			{name: 'pan',mapping: 'pan'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'respCode',mapping: 'respCode'}
		])
	});
	
	riskDataStore.load({
		params:{start: 0}
	});
	
	
	
	
	var riskDataColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '统计日期',dataIndex: 'riskDate',align: 'center',renderer: formatDt,width: 90},
		{header: '触发风控',dataIndex: 'riskId',width: 130,renderer:riskIdName},
		{header: '风控等级',dataIndex: 'riskLvl',align: 'center',width: 70},
		{header: '交易类型',dataIndex: 'txnName',width: 100},
		{header: '交易日期',dataIndex: 'instDate',width: 80,align: 'center',renderer: formatDt},
		{header: '交易时间',dataIndex: 'instDateTime',width: 80,align: 'center',renderer: formatDt},
		{id:'cardAccpId',header: '商户号',dataIndex: 'cardAccpId',width: 250},
		{header: '所属机构',dataIndex: 'bankName',width: 250},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 80},
		{header: '交易卡号',dataIndex: 'pan',align: 'center',width: 150},
		{header: '系统流水号',dataIndex: 'sysSeqNum',align: 'center'},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right'},
		{header: '交易结果',dataIndex: 'transState',renderer: txnSta,align: 'center'},
		{header: '交易应答',dataIndex: 'respCode',width: 150}
		
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'风控类型：',{
					xtype: 'basecomboselect',
					id: 'queryRiskIds',
					baseParams: 'KIND',
					hiddenName: 'queryRiskId',
					width: 140
				},'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'idqueryTxnName',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnName',
					width: 140
				},	'-','商户编号：',
					{
					xtype: 'basecomboselect',
					baseParams: 'MCHNT_NO',
					hiddenName: 'queryCardAccpId',
//					hidden:true,
					width: 250,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
				}
	            ]  
         }) 
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','金额下限：',{
					xtype: 'textfield',
					id: 'queryAmtTransLow',
					name: 'queryAmtTransLow',
					vtype:'isMoney',
					width: 120
	               	},'-','金额上限：',{
					xtype: 'textfield',
					id: 'queryAmtTransUp',
					name: 'queryAmtTransUp',
					vtype:'isMoney',
					width: 120
	               	},'-','统计起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					endDateField: 'endDate',
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','统计结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					startDateField: 'startDate',
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	}
	            ]  
         }) 
         
          var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		
                '-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					 vtype: 'isNumber',
					width: 120
				
				},'-','交易卡号：',{
					xtype: 'textfield',
					id: 'queryPan',
					 vtype: 'isNumber',
					name: 'queryPan',
					width: 120
				},'-','系统流水：',{
					xtype: 'textfield',
					id: 'querySysSeqNum',
					 vtype: 'isNumber',
					name: 'querySysSeqNum',
					width: 120
				}
				 ,'-','交易结果：',{
					xtype: 'basecomboselect',
					id: 'idqueryTransState',
					baseParams: 'TXN_STATUS',
					hiddenName: 'queryTransState',
					width: 120
				}     
				 ,'-','风控等级：',{
					xtype: 'basecomboselect',
					id: 'idriskLvl',
					baseParams: 'RISL_LVL',
					hiddenName: 'queryRiskLvl',
					width: 120
				}
				]  
         }) 
	
	
	var riskDataGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'cardAccpId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: riskDataStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskDataColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '查看商户详细信息',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			disabled: true,
			width: 100,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				var mchtName=riskDataGrid.getSelectionModel().getSelected().get('cardAccpId');
				showMchntDetailS(mchtName.substring(0,mchtName.indexOf('-')).trim(),riskDataGrid);
			}},'-',{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",riskDataGrid);
				Ext.Ajax.request({
					url: 'T40205Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
						
						queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
						queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),

						queryTxnName: Ext.get('queryTxnName').getValue(),
						
						queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
						queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
						
						querySysSeqNum: Ext.getCmp('querySysSeqNum').getValue(),
						
						queryPan: Ext.getCmp('queryPan').getValue(),
						queryTransState: Ext.get('queryTransState').getValue(),
						queryRiskId: Ext.get('queryRiskId').getValue(),
						queryRiskLvl:Ext.get('queryRiskLvl').getValue(),
						
						txnId: '40205',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						hideMask();
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,riskDataGrid);
						}
					},
					failure: function(){
						hideMask();
						showErrorMsg(rspObj.msg,riskDataGrid);
					}
				});
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
					riskDataStore.load();
				}
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryCardAccpId').setValue('');
				
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('queryPan').setValue('');
				Ext.getCmp('querySysSeqNum').setValue('');
				Ext.getCmp('queryAmtTransLow').setValue('');
				Ext.getCmp('queryAmtTransUp').setValue('');
				
				Ext.getCmp('idqueryTxnName').setValue('');
				Ext.getCmp('idqueryTransState').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				Ext.getCmp('queryRiskIds').setValue('');
				Ext.getCmp('idriskLvl').setValue('');
			}
		

		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
            	tbar1.render(this.tbar);  
					tbar2.render(this.tbar); 
		    	  	tbar3.render(this.tbar);  
					tbar2.render(this.tbar); 
                },
             'cellclick':selectableCell,
        }  ,
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: riskDataStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'

			
		})
//		renderTo: Ext.getBody()
	});
	
	riskDataGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(riskDataGrid.getView().getRow(riskDataGrid.getSelectionModel().last)).frame();
			//激活菜单
			Ext.getCmp('detail').enable();
		}
	});
	

	
	riskDataStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			
			queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
			queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),

			queryTxnName: Ext.get('queryTxnName').getValue(),
			
			queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			
			querySysSeqNum: Ext.getCmp('querySysSeqNum').getValue(),
			
			queryPan: Ext.getCmp('queryPan').getValue(),
			queryTransState: Ext.get('queryTransState').getValue(),
			queryRiskId: Ext.get('queryRiskId').getValue(),
			queryRiskLvl:Ext.get('queryRiskLvl').getValue()
			
		});
		Ext.getCmp('detail').disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [riskDataGrid],
		renderTo: Ext.getBody()
	});
});