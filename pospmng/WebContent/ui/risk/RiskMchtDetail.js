

function showRiskMchtDetail(alarmId,alarmSeq,cardAccpId,alarmNum,alarmNumTotal,cheatNum, El) {

	
	var detailMchtGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'loadRecordAction.asp?storeId=getMchntInf'
				}),
		reader : new Ext.data.JsonReader({
			root : 'data'
			}, [
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'addr',mapping: 'addr'}
			
			]),
		autoLoad : false
	});
	

//	=============================================违规记录开始=======================================
	var mchtRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtRule'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'alarmSeq',mapping: 'alarmSeq'},
			{name: 'riskDate',mapping: 'riskDate'},
			{name: 'riskId',mapping: 'riskId'},
			{name: 'saModelDesc',mapping: 'saModelDesc'},
			{name: 'txnCount',mapping: 'txnCount'}
		]),
		autoLoad: true
	}); 
	
	var mchtRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		{header: '警报级别',dataIndex: 'alarmLvl',align: 'center',width: 60,renderer:alarmLvl},
//		{header: '警报编号',dataIndex: 'alarmId',align: 'center',width: 120},
//		{header: '序列号',dataIndex: 'alarmSeq',align: 'center',width: 120},
		{header: '风控日期',dataIndex: 'riskDate',width: 90 ,renderer:formatDt},
		{header: '触发风控',dataIndex: 'riskId',width:140,renderer:riskIdName},
		{header: '规则说明',dataIndex: 'saModelDesc',id:'saModelDescMcht',width: 100,align: 'left'},
//		{header: '处理状态',dataIndex: 'alarmSta',width: 100,align: 'center',renderer:alarmSta}
		{header: '相关交易',dataIndex: 'txnCount',width: 100,align: 'center'}
	]);
	
	var mchtRuleGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 292,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelDescMcht',
		store: mchtRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchtRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载违规记录相关交易列表......'
		},
		tbar:[{
			
			xtype: 'button',
			text: '查看交易详情',
			name: 'mchtDetail',
			id: 'mchtDetail',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {
				var queryAlarmId=mchtRuleGrid.getSelectionModel().getSelected().get('alarmId');
				var queryAlarmSeq=mchtRuleGrid.getSelectionModel().getSelected().get('alarmSeq');
				showRiskMchtTxnDetail(queryAlarmId,queryAlarmSeq)
				
			}
		}],
		bbar: new Ext.PagingToolbar({
			store: mchtRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	mchtRuleGrid.getStore().on('beforeload',function() {
		Ext.getCmp('mchtDetail').disable();
	});
	
	mchtRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchtRuleGrid.getView().getRow(mchtRuleGrid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
			Ext.getCmp('mchtDetail').enable();
		}
	});
	
	mchtRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryCardAccpId:cardAccpId
		});
	});
//	=============================================违规记录结束=======================================
	
	
	
	
	var detailMchtInfoForm = new Ext.form.FormPanel({
				frame : true,
				autoHeight : true,
				waitMsgTarget : true,
				labelWidth : 65,
				items : [{
					layout : 'column',
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '商户编号',
							id : 'mchtNo',
							name : 'mchtNo',
							width : 200
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '商户名称',
							id : 'mchtNm',
							name : 'mchtNm',
							width : 200
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
				       		baseParams: 'MCHT_GROUP_FLAG',
							fieldLabel: '商户种类',
							width : 200,
							id: 'idmchtGroupFlag',
							hiddenName: 'mchtGroupFlag'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
				        	baseParams: 'BRH_BELOW_BRANCH',
							fieldLabel: '签约机构',
							hiddenName: 'bankNo',
							width : 200
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
				        	baseParams: 'MCHNT_GRP_ALL',
							fieldLabel: '商户组别',
							hiddenName: 'mchtGrp',
							width : 200	
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
				        	baseParams: 'RISL_LVL',
							fieldLabel: '风险级别',
							hiddenName: 'rislLvl',
							width : 200
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel: '联系人',
							id: 'contact',
							name: 'contact',
							width : 200
						}]
					}, {
						columnWidth : .66,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
							fieldLabel: '商户MCC',
							store: mchntMccStore,
							hiddenName: 'mcc',
							width : 500
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel: '联系电话',
							id: 'commTel',
							name: 'commTel',
							width : 200
						}]
					}, {
						columnWidth : .66,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel: '商户地址',
							id: 'addr',
							name: 'addr',
							width : 500
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:90,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '当日违规次数',
//							id: 'alarmNum',
//							name: 'alarmNum',
							value:alarmNum,
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:80,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '违规总次数',
//							id: 'alarmNumTotal',
//							name: 'alarmNumTotal',
							value:alarmNumTotal,
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:90,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '涉及欺诈次数',
//							id: 'cheatNum',
//							name: 'cheatNum',
							value:cheatNum,
							width : 200
						}]
					}]
				},{
					xtype: 'tabpanel',
		        	id: 'tab',
		        	frame: false,
		        	border: false,
		            plain: false,
		            activeTab: 0,
		            height: 320,
		            deferredRender: false,
		            enableTabScroll: true,
		            labelWidth: 150,
		            items:[{
//						id:'Info',
						title:'违规记录',
        				frame: false,
        				border: false,
        				layout: 'border',
						items : [mchtRuleGrid]
		            }]
				}]
			});

			var detailMchtWin = new Ext.Window({
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 900,
				items : [detailMchtInfoForm],
				closeAction : 'close',
				iconCls : 'logo',
				resizable : false,
				autoScroll : true,
				buttonAlign : 'center',
				buttons : [{
					text : '关闭',
					handler : function() {
						detailMchtWin.close(El);
					}
				}]
			});

	

	detailMchtGridStore.load({
		params : {
			mchntId: cardAccpId
		},
		callback : function(records, options, success) {
			if (success) {
				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',detailMchtGridStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					detailMchtInfoForm.getForm().findField('mcc').setValue(detailMchtGridStore.getAt(0).data.mcc);
				});
				
				detailMchtInfoForm.getForm().loadRecord(detailMchtGridStore.getAt(0));
				detailMchtInfoForm.getForm().clearInvalid();
				detailMchtWin.setTitle('商户详细信息');
				detailMchtWin.show();
				/*if (detailMchtGridStore.getAt(0).data.resv1_7Dt == '1') {
					Ext.getCmp('settleBankNmDt').clearInvalid();
					detailBrhInfoForm.findById('settleBankNoDtId').show();
					Ext.getCmp('settleBankNoDt').clearInvalid();
				}else{
					Ext.getCmp('settleBankNmDt').clearInvalid();
					detailBrhInfoForm.findById('settleBankNoDtId').hide();
					Ext.getCmp('settleBankNoDt').clearInvalid();
				}*/

//				showCmpProcess(detailMchtInfoForm, '正在加载机构信息，请稍后......');
//				hideCmpProcess(detailMchtInfoForm, 1000);

			} else {
				showErrorMsg("加载商户信息失败，请刷新数据后重试", El);
			}
		}
	});
	
}

