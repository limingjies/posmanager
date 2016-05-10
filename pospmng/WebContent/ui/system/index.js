function keydown(event) {
	if(event.keyCode == 13)
		login();
}

 /*
    判断字符类型
*/
function CharMode(iN) {
    if (iN >= 48 && iN <= 57) //数字  
        return 1;
    if (iN >= 65 && iN <= 90) //大写字母  
        return 2;
    if (iN >= 97 && iN <= 122) //小写  
        return 4;
    else
        return 8; //特殊字符  
}
/*
    统计字符类型
*/
function bitTotal(num) {
    modes = 0;
    for (i = 0; i < 4; i++) {
        if (num & 1) modes++;
        num >>>= 1;
    }
    return modes;
}
/*
    返回密码的强度级别
*/
function checkStrong(sPW) {
    Modes = 0;
    for (i = 0; i < sPW.length; i++) {
        //测试每一个字符的类别并统计一共有多少种模式.  
        Modes |= CharMode(sPW.charCodeAt(i));
    }
    return bitTotal(Modes);
}
	
function newcode(){
	var verify = document.getElementById('safecode');

//    verify.setAttribute('src', '/tlpospmg/ValidateCode?' + Math.random());
//    verify.setAttribute('src', Ext.contextPath+'/ValidateCode?' + Math.random());
	verify.setAttribute('src', 'ValidateCode?'+ Math.random());
}

function login() {
	if(loginForm.getForm().isValid()) {
		showMask("登录中...", loginForm);
		loginForm.getForm().submit({
			url: 'login.asp',
			success: function(form, action) {
				hideMask();
				window.location.href = 'redirect.asp';
			},
			failure: function(form, action) {
				hideMask();
				if(action.result.code) {
					showMsg(action.result.msg, loginForm, function() {
						// 重置密码
						resetPwdWin.show();
						resetPwdForm.getForm().reset();
						if(action.result.code == '01') {
							Ext.getCmp('resvInfo').getEl().up('.x-form-item').setDisplayed(false);
//							resetPwdForm.getForm().findField('resvInfo').allowBlank = true;
						} else{
							Ext.getCmp('resvInfo').getEl().up('.x-form-item').setDisplayed(true);
//							resetPwdForm.getForm().findField('resvInfo').allowBlank = false;
						}
						resetPwdForm.getForm().findField('resetOprId').setValue(loginForm.findById('oprid').getValue());
					});
				} else {
					showErrorMsg(action.result.msg, loginForm);
				}
					newcode();
			},
			params:{
				txnCode:'Login'
			}
		});
	}
}


	
	
	resetPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'resetOprId',
//			hidden:true,
			name: 'resetOprId',
			readOnly: true
		},{
			xtype: 'textfield',
			fieldLabel: '原密码',
			inputType: 'password',
//			regex: /^[0-9a-zA-Z]{6}$/,
//			regexText: '密码必须是6位数字或字母',
			regex: /^[0-9a-zA-Z]{6,15}$/,
			regexText: '请输入长度为6-15位，数字和字母的组合',
			id: 'resetPassword',
			name: 'resetPassword',
			allowBlank: false,
			blankText: '原密码不能为空'
		},{
			xtype: 'textfield',
			fieldLabel: '新密码',
			inputType: 'password',
//			regex: /^[0-9a-zA-Z]{6}$/,
//			regexText: '密码必须是6位数字和字母的组合',
			regex: /^[0-9a-zA-Z]{6,15}$/,
			regexText: '请输入长度为6-15位，数字和字母的组合',
			id: 'resetPassword1',
			name: 'resetPassword1',
			allowBlank: false,
			blankText: '新密码不能为空',
			listeners :{
                'change':function(){
                	if(checkStrong(resetPwdForm.findById('resetPassword1').getValue()) < 2){
             			showErrorMsg("密码必须是6-15位数字和字母的组合", resetPwdForm);
             			Ext.getCmp("resetPassword1").setValue(""); 
             		}
				}
            }
		},{
			xtype: 'textfield',
			fieldLabel: '确认密码',
			inputType: 'password',
//			regex: /^[0-9a-zA-Z]{6}$/,
//			regexText: '密码必须是6位数字和字母的组合',
			regex: /^[0-9a-zA-Z]{6,15}$/,
			regexText: '请输入长度为6-15位，数字和字母的组合',
			id: 'resetPassword2',
			name: 'resetPassword2',
			allowBlank: false,
			blankText: '确认密码不能为空',
			listeners :{
                'change':function(){
                	if(checkStrong(resetPwdForm.findById('resetPassword2').getValue()) < 2){
             			showErrorMsg("密码必须是6-15位数字和字母的组合", resetPwdForm);
             			Ext.getCmp("resetPassword2").setValue(""); 
             		}
				}
            }
		},{
			xtype: 'textarea',
			fieldLabel: '预留信息',
			regex: /^.{1,20}$/,
			regexText: '预留信息不超过20个字',
			width: 'auto',
			id: 'resvInfo',
			name: 'resvInfo'/*,
			allowBlank: false,
			blankText: '预留信息不能为空'*/
		}]
	});

	/**
	 * 修改密码窗口
	 */
	 resetPwdWin = new Ext.Window({
//		title: '密码修改',
		title:'<div align="left">密码修改</div>',
		frame: true,
		width: 300,
		layout: 'fit',
		items: [resetPwdForm],
		resizable: false,
		iconCls: 'key',
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		buttons: [{
			text: '提交',
			handler: function() {
				if(!resetPwdForm.getForm().isValid()) {
					return;
				}
				if(resetPwdForm.get('resetPassword').getValue() == resetPwdForm.get('resetPassword1').getValue()) {
					showAlertMsg('新密码不能和原始密码一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				if(resetPwdForm.get('resetPassword1').getValue() != resetPwdForm.get('resetPassword2').getValue()) {
					showAlertMsg('两次输入的新密码不一致，请重新输入',resetPwdForm,function() {
						resetPwdForm.get('resetPassword1').setValue('');
						resetPwdForm.get('resetPassword2').setValue('');
					});
					return;
				}
				resetPwdForm.getForm().submit({
					url: 'resetPwd.asp',
					waitMsg: '正在提交，请稍后......',
					success: function(form, action) {
						resetPwdWin.hide();
						showMsg(action.result.msg,loginForm);
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,resetPwdWin);
					}
				});
			}
		},{
			text: '重填',
			handler: function() {
				resetPwdForm.getForm().reset();
				resetPwdForm.getForm().findField('resetOprId').setValue(loginForm.findById('oprid').getValue());
			}
		}]
	});

