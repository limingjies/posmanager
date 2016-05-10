Ext.onReady(function() {
    var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
			// 数据集
			var brhStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data'
			});
			SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
				brhStore.loadData(Ext.decode(ret));
			});
			var withDrawFeeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=acctErr'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'key_inst',
					mapping : 'key_inst'
				}, {
					name : 'date_stlm',
					mapping : 'date_stlm'
				}, {
					name : 'acct_err_type',
					mapping : 'acct_err_type'
				}, {
					name : 'txn_num',
					mapping : 'txn_num'
				}, {
					name : 'acct_change_stat',
					mapping : 'acct_change_stat'
				}, {
					name : 'acct_change_resp',
					mapping : 'acct_change_resp'
				}, {
					name : 'acct_change_dt',
					mapping : 'acct_change_dt'
				}, {
					name : 'brh_id',
					mapping : 'brh_id'
				}, {
					name : 'card_accp_id',
					mapping : 'card_accp_id'
				}, {
					name : 'stlm_flag',
					mapping : 'stlm_flag'
				}, {
					name : 'txn_date',
					mapping : 'txn_date'
				}, {
					name : 'txn_time',
					mapping : 'txn_time'
				}, {
					name : 'sys_seq_num',
					mapping : 'sys_seq_num'
				}, {
					name : 'retrivl_ref',
					mapping : 'retrivl_ref'
				}, {
					name : 'inst_code',
					mapping : 'inst_code'
				}, {
					name : 'inst_mcht_id',
					mapping : 'inst_mcht_id'
				}, {
					name : 'inst_term_id',
					mapping : 'inst_term_id'
				}, {
					name : 'inst_retrivl_ref',
					mapping : 'inst_retrivl_ref'
				}, {
					name : 'card_accp_term_id',
					mapping : 'card_accp_term_id'
				}, {
					name : 'term_sn',
					mapping : 'term_sn'
				}, {
					name : 'pan',
					mapping : 'pan'
				}, {
					name : 'card_type',
					mapping : 'card_type'
				}, {
					name : 'amt_trans',
					mapping : 'amt_trans'
				}, {
					name : 'amt_trans_fee',
					mapping : 'amt_trans_fee'
				}, {
					name : 'amt_stlm',
					mapping : 'amt_stlm'
				}, {
					name : 'resp_code',
					mapping : 'resp_code'
				}, {
					name : 'inst_pan',
					mapping : 'inst_pan'
				}, {
					name : 'inst_amt',
					mapping : 'inst_amt'
				}, {
					name : 'inst_amt_fee',
					mapping : 'inst_amt_fee'
				}, {
					name : 'inst_amt_stlm',
					mapping : 'inst_amt_stlm'
				} ]),
				autoLoad : true
			});

			var withDrawFeeColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '通道匹配值',
						dataIndex : 'key_inst',
						align : 'center',
						width : 250
					}, {
						header : '清算日期',
						dataIndex : 'date_stlm',
						id : 'date_stlm',
						width : 100,
						align : 'center',renderer: formatDt
					}, {
						header : '账户差错类型',
						dataIndex : 'acct_err_type',
						width : 120,
						align : 'center',
						renderer : function(val) {
							if (val == '1') {
								return '<font color="green">冲账</font>';
							} else if (val == '2') {
								return '<font color="blue">补账</font>';
							}
							return val;
						}
					}, {
						header : '交易名称',
						dataIndex : 'txn_num',
						width : 100,
						align : 'center'
					}, {
						header : '调账状态',
						dataIndex : 'acct_change_stat',
						width : 80,
						align : 'center',
						renderer: function(val){
					if(val == '0'){
				       return '<font color="grey">调账失败</font>';
			        }else if(val == '1'){
						return '<font color="green">已冲账</font>';
					}else if(val == '2'){
						return '<font color="blue">已补账</font>';
					}else {
						return '<font color="red">未调账</font>';
					}
				   }				
					}, {
						header : '调账应答码',
						dataIndex : 'acct_change_resp',
						width : 80,
						align : 'center',
						renderer : function(val) {
							if (val == '00') {
								return '<font color="green">成功</font>';
							}else if(val == '99'){
								return '<font color="red">失败</font>';
							}
							return  val;
						}
					}, {
						header : '调账日期',
						dataIndex : 'acct_change_dt',
						width : 80,
						align : 'center'
					}, {
						header : '合作伙伴号',
						dataIndex : 'brh_id',
						width : 100,
						align : 'center'
					}, {
						header : '商户号',
						dataIndex : 'card_accp_id',
						width : 300,
						align : 'left'
					}, {
						header : '结算标识',
						dataIndex : 'stlm_flag',
						width : 100,
						align : 'center'
					}, {
						header : '交易日期',
						dataIndex : 'txn_date',
						width : 100,
						align : 'center',renderer: formatDt
					}, {
						header : '交易时间',
						dataIndex : 'txn_time',
						width : 100,
						align : 'center',renderer: formatDt
					}, {
						header : '系统跟踪号',
						dataIndex : 'sys_seq_num',
						width : 100,
						align : 'center'
					}, {
						header : '检索参考号',
						dataIndex : 'retrivl_ref',
						width : 100,
						align : 'center'
					}, {
						header : '通道名称',
						dataIndex : 'inst_code',
						width : 100,
						align : 'center'
					}, {
						header : '通道商户号',
						dataIndex : 'inst_mcht_id',
						width : 120,
						align : 'center'
					}, {
						header : '通道终端号',
						dataIndex : 'inst_term_id',
						width : 100,
						align : 'center'
					}, {
						header : '通道检索参考号',
						dataIndex : 'inst_retrivl_ref',
						width : 130,
						align : 'center'
					}, {
						header : '终端号',
						dataIndex : 'card_accp_term_id',
						width : 80,
						align : 'center'
					}, {
						header : '终端流水号',
						dataIndex : 'term_sn',
						width : 80,
						align : 'center'
					}, {
						header : '卡号',
						dataIndex : 'pan',
						width : 150,
						align : 'left'
					}, {
						header : '卡类型',
						dataIndex : 'card_type',
						width : 80,
						align : 'center'
					}, {
						header : '交易金额',
						dataIndex : 'amt_trans',
						width : 100,
						align : 'right'
					}, {
						header : '交易手续费',
						dataIndex : 'amt_trans_fee',
						width : 100,
						align : 'right'
					}, {
						header : '结算金额',
						dataIndex : 'amt_stlm',
						width : 100,
						align : 'right'
					}, {
						header : '交易响应码',
						dataIndex : 'resp_code',
						width : 100,
						align : 'center'
					}, {
						header : '通道卡号',
						dataIndex : 'inst_pan',
						width : 100,
						align : 'center'
					}, {
						header : '通道交易金额',
						dataIndex : 'inst_amt',
						width : 100,
						align : 'right'
					}, {
						header : '通道交易手续费',
						dataIndex : 'inst_amt_fee',
						width : 120,
						align : 'right'
					}, {
						header : '通道结算金额',
						dataIndex : 'inst_amt_stlm',
						width : 120,
						align : 'right'
					} ]);
			// 可选合作伙伴下拉列表
			var brhCombo = new Ext.form.ComboBox({
				store : brhStore,
				displayField : 'displayField',
				valueField : 'valueField',
				emptyText : '请选择合作伙伴',
				mode : 'local',
				width : 160,
				triggerAction : 'all',
				forceSelection : true,
				typeAhead : true,
				selectOnFocus : true,
				editable : false,
				//allowBlank : false,
				blankText : '请选择一个合作伙伴',
				fieldLabel : '合作伙伴编号',
				id : 'editBrh',
				name : 'editBrh',
				hiddenName : 'brhIdEdit'
			});
			var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '清算日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',					
					altFormats: 'Y-m-d',
					value:timeYesterday,
					width : 100,
					editable : true,
					id : 'QueryTxn_date1',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true,
					blankText : '请选择日期'
				}, '至', {
					xtype : 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					value:timeYesterday,
					width : 100,
					editable : true,
					id : 'QueryTxn_date2',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true,
					blankText : '请选择日期'
				}, '渠道编号：',brhCombo, '商户编号：', {
					xtype : 'dynamicCombo',
					fieldLabel : '商户编号',
					methodName : 'getMchntIdAll',
					hiddenName : 'mchtNo',
					id : 'mchtNoId',
					editable : true,
					width : 250
				},'-','调账状态：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['','未调账'],['0','调账失败'],['1','已冲账'],['2','已补账']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryEflag',
				editable: false,
				id:'idqueryEflag',
				width: 120
			} ]
			});
			var grid = new Ext.grid.GridPanel(
					{
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
						tbar : [
								{
									xtype : 'button',
									text : '下载报表',
									name : 'download',
									id : 'download',
									iconCls : 'download',
									width : 80,
									handler : function() {
										showMask("正在为您准备报表，请稍后。。。", grid);
										if (withDrawFeeStore.getTotalCount() < System[REPORT_MAX_COUNT]) {
											Ext.Ajax
													.request({
														url : 'T73001ExportAction.asp',
														timeout : 60000,
														params : {
															txn_dateStart : Ext
																	.getCmp('QueryTxn_date1').getValue() == null ? null
																	: Ext.util.Format.date(Ext.getCmp('QueryTxn_date1').getValue(),'Ymd'),
															txn_dateEnd : Ext
																	.getCmp('QueryTxn_date2').getValue() == null ? null
																	: Ext.util.Format.date(Ext.getCmp('QueryTxn_date2').getValue(),'Ymd'),
															brhNo : brhCombo.getValue(),
															machNo : Ext.getCmp('mchtNoId').getValue(),
														queryEflag: Ext.getCmp('idqueryEflag').getValue()

														},
														success : function(rsp,opt) {
															hideMask();
															var rspObj = Ext.decode(rsp.responseText);
															if (rspObj.success) {
																window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + rspObj.msg;
															} else {
																showErrorMsg(rspObj.msg,grid);
															}
														},
														failure : function() {
															hideMask();
															showErrorMsg(rspObj.msg,grid);
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
										Ext.getCmp('QueryTxn_date1').setValue('');
										Ext.getCmp('QueryTxn_date2').setValue('');
										Ext.getCmp('editBrh').setValue('');
										Ext.getCmp('mchtNoId').setValue('');
										Ext.getCmp('idqueryEflag').setValue('');
										withDrawFeeStore.reload();
									}
								},
								'-',
								{
									xtype : 'button',
									text : '补账',
									name : 'add',
									id : 'addT',
									disabled : true,
									iconCls : 'add',
									width : 80,
									handler : function() {	
										var rec = grid.getSelectionModel().getSelected();
										Ext.Ajax
												.request({
													url : 'T73001Action.asp?method=add',
													params : {
														'tblAcctErr.txn_date' : rec.get('txn_date'),
														'tblAcctErr.txn_time' : rec.get('txn_time'),
														'tblAcctErr.sys_seq_num' : rec.get('sys_seq_num'),
														'tblAcctErr.card_accp_id' : rec.get('card_accp_id'),
														'tblAcctErr.pan' : rec.get('pan'),
														'tblAcctErr.card_type' : rec.get('card_type'),
														'tblAcctErr.amt_trans' : rec.get('amt_trans'),
														'tblAcctErr.amt_trans_fee' : rec.get('amt_trans_fee'),
														'tblAcctErr.amt_stlm' : rec.get('amt_stlm'),
														txnId : '73001',
														subTxnId : '02'
													},
													success : function(rsp, opt) {
														var rspObj = Ext.decode(rsp.responseText);
														if (rspObj.success) {
															showSuccessMsg(rspObj.msg,grid);
														} else {
															showErrorMsg(rspObj.msg,grid);
														}
														grid.getStore().reload();
													}
												});

									}
								},
								'-',
								{
									xtype : 'button',
									text : '冲账',
									name : 'edit',
									id : 'edit',
									disabled : true,
									iconCls : 'edit',
									width : 80,
									handler : function() {
										var rec = grid.getSelectionModel().getSelected();
										Ext.Ajax.request({
													url : 'T73001Action.asp?method=update',
													params : {'tblAcctErr.txn_date' : rec.get('txn_date'),
														'tblAcctErr.txn_time' : rec.get('txn_time'),
														'tblAcctErr.sys_seq_num' : rec.get('sys_seq_num'),
														'tblAcctErr.card_accp_id' : rec.get('card_accp_id'),
														'tblAcctErr.pan' : rec.get('pan'),
														'tblAcctErr.card_type' : rec.get('card_type'),
														'tblAcctErr.amt_trans' : rec.get('amt_trans'),
														'tblAcctErr.amt_trans_fee' : rec.get('amt_trans_fee'),
														'tblAcctErr.amt_stlm' : rec.get('amt_stlm'),
														txnId : '73001',
														subTxnId : '01'
													},
													success : function(rsp, opt) {
														var rspObj = Ext.decode(rsp.responseText);
														if (rspObj.success) {
															showSuccessMsg(rspObj.msg,grid);
														} else {
															showErrorMsg(rspObj.msg,grid);
														}
														grid.getStore().reload();
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
							var rec = grid.getSelectionModel().getSelected();
								if (rec.get('acct_err_type') == '1' &&  rec.get('acct_change_stat') != '1'  ) {
								  Ext.getCmp('edit').enable();
								  Ext.getCmp('addT').disable();
								}
								else if (rec.get('acct_err_type') == '2' &&  rec.get('acct_change_stat') != '2' ) {
								 Ext.getCmp('addT').enable();
								 Ext.getCmp('edit').disable();
								}else{
								Ext.getCmp('edit').disable();
								Ext.getCmp('addT').disable();
								}
						}
					});

			withDrawFeeStore.on(
							'beforeload',
							function() {
								Ext.apply(
									this.baseParams,{
										start : 0,
										txn_dateStart : Ext.getCmp('QueryTxn_date1') == null ? null
															: Ext.util.Format.date(
																			Ext.getCmp('QueryTxn_date1').getValue(),'Ymd'),
										txn_dateEnd : Ext.getCmp('QueryTxn_date2').getValue() == null ? null
															: Ext.util.Format.date(
																			Ext.getCmp('QueryTxn_date2').getValue(),'Ymd'),
										brhNo : brhCombo.getValue(),
										queryEflag: Ext.getCmp('idqueryEflag').getValue(),
										machNo : Ext.getCmp('mchtNoId').getValue()
												});
								Ext.getCmp('edit').disable();
								Ext.getCmp('addT').disable();

							});			

			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ grid ],
				renderTo : Ext.getBody()
			});
		});