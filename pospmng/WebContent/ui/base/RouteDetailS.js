

function showRouteDetailS(ruleId, brhId, accpId, El) {

	// 商户MCC数据集
	var routeMccStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});

	var baseStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'loadRecordAction.asp?storeId=getRouteRuleInf'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data'
						}, [{
									name : 'brhId',
									mapping : 'brhId'
								}, {
									name : 'accpId',
									mapping : 'accpId'
								}, {
									name : 'ruleId',
									mapping : 'ruleId'
								}, {
									name : 'priority',
									mapping : 'priority'
								}, {
									name : 'txnNum',
									mapping : 'txnNum'
								}, {
									name : 'cardTp',
									mapping : 'cardTp'
								}, {
									name : 'ruleCode',
									mapping : 'ruleCode'
								}, {
									name : 'cardBin',
									mapping : 'cardBin'
								}, {
									name : 'mcc',
									mapping : 'mcc'
								}, {
									name : 'insIdCd',
									mapping : 'insIdCd'
								}, {
									name : 'mchtArea',
									mapping : 'mchtArea'
								}, {
									name : 'dateCtl',
									mapping : 'dateCtl'
								}, {
									name : 'dateBeg',
									mapping : 'dateBeg'
								}, {
									name : 'dateEnd',
									mapping : 'dateEnd'
								}, {
									name : 'timeCtl',
									mapping : 'timeCtl'
								}, {
									name : 'timeBeg',
									mapping : 'timeBeg'
								}, {
									name : 'timeEnd',
									mapping : 'timeEnd'
								}, {
									name : 'amtCtl',
									mapping : 'amtCtl'
								}, {
									name : 'amtBeg',
									mapping : 'amtBeg'
								}, {
									name : 'amtEnd',
									mapping : 'amtEnd'
								}, {
									name : 'onFlag',
									mapping : 'onFlag'
								}, {
									name : 'DetailMchtGrp',
									mapping : 'mchtGroup'
								}]),
				autoLoad : false
			});

	var detailForm = new Ext.form.FormPanel({
		frame : true,
		region : 'center',
		// autoHeight : true,
		// width: 380,
		// defaultType: 'textfield',
		// labelWidth : 110,
		autoScroll : true,
		// width : 900,
		height : 410,
		// layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '规则基本信息',
			layout : 'column',
			collapsible : true,
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
									xtype : 'combofordispaly',
									name : 'ruleId',
									labelWidth : 80,
									// id:'ruleId',
									fieldLabel : '规则序号',
									value : ruleId,
									width : 150
								}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['0', '<font color="red">停用</font>'],
										['1', '<font color="green">启用</font>']],
								reader : new Ext.data.ArrayReader()
							}),
							displayField : 'displayField',
							labelWidth : 80,
							valueField : 'valueField',
							hiddenName : 'onFlag',
							fieldLabel : '路由状态',
							width : 150
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'routeRuleCode',
							fieldLabel : '目的规则',
							labelWidth : 80,
							// id: 'firstMchtCdId',
							hiddenName : 'ruleCode',
							allowBlank : false,
							blankText : '目的规则不能为空',
							emptyText : '请选择目的规则',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 150
								// value: '*'
							}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '低'], ['1', '中低'],
												['2', '中'], ['3', '中高'],
												['4', '高']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '优先级别',
							labelWidth : 80,
							// id: 'priorityId',
							hiddenName : 'priority',
							allowBlank : false,
							blankText : '优先级别不能为空',
							emptyText : '请选择优先级别',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 150
								// value: '*'
						}]
					}, {
						columnWidth : .66,
						layout : 'form',
						// width: 150,
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'routeBrhId',
							fieldLabel : '渠道编号',
							labelWidth : 80,
							// id: 'brhIdId',
							hiddenName : 'brhId',
							allowBlank : false,
							blankText : '渠道编号不能为空',
							emptyText : '请选择渠道编号',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							value : brhId,
							width : 500
								// value: '*'
							}]
					}, {
						columnWidth : .66,
						layout : 'form',
						items : [{
									xtype : 'combofordispaly',
									baseParams : 'routeMchtNo',
									fieldLabel : '商户编号',
									// id: 'accpIdId',
									hiddenName : 'accpId',
									allowBlank : false,
									blankText : '商户编号不能为空',
									emptyText : '请选择商户编号',
									mode : 'local',
									labelWidth : 80,
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 500,
									value : accpId
								}]
					}]
		}, {
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '商户控制',
			layout : 'column',
			labelWidth : 70,
			collapsible : true,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combofordispaly',
					baseParams : 'ROUTE_MCHNT_GRP_ALL',
					fieldLabel : '商户组别',
					// id: 'DetailMchtGrpId',
					hiddenName : 'DetailMchtGrp',
					allowBlank : true,
					blankText : '商户组别不能为空',
					emptyText : '请选择商户组别',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 150,
					// value: '*',
					listeners : {
						'select' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccDetailId').show();
								// detailForm.getForm().findField('mccDetailId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', detailForm.getForm()
												.findField('DetailMchtGrp')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));

										});
							} else {
								Ext.getCmp('mccDetailId').hide();
								// detailForm.getForm().findField('mccDetailId').hide();
								Ext.getCmp('mccDetail').setValue('*');
							}

						},
						'change' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccDetailId').show();
								// detailForm.getForm().findField('mccDetailId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', detailForm.getForm()
												.findField('DetailMchtGrp')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));
										});
							} else {
								Ext.getCmp('mccDetailId').hide();
								// detailForm.getForm().findField('mccDetailId').hide();
								Ext.getCmp('mccDetail').setValue('*');
							}
						}
					}
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				id : 'mccDetailId',
				hidden : true,
				items : [{
					xtype : 'combofordispaly',
					// store: MCC,
					baseParams : 'routeRuleMccDtl',
					fieldLabel : '商户MCC',
					id : 'mccDetail',
					hiddenName : 'mcc',
					allowBlank : false,
					blankText : '商户MCC不能为空',
					emptyText : '请选择商户MCC',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250
						// value: '*'
					}]
			}, {
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combofordispaly',
					baseParams : 'routeMchtArea',
					fieldLabel : '商户地区',
					// id: 'mchtAreaId',
					hiddenName : 'mchtArea',
					allowBlank : false,
					blankText : '商户地区不能为空',
					emptyText : '请选择商户地区',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 150
						// value: '*'
					}]
			}

			]
		}, {
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '卡片控制',
			layout : 'column',
			collapsible : true,
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [ {
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combofordispaly',
					baseParams : 'routeBankCd',
					fieldLabel : '发卡银行',
					// id: 'insIdCdId',
					hiddenName : 'insIdCd',
					allowBlank : false,
					blankText : '发卡银行不能为空',
					emptyText : '请选择发卡银行',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250
						// value: '*'
					}]
			},{
				columnWidth : .33,
				layout : 'form',
				items : [{
					xtype : 'combofordispaly',
					store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['*', '*-无限制'], ['00', '借记卡'],
										['01', '贷记卡'], ['02', '准借记卡'],
										['03', '预付费卡'],['04', '公务卡']],
								reader : new Ext.data.ArrayReader()
							}),
					displayField : 'displayField',
					valueField : 'valueField',
					fieldLabel : '卡类型',
					// id: 'cardTpId',
					hiddenName : 'cardTp',
					allowBlank : false,
					blankText : '卡类型不能为空',
					emptyText : '请选择卡类型',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 150
						// value: '*'
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				items : [{
							xtype : 'displayfield',
							// id: 'cardBin',
							name : 'cardBin',
							fieldLabel : '卡BIN',
							width : 150
						}]
			}

			]
		},

		{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '交易控制',
			layout : 'column',
			labelWidth : 70,
			collapsible : true,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'routeTxnTp',
							fieldLabel : '交易类型',
							// id: 'txnNumId',
							hiddenName : 'txnNum',
							allowBlank : false,
							blankText : '交易类型不能为空',
							emptyText : '请选择交易类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 500
								// value: '*'
							}]
					},{
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combofordispaly',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定金额段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '金额限制',
								// id: 'amtCtlId',
								hiddenName : 'amtCtl',
								allowBlank : false,
								blankText : '金额限制不能为空',
								emptyText : '请选择金额限制',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								lazyRender : true,
								width : 150,
								listeners : {
									'select' : function() {
										if (this.value == '1') {
											Ext.getCmp('amtBegDetailId').show();
											Ext.getCmp('amtEndDetailId').show();
											// Ext.getCmp('amtBegDetail').allowBlank=false;
											// Ext.getCmp('amtEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('amtBegDetailId').hide();
											Ext.getCmp('amtEndDetailId').hide();
											Ext.getCmp('amtBegDetail').allowBlank = true;
											Ext.getCmp('amtEndDetail').allowBlank = true;
											Ext.getCmp('amtBegDetail')
													.setValue('');
											Ext.getCmp('amtEndDetail')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('amtBegDetailId').show();
											Ext.getCmp('amtEndDetailId').show();
											// Ext.getCmp('amtBegDetail').allowBlank=false;
											// Ext.getCmp('amtEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('amtBegDetailId').hide();
											Ext.getCmp('amtEndDetailId').hide();
											Ext.getCmp('amtBegDetail').allowBlank = true;
											Ext.getCmp('amtEndDetail').allowBlank = true;
											Ext.getCmp('amtBegDetail')
													.setValue('');
											Ext.getCmp('amtEndDetail')
													.setValue('');
										}
									}
								}
									// value: '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'amtBegDetailId',
							items : [{
										xtype : 'displayfield',
										fieldLabel : '起始金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'amtBegDetail',
										name : 'amtBeg',
										blankText : '起始金额不能为空',
										emptyText : '请输入起始金额',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							id : 'amtEndDetailId',
							hidden : true,
							items : [{
										xtype : 'displayfield',
										fieldLabel : '结束金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'amtEndDetail',
										name : 'amtEnd',
										blankText : '结束金额不能为空',
										emptyText : '请输入起始金额',
										width : 150
									}]
						}]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combofordispaly',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定日期段'],
													['2', '单日'], ['3', '双日'],
													['4', '周末'], ['5', '周一至周五']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '日期限制',
								// id: 'dateCtlId',
								hiddenName : 'dateCtl',
								allowBlank : false,
								blankText : '日期限制不能为空',
								emptyText : '请选择日期限制',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								lazyRender : true,
								width : 150,
								listeners : {
									'select' : function() {
										if (this.value == '1') {
											Ext.getCmp('dateBegDetailId')
													.show();
											Ext.getCmp('dateEndDetailId')
													.show();
											// Ext.getCmp('dateBegDetail').allowBlank=false;
											// Ext.getCmp('dateEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('dateBegDetailId')
													.hide();
											Ext.getCmp('dateEndDetailId')
													.hide();
											Ext.getCmp('dateBegDetail').allowBlank = true;
											Ext.getCmp('dateEndDetail').allowBlank = true;
											Ext.getCmp('dateBegDetail')
													.setValue('');
											Ext.getCmp('dateEndDetail')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('dateBegDetailId')
													.show();
											Ext.getCmp('dateEndDetailId')
													.show();
											// Ext.getCmp('dateBegDetail').allowBlank=false;
											// Ext.getCmp('dateEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('dateBegDetailId')
													.hide();
											Ext.getCmp('dateEndDetailId')
													.hide();
											Ext.getCmp('dateBegDetail').allowBlank = true;
											Ext.getCmp('dateEndDetail').allowBlank = true;
											Ext.getCmp('dateBegDetail')
													.setValue('');
											Ext.getCmp('dateEndDetail')
													.setValue('');
										}
									}
								}
									// value: '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateBegDetailId',
							items : [{
										xtype : 'displayfield',
										fieldLabel : '起始日期',
										id : 'dateBegDetail',
										name : 'dateBeg',
										format : 'Y-m-d',
										blankText : '起始日期不能为空',
										emptyText : '请输入起始日期',
										editable : false,
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateEndDetailId',
							items : [{
										xtype : 'displayfield',
										fieldLabel : '结束日期',
										id : 'dateEndDetail',
										name : 'dateEnd',
										format : 'Y-m-d',
										blankText : '结束日期不能为空',
										emptyText : '请输入结束日期',
										editable : false,
										width : 150
									}]
						}]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combofordispaly',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定时间段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '时间限制',
								// id: 'timeCtlId',
								hiddenName : 'timeCtl',
								allowBlank : false,
								blankText : '时间限制不能为空',
								emptyText : '请选择时间限制',
								mode : 'local',
								triggerAction : 'all',
								editable : false,
								lazyRender : true,
								width : 150,
								listeners : {
									'select' : function() {
										if (this.value == '1') {
											Ext.getCmp('timeBegDetailId')
													.show();
											Ext.getCmp('timeEndDetailId')
													.show();
											// Ext.getCmp('timeBegDetail').allowBlank=false;
											// Ext.getCmp('timeEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('timeBegDetailId')
													.hide();
											Ext.getCmp('timeEndDetailId')
													.hide();
											Ext.getCmp('timeBegDetail').allowBlank = true;
											Ext.getCmp('timeEndDetail').allowBlank = true;
											Ext.getCmp('timeBegDetail')
													.setValue('');
											Ext.getCmp('timeEndDetail')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('timeBegDetailId')
													.show();
											Ext.getCmp('timeEndDetailId')
													.show();
											// Ext.getCmp('timeBegDetail').allowBlank=false;
											// Ext.getCmp('timeEndDetail').allowBlank=false;
										} else {
											Ext.getCmp('timeBegDetailId')
													.hide();
											Ext.getCmp('timeEndDetailId')
													.hide();
											Ext.getCmp('timeBegDetail').allowBlank = true;
											Ext.getCmp('timeEndDetail').allowBlank = true;
											Ext.getCmp('timeBegDetail')
													.setValue('');
											Ext.getCmp('timeEndDetail')
													.setValue('');
										}
									}
								}
									// value: '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'timeBegDetailId',
							items : [{
										xtype : 'displayfield',
										fieldLabel : '起始时间',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'timeBegDetail',
										name : 'timeBeg',
										// value: '00:00',
										blankText : '起始时间不能为空',
										emptyText : '请输入起始时间',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'timeEndDetailId',
							items : [{
										xtype : 'displayfield',
										fieldLabel : '结束时间',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'timeEndDetail',
										name : 'timeEnd',
										// value: '23:59',
										blankText : '结束时间不能为空',
										emptyText : '请输入结束时间',
										width : 150
									}]
						}]
					}]
		}

		]
	});
	var detailWin = new Ext.Window({
				// title: '路由规则配置',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 900,
				// autoHeight : true,
				layout : 'fit',
				items : [detailForm],
				buttonAlign : 'center',
				closeAction : 'close',
				iconCls : 'logo',
				resizable : false,
				autoScroll : true,
				buttons : [
				/*
				 * ,{ text: '关闭', handler: function() { paramWin.hide(grid); } }
				 */
				]
			});

	baseStore.load({
		params : {
			ruleId : ruleId,
			brhId : brhId,
			accpId : accpId
		},
		callback : function(records, options, success) {
			if (success) {
				detailForm.getForm().loadRecord(baseStore.getAt(0));
				detailForm.getForm().clearInvalid();
				detailWin.setTitle('路由规则详细信息');
				if (baseStore.getAt(0).data.cardBin == '*') {
					// Ext.getCmp('cardBin').setValue('*-无限制');
					detailForm.getForm().findField('cardBin').setValue('*-无限制');
				}
				var mcc = baseStore.getAt(0).data.mcc;
				/*if (mcc == '*') {
					detailForm.getForm().findField('DetailMchtGrp')
							.setValue('*-无限制');
				} else {
					T10600.getMchtGp(baseStore.getAt(0).data.mcc,
							function(ret) {
								if (ret == null) {
									showErrorMsg("找不到商户组别", detailWin);
									return;
								}
								detailForm.getForm().findField('DetailMchtGrp')
										.setValue(ret);
							});
				}*/

				detailWin.show();
				Ext.getCmp('mccDetailId').show();
				/*if (baseStore.getAt(0).data.mcc != '*') {
					Ext.getCmp('mccDetailId').show();
					// detailForm.getForm().findField('mccDetailId').show();
				} else {
					Ext.getCmp('mccDetailId').hide();
					// detailForm.getForm().findField('mccDetailId').hide();
				}*/

				if (baseStore.getAt(0).data.dateCtl != '*') {
					detailForm
							.getForm()
							.findField('dateBeg')
							.setValue(formatDt(baseStore.getAt(0).data.dateBeg));
					detailForm
							.getForm()
							.findField('dateEnd')
							.setValue(formatDt(baseStore.getAt(0).data.dateEnd));
					Ext.getCmp('dateBegDetailId').show();
					Ext.getCmp('dateEndDetailId').show();
				} else {
					detailForm.getForm().findField('dateBeg').setValue('');
					detailForm.getForm().findField('dateEnd').setValue('');
					Ext.getCmp('dateBegDetailId').hide();
					Ext.getCmp('dateEndDetailId').hide();
				}

				if (baseStore.getAt(0).data.timeCtl != '*') {
					detailForm.getForm().findField('timeBeg')
							.setValue(formatDt(baseStore.getAt(0).data.timeBeg
									.substring(0, 4)));
					detailForm.getForm().findField('timeEnd')
							.setValue(formatDt(baseStore.getAt(0).data.timeEnd
									.substring(0, 4)));
					Ext.getCmp('timeBegDetailId').show();
					Ext.getCmp('timeEndDetailId').show();
				} else {
					detailForm.getForm().findField('timeBeg').setValue('');
					detailForm.getForm().findField('timeEnd').setValue('');
					Ext.getCmp('timeBegDetailId').hide();
					Ext.getCmp('timeEndDetailId').hide();
				}

				if (baseStore.getAt(0).data.amtCtl != '*') {
					detailForm.getForm().findField('amtBeg')
							.setValue((+baseStore.getAt(0).data.amtBeg.toString())/100);
					detailForm.getForm().findField('amtEnd')
							.setValue((+baseStore.getAt(0).data.amtEnd.toString())/100);
					Ext.getCmp('amtBegDetailId').show();
					Ext.getCmp('amtEndDetailId').show();
				} else {
					detailForm.getForm().findField('amtBeg').setValue('');
					detailForm.getForm().findField('amtEnd').setValue('');
					Ext.getCmp('amtBegDetailId').hide();
					Ext.getCmp('amtEndDetailId').hide();
				}

				showCmpProcess(detailForm, '正在加载路由信息，请稍后......');
				hideCmpProcess(detailForm, 1000);

			} else {
				showErrorMsg("加载路由信息失败，请刷新数据后重试", detailForm);
			}
		}
	});
}