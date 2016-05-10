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

Ext.onReady(function() {
	//菜单树
	menuTree = new Ext.tree.TreePanel({
		region: 'west',
		useArrows: true,
		autoScroll: true,
		animate: true,
		containerScroll: true,
		width: 200,
		//frame: true,
		split: true,
		//renderTo: Ext.getBody(),
		title: '<center>系统菜单</center>',
		collapsible: true,
		rootVisible: false,
		root: new Ext.tree.AsyncTreeNode({
			text: '测试',
			loader: new Ext.tree.TreeLoader({
				dataUrl: 'tree.asp?id=init'
			})
		}),
		listeners: {
			click: function(node) {
				if(node.leaf) {
					Ext.getCmp("operatrInfo").hide();
					Ext.getCmp("blank").hide();
					initMask.msg = '系统界面加载中，请稍后......';
					initMask.show();
					Ext.get("mainUI").dom.src = node.attributes.url;
					txnCode = node.attributes.id;
					hideToolInitMask.defer(500);
				}
			}
		}
	});
	//打开菜单树
	menuTree.getRootNode().expand(true);
	
	var timeToolItem = new Ext.Toolbar.TextItem('');
	
	// 顶部菜单
	var topToolBar = new Array(toolBarStr);
	
	// 修改操作员密码
	var resetPwdMenu = {
		text: '修改密码',
		id: 'key',
		iconCls: 'key',
		handler: function() {
			resetPwdWin.show();
			resetPwdForm.getForm().reset();
			resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
		}
	};
	
	// 重置操作员密码
	var clearPwdMenu = {
		text: '重置密码',
		id: 'otkey',
		iconCls: 'key',
		handler: function() {
			clearPwdWin.show();
			clearPwdForm.getForm().reset();
			clearPwdForm.get('clearOprId').setValue(operator[OPR_ID_BELLOW]);
		}
	};
	
	var lockScreenMenu = {
		text: '锁屏',
		id: 'lock',
		iconCls: 'lock',
		handler: function() {
			lockWin.show();
			lockForm.getForm().reset();
			lockForm.get('lockOprId').setValue(operator[OPR_ID]);
		}
	};
	
	var quitMenu = {
		text: '安全退出',
		iconCls: 'quit',
		handler: function() {
			showConfirm('确定要退出并关闭吗？',this,function(bt) {
				if(bt == 'yes') {
					window.location.href = 'logout.asp';
					/*Ext.Ajax.request({
						url: 'logout.asp'
						success: function(form, action) {
							window.location.href = 'logout.asp';
						}	
					});*/
//					window.opener=null;
//       				window.open('', '_self', ''); 
//					window.close();
					
//					window.location.href = 'logout.asp';
				}
			});
		}
	};
	topToolBar.add('->');
	topToolBar.add(resetPwdMenu);
	// 当前操作员编号
	if(operator[OPR_ID]=='0000100'){
		topToolBar.add('-');
		topToolBar.add(clearPwdMenu);
	}
	topToolBar.add('-');
	topToolBar.add(quitMenu);
	
	//用户UI主面板
	var mainPanel = new Ext.Panel({
		frame: true,
		html: '<iframe id="mainUI" name="mainUI" width="100%" height="100%" frameborder="0" scrolling="auto"/>',
		region: 'center',
		tbar: topToolBar,
		items: [{
//				title:'用户信息',
				bodyStyle:"background-image:url('" + Ext.contextPath + "/ext/resources/images/operateInfo.png')",
				height:362,
				width:420,
				id: 'operatrInfo',
				frame:false,
//        		collapsible: true,
				xtype: 'panel',
				layout: 'column',
				items: [{
							columnWidth: 1,
							layout: 'form',
							items: [{
								height: 68
							}]
						},{
							columnWidth: 1,
							layout: 'form',
							defaults:{labelStyle: 'padding-left: 80px; font-weight: bold; color: green;'},
							items: [{
								fieldLabel: '提示信息&nbsp',
								labelStyle: 'padding-left: 80px; font-weight: bold; font-size: 20px; color: blue;',
								height: 65
							},{
//								border:false,
								layout:'column',
								items:[
									new Ext.Panel({
										columnWidth:.8,
										layout:'form',
										defaults:{labelStyle: 'padding-left: 80px; font-weight: bold; color: green;'},
										items:[{
											xtype: 'displayfield',
											fieldLabel: '初始预留信息&nbsp',
											height: 56,
											id: 'resvInfoShow'
										}]
									}),
									new Ext.Panel({
										columnWidth: .1,
										layout: {
											type: 'hbox',
										    pack: 'center',
										    align: 'middle'
										},
										items: [{
				                			xtype: 'button',
											iconCls: 'edit',
			//								text:'重置',
											width: 20,
											onClick : function(){
												resvInfoUpdWin.show();
												resvInfoUpdForm.getForm().reset();
//												resvInfoUpdForm.get('resvInfoUpd').setValue(resvInfo);
											}
			                			}]
									})
								]
							},{
								xtype: 'displayfield',
								fieldLabel: '上次登录时间&nbsp',
								height: 28,
								id: 'lastLoginTimeShow'
							},{
								xtype: 'displayfield',
								fieldLabel: '上次登录&nbspI&nbspP&nbsp&nbsp',
								height: 28,
								id: 'lastLoginIpShow'
							},{
								xtype: 'displayfield',
								fieldLabel: '上次登录状态&nbsp',
								height: 28,
								id: 'lastLoginStatusShow'
							}]
						}]
			},{
				id: 'blank',
				xtype: 'panel',
				height:1080
			}],
		bbar: [
			new Ext.Toolbar.TextItem(''),
			{
				xtype: 'tbspacer',
				width: 100
			},
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/user.png" title="操作员"/>' + ' ' + operator['opr_name']),
//			'-',
//			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/branch.png" title="机构"/>' + ' ' + operator['opr_brh_name']),
			'-',
			new Ext.Toolbar.TextItem('<image src="' + Ext.contextPath + '/ext/resources/images/time.png" title="当前时间"/>' + ' '),
			timeToolItem
		],
		listeners: {
			render: function() {
				Ext.TaskMgr.start({
					run: function() {
						Ext.fly(timeToolItem.getEl()).update(new Date().pattern('yyyy-MM-dd HH:mm:ss'));
					},
					interval: 1000
				});
			}
		}
	});
	
	Ext.getCmp('resvInfoShow').setValue(resvInfo);
	Ext.getCmp('lastLoginTimeShow').setValue(lastLoginTime);
	Ext.getCmp('lastLoginIpShow').setValue(lastLoginIp);
	Ext.getCmp('lastLoginStatusShow').setValue(lastLoginStatus);
	//用户界面
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [menuTree,mainPanel],
		renderTo: Ext.getBody()
	});
	
	
	//移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 2

		});
	};
	
	hideMainUIMask.defer(2000);
	
	
	/**
	 * 修改密码表单
	 */
	var resetPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textfield',
			fieldLabel: '操作员编号',
			id: 'resetOprId',
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
//			regexText: '密码必须是6位数字或字母',
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
			fieldLabel: '重复密码',
			inputType: 'password',
