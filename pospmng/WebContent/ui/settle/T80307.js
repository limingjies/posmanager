Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() );
	//获取当前日期的前一天
	var timeToday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=amtbackApplyquery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			
				{name: 'checkOpr',mapping: 'checkOpr'},
				{name: 'checkDate',mapping: 'checkDate'},
				{name: 'applyOpr',mapping: 'applyOpr'},
				{name: 'applyDate',mapping: 'applyDate'},
				{name: 'applyTime',mapping: 'applyTime'},
//				{name: 'applyState',mapping: 'applyState'},
				{name: 'resState',mapping: 'resState'},
				{name: 'misc1',mapping: 'misc1'},
				{name: 'instDate',mapping: 'instDate'},
				{name: 'instTime',mapping: 'instTime'},
				{name: 'pay',mapping: 'pay'},
				{name: 'pan',mapping: 'pan'},
				{name: 'cardAccpId',mapping: 'cardAccpId'},
				{name: 'mchtName',mapping: 'mchtName'},
				{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
				{name: 'termSsn',mapping: 'termSsn'},
				{name: 'retrivlRef',mapping: 'retrivlRef'},
				{name: 'amtTrans',mapping: 'amtTrans'},
				{name: 'dateSettlmt8',mapping: 'dateSettlmt8'},
				{name: 'keyCup',mapping: 'keyCup'},
				{name: 'misc3',mapping: 'misc3'},
				{name: 'txnName',mapping: 'txnName'}
			
			
			
			
		]),
		autoLoad: true
	});
	
	
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
				'<table width=700>',
				
				'<tr><td><font color=gray>交易日期： </font>{instDate:this.formatDate}</td>',
				'<td><font color=gray>交易时间：</font>{instTime:this.formatDate}</td></tr>',
				
				'</tr><td><font color=gray>参考号：</font>{retrivlRef}</td></tr>',
				
				'<tr><td><font color=gray>申请日期： </font>{applyDate:this.formatDate}</td>',
				'<td><font color=gray>申请时间：</font>{applyTime:this.formatDate}</td></tr>',
				
				'<tr><td><font color=gray>申请操作员： </font>{applyOpr}</td></tr>',
				
				'<tr><td><font color=gray>审核日期： </font>{txnName:this.formatDate}</td>',
				'<td><font color=gray>审核操作员：</font>{misc3}</td></tr>',
				
				'<tr><td colspan=2><font color=gray>备注：</font>{misc1}</td></tr>' 
				
			,{
			formatDate : function(val){
				 if(val!=null &&val.length == 8){
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
				+ val.substring(6, 8);
				}if(val!=null &&val.length == 6){
					return val.substring(0, 2) + ':' + val.substring(2, 4) + ':'
				+ val.substring(4);
				}else{
					return val;
				}
			 }
			}
		)
	});
	
	
	
	var detailColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
			{header: '申请日期',dataIndex: 'applyDate',align: 'center',renderer: formatDt,width: 80},
			{header: '交易日期',dataIndex: 'instDate',align: 'center',renderer: formatDt,width: 80},
//		{header: '交易时间',dataIndex: 'instTime',align: 'center',renderer: formatDt,width: 80},
//		{header: '退货类型',dataIndex: 'misc2',align: 'center',renderer: amtBackFlag,width: 80},
		{header: '商户号',dataIndex: 'mchtName',align: 'left',width: 250,id:'mchtName'},
		{header: '卡号',dataIndex: 'pan',align: 'left',width: 200},
		{id:'pay',header: '交易类型',dataIndex: 'pay',align: 'left',width: 60},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right',width: 80},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 80,align: 'center'},
//		{header: '参考号',dataIndex: 'retrivlRef',align: 'center'},
	
//		{header: '申请时间',dataIndex: 'applyTime',align: 'center',renderer: formatDt,width: 80},
//		{header: '申请状态',dataIndex: 'applyState',align: 'left',width: 80,renderer:applyState },
		{header: '退货状态',dataIndex: 'resState',align: 'left',width: 80,renderer:resState }
//		{header: '清算日期',dataIndex: 'dateSettlmt8',hidden: true,renderer: formatDt},
//		{header: '银联关键字',dataIndex: 'keyCup',hidden: true}
		
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','申请起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					editable: false,
					value:timeToday,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','申请结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					editable: false,
					value:timeToday,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	}	,'-',	'退货状态：',{
	               		xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','待受理'],['1','受理成功'],['2','受理拒绝']],
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						editable: false,
						emptyText: '请选择',
						Name: 'queryResState',
						id:'queryResState',
						width: 120
	               	},'-','参考号：       ',{
					xtype: 'textfield',
					name: 'queryRetrivlRef',
					id: 'queryRetrivlRef',
					vtype: 'isNumber',
					width: 120
				}
	               /*	,'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'idqueryTxnName',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnName',
					width: 140
				}*/
	               
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
					width: 144
				},'-','交易卡号：',{
					xtype: 'textfield',
					id: 'queryPan',
					 vtype: 'isNumber',
					name: 'queryPan',
					width: 144
				},'-','商户编号：'
				,{
//					xtype: 'basecomboselect',
//					baseParams: 'MCHT_NO',
//					store : mchtStore,
					xtype : 'dynamicCombo',
					methodName : 'getAllMchntId',
					hiddenName: 'queryCardAccpId',
//					hiddenName : 'queryAccpId',
//					hidden:true,
					width: 300,
					id: 'idqueryCardAccpId',
//					mode:'local',
//					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
					}
				
				]  
         }) 
         
        
     
    
	
	
	
	var detailGrid = new Ext.grid.EditorGridPanel({
//		iconCls: 'T104',
		region:'center',
//		title: '差错信息',
		autoExpandColumn:'mchtName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		plugins: rowExpander,
		forceValidation: true,
		renderTo : Ext.getBody(),
		tbar: ['-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				detailGridStore.load();
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
				
//				Ext.getCmp('idqueryTxnName').setValue('');
				Ext.getCmp('queryRetrivlRef').setValue('');
//				Ext.getCmp('queryApplyState').setValue('');
				Ext.getCmp('queryResState').setValue('');
				
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
				
				detailGridStore.reload();
//				Ext.getCmp('idMchtNo').getStore().reload()
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});*/
				Ext.getCmp('idqueryCardAccpId').getStore().reload();
			}

		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					
							tbar2.render(this.tbar); 
							tbar3.render(this.tbar); 
							
                }  
        }  ,
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'

			
		})
//		renderTo: Ext.getBody()
	});
	
	detailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last)).frame();
			
//				detailGrid.getTopToolbar().items.items[0].enable();
			
//			Ext.getCmp('backApply').enable();
		}
	});
	
	

	
	detailGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			queryPan: Ext.getCmp('queryPan').getValue(),
//			queryApplyState: Ext.getCmp('queryApplyState').getValue(),
			queryResState: Ext.getCmp('queryResState').getValue(),
			queryCardAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
//			queryTxnName: Ext.get('queryTxnName').getValue()
			queryRetrivlRef: Ext.get('queryRetrivlRef').getValue()
			
		});
//		detailGridStore.removeAll();
//		Ext.getCmp('backApply').disable();
//		detailGrid.getTopToolbar().items.items[0].disable();
	});
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [detailGrid],
		renderTo: Ext.getBody()
	});
});