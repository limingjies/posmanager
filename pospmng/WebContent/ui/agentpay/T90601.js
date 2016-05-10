var dsfMchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_MCHT_NO',function(ret){
		dsfMchtNoStore.loadData(Ext.decode(ret));
	});
var tbarArr = new Array();
/*var btn1 = {
	text : '生成退汇文件',
	iconCls : 'edit',
	handler : function() {
		
	}
}*/
var btn2 = {
	text : '下载退汇文件',
	iconCls : 'download',
	handler : function() {
				Ext.Ajax.request({
					url: 'T90601Action.asp',
					timeout: 60000,
					params: {
						batchId:Ext.getCmp('searchBatchId').getValue(),
						startEntDate:typeof(Ext.getCmp('searchStartEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStartEntDate').getValue().format('Ymd'),
//						stopEntDate:typeof(Ext.getCmp('searchStopEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStopEntDate').getValue().format('Ymd'),
						mchtNo:Ext.get('searchMchtNo').getValue(),
						txnId: '90601',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,riskDataGrid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,riskDataGrid);
					}
				});
		
	}
}
var btn3 = {
	text : '查询退汇详细',
	iconCls : 'query',
	handler : function() {
		leftGridStore.load();
	}
}
//tbarArr.push(btn1);
//tbarArr.push("-");
tbarArr.push(btn2);
tbarArr.push("-");
tbarArr.push(btn3);

var queryConditionTbar1 = new Ext.Toolbar({
			renderTo : Ext.grid.EditorGridPanel.tbar,
			items : ["-", "批次号:", {
						xtype : 'textfield',
						id : 'searchBatchId',
						width : 110,
						regex:/^\d{0,14}$/,
						name : 'searchBatchId',
						fieldLabel : '批次号'
					},"-", "商户号:", {
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
						editable : true,
			        	store: dsfMchtNoStore,
			        	width:130,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'searchMchtNo'
					}, "-", "日期:", {
						xtype : 'datefield',
						id : 'searchStartEntDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStartEntDate',
						fieldLabel : '日期'
					}
					/*, "-", "截止时间:", {
						xtype : 'datefield',
						id : 'searchStopEntDate',
						width : 110,
						format : 'Y-m-d',
						name : 'searchStopEntDate',
						fieldLabel : '截止时间'
					}*/
					]
		});