//			regex: /^[0-9a-zA-Z]{6}$/,
//			regexText: '密码必须是6位数字或字母',
			regex: /^[0-9a-zA-Z]{6,15}$/,
			regexText: '请输入长度为6-15位，数字和字母的组合',
			id: 'resetPassword2',
			name: 'resetPassword2',
			allowBlank: false,
			blankText: '重复密码不能为空',
			listeners :{
                'change':function(){
                	if(checkStrong(resetPwdForm.findById('resetPassword2').getValue()) < 2){
             			showErrorMsg("密码必须是6-15位数字和字母的组合", resetPwdForm);
             			Ext.getCmp("resetPassword2").setValue(""); 
             		}
				}
            }
		}]
	});
	
	/**
	 * 修改密码窗口
	 */
	var resetPwdWin = new Ext.Window({
		title: '密码修改',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'key',
		items: [resetPwdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'key',
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
						showSuccessAlert(action.result.msg,resetPwdWin,function() {
							resetPwdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,resetPwdWin);
					}
				});
			}
		},{
			text: '清空',
			handler: function() {
				resetPwdForm.getForm().reset();
				resetPwdForm.get('resetOprId').setValue(operator[OPR_ID]);
			}
		}]
	});
	
	//操作员
	var oprStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('OPR_ID_BELLOW',function(ret){
		oprStore.loadData(Ext.decode(ret));
	});
	
	
	/**
	 * 修改预留信息表单
	 */
	var resvInfoUpdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'textarea',
			width: 160,
			fieldLabel: '初始预留信息',
			regex: /^.{1,20}$/,
			regexText: '预留信息不超过20个字',
			id: 'resvInfoUpd',
			name: 'resvInfoUpd'/*,
			allowBlank: false,
			blankText: '预留信息不能为空'*/
		}]
	});
	
	/**
	 * 修改预留信息窗口
	 */
	var resvInfoUpdWin = new Ext.Window({
		title: '修改预留信息',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'otkey',
		items: [resvInfoUpdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'key',
		buttons: [{
			text: '提交',
			handler: function() {
				if(!resvInfoUpdForm.getForm().isValid()) {
					return;
				}
				resvInfoUpdForm.getForm().submit({
					url: 'UpdResvInfo.asp',
					waitMsg: '正在处理，请稍后......',
					success: function(form, action) {
						Ext.getCmp('resvInfoShow').setValue(resvInfoUpdForm.get('resvInfoUpd').getValue());
						showSuccessAlert(action.result.msg,resvInfoUpdWin,200,function() {
							resvInfoUpdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,resvInfoUpdWin);
					}
				});
			}
		},{
			text: '清空',
			handler: function() {
				resvInfoUpdForm.getForm().reset();
//				resvInfoUpdForm.get('resvInfoUpd').setValue(resvInfo);
			}
		}]
	});
		
	
	
	
	/**
	 * 重置密码表单
	 */
	var clearPwdForm = new Ext.form.FormPanel({
		frame: true,
		width: 300,
		autoHeight: true,
		waitMsgTarget: true,
		items: [{
			xtype: 'combo',
			fieldLabel: '操作员编号',
			store: oprStore,
			id: 'clearOprId',
			name: 'clearOprId',
			readOnly: false
		}]
	});
	
	/**
	 * 重置密码窗口
	 */
	var clearPwdWin = new Ext.Window({
		title: '密码重置',
		frame: true,
		width: 300,
		layout: 'fit',
		iconCls: 'otkey',
		items: [clearPwdForm],
		resizable: false,
		closable: true,
		closeAction: 'hide',
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: false,
		animateTarget: 'key',
		buttons: [{
			text: '重置密码',
			handler: function() {
				if(!clearPwdForm.getForm().isValid()) {
					return;
				}
				clearPwdForm.getForm().submit({
					url: 'clearPwd.asp?resetOprId= '+clearPwdForm.get('clearOprId').getValue(),
					waitMsg: '正在处理，请稍后......',
					success: function(form, action) {
						showMsg(action.result.msg,clearPwdWin,function() {
							clearPwdWin.hide();
						});
					},
					failure: function(form, action) {
						showErrorMsg(action.result.msg,clearPwdWin);
					}
				});
			}
		},{
			text: '清空重填',
			handler: function() {
				clearPwdForm.getForm().reset();
				clearPwdForm.get(clearOprId).setValue(operator[OPR_ID]);
			}
		}]
	});
	
//	// 锁屏表单
//	var lockForm = new Ext.form.FormPanel({
//		frame: true,
//		width: 300,
//		autoHeight: true,
//		waitMsgTarget: true,
//		items: [{
//			xtype: 'textfield',
//			fieldLabel: '操作员编号',
//			id: 'lockOprId',
//			name: 'lockOprId',
//			readOnly: true
//		},{
//			xtype: 'textfield',
//			fieldLabel: '密码',
//			inputType: 'password',
//			regex: /^[0-9a-zA-Z]{6}$/,
//			regexText: '密码必须是6位数字或字母',
//			id: 'lockPassword',
//			name: 'lockPassword',
//			allowBlank: false,
//			blankText: '请输入密码'
//		}]
//	});
//	
//	// 锁屏对话框
//	var lockWin = new Ext.Window({
//		title: '屏幕锁定',
//		frame: true,
//		width: 300,
//		height: 300,
//		layout: 'fit',
//		iconCls: 'lock',
//		items: [lockForm],
//		resizable: false,
//		closable: false,
//		closeAction: 'hide',
//		buttonAlign: 'center',
//		initHiddenL: true,
//		modal: true,
//		draggable: false,
//		animateTarget: 'lock',
//		buttons: [{
//			text: '解锁',
//			iconCls: 'key',
//			tooltip: '解锁',
//			handler: function() {
//				if(!lockForm.getForm().isValid()) {
//					return;
//				}
//				lockForm.getForm().submit({
//					url: 'unlockScreen.asp',
//					waitMsg: '正在验证密码，请稍后......',
//					success: function(form, action) {
//						lockWin.hide();
//						
//					},
//					failure: function(form, action) {
//						showErrorMsg(action.result.msg,lockWin);
//					}
//				});
//			}
//		}]
//	});
	
	
	Ext.get("mainUI").dom.src = Ext.contextPath + '/page/system/main_0.jsp';
});

