Ext.onReady(function() {

	// 商户MCC数据集
	var routeMccStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('routeRuleMccDtl', function(ret) {
				routeMccStore.loadData(Ext.decode(ret));
			});
	
	// 文件上传窗口
	var uploadDialog ;
	
	function showUploadWin(menuArray) {
		uploadDialog = new UploadDialog({
			uploadUrl : 'T10604Action.asp?method=uploadFile',
			filePostName : 'xlsFile',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '10 MB',
			fileTypes : '*.xls',
			fileTypesDescription : '微软Excel文件(1997-2003)',
			title : '商户信息文件上传',
			scope : this,
			onEsc : function() {
				this.hide();
			},
			completeMethod: function() {
				recInfStore.reload();
			},
			postParams : {
				menuArray : Ext.encode(menuArray),
				txnId : '10604',
				subTxnId : '01'
			}
		});
		uploadDialog.show();
	}
	
	// 添加表单
	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
			xtype : 'fieldset',
			title : '规则基本信息',
			layout : 'column',
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeRuleCode',
					fieldLabel : '目的规则*',
					hiddenName : 'ruleCode',
					allowBlank : false,
					blankText : '目的规则不能为空',
					emptyText : '请选择目的规则',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250
						// value: '*'
					}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['0', '低'], ['1', '中低'],
										['2', '中'], ['3', '中高'],
										['4', '高']],
								reader : new Ext.data.ArrayReader()
							}),
					displayField : 'displayField',
					valueField : 'valueField',
					fieldLabel : '优先级别*',
					hiddenName : 'priority',
					allowBlank : false,
					blankText : '优先级别不能为空',
					emptyText : '请选择优先级别',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250
						// value: '*'
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				// width: 150,
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeBrhId',
					fieldLabel : '渠道编号*',
					hiddenName : 'brhId',
					allowBlank : false,
					blankText : '渠道编号不能为空',
					emptyText : '请选择渠道编号',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250,
					value : '*'
				}]
			}]
		}, {
			xtype : 'fieldset',
			title : '商户控制',
			layout : 'column',
			labelWidth : 70,
			collapsible : true,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'ROUTE_MCHNT_GRP_ALL',
					fieldLabel : '商户组别*',
					hiddenName : 'mchtGroup',
					allowBlank : false,
					blankText : '商户组别不能为空',
					emptyText : '请选择商户组别',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250,
					value : '*',
					listeners : {
						'select' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', addForm.getForm()
												.findField('mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));

										});
							} else {
								Ext.getCmp('mccIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							addForm.getForm().findField('mcc')
									.setValue('*')

						},
						'change' : function() {
							if (this.value != '*') {
								Ext.getCmp('mccIdId').show();
								routeMccStore.removeAll();
								// var mchtGrpId =
								// Ext.getCmp('mchtGrpId').getValue();
								// Ext.getCmp('mccId').setValue('');
								// Ext.getDom(Ext.getDoc()).getElementById('mcc').value
								// = '';
								SelectOptionsDWR.getComboDataWithParameter(
										'ROUTE_MCHNT_TP', addForm.getForm()
												.findField('mchtGroup')
												.getValue(), function(ret) {
											routeMccStore.loadData(Ext
													.decode(ret));
										});
							} else {
								Ext.getCmp('mccIdId').hide();
								// Ext.getCmp('mccId').setValue('*');
							}
							addForm.getForm().findField('mcc')
									.setValue('*')
						}
					}
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				id : 'mccIdId',
				hidden : true,
				items : [{
					xtype : 'basecomboselect',
					store : routeMccStore,
					fieldLabel : '商户MCC*',
					hiddenName : 'mcc',
					allowBlank : false,
					blankText : '商户MCC不能为空',
					emptyText : '请选择商户MCC',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250,
					value : '*'
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeMchtArea',
					fieldLabel : '商户地区*',
					hiddenName : 'mchtArea',
					allowBlank : false,
					blankText : '商户地区不能为空',
					emptyText : '请选择商户地区',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250,
					value : '*'
				}]
			}]
		}, {
			xtype : 'fieldset',
			title : '卡片控制',
			layout : 'column',
			collapsible : true,
			labelWidth : 70,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeBankCd',
					fieldLabel : '发卡银行*',
					hiddenName : 'insIdCd',
					allowBlank : false,
					blankText : '交易银行不能为空',
					emptyText : '请选择交易银行',
					mode : 'local',
					triggerAction : 'all',
					editable : true,
					lazyRender : true,
					width : 250,
					value : '*'
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'combo',
					store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['*', '*-无限制'], ['00', '借记卡'],
										['01', '贷记卡'], ['02', '准借记卡'],
										['03', '预付费卡'],['04','公务卡']],
								reader : new Ext.data.ArrayReader()
							}),
					displayField : 'displayField',
					valueField : 'valueField',
					fieldLabel : '卡类型*',
					hiddenName : 'cardTp',
					allowBlank : false,
					blankText : '卡类型不能为空',
					emptyText : '请选择卡类型',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 250,
					value : '*'
				}]
			}, {
				columnWidth : .5,
				layout : 'form',
				items : [{
					xtype : 'numberfield',
					id : 'cardBin',
					name : 'cardBin',
					fieldLabel : '卡BIN',
					allowBlank : true,
					// blankText: '卡BIN不能为空',
					emptyText : '*-无限制',
					vtype : 'isNumber',
					maxLength : 10,
					minLength : 6,
					width : 250
				}]
			}]
		}, {
			// id: 'baseInfo',
			xtype : 'fieldset',
			title : '交易控制',
			layout : 'column',
			labelWidth : 70,
			collapsible : true,
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype : 'basecomboselect',
					baseParams : 'routeTxnTp',
					fieldLabel : '交易类型*',
					hiddenName : 'txnNum',
					allowBlank : false,
					blankText : '交易类型不能为空',
					emptyText : '请选择交易类型',
					mode : 'local',
					triggerAction : 'all',
					editable : false,
					lazyRender : true,
					width : 150,
					value : '*'
				}]
			}, {
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'combo',
						store : new Ext.data.ArrayStore({
									fields : ['valueField',
											'displayField'],
									data : [['*', '*-无限制'],
											['1', '指定金额段']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '金额限制*',
						hiddenName : 'amtCtl',
						allowBlank : false,
						blankText : '金额限制不能为空',
						emptyText : '请选择金额限制',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 150,
						listeners : {
							'select' : function() {
								if (this.value == '1') {
									Ext.getCmp('amtBegId').show();
									Ext.getCmp('amtEndId').show();
									Ext.getCmp('amtBeg').allowBlank = false;
									Ext.getCmp('amtEnd').allowBlank = false;
								} else {
									Ext.getCmp('amtBegId').hide();
									Ext.getCmp('amtEndId').hide();
									Ext.getCmp('amtBeg').allowBlank = true;
									Ext.getCmp('amtEnd').allowBlank = true;
									Ext.getCmp('amtBeg')
											.setValue('');
									Ext.getCmp('amtEnd')
											.setValue('');
								}
							},
							'change' : function() {
								if (this.value == '1') {
									Ext.getCmp('amtBegId').show();
									Ext.getCmp('amtEndId').show();
									Ext.getCmp('amtBeg').allowBlank = false;
									Ext.getCmp('amtEnd').allowBlank = false;
								} else {
									Ext.getCmp('amtBegId').hide();
									Ext.getCmp('amtEndId').hide();
									Ext.getCmp('amtBeg').allowBlank = true;
									Ext.getCmp('amtEnd').allowBlank = true;
									Ext.getCmp('amtBeg')
											.setValue('');
									Ext.getCmp('amtEnd')
											.setValue('');
								}
							}
						},
						value : '*'
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					hidden : true,
					id : 'amtBegId',
					items : [{
						xtype : 'numberfield',
						fieldLabel : '最小金额',
						maxLength : 13,
						vtype : 'isMoney12',
						id : 'amtBeg',
						name : 'amtBeg',
						blankText : '最小金额不能为空',
						width : 150
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					id : 'amtEndId',
					hidden : true,
					items : [{
						xtype : 'numberfield',
						fieldLabel : '最大金额',
						maxLength : 13,
						vtype : 'isMoney12',
						id : 'amtEnd',
						name : 'amtEnd',
						blankText : '最大金额不能为空',
						width : 150
					}]
				}]
			}, {
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'combo',
						store : new Ext.data.ArrayStore({
									fields : ['valueField',
											'displayField'],
									data : [['*', '*-无限制'],
											['1', '指定日期段'],
											['2', '单日'], ['3', '双日'],
											['4', '周末'], ['5', '周一至周五']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '日期限制*',
						hiddenName : 'dateCtl',
						allowBlank : false,
						blankText : '日期限制不能为空',
						emptyText : '请选择日期限制',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 150,
						listeners : {
							'select' : function() {
								if (this.value == '1') {
									Ext.getCmp('dateBegId').show();
									Ext.getCmp('dateEndId').show();
									Ext.getCmp('dateBeg').allowBlank = false;
									Ext.getCmp('dateEnd').allowBlank = false;
								} else {
									Ext.getCmp('dateBegId').hide();
									Ext.getCmp('dateEndId').hide();
									Ext.getCmp('dateBeg').allowBlank = true;
									Ext.getCmp('dateEnd').allowBlank = true;
									Ext.getCmp('dateBeg')
											.setValue('');
									Ext.getCmp('dateEnd')
											.setValue('');
								}

							},
							'change' : function() {
								if (this.value == '1') {
									Ext.getCmp('dateBegId').show();
									Ext.getCmp('dateEndId').show();
									Ext.getCmp('dateBeg').allowBlank = false;
									Ext.getCmp('dateEnd').allowBlank = false;
								} else {
									Ext.getCmp('dateBegId').hide();
									Ext.getCmp('dateEndId').hide();
									Ext.getCmp('dateBeg').allowBlank = true;
									Ext.getCmp('dateEnd').allowBlank = true;
									Ext.getCmp('dateBeg')
											.setValue('');
									Ext.getCmp('dateEnd')
											.setValue('');
								}
							}
						},
						value : '*'
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					hidden : true,
					id : 'dateBegId',
					items : [{
						xtype : 'datefield',
						fieldLabel : '起始日期',
						id : 'dateBeg',
						name : 'dateBeg',
						format : 'Y-m-d',
						blankText : '起始日期不能为空',
						vtype : 'daterange',
						endDateField : 'dateEnd',
						// emptyText: '请输入起始日期',
						editable : false,
						width : 150
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					hidden : true,
					id : 'dateEndId',
					items : [{
						xtype : 'datefield',
						fieldLabel : '结束日期',
						id : 'dateEnd',
						name : 'dateEnd',
						format : 'Y-m-d',
						blankText : '结束日期不能为空',
						vtype : 'daterange',
						startDateField : 'dateBeg',
						// emptyText: '请输入结束日期',
						editable : false,
						width : 150
					}]
				}]
			}, {
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'combo',
						store : new Ext.data.ArrayStore({
									fields : ['valueField',
											'displayField'],
									data : [['*', '*-无限制'],
											['1', '指定时间段']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '时间限制*',
						hiddenName : 'timeCtl',
						allowBlank : false,
						blankText : '时间限制不能为空',
						emptyText : '请选择时间限制',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 150,
						listeners : {
							'select' : function() {
								if (this.value == '1') {
									Ext.getCmp('timeBegId').show();
									Ext.getCmp('timeEndId').show();
									Ext.getCmp('timeBeg').allowBlank = false;
									Ext.getCmp('timeEnd').allowBlank = false;
									Ext.getCmp('timeBeg')
											.setValue('00:00');
									Ext.getCmp('timeEnd')
											.setValue('23:59');
								} else {
									Ext.getCmp('timeBegId').hide();
									Ext.getCmp('timeEndId').hide();
									Ext.getCmp('timeBeg').allowBlank = true;
									Ext.getCmp('timeEnd').allowBlank = true;
									Ext.getCmp('timeBeg')
											.setValue('');
									Ext.getCmp('timeEnd')
											.setValue('');
								}
							},
							'change' : function() {
								if (this.value == '1') {
									Ext.getCmp('timeBegId').show();
									Ext.getCmp('timeEndId').show();
									Ext.getCmp('timeBeg').allowBlank = false;
									Ext.getCmp('timeEnd').allowBlank = false;
									Ext.getCmp('timeBeg')
											.setValue('00:00');
									Ext.getCmp('timeEnd')
											.setValue('23:59');
								} else {
									Ext.getCmp('timeBegId').hide();
									Ext.getCmp('timeEndId').hide();
									Ext.getCmp('timeBeg').allowBlank = true;
									Ext.getCmp('timeEnd').allowBlank = true;
									Ext.getCmp('timeBeg')
											.setValue('');
									Ext.getCmp('timeEnd')
											.setValue('');
								}
							}
						},
						value : '*'
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					hidden : true,
					id : 'timeBegId',
					items : [{
						xtype : 'textfield',
						fieldLabel : '起始时间(hh:mm)',
						maxLength : 5,
						regex : /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText : '该输入框只能输入数字0-9',
						id : 'timeBeg',
						name : 'timeBeg',
						// value: '00:00',
						blankText : '起始时间不能为空',
						// emptyText: '请输入起始时间',
						width : 150
					}]
				}, {
					columnWidth : .33,
					layout : 'form',
					hidden : true,
					id : 'timeEndId',
					items : [{
						xtype : 'textfield',
						fieldLabel : '结束时间(hh:mm)',
						maxLength : 5,
						regex : /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText : '该输入框只能输入数字0-9',
						id : 'timeEnd',
						name : 'timeEnd',
						// value: '23:59',
						blankText : '结束时间不能为空',
						// emptyText: '请输入结束时间',
						width : 150
					}]
				}]
			}]
		}, {
			xtype : 'fieldset',
			title : '目标商户文件',
			width : 850,
			defaults : {
				bodyStyle : 'padding-left: 10px'
			},
			buttonAlign: 'center',
			buttons: [{
				text: '商户信息文件上传',
				handler: function() {
					if (addForm.getForm().isValid()) {
						// 将addForm的内容组装成josn格式数据
						var menuArray = new Array();
						var data = {
							ruleCode: addForm.getForm().findField('ruleCode').getValue(),
							priority: addForm.getForm().findField('priority').getValue(),
							brhId: addForm.getForm().findField('brhId').getValue(),
							mchtGroup: addForm.getForm().findField('mchtGroup').getValue(),
							mcc: addForm.getForm().findField('mcc').getValue(),
							mchtArea: addForm.getForm().findField('mchtArea').getValue(),
							insIdCd: addForm.getForm().findField('insIdCd').getValue(),
							cardTp: addForm.getForm().findField('cardTp').getValue(),
							cardBin: addForm.getForm().findField('cardBin').getValue(),
							txnNum: addForm.getForm().findField('txnNum').getValue(),
							amtCtl: addForm.getForm().findField('amtCtl').getValue(),
							amtBeg: addForm.getForm().findField('amtBeg').getValue(),
							amtEnd: addForm.getForm().findField('amtEnd').getValue(),
							dateCtl: addForm.getForm().findField('dateCtl').getValue(),
							dateBeg: typeof(addForm.getForm().findField('dateBeg').getValue()) == 'string' ? '' : addForm.getForm().findField('dateBeg').getValue().dateFormat('Ymd'),
							dateEnd: typeof(addForm.getForm().findField('dateEnd').getValue()) == 'string' ? '' : addForm.getForm().findField('dateEnd').getValue().dateFormat('Ymd'),
							timeCtl: addForm.getForm().findField('timeCtl').getValue(),
							timeBeg: addForm.getForm().findField('timeBeg').getValue(),
							timeEnd: addForm.getForm().findField('timeEnd').getValue()
						};
						menuArray.push(data);
						
						if(uploadDialog != null){
							uploadDialog.close();
						}
						showUploadWin(menuArray);
					}
				}
			}]
		}]
	});
		
	// 添加窗口
	var addWin = new Ext.Window({
		title : '商户路由批量配置',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 880,
		autoHeight : true,
		layout : 'fit',
		items : [addForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
			}
		}]
	});
	
	//商户数据部分
	var recInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=routeBlukImpRecInfQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[	
			{name: 'batchNo',mapping: 'batchNo'},
			{name: 'blukDate',mapping: 'blukDate'},
			{name: 'blukTime',mapping: 'blukTime'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'blukFileName',mapping: 'blukFileName'},
			{name: 'blukMchnNum',mapping: 'blukMchnNum'},
			{name: 'ruleCode',mapping: 'ruleCode'},
			{name: 'priority',mapping: 'priority'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'mchtGroup',mapping: 'mchtGroup'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'mchtArea',mapping: 'mchtArea'},
			{name: 'insIdCd',mapping: 'insIdCd'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'cardBin',mapping: 'cardBin'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'amtCtl',mapping: 'amtCtl'},
			{name: 'amtBeg',mapping: 'amtBeg'},
			{name: 'amtEnd',mapping: 'amtEnd'},
			{name: 'dateCtl',mapping: 'dateCtl'},
			{name: 'dateBeg',mapping: 'dateBeg'},
			{name: 'dateEnd',mapping: 'dateEnd'},
			{name: 'timeCtl',mapping: 'timeCtl'},
			{name: 'timeBeg',mapping: 'timeBeg'},
			{name: 'timeEnd',mapping: 'timeEnd'}
		])
	});

	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<div width="60%">' +
				'<fieldset style="padding:20px 20px 20px 20px;">' +
					'<legend><font style="font-weight: bold;">规则基本信息</font></legend>' +
					'<ul><table width="100%">' +
						'<tr><td><font color=green>目的规则：</font>{ruleCode}</td>'  +
						'<td><font color=green>优先级别：</font>{priority:this.routePriority}</td>' +
						'<td><font color=green>渠道编号：</font>{bankNo}</td></tr>' +
					'</table></ul>' +
				'</fieldset>' +
				'<fieldset style="padding:20px 20px 20px 20px;">' +
					'<legend><font style="font-weight: bold;">商户控制</font></legend>' +
					'<ul><table width="100%">' +
						'<tr><td><font color=green>商户组别：</font>{mchtGroup}</td>'  +
						'<td><font color=green>商户MCC：</font>{mcc}</td>' +
						'<td><font color=green>商户地区：</font>{mchtArea}</td></tr>' +
					'</table></ul>' +
				'</fieldset>' +
				'<fieldset style="padding:20px 20px 20px 20px;">' +
					'<legend><font style="font-weight: bold;">卡片控制</font></legend>' +
					'<ul><table width="100%">' +
						'<tr><td><font color=green>发卡银行：</font>{insIdCd}</td>'  +
						'<td><font color=green>卡类型：</font>{cardTp:this.cardType}</td>' +
						'<td><font color=green>卡BIN：</font>{cardBin}</td></tr>' +
					'</table></ul>' +
				'</fieldset>' +
				'<fieldset style="padding:20px 20px 20px 20px;">' +
					'<legend><font style="font-weight: bold;">交易控制</font></legend>' +
					'<ul><table width="100%">' +
						'<tr><td><font color=green>交易类型：</font>{txnNum:this.txnNum}</td></tr>' +
						'<tr><td><font color=green>金额限制：</font>{amtCtl:this.amtCtl}</td>'  +
						'<td><font color=green>最小金额：</font>{amtBeg}</td>' +
						'<td><font color=green>最大金额：</font>{amtEnd}</td></tr>' +
						'<tr><td><font color=green>日期限制：</font>{dateCtl:this.dateCtl}</td>'  +
						'<td><font color=green>起始日期：</font>{dateBeg:this.formatDate}</td>' +
						'<td><font color=green>结束日期：</font>{dateEnd:this.formatDate}</td></tr>' +
						'<tr><td><font color=green>时间限制：</font>{timeCtl:this.timeCtl}</td>'  +
						'<td><font color=green>起始时间：</font>{timeBeg:this.formatDate}</td>' +
						'<td><font color=green>结束时间：</font>{timeEnd:this.formatDate}</td></tr>' +
					'</table></ul>' +
				'</fieldset>' +
			'</div>',
			{
				formatDate : function(val){
					if(val!=null &&val.length == 8){
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-' + val.substring(6, 8);
					}if(val!=null &&val.length == 6){
						return val.substring(0, 2) + ':' + val.substring(2, 4) + ':' + val.substring(4);
					}else if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
								+ val.substring(10, 12) + ':' + val.substring(12, 14);
					} else{
						return val;
					}
				},
				routePriority : function(val){
					switch(val) {
						case '0': return '低';
						case '1': return '中低';
						case '2': return '中';
						case '3': return '中高';
						case '4': return '高';
						default : return val;
					}
				},
				cardType : function(val){
					 switch(val) {
					    case '00': return '借记卡';
						case '01': return '贷记卡';
						case '02': return '准贷记卡';
						case '03': return '预付费卡';
						case '04': return '公务卡';
						default : return val;
					}
				},
				txnNum : function(val){
					 switch(val) {
						case '1101': return   '消费';
						case '1161': return   '查询';
						case '5151': return   '退货';
						case '2101': return   '消费冲正';
						case '3101': return   '消费撤消';
						case '4101': return   '撤消冲正';
						case '1011': return   '预授权';
						case '2011': return   '预授权冲正';
						case '3011': return   '预授权撤消';
						case '4011': return   '预授权撤消冲正';
						case '1091': return   '联机预授权完成';
						case '2091': return   '联机预授权完成冲正';
						case '3091': return   '联机预授权完成撤消';
						case '4091': return   '联机预授权完成撤消冲正';
						case '1111': return   '分期付款';
						case '2111': return   '分期付款冲正';
						case '1171': return   '积分查询';
						case '1121': return   '积分消费';
						case '2121': return   '积分消费冲正';
						case '3121': return   '积分撤消';
						case '4121': return   '积分撤消冲正';
						case '5161': return   '离线预授权完成';
						case '1131': return   '财务转账';
						case '3131': return   '转账撤销';
						case '1001': return   '明细查询';
						case '1000': return   '余额查询';
						default : return val;
					}
				},
				amtCtl : function(val){
					 switch(val) {
					    case '1': return '指定金额段';
						default : return val;
					}
				},
				dateCtl : function(val){
					 switch(val) {
						case '1': return '指定日期段';
						case '2': return '单日';
						case '3': return '双日';
						case '4': return '周末';
					    case '5': return '周一至周五';
						default : return val;
					}
				},
				timeCtl : function(val){
					 switch(val) {
					    case '1': return '指定时间段';
						default : return val;
					}
				}
			}
		)
	});
	
	var recInfColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '文件批次号',dataIndex: 'batchNo',width: 200,id: 'batchNo',align: 'center'},
		{header: '执行日期',dataIndex: 'blukDate',width: 150,align: 'center',renderer: formatDt},
		{header: '执行时间',dataIndex: 'blukTime',width: 150,align: 'center',renderer: formatDt},
		{header: '执行机构',dataIndex: 'brhId',width: 150,align: 'center'},
		{header: '操作员号',dataIndex: 'oprId',width:  100,align: 'center'},
		{header: '回执文件名称',dataIndex: 'blukFileName',id: 'blukFileName',width: 200,align: 'center'},
		{header: '批导商户数量',dataIndex: 'blukMchnNum',width: 100,align: 'center',renderer:addSuffix}
	]);
	
	function addSuffix(val) {
		return val + ' 家';
	}


	/***************************查询条件*************************/
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		'-','执行起始日期:',	{
					xtype: 'datefield',
					id: 'queryStartDate',
					name: 'queryStartDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					endDateField: 'queryEndDate',
					editable: false,
					width: 140
				},'-','执行结束日期:',	{
					xtype: 'datefield',
					id: 'queryEndDate',
					name: 'queryEndDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					startDateField: 'queryStartDate',
					editable: false,
					width: 140 
				}]  
         }) 
	
	var recInfGrid = new Ext.grid.GridPanel({
		title: '商户路由批量导入',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'blukFileName',
		stripeRows: true,
		store: recInfStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: recInfColModel,
		plugins: rowExpander,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				recInfStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryStartDate').setValue('');
				Ext.getCmp('queryEndDate').setValue('');
			}
		},'-',{
				text : '下载反馈文件',
				id : 'downloadRetFile',
				iconCls : 'download ',
				disabled: true,
				handler : function() {
					Ext.Ajax.request({
						url : 'T10604Action.asp?method=downloadRetFile',
						timeout : 60000,
						waitMsg : '正在下载反馈文件，请稍等......',
						params: {
							blukFileName: recInfGrid.getSelectionModel().getSelected().get('blukFileName'),
							txnId: '10604',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
															rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg,recInfGrid);
							}
						},
						failure: function(){
							showErrorMsg(rspObj.msg,recInfGrid);
						}
					});
				}
