//新版终端详细信息
function selectTermInfoNew(termId,recCrtTs,grid) {
	var recTerm = grid.getSelectionModel().getSelected();
	var mchntNo = recTerm.get('mchntNo');
	var formTermRiskParamStore = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
           {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
           {name: 'debitSingleAmt',mapping: 'debitSingleAmt'},
           {name: 'creditMonAmt',mapping: 'creditMonAmt'},
           {name: 'debitMonAmt',mapping: 'debitMonAmt'},
           {name: 'creditDayAmt',mapping: 'creditDayAmt'},
           {name: 'debitDayAmt',mapping: 'debitDayAmt'},
           {name: 'remark',mapping: 'remark'},
           {name: 'termPara1',mapping: 'termPara1'}
           ]),
            autoLoad: false
        });
	var termInfoStoreDtl = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdDtl',mapping: 'id.termId'},
            {name: 'recCrtTsDtl',mapping: 'id.recCrtTs'},
            {name: 'mchnNoDtl',mapping: 'mchtCd'},
            {name: 'termMccDtl',mapping: 'termMcc'},
            {name: 'termBranchDtl',mapping: 'termBranch'},
            {name: 'termSignStaDtl',mapping: 'termSignSta'},
            {name: 'termFactoryDtl',mapping: 'termFactory'},
            {name: 'termMachTpDtl',mapping: 'termMachTp'},
            {name: 'termIdIdDtl',mapping: 'termIdId'},
            {name: 'termVerDtl',mapping: 'termVer'},
            {name: 'termTpDtl',mapping: 'termTp'},
            {name: 'contTelDtl',mapping: 'contTel'},
            {name: 'propTpDtl',mapping: 'propTp'},
            {name: 'propInsNmDtl',mapping: 'propInsNm'},
            {name: 'termBatchNmDtl',mapping: 'termBatchNm'},
            {name: 'termStlmDtDtl',mapping: 'termStlmDt'},
            {name: 'connectModeDtl',mapping: 'connectMode'},
            {name: 'financeCard1Dtl',mapping: 'financeCard1'},
            {name: 'financeCard2Dtl',mapping: 'financeCard2'},
            {name: 'financeCard3Dtl',mapping: 'financeCard3'},
            {name: 'bindTel1Dtl',mapping: 'bindTel1'},
            {name: 'bindTel2Dtl',mapping: 'bindTel2'},
            {name: 'bindTel3Dtl',mapping: 'bindTel3'},
            {name: 'termAddrDtl',mapping: 'termAddr'},
            {name: 'productCdDtl',mapping: 'productCd'},
            {name: 'termPlaceDtl',mapping: 'termPlace'},
            {name: 'oprNmDtl',mapping: 'oprNm'},
            {name: 'termParaDtl',mapping: 'termPara'},
            {name: 'termPara1Dtl',mapping: 'termPara2'},
            {name: 'termPara11Dtl',mapping: 'termPara1'},
            {name: 'keyDownSignDtl',mapping: 'keyDownSign'},
            {name: 'paramDownSignDtl',mapping: 'paramDownSign'},
            {name: 'icDownSignDtl',mapping: 'icDownSign'},
            {name: 'reserveFlag1Dtl',mapping: 'reserveFlag1'},
            {name: 'misc2ForDisp',mapping: 'misc2'},
            {name: 'misc2Dtl',mapping: 'misc2'},
            {name: 'misc1',mapping: 'misc1'},
            {name: 'txn22Dtl',mapping: 'txn22Dtl'},
            {name: 'txn27Dtl',mapping: 'txn27Dtl'},
            {name: 'termSerialNum',mapping: 'termSerialNum'},
            {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
            {name: 'creditDayAmt',mapping: 'creditDayAmt'},
            {name: 'creditMonAmt',mapping: 'creditMonAmt'},
            {name: 'debitSingleAmt',mapping: 'debitSingleAmt'},
            {name: 'debitDayAmt',mapping: 'debitDayAmt'},
            {name: 'debitMonAmt',mapping: 'debitMonAmt'}
        ]),
        autoLoad: false
    });
	
	var termPanelDtl = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
