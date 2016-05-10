Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
//		title: '统计报表下载',
		title: '中信一级MCC交易金额报表下载',
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			width: 200
		},
		items: [
		
			{
/*			xtype : 'combo',
			store : new Ext.data.ArrayStore({
						fields : ['valueField', 'displayField'],
						data: [['0','1-商户交易汇总报表'],['1','2-中信一级MCC交易金额报表']],
						reader : new Ext.data.ArrayReader()
					}),
			displayField : 'displayField',
			valueField : 'valueField',
			fieldLabel : '报表类型',
			hiddenName : 'repType',
			allowBlank : false,
			blankText : '报表类型不能为空',
			emptyText : '请选择报表类型',
			mode : 'local',
			triggerAction : 'all',
			editable : false,
			lazyRender : true,
			width : 200
		},{*/
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '起始日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '结束日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '报表下载',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80401Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					params: {
						txnId: '40304',
						subTxnId: '01'
					},
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg(action.result.msg,queryForm);
					}
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
});