var leftGridStore = new  Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=rcvReexchangeData'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "entDate",
								mapping : "entDate"
							}, {
								name : "batchId",
								mapping : "batchId"
							}, {
								name : "seq",
								mapping : "seq"
							}, {
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "rcvDate",
								mapping : "rcvDate"
							}, {
								name : "jsAcctNo",
								mapping : "jsAcctNo"
							}, {
								name : "procStat",
								mapping : "procStat"
							}, {
								name : "rcvBatch_id",
								mapping : "rcvBatch_id"
							}, {
								name : "custId",
								mapping : "custId"
							}, {
								name : "bankCode",
								mapping : "bankCode"
							}, {
								name : "acctNo",
								mapping : "acctNo"
							}, {
								name : "name",
								mapping : "name"
							}, {
								name : "province",
								mapping : "province"
							}, {
								name : "city",
								mapping : "city"
							}, {
								name : "opnOrgName",
								mapping : "opnOrgName"
							}, {
								name : "acctType",
								mapping : "acctType"
							}, {
								name : "custType",
								mapping : "custType"
							}, {
								name : "amt",
								mapping : "amt"
							}, {
								name : "curCd",
								mapping : "curCd"
							}, {
								name : "agtNo",
								mapping : "agtNo"
							}, {
								name : "agtCustNo",
								mapping : "agtCustNo"
							}, {
								name : "idType",
								mapping : "idType"
							}, {
								name : "idNo",
								mapping : "idNo"
							}, {
								name : "mobile",
								mapping : "mobile"
							}, {
								name : "custSelfNo",
								mapping : "custSelfNo"
							}, {
								name : "remark",
								mapping : "remark"
							}, {
								name : "rspCode",
								mapping : "rspCode"
							}, {
								name : "rspDesc",
								mapping : "rspDesc"
							},{
								name : "rspDate",
								mapping : "rspDate"
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
			header : "明细序号",
			width : 60,
			dataIndex : "seq",
			sortable : false
		}, {
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "来帐日期",
			width : 80,
			dataIndex : "rcvDate",
			sortable : false
		},  {
			header : "结算账号",
			width : 80,
			dataIndex : "jsAcctNo",
			sortable : false
		}, {
			header : "处理状态",
			width : 80,
			dataIndex : "procStat",
			sortable : false,
			renderer:changeTblRcvPackDtlProcStat
		}, {
			header : "来帐批次号",
			width : 120,
			dataIndex : "rcvBatch_id",
			sortable : false
		}, {
			header : "用户编号",
			width : 80,
			dataIndex : "custId",
			sortable : false
		}, {
			header : "银行代码",
			width : 80,
			dataIndex : "bankCode",
			sortable : false
		}, {
			header : "账号",
			width : 80,
			dataIndex : "acctNo",
			sortable : false
		}, {
			header : "户名",
			width : 80,
			dataIndex : "name",
			sortable : false
		}, {
			header : "省",
			width : 80,
			dataIndex : "province",
			sortable : false
		}, {
			header : "市",
			width : 80,
			dataIndex : "city",
			sortable : false
		}, {
			header : "开户行名称",
			width : 100,
			dataIndex : "opnOrgName",
			sortable : false
		}, {
			header : "账户类型",
			width : 80,
			dataIndex : "acctType",
			sortable : false,
			renderer:changeTblRcvPackDtlAcctType
		}, {
			header : "客户类型",
			width : 80,
			dataIndex : "custType",
			sortable : false,
			renderer:changeTblRcvPackDtlCustType
		}, {
			header : "金额",
			width : 80,
			dataIndex : "amt",
			sortable : false
		}, {
			header : "货币类型",
			width : 60,
			dataIndex : "curCd",
			sortable : false,
			renderer:changeTblRcvPackDtlCurCd
		}, {
			header : "协议号",
			width : 100,
			dataIndex : "agtNo",
			sortable : false
		}, {
			header : "协议用户编号",
			width : 100,
			dataIndex : "agtCustNo",
			sortable : false
		}, {
			header : "证件类型",
			width : 80,
			dataIndex : "idType",
			sortable : false,
			renderer:changeTblRcvPackDtlIdType
		}, {
			header : "证件号",
			width : 100,
			dataIndex : "idNo",
			sortable : false
		}, {
			header : "手机号",
			width : 100,
			dataIndex : "mobile",
			sortable : false
		}, {
			header : "自定义用户号",
			width : 80,
			dataIndex : "custSelfNo",
			sortable : false
		}, {
			header : "备注",
			width : 120,
			dataIndex : "remark",
			sortable : false
		}, {
			header : "响应状态",
			width : 65,
			dataIndex : "rspCode",
			sortable : false,
			renderer: function(val){
				if(val ==""){
					return '未处理';
				}else	if(val =='0000'){
					return '<font color="green">处理成功</font>';
				}else{
					return '<font color="red">处理失败</font>';
				}
			}
		}, {
			header : "原因",
			width : 270,
			dataIndex : "rspDesc",
			sortable : false
		},  {
			header : "回执日期",
			width : 75,
			dataIndex : "rspDate",
			sortable : false
		}, 
//			{
//			header : "FLAG1",
//			width : 60,
//			dataIndex : "flag1",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "FLAG2",
//			width : 60,
//			dataIndex : "flag2",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "FLAG3",
//			width : 60,
//			dataIndex : "flag3",
//			hidden:true,
//			sortable : false
//		}, 
//			{
//			header : "保留域1",
//			width : 60,
//			dataIndex : "misc1",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "保留域2",
//			width : 60,
//			dataIndex : "misc2",
//			hidden:true,
//			sortable : false
//		}, {
//			header : "保留域3",
//			width : 60,
//			dataIndex : "misc3",
//			hidden:true,
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
					msg : '正在加载代收付退汇信息列表......'
				},
			bbar:new Ext.PagingToolbar({
					store:leftGridStore,
					pageSize:System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
			})
		});
leftGridStore.on("beforeload",function(){
	Ext.apply(this.baseParams,{
		start:0,
		batchId:Ext.getCmp('searchBatchId').getValue(),
		startEntDate:typeof(Ext.getCmp('searchStartEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStartEntDate').getValue().format('Ymd'),
//		stopEntDate:typeof(Ext.getCmp('searchStopEntDate').getValue()) == 'string' ? '' : Ext.getCmp('searchStopEntDate').getValue().format('Ymd'),
		mchtNo:Ext.get('searchMchtNo').getValue()
	})
});
var leftPanel = new Ext.Panel({
		region: 'center',
		title:"代收付退汇信息",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		listeners:{
			'render' : function() {
				queryConditionTbar1.render(this.tbar);
			}
		},
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});