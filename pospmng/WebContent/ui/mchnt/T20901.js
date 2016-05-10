Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	//******************图片处理部分**********开始********
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'id'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});
	var custom = new Ext.Resizable('showBigPic', {
		    wrap:true,
		    pinned:true,
		    minWidth: 50,
		    minHeight: 50,
		    preserveRatio: true,
		    dynamic:true,
		    handles: 'all',
		    draggable:true
		});
	var customEl = custom.getEl();
	var rollTimes=0; //向上滚加1，向下滚减1
	var originalWidth =0; //图片缩放前的原始宽度
	var originalHeight = 0; //图片缩放前的原始高度
	document.body.insertBefore(customEl.dom, document.body.firstChild);
	customEl.on('dblclick', function(){
		customEl.puff();
		rollTimes =0;
	});
	customEl.hide(true);
 	function showPIC(id){
 		var rec = storeImg.getAt(id.substring(5)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=addTmp/' + rec.fileName;
	 		document.getElementById('showBigPic').onmousewheel = bigimg;
	 		originalWidth = document.getElementById('showBigPic').width;
	 		originalHeight = document.getElementById('showBigPic').height;
 		}
 		customEl.center();
	    customEl.show(true);
 	}
 	function bigimg(){
		var zoom = 0.05;
		var rollDirect = event.wheelDelta;
		if(rollDirect>0){
			rollTimes++;
		}
		if(rollDirect<0){
			rollTimes--;
		}
		if(this.height<50 && rollDirect<0){
			return false;
		}
		var cwidth = originalWidth*(1+zoom*rollTimes);
		var cheight = originalHeight*(1+zoom*rollTimes);
		custom.resizeTo(cwidth, cheight);
		return false;
	}
 	function delPIC(id){
 		customEl.hide();
	 	document.getElementById('showBigPic').src="";
	 	showConfirm('确定要删除吗？',mchntForm,function(bt) {
			if(bt == 'yes') {
				var rec = storeImg.getAt(id.substring(5)).data;
		 		T20100.deleteImgFileTmp(rec.fileName,'',function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId,
								mcht: ''
							}
						});
		 			}else{
		 				showMsg('操作失败，请刷新后重试！',mchntForm);
		 			}
		 		});
			}
	 	});
 	}
	storeImg.on('load',function(){
		for(var i=0;i<storeImg.getCount();i++){
			var rec = storeImg.getAt(i).data;
        	Ext.get(rec.btBig).on('click', function(obj,val){
        		showPIC(val.id);
        	});
        	Ext.get(rec.btDel).on('click', function(obj,val){
        		delPIC(val.id);
        	});
		}
	});
	

	
	var dataview = new Ext.DataView({
		frame: true,
        store: storeImg,
        tpl  : new Ext.XTemplate(
            '<ul>',
                '<tpl for=".">',
                    '<li class="phone">',
                    	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
                        	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName=addTmp/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大">&nbsp;<input type="button" id="{btDel}" value="删除"></div>',
						'</div>',
                    '</li>',
                '</tpl>',
            '</ul>'
        ),
        id: 'phones',
        itemSelector: 'li.phone',
        overClass   : 'phone-hover',
        singleSelect: true,
        multiSelect : true,
        autoScroll  : true
    });

    // 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20901Action_upload.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '1 MB',
		fileTypes : '*.jpg;*.png;*.jpeg',
		fileTypesDescription : '图片文件',
		title: '证书影印文件上传',
		scope : this,
