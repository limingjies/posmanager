Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var queryForm = new Ext.form.FormPanel({
		title: '交易审计统计表',
		frame: true,
		border: true,
		width: 480,
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
		},{
			xtype : 'combo',
			store : new Ext.data.ArrayStore({
						fields : ['valueField', 'displayField'],
						data: [['1','按商户统计'],['2','按终端统计']],
						reader : new Ext.data.ArrayReader()
					}),
			displayField : 'displayField',
			valueField : 'valueField',
			fieldLabel : '报表统计类型',
			hiddenName : 'repType',
			allowBlank : false,
			blankText : '报表统计类型不能为空',
			emptyText : '请选统计择报表类型',
			mode : 'local',
			triggerAction : 'all',
			editable : false,
			lazyRender : true,
			width : 200
		},{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			value:timeYesterday,
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
//			vtype: 'daterange',
//			startDateField: 'startDate',
			value:timeYesterday,
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
					url: 'T80402Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					timeout : 50000 ,
					success: function(form,action) {
						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
					},
					failure: function(form,action) {
						showAlertMsg("下载报表失败！",queryForm);
					},
					params: {
						txnId: '80402',
						subTxnId: '01'
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