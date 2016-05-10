Ext.onReady(function() {
	
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	
	//取性质制作下拉列表
	var propStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('PROP_SEL','',function(ret){
		propStore.loadData(Ext.decode(ret));
	});
	
	
//	==================================商户组信息====================================
	// 数据集
	var mchtgStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteMchtg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtGid',mapping: 'mchtGid'},
			{name: 'mchtGname',mapping: 'mchtGname'},
			{name: 'mchtGdsp',mapping: 'mchtGdsp'}
		]),
		autoLoad: true
	});
	
	var mchtgColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户组编号',dataIndex: 'mchtGid',align: 'center',width: 50,id:'mchtg'},
		{header: '商户组名称',dataIndex: 'mchtGname',width: 150,align: 'center' },
		{header: '商户组描述',dataIndex: 'mchtGdsp',align: 'center',width: 150 }
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               	'-','商户组名称：',{
					xtype: 'textfield',
					id: 'mchtGroupNm',
					name: 'mchtGroupNm',
					vtype:'isEngNum',
					maxLength: 10,
					width: 140
				}
	            ]  
	}) ;   
	var tbar2 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
      			'-',	'商户名称：',{
					xtype: 'textfield',
					id: 'mchtNm',
					name: 'mchtNm',
					//vtype:'isEngNum',
					maxLength: 10,
					width: 140
				},'-',	'商户编号：',{
					xtype: 'textfield',
					id: 'mchtNo',
					name: 'mchtNo',
					//vtype:'isEngNum',
					maxLength: 15,
					width: 140
				}
	            ]  
	});
    var mchtgGrid = new Ext.grid.GridPanel({
		width:350,
		id:'mchtgGridPanel',
		title: '商户组信息',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mchtg',
		store: mchtgStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchtgColModel,
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
				mchtgStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('mchtGroupNm').setValue('');
				Ext.getCmp('mchtNo').setValue('');
				Ext.getCmp('mchtNm').setValue('');
				mchtgStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
					tbar1.render(this.tbar); 
                } ,
            'cellclick':selectableCell,
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
    mchtgGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchtgGrid.getView().getRow(mchtgGrid.getSelectionModel().last)).frame();
			Ext.getCmp('add').enable();
			Ext.getCmp('queryRule').enable();
			Ext.getCmp('resetRule').enable();
			grid.getStore().load();
			