//        width: 620,
        defaults : {
					bodyStyle : 'padding-left: 10px'
		},
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
			            fieldLabel: '签购单名称',
			            id: 'txn22Dtl',
			            width:350,
			            name: 'txn22Dtl'
             }]
           },{
                    columnWidth: 1, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '商户英文名称',
                        id: 'txn27Dtl',
                        width:350,
                        name: 'txn27Dtl'
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccDtl',
                        name: 'termMccDtl'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items:[{
	                xtype: 'displayfield',
	                fieldLabel: '终端名称',
	                name: 'misc2ForDisp',
	                id: 'misc2ForDisp'
                }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                 	xtype: 'combofordispaly',
                    fieldLabel: '所属合作伙伴',
                    baseParams: 'BRH_BELOW_BRANCH',
                    hiddenName: 'termBranchDtl'
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '联系电话',
                    id: 'contTelDtl',
                    name: 'contTelDtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '产权属性',
                    baseParams: 'PROP_TP',
                    id:'idpropTpDtl',
                    hiddenName: 'propTpDtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '收单服务机构',
                    baseParams: 'ORGAN',
                    id: 'idpropInsNmDtl',
                    hiddenName: 'propInsNmDtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '第三方分成比例',
                    id: 'termPara1Dtl',
                    name: 'termPara1Dtl'
                }]
            },{
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '连接类型',
                    baseParams: 'CONNECT_MODE',
                    hiddenName: 'connectModeDtl'
                }]
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '签购单模板',
                    baseParams: 'MCHNT_MODEL',
                    hiddenName: 'misc1'
                }]
            
            }]
            },{
                title: '附加信息',
                layout: 'column',
                frame: true,
                items: [{
                      columnWidth: .5,
                      layout: 'form',
                      items:[{
                        xtype: 'combofordispaly',
                        fieldLabel: '终端类型',
                        baseParams: 'TERM_TYPE',
                        id: 'idtermTpDtl',
                        hiddenName: 'termTpDtl'
                      }]
                    },{

                        columnWidth: .5,
                        layout: 'form',
                        hidden: true,
                        items:[{
                          xtype: 'displayfield',
                          fieldLabel: '终端序列号',
                          //baseParams: 'TERM_TYPE',
                          id: 'termSerialNum',
                          hiddenName: 'termSerialNum'
                        }]
                      
                    },{
                        id: 'accountBox4Dtl',
                        hidden: true,
                        columnWidth: .5,
                        hidden: true,
                        layout: 'form',
                        items: [{
                            xtype: 'displayfield',
                            fieldLabel: '电话POS版本号',
                            id: 'termVersionDtl',
                            name: 'termVersionDtl'
                        }]
                    },{
                id: 'accountBox2Dtl',
                hidden: true,
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号1',
                    id: 'financeCard1Dtl',
                    name: 'financeCard1Dtl'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号2',
                    id: 'financeCard2Dtl',
                    name: 'financeCard2Dtl'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号3',
                    id: 'financeCard3Dtl',
                    name: 'financeCard3Dtl'
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '终端安装地址',
                    readOnly: true,
                    width:350,
                    id: 'termAddrDtl',
                    name: 'termAddrDtl'
                }]
            },{				
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
					fieldLabel : '终端SN号',
                    id: 'productCdDtl',
                    name: 'productCdDtl',
					maxLength : 50
                }]
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '所属厂商',
//                    baseParams: 'TERM_FACTURE',
                    hiddenName: 'termFactoryDtl'
                }]
            
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '终端型号',
                   // baseParams: 'TERM_MODEL',
                    hiddenName: 'termMachTpDtl',
                    id:"termMachTpDtl",
