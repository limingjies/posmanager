Ext.onReady(function() {
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskMchtInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
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
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'bankNm',mapping: 'bankNm'},
			{name:'cautionFlag',mapping:'cautionFlag'},
			{name: 'blockSettleFlag',mapping: 'blockSettleFlag'},
			{name: 'blockMchtFlag',mapping: 'blockMchtFlag'}
		]),
		autoLoad: true
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=500>',
		'<tr><td><font color=green>商户MCC：</font>{mcc:this.getmcc()}</td>',
		'<td><font color=green>商户地址：</font>{addr}</td></tr>',
		
		'<tr><td><font color=green>营业执照编号： </font>{licenceNo}</td>',
		'<td><font color=green>法人代表名称：</font>{manager}</td></tr>',
		
		
//		'<tr><td><font color=green>邮编：</font>{postCode}</td>',
//		'<td><font color=green>电子邮件：</font>{commEmail}</td></tr>',
		
		
		'<tr><td><font color=green>联系人姓名：</font>{contactu}</td>',
		'<td><font color=green>联系人电话：</font>{commTel}</td></tr>',
		
		'<tr><td><font color=green>录入柜员：</font>{crtOprId}</td>',
		'<td><font color=green>注册日期：</font>{recCrtTs:this.dateFromt}</td></tr>',
		
		'<tr><td></td>' ,
		'<td></td>',
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
					} else if(val.length == 8){
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8);
					}else {
						return val;
					}
				}	
			}
		)
	});

	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 140},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,id: 'mchtNm'},
//		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtGroupFlag},
		{header: '所属机构',dataIndex: 'bankNm',width: 230},
//		{header: '注册日期',dataIndex: 'recCrtTs',width: 80,renderer: formatDt},
		{header: '风险级别',dataIndex: 'rislLvl',width: 80},
		{header: '提示状态',dataIndex: 'cautionFlag',width: 80,align: 'center',renderer: cautionFlag},
		{header: '冻结结算状态',dataIndex: 'blockSettleFlag',width: 100,align: 'center',renderer: blockMchtFlag},
		{header: '冻结交易状态',dataIndex: 'blockMchtFlag',width: 100,align: 'center',renderer: blockMchtFlag}
