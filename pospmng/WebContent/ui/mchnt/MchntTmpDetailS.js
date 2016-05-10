



//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){
	var now=new Date();
	var nowDate=(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()).toString();
	// *************终端信息****************
	var topQueryPanel = new Ext.form.FormPanel({
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [
				new Ext.form.TextField({
					id : 'termNoQ',
					name : 'termNo',
					fieldLabel : '终端号',
					anchor : '60%'
				}),
				/*{
					xtype : 'dynamicCombo',
					methodName : 'getMchntNo',
					fieldLabel : '商户编号',
					hiddenName : 'mchnNo',
					id : 'mchnNoQ',
					editable : true,
					width : 0,

				},*/
				{
					xtype : 'combo',
					fieldLabel : '终端状态',
					id : 'state',
					name : 'state',
					anchor : '60%',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '0', '新增未审核' ], [ '1', '启用' ],
								[ '2', '修改未审核' ], [ '3', '冻结未审核' ],
								[ '4', '冻结' ], [ '5', '恢复未审核' ],
								[ '6', '注销未审核' ], [ '7', '注销' ] ]
					})
				}, {
					xtype : 'basecomboselect',
					baseParams : 'TERM_TYPE',
					fieldLabel : '终端类型',
					id : 'idtermtpsearch',
					hiddenName : 'termtpsearch',
					anchor : '60%'
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '创建起始时间',
					format : 'Y-m-d',
					name : 'startTime',
					id : 'startTime',
					anchor : '60%'
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '创建截止时间',
					format : 'Y-m-d',
					name : 'endTime',
					id : 'endTime',
					anchor : '60%'
				} ],
		buttons : [ {
			text : '查询',
			handler : function() {
				termStore.load();
				queryWin.hide();
			}
		}, {
			text : '重填',
			handler : function() {
				topQueryPanel.getForm().reset();
			}
		} ]
	});
	var termStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'termId',
			mapping : 'termId'
		}, {
			name : 'mchntNo',
			mapping : 'mchntNo'
		}, {
			name : 'mchntName',
			mapping : 'mchntName'
		}, {
			name : 'termSerialNum',
			mapping : 'termSerialNum'
		}, {
			name : 'termSta',
			mapping : 'termSta'
		}, {
			name : 'termSignSta',
			mapping : 'termSignSta'
		}, {
			name : 'termIdId',
			mapping : 'termIdId'
		}, {
			name : 'termFactory',
			mapping : 'termFactory'
		}, {
			name : 'termMachTp',
			mapping : 'termMachTp'
		}, {
			name : 'termVer',
			mapping : 'termVer'
		}, {
			name : 'termTp',
			mapping : 'termTp'
		}, {
			name : 'termBranch',
			mapping : 'termBranch'
		}, {
			name : 'termIns',
			mapping : 'termIns'
		}, {
			name : 'recCrtTs',
			mapping : 'recCrtTs'
		}, {
			name : 'propTp',
			mapping : 'propTp'
		}, {
			name : 'termAddr',
			mapping : 'termAddr'
		}, {
			name : 'param',
			mapping : 'param'
		} ])
	});

	
	var termColModel = new Ext.grid.ColumnModel([
		 new Ext.grid.RowNumberer(), 
		{header: '终端类型',dataIndex: 'termTp',width: 70,renderer:termForType
		 	},
			{header: '产权属性',dataIndex: 'propTp',width: 100,renderer:
				function settleType(val){
				switch(val){
				case '0':return '钱宝所有';
				case '1':return '合作伙伴所有';
				case '2':return '商户自有';
				}
			}},
			{header: '安装地址',dataIndex: 'termAddr',width: 250},
		{header: '查询',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
				switch(val.substring(10,11)){
					case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '消费类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
				switch(val.substring(11,12)){
				case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '预授权类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(13,14)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '退货类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(17,18)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '借记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(0,1)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '贷记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(1,2)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '准贷记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(2,3)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '预付费卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(3,4)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		}, {
		id : 'termId',
		hidden:true,
		header : '终端号',
		dataIndex : 'termId',
		width : 200
	}, {
		header : '商户号',
		hidden:true,
		dataIndex : 'mchntNo',
		width : 0,
		id : 'mchntNo'
	}, {
		header : '商户名',
		hidden:true,
		dataIndex : 'mchntName',
		width : 1,
		id : 'mchntName'
	}, {
		header : '终端状态',
		hidden:true,
		dataIndex : 'termSta',
		width : 0,
		renderer : termSta
	}, {
		header : '终端序列号',
		hidden:true,
		dataIndex : 'termSerialNum',
		width : 200,
		renderer : termSta
	}, {
		header : '所属合作伙伴',
		hidden:true,
		dataIndex : 'termBranch',
		width: 0
	}, {
		header : '机具库存编号',
		hidden:true,
		dataIndex : 'termIdId',
			width: 0
	}, {
		header : '机具厂商',
		hidden:true,
		dataIndex : 'termFactory',
		width : 280
	}, {
		header : '机具型号',
		hidden:true,
		dataIndex : 'termMachTp',
		width : 200
	}, {
		header : '录入日期',
		hidden:true,
		dataIndex : 'recCrtTs',
		width : 0,
		renderer : formatDt
	} ]);
	var qryMenu = {
			text : '详细信息',
			width : 85,
			hidden:false,
			iconCls : 'detail',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGridPanel.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
				bt.disable();
				setTimeout(function() {
							bt.enable();
						}, 2000);
				selectTermInfoNew(selectedRecord.get('termId'), selectedRecord
								.get('recCrtTs'));
			}
		};
	
	var menuArr = new Array();
	menuArr.push(qryMenu); // [3]

	// 终端信息列表
	var termGridPanel = new Ext.grid.GridPanel({
		title : '终端信息',
		iconCls : 'T301',
		region : 'center',
		frame : true,
		height: 300,
		border : true,
//		autoExpandColumn : 'mchntName',
		columnLines : true,
		stripeRows : true,
		store : termStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : termColModel,
//		clicksToEdit : true,
//		forceValidation : true,
		tbar : menuArr,
//		renderTo : Ext.getBody(),
		loadMask : {
			msg : '正在加载终端信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : termStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	termGridPanel.getSelectionModel().on(
			{
				'rowselect' : function() {
					// 行高亮
					Ext.get(
							termGridPanel.getView().getRow(
									termGridPanel.getSelectionModel().last))
							.frame();
//					termGridPanel.getTopToolbar().items.items[1].enable();
					// 根据商户状态判断哪个编辑按钮可用
					rec = termGridPanel.getSelectionModel().getSelected();
					/*if (rec.get('termSta') == '0'
							|| rec.get('termSta') == '1'
							|| rec.get('termSta') == '2'
							|| rec.get('termSta') == '8'
							|| rec.get('termSta') == '9'
							|| rec.get('termSta') == 'A'
							|| rec.get('termSta') == 'B'
							|| rec.get('termSta') == 'C'
							|| rec.get('termSta') == 'D') {
//						termGridPanel.getTopToolbar().items.items[2].enable();
					} else {
						termGridPanel.getTopToolbar().items.items[2].disable();
					}*/
					// if(rec.get('termSta') == '1')
					// {
					// grid.getTopToolbar().items.items[3].enable();
					// } else {
					// grid.getTopToolbar().items.items[3].disable();
					// }
					termGridPanel.getTopToolbar().items.items[0].enable();
					// grid.getTopToolbar().items.items[5].enable();
				}
			});

	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH', function(ret) {
		brhStore.loadData(Ext.decode(ret));
	});

	// 终端库存号
	var termIdIdStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	// 终端厂商
	/*var manufacturerStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MANUFACTURER', function(ret) {
		manufacturerStore.loadData(Ext.decode(ret));
	});*/

	// 终端型号
	var terminalTypeStore = new Ext.data.JsonStore({
		fields : [ 'valueField', 'displayField' ],
		root : 'data'
	});

//*************终端信息****************

	//*************************费率信息*************************************
	// 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    // 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	//专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
  //EPOS版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
	//feilv
    var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});

	gridStore.on('load',function(){
		store.load({
			params: {
				start: 0,
				discCd: baseStore.getAt(0).data.feeRate,
				mccCode: baseStore.getAt(0).data.mcc
			}
		});
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 80},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	/*
	var termColModel = new Ext.grid.ColumnModel([
    {header: '终端号',dataIndex: 'mchtCd',width: 120},
    {header: '终端所属机构',dataIndex: 'brhIdNew',width: 120},
    {header: '联系电话',dataIndex: 'contTelNew',width: 120},
    {header: '产权属性',dataIndex: 'propTpNew',width: 120,renderer:
    	function settleType(val){
		switch(val){
			case '0':return '我司产权';
			case '1':return '我司租赁';
			case '2':return '第三方投入';
		}
	}},
    {header: '收单服务机构',dataIndex: 'propInsNmNew',width: 120},
    {header: '第三方分成比例(%)',dataIndex: 'termPara1New',width: 120},
    {header: '连接类型',dataIndex: 'connectModeNew',width: 120,renderer:
    	function settleType(val){
		switch(val){
			case 'Z':return '直联';
			case 'J':return '间联';
		}
	}
    },
    {header: '终端类型',dataIndex: 'termTpNew',width: 120,renderer:
    	function settleType(val){
		switch(val){
			case '0':return '普通POS';
			case '1':return '财务POS';
			case '2':return '签约POS';
			case '3':return '电话POS';
			case '4':return 'MISPOS';
			case '5':return '移动POS';
			case '6':return '网络POS';
			case '7':return 'MPOS';
		}
	}},
	{header: '终端序列号',dataIndex: 'termSerialNum',width: 120}
	]);
	
	var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
        width: 650,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                items: [{
                    columnWidth: .8,
                    layout: 'form',
                    items: [{
                    	xtype: 'textfield',
                        fieldLabel: '临时终端号',
                        id: 'termID',
                        name: 'termID',
                        readOnly: true,
                        width: 300
                     }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户名',
                        id: 'txn22New',
                        name: 'txn22New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27New',
                        name: 'txn27New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccNew',
                        name: 'termMccNew',
                        readOnly: true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '终端所属机构*',
                    id: 'termBranchNew',
                    hiddenName: 'brhIdNew',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    blankText: '终端所属机构不能为空',
                    listeners:{
                        'select': function() {
                        	SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('termBranchNew').value,function(ret){
							        eposStore.loadData(Ext.decode(ret));
							    });
                        }
                    }
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 20,
                    allowBlank: false,
                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelNew',
                    name: 'contTelNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
                    allowBlank: false,
                    hiddenName: 'propTpNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','我司产权'],['1','我司租赁'],['2','第三方投入']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpN').getValue();
	                        if(args == 2)
	                        {
	                            Ext.getCmp("termPara1New").show();
                                Ext.getCmp("flagBox1").show();
	                        }
                            else
                            {
                                Ext.getCmp("termPara1New").hide();
                                Ext.getCmp("flagBox1").hide();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单服务机构',
                    store: organStore,
                    id: 'propInsNmN',
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                id: "flagBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1New',
                    name: 'termPara1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeN',
                    value: 'J',
                    allowBlank: false,
                    hiddenName: 'connectModeNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['J','间联'],['Z','直联']]
                    })
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                id: 'info2New',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'combo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpN',
	                    allowBlank: false,
	                    hiddenName: 'termTpNew',
	                    width:150,
	                    store: termTypeStore,
	                    listeners: {
	                     'select': function() {
	                                var value1 = Ext.getCmp("termMccNew").getValue();
	                                var value2 = Ext.getCmp('termTpN').getValue();
	                                //改为使用elseif判断
	                                if(Ext.getCmp('idmchtGroupFlag').value == '6' && value2 != '3'){
	                                	termForm.getForm().findField("termTpN").setValue(3);
		                                showAlertMsg("固话POS商户，终端类型只能选择固话POS",termGridPanel);
	                    	 		}else if(Ext.getCmp('idmchtGroupFlag').value != '6' && value2 == '3'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("非固话POS商户，终端类型不能选择固话POS",termGridPanel);
	                    	 		}else if( value1 != '0000' && value2 == '1'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("非财务POS商户，终端类型不能选择财务POS",termGridPanel);
	                                }
	                                if( Ext.getCmp('termTpN').getValue() == '3' ){
		                                Ext.getCmp('accountBox3').show();
		                                termPanel.get('info3New').setDisabled(true);
//		                                Ext.getCmp('txn14New').allowBlank = true;
		                                Ext.getCmp('termVerN').allowBlank = false;
	                                }else{
	                                	Ext.getCmp('accountBox3').hide();
		                                termPanel.get('info3New').setDisabled(false);
//		                                Ext.getCmp('txn14New').allowBlank = false;
	                                	Ext.getCmp('termVerN').allowBlank = true;
	                                }
	                        }
                    }
                    }]
           },{
                columnWidth: .5,
                id: "accountBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '财务账号1*',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard1New',
                    name: 'financeCard1New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号2',
                    maxLength: 16,
                    id: 'financeCard2New',
                    name: 'financeCard2New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号3',
                    maxLength: 16,
                    id: 'financeCard3New',
                    name: 'financeCard3New'
                }]
            },{
                id: 'accountBox3',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                	xtype: 'combo',
                    fieldLabel: '固话POS版本号*',
                    store: eposStore,
                    id: 'termVerN',
                    hiddenName: 'termVerNew',
                    anchor: '80%'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 200,
                    id: 'termAddrNew',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话1*',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn14New',
                    name: 'txn14New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn15New',
                    name: 'txn15New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn16New',
                    name: 'txn16New'
                }]
            }, {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话1',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel1New',
                    name: 'bindTel1New',
                    allowBlank: false
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话2',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel2New',
                    name: 'bindTel2New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '绑定电话3',
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel3New',
                    name: 'bindTel3New'
                }]
            }, {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignNew',
                    name: 'keyDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignNew',
                    name: 'paramDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignNew',
                    name: 'icDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
                    		r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
                    		r.setValue('1');
						}
                    }
                }]
            }]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款期数',
                        vtype: 'isNumber',
                        id: 'txn35New',
                        maxLength: 2,
                        name: 'txn35New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款限额',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn36New',
                        name: 'txn36New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '消费单笔上限 ',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn37New',
                        name: 'txn37New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货单笔上限',
                        id: 'txn38New',
                        vtype: 'isMoney',
                        maxLength: 12,
                        name: 'txn38New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '转帐单笔上限',
                        vtype: 'isMoney',
                        id: 'txn39New',
                        maxLength: 12,
                        name: 'txn39New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货时间跨度',
                        vtype: 'isNumber',
                        id: 'txn40New',
                        maxLength: 2,
                        name: 'txn40New',
                        value: 30
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1New',
                        name: 'param1New',
                        checked: true,
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2New',
                        name: 'param2New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3New',
                        name: 'param3New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4New',
                        name: 'param4New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5New',
                        name: 'param5New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6New',
                        name: 'param6New',
                        checked: true,
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7New',
                        name: 'param7New',
                        checked: true,
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8New',
                        name: 'param8New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9New',
                        name: 'param9New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10New',
                        name: 'param10New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11New',
                        name: 'param11New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12New',
                        name: 'param12New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13New',
                        name: 'param13New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财务POS） ',
                        id: 'param14New',
                        name: 'param14New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财务POS） ',
                        id: 'param15New',
                        name: 'param15New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16New',
                        name: 'param16New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17New',
                        name: 'param17New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18New',
                        name: 'param18New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19New',
                        name: 'param19New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20New',
                        name: 'param20New',
                        checked: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21New',
                        name: 'param21New',
                        disabled: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22New',
                        name: 'param22New',
                        checked: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23New',
                        name: 'param23New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24New',
                        name: 'param24New',
                        checked: true,
                        inputValue: '1'
                     }]
            }]
            }]
    });
	

	
	 *//**************  终端表单  *********************//*
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 650,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});*/
	
	/***********  终端信息窗口  *****************/
	/*var termWin = new Ext.Window({
		title: '终端信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 650,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				termPanel.setActiveTab("info3New");
				termPanel.setActiveTab("info2New");
				
				if(termForm.getForm().isValid()) {
					
					var aLine = new termCreate();
					aLine.set("termID",termForm.getForm().findField('termID').getValue());
					aLine.set("txn22New",termForm.getForm().findField('txn22New').getValue()); //商户名
					aLine.set("txn27New",termForm.getForm().findField('txn27New').getValue()); //商户英文名
					aLine.set("termMccNew",termForm.getForm().findField('termMccNew').getValue()); //终端MCC码
					aLine.set("brhIdNew",termForm.getForm().findField('termBranchNew').getValue()); //终端所属机构
					aLine.set("contTelNew",termForm.getForm().findField('contTelNew').getValue()); //联系电话
					aLine.set("propTpNew",termForm.getForm().findField('propTpN').getValue()); //产权属性
					aLine.set("propInsNmNew",termForm.getForm().findField('propInsNmN').getValue()); //收单服务机构
					aLine.set("termPara1New",termForm.getForm().findField('termPara1New').getValue()); //第三方分成比例
					aLine.set("connectModeNew",termForm.getForm().findField('connectModeN').getValue()); //连接类型
					aLine.set("termTpNew",termForm.getForm().findField('termTpN').getValue()); //终端类型
					aLine.set("financeCard1New","1"); //财务账号1
					aLine.set("financeCard2New","1"); //财务账号2
					aLine.set("financeCard3New","1"); //财务账号3
					aLine.set("termVerNew",termForm.getForm().findField('termVerN').getValue()); //固话POS版本号
					aLine.set("termAddrNew",termForm.getForm().findField('termAddrNew').getValue()); //终端安装地址
					aLine.set("txn14New",termForm.getForm().findField('txn14New').getValue()); //NAC电话1
					aLine.set("txn15New",termForm.getForm().findField('txn15New').getValue()); //NAC电话2
					aLine.set("txn16New",termForm.getForm().findField('txn16New').getValue()); //NAC电话3
					aLine.set("bindTel1New",termForm.getForm().findField('bindTel1New').getValue()); //绑定电话1
					aLine.set("bindTel2New",termForm.getForm().findField('bindTel2New').getValue()); //绑定电话2
					aLine.set("bindTel3New",termForm.getForm().findField('bindTel3New').getValue()); //绑定电话3
					aLine.set("keyDownSignNew",termForm.getForm().findField('keyDownSignNew').getValue()); //CA公钥下载标志
					aLine.set("paramDownSignNew",termForm.getForm().findField('paramDownSignNew').getValue()); //终端参数下载标志
					aLine.set("icDownSignNew",termForm.getForm().findField('icDownSignNew').getValue()); //IC卡参数下载标志
					aLine.set("reserveFlag1New",termForm.getForm().findField('reserveFlag1New').getValue()); //绑定电话
					aLine.set("txn35New",termForm.getForm().findField('txn35New').getValue()); //分期付款期数
					aLine.set("txn36New",termForm.getForm().findField('txn36New').getValue()); //分期付款限额
					aLine.set("txn37New",termForm.getForm().findField('txn37New').getValue()); //消费单笔上限
					aLine.set("txn38New",termForm.getForm().findField('txn38New').getValue()); //退货单笔上限
					aLine.set("txn39New",termForm.getForm().findField('txn39New').getValue()); //转账单笔上限
					aLine.set("txn40New",termForm.getForm().findField('txn40New').getValue()); //退货时间跨度
					aLine.set("param1New",Ext.getCmp('param1New').getValue()==false?0:1); //查询
					aLine.set("param2New",Ext.getCmp('param2New').getValue()==false?0:1); //预授权
					aLine.set("param3New",Ext.getCmp('param3New').getValue()==false?0:1); //预授权撤销
					aLine.set("param4New",Ext.getCmp('param4New').getValue()==false?0:1); //预授权完成联机
					aLine.set("param5New",Ext.getCmp('param5New').getValue()==false?0:1); //预授权完成撤销
					aLine.set("param6New",Ext.getCmp('param6New').getValue()==false?0:1); //消费
					aLine.set("param7New",Ext.getCmp('param7New').getValue()==false?0:1); //消费撤销
					aLine.set("param8New",Ext.getCmp('param8New').getValue()==false?0:1); //退货
					aLine.set("param9New",Ext.getCmp('param9New').getValue()==false?0:1); //离线结算
					aLine.set("param10New",Ext.getCmp('param10New').getValue()==false?0:1); //结算调整
					aLine.set("param11New",Ext.getCmp('param11New').getValue()==false?0:1); //公司卡转个人卡（财务POS）
					aLine.set("param12New",Ext.getCmp('param12New').getValue()==false?0:1); //个人卡转公司卡（财务POS）
					aLine.set("param13New",Ext.getCmp('param13New').getValue()==false?0:1); //卡卡转帐
					aLine.set("param14New",Ext.getCmp('param14New').getValue()==false?0:1); //上笔交易查询（财务POS）
					aLine.set("param15New",Ext.getCmp('param15New').getValue()==false?0:1); //交易查询（财务POS）
					aLine.set("param16New",Ext.getCmp('param16New').getValue()==false?0:1); //定向汇款
					aLine.set("param17New",Ext.getCmp('param17New').getValue()==false?0:1); //分期付款
					aLine.set("param18New",Ext.getCmp('param18New').getValue()==false?0:1); //分期付款撤销
					aLine.set("param19New",Ext.getCmp('param19New').getValue()==false?0:1); //代缴费
					aLine.set("param20New",Ext.getCmp('param20New').getValue()==false?0:1); //电子现金
					aLine.set("param21New",Ext.getCmp('param21New').getValue()==false?0:1); //IC现金充值
					aLine.set("param22New",Ext.getCmp('param22New').getValue()==false?0:1); //指定账户圈存
					aLine.set("param23New",Ext.getCmp('param23New').getValue()==false?0:1); //非指定账户圈存
					aLine.set("param24New",Ext.getCmp('param24New').getValue()==false?0:1); //非接快速支付
					
					for(var i=0;i<termStore.getCount();i++) {
						if(termStore.getAt(i).data.termID == termForm.getForm().findField('termID').getValue()) {
							termStore.removeAt(i);
						}
					}
					termStore.insert(termStore.getCount(), aLine);
					
					termForm.getForm().reset();
                    termWin.hide();
				} else {
					
                    var finded = true;
	                termForm.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if(tab == 'info1New' || tab == 'info2New' || tab == 'info3New' ){
	                             termPanel.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
				termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termForm.getForm().reset();
				termWin.hide();
			}
		}]
	});

	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getTermInf',
			//params: { userName: 'tom', password: '123' }, //请求参数
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'termID',mapping: 'termID'}, //终端号
			{name: 'txn22New',mapping: 'txn22New'}, //商户名
			{name: 'txn27New',mapping: 'txn27New'}, //商户英文名
			{name: 'termMccNew',mapping: 'termMccNew'}, //终端MCC码
			{name: 'brhIdNew',mapping: 'brhIdNew'}, //终端所属机构
			{name: 'contTelNew',mapping: 'contTelNew'}, //联系电话
			{name: 'propTpNew',mapping: 'propTpNew'}, //产权属性
			{name: 'propInsNmNew',mapping: 'propInsNmNew'}, //收单服务机构
			{name: 'termPara1New',mapping: 'termPara1New'}, //第三方分成比例
			{name: 'connectModeNew',mapping: 'connectModeNew'}, //连接类型
			{name: 'termTpNew',mapping: 'termTpNew'}, //终端类型
			{name: 'financeCard1New',mapping: 'financeCard1New'}, //财务账号1
			{name: 'financeCard2New',mapping: 'financeCard2New'}, //财务账号2
			{name: 'financeCard3New',mapping: 'financeCard3New'}, //财务账号3
			{name: 'termVerNew',mapping: 'termVerNew'}, //固话POS版本号
			{name: 'termAddrNew',mapping: 'termAddrNew'}, //终端安装地址
			{name: 'txn14New',mapping: 'txn14New'}, //NAC电话1
			{name: 'txn15New',mapping: 'txn15New'}, //NAC电话2
			{name: 'txn16New',mapping: 'txn16New'}, //NAC电话3
			{name: 'bindTel1New',mapping: 'bindTel1New'}, //绑定电话1
			{name: 'bindTel2New',mapping: 'bindTel2New'}, //绑定电话2
			{name: 'bindTel3New',mapping: 'bindTel3New'}, //绑定电话3
			{name: 'keyDownSignNew',mapping: 'keyDownSignNew'}, //CA公钥下载标志
			{name: 'paramDownSignNew',mapping: 'paramDownSignNew'}, //终端参数下载标志
			{name: 'icDownSignNew',mapping: 'icDownSignNew'}, //IC卡参数下载标志
			{name: 'reserveFlag1New',mapping: 'reserveFlag1New'}, //绑定电话
			{name: 'txn35New',mapping: 'txn35New'}, //分期付款期数
			{name: 'txn36New',mapping: 'txn36New'}, //分期付款限额
			{name: 'txn37New',mapping: 'txn37New'}, //消费单笔上限
			{name: 'txn38New',mapping: 'txn38New'}, //退货单笔上限
			{name: 'txn39New',mapping: 'txn39New'}, //转账单笔上限
			{name: 'txn40New',mapping: 'txn40New'}, //退货时间跨度
			{name: 'param1New',mapping: 'param1New'}, //查询
			{name: 'param2New',mapping: 'param2New'}, //预授权
			{name: 'param3New',mapping: 'param3New'}, //预授权撤销
			{name: 'param4New',mapping: 'param4New'}, //预授权完成联机
			{name: 'param5New',mapping: 'param5New'}, //预授权完成撤销
			{name: 'param6New',mapping: 'param6New'}, //消费
			{name: 'param7New',mapping: 'param7New'}, //消费撤销
			{name: 'param8New',mapping: 'param8New'}, //退货
			{name: 'param9New',mapping: 'param9New'}, //离线结算
			{name: 'param10New',mapping: 'param10New'}, //结算调整
			{name: 'param11New',mapping: 'param11New'}, //公司卡转个人卡（财务POS）
			{name: 'param12New',mapping: 'param12New'}, //个人卡转公司卡（财务POS）
			{name: 'param13New',mapping: 'param13New'}, //卡卡转帐
			{name: 'param14New',mapping: 'param14New'}, //上笔交易查询（财务POS）
			{name: 'param15New',mapping: 'param15New'}, //交易查询（财务POS）
			{name: 'param16New',mapping: 'param16New'}, //定向汇款
			{name: 'param17New',mapping: 'param17New'}, //分期付款
			{name: 'param18New',mapping: 'param18New'}, //分期付款撤销
			{name: 'param19New',mapping: 'param19New'}, //代缴费
			{name: 'param20New',mapping: 'param20New'}, //电子现金
			{name: 'param21New',mapping: 'param21New'}, //IC现金充值
			{name: 'param22New',mapping: 'param22New'}, //指定账户圈存
			{name: 'param23New',mapping: 'param23New'}, //非指定账户圈存
			{name: 'param24New',mapping: 'param24New'}  //非接快速支付
		]),
		autoLoad: false
	});*/
	
	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '卡类型',
            dataIndex: 'cardType',
            width: 70,
            editor: {
					xtype: 'basecomboselect',
					baseParams: 'feeCardType',
			        store: cardTypeStore,
					id: 'idCardType',
					hiddenName: 'cardType',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '交易类型',
            dataIndex: 'txnNum',
            width: 70,
            editor: {
					xtype: 'basecomboselect',
					baseParams: 'feeTxnNum',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 80,
            sortable: true
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 90,
            editor: {
					xtype: 'basecomboselect',
					baseParams: 'DISC_FLAG',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '回佣值',
            dataIndex: 'feeValue',
            width: 70
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 90
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 90
        }]
	});
 	
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true
	});
	//每一条终端信息就是一个Record，然后放在store中
	//这里的name属性主要用于 termCreate.get(name) 或 termCreate.set(name, value)
