Ext
		.onReady(function() {
			var curDate = new Date();
			var preDate = new Date(curDate.getTime() - 24 * 60 * 60 * 1000);

			// 获取当前日期的前一天
			var timeYesterday = preDate.format("Y") + "-" + preDate.format("m")
					+ "-" + (preDate.format("d"));// 昨天

			// 风控冻结管理
			var alarmTxnStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=recoverTxn'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'cardAccpId',
					mapping : 'cardAccpId'
				}, {
					name : 'machtNm',
					mapping : 'machtNm'
				}, {
					name : 'txnDt',
					mapping : 'txnDt'
				}, {
					name : 'cardAccpId',
					mapping : 'cardAccpId'
				}, {
					name : 'pan',
					mapping : 'pan'
				}, {
					name : 'sysSeqNum',
					mapping : 'sysSeqNum'
				}, {
					name : 'txnNum',
					mapping : 'txnNum'
				}, {
					name : 'transState',
					mapping : 'transState'
				}, {
					name : 'freezeFlag',
					mapping : 'freezeFlag'
				}, {
					name : 'stlmFlag',
					mapping : 'stlmFlag'
				}, {
					name : 'instCode',
					mapping : 'instCode'
				}, {
					name : 'pan',
					mapping : 'pan'
				}, {
					name : 'amtTrans',
					mapping : 'amtTrans'
				}, {
					name : 'amtFee',
					mapping : 'amtFee'
				}, {
					name : 'amtStlm',
					mapping : 'amtStlm'
				}

				]),
				autoLoad : true
			});

			var alarmTxnColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '所属商户',
						dataIndex : 'machtNm',
						id : 'mchtNames',
						align : 'left',
						width : 150
					}, {
						header : '交易时间',
						dataIndex : 'txnDt',
						align : 'center',
						width : 140,
						renderer : formatDt
					}, {
						header : '系统流水',
						dataIndex : 'sysSeqNum',
						align : 'left',
						width : 80,
						id : 'txnNum'
					}, {
						header : '交易类型',
						dataIndex : 'txnNum',
						align : 'center',
						width : 80,
						id : 'txnNum'
					}, {
						header : '交易状态',
						dataIndex : 'transState',
						width : 70,
						align : 'center',
						renderer : txnSta
					}, {
						header : '冻结状态',
						dataIndex : 'freezeFlag',
						width : 60,
						align : 'center',
						renderer : blockFlag
					}, {
						header : '清算状态',
						dataIndex : 'stlmFlag',
						width : 60,
						align : 'center',
						renderer : settleStatus
					}, {
						header : '卡号',
						dataIndex : 'pan',
						width : 150,
						align : 'left'
					}, {
						header : '交易金额',
						dataIndex : 'amtTrans',
						width : 90,
						align : 'right'
					}, {
						header : '交易手续费',
						dataIndex : 'amtFee',
						width : 90,
						align : 'right',
						renderer : cautionFlag
					}, {
						header : '结算金额',
						dataIndex : 'amtStlm',
						width : 90,
						align : 'right'
					} ]);

			var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '商户编号：', {
					xtype : 'dynamicCombo',
					methodName : 'getMchntNoAll',
					hiddenName : 'queryMchtNo',
					width : 300,
					editable : true,
					id : 'idQueryMchtNo',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true
				}, '-', '系统流水：       ', {
					xtype : 'textfield',
					name : 'querySysSeqNum',
					id : 'querySysSeqNum',
					vtype : 'isNumber',
					width : 120
				},

				'-', '冻结状态：', {
					xtype : 'combo',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '1', '已冻结' ], [ '2', '已解冻' ] ],
						reader : new Ext.data.ArrayReader()
					}),
					displayField : 'displayField',
					valueField : 'valueField',
					hiddenName : 'queryBlockFlag',
					editable : false,
					emptyText : '请选择',
					id : 'queryBlockFlagId',
					width : 140
				} ]
			});
			var tbar2 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '起始日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',
					// vtype: 'daterange',
					// endDateField: 'endDate',
					value : timeYesterday,
					editable : false,
					id : 'startDate',
					name : 'startDate',
					width : 120
				}, '-', '结束日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',
					// vtype: 'daterange',
					// startDateField: 'startDate',
					value : timeYesterday,
					editable : false,
					id : 'endDate',
					name : 'endDate',
					width : 120
				}, '-', '卡号：', {
					xtype : 'textfield',
					id : 'queryPan',
					name : 'queryPan',
					maxLength : 40,
					width : 150
				} ]
			});

			var alarmTxnGrid = new Ext.grid.GridPanel(
					{
						// iconCls: 'risk',
						frame : true,
						border : true,
						columnLines : true,
						stripeRows : true,
						height : 480,
						region : 'center',
						clicksToEdit : true,
						autoExpandColumn : 'mchtNames',
						store : alarmTxnStore,
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
						cm : alarmTxnColModel,
						forceValidation : true,
						// renderTo: Ext.getBody(),
						loadMask : {
							msg : '正在加载风险警报相关交易列表......'
						},
						tbar : [
								{
									xtype : 'button',
									text : '解冻交易',
									id : 'recoverTxn',
									width : 80,
									iconCls : 'unlock',// recover
									disabled : true,
									handler : function() {
										showConfirm(
												'确定要对此交易进行解冻吗？',
												alarmTxnGrid,
												function(bt) {
													// 如果点击了提示的确定按钮
													if (bt == "yes") {
														Ext.Ajax
																.request({
																	url : 'T40211Action.asp?method=recoverTxnDtl',
																	waitMsg : '正在提交，请稍后......',
																	params : {
																		instDate : alarmTxnGrid
																				.getSelectionModel()
																				.getSelected()
																				.get(
																						'txnDt'),
																		sysSeqNum : alarmTxnGrid
																				.getSelectionModel()
																				.getSelected()
																				.get(
																						'sysSeqNum'),
																		txnId : '40211',
																		subTxnId : '12'
																	},
																	success : function(
																			rsp,
																			opt) {
																		var rspObj = Ext
																				.decode(rsp.responseText);
																		if (rspObj.success) {
																			showSuccessMsg(
																					rspObj.msg,
																					alarmTxnGrid);

																		} else {
																			showErrorMsg(
																					rspObj.msg,
																					alarmTxnGrid);
																		}
																		alarmTxnGrid
																				.getStore()
																				.reload();
																	}
																});
													}
												})
									}
								},
								'-',
								{
									xtype : 'button',
									text : '查询',
									name : 'query',
									id : 'query',
									iconCls : 'query',
									width : 80,
									handler : function() {
										alarmTxnStore.load();
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
										Ext.getCmp('startDate').reset();

										Ext.getCmp('endDate').reset();

										Ext.getCmp('queryPan').setValue('');

										Ext.getCmp('querySysSeqNum').setValue(
												'');

										Ext.getCmp('queryBlockFlagId')
												.setValue('');
										Ext.getCmp('idQueryMchtNo')
												.setValue('');
										alarmTxnStore.load();
									}
								} ],
						listeners : {
							'render' : function() {
								tbar1.render(this.tbar);
								tbar2.render(this.tbar);
							}
						},
						bbar : new Ext.PagingToolbar({
							store : alarmTxnStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
					});

			alarmTxnGrid
					.getSelectionModel()
					.on(
							{
								'rowselect' : function() {
									// 行高亮
									Ext
											.get(
													alarmTxnGrid
															.getView()
															.getRow(
																	alarmTxnGrid
																			.getSelectionModel().last))
											.frame();
									var rec = alarmTxnGrid.getSelectionModel()
											.getSelected();
									var freezeFlag = rec.get('freezeFlag');
									if (freezeFlag == '1') {

										Ext.getCmp('recoverTxn').enable();
									} else {

										Ext.getCmp('recoverTxn').disable();
									}
								}
							});
			// 禁用编辑按钮
			alarmTxnGrid.getStore().on('beforeload', function() {

				Ext.getCmp('recoverTxn').disable();
			});
			alarmTxnStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
					start : 0,
					startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
							endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
					queryPan : Ext.getCmp('queryPan').getValue(),
					queryMchtNo : Ext.getCmp('idQueryMchtNo').getValue(),
					querySysSeqNum : Ext.getCmp('querySysSeqNum').getValue(),

					queryBlockFlag : Ext.get('queryBlockFlag').getValue()

				});
			});
			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ alarmTxnGrid ],
				renderTo : Ext.getBody()
			});
		});