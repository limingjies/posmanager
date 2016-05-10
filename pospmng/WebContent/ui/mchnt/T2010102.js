Ext.onReady(function() {

	var verifySta = "";
	// 保存是否验证成功的变量
	// 修改时默认为true,当信息变动时为false
	var verifyResult = true;
	var verifyResult2 = true;
// alert(mchntId);
	
	// ******************图片处理部分**********开始********
	var storeImg = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
// autoLoad: true
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'id'
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
	 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName='+ mchntId+'/' + rec.fileName;
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
		 		T20100.deleteImgFileTmp(rec.fileName,mchntId,function(ret){
		 			if("S" == ret){
		 				storeImg.reload({
							params: {
								start: 0,
								imagesId: imagesId,
								mcht: mchntId
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
	
	var settleTypeStore1 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','T+0'],['1','T+1']],
		reader: new Ext.data.ArrayReader()
	});
	var settleTypeStore2 = new Ext.data.ArrayStore ({
		fields: ['valueField','displayField'],
		data: [['0','T+0'],['1','T+1'],['N','不适用']],
		reader: new Ext.data.ArrayReader()
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
								'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
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
		uploadUrl : 'T20101Action_upload.asp',
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
		/*exterMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId,
					mcht:mchntId
				}
			});
		},*/
		completeMethod: function() {
			storeImg.reload({
				params: {
					start: 0,
					imagesId: imagesId,
					mcht:mchntId
				}
			});
		},
		postParams: {
			txnId: '20101',
			subTxnId: '06',
			imagesId: imagesId,
			mchtNo: mchntId
		}
	});

	// ******************图片处理部分**********结束********

/*
 * var flagStore = new Ext.data.JsonStore({ fields:
 * ['valueField','displayField'], root: 'data', id: 'valueField' });
 * 
 * SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
 * flagStore.loadData(Ext.decode(ret)); });
 * 
 * var txnStore = new Ext.data.JsonStore({ fields:
 * ['valueField','displayField'], root: 'data', id: 'valueField' });
 * 
 * SelectOptionsDWR.getComboData('TXN_NUM_FEE',function(ret){
 * txnStore.loadData(Ext.decode(ret)); });
 * 
 * var hasSub = false; var fm = Ext.form;
 * 
 * var store = new Ext.data.Store({ proxy: new Ext.data.HttpProxy({ url:
 * 'gridPanelStoreAction.asp?storeId=getDiscInf' }), reader: new
 * Ext.data.JsonReader({ root: 'data', totalProperty: 'totalCount' },[ {name:
 * 'txnNum',mapping: 'txnNum',type:'string'}, {name: 'floorMount',mapping:
 * 'floorMount'}, {name: 'minFee',mapping: 'minFee'}, {name: 'maxFee',mapping:
 * 'maxFee'}, {name: 'flag',mapping: 'flag'}, {name: 'feeValue',mapping:
 * 'feeValue'} ]), sortInfo: {field: 'floorMount', direction: 'ASC'}, autoLoad:
 * false });
 * 
 * var cm = new Ext.grid.ColumnModel({ columns: [{ header: '交易卡种', dataIndex:
 * 'txnNum', width: 120, editor: { xtype: 'basecomboselect', store: txnStore,
 * id: 'idTxnNum', hiddenName: 'txnNum', width: 160 }, renderer:function(data){
 * if(null == data) return ''; var record = txnStore.getById(data); if(null !=
 * record){ return record.data.displayField; }else{ return ''; } } },{ id:
 * 'floorMount', header: '最低交易金额', dataIndex: 'floorMount', width: 80, sortable:
 * true },{ header: '回佣类型', dataIndex: 'flag', width: 90, editor: { xtype:
 * 'basecomboselect', store: flagStore, id: 'idfalg', hiddenName: 'falg', width:
 * 160 }, renderer:function(data){ if(null == data) return ''; var record =
 * flagStore.getById(data); if(null != record){ return record.data.displayField;
 * }else{ return ''; } } },{ header: '回佣值', dataIndex: 'feeValue', width: 70 },{
 * header: '按比最低收费', dataIndex: 'minFee', width: 90 },{ header: '按比最高收费',
 * dataIndex: 'maxFee', width: 90 }] });
 * 
 * var detailGrid = new Ext.grid.GridPanel({ title: '详细信息', frame: true, border:
 * true, height: 230, columnLines: true, autoExpandColumn: 'floorMount',
 * stripeRows: true, store: store, disableSelection: true, cm: cm,
 * forceValidation: true, loadMask: { msg: '正在加载计费算法详细信息列表......' }, bbar: new
 * Ext.PagingToolbar({ store: gridStore, pageSize: System[QUERY_RECORD_COUNT],
 * displayInfo: true, displayMsg: '显示第{0}-{1}条记录，共{2}条记录', emptyMsg:
 * '没有找到符合条件的记录' }) });
 */
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
// function(){
// Ext.MessageBox.alert("提示","您选择的出版号是："+r.data.discCd)
// }
	/*
	 * gridStore.load({ params: { start: 0 } });
	 */
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '计费代码',dataIndex: 'discCd',sortable: true,width: 80},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:100},
		{header: '所属机构',dataIndex: 'discOrg',sortable: true,width:100,renderer:function(val){return getRemoteTrans(val, "brh");}}
	]);
	
	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
// autoLoad: true,
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar: ['计费代码：',{
			xtype: 'textfield',
			id: 'serdiscCd',
			width: 60
		},'-','计费名称：',{
			xtype: 'textfield',
			id: 'serdiscNm',
			width: 110
		},'-',{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'widfalg',
			width: 60,
			autoLoad: true,
			handler: function(){
				gridStore.load();
			},
			listeners: {
			      'select': function() {
				     store.load();
		        },
		        'change': function() {
		            store.load();
		        }
	        }
			// autoLoad: true,
		}]
	});
	
	/*
	 * gridPanel.getStore().on('beforeload',function() {
	 * Ext.MessageBox.alert("提示","您选择的出版号是：5"); Ext.getCmp('setup').disable();
	 * });
	 */
	
	gridPanel.getStore().on('beforeload',function() {
// Ext.MessageBox.alert("提示","您选择的出版号是：5");
// var id = gridPanel.getSelectionModel().getSelected().data.discCd;
// store.load({
// params: {
// start: 0
// // discCd: gridPanel.getSelectionModel().getSelected().data.discCd
// }
// });
		store.removeAll();
		Ext.getCmp('setup').disable();
	});
	
	
	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
