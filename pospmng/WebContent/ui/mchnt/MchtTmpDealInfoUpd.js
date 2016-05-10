//
// updFormal: 是否更新正式表，1：更新
function updateTmpDealInfo(termId,recCrtTs,grid,updFormal,mchtGrid){
	var mchntNo = '';
	if(mchtGrid != null){
		var recTerm = mchtGrid.getSelectionModel().getSelected();
		mchntNo = recTerm.get('mchtNo');
	}
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
	var termInfoStoreUpd = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdUpd',mapping: 'id.termId'},
            {name: 'recCrtTsUpd',mapping: 'id.recCrtTs'},
            {name: 'mchnNoUpd',mapping: 'mchtCd'},
            {name: 'termMccUpd',mapping: 'termMcc'},
            {name: 'termBranchUpd',mapping: 'termBranch'},
            {name: 'termSignStaUpd',mapping: 'termSignSta'},
            {name: 'termFactoryUpd',mapping: 'termFactory'},
            {name: 'termMachTpUpd',mapping: 'termMachTp'},
            {name: 'termIdIdUpd',mapping: 'termIdId'},
            {name: 'termVerUpd',mapping: 'termVer'},
            {name: 'termTpUpd',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'propTpUpd',mapping: 'propTp'},
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
            {name: 'productCdDtl',mapping: 'productCd'},
            {name: 'termPlaceUpd',mapping: 'termPlace'},
            {name: 'oprNmUpd',mapping: 'oprNm'},
            {name: 'termParaUpd',mapping: 'termPara'},
            {name: 'termPara1Upd',mapping: 'termPara2'},
            {name: 'termPara11Upd',mapping: 'termPara1'},
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'misc2Upd',mapping: 'misc2'},
            {name: 'misc2',mapping: 'misc2'},
            {name: 'misc1',mapping: 'misc1'},
            {name: 'txn22Upd',mapping: 'txn22Dtl'},
            {name: 'txn27Upd',mapping: 'txn27Dtl'},
            {name: 'termSerialNum',mapping: 'termSerialNum'}
        ]),
        autoLoad: false
    });
	
	//终端型号数据集
	var storeTermModel = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	/** ************** 终端修改布局 ************************ */
	var updTermPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 450,
		width : 620,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		frame : true,
		items : [{
			title : '基本信息',
			id : 'info1Upd',
			layout : 'column',
                frame: true,
                items: [{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
	                    xtype: 'displayfield',
			            fieldLabel: '签购单名称',
			            id: 'txn22Upd',
			            width:350,
			            name: 'txn22Upd'
             }]
           },{
                    columnWidth: 1, 
                    layout: 'form',
                    hidden:true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '商户英文名称',
                        id: 'txn27Upd',
                        width:350,
                        name: 'txn27Upd'
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    hidden: true,
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
	                xtype: 'displayfield',
	                fieldLabel: '终端名称',
	                name: 'misc2',
	                id: 'misc2'
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
                    id:'idpropTpUpd',
                    hiddenName: 'propTpUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '收单服务机构',
                    baseParams: 'ORGAN',
                    id: 'idpropInsNmUpd',
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '第三方分成比例',
                    id: 'termPara1Upd',
                    name: 'termPara1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '连接类型',
                    baseParams: 'CONNECT_MODE',
                    hiddenName: 'connectModeUpd'
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
            },
    //        ------------------------------------附加信息---------------------------------------------------------------------
             {
			title : '附加信息',
			id : 'info2Upd',
			layout : 'column',
                frame: true,
                items: [{
                      columnWidth: .5,
                      layout: 'form',
                      items:[{
                        xtype: 'combofordispaly',
                        fieldLabel: '终端类型',
                        baseParams: 'TERM_TYPE',
                        id: 'idtermTpUpd',
                        hiddenName: 'termTpUpd'
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
                        id: 'accountBox4Upd',
                        hidden: true,
                        columnWidth: .5,
                        hidden: true,
                        layout: 'form',
                        items: [{
                            xtype: 'displayfield',
                            fieldLabel: '电话POS版本号',
                            id: 'termVersionUpd',
                            name: 'termVersionUpd'
                        }]
                    },{
                id: 'accountBox2Upd',
                hidden: true,
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
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '终端安装地址',
                    readOnly: true,
                    width:350,
                    id: 'termAddrUpd',
                    name: 'termAddrUpd'
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
                    hiddenName: 'termFactoryUpd'
                }]
            
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '终端型号',
                   // baseParams: 'TERM_MODEL',
                    hiddenName: 'termMachTpUpd',
                    id:"termMachTpUpd",
                    store: storeTermModel,
                }]
            
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话1',
                    id: 'txn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话2',
                    id: 'txn15Upd',
                    name: 'txn15Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话3',
                    id: 'txn16Upd',
                    name: 'txn16Upd'
                }]
            } ,{
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
            } ,{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
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
                hidden:true,
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
                hidden:true,
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
                hidden: true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1Upd',
                    name: 'reserveFlag1Upd',
                    disabled: true
                }]
            }]
            },
 // --------------------------交易信息TAB----------------------------
            {
			title : '交易信息',
			id : 'info3Upd',
			// layout: 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd3',
									width : 350
								}]
					},{
						xtype : 'fieldset',
						title : '交易权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [{
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款期数',
//												vtype : 'isNumber',
												id : 'txn35Upd',
//												maxLength : 2,
												name : 'txn35Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款限额',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn36Upd',
												name : 'txn36Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '消费单笔上限 ',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn37Upd',
												name : 'txn37Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货单笔上限',
//												vtype : 'isMoney',
												id : 'txn38Upd',
//												maxLength : 12,
												name : 'txn38Upd'
											}]
								}, {
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '转帐单笔上限',
//												vtype : 'isMoney',
												id : 'txn39Upd',
//												maxLength : 12,
												name : 'txn39Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货时间跨度',
//												vtype : 'isNumber',
												id : 'txn40Upd',
//												maxLength : 2,
												name : 'txn40Upd',
												value : 30
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '查询 ',
												id : 'param1Upd',
												name : 'param1Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费 ',
												id : 'param6Upd',
												name : 'param6Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费撤销 ',
												id : 'param7Upd',
												name : 'param7Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权 ',
												id : 'param2Upd',
												name : 'param2Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成 ',
												id : 'param4Upd',
												name : 'param4Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权撤销 ',
												id : 'param3Upd',
												name : 'param3Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成撤销 ',
												id : 'param5Upd',
												name : 'param5Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '退货 ',
												id : 'param8Upd',
												name : 'param8Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准退货 ',
												id : 'termPara1P19Upd',
												name : 'termPara1P19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '离线结算 ',
												id : 'param9Upd',
												name : 'param9Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '结算调整 ',
												id : 'param10Upd',
												name : 'param10Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '公司卡转个人卡（财务POS） ',
												id : 'param11Upd',
												name : 'param11Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '个人卡转公司卡（财务POS） ',
												id : 'param12Upd',
												name : 'param12Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '卡卡转帐',
												id : 'param13Upd',
												name : 'param13Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '上笔交易查询（财务POS） ',
												id : 'param14Upd',
												name : 'param14Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '交易查询（财务POS） ',
												id : 'param15Upd',
												name : 'param15Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '定向汇款 ',
												id : 'param16Upd',
												name : 'param16Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款 ',
												id : 'param17Upd',
												name : 'param17Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款撤销 ',
												id : 'param18Upd',
												name : 'param18Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '代缴费 ',
												id : 'param19Upd',
												name : 'param19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '电子现金 ',
												id : 'param20Upd',
												name : 'param20Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : 'IC现金充值 ',
												id : 'param21Upd',
												name : 'param21Upd',
												disabled : true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '指定账户圈存',
												id : 'param22Upd',
												name : 'param22Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非指定账户圈存 ',
												id : 'param23Upd',
												name : 'param23Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非接快速支付 ',
												id : 'param24Upd',
												name : 'param24Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									items : [{
												xtype : 'hidden',
												id : 'termParaUpd',
												name : 'termParaUpd'
											}, {
												xtype : 'hidden',
												id : 'termPara11Upd',
												name : 'termPara11Upd'
											}, {
												xtype : 'hidden',
												id : 'misc2Upd',
												name : 'misc2Upd'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '卡类型权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [

						{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '借记卡 ',
												id : 'param1Upd1',
												name : 'param1Upd1',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '贷记卡 ',
												id : 'param1Upd2',
												name : 'param1Upd2',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准贷记卡 ',
												id : 'param1Upd3',
												name : 'param1Upd3',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预付费卡 ',
												id : 'param1Upd4',
												name : 'param1Upd4',
												inputValue : '1'
											}]
								}

						]
					}

			]
		},{

      	  	title: '风控参数信息',
            id: 'info4Upd',
            hidden:true,
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
  					id : 'debitMonAmt1',
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
  					id : 'creditMonAmt1',
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
  					id : 'debitDayAmt1',
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
  					id : 'creditDayAmt1',
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
  					id : 'debitSingleAmt1',
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
  					id : 'creditSingleAmt1',
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
  					id: 'remark1',
  					name: 'tblRiskParamMng.remark'
  				}]
  			}]
  		}]
		}]
	});
	
	/******************** 终端修改表单 ******************** */
	var updTermForm = new Ext.form.FormPanel({
			frame : true,
			height : 450,
			width : 620,
			labelWidth : 100,
			waitMsgTarget : true,
			layout : 'form',
			items : [{
	             xtype: 'displayfield',
	             fieldLabel: '终端号',
	             labelStyle: 'padding-left: 10px',
	             name: 'termIdUpd',
	             id: 'termIdUpd'
	         },{
	             xtype: 'displayfield',
	             fieldLabel: '商户编号',
	             labelStyle: 'padding-left: 10px',
	             name: 'mchnNoUpd',
	             id: 'idmchnNoUpd'
	           },updTermPanel]
		});
    function praseTermParamUpd(val) {
//        var array = val.split("|");
//        if(array[4] == undefined)
//        	return 0;
//        array[4] = array[4].substring(2,array[4].length);
//        T30101.getTermParam(array[4],function(ret){
//           var termParam = ret;
//	        updTermForm.getForm().findField("txn14Upd").setValue(array[0].substring(2,array[0].length).trim());
//	        updTermForm.getForm().findField("txn15Upd").setValue(array[1].substring(2,array[1].length).trim());
//	        updTermForm.getForm().findField("txn16Upd").setValue(array[2].substring(2,array[2].length).trim());
//	
	        var value = Ext.getCmp('idtermTpUpd').getValue();
	        //判断终端类型
	        if(value == '3'){//固话POS
	        	updTermForm.get('info3Upd').setDisabled(true);  //交易信息tab
	        	//显示电话pos版本号
	        	Ext.getCmp('accountBox4Upd').show();  
	        	updTermForm.getForm().findField("termVersionUpd").setValue(Ext.getCmp('misc2Upd').getValue().substring(0,4));
	        }
//        });
    };
     function praseTermParam1(val)
    {
    	 //卡类型
    	 updTermForm.getForm().findField("param1Upd1").setValue(val.substring(0,1).trim());
    	 updTermForm.getForm().findField("param1Upd2").setValue(val.substring(1,2).trim());
    	 updTermForm.getForm().findField("param1Upd3").setValue(val.substring(2,3).trim());
    	 updTermForm.getForm().findField("param1Upd4").setValue(val.substring(3,4));
         //交易权限
    	 updTermForm.getForm().findField("param1Upd").setValue(val.substring(10,11).trim());
    	 updTermForm.getForm().findField("param6Upd").setValue(val.substring(11,12).trim());
    	 updTermForm.getForm().findField("param2Upd").setValue(val.substring(13,14));
    	 updTermForm.getForm().findField("param8Upd").setValue(val.substring(17,18));
    }
     
     /** ***************** 终端修改信息 ******************** */
 	var updTermWin = new Ext.Window({
 		title : '终端修改',
 		initHidden : true,
 		header : true,
 		frame : true,
 		closable : false,
 		modal : true,
 		width : 620,
 		autoHeight : true,
 		layout : 'fit',
 		items : [updTermForm],
 		buttonAlign : 'center',
 		closeAction : 'hide',
 		iconCls : 'logo',
 		resizable : false,
 		buttons : [{
 			text : '确定',
 			handler : function() {
 				updTermPanel.setActiveTab("info3Upd");
 				//updTermPanel.setActiveTab("info3Upd");
 				if (updTermForm.getForm().isValid()) {
 					updTermForm.getForm().submitNeedAuthorise({
 								url : 'MchtTmpInfoAction_updateDealInfo.asp',
 								waitMsg : '正在提交，请稍后......',
 								success : function(form, action) {
 									showSuccessMsg(action.result.msg,grid);
 									grid.getStore().reload();
 									updTermForm.getForm().reset();
 									grid.getTopToolbar().items.items[1].disable();
 									grid.getTopToolbar().items.items[0].disable();
 									updTermWin.close();
 								},
 								failure : function(form, action) {
 									updTermPanel.setActiveTab('info3Upd');
 									showErrorMsg(action.result.msg||'信息保存失败！', updTermForm);
 								},
 								params : {
 									txnId : '30101',
 									subTxnId : '02',
 									termIdUpd : termId,
									recCrtTs : recCrtTs,
									mchnNoUpd: Ext.getCmp("idmchnNoUpd").getValue(),
									updFormal: updFormal,
									mchntNo : mchntNo
 								}
 							});
 				} else {
 					var finded = true;
 					updTermForm.getForm().items.each(function(f) {
 								if (finded && !f.validate()) {
 									var tab = f.ownerCt.ownerCt.id;
 									if (tab == 'info1Upd' || tab == 'info2Upd'
 											|| tab == 'info3Upd') {
 										updTermPanel.setActiveTab(tab);
 									}else {
 										tab = 'info4Upd';
 										updTermPanel.setActiveTab(tab);
 									}
 									finded = false;
 								}
 							});
 				}
 			}
 		}, {
 			text : '关闭',
 			handler : function() {
 				updTermWin.close();
 			}
 		}]
 	});
	/******************** 数据加载 ******************** */
	termInfoStoreUpd.load({
	    params: {
	         storeId: 'getTermInfo',
	         termId: termId,
	         recCrtTs: recCrtTs
	    },
	    callback: function(termRecords, options, success){
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
				        	storeTermModel.removeAll();
							var tfId = termInfoStoreUpd.getAt(0).data.termFactoryUpd;
							SelectOptionsDWR.getComboDataWithParameter('TERM_MODEL',tfId,function(ret){
								storeTermModel.loadData(Ext.decode(ret));
								Ext.getCmp('termMachTpUpd').setValue(termInfoStoreUpd.getAt(0).data.termMachTpUpd);
							});
				        	updTermForm.getForm().loadRecord(termRecords[0]);
				        	//updTermForm.getForm().loadRecord(termInfoStoreUpd.getAt(0));
				        	var termPara = updTermForm.getForm().findField("termParaUpd").value;
				        	praseTermParamUpd(termPara);
				        	praseTermParam1(updTermForm.getForm().findField("termPara11Upd").value);
				        	updTermPanel.setActiveTab('info3Upd');
				        	
				        	var creditSingleAmt = records[0].get('creditSingleAmt');
				        	var debitSingleAmt = records[0].get('debitSingleAmt');
				        	var creditMonAmt = records[0].get('creditMonAmt');
				        	var debitMonAmt = records[0].get('debitMonAmt');
				        	var creditDayAmt = records[0].get('creditDayAmt');
				        	var debitDayAmt = records[0].get('debitDayAmt');
				        	var remark = records[0].get('remark');
				        	updTermForm.getForm().findField("tblRiskParamMng.creditSingleAmt").setValue(creditSingleAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.debitSingleAmt").setValue(debitSingleAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.creditMonAmt").setValue(creditMonAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.debitMonAmt").setValue(debitMonAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.creditDayAmt").setValue(creditDayAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.debitDayAmt").setValue(debitDayAmt);
				        	updTermForm.getForm().findField("tblRiskParamMng.remark").setValue(remark);
				        	
				        	updTermWin.show();
				        	updTermWin.center();
				        	if('' == mchntNo){
				        		updTermPanel.hideTabStripItem(3);
				        	}
				        }else{
				        	alert("载入终端信息失败，请稍后再试!")
				        }
				    }
				});
	        }else{
	        	updTermWin.close();
	        	alert("载入终端信息失败，请稍后再试!")
	        }
	    }
	});
}
function batchUpdateTmpInfo(termIds,recCrtTs,grid,mchntId){
	/** ************** 终端修改布局 ************************ */
	var updTermPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 450,
		width : 620,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		frame : true,
		items : [
         {
			title : '交易信息',
			id : 'info3Upd',
			// layout: 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd3',
									width : 350
								}]
					},{
						xtype : 'fieldset',
						title : '交易权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [{
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款期数',
//												vtype : 'isNumber',
												id : 'txn35Upd',
//												maxLength : 2,
												name : 'txn35Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款限额',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn36Upd',
												name : 'txn36Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '消费单笔上限 ',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn37Upd',
												name : 'txn37Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货单笔上限',
//												vtype : 'isMoney',
												id : 'txn38Upd',
//												maxLength : 12,
												name : 'txn38Upd'
											}]
								}, {
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '转帐单笔上限',
//												vtype : 'isMoney',
												id : 'txn39Upd',
//												maxLength : 12,
												name : 'txn39Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货时间跨度',
//												vtype : 'isNumber',
												id : 'txn40Upd',
//												maxLength : 2,
												name : 'txn40Upd',
												value : 30
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '查询 ',
												id : 'param1Upd',
												name : 'param1Upd',
												checked:true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费 ',
												id : 'param6Upd',
												name : 'param6Upd',
												checked:true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费撤销 ',
												id : 'param7Upd',
												name : 'param7Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权 ',
												id : 'param2Upd',
												name : 'param2Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成 ',
												id : 'param4Upd',
												name : 'param4Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权撤销 ',
												id : 'param3Upd',
												name : 'param3Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成撤销 ',
												id : 'param5Upd',
												name : 'param5Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '退货 ',
												id : 'param8Upd',
												name : 'param8Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准退货 ',
												id : 'termPara1P19Upd',
												name : 'termPara1P19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '离线结算 ',
												id : 'param9Upd',
												name : 'param9Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '结算调整 ',
												id : 'param10Upd',
												name : 'param10Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '公司卡转个人卡（财务POS） ',
												id : 'param11Upd',
												name : 'param11Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '个人卡转公司卡（财务POS） ',
												id : 'param12Upd',
												name : 'param12Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '卡卡转帐',
												id : 'param13Upd',
												name : 'param13Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '上笔交易查询（财务POS） ',
												id : 'param14Upd',
												name : 'param14Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '交易查询（财务POS） ',
												id : 'param15Upd',
												name : 'param15Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '定向汇款 ',
												id : 'param16Upd',
												name : 'param16Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款 ',
												id : 'param17Upd',
												name : 'param17Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款撤销 ',
												id : 'param18Upd',
												name : 'param18Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '代缴费 ',
												id : 'param19Upd',
												name : 'param19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '电子现金 ',
												id : 'param20Upd',
												name : 'param20Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : 'IC现金充值 ',
												id : 'param21Upd',
												name : 'param21Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '指定账户圈存',
												id : 'param22Upd',
												name : 'param22Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非指定账户圈存 ',
												id : 'param23Upd',
												name : 'param23Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非接快速支付 ',
												id : 'param24Upd',
												name : 'param24Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									items : [{
												xtype : 'hidden',
												id : 'termParaUpd',
												name : 'termParaUpd'
											}, {
												xtype : 'hidden',
												id : 'termPara11Upd',
												name : 'termPara11Upd'
											}, {
												xtype : 'hidden',
												id : 'misc2Upd',
												name : 'misc2Upd'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '卡类型权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [

						{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '借记卡 ',
												id : 'param1Upd1',
												name : 'param1Upd1',
												checked:true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '贷记卡 ',
												checked:true,
												id : 'param1Upd2',
												name : 'param1Upd2',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准贷记卡 ',
												id : 'param1Upd3',
												name : 'param1Upd3',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预付费卡 ',
												id : 'param1Upd4',
												name : 'param1Upd4',
												inputValue : '1'
											}]
								}

						]
					}

			]
		}]
	});
	
	/******************** 终端修改表单 ******************** */
	var updTermForm = new Ext.form.FormPanel({
			frame : true,
			height : 450,
			width : 620,
			labelWidth : 120,
			waitMsgTarget : true,
			layout : 'form',
			items : [{
	             xtype: 'displayfield',
	             fieldLabel: '商户编号',
	             labelStyle: 'padding-left: 10px',
	             name: 'mchnNoUpd',
	             id: 'idmchnNoUpd',
	             value: mchntId
	           },updTermPanel]
		});
	
     /** ***************** 终端修改信息 ******************** */
 	var updTermWin = new Ext.Window({
 		title : '终端批量修改',
 		initHidden : true,
 		header : true,
 		frame : true,
 		closable : false,
 		modal : true,
 		width : 620,
 		autoHeight : true,
 		layout : 'fit',
 		items : [updTermForm],
 		buttonAlign : 'center',
 		closeAction : 'hide',
 		iconCls : 'logo',
 		resizable : false,
 		buttons : [{
 			text : '确定',
 			handler : function() {
 				updTermPanel.setActiveTab("info3Upd");
 				//交易权限不可为空
				if(!(Ext.getCmp('param1Upd').getValue() || Ext.getCmp('param2Upd').getValue() || Ext.getCmp('param6Upd').getValue() || Ext.getCmp('param8Upd').getValue())){
					alert("交易权限：不可为空！");
					updTermPanel.setActiveTab("info3NewId");
					return;
				}
				//卡类型不可为空
				if(!(Ext.getCmp('param1Upd1').getValue() || Ext.getCmp('param1Upd2').getValue() || Ext.getCmp('param1Upd3').getValue() || Ext.getCmp('param1Upd4').getValue())){
					alert("卡类型权限:不可为空！");
					updTermPanel.setActiveTab("info3NewId");
					return;
				}
 				if (updTermForm.getForm().isValid()) {
 					updTermForm.getForm().submitNeedAuthorise({
 								url : 'MchtTmpInfoAction_termBatchhUpdate.asp',
 								waitMsg : '正在提交，请稍后......',
 								success : function(form, action) {
 									if(action.result.success){
 										Ext.Msg.show({ 
 										    title:'提示信息：', 
 										    msg:action.result.msg, 
 										    buttons:Ext.MessageBox.OK 
 										});
 									}else {
 										Ext.Msg.show({ 
 										    title:'提示信息：', 
 										    msg:action.result.msg, 
 										    buttons:Ext.MessageBox.NO 
 										});
 									}
 									grid.getStore().reload();
 									updTermForm.getForm().reset();
 									updTermWin.close();
 								},
 								failure : function(form, action) {
 									updTermPanel.setActiveTab('info3Upd');
 									showErrorMsg(action.result.msg||'信息保存失败！', updTermForm);
 								},
 								params : {
 									termIdUpd : termId,
									recCrtTs : recCrtTs,
									mchnNoUpd: mchntId
 								}
 							});
 				} else {
 					var finded = true;
 					updTermForm.getForm().items.each(function(f) {
 								if (finded && !f.validate()) {
 									var tab = f.ownerCt.ownerCt.id;
 									if (tab == 'info1Upd' || tab == 'info2Upd'
 											|| tab == 'info3Upd') {
 										updTermPanel.setActiveTab(tab);
 									}
 									finded = false;
 								}
 							});
 				}
 			}
 		}, {
 			text : '关闭',
 			handler : function() {
 				updTermWin.close();
 			}
 		}]
 	});
 	updTermWin.show();
}

