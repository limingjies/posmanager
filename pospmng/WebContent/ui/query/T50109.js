Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var infileGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtInfileDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateSettlmt',mapping: 'dateSettlmt'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNoName',mapping: 'mchtNoName'},
			{name: 'brhIdName',mapping: 'brhIdName'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'settleFee',mapping: 'settleFee'},
			{name: 'channelNm',mapping: 'channelNm'}
		]),
		autoLoad: true
	});
//	infileGridStore.load({
//		params:{start: 0}
//	});
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=detailMchtInfileDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transDateTime',mapping: 'transDateTime'},
			{name: 'pan',mapping: 'pan'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'termId',mapping: 'termId'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'settleAmt',mapping: 'settleAmt'},
			{name: 'settleFee',mapping: 'settleFee'}
			
			
		])
	});
	
	
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>批次号：<font color=green>1</font></p>',
			'<p>商户所属机构：<font color=green>{brhIdName}</font></p>',
			'<p>交易起始日期：<font color=green>{startDate}</font></p>',
			'<p>交易结束日期：<font color=green>{endDate}</font></p>'
		)
	});
	
	
	var detailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		rowExpander,
		{header: '交易日期',dataIndex: 'transDate',align: 'center',renderer: formatDt,width: 80},
		{header: '交易时间',dataIndex: 'transDateTime',align: 'center',renderer: formatDt,width: 80},
		{header: '卡号',dataIndex: 'pan',align: 'center',width: 120},
		{header: '交易类型',dataIndex: 'txnNum',align: 'center',width: 100},
		{header: '终端号',dataIndex: 'termId',align: 'center',width: 80},
		{header: '终端流水号',dataIndex: 'termSsn',align: 'center',width: 80},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'right',width: 100},
		{header: '手续费',dataIndex: 'settleFee',align: 'right',width: 80},
		{header: '清算金额',dataIndex: 'settleAmt',align: 'right',width: 100}
	]);
	
	
	
	
	var infileColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateSettlmt',align: 'center',renderer: formatDt,width: 80},
//		{header: '批次号',dataIndex: 'batchNo',align: 'center',width: 60,hidden:true},
		{header: '清算商户',dataIndex: 'mchtNoName',id:'mchtNoName',width: 250},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'right',width: 100},
		{header: '手续费',dataIndex: 'settleFee',align: 'right',width: 80},
		{header: '清算金额',dataIndex: 'settleAmt',align: 'right',width: 100},
		{header: '交易通道',dataIndex: 'channelNm',width: 90,align: 'center'}
//		{header: '交易起始日期',dataIndex: 'startDate',align: 'center',renderer: formatDt,width: 80,hidden:true},
//		{header: '交易结束日期',dataIndex: 'endDate',align: 'center',renderer: formatDt,width: 80,hidden:true}
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               	'-','清算起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					editable: false,
					value:timeYesterday,
					id: 'startDate',
					name: 'startDate'
	             },'-','清算结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					editable: false,
					value:timeYesterday,
					id: 'endDate',
					name: 'endDate'
	             },'-',{
	            		text: '下载所有明细',
	            		xtype: 'button',
	            		width: 85,
	            		iconCls: 'download',
	            		disabled: false,
	            		handler:function() {
	            			var startDate =typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd');
	        				var	endDate = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd');
	        				if(!startDate){
	        					Ext.MessageBox.show({
	        						msg: '清算起始日期:不可为空！',
	        						title: '错误提示',
	        						buttons: Ext.MessageBox.OK,
	        						icon: Ext.MessageBox.ERROR,
	        						modal: true,
	        						width: 250
	        					});
	        				}
	        				if(!endDate){
	        					Ext.MessageBox.show({
	        						msg: '清算结束日期:不可为空！',
	        						title: '错误提示',
	        						buttons: Ext.MessageBox.OK,
	        						icon: Ext.MessageBox.ERROR,
	        						modal: true,
	        						width: 250
	        					});
	        				}
	            			Ext.Ajax.request({
	            					url: 'T5010903Action.asp',
	            					timeout: 60000,
	            					params: {
	            						'startDate': startDate,
	            						'endDate': endDate,
	            						txnId: '5010903',
	            						subTxnId: '01'
	            					},
	            					success: function(rsp,opt) {
	            						var rspObj = Ext.decode(rsp.responseText);
	            						if(rspObj.success) {
	            							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
	            						} else {
	            							showErrorMsg(rspObj.msg,infileGrid);
	            						}
	            					},
	            					failure: function(){
	            						showErrorMsg("操作失败！",infileGrid,function(){});
	            					}
	            				});
	            		}
	             	}
	            ]  
         }); 
      var tbar1 = new Ext.Toolbar({  
            renderTo: Ext.grid.EditorGridPanel.tbar,  
            items:[
            '-','商户编号：',{
				xtype : 'dynamicCombo',
				methodName : 'getAllMchntId',
				hiddenName: 'mchtNo',
				width: 323,
				id: 'idMchtNo',
				mode:'local',
				triggerAction: 'all',
				forceSelection: true,
				selectOnFocus: true,
				editable: true,
				lazyRender: true
		    } 
            ]  
     }); 
     
      var tbar3 = new Ext.Toolbar({  
            renderTo: Ext.grid.EditorGridPanel.tbar,  
            items:[
            '-','合作伙伴：',{
				xtype : 'dynamicCombo',
				methodName : 'getBrhId',
				hiddenName: 'queryBrhId',
				width: 323,
				id: 'idqueryBrhId',
				editable: true,
				lazyRender: true
		    } 
            ]  
     });
     var detailMenu = {
		text: '结算单明细下载',
		width: 85,
		iconCls: 'download',
		disabled: true,
		handler:function() {
			if(infileGrid.getSelectionModel().getSelected()==null){
				showErrorMsg("请先选择一条结算单！",detailGrid);
			}
			Ext.Ajax.request({
					url: 'T5010901Action.asp',
					timeout: 60000,
					params: {
						mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
						dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt,
						
						txnId: '5010901',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,infileGrid);
					}
				});
		}
	};
	var detailGrid = new Ext.grid.GridPanel({
		title: '结算单明细',
		region: 'east',
		width: 550,
//		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [detailMenu],
		loadMask: {
			msg: '正在加载结算单明细列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell,
		}
	});
	
	
	var infileGrid = new Ext.grid.GridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn: 'mchtNoName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: infileGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: infileColModel,
		clicksToEdit: true,
		plugins: rowExpander,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				if(infileGridStore.getTotalCount()>System[REPORT_MAX_COUNT]){
					showMsg("数据量太大，请过滤条件分批下载！",infileGrid);
					return ;
				}
				Ext.Ajax.request({
					url: 'T50109Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
//						dateSettlmt: Ext.getCmp('dateSettlmt').getValue(),
						mchtNo: Ext.getCmp('idMchtNo').getValue(),
						brhId: Ext.getCmp('idqueryBrhId').getValue(),
						txnId: '50109',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,infileGrid);
					}
				});
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				infileGridStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idMchtNo').setValue('');
				Ext.getCmp('idqueryBrhId').setValue('');
				
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
				
				infileGridStore.reload();
