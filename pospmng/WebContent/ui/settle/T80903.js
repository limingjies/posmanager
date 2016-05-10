Ext.onReady(function() {
	
	var queryForm = new Ext.form.FormPanel({
		title: '中信收款单位文件',
		frame: true,
		border: true,
		width: 340,
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
			fieldLabel: '起始日期*',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '结束日期*',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			editable: false,
			allowBlank: false
		},{
			xtype : 'combo',
			store : new Ext.data.ArrayStore({
						fields : ['valueField', 'displayField'],
						data: [['A','新增收款单位'],['U','修改收款单位']],
						reader : new Ext.data.ArrayReader()
					}),
			displayField : 'displayField',
			valueField : 'valueField',
			fieldLabel : '文件类型*',
			hiddenName : 'fileType',
			allowBlank : false,
			blankText : '文件类型不能为空',
			emptyText : '请选择文件类型',
			mode : 'local',
			triggerAction : 'all',
			editable : false,
			lazyRender : true,
			width : 200
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '文件下载',
			iconCls: 'download',
			handler: function() {
				if(!queryForm.getForm().isValid()) {
					return;
				}
				queryForm.getForm().submit({
					url: 'T80903Action.asp',
					waitMsg: '正在下载文件，请稍等......',
					params: {
						txnId: '80903',
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