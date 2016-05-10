var statStore = new Ext.data.JsonStore({
			fields : ['valueField', 'displayField'],
			root : 'data',
			id : 'valueField'
		});
SelectOptionsDWR.getComboData('DSF_MCHTINFO_STAT', function(ret) {
			statStore.loadData(Ext.decode(ret));
		});
// var flagStore = new Ext.data.JsonStore({
// fields: ['valueField','displayField'],
// root: 'data',
// id: 'valueField'
// });
// SelectOptionsDWR.getComboData('DSF_MCHTINFO_FLAG',function(ret){
// flagStore.loadData(Ext.decode(ret));
// });
var ywComboList = new Ext.data.SimpleStore({
			fields : ["valueField", "displayField"],
			data : [["0", "否"], ["1", "是"]]
		});
var rislLvlStore = new Ext.data.JsonStore({
			fields : ['valueField', 'displayField'],
			root : 'data',
			id : 'valueField'
		});
SelectOptionsDWR.getComboData('DSF_MCHTINFO_RISLLVL', function(ret) {
			rislLvlStore.loadData(Ext.decode(ret));
		});
var mchtLvlStore = new Ext.data.JsonStore({
			fields : ['valueField', 'displayField'],
			root : 'data',
			id : 'valueField'
		});
SelectOptionsDWR.getComboData('DSF_MCHTINFO_MCHTLVL', function(ret) {
			mchtLvlStore.loadData(Ext.decode(ret));
		});