/*		},'-',{
			xtype: 'button',
			iconCls: 'detail',
			text:'批量录入说明 - 帮助',
			id: 'detailbu',
			width: 100,
			handler: function(){
				Ext.MessageBox.show({
					title: '批量录入说明 - 帮助',
					msg: 	'<table border="0">' +
							'<tr>' +
							'<td><font color=green>录入说明</font>：</td>' +
							'<td>① 录入时尽可能按单个文件依次上传</td>' +
							'</tr>' +
							'<tr>' +
							'<td></td>' +
							'<td>② 上传录入成功的文件不要重复录入</td>' +
							'</tr>' +
							'<tr>' +
							'<td><font color=green>提示说明</font>：</td>' +
							'<td>提示<font color=red>系统繁忙，发生并发操作，请稍后执行批量导入</font>时，是由于系统繁忙，可稍候重新执行</td>' +
							'</tr>' +
							'<tr>' +
							'<td></td>' +
							'<td>提示<font color=red>执行批量导入严重错误！！！请尽快联系业务员</font>时，是由于系统错误，请尽快联系业务员</td>' +
							'</tr>' +
							'</table>',
					animEl: Ext.get(recInfGrid.getEl()),
					buttons: Ext.MessageBox.OK,
					modal: true,
					width: 650
				});
			}*/
		},'-',{
				text : '商户路由批量导入',
				iconCls : 'upload',
				id : 'upload',
				disabled : false,
				handler : function() {
					addWin.show();
				}
		},'-',{
				text : '下载模版',
				iconCls : 'download ',
				handler : function() {
					Ext.Ajax.request({
//						url : 'T10604Action.asp?method=downloadFile',
						url: 'T1060401Action.asp',
						timeout : 60000,
						waitMsg : '正在下载模版，请稍等......',
						/*params: {
							txnId: '10604',
							subTxnId: '03'
						},*/
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoadFile.asp?path='+
									rspObj.downLoadFile+'&downloadName='+rspObj.downLoadFileName;
							} else {
								showErrorMsg('下载模板失败',recInfGrid);
							}
						}
						
					});
				}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        }  ,
		loadMask: {
			msg: '正在加载批量录入信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: recInfStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	recInfStore.load({
		params:{start: 0}
	});
	
	// 禁用编辑按钮
	recInfGrid.getStore().on('beforeload',function() {
		recInfGrid.getTopToolbar().items.items[4].disable();
	});
	
	recInfGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(recInfGrid.getView().getRow(recInfGrid.getSelectionModel().last)).frame();
			recInfGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	recInfStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('queryStartDate').getValue()) == 'string' ? '' : Ext.getCmp('queryStartDate').getValue().dateFormat('Ymd'),
			endDate: typeof(Ext.getCmp('queryEndDate').getValue()) == 'string' ? '' : Ext.getCmp('queryEndDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [recInfGrid],
		renderTo: Ext.getBody()
	});
	
});