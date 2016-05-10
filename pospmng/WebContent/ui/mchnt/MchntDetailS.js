

//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){
	function checkImageInfo(upload){
		var storeImg1 = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
//				idProperty: 'id'
			},[
				{name: 'id',mapping: 'id'},
				{name: 'btBig',mapping: 'btBig'},
				{name: 'btDel',mapping: 'btDel'},
				{name: 'width',mapping: 'width'},
				{name: 'height',mapping: 'height'},
				{name: 'fileName',mapping: 'fileName'}
			])
		});
		storeImg1.removeAll();
		storeImg1.reload({
			params: {
				start: 0,
				imagesId: mchntId,
				mcht : mchntId,
				upload:upload
			}
		});
		var dataview1 = new Ext.DataView({
			frame: true,
	        store: storeImg1,
	        tpl  : new Ext.XTemplate(
	        		'<ul>',
		            '<tpl for=".">',
		                '<li class="phone">',
		                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
		                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
									'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
								'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
							'</div>',
		                '</li>',
		            '</tpl>',
		        '</ul>'
	        ),
	        id: 'phones1',
	        itemSelector: 'li.phone',
	        overClass   : 'phone-hover',
	        singleSelect: true,
	        multiSelect : true,
	        autoScroll  : true
	    });
		var mchntForm = new Ext.FormPanel({
			region: 'center',
			iconCls: 'mchnt',
			frame: true,
			html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
			//height:430,
//			modal: true,
			height: Ext.getBody().getHeight(true),
	        width: Ext.getBody().getWidth(true),
	        autoScroll: true,
//			title: '商户入网申请',
			
			waitMsgTarget: true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items: [{
				id: 'imageInfo',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '影像信息',
				layout: 'column',
//				autoScroll: true,
				hidden:false,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items : dataview1,
				tbar: [{
	    				xtype: 'button',
						text: '刷新',
						width: '80',
						id: 'view1',
						handler:function() {
							storeImg1.reload({
								params: {
									start: 0,
									imagesId: mchntId,
									mcht : mchntId,
									upload:upload
								}
							});
						}
					},{}]
			}]
		});
		function showPIC(store,id){
			detailWin.close();
	 		var rec = store.getAt(id.substr(5,1)).data;
	 		custom.resizeTo(rec.width, rec.height);
	 		var src = document.getElementById('showBigPic').src;

	 		if(src.indexOf(rec.fileName) == -1){
		 		document.getElementById('showBigPic').src = "";
		 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName='+ mchntId+'/'+ rec.fileName;
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
		storeImg1.on('load',function(){
			for(var i=0;i<storeImg1.getCount();i++){
				var rec = storeImg1.getAt(i).data;
		    	Ext.get(rec.btBig).on('click', function(obj,val){
		    		showPIC(storeImg1,val.id);
		    	});
			}
		});
		 var detailWin = new Ext.Window({
		    	title: '商户影像信息',
				initHidden: true,
				header: true,
				frame: true,
				modal: true,
				width: 1000,
				height:350,
				 autoScroll: true,
//				autoHeight: 680,
				items: [mchntForm],
				buttonAlign: 'center',
//				closable: false,
				closeAction: 'close',
				closable: true,
				resizable: false
		    });
		 detailWin.show();
	}
	
	//图片显示Store
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
	//图像显示dataview
	var dataview = new Ext.DataView({
		frame: true,
	    store: storeImg,
	    tpl  : new Ext.XTemplate(
	        '<ul>',
	            '<tpl for=".">',
	                '<li class="phone">',
	                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
	                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
							'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
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

	// ******************拒绝原因**********开始********
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=lastMchntRefuseInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnTime',mapping: 'txnTime'},
			{name: 'mchntId',mapping: 'mchntId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'refuseType',mapping: 'refuseType'},
			{name: 'refuseInfo',mapping: 'refuseInfo'}
		])
	});
	
	reasonStore.load({
		params: {
			start: 0,
			queryMchtNo:mchntId
		},callback : function(records, options, success) {
			if (success) {
				if (reasonStore.getAt(0) == null || reasonStore.getAt(0).data.refuseInfo == '') {
					Ext.getCmp('refuseReason').hide();
				} else {
					Ext.getCmp('refuseReason').show();
					Ext.getCmp('refuseReasonTxt').setText(reasonStore.getAt(0).data.refuseInfo);
				}
			}else{
				
			}
		}
	});
	// ******************拒绝原因**********结束********
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getMchntInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'manuAuthFlag',mapping: 'manuAuthFlag'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'discConsFlg',mapping: 'discConsFlg'},
			{name: 'discConsRebate',mapping: 'discConsRebate'},
			{name: 'passFlag',mapping: 'passFlag'},
			{name: 'openDays',mapping: 'openDays'},
			{name: 'sleepDays',mapping: 'sleepDays'},
			{name: 'mchtCnAbbr',mapping: 'mchtCnAbbr'},
			{name: 'spellName',mapping: 'spellName'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mchtEnAbbr',mapping: 'mchtEnAbbr'},
			{name: 'areaNo',mapping: 'areaNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'homePage',mapping: 'homePage'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'tcc',mapping: 'tcc'},
			{name: 'etpsAttr',mapping: 'etpsAttr'},
			{name: 'mngMchtId',mapping: 'mngMchtId'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mchtAttr',mapping: 'mchtAttr'},
			{name: 'mchtGroupFlag',mapping: 'mchtGroupFlag'},
			{name: 'mchtGroupId',mapping: 'mchtGroupId'},
			{name: 'mchtEngNm',mapping: 'mchtEngNm'},
			{name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
			{name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'psamNum',mapping: 'psamNum'},
			{name: 'cdMacNum',mapping: 'cdMacNum'},
			{name: 'posNum',mapping: 'posNum'},
			{name: 'connType',mapping: 'connType'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'mchtFunction',mapping: 'mchtFunction'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'licenceEndDate',mapping: 'licenceEndDate'},
			{name: 'bankLicenceNo',mapping: 'bankLicenceNo'},
			{name: 'busType',mapping: 'busType'},
			{name: 'faxNo',mapping: 'faxNo'},
			{name: 'busAmt',mapping: 'busAmt'},
			{name: 'mchtCreLvl',mapping: 'mchtCreLvl'},
			{name: 'contact',mapping: 'contact'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'commMobil',mapping: 'commMobil'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'manager',mapping: 'manager'},
			{name: 'artifCertifTp',mapping: 'artifCertifTp'},
			{name: 'identityNo',mapping: 'identityNo'},
			{name: 'managerTel',mapping: 'managerTel'},
			{name: 'fax',mapping: 'fax'},
			{name: 'electrofax',mapping: 'electrofax'},
			{name: 'compaddr',mapping: 'compaddr'},
			{name: 'compNm',mapping: 'compNm'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'enableDate',mapping: 'enableDate'},
			{name: 'preAudNm',mapping: 'preAudNm'},
			{name: 'confirmNm',mapping: 'confirmNm'},
			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
			{name: 'netTel',mapping: 'netTel'},
			{name: 'prolDate',mapping: 'prolDate'},
			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
			{name: 'operNo',mapping: 'operNo'},
			{name: 'operNm',mapping: 'operNm'},
			{name: 'procFlag',mapping: 'procFlag'},
			{name: 'setCur',mapping: 'setCur'},
	      	{name: 'printInstId',mapping: 'printInstId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'acqBkName',mapping: 'acqBkName'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'orgnNo',mapping: 'orgnNo'},
			{name: 'subbrhNo',mapping: 'subbrhNo'},
			{name: 'subbrhNm',mapping: 'subbrhNm'},
			{name: 'openTime',mapping: 'openTime'},
			{name: 'closeTime',mapping: 'closeTime'},
			{name: 'visActFlg',mapping: 'visActFlg'},
			{name: 'visMchtId',mapping: 'visMchtId'},
			{name: 'mstActFlg',mapping: 'mstActFlg'},
			{name: 'mstMchtId',mapping: 'mstMchtId'},
			{name: 'amxActFlg',mapping: 'amxActFlg'},
			{name: 'amxMchtId',mapping: 'amxMchtId'},
			{name: 'dnrActFlg',mapping: 'dnrActFlg'},
			{name: 'dnrMchtId',mapping: 'dnrMchtId'},
			{name: 'jcbActFlg',mapping: 'jcbActFlg'},
			{name: 'jcbMchtId',mapping: 'jcbMchtId'},
			{name: 'cupMchtFlg',mapping: 'cupMchtFlg'},
			{name: 'debMchtFlg',mapping: 'debMchtFlg'},
			{name: 'creMchtFlg',mapping: 'creMchtFlg'},
			{name: 'cdcMchtFlg',mapping: 'cdcMchtFlg'},
			{name: 'idOtherNo',mapping: 'idOtherNo'},
			{name: 'recUpdTs',mapping: 'recUpdTs'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'internalNo',mapping: 'internalNo'},
			{name: 'reject',mapping: 'reject'},
			{name: 'mchntAttr',mapping: 'mchntAttr'},
			{name: 'linkPer',mapping: 'linkPer'},
			{name: 'SettleAreaNo',mapping: 'SettleAreaNo'},
			{name: 'MainTlr',mapping: 'MainTlr'},
			{name: 'CheckTlr',mapping: 'CheckTlr'},
			{name: 'settleType',mapping: 'settleType'},
			{name: 'rateFlag',mapping: 'rateFlag'},
			{name: 'settleChn',mapping: 'settleChn'},
			{name: 'batTime',mapping: 'batTime'},
			{name: 'autoStlFlg',mapping: 'autoStlFlg'},
			{name: 'partNum',mapping: 'partNum'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeFixed',mapping: 'feeFixed'},
			{name: 'feeMaxAmt',mapping: 'feeMaxAmt'},
			{name: 'feeMinAmt',mapping: 'feeMinAmt'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'feeDiv1',mapping: 'feeDiv1'},
			{name: 'feeDiv2',mapping: 'feeDiv2'},
			{name: 'feeDiv3',mapping: 'feeDiv3'},
			{name: 'settleMode',mapping: 'settleMode'},
			{name: 'feeCycle',mapping: 'feeCycle'},
			{name: 'settleRpt',mapping: 'settleRpt'},
			{name: 'settleBankNo',mapping: 'settleBankNo'},
			{name: 'settleBankNm',mapping: 'settleBankNm'},
			{name: 'settleAcctNm',mapping: 'settleAcctNm'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'settleAcctMid',mapping: 'settleAcctMid'},
			{name: 'settleAcctMidNm',mapping: 'settleAcctMidNm'},
			{name: 'settleAcctMidBank',mapping: 'settleAcctMidBank'},
			{name: 'clearType',mapping: 'clearType'},
			{name: 'feeAcctNm',mapping: 'feeAcctNm'},
			{name: 'feeAcct',mapping: 'feeAcct'},
			{name: 'groupFlag',mapping: 'groupFlag'},
			{name: 'openStlno',mapping: 'openStlno'},
			{name: 'changeStlno',mapping: 'changeStlno'},
			{name: 'speSettleTp',mapping: 'speSettleTp'},
			{name: 'speSettleLv',mapping: 'speSettleLv'},
			{name: 'speSettleDs',mapping: 'speSettleDs'},
			{name: 'feeBackFlg',mapping: 'feeBackFlg'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'mchtMngMode',mapping: 'mchtMngMode'}
		]),
		autoLoad: false
	});

	var fm = Ext.form;

	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});

	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});


	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('feeTxnNum',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('feeCardType',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'floorMount',mapping: 'floorMount'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'cardType',mapping: 'cardType'}
		]),
