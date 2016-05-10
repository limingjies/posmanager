var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		bankInfoWin.show();
		bankInfoWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该银行代码信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90202Action.asp?method=delete',
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
								bankNo: rec.get('bankNo'),
								txnId: '90202',
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
	handler : function() {
		var modifiedRecords = leftGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
		var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					bankNo :record.get("bankNo"),
					bankName : record.get('bankName')
				};
				array.push(data);
			}
		Ext.Ajax.requestNeedAuthorise({
				url: 'T90202Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    bankCodeList: Ext.encode(array),
					txnId: '90202',
					subTxnId: '02'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,bankCodeForm);
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						leftGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,leftGrid);
					} else {
						leftGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,leftGrid);
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


var bankInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items : [
			{
			fieldLabel:"银行代码*",
			id:"bankNO",
			name :"bankNo",
			allowBlank: false,
			regex:/^\w+$/,
			maxLength: 14,
			width:150
		},{
			fieldLabel:"银行名称",
			id:"bankName",
			name :"bankName",
			allowBlank: true,
			maxLength: 40,
			width:150
		}
		]
});


var bankInfoWin = new Ext.Window({
	title : "地区码添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [bankInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (bankInfoForm.getForm().isValid()) {
				bankInfoForm.getForm().submitNeedAuthorise({
							url : 'T90202Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, bankInfoForm);
								// 重置表单
								bankInfoForm.getForm().reset();
								// 重新加载参数列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, bankInfoForm);
							},
							params : {
								txnId : '90202',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			bankInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			bankInfoWin.hide();
		}
	}]
});


var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=bankInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "bankNo",
								mapping : "bankNo"
							}, {
								name : "bankName",
								mapping : "bankName"
							}]),
			autoLoad : true
		});
var leftGridColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : "银行代码",
			width : 360,
			dataIndex : "bankNo",
			sortable : false
		}, {
			header : "银行名称",
			width : 360,
			dataIndex : "bankName",
			sortable : false,
			editor: new Ext.form.TextField({
        		 	maxLength: 40,
        			allowBlank: false
        		 })
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
					msg : '正在加载银行代码信息列表......'
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
		'afteredit': function() {
			leftPanel.getTopToolbar().items.items[4].enable();
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
		title:"代收付银行代码信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});