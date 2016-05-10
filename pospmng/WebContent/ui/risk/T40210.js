Ext.onReady(function() {
		// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	
	var riskTotalStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskTotal'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'riskDate',mapping: 'riskDate'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'brhIdNm',mapping: 'brhIdNm'},
			{name: 'riskSeq',mapping: 'riskSeq'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'}
		])
	});
	
	riskTotalStore.load({
		params:{start: 0}
	});
	
	var riskTotalColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '统计日期',dataIndex: 'riskDate',align: 'center',renderer: formatDt,width: 100},
		{id:'cardAccpId',header: '商户号',dataIndex: 'cardAccpId',width: 150,align: 'center'},
		{header: '商户名',dataIndex: 'mchtNm',width: 150,align: 'center'},
		{header: '受理机构',dataIndex: 'brhIdNm',width: 200,align: 'center'},
		{header: '序列号',dataIndex: 'riskSeq',width: 50,align: 'center',hidden:true},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 100,align: 'center'}
	]);
	
	//移机详细信息部分
	var TermMoveStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termMoveInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'countryCode',mapping: 'countryCode'},
			{name: 'networkCode',mapping: 'networkCode'},
			{name: 'locatAreaCode',mapping: 'locatAreaCode'},
			{name: 'cellNum',mapping: 'cellNum'},
			{name: 'transAmount',mapping: 'transAmount'}
		])
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '国家',dataIndex: 'countryCode',width: 70,align: 'center',renderer: countryInfo},
		{header: '网络类型',dataIndex: 'networkCode',width: 70,align: 'center',renderer: networkInfo},
		{header: '位置码',dataIndex: 'locatAreaCode',width: 70,align: 'center'},
		{header: '小区号',dataIndex: 'cellNum',width: 70,align: 'center'},
		{header: '交易量',dataIndex: 'transAmount',width: 70,align: 'center'}
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','商户编号：'
				,{
//					xtype: 'combo',
//					store : mchtStore,
					hiddenName: 'queryCardAccpId',
					id: 'idqueryCardAccpId',
					xtype : 'dynamicCombo',
					methodName : 'getMchntNoAll',
					lazyRender: true,
					width: 300
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
			            }  
			        }*/
				}
				,'-','受理机构：'
				,{
//					xtype: 'basecomboselect',
					id: 'idqueryBrhId',
					hiddenName: 'queryBrhId',
//				    baseParams: 'BRH_BELOW_BRANCH',
					xtype : 'dynamicCombo',
					methodName : 'getBrhId',
					lazyRender: true,
					width: 250
					/*listeners : {  
			           
			            'select' : function() {
								mchtStore.removeAll();
								SelectOptionsDWR.getComboDataWithParameter(
										'MCHT_BELOW', this.value, function(ret) {
											mchtStore.loadData(Ext.decode(ret));
										});

						}
					}*/
				}
	            ]  
         }) 
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
                '-','统计起始日期：',{
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
	               	},'-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					vtype: 'isNumber',
					width: 120
				
				}
	            ]  
         })
         
    var termGrid = new Ext.grid.GridPanel({
		title: '移机信息详情',
		region: 'east',
		width: 400,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: TermMoveStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载移机详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: TermMoveStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg : '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	var riskTotalGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'cardAccpId',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: riskTotalStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskTotalColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [
				{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
					riskTotalStore.load();
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
			    Ext.getCmp('idqueryBrhId').setValue(''),
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
				Ext.getCmp('idqueryBrhId').getStore().reload();*/
			}
		

		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
            	tbar1.render(this.tbar);
            	tbar2.render(this.tbar); 
                } ,
             'cellclick':selectableCell
        }  ,
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: riskTotalStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'

			
		})
//		renderTo: Ext.getBody()
	});
	riskTotalStore.load();
	
	
	TermMoveStore.on('beforeload', function() {
		termGrid.getStore().rejectChanges();
		Ext.apply(this.baseParams, {
			start: 0,
			riskDate: riskTotalGrid.getSelectionModel().getSelected().data.riskDate,
			riskSeq: riskTotalGrid.getSelectionModel().getSelected().data.riskSeq
		});
	});

	riskTotalGrid.getSelectionModel().on('rowselect', function() {
		//行高亮
		Ext.get(riskTotalGrid.getView().getRow(riskTotalGrid.getSelectionModel().last)).frame();
		termGrid.getStore().load();
	});
	
	riskTotalStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryCardAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
			queryBrhId: Ext.getCmp('idqueryBrhId').getValue(),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue()
		});
		termGrid.getStore().removeAll();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [riskTotalGrid,termGrid],
		renderTo: Ext.getBody()
	});
});