Ext.onReady(function() {
	
	// 数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteRuleCheck'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			id:'ruleId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'accpId',mapping: 'accpId'},
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'priority',mapping: 'priority'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'ruleCode',mapping: 'ruleCode'},
			{name: 'cardBin',mapping: 'cardBin'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'insIdCd',mapping: 'insIdCd'},
			{name: 'mchtArea',mapping: 'mchtArea'},
			{name: 'dateCtl',mapping: 'dateCtl'},
			{name: 'dateBeg',mapping: 'dateBeg'},
			{name: 'dateEnd',mapping: 'dateEnd'},
			{name: 'timeCtl',mapping: 'timeCtl'},
			{name: 'timeBeg',mapping: 'timeBeg'},
			{name: 'timeEnd',mapping: 'timeEnd'},
			{name: 'amtCtl',mapping: 'amtCtl'},
			{name: 'amtBeg',mapping: 'amtBeg'},
			{name: 'amtEnd',mapping: 'amtEnd'},
			{name: 'onFlag',mapping: 'onFlag'}
		]),
	autoLoad: true
	}); 
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '规则序号',dataIndex: 'ruleId',align: 'center',width: 80,hidden:true},
		{header: '渠道编号',dataIndex: 'brhId',id:'brhId',width: 190 ,renderer:routeRule},
		{header: '商户编号',dataIndex: 'accpId',width: 240 ,renderer:routeRule},
		{header: '目的规则',dataIndex: 'ruleCode',width:120,id:'ruleCode'},
		{header: '优先级别',dataIndex: 'priority',width: 100 ,renderer:routePriority},
		{header: '路由状态',dataIndex: 'onFlag',width: 100,align: 'center',renderer:routeStatus},
		{header: '交易类型',dataIndex: 'txnNum',width: 100,align: 'center',renderer:routeTxnTp},
		{header: '卡类型',dataIndex: 'cardTp',width: 100,align: 'center',renderer:routeCardTp}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			{
					xtype: 'textfield',
					id: 'queryRuleId',
					name: 'queryRuleId',
					vtype:'isNumber',
					maxLength: 6,
					hidden:true,
					width: 140
				},'-',	'优先级别：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','低'],['1','中低'],['2','中'],['3','中高'],['4','高']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryPriority',
					id:'queryPriorityId',
					editable: false,
					emptyText: '请选择',
					width: 140
				},	'-','路由状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryOnFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryOnFlagId',
					width: 140
				},'-','卡类型：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['*','无限制'],['00','借记卡'],['01','贷记卡'],['02','准借记卡'],['03','预付费卡'],['04','公务卡']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					editable: false,
					emptyText: '请选择',
					hiddenName: 'queryCardTp',
					id:'queryCardTpId',
					width: 140
				},'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'queryTxnNumId',
					baseParams: 'routeTxnTp',
					hiddenName: 'queryTxnNum',
					width: 140
				}
	            ]  
         }) 
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','渠道编号：',
					{
//					xtype: 'basecomboselect',
//					baseParams: 'routeBrhId',
					xtype : 'dynamicCombo',
					methodName : 'getBrhIdRoute',
					id: 'queryBrhIdId',
					hiddenName: 'queryBrhId',
					lazyRender: true,
					width: 250
				},	'-','商户编号：',
					{
//					xtype: 'basecomboselect',
//					baseParams: 'routeMchtNo',
					xtype: 'dynamicCombo',
					methodName: 'getMchntIdRoute',
					hiddenName: 'queryAccpId',
//					hidden:true,
					width: 250,
					id: 'queryAccpIdId',
					mode:'local',
					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
				},	'-','目的规则：',
					{
					xtype: 'basecomboselect',
					baseParams: 'routeRuleCode',
					hiddenName: 'queryRuleCode',
//					hidden:true,
					width: 250,
					id: 'queryRuleCodeId',
					mode:'local',
					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
				}
	            ]  
         }) 
	
	
	 
	
	
	
	var grid = new Ext.grid.GridPanel({
		title: '路由服务控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'ruleCode',
		store: routeRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载路由记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				routeRuleStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
				Ext.getCmp('queryRuleId').setValue('');
				Ext.getCmp('queryPriorityId').setValue('');
				Ext.getCmp('queryOnFlagId').setValue('');
				Ext.getCmp('queryCardTpId').setValue('');
				
				Ext.getCmp('queryBrhIdId').setValue('');
				Ext.getCmp('queryAccpIdId').setValue('');
//				Ext.getCmp('queryFirstMchtCdId').setValue('');
				Ext.getCmp('queryRuleCodeId').setValue('');
				Ext.getCmp('queryTxnNumId').setValue('');
			}

		
		
		},'-',{
			
			xtype: 'button',
			text: '查看详细信息',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {

				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				var ruleId=grid.getSelectionModel().getSelected().get('ruleId');
				var brhId=grid.getSelectionModel().getSelected().get('brhId').substring(0,grid.getSelectionModel().getSelected().get('brhId').indexOf('-'));
				var accpId=grid.getSelectionModel().getSelected().get('accpId').substring(0,grid.getSelectionModel().getSelected().get('accpId').indexOf('-'));
				showRouteDetailS(ruleId,brhId,accpId,grid);
			}
		},'-',{
			
			xtype: 'button',
			text: '启用路由',
			name: 'start',
			id: 'start',
			iconCls: 'accept',
			width: 80,
			disabled: true,
			handler:function(bt) {

				showConfirm('确定要启用该路由规则吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T10603Action.asp?method=start',
										params : {
											ruleId: grid.getSelectionModel().getSelected().get('ruleId'),
				 							brhId : grid.getSelectionModel().getSelected().get('brhId').substring(0,grid.getSelectionModel().getSelected().get('brhId').indexOf('-')),
				 							accpId: grid.getSelectionModel().getSelected().get('accpId').substring(0,grid.getSelectionModel().getSelected().get('accpId').indexOf('-')),
//											ruleId: grid.getSelectionModel().getSelected().data.ruleId,
//											accpId: grid.getSelectionModel().getSelected().data.accpId,
											txnId : '10603',
											subTxnId : '01'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											Ext.getCmp('start').disable();
											Ext.getCmp('stop').disable();
										}
									});
						}
					})
			}
		},'-',{
			
			xtype: 'button',
			text: '停用路由',
			name: 'stop',
			id: 'stop',
			iconCls: 'stop',
			width: 80,
			disabled: true,
			handler:function(bt) {

				showConfirm('确定要停用该路由规则吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T10603Action.asp?method=stop',
										params : {
											ruleId: grid.getSelectionModel().getSelected().get('ruleId'),
				 							brhId : grid.getSelectionModel().getSelected().get('brhId').substring(0,grid.getSelectionModel().getSelected().get('brhId').indexOf('-')),
				 							accpId: grid.getSelectionModel().getSelected().get('accpId').substring(0,grid.getSelectionModel().getSelected().get('accpId').indexOf('-')),
											txnId : '10603',
											subTxnId : '02'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											Ext.getCmp('start').disable();
											Ext.getCmp('stop').disable();
										}
									});
						}
					})
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
		Ext.getCmp('detail').disable();
		Ext.getCmp('start').disable();
		Ext.getCmp('stop').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
			Ext.getCmp('detail').enable();
			var onFlag=grid.getSelectionModel().getSelected().get('onFlag');
			if(onFlag=='0'){
				Ext.getCmp('start').enable();
				Ext.getCmp('stop').disable();
			}else{
				Ext.getCmp('stop').enable();
				Ext.getCmp('start').disable();
			}
		}
	});
	
	
	routeRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleId: Ext.getCmp('queryRuleId').getValue(),
			queryPriority: Ext.get('queryPriority').getValue(),
			queryOnFlag:Ext.get('queryOnFlag').getValue(),
			queryCardTp:Ext.get('queryCardTp').getValue(),
			
			queryBrhId:Ext.get('queryBrhId').getValue(),
			queryAccpId:Ext.get('queryAccpId').getValue(),
//			queryFirstMchtCd:Ext.get('queryFirstMchtCd').getValue(),
			queryRuleCode:Ext.get('queryRuleCode').getValue(),
			queryTxnNum:Ext.get('queryTxnNum').getValue()
			
				
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});