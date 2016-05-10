/**
 * 通道应到账报表
 * 检索条件：通道列表，清算日期（区间）
 * 列表字段：通道名称,清算日期，交易金额,通道手续费,差错金额,应到金额
 */

Ext.onReady(function() {
	// 数据集
	var storeChlTrans = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getChlTrans'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'chlId',mapping: 'chlId'},
			{name: 'chlName',mapping: 'chlName'},
			{name: 'settleDate',mapping: 'settleDate'},
			{name: 'amtTransBal',mapping: 'amtTransBal'},
			{name: 'amtTransErr',mapping: 'amtTransErr'},
			{name: 'poundageBal',mapping: 'poundageBal'},
			{name: 'poundageErr',mapping: 'poundageErr'},
			{name: 'amtRecv',mapping: 'amtRecv'}		
		]),
		autoLoad: true
	}); 
	
	
	var colModelChlTrans = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),	
		{header: '<center>清算日期</center>',dataIndex: 'settleDate',width: 150,align: 'center',renderer:formatDt},
		{header: '<center>通道名称</center>',dataIndex: 'chlName',width: 200,align: 'left'},
		{header: '<center>对平交易金额</center>',dataIndex: 'amtTransBal',width: 150,align: 'right'},
		{header: '<center>差错交易金额</center>',dataIndex: 'amtTransErr',width: 150,align: 'right'},
		{header: '<center>对平通道手续费</center>',dataIndex: 'poundageBal',width: 150,align: 'right'},
		{header: '<center>差错通道手续费</center>',dataIndex: 'poundageErr',width: 150,align: 'right'},
		{header: '<center>应到金额</center>',dataIndex: 'amtRecv',width: 150,align: 'right'}
	]);  
	
    // 查询条件
	var tbarQueryFileds = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
          '-','通道机构号：',{
				xtype: 'basecomboselect',
				baseParams: 'TRANS_CHANNEL_BRH',
				id: 'idQueryInstCode',
				hiddenName: 'queryInstCode',
				editable: true,
				width: 140
		   	},'-','清算日期：',{
	       		xtype: 'datefield',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				endDateField: 'endDate',
				editable: true,
				id: 'startDate',
				name: 'startDate',
				width: 120
           	},'—',{
				xtype: 'datefield',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				startDateField: 'startDate',
				editable: true,
				id: 'endDate',
				name: 'endDate',
				width: 120
           	}
        ]  
	}); 
	
    var gridChlTrans = new Ext.grid.GridPanel({
		width:460,
		title: '通道应到账报表',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		sm : new Ext.grid.RowSelectionModel({singleSelect : true}),
		store: storeChlTrans,
		cm: colModelChlTrans,
		forceValidation: true,
		loadMask: {
			msg: '正在加载通道清算记录列表......'
		},
		tbar: [{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",gridChlTrans);
				if (storeChlTrans.getTotalCount() < System[REPORT_MAX_COUNT]){
					Ext.Ajax.request({
						url: 'T80807Action.asp',
						timeout: 60000,
						params: {
							startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
							endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
							queryInstCode: Ext.get('queryInstCode').getValue(),							
							txnId: '80807',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg,gridChlTrans);
							}
						},
						failure: function(){
							hideMask();
							showErrorMsg(rspObj.msg,gridChlTrans);
						}
					});
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
					storeChlTrans.load();
			    }
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('idQueryInstCode').setValue('');
					Ext.getCmp('startDate').setValue();
					Ext.getCmp('endDate').setValue();
				}
			}],
		listeners : { 
            'render' : function() {  
            		tbarQueryFileds.render(this.tbar);
                } ,
			'cellclick':selectableCell
        },
		bbar: new Ext.PagingToolbar({
			store: storeChlTrans,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
    gridChlTrans.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridChlTrans.getView().getRow(gridChlTrans.getSelectionModel().last)).frame();
		}
	});
    
	storeChlTrans.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryInstCode: Ext.get('queryInstCode').getValue()
		});
		this.removeAll();
	});
	
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridChlTrans],
		renderTo: Ext.getBody()
	});
});

