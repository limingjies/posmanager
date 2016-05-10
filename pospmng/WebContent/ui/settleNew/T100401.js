Ext.onReady(function() {
	
	var zthGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblZthNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'msgSrcId',mapping: 'msgSrcId'},
			{name: 'msgDestId',mapping: 'msgDestId'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'rspcode',mapping: 'rspcode'},
			{name: 'rspdsp',mapping: 'rspdsp'},
			{name: 'f60',mapping: 'f60'},
			{name: 'f11Pos',mapping: 'f11Pos'},
			{name: 'f2',mapping: 'f2'},
			{name: 'f4',mapping: 'f4'},
			{name: 'nfee',mapping: 'nfee'},
			{name: 'samt',mapping: 'samt'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'eflag',mapping: 'eflag'},
			{name: 'ddate',mapping: 'ddate'},
			{name: 'f22',mapping: 'f22'},
			{name: 'f37Pos',mapping: 'f37Pos'},
			{name: 'f49',mapping: 'f49'},
			{name: 'f41',mapping: 'f41'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'f41Sy',mapping: 'f41Sy'},
			{name: 'f42Sy',mapping: 'f42Sy'},
			{name: 'oAmt',mapping: 'oAmt'},
			{name: 'oF60',mapping: 'oF60'},
			{name: 'oF11',mapping: 'oF11'},
			{name: 'oF13',mapping: 'oF13'},
			{name: 'oF12',mapping: 'oF12'},
			{name: 'oF22',mapping: 'oF22'},
			{name: 'oF37Pos',mapping: 'oF37Pos'},
			{name: 'oF37Sy',mapping: 'oF37Sy'},
			{name: 'oF49',mapping: 'oF49'},
			{name: 'bak1',mapping: 'bak1'}
		]),
		autoLoad: true
	});
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblZthDtlNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'settlmtDate',mapping: 'settlmtDate'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'amt',mapping: 'amt'}
		])
	});
	
	var detailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '退款日期',dataIndex: 'settlmtDate',align: 'center',renderer: formatDt,width: 120},
		{header: '退款金额',dataIndex: 'amt',align: 'right',width: 120}
	]);
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table  width=1000>',
				'<tr>',
				'<td><font color=blue>终端号-上游：</font>{f41Sy}</td>',
				'<td><font color=blue>商户号-上游：</font>{f42Sy}</td>',
				'<td><font color=blue>源进程ID：</font>{msgSrcId}</td>',
				'<td><font color=blue>目的进程ID：</font>{msgDestId}</td>',
				'</tr><tr>',
				'<td><font color=blue>#60-pos：</font>{f60}</td>',
				'<td><font color=blue>终端流水号：</font>{f11Pos}</td>',
				'<td><font color=blue>#22：</font>{f22}</td>',
				'<td><font color=blue>#37应答pos：</font>{f37Pos}</td>',
				'</tr><tr>',
				'<td><font color=blue>#49：</font>{f49}</td>',
				'<td><font color=blue>原交易金额：</font>{oAmt}</td>',
				'<td><font color=blue>原交易#60-pos：</font>{oF60}</td>',
				'<td><font color=blue>原交易终端流水号：</font>{oF11}</td>',
				'</tr><tr>',
				'<td><font color=blue>原交易日期：</font>{oF13:this.formatDate}</td>',
				'<td><font color=blue>原交易时间：</font>{oF12:this.formatDate}</td>',
				'<td><font color=blue>原交易#22：</font>{oF22}</td>',
				'<td><font color=blue>原交易#37-应答pos：</font>{oF37Pos}</td>',
				'</tr><tr>',
				'<td><font color=blue>原交易#37-上游：</font>{oF37Sy}</td>',
				'<td><font color=blue>原交易#49：</font>{oF49}</td>',
				'<td><font color=blue>应答码描述信息：</font>{rspdsp}</td>',
				'<td><font color=blue>备用信息：</font>{bak1}</td>',
				'</tr>',
			'</table>',
			{
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
	
	var zthColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '准退货日期',dataIndex: 'instDate',align: 'center',width: 80,renderer: formatDt},
		{header: '准退货时间',dataIndex: 'instTime',align: 'center',width: 80,renderer: formatDt},
		{header: '准退货流水号',dataIndex: 'sysSeqNum',align: 'center',width: 80},
		{header: '交易码',dataIndex: 'txnNum',align: 'center',width: 50},
		{header: '应答码',dataIndex: 'rspcode',align: 'center',width: 50},
		{header: '终端号',dataIndex: 'f41',align: 'center',width: 75},
		{header: '商户编号',dataIndex: 'mchtNoNm',align: 'left',width: 250},
		{header: '卡号',dataIndex: 'f2',align: 'center',width: 140},
		{header: '退货本金',dataIndex: 'f4',align: 'right',width: 80},
		{header: '退货手续费',dataIndex: 'nfee',align: 'right',width: 90},
		{header: '退还金额',dataIndex: 'samt',align: 'right',width: 80},
		{header: '已退金额',dataIndex: 'amt1',align: 'right',width: 80},
		{header: '退货状态',dataIndex: 'eflag',align: 'center',width: 70,renderer: function(val){
			if(val == '0'){
				return '<font color="grey">未退货</font>';
			}else if(val == '1'){
				return '<font color="green">完成退货</font>';
			}else if(val == '2'){
				return '<font color="blue">部分退货</font>';
			}else {
				return '<font color="red">未知状态</font>';
			}
		}},
		{header: '准退货完成日期',dataIndex: 'ddate',align: 'center',width: 100,renderer: formatDt}/*,
		{header: '终端号-上游',dataIndex: 'f41Sy',align: 'center',width: 80},
		{header: '商户号-上游',dataIndex: 'f42Sy',align: 'center',width: 120},
		{header: '源进程ID',dataIndex: 'msgSrcId',align: 'center',width: 80},
		{header: '目的进程ID',dataIndex: 'msgDestId',align: 'center',width: 80},
		{header: '#60-pos',dataIndex: 'f60',align: 'center',width: 120},
		{header: '终端流水号',dataIndex: 'f11Pos',align: 'center',width: 80},
		{header: '#22',dataIndex: 'f22',align: 'center',width: 80},
		{header: '#37应答pos',dataIndex: 'f37Pos',align: 'center',width: 120},
		{header: '#49',dataIndex: 'f49',align: 'center',width: 80},
		{header: '原交易金额',dataIndex: 'oAmt',align: 'right',width: 80},
		{header: '原交易#60-pos',dataIndex: 'oF60',align: 'center',width: 120},
		{header: '原交易终端流水号',dataIndex: 'oF11',align: 'center',width: 110},
		{header: '原交易日期',dataIndex: 'oF13',align: 'center',width: 80,renderer: formatDt},
		{header: '原交易时间',dataIndex: 'oF12',align: 'center',width: 80,renderer: formatDt},
		{header: '原交易#22',dataIndex: 'oF22',align: 'center',width: 80},
		{header: '原交易#37-应答pos',dataIndex: 'oF37Pos',align: 'center',width: 120},
		{header: '原交易#37-上游',dataIndex: 'oF37Sy',align: 'center',width: 100},
		{header: '原交易#49',dataIndex: 'oF49',align: 'center',width: 80},
		{header: '应答码描述信息',dataIndex: 'rspdsp',align: 'center',width: 100},
		{header: '备用信息',dataIndex: 'bak1',align: 'center',width: 100}*/
	]);
	
	
	var tbar2 = new Ext.Toolbar({  
	    renderTo: Ext.grid.EditorGridPanel.tbar,  
	    items:['-','准退货起始日期：',{
		   		xtype: 'datefield',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				endDateField: 'endDate',
				editable: false,
				id: 'startDate',
				name: 'startDate',
				width: 120
	       	},'-','终端编号：',{
				xtype: 'textfield',
				name: 'queryCardAccpTermId',
				id: 'queryCardAccpTermId',
				 vtype: 'isNumber',
				width: 120
			},'-','交易卡号：',{
				xtype: 'textfield',
				id: 'queryPan',
				 vtype: 'isNumber',
				name: 'queryPan',
				width: 120
			},'-','准退货流水号：',{
				xtype: 'textfield',
				id: 'querySysSeqNum',
				 vtype: 'isNumber',
				name: 'querySysSeqNum',
				width: 120
			}
		]  
	}) 
         
	var tbar3 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','准退货结束日期：',{
				xtype: 'datefield',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				vtype: 'daterange',
				startDateField: 'startDate',
				editable: false,
				id: 'endDate',
				name: 'endDate',
				width: 120
	       	},'-','商户编号：',{
				xtype : 'dynamicCombo',
				methodName : 'getAllMchntId',
				hiddenName: 'queryCardAccpId',
				width: 312,
				id: 'idqueryCardAccpId',
				editable: true,
				lazyRender: true
			},'-','退&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;状&nbsp;&nbsp;态：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未退货'],['1','完成退货'],['2','部分退货']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryEflag',
				editable: false,
				id:'idqueryEflag',
				width: 120
			}
		]
	})
	
	var detailGrid = new Ext.grid.GridPanel({
		title: '准退货明细',
		region: 'east',
		width: 300,
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
		loadMask: {
			msg: '正在加载准退货明细列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	var zthGrid = new Ext.grid.EditorGridPanel({
		region:'center',
//		autoExpandColumn:'mchtName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: zthGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: zthColModel,
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
				zthGridStore.load();
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
				Ext.getCmp('querySysSeqNum').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('idqueryEflag').setValue('');
				zthGridStore.reload();
				Ext.getCmp('idqueryCardAccpId').getStore().reload();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
        	'render' : function() {
				tbar2.render(this.tbar); 
				tbar3.render(this.tbar);
            }  
        },
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: zthGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	zthGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(zthGrid.getView().getRow(zthGrid.getSelectionModel().last)).frame();
			detailGridStore.load({
				params: {
					start: 0,
					instDate: zthGrid.getSelectionModel().getSelected().data.instDate,
					sysSeqNum: zthGrid.getSelectionModel().getSelected().data.sysSeqNum
				}
			});
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			instDate: zthGrid.getSelectionModel().getSelected().data.instDate,
			sysSeqNum: zthGrid.getSelectionModel().getSelected().data.sysSeqNum
			
		});
	});
	
	zthGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			queryPan: Ext.getCmp('queryPan').getValue(),
			querySysSeqNum: Ext.getCmp('querySysSeqNum').getValue(),
			queryCardAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
			queryEflag: Ext.getCmp('idqueryEflag').getValue()
		});
		detailGridStore.removeAll();
	});
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [zthGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});