var tbarArr = new Array();
var btn1 = {
	text : '添加',
	iconCls : 'add',
	handler : function() {
		mchtInfoWin.show();
		mchtInfoWin.center();
	}
}
var btn2 = {
	text : '删除',
	iconCls : 'delete',
	handler : function() {
		if (leftGrid.getSelectionModel().hasSelection()) {
			var rec = leftGrid.getSelectionModel().getSelected();
			showConfirm('确定要删除该地区码信息吗？', leftGrid, function(bt) {
				// 如果点击了提示的确定按钮
				if (bt == "yes") {
					Ext.Ajax.requestNeedAuthorise({
								url : 'T90301Action.asp?method=delete',
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, leftGrid);
										leftGrid.getStore().reload();
										leftPanel.getTopToolbar().items.items[2]
												.disable();
									} else {
										showErrorMsg(rspObj.msg, leftGrid);
									}
								},
								params : {
									mchtNo : rec.get('mchtNo'),
									txnId : '90301',
									subTxnId : '03'
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
				mchtName : record.get("mchtName"),
				stat : record.get("stat"),
				mchtComCode : record.get("mchtComCode"),
				opnBankNo : record.get("opnBankNo"),
				acctNo : record.get("acctNo"),
				name : record.get("name"),
				flag : record.get("ywflag0") + record.get("ywflag1")
						+ record.get("ywflag2") + record.get("ywflag3")
						+ record.get("ywflag4"),
				rislLvl : record.get("rislLvl"),
				mchtLvl : record.get("mchtLvl"),
				mchtGrp : record.get("mchtGrp"),
				areaNo : record.get("areaNo"),
				addr : record.get("addr"),
				zipCode : record.get("zipCode"),
				tel : record.get("tel"),
				mail : record.get("mail")
			};
			array.push(data);
		}
		Ext.Ajax.requestNeedAuthorise({
					url : 'T90301Action.asp?method=update',
					method : 'post',
					timeout : '10000',
					params : {
						mchtList : Ext.encode(array),
						txnId : '90301',
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

var mchtInfoForm = new Ext.form.FormPanel({
	frame : true,
	autoHeight : true,
	width : 600,
	// defaultType : 'textfield',
	labelWidth : 115,
	layout : 'form',
	waitMsgTarget : true,
	items : [{
				id : 'baseInfo',
				xtype : 'fieldset',
				title : '基本信息',
				layout : 'column',
				// collapsible : true,
				labelWidth : 100,
				width : 600,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [{
							layout : "column",
							items : [{
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户号",
													xtype : 'textfield',
													id : "mchtNo",
													name : "mchtNo",
													allowBlank : false,
													maxLength : 15,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户名称",
													xtype : 'textfield',
													id : "mchtName",
													name : "mchtName",
													allowBlank : true,
													maxLength : 120,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "状态",
													id : "statId",
													name : "statNm",
													xtype : 'basecomboselect',
													store : statStore,
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'stat',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户企业代码",
													xtype : 'textfield',
													id : "mchtComCode",
													name : "mchtComCode",
													allowBlank : true,
													maxLength : 14,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "开户行行号",
													xtype : 'textfield',
													id : "opnBankNo",
													name : "opnBankNo",
													allowBlank : true,
													regex: /^[0-9]+$/,
													regexText: '该输入框只能输入数字0-9',
													maxLength : 14,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "结算账号",
													xtype : 'textfield',
													id : "acctNo",
													name : "acctNo",
													allowBlank : true,
													maxLength : 32,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "户名",
													xtype : 'textfield',
													id : "name",
													name : "name",
													allowBlank : true,
													maxLength : 120,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户风险级别",
													id : "rislLvlId",
													name : "rislLvlNm",
													xtype : 'basecomboselect',
													store : rislLvlStore,
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'rislLvl',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户级别",
													id : "mchtLvlId",
													name : "mchtLvlNm",
													xtype : 'basecomboselect',
													store : mchtLvlStore,
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'mchtLvl',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "商户组别",
													xtype : 'textfield',
													id : "mchtGrp",
													name : "mchtGrp",
													allowBlank : true,
													maxLength : 4,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "地区码",
													xtype : 'textfield',
													id : "areaNo",
													name : "areaNo",
													allowBlank : true,
													maxLength : 4,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "地址",
													xtype : 'textfield',
													id : "addr",
													name : "addr",
													allowBlank : true,
													maxLength : 120,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "邮编",
													xtype : 'textfield',
													id : "zipCode",
													name : "zipCode",
													allowBlank : true,
													maxLength : 20,
													regex: /^[0-9]{6}$/,
													regexText: '邮政编码必须是6位数字',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "联系电话",
													xtype : 'textfield',
													id : "tel",
													name : "tel",
													allowBlank : true,
													maxLength : 20,
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "邮箱",
													xtype : 'textfield',
													id : "mail",
													name : "mail",
													allowBlank : true,
													maxLength : 120,
													vtype: 'email',
													width : 150
												}]
									}]
						}]
			}, {
				id : 'ywInfo',
				xtype : 'fieldset',
				title : '开通业务信息',
				layout : 'column',
				// collapsible : true,
				labelWidth : 115,
				width : 600,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [{
							layout : "column",
							items : [{
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "开通代付业务",
													id : "flag0Id",
													name : "flag0Nm",
													xtype : 'combo',
													store : ywComboList,
													value:'0',
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'ywflag0',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "开通代收业务",
													id : "flag1Id",
													name : "flag1Nm",
													xtype : 'combo',
													store : ywComboList,
													value:'0',
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'ywflag1',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "需要替换户名",
													id : "flag2Id",
													name : "flag2Nm",
													xtype : 'combo',
													store : ywComboList,
													value:'0',
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'ywflag2',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "开通单笔代收付业务",
													id : "flag3Id",
													name : "flag3Nm",
													xtype : 'combo',
													store : ywComboList,
													value:'0',
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'ywflag3',
													width : 150
												}]
									}, {
										columnWidth : .5,
										layout : "form",
										items : [{
													fieldLabel : "开通批量代收付业务",
													id : "flag4Id",
													name : "flag4Nm",
													xtype : 'combo',
													store : ywComboList,
													value:'0',
													valueField : 'valueField',// 值
													displayField : 'displayField',// 显示文本
													hiddenName : 'ywflag4',
													width : 150
												}]
									}]
						}]
			}
/*	// {
	// fieldLabel : "录入人员",
	// id : "entryTlr",
	// name : "entryTlr",
	// allowBlank : false,
	// maxLength : 11,
	// width : 150
	// }, {
	// fieldLabel : "录入日期",
	// id : "entryDate",
	// name : "entryDate",
	// allowBlank : false,
	// maxLength : 8,
	// width : 150
	// }, {
	// fieldLabel : "初审人员",
	// id : "preAudTlr",
	// name : "preAudTlr",
	// allowBlank : false,
	// maxLength : 11,
	// width : 150
	// }, {
	// fieldLabel : "复核人员",
	// id : "confirmTlr",
	// name : "confirmTlr",
	// allowBlank : false,
	// maxLength : 11,
	// width : 150
	// }, {
	// fieldLabel : "启用日期",
	// id : "enableDate",
	// name : "enableDate",
	// allowBlank : false,
	// maxLength : 8,
	// width : 150
	// }, {
	// fieldLabel : "联系人",
	// id : "contract",
	// name : "contract",
	// allowBlank : false,
	// maxLength : 120,
	// width : 150
	// }, {
	// fieldLabel : "FLAG1",
	// id : "flag1",
	// name : "flag1",
	// allowBlank : false,
	// maxLength : 20,
	// width : 150
	// }, {
	// fieldLabel : "FLAG2",
	// id : "flag2",
	// name : "flag2",
	// allowBlank : false,
	// maxLength : 20,
	// width : 150
	// }, {
	// fieldLabel : "FLAG3",
	// id : "flag3",
	// name : "flag3",
	// allowBlank : false,
	// maxLength : 20,
	// width : 150
	// }, {
	// fieldLabel : "保留域1",
	// id : "misc1",
	// name : "misc1",
	// allowBlank : false,
	// maxLength : 128,
	// width : 150
	// }, {
	// fieldLabel : "保留域2",
	// id : "misc2",
	// name : "misc2",
	// allowBlank : false,
	// maxLength : 128,
	// width : 150
	// }, {
	// fieldLabel : "保留域3",
	// id : "misc3",
	// name : "misc3",
	// allowBlank : false,
	// maxLength : 128,
	// width : 150
	// }, {
	// fieldLabel : "最近更新柜员",
	// id : "lstUpdTlr",
	// name : "lstUpdTlr",
	// allowBlank : false,
	// maxLength : 11,
	// width : 150
	// }, {
	// fieldLabel : "最近更新时间",
	// id : "lstUpdTime",
	// name : "lstUpdTime",
	// allowBlank : false,
	// maxLength : 14,
	// width : 150
	// }, {
	// fieldLabel : "创建时间",
	// id : "createTime",
	// name : "createTime",
	// allowBlank : false,
	// maxLength : 14,
	// width : 150
	// }
*/	]
});
var mchtInfoWin = new Ext.Window({
	title : "商户信息添加",
	initHidden : true,
	header : true,
	frame : true,
	closable : false,
	modal : true,
	width : 650,
	autoHeight : true,
	layout : 'fit',
	items : [mchtInfoForm],
	buttonAlign : 'center',
	closeAction : 'hide',
	iconCls : 'logo',
	resizable : false,
	buttons : [{
		text : '确定',
		handler : function() {
			if (mchtInfoForm.getForm().isValid()) {
				var flagValue = mchtInfoForm.getForm().findField("flag0Id").getValue()+''+mchtInfoForm.getForm().findField("flag1Id").getValue()+''+mchtInfoForm.getForm().findField("flag2Id").getValue()+''+mchtInfoForm.getForm().findField("flag3Id").getValue()+''+mchtInfoForm.getForm().findField("flag4Id").getValue();
				mchtInfoForm.getForm().submitNeedAuthorise({
							url : 'T90301Action.asp?method=add',
							waitMsg : '正在提交，请稍后......',
							success : function(form, action) {
								showSuccessMsg(action.result.msg, mchtInfoForm);
								// 重置表单
								mchtInfoForm.getForm().reset();
								// 重新加载商户信息列表
								leftGrid.getStore().reload();
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, mchtInfoForm);
							},
							params : {
								txnId : '90301',
								subTxnId : '01',
								flag:flagValue
							}
						});
			}
		}
	}, {
		text : '重置',
		handler : function() {
			mchtInfoForm.getForm().reset();
		}
	}, {
		text : '关闭',
		handler : function() {
			mchtInfoWin.hide();
		}
	}]
});
var leftGridStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
						url : 'gridPanelStoreAction.asp?storeId=mchtInfo'
					}),
			reader : new Ext.data.JsonReader({
						root : "data",
						totalProperty : "totalCount"
					}, [{
								name : "mchtNo",
								mapping : "mchtNo"
							}, {
								name : "mchtName",
								mapping : "mchtName"
							}, {
								name : "stat",
								mapping : "stat"
							}, {
								name : "mchtComCode",
								mapping : "mchtComCode"
							}, {
								name : "opnBankNo",
								mapping : "opnBankNo"
							}, {
								name : "acctNo",
								mapping : "acctNo"
							}, {
								name : "name",
								mapping : "name"
							}, {
								name : "ywflag0",
								mapping : "ywflag0"
							}, {
								name : "ywflag1",
								mapping : "ywflag1"
							}, {
								name : "ywflag2",
								mapping : "ywflag2"
							}, {
								name : "ywflag3",
								mapping : "ywflag3"
							}, {
								name : "ywflag4",
								mapping : "ywflag4"
							}, {
								name : "rislLvl",
								mapping : "rislLvl"
							}, {
								name : "mchtLvl",
								mapping : "mchtLvl"
							}, {
								name : "mchtGrp",
								mapping : "mchtGrp"
							}, {
								name : "areaNo",
								mapping : "areaNo"
							}, {
								name : "addr",
								mapping : "addr"
							}, {
								name : "zipCode",
								mapping : "zipCode"
							}, {
								name : "tel",
								mapping : "tel"
							}, {
								name : "mail",
								mapping : "mail"
							}, {
								name : "entryTlr",
								mapping : "entryTlr"
							}, {
								name : "entryDate",
								mapping : "entryDate"
							}, {
								name : "preAudTlr",
								mapping : "preAudTlr"
							}, {
								name : "confirmTlr",
								mapping : "confirmTlr"
							}, {
								name : "enableDate",
								mapping : "enableDate"
							}, {
								name : "contract",
								mapping : "contract"
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
			header : "商户名称",
			width : 80,
			dataIndex : "mchtName",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 40,
						allowBlank : true
					})
		}, {
			header : "状态",
			width : 160,
			dataIndex : "stat",
			sortable : false,
			editor : {
				xtype : 'basecomboselect',
				store : statStore,
				id : 'idstatTp',
				hiddenName : 'statTp',
				width : 160
			},
			renderer : function(data) {
				if (null == data)
					return '';
				var record = statStore.getById(data);
				if (null != record) {
					return record.data.displayField;
				} else {
					return data;
				}
			}
		}, {
			header : "商户企业代码",
			width : 100,
			dataIndex : "mchtComCode",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 14,
						allowBlank : true
					})
		}, {
			header : "开户行行号",
			width : 100,
			dataIndex : "opnBankNo",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 14,
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						allowBlank : true
					})
		}, {
			header : "结算账号",
			width : 100,
			dataIndex : "acctNo",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 32,
						allowBlank : true
					})
		}, {
			header : "户名",
			width : 160,
			dataIndex : "name",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 40,
						allowBlank : true
					})
		}, {
			header : "开通代付业务",
			width : 140,
			dataIndex : "ywflag0",
			sortable : false,
			editor : {
				xtype : 'combo',
				store : ywComboList,
				id : 'idflag0Tp',
				name : 'ywflag0',
				// hiddenName: 'flagTp',
				width : 140
			},
			renderer : function(data) {
				if (null == data)
					return '';
				if (data == '1') {
					return '是';
				} else if (data == '0') {
					return '否';
				} else {
					return data;
				}
			}
		}, {
			header : "开通代收业务",
			width : 140,
			dataIndex : "ywflag1",
			sortable : false,
			editor : {
				xtype : 'combo',
				store : ywComboList,
				id : 'idflag1Tp',
				name : 'ywflag1',
				// hiddenName: 'flagTp',
				width : 140
			},
			renderer : function(data) {
				if (null == data)
					return '';
				if (data == '1') {
					return '是';
				} else if (data == '0') {
					return '否';
				} else {
					return data;
				}
			}
		}, {
			header : "需要替换户名",
			width : 140,
			dataIndex : "ywflag2",
			sortable : false,
			editor : {
				xtype : 'combo',
				store : ywComboList,
				id : 'idflag2Tp',
				name : 'ywflag2',
				// hiddenName: 'flagTp',
				width : 140
			},
			renderer : function(data) {
				if (null == data)
					return '';
				if (data == '1') {
					return '是';
				} else if (data == '0') {
					return '否';
				} else {
					return data;
				}
			}
		}, {
			header : "开通单笔代收付业务",
			width : 140,
			dataIndex : "ywflag3",
			sortable : false,
			editor : {
				xtype : 'combo',
				store : ywComboList,
				id : 'idflag3Tp',
				name : 'ywflag3',
				// hiddenName: 'flagTp',
				width : 140
			},
			renderer : function(data) {
				if (null == data)
					return '';
				if (data == '1') {
					return '是';
				} else if (data == '0') {
					return '否';
				} else {
					return data;
				}
			}
		}, {
			header : "开通批量代收付业务",
			width : 140,
			dataIndex : "ywflag4",
			sortable : false,
			editor : {
				xtype : 'combo',
				store : ywComboList,
				id : 'idflag4Tp',
				name : 'ywflag4',
				// hiddenName: 'flagTp',
				width : 140
			},
			renderer : function(data) {
				if (null == data)
					return '';
				if (data == '1') {
					return '是';
				} else if (data == '0') {
					return '否';
				} else {
					return data;
				}
			}
		}, {
			header : "商户风险级别",
			width : 120,
			dataIndex : "rislLvl",
			sortable : false,
			editor : {
				xtype : 'basecomboselect',
				store : rislLvlStore,
				id : 'idrislLvlTp',
				hiddenName : 'rislLvlTp',
				width : 160
			},
			renderer : function(data) {
				if (null == data)
					return '';
				var record = rislLvlStore.getById(data);
				if (null != record) {
					return record.data.displayField;
				} else {
					return data;
				}
			}
		}, {
			header : "商户级别",
			width : 80,
			dataIndex : "mchtLvl",
			sortable : false,
			editor : {
				xtype : 'basecomboselect',
				store : mchtLvlStore,
				id : 'idmchtLvlTp',
				hiddenName : 'mchtLvlTp',
				width : 160
			},
			renderer : function(data) {
				if (null == data)
					return '';
				var record = mchtLvlStore.getById(data);
				if (null != record) {
					return record.data.displayField;
				} else {
					return data;
				}
			}
		}, {
			header : "商户组别",
			width : 60,
			dataIndex : "mchtGrp",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 4,
						allowBlank : true
					})
		}, {
			header : "地区码",
			width : 60,
			dataIndex : "areaNo",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 4,
						allowBlank : true
					})
		}, {
			header : "地址",
			width : 120,
			dataIndex : "addr",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 40,
						allowBlank : true
					})
		}, {
			header : "邮编",
			width : 80,
			dataIndex : "zipCode",
			regex: /^[0-9]{6}$/,
			regexText: '邮政编码必须是6位数字',
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 20,
						allowBlank : true
					})
		}, {
			header : "联系电话",
			width : 160,
			dataIndex : "tel",
			sortable : false,
			editor : new Ext.form.TextField({
						maxLength : 20,
						allowBlank : true
					})
		}, {
			header : "邮箱",
			width : 160,
			dataIndex : "mail",
			sortable : false,
			vtype: 'email',
			editor : new Ext.form.TextField({
						maxLength : 100,
						allowBlank : true
					})
		}, {
			header : "录入人员",
			width : 80,
			dataIndex : "entryTlr",
			sortable : false
		}, {
			header : "录入日期",
			width : 120,
			dataIndex : "entryDate",
			sortable : false
		}, {
			header : "初审人员",
			width : 80,
			dataIndex : "preAudTlr",
			sortable : false
		}, {
			header : "复核人员",
			width : 80,
			dataIndex : "confirmTlr",
			sortable : false
		}, {
			header : "启用日期",
			width : 120,
			dataIndex : "enableDate",
			sortable : false
		}, {
			header : "联系人",
			width : 80,
			dataIndex : "contract",
			sortable : false
		},
		// {
		// header : "FLAG1",
		// width : 60,
		// dataIndex : "flag1",
		// sortable : false
		// }, {
		// header : "FLAG2",
		// width : 60,
		// dataIndex : "flag2",
		// sortable : false
		// }, {
		// header : "FLAG3",
		// width : 60,
		// dataIndex : "flag3",
		// sortable : false
		// }, {
		// header : "保留域1",
		// width : 60,
		// dataIndex : "misc1",
		// sortable : false
		// }, {
		// header : "保留域2",
		// width : 60,
		// dataIndex : "misc2",
		// sortable : false
		// }, {
		// header : "保留域3",
		// width : 60,
		// dataIndex : "misc3",
		// sortable : false
		// },
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
			bbar : new Ext.PagingToolbar({
						store : leftGridStore,
						pageSize : System[QUERY_RECORD_COUNT],
						displayInfo : true,
						displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
						emptyMsg : '没有找到符合条件的记录'
					})
		});
leftGridStore.on("beforeload", function() {
			Ext.apply(this.baseParams, {
						start : 0
					})
		});
// 每次在列表信息加载前都将保存按钮屏蔽
leftGrid.getStore().on('beforeload', function() {
			leftPanel.getTopToolbar().items.items[2].disable();
			leftPanel.getTopToolbar().items.items[4].disable();
			leftGrid.getStore().rejectChanges();
		});

leftGrid.on({
			// 在编辑单元格后使保存按钮可用
			'afteredit' : function() {
				leftPanel.getTopToolbar().items.items[4].enable();
			}
		});
leftGrid.getSelectionModel().on({
	'rowselect' : function() {
		//行高亮
		Ext.get(leftGrid.getView().getRow(leftGrid.getSelectionModel().last))
				.frame();
		leftPanel.getTopToolbar().items.items[2].enable();
	}
});
var leftPanel = new Ext.Panel({
			region : 'center',
			title : "代收付商户信息维护",
			frame : true,
			layout : 'border',
			tbar : [tbarArr],
			items : [leftGrid]
		});

var mainUI = new Ext.Viewport({
			layout : "border",
			items : [leftPanel]
		});