function showRiskMchtTxnDetail(queryAlarmId,queryAlarmSeq) {
	//	=============================================交易详情开始=======================================
	var mchtTxnDetailStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=alarmTxn'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'transState',mapping: 'transState'},
			{name: 'rspCode',mapping: 'rspCode'},
			{name: 'cheatTp',mapping: 'cheatTp'},
			{name: 'cheatFlag',mapping: 'cheatFlag'},
			{name: 'cautionFlag',mapping: 'cautionFlag'},
			{name: 'receiptFlag',mapping: 'receiptFlag'},
			{name: 'blockFlag',mapping: 'blockFlag'},
			{name: 'imagPath',mapping: 'imagPath'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'settleFlag',mapping: 'settleFlag'}
		]),
		autoLoad: false
	}); 
	
	var mchtTxnDetailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '交易类型',dataIndex: 'txnNum',align: 'left',width: 80,id:'txnNumCardTxn'},
		{header: '交易时间',dataIndex: 'instDate',align: 'center',width: 140,renderer:formatDt},
		{header: '所属商户',dataIndex: 'mchtNm',id:'mchtName',align: 'left',width:150},
		{header: '交易金额',dataIndex: 'amtTrans',width: 80,align: 'right'},
		{header: '交易卡号',dataIndex: 'pan',width: 140,align: 'left'},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 70 ,align: 'left'},
		{header: '交易结果',dataIndex: 'transState',width: 70,align: 'center',renderer: txnSta},
		{header: '交易应答',dataIndex: 'rspCode',width: 120,align: 'left'},
		{header: '清算状态',dataIndex: 'settleFlag',width: 80,align: 'center',renderer: settleStatus}
	]);
	
	var mchtTxnDetailGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
//		title:'相关交易',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mchtName',
		store: mchtTxnDetailStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchtTxnDetailColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载详细交易列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchtTxnDetailStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	mchtTxnDetailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchtTxnDetailGrid.getView().getRow(mchtTxnDetailGrid.getSelectionModel().last)).frame();
		}
	});
	
	mchtTxnDetailStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryAlarmId:queryAlarmId,
			queryAlarmSeq:queryAlarmSeq
		});
	});
	
	
	var detailMchtTxnDetailWin = new Ext.Window({
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width :1100,
		items : [mchtTxnDetailGrid],
		closeAction : 'close',
		iconCls : 'logo',
		title:'相关交易',
		resizable : false,
		autoScroll : true,
		buttonAlign : 'center'
	});
	
	mchtTxnDetailStore.load({
		params : {
			queryAlarmId: queryAlarmId,
			queryAlarmSeq:queryAlarmSeq
		},
		callback : function(records, options, success) {
			if (success) {
				detailMchtTxnDetailWin.show();
			} else {
				showErrorMsg("加载相关交易信息失败，请刷新数据后重试");
			}
		}
	});
}
