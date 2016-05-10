Ext.onReady(function() {

//	-----------------------------------费率配置----------------------------------------------
	var feeCtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agentFeeCtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'feeName',mapping: 'feeName'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'amt2',mapping: 'amt2'},
			{name: 'misc',mapping: 'misc'},
			{name: 'crt_time',mapping: 'crt_time'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'}
		]),
		autoLoad: true
	}); 
	
	//复选框选择模式  
	var checkboxSM = new Ext.grid.CheckboxSelectionModel({  
	    checkOnly: false,  
	    singleSelect: false  
	});  
	
	var feeCtlColModel = new Ext.grid.ColumnModel([
   		checkboxSM,
   		new Ext.grid.RowNumberer(),
   		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
   		{header: '费率名称',dataIndex: 'feeName',align: 'center',width: 120,id:'feeName'},
   		{header: '费率类型',dataIndex: 'feeType',align: 'left',width: 120,id:'feeType',renderer:feeType },
   		{header: '手续费',dataIndex: 'feeRate',align: 'left',width: 120,id:'feeRate',
   			editor:new Ext.form.NumberField({
	   			allowBlank : false,
	   			maxValue : 100,
				minValue : 0, 
				maxText : '不能超过100',
				minText : '请输入大于0的值',
				decimalPrecision : 3})  }
   	]);
	
	var tbar1 = new Ext.Toolbar({  
    	renderTo: Ext.grid.EditorGridPanel.tbar,  
			items:['',	'费率代码：',{
				xtype: 'textfield',
				id: 'queryDiscId',
				name: 'queryDiscId',
				vtype:'isEngNum',
				maxLength: 5,
				width: 120
			},'-','费率名称：',{
				xtype: 'textfield',
				id: 'queryDiscName',
				name: 'queryDiscName',
				maxLength: 30,
				width: 120
			}]  
	}) 
	
	 var feeCtlGrid = new Ext.grid.EditorGridPanel({
//			width: 430,
		 	height:400,
			collapsible: false,
//			title: '规则商户映射控制',
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
//			region:'center',
			region: 'west',
			clicksToEdit: true,
			autoExpandColumn: 'feeName',
//			plugins: feeCtlExpander,
			store: feeCtlStore,
			sm: checkboxSM,
			cm: feeCtlColModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载分润费率信息......'
			},
			listeners : {  
	            'render' : function() {  
//						tbar1.render(this.tbar); 
	         		}
	        }  ,
	        tbar: 	[{
				xtype: 'label',
				text: '分润费率开通',
				name: 'queryFeeCtlU',
				id: 'queryFeeCtlU',
				iconCls: 'query',
				width: 80,
				handler:function() {
					feeCtlStore.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: feeCtlStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});

	feeCtlGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(feeCtlGrid.getView().getRow(feeCtlGrid.getSelectionModel().last)).frame();
//			feeCfgStore.load();
		}
	});
	
	
	feeCtlStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0/*,
			queryDiscId: Ext.getCmp('queryDiscId').getValue(),
			queryDiscName: Ext.getCmp('queryDiscName').getValue()*/
		});
	});
	
