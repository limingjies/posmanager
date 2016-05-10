Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '密钥同步',
		frame: true,
		border: true,
		width: 360,
		autoHeight: true,
		renderTo: 'report',
		waitMsgTarget: true,
		items: [],
		buttonAlign: 'center',
		buttons: [{
			text: '密钥同步',
			handler: function() {
				T10210.download(function(ret) {
					if(ret == 0) {
						showSuccessMsg("同步成功！", queryForm);
					} else {
						showErrorMsg("同步失败！", queryForm);
					}
				});
			}
		}]
	});
});