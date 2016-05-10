Ext.onReady(function() {
	
	
	
	var queryForm = new Ext.form.FormPanel({
		title: '风险统计报表',
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
			xtype: 'datefield',
			id: 'date1',
			name: 'date1',
			vtype: 'daterange',
			endDateField: 'date2',
			fieldLabel: '统计起始日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'date2',
			name: 'date2',
			vtype: 'daterange',
			startDateField: 'date1',
			fieldLabel: '统计结束日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '风险统计报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T40204Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					params: {
						txnId: '40204',
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