//	-----------------------------表单对象-------------------------------------
	
	
	var brhLvlStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	// 合作伙伴级别
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO', function(ret) {
				brhLvlStore.loadData(Ext.decode(ret));
			});

	var brhStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	var brhNewNoStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	// 上级合作伙伴编号
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH_NEW_NO',function(ret){
		brhNewNoStore.loadData(Ext.decode(ret));
	});
	
	var memPropertyStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	
	// 人员属性
	
	SelectOptionsDWR.getComboData('MEM_PROPERTY', function(ret) {
			memPropertyStore.loadData(Ext.decode(ret));
			});
	
	
	var jobTypeStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	
	// 职务类型
	
	SelectOptionsDWR.getComboData('JOB_TYPE', function(ret) {
		jobTypeStore.loadData(Ext.decode(ret));
			});
	


	var upBrhStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});

	// 上级营业网点编号
	SelectOptionsDWR.getComboData('BRH_UP', function(ret) {
				upBrhStore.loadData(Ext.decode(ret));
			});


    // 开户行信息
    var bankNameStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    SelectOptionsDWR.getComboData('BANKNAME',function(ret){
    	bankNameStore.loadData(Ext.decode(ret));
    });
    var provinceStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    SelectOptionsDWR.getComboData('PROVINCE',function(ret){
    	provinceStore.loadData(Ext.decode(ret));
    });
    var cityStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('CITY','',function(ret){
    	cityStore.loadData(Ext.decode(ret));
    });
    var subbranchStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    

	
	// 可选合作伙伴下拉列表
	var brhCombo = new Ext.form.ComboBox({
				store : brhStore,
				displayField : 'displayField',
				valueField : 'valueField',
				mode : 'local',
				width : 250,
				triggerAction : 'all',
				forceSelection : true,
				typeAhead : true,
				selectOnFocus : true,
				editable : true,
				allowBlank : true,
				fieldLabel : '合作伙伴编号',
				id : 'editBrh',
				name : 'editBrh',
				hiddenName : 'brhIdEdit'
			});
	// 可选合作伙伴号下拉列表
	var brhNewNoCombo = new Ext.form.ComboBox({
				store : brhNewNoStore,
				displayField : 'displayField',
				valueField : 'valueField',
				mode : 'local',
				width : 250,
				triggerAction : 'all',
				forceSelection : true,
				typeAhead : true,
				selectOnFocus : true,
				editable : true,
				allowBlank : true,
				fieldLabel : '合作伙伴号',
				id : 'editBrhNewNo',
				name : 'editBrhNewNo',
				hiddenName : 'brhNewNoIdEdit'
			});

	// 合作伙伴添加表单
	var brhInfoForm = new Ext.form.FormPanel({
				frame : true,
				/*border: true,*/
				/*width: 800,*/
				autoWidth: true,
				autoHeight: true,
				/*height:500,*/
				renderTo: 'agentInfo',
				layout : 'column',
				defaults : {
					labelWidth: 120,
					bodyStyle : 'padding-left: 20px'
				},
				waitMsgTarget : true,
				items : [{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '基本信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '机构代码*',
						allowBlank : false,
						emptyText : '001~999必须为3位数字',
						id : 'brhId',
						name : 'orgNo',
						width : 250,
						maxLength : 3,
						maxLengthText : '机构代码最多可以输入3个数字001~999',
						vtype : 'isNumber',
						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
						blankText : '请输入数字001~999',
						listeners : {
							'change' : function() {
							}
						}
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						store : brhLvlStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'brhLvl',
						emptyText : '请选择合作伙伴级别',
						fieldLabel : '合作伙伴级别*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editable : false,
						allowBlank : false,
						blankText : '请选择一个合作伙伴级别',
						listeners : {
							'select' : function() {
								// 上级营业网点编号
								SelectOptionsDWR.getComboDataWithParameter('UP_BRH_ID', this.value, function(ret) {
											upBrhStore.loadData(Ext.decode(ret));
								});
							}
						}
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : upBrhStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'upBrhId',
						emptyText : '请选择上级合作伙伴编码',
						fieldLabel : '上级合作伙伴编码*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴名称*',
						allowBlank : false,
						blankText : '合作伙伴名称不能为空',
						emptyText : '请输入合作伙伴名称',
						id : 'brhName',
						name : 'brhName',
						width : 250,
						maxLength : 40,
						maxLengthText : '合作伙伴名称最多可以输入40个字符'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : memPropertyStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'memProperty',
						emptyText : '请选择人员属性',
						fieldLabel : '人员属性*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : jobTypeStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'jobType',
						emptyText : '请选择职务类别',
						fieldLabel : '职务类别*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'dynamicCombo',
						methodName : 'getAreaCode',
						fieldLabel : '合作伙伴地区码*',
						allowBlank : false,
						emptyText : '请输入合作伙伴地区码',
						hiddenName : 'resv1',
						width : 250
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴地址*',
						allowBlank : false,
						blankText : '合作伙伴地址不能为空',
						emptyText : '请输入合作伙伴地址',
						id : 'brhAddr',
						name : 'brhAddr',
						maxLength : 40,
						maxLengthText : '合作伙伴地址最多可以输入40个字符',
						width : 250
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴联系电话*',
						allowBlank : false,
						emptyText : '请输入合作伙伴联系电话',
						id : 'brhTelNo',
						name : 'brhTelNo',
						width : 250,
						maxLength : 20,
						maxLengthText : '合作伙伴联系电话最多可以输入20个数字',
						vtype : 'isNumber'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '负责人姓名',
						// emptyText: '请输入负责人姓名',
						id : 'brhContName',
						name : 'brhContName',
						width : 250,
						maxLength : 20,
						maxLengthText : '负责人姓名最多可以输入20个字符'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '是否系统级签到*',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '否',
								inputValue : '0',
								checked:true,
								name : "resv1_5"
							}, {
								boxLabel : '是',
								inputValue : '1',
								name : "resv1_5"
						}]
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '生成对账流水*',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '不生成',
								inputValue : '0',
								checked:true,
								name : "resv1_6"
							}, {
								boxLabel : '生成',
								inputValue : '1',
								name : "resv1_6"
						}]
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '清算到合作伙伴账号*',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '不清算',
								inputValue : '0',
								checked:true,
								name : "resv1_7"
							}, {
								boxLabel : '清算',
								inputValue : '1',
								name : "resv1_7",
								listeners : {
								'check' : function(checkbox, checked) {
								}
							}
						}]
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					id:'settleFlagIds',
//					hidden:true,
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '结算账户类型*',
						id:'settleFlagId',
						allowBlank:false,
						width : 250,
						items: [{
								boxLabel : '对公账户',
								inputValue : '0',
								width :100,
								name : "settleFlag"
							}, {
								boxLabel : '对私账户',
								inputValue : '1',
								width :100,
								name : "settleFlag"
						}]
					}]
				}, {
					columnWidth : 1,
					layout : 'form',
					id:'settleAcctNmId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户户名*',
						allowBlank:false,
						id : 'settleAcctNm',
						name : 'settleAcctNm',
						width : 250,
						maxLength : 80,
						vtype : 'isOverMax'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					id:'settleAcctId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户号*',
						allowBlank:false,
						id : 'settleAcct',
						name : 'settleAcct',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						width : 250,
						maxLength : 40,
						vtype : 'isOverMax'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					id:'settleAcctCheckId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户号(确认)*',
						allowBlank:false,
						id : 'settleAcctCheck',
						name : 'settleAcctCheck',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						width : 250,
						maxLength : 40,
						vtype : 'isOverMax',
						listeners: {
	                     'change': function(){
	                     		if(brhInfoForm.findById('settleAcct').getValue()!=brhInfoForm.findById('settleAcctCheck').getValue()){
	                     			showErrorMsg("两次输入账号不一致，请确认！", brhInfoForm);
	                     			Ext.getCmp("settleAcctCheck").setValue(""); 
	                     		}
	                    	}
	                    }
					}]
				}, {
					columnWidth: 1,
		        	layout: 'form',
		       		items: [{
							xtype: 'basecomboselect',
				        	baseParams: 'PROVINCE',
							fieldLabel: '开户银行-省*',
							store: provinceStore,
							displayField: 'displayField',
							valueField: 'valueField',
							id:'province',
							width:250,
							editable : true,
							listeners: {
								'select': function() {
									cityStore.removeAll();
									var province = Ext.getCmp('province').getValue();
									SelectOptionsDWR.getComboDataWithParameter('CITY',province,function(ret){
										cityStore.loadData(Ext.decode(ret));
									});
								}
							}
			        	}]
				},{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'CITY',
						fieldLabel: '开户银行-市*',
						store: cityStore,
						displayField: 'displayField',
						valueField: 'valueField',
						id:'city',
						width:250,
						editable : true,
						listeners: {
							'select': function() {
								subbranchStore.removeAll();
								var bankName = Ext.getCmp('bankName').getValue();
								if(bankName == ''){
									bankName=' ';
								}
								var province = Ext.getCmp('province').getValue();
								if(province == ''){
									province=' ';
								}
								var city = Ext.getCmp('city').getValue();
								if(city == ''){
									city=' ';
								}
								SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',bankName+','+province+','+city,function(ret){
									subbranchStore.loadData(Ext.decode(ret));
								});
							}
						}
		        	}]
			},{
		        	columnWidth: 1,
		        	layout: 'form',
		       		items: [{
							xtype: 'basecomboselect',
				        	baseParams: 'BANKNAME',
							fieldLabel: '开户银行*',
							store: bankNameStore,
							displayField: 'displayField',
							valueField: 'valueField',
							id:'bankName',
							width:250,
							editable : true,
							listeners: {
								'select': function() {
									subbranchStore.removeAll();
									var bankName = Ext.getCmp('bankName').getValue();
									if(bankName == ''){
										bankName=' ';
									}
									var province = Ext.getCmp('province').getValue();
									if(province == ''){
										province=' ';
									}
									var city = Ext.getCmp('city').getValue();
									if(city == ''){
										city=' ';
									}
									SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',bankName+','+province+','+city,function(ret){
										subbranchStore.loadData(Ext.decode(ret));
									});
								}
							}
			        	}]
				},{
				columnWidth: 1,
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						fieldLabel: '开户支行名*',
						store: subbranchStore,
						displayField: 'displayField',
						valueField: 'valueField',
						allowBlank: false,
						emptyText: '请先选择省市和开户银行',
						id: 'settleBankNoSub',
						width:400,
						editable : true,
						hiddenName: 'settleBankNo',
						listeners : {
				            'beforequery':function(e){  
				                var combo = e.combo;    
				                if(!e.forceAll){    
				                    var input = e.query;    
				                    // 检索的正则  
				                    var regExp = new RegExp(".*" + input + ".*");  
				                    // 执行检索  
				                    combo.store.filterBy(function(record,id){    
				                        // 得到每个record的项目名称值  
				                        var text = record.get(combo.displayField);    
				                        return regExp.test(text);   
				                    });  
				                    combo.expand();    
				                    return false;  
				                }  
				            }  
				        } 
		        	}]
				},
				{
					xtype: 'radiogroup',
					fieldLabel : '是否计算分润*',
					allowBlank : false,
					width : 250,
					hidden: true,
					blankText : '至少选择一项',
					items: [{
							boxLabel : '否',
							inputValue : '0',
							name : "resv1_8"
						}, {
							boxLabel : '是',
							inputValue : '1',
							checked:true,
							name : "resv1_8"}]},
