//修改终端
function TermCupInfoEdit(termId) {
    
	// 直联商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('CUP_MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
	var termInfoStoreEdt = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'term_idE',mapping: 'term_id'},
            {name: 'mcht_noE',mapping: 'mcht_no'},
            {name: 'in_inst_idE',mapping: 'in_inst_id'},
            {name: 'inst_idE',mapping: 'inst_id'},
            {name: 'term_typeE',mapping: 'term_type'},
            {name: 'term_stateE',mapping: 'term_state'},
            {name: 'out_flagE',mapping: 'out_flag'},
            {name: 'IC_flagE',mapping: 'IC_flag'},
            {name: 'call_typeE',mapping: 'call_type'},
            {name: 'currcy_code_transE',mapping: 'currcy_code_trans'},
            {name: 'key_indexE',mapping: 'key_index'},
            {name: 'enc_key_modeE',mapping: 'enc_key_mode'},
            {name: 'trans_key_modeE',mapping: 'trans_key_mode'},
            {name: 'term_enc_typeE',mapping: 'term_enc_type'},
            {name: 'term_enc_key_1E',mapping: 'term_enc_key_1'},
            {name: 'term_trans_key_1E',mapping: 'term_trans_key_1'},
            {name: 'open_timeE',mapping: 'open_time'},
            {name: 'close_timeE',mapping: 'close_time'},
            {name: 'para_down_flagE',mapping: 'para_down_flag'},
            {name: 'tms_down_flagE',mapping: 'tms_down_flag'},
            {name: 'pub_key_flagE',mapping: 'pub_key_flag'},
            {name: 'IC_para_flagE',mapping: 'IC_para_flag'},
            {name: 'emv_flagE',mapping: 'emv_flag'},
            {name: 'key_lengthE',mapping: 'key_length'},
            {name: 'pik_lengthE',mapping: 'pik_length'},
            {name: 'mak_lengthE',mapping: 'mak_length'},
            {name: 'mac_algE',mapping: 'mac_alg'},
            {name: 'country_codeE',mapping: 'country_code'},
            {name: 'connect_modeE',mapping: 'connect_mode'},
            {name: 'track_key_lenE',mapping: 'track_key_len'},
            {name: 'term_shareE',mapping: 'term_share'},
            {name: 'term_call_numE',mapping: 'term_call_num'},
            {name: 'country_codeE',mapping: 'country_code'},
            {name: 'addressE',mapping: 'address'},
            {name: 'nameE',mapping: 'name'},
            {name: 'tel_phoneE',mapping: 'tel_phone'}
        ]),
        autoLoad: false
    });
    
