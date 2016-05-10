//新版终端详细信息
function selectTermCupInfo(termId) {
    
		// 直联商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('CUP_MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
	var termInfoStoreDtl = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'term_idD',mapping: 'term_id'},
            {name: 'mcht_noD',mapping: 'mcht_no'},
            {name: 'in_inst_idD',mapping: 'in_inst_id'},
            {name: 'inst_idD',mapping: 'inst_id'},
            {name: 'term_typeD',mapping: 'term_type'},
            {name: 'term_stateD',mapping: 'term_state'},
            {name: 'out_flagD',mapping: 'out_flag'},
            {name: 'IC_flagD',mapping: 'IC_flag'},
            {name: 'call_typeD',mapping: 'call_type'},
            {name: 'currcy_code_transD',mapping: 'currcy_code_trans'},
            {name: 'key_indexD',mapping: 'key_index'},
            {name: 'enc_key_modeD',mapping: 'enc_key_mode'},
            {name: 'trans_key_modeD',mapping: 'trans_key_mode'},
            {name: 'term_enc_typeD',mapping: 'term_enc_type'},
            {name: 'term_enc_key_1D',mapping: 'term_enc_key_1'},
            {name: 'term_trans_key_1D',mapping: 'term_trans_key_1'},
            {name: 'open_timeD',mapping: 'open_time'},
            {name: 'close_timeD',mapping: 'close_time'},
            {name: 'para_down_flagD',mapping: 'para_down_flag'},
            {name: 'tms_down_flagD',mapping: 'tms_down_flag'},
            {name: 'pub_key_flagD',mapping: 'pub_key_flag'},
            {name: 'IC_para_flagD',mapping: 'IC_para_flag'},
            {name: 'emv_flagD',mapping: 'emv_flag'},
            {name: 'key_lengthD',mapping: 'key_length'},
            {name: 'pik_lengthD',mapping: 'pik_length'},
            {name: 'mak_lengthD',mapping: 'mak_length'},
            {name: 'mac_algD',mapping: 'mac_alg'},
            {name: 'country_codeD',mapping: 'country_code'},
            {name: 'connect_modeD',mapping: 'connect_mode'},
            {name: 'track_key_lenD',mapping: 'track_key_len'},
            {name: 'term_shareD',mapping: 'term_share'},
            {name: 'term_call_numD',mapping: 'term_call_num'},
            {name: 'country_codeD',mapping: 'country_code'},
            {name: 'addressD',mapping: 'address'},
            {name: 'nameD',mapping: 'name'},
            {name: 'tel_phoneD',mapping: 'tel_phone'}
        ]),
        autoLoad: false
    });
	
	var termPanelDtl = new Ext.TabPanel({
        activeTab: 0,
        height: 450,
        width: 780,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'basicD',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单机构*',
						readOnly:true,
						id: 'in_inst_idID',
						hiddenName: 'in_inst_idD',
						allowBlank: false,
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
						fieldLabel: '默认交易货币*',
						id: 'currcy_code_transID',
						hiddenName: 'currcy_code_transD',
						readOnly:true,
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-中国']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '国家代码*',
						readOnly:true,
						id: 'country_codeID',
						hiddenName: 'country_codeD',
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
					    baseParams: 'CUP_CONN_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联接方式*',
						readOnly:true,
						id: 'connect_modeID',
						hiddenName: 'connect_modeD',
						allowBlank: false,
						anchor: '90%',
						value: 'P'
			        }]
			     },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'TERM_STATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端状态*',
						readOnly:true,
						id: 'term_stateID',
						hiddenName: 'term_stateD',
						allowBlank: false,
						anchor: '90%',
						value: '9'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'TERM_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端类型*',
						id: 'term_typeID',
						hiddenName: 'term_typeD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '签约',
                layout: 'column',
                id: 'signD',
                items: [{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否开启外卡机构收单*',
	                        id: 'out_flagID',
	                        hiddenName: 'out_flagD',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combofordispaly',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否支持IC卡*',
	                        id: 'IC_flagID',
	                        hiddenName: 'IC_flagD',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '开通时间',
						format : 'Ymd',
						id: 'open_timeD',
						name: 'open_timeD',
						anchor: '90%'
					}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '注销时间',
						format : 'Ymd',
						id: 'close_timeD',
						name: 'close_timeD',
						anchor: '90%'
					}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'CALL_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '拨入类型*',
						id: 'call_typeID',
						hiddenName: 'call_typeD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端分润方式*',
						id: 'term_shareD',
						name: 'term_shareD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端正常拨入号码*',
						id: 'term_call_numD',
						name: 'term_call_numD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地址*',
						id: 'addressD',
						name: 'addressD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人*',
						id: 'nameD',
						name: 'nameD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系电话*',
						id: 'tel_phoneD',
						name: 'tel_phoneD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '参数配置',
                id: 'paragramD',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'TERM_CUP_KEY_INDEX',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '当前密钥信息索引*',
						id: 'key_indexID',
						hiddenName: 'key_indexD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combofordispaly',
				        baseParams: 'TERM_KEY_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥方式*',
						id: 'enc_key_modeID',
						hiddenName: 'enc_key_modeD',
						allowBlank: false,
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
							data: [['00','手工传输'],['01','ftp文件'],
								   ['02','自定义']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥方式*',
						id: 'trans_key_modeID',
						hiddenName: 'trans_key_modeD',
						allowBlank: false,
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
							data: [['1','DES'],['3','3DES']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密类型*',
						id: 'term_enc_typeID',
						hiddenName: 'term_enc_typeD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥1*',
						id: 'term_enc_key_1D',
						name: 'term_enc_key_1D',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥1*',
						id: 'term_trans_key_1ID',
						name: 'term_trans_key_1D',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '参数下载标识*',
						id: 'para_down_flagID',
						hiddenName: 'para_down_flagD',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'TMS参数下载标识*',
						id: 'tms_down_flagID',
						hiddenName: 'tms_down_flagD',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公钥下载标识*',
						id: 'pub_key_flagID',
						hiddenName: 'pub_key_flagD',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'IC参数下载标识*',
						id: 'IC_para_flagID',
						hiddenName: 'IC_para_flagD',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMV参数下载标识*',
						id: 'emv_flagID',
						hiddenName: 'emv_flagD',
						allowBlank: false,
						value:'0',
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端主密钥长度*',
						id: 'key_lengthID',
						hiddenName: 'key_lengthD',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'PIK长度*',
						id: 'pik_lengthID',
						hiddenName: 'pik_lengthD',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAK长度*',
						id: 'mak_lengthID',
						hiddenName: 'mak_lengthD',
						allowBlank: false,
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
							data: [['01','ANSI9.9'],['02','ANSI9.19'],['03','XOR']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAC算法*',
						id: 'mac_algID',
						hiddenName: 'mac_algD',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '磁道加密密钥长度*',
						id: 'track_key_lenID',
						hiddenName: 'track_key_lenD',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            }]
    });
    
    var termFormDtl = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        labelWidth: 150,
        width: 800,
        waitMsgTarget: true,
        layout: 'form',
        items: [{
             xtype: 'displayfield',
             fieldLabel: '终端号*',
             labelStyle: 'padding-left: 5px',
             name: 'term_idD',
             id: 'term_idD'
         },{
            xtype: 'combofordispaly',
        	store: mchntStore,
            displayField: 'displayField',
            valueField: 'valueField',
            mode: 'local',
            allowBlank: false,
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户号*',
			id: 'idmcht_noD',
			hiddenName: 'mcht_noD'
           },termPanelDtl]
    });
    
    var termWinDtl = new Ext.Window({
        title: '终端信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: true,
        modal: true,
        width: 800,
        autoHeight: true,
        layout: 'fit',
        items: [termFormDtl],
        iconCls: 'logo',
        resizable: false
    });
    

	termInfoStoreDtl.load({
	    params: {
	         storeId: 'getTermCupTmp',
	         termId: termId
	    },
	    callback: function(records, options, success){
	        if(success){
	        	termFormDtl.getForm().loadRecord(termInfoStoreDtl.getAt(0));
	        	termPanelDtl.setActiveTab(0);
	        	termWinDtl.show();
	        	termWinDtl.center();
	        	
	        }else{
	        	termWinDtl.hide();
	        	alert("载入终端信息失败，请稍后再试!")
	        }
	    }
	});
};