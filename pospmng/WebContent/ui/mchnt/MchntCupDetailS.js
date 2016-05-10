

//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){

	var mchntMccStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    
	SelectOptionsDWR.getComboData('MCHNT_TP_CUP',function(ret){
		mchntMccStore.loadData(Ext.decode(ret));
	});
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntCupInfoTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mcht_no'
		},[
			{name: 'mcht_no',mapping: 'mcht_no'},
			{name: 'inner_acq_inst_id',mapping: 'inner_acq_inst_id'},
			{name: 'acq_inst_id_code',mapping: 'acq_inst_id_code'},
			{name: 'inst_id',mapping: 'inst_id'},
			{name: 'mcht_nm',mapping: 'mcht_nm'},
			{name: 'mcht_short_cn',mapping: 'mcht_short_cn'},
			{name: 'area_no',mapping: 'area_no'},
			{name: 'inner_stlm_ins_id',mapping: 'inner_stlm_ins_id'},
			{name: 'mcht_type',mapping: 'mcht_type'},
			{name: 'mcht_status',mapping: 'mcht_status'},
			{name: 'acq_area_cd',mapping: 'acq_area_cd'},
			{name: 'settle_flg',mapping: 'settle_flg'},
			{name: 'conn_inst_cd',mapping: 'conn_inst_cd'},
			{name: 'mchnt_tp_grp',mapping: 'mchnt_tp_grp'},
			{name: 'mchnt_srv_tp',mapping: 'mchnt_srv_tp'},
			{name: 'real_mcht_tp',mapping: 'real_mcht_tp'},
			{name: 'settle_area_cd',mapping: 'settle_area_cd'},
			{name: 'mcht_acq_curr',mapping: 'mcht_acq_curr'},
			{name: 'conn_type',mapping: 'conn_type'},
			{name: 'currcy_cd',mapping: 'currcy_cd'},
			{name: 'deposit_flag',mapping: 'deposit_flag'},
			{name: 'res_pan_flag',mapping: 'res_pan_flag'},
			{name: 'res_track_flag',mapping: 'res_track_flag'},
			{name: 'process_flag',mapping: 'process_flag'},
			{name: 'cntry_code',mapping: 'cntry_code'},
			{name: 'ch_store_flag',mapping: 'ch_store_flag'},
			{name: 'mcht_tp_reason',mapping: 'mcht_tp_reason'},
			{name: 'mcc_md',mapping: 'mcc_md'},
			{name: 'rebate_flag',mapping: 'rebate_flag'},
			{name: 'mcht_acq_rebate',mapping: 'mcht_acq_rebate'},
			{name: 'rebate_stlm_cd',mapping: 'rebate_stlm_cd'},
			{name: 'reason_code',mapping: 'reason_code'},
			{name: 'rate_type',mapping: 'rate_type'},
			{name: 'fee_cycle',mapping: 'fee_cycle'},
			{name: 'fee_type',mapping: 'fee_type'},
			{name: 'limit_flag',mapping: 'limit_flag'},
			{name: 'fee_rebate',mapping: 'fee_rebate'},
			{name: 'settle_amt',mapping: 'settle_amt'},
			{name: 'amt_top',mapping: 'amt_top'},
			{name: 'amt_bottom',mapping: 'amt_bottom'},
			{name: 'disc_cd',mapping: 'disc_cd'},
			{name: 'fee_type_m',mapping: 'fee_type_m'},
			{name: 'limit_flag_m',mapping: 'limit_flag_m'},
			{name: 'settle_amt_m',mapping: 'settle_amt_m'},
			{name: 'amt_top_m',mapping: 'amt_top_m'},
			{name: 'amt_bottom_m',mapping: 'amt_bottom_m'},
			{name: 'disc_cd_m',mapping: 'disc_cd_m'},
			{name: 'fee_rate_type',mapping: 'fee_rate_type'},
			{name: 'settle_bank_no',mapping: 'settle_bank_no'},
			{name: 'settle_bank_type',mapping: 'settle_bank_type'},
			{name: 'advanced_flag',mapping: 'advanced_flag'},
			{name: 'prior_flag',mapping: 'prior_flag'},
			{name: 'open_stlno',mapping: 'open_stlno'},
			{name: 'feerate_index',mapping: 'feerate_index'},
			{name: 'rate_role',mapping: 'rate_role'},
			{name: 'rate_disc',mapping: 'rate_disc'},
			{name: 'cycle_mcht',mapping: 'cycle_mcht'},
			{name: 'mac_chk_flag',mapping: 'mac_chk_flag'},
			{name: 'fee_div_mode',mapping: 'fee_div_mode'},
			{name: 'settle_mode',mapping: 'settle_mode'},
			{name: 'attr_bmp',mapping: 'attr_bmp'},
			{name: 'cycle_settle_type',mapping: 'cycle_settle_type'},
			{name: 'report_bmp',mapping: 'report_bmp'},
			{name: 'day_stlm_flag',mapping: 'day_stlm_flag'},
			{name: 'cup_stlm_flag',mapping: 'cup_stlm_flag'},
			{name: 'adv_ret_flag',mapping: 'adv_ret_flag'},
			{name: 'mcht_file_flag',mapping: 'mcht_file_flag'},
			{name: 'fee_act',mapping: 'fee_act'},
			{name: 'licence_no',mapping: 'licence_no'},
			{name: 'licence_add',mapping: 'licence_add'},
			{name: 'principal',mapping: 'principal'},
			{name: 'comm_tel',mapping: 'comm_tel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'mana_cred_tp',mapping: 'mana_cred_tp'},
	      	{name: 'mana_cred_no',mapping: 'mana_cred_no'},
			{name: 'capital_sett_cycle',mapping: 'capital_sett_cycle'},
			{name: 'card_in_start_date',mapping: 'card_in_start_date'},
			{name: 'settle_acct',mapping: 'settle_acct'},
			{name: 'mcht_e_nm',mapping: 'mcht_e_nm'},
			{name: 'settle_bank',mapping: 'settle_bank'},
			{name: 'rate_no',mapping: 'rate_no'},
			{name: 'card_in_settle_bank',mapping: 'card_in_settle_bank'},
			{name: 'fee_spe_type',mapping: 'fee_spe_type'},
			{name: 'fee_spe_gra',mapping: 'fee_spe_gra'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

    var custom;
	var customEl;


	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		autoScroll  : true,
		frame: true,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
	        	xtype: 'tabpanel',
	        	id: 'tab',
	        	frame: true,
	            plain: false,
	            activeTab: 0,
	            height: 540,
	            deferredRender: false,
	            enableTabScroll: true,
	            labelWidth: 150,
	        	items:[{
	        		title:'基本信息',
	                id: 'basic',
	                frame: true,
	                disable:true,
					layout:'column',
	                items: [{
	        			columnWidth: .33,
		            	layout: 'form',
		        		items: [{
					        xtype: 'combofordispaly',
					        baseParams: 'MCHT_CUP_TYPE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户服务类型*',
							id: 'idmchnt_srv_tp',
							hiddenName: 'mchnt_srv_tp',
							allowBlank: false,
							anchor: '90%',
							value: '00'
				        }]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户号*',
							maxLength: '15',
							vtype: 'isOverMax',
							id: 'mcht_no',
							name: 'mcht_no',
							allowBlank: false,
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
		        			xtype: 'combofordispaly',
					        baseParams: 'CUP_CONN_TYPE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '联接方式*',
							id: 'idconn_type',
							hiddenName: 'conn_type',
							allowBlank: false,
							anchor: '90%',
							value: 'S'
						}]
		        	},{
		        		columnWidth: .33,
		        		xtype: 'panel',
				        layout: 'form',
			       		items: [{
					        xtype: 'combofordispaly',
					        baseParams: 'CUP_BRH_S',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡收单机构*',
							allowBlank: false,
							hiddenName: 'inner_acq_inst_id',
							anchor: '90%'
				        }]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'combofordispaly',
				        	baseParams: 'CUP_BRH_S',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '代理机构*',
							allowBlank: false,
							hiddenName: 'acq_inst_id_code',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
					        xtype: 'combofordispaly',
					        baseParams: 'CUP_BRH_S',
							hiddenName: 'inner_stlm_ins_id',
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
					        xtype: 'displayfield',
//					        baseParams: 'CUP_BRH_S',
//							hiddenName: 'inst_id',
							id: 'inst_id',
				        	name: 'inst_id',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '所属平台机构*',
							allowBlank: false,
							anchor: '90%'
				        }]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
					        xtype: 'displayfield',
//					        baseParams: 'CUP_BRH_S',
//							hiddenName: 'conn_inst_cd',
					        id: 'conn_inst_cd',
				        	name: 'conn_inst_cd',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '收单接入机构*',
							allowBlank: false,
							blankText: '请选接入机构',
							anchor: '90%'
				        }]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
				        		labelStyle: 'padding-left: 5px',
								store: new Ext.data.ArrayStore({
									fields: ['valueField','displayField'],
									data: [['156','156-中国']],
									reader: new Ext.data.ArrayReader()
								}),
								fieldLabel: '国家代码*',
								allowBlank: false,
								id: 'cntry_codeI',
								hiddenName: 'cntry_code',
								value: '156',
								anchor: '90%'
				        	}]
					},{
			        	columnWidth: .33,
			        	id: 'mchtNmPanel',
			        	xtype: 'panel',
			        	layout: 'form',
			       		items: [{
			       			xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户中文名称*',
							maxLength: '60',
							allowBlank: false,
							vtype: 'isOverMax',
							id: 'mcht_nm',
							anchor: '90%'
						}]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户中文简写*',
							maxLength: '40',
							vtype: 'isOverMax',
							id: 'mcht_short_cn',
		  				    allowBlank: false,
							blankText: '请输入中文名称简写',
							name: 'mcht_short_cn',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'displayfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '英文名称',
								maxLength: 40,
								vtype: 'isOverMax',
								regex: /^\w+[\w\s]+\w+$/,
								regexText:'英文名称必须是字母，如Bill Gates',
								id: 'mcht_e_nm',
								name: 'mcht_e_nm',
								anchor: '90%'
				        	}]
					},{
			        	columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'displayfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '行政区划码*',
								allowBlank: false,
								id: 'area_no',
								name: 'area_no',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'combofordispaly',
								labelStyle: 'padding-left: 5px',
								methodName: 'getAreaCode',
								fieldLabel: '受理地区码*',
								hiddenName: 'acq_area_cd',
								allowBlank: false,
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
								xtype: 'combofordispaly',
								labelStyle: 'padding-left: 5px',
								methodName: 'getAreaCode',
								fieldLabel: '清算地区码*',
								hiddenName: 'settle_area_cd',
								allowBlank: false,
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	store: mchntMccStore,
				        		displayField: 'displayField',
								valueField: 'valueField',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户交易类型*',
								maxLength: 4,
								allowBlank: false,
								vtype: 'isOverMax',
								id: 'mcht_type',
								name: 'mcht_type',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	store: mchntMccStore,
				        		displayField: 'displayField',
								valueField: 'valueField',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '真实商户类型*',
								maxLength: 4,
								allowBlank: false,
								vtype: 'isOverMax',
								id: 'real_mcht_tp',
								name: 'real_mcht_tp',
								anchor: '90%'
				        	}]
					},{
			        	columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
								xtype: 'combofordispaly',
					        	baseParams: 'MCHNT_GRP_CUP',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户组别*',
								allowBlank: false,
								hiddenName: 'mchnt_tp_grp',
								id:'mchnt_tp_grpI',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	baseParams: 'MCHNT_TYPE_CUP',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '商户状态*',
								maxLength: 40,
								vtype: 'isOverMax',
								allowBlank: false,
								id: 'mcht_statusI',
								hiddenName: 'mcht_status',
								value:'9',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	baseParams: 'APP_REASON',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '套用商户类型原因',
								vtype: 'isOverMax',
								id: 'mcht_tp_reasonI',
								hiddenName: 'mcht_tp_reason',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	baseParams: 'MCC_MD',
								labelStyle: 'padding-left: 5px',
								fieldLabel: 'MCC套用依据',
								id: 'mcc_mdI',
								hiddenName: 'mcc_md',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .33,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
					        	baseParams: 'CH_STORE_FLAG',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '连锁店标识*',
								width:150,
								id: 'ch_store_flagI',
								hiddenName: 'ch_store_flag',
								allowBlank: false,
								anchor: '90%',
								'listeners':{
	                        		'select': function() {
	                        			mchntForm.getForm().findField('mcht_file_flagI').reset();
	                        		}
								}
				        	}]
					},{
	                  	columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
	                        xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-不生成'],['1','1-生成自身流水文件'],['2','2-生成子商户流水文件']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '商户文件生成标志*',
	                        id: 'mcht_file_flagI',
							hiddenName: 'mcht_file_flag',
							allowBlank: false,
	                        anchor: '90%',
	                        'listeners':{
	                        	'select': function() {
								     
								    var storeFlag = mchntForm.getForm().findField('ch_store_flagI').getValue();
								    var fileFlag = mchntForm.getForm().findField('mcht_file_flagI').getValue();
								    
								    if(storeFlag == ''|| storeFlag == null){
								    	mchntForm.getForm().findField('mcht_file_flagI').reset();
								    	showMsg('请先选择连锁店标识！',mchntForm);
								    }else{
								    	if(storeFlag != '1' && fileFlag =='2'){
									    	mchntForm.getForm().findField('mcht_file_flagI').reset();
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
					        	xtype: 'combofordispaly',
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
								id: 'mcht_acq_currI',
								hiddenName: 'mcht_acq_curr',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .5,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
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
								id: 'currcy_cdI',
								hiddenName: 'currcy_cd',
								anchor: '90%'
				        	}]
					},{
						columnWidth: .5,
		                layout: 'form',
		                items: [{
		                        xtype: 'checkbox',
		                        labelStyle: 'padding-left: 5px',
		                        fieldLabel: '是否向商户收取押金 *',
		                        disabled: true,
		                        id: 'deposit_flag',
		                        name: 'deposit_flag'
		                    }]
	                },{
	                	columnWidth: .5,
		                layout: 'form',
		                items: [{
		                        xtype: 'checkbox',
		                        labelStyle: 'padding-left: 5px',
		                        fieldLabel: '商户是否保存卡号 *',
		                        disabled: true,
		                        id: 'res_pan_flag',
		                        name: 'res_pan_flag'
		                    }]
	                },{
	                	columnWidth: .5,
		                layout: 'form',
		                items: [{
		                        xtype: 'checkbox',
		                        labelStyle: 'padding-left: 5px',
		                        fieldLabel: '商户是否保存磁道信息*',
		                        disabled: true,
		                        id: 'res_track_flag',
		                        name: 'res_track_flag'
		                    }]
	                },{
	                	columnWidth: .5,
		                layout: 'form',
		                items: [{
		                        xtype: 'checkbox',
		                        labelStyle: 'padding-left: 5px',
		                        fieldLabel: '是否向商户收取服务费 *',
		                        disabled: true,
		                        id: 'process_flag',
		                        name: 'process_flag'
		                    }]
	                },{
	                	columnWidth: .5,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'checkbox',
								labelStyle: 'padding-left: 5px',
								fieldLabel: '是否为周期商户*',
								disabled: true,
								id: 'cycle_mcht',
								name: 'cycle_mcht'
				        	}]
					},{
						columnWidth: .5,
			        	layout: 'form',
			        	xtype: 'panel',
			       		items: [{
					        	xtype: 'combofordispaly',
								store: new Ext.data.ArrayStore({
									fields: ['valueField','displayField'],
									data: [['1','1-校验MAC、交易加密'],['2','2-校验MAC、交易不加密'],
										   ['3','3-不校验MAC、交易加密'],['4','4-不校验MAC、交易不加密']],
									reader: new Ext.data.ArrayReader()
								}),
								labelStyle: 'padding-left: 5px',
								fieldLabel: 'MAC校验和交易加密标志',
								id: 'mac_chk_flagI',
								hiddenName: 'mac_chk_flag',
								anchor: '90%'
				        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	id:'accountHide1',
			        	layout: 'form',
		       			items: [{
							xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '营业执照编号',
							maxLength: 20,
							vtype: 'isOverMax',
							id: 'licence_no',
							name: 'licence_no',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '营业地址',
							maxLength: 60,
							vtype: 'isOverMax',
							id: 'licence_add',
							name: 'licence_add',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '负责人',
							maxLength: 10,
							vtype: 'isOverMax',
							id: 'principal',
							name: 'principal',
							anchor: '90%'
			        	}]
			        },{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '固定电话',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							maxLength: 15,
							vtype: 'isOverMax',
							id: 'comm_tel',
							name: 'comm_tel',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法人代表',
							maxLength: 50,
							vtype: 'lengthRange50',
							id: 'manager',
							name: 'manager',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
				        	baseParams: 'CERTIFICATE_CUP',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法人代表证件类型*',
							allowBlank: false,
							hiddenName: 'mana_cred_tp',
							id: 'mana_cred_tpI',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .5,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '法人证件号*',
							maxLength: 20,
							vtype: 'isOverMax',
							id: 'mana_cred_no',
							name: 'mana_cred_no',
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
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-固定比列'],['2','2-固定金额'],
									   ['3','3-算法描述']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '扣率算法标识*',
							id: 'rebate_flagI',
							hiddenName: 'rebate_flag',
							allowBlank: false,
							anchor: '90%',
							listeners: {
							      'select': function() {
								     
								    var rebateFlag = mchntForm.getForm().findField('rebate_flagI').getValue();
		                    	
			                    	if(rebateFlag == '1'){
			                    		Ext.getCmp('rebateACQH').hide();
			                    		Ext.getCmp('rebateACQW').show();
			                    		
			                    		Ext.getCmp('mchtCup401').allowBlank = false;
			                    		Ext.getCmp('mchtCup40').allowBlank = true;
			                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
			                    		
			                    		mchntForm.getForm().findField("mchtCup40").reset();
			                    		mchntForm.getForm().findField("mchtCup401").reset();
			                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
				                     	
			                    	}else if(rebateFlag == '2'){
			                    		Ext.getCmp('rebateACQW').hide();
			                    		Ext.getCmp('rebateACQH').show();
			                    		
			                    		Ext.getCmp('mchtCup40').allowBlank = false;
			                    		Ext.getCmp('mchtCup401').allowBlank = true;
			                    		Ext.getCmp('rebate_stlm_cd').allowBlank = true;
			                    		
			                    		mchntForm.getForm().findField("mchtCup40").reset();
			                    		mchntForm.getForm().findField("mchtCup401").reset();
			                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
			                    	}else if(rebateFlag == '3'){
			                    		Ext.getCmp('rebateACQH').hide();
			                    		Ext.getCmp('rebateACQW').show();
			                    		
			                    		Ext.getCmp('mchtCup401').allowBlank = false;
			                    		Ext.getCmp('mchtCup40').allowBlank = true;
			                    		Ext.getCmp('mchtCup41').enable();
			                    		Ext.getCmp('rebate_stlm_cd').allowBlank = false;
				                     	
				                     	
				                     	mchntForm.getForm().findField("mchtCup40").reset();
			                    		mchntForm.getForm().findField("mchtCup401").reset();
			                    		mchntForm.getForm().findField("rebate_stlm_cd").reset();
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
				        	xtype: 'displayfield',
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
				        	xtype: 'displayfield',
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
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '扣率算法代码*',
							maxLength: 5,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							disabled: true,
							id: 'rebate_stlm_cd',
							name: 'rebate_stlm_cd',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       				xtype: 'combofordispaly',
		       				labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-清算到收单行'],['2','2-清算到开户行'],
									   ['3','3-清算到银联']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '清算机构类型*',
							allowBlank: false,
							id: 'settle_bank_typeI',
							hiddenName: 'settle_bank_type',
							anchor: '90%'
						}]
					},{
						columnWidth: .44,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'textfield',
			        		labelStyle: 'padding-left: 5px',
							fieldLabel: '商户手续费决定索引*',
							id: 'fee_act',
							name: 'fee_act',
							disabled: true,
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .22,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
	                        xtype: 'button',
	                        text: '设置',
	                        id: 'setButtonDtl',
	                        disabled: true,
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
		       				xtype: 'combofordispaly',
		       				methodName: 'getMchntCupBank',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡清算资金开户行清算号',
							id: 'card_in_settle_bankI',
							hiddenName: 'card_in_settle_bank',
							anchor: '90%'
			        	}]
			        },{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡清算资金开户行名称',
							id: 'settle_bank_no',
							name: 'settle_bank_no',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'displayfield',
			        		labelStyle: 'padding-left: 5px',
							fieldLabel: '开户行清算号*',
							allowBlank: false,
							id: 'open_stlno',
							name: 'open_stlno',
							anchor: '90%'
			        	}]
					},{
	                	columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '开户行',
							id: 'settle_bank',
							name: 'settle_bank',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       				xtype: 'displayfield',
		       				labelStyle: 'padding-left: 5px',
							fieldLabel: '内卡开始收单日期',
							format : 'Ymd',
							id: 'card_in_start_date',
							name: 'card_in_start_date',
							anchor: '90%'
						}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '清算账户账号',
	                        maxLength: 40,
							id: 'settle_acct',
							name: 'settle_acct',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
							xtype: 'combofordispaly',
							labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-否'],['1','1-是']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '异常时收单垫付标志*',
							allowBlank: false,
							id: 'advanced_flagI',
							hiddenName: 'advanced_flag',
							anchor: '90%',
							listeners:{
								'select':function(){
									mchntForm.getForm().findField('prior_flagI').reset();
								}
							}
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'combofordispaly',
			        		labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-普通商户'],['1','1-重点商户'],['2','2-收单T+1保障']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '商户优先标志*',
							allowBlank: false,
							id: 'mprior_flagI',
							hiddenName: 'prior_flag',
							anchor: '90%',
							listeners: {
								'select': function() {
									var advanceFlag = mchntForm.getForm().findField('advanced_flagI').getValue();
									var priorFlag = mchntForm.getForm().findField('prior_flagI').getValue();
									
									if(advanceFlag =='' || advanceFlag == null){
										mchntForm.getForm().findField('prior_flagI').reset();
										showMsg('请先选择异常时收单垫付标志！',mchntForm);
									}else{
										if(advanceFlag == '0' && priorFlag == '2'){
											mchntForm.getForm().findField('prior_flagI').reset();
											showMsg('异常时收单垫付标志为否时商户优先标志只能为普通商户和重点商户！',mchntForm);
										}
										if(advanceFlag == '1' && (priorFlag =='0' || priorFlag =='1')){
											mchntForm.getForm().findField('prior_flagI').reset();
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
	                        xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['1','1-清算到本店'],['2','2-清算到总店']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '商户清算模式*',
							allowBlank: false,
							labelStyle: 'padding-left: 5px',
							id: 'settle_modeI',
							hiddenName: 'settle_mode',
							anchor: '90%'
				    	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
				        	labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '周期结算类型*',
							allowBlank: false,
							id: 'cycle_settle_typeI',
							hiddenName: 'cycle_settle_type',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'combofordispaly',
			        		labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-否'],['1','1-是']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '是否参加日间清算*',
							allowBlank: false,
							id: 'day_stlm_flagI',
							hiddenName: 'day_stlm_flag',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
		       			xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       				xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-否'],['1','1-是']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '银联代理清算标识*',
							allowBlank: false,
							id: 'cup_stlm_flagI',
							hiddenName: 'cup_stlm_flag',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'combofordispaly',
			        		labelStyle: 'padding-left: 5px',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-部分回补'],['1','1-全部回补']],
								reader: new Ext.data.ArrayReader()
							}),
							fieldLabel: '垫付回补类型*',
							allowBlank: false,
							id: 'adv_ret_flagI',
							hiddenName: 'adv_ret_flag',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
			        		xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
								reader: new Ext.data.ArrayReader()
							}),
			        		labelStyle: 'padding-left: 5px',
							fieldLabel: '本金清算周期*',
							id: 'capital_sett_cycleI',
							hiddenName: 'capital_sett_cycle',
							anchor: '90%'
			        	}]
			        },{
						columnWidth: .18,
			        	xtype: 'panel',
			        	layout: 'form',
			        	id: 'feeDiv',
			        	labelWidth: 120,
		       			items: [{
				        	xtype: 'combofordispaly',
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
				        	xtype: 'combofordispaly',
				        	fieldLabel: '段2',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							id: 'mchtCup541I',
							hiddenName: 'mchtCup541',
							anchor: '80%',
							listeners: {
		                		'select': function() {
		                			var div1 = mchntForm.getForm().findField('mchtCup541I').getValue();
		                			
		                			if(div1 =='0'){
		                				mchntForm.getForm().findField("mchtCup542I").setValue('0');
		                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
		                				Ext.getCmp('mchtCup542I').readOnly = true;
		                				Ext.getCmp('mchtCup543I').readOnly = true;
		                			}else{
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
				        	xtype: 'combofordispaly',
				        	fieldLabel: '段3',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							id: 'mchtCup542I',
							hiddenName: 'mchtCup542',
							anchor: '80%',
							listeners: {
		                		'select': function() {
		                			var div2 = mchntForm.getForm().findField('mchtCup542I').getValue();
		                			
		                			if(div2 =='0'){
		                				mchntForm.getForm().findField("mchtCup543I").setValue('0');
		                				Ext.getCmp('mchtCup543I').readOnly = true;
		                			}else{
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
				        	xtype: 'combofordispaly',
				        	fieldLabel: '段4',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-不启用'],['1','1-按比例计费'],['2','2-固定金额'],['3','3-按笔数']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							id: 'mchtCup543I',
							hiddenName: 'mchtCup543',
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
		       				xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['00','00-无特殊计费类型'],['01','01-周期计费'],['02','02-微额打包'],['03','03-固定比例'],['04','04-县乡优惠'],['05','05-大商户优惠']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '特殊计费类型',
							id: 'fee_spe_typeI',
							hiddenName: 'fee_spe_type',
							anchor: '90%',
							listeners: {
		                		'select': function() {
		                			mchntForm.getForm().findField('fee_spe_graI').reset();
		                		}
							}
			        	}]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
		       				xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-月结按MCC计费'],['1','1-月结不按MCC计费'],['2','2-普通商户'],['3','3-三农商户'],
									   ['4','4-大商户1级'],['5','5-大商户2级'],['6','6-大商户3级'],['7','7-无特殊计费档次'] ],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '特殊计费档次',
							id: 'fee_spe_graI',
							hiddenName: 'fee_spe_gra',
							anchor: '90%',
							listeners: {
		                		'select': function() {
		                			var feeSpeT = Ext.getCmp("fee_spe_typeI").getValue();
		                			var feeSpe = Ext.getCmp("fee_spe_graI").getValue();
		                			
		                			if(feeSpeT == null || feeSpeT ==''){
		                				mchntForm.getForm().findField('fee_spe_graI').reset();
		                				showMsg('请先选择特殊计费类型！',mchntForm);
		                			}else{
		                				if(feeSpeT =='01'&& feeSpe !='0' && feeSpe !='1'){
			                				mchntForm.getForm().findField('fee_spe_graI').reset();
			                				showMsg('此种的特殊计费类型的特殊计费档次只能选择月结按MCC计费或月结不按MCC计费！',mchntForm);
			                			}else if(feeSpeT =='04'&& feeSpe !='2' && feeSpe !='3'){
			                				mchntForm.getForm().findField('fee_spe_graI').reset();
			                				showMsg('此种的特殊计费类型的特殊计费档次只能选择普通商户或三农商户！',mchntForm);
			                			}else if(feeSpeT =='05'&& feeSpe !='4' && feeSpe !='5' && feeSpe !='6'){
			                				mchntForm.getForm().findField('fee_spe_graI').reset();
			                				showMsg('此种的特殊计费类型的特殊计费档次只能选择大商户1级或大商户2级或大商户3级！',mchntForm);
			                			}else if((feeSpeT =='00' || feeSpeT =='02' || feeSpeT =='03') && feeSpe !='7'){
			                				mchntForm.getForm().findField('fee_spe_graI').reset();
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
	                        xtype: 'combofordispaly',
					        baseParams: 'REASON_CODE',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '登记原因码*',
	                        allowBlank: false,
	                        id: 'reason_codeI',
	                        hiddenName: 'reason_code',
	                        anchor: '90%'
	                    }]
					} ,{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-正常结算类'],['1','1-特殊结算类（普通月结）'],['2','2-特殊结算类（包月类）'],['3','3-特殊结算类（月结封顶、保底类）']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '结算类型标识',
							id: 'rate_typeI',
	                        hiddenName: 'rate_type',
							anchor: '90%'
							
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-日'],['1','1-月'],['2','2-季'],['3','3-半年'],['4','4-年']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '手续费清算周期',
							id: 'fee_cycleI',
	                        hiddenName: 'fee_cycle',
							anchor: '90%'
			        	}]
					}, {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-固定比列（封顶、保底）'],['1','1-固定金额'],['2','2-算法代码']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '收费类型-单笔',
							id: 'fee_typeI',
	                        hiddenName: 'fee_type',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '封顶、保底信息-单笔',
							id: 'limit_flagI',
	                        hiddenName: 'limit_flag',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '手续费扣率-单笔(%%)',
							maxLength: 8,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							id: 'fee_rebate',
	                        name: 'fee_rebate',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '固定金额-单笔(分)', 
							maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							id: 'settle_amt',
	                        name: 'settle_amt',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
	                        xtype: 'displayfield',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '封顶值-单笔*(分)',
	                        maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
	                        id: 'amt_top',
	                        name: 'amt_top',
							anchor: '90%'
	                    }]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '保底值-单笔*(分)',
	                        maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
	                        id: 'amt_bottom',
	                        name: 'amt_bottom',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
		       			xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '算法代码-单笔',
							maxLength: 5,
							vtype: 'isOverMax',
							id: 'disc_cd',
							name: 'disc_cd',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
		       			xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-封顶/保底'],['1','1-固定金额'],['2','2-计费代码']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '收费类型-月结类',
							id: 'fee_type_mI',
							hiddenName: 'fee_type_m',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','0-普通'],['1','1-封顶'],['2','2-保底']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '封顶、保底信息-月结类',
							id: 'limit_flag_mI',
							hiddenName: 'limit_flag_m',
							anchor: '90%'
			        	}]
					} , {
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '固定金额-月结类（分）',
							maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							id: 'settle_amt_m',
							name: 'settle_amt_m',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
	                        xtype: 'displayfield',
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '封顶值-月结*（分）',
	                        maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
	                        id: 'amt_top_m',
	                        name: 'amt_top_m',
							anchor: '90%'
	                    }]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '保底值-月结类(分)',
							maxLength: 15,
							vtype: 'isOverMax',
							regex: /^[0-9]+$/,
							regexText:'该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							id: 'amt_bottom_m',
							name: 'amt_bottom_m',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '算法代码-月结类',
							maxLength: 5,
							vtype: 'isOverMax',
							id: 'disc_cd_m',
							name: 'disc_cd_m',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '分润方式（CUPS侧）',
							maxLength: 5,
							vtype: 'isOverMax',
							id: 'fee_rate_type',
							name: 'fee_rate_type',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'displayfield',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '分润代码',
							maxLength: 20,
							vtype: 'isOverMax',
							id: 'rate_no',
							name: 'rate_no',
							anchor: '90%'
			        	}]
					},{
		        		columnWidth: .99,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['AH057&AH058&#','AH057(商户分润确定)-按比例计费-AH058(商户分润算法)'],
									   ['AH057&0000000001&#','AH057(商户分润确定)-固定金额-0000000001(全国临时使用)']],
								reader: new Ext.data.ArrayReader()
							}),
							labelStyle: 'padding-left: 5px',
							fieldLabel: '商户分润手续费索引*',
							id: 'feerate_indexI',
							hiddenName: 'feerate_index',
							anchor: '60%'
			        	}]
			        }, {
		        		columnWidth: .99,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
				        	xtype: 'combofordispaly',
					    	baseParams: 'MCHNT_CUP_FATE',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '分润算法',
							id: 'rate_discI',
							hiddenName: 'rate_disc',
							anchor: '60%'
			        	}]
					}, {
		        		columnWidth: .15,
			        	xtype: 'panel',
			        	layout: 'form',
			        	labelWidth: 150,
		       			items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '分润角色： 分润机构1',
							disabled: true,
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
							disabled: true,
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
							disabled: true,
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
							disabled: true,
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
							disabled: true,
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
							disabled: true,
							id: 'mchtCup786',
							name: 'mchtCup786',
							anchor: '90%'
			        	}]
					},{
						columnWidth: .11,
			        	xtype: 'panel',
			        	layout: 'form',
			        	labelWidth: 40,
		       			items: [{
				        	xtype: 'checkbox',
							labelStyle: 'padding-left: 5px',
							fieldLabel: '收单行',
							disabled: true,
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
	                        disabled: true,
	                        id: 'mchtCup82',
							name: 'mchtCup82',
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
	                        disabled: true,
	                        id: 'mchtCup821',
							name: 'mchtCup821',
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
	                        disabled: true,
	                        id: 'mchtCup822',
							name: 'mchtCup822',
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
	                        disabled: true,
	                        id: 'attr_bmp',
							name: 'attr_bmp',
	                        anchor: '90%'
	                    }]
	                  }]
				}]
        	}]
    	});

    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1320,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();

				//签约商户内卡收单扣率
				var rebateFlag = baseStore.getAt(0).data.rebate_flag;
				var mchtAcqRebate = baseStore.getAt(0).data.mcht_acq_rebate;
				if(rebateFlag == '1' || rebateFlag == '3'){
            		Ext.getCmp('rebateACQH').hide();
            		Ext.getCmp('rebateACQW').show();
            		mchntForm.getForm().findField("mchtCup401").setValue(mchtAcqRebate);
            	}else if(rebateFlag == '2'){
            		Ext.getCmp('rebateACQW').hide();
            		Ext.getCmp('rebateACQH').show();
            		mchntForm.getForm().findField("mchtCup40").setValue(mchtAcqRebate);
            	}
            	
            	//分段计费
            	var feeDivMode = baseStore.getAt(0).data.fee_div_mode;
            	mchntForm.getForm().findField("mchtCup54I").setValue(feeDivMode.substr(0,1));
            	mchntForm.getForm().findField("mchtCup541I").setValue(feeDivMode.substr(1,1));
				mchntForm.getForm().findField("mchtCup542I").setValue(feeDivMode.substr(2,1));
				mchntForm.getForm().findField("mchtCup543I").setValue(feeDivMode.substr(3,1));
				
				//分润机构
				var rateRole = baseStore.getAt(0).data.rate_role;
            	mchntForm.getForm().findField("mchtCup781").setValue(rateRole.substr(0,1));
            	mchntForm.getForm().findField("mchtCup782").setValue(rateRole.substr(1,1));
				mchntForm.getForm().findField("mchtCup783").setValue(rateRole.substr(2,1));
				mchntForm.getForm().findField("mchtCup784").setValue(rateRole.substr(3,1));
				mchntForm.getForm().findField("mchtCup785").setValue(rateRole.substr(4,1));
				mchntForm.getForm().findField("mchtCup786").setValue(rateRole.substr(5,1));
				mchntForm.getForm().findField("mchtCup787").setValue(rateRole.substr(6,1));
				
				//报表生成标志位图
				var reportBmp = baseStore.getAt(0).data.report_bmp;
            	mchntForm.getForm().findField("mchtCup82").setValue(reportBmp.substr(0,1));
            	mchntForm.getForm().findField("mchtCup821").setValue(reportBmp.substr(1,1));
				mchntForm.getForm().findField("mchtCup822").setValue(reportBmp.substr(2,1));
				
				//报表生成标志位图
				var attrBmp = baseStore.getAt(0).data.attr_bmp;
            	mchntForm.getForm().findField("attr_bmp").setValue(attrBmp.substr(0,1));
				
//				mchntForm.getForm().findField("feerate_index").setValue('0000000001&0000000001&#');
				
				detailWin.setTitle('商户详细信息[商户编号：' + mchntId + ']');
				detailWin.show();


			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}