//		sortInfo: {field: 'floorMount', direction: 'ASC'},
		autoLoad: false
	});

	var cm = new Ext.grid.ColumnModel({
		columns: [{
            header: '卡类型',
            dataIndex: 'cardType',
            width: 70,
            editor: {
					xtype: 'basecomboselect',
			        store: cardTypeStore,
					id: 'idCardType',
					hiddenName: 'cardType',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '交易类型',
            dataIndex: 'txnNum',
            width: 70,
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            id: 'floorMount',
            header: '最低交易金额',
            dataIndex: 'floorMount',
            width: 100
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 90,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idfalg',
					hiddenName: 'falg',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '回佣值',
            dataIndex: 'feeValue',
            width: 50
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 85
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 85
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		autoWidth: true,
		frame: true,
		border: true,
		height: 210,
		columnLines: true,
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true
	});
    var custom;
	var customEl;
	var rollTimes=0; //向上滚加1，向下滚减1
	var originalWidth =0; //图片缩放前的原始宽度
	var originalHeight = 0; //图片缩放前的原始高度
	function showPIC(store,id){
 		var rec = store.getAt(id.substr(5,1)).data;
 		custom.resizeTo(rec.width, rec.height);
 		var src = document.getElementById('showBigPic').src;

 		if(src.indexOf(rec.fileName) == -1){
	 		document.getElementById('showBigPic').src = "";
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName='+ mchntId+'/'+ rec.fileName;
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

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		])
	});

	gridStore.on('load',function(){
		store.load({
			params: {
				start: 0,
				discCd: baseStore.getAt(0).data.feeRate,
				mccCode: baseStore.getAt(0).data.mcc
			}
		});
	});
	//差分信息
	var diffStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtInfDiff'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'col1',mapping: 'col1'},
			{name: 'col2',mapping: 'col2'},
			{name: 'col3',mapping: 'col3'},
			{name: 'col4',mapping: 'col4'},
			{name: 'col5',mapping: 'col5'},
			{name: 'col6',mapping: 'col6'},
			{name: 'col7',mapping: 'col7'},
			{name: 'col8',mapping: 'col8'},
			{name: 'col9',mapping: 'col9'},
			{name: 'col10',mapping: 'col10'},
			{name: 'col11',mapping: 'col11'},
			{name: 'col12',mapping: 'col12'},
			{name: 'col13',mapping: 'col13'},
			{name: 'col14',mapping: 'col14'},
			{name: 'col15',mapping: 'col15'},
			{name: 'col16',mapping: 'col16'},
			{name: 'col17',mapping: 'col17'},
			{name: 'col18',mapping: 'col18'},
			{name: 'col19',mapping: 'col19'},
			{name: 'col20',mapping: 'col20'},
			{name: 'col21',mapping: 'col21'},
			{name: 'col22',mapping: 'col22'},
			{name: 'col23',mapping: 'col23'},
			{name: 'col24',mapping: 'col24'},
			{name: 'col25',mapping: 'col25'},
			{name: 'col26',mapping: 'col26'},
			{name: 'col27',mapping: 'col27'},
			{name: 'col28',mapping: 'col28'},
			{name: 'col29',mapping: 'col29'},
			{name: 'col30',mapping: 'col30'},
			{name: 'col31',mapping: 'col31'},
			{name: 'col32',mapping: 'col32'},
			{name: 'col33',mapping: 'col33'},
			{name: 'col34',mapping: 'col34'},
			{name: 'col35',mapping: 'col35'},
			{name: 'col36',mapping: 'col36'},
			{name: 'col37',mapping: 'col37'}
		])
	});

	var diffGridColumnModel = new Ext.grid.ColumnModel([
		{header: '',dataIndex: 'col1',width: 50},
		{header: '商户类别',dataIndex: 'col2',width: 80},
		{header: '集团商户',dataIndex: 'col3',width: 80},
		{header: '中文名称',dataIndex: 'col4',width: 100},
		{header: '中文名称简写',dataIndex: 'col5',width: 80},
		{header: '英文名称',dataIndex: 'col6',width: 80},
		{header: '签约机构',dataIndex: 'col7',width: 80},
		{header: '商户组别',dataIndex: 'col8',width: 80},
		{header: '风险级别',dataIndex: 'col9',width: 80},
		{header: '所在地区',dataIndex: 'col10',width: 80},
		{header: 'MCC',dataIndex: 'col11',width: 80},
		{header: '是否标准入网',dataIndex: 'col12',width: 80},
		{header: '业务类型',dataIndex: 'col13',width: 80},
		{header: '营业执照号码',dataIndex: 'col14',width: 80},
		{header: '税务登记证号码',dataIndex: 'col15',width: 80},
		{header: '商户类型',dataIndex: 'col16',width: 80},
		{header: '电子邮件',dataIndex: 'col17',width: 80},
		{header: '公司网址',dataIndex: 'col18',width: 80},
		{header: '商户地址',dataIndex: 'col19',width: 80},
		{header: '邮政编码',dataIndex: 'col20',width: 80},
		{header: '法人代表',dataIndex: 'col21',width: 80},
		{header: '注册资金',dataIndex: 'col22',width: 80},
		{header: '法人代表证件类型',dataIndex: 'col23',width: 80},
		{header: '法人代表证件号码',dataIndex: 'col24',width: 80},
		{header: '联系人姓名',dataIndex: 'col25',width: 80},
		{header: '联系人电话',dataIndex: 'col26',width: 80},
		{header: '企业传真',dataIndex: 'col27',width: 80},
		{header: '注册地址',dataIndex: 'col28',width: 80},
		{header: '经营单位',dataIndex: 'col29',width: 80},
		{header: '营业开始时间',dataIndex: 'col30',width: 80},
		{header: '营业结束时间',dataIndex: 'col31',width: 80},
		{header: '商户结算账户类型',dataIndex: 'col32',width: 80},
		{header: '商户账户开户行号',dataIndex: 'col33',width: 80},
		{header: '商户账户开户行名称',dataIndex: 'col34',width: 80},
		{header: '商户结算帐户开户行号',dataIndex: 'col35',width: 80},
		{header: '商户账户户名',dataIndex: 'col36',width: 80},
		{header: '手续费类型',dataIndex: 'col37',width: 80}
	]);
	var gridColumnModel = new Ext.grid.ColumnModel([
		{header: '计费代码',dataIndex: 'discCd',width: 80},
		{header: '计费名称',dataIndex: 'discNm',id:'discNm',width:170},
		{header: '所属机构',dataIndex: 'discOrg',width:80,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 210,
		autoWidth: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		disableSelection: true,
		cm: gridColumnModel,
		forceValidation: true
	});

	var diffPanel = new Ext.grid.GridPanel({
		id:'diffP',
		title: '修改信息',
		frame: true,
		border: true,
		height: 180,
		width : 930,
		columnLines: true,
		stripeRows: true,
		store: diffStore,
		cm: diffGridColumnModel,
		autoScroll: true,
		viewConfig : {
			forceFit : false,
			autoFill : false
		}
	,tbar: [{
		xtype: 'button',
		text: '',
		name: 'formateCol',
		id: 'formateCol',
		width: 1,
		handler:function() {
		}
	}]
	});
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
			columnWidth : .99,
			xtype : 'panel',
			id:'refuseReason',
			layout : 'form',
			labelWidth: 0,
			hide: true,
			width: 500,
			items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
		        	xtype: 'label',
					id: 'refuseReasonTxt',
					style:'color:red;font-weight:bold'
				}]
			}]},{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'MCHT_GROUP_FLAG',
					fieldLabel: '商户种类',
					id: 'idmchtGroupFlag',
					hiddenName: 'mchtGroupFlag'
		        }]
        	},{
        		columnWidth: .4,
            	layout: 'form',
        		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'CONN_TYPE',
					fieldLabel: '商户类型',
					hiddenName: 'connType'
		        	}
        		]
