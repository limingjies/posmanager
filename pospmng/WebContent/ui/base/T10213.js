Ext.onReady(function() {
	
	
	
	var queryForm = new Ext.form.FormPanel({
		title: '刷新共享内存',
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,
		defaults: {
			labelWidth: 30,
			width: 200
		},
		items: [
		
			],
		buttonAlign: 'center',
		buttons: [{
			text: '刷新内存',
			width: 100,
			iconCls: 'accept',
			handler: function() {
				showConfirm('确定要刷新共享内存吗？', queryForm, function(bt) {
				showMask("正在刷新，请稍后。。。",queryForm);
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										
										url : 'T10213Action.asp?method=start',
										timeout: 60000,
										params : {
											txnId : '10213',
											subTxnId : '01'
										},
										success : function(rsp, opt) {
											hideMask();
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,queryForm);
												
											} else {
												showErrorMsg(rspObj.msg, queryForm);
											}
										}
									});
						}
					})
				
			}
		}]
	});
});