//		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		completeMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId,
					mcht:''
				}
			});
		},
		postParams: {
			txnId: '20901',
			subTxnId: '02',
			imagesId: imagesId
		}
	});

	//******************图片处理部分**********结束********
	
	var nextBt1 = {
			xtype: 'button',
			width: 100,
			text: '下一步',
			id: 'nextBt1',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				if (Ext.getCmp('idbankNo').isValid()&Ext.getCmp('compNm').isValid()&Ext.getCmp('compaddr').isValid()&
						Ext.getCmp('licenceNo').isValid()&Ext.getCmp('faxNo').isValid()&Ext.getCmp('manager').isValid()&
						Ext.getCmp('identityNo').isValid()&Ext.getCmp('electrofax').isValid()&Ext.getCmp('idmchtGroupId').isValid()&Ext.getCmp('mchtNm').isValid()&
						Ext.getCmp('addr').isValid()&Ext.getCmp('contact').isValid()&Ext.getCmp('commTel').isValid()&
						Ext.getCmp('finacontact').isValid()&Ext.getCmp('finacommTel').isValid()&Ext.getCmp('busInfo').isValid()/*&
						Ext.getCmp('busArea').isValid()&Ext.getCmp('monaveTrans').isValid()&Ext.getCmp('sigaveTrans').isValid()*/) 
						{
							    Ext.getCmp('baseInfo').hide();
							    Ext.getCmp('manageInfo').hide();
							    Ext.getCmp('businessInfo').hide();
							    Ext.getCmp('settleInfo').show();
							    Ext.getCmp('contdateInfo').show();
								Ext.getCmp('imageInfo').show();
								Ext.getCmp('next1').hide();
								Ext.getCmp('next2').show();
							}
			}
		};
	
	var nextArr1 = new Array();
	nextArr1.push(nextBt1);		// [0]
	
	var checkIds = "T";
	var hasUpload = "0";
	function subSave(){
		var btn = Ext.getCmp('save');		
		var frm = mchntForm.getForm();		
		if (Ext.getCmp('settleAcctNm').isValid()&Ext.getCmp('settleAcct').isValid()&Ext.getCmp('settleAcctConfirm').isValid()&
				Ext.getCmp('settleBankNm').isValid()&Ext.getCmp('openStlno').isValid()&Ext.getCmp('contstartDate').isValid()&Ext.getCmp('contendDate').isValid()) {
			if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
				showErrorMsg('两次输入商户账户账号不一致，请确认！',mchntForm);
			} else {
                var checkStr = Ext.getCmp("openStlno").getValue();
         		T20100.checkCnapsId(checkStr,function(ret){
		    		if(ret=='F'){
             			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
             			Ext.getCmp("openStlno").setValue("");
		    		}else{
						btn.disable();
						frm.submit({
							url: 'T20901Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								checkIds = "T";
		//						showSuccessMsg(action.result.msg,mchntForm);
								btn.enable();
								frm.reset();
								hasUpload = "0";
								resetImagesId();
								storeImg.reload({
								params: {
									start: 0,
									imagesId: imagesId,
									mcht:''
									}
								});
								showSuccessAlert(action.result.msg,mchntForm,250,function(){
										
										window.location.href = Ext.contextPath + '/page/mchnt/T20901.jsp';
								});
							},
							failure : function(form,action) {
								btn.enable();
								if (action.result.msg.substr(0,2) == 'CZ') {
									
									Ext.MessageBox.show({
										msg: action.result.msg.substr(2) + '<br><h1>是否继续保存吗？</h1>',
										title: '确认提示',
										animEl: Ext.get(mchntForm.getEl()),
										buttons: Ext.MessageBox.YESNO,
										icon: Ext.MessageBox.QUESTION,
										modal: true,
										width: 500,
										fn: function(bt) {
											if(bt == 'yes') {
												checkIds = "F";
												subSave();
											}
										}
									});
								} else {
									showErrorMsg(action.result.msg,mchntForm);
								}
							},
							params: {
								txnId: '20901',
								subTxnId: '01',
								checkIds: checkIds,
								hasUpload: hasUpload,
								imagesId: imagesId
		//						clearType: Ext.getCmp('clearType').getGroupValue()
							}
						});
		    		}
		    	});
			}
		}
	}
	
	var nextBt2 = {
			xtype: 'button',
			width: 100,
			text: '上一步',
			id: 'nextBt2',
			height: 30,
			handler: function() {
				var frm = mchntForm.getForm();
//				if (frm.isValid()) {
							    Ext.getCmp('baseInfo').show();
							    Ext.getCmp('manageInfo').show();
							    Ext.getCmp('businessInfo').show();
							    Ext.getCmp('settleInfo').hide();
							    Ext.getCmp('contdateInfo').hide();
								Ext.getCmp('imageInfo').hide();
								Ext.getCmp('next1').show();
								Ext.getCmp('next2').hide();
//							}
			}
		};
	
	var nextBt21 = {
			text: '保存',
            id: 'save',
            width: 100,
            height: 30,
            name: 'save',
			handler: function() {
				subSave();
			}
		};
	
	var nextArr2 = new Array();
	nextArr2.push(nextBt2);
	nextArr2.push(nextBt21);
	

	var mchntForm = new Ext.FormPanel({
		frame: true,
		modal: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
        autoScroll: true,
		title: '商户入网申请',		
		waitMsgTarget: true,
		defaults: {
			bodyStyle: 'padding-left: 10px'
		},
		items: [{
			id: 'baseInfo',
			xtype: 'fieldset',
			title: '注册信息',
			layout: 'column',
			collapsible: true,
//			width: 1035,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},items: [
				{
				columnWidth: .33,
				layout: 'form',
				width: 150,
				items: [{
					xtype: 'basecomboselect',
			        baseParams: 'MCHT_GROUP_FLAG',
					fieldLabel: '商户种类*',
					id: 'idmchtGroupFlag',
					hiddenName: 'tblMchtBaseInfTmpTmp.mchtGroupFlag',
					allowBlank: false,
					width: 150,
					value: '1',
					listeners: {
                    'select': function(){
                     		if(Ext.getCmp("idmchtGroupFlag").getValue() == '1'){
                     			Ext.getCmp('idmchtGroupId').disable();
                     			Ext.getCmp("idmchtGroupId").setValue("-");
                     		}else if(Ext.getCmp("idmchtGroupFlag").getValue() == '2'){
                     			Ext.getCmp('idmchtGroupId').disable();
                     			Ext.getCmp("idmchtGroupId").setValue("-");
                     		}else if(Ext.getCmp("idmchtGroupFlag").getValue() == '3'){
                     			Ext.getCmp('idmchtGroupId').enable();
                     			Ext.getCmp("idmchtGroupId").setValue("");
                     		}                     		
                    	}
                    }
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				width: 150,
				hidden:true,
				items: [{
					xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					fieldLabel: '商户接入方式*',
					id: 'idconnType',
					hiddenName: 'tblMchtBaseInfTmpTmp.connType',
					allowBlank: true,
					width: 150,
					value: 'J'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'dynamicCombo',
					methodName: 'getAreaCode',
					fieldLabel: '所在地区*',
					hiddenName: 'tblMchtBaseInfTmpTmp.areaNo',
					id:'areaNoId',
					allowBlank: true,
//					editable: true,
					/*mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true,*/
					width: 150
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW_BRANCH',
			        id: 'idbankNo',
					hiddenName: 'tblMchtBaseInfTmpTmp.bankNo',
					fieldLabel: '签约机构*',
					allowBlank: false,
					blankText: '请选择签约机构',
					width: 150,
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					typeAhead: true,
					selectOnFocus: true,
					editable: true,
					value:brhId,
					lazyRender: true,
					listeners:{
						'select': function() {
//							SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('idbankNo').value,function(ret){
//							        eposStore.loadData(Ext.decode(ret));
//							    });
                        }
					}
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'basecomboselect',
		        	baseParams: 'MCHNT_ATTR',
					fieldLabel: '商户类型',
					width:150,
					hiddenName: 'tblMchtBaseInfTmpTmp.etpsAttr',
					id:'etpsAttrId',
					listeners : {
						/*'select' : function() {
							var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
							if(etpsAttrTmp == '24')
							{									
								Ext.getCmp('accountHide1').hide();	
								Ext.getCmp('accountHide2').hide();	
								Ext.getCmp('licenceNo').allowBlank = true;
								Ext.getCmp('faxNo').allowBlank = true;
							}else{
								Ext.getCmp('accountHide1').show();	
								Ext.getCmp('accountHide2').show();
								Ext.getCmp('licenceNo').allowBlank = false;
								Ext.getCmp('faxNo').allowBlank = false;
							}
						},
						'change' :function() {
							var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
							if(etpsAttrTmp == '24')
							{
								Ext.getCmp('accountHide1').hide();	
								Ext.getCmp('accountHide2').hide();	
								Ext.getCmp('licenceNo').allowBlank = true;
								Ext.getCmp('faxNo').allowBlank = true;
							}else{
								Ext.getCmp('accountHide1').show();	
								Ext.getCmp('accountHide2').show();
								Ext.getCmp('licenceNo').allowBlank = false;
								Ext.getCmp('faxNo').allowBlank = false;
							}
						}*/
					}
				}]
			},{
        		columnWidth: .66,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_GROUP_NEW',
					fieldLabel: '所属集团商户*',
					id: 'idmchtGroupId',
					hiddenName: 'tblMchtBaseInfTmpTmp.mchtGroupId',
					editable: true,
					disabled: true,
					allowBlank: false,
					anchor: '90%',
					value: '-'
		        }]
			},{
				columnWidth: .66,
				layout: 'form',
				id: 'compNmPanel',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '注册名称*',
					maxLength: '60',
					vtype: 'isOverMax',
					id: 'mchtNm',
					name:'tblMchtBaseInfTmpTmp.mchtNm',
					anchor: '90%'
//					width:476
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'textfield',
					fieldLabel: '英文名称',
					maxLength: 40,
					vtype: 'isOverMax',
					regex: /^\w+[\w\s]+\w+$/,
					regexText:'英文名称必须是字母，如Bill Gates',
					id: 'engName',
					name: 'tblMchtBaseInfTmpTmp.engName',
					width: 150
				}]
			},{				
				columnWidth: .66,
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '注册地址*',
//					width:476,
					anchor: '90%',
					maxLength: 60,
					vtype: 'isOverMax',
					id: 'compaddr',
					name:'tblMchtBaseInfTmpTmp.compaddr'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
//					xtype: 'numberfield',
					xtype: 'textfield',
					fieldLabel: '邮政编码',
					width:150,
					regex: /^[0-9]{6}$/,
					regexText: '邮政编码必须是6位数字',
					id: 'postCode',
					name: 'tblMchtBaseInfTmpTmp.postCode'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide1',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '营业执照编号*',
					width:150,
					maxLength: 20,
					id: 'licenceNo',
					name: 'tblMchtBaseInfTmpTmp.licenceNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide2',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '税务登记证号码*',
					maxLength: 20,
					vtype: 'isOverMax',
					width:150,
					id: 'faxNo',
					name:'tblMchtBaseInfTmpTmp.faxNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
//				labelwidth: 100,
				items: [{
					xtype: 'numberfield',
					fieldLabel: '注册资金',
					regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
					regexText: '注册资金必须是正数，如123.45',
					maxLength: 12,
					vtype: 'isOverMax',
					width:150,
					id: 'busAmt',
					name: 'tblMchtBaseInfTmpTmp.busAmt'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'textfield',
					fieldLabel: '公司网址',
					regex:/^[a-zA-z]+:/,
					regexText:'必须是正确的地址格式，如http://www.xxx.com',
//                    maxLength: 60,
					vtype: 'isOverMax',
					width:150,
					id: 'homePage',
					name: 'tblMchtBaseInfTmpTmp.homePage',
					maxLength: 50
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide3',
//				labelwidth: 100,
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '法人代表*',
					width:150,
					maxLength: 50,
					vtype: 'lengthRange50',
					id: 'manager',
					name:'tblMchtBaseInfTmpTmp.manager'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide4',
				items: [{
					xtype: 'basecomboselect',
		        	baseParams: 'CERTIFICATE',
					fieldLabel: '法人证件类型*',
					width:150,
					allowBlank: false,
					hiddenName: 'tblMchtBaseInfTmpTmp.artifCertifTp',
					id: 'idartifCertifTp',
					value: '01'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'accountHide5',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '法人证件号码*',
					width:150,
					maxLength: 20,
					vtype: 'isOverMax',
					id: 'identityNo',
					name:'tblMchtBaseInfTmpTmp.identityNo'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '企业电话*',
					width:150,
					regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
					regexText:'长度不够，电话必须是7~9位',
					maxLength: 15,
					vtype: 'isOverMax',
					id: 'electrofax',
					name: 'tblMchtBaseInfTmpTmp.electrofax'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
//				hidden:true,
				items: [{
						xtype: 'textfield',
						fieldLabel: '企业传真',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'tblMchtBaseInfTmpTmp.fax'
				}]
			}			
			]},{
				id: 'manageInfo',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '经营信息',
				layout: 'column',
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth: .66,
					layout: 'form',
					id: 'mchtNmPanel',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '经营名称*',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'compNm',
						name:'tblMchtBaseInfTmpTmp.compNm',
						anchor: '90%'
//						width:476
					}]
				},{
	        		columnWidth: .33,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						fieldLabel: '营业开始时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '时间输入错误',
						id: 'openTime',
						name: 'tblMchtBaseInfTmpTmp.openTime',
						value: '00:00'
		        	}]
				},{				
					columnWidth: .66,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '装机地址*',
//						width:476,
						anchor: '90%',
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'addr',
						name:'tblMchtBaseInfTmpTmp.addr'
					}]
				},{
	        		columnWidth: .33,
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						fieldLabel: '营业结束时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '该输入框只能输入数字0-9',
						value: '23:59',
						id: 'closeTime',
						name: 'tblMchtBaseInfTmpTmp.closeTime'
		        	}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '业务联系人*',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contact',
						name:'tblMchtBaseInfTmpTmp.contact'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '业务联系人电话*',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commTel',
						name:'tblMchtBaseInfTmpTmp.commTel'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textfield',
							fieldLabel: '业务联系人邮箱',
							width:150,
							maxLength: 40,
							vtype: 'isOverMax',
							id: 'commEmail',
							name: 'tblMchtBaseInfTmpTmp.commEmail',
							vtype: 'email'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '财务联系人*',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'finacontact',
						name:'tblMchtBaseInfTmpTmp.finacontact'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '财务联系人电话*',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'finacommTel',
						name:'tblMchtBaseInfTmpTmp.finacommTel'
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textfield',
							fieldLabel: '财务联系人邮箱',
							width:150,
							maxLength: 40,
							vtype: 'isOverMax',
							id: 'finacommEmail',
							name: 'tblMchtBaseInfTmpTmp.finacommEmail',
							vtype: 'email'
					}]
				}			
				]
			},{
				id: 'businessInfo',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '营业状况',
				layout: 'column',
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth: .66,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						fieldLabel: '经营内容*',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'busInfo',
						name:'tblMchtBaseInfTmpTmp.busInfo',
						anchor: '90%'
//						width:476
					}]
				},{				
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype: 'textnotnull',
						width: 150,
						fieldLabel: '经营面积(m²)*',
						maxLength: 10,
						vtype: 'isOverMax',
						id: 'busArea',
						name:'tblMchtBaseInfTmpTmp.busArea'
					}]
				}/*,{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: '月平均营业额*',
	                    id: 'monaveTrans',
		                width: 150,
		                hidden:true,
	                    allowBlank: true,
	                    editable: false,
	                    hiddenName: 'tblMchtBaseInfTmpTmp.monaveTrans',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['01','5万元以下'],['02','25万元以下'],['03','50万元以下'],['04','100万元以下'],['05','100万元以上']]
	                    })
	                }]
	            },{
	                columnWidth: .33,
	                layout: 'form',
	                items: [{
	                    xtype: 'combo',
	                    fieldLabel: '单笔平均交易额*',
	                    id: 'sigaveTrans',
		                width: 150,
		                hidden:true,
	                    allowBlank: true,
	                    editable: false,
	                    hiddenName: 'tblMchtBaseInfTmpTmp.sigaveTrans',
	                    store: new Ext.data.ArrayStore({
	                        fields: ['valueField','displayField'],
	                        data: [['01','50元以下'],['02','200元以下'],['03','1000元以下'],['04','5000元以下'],['05','5000元以上']]
	                    })
	                }]
	            }*/
						]
			},{
				xtype: 'fieldset',
				id: 'next1',
				defaults: {
					bodyStyle: 'padding-left: 100px'
				},
//				width: 1035, 
				buttonAlign: 'center',
				buttons: nextArr1
				
			},{
			id: 'settleInfo',
//			width: 1035,
			collapsible: true,
			xtype: 'fieldset',
			title: '账户信息',
			layout: 'column',
			hidden:true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items: [{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '账户户名*',
					width:150,
					maxLength: 80,
					vtype: 'isOverMax',
					id: 'settleAcctNm',
					name:'tblMchtSettleInfTmpTmp.settleAcctNm'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
//					xtype: 'numberfield',
					xtype: 'textfield',
                    fieldLabel: '账户号*',
                    maxLength: 40,
                    allowBlank: false,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
                    width:150,
                    id: 'settleAcct',
                    name: 'tblMchtSettleInfTmpTmp.settleAcct'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
//					xtype: 'numberfield',
					xtype: 'textfield',
                    fieldLabel: '账户号(确认)*',
                    maxLength: 40,
                    allowBlank: false,
					vtype: 'isOverMax',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
                    width:150,
                    id: 'settleAcctConfirm',
//                    name:'tblMchtSettleInfTmpTmp.settleAcctConfirm',
                    listeners: {
                     'change': function(){
                     		if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
                     			showErrorMsg("两次输入商户账户账号不一致，请确认！", mchntForm);
                     			Ext.getCmp("settleAcctConfirm").setValue(""); 
                     		}
                    	}
                    }
				}]
			},{
				columnWidth: .66,
				layout: 'form',
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '账户开户行名称*',
					id: 'settleBankNm',
					name:'tblMchtSettleInfTmpTmp.settleBankNm',
					maxLength: 80,
					vtype: 'isOverMax',
					anchor: '90%'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'basecomboselect',
			        baseParams: 'CERTIFICATE',
					fieldLabel: '证件类型*',
					id: 'certifTypeId',
					hiddenName: 'certifType',
					allowBlank: true,
					width:150,
					value: '01'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'textnotnull',
					fieldLabel: '证件号码*',
					width:150,
					allowBlank : true,
					id: 'certifNoId',
					name:'certifNoId'
				}]
			},{
				columnWidth: .33,
				layout: 'form',
//				hidden:true,
				items: [{
					xtype: 'textfield',
					fieldLabel: '账户开户行号',
					width:150,
					regex: /^[0-9]{12}$/,
					regexText: '请输入12位数字0-9',
//					maskRe: /^[0-9]{12}$/,
//					allowBlank : false,
					id: 'openStlno',
					name:'tblMchtSettleInfTmpTmp.openStlno',
                    listeners: {
                    	'change': function(){
                    		var checkStr = Ext.getCmp("openStlno").getValue();
                     		T20100.checkCnapsId(checkStr,function(ret){
					    		if(ret=='F'){
	                     			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
	                     			Ext.getCmp("openStlno").setValue("");
					    		}
					    	})
                    	}
                    }
				}]
			},{
				columnWidth: .99,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '费率信息',
					emptyText : '请简要描述计费相关信息，30个汉字以内',
					id: 'reserved',
					name: 'tblMchtSettleInfTmpTmp.reserved',
					maxLength: 60,
					vtype: 'isOverMax',
					anchor: '90%'
				}]
			},{
				columnWidth: .55,
				layout: 'form',
				hidden:true,
				items: [{
					xtype: 'radiogroup',
					fieldLabel: '结算账户类型*',
					width:200,
					allowBlank : true,
					blankText:'至少选择一项',
					items: [{
								boxLabel : '对公账户',
								inputValue : '0',
								id: 'clearType',
								name : "clearTypeNm"
							}, {
								boxLabel : '对私账户',
								inputValue : '1',
								name : "clearTypeNm"
					}]
				}]
			}			
			]
		},{
			id: 'contdateInfo',
//			width: 1035,
			collapsible: true,
			xtype: 'fieldset',
			title: '合同有效期',
			layout: 'column',
			hidden:true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items: [{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'datefield',
					id :'contstartDate',
					editable: false,
					width:150,
					name: 'tblMchtBaseInfTmpTmp.contstartDate',
					vtype: 'daterange',
		            fieldLabel: '合同起始日期*',
		            endDateField: 'contendDate',
					blankText: '请选择起始日期',
					allowBlank: false
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'datefield',
					id :'contendDate',
					editable: false,
					width:150,
					name: 'tblMchtBaseInfTmpTmp.contendDate',
					vtype: 'daterange',
		            fieldLabel: '合同终止日期*',
		            startDateField: 'contstartDate',
					blankText: '请选择终止日期',
					allowBlank: false
				}]
			}
					]			
		},{
			id: 'imageInfo',
//			width: 1035,
			collapsible: true,
			xtype: 'fieldset',
			title: '证书影像',
			layout: 'column',
//			autoScroll: true,
			hidden:true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items : dataview,
			tbar: [{
    				xtype: 'button',
					text: '刷新',
					width: '80',
					id: 'view',
					handler:function() {
						storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId,
								mcht:''
							}
						});
					}
				},{
					xtype: 'button',
					width: '80',
					text: '上传',
					id: 'upload',
					handler:function() {
						hasUpload = "1";
						dialog.show();
					}
				}]
		},{	xtype: 'fieldset',
			id: 'next2',
			hidden :true,
			defaults: {
				bodyStyle: 'padding-left: 100px'
			},
//			width: 1035, 
			buttonAlign: 'center',
			buttons: nextArr2		
		}]
	});
	
	// 主界面
	
	new Ext.Viewport({
		layout: 'fit',
		items: [mchntForm]
	});
})