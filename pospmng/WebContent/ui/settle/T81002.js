Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '准退货退款明细报表',
		frame: true,
		border: true,
		width: 285,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,
		items: [{
			xtype: 'datefield',
			id: 'settlmtDate',
			name: 'settlmtDate',
			fieldLabel: '退款日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 150,
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
					url: 'T81002Action.asp',
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