/*	var termCreate = Ext.data.Record.create([
	{
        name: 'termID', //终端号
        type: 'string'
    }, {
        name: 'txn22New', //商户名
        type: 'string'
    }, {
        name: 'txn27New', //商户英文名
        type: 'string'
    }, {
        name: 'termMccNew', //终端MCC码
        type: 'string'
    }, {
        name: 'brhIdNew', //终端所属机构
        type: 'string'
    }, {
        name: 'contTelNew', //联系电话
        type: 'string'
    }, {
        name: 'propTpNew', //产权属性
        type: 'string'
    }, {
        name: 'propInsNmNew', //收单服务机构
        type: 'string'
    }, {
        name: 'termPara1New', //第三方分成比例
        type: 'string'
    }, {
        name: 'connectModeNew', //连接类型
        type: 'string'
    }, {
        name: 'termTpNew', //终端类型
        type: 'string'
    }, {
        name: 'financeCard1New', //财务账号1
        type: 'string'
    }, {
        name: 'financeCard2New', //财务账号2
        type: 'string'
    }, {
        name: 'financeCard3New', //财务账号3
        type: 'string'
    }, {
        name: 'termVerNew', //固话POS版本号
        type: 'string'
    }, {
        name: 'termAddrNew', //终端安装地址
        type: 'string'
    }, {
        name: 'txn14New', //NAC电话1
        type: 'string'
    }, {
        name: 'txn15New', //NAC电话2
        type: 'string'
    }, {
        name: 'txn16New', //NAC电话3
        type: 'string'
    }, {
        name: 'bindTel1New', //绑定电话1
        type: 'string'
    }, {
        name: 'bindTel2New', //绑定电话2
        type: 'string'
    }, {
        name: 'bindTel3New', //绑定电话3
        type: 'string'
    }, {
        name: 'keyDownSignNew', //CA公钥下载标志
        type: 'string'
    }, {
        name: 'paramDownSignNew', //终端参数下载标志
        type: 'string'
    }, {
        name: 'icDownSignNew', //IC卡参数下载标志
        type: 'string'
    }, {
        name: 'reserveFlag1New', //绑定电话
        type: 'string'
    }, {
        name: 'txn35New', //分期付款期数
        type: 'string'
    }, {
        name: 'txn36New', //分期付款限额
        type: 'string'
    }, {
        name: 'txn37New', //消费单笔上限
        type: 'string'
    }, {
        name: 'txn38New', //退货单笔上限
        type: 'string'
    }, {
        name: 'txn39New', //转账单笔上限
        type: 'string'
    }, {
        name: 'txn40New', //退货时间跨度
        type: 'string'
    }, {
        name: 'param1New', //查询
        type: 'string'
    }, {
        name: 'param2New', //预授权
        type: 'string'
    }, {
        name: 'param3New', //预授权撤销
        type: 'string'
    }, {
        name: 'param4New', //预授权完成联机
        type: 'string'
    }, {
        name: 'param5New', //预授权完成撤销
        type: 'string'
    }, {
        name: 'param6New', //消费
        type: 'string'
    }, {
        name: 'param7New', //消费撤销
        type: 'string'
    }, {
        name: 'param8New', //退货
        type: 'string'
    }, {
        name: 'param9New', //离线结算
        type: 'string'
    }, {
        name: 'param10New', //结算调整
        type: 'string'
    }, {
        name: 'param11New', //公司卡转个人卡（财务POS）
        type: 'string'
    }, {
        name: 'param12New', //个人卡转公司卡（财务POS）
        type: 'string'
    }, {
        name: 'param13New', //卡卡转帐
        type: 'string'
    }, {
        name: 'param14New', //上笔交易查询（财务POS）
        type: 'string'
    }, {
        name: 'param15New', //交易查询（财务POS）
        type: 'string'
    }, {
        name: 'param16New', //定向汇款
        type: 'string'
    }, {
        name: 'param17New', //分期付款
        type: 'string'
    }, {
        name: 'param18New', //分期付款撤销
        type: 'string'
    }, {
        name: 'param19New', //代缴费
        type: 'string'
    }, {
        name: 'param20New', //电子现金
        type: 'string'
    }, {
        name: 'param21New', //IC现金充值
        type: 'string'
    }, {
        name: 'param22New', //指定账户圈存
        type: 'string'
    }, {
        name: 'param23New', //非指定账户圈存
        type: 'string'
    }, {
        name: 'param24New', //非接快速支付
        type: 'string'
    }
    ]);
	
//	gridStore.on('beforeload', function() {
//		store.removeAll();
//	});
*/	
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('feeCardType',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('feeTxnNum',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});
	
	
	
	/*var termGridPanel = new Ext.grid.GridPanel({
		title: '终端信息列表',
		iconCls: 'T301',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		height: 250,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		}
	});
	
	termGridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(termGridPanel.getView().getRow(termGridPanel.getSelectionModel().last)).frame();
		}
	});*/
	
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'cardType',mapping: 'cardType'}
		]),
