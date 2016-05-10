var dsfMchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_MCHT_NO_FORADD',function(ret){
		dsfMchtNoStore.loadData(Ext.decode(ret));
	});
var getFileWayStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_GET_FILE_WAY',function(ret){
		getFileWayStore.loadData(Ext.decode(ret));
	});
	
var commWayStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_COMM_WAY',function(ret){
		commWayStore.loadData(Ext.decode(ret));
	});
var checkTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_CHECK_TYPE',function(ret){
		checkTypeStore.loadData(Ext.decode(ret));
	});
var rspSendFlagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_RSP_SEND_FLAG',function(ret){
		rspSendFlagStore.loadData(Ext.decode(ret));
	});
var tbarArr = new Array();
var btn4 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		mchtFileTransInfoWin.show();
		mchtFileTransInfoWin.center();
	}
}
var btn5 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
			if(leftGrid.getSelectionModel().hasSelection()) {
				var rec = leftGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除文件传输配置信息吗？',leftGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T90101Action.asp?method=delete',
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
								txnId: '90101',
								subTxnId: '03'
							}
						});
					}
				});
			}
		
	}
}
var btn6 = {
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
				mchtNo : record.get("mchtNo"),
				getFileWay : record.get("getFileWay"),
				commWay : record.get("commWay"),
				checkType : record.get("checkType"),
				userName : record.get("userName"),
				passwd : record.get("passwd"),
				ip : record.get("ip"),
				port : record.get("port"),
				filePath : record.get("filePath"),
				keyPath : record.get("keyPath"),
				keyIdx : record.get("keyIdx"),
				keyVal : record.get("keyVal"),
				checkVal : record.get("checkVal"),
				rspSendFlag : record.get("rspSendFlag"),
				rspSendTime : record.get("rspSendTime"),
				dzSendTime : record.get("dzSendTime")
			};
			array.push(data);
		}
		Ext.Ajax.requestNeedAuthorise({
					url : 'T90101Action.asp?method=update',
					method : 'post',
					timeout : '10000',
					params : {
						mchtFileTransInfoList : Ext.encode(array),
						txnId : '90101',
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
						leftGrid.getTopToolbar().items.items[4].disable();
						leftGrid.getStore().reload();
						hideProcessMsg();
					}
				});
	}
}
tbarArr.push(btn4);
tbarArr.push("-");
tbarArr.push(btn5);
tbarArr.push("-");
tbarArr.push(btn6);
var mchtFileTransInfoForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 600,
			defaultType : 'textfield',
			labelWidth : 160,
			waitMsgTarget : true,
			items : [{
						fieldLabel : "商户号*",
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
			        	store: dsfMchtNoStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'mchtNo',
						editable:true,
						allowBlank : false,
						width : 150
					}, {
						fieldLabel : "文件获取方式",
						id : "getFileWayId",
						name : "getFileWayNm",
						xtype: 'basecomboselect',
			        	store: getFileWayStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'getFileWay',
						editable:true,
						width : 150
					}, {
						fieldLabel : "通讯方式",
						id : "commWayId",
						name : "commWayNm",
						xtype: 'basecomboselect',
			        	store: commWayStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'commWay',
						editable:true,
						width : 150
					}, {
						fieldLabel : "文件验证方式",
						id : "checkTypeId",
						name : "checkTypeNm",
						xtype: 'basecomboselect',
			        	store: checkTypeStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'checkType',
						editable:true,
						width : 150
					}, {
						fieldLabel : "用户名",
						id : "userName",
						name : "userName",
						allowBlank : true,
						vtype: 'isOverMax',
		        		maxLength: 120,
		        		allowBlank: true,
						width : 150
					}, {
						fieldLabel : "密码",
						id : "passwd",
						name : "passwd",
						allowBlank : true,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "IP",
						id : "ip",
						name : "ip",
						maxLength: 120,
						regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "端口",
						id : "port",
						name : "port",
						maxLength: 6,
						regex:/^\d+$/,
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "文件存放路径",
						id : "filePath",
						name : "filePath",
						maxLength: 128,
						vtype: 'isOverMax',
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "密钥存放路径",
						id : "keyPath",
						name : "keyPath",
						vtype: 'isOverMax',
						maxLength: 128,
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "索引",
						id : "keyIdx",
						name : "keyIdx",
						maxLength: 10,
						vtype: 'isOverMax',
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "密钥",
						id : "keyVal",
						name : "keyVal",
						maxLength: 32,
						vtype: 'isOverMax',
						allowBlank: true,
						width : 150
					}, {
						fieldLabel : "校验值",
						id : "checkVal",
						name : "checkVal",
						maxLength: 16,
						allowBlank: true,
						vtype: 'isOverMax',
						width : 150
					}, {
						fieldLabel : "回执发送方式",
						id : "rspSendFlagId",
						name : "rspSendFlagNm",
						xtype: 'basecomboselect',
			        	store: rspSendFlagStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'rspSendFlag',
						editable:true,
						width : 150
					}, {
						fieldLabel : "回执发送时间",
						id : "rspSendTime",
						name : "rspSendTime",
						xtype:'numberfield',
						maxValue : 8,
						minValue : 0,
						allowBlank : true,
						maxLength : 2,
						width : 150
					}, {
						fieldLabel : "对账文件发送时间",
						id : "dzSendTime",
						name : "dzSendTime",
//						xtype:'timefield',
						regex:/^([0-1]{1}[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
						allowBlank : true,
						maxLength : 6,
						width : 150
					}]
		});

