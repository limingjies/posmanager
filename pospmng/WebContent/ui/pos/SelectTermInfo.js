	var selectTermInfoNamespace = {}; 

    
    selectTermInfoNamespace.termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 370,
        width: 600,
        frame: true,
        items: [{
                title: '基本信息',
                layout: 'column',
                frame: true,
                items: [{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
	                    xtype: 'displayfield',
			            fieldLabel: '商户中文名',
			            id: 'txn22Upd',
			            name: 'txn22Upd'
             }]
           },{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27Upd',
                        name: 'txn27Upd'
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccUpd',
                        name: 'termMccUpd'
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                 	xtype: 'combofordispaly',
                    fieldLabel: '所属合作伙伴',
                    baseParams: 'BRH_BELOW_BRANCH',
                    hiddenName: 'termBranchUpd'
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '联系电话',
                    id: 'contTelUpd',
                    name: 'contTelUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '产权属性',
                    baseParams: 'PROP_TP',
                    hiddenName: 'propTpU'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '收单服务机构',
                    baseParams: 'ORGAN',
                    id: 'propInsNmU',
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                id: "flagBox2",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '第三方分成比例',
                    id: 'termPara1Upd',
                    name: 'termPara1Upd',
                    readOnly: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '连接类型',
                    baseParams: 'CONNECT_MODE',
                    hiddenName: 'connectModeUpd'
                }]
            }/*,{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '签购单模板',
                    baseParams: 'MCHNT_MODEL',
                    hiddenName: 'misc1'
                }]
            
            }*/]
            },{
                title: '附加信息',
                layout: 'column',
                frame: true,
                items: [{
                      columnWidth: 1,
                      layout: 'form',
                      items:[{
                        xtype: 'combofordispaly',
                        fieldLabel: '终端类型',
                        baseParams: 'TERM_TYPE',
                        id: 'termTpU',
                        hiddenName: 'termTpUpd'
                      }]
                    },{
                id: 'accountBox2',
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号1',
                    id: 'financeCard1Upd',
                    name: 'financeCard1Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号2',
                    id: 'financeCard2Upd',
                    name: 'financeCard2Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号3',
                    id: 'financeCard3Upd',
                    name: 'financeCard3Upd'
                }]
            },{
                id: 'accountBox4',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '电话POS版本号',
                    id: 'termVersion',
                    name: 'termVersion'
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '终端安装地址',
                    readOnly: true,
                    id: 'termAddrUpd',
                    name: 'termAddrUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话1',
                    id: 'txn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话2',
                    id: 'txn15Upd',
                    name: 'txn15Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话3',
                    id: 'txn16Upd',
                    name: 'txn16Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话1',
                    hidden: true,
                    id: 'bindTel1Upd',
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话2',
                    hidden: true,
                    id: 'bindTel2Upd',
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话3',
                    hidden: true,
                    id: 'bindTel3Upd',
                    name: 'bindTel3Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignUpd',
                    name: 'keyDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignUpd',
                    name: 'paramDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignUpd',
                    name: 'icDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Upd',
                    name: 'reserveFlag1Upd',
                    disabled: true
                }]
            }]
            },{
                title: '交易信息',
                layout: 'column',
                id: 'info3Upd',
                frame: true,
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款支持期数',
                        id: 'txn35Upd',
                        name: 'txn35Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款限额',
                        id: 'txn36Upd',
                        name: 'txn36Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '消费单笔上限 ',
                        id: 'txn37Upd',
                        name: 'txn37Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货单笔上限 ',
                        id: 'txn38Upd',
                        name: 'txn38Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '转帐单笔上限 ',
                        id: 'txn39Upd',
                        name: 'txn39Upd'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货时间跨度 ',
                        id: 'txn40Upd',
                        name: 'txn40Upd'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1Upd',
                        name: 'param1Upd',
                        inputValue: '1',
                    	disabled: true
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2Upd',
                        name: 'param2Upd',
                        inputValue: '1',
                    	disabled: true
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3Upd',
                        name: 'param3Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4Upd',
                        name: 'param4Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5Upd',
                        name: 'param5Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6Upd',
                        name: 'param6Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7Upd',
                        name: 'param7Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8Upd',
                        name: 'param8Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9Upd',
                        name: 'param9Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10Upd',
                        name: 'param10Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11Upd',
                        name: 'param11Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12Upd',
                        name: 'param12Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13Upd',
                        name: 'param13Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财物POS） ',
                        id: 'param14Upd',
                        name: 'param14Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财物POS） ',
                        id: 'param15Upd',
                        name: 'param15Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16Upd',
                        name: 'param16Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17Upd',
                        name: 'param17Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18Upd',
                        name: 'param18Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19Upd',
                        name: 'param19Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20Upd',
                        name: 'param20Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21Upd',
                        name: 'param21Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22Upd',
                        name: 'param22Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23Upd',
                        name: 'param23Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付  ',
                        id: 'param24Upd',
                        name: 'param24Upd',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'termParaUpd',
                        name: 'termParaUpd'
                }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'recCrtTs',
                        name: 'recCrtTs'
                },{
                        xtype: 'hidden',
                        id: 'misc2Upd',
                        name: 'misc2Upd'
                }]
            }]
            }]
    });
    
    selectTermInfoNamespace.termForm = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        labelWidth: 100,
        waitMsgTarget: true,
        layout: 'form',
        items: [{
             xtype: 'displayfield',
             fieldLabel: '终端库存编号',
             labelStyle: 'padding-left: 5px',
             name: 'termIdUpd',
             id: 'termIdUpd'
         },{
             xtype: 'displayfield',
             fieldLabel: '商户编号',
             labelStyle: 'padding-left: 5px',
             name: 'mchnNoUpd',
             id: 'mchnNoU'
           },selectTermInfoNamespace.termPanel]
    });
    
    selectTermInfoNamespace.termWin = new Ext.Window({
        title: '终端信息',
        initHidden: true,
        header: true,
        frame: true,
        closable: true,
        modal: true,
        width: 626,
        autoHeight: true,
        layout: 'fit',
        items: [selectTermInfoNamespace.termForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false
    });
    
    function praseTermParam(val) {
    	
        var array = val.split("|");
        if(array[4] == undefined)
        	return 0;
        array[4] = array[4].substring(2,array[4].length);
        T30101.getTermParam(array[4],function(ret){
            var termParam = ret;
        for(var i=0;i<=23;i++){
            selectTermInfoNamespace.termForm.getForm().findField('param'+(i+1)+'Upd').setValue(termParam.substring(i,i+1));
        }
        
        selectTermInfoNamespace.termForm.getForm().findField("txn14Upd").setValue(array[0].substring(2,array[0].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn15Upd").setValue(array[1].substring(2,array[1].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn16Upd").setValue(array[2].substring(2,array[2].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn22Upd").setValue(array[3].substring(2,array[3].length));
        selectTermInfoNamespace.termForm.getForm().findField("txn27Upd").setValue(array[5].substring(2,array[5].length));
        selectTermInfoNamespace.termForm.getForm().findField("txn35Upd").setValue(array[12].substring(2,array[12].length).trim());
        selectTermInfoNamespace.termForm.getForm().findField("txn36Upd").setValue(array[13].substring(2,array[13].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn37Upd").setValue(array[14].substring(2,array[14].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn38Upd").setValue(array[15].substring(2,array[15].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn39Upd").setValue(array[16].substring(2,array[16].length).trim()/100);
        selectTermInfoNamespace.termForm.getForm().findField("txn40Upd").setValue(array[17].substring(2,array[17].length).trim());

        var value = Ext.getCmp('termTpU').getValue();
        if(value == '1')   
        {
           Ext.getCmp('accountBox2').show();
//           Ext.getCmp('financeCard1Upd').allowBlank = false;
        }else
	    {
	       Ext.getCmp('accountBox2').hide();
//           Ext.getCmp('financeCard1Upd').allowBlank = true;
	    }
        if(value == '3')
        	selectTermInfoNamespace.termPanel.get('info3Upd').setDisabled(true);
        else
        	selectTermInfoNamespace.termPanel.get('info3Upd').setDisabled(false);
        });
    };
    
    selectTermInfoNamespace.termInfoStore = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdUpd',mapping: 'id.termId'},
            {name: 'recCrtTs',mapping: 'id.recCrtTs'},
            {name: 'mchnNoU',mapping: 'mchtCd'},
            {name: 'termMccUpd',mapping: 'termMcc'},
            {name: 'termBranchUpd',mapping: 'termBranch'},
            {name: 'termSignStaU',mapping: 'termSignSta'},
            {name: 'termFactoryUpd',mapping: 'termFactory'},
            {name: 'termMachTpUpd',mapping: 'termMachTp'},
            {name: 'termIdIdU',mapping: 'termIdId'},
            {name: 'termVerUpd',mapping: 'termVer'},
            {name: 'termTpU',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'propTpU',mapping: 'propTp'},
            {name: 'propInsNmUpd',mapping: 'propInsNm'},
            {name: 'termBatchNmUpd',mapping: 'termBatchNm'},
            {name: 'termStlmDtUpd',mapping: 'termStlmDt'},
            {name: 'connectModeUpd',mapping: 'connectMode'},
            {name: 'financeCard1Upd',mapping: 'financeCard1'},
            {name: 'financeCard2Upd',mapping: 'financeCard2'},
            {name: 'financeCard3Upd',mapping: 'financeCard3'},
            {name: 'bindTel1Upd',mapping: 'bindTel1'},
            {name: 'bindTel2Upd',mapping: 'bindTel2'},
            {name: 'bindTel3Upd',mapping: 'bindTel3'},
            {name: 'termAddrUpd',mapping: 'termAddr'},
            {name: 'termPlaceUpd',mapping: 'termPlace'},
            {name: 'oprNmUpd',mapping: 'oprNm'},
            {name: 'termParaUpd',mapping: 'termPara'},
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'misc2Upd',mapping: 'misc2'}
        ]),
        autoLoad: false
    });

	function selectTermInfo(termId,recCrtTs) {
		
		selectTermInfoNamespace.termInfoStore.load({
	        params: {
	             storeId: 'getTermInfo',
	             termId: termId,
	             recCrtTs: recCrtTs
	        },
	        callback: function(records, options, success){
	        	
	            if(success){

	            	selectTermInfoNamespace.termForm.getForm().loadRecord(selectTermInfoNamespace.termInfoStore.getAt(0));
	            	var termPara = selectTermInfoNamespace.termForm.getForm().findField("termParaUpd").value;
	            	praseTermParam(termPara);
	            	selectTermInfoNamespace.termPanel.setActiveTab(0);
	            	selectTermInfoNamespace.termWin.show();
	            	selectTermInfoNamespace.termWin.center();
	            	if (selectTermInfoNamespace.termForm.getForm().findField("termTpU").getValue() == '3') {
							Ext.getCmp('accountBox4').show();
							selectTermInfoNamespace.termForm.getForm().findField("termVersion").setValue(Ext.getCmp('misc2Upd').getValue().substring(0,4));
							Ext.getCmp('termVersion').allowBlank = false;
						} else {
							Ext.getCmp('accountBox4').hide();
							selectTermInfoNamespace.termForm.getForm().findField("termVersion").setValue("");
							Ext.getCmp('termVersion').allowBlank = true;
						}
	            }else{
	            	selectTermInfoNamespace.termWin.hide();
	            	alert("载入终端信息失败，请稍后再试!")
	            }
	        }
	    });
	};