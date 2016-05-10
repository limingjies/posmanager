Ext.onReady(function() {
	
	// 文件上传窗口
	var dialog ;
	
	function showUploadWin(fileType,amtUp,amtRes) {
//		alert(fileType+"-"+amtUp+"-"+amtRes);
	 // 文件上传窗口
	 	dialog = new UploadDialog({
			uploadUrl : 'T10214Action.asp?method=upload',
			filePostName : 'files',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '10 MB',
			fileTypes : '*.txt;*.TXT',
			fileTypesDescription : '文本文件(*.txt;*.TXT)',
			title: '文件上传',
			scope : this,
			animateTarget: 'download',
			onEsc: function() {
				this.hide();
//				dialog.close();
			},
			completeMethod: function() {
				this.close();
			},
			exterMsgMethod: function(msg) {
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+msg;
			},
			exterMethod: function() {
			},
			postParams: {
				fileType:fileType,
				amtUp:	amtUp,
				amtRes:	amtRes,
				txnId: '10214',
				subTxnId: '01'
			}
		});
		dialog.show();
	}
	
	
	var prizeForm = new Ext.form.FormPanel({
		title: '划付文件大额拆分',
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
		/*defaults: {
			width: 280,
			labelStyle: 'padding-left: 5px'
		},*/
		items: [{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'combo',
				fieldLabel: '文件格式类型',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['01','工商银行'],['02','中信银行(旧)'],['03','中信银行(新)']],
					reader: new Ext.data.ArrayReader()
				}),
				editable: false,
				hiddenName: 'fileType',
				allowBlank: false,
				value: '01',
				blankText: '请选择文件格式类型',
				width: 150
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'numberfield',
				fieldLabel: '大额上限值',
				regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
				regexText: '大额上限值是正数，如123.45',
				maxLength: 10,
				allowBlank: false,
				vtype: 'isOverMax',
				width:150,
				value:50000,
				id: 'amtUp',
				name: 'amtUp'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'numberfield',
				fieldLabel: '拆分金额值',
				regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
				regexText: '拆分金额值是正数，如123.45',
				maxLength: 10,
				allowBlank: false,
				vtype: 'isOverMax',
				width:150,
				value:49999,
				id: 'amtRes',
				name: 'amtRes'
			}]
		}],
		buttonAlign: 'center',
		buttons: [{
			text: '文件导入',
			iconCls: 'download',
			handler: function() {
				if(!prizeForm.getForm().isValid()) {
					return;
				}
				
				
				var fileType=Ext.get('fileType').getValue();
				var amtUp=	Ext.getCmp('amtUp').getValue();
				var amtRes=	Ext.getCmp('amtRes').getValue();
//				dialog.show();
				if(dialog != null){
					dialog.close();
				}
				showUploadWin(fileType,amtUp,amtRes)
			}
		},{
			text: '清空查询条件',
			handler: function() {
				prizeForm.getForm().reset();
			}
		}]
	});
})