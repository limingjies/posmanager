Ext.onReady(function() {

	var verifySta = "";
	
	SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP','----',function(ret){
		mchntMccStore.loadData(Ext.decode(ret));
	});
	
	//保存是否验证成功的变量
	var verifyResult = false;
	var verifyResult2 = false;
	
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
		uploadUrl : 'T20100Action_upload.asp',
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
			txnId: '20101',
			subTxnId: '06',
			imagesId: imagesId
		}
	});

	//******************图片处理部分**********结束********



    //******************计费算法部分**********开始********
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
	var hasUpload = "0";
	var fm = Ext.form;

	var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'//详细信息
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
		height: 250,
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
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});

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
	gridStore.load({
		params: {
			start: 0
		}
	});
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
		height: 250,
		columnLines: true,
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
			handler: function(){
				gridStore.load();
			}
		}]
	});
	gridPanel.getStore().on('beforeload',function() {
		Ext.getCmp('setup').disable();
	});

	gridPanel.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		Ext.getCmp('setup').enable();
		var id = gridPanel.getSelectionModel().getSelected().data.discCd;
		store.load({
			params: {
				start: 0,
				discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				}
			});
	});
	gridStore.on('beforeload', function() {
		store.removeAll();
	});
	 //******************计费算法部分**********结束********