//        	},{
//	        	columnWidth: .33,
//	        	id: 'otherNoPanel',
//	        	xtype: 'panel',
//	        	layout: 'form',
//	       		items: [{
//	       			xtype: 'displayfield',
//					fieldLabel: '映射商户号',
//					vtype: 'isOverMax',
//					id: 'idOtherNo',
//					anchor: '90%'
//				}]
			},{
        		columnWidth: .33,
        		xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'MCHT_GROUP_NEW',
					fieldLabel: '集团商户',
					id: 'idmchtGroupId',
					hiddenName: 'mchtGroupId',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'displayfield',
					fieldLabel: '中文名称',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '中文名称简写',
						id: 'mchtCnAbbr',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'displayfield',
						fieldLabel: '英文名称',
						id: 'engName',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        xtype: 'combofordispaly',
			        baseParams: 'BRH_BELOW_BRANCH',
					fieldLabel: '签约机构',
					hiddenName: 'bankNo',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'combofordispaly',
			        	baseParams: 'MCHNT_GRP_ALL',
						fieldLabel: '商户组别',
						hiddenName: 'mchtGrp',
						anchor: '90%'						
		        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'combofordispaly',
			        	baseParams: 'RISK_LVL',
						fieldLabel: '商户风险级别',
						hiddenName: 'rislLvl',
						anchor: '90%'						
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'combofordispaly',
			        	baseParams: 'CITY_CODE',
						fieldLabel: '所在地区',
						hiddenName: 'areaNo',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
			        	xtype: 'combofordispaly',
						fieldLabel: '商户MCC',
						store: mchntMccStore,
						hiddenName: 'mcc',
						anchor: '90%'
		        	}]
			},{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '是否标准入网',
					displayField: 'displayField',
					valueField: 'valueField',
                    id: 'tccId',
                    hiddenName: 'tcc',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','是'],['1','否']]
                    })
                }]
			},
			{
        		columnWidth: .33,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '商户业务类型',
					displayField: 'displayField',
					valueField: 'valueField',
                    id: 'mchtFunctionId',
                    hiddenName: 'mchtFunction',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','T+0业务'],['1','T+1业务']]
                    })
                }]
			}
			]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 320,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 120,
        	items:[{
                title:'基本信息',
                id: 'basic',
                frame: true,
				layout:'column',
                items: [{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'licenceNoPID',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						id: 'licenceNo'
		        	}]
				},{
					//columnWidth: .2,
					xtype: 'button',
					title:'点击查看营业执照影像',
					width: '60',
					text: '查看照片',
					id: 'click1',
					handler:function() {
						var upload='upload1';
						checkImageInfo(upload);
					}
				},{

					width: '15',
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: '0001',
	       			items: [{
						xtype: 'displayfield',
						id: '0002'
		        	}]
				
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'faxNoPID',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						id: 'faxNo'
		        	}]
				},{
					//columnWidth: .2,
					xtype: 'button',
					title:'点击查看营业税务登记影像',
					width: '60',
					text: '查看照片',
					id: 'click2',
					handler:function() {
						var upload='upload2';
						checkImageInfo(upload);
					}
				},{
					columnWidth: .6,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						id :'licenceEndDate',
						editable: false,
						width:150,
						name: 'licenceEndDate',
						//vtype: 'daterange',
			            fieldLabel: '营业执照有效期',
			            //endDateField: 'contendDate',
//			            value:nowDate,
//			            minValue:nowDate,
//						blankText: '请选择起始日期',
						allowBlank: true
					}]											
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'MCHNT_ATTR',
						fieldLabel: '商户类型',
						hiddenName: 'etpsAttr'
		        	}]
