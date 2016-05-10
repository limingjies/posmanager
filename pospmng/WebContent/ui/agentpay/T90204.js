var paramTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_PARAM_TYPE',function(ret){
		paramTypeStore.loadData(Ext.decode(ret));
	});
	var paramProStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_PARAM_PRO',function(ret){
		paramProStore.loadData(Ext.decode(ret));
	});
var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		paramInfoWin.show();
		paramInfoWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该参数信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90204Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,leftGrid);
									leftGrid.getStore().reload();
									leftGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,leftGrid);
								}
							},
							params: { 
								paramMark: rec.get('paramMark'),
								paramCode: rec.get('paramCode'),
								txnId: '90204',
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
		if (modifiedRecords.length == 0) {
			return;
		}
		var array = new Array();
		for (var index = 0; index < modifiedRecords.length; index++) {
			var record = modifiedRecords[index];
			var data = {
				paramMark : record.get("paramMark"),
				paramCode : record.get("paramCode"),
				paramType : record.get("paramType"),
				paramPro : record.get("paramPro"),
				paramValue : record.get("paramValue"),
				descr : record.get("descr")

			};
			array.push(data);
		}
		Ext.Ajax.requestNeedAuthorise({
					url : 'T90204Action.asp?method=update',
					method : 'post',
					timeout : '10000',
					params : {
						paramList : Ext.encode(array),
						txnId : '90204',
						subTxnId : '02'
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg, leftGrid);
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

var paramInfoForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 600,
			defaultType : 'textfield',
			labelWidth : 160,
			waitMsgTarget : true,
			items : [{
						fieldLabel : "参数标识",
						id : "paramMark",
						name : "paramMark",
						allowBlank : false,
						maxLength : 15,
						width : 150
					}, {
						fieldLabel : "参数代码",
						id : "paramCode",
						name : "paramCode",
						allowBlank : false,
						maxLength : 2,
						width : 150
					}, {
						fieldLabel : "参数类型",
						id : "paramTypeId",
						name : "paramTypeNm",
						xtype: 'basecomboselect',
			        	store: paramTypeStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'paramType',
						width : 150
					}, {
						fieldLabel : "参数属性",
						id : "paramProId",
						name : "paramProNm",
						xtype: 'basecomboselect',
			        	store: paramProStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'paramPro',
						width : 150
					}, {
						fieldLabel : "参数值",
						id : "paramValue",
						name : "paramValue",
						maxLength : 100,
						editor : new Ext.form.TextField({
									allowBlank : true
								}),
						vtype: 'isOverMax',
						width : 150
					}, {
						fieldLabel : "参数描述",
						id : "descr",
						name : "descr",
						maxLength : 100,
						editor : new Ext.form.TextField({
									allowBlank : true
								}),
						vtype: 'isOverMax',
						width : 150
					}]
		});
var paramInfoWin = new Ext.Window({
	title : "地区码添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [paramInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (paramInfoForm.getForm().isValid()) {
				paramInfoForm.getForm().submitNeedAuthorise({
							url : 'T90204Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, paramInfoForm);
								// 重置表单
								paramInfoForm.getForm().reset();
								// 重新加载参数列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, paramInfoForm);
							},
							params : {
								txnId : '90204',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			paramInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			paramInfoWin.hide();
		}
	}]
});

var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=paramInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "paramMark",
								mapping : "paramMark"
							}, {
								name : "paramCode",
								mapping : "paramCode"
							}, {
								name : "paramType",
								mapping : "paramType"
							}, {
								name : "paramPro",
								mapping : "paramPro"
							}, {
								name : "paramValue",
								mapping : "paramValue"
							}, {
								name : "descr",
								mapping : "descr"
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
			header : "参数标识",
			width : 100,
			dataIndex : "paramMark",
			sortable : false
		}, {
			header : "参数代码",
			width : 60,
			dataIndex : "paramCode",
			sortable : false
		}, {
			header : "参数类型",
			width : 80,
			dataIndex : "paramType",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: paramTypeStore,
					id: 'idparamTp',
					hiddenName: 'paramTp',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = paramTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return data;
		    	}
		    }
		}, {
			header : "参数属性",
			width : 100,
			dataIndex : "paramPro",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: paramProStore,
					id: 'idparamProTp',
					hiddenName: 'paramProTp',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = paramProStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return data;
		    	}
		    }
		}, {
			header : "参数值",
			width : 160,
			dataIndex : "paramValue",
			maxLength : 100,
			editor : new Ext.form.TextField({
						vtype : 'isOverMax',
						maxLength : 100,
						allowBlank : true
					}),
			sortable : false
		}, {
			header : "参数描述",
			width : 180,
			dataIndex : "descr",
			maxLength : 100,
			editor : new Ext.form.TextField({
						vtype : 'isOverMax',
						maxLength : 100,
						allowBlank : true
					}),
			sortable : false

		}, {
			header : "最近更新操作员",
			width : 60,
			dataIndex : "lstUpdTlr",
			sortable : false
		}, {
			header : "最近更新时间",
			width : 110,
			dataIndex : "lstUpdTime",
			sortable : false
		}, {
			header : "创建时间",
			width : 110,
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
		title:"代收付参数信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});

var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});