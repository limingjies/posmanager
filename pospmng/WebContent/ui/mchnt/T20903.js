Ext.onReady(function() {
	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntUpdateInfo'
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
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 140,hidden:true},
		{header: '商户名称',dataIndex: 'mchtNm',width: 250,id: 'mchtNm'},
		{header: '法人代表',dataIndex: 'manager',width: 100},
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
			case '1' : return '大商户';
			case '2' : return '普通商户';
			case '3' : return '小商户';
			case '4' : return '超级商户';
			case '9': return '未定级';
			case '0': return '未定级';
		}
	}
	
	
	
	
	

	
	
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
//				showErrorMsg('操作原因最多可以输入30个汉字或60个字母、数字',mchntGrid);
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20903Action_del.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20903',
					subTxnId: '05',
					exMsg: text
					
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);	
		}
	};
	

	
	

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
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
			editable: true,
	//			allowBlank: true,
			lazyRender: true,
			width: 140
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
		title: '商户信息维护',
		region: 'center',
//		iconCls: 'T20903',
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
		},'-',{
			
			text: '修改',
			width: 85,
			id:'update',
			name:'update',
			iconCls: 'edit',
			disabled: true,
			handler:function() {
//				  window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
				window.location.href = Ext.contextPath + '/page/mchnt/T2090301.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
			}
		},'-',{
			
			text: '删除',
			width: 85,
			id:'delete',
			name:'delete',
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('删除商户信息后不可恢复,确定删除吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
//						showInputMsg('提示','请输入进行该操作的原因',true,delBack);
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20903Action_del.asp',
							params: {
								mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
								txnId: '20903',
								subTxnId: '05'
//								exMsg: text
								
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,mchntGrid);
								} else {
									showErrorMsg(rspObj.msg,mchntGrid);
								}
								// 重新加载商户信息
								mchntGrid.getStore().reload();
							}
						});
					}
				});
			}
		}
		       
		],
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  
        }  ,
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
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		Ext.getCmp('detail').disable();
		Ext.getCmp('update').disable();
		Ext.getCmp('delete').disable();
		
	});
	
	var rec;
	


	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			rec = mchntGrid.getSelectionModel().getSelected();
//			mchntGrid.getTopToolbar().items.items[0].enable();
			
			if(rec.get('mchtStatus') == 'A') {
				Ext.getCmp('delete').enable();
			} else {
				Ext.getCmp('delete').disable();
			}
			if(rec.get('mchtStatus') == 'B'||rec.get('mchtStatus') == 'A') {
				Ext.getCmp('update').enable();
			} else {
				Ext.getCmp('update').disable();
			}
			Ext.getCmp('detail').enable();
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
			mchtStatus: Ext.get('queryMchtStatus').getValue(),
			bankNo: Ext.get('queryBankNo').getValue()
			
			
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});