//***********************终端信息部分**********开始********
// 要求在新增商户的时候，要同时可以新增多台终端  -----张宁

    // 终端类型数据集
    var termTypeStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('TERM_TYPE',function(ret){
        termTypeStore.loadData(Ext.decode(ret));
    });
    //专业服务机构
    var organStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('ORGAN',function(ret){
        organStore.loadData(Ext.decode(ret));
    });
    //EPOS版本号
    var eposStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION','',function(ret){
		eposStore.loadData(Ext.decode(ret));
	});
    // 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	//每一条终端信息就是一个Record，然后放在store中
	//这里的name属性主要用于 termCreate.get(name) 或 termCreate.set(name, value)
	var termCreate = Ext.data.Record.create([
	{
        name: 'termID', //终端临时编号
        type: 'string'
    }, {
        name: 'txn22New', //商户名
        type: 'string'
    }, {
        name: 'txn27New', //商户英文名
        type: 'string'
    }, {
        name: 'termMccNew', //终端MCC码
        type: 'string'
    }, {
        name: 'brhIdNew', //终端所属机构
        type: 'string'
    }, {
        name: 'contTelNew', //联系电话
        type: 'string'
    }, {
        name: 'propTpNew', //产权属性
        type: 'string'
    }, {
        name: 'propInsNmNew', //收单服务机构
        type: 'string'
    }, {
        name: 'termPara1New', //第三方分成比例
        type: 'string'
    }, {
        name: 'connectModeNew', //连接类型
        type: 'string'
    }, {
        name: 'termTpNew', //终端类型
        type: 'string'
    }, {
        name: 'financeCard1New', //财务账号1
        type: 'string'
    }, {
        name: 'financeCard2New', //财务账号2
        type: 'string'
    }, {
        name: 'financeCard3New', //财务账号3
        type: 'string'
    }, {
        name: 'termVerNew', //固话POS版本号
        type: 'string'
    }, {
        name: 'termAddrNew', //终端安装地址
        type: 'string'
    }, {
        name: 'txn14New', //NAC电话1
        type: 'string'
    }, {
        name: 'txn15New', //NAC电话2
        type: 'string'
    }, {
        name: 'txn16New', //NAC电话3
        type: 'string'
    }, {
        name: 'bindTel1New', //绑定电话1
        type: 'string'
    }, {
        name: 'bindTel2New', //绑定电话2
        type: 'string'
    }, {
        name: 'bindTel3New', //绑定电话3
        type: 'string'
    }, {
        name: 'keyDownSignNew', //CA公钥下载标志
        type: 'string'
    }, {
        name: 'paramDownSignNew', //终端参数下载标志
        type: 'string'
    }, {
        name: 'icDownSignNew', //IC卡参数下载标志
        type: 'string'
    }, {
        name: 'reserveFlag1New', //绑定电话
        type: 'string'
    }, {
        name: 'txn35New', //分期付款期数
        type: 'string'
    }, {
        name: 'txn36New', //分期付款限额
        type: 'string'
    }, {
        name: 'txn37New', //消费单笔上限
        type: 'string'
    }, {
        name: 'txn38New', //退货单笔上限
        type: 'string'
    }, {
        name: 'txn39New', //转账单笔上限
        type: 'string'
    }, {
        name: 'txn40New', //退货时间跨度
        type: 'string'
    }, {
        name: 'param1New', //查询
        type: 'string'
    }, {
        name: 'param2New', //预授权
        type: 'string'
    }, {
        name: 'param3New', //预授权撤销
        type: 'string'
    }, {
        name: 'param4New', //预授权完成联机
        type: 'string'
    }, {
        name: 'param5New', //预授权完成撤销
        type: 'string'
    }, {
        name: 'param6New', //消费
        type: 'string'
    }, {
        name: 'param7New', //消费撤销
        type: 'string'
    }, {
        name: 'param8New', //退货
        type: 'string'
    }, {
        name: 'param9New', //离线结算
        type: 'string'
    }, {
        name: 'param10New', //结算调整
        type: 'string'
    }, {
        name: 'param11New', //公司卡转个人卡（财务POS）
        type: 'string'
    }, {
        name: 'param12New', //个人卡转公司卡（财务POS）
        type: 'string'
    }, {
        name: 'param13New', //卡卡转帐
        type: 'string'
    }, {
        name: 'param14New', //上笔交易查询（财务POS）
        type: 'string'
    }, {
        name: 'param15New', //交易查询（财务POS）
        type: 'string'
    }, {
        name: 'param16New', //定向汇款
        type: 'string'
    }, {
        name: 'param17New', //分期付款
        type: 'string'
    }, {
        name: 'param18New', //分期付款撤销
        type: 'string'
    }, {
        name: 'param19New', //代缴费
        type: 'string'
    }, {
        name: 'param20New', //电子现金
        type: 'string'
    }, {
        name: 'param21New', //IC现金充值
        type: 'string'
    }, {
        name: 'param22New', //指定账户圈存
        type: 'string'
    }, {
        name: 'param23New', //非指定账户圈存
        type: 'string'
    }, {
        name: 'param24New', //非接快速支付
        type: 'string'
    }
    ]);
	
	//终端临时ID
	var termTmpID = 1;
	
    var termPanel = new Ext.TabPanel({
        activeTab: 0,
        height: 400,
        width: 650,
        frame: true,
        items: [{
                title: '基本信息',
                id: 'info1New',
                layout: 'column',
                items: [{
                    columnWidth: .8,
                    layout: 'form',
                    items: [{
                    	xtype: 'textfield',
                        fieldLabel: '临时终端号',
                        id: 'termID',
                        name: 'termID',
                        readOnly: true,
                        width: 300
                     }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户名',
                        id: 'txn22New',
                        name: 'txn22New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .8, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '商户英文名',
                        id: 'txn27New',
                        name: 'txn27New',
                        readOnly: true,
                        width: 300
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'textfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccNew',
                        name: 'termMccNew',
                        readOnly: true
                    }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                    xtype: 'combo',
                    fieldLabel: '所属合作伙伴*',
                    id: 'termBranchNew',
                    hiddenName: 'brhIdNew',
                    store: brhStore,
                    displayField: 'displayField',
                    valueField: 'valueField',
                    mode: 'local',
                    allowBlank: false,
                    blankText: '所属合作伙伴不能为空',
                    listeners:{
                        'select': function() {
                        	SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('termBranchNew').value,function(ret){
							        eposStore.loadData(Ext.decode(ret));
							    });
                        }
                    }
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '联系电话*',
                    maxLength: 20,
                    allowBlank: false,
                    regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
                    id: 'contTelNew',
                    name: 'contTelNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '产权属性*',
                    id: 'propTpN',
                    allowBlank: false,
                    hiddenName: 'propTpNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['0','钱宝所有'],['1','合作伙伴所有'],['2','商户自有']]
                    }),
                    listeners:{
                        'select': function() {
	                        var args = Ext.getCmp('propTpN').getValue();
	                        if(args == 2)
	                        {
	                            Ext.getCmp("termPara1New").show();
                                Ext.getCmp("flagBox1").show();
	                        }
                            else
                            {
                                Ext.getCmp("termPara1New").hide();
                                Ext.getCmp("flagBox1").hide();
                            }
                        }
                   }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combo',
                    fieldLabel: '收单服务机构',
                    store: organStore,
                    id: 'propInsNmN',
                    hiddenName: 'propInsNmNew'
                }]
            },{
                columnWidth: .5,
                id: "flagBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'numberfield',
                    fieldLabel: '第三方分成比例(%)',
                    id: 'termPara1New',
                    name: 'termPara1New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combo',
                    fieldLabel: '连接类型*',
                    id: 'connectModeN',
                    value: 'J',
                    allowBlank: false,
                    hiddenName: 'connectModeNew',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['J','间联'],['Z','直联']]
                    })
                }]
            }]
            },{
                title: '附加信息',
                layout: 'column',
                id: 'info2New',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'combo',
	                    fieldLabel: '终端类型*',
	                    id: 'termTpN',
	                    allowBlank: false,
	                    hiddenName: 'termTpNew',
	                    width:150,
	                    store: termTypeStore,
	                    listeners: {
	                     'select': function() {
	                                var value1 = Ext.getCmp("termMccNew").getValue();
	                                var value2 = Ext.getCmp('termTpN').getValue();
	                                //改为使用elseif判断
	                                if(Ext.getCmp('idmchtGroupFlag').value == '6' && value2 != '3'){
	                                	termForm.getForm().findField("termTpN").setValue(3);
		                                showAlertMsg("固话POS商户，终端类型只能选择固话POS",termGridPanel);
	                    	 		}else if(Ext.getCmp('idmchtGroupFlag').value != '6' && value2 == '3'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("非固话POS商户，终端类型不能选择固话POS",termGridPanel);
	                    	 		}else if( value1 != '0000' && value2 == '1'){
	                                	termForm.getForm().findField("termTpN").setValue(0);
		                                showAlertMsg("非财务POS商户，终端类型不能选择财务POS",termGridPanel);
	                                }
	                                if( Ext.getCmp('termTpN').getValue() == '3' ){
		                                Ext.getCmp('accountBox3').show();
		                                termPanel.get('info3New').setDisabled(true);
//		                                Ext.getCmp('txn14New').allowBlank = true;
		                                Ext.getCmp('termVerN').allowBlank = false;
	                                }else{
	                                	Ext.getCmp('accountBox3').hide();
		                                termPanel.get('info3New').setDisabled(false);
//		                                Ext.getCmp('txn14New').allowBlank = false;
	                                	Ext.getCmp('termVerN').allowBlank = true;
	                                }
	                        }
                    }
                    }]
           },{
                columnWidth: .5,
                id: "accountBox1",
                hidden: true,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '财务账号1*',
                    maxLength: 16,
                    vtype: 'isNumber',
                    id: 'financeCard1New',
                    name: 'financeCard1New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号2',
                    maxLength: 16,
                    id: 'financeCard2New',
                    name: 'financeCard2New'
                },{
                    xtype: 'textfield',
                    fieldLabel: '财务账号3',
                    maxLength: 16,
                    id: 'financeCard3New',
                    name: 'financeCard3New'
                }]
            },{
                id: 'accountBox3',
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                	xtype: 'combo',
                    fieldLabel: '固话POS版本号*',
                    store: eposStore,
                    id: 'termVerN',
                    hiddenName: 'termVerNew',
                    anchor: '80%'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
                    fieldLabel: '终端安装地址',
                    maxLength: 200,
                    id: 'termAddrNew',
                    name: 'termAddrNew'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话1*',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn14New',
                    name: 'txn14New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话2',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn15New',
                    name: 'txn15New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden: true,
                items: [{
                    xtype: 'textfield',
                    fieldLabel: 'NAC 电话3',
                    maxLength: 14,
                    vtype: 'isNumber',
                    id: 'txn16New',
                    name: 'txn16New'
                }]
            }, {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
//                    fieldLabel: '绑定电话1',
                    hidden: true,
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel1New',
                    name: 'bindTel1New',
                    allowBlank: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
//                    fieldLabel: '绑定电话2',
                    hidden: true,
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel2New',
                    name: 'bindTel2New'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'textfield',
//                    fieldLabel: '绑定电话3',
                    hidden: true,
                    maxLength: 15,
                    vtype: 'isNumber',
                    id: 'bindTel3New',
                    name: 'bindTel3New'
                }]
            }, {
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignNew',
                    name: 'keyDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignNew',
                    name: 'paramDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
							r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignNew',
                    name: 'icDownSignNew',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
                    		r.setValue('1');
						}
                    }
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '绑定电话',
                    id: 'reserveFlag1New',
                    name: 'reserveFlag1New',
                    inputValue: '1',
                    checked: true,
                    listeners :{
                    'check':function(r,c){
                    		r.setValue('1');
						}
                    }
                }]
            }]
            },{
                title: '交易信息',
                id: 'info3New',
                layout: 'column',
                items: [{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款期数',
                        vtype: 'isNumber',
                        id: 'txn35New',
                        maxLength: 2,
                        name: 'txn35New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '分期付款限额',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn36New',
                        name: 'txn36New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '消费单笔上限 ',
                        vtype: 'isMoney',
                        maxLength: 12,
                        id: 'txn37New',
                        name: 'txn37New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货单笔上限',
                        id: 'txn38New',
                        vtype: 'isMoney',
                        maxLength: 12,
                        name: 'txn38New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '转帐单笔上限',
                        vtype: 'isMoney',
                        id: 'txn39New',
                        maxLength: 12,
                        name: 'txn39New'
                    }]
                },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                        xtype: 'textfield',
                        fieldLabel: '退货时间跨度',
                        vtype: 'isNumber',
                        id: 'txn40New',
                        maxLength: 2,
                        name: 'txn40New',
                        value: 30
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '查询 ',
                        id: 'param1New',
                        name: 'param1New',
                        checked: true,
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权 ',
                        id: 'param2New',
                        name: 'param2New',
                        inputValue: '1'
                    }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权撤销 ',
                        id: 'param3New',
                        name: 'param3New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成联机 ',
                        id: 'param4New',
                        name: 'param4New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '预授权完成撤销 ',
                        id: 'param5New',
                        name: 'param5New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费 ',
                        id: 'param6New',
                        name: 'param6New',
                        checked: true,
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '消费撤销 ',
                        id: 'param7New',
                        name: 'param7New',
                        checked: true,
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '退货 ',
                        id: 'param8New',
                        name: 'param8New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '离线结算 ',
                        id: 'param9New',
                        name: 'param9New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '结算调整 ',
                        id: 'param10New',
                        name: 'param10New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '公司卡转个人卡（财务POS） ',
                        id: 'param11New',
                        name: 'param11New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '个人卡转公司卡（财务POS） ',
                        id: 'param12New',
                        name: 'param12New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '卡卡转帐',
                        id: 'param13New',
                        name: 'param13New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '上笔交易查询（财务POS） ',
                        id: 'param14New',
                        name: 'param14New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '交易查询（财务POS） ',
                        id: 'param15New',
                        name: 'param15New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '定向汇款 ',
                        id: 'param16New',
                        name: 'param16New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款 ',
                        id: 'param17New',
                        name: 'param17New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '分期付款撤销 ',
                        id: 'param18New',
                        name: 'param18New',
                        inputValue: '1'
                     }]
                },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '代缴费 ',
                        id: 'param19New',
                        name: 'param19New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '电子现金 ',
                        id: 'param20New',
                        name: 'param20New',
                        checked: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: 'IC现金充值 ',
                        id: 'param21New',
                        name: 'param21New',
                        disabled: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '指定账户圈存',
                        id: 'param22New',
                        name: 'param22New',
                        checked: true,
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非指定账户圈存 ',
                        id: 'param23New',
                        name: 'param23New',
                        inputValue: '1'
                     }]
            },{
                columnWidth: .3,
                layout: 'form',
                items: [{
                        xtype: 'checkbox',
                        fieldLabel: '非接快速支付 ',
                        id: 'param24New',
                        name: 'param24New',
                        checked: true,
                        inputValue: '1'
                     }]
            }]
            }]
    });
    /**************  终端表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 400,
		width: 650,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '终端信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 650,
		autoHeight: true,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				termPanel.setActiveTab("info3New");
				termPanel.setActiveTab("info2New");
				
				if(termForm.getForm().isValid()) {
					
					var aLine = new termCreate();
					aLine.set("termID",termForm.getForm().findField('termID').getValue());
					aLine.set("txn22New",termForm.getForm().findField('txn22New').getValue()); //商户名
					aLine.set("txn27New",termForm.getForm().findField('txn27New').getValue()); //商户英文名
					aLine.set("termMccNew",termForm.getForm().findField('termMccNew').getValue()); //终端MCC码
					aLine.set("brhIdNew",termForm.getForm().findField('termBranchNew').getValue()); //终端所属机构
					aLine.set("contTelNew",termForm.getForm().findField('contTelNew').getValue()); //联系电话
					aLine.set("propTpNew",termForm.getForm().findField('propTpN').getValue()); //产权属性
					aLine.set("propInsNmNew",termForm.getForm().findField('propInsNmN').getValue()); //收单服务机构
					aLine.set("termPara1New",termForm.getForm().findField('termPara1New').getValue()); //第三方分成比例
					aLine.set("connectModeNew",termForm.getForm().findField('connectModeN').getValue()); //连接类型
					aLine.set("termTpNew",termForm.getForm().findField('termTpN').getValue()); //终端类型
					aLine.set("financeCard1New","1"); //财务账号1
					aLine.set("financeCard2New","1"); //财务账号2
					aLine.set("financeCard3New","1"); //财务账号3
					aLine.set("termVerNew",termForm.getForm().findField('termVerN').getValue()); //固话POS版本号
					aLine.set("termAddrNew",termForm.getForm().findField('termAddrNew').getValue()); //终端安装地址
					aLine.set("txn14New",termForm.getForm().findField('txn14New').getValue()); //NAC电话1
					aLine.set("txn15New",termForm.getForm().findField('txn15New').getValue()); //NAC电话2
					aLine.set("txn16New",termForm.getForm().findField('txn16New').getValue()); //NAC电话3
					aLine.set("bindTel1New",termForm.getForm().findField('bindTel1New').getValue()); //绑定电话1
					aLine.set("bindTel2New",termForm.getForm().findField('bindTel2New').getValue()); //绑定电话2
					aLine.set("bindTel3New",termForm.getForm().findField('bindTel3New').getValue()); //绑定电话3
					aLine.set("keyDownSignNew",termForm.getForm().findField('keyDownSignNew').getValue()); //CA公钥下载标志
					aLine.set("paramDownSignNew",termForm.getForm().findField('paramDownSignNew').getValue()); //终端参数下载标志
					aLine.set("icDownSignNew",termForm.getForm().findField('icDownSignNew').getValue()); //IC卡参数下载标志
					aLine.set("reserveFlag1New",termForm.getForm().findField('reserveFlag1New').getValue()); //绑定电话
					aLine.set("txn35New",termForm.getForm().findField('txn35New').getValue()); //分期付款期数
					aLine.set("txn36New",termForm.getForm().findField('txn36New').getValue()); //分期付款限额
					aLine.set("txn37New",termForm.getForm().findField('txn37New').getValue()); //消费单笔上限
					aLine.set("txn38New",termForm.getForm().findField('txn38New').getValue()); //退货单笔上限
					aLine.set("txn39New",termForm.getForm().findField('txn39New').getValue()); //转账单笔上限
					aLine.set("txn40New",termForm.getForm().findField('txn40New').getValue()); //退货时间跨度
					aLine.set("param1New",Ext.getCmp('param1New').getValue()==false?0:1); //查询
					aLine.set("param2New",Ext.getCmp('param2New').getValue()==false?0:1); //预授权
					aLine.set("param3New",Ext.getCmp('param3New').getValue()==false?0:1); //预授权撤销
					aLine.set("param4New",Ext.getCmp('param4New').getValue()==false?0:1); //预授权完成联机
					aLine.set("param5New",Ext.getCmp('param5New').getValue()==false?0:1); //预授权完成撤销
					aLine.set("param6New",Ext.getCmp('param6New').getValue()==false?0:1); //消费
					aLine.set("param7New",Ext.getCmp('param7New').getValue()==false?0:1); //消费撤销
					aLine.set("param8New",Ext.getCmp('param8New').getValue()==false?0:1); //退货
					aLine.set("param9New",Ext.getCmp('param9New').getValue()==false?0:1); //离线结算
					aLine.set("param10New",Ext.getCmp('param10New').getValue()==false?0:1); //结算调整
					aLine.set("param11New",Ext.getCmp('param11New').getValue()==false?0:1); //公司卡转个人卡（财务POS）
					aLine.set("param12New",Ext.getCmp('param12New').getValue()==false?0:1); //个人卡转公司卡（财务POS）
					aLine.set("param13New",Ext.getCmp('param13New').getValue()==false?0:1); //卡卡转帐
					aLine.set("param14New",Ext.getCmp('param14New').getValue()==false?0:1); //上笔交易查询（财务POS）
					aLine.set("param15New",Ext.getCmp('param15New').getValue()==false?0:1); //交易查询（财务POS）
					aLine.set("param16New",Ext.getCmp('param16New').getValue()==false?0:1); //定向汇款
					aLine.set("param17New",Ext.getCmp('param17New').getValue()==false?0:1); //分期付款
					aLine.set("param18New",Ext.getCmp('param18New').getValue()==false?0:1); //分期付款撤销
					aLine.set("param19New",Ext.getCmp('param19New').getValue()==false?0:1); //代缴费
					aLine.set("param20New",Ext.getCmp('param20New').getValue()==false?0:1); //电子现金
					aLine.set("param21New",Ext.getCmp('param21New').getValue()==false?0:1); //IC现金充值
					aLine.set("param22New",Ext.getCmp('param22New').getValue()==false?0:1); //指定账户圈存
					aLine.set("param23New",Ext.getCmp('param23New').getValue()==false?0:1); //非指定账户圈存
					aLine.set("param24New",Ext.getCmp('param24New').getValue()==false?0:1); //非接快速支付
					
					for(var i=0;i<termStore.getCount();i++) {
						if(termStore.getAt(i).data.termID == termForm.getForm().findField('termID').getValue()) {
							termStore.removeAt(i);
						}
					}
					termStore.insert(termStore.getCount(), aLine);
					
					termForm.getForm().reset();
                    termWin.hide();
				} else {
					
                    var finded = true;
	                termForm.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if(tab == 'info1New' || tab == 'info2New' || tab == 'info3New' ){
	                             termPanel.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
				termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termForm.getForm().reset();
				termWin.hide();
			}
		}]
	});
	
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		    {name: 'termID',mapping: 'termID'}, //终端临时编号
			{name: 'txn22New',mapping: 'txn22New'}, //商户名
			{name: 'txn27New',mapping: 'txn27New'}, //商户英文名
			{name: 'termMccNew',mapping: 'termMccNew'}, //终端MCC码
			{name: 'brhIdNew',mapping: 'brhIdNew'}, //终端所属机构
			{name: 'contTelNew',mapping: 'contTelNew'}, //联系电话
			{name: 'propTpNew',mapping: 'propTpNew'}, //产权属性
			{name: 'propInsNmNew',mapping: 'propInsNmNew'}, //收单服务机构
			{name: 'termPara1New',mapping: 'termPara1New'}, //第三方分成比例
			{name: 'connectModeNew',mapping: 'connectModeNew'}, //连接类型
			{name: 'termTpNew',mapping: 'termTpNew'}, //终端类型
			{name: 'financeCard1New',mapping: 'financeCard1New'}, //财务账号1
			{name: 'financeCard2New',mapping: 'financeCard2New'}, //财务账号2
			{name: 'financeCard3New',mapping: 'financeCard3New'}, //财务账号3
			{name: 'termVerNew',mapping: 'termVerNew'}, //固话POS版本号
			{name: 'termAddrNew',mapping: 'termAddrNew'}, //终端安装地址
			{name: 'txn14New',mapping: 'txn14New'}, //NAC电话1
			{name: 'txn15New',mapping: 'txn15New'}, //NAC电话2
			{name: 'txn16New',mapping: 'txn16New'}, //NAC电话3
			{name: 'bindTel1New',mapping: 'bindTel1New'}, //绑定电话1
			{name: 'bindTel2New',mapping: 'bindTel2New'}, //绑定电话2
			{name: 'bindTel3New',mapping: 'bindTel3New'}, //绑定电话3
			{name: 'keyDownSignNew',mapping: 'keyDownSignNew'}, //CA公钥下载标志
			{name: 'paramDownSignNew',mapping: 'paramDownSignNew'}, //终端参数下载标志
			{name: 'icDownSignNew',mapping: 'icDownSignNew'}, //IC卡参数下载标志
			{name: 'reserveFlag1New',mapping: 'reserveFlag1New'}, //绑定电话
			{name: 'txn35New',mapping: 'txn35New'}, //分期付款期数
			{name: 'txn36New',mapping: 'txn36New'}, //分期付款限额
			{name: 'txn37New',mapping: 'txn37New'}, //消费单笔上限
			{name: 'txn38New',mapping: 'txn38New'}, //退货单笔上限
			{name: 'txn39New',mapping: 'txn39New'}, //转账单笔上限
			{name: 'txn40New',mapping: 'txn40New'}, //退货时间跨度
			{name: 'param1New',mapping: 'param1New'}, //查询
			{name: 'param2New',mapping: 'param2New'}, //预授权
			{name: 'param3New',mapping: 'param3New'}, //预授权撤销
			{name: 'param4New',mapping: 'param4New'}, //预授权完成联机
			{name: 'param5New',mapping: 'param5New'}, //预授权完成撤销
			{name: 'param6New',mapping: 'param6New'}, //消费
			{name: 'param7New',mapping: 'param7New'}, //消费撤销
			{name: 'param8New',mapping: 'param8New'}, //退货
			{name: 'param9New',mapping: 'param9New'}, //离线结算
			{name: 'param10New',mapping: 'param10New'}, //结算调整
			{name: 'param11New',mapping: 'param11New'}, //公司卡转个人卡（财务POS）
			{name: 'param12New',mapping: 'param12New'}, //个人卡转公司卡（财务POS）
			{name: 'param13New',mapping: 'param13New'}, //卡卡转帐
			{name: 'param14New',mapping: 'param14New'}, //上笔交易查询（财务POS）
			{name: 'param15New',mapping: 'param15New'}, //交易查询（财务POS）
			{name: 'param16New',mapping: 'param16New'}, //定向汇款
			{name: 'param17New',mapping: 'param17New'}, //分期付款
			{name: 'param18New',mapping: 'param18New'}, //分期付款撤销
			{name: 'param19New',mapping: 'param19New'}, //代缴费
			{name: 'param20New',mapping: 'param20New'}, //电子现金
			{name: 'param21New',mapping: 'param21New'}, //IC现金充值
			{name: 'param22New',mapping: 'param22New'}, //指定账户圈存
			{name: 'param23New',mapping: 'param23New'}, //非指定账户圈存
			{name: 'param24New',mapping: 'param24New'}  //非接快速支付
		]),
		autoLoad: false
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		
	    {header: '终端临时编号',dataIndex: 'termID',width: 120},
	    {header: '所属合作伙伴',dataIndex: 'brhIdNew',width: 120},
	    {header: '联系电话',dataIndex: 'contTelNew',width: 120},
	    {header: '产权属性',dataIndex: 'propTpNew',width: 120,renderer:
	    	function settleType(val){
			switch(val){
				case '0':return '钱宝所有';
				case '1':return '合作伙伴所有';
				case '2':return '商户自有';
			}
		}},
	    {header: '收单服务机构',dataIndex: 'propInsNmNew',width: 120},
	    {header: '第三方分成比例(%)',dataIndex: 'termPara1New',width: 120},
	    {header: '连接类型',dataIndex: 'connectModeNew',width: 120,renderer:
	    	function settleType(val){
			switch(val){
				case 'Z':return '直联';
				case 'J':return '间联';
			}
		}
	    },
	    {header: '终端类型',dataIndex: 'termTpNew',width: 120,renderer:termForType},
		{header: '终端序列号',dataIndex: 'propInsNmNew',width: 120}
		]);
	
	var termGridPanel = new Ext.grid.GridPanel({
		title: '终端信息列表',
		iconCls: 'T301',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		height: 250,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		}
	});
	
	termGridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(termGridPanel.getView().getRow(termGridPanel.getSelectionModel().last)).frame();
		}
	});
	
//  在点击“添加终端”按钮时触发，替代原终端维护中的读取商户信息部分
	var doBeforeAddTerm = function() {
	
		var mccV = mchntForm.getForm().findField('idmcc').getValue(); //商户MCC
		var acqInstV = mchntForm.getForm().findField('idbankNo').getValue(); //收单机构
		var mchtCnAbbrV = mchntForm.getForm().findField('mchtCnAbbr').getValue(); //中文名称简写
		var engNameV = mchntForm.getForm().findField('engName').getValue(); //英文名称
		var settleAcctV = mchntForm.getForm().findField('settleAcct').getValue(); //商户账户账号
		var mchtGroupV = mchntForm.getForm().findField('idmchtGroupFlag').getValue(); //商户种类
		
		if(mccV == "") {
			showAlertMsg("请先选择 商户MCC",mchntForm);
			return false;
		}
		if(mchtCnAbbrV == "") {
			showAlertMsg("请先填写 中文名称简写",mchntForm);
			return false;
		}
		if(settleAcctV == "") {
			showAlertMsg("请先填写 商户账户账号",mchntForm);
			return false;
		}

        Ext.getCmp("termMccNew").setValue(mccV);
        Ext.getCmp("termBranchNew").setValue(acqInstV);
        Ext.getCmp("txn22New").setValue(mchtCnAbbrV);
        Ext.getCmp("txn27New").setValue(engNameV);

        SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',acqInstV,function(ret){
	        eposStore.loadData(Ext.decode(ret));
	    });
        
        if( mccV == '0000' ){
        	
//            Ext.getCmp('accountBox1').show();
            termForm.getForm().findField("termTpN").setValue(1);
            termForm.getForm().findField("termTpN").setReadOnly(true);
//            termForm.getForm().findField("financeCard1New").setValue(settleAcctV);
//            termForm.getForm().findField("financeCard1New").setReadOnly(true);
//            Ext.getCmp('financeCard1New').allowBlank = false;
            
            termForm.getForm().findField("param1New").setValue(1);
            termForm.getForm().findField("param11New").setValue(1);
            termForm.getForm().findField("param12New").setValue(1);
            termForm.getForm().findField("param13New").setValue(1);
            termForm.getForm().findField("param14New").setValue(1);
            termForm.getForm().findField("param15New").setValue(1);
         }else{
//            Ext.getCmp('accountBox1').hide();
            if(termForm.getForm().findField("termTpN").getValue()=='1'){termForm.getForm().findField("termTpN").setValue(0);};
            termForm.getForm().findField("termTpN").setReadOnly(false);
//            termForm.getForm().findField("financeCard1New").setValue("");
//            termForm.getForm().findField("financeCard1New").setReadOnly(false);
//            Ext.getCmp('financeCard1New').allowBlank = true;
            
            termForm.getForm().findField("param1New").setValue(0);
            termForm.getForm().findField("param11New").setValue(0);
            termForm.getForm().findField("param12New").setValue(0);
            termForm.getForm().findField("param13New").setValue(0);
            termForm.getForm().findField("param14New").setValue(0);
            termForm.getForm().findField("param15New").setValue(0);
        }
        if(mchtGroupV == '6'){
        	Ext.getCmp('accountBox3').show();
        	termForm.getForm().findField("termTpN").setValue(3);
            termPanel.get('info3New').setDisabled(true);
//            Ext.getCmp('txn14New').allowBlank = true;
            termForm.getForm().findField("reserveFlag1New").setValue(1);
            Ext.getCmp('termVerN').allowBlank = false;	
        }else{
        	Ext.getCmp('accountBox3').hide();
        	if(termForm.getForm().findField("termTpN").getValue()=='3'){termForm.getForm().findField("termTpN").setValue(0);};
            termPanel.get('info3New').setDisabled(false);
//            Ext.getCmp('txn14New').allowBlank = false;
            termForm.getForm().findField("reserveFlag1New").setValue(0);
        	Ext.getCmp('termVerN').allowBlank = true;
        }
        
        return true;
	};
	
//	用于在提交的时候组织数据
    var makeTermData = function() {
    	
    	var dataArray = new Array();
    	for(var i=0;i<termStore.getCount();i++){
    		
    		var re = termStore.getAt(i);
    		var data = {
					termID: re.data.termID, //终端临时编号
					txn22New: re.data.txn22New, //商户名
					txn27New: re.data.txn27New, //商户英文名
					termMccNew: re.data.termMccNew, //终端MCC码
					brhIdNew: re.data.brhIdNew, //终端所属机构
					contTelNew: re.data.contTelNew, //联系电话
					propTpNew: re.data.propTpNew, //产权属性
					propInsNmNew: re.data.propInsNmNew, //收单服务机构
					termPara1New: re.data.termPara1New, //第三方分成比例
					connectModeNew: re.data.connectModeNew, //连接类型
					termTpNew: re.data.termTpNew, //终端类型
					financeCard1New: re.data.financeCard1New, //财务账号1
					financeCard2New: re.data.financeCard2New, //财务账号2
					financeCard3New: re.data.financeCard3New, //财务账号3
					termVerNew: re.data.termVerNew, //固话POS版本号
					termAddrNew: re.data.termAddrNew, //终端安装地址
					txn14New: re.data.txn14New, //NAC电话1
					txn15New: re.data.txn15New, //NAC电话2
					txn16New: re.data.txn16New, //NAC电话3
					bindTel1New: re.data.bindTel1New, //绑定电话1
					bindTel2New: re.data.bindTel2New, //绑定电话2
					bindTel3New: re.data.bindTel3New, //绑定电话3
					keyDownSignNew: re.data.keyDownSignNew, //CA公钥下载标志
					paramDownSignNew: re.data.paramDownSignNew, //终端参数下载标志
					icDownSignNew: re.data.icDownSignNew, //IC卡参数下载标志
					reserveFlag1New: re.data.reserveFlag1New, //绑定电话
					txn35New: re.data.txn35New, //分期付款期数
					txn36New: re.data.txn36New, //分期付款限额
					txn37New: re.data.txn37New, //消费单笔上限
					txn38New: re.data.txn38New, //退货单笔上限
					txn39New: re.data.txn39New, //转账单笔上限
					txn40New: re.data.txn40New, //退货时间跨度
					param1New: re.data.param1New, //查询
					param2New: re.data.param2New, //预授权
					param3New: re.data.param3New, //预授权撤销
					param4New: re.data.param4New, //预授权完成联机
					param5New: re.data.param5New, //预授权完成撤销
					param6New: re.data.param6New, //消费
					param7New: re.data.param7New, //消费撤销
					param8New: re.data.param8New, //退货
					param9New: re.data.param9New, //离线结算
					param10New: re.data.param10New, //结算调整
					param11New: re.data.param11New, //公司卡转个人卡（财务POS）
					param12New: re.data.param12New, //个人卡转公司卡（财务POS）
					param13New: re.data.param13New, //卡卡转帐
					param14New: re.data.param14New, //上笔交易查询（财务POS）
					param15New: re.data.param15New, //交易查询（财务POS）
					param16New: re.data.param16New, //定向汇款
					param17New: re.data.param17New, //分期付款
					param18New: re.data.param18New, //分期付款撤销
					param19New: re.data.param19New, //代缴费
					param20New: re.data.param20New, //电子现金
					param21New: re.data.param21New, //IC现金充值
					param22New: re.data.param22New, //指定账户圈存
					param23New: re.data.param23New, //非指定账户圈存
					param24New: re.data.param24New //非接快速支付
			};
			dataArray.push(data);
    	}
    	
    	return dataArray;
    };
	 //******************终端信息部分**********结束********
	
	//重置账号验证
	function resetVerify(){
		verifyResult = false;
	    Ext.getCmp('verifySta').setValue('<font color="red">未验证</font>');
	}

	function resetInVerify() {
		verifyResult2 = false;
	    Ext.getCmp('verifyInSta').setValue('<font color="red">未验证</font>');
	}
	
	var mchntForm = new Ext.FormPanel({
        title: '新增商户信息',
		region: 'center',
		iconCls: 'T20100',
		frame: true,
		modal: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
		labelWidth: 120,
		autoScroll: true,
		waitMsgTarget: true,
		labelAlign: 'left',
        items: [{
        	layout:'column',
        	items: [{
        		columnWidth: .33,
            	layout: 'form',
        		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_GROUP_FLAG',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户种类*',
					id: 'idmchtGroupFlag',
					hiddenName: 'mchtGroupFlag',
					allowBlank: false,
					anchor: '90%',
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
//        	},{
//        		columnWidth: .33,
//            	layout: 'form',
//            	xtype: 'panel',
//        		items: [{
//			        xtype: 'checkbox',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '是否映射其他商户号',
//					id: 'selectOtherNo',
//					name: 'selectOtherNo',
//		        	listeners: {
//		        		'check':function(r,c){
//		        			if(c){
//								mchntForm.getForm().findField("idOtherNo").enable();
//							}else{
//								mchntForm.getForm().findField("idOtherNo").disable();
//							}
//		        		}
//		        	}
//        		}]
        	},{
        		columnWidth: .66,
        		xtype: 'panel',
		        layout: 'form',
//		        hidden:true,
	       		items: [{
			        xtype: 'basecomboselect',
			        baseParams: 'MCHT_GROUP_NEW',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '所属集团*',
					allowBlank: false,
					editable: true,
					disabled: true,
					id: 'idmchtGroupId',
					hiddenName: 'mchtGroupId',
					anchor: '94.8%',
					value: '-'
		        }]
			},{
        		columnWidth: .33,
            	layout: 'form',
            	xtype: 'panel',
        		items: [{
        			xtype: 'basecomboselect',
			        baseParams: 'CONN_TYPE',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户接入方式*',
					id: 'idconnType',
					hiddenName: 'connType',
					allowBlank: false,
					anchor: '90%',
					value: 'J'
				}]
        	},
//			{
//	        	columnWidth: .33,
//	        	id: 'otherNoPanel',
//	        	xtype: 'panel',
//	        	layout: 'form',
//	       		items: [{
//	       			xtype: 'textnotnull',
//					labelStyle: 'padding-left: 5px',
//					fieldLabel: '映射商户号',
//					maxLength: '15',
//					vtype: 'isOverMax',
//					id: 'idOtherNo',
//					regex: /^[0-9]+$/,
//					regexText: '该输入框只能输入数字0-9',
//					maskRe: /^[0-9]+$/,
//					disabled: true,
//					anchor: '90%'
//				}]
//			},
			{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
		        	xtype: 'textfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户号',
					maxLength: '15',
					vtype: 'isOverMax',
					id: 'mchtNoBySelf',
					name: 'mchtNoBySelf',
					regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
	       			xtype: 'combo',
//                    store: brhStore,
//                    displayField: 'displayField',
//                    valueField: 'valueField',
                    mode: 'local',
			        xtype: 'basecomboselect',
			        baseParams: 'BRH_BELOW_BRANCH',
			        id: 'idbankNo',
					hiddenName: 'bankNo',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '签约机构*',
					allowBlank: false,
					blankText: '请选择签约机构',
					anchor: '90%',
					listeners:{
						'select': function() {
							SelectOptionsDWR.getComboDataWithParameter('EPOS_VERSION',Ext.getCmp('idbankNo').value,function(ret){
							        eposStore.loadData(Ext.decode(ret));
							    });
                        }
					}
		        }]
			},{
	        	columnWidth: .33,
	        	id: 'mchtNmPanel',
	        	xtype: 'panel',
	        	layout: 'form',
	       		items: [{
	       			xtype: 'textnotnull',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '中文名称*',
					maxLength: '60',
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
					maxLength: '40',
					vtype: 'isOverMax',
					id: 'mchtCnAbbr',
  				    allowBlank: false,
					blankText: '请输入中文名称简写',
					name: 'mchtCnAbbr',
					anchor: '90%'
	        	}]
			},{
	        	columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
	       		items: [{
						xtype: 'dynamicCombo',
						labelStyle: 'padding-left: 5px',
						methodName: 'getAreaCode',
						fieldLabel: '所在地区*',
						hiddenName: 'areaNo',
						allowBlank: false,
						editable: true,
						anchor: '90%'
		        	}]
			},{
				columnWidth: .33,
	        	layout: 'form',
	        	xtype: 'panel',
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
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'MCHNT_GRP_ALL',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户组别*',
						allowBlank: false,
						hiddenName: 'mchtGrp',
						id:'mchtGrpId',
						anchor: '94.8%',
						listeners: {
							'select': function() {
								mchntMccStore.removeAll();
								var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
								mchntMccStore.loadData(Ext.decode(ret));
								
//								if(mchtGrpId == '0010'){
//									Ext.getCmp('settleAcctMidID').hide();
//									Ext.getCmp('settleAcctMid').allowBlank = true;
//									Ext.getCmp('verifyInButtonID').hide();
//									Ext.getCmp('verifyInStaID').hide();
//									verifyResult2 = true;
//									
//								}
								});
								
//								if(this.value == '0003') {
//									Ext.getCmp('licenceNo').hide();
//									Ext.getCmp('faxNo').hide();
//									Ext.getCmp('manager').hide();
//									Ext.getCmp('idartifCertifTp').hide();
//									Ext.getCmp('identityNo').hide();
//								} else {
//									Ext.getCmp('licenceNo').show();
//									Ext.getCmp('faxNo').show();
//									Ext.getCmp('manager').show();
//									Ext.getCmp('idartifCertifTp').show();
//									Ext.getCmp('identityNo').show();
//								}
								
								/*if(this.value == '0003') {
									Ext.getCmp('accountHide1').hide();	
									Ext.getCmp('accountHide2').hide();	
									Ext.getCmp('accountHide3').hide();
									Ext.getCmp('accountHide4').hide();
									Ext.getCmp('accountHide5').hide();
									Ext.getCmp('licenceNo').allowBlank = true;
									Ext.getCmp('faxNo').allowBlank = true;
									Ext.getCmp('manager').allowBlank = true;
									Ext.getCmp('idartifCertifTp').allowBlank = true;
									Ext.getCmp('identityNo').allowBlank = true;
								} else {*/
									Ext.getCmp('accountHide1').show();	
									Ext.getCmp('accountHide2').show();
									Ext.getCmp('accountHide3').show();
									Ext.getCmp('accountHide4').show();
									Ext.getCmp('accountHide5').show();
									Ext.getCmp('licenceNo').allowBlank = false;
									Ext.getCmp('faxNo').allowBlank = false;
									Ext.getCmp('manager').allowBlank = false;
									Ext.getCmp('idartifCertifTp').allowBlank = false;
									Ext.getCmp('identityNo').allowBlank = false;