//				},
//				{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						fieldLabel: '电子邮件',
//						id: 'commEmail',
//						name: 'commEmail'
//		        	}]
//				},{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						fieldLabel: '公司网址',
//						id: 'homePage',
//						name: 'homePage'
//		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '装机地址',
						id: 'addr',
						name: 'addr'
		        	}]
				},{
					//columnWidth: .2,
					xtype: 'button',
					title:'点击查看门店影像',
					width: '60',
					text: '查看照片',
					id: 'click3',
					handler:function() {
						var upload='upload4';
						checkImageInfo(upload);
					}
				},{

					width: '15',
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: '0005',
	       			items: [{
						xtype: 'displayfield',
						id: '0006'
		        	}]
				
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '邮政编码',
						id: 'postCode',
						name: 'postCode'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'managerPID',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人代表',
						id: 'manager',
						name: 'manager'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '注册资金',
						id: 'busAmt',
						name: 'busAmt'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'artifCertifTpPID',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'CERTIFICATE',
						fieldLabel: '法人代表证件类型',
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'identityNoPID',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '法人证件号码',
						id: 'identityNo',
						name: 'identityNo'
		        	}]
				},{
					//columnWidth: .2,
					xtype: 'button',
					title:'点击查看法人证件影像',
					width: '60',
					text: '查看照片',
					id: 'click4',
					handler:function() {
						var upload='upload3';
						checkImageInfo(upload);
					}
				},{

					width: '15',
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: '0003',
	       			items: [{
						xtype: 'displayfield',
						id: '0004'
		        	}]
				
				},{
					columnWidth: .6,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						id :'prolDate',
						//editable: false,
						width:150,
						name: 'prolDate',
						//vtype: 'daterange',
			            fieldLabel: '证件有效期至',
			            //endDateField: 'contendDate',
			            //value:nowDate,
			            //minValue:nowDate,
						//blankText: '请选择起始日期',
						//allowBlank: false
					}]
				
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人姓名',
						id: 'contact',
						name: 'contact'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '联系人电话',
						id: 'commTel',
						name: 'commTel'
		        	}]
				},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '企业电话',
						id: 'electrofax',
						name: 'electrofax'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '企业传真',
						id: 'fax',
						name: 'fax'
		        	}]
				}]
            },{
                title:'附加信息',
                layout:'column',
                id: 'append',
                frame: true,
                items: [{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持无磁无密交易',
						disabled: true,
						id: 'passFlag',
						name: 'passFlag'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持人工授权',
						disabled: true,
						id: 'manuAuthFlag',
						name: 'manuAuthFlag'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否支持折扣消费',
						disabled: true,
						id: 'discConsFlg',
						name: 'discConsFlg'
		        	}]
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
			        	width: 380,
						fieldLabel: '注册地址',
						id: 'compaddr',
						name: 'compaddr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield'
	        		},
	       			items: [{
						fieldLabel: '经营名称',
						id: 'compNm',
						name: 'compNm'
					},{
			    	    xtype: 'combofordispaly',
			        	baseParams: 'BRH_BELOW_ID',
						fieldLabel: '签约网点',
						hiddenName: 'agrBr'
//		        	},{
//						fieldLabel: '协议编号',
//						id: 'protocalId',
//						name: 'protocalId'
//		        	},{
//						fieldLabel: '客户经理工号',
//						id: 'operNo',
//						name: 'operNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'displayfield'
	        		},
	       			items: [{
	       				xtype: 'combofordispaly',
	       				baseParams: 'OPR_ID',
						fieldLabel: '初审人',
						hiddenName: 'updOprId'
							
					},{
						fieldLabel: '批准人',
						id: 'confirmNm',
						name: 'confirmNm'
					},{
				        xtype: 'combofordispaly',
				        baseParams: 'CUP_BRH_BELOW',
						fieldLabel: '受理机构标示码',
						hiddenName: 'signInstId'
//		       		},{
//		        		
////						fieldLabel: '客户经理电话',
////						id: 'netTel',
////						name: 'netTel'
////		        	},{
////						fieldLabel: '客户经理姓名',
////						id: 'operNm',
////						name: 'operNm'
		        	}]
				},{
	        		columnWidth: 1,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						fieldLabel: '是否仅营业时间内交易',
						disabled: true,
						id: 'mchtMngMode',
						name: 'mchtMngMode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
		        		fieldLabel: '营业开始时间(hh:mm)',
						id: 'openTime',
						name: 'openTime'
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						fieldLabel: '营业结束时间(hh:mm)',
						id: 'closeTime',
						name: 'closeTime'
					}]
				}]
			},{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                readOnly: true,
                frame: true,
                items: [
//                        {
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'combofordispaly',
//			        	baseParams: 'SETTLE_TYPE',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '商户结算周期',
//						allowBlank: false,
//						hiddenName: 'settleType',
//						width: 165,
//						anchor: '55%'
//		        	}]
//				},
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '结算账户类型',
						width:200,
						id: 'settleType1'
					}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						allowBlank: true,
						fieldLabel: '账户开户行号',
						width:200,
						id: 'openStlno'
						
		        	}]
				},{
				columnWidth: .5,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '账户开户行名称*',
					maxLength: 80,
					vtype: 'isOverMax',
					width:150,
					id: 'settleBankNm'
       			}]
			},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户户名',
						maxLength: 80,
						vtype: 'isOverMax',
						width:200,
						id: 'settleAcctNm'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'displayfield',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '账户账号',
                        maxLength: 40,
						vtype: 'isOverMax',
                        width:200,
                        id: 'settleAcct'
                    }]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否自动清算',
						disabled: true,
						id: 'autoStlFlg',
						name: 'autoStlFlg'
		        	}]
				},{
	        		columnWidth: .5,
	       			xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '退货返还手续费',
						disabled: true,
						id: 'feeBackFlg',
						name: 'feeBackFlg'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费类型',
						width:150,
						id: 'speSettleTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费档次',
						width:150,
						id: 'speSettleLv'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '特殊计费描述',
						width:150,
						id: 'speSettleDs'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden: true,
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '入账凭单打印机构',
						width:150,
						id: 'printInstId',
						name: 'printInstId'
		        	}]
				}]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
	       				items: [{
	       					xtype: 'displayfield',
	       					fieldLabel: '计费代码',
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
	       					xtype: 'button',
							iconCls: 'detail',
							text:'计费算法配置说明',
							id: 'detailbu',
							width: 60,
							handler: function(){
								Ext.MessageBox.show({
									msg: '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>' +
											'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
											'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
											'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
											'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。',
									title: '计费算法配置说明',
									animEl: Ext.get(mchntForm.getEl()),
									buttons: Ext.MessageBox.OK,
									modal: true,
									width: 650
								});
							}
                		}]
                	}]
                },{
                	region: 'center',
                	items:[gridPanel]
                },{
                	region: 'east',
//                	width:600,
                	items: [detailGrid]
                }]
		    },{
				title:'其他影像 ',
                id: 'images',
                frame: true,
                autoScroll: true,
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
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload'
							}
						});
					}
				}]
		    },{
				title:'修改信息',
                id: 'diffs',
                frame: true,
                autoScroll: false,
                items: [diffPanel]
		    }]
        },{html:'<br>'},{ 
        	xtype: 'panel',
        	layout: 'column',
        	items:[{xtype: 'label',width: 280,html:'&nbsp;&nbsp;&nbsp;'},
        	       {
//        		xtype: 'panel',
//        		layout: 'column',
//    				items: [{
    			    xtype: 'button',
    				text:'通过',
    				id: 'detailbu1',
    				width: 100,
    				height:30,
    				handler: function() {
    					showConfirm('确认审核通过吗？',mchntForm,function(bt) {
    						if(bt == 'yes') {
    							showProcessMsg('正在提交审核信息，请稍后......');
    							//rec = mchntForm.getSelectionModel().getSelected();
    							Ext.Ajax.request({
    								url: 'T20201Action.asp?method=accept',
    								params: {
    									mchntId: mchntId,
    									txnId: '20201',
    									subTxnId: '01'
    								},
    								success: function(rsp,opt) {
    									var rspObj = Ext.decode(rsp.responseText);
    									if(rspObj.success) {
    										showSuccessMsg(rspObj.msg,mchntForm);
    										
    										// 重新加载商户待审核信息
    										El.getStore().reload();
    										//延时关闭页面
    										setTimeout(function(){detailWin.close();},2000);
    									} else {
    										showErrorMsg(rspObj.msg,mchntForm);
    									}
    								}
    							});
    							hideProcessMsg();
    						}
    					});
    				 }
//    			  }
//        	   ]
        	},  {
//        		xtype: 'panel',
//        		layout: 'column',
//    				items: [{
    			    xtype: 'button',
    				text:'退回',
    				id: 'detailbu2',
    				width: 100,
    				height:30,
    				handler: function() {
    					showConfirm('确认退回吗？',mchntForm,function(bt) {
    						if(bt == 'yes') {
    							showInputMsg('提示','请输入退回原因',true,back);
    						}
    					});
    					// 退回按钮执行的方法
    					function back(bt,text) {
    						if(bt == 'ok') {
    							if(getLength(text) > 60 || getLength(text) == 0 || text.trim() == 0) {
    								alert('提交退回原因不能为空且最多可以输入30个汉字或60个字母、数字!');
    								showInputMsg('提示','请输入退回原因',true,back);
    								return;
    							}
    							showProcessMsg('正在审核信息，请稍后......');
    							//rec = mchntGrid.getSelectionModel().getSelected();
    							Ext.Ajax.request({
    								url: 'T20201Action.asp?method=back',
    								params: {
    									mchntId: mchntId,
    									txnId: '20201',
    									subTxnId: '02',
    									refuseInfo: text
    								},
    								success: function(rsp,opt) {
    									var rspObj = Ext.decode(rsp.responseText);
    									if(rspObj.success) {
    										showSuccessMsg(rspObj.msg,mchntForm);
    										
    										// 重新加载商户待审核信息
    										El.getStore().reload();
    										//延时关闭页面
    										setTimeout(function(){detailWin.close();},2000);
    									} else {
    										showErrorMsg(rspObj.msg,mchntForm);
    									}
    								}
    							});
    							hideProcessMsg();
    						}
    					}
    				}
//    			  }
//        	   ] 		
        	},  {
//        		xtype: 'panel',
//        		layout: 'column',
//    				items: [{
    			    xtype: 'button',
    				text:'拒绝',
    				buttonAlign: 'center',
    				id: 'detailbu3',
    				width: 100,
    				height:30,
    				handler: function() {
    					showConfirm('确认拒绝吗？',mchntForm,function(bt) {
    						if(bt == 'yes') {
    							showInputMsg('提示','请输入拒绝原因',true,refuse);
    						}
    					});
    					// 拒绝按钮调用方法
    					function refuse(bt,text) {
    						if(bt == 'ok') {
    							if(getLength(text) > 60 || getLength(text) == 0 || text.trim() == 0) {
    								alert('提交拒绝原因不能为空且最多可以输入30个汉字或60个字母、数字!');
    								showInputMsg('提示','请输入拒绝原因',true,refuse);
    								return;
    							}
    				             showProcessMsg('正在审核信息，请稍后......');
//    							rec = mchntGrid.getSelectionModel().getSelected();
    							Ext.Ajax.request({
    								url: 'T20201Action.asp?method=refuse',
    								params: {
    									mchntId: mchntId,
    									txnId: '20201',
    									subTxnId: '03',
    									refuseInfo: text
    								},
    								success: function(rsp,opt) {
    									var rspObj = Ext.decode(rsp.responseText);
    									if(rspObj.success) {
    										showSuccessMsg(rspObj.msg,mchntForm);
    										
    										// 重新加载商户待审核信息
    										El.getStore().reload();
    										//延时关闭页面
    										setTimeout(function(){detailWin.close();},2000);
    										
    									} else {
    										showErrorMsg(rspObj.msg,mchntForm);
    									}
    								}
    							});
    							hideProcessMsg();
    						}
    					}
    					
    				}
//    			  }
//        	   ]	
        	},{
				xtype : 'button',
				text : '关闭',
				id : 'closeBut',
				width : 100,
				height : 30,
				handler : function() {
					detailWin.close();
				}
			}] //items表示指定布局内的表单组件集合，在此有三个 
        	}
       ]
    });

    var detailWin = new Ext.Window({
    	title: '商户详细信息',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1080,
		height:800,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		resizable: false
    });

	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				//将商户种类不为集团子商户的所属集团设为无
				if(baseStore.getAt(0).data.mchtGroupId == '-'){
					Ext.getCmp('idmchtGroupId').setValue('无');
				}else{
					Ext.getCmp('idmchtGroupId').setValue(baseStore.getAt(0).data.mchtGroupId);
				}
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();
				var discCode = baseStore.getAt(0).data.feeRate;
				Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var mccValue = baseStore.getAt(0).data.mchtGrp;
				var etpsAttrId = baseStore.getAt(0).data.etpsAttr;
				var mchtGrpId = baseStore.getAt(0).data.mchtGrp;