//		sortInfo: {field: 'floorMount', direction: 'ASC'},
		autoLoad: false
	});
	
	//终端临时ID
	/*var termTmpID = 1;*/
	
	
	 var detailGrid = new Ext.grid.GridPanel({
			title: '详细信息',
			autoWidth: true,
			frame: true,
			border: true,
			height: 210,
			columnLines: true,
			stripeRows: true,
			store: store,
			disableSelection: true,
			cm: cm,
			forceValidation: true
		});

 	
//  在点击“添加终端”按钮时触发，替代原终端维护中的读取商户信息部分
	/*var doBeforeAddTerm = function() {
        if(termForm.getForm().findField("termTpN").getValue()=='1'){termForm.getForm().findField("termTpN").setValue(0);};
        termForm.getForm().findField("termTpN").setReadOnly(false);
        termForm.getForm().findField("param1New").setValue(0);
        termForm.getForm().findField("param11New").setValue(0);
        termForm.getForm().findField("param12New").setValue(0);
        termForm.getForm().findField("param13New").setValue(0);
        termForm.getForm().findField("param14New").setValue(0);
        termForm.getForm().findField("param15New").setValue(0);
        
//        if(mchtGroupV == '6'){
//        	Ext.getCmp('accountBox3').show();
//        	termForm.getForm().findField("termTpN").setValue(3);
//            termPanel.get('info3New').setDisabled(true);
////            Ext.getCmp('txn14New').allowBlank = true;
//            termForm.getForm().findField("reserveFlag1New").setValue(1);
//            Ext.getCmp('termVerN').allowBlank = false;	
//        }else{
        	Ext.getCmp('accountBox3').hide();
        	if(termForm.getForm().findField("termTpN").getValue()=='3'){termForm.getForm().findField("termTpN").setValue(0);};
            termPanel.get('info3New').setDisabled(false);
//            Ext.getCmp('txn14New').allowBlank = false;
            termForm.getForm().findField("reserveFlag1New").setValue(0);
        	Ext.getCmp('termVerN').allowBlank = true;
//        }
        
        return true;
	};
	*/
	/*
 	function delPIC(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(5)).data;
		 		T20100.deleteImgFileTmp(rec.fileName,mchntId,function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht: mchntId
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	storeImg.on('load',function(){
		for(var i=0;i<storeImg.getCount();i++){
			var rec = storeImg.getAt(i).data;
        	Ext.get(rec.btBig).on('click', function(obj,val){
        		showPIC(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC(val.id);
        	});
		}
	});
	
*/
	
