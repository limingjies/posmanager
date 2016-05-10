
// mode 0:查看，1：新增，2：修改
function showRouteDetail(mode,ruleId,callback) {
	
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	
	//取性质制作下拉列表
	var propStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('PROP_SEL','',function(ret){
		propStore.loadData(Ext.decode(ret));
	});
	
	// 数据集
	var routeRuleDtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteRuleDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'ruleName',mapping: 'ruleName'},
			{name: 'ruleDsp',mapping: 'ruleDsp'},
			{name: 'validTime',mapping: 'validTime'},
			{name: 'invalidTime',mapping: 'invalidTime'},
			{name: 'orders',mapping: 'orders'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhId2',mapping: 'brhId2'},
			{name: 'brhId3',mapping: 'brhId3'},
			{name: 'mchtGid',mapping: 'mchtGid'},
			{name: 'cardIssuergFlag',mapping: 'cardIssuergFlag'},
			{name: 'cardIssuerg',mapping: 'cardIssuerg'},
			{name: 'cardBinFlag',mapping: 'cardBinFlag'},
			{name: 'cardBin',mapping: 'cardBin'},
			{name: 'cardTypeFlag',mapping: 'cardTypeFlag'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'amountFlag',mapping: 'amountFlag'},
			{name: 'amountBigger',mapping: 'amountBigger'},
			{name: 'amountSmaller',mapping: 'amountSmaller'},
			{name: 'txngFlag',mapping: 'txngFlag'},
			{name: 'txng',mapping: 'txng'},
			{name: 'areagFlag',mapping: 'areagFlag'},
			{name: 'areag',mapping: 'areag'},
			{name: 'mccgFlag',mapping: 'mccgFlag'},
			{name: 'mccg',mapping: 'mccg'},
			{name: 'timesFlag',mapping: 'timesFlag'},
			{name: 'times',mapping: 'times'},
			{name: 'dateType',mapping: 'dateType'},
			{name: 'crtTime',mapping: 'crtTime'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'},
			{name: 'status',mapping: 'status'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'misc1',mapping: 'misc1'},
			{name: 'misc2',mapping: 'misc2'}
		]),
	autoLoad: false
	});
	
