Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var queryForm = new Ext.form.FormPanel({
		title: '商户信息表',
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
			id: 'startDate',
			name: 'startDate',
			vtype: 'daterange',
			endDateField: 'endDate',
//			value:timeYesterday,
			fieldLabel: '入网起始日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false
//			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
//			value:timeYesterday,
			fieldLabel: '入网结束日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false
//			allowBlank: false
		},{
			xtype: 'dynamicCombo',
			methodName: 'getBrhId',
			fieldLabel: '所属机构编号',
			hiddenName: 'brhId',
//			allowBlank: false,
			width: 250,
			editable: true
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
					url: 'T50301Action.asp',
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
});