var mchtFileTransInfoWin = new Ext.Window({
	title : "商户添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 400,
	autoHeight : true,
	layout : 'fit',
	items : [mchtFileTransInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (mchtFileTransInfoForm.getForm().isValid()) {
				mchtFileTransInfoForm.getForm().submitNeedAuthorise({
							url : 'T90101Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, mchtFileTransInfoForm);
								// 重置表单
								mchtFileTransInfoForm.getForm().reset();
								// 重新加载参数列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, mchtFileTransInfoForm);
							},
							params : {
								txnId : '90101',
								subTxnId : '01'
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			mchtFileTransInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			mchtFileTransInfoWin.hide();
		}
	}]
});

var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=mchtFileTransInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "getFileWay",
								mapping : "getFileWay"
							}, {
								name : "commWay",
								mapping : "commWay"
							}, {
								name : "checkType",
								mapping : "checkType"
							}, {
								name : "userName",
								mapping : "userName"
							}, {
								name : "passwd",
								mapping : "passwd"
							}, {
								name : "ip",
								mapping : "ip"
							}, {
								name : "port",
								mapping : "port"
							}, {
								name : "filePath",
								mapping : "filePath"
							}, {
								name : "keyPath",
								mapping : "keyPath"
							}, {
								name : "keyIdx",
								mapping : "keyIdx"
							}, {
								name : "keyVal",
								mapping : "keyVal"
							}, {
								name : "checkVal",
								mapping : "checkVal"
							}, {
								name : "rspSendFlag",
								mapping : "rspSendFlag"
							}, {
								name : "rspSendTime",
								mapping : "rspSendTime"
							}, {
								name : "dzSendTime",
								mapping : "dzSendTime"
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
			header : "文件获取方式",
			width : 150,
			dataIndex : "getFileWay",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: getFileWayStore,
					id: 'getFileWayCMId',
					hiddenName: 'getFileWayHn',
					editable:true,
					width: 150
		       },
		    renderer:function (data){
		    return changeComboShow(data,getFileWayStore)
		    }
		}, {
			header : "通讯方式",
			width : 60,
			dataIndex : "commWay",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: commWayStore,
					id: 'commWayCMId',
					hiddenName: 'commWayHn',
					width: 160
		       },
		    renderer:function (data){
		    return changeComboShow(data,commWayStore)
		    }
		}, {
			header : "文件验证方式",
			width : 80,
			dataIndex : "checkType",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: checkTypeStore,
					id: 'checkTypeCMId',
					hiddenName: 'checkTypeHn',
					width: 160
		       },
		    renderer:function (data){
		    return changeComboShow(data,checkTypeStore)
		    }
		}, {
			header : "用户名",
			width : 80,
			dataIndex : "userName",
			sortable : false,
			editor:new Ext.form.TextField({
        		 	maxLength: 120,
        		 	vtype: 'isOverMax',
        			allowBlank: true,
        			regex:/^[\u4e00-\u9fa50-9a-zA-Z]+$/
        		 })
		}, {
			header : "密码",
			width : 80,
			dataIndex : "passwd",
			sortable : false,
			editor:new Ext.form.TextField({
				maxLength: 120,
				vtype: 'isOverMax',
        		allowBlank: true
//        		inputType: 'password'
			})
		}, {
			header : "IP",
			width : 110,
			dataIndex : "ip",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 40,
					regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/,
					allowBlank: true
			})
		}, {
			header : "端口",
			width : 50,
			dataIndex : "port",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 6,
					regex:/^\d+$/,
					allowBlank: true
			})
		}, {
			header : "文件存放路径",
			width : 160,
			dataIndex : "filePath",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 128,
					vtype: 'isOverMax',
					allowBlank: true
			})
		}, {
			header : "密钥存放路径",
			width : 160,
			dataIndex : "keyPath",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 128,
					vtype: 'isOverMax',
					allowBlank: true
			})
		}, {
			header : "索引",
			width : 60,
			dataIndex : "keyIdx",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 10,
					vtype: 'isOverMax',
					allowBlank: true
			})
		}, {
			header : "密钥",
			width : 160,
			dataIndex : "keyVal",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 32,
					vtype: 'isOverMax',
					allowBlank: true
			})
		}, {
			header : "校验值",
			width : 130,
			dataIndex : "checkVal",
			sortable : false,
			editor:new Ext.form.TextField({
					maxLength: 16,
					vtype: 'isOverMax',
					allowBlank: true
			})
		}, {
			header : "回执发送方式",
			width : 90,
			dataIndex : "rspSendFlag",
			sortable : false,
			editor: {
					xtype: 'basecomboselect',
			        store: rspSendFlagStore,
					id: 'rspSendFlagCMId',
					hiddenName: 'rspSendFlagHn',
					width: 160
		       },
		    renderer:function (data){
		    return changeComboShow(data,rspSendFlagStore)
		    }
		}, {
			header : "回执发送时间",
			width : 90,
			dataIndex : "rspSendTime",
			sortable : false,
			editor:new Ext.form.NumberField({
					maxValue : 8,
					minValue : 0,
					allowBlank : true,
					maxLength : 1
			})
		}, {
			header : "对账文件发送时间",
			width : 120,
			dataIndex : "dzSendTime",
			sortable : false,
			editor:new Ext.form.TextField({
					regex:/^([0-1]{1}[0-9]|2[0-3])([0-5][0-9])([0-5][0-9])$/,
					maxLength: 16,
					allowBlank: true
			})
		},
//		{
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
					msg : '正在加载代收付商户文件传输信息列表......'
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
var leftPanel = new Ext.Panel({
		region: 'center',
		title:"代收付商户文件传输信息维护",
		frame: true,
		layout: 'border',
		tbar:[tbarArr],
		items:[leftGrid]
});
leftGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last)).frame();
		leftPanel.getTopToolbar().items.items[2].enable();
		
		}
	});
var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});