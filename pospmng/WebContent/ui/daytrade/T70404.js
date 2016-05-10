Ext
// 消费调账
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
				align : 'right'
			}, {
				header : '费用最小值',
				dataIndex : 'minFee',
				width : 100,
				align : 'right'
			}, {
				header : '费用最大值',
				dataIndex : 'maxFee',
				width : 100,
				align : 'right'
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

	var grid = new Ext.grid.GridPanel({
		title : '消费调账',
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
			msg : '正在加载消费调账列表......'
		},
		tbar : [ {
			xtype : 'button',
			text : '查询',
			name : 'query',
			id : 'query',
			iconCls : 'query',
			width : 80,
			handler : function() {
				withDrawFeeStore.load();
			}
		}, '-', {
			xtype : 'button',
			text : '重置',
			name : 'reset',
			id : 'reset',
			iconCls : 'reset',
			width : 80,
			handler : function() {
				Ext.getCmp('idqueryCardAccpId').setValue('');
				withDrawFeeStore.load();
			}
		}, '-', {
			xtype : 'button',
			text : '新增调账',
			name : 'add',
			id : 'add',
			iconCls : 'add',
			width : 80,
			handler : function() {
				addWin.show();
				addWin.center();
			}
		}

		],
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

	grid.getSelectionModel()
			.on(
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
						xtype : 'datefield',
						id : 'webTimeId',
						format : 'Y-m-d',
						editable : false,
						width : 250,
						name : 'webTime',
						allowBlank : false,
						fieldLabel : '业务管理平台时间戳',

						blankText : '请选择日期'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '业务管理平台流水号',
						allowBlank : false,
						maxLength : 12,
						width : 250,
						id : 'webSeqNumId',
						name : 'webSeqNum'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'datefield',
						format : 'Y-m-d',
						editable : false,
						allowBlank : false,
						width : 250,
						fieldLabel : '原收单交易时间戳',
						width : 250,
						id : 'pospInstDateId',
						name : 'pospInstDate'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '原收单交易流水号',
						allowBlank : false,
						maxLength : 12,
						width : 250,
						id : 'pospSysSeqNumId',
						name : 'pospSysSeqNum'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'dynamicCombo',
						fieldLabel : '商户编号',
						methodName : 'getMchntIdAll',
						hiddenName : 'merchantId',
						width : 250,
						editable : true,
						id : 'idqueryAcctMchtId',
						mode : 'local',
						triggerAction : 'all',
						forceSelection : true,
						allowBlank : false,
						selectOnFocus : true,
						lazyRender : true
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '交易手续费',
						allowBlank : false,
						regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
						regexText : '费用最小值精确到分，如123.45',
						maxLength : 12,
						width : 250,
						id : 'transFeeId',
						name : 'transFee'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'numberfield',
						fieldLabel : '交易金额',
						allowBlank : false,
						maxLength : 12,
						width : 250,
						id : 'transAmountId',
						name : 'transAmount'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'numberfield',
						fieldLabel : '结算金额',
						allowBlank : false,
						maxLength : 12,
						width : 250,
						id : 'settleAmountId',
						name : 'settleAmount'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [ {
						xtype : 'textfield',
						fieldLabel : '卡号',
						regex : /^(([1-9][0-9]*|0)(\.[0-9]{1,2})?)$/,
						regexText : '费用最小值精确到分，如123.45',
						maxLength : 12,
						width : 250,
						allowBlank : false,
						id : 'cardNoId',
						name : 'cardNo'
					} ]
				},
				{
					columnWidth : .99,
					layout : 'form',
					items : [
							{
								xtype : 'textfield',
								fieldLabel : '卡类型',
								allowBlank : false,
								maxLength : 12,
								width : 250,
								id : 'cardTypeId',
								name : 'cardType'
							},
							{
								columnWidth : .99,
								layout : 'form',
								items : [ {
									xtype : 'combo',
									fieldLabel : '调账类型',
									name : 'combo',
									id : 'frontCodeId',
									width : 250,
									emptyText : '请选择',
									allowBlank : false,
									mode : 'local',
									store : new Ext.data.SimpleStore({
										fields : [ 'value', 'text' ],
										data : [ [ '033001', '冲账' ],
												[ '033011', '补账' ],
												[ '033007', '消费冻结' ],
												[ '033008', '消费解冻结' ] ]
									}),
									editable : false,
									triggerAction : 'all',
									valueField : 'value',
									displayField : 'text'
								} ]
							} ]
				} ]
	});

	var addWin = new Ext.Window({
		title : '新增消费调账',
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
		buttons : [
				{
					text : '确定',
					handler : function() {
						if (addForm.getForm().isValid()) {
							items = new Array();

							var item = {
								webTime : Ext.util.Format.date(addForm
										.getForm().findField('webTimeId')
										.getValue(), 'YmdHis'),
								webSeqNum : addForm.getForm().findField(
										'webSeqNum').getValue(),
								pospInstDate : Ext.util.Format.date(addForm
										.getForm().findField('pospInstDate')
										.getValue(), 'YmdHis'),
								pospSysSeqNum : addForm.getForm().findField(
										'pospSysSeqNum').getValue(),
								transFee : addForm.getForm().findField(
										'transFee').getValue(),
								transAmount : addForm.getForm().findField(
										'transAmount').getValue(),
								settleAmount : addForm.getForm().findField(
										'settleAmount').getValue(),
								cardNo : addForm.getForm().findField('cardNo')
										.getValue(),
								cardType : addForm.getForm().findField(
										'cardType').getValue(),
								merchantId : addForm.getForm().findField(
										'idqueryAcctMchtId').getValue()
							};
							items.push(item);
							FrontT70404.doTxn(addForm.getForm().findField(
									'frontCodeId').getValue(), Ext
									.encode(items), showWdMsg);
							addWin.show();
							addWin.getEl().mask('正在处理提现信息......');
							addWin.center();
							/*
							 * addForm.getForm().submit({ url :
							 * 'T70404Action.asp?method=add', waitMsg :
							 * '正在提交，请稍后......', success : function(form,
							 * action) { showSuccessMsg(action.result.msg,
							 * addForm); // 重置表单 addForm.getForm().reset(); //
							 * 重新加载参数列表 grid.getStore().reload(); addWin.hide(); },
							 * failure : function(form, action) {
							 * showErrorMsg(action.result.msg, addForm); },
							 * params : { txnId : '70404', subTxnId : '01' } });
							 */
						}
					}
				}, {
					text : '重置',
					handler : function() {
						addForm.getForm().reset();
					}
				} ]
	});
	function showWdMsg(retMsg) {
		var text;
		if(retMsg.success){
			text='操作成功！';
			addWin.hidden();
		}else{
			
			text='操作失败！';	
		}
		Ext.Msg.alert('提示信息', text);
	}
	;

	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ grid ],
		renderTo : Ext.getBody()
	});
});