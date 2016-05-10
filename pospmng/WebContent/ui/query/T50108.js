Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
	//获取显示时间（当天和前一天12:00:00），格式例：2014-07-24 12:00:00
	var timeToday = curDate.format("Y") +"-" +curDate.format("m") + "-"+curDate.format("d") + " 12:00:00";//当日12:00:00
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+preDate.format("d") + " 12:00:00";//昨日12:00:00
	//获取处理时间（当天和前一天12:00:00），格式例：20140724120000
	var TodFm = curDate.format("Y")+curDate.format("m")+curDate.format("d")+"120000";
	var YsdFm = preDate.format("Y")+preDate.format("m")+preDate.format("d")+"120000";
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnTaddZeroInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instDateTime',mapping: 'instDateTime'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'revsalSsn',mapping: 'revsalSsn'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'transState',mapping: 'transState'},
			{name: 'respCode',mapping: 'respCode'}
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});*/
	
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'instDate',align: 'center',renderer: formatDt,width: 100},
		{header: '交易时间',dataIndex: 'instDateTime',align: 'center',renderer: formatDt,width: 100},
		{header: '卡号',dataIndex: 'pan',align: 'left',width: 150},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 100,align: 'center'},
		{header: '终端流水号',dataIndex: 'termSsn',width: 100,align: 'center'},
//		{id:'cardAccpId',header: '商户号',dataIndex: 'cardAccpId',align: 'center',width: 120},
		{id:'mchtName',header: '商户名',dataIndex: 'mchtName',width: 250,align: 'left'},
//		{header: '系统流水号',dataIndex: 'sysSeqNum',align: 'center',hidden:true},
		{header: '受理机构',dataIndex: 'brhId',align: 'left',width: 180},
		{header: '冲正流水号',dataIndex: 'revsalSsn',align: 'center'},
		{header: '参考号',dataIndex: 'retrivlRef',align: 'center',width: 160},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right',width: 100},
		{id:'txnName',header: '交易类型',dataIndex: 'txnName',hidden:true,align: 'left'},
		{header: '交易结果',dataIndex: 'transState',renderer: txnSta,hidden:true,align: 'center'},
		{header: '交易应答',dataIndex: 'respCode',width: 150}
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','金额上限：',{
					xtype: 'textfield',
					id: 'queryAmtTransUp',
					name: 'queryAmtTransUp',
					vtype:'isMoney',
					width: 150
	               
				},'-','金额下限：',{
					xtype: 'textfield',
					id: 'queryAmtTransLow',
					name: 'queryAmtTransLow',
					vtype:'isMoney',
					width: 150
	               	},'-','交易卡号：',{
					xtype: 'textfield',
					id: 'queryPan',
					 vtype: 'isNumber',
					name: 'queryPan',
					width: 150
				}
	            ]  
         }); 
         
          var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','起始时间：',{
               		xtype: 'datetimefield',
