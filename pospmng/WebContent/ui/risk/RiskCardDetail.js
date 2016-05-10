
function showRiskCardDetail(alarmId,alarmSeq,pan, El) {

	
	var detailCardGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=alarmCard'
			}),
		reader : new Ext.data.JsonReader({
			root : 'data'
			}, [
			{name: 'pan',mapping: 'pan'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'bankNm',mapping: 'bankNm'},
			{name: 'alarmNum',mapping: 'alarmNum'},
			{name: 'alarmNumTotal',mapping: 'alarmNumTotal'},
			{name: 'cheatNum',mapping: 'cheatNum'},
			{name: 'cardSta',mapping: 'cardSta'}
			]),
		autoLoad : false
	});
	

//	=============================================违规记录开始=======================================
	var cardRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cardRule'
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
	
	var cardRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		{header: '警报级别',dataIndex: 'alarmLvl',align: 'center',width: 60,renderer:alarmLvl},
//		{header: '警报编号',dataIndex: 'alarmId',align: 'center',width: 120},
//		{header: '序列号',dataIndex: 'alarmSeq',align: 'center',width: 120},
		{header: '风控日期',dataIndex: 'riskDate',width: 90 ,renderer:formatDt},
		{header: '触发风控',dataIndex: 'riskId',width:140,renderer:riskIdName},
		{header: '规则说明',dataIndex: 'saModelDesc',id:'saModelDescCard',width: 100,align: 'left'},
//		{header: '处理状态',dataIndex: 'alarmSta',width: 100,align: 'center',renderer:alarmSta}
		{header: '相关交易',dataIndex: 'txnCount',width: 100,align: 'center'}
	]);
	
	var cardRuleGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 292,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'saModelDescCard',
		store: cardRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载违规记录相关交易列表......'
		},
		tbar:[{
			
			xtype: 'button',
			text: '查看交易详情',
			name: 'cardDetail',
			id: 'cardDetail',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {
				var queryAlarmCardId=cardRuleGrid.getSelectionModel().getSelected().get('alarmId');
				var queryAlarmCardSeq=cardRuleGrid.getSelectionModel().getSelected().get('alarmSeq');
				showRiskCardTxnDetail(queryAlarmCardId,queryAlarmCardSeq)
				
			}
		}],
		bbar: new Ext.PagingToolbar({
			store: cardRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用编辑按钮
	cardRuleGrid.getStore().on('beforeload',function() {
		Ext.getCmp('cardDetail').disable();
	});
	
	cardRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(cardRuleGrid.getView().getRow(cardRuleGrid.getSelectionModel().last)).frame();
//			mchntGrid.getTopToolbar().items.items[0].disable();
			Ext.getCmp('cardDetail').enable();
		}
	});
	
	cardRuleStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryPan:pan
//			queryAlarmId:alarmId,
//			queryAlarmSeq:alarmSeq
		});
	});
//	=============================================违规记录结束=======================================
	
	
	
	
	var detailCardInfoForm = new Ext.form.FormPanel({
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
							fieldLabel : '银行卡卡号',
							id : 'pan',
							name : 'pan',
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'combofordispaly',
				       		baseParams: 'feeCardType',
							fieldLabel: '卡种',
							width : 200,
							id: 'cardTpId',
							hiddenName: 'cardTp'
						}]
					},  {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel: '所属银行',
							id: 'bankNm',
							name: 'bankNm',
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:90,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '当日违规次数',
							id: 'alarmNum',
							name: 'alarmNum',
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:80,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '违规总次数',
							id: 'alarmNumTotal',
							name: 'alarmNumTotal',
							width : 200
						}]
					},{
						columnWidth : .33,
						layout : 'form',
						labelWidth:90,
						items : [{
							xtype: 'displayfield',
							fieldLabel: '涉及欺诈次数',
							id: 'cheatNum',
							name: 'cheatNum',
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
						items : [cardRuleGrid]
		            }]
				}]
			});

			var detailCardWin = new Ext.Window({
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 900,
				items : [detailCardInfoForm],
				closeAction : 'close',
				iconCls : 'logo',
				resizable : false,
				autoScroll : true,
				buttonAlign : 'center',
				buttons : [{
					text : '关闭',
					handler : function() {
						detailCardWin.close(El);
					}
				}]
			});

	

	detailCardGridStore.load({
		params : {
			queryPan:pan,
			queryAlarmId:alarmId,
			queryAlarmSeq:alarmSeq
		},
		callback : function(records, options, success) {
			if (success) {
				
				detailCardInfoForm.getForm().loadRecord(detailCardGridStore.getAt(0));
				detailCardInfoForm.getForm().clearInvalid();
				detailCardWin.setTitle('银行卡详细信息');
				detailCardWin.show();
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
				showErrorMsg("加载银行卡详细信息失败，请刷新数据后重试", El);
			}
		}
	});
	
}

function showRiskCardTxnDetail(queryAlarmId,queryAlarmSeq) {
	//	=============================================交易详情开始=======================================
	var cardTxnDetailStore = new Ext.data.Store({
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
	
	var cardTxnDetailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '交易类型',dataIndex: 'txnNum',align: 'left',width: 80,id:'txnNumCardTxn'},
		{header: '交易时间',dataIndex: 'instDate',align: 'center',width: 140,renderer:formatDt},
		{header: '所属商户',dataIndex: 'mchtNm',id:'mchtNm',align: 'left',width:150},
		{header: '交易金额',dataIndex: 'amtTrans',width: 80,align: 'right'},
		{header: '交易卡号',dataIndex: 'pan',width: 140,align: 'left'},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 70 ,align: 'left'},
		{header: '交易结果',dataIndex: 'transState',width: 70,align: 'center',renderer: txnSta},
		{header: '交易应答',dataIndex: 'rspCode',width: 120,align: 'left'},
		{header: '清算状态',dataIndex: 'settleFlag',width: 80,align: 'center',renderer: settleStatus}
	]);
	
	var cardTxnDetailGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
//		title:'相关交易',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 300,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mchtNm',
		store: cardTxnDetailStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardTxnDetailColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载详细交易列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: cardTxnDetailStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	cardTxnDetailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(cardTxnDetailGrid.getView().getRow(cardTxnDetailGrid.getSelectionModel().last)).frame();
		}
	});
	
	cardTxnDetailStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryAlarmId:queryAlarmId,
			queryAlarmSeq:queryAlarmSeq
		});
	});
	
	
	var detailCardTxnDetailWin = new Ext.Window({
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 1100,
		items : [cardTxnDetailGrid],
		closeAction : 'close',
		iconCls : 'logo',
		title:'相关交易',
		resizable : false,
		autoScroll : true,
		buttonAlign : 'center'
	});
	
	cardTxnDetailStore.load({
		params : {
			queryAlarmId: queryAlarmId,
			queryAlarmSeq:queryAlarmSeq
		},
		callback : function(records, options, success) {
			if (success) {
				detailCardTxnDetailWin.show();
			} else {
				showErrorMsg("加载相关交易信息失败，请刷新数据后重试");
			}
		}
	});
}
