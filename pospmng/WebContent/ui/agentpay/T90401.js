var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		mchtFundWin.show();
		mchtFundWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该商户资金信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90401Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,leftGrid);
									leftGrid.getStore().reload();
									leftPanel.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,leftGrid);
								}
							},
							params: { 
								mchtNo: rec.get('mchtNo'),
								txnId: '90401',
								subTxnId: '03'
							}
						});
					}
				});
			}
	}
}
var btn3 = {
	text : '保存',
	iconCls : 'reload',
	disabled : true,
	handler : function() {
		var modifiedRecords = leftGrid.getStore().getModifiedRecords();
		if (modifiedRecords.length == 0) {
			return;
		}
		var array = new Array();
		for (var index = 0; index < modifiedRecords.length; index++) {
			var record = modifiedRecords[index];
			var data = {
				mchtNo : record.get("mchtNo"),
				bal : record.get("bal"),
				avlBal : record.get("avlBal"),
				frzAmt : record.get("frzAmt")
//				flag1 : record.get("flag1"),
//				flag2 : record.get("flag2"),
//				flag3 : record.get("flag3"),
//				misc1 : record.get("misc1"),
//				misc2 : record.get("misc2"),
//				misc3 : record.get("misc3"),
//				lstUpdTlr : record.get("lstUpdTlr"),
//				lstUpdTime : record.get("lstUpdTime"),
//				createTime : record.get("createTime")
			};
			array.push(data);
		}
		Ext.Ajax.requestNeedAuthorise({
					url : 'T90401Action.asp?method=update',
					method : 'post',
					timeout : '10000',
					params : {
						mchtFundList : Ext.encode(array),
						txnId : '90401',
						subTxnId : '02'
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg, areaCodeForm);
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							leftGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg, leftGrid);
						} else {
							leftGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg, leftGrid);
						}
						leftPanel.getTopToolbar().items.items[4].disable();
						leftGrid.getStore().reload();
						hideProcessMsg();
					}
				});
	}
}
tbarArr.push(btn1);
tbarArr.push("-");
tbarArr.push(btn2);
tbarArr.push("-");
tbarArr.push(btn3);

var mchtFundForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 600,
			defaultType : 'textfield',
			labelWidth : 160,
			waitMsgTarget : true,
			items : [{
						fieldLabel : "商户号",
						id : "mchtNo",
						name : "mchtNo",
						allowBlank : false,
						maxLength : 15,
						width : 150
					}, 
						/*{
						xtype:"numberfield",
						fieldLabel : "账户余额",
						id : "bal",
						name : "bal",
						maxLength : 14,
						regex: /^(\d{1,11})(\.\d{0,2})?$/,
						width : 150
					}, */
						{
						xtype:"numberfield",
						fieldLabel : "可用余额",
						id : "avlBal",
						name : "avlBal",
						maxLength : 14,
						regex: /^(\d{1,11})(\.\d{0,2})?$/,
						width : 150
					}, {
						xtype:"numberfield",
						fieldLabel : "冻结金额",
						id : "frzAmt",
						name : "frzAmt",
						maxLength : 14,
						regex: /^(\d{1,11})(\.\d{0,2})?$/,
						width : 150
					}]
		});
var mchtFundWin = new Ext.Window({
	title : "商户信息添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [mchtFundForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (mchtFundForm.getForm().isValid()) {
				mchtFundForm.getForm().submitNeedAuthorise({
							url : 'T90401Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, mchtFundForm);
								// 重置表单
								mchtFundForm.getForm().reset();
								// 重新加载商户信息列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, mchtFundForm);
							},
							params : {
								txnId : '90401',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			mchtFundForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			mchtFundWin.hide();
		}
	}]
});
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=mchtFund'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "bal",
								mapping : "bal"
							}, {
								name : "avlBal",
								mapping : "avlBal"
							}, {
								name : "frzAmt",
								mapping : "frzAmt"
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
			header : "商户号",
			width : 120,
			dataIndex : "mchtNo",
			sortable : false
		}, {
			header : "账户余额",
			width : 200,
			dataIndex : "bal",
			sortable : false
		}, {
			header : "可用余额",
			width : 200,
			dataIndex : "avlBal",
			sortable : false,
			editor:new Ext.form.NumberField({
					maxLength : 14,
					regex: /^(\d{1,11})(\.\d{0,2})?$/
			})
		}, {
			header : "冻结金额",
			width : 200,
			dataIndex : "frzAmt",
			sortable : false,
			validator:function(val){
				alert(val);
			},
			editor:new Ext.form.NumberField({
					maxLength : 14,
					regex: /^(\d{1,11})(\.\d{0,2})?$/
			})
		}, 
//			{
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
//		}, {
//			header : "保留域1",
//			width : 60,
//			dataIndex : "misc1",
//			sortable : false
//		}, {
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
			width : 100,
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
					msg : '正在加载代收付商户资金列表......'
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
		start:0
	})
});
	//每次在列表信息加载前都将保存按钮屏蔽
	leftGrid.getStore().on('beforeload',function() {
		leftPanel.getTopToolbar().items.items[2].disable();
		leftPanel.getTopToolbar().items.items[4].disable();
		leftGrid.getStore().rejectChanges();
	});
	
leftGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(val) {
			leftPanel.getTopToolbar().items.items[4].enable();
			//自动计算出 账户余额； 账户余额=可用金额+冻结金额
			if(val.record.get("frzAmt")==""){
				val.record.set("frzAmt",0);
			}
			if(val.record.get("avlBal")==""){
				val.record.set("avlBal",0);
			}
			val.record.set("bal",parseFloat(val.record.get("frzAmt"),10)+parseFloat(val.record.get("avlBal"),10));
		}
	});
leftGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last)).frame();
		leftPanel.getTopToolbar().items.items[2].enable();
		}
	});
var leftPanel = new Ext.Panel({
		region: 'center',
		title:"代收付商户资金维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});