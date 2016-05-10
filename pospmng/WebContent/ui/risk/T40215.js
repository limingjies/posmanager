Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24 * 60 * 60 * 1000);

	// 获取当前日期的前一天
	var timeYesterday = preDate.format("Y") + "-" + preDate.format("m")
			+ "-" + (preDate.format("d"));// 昨天

	// 商户资金冻结
	var mchtFreezeStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchtFreeze'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'mchtId',
			mapping : 'mchtId'
		}, {
			name : 'freezeAmt',
			mapping : 'freezeAmt'
		}, {
			name : 'doFreezeAmt',
			mapping : 'doFreezeAmt'
		}, {
			name : 'batchNo',
			mapping : 'batchNo'
		}, {
			name : 'freezeDate',
			mapping : 'freezeDate'
		}, {
			name : 'freezeFlag',
			mapping : 'freezeFlag'
		}, {
			name : 'unFreezeFlag',
			mapping : 'unFreezeFlag'
		}, {
			name : 'unFreezeDate',
			mapping : 'unFreezeDate'
		}, {
			name : 'instDate',
			mapping : 'instDate'
		}, {
			name : 'updateDt',
			mapping : 'updateDt'
		} ]),
		autoLoad : true
	});

	// 商户资金冻结明细
	var mchtFreezeDtlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchtFreezeDtl'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'mchtId',
			mapping : 'mchtId'
		}, {
			name : 'batchNo',
			mapping : 'batchNo'
		}, {
			name : 'dateStlm',
			mapping : 'dateStlm'
		}, {
			name : 'freezeFlag',
			mapping : 'freezeFlag'
		}, {
			name : 'channleId',
			mapping : 'channleId'
		}, {
			name : 'freezeAmt',
			mapping : 'freezeAmt'
		}, {
			name : 'instDate',
			mapping : 'instDate'
		} ])
	});

	var riskColModel = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : '商户号',
		dataIndex : 'mchtId',
		width : 250,
		align : 'left'
	}, {
		header : '冻结金额',
		dataIndex : 'freezeAmt',
		align : 'right',
		width : 90
	}, {
		header : '已冻结金额',
		dataIndex : 'doFreezeAmt',
		align : 'right',
		width : 90
	}, {
		header : '冻结批次号',
		dataIndex : 'batchNo',
		width : 150
	}, {
		header : '冻结日期',
		dataIndex : 'freezeDate',
		renderer : formatDt,
		width : 100
	}, {
		header : '冻结标志',
		dataIndex : 'freezeFlag',
		width : 100,
		renderer : freezekFlag
	}, {
		header : '解冻标志',
		dataIndex : 'unFreezeFlag',
		width : 100,
		renderer : unFreezekFlag
	}, {
		header : '解冻日期',
		dataIndex : 'unFreezeDate',
		width : 100,
		renderer : formatDt
	}, {
		header : '插入时间',
		dataIndex : 'instDate',
		renderer : formatDt,
		width : 180
	}, {
		header : '更新时间',
		dataIndex : 'updateDt',
		renderer : formatDt,
		width : 180
	} ]);
	var riskParmColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),/*
										 * { header : '商户号', dataIndex :
										 * 'mchtId', width : 250, align : 'left' }, {
										 * header : '冻结批次号', dataIndex :
										 * 'batchNo', width : 150 },
										 */{
				header : '结算日期',
				dataIndex : 'dateStlm',
				width : 100
			}, {
				header : '冻结标志',
				dataIndex : 'freezeFlag',
				width : 100,
				renderer : FreezekFlagDtl
			}, {
				header : '冻结金额',
				dataIndex : 'freezeAmt',
				align : 'left',
				width : 90
			}, {
				header : '渠道号',
				dataIndex : 'channleId',
				width : 90
			} /*
				 * , { header : '插入时间', dataIndex : 'instDate', width : 180 }
				 */

	]);

	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,

		labelWidth : 100,

		layout : 'column',

		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [ {
			columnWidth : .99,
			layout : 'form',
			items : [ {
				xtype : 'dynamicCombo',
				fieldLabel : '商户编号',
				methodName : 'getMchntNoAll',
				hiddenName : 'mchtNo',
				width : 250,
				editable : true,
				allowBlank: false,
				id : 'mchtIdId',
				mode : 'local',
				triggerAction : 'all',
				forceSelection : true,
				selectOnFocus : true,
				lazyRender : true
			} ]
		}, {
			columnWidth : .99,
			layout : 'form',
			items : [ {
				xtype : 'numberfield',
				fieldLabel : '冻结金额',
				allowBlank: false,
				name : 'freezeAmt',
				id : 'freezeAmtId',
				// vtype : 'isNumber',
				minValue : 0,
				width : 250
			} ]
		} ]
	});

	var addWin = new Ext.Window({
		title : '商户资金冻结',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
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
						url : 'T40215Action.asp?method=add',
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

							txnId : '40215',
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

		]
	});

	var menuArr = new Array();

	var add = {
		text : '商户资金冻结',
		width : 85,
		iconCls : 'add',
		// disabled : true,
		handler : function() {
			addWin.show();
			addWin.center();
			var select = grid.getSelectionModel().getSelected();
			// addForm.getForm().findField('saModelKindadd').setValue(
			// select.data.saModelKind);
			// addForm.getForm().findField('misc').setValue(
			// select.data.misc);
			addForm.getForm().reset();
		}
	};
	var update = {
		xtype : 'button',
		text : '解冻交易',
		id : 'recoverTxn',
		width : 80,
		iconCls : 'unlock',// recover
		disabled : true,
		handler : function() {
			showConfirm('确定要对此交易进行解冻吗？', grid, function(bt) {
				// 如果点击了提示的确定按钮
				if (bt == "yes") {
					Ext.Ajax.request({
						url : 'T40215Action.asp?method=recoverFreeze',
						waitMsg : '正在提交，请稍后......',
						params : {
							batchNo : grid.getSelectionModel().getSelected()
									.get('batchNo'),
							txnId : '40215',
							subTxnId : '02'
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
			})
		}
	};
	var searchBtb = {

		xtype : 'button',
		text : '查询',
		name : 'query',
		id : 'query',
		iconCls : 'query',
		width : 80,
		handler : function() {
			grid.getStore().load();
		}
	};
	var resetBtn = {
		xtype : 'button',
		text : '重置',
		name : 'reset',
		id : 'reset',
		iconCls : 'reset',
		width : 80,
		handler : function() {
			Ext.getCmp('startDate').reset();

			Ext.getCmp('endDate').reset();

			Ext.getCmp('idQueryMchtNo').setValue('');

			Ext.getCmp('queryFreezeFlagId').setValue('');

			Ext.getCmp('queryUnFreezeFlagId').setValue('');
			Ext.getCmp('queryBatchId').setValue('');
			grid.getStore().load();
		}

	};
	menuArr.push(add);
	menuArr.push('-');
	menuArr.push(update);
	menuArr.push('-');
	menuArr.push(searchBtb);
	menuArr.push('-');
	menuArr.push(resetBtn);
	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '商户号：', {
			xtype : 'dynamicCombo',
			methodName : 'getMchntNoAll',
			hiddenName : 'queryMchtNo',
			width : 240,
			editable : true,
			id : 'idQueryMchtNo',
			mode : 'local',
			triggerAction : 'all',
			forceSelection : true,
			selectOnFocus : true,
			lazyRender : true
		},

		'-', '冻结状态：', {
			xtype : 'combo',
			store : new Ext.data.ArrayStore({
				fields : [ 'valueField', 'displayField' ],
				data : [ [ '0', '待冻结' ], [ '1', '部分冻结' ] , [ '2', '完全冻结' ]],
				reader : new Ext.data.ArrayReader()
			}),
			displayField : 'displayField',
			valueField : 'valueField',
			hiddenName : 'queryFreezeFlag',
			editable : false,
			emptyText : '请选择',
			id : 'queryFreezeFlagId',
			width : 140
		},

		'-', '解冻状态：', {
			xtype : 'combo',
			store : new Ext.data.ArrayStore({
				fields : [ 'valueField', 'displayField' ],
				data : [ [ '0', '待解冻' ], [ '1', '不解冻' ], [ '1', '解冻已结算' ] ],
				reader : new Ext.data.ArrayReader()
			}),
			displayField : 'displayField',
			valueField : 'valueField',
			hiddenName : 'queryUnFreezeFlag',
			editable : false,
			emptyText : '请选择',
			id : 'queryUnFreezeFlagId',
			width : 140
		} ]
	});
	var tbar2 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '冻结起始日期：', {
			xtype : 'datefield',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			// vtype: 'daterange',
			// endDateField: 'endDate',
			value : timeYesterday,
			editable : false,
			id : 'startDate',
			name : 'startDate',
			width : 110
		}, '-', '冻结结束日期：', {
			xtype : 'datefield',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			// vtype: 'daterange',
			// startDateField: 'startDate',
			value : timeYesterday,
			editable : false,
			id : 'endDate',
			name : 'endDate',
			width : 110
		}, '-', '批次号：       ', {
			xtype : 'textfield',
			name : 'queryBatch',
			id : 'queryBatchId',
			vtype : 'isNumber',
			width : 150
		} ]
	});
	var detailGrid = new Ext.grid.EditorGridPanel({
		title : '商户资金冻结明细',
		region : 'east',
		width : 410,
		split : true,
		collapsible : true,
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		store : mchtFreezeDtlStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : riskParmColModel,
		forceValidation : true,
		loadMask : {
			msg : '正在加载风控模型参数......'
		},
		bbar : new Ext.PagingToolbar({
			store : mchtFreezeDtlStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	var grid = new Ext.grid.GridPanel({
		title : '商户资金冻结',
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		region : 'center',
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		store : mchtFreezeStore,
		cm : riskColModel,
		forceValidation : true,
		renderTo : Ext.getBody(),
		loadMask : {
			msg : '正在加载商户资金冻结明细......'
		},
		tbar : menuArr,
		listeners : {
			'render' : function() {
				tbar1.render(this.tbar);
				tbar2.render(this.tbar);
			}
		},
		bbar : new Ext.PagingToolbar({
			store : mchtFreezeStore,
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
							grid.getView()
									.getRow(grid.getSelectionModel().last))
							.frame();
					detailGrid.getStore().removeAll();
					var rec = grid.getSelectionModel().getSelected();

					detailGrid.getStore().on('beforeload', function() {

						Ext.apply(this.baseParams, {
							start : 0,
							batchNo : rec.get('batchNo')
						});
					});
					if ('1' == rec.get('unFreezeFlag')) {

						Ext.getCmp('recoverTxn').enable();
					} else {

						Ext.getCmp('recoverTxn').disable();
					}

					detailGrid.getStore().load();
				}
			});
	mchtFreezeStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
			start : 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
					endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryBatch:Ext.getCmp('queryBatchId').getValue(),
			queryUnFreezeFlag:Ext.getCmp('queryUnFreezeFlagId').getValue(),
			queryFreezeFlag:Ext.getCmp('queryFreezeFlagId').getValue(),
			queryMchtNo:Ext.getCmp('idQueryMchtNo').getValue()
		});
		detailGrid.getStore().removeAll();
		Ext.getCmp('recoverTxn').disable();
	});
	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ grid, detailGrid ],
		renderTo : Ext.getBody()
	});
});