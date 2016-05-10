
// type, null:show detail;'approve':审批;’history':修改历史
function showBrhInfDetailS(brhId, El, type, callback, seqId) {


//	-----------------------------------费率配置----------------------------------------------
	var feeUrl = 'gridPanelStoreAction.asp?storeId=agentSelFeeCtl&brhId='+brhId;
	var cashRateUrl = 'gridPanelStoreAction.asp?storeId=brhCashRateInfSel&brhId='+brhId;
	if (type == 'history'){
		feeUrl = 'gridPanelStoreAction.asp?storeId=agentSelFeeCtlHis&brhId='+brhId+ '&seqId=' + seqId;
		cashRateUrl += '&seqId=' + seqId;
	}
	
	var feeCtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: feeUrl
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'feeName',mapping: 'feeName'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'discId',mapping: 'discId'}
		]),
		autoLoad: true
	}); 
	
	//复选框选择模式  
//	var checkboxSM = new Ext.grid.CheckboxSelectionModel({  
//	    checkOnly: false,  
//	    singleSelect: false  
//	});  
	
	var feeCtlColModel = new Ext.grid.ColumnModel([
//   		checkboxSM,
   		new Ext.grid.RowNumberer(),
   		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
   		{header: '费率名称',dataIndex: 'feeName',align: 'center',width: 120,id:'feeName'},
   		{header: '费率类型',dataIndex: 'feeType',align: 'left',width: 120,id:'feeType',renderer:feeType },
   		{header: '手续费',dataIndex: 'feeRate',align: 'left',width: 120,id:'feeRate'  }
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
	
	 var feeCtlGrid = new Ext.grid.GridPanel({
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
//			sm: checkboxSM,
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
				text: '已开通分润费率',
				name: 'queryFeeCtlD',
				id: 'queryFeeCtlD',
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
	
	var brhCashRateStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: cashRateUrl
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'name',mapping: 'name'},
			{name: 'type',mapping: 'type'},
			{name: 'rate',mapping: 'rate'}
		]),
		autoLoad: true
	}); 
	
	var brhCashRateColModel = new Ext.grid.ColumnModel([
	  		new Ext.grid.RowNumberer(),
	  		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
	  		{header: '费率名称',dataIndex: 'name',align: 'center',width: 120,id:'name'},
	  		{header: '费率类型',dataIndex: 'type',align: 'left',width: 120,id:'type',renderer:feeType },
	  		{header: '手续费',dataIndex: 'rate',align: 'left',width: 120,id:'rate'}
	  	]);
	
	 var brhCashRateGrid = new Ext.grid.EditorGridPanel({
		 	height:400,
			collapsible: false,
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
			region: 'west',
			clicksToEdit: true,
			autoExpandColumn: 'name',
			store: brhCashRateStore,
//			sm: checkboxSM1,
			cm: brhCashRateColModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载合作伙伴提现费率信息......'
			},
			listeners : {  
	         	'cellclick':selectableCell
	        }  ,
	        tbar: 	[{
				xtype: 'label',
				text: '提现费率开通',
				name: 'queryBrhCashRate',
				id: 'queryBrhCashRate',
				iconCls: 'query',
				width: 80,
				handler:function() {
					brhCashRateStore.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: brhCashRateStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});

	 brhCashRateGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(brhCashRateGrid.getView().getRow(brhCashRateGrid.getSelectionModel().last)).frame();
	//	                                       			feeCfgStore.load();
		}
	});
	
	
	brhCashRateStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
	});	
	
	
