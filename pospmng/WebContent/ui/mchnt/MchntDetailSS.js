

//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntId,El){
	//T0合作伙伴分润档位
	var rateIdStore3 = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('rateIdStore3',mchntId,function(ret){
		rateIdStore3.loadData(Ext.decode(ret));
	});
	var fieldLableArray = ['是否开通提现','商户类型','集团商户','商户名称','中文简称','签约机构','商户组别','风险级别','MCC','结算方式','T+N','营业执照号码','营业执照有效期',
	                       '税务登记证号码','商户类型','电子邮件','邮政编码','法人代表','注册资金','法人代表证件类型','法人代表证件号码','证件有效期','手续费方式',
	                       '经营名称','经营内容','经营面积','联系人姓名','联系人电话','注册地址','经营单位','营业开始时间','营业结束时间','详细地址','商户结算账户类型',
	                       '商户账户开户行号','商户账户开户行名称','装机地址-省','装机地址-市','商户结算帐户开户行号','商户账户户名','分润方式','分润比例档位','分润封顶档位',
							'对账单','对账单理由','积分','积分理由','紧急','是否合规','是否县乡','借记卡手续费比例','借记卡手续费封顶','贷记卡手续费比例','贷记卡手续费封顶',
							'提现方式','手续费方式','垫资日息（%）','垫资天数计算方式','单笔手续费金额（元）','分润垫资档位','分润垫资天数计算方式','未提现余额处理方式'];
	var diffArray = new Array();
	function diffInfRec(xtype,fieldLabel,id,value){
		var diffRec =  {columnWidth : .5,
		layout : 'form',
		width : 150,
		hidden : false,
		labelWidth : 180,
		items : [ {
			xtype : xtype,
			fieldLabel : fieldLabel,
			id : id,
			//hiddenName : 'tblMchtBaseInfTmpTmp.connType',
			allowBlank : true,
			width : 350,
			value : value
		} ]};
		return diffRec;
	}
	var reasonStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntRefuseInfo'
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
	
	reasonStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: mchntId
		});
	});
	
	reasonStore.load({
		params: {
			start: 0,
			queryMchtNo: mchntId
		}
	});
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '操作时间',dataIndex: 'txnTime',sortable: true,width: 150,renderer: formatTs},
		{header: '商户编号',dataIndex: 'mchntId',width: 150,hidden:true},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,hidden:true},
		{header: '商户所属机构',dataIndex: 'brhId',width: 100,hidden:true},
		{header: '交易操作员',dataIndex: 'oprId',width: 150},
		{header: '操作结果',dataIndex: 'refuseType',width: 150},
		{header: '批注',dataIndex: 'refuseInfo',width: 600,id:'refuseInfo'}
	]);
	

	var reasongrid = new Ext.grid.GridPanel({
		title : '审批过程',
		iconCls : 'T301',
		region : 'center',
		frame : false,
		border : false,
		columnLines : true,
		stripeRows : true,
		height:160,
		store: reasonStore,
		//autoExpandColumn: 'refuseInfo',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
		loadMask: {
			msg: '正在加载商户退回（拒绝）信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: reasonStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	reasongrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(reasongrid.getView().getRow(reasongrid.getSelectionModel().last)).frame();
		}
	});
	// *************终端信息****************
	var termStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=termInfoAll'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'termId',
			mapping : 'termId'
		}, {
			name : 'termSerialNum',
			mapping : 'termSerialNum'
		}, {
			name : 'termSta',
			mapping : 'termSta'
		}, {
			name : 'termSignSta',
			mapping : 'termSignSta'
		}, {
			name : 'termIdId',
			mapping : 'termIdId'
		}, {
			name : 'termFactory',
			mapping : 'termFactory'
		}, {
			name : 'termMachTp',
			mapping : 'termMachTp'
		}, {
			name : 'termVer',
			mapping : 'termVer'
		}, {
			name : 'termTp',
			mapping : 'termTp'
		}, {
			name : 'termBranch',
			mapping : 'termBranch'
		}, {
			name : 'termIns',
			mapping : 'termIns'
		}, {
			name : 'recCrtTs',
			mapping : 'recCrtTs'
		}, {
			name : 'propTp',
			mapping : 'propTp'
		}, {
			name : 'termAddr',
			mapping : 'termAddr'
		}, {
			name : 'param',
			mapping : 'param'
		} ])
	});

	
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}), 
		{header: '终端类型',dataIndex: 'termTp',width: 70,renderer:termForType
		 	},
			{header: '产权属性',dataIndex: 'propTp',width: 100,renderer:
				function settleType(val){
				switch(val){
				case '0':return '钱宝所有';
				case '1':return '合作伙伴所有';
				case '2':return '商户自有';
				}
			}},
			{header: '安装地址',dataIndex: 'termAddr',width: 400},
		{header: '查询',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
				switch(val.substring(10,11)){
					case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '消费类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
				switch(val.substring(11,12)){
				case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '预授权类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(13,14)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '退货类',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(17,18)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '借记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(0,1)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '贷记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(1,2)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '准贷记卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(2,3)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
		{header: '预付费卡',dataIndex: 'param',width: 60,renderer:
			function settleType(val){
			switch(val.substring(3,4)){
			case '1':return '已开通';
					default:return '未开通';
				}
			}
		},
			{
				id : 'termId',
				hidden:true,
				header : '终端号',
				dataIndex : 'termId',
				width : 200
			}, {
				header : '商户号',
				hidden:true,
				dataIndex : 'mchntNo',
				width : 0,
				id : 'mchntNo'
			}, {
				header : '商户名',
				hidden:true,
				dataIndex : 'mchntName',
				width : 1,
				id : 'mchntName'
			}, {
				header : '终端状态',
				hidden:true,
				dataIndex : 'termSta',
				width : 0,
				renderer : termSta
			}, {
				header : '终端序列号',
				hidden:true,
				dataIndex : 'termSerialNum',
				width : 200,
				renderer : termSta
			}, {
				header : '终端所属机构',
				hidden:true,
				dataIndex : 'termBranch',
				width: 0
			}, {
				header : '机具库存编号',
				hidden:true,
				dataIndex : 'termIdId',
					width: 0
			}, {
				header : '机具厂商',
				hidden:true,
				dataIndex : 'termFactory',
				width : 280
			}, {
				header : '机具型号',
				hidden:true,
				dataIndex : 'termMachTp',
				width : 200
			}, {
				header : '录入日期',
				hidden:true,
				dataIndex : 'recCrtTs',
				width : 0,
				renderer : formatDt
			} ]);
	var qryMenu = {
			text : '详细信息',
			width : 85,
			hidden:false,
			iconCls : 'detail',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGridPanel.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
				bt.disable();
				setTimeout(function() {
							bt.enable();
						}, 2000);
				selectTermInfoNew(selectedRecord.get('termId'), selectedRecord
								.get('recCrtTs'));
			}
		};
	var editMenu = {
			text : '修改',
			id:'editMenu',
			hidden:false,
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGridPanel.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
//				bt.disable();
				updateTmpDealInfo(selectedRecord.get('termId'), selectedRecord.get('recCrtTs'),termGridPanel,null,El);
			}
		};
	var bacthMenu = {
			text : '批量修改',
			id:'bacthMenu',
			hidden:false,
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function(bt) {
				var selectedRecords = termGridPanel.getSelectionModel().getSelections();
				if (selectedRecords == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
				var termIds = '';
				var recCrtTs = '';
				for (var i = 0; i < selectedRecords.length; i++) {
					if(i == selectedRecords.length-1){
						termIds += selectedRecords[i].get('termId');
						recCrtTs += selectedRecords[i].get('recCrtTs');
					}else {
						termIds += selectedRecords[i].get('termId')+'|';
						recCrtTs += selectedRecords[i].get('recCrtTs') + '|';
					}
					
				}
				batchUpdTermWithRiskInfo(termIds,recCrtTs,termGridPanel,mchntId);
			}
	};
	
	var menuArr = new Array();
	menuArr.push(qryMenu); 
	menuArr.push(editMenu); 
	menuArr.push(bacthMenu); 

	// 终端信息列表
	var termGridPanel = new Ext.grid.GridPanel({
		title : '终端信息',
		iconCls : 'T301',
		region : 'center',
		frame : false,
		border : false,
		columnLines : true,
		stripeRows : true,
		store : termStore,
		sm : new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		cm : termColModel,
		tbar : menuArr,
		loadMask : {
			msg : '正在加载终端信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : termStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	termGridPanel.getSelectionModel().on(
			{
				'rowselect' : function() {
					// 行高亮
					Ext.get(termGridPanel.getView().getRow(termGridPanel.getSelectionModel().last)).frame();
					// 根据商户状态判断哪个编辑按钮可用
					rec = termGridPanel.getSelectionModel().getSelections();
					if(rec.length>0){
						termGridPanel.getTopToolbar().items.items[2].enable();
					}else termGridPanel.getTopToolbar().items.items[2].disable();
					
					if(rec.length==1){
						termGridPanel.getTopToolbar().items.items[0].enable();
						termGridPanel.getTopToolbar().items.items[1].enable();
					}else {
						termGridPanel.getTopToolbar().items.items[0].disable();
						termGridPanel.getTopToolbar().items.items[1].disable();
					}
				},
				'selectionchange' : function(){
					rec = termGridPanel.getSelectionModel().getSelections();
					if(rec.length>0){
						termGridPanel.getTopToolbar().items.items[2].enable();
					}else termGridPanel.getTopToolbar().items.items[2].disable();
					if(rec.length==1){
						termGridPanel.getTopToolbar().items.items[0].enable();
						termGridPanel.getTopToolbar().items.items[1].enable();
					}else {
						termGridPanel.getTopToolbar().items.items[0].disable();
						termGridPanel.getTopToolbar().items.items[1].disable();
					}
				}
			});
	//省市
	  var provinceStore = new Ext.data.JsonStore({
	    	fields: ['valueField','displayField'],
	    	root: 'data'
	    });
	    SelectOptionsDWR.getComboData('ADDR_PROVINCE',function(ret){
	    	provinceStore.loadData(Ext.decode(ret));
	    });
	    var cityStore = new Ext.data.JsonStore({
	    	fields: ['valueField','displayField'],
	    	root: 'data'
	    });
//	    SelectOptionsDWR.getComboDataWithParameter('CITY','',function(ret){
//	    	cityStore.loadData(Ext.decode(ret));
//	    });
	//新增商户时，选择费率档(费率类别为1时)的下拉表取值
	var rateIdStore1 = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('rateIdStore1',mchntId,function(ret){
		rateIdStore1.loadData(Ext.decode(ret));
	});

	//新增商户时，选择费率档(费率类别为2时)的下拉表取值
	var rateIdStore2 = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('rateIdStore2',mchntId,function(ret){
		rateIdStore2.loadData(Ext.decode(ret));
	});
	
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
		storeImg1.load({
			params: {
				start: 0,
				imagesId: mchntId,
				mcht : mchntId,
				upload:upload
			},callback: function(records, options, success){
				if(success){
					var record = storeImg1.getAt(0);
					if (record && record.data &&  record.data.fileName) {
						var imgSrc = Ext.contextPath + '/PrintImage?fileName='+mchntId+'/' + record.data.fileName;
						showImage(imgSrc, mchntId);
					} else {
						Ext.Msg.alert("提示","没有找到相关图片信息。");
					}
				}		
			}		

		});

//		var dataview1 = new Ext.DataView({
//			frame: true,
//	        store: storeImg1,
//	        tpl  : new Ext.XTemplate(
//	        		'<ul>',
//		            '<tpl for=".">',
//		                '<li class="phone">',
//		                	'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
//		                    	'<img id="{id}" width="120" height="90" src="' + Ext.contextPath +
//									'/PrintImage?fileName='+mchntId+'/{fileName}&width=120&height=90"/>',
//								'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
//							'</div>',
//		                '</li>',
//		            '</tpl>',
//		        '</ul>'
//	        ),
//	        id: 'phones1',
//	        itemSelector: 'li.phone',
//	        overClass   : 'phone-hover',
//	        singleSelect: true,
//	        multiSelect : true,
//	        autoScroll  : true
//	    });
//		var mchntForm = new Ext.FormPanel({
//			region: 'center',
//			iconCls: 'mchnt',
//			frame: true,
//			html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
//			//height:430,
////			modal: true,
//			height: Ext.getBody().getHeight(true),
//	        width: Ext.getBody().getWidth(true),
//	        autoScroll: true,
////			title: '商户入网申请',
//			
//			waitMsgTarget: true,
//			defaults: {
//				bodyStyle: 'padding-left: 10px'
//			},
//			items: [{
//				id: 'imageInfo',
////				width: 1035,
//				collapsible: true,
//				xtype: 'fieldset',
//				title: '影像信息',
//				layout: 'column',
////				autoScroll: true,
//				hidden:false,
//				defaults: {
//					bodyStyle: 'padding-left: 10px'
//				},
//				items : dataview1,
//				tbar: [{
//	    				xtype: 'button',
//						text: '刷新',
//						width: '80',
//						id: 'view1',
//						handler:function() {
//							storeImg1.reload({
//								params: {
//									start: 0,
//									imagesId: mchntId,
//									mcht : mchntId,
//									upload:upload
//								}
//							});
//						}
//					},{}]
//			}]
//		});
//		function showPIC(store,id){
//			detailWin.close();
//	 		var rec = store.getAt(id.substr(5,1)).data;
//	 		custom.resizeTo(rec.width, rec.height);
//	 		var src = document.getElementById('showBigPic').src;
//
//	 		if(src.indexOf(rec.fileName) == -1){
//		 		document.getElementById('showBigPic').src = "";
//		 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName='+ mchntId+'/'+ rec.fileName;
//		 		document.getElementById('showBigPic').onmousewheel = bigimg;
//		 		originalWidth = document.getElementById('showBigPic').width;
//		 		originalHeight = document.getElementById('showBigPic').height;
//	 		}
//	 		customEl.center();
//		    customEl.show(true);
//	 	}
//	 	function bigimg(){
//			var zoom = 0.05;
//			var rollDirect = event.wheelDelta;
//			if(rollDirect>0){
//				rollTimes++;
//			}
//			if(rollDirect<0){
//				rollTimes--;
//			}
//			if(this.height<50 && rollDirect<0){
//				return false;
//			}
//			var cwidth = originalWidth*(1+zoom*rollTimes);
//			var cheight = originalHeight*(1+zoom*rollTimes);
//			custom.resizeTo(cwidth, cheight);
//			return false;
//		}
//		storeImg1.on('load',function(){
//			for(var i=0;i<storeImg1.getCount();i++){
//				var rec = storeImg1.getAt(i).data;
//		    	Ext.get(rec.btBig).on('click', function(obj,val){
//		    		showPIC(storeImg1,val.id);
//		    	});
//			}
//		});
//		 var detailWin = new Ext.Window({
//		    	title: '商户影像信息',
//				initHidden: true,
//				header: true,
//				frame: true,
//				modal: true,
//				width: 1000,
//				height:350,
//				 autoScroll: true,
////				autoHeight: 680,
//				items: [mchntForm],
//				buttonAlign: 'center',
////				closable: false,
//				closeAction: 'close',
//				closable: true,
//				resizable: false
//		    });
//		 detailWin.show();
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
			{name: 'mchtMngMode',mapping: 'mchtMngMode'},
			{name: 'country',mapping: 'country'},
			{name: 'compliance',mapping: 'compliance'},
			{name: 'bankStatement',mapping: 'bankStatement'},
			{name: 'bankStatementReason',mapping: 'bankStatementReason'},
			{name: 'integral',mapping: 'integral'},
			{name: 'integralReason',mapping: 'integralReason'},
			{name: 'emergency',mapping: 'emergency'},
			{name : 'jFee',mapping : 'jFee'},
			{name : 'dFee',mapping : 'dFee'}, 
			{name : 'jMaxFee',mapping : 'jMaxFee'}, 
			{name : 'dMaxFee',mapping : 'dMaxFee'},
			//财务联系人，电话、电子邮件、经营内容、经营面积
			{name : 'finacontact',mapping : 'finacontact'},
			{name : 'finacommTel',mapping : 'finacommTel'},
			{name : 'finacommEmail',mapping : 'finacommEmail'},
			{name : 'busArea',mapping : 'busArea'},
			{name : 'busInfo',mapping : 'busInfo'},
			{name: 'addrProvince',mapping: 'addrProvince'},
			{name: 'cashFlag',mapping: 'cashFlag'},
			{name: 'cashTp',mapping: 'cashTp'},
			{name: 'feeTp',mapping: 'feeTp'},
			{name: 'feeInvstIntrst',mapping: 'feeInvstIntrst'},
			{name: 'feeAmt',mapping: 'feeAmt'},
			{name: 'feeInvstTy',mapping: 'feeInvstTy'},
			{name: 'shrPrftInvstCd',mapping: 'shrPrftInvstCd'},
			{name: 'shrPrftInvstTy',mapping: 'shrPrftInvstTy'},
			{name: 'blncHndlTy',mapping: 'blncHndlTy'},
			{name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
	        {name: 'creditDayAmt',mapping: 'creditDayAmt'},
	        {name: 'creditMonAmt',mapping: 'creditMonAmt'},
	        {name: 'debitSingleAmt',mapping: 'debitSingleAmt'},
	        {name: 'debitDayAmt',mapping: 'debitDayAmt'},
	        {name: 'debitMonAmt',mapping: 'debitMonAmt'},
	        {name: 'creditDayCount',mapping: 'creditDayCount'},
	        {name: 'creditMonCount',mapping: 'creditMonCount'},
	        {name: 'debitDayCount',mapping: 'debitDayCount'},
	        {name: 'debitMonCount',mapping: 'debitMonCount'},
	        {name: 'tblRiskParamMng.remark',mapping: 'remark'},		
	        {name: 'mchtPosAmt',mapping: 'mchtPosAmt'},			
	        {name: 'mchtAmt',mapping: 'mchtAmt'},		
	        {name: 'mchtDayAmt',mapping: 'mchtDayAmt'}			
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
			url: 'gridPanelStoreAction.asp?storeId=mchtDiff'
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
			{name: 'col37',mapping: 'col37'},
			{name: 'col38',mapping: 'col38'},
			{name: 'col39',mapping: 'col39'},
			{name: 'col40',mapping: 'col40'},
			{name: 'col41',mapping: 'col41'},
			{name: 'col42',mapping: 'col42'},
			{name: 'col43',mapping: 'col43'},
			{name: 'col44',mapping: 'col44'},
			{name: 'col45',mapping: 'col45'},
			{name: 'col46',mapping: 'col46'},
			{name: 'col47',mapping: 'col47'},
			{name: 'col48',mapping: 'col48'},
			{name: 'col49',mapping: 'col49'},
			{name: 'col50',mapping: 'col50'},
			{name: 'col51',mapping: 'col51'},
			{name: 'col52',mapping: 'col52'},
			{name: 'col53',mapping: 'col53'},
			{name: 'col54',mapping: 'col54'},
			{name: 'col55',mapping: 'col55'},
			{name: 'col56',mapping: 'col56'},
			{name: 'col57',mapping: 'col57'},
			{name: 'col58',mapping: 'col58'},
			{name: 'col59',mapping: 'col59'},
			{name: 'col60',mapping: 'col60'},
			{name: 'col61',mapping: 'col61'},
			{name: 'col62',mapping: 'col62'},
		])
	});

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

	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		autoScroll : true,
		height : 475,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
			//columnWidth : .99,
			xtype : 'panel',
			id:'refuseReason',
			layout : 'form',
			labelWidth: 0,
			style:'padding-bottom:10',
			hide: true,
			//width: 1,
			items : [reasongrid]
        },{
        	 title:'变更信息',
             xtype: 'fieldset',
             id: 'diffInf',
             frame: true,
             autoHeight: true,
			 layout:'column',
             items: []
        },{
        	title:'商户MCC',
            xtype: 'fieldset',
            id: 'manageMccInfo',
            frame: true,
			layout:'column',
            items: [{
			        	columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
							xtype : 'basecomboselect',
							baseParams : 'MCHNT_GRP_ALL',
							fieldLabel : '商户组别*',
							allowBlank : false,
							blanText:'请选择商户组别',
//							msgTarget:"under",
							hiddenName : 'mchtGrp',
							anchor : '90%',
							id : 'mchtGrpId',
							listeners : {
								'select' : function() {
									mchntMccStore.removeAll();
									var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
									Ext.getCmp('idmcc').setValue('');
									Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
									SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm
															.getForm().findField('mchtGrpId').getValue(),
													function(ret) {
										mchntMccStore.loadData(Ext.decode(ret));
													});
								
								},
								'change' : function() {
									mchntMccStore.removeAll();
									var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
									Ext.getCmp('idmcc').setValue('');
									Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
									SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',
													mchntForm.getForm().findField('mchtGrpId').getValue(),
													function(ret) {
														mchntMccStore.loadData(Ext.decode(ret));
													});
								}
							}
						}]
		        	},{
			        	columnWidth: .66,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
							xtype : 'combo',
							fieldLabel : '商户MCC*',
							store : mchntMccStore,
							displayField : 'displayField',
							valueField : 'valueField',
							mode : 'local',
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true,
							selectOnFocus : true,
							editable : true,
							allowBlank : false,
							lazyRender : true,
							blankText : '请选择商户MCC',
//							msgTarget:"under",
							id : 'idmcc',
							// readOnly: true,
							hiddenName : 'mcc',
							anchor : '90%',
							listeners : {
								'select' : function() {
									
								}
							}
						}]
					},{
			        	columnWidth: .33,
			        	xtype: 'panel',
				        layout: 'form',
			       		items: [{
							xtype : 'basecomboselect',
							baseParams : 'RISL_LVL',
							fieldLabel : '商户风险级别*',
							id : 'rislLvlId',
							hiddenName : 'rislLvl',
							allowBlank : false,
							blanText: '请选择商户风险级别',
//							msgTarget:"under",
							editable : false,
							anchor : '90%'
						}]
					},{

		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
							xtype : 'radiogroup',
							fieldLabel : '是否县乡商户*',
							width : 150,
							allowBlank : false,
							blanText:'请选择是否是县乡商户',
							msgTarget:'qtip',
							id : 'countryId',
							blankText : '至少选择一项',
							items : [
									{
										boxLabel : '是',
										inputValue : '0',
										name : 'country'
									},
									{
										boxLabel : '否',
										inputValue : '1',
										name : 'country',
									} ]
						}]
					
					},{


		        		columnWidth: .33,
			        	xtype: 'panel',
			        	layout: 'form',
		       			items: [{
							xtype : 'radiogroup',
							fieldLabel : '是否完全合规*',
							width : 150,
							allowBlank : false,
							blanText:'请选择是否完全合规',
							msgTarget:'qtip',
							id : 'complianceId',
							blankText : '至少选择一项',
							items : [
									{
										boxLabel : '是',
										inputValue : '0',
										name : 'compliance'
									},
									{
										boxLabel : '否',
										inputValue : '1',
										name : 'compliance',
										checked:true
									} ]
						}]
			}]
        },{/*
        	title:'账单协议信息',
            xtype: 'fieldset',
            id: 'billBaseInfo',
            frame: true,
			layout:'column',
            items: [{
    				//columnWidth: .2,
    				xtype: 'button',
    				title:'点击查看房屋租赁协议影像',
    				width: '60',
    				text: '查看房屋租赁协议影像',
    				id: 'click8',
    				handler:function() {
    					var upload='upload8';
    					checkImageInfo(upload);
    				}
            },{
            	columnWidth: .3,
	        	xtype: 'panel',
	        	layout: 'form',
	        	id: 'dispaly1',
       			items: [{
					xtype: 'displayfield',
					fieldLabel: '',
					id: 'dispaly1_1'
						}]
            },{

				//columnWidth: .2,
				xtype: 'button',
				title:'点击查看水煤电账单影像',
				width: '60',
				text: '查看水煤电账单影像',
				id: 'click9',
				handler:function() {
					var upload='upload9';
					checkImageInfo(upload);
				}
        
            }
    			]
         */},{/*
        	title:'登记信息',
            xtype: 'fieldset',
            id: 'manageBaseInfo',
            frame: true,
			layout:'column',
            items: []
        */},{
//        	xtype: 'tabpanel',
//        	id: 'tab',
//        	frame: true,
//            plain: false,
//            activeTab: 0,
//            height: 320,
//            deferredRender: false,
//            enableTabScroll: true,
//            labelWidth: 120,
//        	items:[{
                title:'注册信息',
                xtype: 'fieldset',
                id: 'basic',
                frame: true,
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
	        		columnWidth: .66,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'MCHNT_ATTR',
						fieldLabel: '商户类型',
						hiddenName: 'etpsAttr'
		        	}]
				},{
            		columnWidth: .4,
                	layout: 'form',
                	hidden:true,
            		items: [{
    			        xtype: 'combofordispaly',
    			        baseParams: 'CONN_TYPE',
    					fieldLabel: '连接类型',
    					hiddenName: 'connType'
    		        	}
            		]
//            	},{
//    	        	columnWidth: .33,
//    	        	id: 'otherNoPanel',
//    	        	xtype: 'panel',
//    	        	layout: 'form',
//    	       		items: [{
//    	       			xtype: 'displayfield',
//    					fieldLabel: '映射商户号',
//    					vtype: 'isOverMax',
//    					id: 'idOtherNo',
//    					anchor: '90%'
//    				}]
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
    	        	columnWidth: .66,
    	        	xtype: 'panel',
    		        layout: 'form',
    	       		items: [{
    			        xtype: 'combofordispaly',
    			        baseParams: 'BRH_BELOW_BRANCH',
    					fieldLabel: '合作伙伴',
    					hiddenName: 'bankNo',
    					anchor: '90%'
    		        }]
    			},{
    	        	columnWidth: .33,
    	        	xtype: 'panel',
    	        	layout: 'form',
    	       		items: [{
    	       			xtype: 'displayfield',
    					fieldLabel: '注册名称',
    					id: 'mchtNm',
    					anchor: '90%'
    				}]
    			},{
    	        	columnWidth: .66,
    	        	layout: 'form',
    	        	xtype: 'panel',
    	       		items: [{
    			        	xtype: 'displayfield',
    						fieldLabel: '中文简称',
    						id: 'mchtCnAbbr',
    						anchor: '90%'
    		        	}]
    			},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide1',
	       			items: [{
			        	xtype: 'displayfield',
			        	width: 380,
						fieldLabel: '注册地址',
						id: 'compaddr',
						name: 'compaddr'
		        	}]
				},{
	        		columnWidth: .66,
		        	xtype: 'panel',
		        	id:'accountHide2',
		        	layout: 'form',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '注册资金',
						id: 'busAmt',
						name: 'busAmt'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'licenceNoPID',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '营业执照编号',
						id: 'licenceNo'
		        	}]
				},{
					columnWidth: .25,
					layout: 'form',
					id:'accountHide5',
					items: [{
						xtype: 'displayfield',
						id :'licenceEndDate',
//						width:150,
						name: 'licenceEndDate',
						//vtype: 'daterange',
			            fieldLabel: '营业执照有效期',
					}]											
				},{
					columnWidth: .4,
		        	xtype: 'panel',
		        	id:'accountHide4',
		        	layout: 'form',
		        	items: [{
					xtype: 'button',
					title:'点击查看营业执照影像',
					width: '150',
					text: '查看营业执照影像',
					id: 'click1',
					handler:function() {
						var upload='upload1';
						checkImageInfo(upload);
					}
					}]
				},{
					columnWidth: 1,
		        	xtype: 'panel',
		        	height:0.5,
		        	layout: 'form',
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'faxNoPID',
	       			items: [{
						xtype: 'displayfield',
						fieldLabel: '税务登记证号码',
						id: 'faxNo'
		        	}]
				},{
					columnWidth: .25,
		        	xtype: 'panel',
		        	id:'accountHide7',
		        	layout: 'form',
		        	items: [{
					xtype: 'button',
					title:'点击查看税务登记证影像',
					width: '150',
					text: '查看税务登记证影像',
					id: 'click2',
					handler:function() {
						var upload='upload2';
						checkImageInfo(upload);
					}}]
				},{
					xtype: 'panel',
		        	layout: 'form',
		        	id:'accountHide8',
		        	columnWidth: .4,
		        	items: [{
					xtype: 'button',
					title:'点击查看组织机构代码影像',
					width: '150',
					text: '查看组织机构代码影像',
					id: 'click6',
					handler:function() {
						var upload='upload6';
						checkImageInfo(upload);
					}}]
				
				},{
					columnWidth: 1,
		        	xtype: 'panel',
		        	height:0.5,
		        	layout: 'form',
				},
    			{/*
    	        	columnWidth: .33,
    	        	layout: 'form',
    	        	hidden:true,
    	        	xtype: 'panel',
    	       		items: [{
    			        	xtype: 'displayfield',
    						fieldLabel: '英文名称',
    						id: 'engName',
    						anchor: '90%'
    		        	}]
    			*/},{/*
    	        	columnWidth: .33,
    	        	layout: 'form',
    	        	xtype: 'panel',
    	        	hidden:true,
    	       		items: [{
    						xtype: 'combofordispaly',
    			        	baseParams: 'CITY_CODE',
    						fieldLabel: '所在地区',
    						hiddenName: 'areaNo',
    						anchor: '90%'
    		        	}]
    			*/},{/*
            		columnWidth: .50,
    	        	xtype: 'panel',
    	        	layout: 'form',
    	        	style:'display:none',
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
    			*/}
    			,{/*
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '邮政编码',
						id: 'postCode',
						name: 'postCode'
		        	}]
				*/},{
	        		columnWidth: .33,
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
	        		columnWidth: .66,
		        	xtype: 'panel',
		        	layout: 'form',
		        	id: 'artifCertifTpPID',
	       			items: [{
			        	xtype: 'combofordispaly',
			        	baseParams: 'CERTIFICATE',
						fieldLabel: '法人证件类型',
						hiddenName: 'artifCertifTp'
		        	}]
				},{
	        		columnWidth: .33,
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
					columnWidth: .25,
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
					xtype: 'panel',
		        	layout: 'form',
		        	columnWidth: .4,
		        	items: [{
					xtype: 'button',
					title:'点击查看法人证件影像',
					width: '150',
					text: '查看证件影像',
					id: 'click4',
					handler:function() {
						var upload='upload3';
						checkImageInfo(upload);
					}}]
				},{
					columnWidth: 1,
		        	xtype: 'panel',
		        	height:0.5,
		        	layout: 'form',
				},{/*
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '业务联系人姓名',
						id: 'contact',
						name: 'contact'
		        	}]
				*/},{/*
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '业务联系人电话',
						id: 'commTel',
						name: 'commTel'
		        	}]
				*/},{/*
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '业务联系人邮箱',
						id: 'commEmail',
						name: 'commEmail'
		        	}]
				*/},{
	        		columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '财务联系人姓名',
						id: 'finacontact',
						name: 'finacontact'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	layout: 'form',
		        	hidden:true,
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '财务联系人电话',
						id: 'finacommTel',
						name: 'finacommTel'
		        	}]
				},{
	        		columnWidth: .6,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '财务联系人邮箱',
						id: 'finacommEmail',
						name: 'finacommEmail'
		        	}]
				},{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       			
	       				xtype: 'combofordispaly',
	       				baseParams: 'OPR_ID',
						fieldLabel: '初审人',
						hiddenName: 'updOprId'
							
					
//			        	xtype: 'displayfield',
//						fieldLabel: '企业电话',
//						id: 'electrofax',
//						name: 'electrofax'
		        	}]
				},{
            		columnWidth: .66,
    	        	xtype: 'panel',
    	        	layout: 'form',
           			items: [{
           				xtype: 'combofordispaly',
			        	baseParams: 'BRH_BELOW_ID',
						fieldLabel: '签约网点',
						hiddenName: 'agrBr'
//                        xtype: 'combofordispaly',
//                        fieldLabel: '商户业务类型',
//    					displayField: 'displayField',
//    					valueField: 'valueField',
//                        id: 'mchtFunctionId',
//                        hiddenName: 'mchtFunction',
//                        store: new Ext.data.ArrayStore({
//                            fields: ['valueField','displayField'],
//                            data: [['0','T+0业务'],['1','T+1业务']]
//                        })
                    }]
    			},{
	        		columnWidth: .4,
		        	xtype: 'panel',
		        	hidden:true,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '企业传真',
						id: 'fax',
						name: 'fax'
		        	}]
				}]
            },{
            	xtype:'fieldset',
                title:'经营信息',
                layout:'column',
                id: 'append',
                frame: true,
                items: [/*{
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
		        	defaults: {
	        			xtype: 'displayfield'
	        		},
	       			items: [{
						fieldLabel: '经营名称',
						id: 'compNm',
						name: 'compNm'
					},{
			    	    
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
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '装机地址',
						id: 'addr',
						name: 'addr'
		        	}]
				},{
					columnWidth: .17,
					xtype: 'button',
					title:'点击查看门店影像',
					width: '45',
					text: '查看门店影像',
					id: 'click3',
					handler:function() {
						var upload='upload4';
						checkImageInfo(upload);
					}
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
				}*/{
					columnWidth: .99,
					layout: 'form',
					id: 'mchtNmPanel',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '经营名称',
						maxLength: '60',
						vtype: 'isOverMax',
						id: 'compNm',
						name:'compNm',
						//anchor: '90%',
						width:550
					}]
				},{				
					columnWidth: .15,
					layout: 'form',
					items: [{
						layout: 'column',
						xtype: 'combofordispaly',
			        	baseParams: 'ADDR_PROVINCE',
						fieldLabel: '装机地址<font color=red>*</font>',
						store: provinceStore,
						hiddenName : 'tblMchtBaseInfTmpTmp.addrProvince',
						displayField: 'displayField',
						valueField: 'valueField',
						id:'provinceAddress',
						width:180,
						readOnly : true,
						listeners: {
							'select': function() {
								cityStore.removeAll();
								var province = Ext.getCmp('provinceAddress').getValue();

								SelectOptionsDWR.getComboDataWithParameter('ADDR_CITY',province,function(ret){
									cityStore.loadData(Ext.decode(ret));
								});
							},'change': function() {
								Ext.getCmp('city').setValue('');
							}
						}
				}]
				},{/*
					columnWidth: .03,
					layout: 'form',
					html:'省'
				*/},{
					columnWidth: .85,
					layout: 'form',
					lableWidth:0,
					lablePad:0,
					items: [{
					xtype: 'combofordispaly',
		        	baseParams: 'ADDR_CITY',
					fieldLabel: '',
					store: cityStore,
					displayField: 'displayField',
					valueField: 'valueField',
					id:'cityAddress',
					hiddenName : 'tblMchtBaseInfTmpTmp.areaNo',
					width:180,
					readOnly : true,
					listeners: {}}]
	        	
				
				},{
					columnWidth: 1,
		        	xtype: 'panel',
		        	height:0.5,
		        	layout: 'form',
				},{
					columnWidth: .58,
		        	layout: 'form',
		        	items: [{
					xtype: 'displayfield',
					fieldLabel: '',
					//anchor: '90%',
					width:550,
					maxLength: 60,
					vtype: 'isOverMax',
					id: 'addr',
//					allowBlank : false,
//					blankText:'请输入详细地址',
//					emptyText:'详细地址',
					name:'addr'
		        	}]
				},{

					xtype: 'panel',
		        	layout: 'form',
		        	columnWidth: .4,
		        	items: [{
					xtype: 'button',
					title:'点击查看门店影像',
					width: '150',
					text: '查看门店影像',
					id: 'click44',
					handler:function() {
						var upload='upload4';
						checkImageInfo(upload);
					}}]
				
				},{
					columnWidth: 1,
		        	xtype: 'panel',
		        	height:0.5,
		        	layout: 'form',
				},{
					
						columnWidth: .99,
						layout: 'form',
						items: [{
							xtype: 'displayfield',
							fieldLabel: '经营内容',
							maxLength: '60',
							vtype: 'isOverMax',
							id: 'busInfo',
							name:'busInfo',
							//anchor: '90%'
							width:550
					}]
				},{
	        		columnWidth: .17,
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						fieldLabel: '营业时间(hh:mm)',
						width:150,
//                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '时间输入错误',
						id: 'openTime',
						name: 'openTime',
						value: '00:00'
		        	}]
				},{
	        		columnWidth: .16,
		        	layout: 'form',
		        	labelPad:20,
		        	labelWidth:30,
	       			items: [{
	       				xtype: 'displayfield',
						fieldLabel: '至',
						width:150,
//                        maxLength: 5,
                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
						regexText: '时间输入错误',
						value: '23:59',
						id: 'closeTime',
						name: 'closeTime'
		        	}]
				},{
						columnWidth: .66,
						layout: 'form',
						items: [{
							xtype: 'displayfield',
							width: 150,
							fieldLabel: '经营面积(m²)',
							maxLength: 10,
							vtype: 'isOverMax',
							id: 'busArea',
							name:'busArea'
						}]
				},
				{
//					columnWidth : .99,
//					layout : 'form',
//					hidden:true,
//					items : [{
//						xtype : 'dynamicCombo',
//						methodName : 'getAreaCode',
//						fieldLabel : '所在地区',
//						hiddenName : 'areaNo',
//						id : 'areaNoId',
//						allowBlank : true,
//						width:260
//					}]
				},{
					columnWidth: .17,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '业务联系人',
						width:150,
						maxLength: 30,
						vtype: 'isOverMax',
						id: 'contact',
						name:'contact'
					}]
				},{
					columnWidth: .16,
					layout: 'form',
					labelPad:20,
		        	labelWidth:30,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '电话',
						width:150,
						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
						regexText:'长度不够，电话必须是7~9位',
						maxLength: 18,
						vtype: 'isOverMax',
						id: 'commTel',
						name:'commTel'
					}]
				},{
					columnWidth: .66,
					layout: 'form',
					items: [{
						xtype: 'displayfield',
							fieldLabel: '企业邮箱',
							width:150,
							maxLength: 40,
							vtype: 'isOverMax',
							id: 'commEmail',
							name: 'commEmail',
							vtype: 'email'
					}]
				}			