function lackScreenSubmit(obj,options){
	
	var form = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		height: 80,
		items: [{
			xtype: 'textfield',
			id: 'username',
			name: 'username',
			fieldLabel: '柜员号',
			maskRe: /^[0-9]+$/,
			allowBlank: false,
			blankText: '请输入柜员号'
		},{
			xtype: 'textfield',
			inputType: 'password',
			id: 'pass',
			name: 'pass',
			fieldLabel: '密码',
			allowBlank: false,
			blankText: '请输入柜员密码'
		}]
	});
	
	
	var win = new Ext.Window({
		title: '钱宝科技统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.submit(options);
						},
						failure : function(form,action) {
							frm.reset();
							showErrorMsg(action.result.msg,obj);
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	});
	win.show();
}


function lackScreenRequest(obj,options){
	
	
	var form = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		height: 80,
		items: [{
			xtype: 'textfield',
			id: 'username',
			name: 'username',
			fieldLabel: '柜员号',
			allowBlank: false,
			blankText: '请输入柜员号'
		},{
			xtype: 'textfield',
			inputType: 'password',
			id: 'pass',
			name: 'pass',
			fieldLabel: '密码',
			allowBlank: false,
			blankText: '请输入柜员密码'
		}]
	});
	
	
	var win = new Ext.Window({
		title: '钱宝科技统一授权系统',
		frame: true,
		width: 300,
		height: 140,
		layout: 'fit',
		iconCls: 'logo',
		items: [form],
		resizable: false,
		closable: true,
		buttonAlign: 'center',
		initHiddenL: true,
		modal: true,
		draggable: true,
		animateTarget: 'lock',
		buttons: [{
			text:'确定',
			handler: function(bt){
				var frm = form.getForm();
				if(frm.isValid()) {
					frm.submit({
						url: 'AuthoriseAction.asp',
						waitTitle : '请稍候',
						waitMsg : '正在验证授权信息,请稍候...',
						success : function(form, action) {
							frm.reset();
							win.close();
							obj.request(options);
						},
						failure : function(form,action) {
							frm.reset();
							Ext.MessageBox.show({
								msg: action.result.msg,
								title: '错误提示',
								animEl: Ext.getBody(),
								buttons: Ext.MessageBox.OK,
								icon: Ext.MessageBox.ERROR,
								modal: true,
								width: 250
							});
						},
						params: {
							txnCode: txnCode
						}
					});
				}
			}
		}]
	});
	win.show();
}