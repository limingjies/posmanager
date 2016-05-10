var lefttbarArr = new Array();
var btn1 = {
	text : '发送文件',
	width : 85,
	iconCls : 'accept',
	disabled : true,
	handler : function() {
		showConfirm('确认发送文件吗？', leftGrid, function(bt) {
			if (bt == 'yes') {
				var batchId = leftGrid.getSelectionModel().getSelected().data.batchId;
				SendRspFiles.sendExcuteMsg(batchId, function(ret) {
							if ("S" == ret) {
								leftGridStore.reload();
								// Ext.TaskMgr.start(task);
								showSuccessMsg("获取文件成功！", leftPanel);
							} else {
								leftGridStore.reload();
								// Ext.TaskMgr.stop(task);
								showErrorMsg(ret, leftPanel);
							}
						})
			}
		})
	}
};

/*var btn2 = {
	text: '退回',
			width: 85,
			iconCls: 'back',
			disabled: true,
			handler:function() {
				showConfirm('确认退回吗？',leftGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入退回原因',true,back);
					}
				});
			}
};
function back(bt,text) {
			if(bt == 'ok') {
				if(getLength(text) > 60) {
					alert('退回原因最多可以输入30个汉字或60个字母、数字');
					showInputMsg('提示','请输入退回原因',true,back);
					return;
				}
				//showProcessMsg('正在提交审核信息，请稍后......');
				rec = leftGrid.getSelectionModel().getSelected();
				Ext.Ajax.request({
					url: 'T90502Action.asp?method=back',
					params: {
						batchId: rec.get('batchId'),
						txnId: '90502',
						subTxnId: '02',
						refuseInfo: text
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							showSuccessMsg(rspObj.msg,leftGrid);
						} else {
							showErrorMsg(rspObj.msg,leftGrid);
						}
						// 重新加载商户待审核信息
						leftGrid.getStore().reload();
					}
				});
				hideProcessMsg();
			}
};*/
/*var btn3 = {
	text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
			showConfirm('确认拒绝吗？',leftGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入拒绝原因',true,refuse);
				}
			});
		}
};
function refuse(bt,text){
	if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交审核信息，请稍后......');
			rec = leftGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url: 'T90502Action.asp?method=refuse',
				params: {
					batchId: rec.get('batchId'),
					txnId: '90502',
					subTxnId: '03',
					refuseInfo: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,leftGrid);
					} else {
						showErrorMsg(rspObj.msg,leftGrid);
					}
					// 重新加载商户待审核信息
					leftGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
}*/
lefttbarArr.push(btn1);