//				,{
//					columnWidth: .66,
//					layout: 'form',
//					id: 'mchtNmPanel',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '经营名称',
//						maxLength: '60',
//						vtype: 'isOverMax',
//						id: 'compNm',
//						name:'compNm',
//						anchor: '90%'
////						width:476
//					}]
//				},{
//	        		columnWidth: .33,
//		        	layout: 'form',
//	       			items: [{
//			        	xtype: 'displayfield',
//						fieldLabel: '营业开始时间(hh:mm)',
//                        maxLength: 5,
//                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
//						regexText: '时间输入错误',
//						id: 'openTime',
//						name: 'openTime',
//						value: '00:00'
//		        	}]
//				},{				
//					columnWidth: .66,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '装机地址',
////						width:476,
//						anchor: '90%',
//						maxLength: 60,
//						vtype: 'isOverMax',
//						id: 'addr',
//						name:'addr'
//					}]
//				},{
//	        		columnWidth: .33,
//		        	layout: 'form',
//	       			items: [{
//	       				xtype: 'displayfield',
//						fieldLabel: '营业结束时间(hh:mm)',
//                        maxLength: 5,
//                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
//						regexText: '时间输入错误',
//						value: '23:59',
//						id: 'closeTime',
//						name: 'closeTime'
//		        	}]
//				},{
//					columnWidth: .99,
//		        	layout: 'form',
//		        	xtype: 'panel',
//		       		items: [{
//							xtype: 'combofordispaly',
//				        	baseParams: 'CITY_CODE',
//							fieldLabel: '所在地区',
//							hiddenName: 'areaNo',
//							width:260
//			        	}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '业务联系人',
//						width:150,
//						maxLength: 30,
//						vtype: 'isOverMax',
//						id: 'contact',
//						name:'contact'
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '业务联系人电话',
//						width:150,
//						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//						regexText:'长度不够，电话必须是7~9位',
//						maxLength: 18,
//						vtype: 'isOverMax',
//						id: 'commTel',
//						name:'commTel'
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//							fieldLabel: '业务联系人邮箱',
//							width:150,
//							maxLength: 40,
//							vtype: 'isOverMax',
//							id: 'commEmail',
//							name: 'commEmail',
//							vtype: 'email'
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '财务联系人',
//						width:150,
//						maxLength: 30,
//						vtype: 'isOverMax',
//						id: 'finacontact',
//						name:'finacontact'
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//						fieldLabel: '财务联系人电话',
//						width:150,
//						regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//						regexText:'长度不够，电话必须是7~9位',
//						maxLength: 18,
//						vtype: 'isOverMax',
//						id: 'finacommTel',
//						name:'finacommTel'
//					}]
//				},{
//					columnWidth: .33,
//					layout: 'form',
//					items: [{
//						xtype: 'displayfield',
//							fieldLabel: '财务联系人邮箱',
//							width:150,
//							maxLength: 40,
//							vtype: 'isOverMax',
//							id: 'finacommEmail',
//							name: 'finacommEmail',
//							vtype: 'email'
//					}]
//				}			
				]
			},{/*
				id : 'businessInfo',
				xtype : 'fieldset',
				title : '营业状况',
				layout : 'column',
                readOnly: true,
                frame: true,
				items : [{
							columnWidth : .66,
							layout : 'form',
							items : [ {
								xtype: 'displayfield',
								labelStyle: 'padding-left: 5px',
								fieldLabel : '经营内容',
								id : 'busInfo'
							} ]
						},{
							columnWidth : .33,
							layout : 'form',
							items : [ {
								xtype : 'displayfield',
								width : 150,
								fieldLabel : '经营面积(m²)',
								id : 'busArea',
							} ]
						}
					]
			*/},{
				xtype:'fieldset',
                title:'账户信息',
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
	        		columnWidth: .33,
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
	        		columnWidth: .66,
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
	        		columnWidth: .33,
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
					columnWidth: .66,
		        	xtype: 'panel',
		        	layout: 'form',
		       			items: [{
						xtype: 'button',
						title:'点击查看银行卡或开户许可证',
						width: '150',
						text: '查看银行卡或开户许可证影像',
						id: 'click10',
						handler:function() {
							var upload='upload10';
							checkImageInfo(upload);
						}
		       			}]
				
				},{
	        		columnWidth: 1,
		        	xtype: 'panel',
		        	layout: 'form',
		        	height:0.5
		        },{
	        		columnWidth: .33,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
			        	xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '账户开户行号',
						width:200,
						id: 'openStlno'
						
		        	}]
				},{
				columnWidth: .66,
	        	xtype: 'panel',
	        	layout: 'form',
       			items: [{
		        	xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: '账户开户行名称*',
					maxLength: 80,
					vtype: 'isOverMax',
					width:350,
					id: 'settleBankNm'
       			}]
			},{
					columnWidth: .33,
					layout: 'form',
					items: [{
						xtype : 'combofordispaly',
						labelStyle: 'padding-left: 5px',
						fieldLabel : '结算类型',
						id : 'mchtFunctionId',
						hiddenName : 'mchtFunctionType',
//						allowBlank : false,
//						blankText:'至少选择一项',
						width: 180,
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField',
									'displayField' ],
							data : [ [ '0', 'T+N' ],
									[ '1', '周期结算' ] ]
						}),
						listeners: {}
		}]
			
			},{
				columnWidth: .66,
				xtype: 'panel',
	        	layout: 'form',
				id : 'mchtFunctionId0',
				hidden:true,
				items: [{
					xtype: 'displayfield',
					labelStyle: 'padding-left: 5px',
					fieldLabel: 'T+N',
					width:80,
//					anchor: '90%',
					maxLength: 3,
					vtype: 'isNumber',
					regex: /^(([0-9]{1})|([1-9]{1}\d{0,2}))$/,
					regexText: '请输入0-999的数字',
					id: 'T_N',
					name:'T_N'
				}]
			
			},{
				columnWidth: .66,
				hidden:true,
				xtype: 'panel',
	        	layout: 'form',
				id : 'mchtFunctionId1',
				items: [{
					xtype : 'combofordispaly',
					fieldLabel : '周期结算',
					labelStyle: 'padding-left: 5px',
					id : 'periodId',
//					editable:false,
					hiddenName : 'period',
//					allowBlank : false,
//					blankText:'至少选择一项',
					width: 80,
					//value:'0',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField','displayField' ],
						data : [ 	[ '0', '周结' ],
									[ '1', '月结' ],
									[ '2', '季结' ]]
					}),
					listeners: {}
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
				//************************费率信息************************///
				id: 'fee',
//				width: 1035,
				collapsible: true,
				xtype: 'fieldset',
				title: '费率信息',
				layout: 'column',
				defaults: {
					//bodyStyle: 'padding-left: 10px'
				},
				items: [{

					columnWidth: 100,
					layout: 'form',
					items: [{
					xtype : 'combofordispaly',
					fieldLabel : '手续费方式',
					id : 'feeId',
					hiddenName: 'tblMchtBaseInfTmpTmp.poundage',//新加字段对应数据库mcht_bak2
					width: 170,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [['0', '按比例' ], [ '1', '比例+封顶' ]/*, [ '2', '固定金额' ]*/]
					}),
					listeners: {
						'select': function() {
							var type = this.getValue().trim();
							if (type=="0") {
				        		Ext.getCmp('jFee').show();
				        		Ext.getCmp('jFee').allowBlank = false;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').hide();
				        		Ext.getCmp('forHide4').show();
				        		Ext.getCmp('forHide1').hide();
				        		Ext.getCmp('jMaxFee').setValue('');
				        		Ext.getCmp('jMaxFee').allowBlank = true;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').show();
				        		Ext.getCmp('dFee').allowBlank = false;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').hide();
				        		Ext.getCmp('forHide3').show();
				        		Ext.getCmp('forHide2').hide();
				        		Ext.getCmp('dMaxFee').setValue('');
				        		Ext.getCmp('dMaxFee').allowBlank = true;
				        		Ext.getCmp('dMaxFee').isValid();
							}else if ("2"==type) {
								Ext.getCmp('jFee').setValue('');
				        		Ext.getCmp('jFee').hide();
				        		Ext.getCmp('forHide3').hide();
				        		Ext.getCmp('forHide2').show();
				        		Ext.getCmp('jFee').allowBlank = true;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').show();
				        		Ext.getCmp('jMaxFee').allowBlank = false;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').setValue('');
				        		Ext.getCmp('dFee').hide();
				        		Ext.getCmp('forHide4').hide();
				        		Ext.getCmp('forHide1').show();
				        		Ext.getCmp('dFee').allowBlank = true;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').show();
				        		Ext.getCmp('dMaxFee').allowBlank = false;
				        		Ext.getCmp('dMaxFee').isValid();
							}else {
								Ext.getCmp('forHide1').show()
								Ext.getCmp('forHide2').show()
								Ext.getCmp('forHide3').show()
								Ext.getCmp('forHide4').show()
				        		Ext.getCmp('jFee').show();
				        		Ext.getCmp('jFee').allowBlank = false;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').show();
				        		Ext.getCmp('jMaxFee').allowBlank = false;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').show();
				        		Ext.getCmp('dFee').allowBlank = false;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').show();
				        		Ext.getCmp('dMaxFee').allowBlank = false;
				        		Ext.getCmp('dMaxFee').isValid();
							}
						},
						'change': function() {
							var type = this.getValue().trim();
							if (type=="0") {
				        		Ext.getCmp('jFee').show();
				        		Ext.getCmp('jFee').allowBlank = false;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').hide();
				        		Ext.getCmp('forHide4').show();
				        		Ext.getCmp('forHide1').hide();
				        		Ext.getCmp('jMaxFee').setValue('');
				        		Ext.getCmp('jMaxFee').allowBlank = true;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').show();
				        		Ext.getCmp('dFee').allowBlank = false;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').hide();
				        		Ext.getCmp('forHide3').show();
				        		Ext.getCmp('forHide2').hide();
				        		Ext.getCmp('dMaxFee').setValue('');
				        		Ext.getCmp('dMaxFee').allowBlank = true;
				        		Ext.getCmp('dMaxFee').isValid();
							}else if ("2"==type) {
								Ext.getCmp('jFee').setValue('');
				        		Ext.getCmp('jFee').hide();
				        		Ext.getCmp('forHide3').hide();
				        		Ext.getCmp('forHide2').show();
				        		Ext.getCmp('jFee').allowBlank = true;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').show();
				        		Ext.getCmp('jMaxFee').allowBlank = false;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').setValue('');
				        		Ext.getCmp('dFee').hide();
				        		Ext.getCmp('forHide4').hide();
				        		Ext.getCmp('forHide1').show();
				        		Ext.getCmp('dFee').allowBlank = true;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').show();
				        		Ext.getCmp('dMaxFee').allowBlank = false;
				        		Ext.getCmp('dMaxFee').isValid();
							}else {
								Ext.getCmp('forHide1').show()
								Ext.getCmp('forHide2').show()
								Ext.getCmp('forHide3').show()
								Ext.getCmp('forHide4').show()
				        		Ext.getCmp('jFee').show();
				        		Ext.getCmp('jFee').allowBlank = false;
				        		Ext.getCmp('jFee').isValid();
				        		Ext.getCmp('jMaxFee').show();
				        		Ext.getCmp('jMaxFee').allowBlank = false;
				        		Ext.getCmp('jMaxFee').isValid();
				        		
				        		Ext.getCmp('dFee').show();
				        		Ext.getCmp('dFee').allowBlank = false;
				        		Ext.getCmp('dFee').isValid();
				        		Ext.getCmp('dMaxFee').show();
				        		Ext.getCmp('dMaxFee').allowBlank = false;
				        		Ext.getCmp('dMaxFee').isValid();
							}
						}
					}
				}]
				
				},{
					columnWidth: .08,
					layout: 'form',
					html:'借记卡:',
					//width:40
				},{
					columnWidth: .06,
					layout: 'form',
					labelWidth:10,
					labelPad:10,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '',
//						allowBlank : true,
//						maxLength: 40,
//						vtype: 'isOverMax',
//						regex: /^([+-]?)\d*\.?\d+$/,
//						regexText:'请输入数字',
						id: 'jFee',
						name: 'jFee',
						//width:50
						anchor: '99%'
					}]
				
				},{
					columnWidth: .03,
					id:'forHide3',
					layout: 'form',
					html:'<font height=20 >%</font>'
				},{
					columnWidth: .05,
					layout: 'form',
					labelWidth:10,
					labelPad:10,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '',
//						allowBlank : true,
//						maxLength: 40,
//						vtype: 'isOverMax',
//						regex: /^([+-]?)\d*\.?\d+$/,
//						regexText:'请输入数字',
						id: 'jMaxFee',
						name: 'jMaxFee',
						width:50
						//anchor: '99%'
					}]
				},{
					columnWidth: .03,
					id:'forHide2',
					layout: 'form',
					html:'元'
				},{
					columnWidth: .08,
					layout: 'form',
					html:'&nbsp;'
				},{
					columnWidth: .08,
					layout: 'form',
					html:'贷记卡:'
				},{
					columnWidth: .06,
					layout: 'form',
					labelWidth:10,
					labelPad:10,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '',
//						allowBlank : true,
//						maxLength: 40,
//						vtype: 'isOverMax',
//						regex: /^([+-]?)\d*\.?\d+$/,
//						regexText:'请输入数字',
						id: 'dFee',
						name: 'dFee',
						anchor: '99%'
					}]
				},{
					columnWidth: .03,
					layout: 'form',
					id:'forHide4',
					html:'%'
				},{
					columnWidth: .05,
					layout: 'form',
					labelWidth:10,
					labelPad:10,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '',
//						maxLength: 40,
						width:50,
//						allowBlank : true,
//						vtype: 'isOverMax',
//						regex: /^([+-]?)\d*\.?\d+$/,
//						regexText:'请输入数字',
						id: 'dMaxFee',
						name: 'dMaxFee',
						//anchor: '99%'
					}]
				},{
					columnWidth: .03,
					id:'forHide1',
					layout: 'form',
					html:'元'
				}]
				
			},{
				id: 'profitInfo',
//				width: 1035,
				xtype: 'fieldset',
				title: '分润信息',
				layout: 'column',
				defaults: {
					//bodyStyle: 'padding-left: 10px'
				},
				items: [
						{
							columnWidth: 1,
							layout: 'form',
							items: [{
							xtype : 'combofordispaly',
							fieldLabel : '分润方式',
							id : 'profitType_id',
							hiddenName: 'profitType',
							width: 170,
							store : new Ext.data.ArrayStore({
								fields : [ 'valueField', 'displayField' ],
								data : [['0', '按比例' ], [ '1', '比例+封顶' ]/*, [ '2', '固定金额' ]*/]
							}),
							listeners: {
								'select': function() {},
								'change': function() {
									var profitType = Ext.getCmp('profitType_id').getValue().trim();
									if (profitType=="0") {
						        		Ext.getCmp('forHide5').hide();
						        		Ext.getCmp('forHide6').show();
									}else if ("2"==profitType) {
						        		Ext.getCmp('forHide5').show();
						        		Ext.getCmp('forHide6').hide();
									}else {
						        		Ext.getCmp('forHide5').show();
						        		Ext.getCmp('forHide6').show();
									}
								}
							}
						}]
						},{
						columnWidth: .33,
						id:'forHide5',
						layout: 'form',
						items: [{
							xtype: 'combofordispaly',
							id: 'rateId_id',
							hiddenName : 'rateId',
							store: rateIdStore1,
							fieldLabel : '比例',
							valueField: 'valueField',
							displayField: 'displayField',
							width :170,
						}]
						},{
						columnWidth: .33,
						id:'forHide6',
						labelWidth:43,
						labelPad:50,
						layout: 'form',
						items: [{
							xtype: 'combofordispaly',
							id: 'capId',
							hiddenName : 'cap_id',
							store: rateIdStore2,
							fieldLabel : '封顶',
							valueField: 'valueField',
							displayField: 'displayField',
							width : 182,
						}]
						}
				        ]
            },{
				id: 'withdrawInfo',
				collapsible: true,
				xtype: 'fieldset',
				hidden:true,
				title: '提现信息',
				layout: 'column',
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [{
						columnWidth: .33,
						labelWidth : 160,
						layout: 'form',
						id : 'cashFlagForHide',
						items: [{
						xtype : 'combofordispaly',
						fieldLabel : '是否开通提现',
						id : 'cashFlag',
						hiddenName: 'tblMchtBaseInfTmpTmp.cashFlag',
						width: 165,
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [ [ '0', '否' ],['1', '是' ]]
							}),
							listeners :{}
						}]
				
				},{
						columnWidth: .66,
						layout: 'form',
						labelWidth : 160,
						id : 'cashTpForHide',
						hidden:true,
						items: [{
						xtype : 'combofordispaly',
						fieldLabel : '商户提现方式',
						id : 'cashTp',
						hiddenName: 'tblMchtCashInfTmpTmp.cashTp',
						width: 165,
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [['1', '主动按金额' ]/*, [ '2', '单笔自动' ]*/]
							})
						}]
				
				},{
						columnWidth: .33,
						layout: 'form',
						labelWidth : 160,
						id : 'feeTpForHide',
						hidden:true,
						items: [{
						xtype : 'combofordispaly',
						fieldLabel : '提现手续费方式',
						id : 'feeTp',
						hiddenName: 'tblMchtCashInfTmpTmp.feeTp',
						width: 165,
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [['1', '按比例' ], [ '2', '单笔固定金额' ]]
							}),
						listeners : {}
						}]
				
				},{
					columnWidth: .66,
					layout: 'form',
					labelWidth : 160,
					id : 'feeInvstTyForHide',
					hidden:true,
					items: [{
					xtype : 'combofordispaly',
					fieldLabel : '垫资天数计算方式',
					id : 'feeInvstTy',
					hiddenName: 'tblMchtCashInfTmpTmp.feeInvstTy',
					width: 165,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [['1', '按自然日（D）' ], [ '2', '按工作日（T）' ]]
						})
					}]
			},{
					columnWidth: .33,
					layout: 'form',
					labelWidth : 160,
					id: 'feeInvstIntrstForHide',
					hidden:true,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '垫资日息(%)',
						id: 'feeInvstIntrst',
						name: 'tblMchtCashInfTmpTmp.feeInvstIntrst',
						value:'',
						width:165
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					labelWidth : 160,
					id: 'feeAmtForHide',
					hidden:true,
					items: [{
						xtype: 'displayfield',
						fieldLabel: '单笔手续费金额(元)',
						id: 'feeAmt',
						name: 'tblMchtCashInfTmpTmp.feeAmt',
						value:'',
						width:165
					}]
				},{
					columnWidth: .66,
					layout: 'form',
					labelWidth : 160,
					id : 'shrPrftInvstCdForHide',
					hidden:true,
					items: [{
					xtype : 'combofordispaly',
					fieldLabel : '提现分润方式',
					id : 'shrPrftInvstCd',
					hiddenName: 'tblMchtCashInfTmpTmp.shrPrftInvstCd',
					width: 165,
					store : rateIdStore3
					}]
				},{
					columnWidth: .33,
					layout: 'form',
					labelWidth : 160,
					id : 'shrPrftInvstTyForHide',
					hidden:true,
					items: [{
					xtype : 'combofordispaly',
					fieldLabel : '分润垫资天数计算方式',
					id : 'shrPrftInvstTy',
					hiddenName: 'tblMchtCashInfTmpTmp.shrPrftInvstTy',
					width: 165,
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [['1', '按自然日（D）' ], [ '2', '按工作日（T）' ]]
						})
					}]
			},{
				columnWidth: .66,
				layout: 'form',
				labelWidth : 160,
				id : 'blncHndlTyForHide',
				hidden:true,
				items: [{
				xtype : 'combofordispaly',
				fieldLabel : '未提现余额处理',
				id : 'blncHndlTy',
				hiddenName: 'tblMchtCashInfTmpTmp.blncHndlTy',
				width: 165,
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [['1', '自动结算' ]/*, [ '2', '手工提现' ]*/]
					})
				}]
		}]
			},{
				id: 'statement_integral_Info',
//				width: 1035,
				hidden:false,
				collapsible: true,
				xtype: 'fieldset',
				title: '积分对账单信息',
				layout: 'column',
				defaults: {
					//bodyStyle: 'padding-left: 10px'
				},
				items: [{
					columnWidth : .33,
					layout : 'form',
					items : [ {
						xtype : 'combofordispaly',
						fieldLabel : '是否需要对账单',
						width : 120,
						msgTarget:"under",
						id : 'bankStatement',
						hiddenName: 'tblMchtSettleInfTmpTmp.bankStatement',
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [['0', '是' ], [ '1', '否' ]]
						}),
						value:'1',
						listeners : {
							'select': function() {
								var checked=Ext.getCmp('bankStatement').getValue();
									     if(checked=='1'){
									     Ext.getCmp("bankStatementReasonId").show();
									    	 }
									     if(checked=='0'){
									    	 Ext.getCmp("bankStatementReasonId").hide();
									    	 }
									     },
							'change': function() {
									var checked=Ext.getCmp('bankStatement').getValue();
											if(checked=='0'){
												Ext.getCmp("bankStatementReasonId").show();
												Ext.getCmp('bankStatementReason').allowBlank = false;
													    	 }
											 if(checked=='1'){
												Ext.getCmp("bankStatementReasonId").hide();
												Ext.getCmp('bankStatementReason').allowBlank = true;
											}
													     
									     }
									}
								}]
				},{
					columnWidth : .25,
					layout : 'form',
					items : [ {
						xtype : 'combofordispaly',
						fieldLabel : '是否积分',
						width : 120,
						msgTarget:"under",
						hiddenName: 'tblMchtSettleInfTmpTmp.integral',
						id : 'integral',
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [['0', '是' ], [ '1', '否' ]]
						}),
						value:'1',
						listeners : {
							'select' : function(){
								var checked=Ext.getCmp('integral').getValue();
									if(checked=='0'){
									 Ext.getCmp("integralReasonId").show();
									 Ext.getCmp('integralReason').allowBlank = false;
									 }
									 if(checked=='1'){
							    		 Ext.getCmp("integralReasonId").hide();
							    		 Ext.getCmp('integralReason').allowBlank = true;
							    	 }     },
							 'change': function() {
									var checked=Ext.getCmp('integral').getValue();
										if(checked=='0'){
										 Ext.getCmp("integralReasonId").show();
										 Ext.getCmp('integralReason').allowBlank = false;
										 }
										 if(checked=='1'){
								    		 Ext.getCmp("integralReasonId").hide();
								    		 Ext.getCmp('integralReason').allowBlank = true;
								    	 }     
							 }
									}
								}]
				},{ 
					columnWidth : .30,
					layout : 'form',
					items : [ {
						xtype:'checkboxgroup',
						disabled:true,
						name:'tblMchtSettleInfTmpTmp.emergency',   
						id: 'emergency', 
						fieldLabel: '是否紧急',
						items: [  
						        { boxLabel: '是', name: 'tblMchtSettleInfTmpTmp.emergency', inputValue: '1' }] 
					}]
				},{				
					columnWidth: .76,
					layout: 'form',
					id: 'bankStatementReasonId',
					hidden:true,
					style:'display:block',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '需要对账单理由',
						width:607,
						//anchor: '90%',
//						maxLength: 200,
						id: 'bankStatementReason',
						name:'tblMchtSettleInfTmpTmp.bankStatementReason'
					}]
				},{				
					columnWidth: .76,
					layout: 'form',
					id: 'integralReasonId',
					hidden:true,
					style:'display:block',
					items: [{
						xtype: 'displayfield',
						fieldLabel: '需要积分理由',
						width:607,
//						anchor: '90%',
						id: 'integralReason',      
						name:'tblMchtSettleInfTmpTmp.integralReason'
					}]
				}]
			
			
			},{
				title:'风控参数修改',
                xtype: 'fieldset',
                id: 'riskParamAdd',
                frame: true,
    			layout:'column',
                items:[{
    				columnWidth : .33,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'debitMonAmt',
    					name:'tblRiskParamMng.debitMonAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .66,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'creditMonAmt',
    					name:'tblRiskParamMng.creditMonAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .33,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'debitDayAmt',
    					name:'tblRiskParamMng.debitDayAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .66,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'creditDayAmt',
    					name:'tblRiskParamMng.creditDayAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .33,
    				layout : 'form',
    				items : [{
    					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'debitSingleAmt',
    					name:'tblRiskParamMng.debitSingleAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .66,
    				layout : 'form',
    				items : [{
    					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'creditSingleAmt',
    					name:'tblRiskParamMng.creditSingleAmt',
    					maxLength: 14,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 9999999999999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .33,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计借记卡交易限定次数<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'debitDayCount',
    					name:'tblRiskParamMng.debitDayCount',
    					maxLength: 5,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 99999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .66,
    				layout : 'form',
    				items : [{
    					fieldLabel : '日累计贷记卡交易限定次数<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'creditDayCount',
    					name:'tblRiskParamMng.creditDayCount',
    					maxLength: 5,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 99999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth :.33,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计借记卡交易限定次数<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'debitMonCount',
    					name:'tblRiskParamMng.debitMonCount',
    					//maxLength: 5,
    					regex: /^\d{1,5}$/,
    					regexText:'请输入符合要求的数字，不能超过5位且未整数）',
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 99999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .66,
    				layout : 'form',
    				items : [{
    					fieldLabel : '月累计贷记卡交易限定次数<font color="red">*</font>',
    					xtype: 'numberfield',
    					id : 'creditMonCount',
    					name:'tblRiskParamMng.creditMonCount',
    					maxLength: 5,
    					editable: true,
    					allowBlank: false,
    		            allowNegative: false,
    		            maxValue: 99999,
    		            minValue: 0,
    		            value: 0,
    		            //设置允许保留2位小数
    		            allowDecimals: true,
    		            decimalPrecision: 0,
    		            width : 200
    				}]
    			},{
    				columnWidth : .33,
    				layout : 'form',
    				items : [{
						fieldLabel : '商户刷卡限额<font color="red">*</font>',
						xtype: 'numberfield',
						id : 'mchtPosAmt',
						name:'tblRiskParamMng.mchtPosAmt',
						maxLength: 14,
						editable: true,
						allowBlank: false,
			            allowNegative: false,
			            maxValue: 9999999999999,
			            minValue: 0,
			            value: 0,
			            //设置允许保留2位小数
			            allowDecimals: true,
			            decimalPrecision: 0,
			            width : 200
    				}]
				},{
    				columnWidth : .66,
    				layout : 'form',
    				id : 'mchtAmtForHide',
    				hidden : true,
    				items : [{
						fieldLabel : '提现单笔限额<font color="red">*</font>',
						xtype: 'numberfield',
						id : 'mchtAmt',
						name:'tblRiskParamMng.mchtAmt',
						maxLength: 14,
						editable: true,
						allowBlank: true,
			            allowNegative: false,
			            maxValue: 9999999999999,
			            minValue: 0,
			            value: 0,
			            //设置允许保留2位小数
			            allowDecimals: true,
			            decimalPrecision: 0,
			            width : 200
    				}]
				},{
    				columnWidth : .33,
    				layout : 'form',
    				id : 'mchtDayAmtForHide',
    				hidden : true,
    				items : [{
						fieldLabel : '提现日累计限额<font color="red">*</font>',
						xtype: 'numberfield',
						id : 'mchtDayAmt',
						name:'tblRiskParamMng.mchtDayAmt',
						maxLength: 14,
						editable: true,
						allowBlank: true,
			            allowNegative: false,
			            maxValue: 9999999999999,
			            minValue: 0,
			            value: 0,
			            //设置允许保留2位小数
			            allowDecimals: true,
			            decimalPrecision: 0,
			            width : 200
    				}]
				},{
    				columnWidth : .99,
    				layout : 'form',
    				items : [{
    					xtype: 'textarea',
    					fieldLabel: '备注',
    					width: 535,
    					vtype: 'length100B',
    					//labelStyle: 'padding-left: 10px',
    					id: 'remarkId',
    					name: 'tblRiskParamMng.remark'
    				}]
    			
    			}]
			},{
//            	title:'签约信息',
//                xtype: 'fieldset',
//                id: 'billInfo',
//                frame: true,
//    			layout:'column',
//                items: []
            	title:'签约信息',
                xtype: 'fieldset',
                id: 'billBaseInfo',
                frame: true,
    			layout:'column',
                items: [{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
					xtype: 'button',
					title:'点击查看协议影像',
					width: '150',
					text: '查看协议影像',
					id: 'click5',
					handler:function() {
						var upload='upload5';
						checkImageInfo(upload);
					}
	       			}]
				}]
             
            },{
            	title:'其他信息',
                xtype: 'fieldset',
                id: 'manageBaseInfo',
                frame: true,
    			layout:'column',
                items: [{
                	xtype: 'panel',
    	        	layout: 'form',
    	        	columnWidth: .20,
    	        	items: [{
    				xtype: 'button',
    				title:'点击查看房屋租赁协议影像',
    				width: '150',
    				text: '查看房屋租赁协议影像',
    				id: 'click8',
    				handler:function() {
    					var upload='upload8';
    					checkImageInfo(upload);
    				}}]
            },{
				xtype: 'panel',
	        	layout: 'form',
	        	columnWidth: .20,
	        	items: [{
				xtype: 'button',
				title:'点击查看委托入款授权证明影像',
				width: '150',
				text: '查看委托入款授权证明影像',
				id: 'click7',
				handler:function() {
					var upload='upload7';
					checkImageInfo(upload);
				}}]
			
			},{
				xtype: 'panel',
	        	layout: 'form',
	        	hidden:true,
	        	columnWidth: .20,
	        	items: [{
				xtype: 'button',
				title:'点击查看水煤电账单影像',
				width: '150',
				text: '查看水煤电账单影像',
				id: 'click9',
				handler:function() {
					var upload='upload9';
					checkImageInfo(upload);
				}
	        	}]
            }
    			]
            },{
		    	xtype:'fieldset',
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
				id:'next4',
				xtype: 'tabpanel',
				activeTab : 0,
				hidden:false,
				height : 300,
//            	region: 'center',
            	items:[termGridPanel]
		},{
        	html:'<br>'
        },{ 
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
    					if (!Ext.getCmp('mchtGrpId').isValid()){
    						showErrorMsg('商户组别未选择，请选择！',mchntForm);
    						return;
    					}
    					if (!Ext.getCmp('idmcc').isValid()){
    						showErrorMsg('商户MCC未选择，请选择！',mchntForm);
    						return;
    					}
    					if (!Ext.getCmp('rislLvlId').isValid()){
    						showErrorMsg('商户风险级别未选择，请选择！',mchntForm);
    						return;
    					}
    					if(!mchntForm.getForm().isValid()){
    						showErrorMsg('风控参数为空或输入不正确！',mchntForm);
        					return;
    					}
    					showConfirm('确认审核通过吗？',mchntForm,function(bt) {
    						if(bt == 'yes') {
    							showProcessMsg('正在提交审核信息，请稍后......');
    							//rec = mchntForm.getSelectionModel().getSelected();
    							mchntForm.getForm().submit({
    								url: 'T20201Action.asp?method=accept',
    								 waitMsg: '正在提交，请稍后......',
    								params: {
    									mchntId: mchntId,
    									txnId: '20201',
    									subTxnId: '01',
    									//mchtGrp  :Ext.getCmp('mchtGrpId').getValue(),
    									idmcc    :Ext.getCmp('idmcc').getValue(),
    									rislLvlId  :Ext.getCmp('rislLvlId').getValue(),
    									countryId  :Ext.getCmp('countryId').getValue().inputValue,
    									complianceId  :Ext.getCmp('complianceId').getValue().inputValue,
    								},
    								success: function(opt,rsp) {
    									var rspObj = rsp.result;
    									if(rspObj.success) {
    										//showSuccessMsg(rspObj.msg,mchntForm);
    										Ext.Msg.alert('提示信息：','    '+rspObj.msg+'!   ');
    										detailWin.close();
    										// 重新加载商户待审核信息
    										El.getStore().reload();
    										//延时关闭页面
//    										setTimeout(function(){
//    											detailWin.close();
//    											},2000);
    									} else {
    										showErrorMsg(rspObj.msg,mchntForm);
    									}
    								}, failure: function(form,action) {
    		                            showErrorMsg(action.result.msg,mchntForm);
    		                            
    		                        },
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
				var type=baseStore.getAt(0).json.poundage;
				if (type=="0") {
	        		Ext.getCmp('jFee').show();
	        		Ext.getCmp('jMaxFee').hide();
	        		Ext.getCmp('forHide4').show();
	        		Ext.getCmp('forHide1').hide();
	        		
	        		Ext.getCmp('dFee').show();
	        		Ext.getCmp('dMaxFee').hide();
	        		Ext.getCmp('forHide3').show();
	        		Ext.getCmp('forHide2').hide();
				}else if ("2"==type) {
	        		Ext.getCmp('jFee').hide();
	        		Ext.getCmp('forHide3').hide();
	        		Ext.getCmp('forHide2').show();
	        		Ext.getCmp('jMaxFee').show();
	        		
	        		Ext.getCmp('dFee').hide();
	        		Ext.getCmp('forHide4').hide();
	        		Ext.getCmp('forHide1').show();
	        		Ext.getCmp('dMaxFee').show();
				}else {
					Ext.getCmp('forHide1').show()
					Ext.getCmp('forHide2').show()
					Ext.getCmp('forHide3').show()
					Ext.getCmp('forHide4').show()
	        		Ext.getCmp('jFee').show();
	        		Ext.getCmp('jMaxFee').show();
	        		
	        		Ext.getCmp('dFee').show();
	        		Ext.getCmp('dMaxFee').show();
				}
				
				//设置结算类型
				var mchtFunction =baseStore.getAt(0).data.mchtFunction;
				if(mchtFunction!=null&&mchtFunction.trim()!=''){
					var mchtFunctionType=mchtFunction.substr(0,1);
					if(mchtFunctionType==0){
						Ext.getCmp('mchtFunctionId0').show();
						Ext.getCmp('mchtFunctionId1').hide();
						Ext.getCmp('T_N').allowBlank=false;
						Ext.getCmp('periodId').allowBlank=true;
					}else if(mchtFunctionType==1){
						Ext.getCmp('mchtFunctionId0').hide();
						Ext.getCmp('mchtFunctionId1').show();
						Ext.getCmp('T_N').allowBlank=true;
						Ext.getCmp('periodId').allowBlank=false;
					}
					Ext.getCmp('mchtFunctionId').fireEvent('select',this,this.store,mchtFunctionType);//combo,record,number
					Ext.getCmp('mchtFunctionId').setValue(mchtFunctionType);//combo,record,number
					Ext.getCmp('periodId').setValue(mchtFunction.substr(1,1));
					Ext.getCmp('T_N').setValue(parseInt((mchtFunction.substr(2,3).trim()==''?'0':mchtFunction.substr(2,3).trim()),10));
				}
				//提现信息
				if(Ext.getCmp('T_N').getValue()=='0'){
					Ext.getCmp('withdrawInfo').show();
					if(baseStore.getAt(0).data.cashFlag == '1'){
						Ext.getCmp('mchtAmtForHide').show();
						Ext.getCmp('mchtAmt').allowBlank = false;
						Ext.getCmp('mchtDayAmtForHide').show();
						Ext.getCmp('mchtDayAmt').allowBlank = false;
						Ext.getCmp('feeInvstIntrstForHide').show();
						Ext.getCmp('cashTpForHide').show();
						Ext.getCmp('feeTpForHide').show();
						Ext.getCmp('feeInvstTyForHide').show();
						Ext.getCmp('shrPrftInvstCdForHide').show();
						Ext.getCmp('shrPrftInvstTyForHide').show();
						Ext.getCmp('blncHndlTyForHide').show();

						if(baseStore.getAt(0).data.feeTp == '1'){
							Ext.getCmp('feeInvstIntrstForHide').show();
							Ext.getCmp('feeAmtForHide').hide();
							Ext.getCmp('feeAmt').setValue('');
						}else {
							Ext.getCmp('feeInvstIntrstForHide').hide();
							Ext.getCmp('feeInvstIntrst').setValue('');
							Ext.getCmp('feeAmtForHide').show();
						}
					}else {
						Ext.getCmp('cashTpForHide').hide();
						Ext.getCmp('feeTpForHide').hide();
						Ext.getCmp('feeInvstTyForHide').hide();
						Ext.getCmp('feeInvstIntrstForHide').hide();
						Ext.getCmp('feeAmtForHide').hide();
						Ext.getCmp('shrPrftInvstCdForHide').hide();
						Ext.getCmp('shrPrftInvstTyForHide').hide();
						Ext.getCmp('blncHndlTyForHide').hide();
					}
				
				}else {
					Ext.getCmp('withdrawInfo').hide();
				}
				var etpsAttrId=baseStore.getAt(0).data.etpsAttr;
				if(etpsAttrId=='01'|etpsAttrId=='02'){
					
				}else if(etpsAttrId=='03'){
					Ext.getCmp('accountHide1').hide();
					Ext.getCmp('accountHide2').hide();
					Ext.getCmp('licenceNoPID').hide();
					Ext.getCmp('accountHide4').hide();
					Ext.getCmp('accountHide5').hide();
					Ext.getCmp('faxNoPID').hide();
					Ext.getCmp('accountHide7').hide();
					Ext.getCmp('accountHide8').hide();
				}
				Ext.getCmp('provinceAddress').setValue(baseStore.getAt(0).data.addrProvince);
				cityStore.removeAll();
				var province = Ext.getCmp('provinceAddress').getValue();
				if(province!=''){
					SelectOptionsDWR.getComboDataWithParameter('ADDR_CITY',province,function(ret){
						cityStore.loadData(Ext.decode(ret));
						Ext.getCmp('cityAddress').setValue(baseStore.getAt(0).data.areaNo);
					});
					
				}
				//设置分润信息的值
				var profit=baseStore.getAt(0).json.speSettleTp;
				if(profit==''){
					profit='     ';
				}
				Ext.getCmp('profitType_id').setValue(profit.substr(0,1));
				if(profit.substr(0,1)=='0'){
					Ext.getCmp('rateId_id').setValue(profit.substr(1,2));
				}else if(profit.substr(0,1)=='2'){
					Ext.getCmp('capId').setValue(profit.substr(3,2));
				}else if(profit.substr(0,1)=='1'){
					Ext.getCmp('rateId_id').setValue(profit.substr(1,2));
					Ext.getCmp('capId').setValue(profit.substr(3,2));
				}
				var profitType = profit.substr(0,1);
				if (profitType=="0") {
	        		Ext.getCmp('forHide5').show();
	        		Ext.getCmp('forHide6').hide();
				}else if ("2"==profitType) {
	        		Ext.getCmp('forHide5').hide();
	        		Ext.getCmp('forHide6').show();
				}else {
	        		Ext.getCmp('forHide5').show();
	        		Ext.getCmp('forHide6').show();
				}
				Ext.getCmp('feeId').setValue(baseStore.getAt(0).json.poundage);
//				var discCode = baseStore.getAt(0).data.feeRate;
//				Ext.getCmp('discCode').setValue(discCode);
				var feeTypeValue = baseStore.getAt(0).data.feeType;
				var settleAcct = baseStore.getAt(0).data.settleAcct;
				var mccValue = baseStore.getAt(0).data.mchtGrp;
				var etpsAttrId = baseStore.getAt(0).data.etpsAttr;
				var mchtGrpId = baseStore.getAt(0).data.mchtGrp;
				var country = baseStore.getAt(0).data.country;
				Ext.getCmp('countryId').setValue(country);
				var compliance = baseStore.getAt(0).data.compliance;
				Ext.getCmp('complianceId').setValue(compliance);
//				var mchtFunction = baseStore.getAt(0).data.mchtFunction;
				var mchtStatus=baseStore.getAt(0).data.mchtStatus;
				if(mchtStatus==='4'){
					Ext.getCmp('editMenu').hide();
					Ext.getCmp('mchtGrpId').readOnly=true;
					Ext.getCmp('idmcc').readOnly=true;
					Ext.getCmp('rislLvlId').readOnly=true;
				}
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
				var integral=Ext.getCmp('integral').getValue();
				if(integral=='0'){
				 Ext.getCmp("integralReasonId").show();
				 }else if(integral=='1'){
		    		 Ext.getCmp("integralReasonId").hide();
		    	 }     


				var bankStatement=Ext.getCmp('bankStatement').getValue();
						if(bankStatement=='0'){
							Ext.getCmp("bankStatementReasonId").show();
								    	 }
						 if(bankStatement=='1'){
							Ext.getCmp("bankStatementReasonId").hide();
						}
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
								//Ext.getCmp('refuseReason').hide();
							} else {
								Ext.getCmp('refuseReason').show();
								//Ext.getCmp('refuseReasonTxt').setText(reasonStore.getAt(0).data.refuseInfo);
							}
						}else{
							
						}
					}
				});
				diffStore.load({
					params: {
						start: 0,
						mchntId: mchntId
					},
					callback : function(records, options, success){
						if(success){
							for (var i = 1; i <= 62; i++) {
								if(records[0].get('col'+i)==''&&records[1].get('col'+i)==''){
									continue;
								}else {
									var org = records[0].get('col'+i)==''?'':records[0].get('col'+i);
									var update = records[1].get('col'+i)==''?'':records[1].get('col'+i);
									if(fieldLableArray[i-1] === '风险级别'&& org ==='0'){
										org = '';
									}
									if((fieldLableArray[i-1] ==='商户组别'||fieldLableArray[i-1] ==='风险级别'
										||fieldLableArray[i-1] ==='是否合规'
											||fieldLableArray[i-1] ==='是否县乡')&&(org === ''&& update!='')){
										continue;
									}
									if(fieldLableArray[i-1] ==='MCC'&&(org === '-'&& update!='')){
										continue;
									}
									var diffRec = diffInfRec('displayfield',fieldLableArray[i-1],'insert'+i,'<font color=green>'+org+'</font>————><font color=red>'+update+'</font>');
									mchntForm.items.items[1].add(diffRec);
								}
							}
							mchntForm.items.items[1].doLayout();
						}
					}
				});
				termStore.on('beforeload', function() {
					Ext.apply(this.baseParams, {
						start : 0,
						mchnNo :mchntId, 
						notEqsta:'7'
					});
				});
				termStore.load();
				// ******************拒绝原因**********结束********
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