//		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt}
	]);
	
	
	
	 var tbarMcht1 = new Ext.Toolbar({  
                renderTo: Ext.grid.GridPanel.tbar,  
                items:[
				'-','提示状态：',
					{
					xtype: 'combo',
					id:'queryMchtCautionFlagId',
					hiddenName: 'queryMchtCautionFlag',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未提示'],['1','已提示']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					editable: false,
					emptyText: '请选择',
					width: 140
					
				},'-','冻结结算状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未冻结'],['1','已冻结']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryBlockSettleFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryBlockSettleFlagId',
					width: 140
				},'-','冻结交易状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未冻结'],['1','已冻结']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryBlockMchtFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryBlockMchtFlagId',
					width: 140
				},'-','风险级别：',
				{
				    xtype: 'basecomboselect',
			        baseParams: 'RISL_LVL',
					hiddenName: 'queryRislLvl',
					id:'idQueryRislLvl',
					editable: false,
					width: 140
				}
				]  
         }) 
         
         var tbarMcht2 = new Ext.Toolbar({  
                renderTo: Ext.grid.GridPanel.tbar,  
                items:[
               			
				'-','商户编号：'
				,{
					xtype: 'dynamicCombo',
					methodName: 'getMchntNoAll',
					hiddenName: 'queryMchtNo',
					width: 300,
					editable: true,
					id: 'idQueryMchtNo',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true
				},'-','签约机构：',
				{
					xtype: 'dynamicCombo',
					methodName: 'getBrhId',
					id: 'idQueryBankNo',
					hiddenName: 'queryBankNo',
					editable: false,
					width: 250
				}
				]  
         }) 
	
		
	
	
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
		tbar: 	[{
				xtype: 'button',
				text : '风险提醒',
				id:'remindMcht',
				width : 80,
				iconCls : 'mchnt',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户风险提醒吗？', mchntGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40213Action.asp?method=remindMcht',
								params : {
									mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
									txnId : '40213',
									subTxnId : '01'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);

									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									mchntGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '商户冻结',
				id:'blockMcht',
				width : 80,
				iconCls : 'stop',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户冻结吗？', mchntGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40213Action.asp?method=blockMcht',
								params : {
									mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
									txnId : '40213',
									subTxnId : '02'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);

									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									mchntGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '商户解冻',
				id:'recoverMcht',
				width : 80,
				iconCls : 'accept',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户解冻吗？', mchntGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40213Action.asp?method=recoverMcht',
								params : {
									mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
									txnId : '40213',
									subTxnId : '03'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);

									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									mchntGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '结算冻结',
				id:'blockSettle',
				width : 80,
				iconCls : 'lock',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户的结算冻结吗？', mchntGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40213Action.asp?method=blockSettle',
								params : {
									mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
									txnId : '40213',
									subTxnId : '04'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);

									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									mchntGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '结算解冻',
				id:'recoverSettle',
				width : 80,
				iconCls : 'unlock',//recover
//				iconCls : 'start',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户的结算解冻吗？', mchntGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40213Action.asp?method=recoverSettle',
								params : {
									mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
									txnId : '40213',
									subTxnId : '05'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, mchntGrid);

									} else {
										showErrorMsg(rspObj.msg, mchntGrid);
									}
									mchntGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
			xtype: 'button',
			text: '查询',
			name: 'queryMcht',
			id: 'queryMcht',
			iconCls: 'query',
			width: 80,
			handler:function() {
				mchntStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetMcht',
			id: 'resetMcht',
			iconCls: 'reset',
			width: 80,
			handler:function() {
//				Ext.getCmp('idqueryCardAccpId').setValue('');
				Ext.getCmp('queryMchtCautionFlagId').setValue('');
				Ext.getCmp('queryBlockSettleFlagId').setValue('');
				Ext.getCmp('queryBlockMchtFlagId').setValue('');
				
				Ext.getCmp('idQueryMchtNo').setValue('');
				Ext.getCmp('idQueryRislLvl').setValue('');
				Ext.getCmp('idQueryBankNo').setValue('');
				
				mchntStore.load();
			}	
		}],
		listeners : {       
            'render' : function() {  
					tbarMcht1.render(this.tbar); 
					tbarMcht2.render(this.tbar);
                } ,
            'cellclick':selectableCell,
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
	
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
//		mchntGrid.getTopToolbar().items.items[0].disable();
		Ext.getCmp('blockMcht').disable();
		Ext.getCmp('recoverMcht').disable();
		Ext.getCmp('remindMcht').disable();
		Ext.getCmp('blockSettle').disable();
		Ext.getCmp('recoverSettle').disable();
	});
	
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
//			var rec = mchntGrid.getSelectionModel().getSelected();
//			mchntGrid.getTopToolbar().items.items[0].enable();
			var cautionFlagMcht=mchntGrid.getSelectionModel().getSelected().get('cautionFlag');
			if(cautionFlagMcht=='0'){
				Ext.getCmp('remindMcht').enable();
			}else{
				Ext.getCmp('remindMcht').disable();
			}
			
			var blockMchtFlag=mchntGrid.getSelectionModel().getSelected().get('blockMchtFlag');
			if(blockMchtFlag=='0'){
				Ext.getCmp('blockMcht').enable();
				Ext.getCmp('recoverMcht').disable();
			}else if(blockMchtFlag=='1'){
				Ext.getCmp('blockMcht').disable();
				Ext.getCmp('recoverMcht').enable();
			}else {
				Ext.getCmp('blockMcht').disable();
				Ext.getCmp('recoverMcht').disable();
			}
			
			var blockSettleFlag=mchntGrid.getSelectionModel().getSelected().get('blockSettleFlag');
			if(blockSettleFlag=='0'){
				Ext.getCmp('blockSettle').enable();
				Ext.getCmp('recoverSettle').disable();
			}else if(blockSettleFlag=='1'){
				Ext.getCmp('blockSettle').disable();
				Ext.getCmp('recoverSettle').enable();
			}else {
				Ext.getCmp('blockSettle').disable();
				Ext.getCmp('recoverSettle').disable();
			}
			
		}
	});
	
	
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchntId: Ext.get('queryMchtNo').getValue(),
			queryRislLvl: Ext.get('queryRislLvl').getValue(),
			queryBrhId: Ext.get('queryBankNo').getValue(),
			queryMchtCautionFlag: Ext.get('queryMchtCautionFlag').getValue(),
			queryBlockSettleFlag: Ext.get('queryBlockSettleFlag').getValue(),
			queryBlockMchtFlag: Ext.get('queryBlockMchtFlag').getValue()
//			settleType: queryForm.findById('settleType').getValue(),
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});