// Ext.MessageBox.alert("提示","您选择的出版号是："+r.data.discCd);
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	
	/*
	 * gridStore.on('beforeload', function() { store.removeAll(); });
	 */
	
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

	var hasSub = false;
	var fm = Ext.form;

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
// sortInfo: {field: 'floorMount', direction: 'ASC'},
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
            width: 80,
            sortable: true
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
            width: 70
        },{
            header: '按比最低收费',
            dataIndex: 'minFee',
            width: 90
        },{
            header: '按比最高收费',
            dataIndex: 'maxFee',
            width: 90
        }]
	});

    var detailGrid = new Ext.grid.GridPanel({
		title: '详细信息',
		frame: true,
		border: true,
		height: 230,
		columnLines: true,
		autoExpandColumn: 'floorMount',
		stripeRows: true,
		store: store,
		disableSelection: true,
		cm: cm,
		forceValidation: true,
		loadMask: {
			msg: '正在加载计费算法详细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
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
			{name: 'regAddr',mapping: 'regAddr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'enableDate',mapping: 'enableDate'},
			{name: 'preAudNm',mapping: 'preAudNm'},
			{name: 'confirmNm',mapping: 'confirmNm'},
			{name: 'protocalId',mapping: 'protocalId'},
			{name: 'signInstId',mapping: 'signInstId'},
			{name: 'netNm',mapping: 'netNm'},
			{name: 'agrBr',mapping: 'agrBr'},
// {name: 'netTel',mapping: 'netTel'},
			{name: 'prolDate',mapping: 'prolDate'},
			{name: 'prolTlr',mapping: 'prolTlr'},
			{name: 'closeDate',mapping: 'closeDate'},
			{name: 'closeTlr',mapping: 'closeTlr'},
// {name: 'operNo',mapping: 'operNo'},
// {name: 'operNm',mapping: 'operNm'},
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
			{name: 'settleAcctConfirm',mapping: 'settleAcct'},
			{name: 'certifTypeId',mapping: 'certifTypeId'},
			{name: 'certifNoId',mapping: 'certifNoId'},
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
	
	baseStore.load({
		params: {
			mchntId: mchntId
		},
		callback: function(records, options, success){
			if(success){
				mchntForm.getForm().loadRecord(baseStore.getAt(0));
				mchntForm.getForm().clearInvalid();

				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var mccValue = baseStore.getAt(0).data.mchtGrp;
				var etpsAttrId = baseStore.getAt(0).data.etpsAttr;
				var mchtGrpId = baseStore.getAt(0).data.mchtGrp;
//				var mchtFunction = baseStore.getAt(0).data.mchtFunction;
				
				if(etpsAttrId == '24'){
					Ext.getCmp('licenceNoPID').hide();
					Ext.getCmp('faxNoPID').hide();
					Ext.getCmp('licenceNo').allowBlank = true;
					Ext.getCmp('faxNo').allowBlank = true;
				}
//				baseStore.getAt(0).data.feeRate
				
				gridStore.load({
					params: {
						start: 0,
						discCd: Ext.getCmp('serdiscCd').getValue(),
						discId: baseStore.getAt(0).data.feeRate,
						discNm: Ext.getCmp('serdiscNm').getValue(),
						mccCode: Ext.getCmp('idmcc').getValue()
					}
				});
				store.load({
					params: {
						start: 0,
						discCd: baseStore.getAt(0).data.feeRate
					}
				});
// 				store.load();

// if(mchtGrpId == '0010') {
// Ext.getCmp('settleAcctMidID').hide();
// Ext.getCmp('settleAcctMid').allowBlank = true;
// Ext.getCmp('verifyInButtonID').hide();
// Ext.getCmp('verifyInStaID').hide();
// }
// if(mccValue == '0003') {
// Ext.getCmp('licenceNoPID').hide();
// Ext.getCmp('faxNoPID').hide();
// Ext.getCmp('managerPID').hide();
// Ext.getCmp('artifCertifTpPID').hide();
// Ext.getCmp('identityNoPID').hide();
// } else {
// Ext.getCmp('licenceNoPID').show();
// Ext.getCmp('faxNoPID').show();
// Ext.getCmp('managerPID').show();
// Ext.getCmp('artifCertifTpPID').show();
// Ext.getCmp('identityNoPID').show();
// }
				
				if(mccValue == '0003') {
					Ext.getCmp('licenceNoPID').hide();	
					Ext.getCmp('faxNoPID').hide();	
					Ext.getCmp('managerPID').hide();
					Ext.getCmp('artifCertifTpPID').hide();
					Ext.getCmp('identityNoPID').hide();
					Ext.getCmp('licenceNo').allowBlank = true;
					Ext.getCmp('faxNo').allowBlank = true;
					Ext.getCmp('manager').allowBlank = true;
					Ext.getCmp('idartifCertifTp').allowBlank = true;
					Ext.getCmp('identityNo').allowBlank = true;
				} else {
					Ext.getCmp('licenceNoPID').show();	
					Ext.getCmp('faxNoPID').show();	
					Ext.getCmp('managerPID').show();
					Ext.getCmp('artifCertifTpPID').show();
					Ext.getCmp('identityNoPID').show();
					Ext.getCmp('licenceNo').allowBlank = false;
					Ext.getCmp('faxNo').allowBlank = false;
					Ext.getCmp('manager').allowBlank = false;
					Ext.getCmp('idartifCertifTp').allowBlank = false;
					Ext.getCmp('identityNo').allowBlank = false;
				}
				
				if(baseStore.getAt(0).data.feeRate!=0){
					Ext.getCmp('discCode').setValue(baseStore.getAt(0).data.feeRate);
				}
				
				imagesId = baseStore.getAt(0).data.mchtNo;

				SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
					mchntMccStore.loadData(Ext.decode(ret));
					Ext.getCmp('idmcc').setValue(baseStore.getAt(0).data.mcc);
				});
				storeImg.reload({
					params: {
						start: 0,
						imagesId: imagesId,
						mcht: mchntId
					}
				});
// mchntForm.getForm().findField('clearTypeNm').setValue(settleAcct.substring(0,1));
				mchntForm.getForm().findField('settleAcct').setValue(settleAcct.substring(1));
				mchntForm.getForm().findField('settleAcctConfirm').setValue(settleAcct.substring(1));
				mchntForm.getForm().findField('clearType').setValue(settleAcct.substring(0,1));
				mchntForm.getForm().findField('tccId').setValue(baseStore.getAt(0).data.tcc);
				mchntForm.getForm().findField('mchtFunctionId').setValue(baseStore.getAt(0).data.mchtFunction);
			
				/*if(mchtFunction!=null && '0' == mchtFunction.substring(1, 2)) {
					Ext.getCmp('mchtFunction').setValue('0');
				}else{
					Ext.getCmp('mchtFunction').setValue('1');
				}*/
				
				if(baseStore.getAt(0).data.openTime == ''
					||baseStore.getAt(0).data.openTime == null)
					mchntForm.getForm().findField('openTime').setValue("00:00");
				if(baseStore.getAt(0).data.closeTime == ''
					||baseStore.getAt(0).data.closeTime == null)
					mchntForm.getForm().findField('closeTime').setValue("23:59");
				
				//将数据库中保存的4位营业时间加载为带有：的4位时间格式（HH：MM）
				var OT = baseStore.getAt(0).data.openTime;
				var CT = baseStore.getAt(0).data.closeTime; 
				if(null != OT & '' != OT) {
					Ext.getCmp('openTime').setValue(OT.substring(0,2) + ':' + OT.substring(2,4));
				}
				if(null != CT & '' != CT) {
					Ext.getCmp('closeTime').setValue(CT.substring(0,2) + ':' + CT.substring(2,4));
				}
				
				var flag = mchntForm.getForm().findField('idmchtGroupFlag').getValue();
				
				// 财务POS商户受控
				if(baseStore.getAt(0).data.mchtGroupFlag == '3'){
					mchntForm.getForm().findField("clearType").readOnly=true;
				}
				

                if(baseStore.getAt(0).data.mchtMngMode == '1'){
                    Ext.getCmp('openTime').allowBlank = false;
                    Ext.getCmp('closeTime').allowBlank = false;
                }else{
                    Ext.getCmp('openTime').allowBlank = true;
                    Ext.getCmp('closeTime').allowBlank = true;
                }
			}else{
				showErrorMsg("加载商户信息失败，请返回刷新数据后重试",mchntForm,function(){
					window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
				});
			}
		}
	});

	// 重置账号验证
	function resetVerify(){
		verifyResult = false;
	    Ext.getCmp('verifySta').setValue('<font color="red">未验证</font>');
	}
	
	function resetInVerify() {
		verifyResult2 = false;
	    Ext.getCmp('verifyInSta').setValue('<font color="red">未验证</font>');
	}
	
	var hasSub = false;

	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var mchntForm = new Ext.FormPanel({
        title: '修改商户信息[商户编号：'+mchntId+']' ,
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		waitMsgTarget: true,
		labelAlign: 'left',
		autoScroll  : true,
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{  // 修改操作该字段不可修改
			        xtype: 'combofordispaly',
			        baseParams: 'MCHT_GROUP_FLAG',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户种类*',
					id: 'idmchtGroupFlag',
					hiddenName: 'mchtGroupFlag'
		        }]
        	},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户连接方式*',
					id: 'idconnType',
					hiddenName: 'connType',
					allowBlank: false,
					anchor: '90%'
				}]
        	},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户编号',
					id: 'mchtNo',
					name: 'mchtNo',
					disabled: true,
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
		        layout: 'form',
		        xtype: 'panel',
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW_BRANCH',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '签约机构*',
					allowBlank: false,
					blankText: '请选择签约机构',
					id: 'idbankNo',
					hiddenName: 'bankNo',
					anchor: '90%'
		        }]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '中文名称*',
					maxLength: 60,
					vtype: 'isOverMax',
					id: 'mchtNm',
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '中文名称简写*',
						maxLength: 40,
						vtype: 'isOverMax',
						allowBlank: false,
						blankText: '请输入中文名称简写',
						id: 'mchtCnAbbr',
						name: 'mchtCnAbbr',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{ // 修改操作该字段不可修改
						xtype: 'combofordispaly',
						labelStyle: 'padding-left: 5px',
			        	baseParams: 'CITY_CODE',
						fieldLabel: '所在地区',
						hiddenName: 'areaNo'
					}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