/** *******************************数据************************************* */
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=sendFilesData'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount",
						idProperty : "batchId"
					}, [{
								name : "batchId",
								mapping : "batchId"
							}, {
								name : "entDate",
								mapping : "entDate"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "busType",
								mapping : "busType"
							}, {
								name : "comCode",
								mapping : "comCode"
							}, {
								name : "fileBatchId",
								mapping : "fileBatchId"
							}, {
								name : "bankCode",
								mapping : "bankCode"
							}, {
								name : "feeCode",
								mapping : "feeCode"
							}, {
								name : "procStat",
								mapping : "procStat"
							}, {
								name : "totCnt",
								mapping : "totCnt"
							}, {
								name : "totAmt",
								mapping : "totAmt"
							}, {
								name : "rspDate",
								mapping : "rspDate"
							}, {
								name : "succCnt",
								mapping : "succCnt"
							}, {
								name : "succAmt",
								mapping : "succAmt"
							}, {
								name : "failCnt",
								mapping : "failCnt"
							}, {
								name : "failAmt",
								mapping : "failAmt"
							}, {
								name : "busCode",
								mapping : "busCode"
							}, {
								name : "tranDate",
								mapping : "tranDate"
							}, {
								name : "curCd",
								mapping : "curCd"
							}, {
								name : "txnFileName",
								mapping : "txnFileName"
							}, {
								name : "fileName",
								mapping : "fileName"
							}, {
								name : "agentAcctNo",
								mapping : "agentAcctNo"
							}, {
								name : "agentAcctName",
								mapping : "agentAcctName"
							}, {
								name : "rNameFlag",
								mapping : "rNameFlag"
							}, {
								name : "rMchtNo",
								mapping : "rMchtNo"
							}, {
								name : "prodCode",
								mapping : "prodCode"
							}, {
								name : "regDate",
								mapping : "regDate"
							}, {
								name : "regTime",
								mapping : "regTime"
							}, {
								name : "batSsn",
								mapping : "batSsn"
							}, {
								name : "lockFlag",
								mapping : "lockFlag"
							}, {
								name : "auditFlag",
								mapping : "auditFlag"
							}, {
								name : "preAuditTlr",
								mapping : "preAuditTlr"
							}, {
								name : "checkTlr",
								mapping : "checkTlr"
							}, {
								name : "finalAuditTlr",
								mapping : "finalAuditTlr"
							}, {
								name : "flag1",
								mapping : "flag1"
							}, {
								name : "flag2",
								mapping : "flag2"
							}, {
								name : "flag3",
								mapping : "flag3"
							}, {
								name : "misc1",
								mapping : "misc1"
							}, {
								name : "misc2",
								mapping : "misc2"
							}, {
								name : "misc3",
								mapping : "misc3"
							}, {
								name : "lstUpdTlr",
								mapping : "lstUpdTlr"
							}, {
								name : "lstUpdTime",
								mapping : "lstUpdTime"
							}, {
								name : "createTime",
								mapping : "createTime"
							}]),
			autoLoad : true
		});
leftGridStore.on("beforeload",function(){
	Ext.apply(this.baseParams,{
		start:0
	})
});
/** *******************************模版************************************* */
var leftGridColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : "批次号",
			width : 120,
			dataIndex : "batchId",
			sortable : false
		}, {
			header : "委托日期",
			width : 75,
			dataIndex : "entDate",
			sortable : false
		}, {
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "业务类型",
			width : 80,
			dataIndex : "busType",
			sortable : false,
			renderer:changeTblRcvPackBusType
		}, {
			header : "企业代码",
			width : 120,
			dataIndex : "comCode",
			sortable : false
		}, {
			header : "文件批次号",
			width : 75,
			dataIndex : "fileBatchId",
			sortable : false
		}, {
			header : "代办银行代码",
			width : 60,
			dataIndex : "bankCode",
			sortable : false
		}, {
			header : "费项代码",
			width : 60,
			dataIndex : "feeCode",
			sortable : false
		}, {
			header : "处理状态",
			width : 80,
			dataIndex : "procStat",
			sortable : false,
			renderer:changeTblRcvPackProcStat
		}, {
			header : "总笔数",
			width : 60,
			dataIndex : "totCnt",
			sortable : false
		}, {
			header : "总金额",
			width : 80,
			dataIndex : "totAmt",
			sortable : false
		}, {
			header : "回执日期",
			width : 75,
			dataIndex : "rspDate",
			sortable : false
		}, {
			header : "成功笔数",
			width : 60,
			dataIndex : "succCnt",
			sortable : false
		}, {
			header : "成功金额",
			width : 80,
			dataIndex : "succAmt",
			sortable : false
		}, {
			header : "失败笔数",
			width : 60,
			dataIndex : "failCnt",
			sortable : false
		}, {
			header : "失败金额",
			width : 80,
			dataIndex : "failAmt",
			sortable : false
		}, {
			header : "业务编码",
			width : 100,
			dataIndex : "busCode",
			sortable : false,
			renderer : changeSndPackBusCode
		}, {
			header : "应划款日期",
			width : 75,
			dataIndex : "tranDate",
			sortable : false
		}, {
			header : "币种",
			width : 60,
			dataIndex : "curCd",
			sortable : false,
			renderer:changeTblRcvPackDtlCurCd
		}, {
			header : "交易文件名",
			width : 100,
			dataIndex : "txnFileName",
			sortable : false
		}, {
			header : "文件名",
			width : 200,
			dataIndex : "fileName",
			sortable : false
		}, {
			header : "代办账号",
			width : 60,
			dataIndex : "agentAcctNo",
			sortable : false
		}, {
			header : "代办账户名称",
			width : 60,
			dataIndex : "agentAcctName",
			sortable : false
		}, {
			header : "替换户名标志",
			width : 60,
			dataIndex : "rNameFlag",
			sortable : false
		}, {
			header : "替换二级商户代码",
			width : 60,
			dataIndex : "rMchtNo",
			sortable : false
		}, {
			header : "产品编码",
			width : 60,
			dataIndex : "prodCode",
			sortable : false
		}, {
			header : "登记日期",
			width : 75,
			dataIndex : "regDate",
			sortable : false
		}, {
			header : "登记时间",
			width : 75,
			dataIndex : "regTime",
			sortable : false
		}, {
			header : "联机批量批次号",
			width : 120,
			dataIndex : "batSsn",
			sortable : false
		}, {
			header : "锁定标志",
			width : 60,
			dataIndex : "lockFlag",
			sortable : false,
			renderer:changeTblRcvLockFlag
		}, {
			header : "审核标志",
			width : 80,
			dataIndex : "auditFlag",
			sortable : false,
			renderer:changeAuditFlag
		}, {
			header : "初审人",
			width : 80,
			dataIndex : "preAuditTlr",
			sortable : false
		}, {
			header : "复核人",
			width : 80,
			dataIndex : "checkTlr",
			sortable : false
		}, {
			header : "终审人",
			width : 80,
			dataIndex : "finalAuditTlr",
			sortable : false
		},
