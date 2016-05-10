//定义范围下拉列表 
var usageStore = new Ext.data.SimpleStore({
			fields : ["value", "text"],
			id: 'valueField',
			data : [["0", "代付"], ["1", "代收"]]
		});

var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		busCodeInfoWin.show();
		busCodeInfoWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该业务代码信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90203Action.asp?method=delete',
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
								busCode: rec.get('busCode'),
								txnId: '90203',
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
				busCode : record.get("busCode"),
				usage : record.get("usage"),
				typeNo : record.get("typeNo"),
				name : record.get("name"),
				seq : record.get("seq")
			};
				array.push(data);
			}
		Ext.Ajax.requestNeedAuthorise({
				url: 'T90203Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    busCodeCodeList: Ext.encode(array),
					txnId: '90203',
					subTxnId: '02'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,busCodeCodeForm);
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

var busCodeInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items : [
			{
			fieldLabel:"银行代码",
			id:"busCode",
			name :"busCode",
			allowBlank: false,
			maxLength: 5,
			width:150
		},{
			fieldLabel:"定义范围",
			xtype: 'combo',
			id:"usageId",
			name :"usageNM",
			width:150,
			store: usageStore,
			valueField : 'value',// 值
			displayField : 'text',// 显示文本
			hiddenName: 'usage'
		},{
			fieldLabel:"类别号",
			id:"typeNo",
			name :"typeNo",
			allowBlank: false,
			maxLength: 2,
			width:150
		},{
			fieldLabel:"名称",
			id:"name",
			name :"name",
			allowBlank: false,
			maxLength: 40,
			width:150
		},{
			fieldLabel:"序号",
			id:"seq",
			name :"seq",
			allowBlank: false,
			maxLength: 2,
			width:150
		}
		]
});
var busCodeInfoWin = new Ext.Window({
	title : "地区码添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [busCodeInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (busCodeInfoForm.getForm().isValid()) {
				busCodeInfoForm.getForm().submitNeedAuthorise({
							url : 'T90203Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, busCodeInfoForm);
								// 重置表单
								busCodeInfoForm.getForm().reset();
								// 重新加载参数列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, busCodeInfoForm);
							},
							params : {
								txnId : '90203',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			busCodeInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			busCodeInfoWin.hide();
		}
	}]
});
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=busCodeInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "busCode",
								mapping : "busCode"
							}, {
								name : "usage",
								mapping : "usage"
							}, {
								name : "typeNo",
								mapping : "typeNo"
							}, {
								name : "name",
								mapping : "name"
							}, {
								name : "seq",
								mapping : "seq"
							}]),
			autoLoad : true
		});
var leftGridColModel = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(), {
			header : "业务代码",
			width : 160,
			dataIndex : "busCode",
			sortable : false
		}, {
			header : "定义范围",
			width : 160,
			dataIndex : "usage",
			sortable : false,
			editor: {
					xtype: 'combo',
			        store: usageStore,
					valueField : 'value',// 值
					displayField : 'text'// 显示文本
		       },
		    renderer:function(data){
		    	if(data == '0'){
		    		return '代付';
		    	}else if(data == '1'){
		    		return '代收';
		    	} else{
		    		return data;
		    	}
		    }
		}, {
			header : "类别号",
			width : 160,
			dataIndex : "typeNo",
			sortable : false,
			editor: new Ext.form.TextField({
        		 	maxLength: 2,
        			allowBlank: false
        		 })
		}, {
			header : "名称",
			width : 160,
			dataIndex : "name",
			sortable : false,
			editor: new Ext.form.TextField({
        		 	maxLength: 40,
        			allowBlank: false
        		 })
		}, {
			header : "序号",
			width : 160,
			dataIndex : "seq",
			sortable : false,
			editor: new Ext.form.TextField({
        		 	maxLength: 2,
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
					msg : '正在加载业务代码信息列表......'
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
		title:"代收付业务代码信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});