//************************************************费率**********************************************************

	//图片显示Store
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var storeImg5 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var storeImg4 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var storeImg3 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var storeImg2 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var storeImg1 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
	    store: storeImg,
	    tpl  : new Ext.XTemplate(
	        '<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
	    ),
	    id: 'phones',
	    itemSelector: 'li.phone',
	    overClass   : 'phone-hover',
	    singleSelect: true,
	    multiSelect : true,
	    autoScroll  : true
	});
	var dataview5 = new Ext.DataView({
		frame: true,
        store: storeImg5,
        tpl  : new Ext.XTemplate(
        		'<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
        ),
        id: 'phones5',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	
	
	var dataview4 = new Ext.DataView({
		frame: true,
        store: storeImg4,
        tpl  : new Ext.XTemplate(
        		'<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
        ),
        id: 'phones4',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	var dataview3 = new Ext.DataView({
		frame: true,
        store: storeImg3,
        tpl  : new Ext.XTemplate(
        		'<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
        ),
        id: 'phones3',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	var dataview2 = new Ext.DataView({
		frame: true,
        store: storeImg2,
        tpl  : new Ext.XTemplate(
        		'<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
        ),
        id: 'phones2',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	var dataview1 = new Ext.DataView({
		frame: true,
        store: storeImg1,
        tpl  : new Ext.XTemplate(
        		'<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
						'</div>',
	                '</li>',
	            '</tpl>',
	        '</ul>'
        ),
        id: 'phones1',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });
	 var custom;
	var customEl;
	var rollTimes=0; //向上滚加1，向下滚减1
	var originalWidth =0; //图片缩放前的原始宽度
	var originalHeight = 0; //图片缩放前的原始高度
	function showPIC(store,id){
 		var rec = store.getAt(id.substr(5,1)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName='+ mchntId+'/'+ rec.fileName;
	 		document.getElementById('showBigPic').onmousewheel = bigimg;
	 		originalWidth = document.getElementById('showBigPic').width;
	 		originalHeight = document.getElementById('showBigPic').height;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
 	function bigimg(){
		var zoom = 0.05;
		var rollDirect = event.wheelDelta;
		if(rollDirect>0){
			rollTimes++;
		}
		if(rollDirect<0){
			rollTimes--;
		}
		if(this.height<50 && rollDirect<0){
			return false;
		}
		var cwidth = originalWidth*(1+zoom*rollTimes);
		var cheight = originalHeight*(1+zoom*rollTimes);
		custom.resizeTo(cwidth, cheight);
		return false;
	}
	var fm = Ext.form;
    var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntTmpInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
//			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'manuAuthFlag',mapping: 'manuAuthFlag'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'discConsFlg',mapping: 'discConsFlg'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
//			{name: 'openDays',mapping: 'openDays'},
//			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
//			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name: 'areaNo',mapping: 'areaNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'homePage',mapping: 'homePage'},
			{name: 'mcc',mapping: 'mcc'},
//			{name: 'tcc',mapping: 'tcc'},
			{name: 'etpsAttr',mapping: 'etpsAttr'},
			{name: 'mngMchtId',mapping: 'mngMchtId'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtAttr',mapping: 'mchtAttr'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'mchtGroupId',mapping: 'mchtGroupId'},
//			{name: 'mchtEngNm',mapping: 'mchtEngNm'},
//			{name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
//			{name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
//			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'psamNum',mapping: 'psamNum'},
			{name: 'cdMacNum',mapping: 'cdMacNum'},
			{name: 'posNum',mapping: 'posNum'},
			{name: 'connType',mapping: 'connType'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'mchtFunction',mapping: 'mchtFunction'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
//			{name: 'enableDate',mapping: 'enableDate'},
//			{name: 'preAudNm',mapping: 'preAudNm'},
//			{name: 'confirmNm',mapping: 'confirmNm'},
//			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
//			{name: 'netTel',mapping: 'netTel'},
//			{name: 'prolDate',mapping: 'prolDate'},
//			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
			{name: 'operNo',mapping: 'operNo'},
			{name: 'operNm',mapping: 'operNm'},
			{name: 'procFlag',mapping: 'procFlag'},
			{name: 'setCur',mapping: 'setCur'},
	      	{name: 'printInstId',mapping: 'printInstId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'acqBkName',mapping: 'acqBkName'},
			{name: 'bankNo',mapping: 'bankNo'},
//			{name: 'orgnNo',mapping: 'orgnNo'},
//			{name: 'subbrhNo',mapping: 'subbrhNo'},
//			{name: 'subbrhNm',mapping: 'subbrhNm'},
			{name: 'openTime',mapping: 'openTime'},
			{name: 'closeTime',mapping: 'closeTime'},
//			{name: 'visActFlg',mapping: 'visActFlg'},
//			{name: 'visMchtId',mapping: 'visMchtId'},
//			{name: 'mstActFlg',mapping: 'mstActFlg'},
//			{name: 'mstMchtId',mapping: 'mstMchtId'},
//			{name: 'amxActFlg',mapping: 'amxActFlg'},
//			{name: 'amxMchtId',mapping: 'amxMchtId'},
//			{name: 'dnrActFlg',mapping: 'dnrActFlg'},
//			{name: 'dnrMchtId',mapping: 'dnrMchtId'},
//			{name: 'jcbActFlg',mapping: 'jcbActFlg'},
//			{name: 'jcbMchtId',mapping: 'jcbMchtId'},
			{name: 'cupMchtFlg',mapping: 'cupMchtFlg'},
			{name: 'debMchtFlg',mapping: 'debMchtFlg'},
			{name: 'creMchtFlg',mapping: 'creMchtFlg'},
			{name: 'cdcMchtFlg',mapping: 'cdcMchtFlg'},
			{name: 'idOtherNo',mapping: 'idOtherNo'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'internalNo',mapping: 'internalNo'},
			{name: 'reject',mapping: 'reject'},
			{name: 'mchntAttr',mapping: 'mchntAttr'},
			{name: 'linkPer',mapping: 'linkPer'},
			{name: 'SettleAreaNo',mapping: 'SettleAreaNo'},
			{name: 'MainTlr',mapping: 'MainTlr'},
			{name: 'CheckTlr',mapping: 'CheckTlr'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'rateFlag',mapping: 'rateFlag'},
			{name: 'settleChn',mapping: 'settleChn'},
			{name: 'batTime',mapping: 'batTime'},
			{name: 'autoStlFlg',mapping: 'autoStlFlg'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeFixed',mapping: 'feeFixed'},
			{name: 'feeMaxAmt',mapping: 'feeMaxAmt'},
			{name: 'feeMinAmt',mapping: 'feeMinAmt'},
			{name: 'feeRate',mapping: 'feeRate'},
//			{name: 'feeDiv1',mapping: 'feeDiv1'},
//			{name: 'feeDiv2',mapping: 'feeDiv2'},
//			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'settleAcctMid',mapping: 'settleAcctMid'},
			{name: 'settleAcctMidNm',mapping: 'settleAcctMidNm'},
			{name: 'settleAcctMidBank',mapping: 'settleAcctMidBank'},
			{name: 'clearType',mapping: 'clearType'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
//			{name: 'feeAcct',mapping: 'feeAcct'},
//			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
//			{name: 'changeStlno',mapping: 'changeStlno'},
//			{name: 'speSettleTp',mapping: 'speSettleTp'},
//			{name: 'speSettleLv',mapping: 'speSettleLv'},
//			{name: 'speSettleDs',mapping: 'speSettleDs'},
//			{name: 'feeBackFlg',mapping: 'feeBackFlg'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			
			{name: 'compNm',mapping: 'compNm'},
			{name: 'compaddr',mapping: 'compaddr'},
			{name: 'finacontact',mapping: 'finacontact'},
			{name: 'finacommTel',mapping: 'finacommTel'},
			{name: 'finacommEmail',mapping: 'finacommEmail'},
			{name: 'busInfo',mapping: 'busInfo'},
			{name: 'busArea',mapping: 'busArea'},
			{name: 'monaveTrans',mapping: 'monaveTrans'},
			{name: 'sigaveTrans',mapping: 'sigaveTrans'},
			{name: 'contstartDate',mapping: 'contstartDate'},
			{name: 'contendDate',mapping: 'contendDate'},
			{name: 'prolDate',mapping: 'prolDate'}
		]),
		autoLoad: false
	});
	


	//******************图片处理部分**********结束********
	var nextBt1 = {
			xtype: 'button',
			width: 100,
			text: '下一页',
			id: 'nextBt1',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				var frm = mchntForm.getForm();
//				if (frm.isValid()) {
					Ext.getCmp('baseInfo').hide();
				    Ext.getCmp('manageInfo').hide();
				    Ext.getCmp('businessInfo').hide();
				    Ext.getCmp('settleInfo').show();
//				    Ext.getCmp('contdateInfo').show();
					Ext.getCmp('imageInfo').show();
					Ext.getCmp('imageInfo1').hide();
					Ext.getCmp('imageInfo2').hide();
					Ext.getCmp('imageInfo3').hide();
					Ext.getCmp('imageInfo4').hide();
					Ext.getCmp('imageInfo5').show();
					Ext.getCmp('next1').hide();
					Ext.getCmp('next2').show();
					Ext.getCmp('tab000').show();
					Ext.getCmp('termgrid').show();
//							}
			}
		};
	var nextArr1 = new Array();
	nextArr1.push(nextBt1);		// [0]
	var checkIds = "T";
	var hasUpload = "0";
	
	
	var nextBt2 = {
			text: '关闭',
            id: 'save',
            width: 100,
            height: 30,
            name: 'save',
			handler: function() {
				detailWin.close();
			}
		};
		
		/*var nextBt21 = {
			text: '重置',
			width: 100,
            height: 30,
            handler: function() {
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
		};*/
		
		var nextBt21 = {
			xtype: 'button',
			width: 100,
			text: '上一页',
			id: 'nextBt21',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				var frm = mchntForm.getForm();
//				if (frm.isValid()) {
				Ext.getCmp('baseInfo').show();
			    Ext.getCmp('manageInfo').show();
			    Ext.getCmp('businessInfo').show();
			    Ext.getCmp('settleInfo').hide();
//			    Ext.getCmp('contdateInfo').hide();
				Ext.getCmp('imageInfo').hide();
				Ext.getCmp('imageInfo1').show();
				Ext.getCmp('imageInfo2').show();
				Ext.getCmp('imageInfo3').show();
				Ext.getCmp('imageInfo4').show();
				Ext.getCmp('imageInfo5').hide();
				Ext.getCmp('next1').show();
				Ext.getCmp('next2').hide();
				Ext.getCmp('tab000').hide();
				Ext.getCmp('termgrid').hide();
//							}
			}
		};
	
	var nextArr2 = new Array();
	nextArr2.push(nextBt21);		// [0]
	nextArr2.push(nextBt2);		// [0]
	
	  
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
		height:430,
//		modal: true,
//		height: Ext.getBody().getHeight(true),
//        width: Ext.getBody().getWidth(true),
        autoScroll: true,
//		title: '商户入网申请',
		
		waitMsgTarget: true,
		defaults: {
			bodyStyle: 'padding-left: 10px'
		},
		items: [{
			id: 'baseInfo',
			xtype: 'fieldset',
			title: '注册信息',
			layout: 'column',
			collapsible: true,
			width: 950,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},items: [
				{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'combofordispaly',
			        baseParams: 'MCHT_GROUP_FLAG',
					fieldLabel: '商户种类',
					id: 'idmchtGroupFlag',
					hiddenName: 'mchtGroupFlag',
					allowBlank: false,
					editable: false,
					width: 150
//					value: '1'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				width: 150,
				hidden:true,
				items: [{
					xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					fieldLabel: '商户接入方式',
					id: 'idconnType',
					hiddenName: 'tblMchtBaseInfTmpTmp.connType',
					allowBlank: true,
					width: 150,
					value: 'J'
				}]
			},/*{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'dynamicCombo',
					methodName: 'getAreaCode',
					fieldLabel: '所在地区',
					hiddenName: 'tblMchtBaseInfTmpTmp.areaNo',
					id:'areaNoId',
					allowBlank: true,
//					editable: true,
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true,
					width: 150
				}]
			},*/{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'combofordispaly',
			        baseParams: 'BRH_BELOW_BRANCH',
			        id: 'idbankNo',
					hiddenName: 'bankNo',
					fieldLabel: '合作伙伴',
					allowBlank: false,
					width: 200

				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'combofordispaly',
		        	baseParams: 'MCHNT_ATTR',
					fieldLabel: '商户类型',
					width:150,
					hiddenName: 'etpsAttr',
					id:'etpsAttrId',
					listeners : {
/*						'select' : function() {
							var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
							if(etpsAttrTmp == '24')
							{									
								Ext.getCmp('accountHide1').hide();	
								Ext.getCmp('accountHide2').hide();	
								Ext.getCmp('licenceNo').allowBlank = true;
								Ext.getCmp('faxNo').allowBlank = true;
							}else{
								Ext.getCmp('accountHide1').show();	
								Ext.getCmp('accountHide2').show();
								Ext.getCmp('licenceNo').allowBlank = false;
								Ext.getCmp('faxNo').allowBlank = false;
							}
						},
						'change' :function() {
							var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
							if(etpsAttrTmp == '24')
							{
								Ext.getCmp('accountHide1').hide();	
								Ext.getCmp('accountHide2').hide();	
								Ext.getCmp('licenceNo').allowBlank = true;
								Ext.getCmp('faxNo').allowBlank = true;
							}else{
								Ext.getCmp('accountHide1').show();	
								Ext.getCmp('accountHide2').show();
								Ext.getCmp('licenceNo').allowBlank = false;
								Ext.getCmp('faxNo').allowBlank = false;
							}
						}*/
					}
				}]
			},{
        		columnWidth: .66,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'MCHT_GROUP_NEW',
					fieldLabel: '所属集团商户',
					width:150,
					id: 'idmchtGroupId',
					hiddenName: 'tblMchtBaseInfTmpTmp.mchtGroupId',
					editable: true,
					allowBlank: false,
					anchor: '90%'
		        }]
			},{
				columnWidth: .66,
				layout: 'form',
				id: 'compNmPanel',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '注册名称',
					maxLength: '60',
					vtype: 'isOverMax',
					id: 'mchtNm',
					name:'tblMchtBaseInfTmpTmp.mchtNm',
					anchor: '90%'
//					width:476
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '英文名称',
					maxLength: 40,
					vtype: 'isOverMax',
					regex: /^\w+[\w\s]+\w+$/,
					regexText:'英文名称必须是字母，如Bill Gates',
					id: 'engName',
					name: 'tblMchtBaseInfTmpTmp.engName',
					width: 150
				}]
			},{				
				columnWidth: .66,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '注册地址',
//					width:476,
					anchor: '90%',
					maxLength: 60,
					vtype: 'isOverMax',
					id: 'compaddr',
					name:'tblMchtBaseInfTmpTmp.compaddr'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '邮政编码',
					width:150,
					regex: /^[0-9]{6}$/,
					regexText: '邮政编码必须是6位数字',
					id: 'postCode',
					name: 'tblMchtBaseInfTmpTmp.postCode'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide1',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '营业执照编号',
					width:150,
					maxLength: 20,
					id: 'licenceNo',
					name: 'tblMchtBaseInfTmpTmp.licenceNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide2',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '税务登记证号码',
					maxLength: 20,
					vtype: 'isOverMax',
					width:150,
					id: 'faxNo',
					name:'tblMchtBaseInfTmpTmp.faxNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
//				labelwidth: 100,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '注册资金',
					regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
					regexText: '注册资金必须是正数，如123.45',
					maxLength: 12,
					vtype: 'isOverMax',
					width:150,
					id: 'busAmt',
					name: 'tblMchtBaseInfTmpTmp.busAmt'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					id :'licenceEndDate',
					editable: false,
					width:150,
					name: 'tblMchtBaseInfTmpTmp.licenceEndDate',
					//vtype: 'daterange',
		            fieldLabel: '营业执照有效期',
		            //endDateField: 'contendDate',
		            //value:nowDate,
//		            minValue:nowDate,
//					blankText: '请选择起始日期',
					allowBlank: true
				}]											
			},{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'textfield',
					fieldLabel: '公司网址',
					regex:/^[a-zA-z]+:/,
					regexText:'必须是正确的地址格式，如http://www.xxx.com',
//                    maxLength: 60,
					vtype: 'isOverMax',
					width:150,
					id: 'homePage',
					name: 'tblMchtBaseInfTmpTmp.homePage',
					maxLength: 50
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide3',
//				labelwidth: 100,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '法人代表',
					width:150,
					maxLength: 50,
					vtype: 'lengthRange50',
					id: 'manager',
					name:'tblMchtBaseInfTmpTmp.manager'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide4',
				items: [{
					xtype: 'combofordispaly',
		        	baseParams: 'CERTIFICATE',
					fieldLabel: '法人证件类型',
					width:150,
					allowBlank: false,
					hiddenName: 'artifCertifTp',
					id: 'idartifCertifTp',
					value: '01'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide5',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '法人证件号码',
					width:150,
					maxLength: 20,
					vtype: 'isOverMax',
					id: 'identityNo',
					name:'tblMchtBaseInfTmpTmp.identityNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					fieldLabel: '企业电话',
					width:150,
					regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
					regexText:'长度不够，电话必须是7~9位',
					maxLength: 15,
					vtype: 'isOverMax',
					id: 'electrofax',
					name: 'tblMchtBaseInfTmpTmp.electrofax'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
//				hidden:true,
				items: [{
						xtype: 'displayfield',
						fieldLabel: '企业传真',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'tblMchtBaseInfTmpTmp.fax'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'displayfield',
					id :'prolDate',
					//editable: false,
					width:150,
					name: 'tblMchtBaseInfTmpTmp.prolDate',
					//vtype: 'daterange',
		            fieldLabel: '证件有效期至',
		            //endDateField: 'contendDate',
		            //value:nowDate,
		            //minValue:nowDate,
					//blankText: '请选择起始日期',
					//allowBlank: false
				}]
			
			}			
			]},
			{
				id: 'imageInfo1',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '营业执照影像',
				layout: 'column',
//				autoScroll: true,
				hidden:false,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items : dataview1,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view1',
						handler:function() {
							storeImg1.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:'upload1'
								}
							});
						}
					},{}]
			},{
				id: 'imageInfo2',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '税务登记证影像',
				layout: 'column',
//				autoScroll: true,
				hidden:false,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items : dataview2,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view2',
						handler:function() {
							storeImg2.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:'upload2'
								}
							});
						}
					},{}]
			},{
				id: 'imageInfo3',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '证件影像',
				layout: 'column',
//				autoScroll: true,
				hidden:false,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items : dataview3,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view3',
						handler:function() {
							storeImg3.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:'upload3'
								}
							});
						}
					},{}]
			},{
				id: 'imageInfo4',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '装机地址影像',
				layout: 'column',
//				autoScroll: true,
				hidden:false,
				defaults: {
					bodyStyle: 'padding-left: 10px',
					visibility : 'hidden' 
				},
				items : dataview4,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view4',
						handler:function() {
							storeImg4.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:'upload4'
								}
							});
						}
					},{}]
			},{
				id: 'manageInfo',
				width: 950,
				collapsible: true,
				xtype: 'fieldset',
				title: '经营信息',
				layout: 'column',
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth: .66,
					layout: 'form',
					id: 'mchtNmPanel',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '经营名称',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'compNm',
						name:'tblMchtBaseInfTmpTmp.compNm',
						anchor: '90%'
//						width:476
					}]
				},{
	        		columnWidth: .33,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '营业开始时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '时间输入错误',
						id: 'openTime',
						name: 'openTime',
						value: '00:00'
		        	}]
				},{				
					columnWidth: .66,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '装机地址',
//						width:476,
						anchor: '90%',
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'addr',
						name:'tblMchtBaseInfTmpTmp.addr'
					}]
				},{
	        		columnWidth: .33,
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						fieldLabel: '营业结束时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '该输入框只能输入数字0-9',
						value: '23:59',
						id: 'closeTime',
						name: 'closeTime'
		        	}]
				},{					
					columnWidth: .99,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
							xtype: 'combofordispaly',
				        	baseParams: 'CITY_CODE',
							fieldLabel: '所在地区',
							hiddenName: 'areaNo',
							width:260
			        	}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '业务联系人',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contact',
						name:'tblMchtBaseInfTmpTmp.contact'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '业务联系人电话',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commTel',
						name:'tblMchtBaseInfTmpTmp.commTel'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
							fieldLabel: '业务联系人邮箱',
							width:150,
							maxLength: 40,
							vtype: 'isOverMax',
							id: 'commEmail',
							name: 'tblMchtBaseInfTmpTmp.commEmail',
							vtype: 'email'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '财务联系人',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'finacontact',
						name:'tblMchtBaseInfTmpTmp.finacontact'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '财务联系人电话',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'finacommTel',
						name:'tblMchtBaseInfTmpTmp.finacommTel'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
							fieldLabel: '财务联系人邮箱',
							width:150,
							maxLength: 40,
							vtype: 'isOverMax',
							id: 'finacommEmail',
							name: 'tblMchtBaseInfTmpTmp.finacommEmail',
							vtype: 'email'
					}]
				}			
				]
			},{
				id: 'businessInfo',
				width: 950,
				collapsible: true,
				xtype: 'fieldset',
				title: '营业状况',
				layout: 'column',
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth: .66,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '经营内容',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'busInfo',
						name:'tblMchtBaseInfTmpTmp.busInfo',
						anchor: '90%'
//						width:476
					}]
				},{				
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						width: 150,
						fieldLabel: '经营面积(m²)',
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'busArea',
						name:'tblMchtBaseInfTmpTmp.busArea'
					}]
				}/*,{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: '月平均营业额',
	                    id: 'monaveTrans',
		                width: 150,
		                hidden:true,
	                    allowBlank: true,
	                    editable: false,
	                    hiddenName: 'tblMchtBaseInfTmpTmp.monaveTrans',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['01','5万元以下'],['02','25万元以下'],['03','50万元以下'],['04','100万元以下'],['05','100万元以上']]
	                    })
	                }]
	            },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                    xtype: 'combofordispaly',
	                    fieldLabel: '单笔平均交易额',
	                    id: 'sigaveTrans',
		                width: 150,
		                hidden:true,
	                    allowBlank: true,
	                    editable: false,
	                    hiddenName: 'tblMchtBaseInfTmpTmp.sigaveTrans',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['01','50元以下'],['02','200元以下'],['03','1000元以下'],['04','5000元以下'],['05','5000元以上']]
	                    })
	                }]
	            }*/
						]
			},{
				xtype: 'fieldset',
				id: 'next1',
				defaults: {
					bodyStyle: 'padding-left: 100px'
				},
				width: 950, 
				buttonAlign: 'center',
				buttons: nextArr1
				
			},{
				id: 'settleInfo',
				width: 950,
				collapsible: true,
				xtype: 'fieldset',
				title: '账户信息',
				layout: 'column',
				hidden:true,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '账户户名',
						width:150,
						maxLength: 80,
						vtype: 'isOverMax',
						id: 'settleAcctNm',
						name:'tblMchtSettleInfTmpTmp.settleAcctNm'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
	                    fieldLabel: '账户号',
	                    maxLength: 40,
	                    allowBlank: false,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
	                    width:150,
	                    id: 'settleAcct',
	                    name: 'tblMchtSettleInfTmpTmp.settleAcct'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					hidden:true,
					items: [{
						xtype: 'basecomboselect',
				        baseParams: 'CERTIFICATE',
						fieldLabel: '证件类型',
						id: 'certifTypeId',
						hiddenName: 'certifType',
						allowBlank: true,
						width:150,
						value: '01'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					hidden:true,
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '证件号码',
						width:150,
						allowBlank : true,
						id: 'certifNoId',
						name:'certifNoId'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
//					hidden:true,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '账户开户行号',
						width:150,
						regex: /^[0-9]{12}$/,
						regexText: '请输入12位数字0-9',
//						maskRe: /^[0-9]{12}$/,
						allowBlank : true,
						id: 'openStlno',
						name:'tblMchtSettleInfTmpTmp.openStlno'
					}]
				},{
					columnWidth: .99,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '账户开户行名称',
						id: 'settleBankNm',
						name:'tblMchtSettleInfTmpTmp.settleBankNm',
						maxLength: 80,
						width:350,
						vtype: 'isOverMax'
//						anchor: '90%'
					}]
				},{
					columnWidth: .99,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '费率信息',
						id: 'reserved',
						name: 'tblMchtSettleInfTmpTmp.reserved',
						maxLength: 60,
						vtype: 'isOverMax',
						anchor: '90%'
					}]
				},{
					columnWidth: .55,
					layout: 'form',
					hidden:true,
					items: [{
						xtype: 'radiogroup',
						fieldLabel: '结算账户类型',
						width:200,
						allowBlank : true,
						blankText:'至少选择一项',
						items: [{
									boxLabel : '对公账户',
									inputValue : '0',
									id: 'clearType',
									name : "clearTypeNm"
								}, {
									boxLabel : '对私账户',
									inputValue : '1',
									name : "clearTypeNm"
						}]
					}]
				}			
				]
			},
