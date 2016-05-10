Ext.onReady(function() {
	
//	==================================主规则====================================
	// 数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ruleMchtMap'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ruleCode',mapping: 'ruleCode'},
			{name: 'srvId',mapping: 'srvId'},
			{name: 'msc1',mapping: 'msc1'},
			{name: 'srvIdName',mapping: 'srvIdName'}
		]),
		autoLoad: true
	}); 
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '规则代码',dataIndex: 'ruleCode',align: 'center',width: 100},
		{header: '规则名称',dataIndex: 'msc1',width: 200,id:'msc1' },
		{header: '通道机构',dataIndex: 'srvIdName',id:'srvId',width: 190 }
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'规则代码：',{
					xtype: 'textfield',
					id: 'queryRuleCode',
					name: 'queryRuleCode',
					vtype:'isEngNum',
					maxLength: 10,
					width: 140
				},'-',	'通道机构：',{
					xtype: 'basecomboselect',
					id: 'querySrvIdId',
					baseParams: 'SETTLE_BRH',
					hiddenName: 'querySrvId',mode:'local',
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				}
	            ]  
         }) 
         
         
    var routeRuleGrid = new Ext.grid.GridPanel({
		width: 460,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'msc1',
		store: routeRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
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
				Ext.getCmp('queryRuleCode').setValue('');
				Ext.getCmp('querySrvIdId').setValue('');
				routeRuleStore.load();
			}

		
		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
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
	
	
	
//	==============================主规则结束=======================================
	
	
	
	
//	==============================详细规则商户=======================================
	// 数据集
	var routeRuleDtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=ruleMchtMapContl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'firstMchtNo',mapping: 'firstMchtNo'},
			{name: 'msc2',mapping: 'msc2'}
		])
	}); 
	
	var routeRuleDtlColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '一级商户号',dataIndex: 'firstMchtNm',id:'firstMchtNm',width: 190 },
		{header: '状态',dataIndex: 'msc2',width: 120 ,align: 'center',renderer:routeStatus}
	]);
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			/*'-',	'一级商户号：',{
					xtype: 'basecomboselect',
					id: 'queryFirstMchtCdId',
					baseParams: 'routeFirstMchtCd',
					hiddenName: 'queryFirstMchtCd',
					mode:'local',
					triggerAction: 'all',
					editable: true,
					lazyRender: true,
					width: 250
				},*/
				'-','状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryMsc2',
					editable: false,
					emptyText: '请选择',
					id:'queryMsc2Id',
					width: 140
				}
	            ]  
         }) 
	
	
	var grid = new Ext.grid.GridPanel({
		region: 'east',
		width: 480,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		autoExpandColumn: 'firstMchtNm',
		store: routeRuleDtlStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleDtlColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'queryDtl',
			id: 'queryDtl',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeRuleDtlStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetDtl',
			id: 'resetDtl',
			iconCls: 'reset',
			width: 80,
			handler:function() {
//				Ext.getCmp('queryFirstMchtCdId').setValue('');
				Ext.getCmp('queryMsc2Id').setValue('');
				routeRuleDtlStore.load();
			}

		
		
		},'-', {
			xtype : 'button',
			text : '启用',
			name : 'start',
			id : 'start',
			disabled : true,
			iconCls : 'accept',
			width : 80,
			handler : function() {
				showConfirm('确定要启用该规则下的一级商户吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10212Action.asp?method=start',
							params : {
								ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
								firstMchtCd : grid.getSelectionModel().getSelected().get('firstMchtNo'),
								txnId : '10212',
								subTxnId : '01'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, grid);
									grid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, grid);
								}
							}
						});
					}
				})
			}
		},'-', {
			xtype : 'button',
			text : '停用',
			name : 'stop',
			id : 'stop',
			disabled : true,
			iconCls : 'stop',
			width : 80,
			handler : function() {
				showConfirm('确定要停用该规则下一级商户吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T10212Action.asp?method=stop',
							params : {
								ruleCode : routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
								firstMchtCd : grid.getSelectionModel().getSelected().get('firstMchtNo'),
								txnId : '10212',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								var rspObj = Ext
										.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, grid);
									grid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, grid);
								}
							}
						});
					}
				})
			}
		}
		],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleDtlStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
//	====================================详细规则商户结束=================================================
	
	
	
//	====================================加载=================================================
	// 禁用编辑按钮
	routeRuleGrid.getStore().on('beforeload',function() {
			grid.getStore().removeAll();
	});
	
	routeRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			routeRuleDtlStore.load();
			/*routeRuleDtlStore.load({
				params: {
					start: 0,
					queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
				}
			})*/
		}
	});
	
	
	routeRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleCode: Ext.getCmp('queryRuleCode').getValue(),
			querySrvId:Ext.get('querySrvId').getValue()
		});
	});
	
//	-----------------------------------------------------------------------------------------
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
			
			Ext.getCmp('stop').disable();
			Ext.getCmp('start').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			Ext.getCmp('detail').enable();
			var onFlag=grid.getSelectionModel().getSelected().get('msc2');
			if(onFlag=='0'){
				Ext.getCmp('start').enable();
				Ext.getCmp('stop').disable();
			}else{
				Ext.getCmp('stop').enable();
				Ext.getCmp('start').disable();
			}
		}
	});
	
	
	routeRuleDtlStore.on('beforeload', function(){
		routeRuleDtlStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode'),
//			queryFirstMchtCd: Ext.get('queryFirstMchtCd').getValue(),
			queryMsc2:Ext.get('queryMsc2').getValue()
		});
	});
	
//	==============================================================================================

			
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [routeRuleGrid,grid],
		renderTo: Ext.getBody()
	});
});