var loginForm;
Ext.onReady(function(){
	Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    
    loginForm = new Ext.form.FormPanel({
		frame: false,
		border : false,
		bodyStyle:'background:transparent',
		labelAlign : 'left',
		labelWidth : 104,
//		labelHeight : 100,
		items: [
			new Ext.form.FieldSet(
	            {
	            defaultType: 'textfield',
	            border : false,
	            bodyStyle: 'padding:15px 0px 0px 0px;',
	            items: [
	            {
	                xtype : 'panel',
					bodyStyle:'background:transparent',
					frame : false,
					border : false,
					height: 10
	            },{
					xtype : 'textfield',
//					fieldLabel: '操作员',
					itemCls:"user",
					style:"font-size:20px;",
					allowBlank: false,
					width : 188,
					height:36,
					blankText: '操作员编号不能为空',
					id: 'oprid',
					name: 'oprid'
	            },
	            {
					xtype : 'panel',
					bodyStyle:'background:transparent',
					frame : false,
					border : false,
					height: 10
	            },
	            {
					xtype : 'textfield',
					inputType: 'password',
//					fieldLabel: '授权码',
//					itemCls:"pwd",
					style:"font-size:20px;",
					allowBlank: false,
					width : 188,
					height:36,
					blankText: '密码不能为空',
					id: 'password',
					name: 'password'
	            },
	            {
					xtype : 'panel',
					bodyStyle:'background:transparent',
					frame : false,
					border : false,
					height: 10
	            },
	            {
					id: 'validateCode',
					name: 'validateCode',
//					fieldLabel: '验证码',
//					itemCls:"vcode",
					style:"font-size:20px;",
			        maxLength: 4,
			        minLength: 4,
					width: 100,
					height:37,
				    allowBlank:false,                    
				    blankText:'验证码不能为空'
	            }]
			})
		],
//		buttonAlign: 'right',
		waitMsgTarget:true/*,
		buttons: [{
			text: '登  录',
			width : 80,
			handler: login
		}]*/
	});
	
   
	loginForm.render('loginForm');
	
	/**
	 * 系统登录
	 */
	
	var bd = Ext.getDom('validateCode');
    var bd2 = Ext.get(bd.parentNode);
    bd2.createChild([{
        tag: 'span',
        html: '<a href="javascript:newcode();">',
        style:'padding-left:20px'
    }, {
        tag: 'img',
        id: 'safecode',
//        src: '/tlpospmg/ValidateCode?' + Math.random(),
//        src: Ext.contextPath+'/ValidateCode?' + Math.random(),
        src: 'ValidateCode?' + Math.random(),
        align: 'absbottom'
    }]);
});