//				var mchtFunction = baseStore.getAt(0).data.mchtFunction;
				
				if(etpsAttrId == '24'){
					Ext.getCmp('licenceNoPID').hide();
					Ext.getCmp('faxNoPID').hide();
				} 
//				if(mccValue == '0004') {
//					Ext.getCmp('licenceNoPID').hide();
//					Ext.getCmp('faxNoPID').hide();
//					Ext.getCmp('managerPID').hide();
//					Ext.getCmp('artifCertifTpPID').hide();
//					Ext.getCmp('identityNoPID').hide();
//				} else {
//					Ext.getCmp('licenceNoPID').show();
//					Ext.getCmp('faxNoPID').show();
//					Ext.getCmp('managerPID').show();
//					Ext.getCmp('artifCertifTpPID').show();
//					Ext.getCmp('identityNoPID').show();
//				}
				if('0' == settleAcct.substring(0,1)) {
					Ext.getCmp('settleType1').setValue('对公账户');
//					Ext.getCmp('settleAcct').setValue(settleAcct.substring(1,settleAcct.lenght));
				}else if('1' == settleAcct.substring(0,1)){
					Ext.getCmp('settleType1').setValue('对私账户');
//					Ext.getCmp('settleAcct').setValue(settleAcct.substring(1,settleAcct.lenght));
				}/*else if('O'== settleAcct.substring(0,1)) {
					Ext.getCmp('settleType1').setValue('他行对公账户');
				}else if('S'== settleAcct.substring(0,1)) {
					Ext.getCmp('settleType1').setValue('他行对私账户');
				}*/
				Ext.getCmp('settleAcct').setValue(settleAcct.substring(1,settleAcct.lenght));