//    termInfoStoreEdt.removeAll();
    termInfoStoreEdt.load({
	    params: {
	         storeId: 'getTermCupInfo',
	         termId: termId
	    },
	    callback: function(records, options, success){
	        if(success){
	        	termFormEdt.getForm().loadRecord(termInfoStoreEdt.getAt(0));
	        	termPanelEdt.setActiveTab(0);
	        	termFormEdt.getForm().clearInvalid();
	        	termWinEdt.show();
	        	termWinEdt.center();
	        	
	        }else{
	        	termWinEdt.hide();
	        	alert("载入终端信息失败，请稍后再试!")
	        }
	    }
	});
	
	var termPanelEdt = new Ext.TabPanel({
		id: 'tabEdt',
    	frame: true,
        plain: false,
        activeTab: 0,
        height: 450,
        width: 760,
        deferredRender: false,
        enableTabScroll: true,
        labelWidth: 150,
        items: [{
                title: '基本信息',
                id: 'basicE',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
			        	xtype: 'combo',
			        	store: mchntStore,
	                    displayField: 'displayField',
	                    valueField: 'valueField',
	                    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户号*',
						readOnly:true,
						id: 'idmcht_noE',
						hiddenName: 'mcht_noE',
						allowBlank: false,
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单机构*',
						readOnly:true,
						id: 'in_inst_idIE',
						hiddenName: 'in_inst_idE',
						allowBlank: false,
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
						fieldLabel: '默认交易货币*',
						id: 'currcy_code_transIE',
						hiddenName: 'currcy_code_transE',
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
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-中国']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '国家代码*',
						readOnly:true,
						id: 'country_codeIE',
						hiddenName: 'country_codeE',
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
					    baseParams: 'CUP_CONN_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联接方式*',
						readOnly:true,
						id: 'connect_modeIE',
						hiddenName: 'connect_modeE',
						allowBlank: false,
						anchor: '90%',
						value: 'P'
			        }]
			     },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_STATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端状态*',
						readOnly:true,
						id: 'term_stateIE',
						hiddenName: 'term_stateE',
						allowBlank: false,
						anchor: '90%',
						value: '9'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端类型*',
						id: 'term_typeIE',
						hiddenName: 'term_typeE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '签约',
                layout: 'column',
                id: 'signE',
                items: [{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否开启外卡机构收单*',
	                        id: 'out_flagIE',
	                        hiddenName: 'out_flagE',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否支持IC卡*',
	                        id: 'IC_flagIE',
	                        hiddenName: 'IC_flagE',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '开通时间',
						format : 'Ymd',
						id: 'open_timeE',
						name: 'open_timeE',
						anchor: '90%'
					}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '注销时间',
						format : 'Ymd',
						id: 'close_timeE',
						name: 'close_timeE',
						anchor: '90%'
					}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CALL_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '拨入类型*',
						id: 'call_typeIE',
						hiddenName: 'call_typeE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端分润方式*',
						id: 'term_shareE',
						name: 'term_shareE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端正常拨入号码*',
						id: 'term_call_numE',
						name: 'term_call_numE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地址*',
						id: 'addressE',
						name: 'addressE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人*',
						id: 'nameE',
						name: 'nameE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系电话*',
						id: 'tel_phoneE',
						name: 'tel_phoneE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '参数配置',
                id: 'paragramE',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_KEY_INDEX',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '当前密钥信息索引*',
						id: 'key_indexIE',
						hiddenName: 'key_indexE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_KEY_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥方式*',
						id: 'enc_key_modeIE',
						hiddenName: 'enc_key_modeE',
						allowBlank: false,
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
							data: [['00','手工传输'],['01','ftp文件'],
								   ['02','自定义']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥方式*',
						id: 'trans_key_modeIE',
						hiddenName: 'trans_key_modeE',
						allowBlank: false,
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
							data: [['1','DES'],['3','3DES']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密类型*',
						id: 'term_enc_typeIE',
						hiddenName: 'term_enc_typeE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥1*',
						id: 'term_enc_key_1E',
						name: 'term_enc_key_1E',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥1*',
						id: 'term_trans_key_1IE',
						name: 'term_trans_key_1E',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '参数下载标志*',
						id: 'para_down_flagIE',
						hiddenName: 'para_down_flagE',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'TMS参数下载标志*',
						id: 'tms_down_flagIE',
						hiddenName: 'tms_down_flagE',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公钥下载标志*',
						id: 'pub_key_flagIE',
						hiddenName: 'pub_key_flagE',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'IC参数下载标志*',
						id: 'IC_para_flagIE',
						hiddenName: 'IC_para_flagE',
						value:'0',
						allowBlank: false,
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
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMV参数下载标志*',
						id: 'emv_flagIE',
						hiddenName: 'emv_flagE',
						allowBlank: false,
						value:'0',
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端主密钥长度*',
						id: 'key_lengthIE',
						hiddenName: 'key_lengthE',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'PIK长度*',
						id: 'pik_lengthIE',
						hiddenName: 'pik_lengthE',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAK长度*',
						id: 'mak_lengthIE',
						hiddenName: 'mak_lengthE',
						allowBlank: false,
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
							data: [['01','ANSI9.9'],['02','ANSI9.19'],['03','XOR']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAC算法*',
						id: 'mac_algIE',
						hiddenName: 'mac_algE',
						allowBlank: false,
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
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '磁道加密密钥长度*',
						id: 'track_key_lenIE',
						hiddenName: 'track_key_lenE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            }]
    });
    
    var termFormEdt = new Ext.form.FormPanel({
    	id: 'formEdt',
        frame: true,
        autoHeight: true,
        labelWidth: 150,
        width: 810,
        waitMsgTarget: true,
        layout: 'form',
        items: [{
             xtype: 'displayfield',
             fieldLabel: '终端号',
             labelStyle: 'padding-left: 5px',
             name: 'term_idE',
             id: 'term_idE'
         },termPanelEdt]
    });
    
    var termWinEdt = new Ext.Window({
        title: '终端修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 810,
        autoHeight: true,
        layout: 'fit',
        items: [termFormEdt],
        iconCls: 'logo',
        resizable: false,
        buttons: [{
			text: '确定',
			handler: function() {
				
//				termPanelEdt.setActiveTab("basicE");
//				termPanelEdt.setActiveTab("signE"); 
//				termPanelEdt.setActiveTab("paragramE"); 
				if(termFormEdt.getForm().isValid()) {
					
					termFormEdt.getForm().submitNeedAuthorise({
						url: 'T30401Action.asp?method=update',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWinEdt);
							//重新加载参数列表
//							grid.getStore().reload();
							//重置表单
							termFormEdt.getForm().reset();
                            termWinEdt.hide();
						},
						failure: function(form,action) {
//							termPanelEdt.setActiveTab('paragramE');
							showErrorMsg(action.result.msg,termWinEdt);
						},
						params: {
							term_id: termId,
							txnId: '30401',
							subTxnId: '02'
						}
					});
				}else {
                    var finded = true;
	                termFormEdt.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if(tab == 'basicE' || tab == 'signE' || tab == 'paragramE' ){
	                             termPanelEdt.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
				termFormEdt.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWinEdt.hide();
			}
		}]
    });
};