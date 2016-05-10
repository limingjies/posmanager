Ext.onReady(function() {
	
	var mchntMccStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboData('MCHNT_TP_CUP',function(ret){
		mchntMccStore.loadData(Ext.decode(ret));
	});
	
	var cupFeeStore1 = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1','----',function(ret){
		cupFeeStore1.loadData(Ext.decode(ret));
	});
	
	var cupFeeStore2 = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP2','----',function(ret){
		cupFeeStore2.loadData(Ext.decode(ret));
	});
	
	var hasSub = false;
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	/***********  商户费率设置  *****************/
	var feeForm = new Ext.form.FormPanel({
    	region: 'north',
        height: 100,
        frame: true,
        layout: 'column',
        labelWidth: 80,
        items: [{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引1*',
					id: 'mchtCup521I',
					hiddenName: 'mchtCup521',
					allowBlank: false,
					readOnly: true,
					anchor: '90%',
					value: 'NK001'
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型1*',
					id: 'mchtCup522I',
					hiddenName: 'mchtCup522',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore1.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP1',feeForm.getForm().findField('mchtCup522I').getValue(),function(ret){
								cupFeeStore1.loadData(Ext.decode(ret));
							});
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore1,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置1*',
					id: 'mchtCup523I',
					hiddenName: 'mchtCup523',
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
    			columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_FEE_ACT',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '决定索引2*',
					id: 'mchtCup524I',
					hiddenName: 'mchtCup524',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							var feeAct2 = feeForm.getForm().findField('mchtCup524I').getValue();
							if(feeAct2 == 'NK001'){
								feeForm.getForm().findField('mchtCup524I').reset();
								showMsg('请选择“NK001-全国南卡北用决定索引”以外的类型！',feeForm);
							}
						}
					}
		        }]
			},{
    			columnWidth: .22,
            	layout: 'form',
        		items: [{
			        xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','0-按比例'],['1','1-固定金额']],
						reader: new Ext.data.ArrayReader()
					}),
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法类型2*',
					id: 'mchtCup525I',
					hiddenName: 'mchtCup525',
					allowBlank: false,
					anchor: '90%',
					listeners:{
						'select': function() {
							cupFeeStore2.removeAll();
							SelectOptionsDWR.getComboDataWithParameter('CUP_FEE_TP2',feeForm.getForm().findField('mchtCup525I').getValue(),function(ret){
								cupFeeStore2.loadData(Ext.decode(ret));
							});
						}
					}
		        }]
			},{
    			columnWidth: .44,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        store: cupFeeStore2,
			        displayField: 'displayField',
					valueField: 'valueField',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '算法配置2*',
					id: 'mchtCup526I',
					hiddenName: 'mchtCup526',
					allowBlank: false,
					anchor: '90%'
		        }]
			}]
    });
    
    
	var feeWin = new Ext.Window({
		title: '商户费率设置',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 1000,
		autoHeight: true,
		layout: 'fit',
		items: [feeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {

				if(feeForm.getForm().isValid()) {
					var fee1 = feeForm.getForm().findField('mchtCup521I').getValue();
					var fee2 = feeForm.getForm().findField('mchtCup522I').getValue();
					var fee3 = feeForm.getForm().findField('mchtCup523I').getValue();
					var fee4 = feeForm.getForm().findField('mchtCup524I').getValue();
					var fee5 = feeForm.getForm().findField('mchtCup525I').getValue();
					var fee6 = feeForm.getForm().findField('mchtCup526I').getValue();
					
					mchntForm.getForm().findField("mchtCup52").setValue(fee1 + '&' + fee3 + '&#' + fee4 + '&' + fee6);
					
				    feeForm.getForm().reset();
                  	feeWin.hide();
				} 
			}
		},{
			text: '重置',
			handler: function() {
				feeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				feeForm.getForm().reset();
				feeWin.hide();
			}
		}]
	});
	

    

    

    
	/***********  商户新增  *****************/
	var mchntForm = new Ext.FormPanel({
        title: '新增商户信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		modal: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		autoScroll: true,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
	        	xtype: 'tabpanel',
	        	id: 'tab',
	        	frame: true,
	            plain: false,
	            activeTab: 0,
	            height: 500,
	            deferredRender: false,
	            enableTabScroll: true,
	            labelWidth: 150,
	        	items:[{
	        		title:'基本信息',
	                id: 'basic',
	                frame: true,
					layout:'column',
		            items: [{
		        		columnWidth: .33,
		            	layout: 'form',
		        		items: [{
					        xtype: 'basecomboselect',
					        baseParams: 'MCHT_CUP_TYPE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户服务类型*',
							id: 'mchtCup1I',
							hiddenName: 'mchtCup1',
							allowBlank: false,
							anchor: '90%',
							value: '00'
				        }]
				    },{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '自定义商户号',
							maxLength: '15',
							vtype: 'isOverMax',
							id: 'mchtCup2',
							name: 'mchtCup2',
//							allowBlank: false,
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
		            	layout: 'form',
		            	xtype: 'panel',
		        		items: [{
		        			xtype: 'basecomboselect',
					        baseParams: 'CUP_CONN_TYPE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '联接方式*',
							id: 'mchtCup3I',
							hiddenName: 'mchtCup3',
							allowBlank: false,
							anchor: '90%',
							value: 'S'
						}]
		        	},{
		        		columnWidth: .33,
		        		xtype: 'panel',
				        layout: 'form',
			       		items: [{
					        xtype: 'basecomboselect',
					        baseParams: 'CUP_BRH_S',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡收单机构*',
							allowBlank: false,
							id: 'mchtCup4I',
							hiddenName: 'mchtCup4',
							anchor: '90%'
				        }]
					},
					{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'basecomboselect',
				        	baseParams: 'CUP_BRH_S',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '代理机构*',
							allowBlank: false,
							id: 'mchtCup5I',
							hiddenName: 'mchtCup5',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
					        xtype: 'basecomboselect',
					        baseParams: 'CUP_BRH_S',
					        id: 'mchtCup7I',
							hiddenName: 'mchtCup7',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡结算机构*',
							allowBlank: false,
							anchor: '90%'
				        }]
					},{
			        	columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
//					        xtype: 'basecomboselect',
//					        baseParams: 'CUP_BRH_S',
//					        id: 'mchtCup6I',
//							hiddenName: 'mchtCup6',
			       			xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '所属平台机构*',
							id: 'mchtCup6',
							name: 'mchtCup6',
							value: '0800093600',
							allowBlank: false,
							anchor: '90%'
				        }]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
//					        xtype: 'basecomboselect',
//					        baseParams: 'CUP_BRH_S',
//					        id: 'mchtCup8I',
//							hiddenName: 'mchtCup8',
			       			xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '收单接入机构*',
							id: 'mchtCup8',
							name: 'mchtCup8',
							value: '0800093600',
							allowBlank: false,
							blankText: '请选接入机构',
							anchor: '90%'
				        }]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combo',
				        		labelStyle: 'padding-left: 5px',
								store: new Ext.data.ArrayStore({
									fields: ['valueField','displayField'],
									data: [['156','156-中国']],
									reader: new Ext.data.ArrayReader()
								}),
								fieldLabel: '国家代码*',
								allowBlank: false,
								id: 'mchtCup9I',
								hiddenName: 'mchtCup9',
								value: '156',
								anchor: '90%'
				        	}]
					},{
			        	columnWidth: .33,
			        	id: 'mchtNmPanel',
			        	xtype: 'panel',
			        	layout: 'form',
			       		items: [{
			       			xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户中文名称*',
							maxLength: '60',
							allowBlank: false,
							vtype: 'isOverMax',
							id: 'mchtCup10',
							anchor: '90%'
						}]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'textfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户中文简写*',
							maxLength: '40',
							vtype: 'isOverMax',
							id: 'mchtCup11',
		  				    allowBlank: false,
							blankText: '请输入中文名称简写',
							name: 'mchtCup11',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '英文名称',
								maxLength: 100,
								vtype: 'isOverMax',
								regex: /^\w+[\w\s]+\w+$/,
								regexText:'英文名称必须是字母，如Bill Gates',
								id: 'mchtCup12',
								name: 'mchtCup12',
								anchor: '90%'
				        	}]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'textfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '行政区划码*',
								allowBlank: false,
								id: 'mchtCup13',
								name: 'mchtCup13',
								regex: /^[0-9]{6}$/,
								regexText: '行政区划码必须是6位数字',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'dynamicCombo',
								labelStyle: 'padding-left: 5px',
								methodName: 'getAreaCode_as',
								fieldLabel: '受理地区码*',
								hiddenName: 'mchtCup14',
								allowBlank: false,
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'dynamicCombo',
								labelStyle: 'padding-left: 5px',
								methodName: 'getAreaCode_as',
								fieldLabel: '清算地区码*',
								hiddenName: 'mchtCup15',
								allowBlank: false,
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	store: mchntMccStore,
					        	displayField: 'displayField',
								valueField: 'valueField',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户交易类型*',
								allowBlank: false,
								id: 'mchtCup16I',
								hiddenName: 'mchtCup16',
								anchor: '90%',
								'listeners':{
									'select': function(){
										var amMchtTp = mchntForm.getForm().findField('mchtCup16I').getValue();
										var realMchtTp = mchntForm.getForm().findField('mchtCup17I').getValue();
										
										if(amMchtTp != realMchtTp){
											mchntForm.getForm().findField('mchtCup20I').allowBlank = false;
											mchntForm.getForm().findField('mchtCup22I').allowBlank = false;
										}else{
											mchntForm.getForm().findField('mchtCup20I').allowBlank = true;
											mchntForm.getForm().findField('mchtCup22I').allowBlank = true;
										}
									}
								}
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	store: mchntMccStore,
					        	displayField: 'displayField',
								valueField: 'valueField',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '真实商户类型*',
								allowBlank: false,
								id: 'mchtCup17I',
								hiddenName: 'mchtCup17',
								anchor: '90%',
								'listeners':{
									'select': function(){
										var amMchtTp = mchntForm.getForm().findField('mchtCup16I').getValue();
										var realMchtTp = mchntForm.getForm().findField('mchtCup17I').getValue();
										
										if(amMchtTp != realMchtTp){
											mchntForm.getForm().findField('mchtCup20I').allowBlank = false;
											mchntForm.getForm().findField('mchtCup22I').allowBlank = false;
										}else{
											mchntForm.getForm().findField('mchtCup20I').allowBlank = true;
											mchntForm.getForm().findField('mchtCup22I').allowBlank = true;
										}
									}
								}
				        	}]
					},{
			        	columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
								xtype: 'basecomboselect',
					        	baseParams: 'MCHNT_GRP_CUP',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户组别*',
								allowBlank: false,
								hiddenName: 'mchtCup18',
								id:'mchtCup18I',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	baseParams: 'MCHNT_TYPE_CUP',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户状态*',
								maxLength: 40,
								vtype: 'isOverMax',
								allowBlank: false,
								disabled: true,
								id: 'mchtCup19I',
								hiddenName: 'mchtCup19',
								value:'9',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	baseParams: 'APP_REASON',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '套用商户类型原因',
								vtype: 'isOverMax',
								id: 'mchtCup20I',
								hiddenName: 'mchtCup20',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	baseParams: 'MCC_MD',
								labelStyle: 'padding-left: 5px',
								fieldLabel: 'MCC套用依据',
								id: 'mchtCup22I',
								hiddenName: 'mchtCup22',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'basecomboselect',
					        	baseParams: 'CH_STORE_FLAG',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '连锁店标识*',
								width:150,
								id: 'mchtCup21I',
								hiddenName: 'mchtCup21',
								value: '0',
								allowBlank: false,
								anchor: '90%',
								'listeners':{
	                        		'select': function() {
	                        			mchntForm.getForm().findField('mchtCup83I').reset();
	                        		}
								}
				        	}]
					},{
	                  	columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-不生成'],['1','1-生成自身流水文件'],['2','2-生成子商户流水文件']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户文件生成标志*',
	                        id: 'mchtCup83I',
							hiddenName: 'mchtCup83',
							value: '0',
							allowBlank: false,
	                        anchor: '90%',
	                        'listeners':{
	                        	'select': function() {
								     
								    var storeFlag = mchntForm.getForm().findField('mchtCup21I').getValue();
								    var fileFlag = mchntForm.getForm().findField('mchtCup83I').getValue();
								    
								    if(storeFlag == ''|| storeFlag == null){
								    	mchntForm.getForm().findField('mchtCup83I').reset();
								    	showMsg('请先选择连锁店标识！',mchntForm);
								    }else{
								    	if(storeFlag != '1' && fileFlag =='2'){
									    	mchntForm.getForm().findField('mchtCup83I').reset();
									    	showMsg('仅在连锁店标识为“根总店”时可选“2-生成子商户流水文件”！',mchntForm);
								    	}
								    }
	                        	}
	                        }
	                    }]
					}]
        	   },{
                title:'签约信息',
                id: 'sign',
                frame: true,
				layout:'column',
                items: [{
                	columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['80000000000000000000','80000000000000000000-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户受理币种*',
							maxLength: 40,
							allowBlank: false,
							vtype: 'isOverMax',
							value:'80000000000000000000',
							id: 'mchtCup23I',
							hiddenName: 'mchtCup23',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['156','156-人民币']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户默认交易币种*',
							maxLength: 40,
							allowBlank: false,
							vtype: 'isOverMax',
							value:'156',
							id: 'mchtCup24I',
							hiddenName: 'mchtCup24',
							anchor: '90%'
			        	}]
				},{
					columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取押金 *',
	                        id: 'mchtCup25',
	                        name: 'mchtCup25'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存卡号 *',
	                        id: 'mchtCup26',
	                        name: 'mchtCup26'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户是否保存磁道信息 ',
	                        id: 'mchtCup27',
	                        name: 'mchtCup27'
	                    }]
                },{
                	columnWidth: .5,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否向商户收取服务费 ',
	                        id: 'mchtCup28',
	                        name: 'mchtCup28'
	                    }]
                },{
                	columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '是否为周期商户*',
							id: 'mchtCup29',
							name: 'mchtCup29'
			        	}]
				},{
					columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
				        	xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-校验MAC、交易加密'],['2','2-校验MAC、交易不加密'],
									   ['3','3-不校验MAC、交易加密'],['4','4-不校验MAC、交易不加密']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: 'MAC校验和交易加密标志*',
							id: 'mchtCup30I',
							hiddenName: 'mchtCup30',
							value:'4',
							anchor: '90%'
			        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	id:'accountHide1',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						maxLength: 20,
						id: 'mchtCup31',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业地址*',
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'mchtCup32',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '负责人*',
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'mchtCup33',
						anchor: '90%'
		        	}]
		        },{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定电话',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maxLength: 15,
						vtype: 'isOverMax',
						id: 'mchtCup34',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表*',
						maxLength: 50,
						vtype: 'lengthRange50',
						allowBlank: false,
						id: 'mchtCup35',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件类型*',
						allowBlank: false,
						value: '01',
						hiddenName: 'mchtCup36',
						id: 'mchtCup36I',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人证件号',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'mchtCup37',
						anchor: '90%'
		        	}]
				}]
            },{
                title:'计费清算',
                layout:'column',
                id: 'settle',
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateFlag',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-固定比例'],['2','2-固定金额'],
								   ['3','3-算法描述']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法标识*',
						id: 'mchtCup39I',
						hiddenName: 'mchtCup39',
						allowBlank: false,
						anchor: '90%',
						listeners: {
						      'select': function() {
							     
							    var rebateFlag = mchntForm.getForm().findField('mchtCup39I').getValue();
	                    	
		                    	if(rebateFlag == '1'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		Ext.getCmp('mchtCup41').disable();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('mchtCup41').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("mchtCup41").reset();
			                     	
		                    	}else if(rebateFlag == '2'){
		                    		Ext.getCmp('rebateACQW').hide();
		                    		Ext.getCmp('rebateACQH').show();
		                    		Ext.getCmp('mchtCup41').disable();
		                    		
		                    		Ext.getCmp('mchtCup40').allowBlank = false;
		                    		Ext.getCmp('mchtCup401').allowBlank = true;
		                    		Ext.getCmp('mchtCup41').allowBlank = true;
		                    		
		                    		mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("mchtCup41").reset();
		                    	}else if(rebateFlag == '3'){
		                    		Ext.getCmp('rebateACQH').hide();
		                    		Ext.getCmp('rebateACQW').show();
		                    		
		                    		Ext.getCmp('mchtCup401').allowBlank = false;
		                    		Ext.getCmp('mchtCup40').allowBlank = true;
		                    		Ext.getCmp('mchtCup41').enable();
		                    		Ext.getCmp('mchtCup41').allowBlank = false;
			                     	
			                     	
			                     	mchntForm.getForm().findField("mchtCup40").reset();
		                    		mchntForm.getForm().findField("mchtCup401").reset();
		                    		mchntForm.getForm().findField("mchtCup41").reset();
		                    	}
					        }}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQH',
		        	labelWidth:160,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(分)*',
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						id: 'mchtCup40',
						name: 'mchtCup40',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'rebateACQW',
		        	hidden: true,
		        	labelWidth:180,
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约商户内卡收单扣率(%%)*',
						maxLength: 8,
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						id: 'mchtCup401',
						name: 'mchtCup401',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'stlmCd',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '扣率算法代码*',
						maxLength: 5,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						disabled: true,
						id: 'mchtCup41',
						name: 'mchtCup41',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
	       				labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到收单行'],['2','2-清算到开户行'],
								   ['3','3-清算到银联']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '清算机构类型*',
						allowBlank: false,
						id: 'mchtCup42I',
						hiddenName: 'mchtCup42',
						anchor: '90%',
						listeners:{
							'select': function() {
								var settleBrhTp = mchntForm.getForm().findField('mchtCup42I').getValue();
								
								if(settleBrhTp == '1'){
									mchntForm.getForm().findField('mchtCup52').allowBlank = false;
									mchntForm.getForm().findField('mchtCup56').allowBlank = true;
									mchntForm.getForm().findField('mchtCup76').allowBlank = true;
								}
								if(settleBrhTp == '2'){
									mchntForm.getForm().findField('mchtCup52').allowBlank = false;
									mchntForm.getForm().findField('mchtCup56').allowBlank = false;
									mchntForm.getForm().findField('mchtCup76').allowBlank = false;
								}
								if(settleBrhTp == '3'){
									mchntForm.getForm().findField('mchtCup52').allowBlank = true;
									mchntForm.getForm().findField('mchtCup56').allowBlank = false;
									mchntForm.getForm().findField('mchtCup76').allowBlank = false;
								}
							}
						}
					}]
				}, {
					columnWidth: .44,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'textfield',
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '商户手续费决定索引',
						maxLength: 30,
						id: 'mchtCup52',
						name: 'mchtCup52',
						readOnly:true,
						anchor: '95%'
		        	}]
				},{
	        		columnWidth: .22,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'button',
                        text: '设置',
                        id: 'setButton',
                        width: 120,
                        handler: function() {
							feeWin.show();
							feeWin.center();
                        }
                    }]
				},{
	        		columnWidth: .66,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						methodName: 'getMchntCupBank',
						fieldLabel: '内卡清算资金开户行清算号',
						id: 'mchtCup56I',
						hiddenName: 'mchtCup56',
						anchor: '90%'
		        	}]
		        },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡清算资金开户行名称',
						id: 'mchtCup76',
						name: 'mchtCup76',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'textfield',
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行清算号*',
						allowBlank: false,
						id: 'mchtCup45',
						name: 'mchtCup45',
						anchor: '90%'
		        	}]
				},{
                	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
						id: 'mchtCup38',
						name: 'mchtCup38',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '内卡开始收单日期',
						format : 'Ymd',
						id: 'mchtCup46',
						name: 'mchtCup46',
						anchor: '90%'
					}]
				},{
		        	columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '清算账户账号',
                        maxLength: 40,
						id: 'mchtCup55',
						name: 'mchtCup55',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '异常时收单垫付标志*',
						allowBlank: false,
						value: '0',
						id: 'mchtCup43I',
						hiddenName: 'mchtCup43',
						anchor: '90%',
						listeners:{
							'select':function(){
								mchntForm.getForm().findField('mchtCup44I').reset();
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通商户'],['1','1-重点商户'],['2','2-收单T+1保障']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户优先标志*',
						allowBlank: false,
						value: '0',
						id: 'mchtCup44I',
						hiddenName: 'mchtCup44',
						anchor: '90%',
						listeners: {
							'select': function() {
								var advanceFlag = mchntForm.getForm().findField('mchtCup43I').getValue();
								var priorFlag = mchntForm.getForm().findField('mchtCup44I').getValue();
								
								if(advanceFlag =='' || advanceFlag == null){
									mchntForm.getForm().findField('mchtCup44I').reset();
									showMsg('请先选择异常时收单垫付标志！',mchntForm);
								}else{
									if(advanceFlag == '0' && priorFlag == '2'){
										mchntForm.getForm().findField('mchtCup44I').reset();
										showMsg('异常时收单垫付标志为否时商户优先标志只能为普通商户和重点商户！',mchntForm);
									}
									if(advanceFlag == '1' && (priorFlag =='0' || priorFlag =='1')){
										mchntForm.getForm().findField('mchtCup44I').reset();
										showMsg('异常时收单垫付标志为是时商户优先标志只能为收单T+1保障！',mchntForm);
									}
								}
							}
						}
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','1-清算到本店'],['2','2-清算到总店']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '商户清算模式*',
						allowBlank: false,
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup47I',
						hiddenName: 'mchtCup47',
						value: '1',
						anchor: '90%'
			    	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
			        	labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '周期结算类型*',
						allowBlank: false,
						id: 'mchtCup48I',
						hiddenName: 'mchtCup48',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '是否参加日间清算*',
						allowBlank: false,
						id: 'mchtCup49I',
						hiddenName: 'mchtCup49',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-否'],['1','1-是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '银联代理清算标识*',
						allowBlank: false,
						id: 'mchtCup50I',
						hiddenName: 'mchtCup50',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
		        		labelStyle: 'padding-left: 5px',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-部分回补'],['1','1-全部回补']],
							reader: new Ext.data.ArrayReader()
						}),
						fieldLabel: '垫付回补类型*',
						allowBlank: false,
						id: 'mchtCup51I',
						hiddenName: 'mchtCup51',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
		        		xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
		        		labelStyle: 'padding-left: 5px',
						fieldLabel: '本金清算周期',
						allowBlank: false,
						id: 'mchtCup53I',
						hiddenName: 'mchtCup53',
						value: '0',
						anchor: '90%'
		        	}]
		        },{
					columnWidth: .18,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv',
		        	labelWidth: 120,
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分段计费模式： 段1',
						id: 'mchtCup54I',
						hiddenName: 'mchtCup54',
						anchor: '85%',
						value: '0',
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			
	                			if(div =='0'){
	                				mchntForm.getForm().findField("mchtCup541I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup541I').readOnly = true;
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				mchntForm.getForm().findField("mchtCup541I").reset();
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup541I').readOnly = false;
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv1',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段2',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup541I',
						hiddenName: 'mchtCup541',
						value: '0',
						anchor: '80%',
						listeners: {
	                		'select': function() {
	                			var div = mchntForm.getForm().findField('mchtCup54I').getValue();
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			
	                			
	                			if(div1 =='0'){
	                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup542I').readOnly = true;
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div=='0'){
	                					mchntForm.getForm().findField("mchtCup541I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup542I").reset();
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				Ext.getCmp('mchtCup542I').readOnly = false;
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv2',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段3',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						id: 'mchtCup542I',
						hiddenName: 'mchtCup542',
						value: '0',
						anchor: '80%',
						listeners: {
	                		'select': function() {
	                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			
	                			if(div2 =='0'){
	                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
	                				Ext.getCmp('mchtCup543I').readOnly = true;
	                			}else{
	                				if(div1=='0'){
	                					mchntForm.getForm().findField("mchtCup542I").reset();
	                					showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                				}
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				
	                				Ext.getCmp('mchtCup543I').readOnly = false;
	                			}
	                		}
						}
		        	}]
				},{
					columnWidth: .62,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'feeDiv3',
		        	labelWidth: 30,
	       			items: [{
			        	xtype: 'combo',
			        	fieldLabel: '段4',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
//						readOnly: true,
						id: 'mchtCup543I',
						hiddenName: 'mchtCup543',
						value: '0',
						anchor: '13%',
						listeners: {
	                		'select': function() {
	                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
	                			var div3 = mchntForm.getForm().findField('mchtCup543I').getValue();
	                			
	                			if(div3 !='0' && div2=='0'){
	                				mchntForm.getForm().findField("mchtCup543I").reset();
	                				showMsg('上段不启用时此段只能为不启用！',mchntForm);
	                			}
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['00','00-无特殊计费类型'],['01','01-周期计费'],['02','02-微额打包'],['03','03-固定比例'],['04','04-县乡优惠'],['05','05-大商户优惠']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						id: 'mchtCup57I',
						hiddenName: 'mchtCup57',
						value:'00',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			mchntForm.getForm().findField('mchtCup58I').reset();
	                		}
						}
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-月结按MCC计费'],['1','1-月结不按MCC计费'],['2','2-普通商户'],['3','3-三农商户'],
								   ['4','4-大商户1级'],['5','5-大商户2级'],['6','6-大商户3级'],['7','7-无特殊计费档次'] ],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						id: 'mchtCup58I',
						hiddenName: 'mchtCup58',
						value:'7',
						anchor: '90%',
						listeners: {
	                		'select': function() {
	                			var feeSpeT = Ext.getCmp("mchtCup57I").getValue();
	                			var feeSpe = Ext.getCmp("mchtCup58I").getValue();
	                			
	                			if(feeSpeT == null || feeSpeT ==''){
	                				mchntForm.getForm().findField('mchtCup58I').reset();
	                				showMsg('请先选择特殊计费类型！',mchntForm);
	                			}else{
	                				if(feeSpeT =='01'&& feeSpe !='0' && feeSpe !='1'){
		                				mchntForm.getForm().findField('mchtCup58I').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择月结按MCC计费或月结不按MCC计费！',mchntForm);
		                			}else if(feeSpeT =='04'&& feeSpe !='2' && feeSpe !='3'){
		                				mchntForm.getForm().findField('mchtCup58I').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择普通商户或三农商户！',mchntForm);
		                			}else if(feeSpeT =='05'&& feeSpe !='4' && feeSpe !='5' && feeSpe !='6'){
		                				mchntForm.getForm().findField('mchtCup58I').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择大商户1级或大商户2级或大商户3级！',mchntForm);
		                			}else if((feeSpeT =='00' || feeSpeT =='02' || feeSpeT =='03') && feeSpe !='7'){
		                				mchntForm.getForm().findField('mchtCup58I').reset();
		                				showMsg('此种的特殊计费类型的特殊计费档次只能选择无特殊计费档次！',mchntForm);
		                			}
	                			}
	                		}
						}
		        	}]
				}]
			},{
                title:'分润',
                layout:'column',
                id: 'rate',
                frame: true,
                items: [{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'basecomboselect',
				        baseParams: 'REASON_CODE',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '登记原因码*',
                        allowBlank: false,
                        id: 'mchtCup59I',
                        hiddenName: 'mchtCup59',
                        anchor: '90%'
                    }]
				} ,{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-正常结算类'],['1','1-特殊结算类（普通月结）'],['2','2-特殊结算类（包月类）'],['3','3-特殊结算类（月结封顶、保底类）']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算类型标识',
						id: 'mchtCup60I',
                        hiddenName: 'mchtCup60',
						anchor: '90%'
						
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费清算周期',
						value: '0',
						id: 'mchtCup61I',
                        hiddenName: 'mchtCup61',
						anchor: '90%'
		        	}]
				}, {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-固定比例（封顶、保底）'],['1','1-固定金额'],['2','2-算法代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-单笔',
						id: 'mchtCup62I',
                        hiddenName: 'mchtCup62',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-单笔',
						id: 'mchtCup63I',
                        hiddenName: 'mchtCup63',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '手续费扣率-单笔(%%)',
						maxLength: 8,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'mchtCup64',
                        name: 'mchtCup64',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-单笔(分)', 
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'mchtCup65',
                        name: 'mchtCup65',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'mchtCup66',
                        name: 'mchtCup66',
                        value: '999999999999',
						anchor: '90%'
                    }]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '保底值-单笔*(分)',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'mchtCup67',
                        name: 'mchtCup67',
                        value: '0',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-单笔',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'mchtCup68',
						name: 'mchtCup68',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-封顶/保底'],['1','1-固定金额'],['2','2-计费代码']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收费类型-月结类',
						id: 'mchtCup69I',
						hiddenName: 'mchtCup69',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '封顶、保底信息-月结类',
						id: 'mchtCup70I',
						hiddenName: 'mchtCup70',
						anchor: '90%'
		        	}]
				} , {
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '固定金额-月结类（分）',
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'mchtCup71',
						name: 'mchtCup71',
						value: '0',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '封顶值-月结*（分）',
                        maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        id: 'mchtCup72',
                        name: 'mchtCup72',
                        value: '999999999999',
						anchor: '90%'
                    }]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '保底值-月结类(分)',
						maxLength: 15,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText:'该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						id: 'mchtCup73',
						name: 'mchtCup73',
						value: '0',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '算法代码-月结类',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'mchtCup74',
						name: 'mchtCup74',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润方式（CUPS侧）',
						maxLength: 5,
						vtype: 'isOverMax',
						id: 'mchtCup75',
						name: 'mchtCup75',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润代码',
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'mchtCup80',
						name: 'mchtCup80',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .99,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['AH057&AH058&#','AH057(商户分润确定)-按比例计费-AH058(商户分润算法)'],
								   ['AH057&0000000001&#','AH057(商户分润确定)-固定金额-0000000001(全国临时使用)']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户分润手续费索引*',
						allowBlank: false,
						value: 'AH057&AH058&#',
						id: 'mchtCup77I',
						hiddenName: 'mchtCup77',
						anchor: '60%'
		        	}]
		        }, {
	        		columnWidth: .99,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
					    baseParams: 'MCHNT_CUP_FATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润算法',
						id: 'mchtCup79I',
						hiddenName: 'mchtCup79',
						anchor: '60%'
		        	}]
				},{
	        		columnWidth: .15,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 150,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润角色： 分润机构1',
						id: 'mchtCup781',
						name: 'mchtCup781',
						anchor: '90%'
		        	}]
				},{
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构2',
						id: 'mchtCup782',
						name: 'mchtCup782',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构3',
						id: 'mchtCup783',
						name: 'mchtCup783',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构4',
						id: 'mchtCup784',
						name: 'mchtCup784',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 60,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '分润机构5',
						id: 'mchtCup785',
						name: 'mchtCup785',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '开户行',
						id: 'mchtCup786',
						name: 'mchtCup786',
						anchor: '90%'
		        	}]
				}, {
					columnWidth: .11,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth: 40,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单行',
						id: 'mchtCup787',
						name: 'mchtCup787',
						anchor: '90%'
		        	}]
				}]
			},{
                title:'其他',
                layout:'column',
                id: 'other',
                frame: true,
                items: [{
                    columnWidth: .25,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:260,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '报表生成标志位图* ：  是否生成汇总类报表',
                        id: 'mchtCup82',
						name: 'mchtCup82',
						checked: true,
						anchor: '90%'
                    }]
                  },{
                  	columnWidth: .18,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '是否生成扣账垫付类报表',
                        id: 'mchtCup821',
						name: 'mchtCup821',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
                  	columnWidth: .52,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:140,
		        	html:'<br/>',
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '是否生成交易明细类报表',
                        id: 'mchtCup822',
						name: 'mchtCup822',
						checked: true,
                        anchor: '90%'
                    }]
                  },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	labelWidth:190,
	       			items: [{
                        xtype: 'checkbox',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户属性位图* : 是否收支两条线',//填充到10位
                        id: 'mchtCup81',
						name: 'mchtCup81',
                        anchor: '90%'
                    }]
                  }]
			}]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
            	subSave();
            }
        },{
            text: '重置',
            handler: function() {
            	hasSub = false;
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
        }]
    });
      
    //外部加入监听
    Ext.getCmp("mchtCup56I").on('select',function(){
    	T20401.getInfo(Ext.getCmp("mchtCup56I").getValue(),function(ret){
    		if(ret=='0'){
    				showErrorMsg("找不到相应信息",grid);
    				mchntForm.getForm().findField('mchtCup56I').reset();
    				mchntForm.getForm().findField('mchtCup76').reset();
    				mchntForm.getForm().findField('mchtCup45').reset();
    				return;
    		}
    		
            var mbfBankInfo = Ext.decode(ret.substring(1,ret.length-1));
            Ext.getCmp("mchtCup76").setValue(mbfBankInfo.bankname);
  			Ext.getCmp("mchtCup45").setValue(mbfBankInfo.dpbankno);
        });
    });
    
    function subSave(){
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		
		// 验证必填表单是否填写了
		if (frm.isValid()) {
			
			btn.disable();
			frm.submit({
				url: 'T20401Action.asp?method=add',
				waitTitle : '请稍候',
				waitMsg : '正在提交表单数据,请稍候...',
				success : function(form, action) {
					showSuccessAlert(action.result.msg,mchntForm);
					btn.enable();
					frm.reset();
				},
				failure : function(form,action) {
					btn.enable();
					showErrorMsg(action.result.msg,mchntForm);
				},
				params: {
					txnId: '20401',
					subTxnId: '01'
				}
		});
		
	} else {
		// 自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'sign'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic' || tab == 'sign' || tab == 'settle' || tab == 'rate'|| tab == 'other'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	}
    );
	}}
    




    //为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    });


	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});