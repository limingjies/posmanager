Ext.onReady(function() {
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTmpInfoQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[	
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'engName',mapping: 'engName'},
			{name: 'bankNo',mapping: 'bankNo'},
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
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name:'reserved',mapping:'reserved'}
		])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
//			'<p>商户英文名称：{engName}</p>',
//			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
//			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>备注：<font color="red">{reserved}</font></p>',
			{
//			getmcc : function(val){return getRemoteTrans(val, "mcc");}
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
	new Ext.grid.RowNumberer(),
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 140,hidden :true},
		
		{header: '商户名称',dataIndex: 'mchtNm',width: 250,id: 'mchtNm',align: 'left'},
		{header: '法人代表',dataIndex: 'manager',width: 100,align: 'left'},
		{header: '签约机构',dataIndex: 'bankNo',width: 180,align: 'left'},
		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtLvl,hidden:true},
		{header: '税务登记证号码',dataIndex: 'faxNo',width: 120,align: 'center'},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 120,align: 'center'},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 100,renderer: mchntSt,align: 'center'},
		{header: '申请日期',dataIndex: 'recCrtTs',width: 100,renderer: formatDt,align: 'center'},
		{header: '申请人',dataIndex: 'crtOprId',width: 100,align: 'left'}
	]);
	
	/**
	 * 商户等级
	 */
	function mchtLvl(val) {
		switch(val) {
			
			
			case '0' : return '普通商户';
			case '1' : return '一级商户';
			case '2' : return '二级商户';
			case '3' : return '网上支付商户';
			case '': return '未定级';
		}
	}


	/***************************查询条件*************************/
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		'-','商户名称:',	{
					xtype: 'textfield',
					id: 'queryMchtNm',
					name: 'queryMchtNm',
//					vtype:'isNumber',
					maxLength: 20,
					width: 140
				},'-','营业执照编号:',	{
					xtype: 'textfield',
					id: 'queryLicenceNo',
					name: 'queryLicenceNo',
//					vtype:'isNumber',
					vtype: 'alphanum',
					maxLength: 20,
					width: 140
				},'-','税务登记证号码:',	{
					xtype: 'textfield',
					id: 'queryFaxNo',
					name: 'queryFaxNo',
//					vtype:'isNumber',
					vtype: 'alphanum',
					maxLength: 20,
					width: 140
				},'-','法人代表:',	{
					xtype: 'textfield',
					id: 'queryManager',
					name: 'queryManager',
//					vtype:'isNumber',
//					vtype: 'alphanum',
					maxLength: 10,
					width: 140
				}
	            ]  
         });
	
	
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		'-','注册起始日期:',	{
					xtype: 'datefield',
					id: 'queryStartDate',
					name: 'queryStartDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					endDateField: 'queryEndDate',
					editable: false,
					width: 140
				},'-','注册结束日期:',	{
					xtype: 'datefield',
					id: 'queryEndDate',
					name: 'queryEndDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					startDateField: 'queryStartDate',
					editable: false,
					width: 140 
				},'-','商户状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','审核通过'],['B','添加待初审'],['1','添加待终审'],['A','添加初审退回'],['2','添加终审退回'],['C','拒绝']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					editable: false,
					emptyText: '请选择',
					hiddenName: 'queryMchtStatus',
					id:'queryMchtStatusId',
					width: 140
				},'-',	'签约机构：',{
					xtype: 'basecomboselect',
					id: 'queryBankNoId',
					emptyText: '请选择',
					baseParams: 'BRH_BELOW_BRANCH',
					hiddenName: 'queryBankNo',
					mode:'local',
					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true,
					width: 140
				}	,'-','申请人:',	{
					xtype: 'textfield',
					id: 'queryCrtOprId',
					name: 'queryCrtOprId',
//					vtype:'isNumber',
					vtype: 'alphanum',
					maxLength: 10,
					width: 140
				}
	            ]  
         });

	
	
	
	
	
	
	
	
	var mchntGrid = new Ext.grid.GridPanel({
//		title: '商户信息查询',
		title: '商户信息查询',
		region: 'center',
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
		tbar: [{
			
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
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				
				
				
				Ext.getCmp('queryMchtNm').setValue('');
				Ext.getCmp('queryLicenceNo').setValue('');
				Ext.getCmp('queryFaxNo').setValue('');
				Ext.getCmp('queryManager').setValue('');
				
				Ext.getCmp('queryStartDate').setValue('');
				Ext.getCmp('queryEndDate').setValue('');
				Ext.getCmp('queryCrtOprId').setValue('');
				Ext.getCmp('queryMchtStatusId').setValue('');
				Ext.getCmp('queryBankNoId').setValue('');
			}

		
		
		},'-',{
			
			xtype: 'button',
			text: '查看详细信息',
			name: 'detail',
			id: 'detail',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
			}
		}],
		listeners : {     
			'cellclick':selectableCell,
			//將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  
        }  ,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load({
		params:{start: 0}
	});
	
//	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
//		
//	});
//	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
//		mchntGrid.getTopToolbar().items.items[0].disable();
		Ext.getCmp('detail').disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
//			mchntGrid.getTopToolbar().items.items[0].enable();
			Ext.getCmp('detail').enable();
		}
	});
	

	
	
	
	

	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNm: Ext.getCmp('queryMchtNm').getValue(),
			queryLicenceNo: Ext.getCmp('queryLicenceNo').getValue(),
			queryFaxNo: Ext.getCmp('queryFaxNo').getValue(),
			queryManager: Ext.getCmp('queryManager').getValue(),
			
			queryStartDate: typeof(Ext.getCmp('queryStartDate').getValue()) == 'string' ? '' : Ext.getCmp('queryStartDate').getValue().dateFormat('Ymd'),
			queryEndDate: typeof(Ext.getCmp('queryEndDate').getValue()) == 'string' ? '' : Ext.getCmp('queryEndDate').getValue().dateFormat('Ymd'),
			
			queryCrtOprId: Ext.getCmp('queryCrtOprId').getValue(),
			queryMchtStatus: Ext.get('queryMchtStatus').getValue(),
			queryBankNo: Ext.get('queryBankNo').getValue()
			
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});