// readOnly: true,
// disabled: true,
						anchor: '90%',
						id:'mchtGrpId',
						listeners: {
							'select': function() {
								mchntMccStore.removeAll();
								var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
								mchntMccStore.loadData(Ext.decode(ret));
// if(mchtGrpId == '0010'){
// Ext.getCmp('settleAcctMidID').hide();
// Ext.getCmp('settleAcctMid').allowBlank = true;
// Ext.getCmp('verifyInButtonID').hide();
// Ext.getCmp('verifyInStaID').hide();
// }
								});
								
								if(this.value == '0003') {
									Ext.getCmp('licenceNoPID').hide();	
									Ext.getCmp('faxNoPID').hide();	
									Ext.getCmp('managerPID').hide();
									Ext.getCmp('artifCertifTpPID').hide();
									Ext.getCmp('identityNoPID').hide();
									Ext.getCmp('licenceNo').allowBlank = true;
									Ext.getCmp('faxNo').allowBlank = true;
									Ext.getCmp('manager').allowBlank = true;
									Ext.getCmp('idartifCertifTp').allowBlank = true;
									Ext.getCmp('identityNo').allowBlank = true;
								} else {
									Ext.getCmp('licenceNoPID').show();	
									Ext.getCmp('faxNoPID').show();	
									Ext.getCmp('managerPID').show();
									Ext.getCmp('artifCertifTpPID').show();
									Ext.getCmp('identityNoPID').show();
									Ext.getCmp('licenceNo').allowBlank = false;
									Ext.getCmp('faxNo').allowBlank = false;
									Ext.getCmp('manager').allowBlank = false;
									Ext.getCmp('idartifCertifTp').allowBlank = false;
									Ext.getCmp('identityNo').allowBlank = false;
								}
							},
							'change': function() {
								mchntMccStore.removeAll();
								var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
								mchntMccStore.loadData(Ext.decode(ret));
// if(mchtGrpId == '0010'){
// Ext.getCmp('settleAcctMidID').hide();
// Ext.getCmp('settleAcctMid').allowBlank = true;
// Ext.getCmp('verifyInButtonID').hide();
// Ext.getCmp('verifyInStaID').hide();
//									
// }else{
// Ext.getCmp('settleAcctMidID').show();
// Ext.getCmp('settleAcctMid').allowBlank = false;
// Ext.getCmp('verifyInButtonID').show();
// Ext.getCmp('verifyInStaID').show();
// }
								});
							}
						}
		        	}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'basecomboselect',
					labelStyle: 'padding-left: 5px',
					baseParams: 'RISL_LVL',
					fieldLabel: '商户风险级别*',
					id:'rislLvlId',
					hiddenName: 'rislLvl',
					allowBlank: false,
					editable: false,
					anchor: '90%'
				}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	       		items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '英文名称',
						maxLength: 40,
						vtype: 'isOverMax',
						regex: /^\w+[\w\s]+\w+$/,
						regexText:'英文名称必须是字母，如Bill Gates',
						id: 'engName',
						name: 'engName',
						anchor: '90%'
		        	}]
			},{
	        	columnWidth: .66,
		        layout: 'form',
	       		items: [{
			        	xtype: 'combo',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户MCC*',
						store: mchntMccStore,
						displayField: 'displayField',
						valueField: 'valueField',
						mode: 'local',
						triggerAction: 'all',
						forceSelection: true,
						typeAhead: true,
						selectOnFocus: true,
						editable: true,
						allowBlank: false,
						lazyRender: true,
						blankText: '请选择商户MCC',
						id: 'idmcc',
// readOnly: true,
						hiddenName: 'mcc',
						anchor: '94.8%',
						listeners: {
						      'select': function() {
							     gridStore.load();
// store.load();
							     Ext.getCmp('discCode').setValue('');
					        },
					        'change': function() {
					        	gridStore.load();
// store.load();
							    Ext.getCmp('discCode').setValue('');
					        }
				        }
		        	}]
			},{
        		columnWidth: .33,
	        	layout: 'form',
       			items: [new Ext.form.RadioGroup({
					labelStyle: 'padding-left: 5px',
					fieldLabel: '是否标准入网*',
					allowBlank : false,
					id: 'tccId',
					blankText:'至少选择一项',
					anchor: '90%',
					items: [{
						boxLabel : '是',
						inputValue : '0',
						name : 'tcc'
					}, {
						boxLabel : '否',
						inputValue : '1',
						name : 'tcc'
					}]
				})]
			},{
        		columnWidth: .33,
	        	layout: 'form',
       			items: [new Ext.form.RadioGroup({
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户业务类型*',
					allowBlank : false,
					id: 'mchtFunctionId',
					blankText:'至少选择一项',
					anchor: '90%',
					items: [{
						boxLabel : 'T+0业务',
						inputValue : '0',
						name : 'mchtFunction'
					}, {
						boxLabel : 'T+1业务',
						inputValue : '1',
						name : 'mchtFunction'
					}]
				})]
			},{
				columnWidth: .1,
				xtype: 'panel',
				html:'<br/>'
			}]
        },{
        	xtype: 'tabpanel',
        	id: 'tab',
        	frame: true,
            plain: false,
            activeTab: 0,
            height: 350,
            deferredRender: false,
            enableTabScroll: true,
            labelWidth: 150,
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
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						width:150,
						maxLength: 20,
// vtype: 'isNumber',
						id: 'licenceNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'faxNoPID',
	       			items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码*',
						width:150,
						id: 'faxNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_ATTR',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户类型',
						width:150,
						hiddenName: 'etpsAttr',
						id:'etpsAttrId',
						listeners : {
							'select' : function() {
								var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
								if(etpsAttrTmp == '24')
								{									
									Ext.getCmp('licenceNoPID').hide();	
									Ext.getCmp('faxNoPID').hide();	
									Ext.getCmp('faxNo').allowBlank = true;
									Ext.getCmp('licenceNo').allowBlank = true;
								}
							},
							'change' :function() {
								var etpsAttrTmp = Ext.getCmp('etpsAttrId').getValue();
								if(etpsAttrTmp == '24')
								{
									Ext.getCmp('faxNoPID').hide();	
									Ext.getCmp('licenceNoPID').hide();	
									Ext.getCmp('faxNo').allowBlank = true;
									Ext.getCmp('licenceNo').allowBlank = true;
								}else{
									Ext.getCmp('faxNoPID').show();	
									Ext.getCmp('licenceNoPID').show();
									Ext.getCmp('faxNo').allowBlank = false;
									Ext.getCmp('licenceNo').allowBlank = false;
								}
							}
						}
		        	}]
				},
