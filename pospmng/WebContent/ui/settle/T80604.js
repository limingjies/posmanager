Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	T80604.getSettleDate(function(ret){
		Ext.getCmp('misc').setValue(ret);
	})
	
	var queryForm = new Ext.form.FormPanel({
		title: '准退货批量任务',
		frame: true,
		border: true,
		width: 320,
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
			id: 'settleDate',
			name: 'settleDate',
			maxValue:preDate,
//			vtype: 'daterange',
//			endDateField: 'endDate',
			value:timeYesterday,
			fieldLabel: '清算日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 180,
			editable: false,
			allowBlank: false
		},{
			xtype : 'displayfield',
			fieldLabel : '最新跑批清算日期',
			name : 'misc',
			id : 'misc',
			width : 200
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '启动批量',
			width:100,
			iconCls: 'upload',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				showConfirm('确定要执行准退货批量吗？', queryForm, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
				
						queryForm.getForm().submit({
							url: 'T80604Action.asp?method=start',
							waitMsg: '正在执行批量，请稍等......',
							timeout : 50000 ,
							params: {
								txnId: '80604',
								subTxnId: '01'
							},
							success: function(form,action) {
		//						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
								showSuccessMsg(action.result.msg,queryForm);
								T80604.getSettleDate(function(ret){
									Ext.getCmp('misc').setValue(ret);
								})
							},
							failure: function(form,action) {
								showAlertMsg(action.result.msg,queryForm);
		//						Ext.TaskMgr.stop(task);
							}
						});
				
					}
				})
			}
		}]
	});
});