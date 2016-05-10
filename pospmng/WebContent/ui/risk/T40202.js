Ext.onReady(function() {
	
	// 风险交易数据集
	var riskTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskModelInfoUpdLog'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saModelconn',mapping: 'saModelconn'},
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saModelKindName',mapping: 'saModelKindName'},
			{name: 'saFieldName',mapping: 'saFieldName'},
			{name: 'saFieldValueBF',mapping: 'saFieldValueBF'},
			{name: 'saFieldValue',mapping: 'saFieldValue'},
			{name: 'modiOprId',mapping: 'modiOprId'},
			{name: 'modiTime',mapping: 'modiTime'},
			{name: 'modiDate',mapping: 'modiDate'}
		]),
	autoLoad: true
	}); 
	
	var riskColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '模型关联项',dataIndex: 'saModelconn',align: 'center',width: 120,renderer:saModelconn},
		{header: '监控模型',dataIndex: 'saModelKind',align: 'center',width: 80},
		{header: '模型类别',dataIndex: 'saModelKind',id:'saModelKindName',renderer:riskName},
		{header: '修改属性',dataIndex: 'saFieldName',width: 200},
		{header: '原值',dataIndex: 'saFieldValueBF',width:120,renderer: saBeUse},
		{header: '修改值',dataIndex: 'saFieldValue',width: 120,renderer: saBeUse},
		{header: '修改人',dataIndex: 'modiOprId',width: 100,align: 'center'},
		{header: '修改日期',dataIndex: 'modiDate',width: 120,renderer: formatDt,align: 'center'},
		{header: '修改时间',dataIndex: 'modiTime',width: 120,renderer: formatDt,align: 'center'}
	]);
	
	
	
	function saBeUse(val) {
		switch(val) {
			case '启用' : return '<font color="green">启用</font>';
			case '停用' : return '<font color="red">停用</font>';
			case '低' : return '<font color="green">低</font>';
			case '中' : return '<font color="green">中</font>';
			case '高' : return '<font color="green">高</font>';
			default: return val;
		}
	}
	
	
	
	 
	
	var tbar2 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','模型关联项：',{
			xtype: 'basecomboselect',
			id: 'querySaModelconnId',
			baseParams: 'SA_MODEL_CONN',
			hiddenName: 'querySaModelconn',
			width: 140
		},'-','风控类型：',{
			xtype: 'basecomboselect',
			id: 'queryRiskIds',
			baseParams: 'KIND',
			hiddenName: 'queryRiskId',
			width: 140
		},'-','起始日期：',{
       		xtype: 'datefield',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			editable: false,
			id: 'startDate',
			name: 'startDate'
           	},'-','结束日期：',{
			xtype: 'datefield',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			editable: false,
			id: 'endDate',
			name: 'endDate'
       	}]  
    }) 
	
	// 风险交易监控
	var grid = new Ext.grid.GridPanel({
		title: '风控模型修改记录',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelKindName',
		store: riskTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风控模型修改记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				riskTxnStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				Ext.getCmp('queryRiskIds').setValue('');
				Ext.getCmp('querySaModelconnId').setValue('');
			}

		
		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: riskTxnStore,
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
		}
	});
	
	
	riskTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			saModelconn:Ext.get('querySaModelconn').getValue(),
			saModelKind:Ext.get('queryRiskId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});