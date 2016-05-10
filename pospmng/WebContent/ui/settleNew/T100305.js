Ext
		.onReady(function() {

			var curDate = new Date();
			var preDate = new Date(curDate.getTime() - 24 * 60 * 60 * 1000);

			// 获取当前日期的前一天
			var timeYesterday = preDate.format("Y") + "-" + preDate.format("m")
					+ "-" + (preDate.format("d"));// 昨天

		

			var txnGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=settleDtlNew'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'dateStlm',
					mapping : 'dateStlm'
				}, {
					name : 'mchtId',
					mapping : 'mchtId'
				}, {
					name : 'channel',
					mapping : 'channel'
				}, {
					name : 'begDate',
					mapping : 'begDate'
				}, {
					name : 'endDate',
					mapping : 'endDate'
				}, {
					name : 'amtNoral',
					mapping : 'amtNoral'
				}, {
					name : 'amtErrMinus',
					mapping : 'amtErrMinus'
				}, {
					name : 'amtErrAdd',
					mapping : 'amtErrAdd'
				}, {
					name : 'amtHanging',
					mapping : 'amtHanging'
				}, {
					name : 'amtSttle',
					mapping : 'amtSttle'
				}, {
					name : 'payType',
					mapping : 'payType'
				}, {
					name : 'machtNm',
					mapping : 'machtNm'
				}, {
					name : 'mchtAcctNo',
					mapping : 'mchtAcctNo'
				}, {
					name : 'mchtAcctNm',
					mapping : 'mchtAcctNm'
				}, {
					name : 'cnapsId',
					mapping : 'cnapsId'
				}, {
					name : 'anapsName',
					mapping : 'anapsName'
				}, {
					name : 'areaName',
					mapping : 'areaName'
				}, {
					name : 'lstUpdTm',
					mapping : 'lstUpdTm'
				}

				]),
				autoLoad : true
			});
			var settlRowExpander = new Ext.ux.grid.RowExpander(
					{
						tpl : new Ext.Template(
								'<table width=500>',
								'<tr><td><font color=green>商户账户号：</font>{mchtAcctNo}</td>',
								'<td><font color=green>商户账户名：</font>{mchtAcctNm}</td></tr>',
								'<tr><td><font color=green>开户行号：</font>{cnapsId}</td>',
								'<td><font color=green>开户行名称： </font>{anapsName}</td></tr>',
								'<tr><td><font color=green>地区名称：</font>{areaName}</td>',
								'</tr>', '</table>', {

								})
					});
			var txnColModel = new Ext.grid.ColumnModel([ settlRowExpander, {
				header : '清算日期',
				dataIndex : 'dateStlm',
				align : 'center',
				renderer : formatDt,
				width : 90
			}, {
				header : '商户名',
				dataIndex : 'machtNm',
				width : 350,
				align : 'left',
				id : 'mchtNoNm'
			}, {
				header : '结算通道号',
				dataIndex : 'channel',
				align : 'left',
				width : 120
			}, {
				header : '结算开始日期',
				dataIndex : 'begDate',
				align : 'center',
				renderer : formatDt,
				width : 90
			}, {
				header : '结算截止日期',
				dataIndex : 'endDate',
				align : 'center',
				width : 80,
				renderer : formatDt
			}, {
				header : '正常结算金额',
				dataIndex : 'amtNoral',
				align : 'right',
				width : 100
			}, {
				header : '差错扣减金额',
				dataIndex : 'amtErrMinus',
				align : 'right',
				width : 100
			}, {
				header : '差错到账金额',
				dataIndex : 'amtErrAdd',
				align : 'right'
			}, {
				header : '挂账金额',
				dataIndex : 'amtHanging',
				align : 'right',
				width : 80
			}, {
				header : '结算金额',
				dataIndex : 'amtSttle',
				align : 'center'
			}, {
				header : '支付方式',
				dataIndex : 'payType',
				align : 'center',
				renderer:function(val){
					if(val=='0'){						
						return '<font color="gray">无需支付</font>';
					}else if(val=='1'){
						
						return '<font color="red">大额支付</font>';
						
					}else if(val=='2'){
						
						return '<font color="green">小额支付</font>';
					}
				}
			} ]);

			var tbar2 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '交易金额：', {
					xtype : 'textfield',
					id : 'queryAmtTransLow',
					name : 'queryAmtTransLow',
					vtype : 'isMoney',
					width : 120
				}, '—', {
					xtype : 'textfield',
					id : 'queryAmtTransUp',
					name : 'queryAmtTransUp',
					vtype : 'isMoney',
					width : 120
				} ]
			});

			var tbar3 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '清算日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',

					value : timeYesterday,
					editable : false,
					id : 'startDate',
					name : 'startDate',
					width : 120
				}, '—', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',

					value : timeYesterday,
					editable : false,
					id : 'endDate',
					name : 'endDate',
					width : 120
				}, '-', '商户编号：', {
					xtype : 'dynamicCombo',
					methodName : 'getMchntNoAll',
					hiddenName : 'queryMchtNoNm',
					width : 285,
					editable : true,
					id : 'idqueryMchtNoNm',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true
				} ]
			});
		
			var txnGrid = new Ext.grid.EditorGridPanel(
					{
						iconCls : 'T104',
						region : 'center',
						autoExpandColumn : 'mchtNoNm',
						frame : true,
						border : true,
						columnLines : true,
						stripeRows : true,
						plugins : settlRowExpander,
						store : txnGridStore,
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
						cm : txnColModel,
						clicksToEdit : true,
						forceValidation : true,
						renderTo : Ext.getBody(),
						loadMask : {
							msg : '正在加载交易信息列表......'
						},
						tbar : [
								{
									xtype : 'button',
									text : '下载报表',
									name : 'download',
									id : 'download',
									iconCls : 'download',
									width : 80,
									handler : function() {
										showMask("正在为您准备报表，请稍后。。。", txnGrid);
										if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]) {
											Ext.Ajax
													.request({
														url : 'T100305Action.asp',
														timeout : 60000,
														params : {
															startDate : typeof (Ext
																	.getCmp('startDate')
																	.getValue()) == 'string' ? ''
																	: Ext
																			.getCmp(
																					'startDate')
																			.getValue()
																			.format(
																					'Ymd'),
															endDate : typeof (Ext
																	.getCmp('endDate')
																	.getValue()) == 'string' ? ''
																	: Ext
																			.getCmp(
																					'endDate')
																			.getValue()
																			.format(
																					'Ymd'),
															queryMchtNoNm : Ext
																	.get(
																			'queryMchtNoNm')
																	.getValue(),

															queryAmtTransLow : Ext
																	.getCmp(
																			'queryAmtTransLow')
																	.getValue(),
															queryAmtTransUp : Ext
																	.getCmp(
																			'queryAmtTransUp')
																	.getValue()

														},
														success : function(rsp,
																opt) {
															hideMask();
															var rspObj = Ext
																	.decode(rsp.responseText);
															if (rspObj.success) {
																window.location.href = Ext.contextPath
																		+ '/ajaxDownLoad.asp?path='
																		+ rspObj.msg;
															} else {
																showErrorMsg(
																		rspObj.msg,
																		txnGrid);
															}
														},
														failure : function() {
															hideMask();
															showErrorMsg(
																	rspObj.msg,
																	txnGrid);
														}
													});
											// txnGridStore.load();
										} else {
											hideMask();
											Ext.MessageBox
													.show({
														msg : '数据量超过限定值,请输入限制条件再进行此操作!!!',
														title : '报表下载说明',
														buttons : Ext.MessageBox.OK,
														modal : true,
														width : 220
													});
										}
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
										txnGridStore.load();
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
										Ext.getCmp('idqueryMchtNoNm').setValue(
												'');

									/*	Ext.getCmp('queryAmtTransLow')
												.setValue('');
										Ext.getCmp('queryAmtTransUp').setValue(
												'');*/

										Ext.getCmp('startDate').setValue(
												timeYesterday);
										Ext.getCmp('endDate').setValue(
												timeYesterday);

									}
								} ],
						listeners : {
							'render' : function() {
								tbar3.render(this.tbar);
							//	tbar2.render(this.tbar);
							}
						},
						bbar : new Ext.PagingToolbar({
							store : txnGridStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
					});

			txnGrid.getSelectionModel().on(
					{
						'rowselect' : function() {
							// 行高亮
							Ext.get(
									txnGrid.getView().getRow(
											txnGrid.getSelectionModel().last))
									.frame();
						}
					});

			txnGridStore.on('beforeload', function() {
				Ext
						.apply(this.baseParams,
								{
									start : 0,
									startDate : typeof (Ext.getCmp('startDate')
											.getValue()) == 'string' ? '' : Ext
											.getCmp('startDate').getValue()
											.format('Ymd'),
									endDate : typeof (Ext.getCmp('endDate')
											.getValue()) == 'string' ? '' : Ext
											.getCmp('endDate').getValue()
											.format('Ymd'),
									queryMchtNoNm : Ext.get('queryMchtNoNm')
											.getValue()/*,
									queryAmtTransLow : Ext.getCmp(
											'queryAmtTransLow').getValue(),

									queryAmtTransUp : Ext.getCmp(
											'queryAmtTransUp').getValue()*/

								});
			});

			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ txnGrid ],
				renderTo : Ext.getBody()
			});
		});