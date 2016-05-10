Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户简称报表 ',
		frame: true,
		border: true,
		width: 480,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,  
		defaults: {
			labelWidth: 30
//			width: 200
		},
		items: [{
			xtype: 'dynamicCombo',
			methodName: 'getBrhId',
			fieldLabel: '合作伙伴',
			hiddenName: 'brhId',
//			allowBlank: false,
			width: 350,
			editable: true
		},{
			xtype: 'dynamicCombo',
			methodName: 'getMchntId',
			fieldLabel: '商户编号',
			hiddenName: 'mchntId',
//			allowBlank: false,
			editable: true,
			width: 350
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '下载报表',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80213Action.asp',
					timeout: 60000,
					waitMsg: '正在下载报表，请稍等......',
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
//	queryForm.getForm().findField('brhId').setValue(BRHID);
})