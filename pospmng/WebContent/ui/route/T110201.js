Ext.onReady(function() {
	
	var dialog;
	function showUploadWin() {
	 // 文件上传窗口
		dialog = new UploadDialog({
			uploadUrl : 'T110201Action_upload.asp',//importCsv
			filePostName : 'files',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '10 MB',
			fileTypes : '*.csv',
			fileTypesDescription : 'csv文件',
			title: '上传渠道商户文件',
			scope : this,
			onEsc: function() {
				this.hide();
				gridStore.reload();
			},
			completeMethod: function() {
				gridStore.reload();
				dialog.on('uploadsuccess', onUploadSuccess);
				this.close();
			}
		});
		gridStore.reload();
		dialog.show();
	}
	
		// 文件上传窗口
		function onUploadSuccess(dialog, filename, resp_data, record){
			// 回调函数代码  
			alert(resp_data);
			//gridStore.reload(); 
			Ext.Msg.confirm("提示：",resp_data);
		}
		
	
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('feeCardType',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});

	//取规则商户名称下拉列表
	var mchtNmStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('MCHT_NAME','',function(ret){
		mchtNmStore.loadData(Ext.decode(ret));
	});


	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});

	//取性质制作下拉列表
	var businessNmStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('businessNmStore','',function(ret){
		businessNmStore.loadData(Ext.decode(ret));
	});

	//取档位名称下拉列表
	var discNmStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('DISC_NM','',function(ret){
		discNmStore.loadData(Ext.decode(ret));
	});

	
	// 渠道商户信息add
	var addOprForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
					xtype : 'textfield',
					id: 'mchtIdUpId',
					name : 'mchtIdUp',
					allowBlank : false,
					fieldLabel : '渠道商户号<font color="red">*</font>',
					width : 240,
					regex:/^\d+$/,
					maxLength : 15
				},{
					xtype : 'textfield',
					id: 'mchtNameUpId',
					name : 'mchtNameUp',
					allowBlank : false,
					fieldLabel : '渠道商户名称<font color="red">*</font>',
					width : 240,
					maxLength : 40
				},{
					xtype : 'textfield',
					id: 'termIdUpId',
					name : 'termIdUp',
					allowBlank : false,
					invalidText:'请输入8位数字！',
					regex : /(\d{8})$/,
					fieldLabel : '渠道终端号<font color="red">*</font>',
					width : 240,
					maxLength : 8
				},{
					xtype: 'dynamicCombo',
					//labelStyle: 'padding-left: 5px',
					methodName: 'getAreaCode',
					fieldLabel : '所属地区<font color="red">*</font>',
					id: 'areaId',
					name : 'area',
					allowBlank: false,
					editable: true,
					width : 240,
					maxLength : 60
				},{
					xtype: 'basecomboselect',
					baseParams: 'CHANNEL_ALL',
					id:'quDaoId',
					hiddenName: 'quDao',
					fieldLabel : '支付渠道<font color="red">*</font>',
					allowBlank : false,
					width: 240,
					listeners: {
						'select': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('quDaoId').getValue();
							Ext.getCmp('yeWuId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('yeWuId').value = '';
							//addOprForm.getForm().findField("yeWuId").reset();
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						},
						'change': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('quDaoId').getValue();
							Ext.getCmp('yeWuId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('yeWuId').value = '';
							//addOprForm.getForm().findField("yeWuId").reset();
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						}
					}
				},{
					xtype: 'basecomboselect',
					id: 'yeWuId',
					hiddenName: 'yeWu',
					store: busiStore,
					fieldLabel : '业务<font color="red">*</font>',
					displayField: 'displayField',
					valueField: 'valueField',
					minLength:'1',
					minLengthText:'业务：不可为空！',
					allowBlank : false,
					width: 240,
					listeners: {
						'select': function() {
							var chlId = Ext.getCmp('yeWuId').getValue();
							businessNmStore.removeAll();
							Ext.getCmp('brhId3Id').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('brhId3Id').value = '';
							SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
							businessNmStore.loadData(Ext.decode(ret));
							});

							discNmStore.removeAll();
							Ext.getCmp('discIdId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('discIdId').value = '';
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM',chlId,function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});

							Ext.Ajax.request({
								url : 'T110121Action_queryEncType.asp',
								params : {
									brhId2 : chlId
								},
			                    //成功时回调
			                    success: function(response, options) {
			                       //获取响应的json字符串
			                      var responseArray = Ext.util.JSON.decode(response.responseText); 
			                         if(responseArray.msg=="false"){
			                        	 Ext.getCmp('zmkId').allowBlank = false;
			                         }else {
			                        	 Ext.getCmp('zmkId').allowBlank = true;
									}
			                    }
							});
						},
						'change': function() {
							businessNmStore.removeAll();
							var chlId = Ext.getCmp('yeWuId').getValue();
							Ext.getCmp('brhId3Id').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('brhId3Id').value = '';
							SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
							businessNmStore.loadData(Ext.decode(ret));
							});

							discNmStore.removeAll();
							Ext.getCmp('discIdId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('discIdId').value = '';
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM',chlId,function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});

							Ext.Ajax.request({
								url : 'T110121Action_queryEncType.asp',
								params : {
									brhId2 : chlId
								},
			                    //成功时回调
			                    success: function(response, options) {
				                       //获取响应的json字符串
				                      var responseArray = Ext.util.JSON.decode(response.responseText); 
				                         if(responseArray.msg=="false"){
				                        	 Ext.getCmp('zmkId').allowBlank = false;
				                         }else {
				                        	 Ext.getCmp('zmkId').allowBlank = true;
										}
				                }
							});
						}
					}
				},{
					xtype: 'basecomboselect',
					id: 'brhId3Id',
					hiddenName : 'brhId3',
					store: businessNmStore,
					fieldLabel : '性质<font color="red">*</font>',
					displayField: 'displayField',
					valueField: 'valueField',
					minLength:'1',
					minLengthText:'性质：不可为空！',
					allowBlank : false,
					width : 240,
					maxLength : 60
				},{
					xtype : 'textfield',
					id: 'zmkId',
					name : 'zmk',
					allowBlank : true,
					fieldLabel : '终端主密钥',
					width : 240,
					maxLength : 32
				},{
					xtype: 'basecomboselect',
					id: 'discIdId',
					hiddenName : 'discId',
            		allowBlank: false,
					blankText: '成本扣率不能为空',
					emptyText : '请选择成本扣率',
					store: discNmStore,
					fieldLabel : '成本扣率<font color="red">*</font>',
					displayField: 'displayField',
					valueField: 'valueField',
					minLength:'1',
					minLengthText:'成本扣率：不可为空！',
					width: 240
				},{
					xtype: 'basecomboselect',
					baseParams: 'MCHNT_NO_GTWY',
					id: 'mchtIdId',
					hiddenName : 'mchtId',
					allowBlank : false,
					fieldLabel : '合规商户<font color="red">*</font>',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true,
					width : 240,
					maxLength : 60,
					listeners : {
			            'beforequery':function(e){  
			                var combo = e.combo;    
			                if(!e.forceAll){    
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }  
			            }  
			        } 
				},{
					xtype : 'combo',
					fieldLabel : '特殊计费',
					id : 'misc01_add',
					hiddenName : 'misc1',
					value : '0',
					allowBlank : false,
					width : 240,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [[ '0', '无' ],
						        [ '1', '县乡普通' ],
								[ '2', '县乡三农' ] ]
					})
				},{
					xtype : 'textfield',
					id: 'mchtDspId',
					name : 'mchtDsp',
					allowBlank : true,
					fieldLabel : '备注',
					width : 240,
					maxLength : 50
				}]
	});

	
	// 渠道商户信息停用原因
	var stopOprForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
					xtype : 'combo',
					fieldLabel : '停用类别',
					id : 'stopType_st',
					hiddenName: 'stopType',
					allowBlank : false,
					width: 240,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '1', '商户被投诉' ], [ '2', '商户号被晒单' ],
						         [ '3', '商户号被整改' ], [ '4', '交易金额过大' ],[ '5', '其他' ] ]
					})
				},
				new Ext.form.TextArea({
					id: 'stopReason_st',
					name : 'stopReason',
					fieldLabel : '停用原因',
					width : 240,
					maxLength : 60
	            })]
	});


	// 渠道商户信息修改
	var updOprForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
					xtype : 'textfield',
					id: 'mchtNameUp_upd',
					name : 'mchtNameUp',
					allowBlank : false,
					fieldLabel : '渠道商户名称<font color="red">*</font>',
					width : 240,
					maxLength : 40
				},{
					xtype: 'dynamicCombo',
					//labelStyle: 'padding-left: 5px',
					methodName: 'getAreaCode',
					fieldLabel : '所属地区<font color="red">*</font>',
					id: 'area_upd',
					hiddenName : 'area',
					allowBlank: false,
					editable: true,
					width : 240,
					maxLength : 60
				},{
					xtype: 'basecomboselect',
					id:'channelNm_upd',
					hiddenName : 'channelNm',
					baseParams: 'CHANNEL_ALL',
					fieldLabel : '支付渠道<font color="red">*</font>',
					allowBlank : false,
					width: 240,
					readOnly:true,
					listeners: {
						'select': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('channelNm_upd').getValue();
							Ext.getCmp('businessNm_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('businessNm_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						},
						'change': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('channelNm_upd').getValue();
							Ext.getCmp('businessNm_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('businessNm_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						}
					}
				},{
					xtype: 'basecomboselect',
					id: 'businessNm_upd',
					hiddenName : 'businessNm',
					store: busiStore,
					fieldLabel : '业务<font color="red">*</font>',
					allowBlank : false,
					displayField: 'displayField',
					valueField: 'valueField',
					width: 240,
					listeners: {
						'select': function() {
							businessNmStore.removeAll();
							var chlId = Ext.getCmp('businessNm_upd').getValue();
							Ext.getCmp('characterNm_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('characterNm_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
							businessNmStore.loadData(Ext.decode(ret));
							});

							discNmStore.removeAll();
							Ext.getCmp('discId_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('discId_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM',chlId,function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});

							Ext.Ajax.request({
								url : 'T110121Action_queryEncType.asp',
								params : {
									brhId2 : chlId
								},
			                    //成功时回调
			                    success: function(response, options) {
			                       //获取响应的json字符串
			                      var responseArray = Ext.util.JSON.decode(response.responseText); 
			                         if(responseArray.msg=="false"){
			                        	 Ext.getCmp('zmkId').allowBlank = false;
			                         }else {
			                        	 Ext.getCmp('zmkId').allowBlank = true;
									}
			                    }
							});
						},
						'change': function() {
							businessNmStore.removeAll();
							var chlId = Ext.getCmp('businessNm_upd').getValue();
							Ext.getCmp('characterNm_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('characterNm_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
							businessNmStore.loadData(Ext.decode(ret));
							});

							discNmStore.removeAll();
							Ext.getCmp('discId_upd').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('discId_upd').value = '';
							SelectOptionsDWR.getComboDataWithParameter('DISC_NM',chlId,function(ret){
								discNmStore.loadData(Ext.decode(ret));
							});

							Ext.Ajax.request({
								url : 'T110121Action_queryEncType.asp',
								params : {
									brhId2 : chlId
								},
			                    //成功时回调
			                    success: function(response, options) {
			                       //获取响应的json字符串
			                      var responseArray = Ext.util.JSON.decode(response.responseText); 
			                         if(responseArray.msg=="false"){
			                        	 Ext.getCmp('zmkId').allowBlank = false;
			                         }else {
			                        	 Ext.getCmp('zmkId').allowBlank = true;
									}
			                    }
							});
						}
					}
				},{
					xtype: 'basecomboselect',
					id: 'characterNm_upd',//brhId3_upd
					hiddenName : 'characterNm',//brhId3
					store: businessNmStore,
					fieldLabel : '性质<font color="red">*</font>',
					displayField: 'displayField',
					valueField: 'valueField',
					allowBlank : false,
					width : 240,
					maxLength : 60
				},{
					xtype : 'textfield',
					id: 'zmk_upd',
					name : 'zmk',
					allowBlank : true,
					fieldLabel : '终端主密钥',
					width : 240,
					maxLength : 32
				},{
					xtype: 'basecomboselect',
					id: 'discId_upd',
					name : 'discId',
            		allowBlank: false,
					hiddenName: 'discId',
					blankText: '成本扣率不能为空',
					emptyText : '请选择成本扣率',
					store: discNmStore,
					fieldLabel : '成本扣率<font color="red">*</font>',
					displayField: 'displayField',
					valueField: 'valueField',
					width: 240,
					maxLength : 60,
					listeners : {
					    render : function() {
					        Ext.fly(this.el).on('click', function(e, t) {
								discNmStore.removeAll();
								var chlId = Ext.getCmp('businessNm_upd').getValue();
								SelectOptionsDWR.getComboDataWithParameter('DISC_NM',chlId,function(ret){
									discNmStore.loadData(Ext.decode(ret));
								});
							});
					    }
					}
				},{
					xtype: 'basecomboselect',
					baseParams: 'MCHNT_NO_GTWY',
					id: 'mchtId_upd',
					hiddenName : 'mchtId',
					allowBlank : false,
					fieldLabel : '合规商户<font color="red">*</font>',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true,
					width : 240,
					maxLength : 60,
					listeners : {
			            'beforequery':function(e){  
			                var combo = e.combo;    
			                if(!e.forceAll){    
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }  
			            }  
			        }
				},{
					xtype : 'basecomboselect',
					fieldLabel : '特殊计费',
					id : 'misc01_upd',
					hiddenName : 'misc1',
					value : '0',
					allowBlank : false,
					width : 240,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [[ '0', '无' ],
						        [ '1', '县乡普通' ],
								[ '2', '县乡三农' ] ]
					})
				},{
					xtype : 'textfield',
					id: 'mchtDsp_upd',
					name : 'mchtDsp',
					allowBlank : true,
					fieldLabel : '备注',
					width : 240,
					maxLength : 50
				},{
					hidden : true,
					xtype : 'textfield',
					id: 'termIdUp_upd',
					name : 'termIdUp'
				},{
					hidden : true,
					xtype : 'textfield',
					id: 'mchtIdUp_upd',
					name : 'mchtIdUp'
				},{
					hidden : true,
					xtype : 'textfield',
					id: 'brhId3_upd',
					name : 'brhId3'
				}]
	});
	

	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			addOprWin.show();
		}
	};


	// 渠道商户信息添加窗口
	var addOprWin = new Ext.Window({
		title : '渠道商户信息新增',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		width : 400,
		autoHeight : true,
		layout : 'fit',
		items : [ addOprForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'add',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				var chlId = addOprForm.getForm().findField('quDao').getValue();
				var busiId = addOprForm.getForm().findField('yeWu').getValue();
				var charId = addOprForm.getForm().findField('brhId3').getValue();
				var discId = addOprForm.getForm().findField('discId').getValue();
				if(chlId.trim().length <4){
					alert('支付渠道：不可为空！');
					return false;
				}
				if(busiId.trim().length <8){
					alert('业务：不可为空！');
					return false;
				}
				if(charId.trim().length <12){
					alert('性质：不可为空！');
					return false;
				}
				if(discId.trim().length <1){
					alert('成本扣率：不可为空！');
					return false;
				}
				if(chlId.trim() != charId.trim().substr(0,4) || busiId.trim() != charId.trim().substr(0,8)){
					alert("支付渠道 、业务、性质数据不正确！");
					return false;
				}
				if (addOprForm.getForm().isValid()) {
					addOprForm.getForm().submit({
						url : 'T110201Action_add.asp',
						waitMsg : '正在添加信息，请稍后......',
						params : {
							txnId : 'T0100020',
							subTxnId : '01'
						},
						success : function(form, action) {
							showSuccessMsg("添加成功", addOprForm);
							addOprForm.getForm().reset();
							addOprWin.hide(addOprForm);
							gridStore.reload();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, addOprForm);
						}
					});

				}
			}
		}, {
			text : '重置',
			handler : function() {
				addOprForm.getForm().reset();
			}
		} ]
	});

	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			var rec = gridPanel.getSelectionModel().getSelected();
			//渠道不可修改，更新性质
			busiStore.removeAll();
			SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',rec.get('channelId'),function(ret){
				busiStore.loadData(Ext.decode(ret));
				updOprForm.findById('businessNm_upd').setValue(rec.get('businessId'));
			});
			
			businessNmStore.removeAll();
			SelectOptionsDWR.getComboDataWithParameter('businessNmStore',rec.get('businessId'),function(ret){
				businessNmStore.loadData(Ext.decode(ret));
				updOprForm.findById('characterNm_upd').setValue(rec.get('characterId'));
			});

			discNmStore.removeAll();
			SelectOptionsDWR.getComboDataWithParameter('DISC_NM',rec.get('businessId'),function(ret){
				discNmStore.loadData(Ext.decode(ret));
				updOprForm.findById('discId_upd').setValue(rec.get('discId'));
			});
			//var mchtIdUp = gridPanel.getSelectionModel().getSelected().data.mchtIdUp;

			updOprForm.findById('mchtIdUp_upd').setValue(rec.get('mchtIdUp'));
			updOprForm.findById('brhId3_upd').setValue(rec.get('brhId3'));
			updOprForm.findById('termIdUp_upd').setValue(rec.get('termIdUp'));
			updOprForm.findById('mchtNameUp_upd').setValue(rec.get('mchtNameUp'));
			updOprForm.findById('area_upd').setValue(rec.get('area'));
			updOprForm.findById('channelNm_upd').setValue(rec.get('channelId'));
			updOprForm.findById('zmk_upd').setValue(rec.get('zmk'));
			updOprForm.findById('mchtDsp_upd').setValue(rec.get('mchtDsp'));
			updOprForm.findById('mchtId_upd').setValue(rec.get('mchtId'));
			updOprForm.findById('misc01_upd').setValue(rec.get('misc1').substring(0,1));
			//Ext.getCmp('misc01_upd').setValue(misc1.substring(0,1));

			updOprWin.show();
			updOprWin.center();
		}
	};

	// 渠道商户信息添加窗口
	var updOprWin = new Ext.Window({
		title : '渠道商户信息修改',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		width : 400,
		autoHeight : true,
		layout : 'fit',
		items : [ updOprForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'edit',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				var chlId = updOprForm.getForm().findField('channelNm').getValue();
				var busiId = updOprForm.getForm().findField('businessNm').getValue();
				var charId = updOprForm.getForm().findField('characterNm').getValue();
				var discId = updOprForm.getForm().findField('discId').getValue();
				if(chlId.trim().length <4){
					alert('支付渠道：不可为空！');
					return false;
				}
				if(busiId.trim().length <8){
					alert('业务：不可为空！');
					return false;
				}
				if(charId.trim().length <12){
					alert('性质：不可为空！');
					return false;
				}
				if(discId.trim().length <1){
					alert('成本扣率：不可为空！');
					return false;
				}
				if(chlId.trim() != charId.trim().substr(0,4) || busiId.trim() != charId.trim().substr(0,8)){
					alert("支付渠道 、业务、性质数据不正确！");
					return false;
				}
				if (updOprForm.getForm().isValid()) {
					updOprForm.getForm().submit({
						url : 'T110201Action_updateInfo.asp',
						waitMsg : '正在加载信息，请稍后......',
						params : {
							txnId : 'T0100020',
							subTxnId : '01'
						},
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updOprForm);
							updOprForm.getForm().reset();
							updOprWin.hide(updOprForm);
							gridStore.reload();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updOprForm);
							updOprForm.getForm().reset();
							updOprWin.hide(updOprForm);
							gridStore.reload();
						}
					});

				}
			}
		}, {
			text : '重置',
			handler : function() {
				updOprForm.getForm().reset();
			}
		} ]
	});

	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
			var mchtIdUp = gridPanel.getSelectionModel().getSelected().data.mchtIdUp;
			showConfirm('确定要删除该渠道商户吗？渠道商户号：' + mchtIdUp,gridPanel,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						var termIdUp = gridPanel.getSelectionModel().getSelected().data.termIdUp;
						var brhId3 = gridPanel.getSelectionModel().getSelected().data.brhId3;
						Ext.Ajax.request({
							url : 'T110201Action_delete.asp',
							params : {
								"mchtIdUp" : mchtIdUp,
								"termIdUp" : termIdUp,
								"brhId3" : brhId3
							},
							success : function(response) {
								Ext.Msg.alert('提示', '删除成功！');
								gridStore.reload();
							}
						});
					}
				});
		}
	};
	
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'mchtIdUp_query',
			name: 'mchtIdUp',
			vtype: 'alphanum',
			regex:/^\d+$/,
			maxLength:15,
			width: 240,
			fieldLabel: '渠道商户号'
		},{
			xtype: 'textfield',
			id: 'mchtNameUp_query',
			name: 'qudismchtNameUpcNm',
			width: 240,
			fieldLabel: '渠道商户名称'
		},{
			xtype: 'textfield',
			id: 'termIdUp_query',
			name: 'termIdUp',
			vtype: 'alphanum',
			invalidText:'请输入8位数字！',
			regex : /(\d{8})$/,
			maxLength : 8,
			width: 240,
			fieldLabel: '渠道终端号'
		},{
			xtype: 'basecomboselect',
			baseParams: 'CHANNEL_ALL',
			id:'channelNm_query',
			hiddenName: 'channelNm',
			fieldLabel : '支付渠道',
			emptyText:'',
			value:'',
			width: 240,
			listeners: {
				'select': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('channelNm_query').getValue();
					Ext.getCmp('businessNm_query').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('businessNm_query').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
					busiStore.loadData(Ext.decode(ret));
					});
				},
				'change': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('channelNm_query').getValue();
					Ext.getCmp('businessNm_query').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('businessNm_query').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
					busiStore.loadData(Ext.decode(ret));
					});
				}
			}
		},{
			xtype: 'basecomboselect',
			id: 'businessNm_query',
			hiddenName: 'businessNm',
			store: busiStore,
			fieldLabel : '业务',
			emptyText:'',
			value:'',
			displayField: 'displayField',
			valueField: 'valueField',
			width: 240,
			listeners: {
				'select': function() {
					var chlId = Ext.getCmp('businessNm_query').getValue();
					businessNmStore.removeAll();
					Ext.getCmp('characterNm_query').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('characterNm_query').value = '';
					SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
					businessNmStore.loadData(Ext.decode(ret));
					});
				},
				'change': function() {
					businessNmStore.removeAll();
					var chlId = Ext.getCmp('businessNm_query').getValue();
					Ext.getCmp('characterNm_query').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('characterNm_query').value = '';
					SelectOptionsDWR.getComboDataWithParameter('businessNmStore',chlId,function(ret){
					businessNmStore.loadData(Ext.decode(ret));
					});
				}
			}
		},{
			xtype: 'basecomboselect',
			id: 'characterNm_query',
			hiddenName : 'characterNm',
			store: businessNmStore,
			fieldLabel : '性质',
			emptyText:'',
			value:'',
			displayField: 'displayField',
			valueField: 'valueField',
			width : 240,
			maxLength : 60
		},{
			xtype : 'combo',
			fieldLabel : '渠道商户状态',
			id : 'status_query',
			name: 'status',
			width: 240,
			store : new Ext.data.ArrayStore({
				fields : [ 'valueField', 'displayField' ],
				data : [ [ '0', '启用' ], [ '1', '停用' ] ]
			})
		},{
			xtype: 'dynamicCombo',
			methodName: 'getAreaCode',
			fieldLabel : '所属地区',
			id: 'area_query',
			name : 'area',
			allowBlank: true,
			width : 240,
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_NO_GTWY',
			id: 'mchtId_query',
			hiddenName : 'mchtId',
			fieldLabel : '合规商户',
			mode:'local',
			triggerAction: 'all',
			forceSelection: true,
			selectOnFocus: true,
			editable: true,
			lazyRender: true,
			width : 240,
			listeners : {
	            'beforequery':function(e){  
	                var combo = e.combo;    
	                if(!e.forceAll){    
	                    var input = e.query;    
	                    // 检索的正则  
	                    var regExp = new RegExp(".*" + input + ".*");  
	                    // 执行检索  
	                    combo.store.filterBy(function(record,id){    
	                        // 得到每个record的项目名称值  
	                        var text = record.get(combo.displayField);    
	                        return regExp.test(text);   
	                    });  
	                    combo.expand();    
	                    return false;  
	                }  
	            }  
	        } 
		},{
			xtype : 'combo',
			fieldLabel : '特殊计费',
			id : 'misc01_query',
			hiddenName : 'misc1',
			//value : '0',
			width : 240,
			store : new Ext.data.ArrayStore({
				fields : [ 'valueField',
						'displayField' ],
				data : [[ '0', '无' ],
				        [ '1', '县乡普通' ],
						[ '2', '县乡三农' ] ]
			})
		},{
			xtype: 'textfield',
			id: 'min_query',
			name: 'min',
			vtype: 'alphanum',
			width: 240,
			regex:/^\d+$/,
			maxLength:6,
			fieldLabel: '关联本地商户数量(最小值)'
		},{
			xtype: 'textfield',
			id: 'max_query',
			name: 'max',
			vtype: 'alphanum',
			regex:/^\d+$/,
			maxLength:6,
			width: 240,
			fieldLabel: '关联本地商户数量(最大值)'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 400,
		autoHeight: true,
		items: [queryForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryWin.collapse();
				queryWin.getEl().pause(1);
				queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryWin.expand();
				queryWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				gridStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(editMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(delMenu);     //[4]
	menuArr.push('-');         //[5]
	menuArr.push(queryCondition);  //[6]
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblUpbrhMcht'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId3',mapping: 'brhId3'},
			{name: 'mchtIdUp',mapping: 'mchtIdUp'},
			{name: 'mchtNameUp',mapping: 'mchtNameUp'},
			{name: 'termIdUp',mapping: 'termIdUp'},
			{name: 'channelId',mapping: 'channelId'},
			{name: 'channelNm',mapping: 'channelNm'},
			{name: 'businessId',mapping: 'businessId'},	
			{name: 'businessNm',mapping: 'businessNm'},				
			{name: 'characterId',mapping: 'characterId'},			
			{name: 'characterNm',mapping: 'characterNm'},	
			{name: 'status',mapping: 'status'},
			{name: 'area',mapping: 'area'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'amount',mapping: 'amount'},
			{name: 'runTime',mapping: 'runTime'},				
			{name: 'stopTime',mapping: 'stopTime'},	
			{name: 'stopType',mapping: 'stopType'},
			{name: 'stopReason',mapping: 'stopReason'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'},
			{name: 'zmk',mapping: 'zmk'},
			{name: 'mchtDsp',mapping: 'mchtDsp'},
			{name: 'discId',mapping: 'discId'},
			{name: 'misc1',mapping: 'misc1'}
		])
	});
	
	gridStore.load({
		params: {
			start: 0
		}
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),
		{header: '渠道商户号',dataIndex: 'mchtIdUp',width: 130},
		{header: '渠道商户名称',dataIndex: 'mchtNameUp'},
		{header: '渠道终端号',dataIndex: 'termIdUp'},
		{header: '支付渠道',dataIndex: 'channelNm'},
		{header: '业务',dataIndex: 'businessNm'},
		{header: '性质',dataIndex: 'characterNm'},
		{header: '渠道商户状态',dataIndex: 'status',renderer:feeStatus},
		{header: '所属地区',dataIndex: 'area'},
		{header: '合规商户编号',dataIndex: 'mchtId',width: 130},
		{header: '合规商户名称',dataIndex: 'mchtNm'},
		{header: '特殊计费',dataIndex: 'misc1',renderer:misc1Render},
		{header: '关联本地商户数量',dataIndex: 'amount'},
		{header: '启用时间',dataIndex: 'runTime',renderer:formatDt},
		{header: '停用时间',dataIndex: 'stopTime',renderer:formatDt},
		{header: '停用类型',dataIndex: 'stopType',renderer:stopTypeRender},
		{header: '停用原因',dataIndex: 'stopReason'},
		{header: '最后修改时间',dataIndex: 'uptTime',renderer:formatDt},
		{header: '修改人',dataIndex: 'uptOpr',id:'firstMchtNm'}
	]);


	function misc1Render(value) {
		if ('0' == value.substring(0,1)) {
			return "<font color='black'>无</font>";
		}else if('1' == value.substring(0,1)){
			return "<font color='black'>县乡普通</font>";
		}else if('2' == value.substring(0,1)){
			return "<font color='black'>县乡三农</font>";
		} else {
			return "";
		}
	}
	
	var gridPanel = new Ext.grid.GridPanel({
		title: '渠道商户信息维护',
		region: 'center',
		iconCls: 'T207',
		frame: true,
		border: true,
		columnLines: true,	
		stripeRows: true,
		store: gridStore,
		autoExpandColumn: 'firstMchtNm',
		//sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		sm : new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),
		cm: gridColumnModel,
		forceValidation: true,
		tbar:[menuArr
		    ,'-',{
			xtype: 'button',
			text: '启用',
			name: 'resetDtl',
			id: 'resetDtl21',
			iconCls: 'accept',
			width: 80,
			handler:function() {

				//取多选记录方法 用逗号分开 后台处理
				var rows=gridPanel.getSelectionModel().getSelections();//获取所有选中行
				var str = '';
				for(var i=0;i <rows.length;i++){
					str = str+rows[i].get('mchtIdUp')+','+rows[i].get('termIdUp')+','+rows[i].get('brhId3')+';';
				}
				showConfirm('是否启用选中记录？',gridPanel,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url : 'T110201Action_start.asp',
								params : {
									"str" : str
								},
								method: 'POST',
								success : function(response) {
									Ext.Msg.alert('提示', '启用成功！');
									gridStore.reload();
								}
							});
						}
					});
			}
		},'-',{
			xtype: 'button',
			text: '停用',
			name: 'resetDtl',
			id: 'resetDtl',
			iconCls: 'stop',
			width: 80,
			handler:function() {
				showConfirm('是否停用选中记录？',gridPanel,function(bt) {
					
						//取多选记录方法 用逗号分开 后台处理
						var rows=gridPanel.getSelectionModel().getSelections();//获取所有选中行
						var str = '';
						for(var i=0;i <rows.length;i++){
							str = str+rows[i].get('mchtIdUp')+','+rows[i].get('termIdUp')+','+rows[i].get('brhId3')+';';
						}
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url : 'T110201Action_findMapByMchtIdUp.asp',
								params : {
									"str" : str
								},
								method: 'POST',
			                    success: function(response, options) {
			                       //获取响应的json字符串
			                      var responseArray = Ext.util.JSON.decode(response.responseText); 
			                         if(responseArray.msg=="false"){
											Ext.Msg.alert('提示', '选中记录中有存在映射关系，不可停用！请重新选择或单个停用');
			                         }else {
			 							stopOprWin.show();
									}
			                    }
							});
						}
				});
			}
		},'-', {
			xtype : 'button',
			text : '导入数据',
			name : 'button_upload',
			id : 'button_upload',
			iconCls : 'add',
			width : 80,
			handler : function() {
				showUploadWin();
			}
		},'-',{
			xtype: 'button',
			text: '导出数据',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				if (gridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
					Ext.Ajax.request({
						url: 'T110201Action_dateExport.asp',
						timeout: 60000,
						params: {},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
								//gridStore.load();
							} else {
								showErrorMsg(rspObj.msg,gridPanel);
							}
						},
						failure: function(){
							showErrorMsg('导出数据失败!!!',gridPanel);
						}
					});
				    } else {
				    	Ext.MessageBox.show({
							msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
							title: '报表下载说明',
							buttons: Ext.MessageBox.OK,
							modal: true,
							width: 220
						});
					}
				}
		}],
		loadMask: {
			msg: '正在加载渠道商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell,
		}
	});


	// 渠道商户信息停用原因
	var stopOprWin = new Ext.Window({
		title : '渠道商户停用原因',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		width : 400,
		autoHeight : true,
		layout : 'fit',
		items : [ stopOprForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'stop',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				
				//取多选记录方法 用逗号分开 后台处理
				var rows=gridPanel.getSelectionModel().getSelections();//获取所有选中行
				var str = '';
				for(var i=0;i <rows.length;i++){
					str = str+rows[i].get('mchtIdUp')+','+rows[i].get('termIdUp')+','+rows[i].get('brhId3')+';';
				}
				
				if (stopOprForm.getForm().isValid()) {
					stopOprForm.getForm().submit({
						url : 'T110201Action_stop.asp',
						waitMsg : '正在添加信息，请稍后......',
						params : {
							"str" : str
						},
						method: 'POST',
						success : function(form, action) {
							showSuccessMsg("停用成功", stopOprForm);
							stopOprForm.getForm().reset();
							stopOprWin.hide(stopOprForm);
							gridStore.reload();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, stopOprForm);
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				stopOprForm.getForm().reset();
			}
		} ]
	});

	
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
		gridPanel.getTopToolbar().items.items[2].disable();
		gridPanel.getTopToolbar().items.items[4].disable();
		gridPanel.getTopToolbar().items.items[8].disable();
		gridPanel.getTopToolbar().items.items[10].disable();
	}); 
	
	var rec;
	
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			var status=gridPanel.getSelectionModel().getSelected().get('status');
			if(null != rec){
				gridPanel.getTopToolbar().items.items[2].enable();
				gridPanel.getTopToolbar().items.items[4].enable();
			}else{
				gridPanel.getTopToolbar().items.items[2].disable();
				gridPanel.getTopToolbar().items.items[4].disable();
			}
			if ('1'==status) {
				gridPanel.getTopToolbar().items.items[8].enable();
				gridPanel.getTopToolbar().items.items[10].disable();
			}else if ('0'==status) {
				gridPanel.getTopToolbar().items.items[8].disable();
				gridPanel.getTopToolbar().items.items[10].enable();
			}
		}
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchtIdUp: queryForm.getForm().findField('mchtIdUp_query').getValue(),
			mchtNameUp: queryForm.getForm().findField('mchtNameUp_query').getValue(),
			termIdUp: queryForm.getForm().findField('termIdUp_query').getValue(),
			/*channelNm:Ext.get('channelNm_query').dom.value,//Ext.get获取的是displayField的内容
			businessNm:Ext.get('businessNm_query').dom.value,
			characterNm:Ext.get('characterNm_query').dom.value,*/
			channelNm:queryForm.getForm().findField('channelNm_query').getValue(),
			businessNm:queryForm.getForm().findField('businessNm_query').getValue(),
			characterNm:queryForm.getForm().findField('characterNm_query').getValue(),
			area:queryForm.getForm().findField('area_query').getValue(),
			mchtId:queryForm.getForm().findField('mchtId_query').getValue(),
			status:queryForm.getForm().findField('status_query').getValue(),
			misc1:queryForm.getForm().findField('misc01_query').getValue(),
			min:queryForm.getForm().findField('min_query').getValue(),
			max:queryForm.getForm().findField('max_query').getValue(),
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridPanel],
		renderTo: Ext.getBody()
	});
	
});