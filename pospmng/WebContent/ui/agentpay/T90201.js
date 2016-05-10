var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		areaInfoWin.show();
		areaInfoWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该地区码信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90201Action.asp?method=delete',
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
								areaNo: rec.get('areaNo'),
								zip:rec.get('zip'),
								txnId: '90201',
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
					areaNo :record.get("areaNo"),
					zip : record.get('zip'),
					province : record.get('province'),
					areaName : record.get('areaName')
				};
				array.push(data);
			}
		Ext.Ajax.requestNeedAuthorise({
				url: 'T90201Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    areaCodeList: Ext.encode(array),
					txnId: '90201',
					subTxnId: '02'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,areaCodeForm);
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
var areaInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items : [
			{
			fieldLabel:"地区码*",
			id:"areaNO",
			name :"areaNo",
			allowBlank: false,
			maxLength: 5,
			width:150
		},{
			fieldLabel:"邮编*",
			id:"zip",
			name :"zip",
			allowBlank: false,
			regex:/^[0-9]{6}$/,
			maxLength: 6,
			width:150
		},{
			fieldLabel:"省",
			id:"province",
			name :"province",
			allowBlank: true,
			maxLength: 40,
			width:150
		},{
			fieldLabel:"地区名称",
			id:"areaName",
			name :"areaName",
			allowBlank: true,
			maxLength: 40,
			width:150
		}
		]
});


var areaInfoWin = new Ext.Window({
	title : "地区码添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [areaInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (areaInfoForm.getForm().isValid()) {
				areaInfoForm.getForm().submitNeedAuthorise({
							url : 'T90201Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, areaInfoForm);
								// 重置表单
								areaInfoForm.getForm().reset();
								// 重新加载参数列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, areaInfoForm);
							},
							params : {
								txnId : '90201',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			areaInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			areaInfoWin.hide();
		}
	}]
});

var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=areaInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "areaNo",
								mapping : "areaNo"
							}, {
								name : "zip",
								mapping : "zip"
							}, {
								name : "province",
								mapping : "province"
							}, {
								name : "areaName",
								mapping : "areaName"
							}]),
			autoLoad : true
		});
var leftGridColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : "地区码",
			width : 160,
			dataIndex : "areaNo",
			sortable : false
		}, {
			header : "邮编",
			width : 160,
			dataIndex : "zip",
			sortable : false
//			,
//			editor:new Ext.form.TextField({
//        		 	maxLength: 6,
//        		 	regex:/^[0-9]{6}$/,
//        			allowBlank: false
//        		 })
		}, {
			header : "省",
			width : 160,
			dataIndex : "province",
			sortable : false,
			editor:new Ext.form.TextField({
        		 	maxLength: 40,
        			allowBlank: true
        		 })
		}, {
			header : "地区名称",
			width : 160,
			dataIndex : "areaName",
			sortable : true,
			editor:new Ext.form.TextField({
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
					msg : '正在加载地区码信息列表......'
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
		title:"代收付地区码信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});