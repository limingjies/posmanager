Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() + 30 * 2 * 24 * 60 * 60 * 1000);
	var twoMonday = preDate.format("Y") + "-" + preDate.format("m") + "-"
			+ (preDate.format("d"));
	var mchtNoStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_TMP_BELOW', function(ret) {
		mchtNoStore.loadData(Ext.decode(ret));
	});

	// 商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchtLimitDateInfo'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'mchtNo',
			mapping : 'mchtNo'
		}, {
			name : 'limitDate',
			mapping : 'limitDate'
		}, {
			name : 'status',
			mapping : 'status'
		}, {
			name : 'reserved',
			mapping : 'reserved'
		}, {
			name : 'crtOpr',
			mapping : 'crtOpr'
		}, {
			name : 'crtTime',
			mapping : 'crtTime'
		}, {
			name : 'updOpr',
			mapping : 'updOpr'
		}, {
			name : 'updTime',
			mapping : 'updTime'
		}, {
			name : 'ceilDate',
			mapping : 'ceilDate'
		}, {
			name : 'mchtName',
			mapping : 'mchtName'
		}, {
			name : 'misc',
			mapping : 'misc'
		} ])
	});
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl : new Ext.Template('<p>备注：<font>{reserved}</font></p>', {
		// getmcc : function(val){return getRemoteTrans(val, "mcc");}
		})
	});

	var mchntColModel = new Ext.grid.ColumnModel([
			mchntRowExpander,
			new Ext.grid.RowNumberer(),

			{
				header : '商户编号',
				dataIndex : 'mchtName',
				width : 250,
				id : 'mchtNm',
				align : 'left'
			},
			{
				header : '到期日期',
				dataIndex : 'limitDate',
				renderer : formatDt,
				width : 100,
				align : 'center'
			},
			{
				header : '到期剩余天数',
				dataIndex : 'ceilDate',
				width : 100,
				align : 'center'
			},
			{
				header : '漏补状态',
				dataIndex : 'status',
				width : 80,
				align : 'center',
				renderer : function(value, e, rec) {

					if (value == '1') {
						if (rec.data.ceilDate <= 0) {
							return '<font color="red">待补充</font>';
						} else if (rec.data.ceilDate <= 3
								&& rec.data.ceilDate > 0) {

							return '<font color="#FFC125">待补充</font>';

						}

						return '待补充';

					} else if (value == '0') {

						return '已补充';

					}

				}

			}, {
				header : '创建人',
				dataIndex : 'crtOpr',
				width : 90,
				id : 'mchtGroupFlag',
				align : 'center'
			}, {
				header : '创建日期',
				dataIndex : 'crtTime',
				width : 150,
				renderer : formatDt,
				align : 'center'
			}, {
				header : '修改人',
				dataIndex : 'updOpr',
				width : 90,
				align : 'center'
			}, {
				header : '修改日期',
				dataIndex : 'updTime',
				width : 150,
				renderer : formatDt,
				align : 'center'
			}, {
				header : '延期次数',
				dataIndex : 'misc',
				width : 90,
				align : 'center'
			}

	]);

	// 菜单集合
	var menuArr = new Array();

	var stopMenu = {
		text : '冻结',
		width : 85,
		iconCls : 'stop',
		disabled : true,
		handler : function() {
			showConfirm('确认冻结吗？', mchntGrid, function(bt) {
				if (bt == 'yes') {
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url : 'T20202Action.asp?method=stop',
						params : {
							mchtNo : rec.get('mchtNo'),
							txnId: '20202',
							subTxnId: '01'					
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, mchntGrid);
							} else {
								showErrorMsg(rspObj.msg, mchntGrid);
							}
							mchntGrid.getStore().reload();
						}
					});
				}
			});
		}
	};

	var completeMenu = {
		text : '已补充',
		width : 85,
		iconCls : 'accept',
		disabled : true,
		handler : function() {
			showConfirm('确认已补充吗？', mchntGrid, function(bt) {
				if (bt == 'yes') {
					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url : 'T20202Action.asp?method=complete',
						params : {
							mchtNo : rec.get('mchtNo'),
							txnId: '20202',
							subTxnId: '02'
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								showSuccessMsg(rspObj.msg, mchntGrid);
							} else {
								showErrorMsg(rspObj.msg, mchntGrid);
							}
							mchntGrid.getStore().reload();
						}
					});
				}
			});
		}
	};
	var limitDateMenu = {
		text : '延期',
		width : 85,
		iconCls : 'edit',
		disabled : true,
		handler : function() {
			queryWin.show();
		}
	};

	var queryCondition = {
		xtype : 'button',
		text : '查询',
		name : 'query',
		id : 'query',
		iconCls : 'query',
		width : 80,
		handler : function() {
			mchntStore.load();
		}
	};

	var reSet = {
		xtype : 'button',
		text : '重置',
		name : 'reset',
		id : 'reset',
		iconCls : 'reset',
		width : 80,
		handler : function() {
			Ext.getCmp('queryMchtNo').setValue('');
			Ext.getCmp('queryEndDate').setValue('');
			Ext.getCmp('queryStartDate').setValue('');
		}
	};
	
	var queryForm = new Ext.form.FormPanel({
		frame : true,
		border : true,
		width : 320,
		autoHeight : true,
		waitMsgTarget : true,
		defaults : {
			labelWidth : 30
		},
		items : [{
			xtype : 'datefield',
			editable : false,
			width : 150,
			name : 'limitDate',
			fieldLabel : '到期日期*',
			allowBlank : false,
			minValue:new Date(),
			maxValue : twoMonday,
			listeners : {
				'change' : function(th, newValue, oldValue) {
					if (null != newValue && newValue > preDate) {
						Ext.Msg.alert('系统提示', '到期日期最大设置为两个月以内');
						th.setValue('');
						return;
					}
				}
			}
		}],
		buttonAlign : 'center',
		buttons : [ {
			text : '保存',
			iconCls : 'download',
			handler : function() {
				if (!queryForm.getForm().isValid()) {
					return;
				}
				rec = mchntGrid.getSelectionModel().getSelected();
				queryForm.getForm().submit({
					url : 'T20202Action.asp?method=limit',
					timeout : 60000,
					waitMsg : '正在提交，请稍等......',
					params : {
						mchtNo : rec.get('mchtNo'),
						txnId: '20202',
						subTxnId: '03'
					},
					success: function(form, action) {
						queryWin.hide();
						mchntGrid.getStore().reload();
						showSuccessMsg(action.result.msg,mchntGrid);
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,mchntGrid);
					}
				});
			}
		},{
			text : '重置',
			handler : function() {
				queryForm.getForm().reset();
			}
		}]
	});
	var queryWin = new Ext.Window({
		title : '修改日期',
		layout : 'fit',
		width : 320,
		modal : true,
		autoHeight : true,
		items : [queryForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		resizable : false,
		closable : true

	});

	menuArr.push(stopMenu); // [0]
	menuArr.push('-'); // [1]
	menuArr.push(completeMenu); // [2]
	menuArr.push('-'); // [3]
	menuArr.push(limitDateMenu); // [4]
	menuArr.push('-'); // [5]
	menuArr.push(queryCondition); // [6]
	menuArr.push('-'); // [7]
	menuArr.push(reSet); // [8]

	var mchntGrid = new Ext.grid.GridPanel({
		title : '入网提醒查询',
		region : 'center',
		frame : true,
		border : true,
		columnLines : true,
		autoExpandColumn : 'mchtNm',
		stripeRows : true,
		store : mchntStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : mchntColModel,
		clicksToEdit : true,
		forceValidation : true,
		tbar : menuArr,
		listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
			'render' : function() {
				tbar1.render(this.tbar);
				// tbar2.render(this.tbar);
			},
			'cellclick':selectableCell
		},
		plugins : mchntRowExpander,
		loadMask : {
			msg : '正在加载入网信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : mchntStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	mchntStore.load({
		params : {
			start : 0
		}
	});

	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
	});

	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload', function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
	});

	var rec;

	mchntGrid.getSelectionModel().on(
			{
				'rowselect' : function() {
					// 行高亮
					Ext.get(
							mchntGrid.getView().getRow(
									mchntGrid.getSelectionModel().last))
							.frame();

					// 根据所选择的商户状态判断哪个按钮可用
					rec = mchntGrid.getSelectionModel().getSelected();

					mchntGrid.getTopToolbar().items.items[0].enable();
					mchntGrid.getTopToolbar().items.items[2].enable();
					mchntGrid.getTopToolbar().items.items[4].enable();
					if (rec.get('mchtStatus') == '0'
							|| rec.get('mchtStatus') == '2') {
						mchntGrid.getTopToolbar().items.items[0].disable();
						mchntGrid.getTopToolbar().items.items[2].disable();
					}
				}
			});

	/** *************************查询条件************************ */
	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '商户编号', {
			xtype : 'dynamicCombo',
			fieldLabel : '商户编号',
			methodName : 'getAllMchntId',
			id : 'queryMchtNo',
			editable : true,
			width : 300
		}, '-', '提醒起始日期:', {
			xtype : 'datefield',
			id : 'queryStartDate',
			name : 'queryStartDate',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			vtype : 'daterange',
			endDateField : 'queryEndDate',
			editable : false,
			width : 140
		}, '-', '提醒结束日期:', {
			xtype : 'datefield',
			id : 'queryEndDate',
			name : 'queryEndDate',
			format : 'Y-m-d',
			altFormats : 'Y-m-d',
			vtype : 'daterange',
			startDateField : 'queryStartDate',
			editable : false,
			width : 140
		} ]
	});

	/*
	 * var tbar2 = new Ext.Toolbar({ renderTo: Ext.grid.EditorGridPanel.tbar,
	 * items:[ ,'-','商户状态：', { xtype: 'combo', store: new Ext.data.ArrayStore({
	 * fields: ['valueField','displayField'], data:
	 * [['0','审核通过'],['1','申请待审核'],['2','审核退回']], reader: new
	 * Ext.data.ArrayReader() }), displayField: 'displayField', valueField:
	 * 'valueField', editable: false, emptyText: '请选择', hiddenName:
	 * 'queryMchtStatus', id:'queryMchtStatusId', width: 140 },'-', '签约机构：',{
	 * xtype: 'basecomboselect', id: 'queryBankNoId', emptyText: '请选择',
	 * baseParams: 'BRH_BELOW_BRANCH', hiddenName: 'queryBankNo', mode:'local',
	 * triggerAction: 'all', editable: true, lazyRender: true, width: 140 }
	 * ,'-','申请人:', { xtype: 'textfield', id: 'queryCrtOprId', name:
	 * 'queryCrtOprId', // vtype:'isNumber', vtype: 'alphanum', maxLength: 10,
	 * width: 140 } ] });
	 */

	/** *************************查询条件************************ */

	mchntStore.on('beforeload', function() {
		Ext
				.apply(this.baseParams,
						{
							start : 0,
							mchtNo : Ext.getCmp('queryMchtNo').getValue(),
							startDate : typeof (Ext.getCmp('queryStartDate')
									.getValue()) == 'string' ? '' : Ext.getCmp(
									'queryStartDate').getValue().dateFormat(
									'Ymd'),
							endDate : typeof (Ext.getCmp('queryEndDate')
									.getValue()) == 'string' ? '' : Ext.getCmp(
									'queryEndDate').getValue()
									.dateFormat('Ymd')

						});
	});

	/** *************************渠道商户信息更新************************ */
	// 添加T20901中的mchntForm
	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ mchntGrid ],
		renderTo : Ext.getBody()
	});

});