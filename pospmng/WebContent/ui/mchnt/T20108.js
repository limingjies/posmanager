Ext.onReady(function() {
	
	// 商户MCC数据集
	var mchntMccStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCC', function(ret) {
				mchntMccStore.loadData(Ext.decode(ret));
			});
			
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntOpenInfoQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'mchtNo'
		},[	
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'termTmpCount',mapping: 'termTmpCount'},
			{name: 'refuseReason',mapping: 'refuseReason'}
		]),
		autoLoad: true
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=500>',
		'<tr><td><font color=green>商户MCC：</font>{mcc:this.getmcc()}</td>',
		'<td><font color=green>商户地址：</font>{addr}</td></tr>',
		'<tr><td><font color=green>邮编：</font>{postCode}</td>',
		'<!--<td><font color=green>商户英文名称： </font>{engName}</td>--></tr>',
		'<tr><td><font color=green>电子邮件：</font>{commEmail}</td>',
		
		'<td><font color=green>法人代表名称：</font>{manager}</td></tr>',
		'<tr><td><font color=green>联系人姓名：</font>{contactu}</td>',
		'<td><font color=green>联系人电话：</font>{commTel}</td></tr>',
		'<tr><td><font color=green>录入柜员：</font>{crtOprId}</td>',
		'<td><font color=green>审核柜员：</font>{updOprId}</td></tr>',
		
		'<tr><td><font color=green>注册日期：</font>{recCrtTs:this.dateFromt}</td>' ,
		'<td><font color=green>最近更新时间：</font>{updTs:this.dateFromt}</td>',
		'</tr>',
		'<tr><td><font color=green>最近一次拒绝原因：</font>{refuseReason}</td>' ,
		'</tr>',
	
		
		'</table>',	
			/*'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',*/{
			getmcc : function(val){return getRemoteTrans(val, "mcc");},
			dateFromt: function(val) {
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
								+ val.substring(10, 12) + ':' + val.substring(12, 14);
					} else if (val.length == 10) {
						return val.substring(0, 2) + '-' + val.substring(2, 4) + ' '
								+ val.substring(4, 6) + ':' + val.substring(6, 8) + ':'
								+ val.substring(8, 10);
				
					} else if(val.length == 8){
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8);
					}else if(val.length == 6){
						return val.substring(0, 2) + ':' + val.substring(2, 4) + ':'
								+ val.substring(4, 6);
					}else if(val.length == 4){
						return val.substring(0, 2) + ':' + val.substring(2, 4) ;
					}else {
						return val;
					}
				}	
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 130},
		
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,id: 'mchtNm'},
//		{header: '商户等级',dataIndex: 'mchtLvl',width: 80,id: 'mchtLvl',align: 'center',renderer:mchtLvl},
		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtGroupFlag},
		
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 130},
//		{header: '注册日期',dataIndex: 'recCrtTs',width: 80,renderer: formatDt},
		{header: '风险级别',dataIndex: 'rislLvl',width: 80},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt,width: 90},
		{header: '终端数量',dataIndex: 'termCount',width: 60},
		{header: '终端添加状态',dataIndex: 'termTmpCount',width: 90,renderer: termAddSta,align: 'center'}
	]);
	
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息查询',
		region: 'center',
		iconCls: 'T10403',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				mchntStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '存量开户',
			name: 'detail',
			id: 'detail',
			iconCls: 'edit',
			width: 80,
			handler:function(bt) {

				showConfirm('确定要存量开户吗？', mchntGrid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								showProcessMsg('正在提交，请稍后......');
								Ext.Ajax.request({
									url : 'T20108Action.asp?method=openAcct',
									timeout: 60000,
									params : {
										txnId : '20108',
										subTxnId : '01'
									},
									success : function(rsp, opt) {
										hideProcessMsg();
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessAlert(rspObj.msg, mchntGrid);
										} else {
											showErrorMsg(rspObj.msg, mchntGrid);
										}
										mchntGrid.getStore().reload();
									}
								});
							}
						})
			}
		}],
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
		}
	});
	
	
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});