Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=firstMccCount2'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnKey',mapping: 'txnKey'},
			{name: 'settLmt',mapping: 'settLmt'},
			{name: 'txnCount',mapping: 'txnCount'},
			{name: 'txnAmt',mapping: 'txnAmt'}
		]),
		autoLoad: true
	});
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '一级商户号',dataIndex: 'txnKey',align: 'left',width: 300},
		{header: '交易时间',dataIndex: 'settLmt',align: 'left',width: 150,renderer : formatDt},
		{header: '总笔数',dataIndex: 'txnCount',align: 'left',width: 180},
		{header: '总金额',dataIndex: 'txnAmt',align: 'left',width: 180}
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['-','交易起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','交易结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeYesterday,
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	},'-',	'计费代码：',{
					xtype: 'basecomboselect',
					id: 'idqueryTxnName',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnName',
					width: 120
				    }
				]  
         }) 
         
          var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['-','清算起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','清算结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeYesterday,
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	}
				]  
         }) 
	
	
          var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
              			'-','一级商户号：',{
              				xtype: 'basecomboselect',
              				id: 'queryTxnKey',
              				baseParams: 'routeFirstMchtCd',
              				hiddenName: 'queryFirstMchtCd',
              				mode:'local',
              				triggerAction: 'all',
              				editable: true,
              				lazyRender: true,
              				width: 250
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
		tbar: [{
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
					url: 'T50403Action.asp',
					timeout: 60000,
					params: {
						queryTxnKey: Ext.get('queryTxnKey').getValue().substring(3,15)
						
				
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
			}},'-',{
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
				Ext.getCmp('queryTxnKey').setValue('');
			
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	 // 	tbar3.render(this.tbar);  
					//tbar2.render(this.tbar);
					tbar1.render(this.tbar);
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
			queryTxnKey: Ext.get('queryTxnKey').getValue().substring(3,15)
		});
		
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});