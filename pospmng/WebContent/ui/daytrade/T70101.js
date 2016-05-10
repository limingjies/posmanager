Ext
		.onReady(function() {

			var curDate = new Date();
			// 数据集
			var withDrawFeeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=withDrawFee'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'rowId',
					mapping : 'rowId'
				}, {
					name : 'merchantId',
					mapping : 'merchantId'
				}, {
					name : 'merchantIdNm',
					mapping : 'merchantIdNm'
				}, {
					name : 'feeType',
					mapping : 'feeType'
				}, {
					name : 'rate',
					mapping : 'rate'
				}, {
					name : 'startDate',
					mapping : 'startDate'
				}, {
					name : 'endDate',
					mapping : 'endDate'
				}, {
					name : 'status',
					mapping : 'status'
				}, {
					name : 'maxFee',
					mapping : 'maxFee'
				}, {
					name : 'minFee',
					mapping : 'minFee'
				}, {
					name : 'createDate',
					mapping : 'createDate'
				}, {
					name : 'createBy',
					mapping : 'createBy'
				}, {
					name : 'updateDate',
					mapping : 'updateDate'
				}, {
					name : 'updateBy',
					mapping : 'updateBy'
				} ]),
				autoLoad : true
			});

			var withDrawFeeColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '计费编号',
						dataIndex : 'rowId',
						align : 'center',
						width : 80
					}, {
						header : '商户名',
						dataIndex : 'merchantIdNm',
						id : 'merchantIdNm',
						width : 300,
						align : 'left'
					}, {
						header : '计费类型',
						dataIndex : 'feeType',
						width : 80,
						align : 'center',
						renderer : function(val) {
							if (val == '01') {
								return '百分比';
							} else if (val == '02') {
								return '单笔';
							}
							return '未知类型';
						}
					}, {
						header : '费用值',
						dataIndex : 'rate',
						width : 80,
						align : 'right',
						renderer : function(val) {
							if (null != val&&''!=val) {
								
								return val + "%";
								
							}else {
								
								return '';
							}

						}
					}, {
						header : '费用最小值',
						dataIndex : 'minFee',
						width : 100,
						align : 'right',
						renderer : function(val) {
							return Ext.util.Format.number(val, '00.00');
						}
					}, {
						header : '费用最大值',
						dataIndex : 'maxFee',
						width : 100,
						align : 'right',
						renderer : function(val) {
							return Ext.util.Format.number(val, '00.00');
						}
					}, {
						header : '状态',
						dataIndex : 'status',
						width : 80,
						align : 'center',
						renderer : function(val) {
							if (val == '1') {
								return '<font color="red">已停用</font>';
							} else if (val == '0') {
								return '<font color="green">已启用</font>';
							}
							return '';
						}
					}, {
						header : '生效日期',
						dataIndex : 'startDate',
						width : 100,
						align : 'center'
					}, {
						header : '失效日期',
						dataIndex : 'endDate',
						width : 100,
						align : 'center'
					}, {
						header : '创建时间',
						dataIndex : 'createDate',
						width : 150,
						align : 'center'
					}, {
						header : '创建员号',
						dataIndex : 'createBy',
						width : 80,
						align : 'center'
					}, {
						header : '修改时间',
						dataIndex : 'updateDate',
						width : 150,
						align : 'center'
					}, {
						header : '修改员号',
						dataIndex : 'updateBy',
						width : 80,
						align : 'center'
					} ]);

			var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '商户编号：', {
					xtype : 'dynamicCombo',
					methodName : 'getHaveAcctMchntId',
					hiddenName : 'queryCardAccpId',
					width : 250,
					editable : true,
					id : 'idqueryCardAccpId',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true
				} ]
			});

			var grid = new Ext.grid.GridPanel(
					{
						title : '提现计费维护',
						iconCls : 'logo',
						frame : true,
						border : true,
						columnLines : true,
						stripeRows : true,
						region : 'center',
						clicksToEdit : true,
						// autoExpandColumn: 'merchantIdNm',
						store : withDrawFeeStore,
						sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
						cm : withDrawFeeColModel,
						forceValidation : true,
						loadMask : {
							msg : '正在加载提现计费列表......'
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
										withDrawFeeStore.load();
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
										withDrawFeeStore.load();
									}
								},
								'-',
								{
									xtype : 'button',
									text : '新增',
									name : 'add',
									id : 'add',
									iconCls : 'add',
									width : 80,
									handler : function() {
										addWin.show();
										addWin.center();
									}
								},
								'-',
								{
									xtype : 'button',
									text : '修改',
									name : 'edit',
									id : 'edit',
									disabled : true,
									iconCls : 'edit',
									width : 80,
									handler : function() {
										updateReset();
										updWin.show();
										updWin.center();
									}
								},
								'-',
								{
									xtype : 'button',
									text : '删除',
									name : 'delete',
									id : 'delete',
									disabled : true,
									iconCls : 'delete',
									width : 80,
									handler : function() {
										showConfirm(
												'确定要删除该计费信息吗？',
												grid,
												function(bt) {
													// 如果点击了提示的确定按钮
													if (bt == "yes") {
														Ext.Ajax
																.request({
																	url : 'T70101Action.asp?method=delete',
																	params : {
																		rowId : grid
																				.getSelectionModel()
																				.getSelected()
																				.get(
																						'rowId'),
																		txnId : '70101',
																		subTxnId : '03'
																	},
																	success : function(
																			rsp,
																			opt) {
																		var rspObj = Ext
																				.decode(rsp.responseText);
																		if (rspObj.success) {
																			showSuccessMsg(
																					rspObj.msg,
																					grid);
																		} else {
																			showErrorMsg(
																					rspObj.msg,
																					grid);
																		}
																		grid
																				.getStore()
																				.reload();
																		Ext
																				.getCmp(
																						'delete')
																				.disable();
																	}
																});
													}
												})
									}
								},
								'-',
								{
									xtype : 'button',
									text : '启用',
									name : 'start',
									id : 'start',
									iconCls : 'accept',
									width : 80,
									handler : function() {
										Ext.Ajax
												.request({
													url : 'T70101Action.asp?method=switch',
													params : {
														rowId : grid
																.getSelectionModel()
																.getSelected()
																.get('rowId'),
														txnId : '70101',
														subTxnId : '04'
													},
													success : function(rsp, opt) {
														var rspObj = Ext
																.decode(rsp.responseText);
														if (rspObj.success) {
															showSuccessMsg(
																	rspObj.msg,
																	grid);
														} else {
															showErrorMsg(
																	rspObj.msg,
																	grid);
														}
														grid.getStore().load();
													}
												});
									}
								},
								'-',
								{
									xtype : 'button',
									text : '停用',
									name : 'stop',
									id : 'stop',
									iconCls : 'stop',
									width : 80,
									handler : function() {
										Ext.Ajax
												.request({
													url : 'T70101Action.asp?method=switch',
													params : {
														rowId : grid
																.getSelectionModel()
																.getSelected()
																.get('rowId'),
														txnId : '70101',
														subTxnId : '05'
													},
													success : function(rsp, opt) {
														var rspObj = Ext
																.decode(rsp.responseText);
														if (rspObj.success) {
															showSuccessMsg(
																	rspObj.msg,
																	grid);
														} else {
															showErrorMsg(
																	rspObj.msg,
																	grid);
														}
														grid.getStore().load();
													}
												});
									}
								} ],
						listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
							'render' : function() {
								tbar1.render(this.tbar);
							}
						},
						bbar : new Ext.PagingToolbar({
							store : withDrawFeeStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
					});

			grid.getSelectionModel().on(
					{
						'rowselect' : function() {
							// 行高亮
							Ext.get(
									grid.getView().getRow(
											grid.getSelectionModel().last))
									.frame();
							Ext.getCmp('edit').enable();
							Ext.getCmp('delete').enable();
							if (grid.getSelectionModel().getSelected().get(
									'status') == '0') {
								Ext.getCmp('start').disable();
								Ext.getCmp('stop').enable();
							} else {
								Ext.getCmp('start').enable();
								Ext.getCmp('stop').disable();
							}
						}
					});

			withDrawFeeStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
					start : 0,
					queryMchtNo : Ext.getCmp('idqueryCardAccpId').getValue()

				});
				Ext.getCmp('edit').disable();
				Ext.getCmp('delete').disable();
				Ext.getCmp('start').disable();
				Ext.getCmp('stop').disable();
			});

			// 添加
			var addForm = new Ext.form.FormPanel({
				frame : true,
				autoHeight : true,
				labelWidth : 80,
				autoScroll : true,
				layout : 'column',
				waitMsgTarget : true,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [
						{
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'dynamicCombo',
								//methodName : 'getHaveAcctMchntId',
								methodName : 'getMchntIdAll',
								fieldLabel : '商户编号*',
								blankText : '商户编号不能为空',
								emptyText : '请选择商户编号',
								hiddenName : 'mbWithdrawFeeAdd.merchantId',
								width : 250,
								mode : 'local',
								triggerAction : 'all',
								forceSelection : true,
								selectOnFocus : true,
								editable : true,
								allowBlank : false,
								lazyRender : true
							} ]
						},
						{
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '计费类型',
								width : 250,
								id : 'feeTypeAdd',
								allowBlank : false,
								hiddenName : 'mbWithdrawFeeAdd.feeType',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField', 'displayField' ],
									data : [ [ '01', '百分比' ], [ '02', '单笔' ] ]
								}),
								listeners : {
									'select' : function() {
										if (this.getValue() == '01') {
											Ext.getCmp('rateAdd').setValue('');
											Ext.getCmp('maxFeeAdd')
													.setDisabled(false);
											Ext.getCmp('minFeeAdd')
													.setDisabled(false);
										} else {
											Ext.getCmp('rateAdd').setValue('');
											Ext.getCmp('maxFeeAdd')
													.setDisabled(true);
											Ext.getCmp('maxFeeAdd')
													.setValue('');
											Ext.getCmp('minFeeAdd')
													.setDisabled(true);
											Ext.getCmp('minFeeAdd')
													.setValue('');
										}
									}
								}
							} ]
						},
						{
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '费用值',
								allowBlank : false,
								regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
								regexText : '费用值：[百分比：舍去百分号的值];[单笔：精确到分的值]',
								maxLength : 12,
								vtype : 'isOverMax',
								width : 250,
								id : 'rateAdd',
								name : 'mbWithdrawFeeAdd.rate'
							} ]
						},
						{
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '费用最小值',
								disabled : true,
								regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
								regexText : '费用最小值精确到分，如123.45',
								maxLength : 12,
								vtype : 'isOverMax',
								width : 250,
								id : 'minFeeAdd',
								name : 'mbWithdrawFeeAdd.minFee',
								listeners : {
									'blur' : function(th) {
										var max = addForm.findById('maxFeeAdd')
												.getValue();
										var min = th.getValue();
										if (null != min && '' != min
												&& null != max && '' != max) {

											if (parseFloat(max) < parseFloat(min)) {
												Ext.Msg.alert('错误提示',
														'最小值不能大于最大值');

											}

										}

									}

								}

							} ]
						},
						{
							columnWidth : .99,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '费用最大值',
								disabled : true,
								regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
								regexText : '费用最大值精确到分，如123.45',
								maxLength : 12,
								vtype : 'isOverMax',
								width : 250,
								id : 'maxFeeAdd',
								name : 'mbWithdrawFeeAdd.maxFee',
								listeners : {
									'blur' : function(th) {
										var min = addForm.findById('minFeeAdd')
												.getValue();
										var max = th.getValue();
										if (null != min && '' != min
												&& null != max && '' != max) {

											if (parseFloat(max) < parseFloat(min)) {
												Ext.Msg.alert('错误提示',
														'最大值不能小于最小值');

											}

										}

									}

								}
							} ]
						}, {
							columnWidth : .99,
							layout : 'form',
							hidden : true,
							items : [ {
								xtype : 'datefield',
								id : 'startDateAdd',
								format : 'Y-m-d',
								editable : false,
								width : 250,
								name : 'mbWithdrawFeeAdd.startDate',
								vtype : 'daterange',
								fieldLabel : '生效日期',
								endDateField : 'endDateAdd',
								blankText : '请选择生效日期'
							} ]
						}, {
							columnWidth : .99,
							layout : 'form',
							hidden : true,
							items : [ {
								xtype : 'datefield',
								id : 'endDateAdd',
								format : 'Y-m-d',
								editable : false,
								width : 250,
								name : 'mbWithdrawFeeAdd.endDate',
								vtype : 'daterange',
								fieldLabel : '失效日期',
								startDateField : 'startDateAdd',
								blankText : '请选择失效日期'
							} ]
						} ]
			});

			var addWin = new Ext.Window({
				title : '新增计费信息',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 390,
				autoHeight : true,
				layout : 'fit',
				items : [ addForm ],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [ {
					text : '确定',
					handler : function() {
						if (addForm.getForm().isValid()) {
							addForm.getForm().submit({
								url : 'T70101Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									showSuccessMsg(action.result.msg, addForm);
									// 重置表单
									addForm.getForm().reset();
									// 重新加载参数列表
									grid.getStore().reload();
									addWin.hide();
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, addForm);
								},
								params : {
									txnId : '70101',
									subTxnId : '01'

								}
							});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						addForm.getForm().reset();
					}
				} ]
			});

			// 修改商户提现费率
			var updForm = new Ext.form.FormPanel({
				frame : true,
				autoHeight : true,
				labelWidth : 80,
				autoScroll : true,
				layout : 'column',
				waitMsgTarget : true,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [ {
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'displayfield',
						fieldLabel : '商户编号*',
						name : 'mbWithdrawFeeUpd.merchantId',
						width : 250
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'combo',
						fieldLabel : '计费类型',
						width : 250,
						id : 'feeTypeUpd',
						allowBlank : false,
						hiddenName : 'mbWithdrawFeeUpd.feeType',
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [ [ '01', '百分比' ], [ '02', '单笔' ] ]
						}),
						listeners : {
							'select' : function() {
								if (this.getValue() == '01') {
									Ext.getCmp('rateUpd').setValue('');
									Ext.getCmp('maxFeeUpd').setDisabled(false);
									Ext.getCmp('minFeeUpd').setDisabled(false);
								} else {
									Ext.getCmp('rateUpd').setValue('');
									Ext.getCmp('maxFeeUpd').setDisabled(true);
									Ext.getCmp('maxFeeUpd').setValue('');
									Ext.getCmp('minFeeUpd').setDisabled(true);
									Ext.getCmp('minFeeUpd').setValue('');
								}
							}
						}
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '费用值',
						allowBlank : false,
						regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
						regexText : '费用值：[百分比：舍去百分号的值];[单笔：精确到分的值]',
						maxLength : 12,
						vtype : 'isOverMax',
						width : 250,
						id : 'rateUpd',
						name : 'mbWithdrawFeeUpd.rate'
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '费用最大值',
						disabled : true,
						regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
						regexText : '费用最大值精确到分，如123.45',
						maxLength : 12,
						vtype : 'isOverMax',
						width : 250,
						id : 'maxFeeUpd',
						name : 'mbWithdrawFeeUpd.maxFee'
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '费用最小值',
						disabled : true,
						regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
						regexText : '费用最小值精确到分，如123.45',
						maxLength : 12,
						vtype : 'isOverMax',
						width : 250,
						id : 'minFeeUpd',
						name : 'mbWithdrawFeeUpd.minFee'
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					hidden : true,
					items : [ {
						xtype : 'datefield',
						id : 'startDateUpd',
						format : 'Y-m-d',
						editable : false,
						width : 250,
						name : 'mbWithdrawFeeUpd.startDate',
						vtype : 'daterange',
						fieldLabel : '生效日期',
						endDateField : 'endDateUpd',
						blankText : '请选择生效日期'
					} ]
				}, {
					columnWidth : .99,
					layout : 'form',
					hidden : true,
					items : [ {
						xtype : 'datefield',
						id : 'endDateUpd',
						format : 'Y-m-d',
						editable : false,
						width : 250,
						name : 'mbWithdrawFeeUpd.endDate',
						vtype : 'daterange',
						fieldLabel : '失效日期',
						startDateField : 'startDateUpd',
						blankText : '请选择失效日期'
					} ]
				} ]
			});

			function updateReset() {
				var rec = grid.getSelectionModel().getSelected().data;

				updForm.getForm().findField('mbWithdrawFeeUpd.merchantId')
						.setValue(rec.merchantId);
				Ext.getCmp('feeTypeUpd').setValue(rec.feeType);
				if (rec.feeType == '01') {
					Ext.getCmp('maxFeeUpd').setDisabled(false);
					Ext.getCmp('minFeeUpd').setDisabled(false);
				}
				Ext.getCmp('rateUpd').setValue(rec.rate);
				Ext.getCmp('maxFeeUpd').setValue(rec.maxFee);
				Ext.getCmp('minFeeUpd').setValue(rec.minFee);
			}

			var updWin = new Ext.Window(
					{
						title : '修改计费信息',
						initHidden : true,
						header : true,
						frame : true,
						closable : true,
						modal : true,
						width : 390,
						autoHeight : true,
						layout : 'fit',
						items : [ updForm ],
						buttonAlign : 'center',
						closeAction : 'hide',
						iconCls : 'logo',
						resizable : false,
						buttons : [
								{
									text : '确定',
									handler : function() {
										if (updForm.getForm().isValid()) {
											updForm
													.getForm()
													.submit(
															{
																url : 'T70101Action.asp?method=update',
																waitMsg : '正在提交，请稍后......',
																success : function(
																		form,
																		action) {
																	showSuccessMsg(
																			action.result.msg,
																			updForm);
																	// 重置表单
																	updForm
																			.getForm()
																			.reset();
																	// 重新加载参数列表
																	grid
																			.getStore()
																			.reload();
																	updWin
																			.hide();
																},
																failure : function(
																		form,
																		action) {
																	showErrorMsg(
																			action.result.msg,
																			updForm);
																},
																params : {
																	rowId : grid
																			.getSelectionModel()
																			.getSelected()
																			.get(
																					'rowId'),
																	txnId : '70101',
																	subTxnId : '02'
																}
															});
										}
									}
								}, {
									text : '重置',
									handler : function() {
										updateReset();
									}
								} ]
					});

			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ grid ],
				renderTo : Ext.getBody()
			});
		});