//				{
//					columnWidth : .5,
//					layout : 'form',
//					items : [{
//						xtype: 'textfield',
//						fieldLabel : '',
//						hidden:true,
//						id : 'settleBankNm',
//						name : 'settleBankNm'
//					},{
//						xtype: 'radiogroup',
//						fieldLabel : '是否计算分润*',
//						allowBlank : false,
//						width : 250,
//						blankText : '至少选择一项',
//						items: [{
//								boxLabel : '否',
//								inputValue : '0',
//								checked:true,
//								name : "resv1_8"
//							}, {
//								boxLabel : '是',
//								inputValue : '1',
//								name : "resv1_8",
//								listeners : {
//								'check' : function(checkbox, checked) {
//									if (checked) {
//										brhInfoForm.findById('brhFeeCtlId').show();
//										Ext.getCmp('brhFeeCtl').allowBlank=false;
//										Ext.getCmp('brhFeeCtl').blankText='请选择合作伙伴分润费率';
//										Ext.getCmp('brhFeeCtl').clearInvalid();
//										
//									}else{
//										brhInfoForm.findById('brhFeeCtlId').hide();
//										Ext.getCmp('brhFeeCtl').allowBlank=true;
////										Ext.getCmp('brhFeeCtl').reset();
//										Ext.getCmp('brhFeeCtl').setValue("");
//										Ext.getCmp('brhFeeCtl').clearInvalid();
//										
//									}
//								}
//							}
//						}]
//					}]
//				},{
//					columnWidth : 1,
//					layout : 'form',
//					id:'brhFeeCtlId',
//					hidden:true,
//					items : [{
//						xtype : 'dynamicCombo',
//						methodName : 'getBrhFeeCtl',
//						fieldLabel : '分润费率代码',
//	//					id:'tblBrhFeeCfgAdd.mchtNo',
//						hiddenName : 'brhFee',
//						id : 'brhFeeCtl',
////						allowBlank : true,
////						width : 250
//						width : 400
//					}]
//				}
				],
