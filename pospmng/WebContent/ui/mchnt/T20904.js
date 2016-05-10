Ext.onReady(function() {
	
	var mchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_TMP_BELOW',function(ret){
		mchtNoStore.loadData(Ext.decode(ret));
	});
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntNetworkCheckInfo'
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
			{name: 'faxNo',mapping: 'faxNo'},
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
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name:'reserved',mapping:'reserved'}
		])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
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
        {header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 100,hidden :true},
		
		{header: '商户名称',dataIndex: 'mchtNm',width: 180,align: 'left'},
		{header: '法人代表',dataIndex: 'manager',width: 100,align: 'left'},
		{header: '合作伙伴',dataIndex: 'bankNo',width: 200,align: 'left'},
		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtLvl,hidden:true},
		{header: '税务登记证号码',dataIndex: 'faxNo',width: 120,align: 'center'},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 120,align: 'center'},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 100,renderer: mchntSt,align: 'center'},
		{header: '申请日期',dataIndex: 'recCrtTs',width: 100,renderer: formatDt,align: 'center'},
		{id: 'mchtNm',header: '申请人',dataIndex: 'crtOprId',width: 100,align: 'left'}
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
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
		
	var acceptMenu = {
		text: '审核',
		iconCls: 'accept',
		width: 85,
		id: 'accept',
		disabled: true,
		handler:function() {
//			showConfirm('确认进行审核通过？',mchntGrid,function(bt) {
//				if(bt == 'yes') {
					Ext.Ajax.request({
						url: 'T20904Action.asp?method=accept',
						params: {
							mchntId: rec.get('mchtNo'),
							txnId: '20904',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/page/mchnt/MchntTmpComplete.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
							} else {
								showErrorMsg(rspObj.msg,mchntGrid);
							}
							// 重新加载商户待审核信息
//							mchntGrid.getStore().reload();
						}
					});
//					hideProcessMsg();
//				}
//			});
		}
	};
	
	
	var detailMenu = {
			text: '查看详细信息',
			width: 85,
			iconCls: 'detail',
			disabled: true,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable()},2000);
				showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);
			}
		};
	
var queryCondition = {
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				mchntStore.load();
			}
		};

var reSet = {
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
//			Ext.getCmp('queryMchtStatusId').setValue('');
			Ext.getCmp('queryBankNoId').setValue('');
		}

	};

	menuArr.push(acceptMenu);  //[0]
	menuArr.push('-');         //[3]
