Ext.onReady(function() {
	
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	// 数据集
	var mappingHisStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMappingHis'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
//本地商户号、本地商户名称、本地商户地区、本地商户MCC、支付渠道、业务、性质、渠道商户号、渠道商户名称、映射状态、启用时间、停用时间
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'mchtArea',mapping: 'mchtArea'},
			{name: 'mchtMCC',mapping: 'mchtMCC'},
			{name: 'chlName',mapping: 'chlName'},
			{name: 'busiName',mapping: 'busiName'},
			{name: 'charName',mapping: 'charName'},
			{name: 'mchtIdUp',mapping: 'mchtIdUp'},
			{name: 'mchtNameUp',mapping: 'mchtNameUp'},
			{name: 'mapStatus',mapping: 'mapStatus'},
			{name: 'enDate',mapping: 'enDate'},
			{name: 'disDate',mapping: 'disDate'},
			{name: 'upBrhTermId',mapping: 'upBrhTermId'}
		]),
		autoLoad: true
	}); 
	
	var channelColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '本地商户号',dataIndex: 'mchtNo',align: 'center', width:100,id:"mapingHis"},
		{header: '本地商户名称',dataIndex: 'mchtName',align: 'center',width: 150},		
		{header: '本地商户地区',dataIndex: 'mchtArea',align: 'center', width: 150},
		{header: '本地商户MCC',dataIndex: 'mchtMCC',align: 'center', width: 150 },
		{header: '支付渠道',dataIndex: 'chlName',align: 'center', width: 100 },
		{header: '业务',dataIndex: 'busiName',align: 'center', width:100},
		{header: '性质',dataIndex: 'charName',align: 'center', width: 100 },
		{header: '渠道商户号',dataIndex: 'mchtIdUp',align: 'center',width: 100},
		{header: '渠道商户名称',dataIndex: 'mchtNameUp',align: 'center',width: 150},
		{header: '映射状态',dataIndex: 'mapStatus',align: 'center',width: 150,renderer:branchStatus},
		{header: '启用时间',dataIndex: 'enDate',align: 'center',width: 150, renderer:formatDt},
		{header: '停用时间',dataIndex: 'disDate',align: 'center',width: 150, renderer:formatDt},
		{header: '渠道终端号',dataIndex: 'upBrhTermId',align: 'center',width: 100}
	]);
	
	/*
	 * 查询条件
	 * 本地商户号、本地商户名称、本地商户地区、本地商户MCC、支付渠道、业务、性质（包含/不包含）、渠道商户号、渠道商户名称、映射状态（启用/停用）
	 */
	var tbar1 = new Ext.Toolbar({  
		items:[
		'-','本地商户：',{
			xtype: 'dynamicCombo',
			methodName: 'getMchntIdRoute',
			hiddenName: 'queryMchtId',
			id: 'queryMchtIdId',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 250
		},'-','本地商户地区：',{
			xtype: 'dynamicCombo',
			methodName: 'getAreaCode',
			editable: true,
			id: 'queryMchtAreaId',
			hiddenName:'queryMchtArea',
			width: 150
		},'-','本地商户MCC:',{
			xtype: 'basecomboselect',
			baseParams: 'MCC',
			editable: true,
			lazyRender: true,
			id: 'queryMchtMCCId',
			hiddenName:'queryMchtMCC',
			width: 250
		},'-',	'映射状态：',{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','启用'],['1','停用']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'queryMapStatus',
			id:'queryMapStatusId',
			editable: false,
			blankText: '请选择',
			value: "",
			width: 140
		}
        ]  
	}) 
	
	
	var tbar2 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[	
          '-','渠道商户：',{
			xtype: 'dynamicCombo',
			methodName: 'getUpbrhMcht',
			hiddenName: 'queryMchtUp',
			id: 'queryMchtUpId',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 250
          },'-','支付渠道:',{
            xtype: 'basecomboselect',
			baseParams: 'CHANNEL_ALL',
			fieldLabel: '支付渠道',
			hiddenName: 'queryChannel',
			id:'queryChannelId',
			width: 140,
			listeners: {
				'select': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('queryChannelId').getValue();
					Ext.getCmp('queryBusinessId').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
						busiStore.loadData(Ext.decode(ret));
					});
				},
				'change': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('queryChannelId').getValue();
					Ext.getCmp('queryBusinessId').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
						busiStore.loadData(Ext.decode(ret));
					});
				}
			}
		},'-','业务:',{
			xtype: 'basecomboselect',
			store: busiStore,
			displayField: 'displayField',
			valueField: 'valueField',
			id: 'queryBusinessId',
			hiddenName: 'queryBusiness',
			value:'',
			width: 140
		},'-','性质: ',{
			xtype: 'textfield',
			id: 'queryCharNameId',
			hiddenName:'queryCharName',
			width: 140
		
		},{
			xtype: 'radiogroup',
			id: 'queryCharConGId',
			items:[
			    {
			    	xtype:'radio',
			    	boxLabel:'包含',
			    	inputValue:'0',
			    	checked:true,
			    	id:'queryCharConAndId',
			    	name:'queryChrCon',
			    	value:'',
			    	width:60
			    }, {
			    	xtype:'radio',
			    	boxLabel:'不包含',
			    	inputValue:'1',
			    	id:'queryCharConNotId',
			    	name:'queryChrCon',
			    	value:'',
			    	width:60
			    }
			       
			],
			//hiddenName: 'queryBusiness',
			//value:'',
			width: 125
		}
        ]
	});
	
	
	var grid = new Ext.grid.GridPanel({
		title: '映射关系历史查询',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mapingHis',
		store: mappingHisStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: channelColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载映射关系历史记录列表......'
		},
		tbar: 	[{			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				mappingHisStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryMchtIdId').setValue('');
				Ext.getCmp('queryMchtAreaId').setValue('');
				Ext.getCmp('queryMchtMCCId').setValue('');			
				Ext.getCmp('queryMapStatusId').setValue('');
				Ext.getCmp('queryMchtUpId').setValue('');
				Ext.getCmp('queryChannelId').setValue('');
				Ext.getCmp('queryBusinessId').setValue('');
				Ext.getCmp('queryCharNameId').setValue('');
				Ext.getDom(Ext.getDoc()).getElementById('queryCharConAndId').checked = true;
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar);
					tbar2.render(this.tbar);
                }  ,
             'cellclick':selectableCell,
        }  ,
		bbar: new Ext.PagingToolbar({
			store: mappingHisStore,
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
	
	mappingHisStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtId: (Ext.getCmp('queryMchtIdId').getValue().trim() =="*")?'':Ext.getCmp('queryMchtIdId').getValue().trim(),
			mchtAreaNo: Ext.getCmp('queryMchtAreaId').getValue(),
			mchtMCC: Ext.getCmp('queryMchtMCCId').getValue(),
			mapStatus: Ext.getCmp('queryMapStatusId').getValue(),
			mchtUpId: (Ext.getCmp('queryMchtUpId').getValue().trim()=="*")?'':Ext.getCmp('queryMchtUpId').getValue().trim(),			
			chlId: Ext.getCmp('queryChannelId').getValue(),
			busiId: Ext.getCmp('queryBusinessId').getValue(),
			charName:Ext.get('queryCharNameId').getValue(),
			charCon:(Ext.getDom(Ext.getDoc()).getElementById('queryCharConAndId').checked)?'0':'1',
					
		});
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});