//		{
//			header : "FLAG1",
//			width : 60,
//			dataIndex : "flag1",
//			sortable : false
//		}, {
//			header : "FLAG2",
//			width : 60,
//			dataIndex : "flag2",
//			sortable : false
//		}, {
//			header : "FLAG3",
//			width : 60,
//			dataIndex : "flag3",
//			sortable : false
//		}, 
			{
			header : "拒绝原因",
			width : 60,
			dataIndex : "misc1",
			sortable : false
		}, 
//			{
//			header : "保留域2",
//			width : 60,
//			dataIndex : "misc2",
//			sortable : false
//		}, {
//			header : "保留域3",
//			width : 60,
//			dataIndex : "misc3",
//			sortable : false
//		}, 
			{
			header : "最近更新柜员",
			width : 80,
			dataIndex : "lstUpdTlr",
			sortable : false
		}, {
			header : "最近更新时间",
			width : 120,
			dataIndex : "lstUpdTime",
			sortable : false
		}, {
			header : "创建时间",
			width : 120,
			dataIndex : "createTime",
			sortable : false
		}]);


/** *******************************界面************************************* */

var leftGrid = new Ext.grid.EditorGridPanel({
			region : "center",
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			store : leftGridStore,
			sm : new Ext.grid.RowSelectionModel({
						singleSelect : true
					}),
			cm : leftGridColModel,
			forceValidation : true,
			clicksToEdit : true,
			loadMask : {
					msg : '正在加载代收付来帐包登记簿列表......'
				},
			bbar:new Ext.PagingToolbar({
					store:leftGridStore,
					pageSize:System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
			})
		});

var leftPanel = new Ext.Panel({
		region: 'center',
		title:"发送文件",
		frame: true,
		layout: 'border',
		tbar :[lefttbarArr],
		items:[leftGrid]
});
var mainUI = new Ext.Viewport({
			layout : 'border',
			items : [leftPanel]
		});
		
/** *******************************事件处理************************************* */
leftGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last)).frame();

			leftPanel.getTopToolbar().items.items[0].enable();
		}
	});