//								}
							},
							'change': function() {
								mchntMccStore.removeAll();
								var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
								Ext.getCmp('idmcc').setValue('');
								Ext.getDom(Ext.getDoc()).getElementById('mcc').value = '';
								SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrp').getValue(),function(ret){
								mchntMccStore.loadData(Ext.decode(ret));
//								if(mchtGrpId == '0010'){
//									Ext.getCmp('settleAcctMidID').hide();
//									Ext.getCmp('settleAcctMid').allowBlank = true;
//									Ext.getCmp('verifyInButtonID').hide();
//									Ext.getCmp('verifyInStaID').hide();
//									verifyResult2 = true;									
//								}else{
//									Ext.getCmp('settleAcctMidID').show();
//									Ext.getCmp('settleAcctMid').allowBlank = false;
//									Ext.getCmp('verifyInButtonID').show();
//									Ext.getCmp('verifyInStaID').show();
//								}
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
	        	columnWidth: .66,
	        	xtype: 'panel',
		        layout: 'form',
	       		items: [{
		        	xtype: 'basecomboselect',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '商户MCC*',
					store: mchntMccStore,
					displayField: 'displayField',
					valueField: 'valueField',
					mode: 'local',
					triggerAction: 'all',
					typeAhead: false,
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					allowBlank: false,
					lazyRender: true,
					anchor: '94.8%',
					blankText: '请选择商户MCC',
					id: 'idmcc',
					hiddenName: 'mcc',
					listeners: {
					      'select': function() {
						    gridStore.load();
						    Ext.getCmp('discCode').setValue('');
						     
						    var mchtGroup = mchntForm.getForm().findField('mchtGroupFlag').getValue();
                    	
	                    	if(mchtGroup != '6'){
	                    		mchntForm.getForm().findField('licenceNo').allowBlank = false;
		                     	mchntForm.getForm().findField('faxNo').allowBlank = false;
	                    	}else{
	                    		mchntForm.getForm().findField('licenceNo').allowBlank = true;
		                     	mchntForm.getForm().findField('faxNo').allowBlank = true;
	                    	}
				        }}
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
//					value: '0',
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
					fieldLabel: '业务类型*',
					allowBlank : false,
					id: 'mchtFunctionId',
					blankText:'至少选择一项',
//					value: '0',
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
			columnWidth: 1,
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
		        	id:'accountHide1',
		        	layout: 'form',
	       			items: [{
	       				columnWidth: .2,
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '营业执照编号*',
						width:150,
						maxLength: 20,
						id: 'licenceNo'
		        	},{
                			xtype: 'button',
                			columnWidth: .3,
//							iconCls: 'recover',
							text:'上传',
							id: 'resetbu1',
							width: 60,
							handler: function(){
								dialog.show();
							}
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	id:'accountHide2',
		        	layout: 'form',
	       			items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '税务登记证号码*',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'faxNo'
							
		        	},{
		        		xtype: 'panel',
		        		columnWidth: .1,
		        		layout: 'form',
		        		
	       				items: [{
                			xtype: 'button',
//							iconCls: 'recover',
							text:'上传',
							id: 'resetbu2',
							width: 60,
							handler: function(){
								dialog.show();
							}
                		}]
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
									Ext.getCmp('accountHide1').hide();	
									Ext.getCmp('accountHide2').hide();	
									Ext.getCmp('licenceNo').allowBlank = true;
									Ext.getCmp('faxNo').allowBlank = true;
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
							}
						}
		        	}]
				},
