Ext.onReady(function() {
	
	// 数据集
	var runRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=runRisk'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'riskType',mapping: 'riskType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'txnCount',mapping: 'txnCount'},
			{name: 'regTime',mapping: 'regTime'},
			{name: 'updTime',mapping: 'updTime'},
			{name: 'onFlag',mapping: 'onFlag'},
			{name: 'mchtNo',mapping: 'mchtNo'}
		]),
	autoLoad: true
	}); 
	
	var runRiskColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '风控序号',dataIndex: 'ruleId',align: 'center',width: 60},
		{header: '风控级别',dataIndex: 'riskLvl',align: 'center',width: 80,renderer:routeRule},
		{id:'cardAccpId',header: '商户编号',dataIndex: 'cardAccpId',width: 240 ,renderer:routeRule},
		{header: '风控类型',dataIndex: 'riskType',width:100,renderer:runRiskType},
		{header: '交易类型',dataIndex: 'txnNum',width: 100,align: 'left',renderer:routeTxnTp},
		{header: '卡类型',dataIndex: 'cardTp',width: 100,align: 'center',renderer:routeCardTp},
		{header: '交易金额',dataIndex: 'txnAmt',width: 100 ,align: 'right'},
		{header: '风控状态',dataIndex: 'onFlag',width: 100,align: 'center',renderer:routeStatus}
	]);
	
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			{
					xtype: 'textfield',
					id: 'queryRuleId',
					name: 'queryRuleId',
					vtype:'isNumber',
					hidden:true,
					maxLength: 6,
					width: 140
				},'-',	'风控类型：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','单笔限额'],['1','日累计限额']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryRiskType',
					id:'queryRiskTypeId',
					editable: false,
					emptyText: '请选择',
					width: 140
				},	'-','风控状态：',
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
               			'-','商户编号：',
					{
//					xtype: 'basecomboselect',
//					baseParams: 'MCHNT_NO',
					xtype: 'dynamicCombo',
					methodName: 'getMchntIdRoute',
					hiddenName: 'queryAccpId',
					width: 300,
					id: 'queryAccpIdId',
					lazyRender: true
					/*listeners : {  
			            'beforequery':function(e){  
			                   
			                var combo = e.combo;    
			                if(!e.forceAll){    
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }  
			            }  }*/
				},	'-','风控级别：',
					{
					xtype: 'basecomboselect',
					baseParams: 'RISK_LVL',
					hiddenName: 'queryRiskLvl',
//					hidden:true,
					width: 150,
					id: 'queryRiskLvlId',
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
		title: '事中风控控制',
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'cardAccpId',
		store: runRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: runRiskColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
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
				
				Ext.getCmp('queryRuleId').setValue('');
				Ext.getCmp('queryRiskTypeId').setValue('');
				Ext.getCmp('queryOnFlagId').setValue('');
				Ext.getCmp('queryCardTpId').setValue('');
				
				Ext.getCmp('queryAccpIdId').setValue('');
				Ext.getCmp('queryRiskLvlId').setValue('');
				Ext.getCmp('queryTxnNumId').setValue('');
				
				runRiskStore.load();
//				Ext.getCmp('queryAccpIdId').getStore().reload();
			}

		
		
		},'-',{
			
			xtype: 'button',
			text: '启用风控',
			name: 'start',
			id: 'start',
			iconCls: 'accept',
			width: 80,
			disabled: true,
			handler:function(bt) {

				showConfirm('确定要启用该事中风控吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T40103Action.asp?method=start',
										params : {
											riskLvl: grid.getSelectionModel().getSelected().get('riskLvl').substring(0,1),
				 							cardAccpId: grid.getSelectionModel().getSelected().get('mchtNo'),
											ruleId: grid.getSelectionModel().getSelected().data.ruleId,
											txnId : '40103',
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
			text: '停用风控',
			name: 'stop',
			id: 'stop',
			iconCls: 'stop',
			width: 80,
			disabled: true,
			handler:function(bt) {

				showConfirm('确定要停用该事中风控吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T40103Action.asp?method=stop',
										params : {
											riskLvl: grid.getSelectionModel().getSelected().get('riskLvl').substring(0,1),
				 							cardAccpId: grid.getSelectionModel().getSelected().get('mchtNo'),
											ruleId: grid.getSelectionModel().getSelected().data.ruleId,
											txnId : '40103',
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
                }  ,
            'cellclick':selectableCell,
        }  ,
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
		Ext.getCmp('start').disable();
		Ext.getCmp('stop').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
//			Ext.getCmp('detail').enable();
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
	
	
	runRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleId: Ext.getCmp('queryRuleId').getValue(),
			queryRiskLvl: Ext.get('queryRiskLvl').getValue(),
			queryOnFlag:Ext.get('queryOnFlag').getValue(),
			queryCardTp:Ext.get('queryCardTp').getValue(),
			
			queryAccpId:Ext.get('queryAccpId').getValue(),
			queryRiskType:Ext.get('queryRiskType').getValue(),
			queryTxnNum:Ext.get('queryTxnNum').getValue()
			
				
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});