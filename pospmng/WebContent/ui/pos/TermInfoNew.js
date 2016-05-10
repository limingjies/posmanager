//新版终端详细信息
function selectTermInfoNew(termId,recCrtTs) {
    
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
            {name: 'misc3',mapping: 'misc3'},
            {name: 'txn22Dtl',mapping: 'txn22Dtl'},
            {name: 'txn27Dtl',mapping: 'txn27Dtl'},
            {name: 'termSerialNum',mapping: 'termSerialNum'},
            {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
            {name: 'creditDayAmt',mapping: 'creditDayAmt'},
            {name: 'creditMonAmt',mapping: 'creditMonAmt'},
            {name: 'debitSingleAmt',mapping: 'debitSingleAmt'},
            {name: 'debitDayAmt',mapping: 'debitDayAmt'},
            {name: 'debitMonAmt',mapping: 'debitMonAmt'},
            {name: 'remark',mapping: 'remark'}
        ]),
        autoLoad: false
    });
	
	//终端型号数据集
	var storeTermModel = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var termPanelDtl = new Ext.TabPanel({
        activeTab: 0,
        height: 420,
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
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,//false
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '保留字段3',
                    id: 'misc3',
                    name: 'misc3'
                }]
            },{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'combofordispaly',
					fieldLabel : '是否强制联机报道',
					id : 'misc3Flag2',
					hiddenName : 'misc3_2',
					allowBlank : false,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [ [ '0', '不需要更新' ],
								[ '1', '建议更新' ],
								[ '2', '必须更新' ] ]
					}),
					width : 134
				} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'combofordispaly',
					fieldLabel : '是否强制下载主密钥',
					width : 134,
					id : 'misc3Flag1',
					allowBlank : false,
					hiddenName : 'misc3_1',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [[ '1', '未下载主密钥' ],
								[ '2', '已下载主密钥' ]]
					})
				} ]
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
                columnWidth: .6,
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
                    baseParams: 'TERM_FACTURE',
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
                    store: storeTermModel,
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
                    	disabled: true
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
                    	disabled: true
                     }]
                /*},{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7Dtl',
                        name: 'param7Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]*/
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权类 ',
                        id: 'param2Dtl',
                        name: 'param2Dtl',
                        inputValue: '1',
                    	disabled: true
                    }]
                /*},{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成',
                        id: 'param4Dtl',
                        name: 'param4Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3Dtl',
                        name: 'param3Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5Dtl',
                        name: 'param5Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]*/
                },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8Dtl',
                        name: 'param8Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]
               /* },{
                columnWidth: .33,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '准退货 ',
                        id: 'termPara1P19Dtl',
                        name: 'termPara1P19Dtl',
                        inputValue: '1',
                    	disabled: true
                     }]*/
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
	                        disabled: true,
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
	                        disabled: true,
	                        inputValue: '1'
	                     }]
	                },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                        xtype: 'checkbox',
	                        fieldLabel: '准贷记卡 ',
	                        id: 'param1Dtl3',
	                        disabled: true,
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
	                        disabled: true,
	                        name: 'param1Dtl4',
	                        inputValue: '1'
	                    }]
	                }
	             ]}
            ,{
    			id : 'riskParamInfoDtl',
    			xtype : 'fieldset',
    			title : '终端限额信息<font color="red">（0表示不限额）</font>',
    			layout : 'column',
    			labelWidth : 90,
    			items : [{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'debitMonAmtDtl',
    					name : 'debitMonAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'creditMonAmtDtl',
    					name : 'creditMonAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'debitDayAmtDtl',
    					name : 'debitDayAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'creditDayAmtDtl',
    					name : 'creditDayAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'debitSingleAmtDtl',
    					name : 'debitSingleAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .5,
    				layout : 'form',
    				items : [{
    					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'displayfield',
    					id : 'creditSingleAmtDtl',
    					name :'creditSingleAmt',
    		            width : 200
    				}]
    			},{
    				columnWidth : .9,
    				layout : 'form',
    				items : [{
	    				xtype: 'displayfield',
						fieldLabel: '备注',
						width: 480,
	//					vtype: 'length100B',
	//					labelStyle: 'padding-left: 10px',
						id: 'remark',
						name: 'remark'
    				}]
    			}]
    		}
            ]}
            
            ]
    });
    
    var termFormDtl = new Ext.form.FormPanel({
        frame: true,
        autoHeight: true,
        labelWidth: 120,
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
        title: '终端信息',
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
        resizable: false
    });
    
    function praseTermParamDtl(val) {
        var array = val.split("|");
        if(array[4] == undefined)
        	return 0;
        array[4] = array[4].substring(2,array[4].length);
        T30101.getTermParam(array[4],function(ret){
            var termParam = ret;
        
//        for(var i=0;i<=23;i++){
//        	termFormDtl.getForm().findField('param'+(i+1)+'Dtl').setValue(termParam.substring(i,i+1));
//        }
        
        termFormDtl.getForm().findField("txn14Dtl").setValue(array[0].substring(2,array[0].length).trim());
        termFormDtl.getForm().findField("txn15Dtl").setValue(array[1].substring(2,array[1].length).trim());
        termFormDtl.getForm().findField("txn16Dtl").setValue(array[2].substring(2,array[2].length).trim());
        //termFormDtl.getForm().findField("txn22Dtl").setValue(array[3].substring(2,array[3].length));
        //termFormDtl.getForm().findField("txn27Dtl").setValue(array[5].substring(2,array[5].length));
        /*termFormDtl.getForm().findField("txn35Dtl").setValue(array[12].substring(2,array[12].length).trim());
        termFormDtl.getForm().findField("txn36Dtl").setValue(array[13].substring(2,array[13].length).trim()/100);
        termFormDtl.getForm().findField("txn37Dtl").setValue(array[14].substring(2,array[14].length).trim()/100);
        termFormDtl.getForm().findField("txn38Dtl").setValue(array[15].substring(2,array[15].length).trim()/100);
        termFormDtl.getForm().findField("txn39Dtl").setValue(array[16].substring(2,array[16].length).trim()/100);
        termFormDtl.getForm().findField("txn40Dtl").setValue(array[17].substring(2,array[17].length).trim());*/

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
    
     function praseTermParam1(val){
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
	        	storeTermModel.removeAll();
				var tfId = termInfoStoreDtl.getAt(0).data.termFactoryDtl;
				SelectOptionsDWR.getComboDataWithParameter('TERM_MODEL',tfId,function(ret){
					storeTermModel.loadData(Ext.decode(ret));
					Ext.getCmp('termMachTpDtl').setValue(termInfoStoreDtl.getAt(0).data.termMachTpDtl);
				});
	        	termFormDtl.getForm().loadRecord(termInfoStoreDtl.getAt(0));
	        	
				var misc3Value = Ext.getCmp('misc3').getValue();
				Ext.getCmp('misc3Flag1').setValue(misc3Value.substring(0,1));
				Ext.getCmp('misc3Flag2').setValue(misc3Value.substring(1,2));
	        	
	        	var termPara = termFormDtl.getForm().findField("termParaDtl").value;
	        	praseTermParamDtl(termPara);
	        	praseTermParam1(termFormDtl.getForm().findField("termPara11Dtl").value);
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