Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天

	
	var queryForm = new Ext.form.FormPanel({
		title: '操作员操作统计',
		frame: true,
		border: true,
		width: 370,
		autoHeight: true,
		renderTo: 'reports',
		iconCls: 'T40204',
		waitMsgTarget: true,
		defaults: {
			labelStyle: 'padding-left: 20px;',
			labelWidth: 20,
			width: 220
		},
		items: [{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
//			vtype: 'daterange',
//			endDateField: 'endDate',
			value:timeYesterday,
			fieldLabel: '操作起始日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
//			vtype: 'daterange',
//			startDateField: 'startDate',
			value:timeYesterday,
			fieldLabel: '操作结束日期',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			editable: false
		},{
			xtype: 'textfield',
			id: 'ipAddr',
			name: 'ipAddr',
			fieldLabel: '操作IP地址'
		},{
			xtype: 'basecomboselect',
			baseParams: 'BRH_BELOW',
			fieldLabel: '操作机构',
			hiddenName: 'brhId'
		},{
			xtype: 'textfield',
			id: 'oprNo',
			name: 'oprNo',
			vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},{
			xtype: 'basecomboselect',
			baseParams: 'CON_TXN',
			fieldLabel: '操作名称',
			hiddenName: 'conTxn'
		},{
        	xtype: 'panel',
        	layout: 'form',
			fieldLabel: '分组方式*',
   			items: [new Ext.form.RadioGroup({
				allowBlank : false,
				id: 'secGroupTyp',
				blankText:'至少选择一项',
				value: '0',
				items: [{
					boxLabel : '按操作员',
					inputValue : '0',
					name : 'secGroupTyp'
				}, {
					boxLabel : '按操作名称',
					inputValue : '1',
					name : 'secGroupTyp'
				}]
			})]
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
					url: 'T10404Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					params: {
						txnId: '10404',
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
			text: '清空筛选条件',
			handler: function() {
				queryForm.getForm().reset();
				Ext.getCmp('secGroupTyp').setValue('0');
			}
		}]
	});
});