//				Ext.getCmp('idMchtNo').getStore().reload()
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
				Ext.getCmp('idqueryBrhId').getStore().reload();*/
			}

		
		},'-',{
			xtype: 'button',
			text: '确认入账',
			name: 'confirmIn',
			id: 'confirmIn',
			iconCls: 'confirmIn',
			width: 80,
			handler:function() {
				var startDate = typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd');
				var endDate = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd');
				if (startDate != endDate) {
					showErrorMsg('清算起始日期必须等于清算结束日期',infileGrid);
					return;
				}
				showMask("确认入账处理中", infileGrid);
				Ext.Ajax.request({
					url: 'T5010902Action.asp',
					timeout: 60000,
					params: {
						dateSettlmt: startDate,
						txnId: '5010902',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg, infileGrid);
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,infileGrid);
					}
				});
			}
		
		},'-',{
			xtype: 'button',
			text: '下载回盘文件',
			name: 'downloadRediscFile',
			id: 'downloadRediscFile',
			iconCls: 'downloadRediscFile',
			width: 80,
			handler:function() {
				var startDate = typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd');
				var endDate = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd');
				if (startDate != endDate) {
					showErrorMsg('清算起始日期必须等于清算结束日期',infileGrid);
					return;
				}
				showMask("下载回盘文件处理中", infileGrid);
				Ext.Ajax.request({
					url: 'T5010902Action.asp',
					timeout: 60000,
					params: {
						dateSettlmt: startDate,
						txnId: '5010902',
						subTxnId: '02'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,infileGrid);
					}
				});
			}
		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					
			
							tbar2.render(this.tbar); 
							tbar1.render(this.tbar); 
							tbar3.render(this.tbar);
					
                }  ,
            'cellclick':selectableCell,
        }  ,
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: infileGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'

			
		})
//		renderTo: Ext.getBody()
	});
	
	infileGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(infileGrid.getView().getRow(infileGrid.getSelectionModel().last)).frame();
			detailGridStore.load({
				params: {
					start: 0,
					mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
					dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt
				}
			})
				detailGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
			dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt
			
		});
	});

	
	infileGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			mchtNo: Ext.getCmp('idMchtNo').getValue(),
			brhId: Ext.getCmp('idqueryBrhId').getValue()
			
		});
		detailGridStore.removeAll();
		detailGrid.getTopToolbar().items.items[0].disable();
	});
	
	
//			Ext.getCmp('mchtText').show();
			Ext.getCmp('idMchtNo').show();
//			Ext.getCmp('brhText').show();
			Ext.getCmp('idqueryBrhId').show();
			
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [infileGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});