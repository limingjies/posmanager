Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '准退货退报表下载',
		frame: true,
		border: true,
		width: 285,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '准退货起始日期*',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 120,
			editable: false,
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '准退货结束日期*',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 120,
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
					url: 'T100401Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					timeout : 50000 ,
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