//				{
//	        		columnWidth: .5,
//		        	xtype: 'panel',
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'basecomboselect',
//			        	baseParams: 'MCHT_CRE_LVL',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '商户等级',
//						width:150,
//						hiddenName: 'mchtCreLvl'
//		        	}]
//				},
				{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '电子邮件',
						width:150,
						maxLength: 40,
						vtype: 'isOverMax',
						id: 'commEmail',
						name: 'commEmail',
						vtype: 'email'
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
                        maxLength: 60,
						vtype: 'isOverMax',
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
						maxLength: 60,
						vtype: 'isOverMax',
						id: 'addr'
		        	},{
                			xtype: 'button',
//							iconCls: 'recover',
							text:'上传',
							id: 'resetbu4',
							width: 60,
							handler: function(){
								dialog.show();
							}
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
		        	id:'accountHide3',
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
						regex: /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
						regexText: '注册资金必须是正数，如123.45',
						maxLength: 12,
						vtype: 'isOverMax',
						width:150,
						id: 'busAmt',
						name: 'busAmt'
		        	}]
				},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide4',
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
		        	id:'accountHide5',
	       			items: [{
			        	xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '法人代表证件号码*',
						width:150,
						maxLength: 20,
						vtype: 'isOverMax',
						id: 'identityNo',
						
		        	},{
                			xtype: 'button',
//							iconCls: 'recover',
							text:'上传',
							id: 'resetbu3',
							width: 60,
							handler: function(){
								dialog.show();
							}
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
						maxLength: 30,
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
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 14,
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
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 15,
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
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						maxLength: 20,
						vtype: 'isOverMax',
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
			        	width: 380,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '注册地址',
						maxLength: 60,
						vtype: 'isOverMax',
						width:150,
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
						width:150,
						maxLength: 8,
						vtype: 'isOverMax',
						id: 'prolTlr',
						name: 'prolTlr'
					},{
						xtype: 'basecomboselect',
			        	baseParams: 'BRH_BELOW_ID',
						fieldLabel: '签约网点*',
						allowBlank: false,
						editable: true,
						blankText: '请选择签约网点',
						id: 'idagrBr',
						hiddenName: 'agrBr',
						anchor: '90%'
		        	},{
						fieldLabel: '协议编号',
						maxLength: 20,
						vtype: 'isOverMax',
						width:150,
						id: 'protocalId',
						name: 'protocalId'
		        	}/*,{
		        		xtype: 'textnotnull',
						fieldLabel: '客户经理工号*',
						width:150,
						id: 'operNo',
						name: 'operNo',
						maxLength: 8
		        	}*/]
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
			        } /*,{
						fieldLabel: '客户经理电话',
						maxLength: 18,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						vtype: 'isOverMax',
						width:150,
						id: 'netTel',
						name: 'netTel'
		        	},*/
		        	/*{
		        		xtype: 'textnotnull',
						fieldLabel: '客户经理姓名*',
						maxLength: 10,
						vtype: 'isOverMax',
						width:150,
						id: 'operNm',
						name: 'operNm'
		        	}*/]
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
						value: '00:00',
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
						value: '23:59',
						id: 'closeTime',
						name: 'closeTime',
    					anchor: '55%'
		        	}]
				},{//附件上传按钮
					columnWidth: .1,
    				xtype: 'button',
					text: '上传附件',
					//width: '80',
					id: 'view4',
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
						width:150,
//						maxLength: 20,
						regex: /^[0-9]{12}$/,
						regexText: '请输入12位数字0-9',
//						maskRe: /^[0-9]{12}$/,
						allowBlank : false,
//						id: 'settleBankNo'
						id: 'openStlno',
                        listeners: {
                        	'change': function(){
                        		var checkStr = Ext.getCmp("openStlno").getValue();
	                     		T20100.checkCnapsId(checkStr,function(ret){
						    		if(ret=='F'){
		                     			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
		                     			Ext.getCmp("openStlno").setValue("");
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
//						xtype: 'basecomboselect',
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
//			        	xtype: 'textnotnull',
//						labelStyle: 'padding-left: 5px',
//						fieldLabel: '证件号码*',
//						width:150,
//						allowBlank : false,
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
				} , {
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
				}, {
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [
						new Ext.form.RadioGroup({
							fieldLabel: '商户结算账户类型*', //['0','对公账户'],['1','对私账户']
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
					//width: '80',
					id: 'view2',
					handler:function() {
						dialog.show();
					}
            }]
			},{
				title:'费率设置',
                id: 'feeamt',
                frame: true,
                layout: 'border',
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
//							iconCls: 'recover',
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
		    },{
				title:'终端信息',
                id: 'addTerm',
                frame: true,
                layout: 'border',
                items: [{
                	region: 'north',
                	height: 35,
                	layout: 'column',                	
                	items: [{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'add',
							text:'添加终端',
							id: 'addTermButton',
							width: 60,
							handler: function(){
								if(!doBeforeAddTerm()) {
									return ;
								}
								termForm.getForm().findField('termID').setValue(termTmpID);
								termTmpID = termTmpID + 1;
								termPanel.setActiveTab(0);
								termWin.show();
								termWin.center();
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'edit',
							text:'修改终端',
							id: 'updTermButton',
							width: 60,
							handler: function(){
								if(termGridPanel.getSelectionModel().hasSelection()) {									
									var rec = termGridPanel.getSelectionModel().getSelected();
									var termTmpId = rec.get('termID');									
									for(var i=0;i<termStore.getCount();i++) {
										if(termStore.getAt(i).data.termID == termTmpId) {
											termForm.getForm().loadRecord(termStore.getAt(i));
										}
									}
									termPanel.setActiveTab(0);
									termWin.show();
								}
							}
                		}]
					},{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
		        		xtype: 'panel',
		        		layout: 'form',
	       				items: [{
                			xtype: 'button',
							iconCls: 'delete',
							text:'删除终端',
							id: 'delTermButton',
							width: 60,
							handler: function(){								
								if(termGridPanel.getSelectionModel().hasSelection()) {									
									var rec = termGridPanel.getSelectionModel().getSelected();
									var termTmpId = rec.get('termID');									
									showConfirm('确定要删除该条终端信息吗？终端临时编号：' + termTmpId,termGridPanel,function(bt) {
										if(bt == "yes") {
											for(var i=0;i<termStore.getCount();i++) {
												if(termStore.getAt(i).data.termID == termTmpId) {
													termStore.removeAt(i);
												}
											}
										}
									});
								}
							}
                		}]
					}
//					,{html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'},{
//		        		xtype: 'panel',
//		        		layout: 'form',
//	       				items: [{
//                			xtype: 'button',
//							iconCls: 'query',
//							text:'查看终端',
//							id: 'queTermButton',
//							width: 60,
//							handler: function(){
//								
//							}
//                		}]
//					}
					]
                },{
                	region: 'center',
                	items:[termGridPanel]
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
            text: '重置',
            handler: function() {
            	hasSub = false;
            	checkIds = "T";
				mchntForm.getForm().reset();
			}
        }]
    });
    
    
        //外部加入监听
/*    Ext.getCmp("idmchtGroupId").on('select',function(){
    	T20100.getGroupMchnt(mchntForm.getForm().findField('idmchtGroupId').getValue(),function(ret){
    		if(ret=='0'){
    				showErrorMsg("用户清算表找不到相应集团商户",grid);
    				termForm.getForm().reset();
    				return;
    		}
    		
            var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
            mchntForm.getForm().findField('idbankNo').setValue(mchntInfo.bankNo.trim());
            Ext.getCmp("settleAcct").setValue(mchntInfo.mchtPerson.trim());
            Ext.getCmp("settleAcctMid").setValue(mchntInfo.contactAddr.trim());
        });
    });*/
    
    var checkIds = "T";
    function subSave(){
    	var btn = Ext.getCmp('save');
		var frm = mchntForm.getForm();
		hasSub = true;
		// 验证必填表单是否填写了
		if (frm.isValid()) {
			
//			Ext.getCmp('verifyButton').enable();

			if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
				showErrorMsg('两次输入商户账户账号不一致，请确认！',mchntForm);
			} else {
				// 营业时间判断
				var transTime = mchntForm.getForm().findField('mchtMngMode').getValue();
				if(transTime) {
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
				}
				var checkStr = Ext.getCmp("openStlno").getValue();
         		T20100.checkCnapsId(checkStr,function(ret){
		    		if(ret=='F'){
             			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
             			Ext.getCmp("openStlno").setValue("");
						Ext.getCmp("tab").setActiveTab("settle");
		    		}else{
						btn.disable();
						var dataArray = makeTermData();
						frm.submit({
							url: 'T20100Action_add.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								hasSub = false;
								checkIds = "T";
								showSuccessAlert(action.result.msg,mchntForm);
								btn.enable();
								frm.reset();
								termStore.removeAll();
								hasUpload = "0";
								resetImagesId();
								storeImg.reload({
								params: {
									start: 0,
									imagesId: imagesId,
									mcht:''
									}
								});
							},
							failure : function(form,action) {
								btn.enable();
								hasSub = false;
		//						Ext.getCmp('verifyButton').disable();
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
								hasUpload: hasUpload,
								imagesId: imagesId,
								checkIds: checkIds,
								data: Ext.encode(dataArray),
								clearType: Ext.getCmp('clearType').getValue().inputValue
		//						settleBankNo: Ext.getCmp('settleBankNo').getValue(),
		//						settleAcctNm: Ext.getCmp('settleAcctNm').getValue()
							}
					});
	    		}
	    	})
		}
	} else {
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
    	}
    );
	}}
    
  /*  var upload_button=Ext.create("Ext.Button", {
        text: "附件上传",
        allowDepress: true,     //是否允许按钮被按下的状态
        enableToggle: true,     //是否允许按钮在弹起和按下两种状态中切换
        handler: function () {
            Ext.Msg.alert("提示", "第一个事件");
        },
        id: "bt1"
    });*/
    mchntForm.getForm().findField('idbankNo').setValue(brhId.trim());
    mchntForm.getForm().findField('agrBr').setValue(brhId);

    mchntForm.getForm().findField('signInstId').setValue(cupBrhId);

    //为保证验证信息显示的正确，当切换tab时重新验证
    Ext.getCmp("tab").on('tabchange',function(){
    	if(hasSub){
			mchntForm.getForm().isValid();
		}else{
			mchntForm.getForm().clearInvalid();
		}
    });

    gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: Ext.getCmp('serdiscCd').getValue(),
			discNm: Ext.getCmp('serdiscNm').getValue(),
			mccCode: Ext.getCmp('idmcc').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntForm],
		renderTo: Ext.getBody()
	});
});