//	=============================================主规则新增=================================================
	var dtlForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		autoWidth: true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth: 100,
		width: 1000,
		height : 430,
		autoScroll : true,
		layout: 'form',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'column',
				items : [{
					xtype : 'panel',
					columnWidth : .33,
					id:'ruleIdPan',
					layout : 'form',
					items : [{
						xtype : 'textfield',
						name : 'ruleId',
						readOnly :true,
						labelWidth : 80,
						id:'ruleId',
						fieldLabel : '规则编号',
						name : 'routeRuleInfo.ruleId',
						value : ruleId,
						width : 180
					}]
				}]
			},{
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '规则名称*',
						name : 'routeRuleInfo.ruleName',
						allowBlank : false,
						blankText: '规则名称不能为空',
						emptyText : '请输入规则名称',
						id: 'ruleName',
						//name: 'ruleName',
						maxLength: 40,
						width: 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '描述',
						name : 'routeRuleInfo.ruleDsp',
						allowBlank : true,
						id: 'ruleDsp',
						emptyText : '请输入描述',
						//name: 'ruleDsp',
						maxLength: 100,
						width: 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '优先级*',
						name : 'routeRuleInfo.orders',
						allowBlank : false,
						blankText: '优先级不能为空',
						emptyText : '请输入优先级',
						id: 'orders',
						vtype:'isNumber',
						maxLength: 6,
						width: 180
					}]
				}]
			},{
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype:'datetimefield',
						fieldLabel : '规则生效日期',
						name : 'routeRuleInfo.validTime',
						id: 'validTime',
						//emptyText : '请输入规则生效日期',
						//format: 'YmdHis',
						//name: 'validTime',
						value : new Date(),
						//blankText : '规则生效日期不能为空',
						//vtype : 'daterange',
						endDateField : 'invalidTime',
						// emptyText: '请输入起始日期',
						editable : false,
						width : 180,
						listeners: {
		                     'change': function(){
		                    	 	var strValidTime = Ext.getCmp('validTime').getValue();
		                    	 	var strInvalidTime = Ext.getCmp('invalidTime').getValue();
		                    	 	if (strValidTime != '' && strInvalidTime != ''){
		                    	 		var dtValidTime = Date.parse(strValidTime);
		                    	 		var dtInvalidTime = Date.parse(strInvalidTime);
		                    	 		
		                    	 		if (dtValidTime >= dtInvalidTime) {
		                    	 			showErrorMsg('生效日期不能再截止日期之后！',dtlForm);
		                    	 		}
		                    	 	}
		                    	}
		                    }
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype:'datetimefield',
						fieldLabel : '规则截止日期',
						name : 'routeRuleInfo.invalidTime',
						//allowBlank : true,
						id: 'invalidTime',
						//emptyText : '请输入规则截止日期',
						//name: 'invalidTime',
						value : new Date(),
						//blankText : '规则截止日期不能为空',
						//vtype : 'daterange',
						startDateField : 'validTime',
						// emptyText: '请输入结束日期',
						editable : false,
						width : 180,
						listeners: {
		                     'change': function(){
		                    	 	var strValidTime = Ext.getCmp('validTime').getValue();
		                    	 	var strInvalidTime = Ext.getCmp('invalidTime').getValue();
		                    	 	if (strValidTime != '' && strInvalidTime != ''){
		                    	 		var dtValidTime = Date.parse(strValidTime);
		                    	 		var dtInvalidTime = Date.parse(strInvalidTime);
		                    	 		
		                    	 		if (dtValidTime >= dtInvalidTime) {
		                    	 			showErrorMsg('生效日期不能再截止日期之后！',dtlForm);
		                    	 		}
		                    	 	}
		                    	}
		                    }
					}]
				}]
			},{
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'basecomboselect',
						baseParams: 'CHANNEL_ALL',
						fieldLabel: '支付渠道*',
						hiddenName: 'brh',
						id:'brhId',
						allowBlank : false,
						blankText: '支付渠道不能为空',
						emptyText : '请选择支付渠道',
						width: 180,
						listeners: {
							'select': function() {
								busiStore.removeAll();
								var brhId = Ext.getCmp('brhId').getValue();
								Ext.getCmp('brhId2').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('brh2').value = '';
								Ext.getCmp('brhId3').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('routeRuleInfo.brhId3').value = '';
								SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
								busiStore.loadData(Ext.decode(ret));
								});
							},
							'change': function() {
								busiStore.removeAll();
								var brhId = Ext.getCmp('brhId').getValue();
								Ext.getCmp('brhId2').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('brh2').value = '';
								Ext.getCmp('brhId3').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('routeRuleInfo.brhId3').value = '';
								SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
								busiStore.loadData(Ext.decode(ret));
								});
							}
						}
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'basecomboselect',
						fieldLabel: '业　　务*',
						store: busiStore,
						displayField: 'displayField',
						valueField: 'valueField',
						id: 'brhId2',
						hiddenName: 'brh2',
						allowBlank : false,
						blankText: '业务不能为空',
						emptyText : '请选择业务',
						width: 180,
						listeners: {
							'select': function() {
								propStore.removeAll();
								var brhId2 = Ext.getCmp('brhId2').getValue();
								Ext.getCmp('brhId3').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('routeRuleInfo.brhId3').value = '';
								SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
									propStore.loadData(Ext.decode(ret));
								});
							},
							'change': function() {
								propStore.removeAll();
								var brhId2 = Ext.getCmp('brhId2').getValue();
								Ext.getCmp('brhId3').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('routeRuleInfo.brhId3').value = '';
								SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
									propStore.loadData(Ext.decode(ret));
								});
							}
						}
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'basecomboselect',
						fieldLabel: '性　　质*',
						store: propStore,
						displayField: 'displayField',
						valueField: 'valueField',
						id: 'brhId3',
						hiddenName: 'routeRuleInfo.brhId3',
						allowBlank : false,
						blankText: '性质不能为空',
						emptyText : '请选择性质',
						width: 180
					}]
				}]
			},{
				columnWidth : .99,
				layout : 'column',
				items : [{
					columnWidth : .99,
					layout : 'form',
					items : [{
						xtype: 'combo',
						//baseParams: 'ROUTE_MCHTG_SEL',
						fieldLabel: '商户组*',
						hiddenName: 'routeRuleInfo.mchtGid',
						allowBlank : false,
						editable : false,
						blankText : '商户组不能为空',
						emptyText : '请选择商户组',
						id:'mchtGid',
						width: 180,
						store : new Ext.data.ArrayStore({
							fields : ['valueField','displayField'],
							data : [[Ext.getCmp("mchtgGridPanel").getSelectionModel().getSelected().get('mchtGid'), Ext.getCmp("mchtgGridPanel").getSelectionModel().getSelected().get('mchtGname')]],
							reader : new Ext.data.ArrayReader()
						}),
						listeners: {
							'select': function() {
								;
							},
							'change': function() {
								;
							}
						}
					}]
				}]
			},{
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
									data : [['1', '大于'],
											['2', '小于'],
											['3', '大于等于'],
											['4', '小于等于'],
											['5', '大于等于-小于'],
											['6', '大于等于-小于等于'],
											['7', '大于-小于'],
											['8', '大于-小于等于']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '金额',
						hiddenName : 'routeRuleInfo.amountFlag',
						id:'amountFlag',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						emptyText : '请选择金额操作类型',
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('amountFlag').value == ''){
									Ext.getCmp('amountBigger').allowBlank = true;
									Ext.getCmp('amountSmaller').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountBigger').setValue('');
									Ext.getCmp('amountSmaller').setValue('');
								}else if (Ext.getCmp('amountFlag').value == '1' || Ext.getCmp('amountFlag').value == '3') {
									Ext.getCmp('amountBigger').allowBlank = false;
									Ext.getCmp('amountSmaller').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').setValue('');
								} else if (Ext.getCmp('amountFlag').value == '2' || Ext.getCmp('amountFlag').value == '4') {
									Ext.getCmp('amountSmaller').allowBlank = false;
									Ext.getCmp('amountBigger').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountBigger').setValue('');
								} else {
									Ext.getCmp('amountBigger').allowBlank = false;
									Ext.getCmp('amountSmaller').allowBlank = false;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
								}	
							},
							'change' : function() {
								if (Ext.getCmp('amountFlag').value == ''){
									Ext.getCmp('amountBigger').allowBlank = true;
									Ext.getCmp('amountSmaller').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountBigger').setValue('');
									Ext.getCmp('amountSmaller').setValue('');
								}else if (Ext.getCmp('amountFlag').value == '1' || Ext.getCmp('amountFlag').value == '3') {
									Ext.getCmp('amountBigger').allowBlank = false;
									Ext.getCmp('amountSmaller').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').setValue('');
								} else if (Ext.getCmp('amountFlag').value == '2' || Ext.getCmp('amountFlag').value == '4') {
									Ext.getCmp('amountSmaller').allowBlank = false;
									Ext.getCmp('amountBigger').allowBlank = true;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountBigger').setValue('');
								} else {
									Ext.getCmp('amountBigger').allowBlank = false;
									Ext.getCmp('amountSmaller').allowBlank = false;
									Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
									Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'numberfield',
						fieldLabel : '大于（等于）',
						name : 'routeRuleInfo.amountBigger',
						allowBlank : true,
						id: 'amountBigger',
						//name: 'amountBigger',
						maxLength: 15,
						blankText : '大于（等于）的金额不能为空',
						//emptyText : '请输入金额',
						width: 120
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype: 'numberfield',
						fieldLabel : '小于（等于）',
						name : 'routeRuleInfo.amountSmaller',
						allowBlank : true,
						id: 'amountSmaller',
						blankText : '小于（等于）的金额不能为空',
						//emptyText : '请输入金额',
						//name: 'amountSmaller',
						maxLength: 15,
						width: 120
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '发卡行',
						hiddenName : 'routeRuleInfo.cardIssuergFlag',
						id:'cardIssuergFlag',
						allowBlank : true,
						emptyText : '请选择发卡行操作类型',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('cardIssuergFlag').value != '0') {
									Ext.getCmp('cardIssuerg').allowBlank = false;
								} else {
									Ext.getCmp('cardIssuerg').allowBlank = true;
								}
							},
							'change' : function() {
								if (Ext.getCmp('cardIssuergFlag').value != '0') {
									Ext.getCmp('cardIssuerg').allowBlank = false;
								} else {
									Ext.getCmp('cardIssuerg').allowBlank = true;
								}
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.cardIssuerg',
						readOnly :true,
						allowBlank : true,
						id: 'cardIssuerg',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						//name: 'cardIssuerg',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditCardIssuerg',
						handler : function() {
							multiSelectPopup('CardIssuerg', '发卡行', 50, Ext.getCmp('cardIssuerg'));
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearCardIssuerg',
						handler : function() {
							Ext.getCmp('cardIssuerg').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '卡BIN',
						hiddenName : 'routeRuleInfo.cardBinFlag',
						id:'cardBinFlag',
						allowBlank : true,
						emptyText : '请选择卡BIN操作类型',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('cardBinFlag').value != '0') {
									Ext.getCmp('cardBin').allowBlank = false;
								} else {
									Ext.getCmp('cardBin').allowBlank = true;
								}
							},
							'change' : function() {
								if (Ext.getCmp('cardBinFlag').value != '0') {
									Ext.getCmp('cardBin').allowBlank = false;
								} else {
									Ext.getCmp('cardBin').allowBlank = true;
								}
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.cardBin',
						readOnly :true,
						allowBlank : true,
						id: 'cardBin',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						//name: 'cardBin',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditCardBin',
						handler : function() {
							multiSelectPopup('CardBin', '卡BIN', 100, Ext.getCmp('cardBin'), null, null, Ext.getCmp('cardIssuerg').getValue());
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearCardBin',
						handler : function() {
							Ext.getCmp('cardBin').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '卡类型',
						hiddenName : 'routeRuleInfo.cardTypeFlag',
						id:'cardTypeFlag',
						allowBlank : true,
						emptyText : '请选择卡类型操作类型',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('cardTypeFlag').value != '0') {
									Ext.getCmp('cardType').allowBlank = false;
								} else {
									Ext.getCmp('cardType').allowBlank = true;
								}	
							},
							'change' : function() {
								if (Ext.getCmp('cardTypeFlag').value != '0') {
									Ext.getCmp('cardType').allowBlank = false;
								} else {
									Ext.getCmp('cardType').allowBlank = true;
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.cardType',
						allowBlank : true,
						readOnly :true,
						id: 'cardType',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						//name: 'cardType',
						maxLength: 25,
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditCardType',
						handler : function() {
							multiSelectPopup('CardType', '卡类型', 50, Ext.getCmp('cardType'),
									function(txt){
										// 把内容转换为逗号分隔的序号
										var ret = '';
										if (txt == ''){
											return '';
										}
										if (txt.substr(0,1) == '1'){
											return '1,2,3,4,5,6'
										} else {
											for (var i = 1; i < txt.length; i++){
												if (txt.substr(i,1) == '1'){
													if (ret != ''){
														ret += ',' + i;
													} else {
														ret += i;
													}
												}
											}
											return ret;
										}
									}, function(txt){
										// 把最终选择的逗号分隔的值，变为合适的格式
										if (txt != ''){
											var list = txt.split(',');
											var ret = ['0','0','0','0','0','0','0'];
											if (list.length == 6) {
												return '1000000';
											} else {
												for (var i = 0; i < list.length; i++){
													ret[list[i]] = '1';
												}
												return ret.toString().replace(/\,/g,'');
											}
										}
							});
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearCardType',
						handler : function() {
							Ext.getCmp('cardType').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '交易类型',
						hiddenName : 'routeRuleInfo.txngFlag',
						id:'txngFlag',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						allowBlank : true,
						emptyText : '请选择交易类型操作类型',
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('txngFlag').value != '0') {
									Ext.getCmp('txng').allowBlank = false;
								} else {
									Ext.getCmp('txng').allowBlank = true;
								}		
							},
							'change' : function() {
								if (Ext.getCmp('txngFlag').value != '0') {
									Ext.getCmp('txng').allowBlank = false;
								} else {
									Ext.getCmp('txng').allowBlank = true;
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.txng',
						readOnly:true,
						allowBlank : true,
						id: 'txng',
						//name: 'txng',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditTxng',
						handler : function() {
							multiSelectPopup('Txng', '交易类型', 50, Ext.getCmp('txng'));
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearTxng',
						handler : function() {
							Ext.getCmp('txng').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '地区',
						hiddenName : 'routeRuleInfo.areagFlag',
						id:'areagFlag',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						allowBlank : true,
						emptyText : '请选择地区操作类型',
						width : 180,
						listeners : {
							'select' : function() {
								if (Ext.getCmp('areagFlag').value != '0') {
									Ext.getCmp('areag').allowBlank = false;
								} else {
									Ext.getCmp('areag').allowBlank = true;
								}	
							},
							'change' : function() {
								if (Ext.getCmp('areagFlag').value != '0') {
									Ext.getCmp('areag').allowBlank = false;
								} else {
									Ext.getCmp('areag').allowBlank = true;
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.areag',
						allowBlank : true,
						readOnly:true,
						id: 'areag',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						//name: 'areag',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditAreag',
						handler : function() {
							multiSelectPopup('Areag', '地区', 30, Ext.getCmp('areag'));
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearAreag',
						handler : function() {
							Ext.getCmp('areag').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : 'MCC',
						hiddenName : 'routeRuleInfo.mccgFlag',
						id:'mccgFlag',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 180,
						allowBlank : true,
						emptyText : '请选择MCC操作类型',
						listeners : {
							'select' : function() {
								if (Ext.getCmp('mccgFlag').value != '0') {
									Ext.getCmp('mccg').allowBlank = false;
								} else {
									Ext.getCmp('mccg').allowBlank = true;
								}
							},
							'change' : function() {
								if (Ext.getCmp('mccgFlag').value != '0') {
									Ext.getCmp('mccg').allowBlank = false;
								} else {
									Ext.getCmp('mccg').allowBlank = true;
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.mccg',
						allowBlank : true,
						readOnly:true,
						id: 'mccg',
						//emptyText : '请点击编辑按钮进行内容的输入或选择',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditMccg',
						handler : function() {
							multiSelectPopup('Mccg', 'MCC', 50, Ext.getCmp('mccg'));
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearMccg',
						handler : function() {
							Ext.getCmp('mccg').setValue('');
						}
					}]
				}]
			},{
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
									data : [['0', '停用'],
											['1', '包含'],
											['2', '不包含']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '时间',
						hiddenName : 'routeRuleInfo.timesFlag',
						id:'timesFlag',
						mode : 'local',
						triggerAction : 'all',
						editable : false,
						lazyRender : true,
						width : 180,
						allowBlank : true,
						emptyText : '请选择时间操作类型',
						listeners : {
							'select' : function() {
								if (Ext.getCmp('timesFlag').value != '0') {
									Ext.getCmp('times').allowBlank = false;
								} else {
									Ext.getCmp('times').allowBlank = true;
								}
							},
							'change' : function() {
								if (Ext.getCmp('timesFlag').value != '0') {
									Ext.getCmp('times').allowBlank = false;
								} else {
									Ext.getCmp('times').allowBlank = true;
								}	
							}
						},
						value : '*'
					}]
				},{
					columnWidth : .66,
					layout : 'table',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						name : 'routeRuleInfo.times',
						allowBlank : true,
						readOnly:true,
						id: 'times',
						////emptyText : '请点击编辑按钮进行内容的输入或选择',
						width: 250
					},{
						xtype: 'button',
						text: '编辑',
						id: 'btnEditTimes',
						handler : function() {
							multiRowEditPopup('TimeRange', '交易时间段', 10, Ext.getCmp('times'));
						}
					},{
						xtype: 'button',
						text: '清除',
						id: 'btnClearMccg',
						handler : function() {
							Ext.getCmp('times').setValue('');
						}
					}]
				}]
			},{
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
									data : [['1', '单日'],
											['2', '双日'],
											['3', '工作日'],
											['4', '周末']],
									reader : new Ext.data.ArrayReader()
								}),
						fieldLabel : '可用日期',
						hiddenName : 'routeRuleInfo.dateType',
						id:'dateType',
						mode : 'local',
						triggerAction : 'all',
						editable : true,
						lazyRender : true,
						width : 180,
						allowBlank : true,
						emptyText : '请选择日期操作类型',
						listeners : {
							'select' : function() {
								;
							},
							'change' : function() {
								;
							}
						},
						value : '*'
					}]
				}]
			},{
				columnWidth : .99,
				xtype : 'panel',
				id:'creModPan',
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						readOnly :true,
						id: 'crtTime',
						name : 'routeRuleInfo.crtTime',
						fieldLabel : '创建时间',
						width : 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						readOnly :true,
						id: 'uptTime',
						name : 'routeRuleInfo.uptTime',
						fieldLabel : '最后修改时间',
						width : 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						readOnly :true,
						id: 'uptOpr',
						name : 'routeRuleInfo.uptOpr',
						fieldLabel : '最后修改人',
						width : 180
					}]
				}]
			},{
				columnWidth : .99,
				xtype : 'panel',
				layout : 'column',
				items : [{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						hidden:true,
						hiddenLabel:true,
						id: 'status',
						name : 'routeRuleInfo.status',
						width : 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						hidden:true,
						hiddenLabel:true,
						id: 'crtOpr',
						name : 'routeRuleInfo.crtOpr',
						width : 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						hidden:true,
						hiddenLabel:true,
						id: 'misc1',
						name : 'routeRuleInfo.misc1',
						width : 180
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						hidden:true,
						hiddenLabel:true,
						id: 'misc2',
						name : 'routeRuleInfo.misc2',
						width : 180
					}]
				}]
			}
		]
	});
	
	var dtlWin = new Ext.Window({
		title : '新增路由规则',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 1000,
		autoHeight : true,
		layout : 'fit',
		items : [dtlForm],
		buttonAlign : 'center',
		closeAction : 'close',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			id: 'btnOk',
			handler : function() {
				var url = 'T110311Action';
				if (mode == 0) {
					dtlWin.close();
					return;
				}
				if (mode == 1) {
					url += '_add.asp';
				} else {
					url += '_update.asp';
				}
				
				
				{// 日期大小关系检查
            	 	var strValidTime = Ext.getCmp('validTime').getValue();
            	 	var strInvalidTime = Ext.getCmp('invalidTime').getValue();
            	 	if (strValidTime != '' && strInvalidTime != ''){
            	 		var dtValidTime = Date.parse(strValidTime);
            	 		var dtInvalidTime = Date.parse(strInvalidTime);
            	 		
            	 		if (dtValidTime >= dtInvalidTime) {
            	 			showErrorMsg('生效日期不能再截止日期之后！',dtlForm);
            	 		}
            	 	}
				}
				
				{//金额大小关系检查
					var amountBigger = Ext.getCmp('amountBigger').getValue();
					var amountSmaller = Ext.getCmp('amountSmaller').getValue();					
					if (amountBigger != '' && amountSmaller != ''){
						if (amountSmaller <= amountBigger){
							showErrorMsg('金额的大小关系不正确！',dtlForm);
							return;
						}
					}
				}
				// 
				
				if (dtlForm.getForm().isValid()) {
					dtlForm.getForm().submit({
						url : url,
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							// 重置表单
							dtlForm.getForm().reset();
							alert(action.result.msg);
							callback();		//调用回调函数
							dtlWin.close();	
						},
						failure : function(form, action) {
							alert(action.result.msg);
							dtlWin.close();
						},
						params : {
							txnId : '110311',
							subTxnId : '01'
						}
					});
				}
			}
		}, {
			text : '重置',
			name: 'btnReset',
			id: 'btnReset',
			handler : function() {
				dtlForm.getForm().reset();
				
				//交易类型编辑
				{
					var txngString = Ext.getCmp('txng').getValue();
					if (txngString != '') {
						var txngList = txngString.split(',');
						var retString = '';
						for(var i = 0; i < txngList.length; i++){

							// 将{id:'3101', name:'消费撤销'}
							//{id:'1091', name:'预授权完成'},
				            //{id:'3011', name:'预授权撤销'},
				            //{id:'3091', name:'预授权完成撤销'},过滤掉以后再表示
							if (txngList[i] == '3101' || txngList[i] == '1091' || txngList[i] == '3011' || txngList[i] == '3091'){
								;
							} else {
								if (retString != '') {
									retString += ',' + txngList[i];
								} else {
									retString += txngList[i];
								}
							}
						}
					}
					Ext.getCmp('txng').setValue(retString);
				}
			}
		}]
	});
	
