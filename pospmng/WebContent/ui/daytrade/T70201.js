Ext.onReady(function() {

	// 提现查询
	var acctMchtParamsStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=withdrawTxn'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'instDate',
			mapping : 'instDate'
		}, {
			name : 'crateDate',
			mapping : 'crateDate'
		}, {
			name : 'sysSeqNumber',
			mapping : 'sysSeqNumber'
		}, {
			name : 'webTime',
			mapping : 'webTime'
		}, {
			name : 'webSqlNumber',
			mapping : 'webSqlNumber'
		}, {
			name : 'merchantId',
			mapping : 'merchantId'
		}, {
			name : 'acctNo',
			mapping : 'acctNo'
		}, {
			name : 'totalNumber',
			mapping : 'totalNumber'
		}, {
			name : 'totalAmount',
			mapping : 'totalAmount'
		}, {
			name : 'totalFree',
			mapping : 'totalFree'
		}, {
			name : 'status',
			mapping : 'status'
		}, {
			name : 'taotalSettle',
			mapping : 'taotalSettle'
		} ]),
		autoLoad : true
	});

	var acctMchtParamsColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '提现时间',
				dataIndex : 'crateDate',
				width : 180,
				align : 'center'
			}, {
				header : '前置流水号',
				dataIndex : 'sysSeqNumber',
				width : 100,
				align : 'center'
			}, {
				header : '业务管理平台流水号',
				dataIndex : 'webSqlNumber',
				width : 150,
				align : 'center'
			}, {
				header : '商户号',
				dataIndex : 'merchantId',
				width : 180,
				align : 'left'
			}, {
				header : '总笔数',
				dataIndex : 'totalNumber',
				width : 120,
				align : 'center'
			}, {
				header : '总金额',
				dataIndex : 'totalAmount',
				width : 100,
				align : 'right'
			}, {
				header : '总手续费',
				dataIndex : 'totalFree',
				width : 100,
				align : 'right'
			}, {
				header : '总结算金额',
				dataIndex : 'taotalSettle',
				width : 100,
				align : 'right'
			}, {
				header : '提现状态',
				dataIndex : 'status',
				width : 100,
				align : 'center',
				renderer : function(val) {
					if (val == '0') {

						return '成功';
					} else if (val == '1') {

						return '失败';
					} else {

						return '处理中';
					}
				}
			} ]);

	// 详细信息数据集
	var detailGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=withdrawTxnDetail'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'instDate',
			mapping : 'instDate'
		}, {
			name : 'sysSeqNumber',
			mapping : 'sysSeqNumber'
		}, {
			name : 'webTime',
			mapping : 'webTime'
		}, {
			name : 'webSqlNumber',
			mapping : 'webSqlNumber'
		}, {
			name : 'merchantId',
			mapping : 'merchantId'
		}, {
			name : 'amount',
			mapping : 'amount'
		}, {
			name : 'stauts',
			mapping : 'stauts'
		}, {
			name : 'createDate',
			mapping : 'createDate'
		}, {
			name : 'transDate',
			mapping : 'transDate'
		}, {
			name : 'transFee',
			mapping : 'transFee'
		}, {
			name : 'settleAmount',
			mapping : 'settleAmount'
		}, {
			name : 'transDate',
			mapping : 'transDate'
		}, {
			name : 'cardNo',
			mapping : 'cardNo'
		}, {
			name : 'terminalNum',
			mapping : 'terminalNum'
		}, {
			name : 'withdrawInstDate',
			mapping : 'withdrawInstDate'
		} ])
	});

	var detailColModel = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(),
			{
				header : '提现时间',
				dataIndex : 'createDate',
				align : 'right',
				width : 150
			}, {
				header : '前置流水号',
				dataIndex : 'sysSeqNumber',
				width : 120,
				align : 'right'

			},/*
				 * { header : '业务管理平台流水号', dataIndex : 'webSqlNumber', width :
				 * 150, align : 'center' },
				 */{
				header : '商户号',
				dataIndex : 'merchantId',
				width : 180,
				align : 'left'
			}, {
				header : '交易金额',
				dataIndex : 'amount',
				width : 150,
				align : 'center',
				renderer : function(val) {
					return Ext.util.Format.number(val, '00.00');
				}
			}, {
				header : '交易手续费',
				dataIndex : 'transFee',
				width : 150,
				align : 'center'
			}, {
				header : '结算金额',
				dataIndex : 'settleAmount',
				width : 150,
				align : 'center'
			}, {
				header : '终端号',
				dataIndex : 'terminalNum',
				width : 150,
				align : 'center'
			}, {
				header : '卡号',
				dataIndex : 'cardNo',
				width : 150,
				align : 'center'
			}, {
				header : '交易日期',
				dataIndex : 'transDate',
				width : 150,
				align : 'center',
				renderer : formatDt
			}, {
				header : '提现状态',
				dataIndex : 'stauts',
				width : 150,
				align : 'center',
				renderer : function(val) {
					if (val == '0') {

						return '成功';
					} else if (val == '1') {

						return '失败';
					} else {

						return '处理中';
					}
				}
			} ]);

	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '商户编号：', {
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdAll',
			hiddenName : 'queryMerchantId',
			width : 250,
			editable : true,
			id : 'queryMerchantIdId',
			mode : 'local',
			triggerAction : 'all',
			forceSelection : true,
			selectOnFocus : true,
			lazyRender : true
		}, '-', '前置流水号：', {
			xtype : 'textfield',
			name : 'querySysSeqNumber',
			width : 150,
			id : 'querySysSeqNumberId'

		}, '-', '业务管理平台流水号：', {
			xtype : 'textfield',
			name : 'queryWebSqlNumber',
			width : 150,
			id : 'queryWebSqlNumberId'

		}, '-', '提现状态：', {
			xtype : 'combo',
			// fieldLabel : '提现状态',
			name : 'queryStatus',
			id : 'queryStatusId',
			width : 150,
			emptyText : '请选择',
			mode : 'local',
			store : new Ext.data.SimpleStore({
				fields : [ 'value', 'text' ],
				data : [ [ '1', '失败' ], [ '2', '处理中' ], [ '0', '成功' ] ]
			}),
			editable : false,
			triggerAction : 'all',
			valueField : 'value',
			displayField : 'text'
		} ]
	});
	var tbar2 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '提现起始日期：',{
			xtype : 'datefield',
			id : 'queryStartDate',
			name : 'startDate',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			/*vtype : 'daterange',
			endDateField : 'endDate',*/
			fieldLabel : '提现起始日期',
			editable : false,
			value : new Date(),
			width : 180
		}, '提现结束日期：',{
			xtype : 'datefield',
			id : 'queryEndDate',
			name : 'endDate',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
		/*	vtype : 'daterange',
			startDateField : 'startDate',*/
			fieldLabel : '提现结束日期',
			editable : false,
			value : new Date(),
			width : 180
		} ]
	});
	var detailGrid = new Ext.grid.EditorGridPanel({
		// title : '交易详情信息',
		// region : 'east',
		width : 600,
		height : 300,
		// split : true,
		// collapsible : true,
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : detailGridStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : detailColModel,
		clicksToEdit : true,
		forceValidation : true,
		bbar : new Ext.PagingToolbar({
			store : detailGridStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	var withdrawWin = new Ext.Window({
		title : '提现相信信息',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		autoHeight : true,
		autoWidth : true,
		layout : 'fit',
		items : [ detailGrid ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'withdraw',
		resizable : false,
		buttons : [ {
			text : '关闭',
			handler : function() {
				withdrawWin.hide();
			}
		} ]
	});
	var mchtParamsGrid = new Ext.grid.GridPanel({
		title : '商户提现信息',
		iconCls : 'logo',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		region : 'center',
		clicksToEdit : true,
		store : acctMchtParamsStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : acctMchtParamsColModel,
		forceValidation : true,
		loadMask : {
			msg : '正在加载商户提现信息列表......'
		},
		tbar : [
				{
					xtype : 'button',
					text : '查询',
					name : 'query',
					id : 'query',
					iconCls : 'query',
					width : 80,
					handler : function() {
						acctMchtParamsStore.load();
					}
				},
				'-',
				{
					xtype : 'button',
					text : '重置',
					name : 'reset',
					id : 'reset',
					iconCls : 'reset',
					width : 80,
					handler : function() {
						Ext.getCmp('queryMerchantIdId').setValue(''), Ext
								.getCmp('querySysSeqNumberId').setValue(''),
								Ext.getCmp('queryWebSqlNumberId').setValue(''),
								Ext.getCmp('queryStatusId').setValue('');
						Ext.getCmp('queryStartDate').reset();
						Ext.getCmp('queryEndDate').reset();
					}
				},
				'-',
				{
					xtype : 'button',
					text : '查看提现详细信息',
					name : 'detail',
					id : 'detailId',
					iconCls : 'detail',
					width : 80,
					disabled : true,
					handler : function() {
						var record = mchtParamsGrid.getSelectionModel()
								.getSelected();
						detailGridStore.on('beforeload', function() {

							Ext.apply(this.baseParams, {
								start : 0,
								instDate : record.get('instDate'),
								sysSeqNumber : record.get('sysSeqNumber')
							});
						});
						detailGridStore.removeAll();
						detailGridStore.load();

						withdrawWin.show();
						withdrawWin.center();
					}
				} ],
		listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
			'render' : function() {
				tbar1.render(this.tbar);
				tbar2.render(this.tbar);
			}
		},
		bbar : new Ext.PagingToolbar({
			store : acctMchtParamsStore,
			pageSize : 25,
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	acctMchtParamsStore.on('beforeload', function() {
		var today = Ext.getCmp('queryEndDate').getValue();
		today.setDate(today.getDate() + 1);
		Ext.apply(this.baseParams, {
			start : 0,
			limit : 25,
			queryMerchantId : Ext.getCmp('queryMerchantIdId').getValue(),
			querySysSeqNumber : Ext.getCmp('querySysSeqNumberId').getValue(),
			queryWebSqlNumber : Ext.getCmp('queryWebSqlNumberId').getValue(),
			queryStatus : Ext.getCmp('queryStatusId').getValue(),
			queryStartDate : Ext.util.Format.date(Ext.getCmp('queryStartDate')
					.getValue(), 'Ymd'),

			queryEndDate : Ext.util.Format.date(today, 'Ymd')

		});
		Ext.getCmp('detailId').disable();

	});

	mchtParamsGrid.getSelectionModel().on(
			{
				'rowselect' : function() {
					Ext.get(
							mchtParamsGrid.getView().getRow(
									mchtParamsGrid.getSelectionModel().last))
							.frame();
					Ext.getCmp('detailId').enable();
				}
			});

	var mainView = new Ext.Viewport({
		layout : 'border',
		// items : [ mchtParamsGrid, detailGrid ],
		items : [ mchtParamsGrid ],
		renderTo : Ext.getBody()
	});
});