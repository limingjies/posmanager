Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime()-24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var riskAlarmLvl = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('ALARM_LVL',function(ret){
		riskAlarmLvl.loadData(Ext.decode(ret));
	});
	// 数据集
	var riskAlarmStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskAlarmT1'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'alarmSeq',mapping: 'alarmSeq'},
			{name: 'riskDate',mapping: 'riskDate'},
			{name: 'riskId',mapping: 'riskId'},
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'alarmLvl',mapping: 'alarmLvl'},
			{name: 'alarmSta',mapping: 'alarmSta'},
			{name: 'saModelDesc',mapping: 'saModelDesc'},
			{name: 'misc',mapping: 'misc'}
		]),
		autoLoad: true
	}); 
	
	var riskAlarmColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '警报级别',dataIndex: 'alarmLvl',align: 'center',width: 60,renderer:alarmLvl},
		{header: '警报编号',dataIndex: 'alarmId',align: 'center',width: 120},
		{header: '序列号',dataIndex: 'alarmSeq',align: 'center',width: 120},
		{header: '风控日期',dataIndex: 'riskDate',width: 100 ,renderer:formatDt},
		{header: '触发风控',dataIndex: 'riskId',width:130,renderer:riskIdName},
		{header: '规则说明',dataIndex: 'saModelDesc',id:'saModelDesc',width: 100,align: 'left'},
		{header: '处理状态',dataIndex: 'alarmSta',width: 100,align: 'center',renderer:alarmSta}
//		{header: '备注说明',dataIndex: 'txnAmt',width: 100 ,align: 'right'},
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeYesterday,
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	},'-',	'序列号：',{
					xtype: 'textfield',
					id: 'queryAlarmSeq',
					name: 'queryAlarmSeq',
					maxLength: 40,
					width: 120
				}
	            ]  
         }) 
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'警报级别：',{
					xtype: 'combo',
					store: riskAlarmLvl,
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryAlarmLvl',
					id:'queryAlarmLvlId',
					editable: false,
					emptyText: '请选择',
					width: 120
				},	'-','处理状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['00','未处理'],['01','处理中'],['02','已处理']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryAlarmSta',
					editable: false,
					emptyText: '请选择',
					id:'queryAlarmStaId',
					width: 120
				},'-',	'风控类型：',{
					xtype: 'basecomboselect',
					id: 'queryRiskIds',
					baseParams: 'KIND',
					hiddenName: 'queryRiskId',
					width: 120
				}
	            ]  
         }) 
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '风险警报',
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelDesc',
		store: riskAlarmStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskAlarmColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险警报信息列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				riskAlarmStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
				Ext.getCmp('queryAlarmStaId').setValue('');
				Ext.getCmp('queryAlarmLvlId').setValue('');
				Ext.getCmp('queryRiskIds').setValue('');
				Ext.getCmp('queryAlarmSeq').setValue(''),
				
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
				riskAlarmStore.load();
			}

		
		
		},'-',{
			
			xtype: 'button',
			text: '查看详情',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {
				window.location.href = Ext.contextPath + '/page/risk/T4021101.jsp?alarmId='+
				grid.getSelectionModel().getSelected().get('alarmId')+
				'&alarmSeq='+grid.getSelectionModel().getSelected().get('alarmSeq');
			}
		}],
		listeners : {     //将第二个bar渲染到tbar里面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: riskAlarmStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
		Ext.getCmp('detail').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
			Ext.getCmp('detail').enable();
		}
	});
	
	
	riskAlarmStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryAlarmSta: Ext.get('queryAlarmSta').getValue(),
			queryAlarmLvl:Ext.get('queryAlarmLvl').getValue(),
			queryRiskId:Ext.get('queryRiskId').getValue(),
			queryAlarmSeq:Ext.getCmp('queryAlarmSeq').getValue(),
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});