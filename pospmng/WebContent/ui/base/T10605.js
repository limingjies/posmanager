Ext.onReady(function() {

	// 商户MCC数据集
	var routeMccStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('routeRuleMccDtl', function(ret) {
				routeMccStore.loadData(Ext.decode(ret));
			});
	// 数据集
	var routeRuleStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'gridPanelStoreAction.asp?storeId=getRouteRule'
						}),
				reader : new Ext.data.JsonReader({
							root : 'data',
							totalProperty : 'totalCount',
							id:'ruleId'
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
								},{
									name : 'mchtGroup',
									mapping : 'mchtGroup'
								}]),
				autoLoad : true
			});

	var routeRuleColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '规则序号',
				dataIndex : 'ruleId',
				align : 'center',
				width : 80,
				hidden : true
			}, {
				header : '渠道编号',
				dataIndex : 'brhId',
				id : 'brhId',
				width : 190,
				renderer : routeRule
			}, {
				header : '商户编号',
				dataIndex : 'accpId',
				width : 240,
				renderer : routeRule
			}, {
				header : '目的规则',
				dataIndex : 'ruleCode',
				width : 120,
				id : 'ruleCode'
			}, {
				header : '优先级别',
				dataIndex : 'priority',
				width : 100,
				renderer : routePriority
			}, {
				header : '路由状态',
				dataIndex : 'onFlag',
				width : 100,
				align : 'center',
				renderer : routeStatus
			}, {
				header : '交易类型',
				dataIndex : 'txnNum',
				width : 100,
				align : 'center',
				renderer : routeTxnTp
			}, {
				header : '卡类型',
				dataIndex : 'cardTp',
				width : 100,
				align : 'center',
				renderer : routeCardTp
			}]);

	var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [{
							xtype : 'textfield',
							id : 'queryRuleId',
							name : 'queryRuleId',
							vtype : 'isNumber',
							maxLength : 6,
							hidden : true,
							width : 140
						}, '-', '优先级别：', {
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '低'], ['1', '中低'],
												['2', '中'], ['3', '中高'],
												['4', '高']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'queryPriority',
							id : 'queryPriorityId',
							editable : false,
							emptyText : '请选择',
							width : 140
						}, '-', '路由状态：', {
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '停用'], ['1', '启用']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'queryOnFlag',
							editable : false,
							emptyText : '请选择',
							id : 'queryOnFlagId',
							width : 140
						}, '-', '卡类型：', {
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['*', '无限制'], ['00', '借记卡'],
												['01', '贷记卡'], ['02', '准借记卡'],
												['03', '预付费卡'],['04','公务卡']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							editable : false,
							emptyText : '请选择',
							hiddenName : 'queryCardTp',
							id : 'queryCardTpId',
							width : 140
						}, '-', '交易类型：', {
							xtype : 'basecomboselect',
							id : 'queryTxnNumId',
							baseParams : 'routeTxnTp',
							hiddenName : 'queryTxnNum',
							width : 140
						}]
			})

	var tbar2 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : ['-', '渠道编号：', {
//							xtype : 'basecomboselect',
//							baseParams : 'routeBrhId',
							xtype : 'dynamicCombo',
							methodName : 'getBrhIdRoute',
							id: 'queryBrhIdId',
							hiddenName: 'queryBrhId',
							lazyRender: true,
							width: 250
						}, '-', '商户编号：', {
							// xtype: 'basecomboselect',
							// baseParams: 'routeMchtNo',
							xtype : 'dynamicCombo',
							methodName : 'getMchntIdRoute',
							hiddenName : 'queryAccpId',
							// hidden:true,
							width : 250,
							id : 'queryAccpIdId',
							mode : 'local',
							triggerAction : 'all',
							// forceSelection: true,
							// typeAhead: true,
							// selectOnFocus: true,
							editable : true,
							// allowBlank: true,
							lazyRender : true
						}, '-', '目的规则：', {
							xtype : 'basecomboselect',
							baseParams : 'routeRuleCode',
							hiddenName : 'queryRuleCode',
							// hidden:true,
							width : 250,
							id : 'queryRuleCodeId',
							mode : 'local',
							triggerAction : 'all',
							// forceSelection: true,
							// typeAhead: true,
							// selectOnFocus: true,
							editable : true,
							// allowBlank: true,
							lazyRender : true
						}]
			})

	var grid = new Ext.grid.GridPanel({
		title : '路由规则配置',
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		region : 'center',
		clicksToEdit : true,
		autoExpandColumn : 'ruleCode',
		store : routeRuleStore,
		sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
		cm : routeRuleColModel,
		forceValidation : true,
		// renderTo: Ext.getBody(),
		loadMask : {
			msg : '正在加载路由记录列表......'
		},
		tbar : [{

					xtype : 'button',
					text : '查询',
					name : 'query',
					id : 'query',
					iconCls : 'query',
					width : 80,
					handler : function() {

						routeRuleStore.load();
					}
				}, '-', {
					xtype : 'button',
					text : '重置',
					name : 'reset',
					id : 'reset',
					iconCls : 'reset',
					width : 80,
					handler : function() {

						Ext.getCmp('queryRuleId').setValue('');
						Ext.getCmp('queryPriorityId').setValue('');
						Ext.getCmp('queryOnFlagId').setValue('');
						Ext.getCmp('queryCardTpId').setValue('');

						Ext.getCmp('queryBrhIdId').setValue('');
						Ext.getCmp('queryAccpIdId').setValue('');
						// Ext.getCmp('queryFirstMchtCdId').setValue('');
						Ext.getCmp('queryRuleCodeId').setValue('');
						Ext.getCmp('queryTxnNumId').setValue('');
					}

				}, '-', {

					xtype : 'button',
					text : '查看详细信息',
					name : 'detail',
					id : 'detail',
					iconCls : 'detail',
					width : 80,
					disabled : true,
					handler : function(bt) {

						bt.disable();
						setTimeout(function() {
									bt.enable()
								}, 2000);
						var ruleId = grid.getSelectionModel().getSelected()
								.get('ruleId');
						var brhId = grid.getSelectionModel().getSelected()
								.get('brhId').substring(
										0,
										grid.getSelectionModel().getSelected()
												.get('brhId').indexOf('-'));
						var accpId = grid.getSelectionModel().getSelected()
								.get('accpId').substring(
										0,
										grid.getSelectionModel().getSelected()
												.get('accpId').indexOf('-'));
						showRouteDetailS(ruleId, brhId, accpId, grid);
					}
				}, '-', {
					xtype : 'button',
					text : '路由规则新增',
					name : 'add',
					id : 'add',
					iconCls : 'add',
					width : 80,
					handler : function() {
						addWin.show();
						addWin.center();

					}

				}, '-', {
					xtype : 'button',
					text : '路由规则修改',
					name : 'edit',
					id : 'edit',
					disabled : true,
					iconCls : 'edit',
					width : 80,
					handler : function() {
						var select = grid.getSelectionModel().getSelected();
						if (select == null) {
							showMsg("请选择一条记录后再进行操作。", grid);
							return;
						}
						// updateStore.load({
						// params: {
						//		
						// ruleId:
						// grid.getSelectionModel().getSelected().get('ruleId'),
						// brhId :
						// grid.getSelectionModel().getSelected().get('brhId').substring(0,grid.getSelectionModel().getSelected().get('brhId').indexOf('-')),
						// accpId:
						// grid.getSelectionModel().getSelected().get('accpId').substring(0,grid.getSelectionModel().getSelected().get('accpId').indexOf('-'))
						// },
						// callback: function(records, options, success){
						// if(success){
						// updForm.getForm().loadRecord(updateStore.getAt(0));
						var data = select.data;
						updForm.getForm().findField('tblRouteRuleUpd.brhId')
								.setValue(data.brhId.substring(0,
										data.brhId.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.accpId')
								.setValue(data.accpId.substring(0,
										data.accpId.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.ruleCode')
								.setValue(data.ruleCode.substring(0,
										data.ruleCode.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.priority')
								.setValue(data.priority);
						updForm.getForm().findField('tblRouteRuleUpd.txnNum')
								.setValue(data.txnNum.substring(0,
										data.txnNum.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.cardTp')
								.setValue(data.cardTp);
						updForm.getForm().findField('tblRouteRuleUpd.cardBin')
								.setValue(data.cardBin);
						updForm.getForm().findField('tblRouteRuleUpd.mcc')
								.setValue(data.mcc);
						updForm.getForm().findField('tblRouteRuleUpd.insIdCd')
								.setValue(data.insIdCd);
						updForm.getForm().findField('tblRouteRuleUpd.mchtArea')
								.setValue(data.mchtArea);
						updForm.getForm().findField('tblRouteRuleUpd.dateCtl')
								.setValue(data.dateCtl);
						// updForm.getForm().findField('dateBeg').setValue(data.dateBeg);
						// updForm.getForm().findField('dateEnd').setValue(data.dateEnd);
						updForm.getForm().findField('tblRouteRuleUpd.timeCtl')
								.setValue(data.timeCtl);
						updForm.getForm().findField('tblRouteRuleUpd.timeBeg')
								.setValue(data.timeBeg);
						updForm.getForm().findField('tblRouteRuleUpd.timeEnd')
								.setValue(data.timeEnd);
						updForm.getForm().findField('tblRouteRuleUpd.amtCtl')
								.setValue(data.amtCtl);
						updForm.getForm().findField('tblRouteRuleUpd.amtBeg')
								.setValue(data.amtBeg);
						updForm.getForm().findField('tblRouteRuleUpd.amtEnd')
								.setValue(data.amtEnd);
						updForm.getForm().findField('tblRouteRuleUpd.mchtGroup')
								.setValue(data.mchtGroup);
						updForm.getForm().clearInvalid();

						if (data.cardBin == '*') {
							updForm.getForm()
									.findField('tblRouteRuleUpd.cardBin')
									.setValue('');
						}

						var mcc = data.mcc;
						/*if (mcc == '*') {
							updForm.getForm().findField('tblRouteRuleUpd.mchtGroup')
									.setValue('*-无限制');
						} else {
							T10600.getMchtGp(select.data.mcc, function(ret) {
								if (ret == null) {
									showErrorMsg("找不到商户组别", updWin);
									return;
								}
								updForm.getForm().findField('tblRouteRuleUpd.mchtGroup')
										.setValue(ret);

								routeMccStore.removeAll();
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', updForm.getForm()
												.findField('tblRouteRuleUpd.mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));
										})
									// updForm.getForm().findField('mcc').setValue(data.mcc);
								});
						}*/

						updWin.show();
						updWin.center();

						if (select.data.mchtGroup != '*') {
							routeMccStore.removeAll();
							SelectOptionsDWR.getComboDataWithParameter(
									'ROUTE_MCHNT_TP', updForm.getForm()
											.findField('tblRouteRuleUpd.mchtGroup')
											.getValue(), function(ret) {
										routeMccStore.loadData(Ext
												.decode(ret));
							})
							Ext.getCmp('mccUpdIdId').show();
									// updForm.getForm().findField('mcc').setValue(data.mcc);
								
							// detailForm.getForm().findField('mccDetailId').show();
						} else {
							Ext.getCmp('mccUpdIdId').hide();
							// detailForm.getForm().findField('mccDetailId').hide();
						}

						if (select.data.dateCtl != '*') {
							updForm.getForm().findField('dateBeg')
									.setValue(formatDt(select.data.dateBeg));
							updForm.getForm().findField('dateEnd')
									.setValue(formatDt(select.data.dateEnd));
							// updForm.getForm().findField('dateBeg').setValue(select.data.dateBeg);
							// updForm.getForm().findField('dateEnd').setValue(select.data.dateEnd);
							Ext.getCmp('dateBegUpdId').show();
							Ext.getCmp('dateEndUpdId').show();
						} else {
							updForm.getForm().findField('dateBeg').setValue('');
							updForm.getForm().findField('dateEnd').setValue('');
							Ext.getCmp('dateBegUpdId').hide();
							Ext.getCmp('dateEndUpdId').hide();
						}

						if (select.data.timeCtl != '*') {
							updForm.getForm()
									.findField('tblRouteRuleUpd.timeBeg')
									.setValue(formatDt(select.data.timeBeg
											.substring(0, 4)));
							updForm.getForm()
									.findField('tblRouteRuleUpd.timeEnd')
									.setValue(formatDt(select.data.timeEnd
											.substring(0, 4)));
							Ext.getCmp('timeBegUpdId').show();
							Ext.getCmp('timeEndUpdId').show();
						} else {
							updForm.getForm()
									.findField('tblRouteRuleUpd.timeBeg')
									.setValue('');
							updForm.getForm()
									.findField('tblRouteRuleUpd.timeEnd')
									.setValue('');
							Ext.getCmp('timeBegUpdId').hide();
							Ext.getCmp('timeEndUpdId').hide();
						}

						if (select.data.amtCtl != '*') {
							updForm.getForm().findField('tblRouteRuleUpd.amtBeg')
								.setValue((+select.data.amtBeg.toString())/100);
							updForm.getForm().findField('tblRouteRuleUpd.amtEnd')
								.setValue((+select.data.amtEnd.toString())/100);

							Ext.getCmp('amtBegUpdId').show();
							Ext.getCmp('amtEndUpdId').show();
						} else {
							Ext.getCmp('amtBegUpdId').hide();
							Ext.getCmp('amtEndUpdId').hide();
						}
						
						showCmpProcess(updForm, '正在加载路由信息，请稍后......');
						hideCmpProcess(updForm, 1000);

					}

				}, '-', {
					xtype : 'button',
					text : '路由规则删除',
					name : 'delete',
					id : 'delete',
					disabled : true,
					iconCls : 'delete',
					width : 80,
					handler : function() {
						showConfirm('确定要删除该路由规则吗？', grid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T10602Action.asp?method=delete',
									params : {
										ruleId : grid.getSelectionModel()
												.getSelected().get('ruleId'),
										brhId : grid
												.getSelectionModel()
												.getSelected()
												.get('brhId')
												.substring(
														0,
														grid
																.getSelectionModel()
																.getSelected()
																.get('brhId')
																.indexOf('-')),
										accpId : grid
												.getSelectionModel()
												.getSelected()
												.get('accpId')
												.substring(
														0,
														grid
																.getSelectionModel()
																.getSelected()
																.get('accpId')
																.indexOf('-')),
										txnId : '10602',
										subTxnId : '03'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, grid);

										} else {
											showErrorMsg(rspObj.msg, grid);
										}
										grid.getStore().reload();
										Ext.getCmp('detail').disable();
										Ext.getCmp('edit').disable();
										Ext.getCmp('delete').disable();
									}
								});
							}
						})

					}

				}],
		listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
			'render' : function() {
				tbar1.render(this.tbar);
				tbar2.render(this.tbar);
			}
		},
		bbar : new Ext.PagingToolbar({
					store : routeRuleStore,
					pageSize : System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
				})
	});

	// 禁用编辑按钮
	grid.getStore().on('beforeload', function() {
				Ext.getCmp('detail').disable();
				Ext.getCmp('edit').disable();
				Ext.getCmp('delete').disable();
			});
	grid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last))
					.frame();
			Ext.getCmp('detail').enable();
			// Ext.getCmp('edit').enable();
			// Ext.getCmp('delete').enable();
			if (grid.getSelectionModel().getSelected().get('onFlag') == '0') {
				Ext.getCmp('delete').enable();
				Ext.getCmp('edit').enable();
			} else {
				Ext.getCmp('delete').disable();
				Ext.getCmp('edit').disable();
			}
		}
	});

	routeRuleStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
							start : 0,
							queryRuleId : Ext.getCmp('queryRuleId').getValue(),
							queryPriority : Ext.get('queryPriority').getValue(),
							queryOnFlag : Ext.get('queryOnFlag').getValue(),
							queryCardTp : Ext.get('queryCardTp').getValue(),

							queryBrhId : Ext.get('queryBrhId').getValue(),
							queryAccpId : Ext.get('queryAccpId').getValue(),
							// queryFirstMchtCd:Ext.get('queryFirstMchtCd').getValue(),
							queryRuleCode : Ext.get('queryRuleCode').getValue(),
							queryTxnNum : Ext.get('queryTxnNum').getValue()

						});
			});

	// 添加
	var addForm = new Ext.form.FormPanel({
		frame : true,
		// autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		// labelWidth: 100,
		// width: 900,
		height : 430,
		autoScroll : true,
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
			// collapsible : true,
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'basecomboselect',
							baseParams : 'routeRuleCode',
							fieldLabel : '目的规则*',
							hiddenName : 'tblRouteRule.ruleCode',
							allowBlank : false,
							blankText : '目的规则不能为空',
							emptyText : '请选择目的规则',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 250
								// value: '*'
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '低'], ['1', '中低'],
												['2', '中'], ['3', '中高'],
												['4', '高']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '优先级别*',
							hiddenName : 'tblRouteRule.priority',
							allowBlank : false,
							blankText : '优先级别不能为空',
							emptyText : '请选择优先级别',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250
								// value: '*'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						// width: 150,
						items : [{
									xtype : 'basecomboselect',
									baseParams : 'routeBrhId',
									fieldLabel : '渠道编号*',
									hiddenName : 'tblRouteRule.tblRouteRulePK.brhId',
									allowBlank : false,
									blankText : '渠道编号不能为空',
									emptyText : '请选择渠道编号',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250,
									value : '*'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'basecomboselect',
									baseParams : 'routeMchtNo',
//									xtype : 'dynamicCombo',
//									methodName : 'getMchntId',
									fieldLabel : '商户编号*',
									hiddenName : 'tblRouteRule.tblRouteRulePK.accpId',
									allowBlank : false,
									blankText : '商户编号不能为空',
									emptyText : '请选择商户编号',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250,
									value : '*'
								}]
					}

			]
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
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'ROUTE_MCHNT_GRP_ALL',
					fieldLabel : '商户组别*',
					hiddenName : 'tblRouteRule.mchtGroup',
					allowBlank : false,
					blankText : '商户组别不能为空',
					emptyText : '请选择商户组别',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250,
					value : '*',
					listeners : {
						'select' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', addForm.getForm()
												.findField('tblRouteRule.mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));

										});
							} else {
								Ext.getCmp('mccIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							addForm.getForm().findField('tblRouteRule.mcc')
									.setValue('*')

						},
						'change' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', addForm.getForm()
												.findField('tblRouteRule.mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));
										});
							} else {
								Ext.getCmp('mccIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							addForm.getForm().findField('tblRouteRule.mcc')
									.setValue('*')
						}
					}
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				id : 'mccIdId',
				hidden : true,
				items : [{
							xtype : 'basecomboselect',
							store : routeMccStore,
							fieldLabel : '商户MCC*',
							hiddenName : 'tblRouteRule.mcc',
							allowBlank : false,
							blankText : '商户MCC不能为空',
							emptyText : '请选择商户MCC',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 250,
							value : '*'
						}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
							xtype : 'basecomboselect',
							baseParams : 'routeMchtArea',
							fieldLabel : '商户地区*',
							hiddenName : 'tblRouteRule.mchtArea',
							allowBlank : false,
							blankText : '商户地区不能为空',
							emptyText : '请选择商户地区',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250,
							value : '*'
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
			items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
									xtype : 'basecomboselect',
									baseParams : 'routeBankCd',
									fieldLabel : '发卡银行*',
									hiddenName : 'tblRouteRule.insIdCd',
									allowBlank : false,
									blankText : '交易银行不能为空',
									emptyText : '请选择交易银行',
									mode : 'local',
									triggerAction : 'all',
									editable : true,
									lazyRender : true,
									width : 250,
									value : '*'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['*', '*-无限制'], ['00', '借记卡'],
												['01', '贷记卡'], ['02', '准借记卡'],
												['03', '预付费卡'],['04','公务卡']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '卡类型*',
							hiddenName : 'tblRouteRule.cardTp',
							allowBlank : false,
							blankText : '卡类型不能为空',
							emptyText : '请选择卡类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250,
							value : '*'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									// xtype: 'textfield',
									xtype : 'numberfield',
									id : 'tblRouteRule.cardBin',
									name : 'tblRouteRule.cardBin',
									fieldLabel : '卡BIN',
									allowBlank : true,
									// blankText: '卡BIN不能为空',
									emptyText : '*-无限制',
									vtype : 'isNumber',
									maxLength : 10,
									minLength : 6,
									width : 250
								}]
					}

			]
		}, {
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
									xtype : 'basecomboselect',
									baseParams : 'routeTxnTp',
									fieldLabel : '交易类型*',
									hiddenName : 'tblRouteRule.txnNum',
									allowBlank : false,
									blankText : '交易类型不能为空',
									emptyText : '请选择交易类型',
									mode : 'local',
									triggerAction : 'all',
									editable : false,
									lazyRender : true,
									width : 150,
									value : '*'
								}]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定金额段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '金额限制*',
								hiddenName : 'tblRouteRule.amtCtl',
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
											Ext.getCmp('amtBegId').show();
											Ext.getCmp('amtEndId').show();
											Ext.getCmp('tblRouteRule.amtBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.amtEnd').allowBlank = false;
										} else {
											Ext.getCmp('amtBegId').hide();
											Ext.getCmp('amtEndId').hide();
											Ext.getCmp('tblRouteRule.amtBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.amtEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.amtBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.amtEnd')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('amtBegId').show();
											Ext.getCmp('amtEndId').show();
											Ext.getCmp('tblRouteRule.amtBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.amtEnd').allowBlank = false;
										} else {
											Ext.getCmp('amtBegId').hide();
											Ext.getCmp('amtEndId').hide();
											Ext.getCmp('tblRouteRule.amtBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.amtEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.amtBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.amtEnd')
													.setValue('');
										}
									}
								},
								value : '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'amtBegId',
							items : [{
										xtype : 'numberfield',
										fieldLabel : '起始金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRouteRule.amtBeg',
										name : 'tblRouteRule.amtBeg',
										blankText : '起始金额不能为空',
										// emptyText: '请输入起始金额',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							id : 'amtEndId',
							hidden : true,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '结束金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRouteRule.amtEnd',
										name : 'tblRouteRule.amtEnd',
										blankText : '结束金额不能为空',
										// emptyText: '请输入起始金额',
										width : 150
									}]
						}

						]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定日期段'],
													['2', '单日'], ['3', '双日'],
													['4', '周末'], ['5', '周一至周五']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '日期限制*',
								hiddenName : 'tblRouteRule.dateCtl',
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
											Ext.getCmp('dateBegId').show();
											Ext.getCmp('dateEndId').show();
											Ext.getCmp('tblRouteRule.dateBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.dateEnd').allowBlank = false;
										} else {
											Ext.getCmp('dateBegId').hide();
											Ext.getCmp('dateEndId').hide();
											Ext.getCmp('tblRouteRule.dateBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.dateEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.dateBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.dateEnd')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('dateBegId').show();
											Ext.getCmp('dateEndId').show();
											Ext.getCmp('tblRouteRule.dateBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.dateEnd').allowBlank = false;
										} else {
											Ext.getCmp('dateBegId').hide();
											Ext.getCmp('dateEndId').hide();
											Ext.getCmp('tblRouteRule.dateBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.dateEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.dateBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.dateEnd')
													.setValue('');
										}
									}
								},
								value : '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateBegId',
							items : [{
										xtype : 'datefield',
										fieldLabel : '起始日期',
										id : 'tblRouteRule.dateBeg',
										name : 'tblRouteRule.dateBeg',
										format : 'Y-m-d',
										blankText : '起始日期不能为空',
										vtype : 'daterange',
										endDateField : 'dateEnd',
										// emptyText: '请输入起始日期',
										editable : false,
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateEndId',
							items : [{
										xtype : 'datefield',
										fieldLabel : '结束日期',
										id : 'tblRouteRule.dateEnd',
										name : 'tblRouteRule.dateEnd',
										format : 'Y-m-d',
										blankText : '结束日期不能为空',
										vtype : 'daterange',
										startDateField : 'dateBeg',
										// emptyText: '请输入结束日期',
										editable : false,
										width : 150
									}]
						}

						]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定时间段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '时间限制*',
								hiddenName : 'tblRouteRule.timeCtl',
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
											Ext.getCmp('timeBegId').show();
											Ext.getCmp('timeEndId').show();
											Ext.getCmp('tblRouteRule.timeBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.timeEnd').allowBlank = false;
											Ext.getCmp('tblRouteRule.timeBeg')
													.setValue('00:00');
											Ext.getCmp('tblRouteRule.timeEnd')
													.setValue('23:59');
										} else {
											Ext.getCmp('timeBegId').hide();
											Ext.getCmp('timeEndId').hide();
											Ext.getCmp('tblRouteRule.timeBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.timeEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.timeBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.timeEnd')
													.setValue('');

										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('timeBegId').show();
											Ext.getCmp('timeEndId').show();
											Ext.getCmp('tblRouteRule.timeBeg').allowBlank = false;
											Ext.getCmp('tblRouteRule.timeEnd').allowBlank = false;
											Ext.getCmp('tblRouteRule.timeBeg')
													.setValue('00:00');
											Ext.getCmp('tblRouteRule.timeEnd')
													.setValue('23:59');
										} else {
											Ext.getCmp('timeBegId').hide();
											Ext.getCmp('timeEndId').hide();
											Ext.getCmp('tblRouteRule.timeBeg').allowBlank = true;
											Ext.getCmp('tblRouteRule.timeEnd').allowBlank = true;
											Ext.getCmp('tblRouteRule.timeBeg')
													.setValue('');
											Ext.getCmp('tblRouteRule.timeEnd')
													.setValue('');
										}
									}
								},
								value : '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'timeBegId',
							items : [{
										xtype : 'textfield',
										fieldLabel : '起始时间(hh:mm)',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'tblRouteRule.timeBeg',
										name : 'tblRouteRule.timeBeg',
										// value: '00:00',
										blankText : '起始时间不能为空',
										// emptyText: '请输入起始时间',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'timeEndId',
							items : [{
										xtype : 'textfield',
										fieldLabel : '结束时间(hh:mm)',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'tblRouteRule.timeEnd',
										name : 'tblRouteRule.timeEnd',
										// value: '23:59',
										blankText : '结束时间不能为空',
										// emptyText: '请输入结束时间',
										width : 150
									}]
						}

						]
					}

			]
		}]
	});
	var addWin = new Ext.Window({
				title : '路由规则配置',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 900,
				autoHeight : true,
				layout : 'fit',
				items : [addForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [{
					text : '确定',
					handler : function() {
						if (addForm.getForm().isValid()) {
							addForm.getForm().submit({
										url : 'T10602Action.asp?method=add',
										waitMsg : '正在提交，请稍后......',
										success : function(form, action) {
											showSuccessMsg(action.result.msg,
													addForm);
											// 重置表单
											addForm.getForm().reset();
											// 重新加载参数列表
											grid.getStore().reload();
											addWin.hide();
										},
										failure : function(form, action) {
											showErrorMsg(action.result.msg,
													addForm);
										},
										params : {
											txnId : '10602',
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
				}
				/*
				 * ,{ text: '关闭', handler: function() { paramWin.hide(grid); } }
				 */
				]
			});

	var updForm = new Ext.form.FormPanel({
		frame : true,
//		autoHeight : true,
		// width: 380,
		// defaultType: 'textfield',
		// labelWidth : 100,
		// width : 900,
		// height : 250,
		// layout : 'column',
		height : 430,
		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '规则基本信息',
			layout : 'column',
			// collapsible : true,
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [
	{
						columnWidth : .5,
						layout : 'form',
						// width: 150,
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'routeBrhId',
							fieldLabel : '渠道编号*',
							hiddenName : 'tblRouteRuleUpd.brhId',
							allowBlank : false,
							blankText : '渠道编号不能为空',
							emptyText : '请选择渠道编号',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 250
								// value: '*'
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'routeMchtNo',
							fieldLabel : '商户编号*',
							hiddenName : 'tblRouteRuleUpd.accpId',
							allowBlank : false,
							blankText : '商户编号不能为空',
							emptyText : '请选择商户编号',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 330
								// value: '*'
							}]
					},
			{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'basecomboselect',
							baseParams : 'routeRuleCode',
							fieldLabel : '目的规则*',
							hiddenName : 'tblRouteRuleUpd.ruleCode',
							allowBlank : false,
							blankText : '目的规则不能为空',
							emptyText : '请选择目的规则',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 250
								// value: '*'
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['0', '低'], ['1', '中低'],
												['2', '中'], ['3', '中高'],
												['4', '高']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '优先级别*',
							hiddenName : 'tblRouteRuleUpd.priority',
							allowBlank : false,
							blankText : '优先级别不能为空',
							emptyText : '请选择优先级别',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250
								// value: '*'
						}]
					}

			]
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
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'ROUTE_MCHNT_GRP_ALL',
					fieldLabel : '商户组别*',
					// id: 'mchtGrpId',
					hiddenName : 'tblRouteRuleUpd.mchtGroup',
					allowBlank : false,
					blankText : '商户组别不能为空',
					emptyText : '请选择商户组别',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250,
					// value: '*',
					listeners : {
						'select' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccUpdIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', updForm.getForm()
												.findField('tblRouteRuleUpd.mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));

										});
							} else {
								Ext.getCmp('mccUpdIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							updForm.getForm().findField('tblRouteRuleUpd.mcc')
									.setValue('*')

						},
						'change' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccUpdIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', updForm.getForm()
												.findField('tblRouteRuleUpd.mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));
										});
							} else {
								Ext.getCmp('mccUpdIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							updForm.getForm().findField('tblRouteRuleUpd.mcc')
									.setValue('*')
						}
					}
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				id : 'mccUpdIdId',
				hidden : true,
				items : [{
					xtype : 'basecomboselect',
					store : routeMccStore,
					fieldLabel : '商户MCC*',
					hiddenName : 'tblRouteRuleUpd.mcc',
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
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeMchtArea',
					fieldLabel : '商户地区*',
					hiddenName : 'tblRouteRuleUpd.mchtArea',
					allowBlank : false,
					blankText : '商户地区不能为空',
					emptyText : '请选择商户地区',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250
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
			items : [

			{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'basecomboselect',
							baseParams : 'routeBankCd',
							fieldLabel : '发卡银行*',
							hiddenName : 'tblRouteRuleUpd.insIdCd',
							allowBlank : false,
							blankText : '交易银行不能为空',
							emptyText : '请选择交易银行',
							mode : 'local',
							triggerAction : 'all',
							editable : true,
							lazyRender : true,
							width : 250
								// value: '*'
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : new Ext.data.ArrayStore({
										fields : ['valueField', 'displayField'],
										data : [['*', '*-无限制'], ['00', '借记卡'],
												['01', '贷记卡'], ['02', '准借记卡'],
												['03', '预付费卡'],['04','公务卡']],
										reader : new Ext.data.ArrayReader()
									}),
							displayField : 'displayField',
							valueField : 'valueField',
							fieldLabel : '卡类型*',
							hiddenName : 'tblRouteRuleUpd.cardTp',
							allowBlank : false,
							blankText : '卡类型不能为空',
							emptyText : '请选择卡类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 250
								// value: '*'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
									// xtype: 'textfield',
									xtype : 'numberfield',
									id : 'tblRouteRuleUpd.cardBin',
									name : 'tblRouteRuleUpd.cardBin',
									fieldLabel : '卡BIN',
									allowBlank : true,
									// blankText: '卡BIN不能为空',
									emptyText : '*-无限制',
									vtype : 'isNumber',
									maxLength : 10,
									minLength : 6,
									width : 250
								}]
					}

			]
		}, {
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
							xtype : 'basecomboselect',
							baseParams : 'routeTxnTp',
							fieldLabel : '交易类型*',
							hiddenName : 'tblRouteRuleUpd.txnNum',
							allowBlank : false,
							blankText : '交易类型不能为空',
							emptyText : '请选择交易类型',
							mode : 'local',
							triggerAction : 'all',
							editable : false,
							lazyRender : true,
							width : 150
								// value: '*'
							}]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [

						{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定金额段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '金额限制*',
								hiddenName : 'tblRouteRuleUpd.amtCtl',
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
											Ext.getCmp('amtBegUpdId').show();
											Ext.getCmp('amtEndUpdId').show();
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd').allowBlank = false;
										} else {
											Ext.getCmp('amtBegUpdId').hide();
											Ext.getCmp('amtEndUpdId').hide();
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg')
													.setValue('');
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd')
													.setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('amtBegUpdId').show();
											Ext.getCmp('amtEndUpdId').show();
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd').allowBlank = false;
										} else {
											Ext.getCmp('amtBegUpdId').hide();
											Ext.getCmp('amtEndUpdId').hide();
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.amtBeg')
													.setValue('');
											Ext
													.getCmp('tblRouteRuleUpd.amtEnd')
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
							id : 'amtBegUpdId',
							items : [{
										xtype : 'numberfield',
										fieldLabel : '起始金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRouteRuleUpd.amtBeg',
										name : 'tblRouteRuleUpd.amtBeg',
										blankText : '起始金额不能为空',
										// emptyText: '请输入起始金额',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							id : 'amtEndUpdId',
							hidden : true,
							items : [{
										xtype : 'numberfield',
										fieldLabel : '结束金额',
										maxLength : 13,
										vtype : 'isMoney12',
										id : 'tblRouteRuleUpd.amtEnd',
										name : 'tblRouteRuleUpd.amtEnd',
										blankText : '结束金额不能为空',
										// emptyText: '请输入起始金额',
										width : 150
									}]
						}

						]
					}, {
						columnWidth : .99,
						layout : 'column',
						items : [{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定日期段'],
													['2', '单日'], ['3', '双日'],
													['4', '周末'], ['5', '周一至周五']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '日期限制*',
								hiddenName : 'tblRouteRuleUpd.dateCtl',
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
											Ext.getCmp('dateBegUpdId').show();
											Ext.getCmp('dateEndUpdId').show();
											Ext.getCmp('dateBeg').allowBlank = false;
											Ext.getCmp('dateEnd').allowBlank = false;
										} else {
											Ext.getCmp('dateBegUpdId').hide();
											Ext.getCmp('dateEndUpdId').hide();
											Ext.getCmp('dateBeg').allowBlank = true;
											Ext.getCmp('dateEnd').allowBlank = true;
											Ext.getCmp('dateBeg').setValue('');
											Ext.getCmp('dateEnd').setValue('');
										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('dateBegUpdId').show();
											Ext.getCmp('dateEndUpdId').show();
											Ext.getCmp('dateBeg').allowBlank = false;
											Ext.getCmp('dateEnd').allowBlank = false;
										} else {
											Ext.getCmp('dateBegUpdId').hide();
											Ext.getCmp('dateEndUpdId').hide();
											Ext.getCmp('dateBeg').allowBlank = true;
											Ext.getCmp('dateEnd').allowBlank = true;
											Ext.getCmp('dateBeg').setValue('');
											Ext.getCmp('dateEnd').setValue('');
										}
									}
								}
									// value: '*'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateBegUpdId',
							items : [{
										xtype : 'datefield',
										fieldLabel : '起始日期',
										id : 'dateBeg',
										name : 'dateBeg',
										format : 'Y-m-d',
										blankText : '起始日期不能为空',
										vtype : 'daterange',
										endDateField : 'dateEnd',
										// emptyText: '请输入起始日期',
										editable : false,
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'dateEndUpdId',
							items : [{
										xtype : 'datefield',
										fieldLabel : '结束日期',
										id : 'dateEnd',
										name : 'dateEnd',
										format : 'Y-m-d',
										blankText : '结束日期不能为空',
										vtype : 'daterange',
										startDateField : 'dateBeg',
										// emptyText: '请输入结束日期',
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
								xtype : 'combo',
								store : new Ext.data.ArrayStore({
											fields : ['valueField',
													'displayField'],
											data : [['*', '*-无限制'],
													['1', '指定时间段']],
											reader : new Ext.data.ArrayReader()
										}),
								fieldLabel : '时间限制*',
								hiddenName : 'tblRouteRuleUpd.timeCtl',
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
											Ext.getCmp('timeBegUpdId').show();
											Ext.getCmp('timeEndUpdId').show();
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg')
													.setValue('00:00');
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd')
													.setValue('23:59');
										} else {
											Ext.getCmp('timeBegUpdId').hide();
											Ext.getCmp('timeEndUpdId').hide();
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg')
													.setValue('');
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd')
													.setValue('');

										}

									},
									'change' : function() {
										if (this.value == '1') {
											Ext.getCmp('timeBegUpdId').show();
											Ext.getCmp('timeEndUpdId').show();
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd').allowBlank = false;
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg')
													.setValue('00:00');
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd')
													.setValue('23:59');
										} else {
											Ext.getCmp('timeBegUpdId').hide();
											Ext.getCmp('timeEndUpdId').hide();
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd').allowBlank = true;
											Ext
													.getCmp('tblRouteRuleUpd.timeBeg')
													.setValue('');
											Ext
													.getCmp('tblRouteRuleUpd.timeEnd')
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
							id : 'timeBegUpdId',
							items : [{
										xtype : 'textfield',
										fieldLabel : '起始时间(hh:mm)',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'tblRouteRuleUpd.timeBeg',
										name : 'tblRouteRuleUpd.timeBeg',
										// value: '00:00',
										blankText : '起始时间不能为空',
										// emptyText: '请输入起始时间',
										width : 150
									}]
						}, {
							columnWidth : .33,
							layout : 'form',
							hidden : true,
							id : 'timeEndUpdId',
							items : [{
										xtype : 'textfield',
										fieldLabel : '结束时间(hh:mm)',
										maxLength : 5,
										regex : /^([01]\d|2[0123]):([0-5]\d)$/,
										regexText : '该输入框只能输入数字0-9',
										id : 'tblRouteRuleUpd.timeEnd',
										name : 'tblRouteRuleUpd.timeEnd',
										// value: '23:59',
										blankText : '结束时间不能为空',
										// emptyText: '请输入结束时间',
										width : 150
									}]
						}]
					}

			]
		}

		]
	});
	var updWin = new Ext.Window({
		title : '路由规则修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 900,
		autoHeight : true,
		layout : 'fit',
		items : [updForm],
		buttonAlign : 'center',
		closeAction : 'hide',
//		closeAction : 'close',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (updForm.getForm().isValid()) {
					updForm.getForm().submit({
						url : 'T10602Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							grid.getStore().reload();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
							ruleId : grid.getSelectionModel().getSelected()
									.get('ruleId'),
							brhId : grid.getSelectionModel().getSelected()
									.get('brhId').substring(
											0,
											grid.getSelectionModel()
													.getSelected().get('brhId')
													.indexOf('-')),
							accpId : grid
									.getSelectionModel()
									.getSelected()
									.get('accpId')
									.substring(
											0,
											grid.getSelectionModel()
													.getSelected()
													.get('accpId').indexOf('-')),
							txnId : '10602',
							subTxnId : '02'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
				var select = grid.getSelectionModel().getSelected();
				
				
				var data = select.data;
						updForm.getForm().findField('tblRouteRuleUpd.brhId')
								.setValue(data.brhId.substring(0,
										data.brhId.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.accpId')
								.setValue(data.accpId.substring(0,
										data.accpId.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.ruleCode')
								.setValue(data.ruleCode.substring(0,
										data.ruleCode.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.priority')
								.setValue(data.priority);
						updForm.getForm().findField('tblRouteRuleUpd.txnNum')
								.setValue(data.txnNum.substring(0,
										data.txnNum.indexOf('-')).trim());
						updForm.getForm().findField('tblRouteRuleUpd.cardTp')
								.setValue(data.cardTp);
						updForm.getForm().findField('tblRouteRuleUpd.cardBin')
								.setValue(data.cardBin);
						updForm.getForm().findField('tblRouteRuleUpd.mcc')
								.setValue(data.mcc);
						updForm.getForm().findField('tblRouteRuleUpd.insIdCd')
								.setValue(data.insIdCd);
						updForm.getForm().findField('tblRouteRuleUpd.mchtArea')
								.setValue(data.mchtArea);
						updForm.getForm().findField('tblRouteRuleUpd.dateCtl')
								.setValue(data.dateCtl);
						// updForm.getForm().findField('dateBeg').setValue(data.dateBeg);
						// updForm.getForm().findField('dateEnd').setValue(data.dateEnd);
						updForm.getForm().findField('tblRouteRuleUpd.timeCtl')
								.setValue(data.timeCtl);
						updForm.getForm().findField('tblRouteRuleUpd.timeBeg')
								.setValue(data.timeBeg);
						updForm.getForm().findField('tblRouteRuleUpd.timeEnd')
								.setValue(data.timeEnd);
						updForm.getForm().findField('tblRouteRuleUpd.amtCtl')
								.setValue(data.amtCtl);
						updForm.getForm().findField('tblRouteRuleUpd.amtBeg')
								.setValue(data.amtBeg);
						updForm.getForm().findField('tblRouteRuleUpd.amtEnd')
								.setValue(data.amtEnd);
						updForm.getForm().clearInvalid();

						if (data.cardBin == '*') {
							updForm.getForm()
									.findField('tblRouteRuleUpd.cardBin')
									.setValue('');
						}
				
				
				
				var mcc = select.data.mcc;
				/*if (mcc == '*') {
					updForm.getForm().findField('tblRouteRuleUpd.mchtGroup').setValue('*-无限制');
				} else {
					T10600.getMchtGp(select.data.mcc, function(ret) {
						if (ret == null) {
							showErrorMsg("找不到商户组别", updWin);
							return;
						}
						updForm.getForm().findField('tblRouteRuleUpd.mchtGroup').setValue(ret);
						routeMccStore.removeAll();
						SelectOptionsDWR.getComboDataWithParameter(
								'ROUTE_MCHNT_TP', updForm.getForm()
										.findField('tblRouteRuleUpd.mchtGroup').getValue(),
								function(ret) {
									routeMccStore.loadData(Ext.decode(ret));
								})
					});
				}*/

				if (select.data.mchtGroup != '*') {
					routeMccStore.removeAll();
					SelectOptionsDWR.getComboDataWithParameter(
							'ROUTE_MCHNT_TP', updForm.getForm()
									.findField('tblRouteRuleUpd.mchtGroup')
									.getValue(), function(ret) {
								routeMccStore.loadData(Ext
										.decode(ret));
					})
					Ext.getCmp('mccUpdIdId').show();
					// detailForm.getForm().findField('mccDetailId').show();
				} else {
					Ext.getCmp('mccUpdIdId').hide();
					// detailForm.getForm().findField('mccDetailId').hide();
				}

				if (select.data.dateCtl != '*') {
					updForm.getForm().findField('dateBeg')
							.setValue(formatDt(select.data.dateBeg));
					updForm.getForm().findField('dateEnd')
							.setValue(formatDt(select.data.dateEnd));
					Ext.getCmp('dateBegUpdId').show();
					Ext.getCmp('dateEndUpdId').show();
				} else {
					updForm.getForm().findField('dateBeg').setValue('');
					updForm.getForm().findField('dateEnd').setValue('');
					Ext.getCmp('dateBegUpdId').hide();
					Ext.getCmp('dateEndUpdId').hide();
				}

				if (select.data.timeCtl != '*') {
					updForm.getForm().findField('tblRouteRuleUpd.timeBeg')
							.setValue(formatDt(select.data.timeBeg.substring(0,
									4)));
					updForm.getForm().findField('tblRouteRuleUpd.timeEnd')
							.setValue(formatDt(select.data.timeEnd.substring(0,
									4)));
					Ext.getCmp('timeBegUpdId').show();
					Ext.getCmp('timeEndUpdId').show();
				} else {
					updForm.getForm().findField('tblRouteRuleUpd.timeBeg')
							.setValue('');
					updForm.getForm().findField('tblRouteRuleUpd.timeEnd')
							.setValue('');
					Ext.getCmp('timeBegUpdId').hide();
					Ext.getCmp('timeEndUpdId').hide();
				}

				if (select.data.amtCtl != '*') {
					updForm.getForm().findField('tblRouteRuleUpd.amtBeg')
							.setValue((+select.data.amtBeg.toString())/100);
					updForm.getForm().findField('tblRouteRuleUpd.amtEnd')
							.setValue((+select.data.amtEnd.toString())/100);
					Ext.getCmp('amtBegUpdId').show();
					Ext.getCmp('amtEndUpdId').show();
				} else {
					Ext.getCmp('amtBegUpdId').hide();
					Ext.getCmp('amtEndUpdId').hide();
				}
				
				

			}
		}
		/*
		 * ,{ text: '关闭', handler: function() { paramWin.hide(grid); } }
		 */
		]
	});
	var mainView = new Ext.Viewport({
				layout : 'border',
				items : [grid],
				renderTo : Ext.getBody()
			});
});