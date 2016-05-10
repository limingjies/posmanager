Ext.onReady(function() {
	
	
	
	var selectedRecord;
	// 商户
	var mchntStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHNT_NO', function(ret) {
				mchntStore.loadData(Ext.decode(ret));
			});
	// 终端类型数据集
	var termTypeStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('TERM_TYPE', function(ret) {
				termTypeStore.loadData(Ext.decode(ret));
			});
	// 模板数据集
	var modelStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_MODEL', function(ret) {
		modelStore.loadData(Ext.decode(ret));
	});
	// 专业服务机构
	var organStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('ORGAN', function(ret) {
				organStore.loadData(Ext.decode(ret));
			});
	// EPOS版本号
	var eposStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION', '',
			function(ret) {
				eposStore.loadData(Ext.decode(ret));
			});

	var mchtGroupFlag;
	var topQueryPanel = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 500,
				autoHeight : true,
				labelWidth : 80,
				items : [new Ext.form.TextField({
									id : 'termNoQ',
									name : 'termNo',
									fieldLabel : '终端号',
									anchor : '60%'
								}), {
							xtype : 'dynamicCombo',
							methodName : 'getMchntNo',
							fieldLabel : '商户编号',
							hiddenName : 'mchnNo',
							id : 'mchnNoQ',
							editable : true,
							width : 300,
							
						}, {
							xtype : 'combo',
							fieldLabel : '终端状态',
							id : 'state',
							name : 'state',
							anchor : '60%',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '新增未审核'], ['1', '启用'],
												['2', '修改未审核'], ['3', '冻结未审核'],
												['4', '冻结'], ['5', '恢复未审核'],
												['6', '注销未审核'], ['7', '注销']]
									})
						}, {
							xtype : 'basecomboselect',
							baseParams : 'TERM_TYPE',
							fieldLabel : '终端类型',
							id : 'idtermtpsearch',
							hiddenName : 'termtpsearch',
							anchor : '60%'
						}, {
							width : 150,
							xtype : 'datefield',
							fieldLabel : '创建起始时间',
							format : 'Y-m-d',
							name : 'startTime',
							id : 'startTime',
							anchor : '60%'
						}, {
							width : 150,
							xtype : 'datefield',
							fieldLabel : '创建截止时间',
							format : 'Y-m-d',
							name : 'endTime',
							id : 'endTime',
							anchor : '60%'
						}],
				buttons : [{
							text : '查询',
							handler : function() {
								termStore.load();
								queryWin.hide();
							}
						}, {
							text : '重填',
							handler : function() {
								topQueryPanel.getForm().reset();
							}
						}]
			});

	var termStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'gridPanelStoreAction.asp?storeId=termInfoAll'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data',
							totalProperty : 'totalCount'
						}, [{
									name : 'termId',
									mapping : 'termId'
								}, {
									name : 'mchntNo',
									mapping : 'mchntNo'
								}, {
									name : 'mchntName',
									mapping : 'mchntName'
								}, {
								}, {
									name : 'termSerialNum',
									mapping : 'termSerialNum'
								}, {
									name : 'termSta',
									mapping : 'termSta'
								}, {
									name : 'termSignSta',
									mapping : 'termSignSta'
								}, {
									name : 'termIdId',
									mapping : 'termIdId'
								}, {
									name : 'termFactory',
									mapping : 'termFactory'
								}, {
									name : 'termMachTp',
									mapping : 'termMachTp'
								}, {
									name : 'termVer',
									mapping : 'termVer'
								}, {
									name : 'termTp',
									mapping : 'termTp'
								}, {
									name : 'termBranch',
									mapping : 'termBranch'
								}, {
									name : 'termIns',
									mapping : 'termIns'
								}, {
									name : 'recCrtTs',
									mapping : 'recCrtTs'
								}, {
									name : 'productCd',
									mapping : 'productCd'
								}])
			});

	termStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
							start : 0,
							termId : Ext.getCmp('termNoQ').getValue(),
							termSta : Ext.getCmp('state').getValue(),
							startTime : topQueryPanel.getForm()
									.findField('startTime').getValue(),
							endTime : topQueryPanel.getForm()
									.findField('endTime').getValue(),
							mchnNo : Ext.getCmp('mchnNoQ').getValue(),
							termTp : Ext.getCmp('idtermtpsearch').getValue()
						});
			});
	termStore.load();
	var termColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
				id : 'termId',
				header : '终端号',
				dataIndex : 'termId',
				width : 100
			}, {
				header : '商户号',
				dataIndex : 'mchntNo',
				width : 130,
				id : 'mchntNo'
			}, {
				header : '商户名',
				dataIndex : 'mchntName',
				width : 130,
				id : 'mchntName'
			}, {
				header : '终端状态',
				dataIndex : 'termSta',
				width : 100,
				renderer : termSta
			}/*, {
				header : '终端序列号',
				dataIndex : 'termSerialNum',
				width : 80,
				//renderer : termSta
			}*/,
			{
				header : '所属合作伙伴',
				dataIndex : 'termBranch',
				width : 180
			},{
				header : '终端SN号',
				dataIndex : 'productCd',
				width:100
			}/* {
				header : '机具库存编号',
				dataIndex : 'termIdId'
			}, {
				header : '机具厂商',
				dataIndex : 'termFactory',
				width : 100
			}, {
				header : '机具型号',
				dataIndex : 'termMachTp',
				width : 100
			}*/, {
				header : '录入日期',
				dataIndex : 'recCrtTs',
				width : 150,
				renderer : formatDt
			}]);

	var addMenu = {
		text : '添加',
		hidden: true,
		width : 85,
		iconCls : 'add',
		handler : function() {
			termPanel.setActiveTab(0);
			termWin.show();
			termWin.center();
		}
	};
	var termInfoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'loadRecordAction.asp'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data',
							idProperty : 'id'
						}, [{
									name : 'termIdUpd',
									mapping : 'id.termId'
								},{
									name : 'termIdUpdForDisp',
									mapping : 'id.termId'
								}, {
									name : 'recCrtTs',
									mapping : 'id.recCrtTs'
								}, {
									name : 'mchnNoU',
									mapping : 'mchtCd'
								}, {
									name : 'mchnNoUForDisp',
									mapping : 'mchtCd'
								}, {
									name : 'termMccUpd',
									mapping : 'termMcc'
								}, {
									name : 'termBranchUpd',
									mapping : 'termBranch'
								},
								 {
									name : 'termSerialNumU',
									mapping : 'termSerialNum'
								},{
									name : 'termSignStaU',
									mapping : 'termSignSta'
								}, {
									name : 'termFactoryUpd',
									mapping : 'termFactory'
								}, {
									name : 'termMachTpUpd',
									mapping : 'termMachTp'
								}, {
									name : 'termIdIdU',
									mapping : 'termIdId'
								}, {
									name : 'termVerUpd',
									mapping : 'termVer'
								}, {
									name : 'termVerTpUpd',
									mapping : 'termVerTp'
								}, {
									name : 'termTpU',
									mapping : 'termTp'
								}, {
									name : 'contTelUpd',
									mapping : 'contTel'
								}, {
									name : 'propTpU',
									mapping : 'propTp'
								}, {
									name : 'propInsNmUpd',
									mapping : 'propInsNm'
								}, {
									name : 'termBatchNmUpd',
									mapping : 'termBatchNm'
								}, {
									name : 'termStlmDtUpd',
									mapping : 'termStlmDt'
								}, {
									name : 'connectModeU',
									mapping : 'connectMode'
								}, {
									name : 'financeCard1Upd',
									mapping : 'financeCard1'
								}, {
									name : 'financeCard2Upd',
									mapping : 'financeCard2'
								}, {
									name : 'financeCard3Upd',
									mapping : 'financeCard3'
								}, {
									name : 'bindTel1Upd',
									mapping : 'bindTel1'
								}, {
									name : 'bindTel2Upd',
									mapping : 'bindTel2'
								}, {
									name : 'bindTel3Upd',
									mapping : 'bindTel3'
								}, {
									name : 'termAddrUpd',
									mapping : 'termAddr'
								}, {
									name: 'productCdDtl',
									mapping: 'productCd'
								}, {
									name : 'termPlaceUpd',
									mapping : 'termPlace'
								}, {
									name : 'oprNmUpd',
									mapping : 'oprNm'
								}, {
									name : 'termParaUpd',
									mapping : 'termPara'
								}, {
									name : 'termPara11Upd',
									mapping : 'termPara1'
								}, {
									name : 'termPara1Upd',
									mapping : 'termPara2'
								}, {
									name : 'keyDownSignUpd',
									mapping : 'keyDownSign'
								}, {
									name : 'paramDownSignUpd',
									mapping : 'paramDownSign'
								}, {
									name : 'icDownSignUpd',
									mapping : 'icDownSign'
								}, {
									name : 'reserveFlag1Upd',
									mapping : 'reserveFlag1'
								}, {
									name : 'misc2Upd',
									mapping : 'misc2'
								}, {
									name : 'misc2',
									mapping : 'misc2'
								},{
									name : 'txn22Upd',
									mapping : 'txn22Dtl'
								},{
									name : 'txn27Upd',
									mapping : 'txn27Dtl'
								},{
									name : 'misc1',
									mapping : 'misc1'
								}]),
				autoLoad : false
			});

	function praseTermParam(val) {
		var array = val.split("|");
		if (array[4] == undefined)
			return 0;
		array[4] = array[4].substring(2, array[4].length);
		T30101.getTermParam(array[4], function(ret) {
					var termParam = ret;
					// for(var i=0;i<=23;i++){
					// updTermForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
					// }

					updTermForm.getForm().findField("txn14Upd")
							.setValue(array[0].substring(2, array[0].length)
									.trim());
					updTermForm.getForm().findField("txn15Upd")
							.setValue(array[1].substring(2, array[1].length)
									.trim());
					updTermForm.getForm().findField("txn16Upd")
							.setValue(array[2].substring(2, array[2].length)
									.trim());
//					updTermForm.getForm().findField("txn22Upd")
//							.setValue(array[3].substring(2, array[3].length));
//					updTermForm.getForm().findField("txn27Upd")
//							.setValue(array[5].substring(2, array[5].length));
					updTermForm.getForm().findField("txn35Upd")
							.setValue(array[12].substring(2, array[12].length)
									.trim());
					// updTermForm.getForm().findField("txn36Upd").setValue(array[13].substring(2,array[13].length).trim()/100);
					// updTermForm.getForm().findField("txn37Upd").setValue(array[14].substring(2,array[14].length).trim()/100);
					// updTermForm.getForm().findField("txn38Upd").setValue(array[15].substring(2,array[15].length).trim()/100);
					// updTermForm.getForm().findField("txn39Upd").setValue(array[16].substring(2,array[16].length).trim()/100);
					// updTermForm.getForm().findField("txn40Upd").setValue(array[17].substring(2,array[17].length).trim());
					var value = Ext.getCmp('termTpU').getValue();
					if (value == '1') {
						// Ext.getCmp('accountBox2').show();
						// Ext.getCmp('financeCard1Upd').allowBlank = false;
						updTermForm.getForm().findField("termTpU")
								.setReadOnly(true);
					} else {
						// Ext.getCmp('accountBox2').hide();
						// Ext.getCmp('financeCard1Upd').allowBlank = true;
						updTermForm.getForm().findField("termTpU")
								.setReadOnly(false);
					}
					if (value == '3') {
						// updTermForm.getForm().findField("reserveFlag1Upd").setValue(1);//修改操作无默认
						updTermPanel.get('info3Upd').setDisabled(true);
						// Ext.getCmp('txn14Upd').allowBlank = true;
					} else {
						updTermPanel.get('info3Upd').setDisabled(false);
						// Ext.getCmp('txn14Upd').allowBlank = false;
						// updTermForm.getForm().findField("reserveFlag1Upd").setValue(0);//修改操作无默认
					}

				});
	}

	function praseTermParam1(val) {
		updTermForm.getForm().findField("param1Upd1").setValue(val.substring(0,
				1).trim());
		updTermForm.getForm().findField("param1Upd2").setValue(val.substring(1,
				2).trim());
		updTermForm.getForm().findField("param1Upd3").setValue(val.substring(2,
				3).trim());
		updTermForm.getForm().findField("param1Upd4").setValue(val.substring(3,
				4));

		updTermForm.getForm().findField("param1Upd").setValue(val.substring(10,
				11).trim());
		updTermForm.getForm().findField("param6Upd").setValue(val.substring(11,
				12).trim());
		updTermForm.getForm().findField("param7Upd").setValue(val.substring(12,
				13).trim());
		updTermForm.getForm().findField("param2Upd").setValue(val.substring(13,
				14));
		updTermForm.getForm().findField("param4Upd").setValue(val.substring(14,
				15));
		updTermForm.getForm().findField("param3Upd").setValue(val.substring(15,
				16));
		updTermForm.getForm().findField("param5Upd").setValue(val.substring(16,
				17));
		updTermForm.getForm().findField("param8Upd").setValue(val.substring(17,
				18));
		updTermForm.getForm().findField("termPara1P19Upd").setValue(val.substring(18,
				19));

	}

	var editMenu = {
		text : '修改',
		width : 85,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			updateTmpDealInfo(selectedRecord.get('termId'), selectedRecord.get('recCrtTs'),grid,1);
//			termInfoStore.load({
//						params : {
//							storeId : 'getTermInfo',
//							termId : selectedRecord.get('termId'),
//							recCrtTs : selectedRecord.get('recCrtTs')
//						},
//						callback : function(records, options, success) {
//							if (success) {
//								updTermForm.getForm().loadRecord(records[0]);
//								updTermPanel.setActiveTab(0);
//								var termPara = updTermForm.getForm()
//										.findField("termParaUpd").value;
//								//注释 by caotz
//								/*var args = Ext.getCmp('propTpU').getValue();
//								if (args == '2') {
//									Ext.getCmp("termPara1Upd").show();
//									Ext.getCmp("flagBox2").show();
//								} else {
//									Ext.getCmp("termPara1Upd").hide();
//									Ext.getCmp("flagBox2").hide();
//								}*/
//								Ext.getCmp("termPara1Upd").hide();
//								Ext.getCmp("flagBox2").hide();
//								if (Ext.getCmp('termTpU').value == '3') {
//									Ext.getCmp('accountBox4').show();
//									SelectOptionsDWR
//											.getComboDataWithParameter(
//													'EPOS_VERSION',
//													updTermForm
//															.getForm()
//															.findField("termBranchUpd").value,
//													function(ret) {
//														eposStore.loadData(Ext
//																.decode(ret));
//													});
//									updTermForm
//											.getForm()
//											.findField("termVersionU")
//											.setValue(Ext.getCmp('misc2Upd')
//													.getValue().substring(0, 4));
//									Ext.getCmp('termVersionU').allowBlank = false;
//								} else {
//									Ext.getCmp('accountBox4').hide();
//									updTermForm.getForm()
//											.findField("termVersionU")
//											.setValue("");
//									Ext.getCmp('termVersionU').allowBlank = true;
//								}
//								praseTermParam(termPara);
//
//								praseTermParam1(updTermForm.getForm()
//										.findField("termPara11Upd").value);
//
//								updTermWin.show();
//							} else {
//								updTermWin.hide();
//							}
//						}
//					});
		}
	};
	var copyMenu = {
		text : '复制',
		width : 85,
		iconCls : 'copy',
		disabled : true,
		handler : function() {
			selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			showConfirm('确定要复制吗？', grid, function(bt) {
						if (bt == 'yes') {
							showProcessMsg('正在复制信息，请稍后......');
							rec = grid.getSelectionModel().getSelected();
							Ext.Ajax.requestNeedAuthorise({
										url : 'T3010103Action.asp',
										params : {
											termId : selectedRecord
													.get('termId'),
											recCrtTs : selectedRecord
													.get('recCrtTs'),
											txnId : '30101',
											subTxnId : '03'
										},
										success : function(rsp, opt) {
											var rspObj = Ext
													.decode(rsp.responseText);
											termStore.reload();
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg, grid);
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
										}
									});
							grid.getTopToolbar().items.items[3].disable();
							hideProcessMsg();
						}
					});
		}
	};

	var queryWin = new Ext.Window({
				title : '查询条件',
				layout : 'fit',
				width : 500,
				autoHeight : true,
				items : [topQueryPanel],
				buttonAlign : 'center',
				closeAction : 'hide',
				resizable : false,
				closable : true,
				animateTarget : 'query',
				tools : [{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10,
								Ext.getBody().getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				}]
			});
	var qryMenu = {
		text : '详细信息',
		width : 85,
		iconCls : 'detail',
		disabled : true,
		handler : function(bt) {
			var selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			bt.disable();
			setTimeout(function() {
						bt.enable();
					}, 2000);
			selectTermInfoNew(selectedRecord.get('termId'), selectedRecord
							.get('recCrtTs'));
		}
	};

	var queryMenu = {
		text : '录入查询条件',
		width : 85,
		id : 'query',
		iconCls : 'query',
		handler : function() {
			queryWin.show();
		}
	};

	var sendTermInfo = {
		text : '补发终端信息至前置',
		width : 85,
		iconCls : 'recover',
		disabled : true,
		handler : function() {

			var selectedRecord = grid.getSelectionModel().getSelected();
			if (selectedRecord == null) {
				showAlertMsg("没有选择记录", grid);
				return;
			}
			var sendTermId = selectedRecord.get('termId');

			showConfirm('发送终端信息，终端号：' + sendTermId, grid, function(bt) {
						if (bt == 'yes') {
							showProcessMsg('正在发送，请稍后......');
							Ext.Ajax.request({
										url : 'T30201Action.asp',
										params : {
											termId : rec.get('termId'),
											recCrtTs : rec.get('recCrtTs'),
											txnId : '30201',
											subTxnId : '2'
										},
										success : function(rsp, opt) {
											var rspObj = Ext
													.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg, grid);
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											// 重新加载终端信息
											grid.getStore().reload();
										}
									});
							hideProcessMsg();
							// grid.getTopToolbar().items.items[5].disable();
						}
					});
		}
	};

	var menuArr = new Array();

	menuArr.push(qryMenu); // [0]
	menuArr.push(editMenu); // [1]
	menuArr.push(queryMenu); // [2]
	menuArr.push(addMenu); // [3]

	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
				title : '终端信息维护',
				iconCls : 'T301',
				region : 'center',
				frame : true,
				border : true,
				autoExpandColumn : 'mchntName',
				columnLines : true,
				stripeRows : true,
				store : termStore,
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				cm : termColModel,
				clicksToEdit : true,
				forceValidation : true,
				tbar : menuArr,
				renderTo : Ext.getBody(),
				loadMask : {
					msg : '正在加载终端信息列表......'
				},
				listeners:{
					'cellclick':selectableCell,
				},
				bbar : new Ext.PagingToolbar({
							store : termStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						})
			});

	grid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
					.frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