//                    store: storeTermModel,
                }]
            
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话1',
                    id: 'txn14Dtl',
                    name: 'txn14Dtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话2',
                    id: 'txn15Dtl',
                    name: 'txn15Dtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话3',
                    id: 'txn16Dtl',
                    name: 'txn16Dtl'
                }]
            } ,{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话1',
                    hidden: true,
                    id: 'bindTel1Dtl',
                    name: 'bindTel1Dtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话2',
                    hidden: true,
                    id: 'bindTel2Dtl',
                    name: 'bindTel2Dtl'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话3',
                    hidden: true,
                    id: 'bindTel3Dtl',
                    name: 'bindTel3Dtl'
                }]
            } ,{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignDtl',
                    name: 'keyDownSignDtl',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignDtl',
                    name: 'paramDownSignDtl',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignDtl',
                    name: 'icDownSignDtl',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Dtl',
                    name: 'reserveFlag1Dtl',
                    disabled: true
                }]
            }]
            },{
                title: '交易信息',
//                layout: 'column',
                id: 'info3Dtl',
                frame: true,
                items: [
                
                	{
                	 
                	xtype : 'fieldset',
				title : '交易权限',
				layout : 'column',
				// collapsible : true,
				labelWidth : 70,
				width: 570,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款支持期数',
                        id: 'txn35Dtl',
                        name: 'txn35Dtl'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '分期付款限额',
                        id: 'txn36Dtl',
                        name: 'txn36Dtl'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '消费单笔上限 ',
                        id: 'txn37Dtl',
                        name: 'txn37Dtl'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货单笔上限 ',
                        id: 'txn38Dtl',
                        name: 'txn38Dtl'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '转帐单笔上限 ',
                        id: 'txn39Dtl',
                        name: 'txn39Dtl'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'displayfield',
                        fieldLabel: '退货时间跨度 ',
                        id: 'txn40Dtl',
                        name: 'txn40Dtl'
                    }]
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1Dtl',
                        name: 'param1Dtl',
                        inputValue: '1',
                    }]
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费类 ',
                        id: 'param6Dtl',
                        name: 'param6Dtl',
                        inputValue: '1',
                     }]
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权类 ',
                        id: 'param2Dtl',
                        name: 'param2Dtl',
                        inputValue: '1',
                    }]
               
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8Dtl',
                        name: 'param8Dtl',
                        inputValue: '1',
                     }]
              
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9Dtl',
                        name: 'param9Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10Dtl',
                        name: 'param10Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11Dtl',
                        name: 'param11Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12Dtl',
                        name: 'param12Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13Dtl',
                        name: 'param13Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财物POS） ',
                        id: 'param14Dtl',
                        name: 'param14Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财物POS） ',
                        id: 'param15Dtl',
                        name: 'param15Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16Dtl',
                        name: 'param16Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17Dtl',
                        name: 'param17Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18Dtl',
                        name: 'param18Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19Dtl',
                        name: 'param19Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20Dtl',
                        name: 'param20Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21Dtl',
                        name: 'param21Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22Dtl',
                        name: 'param22Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23Dtl',
                        name: 'param23Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                hidden:true,
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付  ',
                        id: 'param24Dtl',
                        name: 'param24Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'termParaDtl',
                        name: 'termParaDtl'
                },{
                        xtype: 'hidden',
                        id: 'termPara11Dtl',
                        name: 'termPara11Dtl'
                }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'hidden',
                        id: 'recCrtTsDtl',
                        name: 'recCrtTsDtl'
                },{
                        xtype: 'hidden',
                        id: 'misc2Dtl',
                        name: 'misc2Dtl'
                }]
            }]
            },{
                	xtype : 'fieldset',
				title : '卡类型权限',
				layout : 'column',
				// collapsible : true,
				labelWidth : 70,
				width: 570,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [
				
					
                	{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '借记卡 ',
	                        id: 'param1Dtl1',
	                        name: 'param1Dtl1',
	                        inputValue: '1'
	                     }]
	                },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '贷记卡 ',
	                        id: 'param1Dtl2',
	                        name: 'param1Dtl2',
	                        inputValue: '1'
	                     }]
	                },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '准贷记卡 ',
	                        id: 'param1Dtl3',
	                        name: 'param1Dtl3',
	                        inputValue: '1'
	                    }]
	                },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '预付费卡 ',
	                        id: 'param1Dtl4',
	                        name: 'param1Dtl4',
	                        inputValue: '1'
	                    }]
	                }
	             ]},{}
            ]},{
            	  title: '风控参数信息',
	              id: 'info4Dtl',
	              frame: true,
	              items: [
	              	{

	     			id : 'riskParamInfo',
	    			xtype : 'fieldset',
	    			title : '终端限额信息<font color="red">（0表示不限额）</font>',
	    			layout : 'column',
	    			labelWidth : 90,
	    			items : [{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '月累计借记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'debitMonAmt',
	    					name:'tblRiskParamMng.debitMonAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			},{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'creditMonAmt',
	    					name:'tblRiskParamMng.creditMonAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			},{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'debitDayAmt',
	    					name:'tblRiskParamMng.debitDayAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			},{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'creditDayAmt',
	    					name:'tblRiskParamMng.creditDayAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			},{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'debitSingleAmt',
	    					name:'tblRiskParamMng.debitSingleAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			},{
	    				columnWidth : .5,
	    				layout : 'form',
	    				items : [{
	    					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
	    					xtype: 'numberfield',
	    					id : 'creditSingleAmt',
	    					name:'tblRiskParamMng.creditSingleAmt',
	    					maxLength: 14,
	    					editable: true,
	    					allowBlank: false,
	    		            allowNegative: false,
	    		            maxValue: 9999999999999,
	    		            minValue: 0,
	    		            value: 0,
	    		            //设置允许保留0位小数
	    		            allowDecimals: true,
	    		            decimalPrecision: 0,
	    		            width : 160
	    				}]
	    			}]
              	},{
	    			id : 'otherInfo',
	    			xtype : 'fieldset',
	    			title : '其他信息',
	    			layout : 'column',
	    			labelWidth : 50,
	    			items : [{
	    				columnWidth : .9,
	    				layout : 'form',
	    				items : [{
	    					xtype: 'textarea',
	    					fieldLabel: '备注',
	    					width: 480,
	    					vtype: 'length100B',
	    					labelStyle: 'padding-left: 10px',
	    					id: 'remark',
	    					name: 'tblRiskParamMng.remark'
	    				}]
	    			}]
	    		}]
            }
            
            ]
    });
    
    var termFormDtl = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        labelWidth: 100,
        waitMsgTarget: true,
        layout: 'form',
        items: [{
             xtype: 'displayfield',
             fieldLabel: '终端号',
             labelStyle: 'padding-left: 10px',
             name: 'termIdDtl',
             id: 'termIdDtl'
         },{
             xtype: 'displayfield',
             fieldLabel: '商户编号',
             labelStyle: 'padding-left: 10px',
             name: 'mchnNoDtl',
             id: 'idmchnNoDtl'
           },termPanelDtl]
    });
    
    var termWinDtl = new Ext.Window({
        title: '终端信息审核',
        initHidden: true,
        header: true,
        frame: true,
        closable: true,
        modal: true,
        width: 640,
        autoHeight: true,
        layout: 'fit',
        items: [termFormDtl],
        iconCls: 'logo',
        resizable: false,
        buttonAlign: 'center',
        buttons:[{
        	text: '通过',
    		width: 85,
    		iconCls: 'accept',
        	handler: function() {
        		var termParam1=getTermParam1(termFormDtl.getForm().findField("termPara11Dtl").value);
        		if(!termFormDtl.getForm().isValid()){
        			showAlertMsg("数据填写不正确！",grid);
        			termPanelDtl.setActiveTab(3);
        			return;
        		}
    			showConfirm('确认通过吗？',grid,function(bt) {
    				if(bt == 'yes') {
    					showProcessMsg('正在提交信息，请稍后......');
    					var rec = grid.getSelectionModel().getSelected();
                        if(rec == null)
    		            {
    		                showAlertMsg("没有选择记录",grid);
    		                return;
    		            }  
    					Ext.Ajax.requestNeedAuthorise({
    						url: 'T30201Action.asp',
    						waitMsg: '请稍后......',
    						params: {
    							termId: rec.get('termId'),
                                recCrtTs: rec.get('recCrtTs'),
                                termSta: rec.get('termSta'),
                                termParam1:termParam1,
                                'tblRiskParamMng.creditSingleAmt' :termFormDtl.getForm().findField("tblRiskParamMng.creditSingleAmt").getValue(),
    				        	'tblRiskParamMng.debitSingleAmt' :termFormDtl.getForm().findField("tblRiskParamMng.debitSingleAmt").getValue(),
    				        	'tblRiskParamMng.creditMonAmt' : termFormDtl.getForm().findField("tblRiskParamMng.creditMonAmt").getValue(),
    				        	'tblRiskParamMng.debitMonAmt' : termFormDtl.getForm().findField("tblRiskParamMng.debitMonAmt").getValue(),
    				        	'tblRiskParamMng.creditDayAmt' : termFormDtl.getForm().findField("tblRiskParamMng.creditDayAmt").getValue(),
    				        	'tblRiskParamMng.debitDayAmt' : termFormDtl.getForm().findField("tblRiskParamMng.debitDayAmt").getValue(),
    				        	'tblRiskParamMng.remark' : termFormDtl.getForm().findField("tblRiskParamMng.remark").getValue(),
    							txnId: '30201',
    							subTxnId: '0'
    						},
    						success: function(rsp,opt) {
    							hideProcessMsg();
    							var rspObj = Ext.decode(rsp.responseText);
    							if(rspObj.success) {
    								showSuccessMsg(rspObj.msg,grid);
    							} else {
    								showErrorMsg(rspObj.msg,grid);
    							}
    							termWinDtl.close();
    							// 重新加载终端待审核信息
    							grid.getStore().reload();
    						}
    					});
    					
                        grid.getTopToolbar().items.items[0].disable();
    	                grid.getTopToolbar().items.items[1].disable();
    	                grid.getTopToolbar().items.items[2].disable();
    				}
    			});
        	}
        },{
        	text: '拒绝',
    		width: 85,
    		iconCls: 'refuse',
    		handler: function() {
                showConfirm('确认拒绝吗？',grid,function(bt) {
                    if(bt == 'yes') {
                    	showInputMsg('提示','请输入拒绝原因',true,refuse);
                    
                    }
                });
    		}
        }]
    });
    function praseTermParamDtl(val) {
        var array = val.split("|");
        if(array[4] == undefined)
        	return 0;
        array[4] = array[4].substring(2,array[4].length);
        T30101.getTermParam(array[4],function(ret){
            var termParam = ret;
        
        
        termFormDtl.getForm().findField("txn14Dtl").setValue(array[0].substring(2,array[0].length).trim());
        termFormDtl.getForm().findField("txn15Dtl").setValue(array[1].substring(2,array[1].length).trim());
        termFormDtl.getForm().findField("txn16Dtl").setValue(array[2].substring(2,array[2].length).trim());

        var value = Ext.getCmp('idtermTpDtl').getValue();
        //判断终端类型
        if(value == '1'){//财务POS
//            Ext.getCmp('accountBox2Dtl').show();
        }else if(value == '3'){//固话POS
        	termPanelDtl.get('info3Dtl').setDisabled(true);
        	Ext.getCmp('accountBox4Dtl').show();
        	termFormDtl.getForm().findField("termVersionDtl").setValue(Ext.getCmp('misc2Dtl').getValue().substring(0,4));
        }
        });
    };
     function praseTermParam1(val)
    {
        termFormDtl.getForm().findField("param1Dtl1").setValue(val.substring(0,1).trim());
        termFormDtl.getForm().findField("param1Dtl2").setValue(val.substring(1,2).trim());
        termFormDtl.getForm().findField("param1Dtl3").setValue(val.substring(2,3).trim());
        termFormDtl.getForm().findField("param1Dtl4").setValue(val.substring(3,4));
        
        termFormDtl.getForm().findField("param1Dtl").setValue(val.substring(10,11).trim());
        termFormDtl.getForm().findField("param6Dtl").setValue(val.substring(11,12).trim());
//        termFormDtl.getForm().findField("param7Dtl").setValue(val.substring(12,13).trim());
        termFormDtl.getForm().findField("param2Dtl").setValue(val.substring(13,14));
//        termFormDtl.getForm().findField("param4Dtl").setValue(val.substring(14,15));
//        termFormDtl.getForm().findField("param3Dtl").setValue(val.substring(15,16));
//        termFormDtl.getForm().findField("param5Dtl").setValue(val.substring(16,17));
        termFormDtl.getForm().findField("param8Dtl").setValue(val.substring(17,18));
//        termFormDtl.getForm().findField("termPara1P19Dtl").setValue(val.substring(18,19));
        
    }
	termInfoStoreDtl.load({
	    params: {
	         storeId: 'getTermInfo',
	         termId: termId,
	         recCrtTs: recCrtTs
	    },
	    callback: function(records, options, success){
	        if(success){
	        	formTermRiskParamStore.load({
				    params: {
				         storeId: 'getTermRiskInfo',
				         termId: termId,
				         recCrtTs: recCrtTs,
				         mchntNo : mchntNo
				    },
				    callback: function(records, options, success){
				        if(success){
				        	termFormDtl.getForm().loadRecord(termInfoStoreDtl.getAt(0));
				        	var creditSingleAmt = records[0].get('creditSingleAmt');
				        	var debitSingleAmt = records[0].get('debitSingleAmt');
				        	var creditMonAmt = records[0].get('creditMonAmt');
				        	var debitMonAmt = records[0].get('debitMonAmt');
				        	var creditDayAmt = records[0].get('creditDayAmt');
				        	var debitDayAmt = records[0].get('debitDayAmt');
				        	var remark = records[0].get('remark');
				        	termFormDtl.getForm().findField("tblRiskParamMng.creditSingleAmt").setValue(creditSingleAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.debitSingleAmt").setValue(debitSingleAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.creditMonAmt").setValue(creditMonAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.debitMonAmt").setValue(debitMonAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.creditDayAmt").setValue(creditDayAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.debitDayAmt").setValue(debitDayAmt);
				        	termFormDtl.getForm().findField("tblRiskParamMng.remark").setValue(remark);
				        	
				        	
				        	var termPara = termFormDtl.getForm().findField("termParaDtl").value;
				        	praseTermParamDtl(termPara);
				        	praseTermParam1(termFormDtl.getForm().findField("termPara11Dtl").value);
				        	termPanelDtl.setActiveTab(0);
				        	if(termFormDtl.getForm().findField("param2Dtl").getValue()||termFormDtl.getForm().findField("param8Dtl").getValue()){
			        			showAlertMsg("此终端已开通预授权或退货类的交易！",grid);
			        		}
				        	termWinDtl.show();
				        	termWinDtl.center();
				        }else{
				        	alert("载入终端信息失败，请稍后再试!")
				        }
				    }
				});
	        }else{
	        	termWinDtl.hide();
	        	alert("载入终端信息失败，请稍后再试!")
	        }
	    }
	});
	//获取附加信息的新值
	function getTermParam1(val){
		var array=val.split('');
		array.splice(0,1, termFormDtl.getForm().findField("param1Dtl1").getValue()==true?1:0);
		array.splice(1,1, termFormDtl.getForm().findField("param1Dtl2").getValue()==true?1:0);
		array.splice(2,1, termFormDtl.getForm().findField("param1Dtl3").getValue()==true?1:0);
		array.splice(3,1, termFormDtl.getForm().findField("param1Dtl4").getValue()==true?1:0);
		array.splice(10,1, termFormDtl.getForm().findField("param1Dtl").getValue()==true?1:0);
		array.splice(11,1, termFormDtl.getForm().findField("param6Dtl").getValue()==true?1:0);
		array.splice(13,1, termFormDtl.getForm().findField("param2Dtl").getValue()==true?1:0);
		array.splice(17,1, termFormDtl.getForm().findField("param8Dtl").getValue()==true?1:0);
		return array.join('');
	}
	// 终端拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(text==null){
				alert('请输入拒绝原因！');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}else if(text.trim() ==''){
				alert('请输入拒绝原因！');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
		    var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
		    Ext.Ajax.requestNeedAuthorise({
		        url: 'T30201Action.asp',
		        waitMsg: '请稍后......',
		        params: {
		            termId: rec.get('termId'),
		            recCrtTs: rec.get('recCrtTs'),
		            termSta: rec.get('termSta'),
		            txnId: '30201',
		            subTxnId: '1',
		            refuseInfo: text
		        },
		        success: function(rsp,opt) {
		            var rspObj = Ext.decode(rsp.responseText);
		            if(rspObj.success) {
		                showSuccessMsg(rspObj.msg,grid);
		            } else {
		                showErrorMsg(rspObj.msg,grid);
		            }
		            termWinDtl.close();
		            // 重新加载终端待审核信息
		            grid.getStore().reload();
		        }
		    });
		    hideProcessMsg();
            grid.getTopToolbar().items.items[0].disable();
            grid.getTopToolbar().items.items[1].disable();
            grid.getTopToolbar().items.items[2].disable();
		}
	}
};