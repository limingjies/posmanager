//终端风控参数维护
Ext.onReady(function() {
	//修改表单
	var formTermRiskParam = new Ext.form.FormPanel({
		frame : true,
		height :500,
		autoScroll : true,
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
		            width : 200
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
		            width : 200
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
		            width : 200
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
		            width : 200
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
		            width : 200
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
							items : [{
								xtype : 'checkbox',
								fieldLabel : '退货 ',
								id : 'param8Upd',
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
					id: 'remark',
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
		height : 500,
		layout : 'fit',
		items : [formTermRiskParam],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			id : 'btnSubmit',
			text : '提交',
			handler : function() {
				var fr = formTermRiskParam.getForm();
				if(!fr.findField("param1Upd1").getValue() && !fr.findField("param1Upd2").getValue() && !fr.findField("param1Upd3").getValue() && !fr.findField("param1Upd4").getValue()){
					alert('卡类型权限:不能为空！');
					return;
				}
				if(!fr.findField("param1Upd").getValue() && !fr.findField("param6Upd").getValue() && !fr.findField("param2Upd").getValue() && !fr.findField("param8Upd").getValue()){
					alert('交易权限:不能为空！');
					return;
				}
				
				if(formTermRiskParam.getForm().isValid()){
					formTermRiskParam.getForm().submit({
						url : 'T40407Action.asp?method=edit',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formTermRiskParam);
							// 重置表单
							formTermRiskParam.getForm().reset();
							// 重新加载参数列表
							storeTermRiskParam.load();
							winTermRiskParam.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formTermRiskParam);
							formTermRiskParam.getForm().reset();
							winTermRiskParam.hide();
						},
						params : {
							'txnId' : '40407',
							'subTxnId' : '02',
							'termInfo' : getSelTermInfo(),
							'tradeInfo' : getSelTradeInfo()
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				winTermRiskParam.hide();
			}
		}]
	});
	//初始化交易信息
    function initTermTradeInfo(val,suffix)
    {
    	if(suffix == "Dtl"){
    		var fr = formTermRiskParamDtl.getForm();
    	}else{
    		var fr = formTermRiskParam.getForm();
    	}
        fr.findField("param1Upd1").setValue(val.substring(0,1));
        fr.findField("param1Upd2").setValue(val.substring(1,2));
        fr.findField("param1Upd3").setValue(val.substring(2,3));
        fr.findField("param1Upd4").setValue(val.substring(3,4));
        
        fr.findField("param1Upd").setValue(val.substring(10,11));
        fr.findField("param6Upd").setValue(val.substring(11,12));
        fr.findField("param2Upd").setValue(val.substring(13,14));
        fr.findField("param8Upd").setValue(val.substring(17,18));
        
    }
    
	//拼接选择的终端信息
	function getSelTermInfo(){
		var recs = gridTermRiskParam.getSelectionModel().getSelections();
		var fr = formTermRiskParam.getForm();
		var retStr = "";
		var termInfo = "";
		for(var i=0;i<recs.length;i++){
			if(recs[i].data.termStatus == '7'){
				continue;
			}
			termInfo = recs[i].data.mchtNo + "#" + recs[i].data.termNo + '#' + recs[i].data.crtTime;
			if(retStr){
				retStr += ',';
			}
			retStr += termInfo;
		}
		return retStr;
	}
	//拼接选择的交易信息
	function getSelTradeInfo(){
		var fr = formTermRiskParam.getForm();
		var array = new Array(19);
		for(var i=0;i<array.length;i++){
			array[i] = ' ';
		}
		array.splice(0,1, (fr.findField("param1Upd1").getValue()==true?1:0));
		array.splice(1,1, (fr.findField("param1Upd2").getValue()==true?1:0));
		array.splice(2,1, (fr.findField("param1Upd3").getValue()==true?1:0));
		array.splice(3,1, (fr.findField("param1Upd4").getValue()==true?1:0));
		
		array.splice(10,1, (fr.findField("param1Upd").getValue()==true?1:0));
		array.splice(11,1, (fr.findField("param6Upd").getValue()==true?1:0));
		array.splice(12,1, (fr.findField("param6Upd").getValue()==true?1:0));
		array.splice(13,1, (fr.findField("param2Upd").getValue()==true?1:0));
		array.splice(14,1, (fr.findField("param2Upd").getValue()==true?1:0));
		array.splice(15,1, (fr.findField("param2Upd").getValue()==true?1:0));
		array.splice(16,1, (fr.findField("param2Upd").getValue()==true?1:0));
		array.splice(17,1, (fr.findField("param8Upd").getValue()==true?1:0));
		array.splice(18,1, (fr.findField("param8Upd").getValue()==true?1:0));
		return array.join('');
	}
	
	/***************************************************************************************************************/
	//详情表单
	var formTermRiskParamDtl = new Ext.form.FormPanel({
		frame : true,
		height :500,
		autoScroll : true,
		width : 700,
		layout : 'form',
		waitMsgTarget : true,
		items : [{
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
			}]
		},{
			id : 'tradInfoDtl',
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
								id : 'param1UpdDtl',
								name : 'param1Upd',
								disabled:true,
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '消费 ',
								id : 'param6UpdDtl',
								name : 'param6Upd',
								disabled:true,
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '预授权 ',
								id : 'param2UpdDtl',
								name : 'param2Upd',
								disabled:true,
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '退货 ',
								id : 'param8UpdDtl',
								name : 'param8Upd',
								disabled:true,
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
							id : 'param1Upd1Dtl',
							name : 'param1Upd1',
							disabled:true,
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '贷记卡 ',
							id : 'param1Upd2Dtl',
							name : 'param1Upd2',
							disabled:true,
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '准贷记卡 ',
							id : 'param1Upd3Dtl',
							name : 'param1Upd3',
							disabled:true,
							inputValue : '1'
						}]
					}, {
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '预付费卡 ',
							id : 'param1Upd4Dtl',
							name : 'param1Upd4',
							disabled:true,
							inputValue : '1'
						}]
					}]
				}
			]
		},{
			id : 'otherInfoDtl',
			xtype : 'fieldset',
			title : '其他信息',
			layout : 'column',
			labelWidth : 50,
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel: '备注',
					width: 480,
					vtype: 'length100B',
					labelStyle: 'padding-left: 10px',
					id: 'remarkDtl',
					name: 'remark'
				}]
			}]
		}]
	});
	
	var winTermRiskParamDtl = new Ext.Window({
		title:'终端风控参数详情',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		shadow : false,
		modal : true,
		width : 700,
		height : 500,
		layout : 'fit',
		items : [formTermRiskParamDtl],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '关闭',
			handler : function() {
				winTermRiskParamDtl.hide();
			}
		}]
	});
	
	/*********************************************************************************************************/
	
	var storeTermRiskParam = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getTermRiskParamList'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   	{name: 'termNo',mapping: 'term_id'},
			{name: 'mchtNo',mapping: 'mcht_id'},
			{name: 'mchtNm',mapping: 'mcht_nm'},
			{name: 'brhNo',mapping: 'create_new_no'},
			{name: 'brhNm',mapping: 'brh_name'},
			{name: 'creditSingleAmt',mapping: 'credit_single_amt'},
			{name: 'creditDayAmt',mapping: 'credit_day_amt'},
			//{name: 'creditDayCount',mapping: 'credit_day_count'},
			{name: 'creditMonAmt',mapping: 'credit_mon_amt'},
			//{name: 'creditMonCount',mapping: 'credit_mon_count'},
			{name: 'debitSingleAmt',mapping: 'debit_single_amt'},
			{name: 'debitDayAmt',mapping: 'debit_day_amt'},
			//{name: 'debitDayCount',mapping: 'debit_day_count'},
			{name: 'debitMonAmt',mapping: 'debit_mon_amt'},
			//{name: 'debitMonCount',mapping: 'debit_mon_count'},
			{name: 'remark',mapping: 'remark'},
			{name: 'updTime',mapping: 'upd_time'},
			{name: 'termTradInfo',mapping: 'termTradInfo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'crtTime',mapping: 'rec_crt_ts'}
		]),
		autoLoad: true
	});
	
	var colMolTermRiskParam = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '终端号',dataIndex: 'termNo',width:100},
		{header: '商户号',dataIndex: 'mchtNo',width:120},
		{header: '商户名称',dataIndex: 'mchtNm',align: 'left',id:'mchtNm',width: 200},
		{header: '合作伙伴号',dataIndex: 'brhNo',width: 120},
		{header: '合作伙伴名称',dataIndex: 'brhNm',align: 'left',width: 200},
		{header: '终端状态',dataIndex: 'termStatus',width: 100,renderer:termSta},
		{header: '单笔借记卡限额',dataIndex: 'debitSingleAmt',width: 140},
		{header: '单笔贷记卡限额',dataIndex: 'creditSingleAmt',width: 140},
		{header: '日累计借记卡限额',dataIndex: 'debitDayAmt',width: 140},
		{header: '日累计贷记卡限额',dataIndex: 'creditDayAmt',width: 140},
		{header: '月累计借记卡限额',dataIndex: 'debitMonAmt',width: 140},
		{header: '月累计贷记卡限额',dataIndex: 'creditMonAmt',width: 140},
		{header: '更新时间',dataIndex: 'updTime',width: 140,renderer:formatDt}
	]);
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
			'-','终端号：',{
				xtype:'textfield',
				name:'queryTermNo',
				id:'queryTermNoId',
				width:150
			},'-','商户号：',{
				hiddenName: 'queryMchtNo',
				id: 'queryMchtNoId',
				xtype : 'dynamicCombo',
				methodName : 'getMchntNoAll',
				lazyRender: true,
				forceSelection : true,
				width: 250,
	        },'-','合作伙伴号：',{
				xtype : 'dynamicCombo',
				methodName : 'getBrhId',
				id:'queryBrhNoId',
				hiddenName: 'queryBrhNo',
				forceSelection : true,
				width: 250
			}
		]  
	});
	
	var gridTermRiskParam = new Ext.grid.GridPanel({
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: storeTermRiskParam,
		sm: new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		cm: colMolTermRiskParam,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'btnQuery',
			id: 'btnQuery',
			iconCls: 'query',
			width: 80,
			handler:function() {
				storeTermRiskParam.load();
			}
		},'-',{
			xtype: 'button',										
			text: '重置',
			name: 'btnReset',
			id: 'btnReset',
			iconCls: 'reset',
			width: 80,
			handler:function() {	
				Ext.getCmp('queryTermNoId').setValue('');
				Ext.getCmp('queryMchtNoId').setValue('');	
				Ext.getCmp('queryBrhNoId').setValue('');
				storeTermRiskParam.reload();
			}
		},'-',{
			xtype: 'button',
			text: '查看详情',
			id:'btnDetail',
			iconCls: 'detail',
			disabled : true,
			width: 85,
			handler:function() {
				var recs = gridTermRiskParam.getSelectionModel().getSelections();
				if(recs && recs.length > 1){
					alert("只可以选择一条记录进行查看！");
					return;
				}
				winTermRiskParamDtl.show();
				formTermRiskParamDtl.getForm().loadRecord(recs[0]);
				initTermTradeInfo(recs[0].data.termTradInfo,"Dtl");
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			id:'btnEdit',
			iconCls: 'edit',
			disabled : true,
			width: 85,
			handler:function() {
				var recs = gridTermRiskParam.getSelectionModel().getSelections();
				if(recs && recs.length > 1){
					alert("只可以选择一条记录进行修改！");
					return;
				}
				if(recs[0].data.termStatus == '7'){ //注销
					alert('已注销的终端不可修改风控参数！');
					return;
				}
				winTermRiskParam.show();
				formTermRiskParam.getForm().reset();
				formTermRiskParam.getForm().loadRecord(recs[0]);
				initTermTradeInfo(recs[0].data.termTradInfo,"");
			}
		},'-',{
			xtype: 'button',
			text: '批量修改',
			id:'btnBatchEdit',
			iconCls: 'edit',
			disabled : true,
			width: 85,
			handler:function() {
				var recs = gridTermRiskParam.getSelectionModel().getSelections();
				for(var i=0;i<recs.length;i++){
					if(recs[i].data.termStatus == '7'){ //注销
						if(!confirm('您选择了已注销终端，已注销的终端不可修改风控参数，请核实！')){
							return;
						}
					}
				}
				winTermRiskParam.show();
				formTermRiskParam.getForm().reset();
				//设置默认值
				var fr = formTermRiskParam.getForm();
		        fr.findField("param1Upd1").setValue('1');
		        fr.findField("param1Upd2").setValue('1');
		        fr.findField("param1Upd").setValue('1');
		        fr.findField("param6Upd").setValue('1');
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar);
                } ,
            'cellclick':selectableCell
        },
		loadMask: {
			msg: '正在加载商户风控参数信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: storeTermRiskParam,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridTermRiskParam.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridTermRiskParam.getView().getRow(gridTermRiskParam.getSelectionModel().last)).frame();
			//激活菜单
			var recs = gridTermRiskParam.getSelectionModel().getSelections();
			for(var i=0;i<recs.length;i++){
				if(recs[i].data.termStatus != '7'){
					Ext.getCmp('btnEdit').enable();
					Ext.getCmp('btnBatchEdit').enable();
					break;
				}
			}
			Ext.getCmp('btnDetail').enable();
		},
		'rowdeselect':function(){
			var recs = gridTermRiskParam.getSelectionModel().getSelections();
			if(recs.length <= 0){
				Ext.getCmp('btnEdit').disable();
				Ext.getCmp('btnDetail').disable();
				Ext.getCmp('btnBatchEdit').disable();
			}else{
				Ext.getCmp('btnEdit').disable();
				Ext.getCmp('btnBatchEdit').disable();
				var recs = gridTermRiskParam.getSelectionModel().getSelections();
				for(var i=0;i<recs.length;i++){
					if(recs[i].data.termStatus != '7'){
						Ext.getCmp('btnEdit').enable();
						Ext.getCmp('btnBatchEdit').enable();
						break;
					}
				}
			}
		}
	});
	
	storeTermRiskParam.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			termNo: Ext.getCmp('queryTermNoId').getValue(),
			mchtNo: Ext.getCmp('queryMchtNoId').getValue(),
			brhNo: Ext.getCmp('queryBrhNoId').getValue()
		});
		Ext.getCmp('btnEdit').disable();
		Ext.getCmp('btnDetail').disable();
		Ext.getCmp('btnBatchEdit').disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridTermRiskParam],
		renderTo: Ext.getBody()
	});
});

