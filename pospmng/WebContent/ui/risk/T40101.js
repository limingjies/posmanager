Ext.onReady(function() {
	var curDate = new Date();
	
	// 数据集
	var runRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=runRisk'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'transType',mapping: 'transType'},
			{name: 'transTpName',mapping: 'transTpName'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'risInfoTriggered',mapping: 'risInfoTriggered'}
		]),
	autoLoad: true
	}); 
	
	var runRiskColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '终端号',dataIndex: 'termId',align: 'center',width: 80},
		{header: '商户号',dataIndex: 'mchtId',align: 'center',width: 120 },
		{header: '商户名称',dataIndex: 'mchtNm',align: 'left',width:220},
		//{header: '交易类型',dataIndex: 'transType',width: 100,align: 'center',renderer:routeTxnTp},
		{header: '交易类型',dataIndex: 'transTpName',width: 100,align: 'center',},
		{header: '卡类型',dataIndex: 'cardType',width: 60,align: 'center',renderer:routeCardTp},
		{header: 'MCC',dataIndex: 'mcc',width: 160,align: 'left'},
		{header: '交易金额',dataIndex: 'amtTrans',width: 100 ,align: 'right'},
		{header: '交易时间',dataIndex: 'instDate',width: 130 ,align: 'center',renderer:formatDt},
		{header: '风控规则',dataIndex: 'risInfoTriggered',width: 160,align: 'left'}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['-','终端号：',{
						xtype : 'textfield',
	 					hiddenName: 'termId',
	 					width: 150,
	 					id: 'queryTermId',
	 					editable: true,
	 					lazyRender: true
				   },'-','商户号：',{
						xtype: 'dynamicCombo',
						methodName: 'getMchntIdRoute',
						hiddenName: 'mchtId',
						id: 'queryMchtIdId',
						width: 300,
						lazyRender: true
					},'-',	'交易类型：',{
						xtype: 'basecomboselect',
						id: 'queryTransTypeId',
						baseParams: 'routeTxnTp',
						hiddenName: 'transType',
						editable: true,
						width: 140
					},'-','卡类型：',{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['*','无限制'],['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡']],
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						editable: true,
						emptyText: '请选择',
						hiddenName: 'cardType',
						id:'queryCardTpId',
						width: 140
					}]  
         }) 
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[	'-','MCC：',{
				  			xtype: 'dynamicCombo',
				  			methodName: 'getMchtMcc',
               				id: 'queryMccId',
               				name: 'mchtIdUp',
				  			mode:'local',
				  			triggerAction: 'all',
				  			editable: true,
				  			lazyRender: true,
				  			width: 240
				   },'-','开始日期：',{
	               		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
						editable: false,
						//value:timeYesterday,
						id: 'startDate',
						name: 'startDate',
	 					width: 100
	               	},'-','截止日期：',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
						vtype: 'daterange',
						startDateField: 'startDate',
						editable: false,
						value:curDate,
						id: 'endDate',
						name: 'endDate',
	 					width: 100
	               	}
	            ]  
         }) 
	
	
	var grid = new Ext.grid.GridPanel({
		title: '事中风控信息查询',
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: runRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: runRiskColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载风控信息列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				runRiskStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryTermId').setValue('');
				Ext.getCmp('queryMchtIdId').setValue('');
				Ext.getCmp('queryTransTypeId').setValue('');
				Ext.getCmp('queryCardTpId').setValue('');
				Ext.getCmp('queryMccId').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				runRiskStore.load();
//				Ext.getCmp('queryMchtIdId').getStore().reload();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  ,
            'cellclick':selectableCell,
        },
		bbar: new Ext.PagingToolbar({
			store: runRiskStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
//		Ext.getCmp('detail').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
//			Ext.getCmp('detail').enable();
		}
	});
	
	
	runRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryTermId : Ext.getCmp('queryTermId').getValue(),
			queryMchtIdId : Ext.getCmp('queryMchtIdId').getValue(),
			queryTransTypeId : Ext.getCmp('queryTransTypeId').getValue(),
			queryCardTpId : Ext.getCmp('queryCardTpId').getValue(),
			queryMccId : Ext.getCmp('queryMccId').getValue(),
			startDate : Ext.getCmp('startDate').getValue(),
			endDate : Ext.getCmp('endDate').getValue()
				
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});