//	-----------------------------费率信息END--------------------------------------
	/*// 合作伙伴级别
	var brhLvlStoreDt = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO_DTL', function(ret) {
		brhLvlStoreDt.loadData(Ext.decode(ret));
	});*/

	// 人员属性
	var memPropertyStoreDt = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MEM_PROPERTY', function(ret) {
		memPropertyStoreDt.loadData(Ext.decode(ret));
	});
	/*	
	// 职务类型	
	var jobTypeStoreDt = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('JOB_TYPE', function(ret) {
		jobTypeStoreDt.loadData(Ext.decode(ret));
	});
	
// 上级合作伙伴编号
	var upBrhStoreDt = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_UP', function(ret) {
		upBrhStoreDt.loadData(Ext.decode(ret));
	});
	//银联机构号
	var cupBrhStore1Dt = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data',
		autoLoad : false
	});
	SelectOptionsDWR.loadCupBrhIdOptData(brhId, function(ret) {
		cupBrhStore1Dt.loadData(Ext.decode(ret));
	});*/
	
	var agentInfUrl = 'loadRecordAction.asp?storeId=getAgentInf&brhId='+brhId
	
	if (type == 'history'){
		agentInfUrl = 'loadRecordAction.asp?storeId=getAgentInfHis&brhId='+brhId+ '&seqId=' + seqId;
	}
	var detailGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : agentInfUrl
				}),
		reader : new Ext.data.JsonReader({
			root : 'data'
			}, [
				{name : 'brhIdDt',mapping : 'brhId'}, 
				{name : 'brhLevelDt',mapping : 'brhLevel'},
				{name : 'upBrhIdDt',mapping : 'upBrhId'},
				{name : 'brhNameDt',mapping : 'brhName'},
				{name : 'brhAddrDt',mapping : 'brhAddr'},
				{name : 'brhTelNoDt',mapping : 'brhTelNo'},
				{name : 'postCodeDt',mapping : 'postCode'},
				{name : 'brhContNameDt',mapping : 'brhContName'},
				{name : 'cupBrhIdDt',mapping : 'cupBrhId'},
				{name : 'resv1Dt',mapping : 'resv1'},
				{name : 'resv2Dt',mapping : 'resv2'},
				
				{name : 'settleOrgNoDt',mapping : 'settleOrgNo'},
				{name : 'settleJobTypeDt',mapping : 'settleJobType'},
				{name : 'settleMemPropertyDt',mapping : 'settleMemProperty'},
				{name : 'createNewNoDt',mapping : 'createNewNo'},
				
				{name : 'resv1_5Dt',mapping : 'resv1_5'},
				{name : 'resv1_6Dt',mapping : 'resv1_6'},
				{name : 'resv1_7Dt',mapping : 'resv1_7'},
				{name : 'resv1_8Dt',mapping : 'resv1_8'},
				{name : 'settleFlagDt',mapping : 'settleFlag'},
				{name : 'settleAcctNmDt',mapping : 'settleAcctNm'},
				{name : 'settleAcctDt',mapping : 'settleAcct'},
				{name : 'settleBankNmDt',mapping : 'settleBankNm'},
				{name : 'settleBankNoDt',mapping : 'settleBankNo'},
				{name : 'brhFeeDt',mapping : 'brhFee'},
				
				{name:'agentNo',mapping:'agentNo'},
				{name:'allotRate',mapping:'allotRate'},
				{name:'amt1',mapping:'amt1'},
				{name:'amt2',mapping:'amt2'},
				{name:'baseAmtMonth',mapping:'baseAmtMonth'},
				{name:'crtTime',mapping:'crtTime'},
				{name:'discId',mapping:'discId'},
				{name:'enableFlag',mapping:'enableFlag'},
				{name:'extJcbRate',mapping:'extJcbRate'},
				{name:'extMasterRate',mapping:'extMasterRate'},
				{name:'extRate1',mapping:'extRate1'},
				{name:'extRate2',mapping:'extRate2'},
				{name:'extRate3',mapping:'extRate3'},
				{name:'extVisaRate',mapping:'extVisaRate'},
				{name:'flag1',mapping:'flag1'},
				{name:'flag2',mapping:'flag2'},
				{name:'gradeAmtMonth',mapping:'gradeAmtMonth'},
				{name:'mchtGrp',mapping:'mchtGrp'},
				{name:'mchtNo',mapping:'mchtNo'},
				{name : 'misc',mapping : 'misc'},
				{name:'misc2',mapping:'misc2'},
				{name:'name',mapping:'name'},
				{name:'promotionBegDate',mapping:'promotionBegDate'},
				{name:'promotionEndDate',mapping:'promotionEndDate'},
				{name:'promotionRate',mapping:'promotionRate'},
				{name:'seq',mapping:'seq'},
				{name:'specFeeRate',mapping:'specFeeRate'},
				{name:'uptOpr',mapping:'uptOpr'},
				{name:'uptTime',mapping:'uptTime'},
				
				{name:'bank_name',mapping:'bank_name'},
				{name:'subbranch_name',mapping:'subbranch_name'},
				{name:'province',mapping:'province'},
				{name:'subbranch_id',mapping:'subbranch_id'},
				{name:'city',mapping:'city'},
				
				{name : 'settleType',mapping : 'resv4'},
			]),
		autoLoad : false
	});
	// ******************拒绝原因**********开始********
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lastBrhRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			queryBrhId:brhId
		},callback : function(records, options, success) {
			if (success) {
				if (reasonStore.getAt(0) == null || reasonStore.getAt(0).data.refuseInfo == '') {
					//Ext.getCmp('refuseReason').hide();
				} else {
//					Ext.getCmp('approveProcessInfo').show();
					Ext.getCmp('refuseReasonTxt').setText(reasonStore.getAt(0).data.refuseInfo);
				}
			}else{
				
			}
		}
	});
	// ******************拒绝原因**********结束********
	var detailBrhInfoForm = new Ext.form.FormPanel({
		region: 'center',
				frame : true,
				autoScroll : true,
				height: 400,
				layout : 'column',
				waitMsgTarget : true,
				width:865,
				defaults : {
					bodyStyle : 'padding-left: 20px;overflow-y:auto;'
				},
				items : [{
					xtype : 'fieldset',
					title : '基本信息',
					layout : 'column',
					labelWidth : 120,
					width : 850,
					items : [{
						columnWidth : .99,
						xtype : 'panel',
						id:'approveProcessInfo',
						layout : 'form',
						labelWidth: 0,
						style:'padding-bottom:10',
						hidden: true,
						width: 850,
						items : [{
							columnWidth : .99,
							layout : 'column',
							items : [{
					        	xtype: 'button',
								id: 'btnShowProcess',
								height:30,
								text:'查看审批过程',
								handler : function() {
									showApproveInfo(brhId);
								}
							},{
					        	xtype: 'label',
					        	columnWidth : .88,
//					        	width:400,
								id: 'refuseReasonTxt',
								style:'color:red;font-weight:bold;padding:10 0 0 20'
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴编码',
							id : 'brhIdDt',
							name : 'brhIdDt',
							width : 200
						}]
					},
					{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴号',
							id : 'createNewNoDt',
							name : 'createNewNoDt',
							width : 200
						}]
					}, /*{
						columnWidth : .5,
						layout : 'form',
						hidden : true,
						items : [{
							xtype : 'combofordispaly',
							store : brhLvlStoreDt,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'brhLevelDt',
							fieldLabel : '合作伙伴级别',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true
							
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						hidden : true,
						items : [{
							xtype : 'combofordispaly',
							store : upBrhStoreDt,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'upBrhIdDt',
							fieldLabel : '上级合作伙伴编码',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true
						}]
					}, */{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴名称',
							width : 200,
							id : 'brhNameDt',
							name : 'brhNameDt'
						}]
					},/*{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '机构代码',
							width : 200,
							id : 'settleOrgNoDt',
							name : 'settleOrgNoDt'
						}]
					},*/{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							store : memPropertyStoreDt,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'settleMemPropertyDt',
							fieldLabel : '人员属性',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true
							
						}]
					},/*{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							store : jobTypeStoreDt,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'settleJobTypeDt',
							fieldLabel : '职务类别',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true
							
						}]
					}, */{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							baseParams : 'CITY_CODE',
							fieldLabel : '合作伙伴地区码',
							hiddenName : 'resv1Dt',
							width : 200
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴地址',
							id : 'brhAddrDt',
							name : 'brhAddrDt',
							width : 200
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴联系电话',
							id : 'brhTelNoDt',
							name : 'brhTelNoDt',
							width : 200
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '负责人姓名',
							id : 'brhContNameDt',
							name : 'brhContNameDt',
							width : 200
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '是否系统级签到',
							width : 200,
							disabled:true,
							name : 'resv1_5Dt',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '否',
									inputValue : '0'
//									name : "resv1_5Dt"
								}, {
									boxLabel : '是',
									inputValue : '1'
//									name : "resv1_5Dt"
							}]
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '生成对账流水',
							width : 200,
							disabled:true,
							name : 'resv1_6Dt',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '不生成',
									inputValue : '0'
								}, {
									boxLabel : '生成',
									inputValue : '1'
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '清算到机构账号',
							allowBlank : false,
							width : 200,
							disabled:true,
							name : 'resv1_7Dt',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '不清算',
									inputValue : '0'
								}, {
									boxLabel : '清算',
									inputValue : '1'
									
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						id:'settleFlagDtIds',
						hidden:true,
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '结算账户类型',
							id:'settleFlagDtId',
							disabled:true,
							width : 200,
							name : 'settleFlagDt',
							items: [{
									boxLabel : '对公账户',
									inputValue : '0',
									width :100
								}, {
									boxLabel : '对私账户',
									inputValue : '1',
									width :100
							}]
						}]
					},{
					
						columnWidth: .5,
						layout: 'form',
						items: [{
							xtype : 'combo',
							fieldLabel : '结算类型',
							id : 'settleTypeDtlId',
							hiddenName : 'settleTypeDtlType',
							allowBlank : false,
							disabled:true,
							blankText:'至少选择一项',
							width: 180,
							store : new Ext.data.ArrayStore({
								fields : [ 'valueField',
										'displayField' ],
								data : [ [ '0', 'T+N' ],
										[ '1', '周期结算' ] ]
							}),
							listeners: {
								'select': function(combo,record,number) {
									if(number==0){
										Ext.getCmp('settleTypeDtlId0').show();
										Ext.getCmp('settleTypeDtlId1').hide();
										Ext.getCmp('T_Ndtl').allowBlank=false;
										Ext.getCmp('periodIdDtl').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeDtlId0').hide();
										Ext.getCmp('settleTypeDtlId1').show();
										Ext.getCmp('T_Ndtl').allowBlank=true;
										Ext.getCmp('periodIdDtl').allowBlank=false;
									}
								},'change': function(combo,number,orignal) {
									if(number==0){
										Ext.getCmp('settleTypeDtlId0').show();
										Ext.getCmp('settleTypeDtlId1').hide();
										Ext.getCmp('T_Ndtl').allowBlank=false;
										Ext.getCmp('periodIdDtl').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeDtlId0').hide();
										Ext.getCmp('settleTypeDtlId1').show();
										Ext.getCmp('T_Ndtl').allowBlank=true;
										Ext.getCmp('periodIdDtl').allowBlank=false;
									}
								}
							}
						}]
			},{
				columnWidth: .5,
				layout: 'form',
				id : 'settleTypeDtlId0',
				hidden:true,
				lableWidth:2,
				lablePad:2,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'T+N',
//					width:100,
					anchor: '90%',
					allowBlank : false,
					disabled:true,
					maxLength: 3,
					vtype: 'isNumber',
					regex: /^(([0-9]{1})|([1-9]{1}\d{0,2}))$/,
					regexText: '请输入0-999的数字',
					id: 'T_Ndtl',
					name:'T_Ndtl'
				}]
			
			},{
				columnWidth: .5,
				hidden:true,
				layout: 'form',
				id : 'settleTypeDtlId1',
				items: [{
					xtype : 'combo',
					fieldLabel : '周期结算',
					id : 'periodIdDtl',
					allowBlank : false,
					disabled:true,
					hiddenName : 'period',
					blankText:'至少选择一项',
					width: 180,
//					value:'0',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField','displayField' ],
						data : [ 	[ '0', '周结' ],
									[ '1', '月结' ],
									[ '2', '季结' ]]
					}),
					listeners: {}
				}]
	
			}, {
						columnWidth : .5,
						layout : 'form',
						id:'settleAcctNmDtId',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '账户户名',
								id : 'settleAcctNmDt',
								name : 'settleAcctNmDt',
								width : 200
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						id:'settleAcctDtId',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '账户号',
								id : 'settleAcctDt',
								name : 'settleAcctDt',
								width : 200
							}]
					}, 
					
					
					
					{
						columnWidth : .5,
						layout : 'form',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '开户银行-省',
								id : 'provinceDtl',
								name : 'province',
								width : 200
							}]
					},{
						columnWidth : .5,
						layout : 'form',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '开户银行-市',
								id : 'cityDtl',
								name : 'city',
								width : 200
							}]
					},{
						columnWidth : .5,
						layout : 'form',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '开户银行',
								id : 'bank_name',
								name : 'bank_name',
								width : 200
							}]
					},
					
					
					
					
					{
						columnWidth : .5,
						layout : 'form',
						id:'settleBankNmDtId',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '账户开户行名称',
								id : 'settleBankNmDt',
								name : 'settleBankNmDt',
								width : 200
							}]
					}, {
						columnWidth : .5,
						layout : 'form',
						id:'settleBankNoDtId',
//						hidden:true,
						items : [{
								xtype: 'displayfield',
								fieldLabel : '账户开户行号',
								id : 'settleBankNoDt',
								name : 'settleBankNoDt',
								width : 200
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
							name : "resv1_8"}]}
