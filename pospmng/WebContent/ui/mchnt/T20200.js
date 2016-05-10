Ext.onReady(function() {
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCheckInfoFirst'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'bankNo1',mapping: 'bankNo'},
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
			{name: 'partNum',mapping: 'partNum'},
			{name: 'updTs',mapping: 'updTs'},
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name: 'rislLvl',mapping: 'rislLvl'}
		])
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
	
		
		'</table>',	
			/*'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}</p>',
			'<p>申请原因：{partNum}</p>',
			'<p>最近更新时间：{updTs}</p>'*/{
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
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 140},
		{header: '商户名称',dataIndex: 'mchtNm',width: 160,id: 'mchtNm'},
		{header: '合作伙伴',dataIndex: 'bankNo1',width: 140,id: 'bankNo1',align: 'center'},
		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtGroupFlag},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 130},
//		{header: '注册日期',dataIndex: 'recCrtTs',width: 80,renderer: formatDt},
		{header: '风险级别',dataIndex: 'rislLvl',width: 60},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt},
		{header: '终端数量',dataIndex: 'termCount',width: 60}
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
			case '': return '未定级';
		}
	}
	
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
	
	
	
	// 菜单集合
	var menuArr = new Array();
		
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20200Action.asp?method=accept',
						params: {
							mchntId: rec.get('mchtNo'),
							txnId: '20200',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,mchntGrid);
							} else {
								showErrorMsg(rspObj.msg,mchntGrid);
							}
							// 重新加载商户待审核信息
							mchntGrid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
	};
	
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = mchntGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T20200Action.asp?method=refuse',
				params: {
					mchntId: rec.get('mchtNo'),
					txnId: '20200',
					subTxnId: '03',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户待审核信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var backMenu = {
			text: '退回',
			width: 85,
			iconCls: 'back',
			disabled: true,
			handler:function() {
				showConfirm('确认退回吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入退回原因',true,back);
					}
				});
			}
		};
		
		// 退回按钮执行的方法
		function back(bt,text) {
			if(bt == 'ok') {
				if(getLength(text) > 60) {
					alert('退回原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入退回原因',true,back);
					return;
				}
				//showProcessMsg('正在提交审核信息，请稍后......');
				rec = mchntGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T20200Action.asp?method=back',
					params: {
						mchntId: rec.get('mchtNo'),
						txnId: '20200',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,mchntGrid);
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
						// 重新加载商户待审核信息
						mchntGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
		}
	var childWin;
		
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
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};

	menuArr.push(acceptMenu);  //[0]
	menuArr.push('-');         //[1]
	menuArr.push(backMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(refuseMenu);  //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	
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
		tbar: [termDetailMenu],
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
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户初级审核',
		region: 'center',
		iconCls: 'T20200',
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
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
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
			mchntGrid.getTopToolbar().items.items[6].enable();
			if(rec.get('mchtStatus') == '5' ||rec.get('mchtStatus') == '7' ||rec.get('mchtStatus') == '9' ) {
				mchntGrid.getTopToolbar().items.items[2].disable();
			}
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'mchtNo',
			name: 'mchtNo',
			vtype: 'alphanum',
			fieldLabel: '商户编号',
			maskRe: /^[0-9]+$/
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '注册开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '注册结束日期',
			editable: false
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '签约机构',
			id: 'idbankNo',
			hiddenName: 'bankNo'
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.findById('mchtNo').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('bankNo').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});