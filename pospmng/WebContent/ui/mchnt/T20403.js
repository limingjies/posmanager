Ext.onReady(function() {
	var rec;
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntCupInfoExa'
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
		{header: '商户名称',dataIndex: 'mchtNm',width: 280},
		{header: '商户类型',dataIndex: 'mchtType',width: 180,renderer:mchtCupType},
		{header: '商户状态',dataIndex: 'mchtStates',renderer:mchtCupSt},
		{header: '入网日期',dataIndex: 'crtTs',width: 140}
	]);
	
	var menuArr = new Array();
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
			rec = mchntGrid.getSelectionModel().getSelected();
            if(rec == null){
                showAlertMsg("没有选择记录",mchntGrid);
                return;
            }  
            
			showConfirm('确认审核通过吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20403Action.asp?method=accept',
						params: {
							mchntId: rec.get('mchtNo'),
							txnId: '20403',
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
			rec = mchntGrid.getSelectionModel().getSelected();
            if(rec == null){
                showAlertMsg("没有选择记录",mchntGrid);
                return;
            }  
            
			showConfirm('确认拒绝吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交审核信息，请稍后......');
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url: 'T20403Action.asp?method=refuse',
						params: {
							mchntId: rec.get('mchtNo'),
							txnId: '20403',
							subTxnId: '02'
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
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			rec = mchntGrid.getSelectionModel().getSelected();
            if(rec == null){
                showAlertMsg("没有选择记录",mchntGrid);
                return;
            }  
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);	
		}
	};
	
	var qryMenu = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArr.push(acceptMenu);  	//[0]
	menuArr.push('-');         	//[1]
	menuArr.push(refuseMenu);   //[2]
	menuArr.push('-');         	//[3]
	menuArr.push(detailMenu);   //[4]
	menuArr.push('-');         	//[5]
	menuArr.push(qryMenu);   //[6]
	
	// 直联商户审核列表
	var mchntGrid = new Ext.grid.EditorGridPanel({
		title: '直联商户审核',
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
			msg: '正在加载直联商户审核列表......'
		},
		tbar: menuArr,
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
	});
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			
			// 根据所选择的商户状态判断哪个按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
			if(rec != null){
				mchntGrid.getTopToolbar().items.items[0].enable();
				mchntGrid.getTopToolbar().items.items[2].enable();
                mchntGrid.getTopToolbar().items.items[4].enable();
            }else {
            	mchntGrid.getTopToolbar().items.items[0].disable();
				mchntGrid.getTopToolbar().items.items[2].disable();
            	mchntGrid.getTopToolbar().items.items[4].disable();
            }
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
				data: [['9','新增待审核'],['7','修改待审核'],['5','冻结待审核'],['3','注销待审核'],['H','恢复待审核']],
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
	
//	mchntGrid.getStore().on('beforeload',function() {
//		mchntGrid.getStore().rejectChanges();
//	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
});