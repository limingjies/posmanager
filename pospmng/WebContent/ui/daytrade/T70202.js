Ext// 提现审核
.onReady(function() {
	var curDate = new Date().format('Ymd');

	var detailGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=withdrawInfDtl'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'instDate',
			mapping : 'instDate'
		}, {
			name : 'pan',
			mapping : 'pan'
		}, {
			name : 'cardAccpTermId',
			mapping : 'cardAccpTermId'
		}, {
			name : 'termSsn',
			mapping : 'termSsn'
		}, {
			name : 'sysSeqNum',
			mapping : 'sysSeqNum'
		}, {
			name : 'retrivlRef',
			mapping : 'retrivlRef'
		}, {
			name : 'amtTrans',
			mapping : 'amtTrans'
		}, {
			name : 'amtFee',
			mapping : 'amtFee'
		}, {
			name : 'amtSettle',
			mapping : 'amtSettle'
		}, {
			name : 'txnName',
			mapping : 'txnName'
		}, {
			name : 'transState',
			mapping : 'transState'
		} ])
	});

	var gridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=withdrawInf'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'batchNo',
			mapping : 'batchNo'
		}, {
			name : 'mchtNoNm',
			mapping : 'mchtNoNm'
		}, {
			name : 'brhIdName',
			mapping : 'brhIdName'
		}, {
			name : 'wdNum',
			mapping : 'wdNum'
		}, {
			name : 'wdamt',
			mapping : 'wdamt'
		}, {
			name : 'wdFee',
			mapping : 'wdFee'
		}, {
			name : 'wdSettle',
			mapping : 'wdSettle'
		}, {
			name : 'wdStatus',
			mapping : 'wdStatus'
		}, {
			name : 'applyOpr',
			mapping : 'applyOpr'
		}, {
			name : 'applyTime',
			mapping : 'applyTime'
		}, {
			name : 'checkOpr',
			mapping : 'checkOpr'
		}, {
			name : 'checkTime',
			mapping : 'checkTime'
		}, {
			name : 'refuseReason',
			mapping : 'refuseReason'
		} ]),
		autoLoad : true
	});

	var colModel = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), 
	{
		header : '申请时间',
		dataIndex : 'applyTime',
		align : 'center',
		renderer : formatDt,
		width : 140
	}, {
		header : '所属商户',
		dataIndex : 'mchtNoNm',
		align : 'left',
		width : 160
	}, 
		{
		header : '申请人员',
		dataIndex : 'applyOpr',
		align : 'center',
		width : 80
	}, {
		header : '提现状态',
		dataIndex : 'wdStatus',
		align : 'center',
		width : 80,
		renderer : wdStatus
	}, {
		header : '提现金额',
		dataIndex : 'wdamt',
		align : 'right',
		width : 80,
		renderer : function(val) {
			return Ext.util.Format.number(val, '00.00');
		}
	}, {
		header : '手续费',
		dataIndex : 'wdFee',
		align : 'right',
		width : 80,
		renderer : function(val) {
			return Ext.util.Format.number(val, '00.00');
		}
	}, {
		header : '到账金额',
		dataIndex : 'wdSettle',
		align : 'right',
		width : 80,
		renderer : function(val) {
			return Ext.util.Format.number(val, '00.00');
		}
	}, {
		header : '审核时间',
		dataIndex : 'applyTime',
		align : 'center',
		renderer : formatDt,
		width : 140
	}, {
		header : '审核人员',
		dataIndex : 'applyOpr',
		align : 'center',
		width : 80
	} ]);

	var dtlRowExpander = new Ext.ux.grid.RowExpander({
		tpl : new Ext.Template('<table width=650>'
				+ '<tr><td><font color=gray>终端号： </font>{cardAccpTermId}</td>'
				+ '<td><font color=gray>终端流水号：</font>{termSsn}</td>'
				+ '<td><font color=gray>参考号：</font>{retrivlRef}</td></tr>'
				+ '</table>')
	});

	var detailColModel = new Ext.grid.ColumnModel([ dtlRowExpander,
			new Ext.grid.RowNumberer(), {
				header : '交易日期',
				dataIndex : 'instDate',
				align : 'center',
				renderer : formatDt,
				width : 140
			}, {
				header : '系统流水号',
				dataIndex : 'sysSeqNum',
				align : 'center'
			}, {
				header : '交易金额',
				dataIndex : 'amtTrans',
				align : 'right',
				width : 80,
				renderer : function(val) {
					return Ext.util.Format.number(val, '00.00');
				}
			}, {
				header : '手续费',
				dataIndex : 'amtFee',
				align : 'right',
				width : 80,
				renderer : function(val) {
					return Ext.util.Format.number(val, '00.00');
				}
			}, {
				header : '清算金额',
				dataIndex : 'amtSettle',
				align : 'right',
				width : 80,
				renderer : function(val) {
					return Ext.util.Format.number(val, '00.00');
				}
			}, {
				header : '交易类型',
				dataIndex : 'txnName',
				align : 'center',
				width : 80
			}, {
				header : '交易结果',
				dataIndex : 'transState',
				align : 'center',
				width : 80,
				renderer : txnSta
			} ]);

	var detailGrid = new Ext.grid.GridPanel({
		title : '提现流水信息',
		region : 'east',
		width : 700,
		split : true,
		collapsible : true,
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
		plugins : dtlRowExpander,
		forceValidation : true,
		loadMask : {
			msg : '正在加载提现流水信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : detailGridStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	// 拒绝按钮调用方法
	function refuse(bt, text) {
		if (bt == 'ok') {
			if (getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示', '请输入拒绝原因', true, refuse);
				return;
			}
			Ext.Ajax.request({
				url : 'T70202Action.asp?method=refuse',
				params : {
					'tblWithdrawInf.batchNo' : grid.getSelectionModel()
							.getSelected().data.batchNo,
					refuseReason : text,
					txnId : 'T70202',
					subTxnId : '03'
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if (rspObj.success) {
						showSuccessMsg(rspObj.msg, grid);

					} else {
						showErrorMsg(rspObj.msg, grid);
					}
					grid.getStore().reload();
				}
			});
		}
	}

	var grid = new Ext.grid.GridPanel({
		region : 'center',
		title : '提现申请信息',
		// autoExpandColumn:'',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : gridStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : colModel,
		clicksToEdit : true,
		forceValidation : true,
		tbar : [
				'-',
				{
					xtype : 'button',
					text : '通过',
					id : 'pass',
					width : 85,
					iconCls : 'accept',
					disabled : true,
					handler : function() {
						showConfirm('确定要提交此笔提现申请吗？', grid, function(bt) {
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T70202Action.asp?method=pass',
									params : {
										'tblWithdrawInf.batchNo' : grid
												.getSelectionModel()
												.getSelected().data.batchNo,
										txnId : 'T70202',
										subTxnId : '02'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);

										} else {
											showErrorMsg("审核失败，错误码："
													+ rspObj.msg + " "
													+ wdAppStatus(rspObj.msg),
													grid);
										}
										grid.getStore().reload();
									}
								});
							}
						})
					}
				}, '-', {
					xtype : 'button',
					text : '拒绝',
					id : 'refuse',
					width : 85,
					iconCls : 'refuse',
					disabled : true,
					handler : function() {
						showInputMsg('提示', '请输入拒绝原因', true, refuse);
					}
				} ],
		loadMask : {
			msg : '正在加载提现申请信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : gridStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	grid.getSelectionModel().on(
			{
				'rowselect' : function() {
					// 行高亮
					Ext.get(
							grid.getView()
									.getRow(grid.getSelectionModel().last))
							.frame();
					detailGridStore
							.load({
								params : {
									start : 0,
									batchNo : grid.getSelectionModel()
											.getSelected().data.batchNo
								}
							});
					Ext.getCmp('pass').enable();
					Ext.getCmp('refuse').enable();
				}
			});

	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start : 0,
			batchNo : grid.getSelectionModel().getSelected().data.batchNo
		});
	});

	gridStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
			start : 0,
			startDate : curDate, // 交易当日提现，当日审核，隔日不提不审核
			endDate : curDate, // 交易当日提现，当日审核，隔日不提不审核
			status : '5' // 筛选出待审/核提现申请
		});
		detailGridStore.removeAll();
		Ext.getCmp('pass').disable();
		Ext.getCmp('refuse').disable();
	});

	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ grid, detailGrid ],
		renderTo : Ext.getBody()
	});
});