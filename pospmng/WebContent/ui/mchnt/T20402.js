Ext.onReady(function() {
	
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCupInfoTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtStates',mapping: 'mchtStates'},
			{name:'mchtType',mapping:'mchtType'},
			{name:'crtTs',mapping:'crtTs'}
		])
	});
	
	mchntStore.load({
		params: {
			start: 0
		}
	});
	
//	var mchntRowExpander = new Ext.ux.grid.RowExpander({
//		tpl: new Ext.Template(
//			'<p>商户英文名称：{engName}</p>',
//			'<p>商户MCC：{mcc:this.getmcc()}</p>',
//			'<p>商户地址：{addr}</p>',
//			'<p>邮编：{postCode}</p>',
//			'<p>电子邮件：{commEmail}</p>',
//			'<p>法人代表名称：{manager}</p>',
//			'<p>联系人姓名：{contact}</p>',
//			'<p>联系人电话：{commTel}</p>',
//			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
//			'<p>最近更新时间：{updTs}</p>',{
//			getmcc : function(val){return getRemoteTrans(val, "mcc");}
//			}
//		)
//	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
//		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 140},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200},
		{header: '商户类型',dataIndex: 'mchtType',width: 180,renderer:mchtCupType},
		{header: '商户状态',dataIndex: 'mchtStates',renderer:mchtCupSt},
		{header: '入网日期',dataIndex: 'crtTs',width: 140}
	]);
	
	var menuArray = new Array();
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			window.location.href = Ext.contextPath + '/page/mchnt/T2040201.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
		}
	};
	
	var stopMenu = {
		text: '冻结',
		width: 85,
		iconCls: 'stop',
		disabled: true,
		handler:function() {
			showConfirm('确定冻结吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20402Action.asp?method=stop',
						params: {
							mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
							txnId: '20402',
							subTxnId: '02'
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
			});
		}
	};
	
	
	var recoverMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T20402Action.asp?method=recover',
						params: {
							mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
							txnId: '20402',
							subTxnId: '03'
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
			});
		}
	};
	
	var delMenu = {
			text: '注销',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('注销商户信息后不可恢复,确定注销吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20402Action.asp?method=del',
							params: {
								mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
								txnId: '20402',
								subTxnId: '04'
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
				});
			}
		};

	
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
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var report = {
			text: '导出直联商户信息',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",mchntGrid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T2040202Action.asp',
					params: {
						mchtNo: queryForm.findById('mchtNoQ').getValue(),
			            mchtStatus: queryForm.findById('mchtStatusQ').getValue(),
			            mchtType: queryForm.findById('mchtTypeQ').getValue(),
			            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
						endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
						txnId: '2040202',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,mchntGrid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		}
	
	menuArray.push(editMenu);    //[0]
	menuArray.push('-');         //[1]
	menuArray.push(stopMenu);    //[2]
	menuArray.push('-');         //[3]
	menuArray.push(recoverMenu); //[4]
	menuArray.push('-');         //[5]
	menuArray.push(delMenu);     //[6]
	menuArray.push('-');         //[7]
	menuArray.push(detailMenu);  //[8]
	menuArray.push('-');         //[9]
	menuArray.push(queryCondition);  //[10]
//	menuArray.push('-');         //[11]
//	menuArray.push(report);  //[12]
		
	
	// 商户黑名单列表
	var mchntGrid = new Ext.grid.EditorGridPanel({
		title: '直联商户信息信息',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载直联商户信息列表......'
		},
		tbar: menuArray,
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
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();
	});
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
			if(rec.get('mchtStates') == '1' || rec.get('mchtStates') == '9' || rec.get('mchtStates') == '8' || rec.get('mchtStates') == '7' || rec.get('mchtStates') == '6' || rec.get('mchtStates') == '4' || rec.get('mchtStates') == 'D' || rec.get('mchtStates') == 'B') {
				mchntGrid.getTopToolbar().items.items[0].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[0].disable();
			}
			if(rec.get('mchtStates') == '1') {//正常可冻结和查看详细
				mchntGrid.getTopToolbar().items.items[2].enable();
				mchntGrid.getTopToolbar().items.items[8].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[2].disable();
				mchntGrid.getTopToolbar().items.items[8].disable();
			}
			if(rec.get('mchtStates') == '2') {//冻结时才能恢复
				mchntGrid.getTopToolbar().items.items[4].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[4].disable();
			}
			if(rec.get('mchtStates') == '1'||rec.get('mchtStates') == '2') {//正常和冻结时可以注销
				mchntGrid.getTopToolbar().items.items[6].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[6].disable();
			}
			mchntGrid.getTopToolbar().items.items[8].enable();
		}
	});
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '入网日期始',
			editable: false,
			anchor: '70%'
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '入网日期止',
			editable: false,
			anchor: '70%'
		},{
		    xtype: 'basecomboselect',
	        baseParams: 'MCHT_CUP_TYPE',
			fieldLabel: '商户种类',
			id: 'mchtTypeQ',
			hiddenName: 'mchtType',
			editable: false,
			anchor: '70%'
		},{
			xtype: 'combo',
			id:'mchtStatusQ',
			hiddenName: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','正常'],['9','新增待审核'],['8','新增拒绝'],['0','注销'],['2','冻结'],['7','修改待审核'],
					   ['6','修改拒绝'],['5','冻结待审核'],['3','注销待审核'],['H','恢复待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntCupIdAll',
			id:'mchtNoQ',
			hiddenName: 'mchtNo',
			editable: true,
			anchor: '70%'
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
	
	mchntStore.on('beforeload',function() {
		Ext.apply(this.baseParams, {
            start: 0,
            mchtNo: queryForm.findById('mchtNoQ').getValue(),
            mchtStatus: queryForm.findById('mchtStatusQ').getValue(),
            mchtType: queryForm.findById('mchtTypeQ').getValue(),
            startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd')
        });
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
});