//					items : [
//					         {
//						columnWidth : .5,
//						layout : 'form',
//						// width: 150,
//						items : [{
//							xtype : 'displayfield',
//							fieldLabel : '费率代码',
//							id:'tblAgentFeeCfgZlfAdd.tblBrhFeeCfgPK.discId',
//							name : 'tblAgentFeeCfgZlfAdd.tblBrhFeeCfgPK.discId',
//							width : 250
//						}]
//					},
//					{
//						columnWidth : .5,
//						layout : 'form',
//						items : [{
//							xtype : 'dynamicCombo',
//							methodName : 'getMchntId',
//							fieldLabel : '商户编号',
////							id:'tblAgentFeeCfgZlfAdd.mchtNo',
//							hiddenName : 'tblAgentFeeCfgZlfAdd.mchtNo',
//							allowBlank : true,
////							maxLength : 30,
////							blankText : '费率名称不能为空',
//							width : 250
//						}]
//					},{
//						columnWidth : .5,
//						layout : 'form',
//						items : [{
//							xtype : 'basecomboselect',
//							baseParams : 'MCHNT_GRP_ALL',
//							fieldLabel : '商户组别',
////							id:'tblAgentFeeCfgZlfAdd.mchtNo',
//							hiddenName : 'tblAgentFeeCfgZlfAdd.mchtGrp',
//							allowBlank : true,
//							editable: true,
////							maxLength : 30,
////							blankText : '费率名称不能为空',
//							width : 250
//						}]
//					}]
				},
				{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '费率信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
						columnWidth : 1,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '规则名称*',
							id:'tblAgentFeeCfgZlfAdd.name',
							name : 'tblAgentFeeCfgZlfAdd.name',
							allowBlank : false,
							maxLength : 30,
							blankText : '费率名称不能为空',
							width : 636
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励开始日期',
							id:'tblAgentFeeCfgZlfAdd.promotionBegDate',
							name : 'tblAgentFeeCfgZlfAdd.promotionBegDate',
							vtype : 'daterange',
							endDateField : 'tblAgentFeeCfgZlfAdd.promotionEndDate',
							format : 'Y-m-d',
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励结束日期',
							id:'tblAgentFeeCfgZlfAdd.promotionEndDate',
							name : 'tblAgentFeeCfgZlfAdd.promotionEndDate',
							vtype : 'daterange',
							startDateField : 'tblAgentFeeCfgZlfAdd.promotionBegDate',
							format : 'Y-m-d',
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '月交易额(万元)*',
							id:'tblAgentFeeCfgZlfAdd.baseAmtMonth',
							name : 'tblAgentFeeCfgZlfAdd.baseAmtMonth',
							allowBlank : false,
							blankText : '月交易额不能为空',
//							maxLength : 15,
							maxValue : 9999999999999.99,
							minValue : 0, 
							maxText : '不能超过9999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 2, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '提档交易额(万元)*',
							id:'tblAgentFeeCfgZlfAdd.gradeAmtMonth',
							name : 'tblAgentFeeCfgZlfAdd.gradeAmtMonth',
							allowBlank : false,
							blankText : '提档交易额不能为空',
//							maxLength : 15,
							maxValue : 9999999999999.99,
							minValue : 0, 
							maxText : '不能超过9999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 2, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '激励系数*',
							id:'tblAgentFeeCfgZlfAdd.promotionRate',
							name : 'tblAgentFeeCfgZlfAdd.promotionRate',
							allowBlank : false,
							blankText : '激励系数不能为空',
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}/*,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '成本费率(%)*',
							id:'tblAgentFeeCfgZlfAdd.feeRate',
							name : 'tblAgentFeeCfgZlfAdd.feeRate',
							allowBlank : false,
							blankText : '成本费率不能为空',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '成本封顶值*',
							id:'tblAgentFeeCfgZlfAdd.capFeeValue',
							name : 'tblAgentFeeCfgZlfAdd.capFeeValue',
							allowBlank : false,
							blankText : '成本费率不能为空',
//							maxLength : 12,
							maxValue : 999999999999.99,
							minValue : 0, 
							maxText : '不能超过999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}*/,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '分润比率*',
							id:'tblAgentFeeCfgZlfAdd.allotRate',
							name : 'tblAgentFeeCfgZlfAdd.allotRate',
							allowBlank : false,
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '特殊费率*',
							id:'tblAgentFeeCfgZlfAdd.specFeeRate',
							name : 'tblAgentFeeCfgZlfAdd.specFeeRate',
							allowBlank : false,
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'VISA成本费率',
							id:'tblAgentFeeCfgZlfAdd.extVisaRate',
							name : 'tblAgentFeeCfgZlfAdd.extVisaRate',
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'MASTER成本费率',
							id:'tblAgentFeeCfgZlfAdd.extMasterRate',
							name : 'tblAgentFeeCfgZlfAdd.extMasterRate',
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'JCB成本费率',
							id:'tblAgentFeeCfgZlfAdd.extJcbRate',
							name : 'tblAgentFeeCfgZlfAdd.extJcbRate',