//			if (rec.get('termSta') == '0' || rec.get('termSta') == '1'
//					|| rec.get('termSta') == '2' || rec.get('termSta') == '8'
//					|| rec.get('termSta') == '9' || rec.get('termSta') == 'A'
//					|| rec.get('termSta') == 'B' || rec.get('termSta') == 'C'
//					|| rec.get('termSta') == 'D') {
			if (rec.get('termSta') == '1') {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
			// if(rec.get('termSta') == '1')
			// {
			// grid.getTopToolbar().items.items[3].enable();
			// } else {
			// grid.getTopToolbar().items.items[3].disable();
			// }
			grid.getTopToolbar().items.items[0].enable();
			// grid.getTopToolbar().items.items[5].enable();
		}
	});

	// 所属机构
	var brhStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH', function(ret) {
				brhStore.loadData(Ext.decode(ret));
			});

	// 终端库存号
	var termIdIdStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	// 终端厂商
	var manufacturerStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MANUFACTURER', function(ret) {
				manufacturerStore.loadData(Ext.decode(ret));
			});

	// 终端型号
	var terminalTypeStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});

	var panel2 = new Ext.Panel({
		title : '附加信息',
		layout : 'column',
		id : 'info2New',
		items : [{
					columnWidth : .9,
					layout : 'form',
					items : [{
								xtype : 'textfield',
								hidden : true,
								id:'isNull2New',
								width : 350
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						fieldLabel : '终端类型*',
						id : 'termTpN',
						allowBlank : false,
						hiddenName : 'termTpNew',
						width : 150,
						store : termTypeStore,
						listeners : {
							'select' : function() {
								var value1 = Ext.getCmp("termMccNew")
										.getValue();
								var value2 = Ext.getCmp('termTpN')
										.getValue();
								// 改为使用elseif判断
								if (mchtGroupFlag == '6' && value2 != '3') {
									termForm.getForm().findField("termTpN")
											.setValue(3);
									showAlertMsg("固话POS商户，终端类型只能选择固话POS",
											grid);
								} else if (mchtGroupFlag != '6'
										&& value2 == '3') {
									termForm.getForm().findField("termTpN")
											.setValue(0);
									showAlertMsg("非固话POS商户，终端类型不能选择固话POS",
											grid);
								} else if (value1 != '0000'
										&& value2 == '1') {
									termForm.getForm().findField("termTpN")
											.setValue(0);
									showAlertMsg("非财务POS商户，终端类型不能选择财务POS",
											grid);
								}
								if (Ext.getCmp('termTpN').getValue() == '3') {
									Ext.getCmp('accountBox3').show();
									termPanel.get('info3New')
											.setDisabled(true);
									// Ext.getCmp('txn14New').allowBlank =
									// true;
									// termForm.getForm().findField("reserveFlag1New").setValue(1);
									Ext.getCmp('termVerN').allowBlank = false;
								} else {
									Ext.getCmp('accountBox3').hide();
									// termPanel.get('info3New').setDisabled(false);
									// Ext.getCmp('txn14New').allowBlank =
									// false;
									// termForm.getForm().findField("reserveFlag1New").setValue(0);
									Ext.getCmp('termVerN').allowBlank = true;
								}
							}
						}
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : 'textfield',
								fieldLabel : '添加终端数量',  // 批量添加终端的数量
								maxLength : 2,
								allowBlank : false,
								vtype : 'isNumber',
								id : 'termNumNew',
								name : 'termNumNew',
								listeners: {
									'blur': function(f){  
										for(var i=2;i<=10;i++){
											(Ext.getCmp("i"+i)).hide();
											((Ext.getCmp("i"+i)).items).items[0].allowBlank=true;
										};
										var times=f.getValue();
										 
										 var term={
												 columnWidth : .5,
													layout : 'form',
													items : [{
																xtype : 'textfield',
																fieldLabel : '终端序列号',  // 批量添加终端序列号
																maxLength : 15,
																allowBlank : false,
																id : 'termSerialNum',
																name : 'termSerialNum'
																
													
													}] 
										 };
										
										 //循环添加终端序列号输入框
										 if (times > 10){
											 showSuccessMsg("不能大于10");
											 return;
										 }
										 for(var i=1;i<=times;i++){
											 
											 Ext.getCmp("i"+i).show();
											 Ext.getCmp("i"+i).items.items[0].allowBlank=false;
										 };
										 panel2.doLayout();
									}}
								
							}]
				},{
					columnWidth : .5,
					layout : 'form',
					id:"i1",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号1',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : false,
								id : 'termSerialNum1',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i2",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号2',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum2',
								name : 'termSerialNum',
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i3",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号3',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum3',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i4",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号4',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum4',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i5",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号5',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum5',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i6",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号6',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum6',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i7",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号7',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum7',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i8",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号8',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum8',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i9",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号9',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum9',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					id:"i10",
					/*html:'终端序列号 :<input type="text" id="termSerialNum" name="termSerialNum">'*/
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端序列号10',  // 批量添加终端序列号
								maxLength : 15,
								allowBlank : true,
								id : 'termSerialNum10',
								name : 'termSerialNum'
					}]
				},{
					columnWidth : .5,
					id : "accountBox1",
					hidden : true,
					layout : 'form',
					items : [{
								xtype : 'textfield',
								fieldLabel : '财务账号1*',
								maxLength : 20,
								vtype : 'isNumber',
								id : 'financeCard1New',
								name : 'financeCard1New'
							}, {
								xtype : 'textfield',
								fieldLabel : '财务账号2',
								maxLength : 20,
								vtype : 'isNumber',
								id : 'financeCard2New',
								name : 'financeCard2New'
							}, {
								xtype : 'textfield',
								fieldLabel : '财务账号3',
								maxLength : 20,
								vtype : 'isNumber',
								id : 'financeCard3New',
								name : 'financeCard3New'
							}]
				}, {
					id : 'accountBox3',
					columnWidth : .5,
					hidden : true,
					layout : 'form',
					items : [{
								xtype : 'combo',
								fieldLabel : '固话POS版本号*',
								store : eposStore,
								id : 'termVerN',
								hiddenName : 'termVerNew',
								anchor : '80%'
							}]
				}, {
					columnWidth : .9,
					layout : 'form',
					hide : true,
					items : [{
								xtype : 'textfield',
								fieldLabel : '终端安装地址',
								maxLength : 200,
								width : 350,
								id : 'termAddrNew',
								name : 'termAddrNew'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					items : [{
								xtype : 'textfield',
								fieldLabel : 'NAC 电话1*',
								maxLength : 14,
								// allowBlank: false,
								vtype : 'isNumber',
								id : 'txn14New',
								name : 'txn14New'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					items : [{
								xtype : 'textfield',
								fieldLabel : 'NAC 电话2',
								maxLength : 14,
								vtype : 'isNumber',
								id : 'txn15New',
								name : 'txn15New'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					items : [{
								xtype : 'textfield',
								fieldLabel : 'NAC 电话3',
								maxLength : 14,
								vtype : 'isNumber',
								id : 'txn16New',
								name : 'txn16New'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : 'textfield',
//								fieldLabel : '绑定电话1*',
			                    hidden: true,
								maxLength : 15,
								allowBlank : true,
								vtype : 'isNumber',
								id : 'bindTel1New',
								name : 'bindTel1New'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : 'textfield',
//								fieldLabel : '绑定电话2',
			                    hidden: true,
								maxLength : 15,
								vtype : 'isNumber',
								id : 'bindTel2New',
								name : 'bindTel2New'
							}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
								xtype : 'textfield',
//								fieldLabel : '绑定电话3',
			                    hidden: true,
								maxLength : 15,
								vtype : 'isNumber',
								id : 'bindTel3New',
								name : 'bindTel3New'
							}]
				},
				/*
				 * { columnWidth: .5, layout: 'form', items: [{ xtype:
				 * 'checkbox', fieldLabel: 'CA公钥下载标志', id: 'keyDownSignNew',
				 * name: 'keyDownSignNew', // inputValue: '1', // checked:
				 * true, listeners :{ 'check':function(r,c){
				 * if(keyDownSignNew.checked){ r.setValue('1'); }else{
				 * r.setValue('0'); } } } }] },{ columnWidth: .5, layout:
				 * 'form', items: [{ xtype: 'checkbox', fieldLabel:
				 * '终端参数下载标志', id: 'paramDownSignNew', name:
				 * 'paramDownSignNew', // inputValue: '1', // checked: true,
				 * listeners :{ 'check':function(r,c){
				 * if(paramDownSignNew.checked){ r.setValue('1'); }else{
				 * r.setValue('0'); } } } }] },{ columnWidth: .5, layout:
				 * 'form', items: [{ xtype: 'checkbox', fieldLabel:
				 * 'IC卡参数下载标志', id: 'icDownSignNew', name: 'icDownSignNew', //
				 * inputValue: '1', // checked: true, listeners :{
				 * 'check':function(r,c){ if(icDownSignNew.checked){
				 * r.setValue('1'); }else{ r.setValue('0'); } } } }] },
				 */{
					columnWidth : .5,
					layout : 'form',
					hidden : true,
					items : [{
						xtype : 'checkbox',
						fieldLabel : '绑定电话',
						id : 'reserveFlag1New',
						name : 'reserveFlag1New',
						inputValue : '1'
							// checked: true,
							// listeners :{
							// 'check':function(r,c){
							// if(reserveFlag1New.checked){
							// r.setValue('1');
							// }else{
							// r.setValue('0');
							// }
							// }
							// }
							// listeners :{
							// 'check':function(r,c){
							// if(Ext.getCmp('termTpN').value == '3')
							// r.setValue('1');
							// }
							// }
						}]
				}]
	});  
	
	
	
	
	var termPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 450,
		width : 620,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		frame : true,
		items : [{
			title : '基本信息',
			id : 'info1New',
			layout : 'column',
			frame : false,
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNull1New',
									width : 350
								}]
					}, {
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'dynamicCombo',
									methodName : 'getMchntNo',
									fieldLabel : '商户编号*',
									hiddenName : 'mchnNoNew',
									id : 'mchnNoN',
									blankText : '商户编号不能为空',
									allowBlank : false,
									editable : true,
									emptyText : '请选择商户号',
									width : 350,
									listeners: {
										'change': function(f){  
											var times=Ext.getCmp("termNumNew").getValue();
											for(var i=2;i<=10;i++){
												(Ext.getCmp("i"+i)).hide();
												((Ext.getCmp("i"+i)).items).items[0].allowBlank=true;
											};
											 
											 panel2.doLayout();
										}}
								}]
					}
					/*,{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '商户名称',
									id : 'mchtNameNew',
									readOnly : true,
									width : 350
								}]
					}*/
					,  {
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '签购单名称',
									id : 'txn22New',
									name : 'txn22New',
									readOnly : true,
									width : 350
								}]
					}, {
						columnWidth : .9,
						layout : 'form',
						hidden: true,
						items : [{
									xtype : 'textfield',
									fieldLabel : '商户英文名称',
									id : 'txn27New',
									name : 'txn27New',
									readOnly : true,
									width : 350
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '终端MCC码',
									id : 'termMccNew',
									name : 'termMccNew',
									readOnly : true
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							fieldLabel : '所属合作伙伴',
							id : 'termBranchNew',
							hiddenName : 'brhIdNew',
							store : brhStore,
							displayField : 'displayField',
							valueField : 'valueField',
							mode : 'local',
//							allowBlank : false,
							readOnly : true,
							width : 150,
							blankText : '所属合作伙伴不能为空',
							listeners : {
								'select' : function() {
									SelectOptionsDWR.getComboDataWithParameter(
											'EPOS_VERSION',
											Ext.getCmp('termBranchNew').value,
											function(ret) {
												eposStore.loadData(Ext
														.decode(ret));
											});
								}
							}
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '联系电话*',
							maxLength : 20,
							allowBlank : false,
							regex : /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
							id : 'contTelNew',
							name : 'contTelNew'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							fieldLabel : '产权属性*',
							width : 134,
							displayField: 'displayField',
							valueField: 'valueField',
							emptyText: '请选择产权属性',
							blankText: '产权属性不能为空',
							id : 'propTpN',
							allowBlank : false,
							hiddenName : 'propTpNew',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data: [['0','钱宝所有'],['1','合作伙伴所有'],['2','商户自有']]
									}),
							listeners : {
								'select' : function() {
									// 注释 by caotz
									/*
									 * var args =
									 * Ext.getCmp('propTpN').getValue(); if(args ==
									 * 2) { Ext.getCmp("termPara1New").show();
									 * Ext.getCmp("flagBox1").show(); } else {
									 * Ext.getCmp("termPara1New").hide();
									 * Ext.getCmp("flagBox1").hide(); }
									 */
									Ext.getCmp("termPara1New").hide();
									Ext.getCmp("flagBox1").hide();
								}
							}
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						hidden : true,
						items : [{
									xtype : 'combo',
									fieldLabel : '收单服务机构',
									store : organStore,
									id : 'propInsNmN',
									hiddenName : 'propInsNmNew'
								}]
					}, {
						columnWidth : .5,
						id : "flagBox1",
						hidden : true,
						layout : 'form',
						items : [{
									xtype : 'numberfield',
									fieldLabel : '第三方分成比例(%)',
									id : 'termPara1New',
									name : 'termPara1New'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							fieldLabel : '连接类型*',
							id : 'connectModeN',
							hiddenName : 'connectModeNew',
							value : ' ',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['J', '间联'], ['Z', '直联']]
									}),
							width : 146,
							readOnly : true
						}]
						// items: [{
						// xtype: 'combo',
						// fieldLabel: '连接类型*',
						// id: 'connectModeN',
						// value: 2,
						// allowBlank: false,
						// hiddenName: 'connectModeNew',
						// store: new Ext.data.ArrayStore({
						// fields: ['valueField','displayField'],
						// // data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
						// data: [['2','间联'],['1','直联']]
						// })
						// }]
					},{

						columnWidth : .5,
						layout : 'form',
						items : [{

							xtype : 'dynamicCombo',
							methodName : 'getMchntModel',
							fieldLabel : '签购单模板*',
							hiddenName : 'modelNum',
							id : 'mchnModel',
							blankText : '模板不能为空',
							allowBlank : false,
							store:modelStore,
							editable : true,
							emptyText : '请选择模板',
							width : 134,
							listeners: {
								'change': function(f){  
									var times=Ext.getCmp("termNumNew").getValue();
									for(var i=2;i<=10;i++){
										(Ext.getCmp("i"+i)).hide();
										((Ext.getCmp("i"+i)).items).items[0].allowBlank=true;
									};
									 
									 panel2.doLayout();
								}}
						
					}]
					}]

		}, 
		panel2
		, {
			title : '交易信息',
			id : 'info3New',
			// layout: 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNull3New',
									width : 350
								}]
					},

					// {
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '分期付款期数',
					// vtype: 'isNumber',
					// id: 'txn35New',
					// maxLength: 2,
					// name: 'txn35New'
					// }]
					// },{
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '分期付款限额',
					// vtype: 'isMoney',
					// maxLength: 12,
					// id: 'txn36New',
					// name: 'txn36New'
					// }]
					// },{
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '消费单笔上限 ',
					// vtype: 'isMoney',
					// maxLength: 12,
					// id: 'txn37New',
					// name: 'txn37New'
					// }]
					// },{
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '退货单笔上限',
					// id: 'txn38New',
					// vtype: 'isMoney',
					// maxLength: 12,
					// name: 'txn38New'
					// }]
					// },{
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '转帐单笔上限',
					// vtype: 'isMoney',
					// id: 'txn39New',
					// maxLength: 12,
					// name: 'txn39New'
					// }]
					// },{
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'textfield',
					// fieldLabel: '退货时间跨度',
					// vtype: 'isNumber',
					// id: 'txn40New',
					// maxLength: 2,
					// name: 'txn40New',
					// value: 30
					// }]
					// },
					{
				xtype : 'fieldset',
				title : '交易权限',
				layout : 'column',
				// collapsible : true,
				labelWidth : 70,
				width : 570,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '查询 ',
								id : 'param1New',
								name : 'param1New',
								inputValue : '1'
									// checked: true
								}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '消费 ',
										id : 'param6New',
										name : 'param6New',
										inputValue : '1',
										checked : true
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '消费撤销 ',
										id : 'param7New',
										name : 'param7New',
										inputValue : '1',
										checked : true
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '预授权 ',
										id : 'param2New',
										name : 'param2New',
										inputValue : '1'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '预授权完成 ',
										id : 'param4New',
										name : 'param4New',
										inputValue : '1'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '预授权撤销 ',
										id : 'param3New',
										name : 'param3New',
										inputValue : '1'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '预授权完成撤销 ',
										id : 'param5New',
										name : 'param5New',
										inputValue : '1'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '退货 ',
										id : 'param8New',
										name : 'param8New',
										inputValue : '1'
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '准退货 ',
										id : 'termPara1P19New',
										name : 'termPara1P19New',
										inputValue : '1'
									}]
						}]
			}, {
				xtype : 'fieldset',
				title : '卡类型权限',
				layout : 'column',
				// collapsible : true,
				labelWidth : 70,
				width : 570,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [

				{
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '借记卡 ',
										id : 'param1New1',
										name : 'param1New1',
										inputValue : '1',
										checked : true
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '贷记卡 ',
										id : 'param1New2',
										name : 'param1New2',
										inputValue : '1',
										checked : true
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '准贷记卡 ',
										id : 'param1New3',
										name : 'param1New3',
										inputValue : '1',
										checked : true
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
										xtype : 'checkbox',
										fieldLabel : '预付费卡 ',
										id : 'param1New4',
										name : 'param1New4',
										inputValue : '1',
										checked : true
									}]
						}

				]
			}]
		}
		// ,{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '离线结算 ',
		// id: 'param9New',
		// name: 'param9New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '结算调整 ',
		// id: 'param10New',
		// name: 'param10New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '公司卡转个人卡（财务POS） ',
		// id: 'param11New',
		// name: 'param11New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '个人卡转公司卡（财务POS） ',
		// id: 'param12New',
		// name: 'param12New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '卡卡转帐',
		// id: 'param13New',
		// name: 'param13New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '上笔交易查询（财务POS） ',
		// id: 'param14New',
		// name: 'param14New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '交易查询（财务POS） ',
		// id: 'param15New',
		// name: 'param15New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '定向汇款 ',
		// id: 'param16New',
		// name: 'param16New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '分期付款 ',
		// id: 'param17New',
		// name: 'param17New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '分期付款撤销 ',
		// id: 'param18New',
		// name: 'param18New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '代缴费 ',
		// id: 'param19New',
		// name: 'param19New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '电子现金 ',
		// id: 'param20New',
		// name: 'param20New',
		// checked: true,
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: 'IC现金充值 ',
		// id: 'param21New',
		// name: 'param21New',
		// disabled: true,
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '指定账户圈存',
		// id: 'param22New',
		// name: 'param22New',
		// checked: true,
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '非指定账户圈存 ',
		// id: 'param23New',
		// name: 'param23New',
		// inputValue: '1'
		// }]
		// },{
		// columnWidth: .3,
		// layout: 'form',
		// items: [{
		// xtype: 'checkbox',
		// fieldLabel: '非接快速支付 ',
		// id: 'param24New',
		// name: 'param24New',
		// checked: true,
		// inputValue: '1'
		// }]
		// }]
		// }
		]
	});

	// 外部加入监听
	Ext.getCmp("mchnNoN").on('select', function() {
		T30101.getMchnt(Ext.getCmp("mchnNoN").getValue(), function(ret) {
					if (ret == '0') {
						showErrorMsg("用户清算表找不到相应商户", grid);
						termForm.getForm().reset();
						return;
					}

					var mchntInfo = Ext
							.decode(ret.substring(1, ret.length - 1));
					Ext.getCmp("termMccNew").setValue(mchntInfo.mcc);
					Ext.getCmp("termBranchNew").setValue(mchntInfo.bankNo);
//					Ext.getCmp("mchtNameNew").setValue(mchntInfo.mchtNm);
					Ext.getCmp("txn22New").setValue(mchntInfo.mchtCnAbbr);
					Ext.getCmp("txn27New").setValue(mchntInfo.engName);
					Ext.getCmp("connectModeN").setValue(mchntInfo.connType);
					Ext.getCmp("contTelNew").setValue(mchntInfo.commTel);
					Ext.getCmp("termNumNew").setValue('1'); // 批量添加终端的数量默认为“1”
					Ext.getCmp("bindTel1New").setValue(mchntInfo.commTel);

					mchtGroupFlag = mchntInfo.mchtGroupFlag;
					SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',
							mchntInfo.bankNo, function(ret) {
								eposStore.loadData(Ext.decode(ret));
							});
					if (mchntInfo.mcc == '0000') {
						// Ext.getCmp('accountBox1').show();
						termForm.getForm().findField("termTpN").setValue(1);
						termForm.getForm().findField("termTpN")
								.setReadOnly(true);
						// termForm.getForm().findField("financeCard1New").setValue(mchntInfo.updOprId);
						// termForm.getForm().findField("financeCard1New").setReadOnly(true);
						// Ext.getCmp('financeCard1New').allowBlank = false;

						// termForm.getForm().findField("param1New").setValue(1);
						// termForm.getForm().findField("param11New").setValue(1);
						// termForm.getForm().findField("param12New").setValue(1);
						// termForm.getForm().findField("param13New").setValue(1);
						// termForm.getForm().findField("param14New").setValue(1);
						// termForm.getForm().findField("param15New").setValue(1);
					} else {
						// Ext.getCmp('accountBox1').hide();
						if (termForm.getForm().findField("termTpN").getValue() == '1') {
							termForm.getForm().findField("termTpN").setValue(0);
						};
						termForm.getForm().findField("termTpN")
								.setReadOnly(false);
						// termForm.getForm().findField("financeCard1New").setValue("");
						// termForm.getForm().findField("financeCard1New").setReadOnly(false);
						// Ext.getCmp('financeCard1New').allowBlank = true;

						// termForm.getForm().findField("param1New").setValue(0);
						// termForm.getForm().findField("param11New").setValue(0);
						// termForm.getForm().findField("param12New").setValue(0);
						// termForm.getForm().findField("param13New").setValue(0);
						// termForm.getForm().findField("param14New").setValue(0);
						// termForm.getForm().findField("param15New").setValue(0);
					}
					if (mchtGroupFlag == '6') {
						Ext.getCmp('accountBox3').show();
						termForm.getForm().findField("termTpN").setValue(3);
						// termPanel.get('info3New').setDisabled(true);
						// Ext.getCmp('txn14New').allowBlank = true;
						termForm.getForm().findField("reserveFlag1New")
								.setValue(1);
						Ext.getCmp('termVerN').allowBlank = false;
					} else {
						Ext.getCmp('accountBox3').hide();
						if (termForm.getForm().findField("termTpN").getValue() == '3') {
							termForm.getForm().findField("termTpN").setValue(0);
						};
						// termPanel.get('info3New').setDisabled(false);
						// Ext.getCmp('txn14New').allowBlank = false;
						termForm.getForm().findField("reserveFlag1New")
								.setValue(0);
						Ext.getCmp('termVerN').allowBlank = true;
					}
				});
	});

	/** ************ 终端表单 ******************** */
	var termForm = new Ext.form.FormPanel({
				frame : true,
				height : 450,
				width : 620,
				labelWidth : 100,
				waitMsgTarget : true,
				layout : 'fit',
				items : [termPanel]
			});

	/** ********* 终端信息窗口 **************** */
	var termWin = new Ext.Window({
				title : '终端添加',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 620,
				autoHeight : true,
				layout : 'fit',
				items : [termForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [{
					text : '确定',
					handler : function() {
						termPanel.setActiveTab("info3New");
						termPanel.setActiveTab("info2New");
						if (termForm.getForm().isValid()) {
							termForm.getForm().submitNeedAuthorise({
										url : 'T30101Action.asp',
										waitMsg : '正在提交，请稍后......',
										success : function(form, action) {
											showSuccessDtl(action.result.msg,
													termWin);
											// 重新加载参数列表
											grid.getStore().reload();
											// 重置表单
											termForm.getForm().reset();

											termWin.hide();
										},
										failure : function(form, action) {
											termPanel.setActiveTab('info3New');
											showErrorMsg(action.result.msg,
													termWin);
										},
										params : {
											txnId : '30101',
											subTxnId : '01'
										}
									});
						} else {
							var finded = true;
							termForm.getForm().items.each(function(f) {
										if (finded && !f.validate()) {
											var tab = f.ownerCt.ownerCt.id;
											if (tab == 'info1New'
													|| tab == 'info2New'
													|| tab == 'info3New') {
												termPanel.setActiveTab(tab);
											}
											finded = false;
										}
									});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						termForm.getForm().reset();
					}
				}, {
					text : '关闭',
					handler : function() {
						termWin.hide();
					}
				}]
			});
	/** ************** 终端修改 ************************ */
	var updTermPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 450,
		width : 620,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		frame : true,
		items : [{
			title : '基本信息',
			id : 'info1Upd',
			layout : 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd1',
									width : 350
								}]
					},
					{
						columnWidth : .5,
						layout : 'form',
						hidden : true,
						style: 'display:none',
						items : [
						         {
									xtype : 'textfield',
									fieldLabel : '终端号*',
									name : 'termIdUpd',
									id : 'termIdUpd',
									readOnly : true
								}]
					}, 
					{
						columnWidth : .3,
						layout : 'form',
						items : [{
									xtype : 'hidden',
									id : 'recCrtTs',
									name : 'recCrtTs'
								}]
					}
					, {
						columnWidth : .5,
						layout : 'form',						
						hidden : true,
						style: 'display:none',
						items : [{
									xtype : 'textfield',
									fieldLabel : '商户号*',
									name : 'mchnNoUpd',
									id : 'mchnNoU',
									readOnly : true
								}]
					}
					, {
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									fieldLabel : '签购单名称',
									id : 'txn22Upd',
									name : 'txn22Upd',
									readOnly : true,
									width : 350
								}]
					}, {
						columnWidth : .9,
						layout : 'form',
						hidden: true,
						items : [{
									xtype : 'textfield',
									fieldLabel : '商户英文名称',
									id : 'txn27Upd',
									name : 'txn27Upd',
									readOnly : true,
									width : 350
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						hidden: true,
						items : [{
									xtype : 'textfield',
									fieldLabel : '终端MCC码',
									id : 'termMccUpd',
									name : 'termMccUpd',
									readOnly : true
								}]
					},{
		                columnWidth: .5,
		                layout: 'form',
		                items: [{
		                    xtype: 'textfield',
		                    fieldLabel: '终端名称',
		                    maxLength: 40,
		                    allowBlank: true,
		                    id: 'misc2',
		                }]
		            }, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'combo',
									fieldLabel : '所属合作伙伴*',
									id : 'termBranchUpd',
									hiddenName : 'brhIdUpd',
									store : brhStore,
									displayField : 'displayField',
									valueField : 'valueField',
									mode : 'local',
									allowBlank : false,
									readOnly : true,
									width : 150,
									blankText : '所属合作伙伴不能为空',
									listeners : {
										'select' : function() {
										}
									}
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'textfield',
							fieldLabel : '联系电话*',
							maxLength : 20,
							allowBlank : false,
							regex : /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
							id : 'contTelUpd',
							name : 'contTelUpd'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							fieldLabel : '产权属性',
							id : 'propTpU',
							width : 134,
							hiddenName : 'propTpUpd',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data: [['0','钱宝所有'],['1','合作伙伴所有'],['2','商户自有']]
									}),
							listeners : {
								'select' : function() {
									// 注释by caotz
									/*
									 * var args =
									 * Ext.getCmp('propTpU').getValue(); if(args ==
									 * 2) { Ext.getCmp("termPara1Upd").show();
									 * Ext.getCmp("flagBox2").show(); } else {
									 * Ext.getCmp("termPara1Upd").hide();
									 * Ext.getCmp("flagBox2").hide(); }
									 */
									Ext.getCmp("termPara1Upd").hide();
									Ext.getCmp("flagBox2").hide();
								}
							}
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						hidden : true,
						items : [{
									xtype : 'combo',
									fieldLabel : '收单服务机构',
									store : organStore,
									id : 'propInsNmU',
									hiddenName : 'propInsNmUpd'
								}]
					}, {
						columnWidth : .5,
						id : "flagBox2",
						hidden : true,
						layout : 'form',
						items : [{
									xtype : 'numberfield',
									fieldLabel : '第三方分成比例(%)',
									id : 'termPara1Upd',
									name : 'termPara1Upd'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						hidden: true,
						items : [{
							xtype : 'combo',
							fieldLabel : '连接类型*',
							id : 'connectModeU',
							hiddenName : 'connectModeUpd',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['J', '间联'], ['Z', '直联']]
									}),
							readOnly : true,
							width : 145
						}]
						// items: [{
						// xtype: 'combo',
						// fieldLabel: '连接类型*',
						// id: 'connectModeU',
						// allowBlank: false,
						// hiddenName: 'connectModeUpd',
						// store: new Ext.data.ArrayStore({
						// fields: ['valueField','displayField'],
						// data: [['2','间联'],['1','直联']]
						// // data: [['2','间联'],['1','直联'],['3','第三方平台接入']]
						// })
						// }]
					},{


						columnWidth : .5,
						layout : 'form',
						items : [{

							xtype : 'combo',
//							methodName : 'getMchntModel',
							fieldLabel : '签购单模板*',
							hiddenName : 'misc1',
							id : 'misc1Upd',
							blankText : '模板不能为空',
							allowBlank : false,
							store:modelStore,
							editable : true,
							emptyText : '请选择模板',
							width : 134,
							listeners: {
								'change': function(f){  
									var times=Ext.getCmp("misc1Upd").getValue();
									for(var i=2;i<=10;i++){
										(Ext.getCmp("i"+i)).hide();
										((Ext.getCmp("i"+i)).items).items[0].allowBlank=true;
									};
									 
									 panel2.doLayout();
								}}
						
					}]
					
					}]
		}, {
			title : '附加信息',
			id : 'info2Upd',
			layout : 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd2',
									width : 350
								}]
					},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					fieldLabel : '终端类型*',
					id : 'termTpU',
					allowBlank : false,
					hiddenName : 'termTpUpd',
					store : termTypeStore,
					width : 150,
					listeners : {
//						'select' : function() {
//							var value = Ext.getCmp('termTpU').getValue();
//							T30101.getMchnt(Ext.getCmp("mchnNoU").getValue(),
//									function(ret) {
//										var mchntInfo = Ext.decode(ret
//												.substring(1, ret.length - 1));
//										mchtGroupFlag = mchntInfo.mchtGroupFlag;
//										// 合并判断,等待DWR返回结果后处理
//
//										if (mchtGroupFlag == '6'
//												&& value != '3') {
//											updTermForm.getForm()
//													.findField("termTpU")
//													.setValue(3);
//											showAlertMsg(
//													"固话POS商户，终端类型只能选择固话POS",
//													grid);
//										} else if (Ext.getCmp("termMccUpd")
//												.getValue() == '0000'
//												&& value != '1') {
//											updTermForm.getForm()
//													.findField("termTpU")
//													.setValue(1);
//											showAlertMsg(
//													"财务POS商户，终端类型只能选择财务POS",
//													grid);
//										} else if (mchtGroupFlag != '6'
//												&& value == '3') {
//											updTermForm.getForm()
//													.findField("termTpU")
//													.setValue(0);// 为保证正确性,均重置为普通POS
//											showAlertMsg(
//													"非固话POS商户，终端类型不能选择固话POS",
//													grid);
//										} else if (Ext.getCmp("termMccUpd")
//												.getValue() != '0000'
//												&& value == '1') {
//											updTermForm.getForm()
//													.findField("termTpU")
//													.setValue(0);// 为保证正确性,均重置为普通POS
//											showAlertMsg(
//													"非财务POS商户，终端类型不能选择财务POS",
//													grid);
//										}
//										// 电话POS判断
//										if (Ext.getCmp('termTpU').getValue() == '3') {
//											Ext.getCmp('accountBox4').show();
//											SelectOptionsDWR
//													.getComboDataWithParameter(
//															'EPOS_VERSION',
//															updTermForm
//																	.getForm()
//																	.findField("termBranchUpd").value,
//															function(ret) {
//																eposStore
//																		.loadData(Ext
//																				.decode(ret));
//															});
//											// Ext.getCmp('txn14Upd').allowBlank
//											// = true;
//											updTermForm
//													.getForm()
//													.findField("reserveFlag1Upd")
//													.setValue(1);
//											updTermPanel.get('info3Upd')
//													.setDisabled(true);
//											Ext.getCmp('termVersionU').allowBlank = false;
//										} else {
//											Ext.getCmp('accountBox4').hide();
//											// Ext.getCmp('txn14Upd').allowBlank
//											// = false;
//											updTermForm
//													.getForm()
//													.findField("reserveFlag1Upd")
//													.setValue(0);
//											updTermPanel.get('info3Upd')
//													.setDisabled(false);
//											Ext.getCmp('termVersionU').allowBlank = true;
//										}
//
//										// 财务POS判断
//										if (Ext.getCmp('termTpU').getValue() == '1') {
//											// Ext.getCmp('accountBox2').show();
//											// Ext.getCmp('financeCard1Upd').allowBlank
//											// = false;
//											updTermForm.getForm()
//													.findField("param1Upd")
//													.setValue(1);
//											updTermForm.getForm()
//													.findField("param11Upd")
//													.setValue(1);
//											updTermForm.getForm()
//													.findField("param12Upd")
//													.setValue(1);
//											updTermForm.getForm()
//													.findField("param13Upd")
//													.setValue(1);
//											updTermForm.getForm()
//													.findField("param14Upd")
//													.setValue(1);
//											updTermForm.getForm()
//													.findField("param15Upd")
//													.setValue(1);
//										} else {
//											// Ext.getCmp('accountBox2').hide();
//											// Ext.getCmp('financeCard1Upd').allowBlank
//											// = true;
//											updTermForm.getForm()
//													.findField("param11Upd")
//													.setValue(0);
//											updTermForm.getForm()
//													.findField("param12Upd")
//													.setValue(0);
//											updTermForm.getForm()
//													.findField("param13Upd")
//													.setValue(0);
//											updTermForm.getForm()
//													.findField("param14Upd")
//													.setValue(0);
//											updTermForm.getForm()
//													.findField("param15Upd")
//													.setValue(0);
//										}
//									});
//						}
					}
				}]
			}, {
				id : 'accountBox2',
				columnWidth : .5,
				layout : 'form',
				hidden : 'true',
				items : [{
							xtype : 'textfield',
							fieldLabel : '财务账号1*',
							maxLength : 16,
							vtype : 'isNumber',
							id : 'financeCard1Upd',
							name : 'financeCard1Upd'
						}, {
							xtype : 'textfield',
							fieldLabel : '财务账号2',
							maxLength : 16,
							vtype : 'isNumber',
							id : 'financeCard2Upd',
							name : 'financeCard2Upd'
						}, {
							xtype : 'textfield',
							fieldLabel : '财务账号3',
							maxLength : 20,
							vtype : 'isNumber',
							id : 'financeCard3Upd',
							name : 'financeCard3Upd'
						}]
			}, {
				id : 'accountBox4',
				columnWidth : .5,
				hidden : true,
				layout : 'form',
				items : [{
							xtype : 'combo',
							fieldLabel : '固话POS版本号*',
							store : eposStore,
							id : 'termVersionU',
							hiddenName : 'termVersion',
							anchor : '80%'
						}]
			}, {
				columnWidth : .9,
				layout : 'form',
				items : [{
							xtype : 'textfield',
							fieldLabel : '终端安装地址',
							maxLength : 175,
							width:350,
							id : 'termAddrUpd',
							name : 'termAddrUpd'
						}]
			},{				
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
					fieldLabel : '终端SN号',
                    id: 'productCdDtl',
                    name: 'productCdDtl',
					maxLength : 50
                }]
			}, {
				columnWidth : .5,
				layout : 'form',
				hidden : true,
				items : [{
							xtype : 'textfield',
							fieldLabel : 'NAC 电话1*',
							maxLength : 14,
							vtype : 'isNumber',
							id : 'txn14Upd',
							name : 'txn14Upd'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				hidden : true,
				items : [{
							xtype : 'textfield',
							fieldLabel : 'NAC 电话2',
							maxLength : 14,
							vtype : 'isNumber',
							id : 'txn15Upd',
							name : 'txn15Upd'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				hidden : true,
				items : [{
							xtype : 'textfield',
							fieldLabel : 'NAC 电话3',
							maxLength : 14,
							vtype : 'isNumber',
							id : 'txn16Upd',
							name : 'txn16Upd'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'textfield',
//							fieldLabel : '绑定电话1*',
		                    hidden: true,
							maxLength : 15,
							allowBlank : true,
							vtype : 'isNumber',
							id : 'bindTel1Upd',
							name : 'bindTel1Upd'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'textfield',
//							fieldLabel : '绑定电话2',
		                    hidden: true,
							maxLength : 15,
							vtype : 'isNumber',
							id : 'bindTel2Upd',
							name : 'bindTel2Upd'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'textfield',
//							fieldLabel : '绑定电话3',
		                    hidden: true,
							maxLength : 15,
							vtype : 'isNumber',
							id : 'bindTel3Upd',
							name : 'bindTel3Upd'
						}]
			},
					// {
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'checkbox',
					// fieldLabel: 'CA公钥下载标志',
					// id: 'keyDownSignUpd',
					// name: 'keyDownSignUpd',
					// inputValue: '1',
					// listeners :{
					// 'check':function(r,c){
					// r.setValue('1');
					// }
					// }
					// }]
					// },
					// {
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'checkbox',
					// fieldLabel: '终端参数下载标志',
					// id: 'paramDownSignUpd',
					// name: 'paramDownSignUpd',
					// inputValue: '1',
					// listeners :{
					// 'check':function(r,c){
					// r.setValue('1');
					// }
					// }
					// }]
					// },
					// {
					// columnWidth: .5,
					// layout: 'form',
					// items: [{
					// xtype: 'checkbox',
					// fieldLabel: 'IC卡参数下载标志',
					// id: 'icDownSignUpd',
					// name: 'icDownSignUpd',
					// inputValue: '1',
					// listeners :{
					// 'check':function(r,c){
					// r.setValue('1');
					// }
					// }
					// }]
					// },
					{
						columnWidth : .5,
						layout : 'form',
						hidden: true,
						items : [{
							xtype : 'checkbox',
							fieldLabel : '绑定电话',
							id : 'reserveFlag1Upd',
							name : 'reserveFlag1Upd',
							inputValue : '1'
								// listeners :{
								// 'check':function(r,c){
								// if(Ext.getCmp('termTpU').value == '3')
								// r.setValue('1');
								// }
								// }
							}]
					}]
		}, {
			title : '交易信息',
			id : 'info3Upd',
			// layout: 'column',
			// defaults : {
			// bodyStyle : 'padding-left: 10px'
			// },
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd3',
									width : 350
								}]
					},{
						xtype : 'fieldset',
						title : '交易权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [{
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款期数',
//												vtype : 'isNumber',
												id : 'txn35Upd',
//												maxLength : 2,
												name : 'txn35Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款限额',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn36Upd',
												name : 'txn36Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '消费单笔上限 ',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn37Upd',
												name : 'txn37Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货单笔上限',
//												vtype : 'isMoney',
												id : 'txn38Upd',
//												maxLength : 12,
												name : 'txn38Upd'
											}]
								}, {
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '转帐单笔上限',
//												vtype : 'isMoney',
												id : 'txn39Upd',
//												maxLength : 12,
												name : 'txn39Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货时间跨度',
//												vtype : 'isNumber',
												id : 'txn40Upd',
//												maxLength : 2,
												name : 'txn40Upd',
												value : 30
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '查询 ',
												id : 'param1Upd',
												name : 'param1Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费 ',
												id : 'param6Upd',
												name : 'param6Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费撤销 ',
												id : 'param7Upd',
												name : 'param7Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权 ',
												id : 'param2Upd',
												name : 'param2Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成 ',
												id : 'param4Upd',
												name : 'param4Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权撤销 ',
												id : 'param3Upd',
												name : 'param3Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成撤销 ',
												id : 'param5Upd',
												name : 'param5Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '退货 ',
												id : 'param8Upd',
												name : 'param8Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准退货 ',
												id : 'termPara1P19Upd',
												name : 'termPara1P19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '离线结算 ',
												id : 'param9Upd',
												name : 'param9Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '结算调整 ',
												id : 'param10Upd',
												name : 'param10Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '公司卡转个人卡（财务POS） ',
												id : 'param11Upd',
												name : 'param11Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '个人卡转公司卡（财务POS） ',
												id : 'param12Upd',
												name : 'param12Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '卡卡转帐',
												id : 'param13Upd',
												name : 'param13Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '上笔交易查询（财务POS） ',
												id : 'param14Upd',
												name : 'param14Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '交易查询（财务POS） ',
												id : 'param15Upd',
												name : 'param15Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '定向汇款 ',
												id : 'param16Upd',
												name : 'param16Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款 ',
												id : 'param17Upd',
												name : 'param17Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款撤销 ',
												id : 'param18Upd',
												name : 'param18Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '代缴费 ',
												id : 'param19Upd',
												name : 'param19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '电子现金 ',
												id : 'param20Upd',
												name : 'param20Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : 'IC现金充值 ',
												id : 'param21Upd',
												name : 'param21Upd',
												disabled : true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '指定账户圈存',
												id : 'param22Upd',
												name : 'param22Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非指定账户圈存 ',
												id : 'param23Upd',
												name : 'param23Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非接快速支付 ',
												id : 'param24Upd',
												name : 'param24Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									items : [{
												xtype : 'hidden',
												id : 'termParaUpd',
												name : 'termParaUpd'
											}, {
												xtype : 'hidden',
												id : 'termPara11Upd',
												name : 'termPara11Upd'
											}, {
												xtype : 'hidden',
												id : 'misc2Upd',
												name : 'misc2Upd'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '卡类型权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [

						{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '借记卡 ',
												id : 'param1Upd1',
												name : 'param1Upd1',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '贷记卡 ',
												id : 'param1Upd2',
												name : 'param1Upd2',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准贷记卡 ',
												id : 'param1Upd3',
												name : 'param1Upd3',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预付费卡 ',
												id : 'param1Upd4',
												name : 'param1Upd4',
												inputValue : '1'
											}]
								}

						]
					}

			]
		}]
	});
	/** ***************** 终端修改表单 ******************** */
	var updTermForm = new Ext.form.FormPanel({
				frame : true,
				height : 450,
				width : 620,
				labelWidth : 100,
				waitMsgTarget : true,
				layout : 'form',
				items : [{
		             xtype: 'displayfield',
		             fieldLabel: '终端号',
		             labelStyle: 'padding-left: 10px',
		             //name: 'termIdUpd'
		             id: 'termIdUpdForDisp'
		         },{
		             xtype: 'displayfield',
		             fieldLabel: '商户编号',
		             labelStyle: 'padding-left: 10px',
		             //name: 'mchnNoUpd',
		             id: 'mchnNoUForDisp'
		           },updTermPanel]
			});

	/** ***************** 终端修改信息 ******************** */
	var updTermWin = new Ext.Window({
		title : '终端修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		width : 620,
		autoHeight : true,
		layout : 'fit',
		items : [updTermForm],
		buttonAlign : 'center',
		closeAction : 'close',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				updTermPanel.setActiveTab("info2Upd");
				updTermPanel.setActiveTab("info3Upd");
				if (updTermForm.getForm().isValid()) {
					updTermForm.getForm().submitNeedAuthorise({
								url : 'T3010102Action.asp',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									showSuccessMsg(action.result.msg,
											updTermForm);
									grid.getStore().reload();
									updTermForm.getForm().reset();
									updTermWin.hide();
									grid.getTopToolbar().items.items[2]
											.disable();
								},
								failure : function(form, action) {
									updTermPanel.setActiveTab('info3Upd');
									showErrorMsg(action.result.msg, updTermForm);
								},
								params : {
									txnId : '30101',
									subTxnId : '02'
								}
							});
				} else {
					var finded = true;
					updTermForm.getForm().items.each(function(f) {
								if (finded && !f.validate()) {
									var tab = f.ownerCt.ownerCt.id;
									if (tab == 'info1Upd' || tab == 'info2Upd'
											|| tab == 'info3Upd') {
										updTermPanel.setActiveTab(tab);
									}
									finded = false;
								}
							});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				updTermWin.hide();
			}
		}]
	});
	var mainUI = new Ext.Viewport({
				layout : 'border',
				renderTo : Ext.getBody(),
				items : [grid]
			});
	
});