//					,
//				{
//					columnWidth : .5,
//					layout : 'form',
//					items : [{
//						xtype: 'displayfield',
//						fieldLabel : '',
//						hidden:true,
//						id : 'settleBankNm',
//						name : 'settleBankNm'
//					}]}
				]},
				{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '费率信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					items : [{
						columnWidth : 1,
						layout : 'form',
						hidden:true,
						items : [{
							xtype: 'displayfield',
							fieldLabel : '规则名称*',
							id:'name',
							name : 'name',
							allowBlank : true,
							maxLength : 30,
							//blankText : '费率名称不能为空',
							width : 636
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '激励开始日期',
							id:'promotionBegDate',
							name : 'promotionBegDate',
							vtype : 'daterange',
							endDateField : 'promotionEndDate',
							format : 'Y-m-d',
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '激励结束日期',
							id:'promotionEndDate',
							name : 'promotionEndDate',
							vtype : 'daterange',
							startDateField : 'promotionBegDate',
							format : 'Y-m-d',
							width : 250
						}]
					},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '月交易额(万元)*',
							id:'baseAmtMonth',
							name : 'baseAmtMonth',
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
							xtype: 'displayfield',
							fieldLabel : '提档交易额(万元)*',
							id:'gradeAmtMonth',
							name : 'gradeAmtMonth',
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
							xtype: 'displayfield',
							fieldLabel : '激励系数%*',
							id:'promotionRate',
							name : 'promotionRate',
							allowBlank : true,
							blankText : '激励系数不能为空',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}/*,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '成本费率(%)*',
							id:'feeRate',
							name : 'feeRate',
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
							xtype: 'displayfield',
							fieldLabel : '成本封顶值*',
							id:'capFeeValue',
							name : 'capFeeValue',
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
							xtype: 'displayfield',
							fieldLabel : '分润比率%*',
							id:'allotRate',
							name : 'allotRate',
							allowBlank : false,
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
							xtype: 'displayfield',
							fieldLabel : 'JCB成本费率%',
							id:'extJcbRate',
							name : 'extJcbRate',
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
						hidden:true,
						items : [{
							xtype: 'displayfield',
							fieldLabel : '特殊费率%*',
							id:'specFeeRate',
							name : 'specFeeRate',
							allowBlank : true,
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
							xtype: 'displayfield',
							fieldLabel : '分润封顶值%',
							id:'misc_desc',
							name : 'misc',
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
							xtype: 'displayfield',
							fieldLabel : 'VISA成本费率%',
							id:'extVisaRate',
							name : 'extVisaRate',
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
	   						xtype: 'displayfield',
	   						fieldLabel : '提现垫资分润比例%',
	   						id : 'extRate1',
	   						name : 'extRate1',
	   						width : 200,
	   					}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : 'MASTER成本费率%',
							id:'extMasterRate',
							name : 'extMasterRate',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
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
					items : [{
						width : 800,
						items : feeCtlGrid
					}]
				},{
					xtype : 'fieldset',
					title : '提现费率信息',
					layout : 'column',
					labelWidth : 120,
					width : 850,
					items : [{
						width : 800,
						items : brhCashRateGrid
					}]
				}]
			});

			var detailBrhWin = new Ext.Window({
				title : '合作伙伴详细信息',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 875,
				items : [detailBrhInfoForm],
				defaults : {
					bodyStyle : 'overflow-y:auto;'
				},
				closeAction : 'close',
				iconCls : 'logo',
				resizable : false,
				//autoScroll : true,
				buttonAlign : 'center',
				buttons : [{
					text : '通过',
					id: 'btnAccept',
					hidden: true,
					handler : function() {
						showConfirm('确认审核通过吗？合作伙伴编号：' + brhId, El, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.requestNeedAuthorise({
									url : 'T10101Action.asp?method=accept',
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											Ext.Msg.alert("提示",rspObj.msg);
											detailBrhWin.close(El);
											callback();
										} else {
											Ext.Msg.alert("提示",rspObj.msg);
										}
									},
									params : {
										brhId : brhId,
										txnId : '10101',
										subTxnId : '04'
									}
								});
							}
						});
					}
				},{
					text : '拒绝',
					id: 'btnRefuse',
					hidden: true,
					handler : function() {
						showConfirm('确认拒绝此申请吗？合作伙伴编号：' + brhId, El, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								showInputMsg('提示', '请输入退回原因', true, function(bt, text){
									if (bt == 'ok') {
										if (getLength(text) > 60 || getLength(text) == ''
											|| text.trim() == '') {
											alert('提交退回原因不能为空且最多可以输入30个汉字或60个字母、数字!');
											showInputMsg('提示', '请输入退回原因', true, back);
											return;
										}
										Ext.Ajax.requestNeedAuthorise({
											url : 'T10101Action.asp?method=refuse',
											success : function(rsp, opt) {
												var rspObj = Ext.decode(rsp.responseText);
												if (rspObj.success) {
													Ext.Msg.alert("提示",rspObj.msg);
													detailBrhWin.close(El);
													callback();
												} else {
													Ext.Msg.alert("提示",rspObj.msg);
												}
											},
											params : {
												brhId : brhId,
												refuseInfo: text,
												txnId : '10101',
												subTxnId : '05'
											}
										});
										hideProcessMsg();
									}
								});
							}
						});
					}
				},{
					text : '关闭',
					handler : function() {
						detailBrhWin.close(El);
					}
				}]
			});

	

	detailGridStore.load({
		params : {
			brhId : brhId
		},
		callback : function(records, options, success) {
			if (success) {
				detailBrhInfoForm.getForm().loadRecord(detailGridStore.getAt(0));
				detailBrhInfoForm.getForm().clearInvalid();
//				detailBrhWin.setTitle('机构详细信息');
				detailBrhWin.show();
//				if (detailGridStore.getAt(0).data.resv1_7Dt == '1') {
					detailBrhInfoForm.findById('settleFlagDtIds').show();
					Ext.getCmp('settleFlagDtId').clearInvalid();
					detailBrhInfoForm.findById('settleAcctNmDtId').show();
					Ext.getCmp('settleAcctNmDt').clearInvalid();
					detailBrhInfoForm.findById('settleAcctDtId').show();
					Ext.getCmp('settleAcctDt').clearInvalid();
					detailBrhInfoForm.findById('settleBankNmDtId').show();
					Ext.getCmp('settleBankNmDt').clearInvalid();
					detailBrhInfoForm.findById('settleBankNoDtId').show();
					Ext.getCmp('settleBankNoDt').clearInvalid();
//				}else{
//					detailBrhInfoForm.findById('settleFlagDtIds').hide();
//					Ext.getCmp('settleFlagDtId').clearInvalid();
//					detailBrhInfoForm.findById('settleAcctNmDtId').hide();
//					Ext.getCmp('settleAcctNmDt').clearInvalid();
//					detailBrhInfoForm.findById('settleAcctDtId').hide();
//					Ext.getCmp('settleAcctDt').clearInvalid();
//					detailBrhInfoForm.findById('settleBankNmDtId').hide();
//					Ext.getCmp('settleBankNmDt').clearInvalid();
//					detailBrhInfoForm.findById('settleBankNoDtId').hide();
//					Ext.getCmp('settleBankNoDt').clearInvalid();
//				}
				
//				if (detailGridStore.getAt(0).data.resv1_8Dt == '1') {
//					detailBrhInfoForm.findById('brhFeeCtlIdDt').show();
//					Ext.getCmp('brhFeeCtlDt').clearInvalid();
//				}else{
//					detailBrhInfoForm.findById('brhFeeCtlIdDt').hide();
//					Ext.getCmp('brhFeeCtlDt').clearInvalid();
//				}
				//设置结算类型
				var settleTypeDtl =detailGridStore.getAt(0).data.settleType;
				if(settleTypeDtl!=null&&settleTypeDtl.trim()!=''){
					var settleTypeDtlType=settleTypeDtl.substr(0,1);
					Ext.getCmp('settleTypeDtlId').fireEvent('select',this,this.store,settleTypeDtlType);//combo,record,number
					Ext.getCmp('settleTypeDtlId').setValue(settleTypeDtlType);//combo,record,number
					Ext.getCmp('periodIdDtl').setValue(settleTypeDtl.substr(1,1));
					Ext.getCmp('T_Ndtl').setValue(parseInt(settleTypeDtl.substr(2,3),10));
					
				}

				showCmpProcess(detailBrhInfoForm, '正在加载合作伙伴信息，请稍后......');
				hideCmpProcess(detailBrhInfoForm, 1000);

			} else {
				showErrorMsg("加载机构信息失败，请刷新数据后重试", El);
			}
		}
	});
	
	if (type == 'approve') {
		Ext.getCmp('btnAccept').show();
		Ext.getCmp('btnRefuse').show();
		Ext.getCmp('approveProcessInfo').show();
	}
}