//	menuArr.push(detailMenu);  //[4]
//	menuArr.push('-');         //[5]
	menuArr.push(queryCondition);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(reSet);  //[8]
	
	/***************************查询条件*************************/
	var tbar1 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
       		'-','商户名称:',	{
			xtype: 'textfield',
			id: 'queryMchtNm',
			name: 'queryMchtNm',
//			vtype:'isNumber',
			maxLength: 20,
			width: 140
		},'-','营业执照编号:',	{
			xtype: 'textfield',
			id: 'queryLicenceNo',
			name: 'queryLicenceNo',
//			vtype:'isNumber',
			vtype: 'alphanum',
			maxLength: 20,
			width: 140
		},'-','税务登记证号码:',	{
			xtype: 'textfield',
			id: 'queryFaxNo',
			name: 'queryFaxNo',
//			vtype:'isNumber',
			vtype: 'alphanum',
			maxLength: 20,
			width: 140
		},'-','法人代表:',	{
			xtype: 'textfield',
			id: 'queryManager',
			name: 'queryManager',
//			vtype:'isNumber',
//			vtype: 'alphanum',
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
		},/*'-','商户状态：',
			{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','审核通过'],['1','申请待审核'],['2','审核退回']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			editable: false,
			emptyText: '请选择',
			hiddenName: 'queryMchtStatus',
			id:'queryMchtStatusId',
			width: 140
		},*/'-',	'合作伙伴：',{
			xtype: 'basecomboselect',
			id: 'queryBankNoId',
			emptyText: '请选择',
			baseParams: 'BRH_BELOW_BRANCH',
			hiddenName: 'queryBankNo',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 200
		}	,'-','申请人:',	{
			xtype: 'textfield',
			id: 'queryCrtOprId',
			name: 'queryCrtOprId',
//			vtype:'isNumber',
			vtype: 'alphanum',
			maxLength: 10,
			width: 140
		}
        ]  
	});
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户入网审核',
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
		tbar: menuArr,
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
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});
	});
		
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
//		mchntGrid.getTopToolbar().items.items[2].disable();
//		mchntGrid.getTopToolbar().items.items[4].disable();
	});
	
	var rec;
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();

			mchntGrid.getTopToolbar().items.items[0].enable();
			mchntGrid.getTopToolbar().items.items[2].enable();
			mchntGrid.getTopToolbar().items.items[4].enable();
			if(rec.get('mchtStatus') == '0' ||rec.get('mchtStatus') == '2' ) {
				mchntGrid.getTopToolbar().items.items[0].disable();
				mchntGrid.getTopToolbar().items.items[2].disable();
			}
			var mchtSta=mchntGrid.getSelectionModel().getSelected().data.mchtStatus;
			if('3'===mchtSta){
				Ext.getCmp('termEditMenu').hide();
			}else {
				Ext.getCmp('termEditMenu').show();
			}
		}
	});
	
	/***************************查询条件*************************/
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNm: Ext.getCmp('queryMchtNm').getValue(),
			licenceNo: Ext.getCmp('queryLicenceNo').getValue(),
			faxNo: Ext.getCmp('queryFaxNo').getValue(),
			manager: Ext.getCmp('queryManager').getValue(),
			startDate: typeof(Ext.getCmp('queryStartDate').getValue()) == 'string' ? '' : Ext.getCmp('queryStartDate').getValue().dateFormat('Ymd'),
			endDate: typeof(Ext.getCmp('queryEndDate').getValue()) == 'string' ? '' : Ext.getCmp('queryEndDate').getValue().dateFormat('Ymd'),
			crtOprId: Ext.getCmp('queryCrtOprId').getValue(),
//			mchtStatus: Ext.get('queryMchtStatus').getValue(),
			bankNo: Ext.get('queryBankNo').getValue()	
		});
	});
	//终端数据部分
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfoTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 100},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 90},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',hidden:true,renderer: termSignSta,width: 60},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
	var termDetailMenu = {
			text: '详细信息',
			width: 85,
			iconCls: 'detail',
			disabled: true,
			handler:function(bt) {
				bt.disable();
				setTimeout(function(){bt.enable();},2000);
				selectTermInfoNew(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
			}
		};
	var termEditMenu = {
			text : '修改',
			id:'termEditMenu',
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGrid.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGrid);
					return;
				}
				//bt.disable();
				updateTmpDealInfo(selectedRecord.get('termNo'), selectedRecord.get('recCrtTs'),termGrid);
			}
		};
		
		var termGrid = new Ext.grid.GridPanel({
			title: '终端信息',
			region: 'east',
			width: 260,
			iconCls: 'T301',
			split: true,
			collapsible: true,
			frame: true,
			border: true,
			columnLines: true,
			autoExpandColumn: 'termSta',
			stripeRows: true,
			store: termStore,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			cm: termColModel,
			clicksToEdit: true,
			forceValidation: true,
			tbar: [termDetailMenu,termEditMenu],
			loadMask: {
				msg: '正在加载终端信息列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: termStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: false
			})
		});
		
		// 禁用编辑按钮
		termGrid.getStore().on('beforeload',function() {
			termGrid.getTopToolbar().items.items[0].disable();
			termGrid.getTopToolbar().items.items[1].disable();
		});
		
		termGrid.getSelectionModel().on({
			'rowselect': function() {
				termGrid.getTopToolbar().items.items[0].enable();
				termGrid.getTopToolbar().items.items[1].enable();
			}
		});
	/***************************渠道商户信息更新*************************/
//	添加T20901中的mchntForm

	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});