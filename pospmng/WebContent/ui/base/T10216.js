Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	// 文件上传窗口
	var prizeForm = new Ext.form.FormPanel({
		title: '新划付明细生成',
		frame: true,
		border: true,
		width: 350,
		autoHeight: true,
		renderTo: 'prize',
		iconCls: 'mchnt',
		waitMsgTarget: true,
		labelwith:80,
		layout : 'column',
		defaults : {
			bodyStyle : 'padding-left: 20px'
		},
		items: [{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'datefield',
				id: 'settleDate',
				name: 'settleDate',
//				vtype: 'daterange',
	//			endDateField: 'endDate',
				value:timeYesterday,
				maxValue:preDate,
				minValue:'2015-02-10',
				fieldLabel: '划付日期',
				format: 'Y-m-d',
				altFormats: 'Y-m-d',
				width: 150,
				editable: false,
				allowBlank: false
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'basecomboselect',
				baseParams: 'PAY_FILE_ID_SETTLE',
				fieldLabel : '指定结算通道',
				emptyText : '请选择指定结算通道',
				allowBlank : false,
				id : 'aimChnlIds',
				hiddenName : 'aimChnlId',
				width : 150,
				blankText : '请选择一个银联机构号'
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '文件下载',
			iconCls: 'download',
			handler: function() {
				if(!prizeForm.getForm().isValid()) {
					return;
				}
				
				prizeForm.getForm().submit({
					url: 'T10216Action.asp',
					waitMsg: '正在下载报表，请稍等......',
					timeout : 50000 ,
					success: function(form,action) {
//						window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
						window.location.href = Ext.contextPath + '/ajaxDownLoadFile.asp?path='+
								action.result.downLoadFile+'&downloadName='+action.result.downLoadFileName;
					},
					failure: function(form,action) {
//						showAlertMsg("下载报表失败！",prizeForm);
						showAlertMsg(action.result.msg,prizeForm);
					}
					/*params: {
						txnId: '80403',
						subTxnId: '01'
					}*/
				});
			}
		},{
			text: '清空查询条件',
			handler: function() {
				prizeForm.getForm().reset();
			}
		}]
	});
})