//			Ext.getCmp('edit').enable();
//			Ext.getCmp('transferIn').enable();
//			Ext.getCmp('delete').enable();
//			Ext.getCmp('queryDetail').enable();
//			Ext.getCmp('addDetail').enable();
		}
	});
	
	
	mchtgStore.on('beforeload', function(){
		grid.getStore().removeAll();
		Ext.getCmp('add').disable();
		Ext.getCmp('queryRule').disable();
		Ext.getCmp('resetRule').disable();
		Ext.getCmp('detail').disable();
		Ext.getCmp('delete').disable();
		Ext.getCmp('edit').disable();
		Ext.getCmp('start').disable();
		Ext.getCmp('stop').disable();
		//Ext.getCmp('transferIn').disable();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: Ext.getCmp('mchtNo').getValue(),
			mchtNm:Ext.get('mchtNm').getValue(),
			mchtGroupNm:Ext.get('mchtGroupNm').getValue()
		});
	});
	

	//==================================商户组信息结束==================================
	
	//==================================
	// 路由规则数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteRuleInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'ruleName',mapping: 'ruleName'},
			{name: 'orders',mapping: 'orders'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhId2',mapping: 'brhId2'},
			{name: 'brhId3',mapping: 'brhId3'},
			{name: 'status',mapping: 'status'},
			{name: 'ruleDsp',mapping: 'ruleDsp'}
		]),
	autoLoad: false
	});
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '规则编号',dataIndex: 'ruleId',id:'ruleId',align: 'center',width: 100},
		{header: '规则名称',dataIndex: 'ruleName',width: 180,align: 'center'},
		{header: '优先级',dataIndex: 'orders',align: 'center',width: 80 },
		{header: '支付渠道',dataIndex: 'brhId',align: 'center',width: 100 },
		{header: '业务',dataIndex: 'brhId2',align: 'center',width: 100 },
		{header: '性质',dataIndex: 'brhId3',align: 'center',width: 100 },
		{header: '规则状态',dataIndex: 'status',align: 'center',width: 80 ,renderer:branchStatus},
		{header: '描述',dataIndex: 'ruleDsp',align: 'center',width: 190 }
	]);
	
	var tbar3 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
           '-',	'规则编号：',{
				xtype: 'textfield',
				id: 'queryRuleId',
				name: 'queryRuleId',
				vtype:'isEngNum',
				maxLength: 8,
				width: 140
			},'-',	'规则名称：',{
				xtype: 'textfield',
				id: 'queryRuleName',
				name: 'queryRuleName',
				width: 140
			},'-',	'规则状态：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','启用'],['1','停用']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryStatus',
				id:'queryStatusId',
				editable: false,
				//emptyText: '请选择',
				value: "",
				width: 140
			}
        ]  
	})
	
	var tbar4 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
            '-','支付渠道：',{
				xtype: 'basecomboselect',
				baseParams: 'CHANNEL_ALL',
				fieldLabel: '支付渠道',
				hiddenName: 'queryBrh',
				id:'queryBrhId',
				width: 140,
				listeners: {
					'select': function() {
						busiStore.removeAll();
						var brhId = Ext.getCmp('queryBrhId').getValue();
						Ext.getCmp('queryBrhId2').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh2').value = '';
						Ext.getCmp('queryBrhId3').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
						SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
						busiStore.loadData(Ext.decode(ret));
						});
					},
					'change': function() {
						busiStore.removeAll();
						var brhId = Ext.getCmp('queryBrhId').getValue();
						Ext.getCmp('queryBrhId2').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh2').value = '';
						Ext.getCmp('queryBrhId3').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
						SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
						busiStore.loadData(Ext.decode(ret));
						});
					}
				}
			},'-','业　　务：',{
				xtype: 'basecomboselect',
				store: busiStore,
				displayField: 'displayField',
				valueField: 'valueField',
				id: 'queryBrhId2',
				hiddenName: 'queryBrh2',
				value:'',
				width: 140,
				listeners: {
					'select': function() {
						propStore.removeAll();
						var brhId2 = Ext.getCmp('queryBrhId2').getValue();
						Ext.getCmp('queryBrhId3').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
						SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
							propStore.loadData(Ext.decode(ret));
						});
					},
					'change': function() {
						propStore.removeAll();
						var brhId2 = Ext.getCmp('queryBrhId2').getValue();
						Ext.getCmp('queryBrhId3').setValue('');
						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
						SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
							propStore.loadData(Ext.decode(ret));
						});
					}
				}
			},'-',	'性　　质：',{
				xtype: 'basecomboselect',
				store: propStore,
				displayField: 'displayField',
				valueField: 'valueField',
				id: 'queryBrhId3',
				hiddenName: 'queryBrh3',
				value:'',
				width: 180
			}
        ]  
	})
	
	
	var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: false});
	
	var grid = new Ext.grid.GridPanel({
		title: '路由规则维护',
		region: 'east',
		width: 700,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		autoExpandColumn: 'ruleId',
		store: routeRuleStore,
		sm: sm,
		cm: routeRuleColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载路由规则列表......'
		},
		tbar:[{
			
			xtype: 'button',
			text: '查询',
			name: 'queryRule',
			id: 'queryRule',
			iconCls: 'query',
			width: 80,
			disabled:true,
			handler:function() {
				routeRuleStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetRule',
			id: 'resetRule',
			iconCls: 'reset',
			disabled:true,
			width: 80,
			handler:function() {
				
				Ext.getCmp('queryRuleId').setValue('');
				Ext.getCmp('queryRuleName').setValue('');
				Ext.getCmp('queryBrhId').setValue('');
				Ext.getCmp('queryBrhId2').setValue('');
				Ext.getCmp('queryBrhId3').setValue('');
				Ext.getDom(Ext.getDoc()).getElementById('queryBrh').value = '';
				Ext.getDom(Ext.getDoc()).getElementById('queryBrh2').value = '';
				Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
				Ext.getCmp('queryStatusId').setValue('');
				//重新获取下拉列表
				Ext.getCmp('queryBrhId').store.reload();
				SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
					busiStore.loadData(Ext.decode(ret));
				});
				SelectOptionsDWR.getComboDataWithParameter('PROP_SEL','',function(ret){
					propStore.loadData(Ext.decode(ret));
				});
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
				
				showRouteDetail(0, ruleId);
			}
		},'-',{			
			xtype: 'button',
			text: '新增',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 80,
			disabled: true,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				showRouteDetail(1, '', function(){grid.getStore().reload();});
			}
		},'-',{			
			xtype : 'button',
			text : '修改',
			name : 'edit',
			id : 'edit',
			disabled : true,
			iconCls : 'edit',
			width : 80,
			handler:function(bt) {
				
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				var ruleId=grid.getSelectionModel().getSelected().get('ruleId');
				
				showRouteDetail(2, ruleId, function(){grid.getStore().reload();});
			}
		},'-',{			
			xtype: 'button',
			text: '删除',
			name: 'delete',
			id: 'delete',
			iconCls: 'delete',
			width: 80,
			disabled: true,
			handler:function() {
				showConfirm('确定要删除该条记录吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						
						Ext.Ajax.request({
							url : 'T110311Action_delete.asp',
							params : {
								ruleId : grid.getSelectionModel().getSelected().get('ruleId'),
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, grid);
								}
							}
						});
					}
				})
			}
		},'-',{
			
			xtype: 'button',
			text: '启用',
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
										url : 'T110311Action_start.asp',
										params : {
											ruleId: grid.getSelectionModel().getSelected().get('ruleId'),
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
			text: '停用',
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
										url : 'T110311Action_stop.asp',
										params : {
											ruleId: grid.getSelectionModel().getSelected().get('ruleId'),
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
					tbar3.render(this.tbar); 
					tbar4.render(this.tbar); 
                },
            'cellclick':selectableCell,
                
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
		//Ext.getCmp('add').disable();
		Ext.getCmp('detail').disable();
		Ext.getCmp('delete').disable();
		Ext.getCmp('edit').disable();
		Ext.getCmp('start').disable();
		Ext.getCmp('stop').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
			Ext.getCmp('detail').enable();
			Ext.getCmp('delete').enable();
			Ext.getCmp('edit').enable();
			
			var onFlag=grid.getSelectionModel().getSelected().get('status');
			if(onFlag =='0'){
				Ext.getCmp('start').disable();
				Ext.getCmp('stop').enable();
			}else{
				Ext.getCmp('stop').disable();
				Ext.getCmp('start').enable();
			}
		}
	});
	
	routeRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryGroup:mchtgGrid.getSelectionModel().getSelected().get('mchtGid'),
			queryRuleId: Ext.get('queryRuleId').getValue(),
			queryRuleName: Ext.get('queryRuleName').getValue(),
			queryBrhId:Ext.getCmp('queryBrhId').getValue(),
			queryBrhId2:Ext.getCmp('queryBrhId2').getValue(),			
			queryBrhId3:Ext.getCmp('queryBrhId3').getValue(),
			queryStatus:Ext.getCmp('queryStatusId').getValue()			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchtgGrid,grid],
		renderTo: Ext.getBody()
	});
});