//							maxLength : 12,
							maxValue : 1,
							minValue : 0, 
							maxText : '不能超过1',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}]
				},{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '分润信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
						width : 800,
						items : feeCtlGrid
					}]
				}],
					
				buttonAlign :"center",
				buttons : [{
					text : '确定',
					handler : function() {
						if (brhInfoForm.getForm().isValid()) {
							
							var store=feeCtlGrid.getSelectionModel().getSelections();
							var jsonArray=[];
							var tempgrid="";
//							store.each(function(record){
							for(var i=0;i<store.length;i++){
								var record = store[i];
								//得到当前记录判断是否为空，及新增的最后一条
								var currentRecord=record.get("rateId")+record.get("feeType")
				                                 +record.get("feeRate");
								currentRecord=Ext.util.Format.trim(currentRecord);
								if(currentRecord!=""){
									tempgrid=tempgrid+currentRecord;
									jsonArray.push(Ext.util.JSON.encode(record.data));
								}	
							}
//							});
							tempgrid=Ext.util.Format.trim(tempgrid);
							var resultJson=Ext.util.JSON.encode(jsonArray);
							
							brhInfoForm.getForm().submit({
								url : 'T10105Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									// 重置表单
									brhInfoForm.getForm().reset();
									feeCtlGrid.getStore().reload();
									showSuccessMsg(action.result.msg,
											brhInfoForm);
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, brhInfoForm);
								},
								params : {
									txnId : '10105',
									subTxnId : '01',
									gridJson:resultJson
								}
							});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						brhInfoForm.getForm().reset();
						feeCtlGrid.getStore().reload();
					}
				}]
			});

});