//				Ext.getCmp('clearType').setValue(settleAcct.substring(0,1));
//	            Ext.getCmp('feeSelfBLId').setValue(baseStore.getAt(0).data.feeRate);
				
				/*if(mchtFunction!=null && '0' == mchtFunction) {
					Ext.getCmp('mchtFunction').setValue('T+0业务');
				}else{
					Ext.getCmp('mchtFunction').setValue('T+1业务');
				}*/
				//将数据库中的8位日期变成带分割符的日期来表示
				var licenceEndDate = baseStore.getAt(0).data.licenceEndDate;
				var prolDate = baseStore.getAt(0).data.prolDate;
				if (licenceEndDate != null && licenceEndDate != '' && licenceEndDate.length == 8) {
					var tmpStr = licenceEndDate.substr(0,4) + '-' + licenceEndDate.substr(4,2) + '-' + licenceEndDate.substr(6,2);	
					Ext.getCmp('licenceEndDate').setValue(tmpStr);
				}
				if (prolDate != null && prolDate != '' && prolDate.length == 8) {
					var tmpStr = prolDate.substr(0,4) + '-' + prolDate.substr(4,2) + '-' + prolDate.substr(6,2);	
					Ext.getCmp('prolDate').setValue(tmpStr);
				}
				//将数据库中保存的4位营业时间加载为带有：的4位时间格式（HH：MM）
				var OT = baseStore.getAt(0).data.openTime;
				var CT = baseStore.getAt(0).data.closeTime; 
				if(null != OT & '' != OT) {
					Ext.getCmp('openTime').setValue(OT.substring(0,2) + ':' + OT.substring(2,4));
				}
				if(null != CT & '' != CT) {
					Ext.getCmp('closeTime').setValue(CT.substring(0,2) + ':' + CT.substring(2,4));
				}
				
				
				detailWin.setTitle('商户详细信息[商户编号：' + mchntId + ']');
				detailWin.show();

				gridStore.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate,
						mccCode: baseStore.getAt(0).data.mcc
					}
				});
				store.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});
				diffStore.load({
					params: {
						start: 0,
						mchntId: mchntId
					}
				});
				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',baseStore.getAt(0).data.mchtGrp,function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					mchntForm.getForm().findField('mcc').setValue(baseStore.getAt(0).data.mcc);
				});
				custom = new Ext.Resizable('showBigPic', {
					    wrap:true,
					    pinned:true,
					    minWidth: 50,
					    minHeight: 50,
					    preserveRatio: true,
					    dynamic:true,
					    handles: 'all',
					    draggable:true
					});
				customEl = custom.getEl();
				customEl.on('dblclick', function(){
					customEl.puff();
					rollTimes =0;
				});
				customEl.hide(true);


				storeImg.on('load',function(){
					for(var i=0;i<storeImg.getCount();i++){
						var rec = storeImg.getAt(i).data;
				    	Ext.get(rec.btBig).on('click', function(obj,val){
				    		showPIC(storeImg,val.id);
				    	});
					}
				});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: mchntId,
						mcht:mchntId,
						upload:'upload'
					}
				});
			}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});
}