//					format: 'Y-m-d',
//					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 150,
					listeners: {
	                     'change': function(){
	                     	var sd = typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('YmdHis');
	                     	var ed = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('YmdHis');
	                     		if(sd >= YsdFm){
	                     			if(sd > ed){
	                     			showErrorMsg("起始时间不能超过终止时间", tbar3);
	                     			Ext.getCmp('startDate').setValue(timeYesterday);
	                     			}
	                     		}/*else{
	                     			showErrorMsg("时间范围：昨日12点~今日12点", tbar3);
	                     			Ext.getCmp('startDate').setValue(timeYesterday);
	                     		}*/
	                    	}
	                    }
	               	},'-','终止时间：',{
					xtype: 'datetimefield',
//					format: 'Y-m-d',
//					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeToday,
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 150,
					listeners: {
	                     'change': function(){
	                     	var sd = typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('YmdHis');
	                     	var ed = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('YmdHis');
	                     		if(ed <= TodFm){
	                     			if(sd > ed){
	                     			showErrorMsg("起始时间不能超过终止时间", tbar2);
	                     			Ext.getCmp('endDate').setValue(timeToday);
	                     			}
	                     		}/*else{
	                     			showErrorMsg("时间范围：昨日12点~今日12点", tbar2);
	                     			Ext.getCmp('endDate').setValue(timeToday);
	                     		}*/
	                    	}
	                    }
	               	}
				,'-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					 vtype: 'isNumber',
					width: 150
				},'-','终端流水：',{
					xtype: 'textfield',
					id: 'queryTermSsn',
					name: 'queryTermSsn',
					 vtype: 'isNumber',
					width: 150
				}]  
         }); 
	
          var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','商户编号：'
				,{
//					xtype: 'combo',
//					store : mchtStore,
					xtype : 'dynamicCombo',
					methodName : 'getAllMchntId',
					hiddenName: 'queryCardAccpId',
					width: 372,
					editable: true,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true
				},'-','受理机构：'
				,{
//					xtype: 'basecomboselect',
//				    baseParams: 'BRH_BELOW_BRANCH',
					xtype : 'dynamicCombo',
					methodName : 'getBrhId',
					id: 'idqueryBrhId',
					hiddenName: 'queryBrhId',
				    editable: true,
				    mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true,
					width: 372
					
				}
	            ]  
         }); 
	
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
		tbar: [/*{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",txnGrid);
				if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
				Ext.Ajax.request({
					url: 'T50107Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
						queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
						queryBrhId: Ext.get('queryBrhId').getValue(),
						queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
						queryPan: Ext.getCmp('queryPan').getValue(),
						queryTermSsn: Ext.get('queryTermSsn').getValue(),
						queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
						queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),
						queryTxnName: Ext.get('queryTxnName').getValue(),
						
						queryTransState: Ext.get('queryTransState').getValue(),
						
						
						txnId: '50107',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,txnGrid);
						}
					},
					failure: function(){
						hideMask();
						showErrorMsg(rspObj.msg,txnGrid);
					}
				});
//				txnGridStore.load();
			    } else {
			    	hideMask();
			    	Ext.MessageBox.show({
						msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
						title: '报表下载说明',
						buttons: Ext.MessageBox.OK,
						modal: true,
						width: 220
					});
				}
			}},'-',*/{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
//			 		query.hide();
/*					var sd = typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('YmdHis');
                 	var ed = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('YmdHis');
			 		T50108.compareDate(sd,ed,function(ret){
			 			if("S" == ret){*/
			 				txnGridStore.load();
/*			 			}else{
			 				showMsg('您本机与服务器系统日期不一致，请校准您本机系统日期！',txnGrid);
			 			}
			 		});*/
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
				Ext.getCmp('idqueryBrhId').setValue('');
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('queryPan').setValue('');
				Ext.getCmp('queryTermSsn').setValue('');
				Ext.getCmp('queryAmtTransLow').setValue('');
				Ext.getCmp('queryAmtTransUp').setValue('');
				
//				Ext.getCmp('idqueryTxnName').setValue('');
//				Ext.getCmp('idqueryTransState').setValue('');
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeToday);
//				txnGridStore.load();
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
				Ext.getCmp('idqueryBrhId').getStore().reload();*/
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar3.render(this.tbar);  
					tbar2.render(this.tbar); 
					tbar1.render(this.tbar); 
                }  ,
             'cellclick':selectableCell,
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
			queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
			queryBrhId: Ext.get('queryBrhId').getValue(),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			queryPan: Ext.getCmp('queryPan').getValue(),
			queryTermSsn: Ext.get('queryTermSsn').getValue(),
			queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
			queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),
//			queryTxnName: Ext.get('queryTxnName').getValue(),			
//			queryTransState: Ext.get('queryTransState').getValue(),
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('YmdHis'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('YmdHis')
		
		});
		
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});