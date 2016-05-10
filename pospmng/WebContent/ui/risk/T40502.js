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
			url : 'gridPanelStoreAction.asp?storeId=mchtRiskAnalyticsDetail'
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
			name : 'rule',
			mapping : 'rule'
		}, {
			name : 'isCheat',
			mapping : 'isCheat'
		}, {
			name : 'pan',
			mapping : 'pan'
		}, {
			name : 'txnAmt',
			mapping : 'txnAmt'
		}, {
			name : 'txnDate',
			mapping : 'txnDate'
		}, {
			name : 'txnTime',
			mapping : 'txnTime'
		}, {
			name : 'txnType',
			mapping : 'txnType'
		}, {
			name : 'txnState',
			mapping : 'txnState'
		}, {
			name : 'terminal',
			mapping : 'terminal'
		} ]),
		autoLoad : true
	});

	var txnColModel = new Ext.grid.ColumnModel([ new Ext.grid.RowNumberer(), {
		header : '商户编号',
		dataIndex : 'mchtNo',
		align : 'left',
		width : 150
	}, {
		header : '商户名称',
		dataIndex : 'mchtNm',
		align : 'left',
		width : 200
	}, {
		header : '风控级别',
		dataIndex : 'riskLvl',
		align : 'center',
		width : 80
	}, {
		header : '触发规则',
		dataIndex : 'rule',
		renderer : riskIdName,
		align : 'left',
		width : 100
	}, {
		header : '是否欺诈',
		dataIndex : 'isCheat',
		renderer : isCheat,
		align : 'center',
		width : 80
	}, {
		header : '银行卡卡号',
		dataIndex : 'pan',
		align : 'left',
		width : 140
	}, {
		header : '交易金额',
		dataIndex : 'txnAmt',
		align : 'right',
		width : 80
	}, {
		header : '交易日期',
		dataIndex : 'txnDate',
		renderer : formatDt,
		align : 'center',
		width : 80
	}, {
		header : '交易时间',
		dataIndex : 'txnTime',
		renderer : formatDt,
		align : 'center',
		width : 80
	}, {
		header : '交易类型',
		dataIndex : 'txnType',
		align : 'center',
		width : 100
	}, {
		header : '交易结果',
		dataIndex : 'txnState',
		renderer : txnSta,
		align : 'center',
		width : 100
	}, {
		header : '终端号',
		dataIndex : 'terminal',
		align : 'left',
		width : 120
	} ]);

	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '商户编号：', {
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
			editable : true,
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
		}, '-', '入网时间：', {
			xtype : 'datefield',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			vtype : 'daterange',
			editable : false,
			id : 'joinDate',
			name : 'joinDate',
			width : 120
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

	var tbar2 = new Ext.Toolbar({
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
		}, '-', '风控规则：', {
			xtype : 'basecomboselect',
			id : 'idqueryRule',
			baseParams : 'KIND',
			hiddenName : 'queryRule',
			editable : true,
			width : 140
		}, '-', '交易卡号：', {
			xtype : 'textfield',
			id : 'idqueryPan',
			vtype : 'isNumber',
			name : 'queryPan',
			width : 120
		}, '-', '是否欺诈：', {
			xtype : 'combo',
			store : new Ext.data.ArrayStore({
				fields : [ 'valueField', 'displayField' ],
				data : [ [ '1', '是' ], [ '0', '否' ]],
				reader : new Ext.data.ArrayReader()
			}),
			displayField : 'displayField',
			valueField : 'valueField',
			id : 'idqueryIsCheat',
			hiddenName : 'queryIsCheat',
			mode : 'local',
			editable : true,
			width : 80
		} ]
	})

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
						url : 'T40502Action.asp',
						timeout : 60000,
						params : {
							startDate : typeof (Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
							endDate : typeof (Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
							joinDate : typeof (Ext.getCmp('joinDate').getValue()) == 'string' ? '' : Ext.getCmp('joinDate').getValue().format('Ymd'),
							queryMchtNoNm : Ext.get('queryMchtNoNm').getValue(),
							queryRiskLvl : Ext.get('queryRiskLvl').getValue(),
							queryPan : Ext.getCmp('idqueryPan').getValue(),
							queryRule : Ext.getCmp('idqueryRule').getValue(),
							queryIsCheat : Ext.getCmp('idqueryIsCheat').getValue(),
							txnId : '40502',
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
				Ext.getCmp('joinDate').setValue('');
				Ext.getCmp('idqueryRiskLvl').setValue('');
				Ext.getCmp('idqueryIsCheat').setValue('');
				Ext.getCmp('idqueryPan').setValue('');
				Ext.getCmp('idqueryRule').setValue('');
				mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
			}
		} ],
		listeners : {
			'render' : function() {
				tbar2.render(this.tbar);
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
			joinDate : typeof (Ext.getCmp('joinDate').getValue()) == 'string' ? '' : Ext.getCmp('joinDate').getValue().format('Ymd'),
			queryMchtNoNm : Ext.get('queryMchtNoNm').getValue(),
			queryRiskLvl : Ext.get('queryRiskLvl').getValue(),
			queryPan : Ext.getCmp('idqueryPan').getValue(),
			queryRule : Ext.getCmp('idqueryRule').getValue(),
			queryIsCheat : Ext.getCmp('idqueryIsCheat').getValue()
		});
	});

	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ txnGrid ],
		renderTo : Ext.getBody()
	});
});

function isCheat(val) {
	switch (val) {
	case '1':
		return '是';
	case '0':
		return '否';
	default:
		return '未知';
	}
}