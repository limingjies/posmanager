Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=instBalanceAjust'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateSettlmt',mapping: 'dateSettlmt'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtType',mapping: 'mchtType'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'acctCurr',mapping: 'acctCurr'},
			{name: 'settlDate',mapping: 'settlDate'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'transType',mapping: 'transType'},
			{name: 'transRef',mapping: 'transRef'},
			{name: 'recUpdUsrId',mapping: 'recUpdUsrId'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});*/
		
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateSettlmt',align: 'center',renderer: formatDt,width: 90},
		{header: '商户号',dataIndex: 'mchtId',align: 'center',width: 150},
		{header: '商户类型',dataIndex: 'mchtType',align: 'center',width: 100},
		{header: '通道机构号',dataIndex: 'instCode',align: 'center',width: 100},
		{header: '交易币种',dataIndex: 'acctCurr',align: 'center',width: 90,renderer: currType},
		{header: '结算日',dataIndex: 'settlDate',align: 'center',width: 150},
		{header: '原交易接入方机构号',dataIndex: 'brhId',align: 'center',width: 150},
		{header: '原交易接入方交易日期',dataIndex: 'transDate',align: 'center',width: 150,renderer: formatDt},
		{header: '原交易接入方交易流水号',dataIndex: 'sysSeqNum',align: 'center',width: 150},
		{header: '原交易接入方交易类型',dataIndex: 'transType',align: 'center',width: 150},
		{header: '原交易接入方检索参考号',dataIndex: 'transRef',align: 'center',width: 150},
		{header: '处理人ID',dataIndex: 'recUpdUsrId',align: 'center',width: 100},
		{header: '处理时间',dataIndex: 'recCrtTs',align: 'center',width: 150,renderer: formatDt}
	]);
	
	function instCode(val) {
        if(val == '0001')
            return "<font color='blue'>"+val+"-中信</font>";
        if(val == '0002')
            return "<font color='blue'>"+val+"-银生宝</font>";
        if(val == '0003')
            return "<font color='blue'>"+val+"-轩宸</font>";
        if(val == '0004')
            return "<font color='blue'>"+val+"-聚财通</font>";
        return val;
    }

//	function stlmFlag(val) {
//        if(val == '1')
//            return "本地<font color='green'>成功</font>，通道机构<font color='red'>失败</font>";
//        if(val == '2')
//            return "本地<font color='red'>失败</font>，通道机构<font color='green'>成功</font>";
//        if(val == '3')
//            return "本地与通道机构金额不一致";
//        return val;
//    }
    
	  var tbar1 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	       			'-','清算日期：',{
		       		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDateSettlmt',
					name: 'startDateSettlmt',
					width: 120
		           	},'~',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeYesterday,
					editable: false,
					id: 'endDateSettlmt',
					name: 'endDateSettlmt',
					width: 120
		           	}
//		           	,'-','合作伙伴：',{
//						xtype : 'dynamicCombo',
//						methodName : 'getBrhId',
//						id: 'idqueryBrhId',
//						hiddenName: 'queryBrhId',
//					    editable: true,
//					    mode:'local',
//						triggerAction: 'all',
//						forceSelection: true,
//						selectOnFocus: true,
//						lazyRender: true,
//						width: 250
//					}
		           	,'-','商户号：',{
						xtype: 'dynamicCombo',
						methodName: 'getMchntNoAll',
						fieldLabel: '商户编号',
						id: 'idqueryMchtId',
						hiddenName: 'queryMchtId',
						editable: true,
						width: 300
					},'-','通道机构号：',{
						xtype: 'basecomboselect',
						baseParams: 'SETTLE_CHANNEL_BRH',
						id: 'idqueryInstCode',
						hiddenName: 'queryInstCode',
						editable: true,
						emptyText: '请选择',
						width: 140
			           	}]  
	 }); 
	
//	  var tbar2 = new Ext.Toolbar({  
//	        renderTo: Ext.grid.EditorGridPanel.tbar,  
//	        items:[
//	       			'-','交易日期：',{
//		       		xtype: 'datefield',
//					format: 'Y-m-d',
//					altFormats: 'Y-m-d',
//					value:timeYesterday,
//					editable: false,
//					id: 'startDateTrans',
//					name: 'startDateTrans',
//					width: 120
//		           	},'~',{
//					xtype: 'datefield',
//					format: 'Y-m-d',
//					altFormats: 'Y-m-d',
//					value:timeYesterday,
//					editable: false,
//					id: 'endDateTrans',
//					name: 'endDateTrans',
//					width: 120
//		           	},'-','流水号：',{
//						xtype: 'textfield',
//						id: 'querySeqNum',
//						vtype: 'isNumber',
//						maxLength:6,
//						width: 150
//					},'-','参考号：',{
//						xtype: 'textfield',
//						id: 'queryRefNum',
//						vtype: 'isNumber',
//						maxLength:12,
//						width: 150
//					}]  
//	 }); 
	  
	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'txnName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		clicksToEdit: true,
		forceValidation: true,
        renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				txnGridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('startDateSettlmt').setValue(timeYesterday);
				Ext.getCmp('endDateSettlmt').setValue(timeYesterday);
//				Ext.getCmp('startDateTrans').setValue(timeYesterday);
//				Ext.getCmp('endDateTrans').setValue(timeYesterday);
				Ext.getCmp('idqueryMchtId').setValue('');
				Ext.getCmp('idqueryInstCode').setValue('');
//				Ext.getCmp('idqueryBrhId').setValue('');
//				Ext.getCmp('querySeqNum').setValue('');
//				Ext.getCmp('queryRefNum').setValue('');
				
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar1.render(this.tbar);
//		    	  	tbar2.render(this.tbar);
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: txnGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'			
		})
	});
	
	
	txnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
		}
	});
	
	txnGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDateSettlmt: typeof(Ext.getCmp('startDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('startDateSettlmt').getValue().format('Ymd'),
			endDateSettlmt: typeof(Ext.getCmp('endDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('endDateSettlmt').getValue().format('Ymd'),
//			startDateTrans: typeof(Ext.getCmp('startDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('startDateTrans').getValue().format('Ymd'),
//			endDateTrans: typeof(Ext.getCmp('endDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('endDateTrans').getValue().format('Ymd'),
			queryInstCode: Ext.get('queryInstCode').getValue(),
			queryMchtId: Ext.get('queryMchtId').getValue(),
//			queryBrhId: Ext.get('queryBrhId').getValue(),
//			querySeqNum: Ext.get('querySeqNum').getValue(),
//			queryRefNum: Ext.get('queryRefNum').getValue(),
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});