function batchUpdTermWithRiskInfo(termId,recCrtTs,grid,mchntId){
var formTermRiskParamStore = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
            {name: 'deditSingleAmt',mapping: 'deditSingleAmt'},
            {name: 'termPara1',mapping: 'termPara1'}
            ]),
            autoLoad: false
        });
	//修改表单
	var formTermRiskParam = new Ext.form.FormPanel({
		frame : true,
		height : 500,
		autoScroll:true,
		width : 700,
		layout : 'form',
		waitMsgTarget : true,
		items : [{
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
					id : 'debitMonAmtForBatch',
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
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditMonAmtForBatch',
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
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitDayAmtForBatch',
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
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditDayAmtForBatch',
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
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitSingleAmtForBatch',
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
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditSingleAmtForBatch',
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
		            width : 200
				}]
			}]
		},{
			id : 'tradInfo',
			xtype : 'fieldset',
			title : '交易信息',
			layout : 'column',
			labelWidth : 50,
			items : [
				{
					xtype : 'fieldset',
					title : '交易权限',
					layout : 'column',
					labelWidth : 70,
					width : 570,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [
						{
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '查询 ',
								id : 'param1UpdForBatch',
								name : 'param1Upd',
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '消费 ',
								id : 'param6UpdForBatch',
								name : 'param6Upd',
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '预授权 ',
								id : 'param2UpdForBatch',
								name : 'param2Upd',
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '退货 ',
								id : 'param8UpdForBatch',
								name : 'param8Upd',
								inputValue : '1'
							}]
						}
					]
				},{
					xtype : 'fieldset',
					title : '卡类型权限',
					layout : 'column',
					labelWidth : 70,
					width : 570,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '借记卡 ',
							id : 'param1Upd1ForBatch',
							name : 'param1Upd1',
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '贷记卡 ',
							id : 'param1Upd2ForBatch',
							name : 'param1Upd2',
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '准贷记卡 ',
							id : 'param1Upd3ForBatch',
							name : 'param1Upd3',
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '预付费卡 ',
							id : 'param1Upd4ForBatch',
							name : 'param1Upd4',
							inputValue : '1'
						}]
					}]
				}
			]
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
					id: 'remarkForBatch',
					name: 'tblRiskParamMng.remark'
				}]
			}]
		}]
	});
	
	var winTermRiskParam = new Ext.Window({
		title:'终端风控参数修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		shadow : false,
		modal : true,
		width : 700,
		height:500,
		//autoHeight : true,
		layout : 'fit',
		items : [formTermRiskParam],
		buttonAlign : 'center',
		closeAction : 'close',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			id : 'btnSubmit',
			text : '提交',
			handler : function() {
				if(formTermRiskParam.getForm().isValid()){
					var termParam = getTermParam1();
					formTermRiskParam.getForm().submit({
						url : 'T40407Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formTermRiskParam);
							// 重置表单
							setInterval(function(){
								formTermRiskParam.getForm().reset();
								winTermRiskParam.close();
							},2000);
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formTermRiskParam);
							setInterval(function(){
								formTermRiskParam.getForm().reset();
								winTermRiskParam.close();
							},2000);
						},
						params : {
							'txnId' : '40407',
							'subTxnId' : '02',
							'termParam1' : termParam,
							'termIds' : termId,
							'mchtIds' : mchntId,
							'recCrtTs' : recCrtTs
							
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				formTermRiskParam.getForm().reset();
				winTermRiskParam.close();
			}
		}]
	});
	//获取附加信息的新值
	function getTermParam1(){
		var val = '                   ';
		var array=val.split('');
		array.splice(0,1, formTermRiskParam.getForm().findField("param1Upd1").getValue()==true?1:0);
		array.splice(1,1, formTermRiskParam.getForm().findField("param1Upd2").getValue()==true?1:0);
		array.splice(2,1, formTermRiskParam.getForm().findField("param1Upd3").getValue()==true?1:0);
		array.splice(3,1, formTermRiskParam.getForm().findField("param1Upd4").getValue()==true?1:0);
		array.splice(10,1, formTermRiskParam.getForm().findField("param1Upd").getValue()==true?1:0);
		array.splice(11,1, formTermRiskParam.getForm().findField("param6Upd").getValue()==true?1:0);
		array.splice(12,1, formTermRiskParam.getForm().findField("param6Upd").getValue()==true?1:0);
		array.splice(13,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
		array.splice(14,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
		array.splice(15,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
		array.splice(16,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
		array.splice(17,1, formTermRiskParam.getForm().findField("param8Upd").getValue()==true?1:0);
		array.splice(18,1, formTermRiskParam.getForm().findField("param8Upd").getValue()==true?1:0);
		return array.join('');
	}
	formTermRiskParam.getForm().findField("param1Upd1").setValue('1');
	formTermRiskParam.getForm().findField("param1Upd2").setValue('1');
	formTermRiskParam.getForm().findField("param6Upd").setValue('1');
	formTermRiskParam.getForm().findField("param1Upd").setValue('1');
	winTermRiskParam.show();
}