//			{
//				id: 'contdateInfo',
//				width: 950,
//				collapsible: true,
//				xtype: 'fieldset',
//				title: '合同有效期',
//				layout: 'column',
//				hidden:true,
//				defaults: {
//					bodyStyle: 'padding-left: 10px'
//				},
//				items: [{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						id :'contstartDate',
//						editable: false,
//						width:150,
//						name: 'tblMchtBaseInfTmpTmp.contstartDate',
//						vtype: 'daterange',
//			            fieldLabel: '合同起始日期',
//			            endDateField: 'contendDate',
//						blankText: '请选择起始日期',
//						allowBlank: false
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						id :'contendDate',
//						editable: false,
//						width:150,
//						name: 'tblMchtBaseInfTmpTmp.contendDate',
//						vtype: 'daterange',
//			            fieldLabel: '合同终止日期',
//			            startDateField: 'contstartDate',
//						blankText: '请选择终止日期',
//						allowBlank: false
//					}]
//				}
//						]			
//			},
			{
				id: 'imageInfo5',
				//width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '合同影像',
				layout: 'column',
//				autoScroll: true,
				hidden:true,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items : dataview5,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view5',
						handler:function() {
							storeImg5.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:'upload5'
								}
							});
						}
					}]
			},{
			id: 'imageInfo',
			width: 950,
			collapsible: true,
			xtype: 'fieldset',
			title: '其他影像',
			layout: 'column',
//			autoScroll: true,
			hidden:true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items : dataview,
			tbar: [{
    				xtype: 'button',
					text: '刷新',
					width: '80',
					
					id: 'view',
					handler:function() {
						storeImg.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload'
							}
						});
					}
				},{
					xtype: 'button',
					width: '80',
					text: '上传',
					hidden:true,
					id: 'upload',
					handler:function() {
						hasUpload = "1";
						dialog.show();
					}
				}]
		},{

			xtype: 'tabpanel',
        	id: 'tab000',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 320,
            hidden: true,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
        	items:[{
				title:'费率设置',
                id: 'feeamt1',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					fieldLabel: '计费代码',
							id: 'feeRate',
							name: 'tblMchtBaseInfTmpTmp.feeRate',
							readOnly: true
						}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
	       					xtype: 'button',
							iconCls: 'detail',
							text:'计费算法配置说明',
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>' +
											'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
											'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
											'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
											'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。',
									title: '计费算法配置说明',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.OK,
									modal: true,
									width: 650
								});
							}
                		}]
                	}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
                	width:600,
                	items: [detailGrid]
                }]
		    }]
        
		},{
        	xtype: 'tabpanel',
			activeTab : 0,
			hidden:true,
			height : 450,
        	region: 'center',
        	id:'termgrid',
        	items:[termGridPanel]
		},{	xtype: 'fieldset',
			id: 'next2',
			hidden :true,
			defaults: {
				bodyStyle: 'padding-left: 100px'
			},
			width: 950, 
			buttonAlign: 'center',
			buttons: nextArr2		
		}
		]
	});
	
	
	

	 var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1000,
