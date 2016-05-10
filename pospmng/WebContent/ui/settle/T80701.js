Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=errDtlInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'settlmtDate',mapping: 'settlmtDate'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'transDateTime',mapping: 'transDateTime'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'brhIdNm',mapping: 'brhIdNm'},
			{name: 'termId',mapping: 'termId'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'pospPan',mapping: 'pospPan'},
			{name: 'instPan',mapping: 'instPan'},
			{name: 'pospAmtTrans',mapping: 'pospAmtTrans'},
			{name: 'instAmtTrans',mapping: 'instAmtTrans'},
			{name: 'fee',mapping: 'fee'},
			{name: 'stlmFlag',mapping: 'stlmFlag'}
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});*/
	
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'settlmtDate',align: 'center',renderer: formatDt,width: 120},
		{header: '通道机构号',dataIndex: 'instCode',align: 'center',width: 100},
		{header: '交易传输时间',dataIndex: 'transDateTime',align: 'center',renderer: formatDt,width: 150},
		{header: '商户名',dataIndex: 'mchtNoNm',width: 250,align: 'left'},
		{header: '受理机构',dataIndex: 'brhIdNm',align: 'left',width: 200},
		{header: '终端号',dataIndex: 'termId',align: 'center',width: 100},
		{header: '交易类型',dataIndex: 'txnName',align: 'left',width: 120},
		{header: '系统流水号',dataIndex: 'sysSeqNum',align: 'center'},
		{header: '收单卡号',dataIndex: 'pospPan',align: 'center',width: 150},
		{header: '通道卡号',dataIndex: 'instPan',align: 'center',width: 150},
		{header: '收单交易金额',dataIndex: 'pospAmtTrans',align: 'right',width: 100},
		{header: '通道交易金额',dataIndex: 'instAmtTrans',align: 'right',width: 100},
		{header: '手续费成本',dataIndex: 'fee',align: 'right'},
		{header: '差错描述',dataIndex: 'stlmFlag',align: 'left',renderer: stlmFlag,width: 250}
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
    
	function stlmFlag(val) {
        if(val == '1')
            return "posp<font color='green'>成功</font>，通道机构<font color='red'>失败</font>";
        if(val == '2')
            return "posp<font color='red'>失败</font>，通道机构<font color='green'>成功</font>";
        if(val == '3')
            return "posp与通道机构金额不一致";
        return val;
    }
    
	  var tbar3 = new Ext.Toolbar({  
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
					id: 'startDate',
					name: 'startDate',
					width: 120
		           	},'—',{
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
		           	},'-','商户编号：'
					,{
						xtype: 'dynamicCombo',
						methodName: 'getMchntNoAll',
						fieldLabel: '商户编号',
						id: 'idqueryMchtNoNm',
						hiddenName: 'queryMchtNoNm',
						editable: true,
						width: 300
					},'-','通道机构号：',{
					xtype: 'basecomboselect',
					baseParams: 'SETTLE_BRH',
					id: 'idqueryInstCode',
					hiddenName: 'queryInstCode',
					editable: false,
					width: 140
		           	}]  
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
					url: 'T80701Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
						queryMchtNoNm: Ext.get('queryMchtNoNm').getValue(),
						queryInstCode: Ext.get('queryInstCode').getValue(),
						
						txnId: '80701',
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
				Ext.getCmp('idqueryMchtNoNm').setValue('');
				Ext.getCmp('idqueryInstCode').setValue('');
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
//				txnGridStore.load();
				
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar3.render(this.tbar);
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
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryMchtNoNm: Ext.get('queryMchtNoNm').getValue(),
			queryInstCode: Ext.get('queryInstCode').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});