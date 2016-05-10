Ext.onReady(function() {

	// 数据集
	var mchtStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
		mchtStore.loadData(Ext.decode(ret));
	});

	var txnGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchtRiskAnalyticsSummary'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'mchtNo',
			mapping : 'mchtNo'
		}, {
			name : 'mchtNm',
			mapping : 'mchtNm'
		}, {
			name : 'riskLvl',
			mapping : 'riskLvl'
		}, {
			name : 'txnAmt',
			mapping : 'txnAmt'
		}, {
			name : 'txnCount',
			mapping : 'txnCount'
		}, {
			name : 'firstLevelCount',
			mapping : 'firstLevelCount'
		}, {
			name : 'secondLevelCount',
			mapping : 'secondLevelCount'
		}, {
			name : 'thirdLevelCount',
			mapping : 'thirdLevelCount'
		}, {
			name : 'allLevelsCount',
			mapping : 'allLevelsCount'
		}, {
			name : 'cheatAmt',
			mapping : 'cheatAmt'
		}, {
			name : 'cheatCount',
			mapping : 'cheatCount'
		}, {
			name : 'cheatRatioOfAmount',
			mapping : 'cheatRatioOfAmount'
		}, {
			name : 'cheatRatioOfCount',
			mapping : 'cheatRatioOfCount'
		} ]),
		autoLoad : true
	});

	var txnColModel = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : '商户编号',
		dataIndex : 'mchtNo',
		align : 'left',
		width : 200
	}, {
		header : '商户名称',
		dataIndex : 'mchtNm',
		align : 'center',
		width : 150
	}, {
		header : '风控级别',
		dataIndex : 'riskLvl',
		align : 'center',
		width : 80
	}, {
		header : '交易总金额',
		dataIndex : 'txnAmt',
		align : 'right',
		width : 120
	}, {
		header : '交易总笔数',
		dataIndex : 'txnCount',
		align : 'center',
		width : 100
	}, {
		header : '触发一级警报次数',
		dataIndex : 'firstLevelCount',
		align : 'center',
		width : 100
	}, {
		header : '触发二级警报次数',
		dataIndex : 'secondLevelCount',
		align : 'center',
		width : 100
	}, {
		header : '触发三级警报次数',
		dataIndex : 'thirdLevelCount',
		align : 'center',
		width : 100
	}, {
		header : '触发风控规则总次数',
		dataIndex : 'allLevelsCount',
		align : 'center',
		width : 100
	}, {
		header : '欺诈金额',
		dataIndex : 'cheatAmt',
		align : 'right',
		width : 100
	}, {
		header : '欺诈笔数',
		dataIndex : 'cheatCount',
		align : 'center',
		width : 80
	}, {
		header : '欺诈率（金额）',
		dataIndex : 'cheatRatioOfAmount',
		align : 'center',
		width : 100
	}, {
		header : '欺诈率（笔数）',
		dataIndex : 'cheatRatioOfCount',
		align : 'center',
		width : 100
	} ]);

	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '起止日期：', {
			xtype : 'datefield',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			vtype : 'daterange',
			endDateField : 'endDate',
			editable : false,
			id : 'startDate',
			name : 'startDate',
			width : 120
		}, '—', {
			xtype : 'datefield',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			vtype : 'daterange',
			startDateField : 'startDate',
			editable : false,
			id : 'endDate',
			name : 'endDate',
			width : 120
		}, '-', '商户编号：', {
			xtype : 'combo',
			store : mchtStore,
			hiddenName : 'queryMchtNoNm',
			width : 285,
			editable : true,
			id : 'idqueryMchtNoNm',
			mode : 'local',
			triggerAction : 'all',
			forceSelection : true,
			selectOnFocus : true,
			lazyRender : true,
			listeners : {
				'beforequery' : function(e) {
					var combo = e.combo;
					if (!e.forceAll) {
						var input = e.query;
						// 检索的正则
						var regExp = new RegExp(".*" + input + ".*");
						// 执行检索
						combo.store.filterBy(function(record, id) {
							// 得到每个record的项目名称值
							var text = record.get(combo.displayField);
							return regExp.test(text);
						});
						combo.expand();
						return false;
					}
				}
			}
		}, '-', '风控级别：', {
			xtype : 'basecomboselect',
			baseParams : 'RISK_LVL',
			hiddenName : 'queryRiskLvl',
			width : 150,
			id : 'idqueryRiskLvl',
			mode : 'local',
			triggerAction : 'all',
			forceSelection : true,
			editable : true,
			lazyRender : true
		} ]
	});

	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls : 'T104',
		region : 'center',
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
			msg : '正在加载交易信息列表......'
		},
		tbar : [ {
			xtype : 'button',
			text : '下载报表',
			name : 'download',
			id : 'download',
			iconCls : 'download',
			width : 80,
			handler : function() {
				showMask("正在为您准备报表，请稍后。。。", txnGrid);
				if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]) {
					Ext.Ajax.request({
						url : 'T40501Action.asp',
						timeout : 60000,
						params : {
							startDate : typeof (Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
							endDate : typeof (Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
							queryMchtNoNm : Ext.get('queryMchtNoNm').getValue(),
							queryRiskLvl : Ext.get('queryRiskLvl').getValue(),
							txnId : '40501',
							subTxnId : '01'
						},
						success : function(rsp, opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg, txnGrid);
							}
						},
						failure : function() {
							hideMask();
							showErrorMsg(rspObj.msg, txnGrid);
						}
					});
				} else {
					hideMask();
					Ext.MessageBox.show({
						msg : '数据量超过限定值,请输入限制条件再进行此操作!!!',
						title : '报表下载说明',
						buttons : Ext.MessageBox.OK,
						modal : true,
						width : 220
					});
				}
			}
		}, '-', {
			xtype : 'button',
			text : '查询',
			name : 'query',
			id : 'query',
			iconCls : 'query',
			width : 80,
			handler : function() {
				txnGridStore.load();
			}
		}, '-', {
			xtype : 'button',
			text : '重置',
			name : 'reset',
			id : 'reset',
			iconCls : 'reset',
			width : 80,
			handler : function() {
				Ext.getCmp('idqueryMchtNoNm').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				Ext.getCmp('idqueryRiskLvl').setValue('');
				mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
			}
		} ],
		listeners : {
			'render' : function() {
				tbar1.render(this.tbar);
			},
			'cellclick':selectableCell
		},
		bbar : new Ext.PagingToolbar({
			store : txnGridStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	txnGrid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
		}
	});

	txnGridStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
			start : 0,
			startDate : typeof (Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate : typeof (Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryMchtNoNm : Ext.get('queryMchtNoNm').getValue(),
			queryRiskLvl : Ext.get('queryRiskLvl').getValue()
		});
	});

	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ txnGrid ],
		renderTo : Ext.getBody()
	});
});