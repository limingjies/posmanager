//商户风控参数维护
Ext.onReady(function() {
	//修改表单
	var formMchtRiskParam = new Ext.form.FormPanel({
		//title:'商户风控参数修改',
		frame : true,
		height :500,
		autoScroll : true,
		width : 700,
		layout : 'form',
		waitMsgTarget : true,
		items : [{
			id : 'basicInfo',
			xtype : 'fieldset',
			title : '基本信息',
			layout : 'column',
			labelWidth : 90,
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '商户号',
					xtype : 'displayfield',
					id : 'mchtNo',
					width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '商户名称',
					xtype : 'displayfield',
					id : 'mchtNm',
					width : 200
				}]
			}]
		},{
			id : 'riskParamInfo',
			xtype : 'fieldset',
			title : '商户交易限额/限次<font color="red">（0表示不限额/限次）</font>',
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
					maxLength: 13,
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
					maxLength: 13,
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
					maxLength: 13,
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
					maxLength: 13,
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
					maxLength: 13,
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
					maxLength: 13,
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
					fieldLabel : '日累计借记卡交易限定次数<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitDayCount',
					name:'tblRiskParamMng.debitDayCount',
					maxLength: 5,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 99999,
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
					fieldLabel : '日累计贷记卡交易限定次数<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditDayCount',
					name:'tblRiskParamMng.creditDayCount',
					maxLength: 5,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 99999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth :.5,
				layout : 'form',
				items : [{
					fieldLabel : '月累计借记卡交易限定次数<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitMonCount',
					name:'tblRiskParamMng.debitMonCount',
					maxLength: 5,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 99999,
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
					fieldLabel : '月累计贷记卡交易限定次数<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditMonCount',
					name:'tblRiskParamMng.creditMonCount',
					maxLength: 5,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 99999,
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
					fieldLabel : '商户提现单笔限额<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'mchtAmt',
					name:'tblRiskParamMng.mchtAmt',
					maxLength: 13,
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
					fieldLabel : '商户提现日累计限额<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'mchtDayAmt',
					name:'tblRiskParamMng.mchtDayAmt',
					maxLength: 13,
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
					fieldLabel : '商户刷卡限额<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'mchtPosAmt',
					name:'tblRiskParamMng.mchtPosAmt',
					maxLength: 13,
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
	
	var winMchtRiskParam = new Ext.Window({
		title:'商户风控参数修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		shadow : false,
		modal : true,
		width : 700,
		height : 500,
		layout : 'fit',
		items : [formMchtRiskParam],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			id : 'btnSubmit',
			text : '提交',
			handler : function() {
				if(formMchtRiskParam.getForm().isValid()){
					formMchtRiskParam.getForm().submit({
						url : 'T40405Action.asp?method=edit',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formMchtRiskParam);
							// 重置表单
							formMchtRiskParam.getForm().reset();
							// 重新加载参数列表
							storeMchtRiskParam.load();
							winMchtRiskParam.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formMchtRiskParam);
							winMchtRiskParam.hide();
						},
						params : {
							'txnId' : '40405',
							'subTxnId' : '02',
							'tblRiskParamMng.id.mchtId' : Ext.getCmp('mchtNo').getValue(),
							'tblRiskParamMng.id.termId' : '00000000',
							'tblRiskParamMng.id.riskType' : '0'
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				winMchtRiskParam.hide();
			}
		}]
	});
	
	
	/***************************************************************************************************************/
	var storeMchtRiskParam = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchtRiskParamList'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mcht_id'},
			{name: 'mchtNm',mapping: 'mcht_nm'},
			{name: 'brhNo',mapping: 'create_new_no'},
			{name: 'brhNm',mapping: 'brh_name'},
			{name: 'creditSingleAmt',mapping: 'credit_single_amt'},
			{name: 'creditDayAmt',mapping: 'credit_day_amt'},
			{name: 'creditDayCount',mapping: 'credit_day_count'},
			{name: 'creditMonAmt',mapping: 'credit_mon_amt'},
			{name: 'creditMonCount',mapping: 'credit_mon_count'},
			{name: 'debitSingleAmt',mapping: 'debit_single_amt'},
			{name: 'debitDayAmt',mapping: 'debit_day_amt'},
			{name: 'debitDayCount',mapping: 'debit_day_count'},
			{name: 'debitMonAmt',mapping: 'debit_mon_amt'},
			{name: 'debitMonCount',mapping: 'debit_mon_count'},
			{name: 'remark',mapping: 'remark'},
			{name: 'updTime',mapping: 'upd_time'},
			{name: 'mchtStatus',mapping: 'mcht_status'},
			{name: 'mchtAmt',mapping: 'mcht_amt'},
			{name: 'mchtDayAmt',mapping: 'mcht_day_amt'},
			{name: 'mchtPosAmt',mapping: 'mcht_pos_amt'}
		]),
		autoLoad: true
	});
	var txnRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'备注：<br/>',
			'<b>{remark}</b>'
		)
	});
	
	var colMolMchtRiskParam = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		txnRowExpander,
		{header: '商户号',dataIndex: 'mchtNo',width:120},
		{header: '商户名称',dataIndex: 'mchtNm',align: 'left',id:'mchtNm',width: 200},
		{header: '合作伙伴号',dataIndex: 'brhNo',width: 120},
		{header: '合作伙伴名称',dataIndex: 'brhNm',align: 'left',width: 200},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 100,renderer:mchntSt},
		{header: '单笔借记卡限额',dataIndex: 'debitSingleAmt',align: 'left',width: 140},
		{header: '单笔贷记卡限额',dataIndex: 'creditSingleAmt',align: 'left',width: 140},
		{header: '日累计借记卡限额',dataIndex: 'debitDayAmt',align: 'left',width: 140},
		{header: '日累计贷记卡限额',dataIndex: 'creditDayAmt',align: 'left',width: 140},
		{header: '日累计借记卡限定次数',dataIndex: 'debitDayCount',width: 140},
		{header: '日累计贷记卡限定次数',dataIndex: 'creditDayCount',width: 140},
		{header: '月累计借记卡限额',dataIndex: 'debitMonAmt',width: 140},
		{header: '月累计贷记卡限额',dataIndex: 'creditMonAmt',width: 140},
		{header: '月累计借记卡限定次数',dataIndex: 'debitMonCount',width: 140},
		{header: '月累计贷记卡限定次数',dataIndex: 'creditMonCount',width: 140},
		{header: '商户提现单笔限额',dataIndex: 'mchtAmt',width: 140},
		{header: '商户提现日累计限额',dataIndex: 'mchtDayAmt',width: 140},
		{header: '商户刷卡限额',dataIndex: 'mchtPosAmt',width: 140},
		{header: '更新时间',dataIndex: 'updTime',width: 140,renderer:formatDt}
	]);
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
            '-','商户号：',{
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
	
	var gridMchtRiskParam = new Ext.grid.GridPanel({
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: storeMchtRiskParam,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colMolMchtRiskParam,
		clicksToEdit: true,
		forceValidation: true,
		plugins: txnRowExpander,
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'btnQuery',
			id: 'btnQuery',
			iconCls: 'query',
			width: 80,
			handler:function() {
				storeMchtRiskParam.load();
			}
		},'-',{
			xtype: 'button',										
			text: '重置',
			name: 'btnReset',
			id: 'btnReset',
			iconCls: 'reset',
			width: 80,
			handler:function() {							
				Ext.getCmp('queryMchtNoId').setValue('');	
				Ext.getCmp('queryBrhNoId').setValue('');
				storeMchtRiskParam.reload();
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			id:'bthEdit',
			iconCls: 'edit',
			width: 85,
			disabled:true,
			handler:function() {
				var selRec = gridMchtRiskParam.getSelectionModel().getSelected();
				if(selRec.data.mchtStatus == '8'){ //注销
					alert('已注销的商户不可修改风控参数！');
					return;
				}
				winMchtRiskParam.show();
				formMchtRiskParam.getForm().loadRecord(selRec);
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
			store: storeMchtRiskParam,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridMchtRiskParam.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridMchtRiskParam.getView().getRow(gridMchtRiskParam.getSelectionModel().last)).frame();
			//激活菜单
			var selRec = gridMchtRiskParam.getSelectionModel().getSelected();
			if(selRec.data.mchtStatus != '8'){ //注销
				Ext.getCmp('bthEdit').enable();
			}
		}
	});
	
	storeMchtRiskParam.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: Ext.getCmp('queryMchtNoId').getValue(),
			brhNo:Ext.getCmp('queryBrhNoId').getValue()
		});
		Ext.getCmp('bthEdit').disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridMchtRiskParam],
		renderTo: Ext.getBody()
	});
});

