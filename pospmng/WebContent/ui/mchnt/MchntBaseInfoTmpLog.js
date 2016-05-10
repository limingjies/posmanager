Ext.onReady(function() {
			var txnGridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=mchntBaseInfTmpLog'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'LMchtNo',
					mapping : 'LMchtNo'
				}, {
					name : 'LManuAuthFlag',
					mapping : 'LManuAuthFlag'
				}, {
					name : 'beMchtNm',
					mapping : 'beMchtNm'
				}, {
					name : 'beLicenceNo',
					mapping : 'beLicenceNo'
				}, {
					name : 'beRislLvl',
					mapping : 'beRislLvl'
				}, {
					name : 'beMchtStatus',
					mapping : 'beMchtStatus'
				}, {
					name : 'beBankNo',
					mapping : 'beBankNo'
				}, {
					name : 'beMchtCnAbbr',
					mapping : 'beMchtCnAbbr'
				}, {
					name : 'beMchtGroupId',
					mapping : 'beMchtGroupId'
				}, {
					name : 'beMcc',
					mapping : 'beMcc'
				}, {
					name : 'beFaxNo',
					mapping : 'beFaxNo'
				}, {
					name : 'beAddr',
					mapping : 'beAddr'
				}, {
					name : 'beManager',
					mapping : 'beManager'
				}, {
					name : 'beArtifCertifTp',
					mapping : 'beArtifCertifTp'
				}, {
					name : 'beEntityNo',
					mapping : 'beEntityNo'
				}, {
					name : 'beContact',
					mapping : 'beContact'
				}, {
					name : 'beCommtel',
					mapping : 'beCommtel'
				}, {
					name : 'beElectroFax',
					mapping : 'beElectroFax'
				}, {
					name : 'beAgrBr',
					mapping : 'beAgrBr'
				}, {
					name : 'beSigninstId',
					mapping : 'beSigninstId'
				}, {
					name : 'beSettleacct',
					mapping : 'beSettleacct'
				}, {
					name : 'beSettleacctconfirm',
					mapping : 'beSettleacctconfirm'
				}, {
					name : 'beSettlebanknm',
					mapping : 'beSettlebanknm'
				}, {
					name : 'beSettleacctnm',
					mapping : 'beSettleacctnm'
				}, {
					name : 'beDisccode',
					mapping : 'beDisccode'
				}, {
					name : 'afMchtNm',
					mapping : 'afMchtNm'
				}, {
					name : 'afLicenceNo',
					mapping : 'afLicenceNo'
				}, {
					name : 'afRislLvl',
					mapping : 'afRislLvl'
				}, {
					name : 'afMchtStatus',
					mapping : 'afMchtStatus'
				}, {
					name : 'afBankNo',
					mapping : 'afBankNo'
				}, {
					name : 'afMchtCnAbbr',
					mapping : 'afMchtCnAbbr'
				}, {
					name : 'afMchtGroupId',
					mapping : 'afMchtGroupId'
				}, {
					name : 'afMcc',
					mapping : 'afMcc'
				}, {
					name : 'afFaxNo',
					mapping : 'afFaxNo'
				}, {
					name : 'afAddr',
					mapping : 'afAddr'
				}, {
					name : 'afManager',
					mapping : 'afManager'
				}, {
					name : 'afArtifCertifTp',
					mapping : 'afArtifCertifTp'
				}, {
					name : 'afEntityNo',
					mapping : 'afEntityNo'
				}, {
					name : 'afContact',
					mapping : 'afContact'
				}, {
					name : 'afCommtel',
					mapping : 'afCommtel'
				}, {
					name : 'afElectroFax',
					mapping : 'afElectroFax'
				}, {
					name : 'afAgrBr',
					mapping : 'afAgrBr'
				}, {
					name : 'afSigninstId',
					mapping : 'afSigninstId'
				}, {
					name : 'afSettleacct',
					mapping : 'afSettleacct'
				}, {
					name : 'afSettleacctconfirm',
					mapping : 'afSettleacctconfirm'
				}, {
					name : 'afSettlebanknm',
					mapping : 'afSettlebanknm'
				}, {
					name : 'afSettleacctnm',
					mapping : 'afSettleacctnm'
				}, {
					name : 'afDisccode',
					mapping : 'afDisccode'
				}, {
					name : 'LCreatedate',
					mapping : 'LCreatedate'
				}, {
					name : 'LCreatepeople',
					mapping : 'LCreatepeople'
				}, {
					name : 'LUpts',
					mapping : 'LUpts'
				}, {
					name : 'LUpoprid',
					mapping : 'LUpoprid'
				}, {
					name : 'LUptype',
					mapping : 'LUptype'
				}, {
					name : 'beOpenstlno',
					mapping : 'beOpenstlno'
				}, {
					name : 'afOpenstlno',
					mapping : 'afOpenstlno'
				}, {
					name : 'beCleartypenm',
					mapping : 'beCleartypenm'
				}, {
					name : 'afCleartypenm',
					mapping : 'afCleartypenm'
				} ]),
				autoLoad : true
			});

			/*
			 * txnGridStore.load({ params:{start: 0} });
			 */

			var mchntRowExpander = new Ext.ux.grid.RowExpander(
					{
						tpl : new Ext.Template(
								'<table  border=1 border-color:green>',
								'<tr align="center">'
										+ '<td  style="width:50"></td>'
										+ '<td style="width:50"><font size="0.2" color=green>商户名称</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>营业执照编号</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>风险级别</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>签约机构 </font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>中文名称简写</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>商户MCC</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>税务登记证号码</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>法人代表</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>法人代表证件类型</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>法人代表证件号码</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>联系人姓名</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>企业电话</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>签约网点</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>受理机构标示码</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>商户账户账号</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>开户行账号</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>开户行名称</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>商户账户户名</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>结算账户类型</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=green>计费代码</font></td>'
										+ '</tr>'
										+ '<tr align="center">'
										+ '<td style="width:50"><font size="0.2" color=black>修改前</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beMchtNm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beLicenceNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beRislLvl}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beBankNo} </font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beMchtCnAbbr}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beMcc}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beFaxNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beManager}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beArtifCertifTp}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beEntityNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beContact}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beCommtel}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beElectroFax}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beSigninstId}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beSettleacct}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beOpenstlno}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beSettlebanknm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beSettleacctnm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beCleartypenm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{beDisccode}</font></td>'
											+ '</tr>'
										+ '<tr align="center">'
										+ '<td  style="width:50"><font size="0.2" color=black>修改后</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afMchtNm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afLicenceNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afRislLvl}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afBankNo} </font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afMchtCnAbbr}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afMcc}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afFaxNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afManager}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afArtifCertifTp}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afEntityNo}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afContact}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afCommtel}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afElectroFax}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afSigninstId}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afSettleacct}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afOpenstlno}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afSettlebanknm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afSettleacctnm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afCleartypenm}</font></td>'
										+ '<td  style="width:50"><font size="0.2" color=black>{afDisccode}</font></td>'
										+ '</tr>' + '</table>', {
									getmcc : function(val) {
									},
									dateFromt : function(val) {

									}
								})
					});

			var txnColModel = new Ext.grid.ColumnModel([ mchntRowExpander,
					new Ext.grid.RowNumberer(), {
						header : '商户编号',
						dataIndex : 'LMchtNo',
						align : 'left',
						width : 300
					},
					// {header: '商户种类',dataIndex: 'LManuAuthFlag',align:
					// 'center',width: 180},
					{
						header : '审核日期',
						dataIndex : 'LCreatedate',
						width : 180,
						align : 'center',
						renderer : formatDt
					}, {
						header : '审核人',
						dataIndex : 'LCreatepeople',
						width : 180,
						align : 'center'
					}, {
						header : '申请日期',
						dataIndex : 'LUpts',
						align : 'center',
						width : 180,
						renderer : formatDt
					}, {
						header : '申请人',
						dataIndex : 'LUpoprid',
						align : 'center',
						width : 150
					}, {
						header : '日志类型',
						dataIndex : 'beMchtStatus',
						align : 'center',
						width : 150,
						renderer : function(value) {

							if (value == 1 || value == "B") {

								return "添加日志";
							} else if (value == 3) {

								return "修改日志";
							} else if (value == 5) {

								return "冻结日志";

							} else if (value == 7) {

								return "恢复日志";

							} else if (value == 9) {

								return "注销日志";
							} else if (value == "w") {

								return "入网冻结日志";
							} else if (value == "I") {

								return "批量导入日志";

							}

						}

					}

			]);

			var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '商户编号：', {
					xtype : 'dynamicCombo',
					methodName : 'getAllMchntId',
					hiddenName : 'queryCardAccpId',
					width : 312,
					editable : true,
					id : 'idqueryCardAccpId',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true
				}, '-', '生成日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',
					// vtype: 'daterange',
					// endDateField: 'endDate',
					// value : timeYesterday,
					editable : false,
					id : 'queryStartTm',
					name : 'startDate',
					width : 120
				}, ' ', '至：', {
					xtype : 'datefield',
					format : 'Y-m-d',
					altFormats : 'Y-m-d',
					// vtype: 'daterange',
					// startDateField: 'startDate',
					// value : timeYesterday,
					editable : false,
					id : 'queryEndTm',
					name : 'endDate',
					width : 120
				} ]
			});
			var txnGrid = new Ext.grid.EditorGridPanel(
					{
						iconCls : 'T104',
						region : 'center',
						// autoExpandColumn:'txnName',
						frame : true,
						border : true,
						columnLines : true,
						stripeRows : true,
						store : txnGridStore,
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
						cm : txnColModel,
						clicksToEdit : true,
						forceValidation : true,
						renderTo : Ext.getBody(),
						loadMask : {
							msg : '正在加载商户维护日志信息列表......'
						},
						plugins : mchntRowExpander,
						tbar : [
								{
									xtype : 'button',
									text : '下载报表',
									name : 'download',
									// id: 'download',
									iconCls : 'download',
									width : 80,
									handler : function() {
										showMask("正在为您准备报表，请稍后。。。", txnGrid);
										if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]) {
											Ext.Ajax
													.request({
														url : 'T2010101Action_reportAction.asp',
														timeout : 60000,
														params : {
															queryCardAccpId : Ext
																	.get(
																			'queryCardAccpId')
																	.getValue(),
															queryStartTm : Ext
																	.get(
																			'queryStartTm')
																	.getValue()
																	.replace(
																			'-',
																			'')
																	.replace(
																			'-',
																			''),
															queryEndTm : Ext
																	.get(
																			'queryEndTm')
																	.getValue()
																	.replace(
																			'-',
																			'')
																	.replace(
																			'-',
																			'')

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
															// showErrorMsg(rspObj.msg,txnGrid);
														}
													});

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
										Ext.getCmp('idqueryCardAccpId')
												.setValue('');
										Ext.getCmp('queryStartTm').setValue('');
										Ext.getCmp('queryEndTm').setValue('');
										/*
										 * Ext.getCmp('idqueryBrhId').setValue('');
										 * Ext.getCmp('queryCardAccpTermId').setValue('');
										 * Ext.getCmp('queryPan').setValue('');
										 * Ext.getCmp('queryTermSsn').setValue('');
										 * Ext.getCmp('queryAmtTransLow').setValue('');
										 * Ext.getCmp('queryAmtTransUp').setValue('');
										 * 
										 * Ext.getCmp('querySysSeqNum').setValue('');
										 * 
										 * Ext.getCmp('idqueryTxnName').setValue('');
										 * Ext.getCmp('idqueryTransState').setValue('');
										 * Ext.getCmp('querySettleBrhId').setValue('');
										 */

										// txnGridStore.load();
										// mchtStore.removeAll();
										// SelectOptionsDWR.getComboData('MCHT_BELOW',
										// function(ret) {
										// mchtStore.loadData(Ext.decode(ret));
										// });
										// Ext.getCmp('idqueryBrhId').getStore().reload();
									}
								} ],
						listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
							'render' : function() {
								/*
								 * tbar3.render(this.tbar);
								 * tbar2.render(this.tbar);
								 */
								tbar1.render(this.tbar);
							},
							'cellclick':selectableCell,
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

				Ext.apply(this.baseParams, {
					start : 0,
					queryCardAccpId : Ext.get('queryCardAccpId').getValue(),
					queryStartTm : Ext.get('queryStartTm').getValue().replace(
							'-', '').replace('-', ''),
					queryEndTm : Ext.get('queryEndTm').getValue().replace('-',
							'').replace('-', '')

				});

			});

			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ txnGrid ],
				renderTo : Ext.getBody()
			});
		});