// {
// columnWidth: .5,
// xtype: 'panel',
// layout: 'form',
// items: [{
// xtype: 'combofordispaly',
// baseParams: 'MCHT_CRE_LVL',
// labelStyle: 'padding-left: 5px',
// fieldLabel: '商户等级',
// width:150,
// id: 'mchtCreLvl',
// hiddenName: 'mchtCreLvl'
// }]
// },
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '电子邮件',
						width:150,
						id: 'commEmail',
						name: 'commEmail',
						vtype: 'email',
						maxLength: 50,
						maxlength: '电子邮件输入内容长度超出限制'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公司网址',
						regex:/^[a-zA-z]+:/,
						regexText:'必须是正确的地址格式，如http://www.xxx.com',
						width:150,
						id: 'homePage',
						name: 'homePage',
						maxLength: 50
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户地址*',
						width:150,
						maxLength: 80,
						vtype: 'isOverMax',
						id: 'addr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '邮政编码',
						width:150,
						regex: /^[0-9]{6}$/,
						regexText: '邮政编码必须是6位数字',
						id: 'postCode',
						name: 'postCode'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'managerPID',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表*',
						width:150,
						maxLength: 50,
						vtype: 'lengthRange50',
						id: 'manager'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
						xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册资金',
						width:150,
						id: 'busAmt',
						name: 'busAmt'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'artifCertifTpPID',
	       			items: [{
			        	xtype: 'basecomboselect',
			        	baseParams: 'CERTIFICATE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件类型*',
						width:150,
						allowBlank: false,
						hiddenName: 'artifCertifTp',
						id: 'idartifCertifTp'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'identityNoPID',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件号码*',
						width:150,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人姓名*',
						width:150,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'contact'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人电话*',
						width:150,
						maxLength: 14,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						vtype: 'isOverMax',
						id: 'commTel'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业电话*',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'electrofax',
						name: 'electrofax'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '企业传真',
						width:150,
						maxLength: 20,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						vtype: 'isOverMax',
						id: 'fax',
						name: 'fax'
		        	}]
				},{//附件上传按钮
					columnWidth: .1,
    				xtype: 'button',
					text: '上传附件',
					width: '80',
					id: 'view1',
					handler:function() {
						dialog.show();
					}
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持无磁无密交易',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持人工授权',
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
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否支持折扣消费',
						id: 'discConsFlg',
						name: 'discConsFlg'
		        	}]
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
			        	width: 280,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册地址',
						id: 'regAddr',
						name: 'regAddr'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
						fieldLabel: '经营单位',
						width:280,
						id: 'prolTlr',
						name: 'prolTlr'
					},{
				        xtype: 'basecomboselect',
				        baseParams: 'BRH_BELOW_ID',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '签约网点*',
						allowBlank: false,
						editable: true,
						blankText: '请选择签约网点',
						id: 'idagrBr',
						hiddenName: 'agrBr',
						anchor: '90%'
		        	},{
						fieldLabel: '协议编号',
						width:150,
						id: 'protocalId',
						name: 'protocalId'
		        	}/*
						 * ,{ xtype: 'textnotnull', fieldLabel: '客户经理工号*',
						 * width:150, id: 'operNo', name: 'operNo', maxLength:
						 * 8, vtype: 'isOverMax' }
						 */]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	defaults: {
	        			xtype: 'textfield',
	        			labelStyle: 'padding-left: 5px'
	        		},
	       			items: [{
	       				xtype: 'textfield',
						fieldLabel: '批准人',
						maxLength: 40,
						vtype: 'isOverMax',
						width:150,
						id: 'confirmNm',
						name: 'confirmNm'
					},{
                        xtype: 'basecomboselect',
						fieldLabel: '受理机构标示码*',
						baseParams: 'CUP_BRH_BELOW',
						labelStyle: 'padding-left: 5px',
						allowBlank: false,
						hiddenName: 'signInstId',
						anchor: '80%'
			        }/*
						 * ,{ fieldLabel: '客户经理电话', width:150, regex:
						 * /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						 * id: 'netTel', name: 'netTel' },{ xtype:
						 * 'textnotnull', fieldLabel: '客户经理姓名*', width:150,
						 * maxLength: 10, vtype: 'isOverMax', id: 'operNm',
						 * name: 'operNm' }
						 */]
				},{
	        		columnWidth: 1,
	       			xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'checkbox',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '是否仅营业时间内交易',
						id: 'mchtMngMode',
						name: 'mchtMngMode',
						listeners: {
		                    'check':function(r,c){
		                    	if(r.getValue()==1){
		                    		Ext.getCmp('openTime').allowBlank = false;
		                    		Ext.getCmp('closeTime').allowBlank = false;
		                    	}else{
		                    		Ext.getCmp('openTime').allowBlank = true;
		                    		Ext.getCmp('closeTime').allowBlank = true;
		                    	}
		                    }
	                    }
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业开始时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '时间输入错误',
						id: 'openTime',
						name: 'openTime',
    					anchor: '55%'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业结束时间(hh:mm)',
                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '该输入框只能输入数字0-9',
						id: 'closeTime',
						name: 'closeTime',
    					anchor: '55%'
		        	}]
				},{//附件上传按钮
					columnWidth: .1,
    				xtype: 'button',
					text: '上传附件',
					width: '80',
					id: 'view2',
					handler:function() {
						dialog.show();
					}
            }]
			},{
                title:'清算信息',
                layout:'column',
                id: 'settle',
                frame: true,
                items: [
                {
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户账号*',
                        maxLength: 40,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        id: 'settleAcct'
                    }]
				} , {
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户开户行号*',
						allowBlank: false,
						width:150,
						regex: /^[0-9]{12}$/,
						regexText: '请输入12位数字0-9',
//						maxLength: 12,
//						minLength: 12,
//						allowBlank : false,
//						id: 'settleBankNo'，
						id: 'openStlno',
                        listeners: {
                        	'change': function(){
                        		var checkStr = Ext.getCmp("openStlno").getValue();
	                     		T20100.checkCnapsId(checkStr,function(ret){
						    		if(ret=='F'){
		                     			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
		                     			Ext.getCmp("openStlno").setValue(baseStore.getAt(0).data.openStlno);
										Ext.getCmp("tab").setActiveTab("settle");
						    		}
						    	})
	                    	}
	                    }
		        	}]
				} , {
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
                        xtype: 'textnotnull',
                        labelStyle: 'padding-left: 5px',
                        fieldLabel: '商户账户账号(确认)*',
                        maxLength: 40,
						vtype: 'isOverMax',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
                        width:150,
                        id: 'settleAcctConfirm',
                        listeners: {
	                     'change': function(){
	                     		if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
	                     			showErrorMsg("两次输入商户账户账号不一致，请确认！", mchntForm);
	                     			Ext.getCmp("settleAcctConfirm").setValue(""); 
	                     		}
	                    	}
	                    }
                    }]