//	dtlWin.on('close',function(){
//		dtlForm.getForm().reset();
//	});

	//dtlWin.on('beforeload', function(){
	//	showErrorMsg("hello.",dtlForm);
	//});
	
	routeRuleDtlStore.load({
		params : {
			ruleId : ruleId
		},
		callback : function(records, options, success) {
			if (success) {
				// 重置表单
				dtlForm.getForm().reset();
				// 查看详细信息
				if (mode == 0) {
					dtlWin.setTitle('路由规则详细信息');
					Ext.getCmp('btnOk').text = '关闭';
					//Ext.getCmp('ruleName').disable();
					dtlForm.getForm().items.eachKey(function(key,item){  
						//console.log(item.fieldLabel)  
						item.setDisabled(true)  
						//item.getEl().dom.readOnly=true;
					});
					Ext.getCmp('btnReset').hide();
				// 新增路由规则
				}else if (mode == 1){
					Ext.getCmp('ruleIdPan').hide();
					Ext.getCmp('creModPan').hide();
					
					dtlWin.setTitle('新增路由规则');
					dtlForm.getForm().clearInvalid();
					dtlWin.show();
					
					Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
					
					Ext.getCmp('mchtGid').setValue(Ext.getCmp("mchtgGridPanel").getSelectionModel().getSelected().get('mchtGid'));
					
					return;
				// 修改路由规则
				} else {
					
					dtlWin.setTitle('修改路由规则');
				}
				
				dtlForm.getForm().loadRecord(routeRuleDtlStore.getAt(0));
				dtlForm.getForm().clearInvalid();
				dtlWin.show();

				if (Ext.getCmp('amountFlag').value == ''){
					Ext.getCmp('amountBigger').allowBlank = true;
					Ext.getCmp('amountSmaller').allowBlank = true;
					Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('amountBigger').setValue('');
					Ext.getCmp('amountSmaller').setValue('');
				}else if (Ext.getCmp('amountFlag').value == '1' || Ext.getCmp('amountFlag').value == '3') {
					Ext.getCmp('amountBigger').allowBlank = false;
					Ext.getCmp('amountSmaller').allowBlank = true;
					Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
					Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('amountSmaller').setValue('');
				} else if (Ext.getCmp('amountFlag').value == '2' || Ext.getCmp('amountFlag').value == '4') {
					Ext.getCmp('amountSmaller').allowBlank = false;
					Ext.getCmp('amountBigger').allowBlank = true;
					Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
					Ext.getCmp('amountBigger').setValue('');
				} else {
					Ext.getCmp('amountBigger').allowBlank = false;
					Ext.getCmp('amountSmaller').allowBlank = false;
					Ext.getCmp('amountBigger').getEl().up('.x-form-item').setDisplayed(true);
					Ext.getCmp('amountSmaller').getEl().up('.x-form-item').setDisplayed(true);
				}
				
				//交易类型编辑
				{
					var txngString = Ext.getCmp('txng').getValue();
					if (txngString != '') {
						var txngList = txngString.split(',');
						var retString = '';
						for(var i = 0; i < txngList.length; i++){

							// 将{id:'3101', name:'消费撤销'}
							//{id:'1091', name:'预授权完成'},
				            //{id:'3011', name:'预授权撤销'},
				            //{id:'3091', name:'预授权完成撤销'},过滤掉以后再表示
							if (txngList[i] == '3101' || txngList[i] == '1091' || txngList[i] == '3011' || txngList[i] == '3091'){
								;
							} else {
								if (retString != '') {
									retString += ',' + txngList[i];
								} else {
									retString += txngList[i];
								}
							}
						}
					}
					Ext.getCmp('txng').setValue(retString);
				}
				
				// 下拉框初始化
				var brhId = Ext.getCmp('brhId').getValue();
				if (brhId != null) {
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
												busiStore.loadData(Ext.decode(ret));
												});
				}

				var brhId2 = Ext.getCmp('brhId2').getValue();
				if (brhId2 != null) {
					SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
													propStore.loadData(Ext.decode(ret));
												});
				}
				showCmpProcess(dtlForm, '正在加载路由信息，请稍后......');
				hideCmpProcess(dtlForm, 1000);

			} else {
				showErrorMsg("加载路由信息失败，请刷新数据后重试", dtlForm);
			}
		}
	});
}