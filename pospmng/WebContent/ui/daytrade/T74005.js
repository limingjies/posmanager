Ext.onReady(function() {

    var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
			// 数据集
			var withDrawFeeStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=withdrawErr'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'front_dt',
					mapping : 'front_dt'
				}, {
					name : 'front_sn',
					mapping : 'front_sn'
				}, {
					name : 'mcht_withdraw_dt',
					mapping : 'mcht_withdraw_dt'
				}, {
					name : 'mcht_withdraw_sn',
					mapping : 'mcht_withdraw_sn'
				}, {
					name : 'txn_date',
					mapping : 'txn_date'
				}, {
					name : 'err_type',
					mapping : 'err_type'
				}, {
					name : 'acct_change',
					mapping : 'acct_change'
				} 
				, {
					name : 'acct_change_resp',
					mapping : 'acct_change_resp'
				} 
				, {
					name : 'acct_change_dt',
					mapping : 'acct_change_dt'
				}, {
					name : 'front_stat',
					mapping : 'front_stat'
				}, {
					name : 'front_withdraw_amt',
					mapping : 'front_withdraw_amt'
				}, {
					name : 'front_withdraw_fee',
					mapping : 'front_withdraw_fee'
				}, {
					name : 'front_withdraw_stlm_amt',
					mapping : 'front_withdraw_stlm_amt'
				}, {
					name : 'acct_withdraw_stat',
					mapping : 'acct_withdraw_stat'
				}, {
					name : 'acct_withdraw_amt',
					mapping : 'acct_withdraw_amt'
				}, {
					name : 'acct_withdraw_fee',
					mapping : 'acct_withdraw_fee'
				}, {
					name : 'acct_withdraw_stlm_amt',
					mapping : 'acct_withdraw_stlm_amt'
				}  , {
					name : 'inst_dt',
					mapping : 'inst_dt'
				} , {
					name : 'update_dt',
					mapping : 'update_dt'
				} 
				]),
				autoLoad : true
			});

			var withDrawFeeColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '前置时间',
						dataIndex : 'front_dt',
						align : 'center',
						width : 150,renderer: formatDt
					}, {
						header : '前置流水号',
						dataIndex : 'front_sn',
						id : 'front_sn',
						width : 100,
						align : 'center'
					}, {
						header : '原商服提现时间',
						dataIndex : 'mcht_withdraw_dt',
						width : 150,
						align : 'center',renderer: formatDt
					}, {
						header : '原商服提现流水',
						dataIndex : 'mcht_withdraw_sn',
						width : 100,
						align : 'center'
					}, {
						header : '交易日期',
						dataIndex : 'txn_date',
						width : 120,
						align : 'center',
						renderer: formatDt
						
					}, {
						header : '差异类型',
						dataIndex : 'err_type',
						width : 150,
						align : 'center',
						renderer : function(val) {
							if (val == 'D001') {
								 return '<font color="grey">前置和账户系统数据差异</font>';
							}else if(val == 'D002'){
								 return '<font color="grey">前置存在，账户不存在</font>';
							}else if(val == 'D003'){
								return '<font color="green">前置不存在，账户存在</font>';
							} 							
							return val;
						}
						
					}, {
						header : '调账状态',
						dataIndex : 'acct_change',
						width : 80,
						align : 'center',
					renderer: function(val){				
					if(val == '0'){
				       return '<font color="grey">调账失败</font>';
			        }else if(val == '1'){
						return '<font color="green">已冲账</font>';
					}else {
						return '<font color="red">未调账</font>';
					}								
					}}
					, {
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
					}
					, {
						header : '调账时间',
						dataIndex : 'acct_change_dt',
						width : 120,
						align : 'center'
					}, {
						header : '前置提现状态',
						dataIndex : 'front_stat',
						width : 100,
						align : 'center',
						renderer : function(val) {
							if (val == '0') {
								return '成功';
							}else if(val == '1'){
								return '失败';
							}else if(val == '2'){
								return '处理中';
							} 							
							return val;
						}
							
					}, {
						header : '前置提现金额',
						dataIndex : 'front_withdraw_amt',
						width : 100,
						align : 'right'
					}, {
						header : '前置提现手续费',
						dataIndex : 'front_withdraw_fee',
						width : 100,
						align : 'right'
					}, {
						header : '前置提现结算金额',
						dataIndex : 'front_withdraw_stlm_amt',
						width : 150,
						align : 'right'
					}, {
						header : '账户提现状态',
						dataIndex : 'acct_withdraw_stat',
						width : 100,
						align : 'center'
					}, {
						header : '账户提现金额',
						dataIndex : 'acct_withdraw_amt',
						width : 150,
						align : 'right'
					}, {
						header : '账户提现手续费',
						dataIndex : 'acct_withdraw_fee',
						width : 120,
						align : 'right'
					} 
					, {
						header : '账户提现结算金额',
						dataIndex : 'acct_withdraw_stlm_amt',
						width : 120,
						align : 'right'
					}
					, {
						header : '插入时间',
						dataIndex : 'inst_dt',
						width : 150,
						align : 'center',renderer: formatDt
					}, {
						header : '更新时间',
						dataIndex : 'update_dt',
						width : 150,
						align : 'center',renderer: formatDt
					}]);

			var tbar1 = new Ext.Toolbar({
				renderTo : Ext.grid.EditorGridPanel.tbar,
				items : [ '-', '交易日期：', {
					xtype : 'datefield',
					format : 'Y-m-d',					
					altFormats: 'Y-m-d',
					value:timeYesterday,
					width : 120,
					editable : true,
					id: 'startDate',
					name: 'startDate',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true,
					blankText : '请选择日期'
				},'-','至：',{
					xtype : 'datefield',
					format : 'Y-m-d',					
					altFormats: 'Y-m-d',
					value:timeYesterday,
					width : 120,
					editable : true,
					id: 'endDate',
					name: 'endDate',
					mode : 'local',
					triggerAction : 'all',
					forceSelection : true,
					selectOnFocus : true,
					lazyRender : true,
					blankText : '请选择日期'
               	},'-','调账状态：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['','未调账'],['0','调账失败'],['1','已冲账']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryEflag',
				editable: false,
				id:'idqueryEflag',
				width: 120
			}
				
				]
			});

			var grid = new Ext.grid.GridPanel(
					{
						title : '提现调账',
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
							msg : '正在加载提现调账列表......'
						},
						tbar : [{
							xtype: 'button',
							text: '下载报表',
							name: 'download',
							id: 'download',
							iconCls: 'download',
							width: 80,
							handler:function() {
								showMask("正在为您准备报表，请稍后。。。",grid);
								if (withDrawFeeStore.getTotalCount() < System[REPORT_MAX_COUNT]){
								Ext.Ajax.request({
									url: 'T74005ReportAction.asp',
									timeout: 60000,
									params: {
										txn_date : Ext.getCmp('startDate') == null ? null
															: Ext.util.Format.date(Ext.getCmp('startDate').getValue(),'Ymd'),
									   txn_date2 : Ext.getCmp('endDate') == null ? null
															: Ext.util.Format.date(Ext.getCmp('endDate').getValue(),'Ymd'),
										queryEflag: Ext.getCmp('idqueryEflag').getValue(),
										
										txnId : '74005',
										subTxnId : '02'
									},
									success: function(rsp,opt) {
										hideMask();
										var rspObj = Ext.decode(rsp.responseText);
										if(rspObj.success) {
											window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
																		rspObj.msg;
										} else {
											showErrorMsg(rspObj.msg,grid);
										}
									},
									failure: function(){
										hideMask();
										showErrorMsg(rspObj.msg,grid);
									}
								});
//								grid.load();
							    } else {
							    	hideMask();
							    	Ext.MessageBox.show({
										msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
										title: '报表下载说明',
										buttons: Ext.MessageBox.OK,
										modal: true,
										width: 220
									});
								}
							}},'-',
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
										Ext.getCmp('startDate')
										.setValue('');
										Ext.getCmp('endDate').setValue('');
										Ext.getCmp('idqueryEflag').setValue('');
										withDrawFeeStore.reload();
									}
								},
								'-',
								{xtype : 'button',
									text : '冲账',
									name : 'edit',
									id : 'edit',
									disabled : true,
									iconCls : 'edit',
									width : 80,
									handler : function() {
										var rec =grid.getSelectionModel().getSelected();										
										Ext.Ajax.request({
											url : 'T74005Action.asp?method=update',
											params : {
												'tblWithdrawErr.mcht_withdraw_dt' : rec.get('mcht_withdraw_dt'),
												'tblWithdrawErr.mcht_withdraw_sn' : rec.get('mcht_withdraw_sn'),
												txnId : '74005',
												subTxnId : '01'
											},
											success : function(
													rsp,
													opt) {
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
								}],
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
									if (rec.get('err_type') == 'D003' && rec.get('acct_change') !='1' ){
									Ext.getCmp('edit').enable();	
									}else{
									Ext.getCmp('edit').disable();	
									}
									
						}
					});

			withDrawFeeStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
					start : 0,
					txn_date : Ext.getCmp('startDate') == null ? null
									: Ext.util.Format.date(Ext.getCmp('startDate').getValue(),'Ymd'),
					txn_date2 : Ext.getCmp('endDate') == null ? null
									: Ext.util.Format.date(Ext.getCmp('endDate').getValue(),'Ymd'),										
					queryEflag: Ext.getCmp('idqueryEflag').getValue()

				});
				Ext.getCmp('edit').disable();			
			});

			var mainView = new Ext.Viewport({
				layout : 'border',
				items : [ grid ],
				renderTo : Ext.getBody()
			});
		});