//				} , {
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//	       				xtype: 'basecomboselect',
//				        baseParams: 'CERTIFICATE',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '证件类型*',
//						id: 'certifTypeId',
//						hiddenName: 'certifType',
//						allowBlank: false,
//						width:150,
//						value: '01'
//		        	}]
//				}, {
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//	       				xtype: 'textnotnull',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '证件号码*',
//						width:150,
//						allowBlank: false,
//						id: 'certifNoId'
//		        	}]
				} , {
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户开户行名称*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleBankNm'
		        	}]
				} , 
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户账户户名*',
						maxLength: 80,
						vtype: 'isOverMax',
						width:150,
						id: 'settleAcctNm'
		        	}]
				} ,	{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [
	       				new Ext.form.RadioGroup({
							fieldLabel: '商户结算账户类型*', // ['0','本行对公账户'],['1','本行对私账户或单位卡']
							labelStyle: 'padding-left: 5px',
							width:300,
							allowBlank : false,
					        id: 'clearType',
					        blankText:'至少选择一项',
							items: [{
								boxLabel : '对公账户',
								inputValue : '0',
								name : "clearTypeNm"
							}, {
								boxLabel : '对私账户',
								inputValue : '1',
								name : "clearTypeNm"
							}]
						})
		        	]
				},{//附件上传按钮
					columnWidth: .1,
    				xtype: 'button',
					text: '上传附件',
					width: '80',
					id: 'view3',
					handler:function() {
						dialog.show();
					}
            }]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
                autoScroll: true,
                items: [{
                	region: 'north',
                	height: 50,
                	layout: 'column',
                	items: [{
		        		xtype: 'panel',
		        		layout: 'form',
                		labelWidth: 70,
                		columnWidth: .2,
	       				items: [{
	       					xtype: 'textnotnull',
	       					fieldLabel: '计费代码*',
							id: 'discCode',
							name: 'discCode',
							readOnly: true
						}]
					},{
		        		xtype: 'panel',
		        		columnWidth: .1,
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'recover',
							text:'重置',
							id: 'resetbu',
							width: 60,
							handler: function(){
								Ext.getCmp('discCode').reset();
							}
                		}]
					},{
		        		xtype: 'panel',
		        		columnWidth: .15,
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'accept',
							text:'设为该商户计费算法',
							id: 'setup',
							width: 60,
							disabled: true,
							handler: function(){
								Ext.getCmp('discCode').setValue(gridPanel.getSelectionModel().getSelected().data.discCd);
							}
                		}]
					},{
		        		xtype: 'panel',
		        		columnWidth: .1,
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
                	width:600,
                	items: [detailGrid]
                }]
		    },{
				title:'证书影像',
                id: 'images',
                frame: true,
                border: true,
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
								imagesId: imagesId,
								mcht: mchntId
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
		    }]
        }],
        buttons: [{
            text: '保存',
            id: 'save',
            name: 'save',
            handler : function() {
				subSave();
            }
        },{
            text: '返回',
            handler: function() {
            	window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
			}
        }]
    });
	var checkIds = "T";
    function subSave(){
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		hasSub = true;
		if (frm.isValid()) {
// var flag = mchntForm.getForm().findField('clearType').getValue();
			if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
				showErrorMsg('两次输入商户账户账号不一致，请确认！',mchntForm);
			}else{
				// 营业时间判断
				var transTime = mchntForm.getForm().findField('mchtMngMode').getValue();
				if(transTime){
					var open = mchntForm.getForm().findField('openTime').getValue();
					var close = mchntForm.getForm().findField('closeTime').getValue();
					if(open==''||close==''){
						showErrorMsg('已勾选仅营业时间内交易时,请输入商户营业开始时间及结束时间!',mchntForm);
						Ext.getCmp("tab").setActiveTab("append");
						return;
					}
					if(open==close){
						showErrorMsg('已勾选仅营业时间内交易时,营业开始时间和结束时间不能相同!',mchntForm);
						Ext.getCmp("tab").setActiveTab("append");
						return;
					}
					if(Number(open)>Number(close)){
						showErrorMsg('已勾选仅营业时间内交易时,商户营业开始时间不能在营业结束时间之后!',mchntForm);
						Ext.getCmp("tab").setActiveTab("append");
						return;
					}
					if(close=='000000'){
						showErrorMsg('已勾选仅营业时间内交易时,营业结束时间不能为000000(最晚235959)!',mchntForm);
						Ext.getCmp("tab").setActiveTab("append");
						return;
					}
				}
				
				var checkStr = Ext.getCmp("openStlno").getValue();
	     		T20100.checkCnapsId(checkStr,function(ret){
		    		if(ret=='F'){
	         			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
//	         			Ext.getCmp("openStlno").setValue(baseStore.getAt(0).data.openStlno);
						Ext.getCmp("tab").setActiveTab("settle");
						return;
		    		}else{
						btn.disable();
						frm.submitNeedAuthorise({
							url: 'T20101Action_update.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								hasSub = false;
								showSuccessAlert(action.result.msg,mchntForm,250,function(){
									window.location.href = Ext.contextPath + '/page/mchnt/T20101.jsp';
									});
							},
							failure : function(form,action) {
								btn.enable();
								hasSub = false;
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
								txnId: '20101',
								subTxnId: '01',
								mchtNo: mchntId,
								checkIds: checkIds,
								clearType: Ext.getCmp('clearType').getValue().inputValue
		// settleAcctNm: Ext.getCmp('settleAcctNm').getValue(),
		// settleBankNo: Ext.getCmp('settleBankNo').getValue()
							}
					});
	    		}
	    	});
		}
	}else{
		// 自动切换到未通过验证的tab
		var finded = true;
		frm.items.each(function(f){
    		if(finded && !f.validate()){
    			var tab = f.ownerCt.ownerCt.id;
    			var tab2 = f.ownerCt.ownerCt.ownerCt.id;
    			if(tab2 == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab2);
    			}
    			if(tab == 'basic' || tab == 'append' || tab == 'settle' || tab == 'feeamt'){
    				 Ext.getCmp("tab").setActiveTab(tab);
    			}
    			finded = false;
    		}
    	});
	}
    }
    
    
    // 为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    });

	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});

	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue(),
			mccCode: Ext.getCmp('idmcc').getValue()
		});
	});

//	store.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			start: 0,
//			discCd: Ext.getCmp('discCode').getValue()
//		});
//				
//	});
	// 移除主界面初始化图层
	var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 1

		});
	};
	hideMainUIMask.defer(1000);
});