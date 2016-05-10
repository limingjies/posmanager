var dsfMchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('DSF_MCHT_NO',function(ret){
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
var btn1 = {
	text : '获取指定商户来帐文件',
	width : 85,
	iconCls : 'accept',
	handler : function() {
		var mchtNo = leftGrid.getSelectionModel().getSelected().data.mchtNo;
		var oprType = "1";
		GetDSFFiles.sendGetFileMsg(mchtNo, oprType,
				function(ret) {
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
}
var btn2 = {
	text : '获取全部商户来帐文件',
	width : 85,
	iconCls : 'accept',
	handler : function() {
		var mchtNo = "";
		var oprType = "0";
		GetDSFFiles.sendGetFileMsg(mchtNo, oprType,
				function(ret) {
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
}
var btn3 = {
	text : '下载源文件',
	iconCls : 'download',
	handler : function() {
		Ext.Ajax.request({
					url : 'T9010101Action.asp',
					params:{
						mchtNo:leftGrid.getSelectionModel().getSelected().data.mchtNo
					},
					success : function(rsp, opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if (rspObj.success) {
							window.location.href = Ext.contextPath
									+ '/ajaxDownLoad.asp?path=' + rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg, grid);
						}
					},
					failure : function() {
					}
				});
	}
}
tbarArr.push(btn1);
tbarArr.push("-");
tbarArr.push(btn2);
//tbarArr.push("-");
//tbarArr.push(btn3);
var mchtFileTransInfoForm = new Ext.form.FormPanel({
			frame : true,
			autoHeight : true,
			width : 600,
			defaultType : 'textfield',
			labelWidth : 160,
			waitMsgTarget : true,
			items : [{
						fieldLabel : "商户号",
						id : "mchtNoId",
						name : "mchtNoNm",
						xtype: 'basecomboselect',
			        	store: dsfMchtNoStore,
			        	valueField : 'valueField',// 值
						displayField : 'displayField',// 显示文本
						hiddenName: 'mchtNo',
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
						width : 150
					}, {
						fieldLabel : "用户名",
						id : "userName",
						name : "userName",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "密码",
						id : "passwd",
						name : "passwd",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "IP",
						id : "ip",
						name : "ip",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "端口",
						id : "port",
						name : "port",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "文件存放路径",
						id : "filePath",
						name : "filePath",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "密钥存放路径",
						id : "keyPath",
						name : "keyPath",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "索引",
						id : "keyIdx",
						name : "keyIdx",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "密钥",
						id : "keyVal",
						name : "keyVal",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "校验值",
						id : "checkVal",
						name : "checkVal",
						allowBlank : false,
						maxLength : 120,
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
						width : 150
					}, {
						fieldLabel : "回执发送时间",
						id : "rspSendTime",
						name : "rspSendTime",
						allowBlank : false,
						maxLength : 120,
						width : 150
					}, {
						fieldLabel : "对账文件发送时间",
						id : "dzSendTime",
						name : "dzSendTime",
						allowBlank : false,
						maxLength : 120,
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
			sortable : false,
		    renderer:function (data){
		    return changeComboShow(data,dsfMchtNoStore)
		    }
		}, {
			header : "文件获取方式",
			width : 140,
			dataIndex : "getFileWay",
			sortable : false,
		    renderer:function (data){
		    return changeComboShow(data,getFileWayStore)
		    }
		}, {
			header : "通讯方式",
			width : 75,
			dataIndex : "commWay",
			sortable : false,
		    renderer:function (data){
		    return changeComboShow(data,commWayStore)
		    }
		}, {
			header : "文件验证方式",
			width : 100,
			dataIndex : "checkType",
			sortable : false,
		    renderer:function (data){
		    return changeComboShow(data,checkTypeStore)
		    }
		}, {
			header : "用户名",
			width : 80,
			dataIndex : "userName",
			sortable : false
		}, {
			header : "密码",
			width : 80,
			dataIndex : "passwd",
			sortable : false
		}, {
			header : "IP",
			width : 100,
			dataIndex : "ip",
			sortable : false
		}, {
			header : "端口",
			width : 60,
			dataIndex : "port",
			sortable : false
		}, {
			header : "文件存放路径",
			width : 160,
			dataIndex : "filePath",
			sortable : false
		}, {
			header : "密钥存放路径",
			width : 160,
			dataIndex : "keyPath",
			sortable : false
		}, {
			header : "索引",
			width : 60,
			dataIndex : "keyIdx",
			sortable : false
		}, {
			header : "密钥",
			width : 60,
			dataIndex : "keyVal",
			sortable : false
		}, {
			header : "校验值",
			width : 60,
			dataIndex : "checkVal",
			sortable : false
		}, {
			header : "回执发送方式",
			width : 100,
			dataIndex : "rspSendFlag",
			sortable : false,
		    renderer:function (data){
		    return changeComboShow(data,rspSendFlagStore)
		    }
		}, {
			header : "回执发送时间",
			width : 80,
			dataIndex : "rspSendTime",
			sortable : false
		}, {
			header : "对账文件发送时间",
			width : 120,
			dataIndex : "dzSendTime",
			sortable : false
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
		leftPanel.getTopToolbar().items.items[0].disable();
//		leftPanel.getTopToolbar().items.items[4].disable();
		leftGrid.getStore().rejectChanges();
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
		leftPanel.getTopToolbar().items.items[0].enable();
		}
	});
var mainUI = new Ext.Viewport({
	layout : "border",
	items : [leftPanel]
});