//		height:550,
		 autoScroll: true,
//		autoHeight: 680,
		items: [mchntForm],
		buttonAlign: 'center',
//		closable: false,
		closeAction: 'close',
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
				detailWin.setTitle('商户详细信息');
				detailWin.show();
				
				if(baseStore.getAt(0).data.mchtGroupId == '-'){
					Ext.getCmp('idmchtGroupId').setValue('无');
				}else{
					Ext.getCmp('idmchtGroupId').setValue(baseStore.getAt(0).data.mchtGroupId);
				}
				
				
				if('B' == baseStore.getAt(0).data.mchtStatus||'A' == baseStore.getAt(0).data.mchtStatus) {
					Ext.getCmp('settleAcct').setValue(baseStore.getAt(0).data.settleAcct.substring(1,settleAcct.lenght));
				}else {
					Ext.getCmp('settleAcct').setValue(baseStore.getAt(0).data.settleAcct.substring(1,settleAcct.lenght));
				}
				//Ext.getCmp('validatyDate').setValue(baseStore.getAt(0).data.validatyDate);
				//将数据库中的8位日期变成带分割符的日期来表示
				var licenceEndDate = baseStore.getAt(0).data.licenceEndDate;
				var prolDate = baseStore.getAt(0).data.prolDate;
				if (licenceEndDate != null && licenceEndDate != '' && licenceEndDate.length == 8) {
					var tmpStr = licenceEndDate.substr(0,4) + '-' + licenceEndDate.substr(4,2) + '-' + licenceEndDate.substr(6,2);	
					Ext.getCmp('licenceEndDate').setValue(tmpStr);
				}
				if (prolDate != null && prolDate != '' && prolDate.length == 8) {
					var tmpStr = prolDate.substr(0,4) + '-' + prolDate.substr(4,2) + '-' + prolDate.substr(6,2);	
					Ext.getCmp('prolDate').setValue(tmpStr);
				}
				//将数据库中保存的4位营业时间加载为带有：的4位时间格式（HH：MM）
				var OT = baseStore.getAt(0).data.openTime;
				var CT = baseStore.getAt(0).data.closeTime; 
				if(null != OT & '' != OT) {
					Ext.getCmp('openTime').setValue(OT.substring(0,2) + ':' + OT.substring(2,4));
				}
				if(null != CT & '' != CT) {
					Ext.getCmp('closeTime').setValue(CT.substring(0,2) + ':' + CT.substring(2,4));
				}
//				baseStore.getAt(0).data.contstartDate==''?baseStore.getAt(0).data.contstartDate:Ext.getCmp('contstartDate').setValue(formatDt(baseStore.getAt(0).data.contstartDate));
//				baseStore.getAt(0).data.contendDate==''?baseStore.getAt(0).data.contendDate:Ext.getCmp('contendDate').setValue(formatDt(baseStore.getAt(0).data.contendDate));
				custom = new Ext.Resizable('showBigPic', {
					    wrap:true,
					    pinned:true,
					    minWidth: 50,
					    minHeight: 50,
					    preserveRatio: true,
					    dynamic:true,
					    handles: 'all',
					    draggable:true
					});
				customEl = custom.getEl();
				customEl.on('dblclick', function(){
					customEl.puff();
					rollTimes =0;
				});
				customEl.hide(true);


				storeImg.on('load',function(){
					for(var i=0;i<storeImg.getCount();i++){
						var rec = storeImg.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg,val.id);
				    	});
					}
				});

				storeImg1.on('load',function(){
					for(var i=0;i<storeImg1.getCount();i++){
						var rec = storeImg1.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg1,val.id);
				    	});
					}
				});

				storeImg2.on('load',function(){
					for(var i=0;i<storeImg2.getCount();i++){
						var rec = storeImg2.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg2,val.id);
				    	});
					}
				});

				storeImg3.on('load',function(){
					for(var i=0;i<storeImg3.getCount();i++){
						var rec = storeImg3.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg3,val.id);
				    	});
					}
				});

				storeImg4.on('load',function(){
					for(var i=0;i<storeImg4.getCount();i++){
						var rec = storeImg4.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg4,val.id);
				    	});
					}
				});

				storeImg5.on('load',function(){
					for(var i=0;i<storeImg5.getCount();i++){
						var rec = storeImg5.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg5,val.id);
				    	});
					}
				});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload'
						
					}
				});
				storeImg1.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload1'
						
					}
				});
				storeImg2.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload2'
						
					}
				});
				storeImg3.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload3'
						
					}
				});
				storeImg4.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload4'
						
					}
				});
				storeImg5.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload :'upload5'
						
					}
				});
				gridStore.load({
					params: {
						start: 0,
						discCd: Ext.getCmp('feeRate').getValue(),
						mccCode: baseStore.getAt(0).data.mcc
					}
				});
				termStore.on('beforeload', function() {
					Ext.apply(this.baseParams, {
						start : 0,
						termId : Ext.getCmp('termNoQ').getValue(),
						termSta : Ext.getCmp('state').getValue(),
						startTime : topQueryPanel.getForm().findField('startTime')
								.getValue(),
						endTime : topQueryPanel.getForm().findField('endTime')
								.getValue(),
						mchnNo : baseStore.getAt(0).data.mchtNo,//Ext.getCmp('mchnNoQ').getValue(),
						termTp : Ext.getCmp('idtermtpsearch').getValue()
					});
				});
				termStore.load();

				store.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});					
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});	
}