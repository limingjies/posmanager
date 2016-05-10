Ext.onReady(function() {
	 Ext.QuickTips.init();
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
	 //当前日期
//			var now =new Date();
//			var nowDate=(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()).replaceAll('-','');
			var clearTypeTmp = '';
			// *************终端信息****************
			var topQueryPanel = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 500,
				autoHeight : true,
				labelWidth : 80,
				items : [
						new Ext.form.TextField({
							id : 'termNoQ',
							name : 'termNo',
							fieldLabel : '终端号',
							anchor : '60%'
						}),
						/*{
							xtype : 'dynamicCombo',
							methodName : 'getMchntNo',
							fieldLabel : '商户编号',
							hiddenName : 'mchnNo',
							id : 'mchnNoQ',
							editable : true,
							width : 0,

						},*/
						{
							xtype : 'combo',
							fieldLabel : '终端状态',
							id : 'state',
							name : 'state',
							anchor : '60%',
							store : new Ext.data.ArrayStore({
								fields : [ 'valueField', 'displayField' ],
								data : [ [ '0', '新增未审核' ], [ '1', '启用' ],
										[ '2', '修改未审核' ], [ '3', '冻结未审核' ],
										[ '4', '冻结' ], [ '5', '恢复未审核' ],
										[ '6', '注销未审核' ], [ '7', '注销' ] ]
							})
						}, {
							xtype : 'basecomboselect',
							baseParams : 'TERM_TYPE',
							fieldLabel : '终端类型',
							id : 'idtermtpsearch',
							hiddenName : 'termtpsearch',
							anchor : '60%'
						}, {
							width : 150,
							xtype : 'datefield',
							fieldLabel : '创建起始时间',
							format : 'Y-m-d',
							name : 'startTime',
							id : 'startTime',
							anchor : '60%'
						}, {
							width : 150,
							xtype : 'datefield',
							fieldLabel : '创建截止时间',
							format : 'Y-m-d',
							name : 'endTime',
							id : 'endTime',
							anchor : '60%'
						} ],
				buttons : [ {
					text : '查询',
					handler : function() {
						termStore.load();
						queryWin.hide();
					}
				}, {
					text : '重填',
					handler : function() {
						topQueryPanel.getForm().reset();
					}
				} ]
			});
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
				},/* {
					name : 'mchntNo',
					mapping : 'mchntNo'
				}, {
					name : 'mchntName',
					mapping : 'mchntName'
				},*/ {
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
						header : '所属合作伙伴',
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
//						bt.disable();
						updateTmpDealInfo(selectedRecord.get('termId'), selectedRecord.get('recCrtTs'),termGridPanel);
					}
				};
			
			var menuArr = new Array();
			menuArr.push(qryMenu); 
			menuArr.push(editMenu); 

			// 终端信息列表
			var termGridPanel = new Ext.grid.GridPanel({
				title : '终端信息',
				iconCls : 'T301',
				region : 'center',
				frame : false,
//				autoHeight:true,
				border : false,
//				autoExpandColumn : 'mchntName',
				columnLines : true,
				stripeRows : true,
				store : termStore,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				cm : termColModel,
//				clicksToEdit : true,
//				forceValidation : true,
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
//							termGridPanel.getTopToolbar().items.items[1].enable();
							// 根据商户状态判断哪个编辑按钮可用
							rec = termGridPanel.getSelectionModel().getSelected();
							/*if (rec.get('termSta') == '0'
									|| rec.get('termSta') == '1'
									|| rec.get('termSta') == '2'
									|| rec.get('termSta') == '8'
									|| rec.get('termSta') == '9'
									|| rec.get('termSta') == 'A'
									|| rec.get('termSta') == 'B'
									|| rec.get('termSta') == 'C'
									|| rec.get('termSta') == 'D') {
//								termGridPanel.getTopToolbar().items.items[2].enable();
							} else {
								termGridPanel.getTopToolbar().items.items[2].disable();
							}*/
							// if(rec.get('termSta') == '1')
							// {
							// grid.getTopToolbar().items.items[3].enable();
							// } else {
							// grid.getTopToolbar().items.items[3].disable();
							// }
							termGridPanel.getTopToolbar().items.items[0].enable();
							termGridPanel.getTopToolbar().items.items[1].enable();
							// grid.getTopToolbar().items.items[5].enable();
						}
					});

			// 所属机构
			var brhStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data'
			});
			SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH', function(ret) {
				brhStore.loadData(Ext.decode(ret));
			});

			// 终端库存号
			var termIdIdStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data'
			});
			// 终端厂商
			/*var manufacturerStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data'
			});
			SelectOptionsDWR.getComboData('MANUFACTURER', function(ret) {
				manufacturerStore.loadData(Ext.decode(ret));
			});*/

			// 终端型号
			var terminalTypeStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data'
			});

// *************终端信息****************

			Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = 'side';
			// 两个月后
			var curDate = new Date();
			var preDate = new Date(curDate.getTime() + 30 * 2 * 24 * 60 * 60
					* 1000);
			var twoMonday = preDate.format("Y") + "-" + preDate.format("m")
					+ "-" + (preDate.format("d"));// 昨天
			// ******************图片处理部分**********开始********
			var storeImg = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				// idProperty: 'id'
				}, [ {
					name : 'id',
					mapping : 'id'
				}, {
					name : 'btBig',
					mapping : 'btBig'
				}, {
					name : 'btDel',
					mapping : 'btDel'
				}, {
					name : 'width',
					mapping : 'width'
				}, {
					name : 'height',
					mapping : 'height'
				}, {
					name : 'fileName',
					mapping : 'fileName'
				} ])
			});
			var storeImg10 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg9 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg8 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg7 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg6 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg5 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg4 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg3 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg2 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'id',mapping: 'id'},
					{name: 'btBig',mapping: 'btBig'},
					{name: 'btDel',mapping: 'btDel'},
					{name: 'width',mapping: 'width'},
					{name: 'height',mapping: 'height'},
					{name: 'fileName',mapping: 'fileName'}
				])
			});
			var storeImg1 = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=getImgInfTmp'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
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
				wrap : true,
				pinned : true,
				minWidth : 50,
				minHeight : 50,
				preserveRatio : true,
				dynamic : true,
				handles : 'all',
				draggable : true
			});
			var customEl = custom.getEl();
			var rollTimes = 0; // 向上滚加1，向下滚减1
			var originalWidth = 0; // 图片缩放前的原始宽度
			var originalHeight = 0; // 图片缩放前的原始高度
			document.body.insertBefore(customEl.dom, document.body.firstChild);
			customEl.on('dblclick', function() {
				customEl.puff();
				rollTimes = 0;
			});
			customEl.hide(true);
			function showPIC(store,id) {
				var rec = store.getAt(id.substr(5,1)).data;
				/*custom.resizeTo(rec.width, rec.height);
				var src = document.getElementById('showBigPic').src;

				if (src.indexOf(rec.fileName) == -1) {
					document.getElementById('showBigPic').src = "";
					document.getElementById('showBigPic').src = Ext.contextPath
							+ '/PrintImage?fileName=' + mchntId + '/'
							+ rec.fileName;
					document.getElementById('showBigPic').onmousewheel = bigimg;
					originalWidth = document.getElementById('showBigPic').width;
					originalHeight = document.getElementById('showBigPic').height;
				}
				customEl.center();
				customEl.show(true);*/
				
				var imgSrc = Ext.contextPath + '/PrintImage?fileName=' + mchntId + '/' + rec.fileName;
				showImage(imgSrc, mchntId);
			}
			/*function bigimg() {
				var zoom = 0.05;
				var rollDirect = event.wheelDelta;
				if (rollDirect > 0) {
					rollTimes++;
				}
				if (rollDirect < 0) {
					rollTimes--;
				}
				if (this.height < 50 && rollDirect < 0) {
					return false;
				}
				var cwidth = originalWidth * (1 + zoom * rollTimes);
				var cheight = originalHeight * (1 + zoom * rollTimes);
				custom.resizeTo(cwidth, cheight);
				return false;
			}
			function delPIC(store,id) {
				customEl.hide();
				document.getElementById('showBigPic').src = "";
				showConfirm('确定要删除吗？', mchntForm, function(bt) {
					if (bt == 'yes') {
						var rec = store.getAt(id.substring(5,1)).data;
						T20100.deleteImgFileTmp(rec.fileName, mchntId,
								function(ret) {
									if ("S" == ret) {
										store.reload({
											params : {
												start : 0,
												imagesId : imagesId,
												mcht : mchntId
											}
										});
									} else {
										showMsg('操作失败，请刷新后重试！', mchntForm);
									}
								});
					}
				});
			}*/
			storeImg.on('load', function() {
				for (var i = 0; i < storeImg.getCount(); i++) {
					var rec = storeImg.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg,val.id);
					});
				}
			});
			storeImg1.on('load', function() {
				for (var i = 0; i < storeImg1.getCount(); i++) {
					var rec = storeImg1.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg1,val.id);
					});
				}
			});
			storeImg2.on('load', function() {
				for (var i = 0; i < storeImg2.getCount(); i++) {
					var rec = storeImg2.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg2,val.id);
					});
				}
			});
			storeImg3.on('load', function() {
				for (var i = 0; i < storeImg3.getCount(); i++) {
					var rec = storeImg3.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg3,val.id);
					});
				}
			});
			storeImg4.on('load', function() {
				for (var i = 0; i < storeImg4.getCount(); i++) {
					var rec = storeImg4.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg4,val.id);
					});
				}
			});
			storeImg5.on('load', function() {
				for (var i = 0; i < storeImg5.getCount(); i++) {
					var rec = storeImg5.getAt(i).data;
					Ext.get(rec.btBig).on('click', function(obj, val) {
						showPIC(storeImg5,val.id);
					});
				}
			});
			storeImg6.on('load',function(){
				for(var i=0;i<storeImg6.getCount();i++){
					var rec = storeImg6.getAt(i).data;
		        	Ext.get(rec.btBig).on('click', function(obj,val){
		        		showPIC(storeImg6,val.id);
		        	});
				}
			});
			storeImg7.on('load',function(){
				for(var i=0;i<storeImg7.getCount();i++){
					var rec = storeImg7.getAt(i).data;
		        	Ext.get(rec.btBig).on('click', function(obj,val){
		        		showPIC(storeImg7,val.id);
		        	});
				}
			});
			storeImg8.on('load',function(){
				for(var i=0;i<storeImg8.getCount();i++){
					var rec = storeImg8.getAt(i).data;
		        	Ext.get(rec.btBig).on('click', function(obj,val){
		        		showPIC(storeImg8,val.id);
		        	});
				}
			});
			storeImg9.on('load',function(){
				for(var i=0;i<storeImg9.getCount();i++){
					var rec = storeImg9.getAt(i).data;
		        	Ext.get(rec.btBig).on('click', function(obj,val){
		        		showPIC(storeImg9,val.id);
		        	});
				}
			});
			storeImg10.on('load',function(){
				for(var i=0;i<storeImg10.getCount();i++){
					var rec = storeImg10.getAt(i).data;
		        	Ext.get(rec.btBig).on('click', function(obj,val){
		        		showPIC(storeImg10,val.id);
		        	});
				}
			});
			var dataview = new Ext.DataView(
					{
						frame : true,
						store : storeImg,
						tpl : new Ext.XTemplate(
								'<ul>',
								'<tpl for=".">',
								'<li class="phone">',
								'<div onmouseover="this.style.cursor=\'hand\'" title="点击“放大”按钮查看大图片，放大后可拖动图片的大小，双击图片可以隐藏">',
								'<img id="{id}" width="120" height="90" src="'
										+ Ext.contextPath
										+ '/PrintImage?fileName=' + mchntId
										+ '/{fileName}&width=120&height=90"/>',
								'<div style=""><input type="button" id="{btBig}" value="放大"></div>',
								'</div>', '</li>', '</tpl>', '</ul>'),
						id : 'phones',
						itemSelector : 'li.phone',
						overClass : 'phone-hover',
						singleSelect : true,
						multiSelect : true,
						autoScroll : true
					});
			var dataview10 = new Ext.DataView({
				frame: true,
		        store: storeImg10,
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
		        id: 'phones10',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview9 = new Ext.DataView({
				frame: true,
		        store: storeImg9,
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
		        id: 'phones9',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview8 = new Ext.DataView({
				frame: true,
		        store: storeImg8,
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
		        id: 'phones8',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview7 = new Ext.DataView({
				frame: true,
		        store: storeImg7,
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
		        id: 'phones7',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview6 = new Ext.DataView({
				frame: true,
		        store: storeImg6,
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
		        id: 'phones6',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview5 = new Ext.DataView({
				frame: true,
		        store: storeImg5,
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
		        id: 'phones5',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			
			
			var dataview4 = new Ext.DataView({
				frame: true,
		        store: storeImg4,
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
		        id: 'phones4',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview3 = new Ext.DataView({
				frame: true,
		        store: storeImg3,
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
		        id: 'phones3',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
		    });
			var dataview2 = new Ext.DataView({
				frame: true,
		        store: storeImg2,
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
		        id: 'phones2',
		        itemSelector: 'li.phone',
		        overClass   : 'phone-hover',
		        singleSelect: true,
		        multiSelect : true,
		        autoScroll  : true
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
			// 文件上传窗口
			var dialog = new UploadDialog({
				uploadUrl : 'T20901Action_upload.asp',
				filePostName : 'imgFile',
				flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
				fileSize : '1 MB',
				fileTypes : '*.jpg;*.png;*.jpeg',
				fileTypesDescription : '图片文件',
				title : '证书影印文件上传',
				scope : this,
				// animateTarget: 'upload',
				onEsc : function() {
					this.hide();
				},
				completeMethod : function() {
					storeImg.reload({
						params : {
							start : 0,
							imagesId : imagesId,
							mcht : mchntId
						}
					});
				},
				postParams : {
					txnId : '20901',
					subTxnId : '02',
					imagesId : imagesId,
					mcht : mchntId
				}
			});
			// ******************图片处理部分**********结束********

			// ******************计费算法部分**********开始********
			var gridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=tblInfDiscCd'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'discCd',
					mapping : 'discCd'
				}, {
					name : 'discNm',
					mapping : 'discNm'
				}, {
					name : 'discOrg',
					mapping : 'discOrg'
				} ])
			});

			var gridColumnModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						header : '计费代码',
						dataIndex : 'discCd',
						sortable : true,
						width : 80
					}, {
						header : '计费名称',
						dataIndex : 'discNm',
						sortable : true,
						id : 'discNm',
						width : 100
					}, {
						header : '所属机构',
						dataIndex : 'discOrg',
						sortable : true,
						width : 100,
						renderer : function(val) {
							return getRemoteTrans(val, "brh");
						}
					} ]);

			var gridPanel = new Ext.grid.GridPanel({
				title : '计费算法信息',
				frame : true,
				border : true,
				height : 250,
				columnLines : true,
				// autoLoad: true,
				autoExpandColumn : 'discNm',
				stripeRows : true,
				store : gridStore,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				cm : gridColumnModel,
				forceValidation : true,
				loadMask : {
					msg : '正在加载计费算法信息列表......'
				},
				bbar : new Ext.PagingToolbar({
					store : gridStore,
					pageSize : System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
				}),
				tbar : [ '计费代码：', {
					xtype : 'textfield',
					id : 'serdiscCd',
					width : 60
				}, '-', '计费名称：', {
					xtype : 'textfield',
					id : 'serdiscNm',
					width : 110
				}, '-', {
					xtype : 'button',
					iconCls : 'query',
					text : '查询',
					id : 'widfalg',
					width : 60,
					autoLoad : true,
					handler : function() {
						gridStore.load();
					},
					listeners : {
						'select' : function() {
							store.load();
						},
						'change' : function() {
							store.load();
						}
					}
				} ]
			});

			gridPanel.getStore().on('beforeload', function() {
				Ext.getCmp('setup').disable();
			});

			gridPanel.getSelectionModel()
					.on(
							'rowselect',
							function(sm, rowIdx, r) {
								Ext.getCmp('setup').enable();
								var id = gridPanel.getSelectionModel()
										.getSelected().data.discCd;
								store.load({
									params : {
										start : 0,
										discCd : gridPanel.getSelectionModel()
												.getSelected().data.discCd
									}
								});
							});

			var flagStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data',
				id : 'valueField'
			});

			SelectOptionsDWR.getComboData('DISC_FLAG', function(ret) {
				flagStore.loadData(Ext.decode(ret));
			});

			var txnStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data',
				id : 'valueField'
			});

			SelectOptionsDWR.getComboData('feeTxnNum', function(ret) {
				txnStore.loadData(Ext.decode(ret));
			});
			var cardTypeStore = new Ext.data.JsonStore({
				fields : [ 'valueField', 'displayField' ],
				root : 'data',
				id : 'valueField'
			});

			SelectOptionsDWR.getComboData('feeCardType', function(ret) {
				cardTypeStore.loadData(Ext.decode(ret));
			});

			var hasSub = false;
			var fm = Ext.form;

			var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'gridPanelStoreAction.asp?storeId=getDiscInf'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'txnNum',
					mapping : 'txnNum',
					type : 'string'
				}, {
					name : 'floorMount',
					mapping : 'floorMount'
				}, {
					name : 'minFee',
					mapping : 'minFee'
				}, {
					name : 'maxFee',
					mapping : 'maxFee'
				}, {
					name : 'flag',
					mapping : 'flag'
				}, {
					name : 'feeValue',
					mapping : 'feeValue'
				}, {
					name : 'cardType',
					mapping : 'cardType'
				} ]),
				sortInfo : {
					field : 'floorMount',
					direction : 'ASC'
				},
				autoLoad : false
			});

			var cm = new Ext.grid.ColumnModel({
				columns : [ {
					header : '卡类型',
					dataIndex : 'cardType',
					width : 70,
					editor : {
						xtype : 'basecomboselect',
						store : cardTypeStore,
						id : 'idCardType',
						hiddenName : 'cardType',
						width : 130
					},
					renderer : function(data) {
						if (null == data)
							return '';
						var record = cardTypeStore.getById(data);
						if (null != record) {
							return record.data.displayField;
						} else {
							return '';
						}
					}
				}, {
					header : '交易类型',
					dataIndex : 'txnNum',
					width : 70,
					editor : {
						xtype : 'basecomboselect',
						store : txnStore,
						id : 'idTxnNum',
						hiddenName : 'txnNum',
						width : 130
					},
					renderer : function(data) {
						if (null == data)
							return '';
						var record = txnStore.getById(data);
						if (null != record) {
							return record.data.displayField;
						} else {
							return '';
						}
					}
				}, {
					id : 'floorMount',
					header : '最低交易金额',
					dataIndex : 'floorMount',
					width : 80,
					sortable : true
				}, {
					header : '回佣类型',
					dataIndex : 'flag',
					width : 90,
					editor : {
						xtype : 'basecomboselect',
						store : flagStore,
						id : 'idfalg',
						hiddenName : 'falg',
						width : 160
					},
					renderer : function(data) {
						if (null == data)
							return '';
						var record = flagStore.getById(data);
						if (null != record) {
							return record.data.displayField;
						} else {
							return '';
						}
					}
				}, {
					header : '回佣值',
					dataIndex : 'feeValue',
					width : 70
				}, {
					header : '按比最低收费',
					dataIndex : 'minFee',
					width : 90
				}, {
					header : '按比最高收费',
					dataIndex : 'maxFee',
					width : 90
				} ]
			});

			var detailGrid = new Ext.grid.GridPanel({
				title : '详细信息',
				frame : true,
				border : true,
				height : 50,
				columnLines : true,
				autoExpandColumn : 'floorMount',
				stripeRows : true,
				store : store,
				disableSelection : true,
				cm : cm,
				forceValidation : true,
				loadMask : {
					msg : '正在加载计费算法详细信息列表......'
				},
				bbar : new Ext.PagingToolbar({
					store : gridStore,
					pageSize : System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
				})
			});
			// ******************计费算法部分**********结束********
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
							Ext.getCmp('refuseReasonTxt').setText(reasonStore.getAt(0).data.refuseInfo);
						}
					}else{
						
					}
				}
			});
			// ******************拒绝原因**********结束********
			var fm = Ext.form;
			var baseStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'loadRecordAction.asp?storeId=getMchntTmpInf'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data'
				// idProperty: 'mchtNo'
				}, [ {
					name : 'mchtNo',
					mapping : 'mchtNo'
				}, {
					name : 'mchtNm',
					mapping : 'mchtNm'
				}, {
					name : 'rislLvl',
					mapping : 'rislLvl'
				}, {
					name : 'mchtLvl',
					mapping : 'mchtLvl'
				}, {
					name : 'mchtStatus',
					mapping : 'mchtStatus'
				}, {
					name : 'manuAuthFlag',
					mapping : 'manuAuthFlag'
				}, {
					name : 'partNum',
					mapping : 'partNum'
				}, {
					name : 'discConsFlg',
					mapping : 'discConsFlg'
				}, {
					name : 'discConsRebate',
					mapping : 'discConsRebate'
				}, {
					name : 'passFlag',
					mapping : 'passFlag'
				},
				// {name: 'openDays',mapping: 'openDays'},
				// {name: 'sleepDays',mapping: 'sleepDays'},
				{
					name : 'mchtCnAbbr',
					mapping : 'mchtCnAbbr'
				},
				// {name: 'spellName',mapping: 'spellName'},
				{
					name : 'engName',
					mapping : 'engName'
				}, {
					name : 'mchtEnAbbr',
					mapping : 'mchtEnAbbr'
				}, {
					name : 'areaNo',
					mapping : 'areaNo'
				}, {
					name : 'addr',
					mapping : 'addr'
				}, {
					name : 'homePage',
					mapping : 'homePage'
				}, {
					name : 'mcc',
					mapping : 'mcc'
				},
				// {name: 'tcc',mapping: 'tcc'},
				{
					name : 'etpsAttr',
					mapping : 'etpsAttr'
				}, {
					name : 'mngMchtId',
					mapping : 'mngMchtId'
				}, {
					name : 'mchtGrp',
					mapping : 'mchtGrp'
				}, {
					name : 'mchtAttr',
					mapping : 'mchtAttr'
				}, {
					name : 'mchtGroupFlag',
					mapping : 'mchtGroupFlag'
				}, {
					name : 'mchtGroupId',
					mapping : 'mchtGroupId'
				},
				// {name: 'mchtEngNm',mapping: 'mchtEngNm'},
				// {name: 'mchtEngAddr',mapping: 'mchtEngAddr'},
				// {name: 'mchtEngCityName',mapping: 'mchtEngCityName'},
				// {name: 'saLimitAmt',mapping: 'saLimitAmt'},
				{
					name : 'saAction',
					mapping : 'saAction'
				}, {
					name : 'psamNum',
					mapping : 'psamNum'
				}, {
					name : 'cdMacNum',
					mapping : 'cdMacNum'
				}, {
					name : 'posNum',
					mapping : 'posNum'
				}, {
					name : 'connType',
					mapping : 'connType'
				}, {
					name : 'mchtMngMode',
					mapping : 'mchtMngMode'
				}, {
					name : 'mchtFunction',
					mapping : 'mchtFunction'
				}, {
					name : 'licenceNo',
					mapping : 'licenceNo'
				}, {
					name : 'licenceEndDate',
					mapping : 'licenceEndDate'
				}, {
					name : 'bankLicenceNo',
					mapping : 'bankLicenceNo'
				}, {
					name : 'busType',
					mapping : 'busType'
				}, {
					name : 'faxNo',
					mapping : 'faxNo'
				}, {
					name : 'busAmt',
					mapping : 'busAmt'
				}, {
					name : 'mchtCreLvl',
					mapping : 'mchtCreLvl'
				}, {
					name : 'contact',
					mapping : 'contact'
				}, {
					name : 'postCode',
					mapping : 'postCode'
				}, {
					name : 'commEmail',
					mapping : 'commEmail'
				}, {
					name : 'commMobil',
					mapping : 'commMobil'
				}, {
					name : 'commTel',
					mapping : 'commTel'
				}, {
					name : 'manager',
					mapping : 'manager'
				}, {
					name : 'artifCertifTp',
					mapping : 'artifCertifTp'
				}, {
					name : 'identityNo',
					mapping : 'identityNo'
				}, {
					name : 'managerTel',
					mapping : 'managerTel'
				}, {
					name : 'fax',
					mapping : 'fax'
				}, {
					name : 'electrofax',
					mapping : 'electrofax'
				}, {
					name : 'regAddr',
					mapping : 'regAddr'
				}, {
					name : 'applyDate',
					mapping : 'applyDate'
				},
				// {name: 'enableDate',mapping: 'enableDate'},
				// {name: 'preAudNm',mapping: 'preAudNm'},
				// {name: 'confirmNm',mapping: 'confirmNm'},
				// {name: 'protocalId',mapping: 'protocalId'},
				{
					name : 'signInstId',
					mapping : 'signInstId'
				}, {
					name : 'netNm',
					mapping : 'netNm'
				}, {
					name : 'agrBr',
					mapping : 'agrBr'
				},
				// {name: 'netTel',mapping: 'netTel'},
				// {name: 'prolDate',mapping: 'prolDate'},
				// {name: 'prolTlr',mapping: 'prolTlr'},
				{
					name : 'closeDate',
					mapping : 'closeDate'
				}, {
					name : 'closeTlr',
					mapping : 'closeTlr'
				}, {
					name : 'operNo',
					mapping : 'operNo'
				}, {
					name : 'operNm',
					mapping : 'operNm'
				}, {
					name : 'procFlag',
					mapping : 'procFlag'
				}, {
					name : 'setCur',
					mapping : 'setCur'
				}, {
					name : 'printInstId',
					mapping : 'printInstId'
				}, {
					name : 'acqInstId',
					mapping : 'acqInstId'
				}, {
					name : 'acqBkName',
					mapping : 'acqBkName'
				}, {
					name : 'bankNo',
					mapping : 'bankNo'
				},
				// {name: 'orgnNo',mapping: 'orgnNo'},
				// {name: 'subbrhNo',mapping: 'subbrhNo'},
				// {name: 'subbrhNm',mapping: 'subbrhNm'},
				{
					name : 'openTime',
					mapping : 'openTime'
				}, {
					name : 'closeTime',
					mapping : 'closeTime'
				},
				// {name: 'visActFlg',mapping: 'visActFlg'},
				// {name: 'visMchtId',mapping: 'visMchtId'},
				// {name: 'mstActFlg',mapping: 'mstActFlg'},
				// {name: 'mstMchtId',mapping: 'mstMchtId'},
				// {name: 'amxActFlg',mapping: 'amxActFlg'},
				// {name: 'amxMchtId',mapping: 'amxMchtId'},
				// {name: 'dnrActFlg',mapping: 'dnrActFlg'},
				// {name: 'dnrMchtId',mapping: 'dnrMchtId'},
				// {name: 'jcbActFlg',mapping: 'jcbActFlg'},
				// {name: 'jcbMchtId',mapping: 'jcbMchtId'},
				{
					name : 'cupMchtFlg',
					mapping : 'cupMchtFlg'
				}, {
					name : 'debMchtFlg',
					mapping : 'debMchtFlg'
				}, {
					name : 'creMchtFlg',
					mapping : 'creMchtFlg'
				}, {
					name : 'cdcMchtFlg',
					mapping : 'cdcMchtFlg'
				}, {
					name : 'idOtherNo',
					mapping : 'idOtherNo'
				}, {
					name : 'recUpdTs',
					mapping : 'recUpdTs'
				}, {
					name : 'recCrtTs',
					mapping : 'recCrtTs'
				}, {
					name : 'updOprId',
					mapping : 'updOprId'
				}, {
					name : 'crtOprId',
					mapping : 'crtOprId'
				}, {
					name : 'internalNo',
					mapping : 'internalNo'
				}, {
					name : 'reject',
					mapping : 'reject'
				}, {
					name : 'mchntAttr',
					mapping : 'mchntAttr'
				}, {
					name : 'linkPer',
					mapping : 'linkPer'
				}, {
					name : 'SettleAreaNo',
					mapping : 'SettleAreaNo'
				}, {
					name : 'MainTlr',
					mapping : 'MainTlr'
				}, {
					name : 'CheckTlr',
					mapping : 'CheckTlr'
				}, {
					name : 'settleType',
					mapping : 'settleType'
				}, {
					name : 'rateFlag',
					mapping : 'rateFlag'
				}, {
					name : 'settleChn',
					mapping : 'settleChn'
				}, {
					name : 'batTime',
					mapping : 'batTime'
				}, {
					name : 'autoStlFlg',
					mapping : 'autoStlFlg'
				}, {
					name : 'partNum',
					mapping : 'partNum'
				}, {
					name : 'feeType',
					mapping : 'feeType'
				}, {
					name : 'feeFixed',
					mapping : 'feeFixed'
				}, {
					name : 'feeMaxAmt',
					mapping : 'feeMaxAmt'
				}, {
					name : 'feeMinAmt',
					mapping : 'feeMinAmt'
				},
				// {name: 'feeRate',mapping: 'feeRate'},
				// {name: 'feeDiv1',mapping: 'feeDiv1'},
				// {name: 'feeDiv2',mapping: 'feeDiv2'},
				// {name: 'feeDiv3',mapping: 'feeDiv3'},
				{
					name : 'settleMode',
					mapping : 'settleMode'
				}, {
					name : 'feeCycle',
					mapping : 'feeCycle'
				}, {
					name : 'settleRpt',
					mapping : 'settleRpt'
				}, {
					name : 'settleBankNo',
					mapping : 'settleBankNo'
				}, {
					name : 'settleBankNm',
					mapping : 'settleBankNm'
				}, {
					name : 'settleAcctNm',
					mapping : 'settleAcctNm'
				}, {
					name : 'settleAcct',
					mapping : 'settleAcct'
				}, {
					name : 'settleAcctMid',
					mapping : 'settleAcctMid'
				}, {
					name : 'settleAcctMidNm',
					mapping : 'settleAcctMidNm'
				}, {
					name : 'settleAcctMidBank',
					mapping : 'settleAcctMidBank'
				}, {
					name : 'clearType',
					mapping : 'clearType'
				}, {
					name : 'feeAcctNm',
					mapping : 'feeAcctNm'
				},
				// {name: 'feeAcct',mapping: 'feeAcct'},
				// {name: 'groupFlag',mapping: 'groupFlag'},
				{
					name : 'openStlno',
					mapping : 'openStlno'
				},
				// {name: 'changeStlno',mapping: 'changeStlno'},
				// {name: 'speSettleTp',mapping: 'speSettleTp'},
				// {name: 'speSettleLv',mapping: 'speSettleLv'},
				// {name: 'speSettleDs',mapping: 'speSettleDs'},
				// {name: 'feeBackFlg',mapping: 'feeBackFlg'},
				{
					name : 'reserved',
					mapping : 'reserved'
				}, {
					name : 'mchtMngMode',
					mapping : 'mchtMngMode'
				},

				{
					name : 'compNm',
					mapping : 'compNm'
				}, {
					name : 'compaddr',
					mapping : 'compaddr'
				}, {
					name : 'finacontact',
					mapping : 'finacontact'
				}, {
					name : 'finacommTel',
					mapping : 'finacommTel'
				}, {
					name : 'finacommEmail',
					mapping : 'finacommEmail'
				}, {
					name : 'busInfo',
					mapping : 'busInfo'
				}, {
					name : 'busArea',
					mapping : 'busArea'
				}, {
					name : 'monaveTrans',
					mapping : 'monaveTrans'
				}, {
					name : 'sigaveTrans',
					mapping : 'sigaveTrans'
				}, {
					name : 'contstartDate',
					mapping : 'contstartDate'
				}, {
					name : 'contendDate',
					mapping : 'contendDate'
				}, {
					name : 'jFee',
					mapping : 'jFee'
				}, {
					name : 'dFee',
					mapping : 'dFee'
				}, {
					name : 'jMaxFee',
					mapping : 'jMaxFee'
				}, {
					name : 'dMaxFee',
					mapping : 'dMaxFee'
				} ,{
					name : 'status',
					mapping : 'status'
				},{
					name : 'areaNo1',
					mapping : 'areaNo1'
				},{
					name : 'mchtGrp1',
					mapping : 'mchtGrp1'
				},{
					name : 'mcc1',
					mapping : 'mcc1'
				},{
					name : 'rislLvl1',
					mapping : 'rislLvl1'
				},{
					name : 'tcc1',
					mapping : 'tcc1'
				},{
					name : 'mchtFunction1',
					mapping : 'mchtFunction1'
				},{
					name : 'prolDate',
					mapping : 'prolDate'
				},{
					name : 'mchtCnAbbr1',
					mapping : 'mchtCnAbbr1'
				},{name: 'bankStatement',mapping: 'bankStatement'},
				{name: 'bankStatementReason',mapping: 'bankStatementReason'},
				{name: 'integral',mapping: 'integral'},
				{name: 'integralReason',mapping: 'integralReason'},
				{name: 'emergency',mapping: 'emergency'},
				{name: 'country',mapping: 'country'},
				{name: 'compliance',mapping: 'compliance'},
				{name: 'addrProvince',mapping: 'addrProvince'}
				]),
				autoLoad : false 
			});

			// ******************第一步按钮******************
			var nextBt1 = {
				xtype : 'button',
				width : 100,
				text : '下一步',
				id : 'nextBt1',
				// columnWidth: .5,
				height : 30,
				handler : function() {
					if (true/*Ext.getCmp('idbankNo').isValid()
							& Ext.getCmp('compNm').isValid()
							& Ext.getCmp('compaddr').isValid()
							& Ext.getCmp('licenceNo').isValid()
							& Ext.getCmp('faxNo').isValid()
							& Ext.getCmp('manager').isValid()
							& Ext.getCmp('idartifCertifTp').isValid()
							& Ext.getCmp('identityNo').isValid()
							& Ext.getCmp('electrofax').isValid()
							& Ext.getCmp('idmchtGroupId').isValid()
							& Ext.getCmp('mchtNm').isValid()
							& Ext.getCmp('addr').isValid()
							& Ext.getCmp('contact').isValid()
							& Ext.getCmp('commTel').isValid()
							& Ext.getCmp('finacontact').isValid()
							& Ext.getCmp('finacommTel').isValid()
							& Ext.getCmp('busInfo').isValid()
							& Ext.getCmp('busArea').isValid()
							& Ext.getCmp('monaveTrans').isValid()
							& Ext.getCmp('sigaveTrans').isValid()*/) {
						
						var etpsAttrId=Ext.getCmp('etpsAttrId').getValue();
						if(etpsAttrId=='01'|etpsAttrId=='02'){
							Ext.getCmp('imageInfo6').show();
							Ext.getCmp('imageInfo7').show();
							Ext.getCmp('imageInfo8').hide();
							Ext.getCmp('imageInfo9').hide();
						}else if(etpsAttrId=='03'){
							Ext.getCmp('imageInfo6').hide();
							Ext.getCmp('imageInfo7').show();
							Ext.getCmp('imageInfo9').hide();
							
						}
						Ext.getCmp('imageInfo8').show();
						Ext.getCmp('baseInfo').hide();
						Ext.getCmp('manageInfo').hide();
						//Ext.getCmp('businessInfo').hide();
						Ext.getCmp('settleInfo').show();
//						Ext.getCmp('contdateInfo').show();
						Ext.getCmp('imageInfo').show();
						Ext.getCmp('imageInfo1').hide();
						Ext.getCmp('imageInfo2').hide();
						Ext.getCmp('imageInfo6').hide();
						Ext.getCmp('imageInfo7').show();
						Ext.getCmp('imageInfo3').hide();
						Ext.getCmp('imageInfo4').hide();
						Ext.getCmp('imageInfo5').show();
						Ext.getCmp('imageInfo10').show();
						Ext.getCmp('statement_integral_Info').show();
						
						Ext.getCmp('mccInfo').hide();
						Ext.getCmp('feeInfo').hide();
						Ext.getCmp('fee').show();
						Ext.getCmp('next1').hide();
						Ext.getCmp('next2').show();
						Ext.getCmp('next3').hide();
						Ext.getCmp('next4').hide();
						Ext.getCmp('limitDateInfo').hide();
						Ext.getCmp('profitInfo').show();
					}
				}
			};

			var nextArr1 = new Array();
			nextArr1.push(nextBt1); // [0]

			// ******************第二步按钮******************
			var nextBt2 = {
				xtype : 'button',
				width : 100,
				text : '上一步',
				id : 'nextBt2',
				height : 30,
				handler : function() {
					var etpsAttrId=Ext.getCmp('etpsAttrId').getValue();
					if(etpsAttrId=='01'|etpsAttrId=='02'){
						Ext.getCmp('imageInfo1').show();
						Ext.getCmp('imageInfo2').show();
						Ext.getCmp('imageInfo6').show();
						Ext.getCmp('imageInfo7').hide();
					}else if(etpsAttrId=='03'){
						Ext.getCmp('imageInfo1').hide();
						Ext.getCmp('imageInfo2').hide();
						Ext.getCmp('imageInfo6').hide();
						Ext.getCmp('imageInfo7').hide();
					}
					
					//Ext.getCmp('imageInfo6').hide();
					//Ext.getCmp('imageInfo7').hide();
					Ext.getCmp('imageInfo8').hide();
					Ext.getCmp('imageInfo9').hide();
					Ext.getCmp('imageInfo10').hide();
					Ext.getCmp('baseInfo').show();
					Ext.getCmp('manageInfo').show();
					//Ext.getCmp('businessInfo').show();
					Ext.getCmp('settleInfo').hide();
//					Ext.getCmp('contdateInfo').hide();
					Ext.getCmp('imageInfo').hide();
					Ext.getCmp('imageInfo3').show();
					Ext.getCmp('imageInfo4').show();
					Ext.getCmp('imageInfo5').hide();
					Ext.getCmp('mccInfo').hide();
					Ext.getCmp('feeInfo').hide();
					Ext.getCmp('fee').hide();
					Ext.getCmp('next1').show();
					Ext.getCmp('next2').hide();
					Ext.getCmp('next3').hide();
					Ext.getCmp('limitDateInfo').hide();
					Ext.getCmp('next4').hide();
					Ext.getCmp('profitInfo').hide();
					Ext.getCmp('statement_integral_Info').hide();
				}
			};

			var nextBt21 = {
				xtype : 'button',
				width : 100,
				text : '下一步',
				id : 'nextBt21',
				height : 30,
				handler : function() {
					if (true/*Ext.getCmp('settleAcctNm').isValid()
							& Ext.getCmp('settleAcct').isValid()
							& Ext.getCmp('settleAcctConfirm').isValid()
							& Ext.getCmp('settleBankNm').isValid()
							& Ext.getCmp('openStlno').isValid()*/)
						//	& Ext.getCmp('contstartDate').isValid()
						//	& Ext.getCmp('contendDate').isValid())
//						if (mchntForm.findById('settleAcct').getValue() != mchntForm
//								.findById('settleAcctConfirm').getValue()) {
//							showErrorMsg('两次输入商户账户账号不一致，请确认！', mchntForm);
//						} else {
							Ext.getCmp('baseInfo').hide();
							Ext.getCmp('manageInfo').hide();
							//Ext.getCmp('businessInfo').hide();
							Ext.getCmp('settleInfo').hide();
//							Ext.getCmp('contdateInfo').hide();
							Ext.getCmp('imageInfo').hide();
							Ext.getCmp('imageInfo1').hide();
							Ext.getCmp('imageInfo2').hide();
							Ext.getCmp('imageInfo3').hide();
							Ext.getCmp('imageInfo4').hide();
							Ext.getCmp('imageInfo5').hide();
							Ext.getCmp('imageInfo6').hide();
							Ext.getCmp('imageInfo7').hide();
							Ext.getCmp('imageInfo8').hide();
							Ext.getCmp('imageInfo9').hide();
							Ext.getCmp('imageInfo10').hide();
							Ext.getCmp('mccInfo').show();
							Ext.getCmp('feeInfo').hide();
							Ext.getCmp('fee').hide();
							Ext.getCmp('next1').hide();
							Ext.getCmp('next2').hide();
							Ext.getCmp('next3').show();
							Ext.getCmp('limitDateInfo').show();
							Ext.getCmp('next4').show();
							Ext.getCmp('profitInfo').hide();
							Ext.getCmp('statement_integral_Info').hide();
						}
			};

			var nextArr2 = new Array();
			nextArr2.push(nextBt2); // [0]
			nextArr2.push(nextBt21); // [1]
			// ******************第三步按钮******************
			var nextBt3 = {
				xtype : 'button',
				width : 100,
				text : '上一步',
				id : 'nextBt3',
				height : 30,
				handler : function() {
					
					var etpsAttrId=Ext.getCmp('etpsAttrId').getValue();
					if(etpsAttrId=='01'|etpsAttrId=='02'){
						//Ext.getCmp('imageInfo6').show();
						//Ext.getCmp('imageInfo7').show();
						Ext.getCmp('imageInfo8').hide();
						Ext.getCmp('imageInfo9').hide();
					}else if(etpsAttrId=='03'){
						//Ext.getCmp('imageInfo6').hide();
						//Ext.getCmp('imageInfo7').hide();
						Ext.getCmp('imageInfo9').hide();
						
					}
					Ext.getCmp('statement_integral_Info').show();
					Ext.getCmp('imageInfo8').show();
					Ext.getCmp('imageInfo7').show();
					Ext.getCmp('profitInfo').show();
					Ext.getCmp('baseInfo').hide();
					Ext.getCmp('manageInfo').hide();
					//Ext.getCmp('businessInfo').hide();
					Ext.getCmp('settleInfo').show();
//					Ext.getCmp('contdateInfo').show();
					Ext.getCmp('imageInfo').show();
					Ext.getCmp('imageInfo5').show();
					Ext.getCmp('imageInfo10').show();
					Ext.getCmp('mccInfo').hide();
					Ext.getCmp('feeInfo').hide();
					Ext.getCmp('fee').show();
					Ext.getCmp('next1').hide();
					Ext.getCmp('next2').show();
					Ext.getCmp('next3').hide();
					Ext.getCmp('limitDateInfo').hide();
					Ext.getCmp('next4').hide();
				}
			};
			var nextBt32 = {
				xtype : 'button',
				width : 100,
				text : '下一步',
				id : 'nextBt32',
				height : 30,
				handler : function() {
					Ext.getCmp('baseInfo').hide();
					Ext.getCmp('manageInfo').hide();
					//Ext.getCmp('businessInfo').hide();
					Ext.getCmp('settleInfo').hide();
//					Ext.getCmp('contdateInfo').hide();
					Ext.getCmp('imageInfo').hide();
					Ext.getCmp('mccInfo').hide();
					Ext.getCmp('feeInfo').hide();
					Ext.getCmp('fee').hide();
					Ext.getCmp('next1').hide();
					Ext.getCmp('next2').show();
					Ext.getCmp('next3').hide();
					Ext.getCmp('limitDateInfo').hide();
					Ext.getCmp('next4').show();
					Ext.getCmp('profitInfo').hide();
					
				}
			};

			var nextBt31 = {
				xtype : 'button',
				width : 100,
				text : '通过',
				id : 'nextBt31',
				height : 30,
				handler : function() {
					subSave();
				}
			};
			var checkIds = "T";
			var hasUpload = "1";

			function subSave() {
				var btn = Ext.getCmp('nextBt31');		
				var frm = mchntForm.getForm();		
				//if (Ext.getCmp('mchtCnAbbr').isValid()&Ext.getCmp('areaNoId').isValid()&Ext.getCmp('clearType').isValid()&Ext.getCmp('rislLvlId').isValid()&Ext.getCmp('tccId').isValid()&Ext.getCmp('mchtFunctionId').isValid()) {
				if (Ext.getCmp('mchtCnAbbr').isValid()&Ext.getCmp('idmcc').isValid()&Ext.getCmp('rislLvlId').isValid()&Ext.getCmp('mchtFunctionId').isValid()) {
//					if(mchntForm.findById('settleAcct').getValue()!=mchntForm.findById('settleAcctConfirm').getValue()){
//						showErrorMsg('两次输入商户账户账号不一致，请确认！',mchntForm);
//					}else {
		        		var checkStr = Ext.getCmp("openStlno").getValue();
		         		T20100.checkCnapsId(checkStr,function(ret){
				    		if(ret=='F'){
		             			showErrorMsg("无效开户行号，请重新确认！", mchntForm);
//		             			Ext.getCmp("openStlno").setValue("");
		             			Ext.getCmp("nextBt3").handler();
				    		}else{
								btn.disable();
								frm.submit({
									url: 'T2090401Action_accept.asp',
//									url: 'T2090401Action.asp?method=accept',
									waitTitle : '请稍候',
									waitMsg : '正在提交表单数据,请稍候...',
									success : function(form, action) {
										showSuccessAlert(action.result.msg,mchntForm,250,function(){
											window.location.href = Ext.contextPath + '/page/mchnt/T20200.jsp';
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
										txnId: '20904',
										subTxnId: '01',
										checkIds: checkIds,
										hasUpload: hasUpload,
										imagesId: imagesId,
										clearType: Ext.getCmp('clearType').getValue(),
										mcht:mchntId/*,
										'tblMchtSettleInfTmpTmp.speSettleTp':profit*/
									}
								});
				    		}
				    	})
				}else{
					if (!Ext.getCmp('mchtCnAbbr').isValid()){
						showErrorMsg('中文简写未输入或输入有误，请确认！',mchntForm);
						return;
					}
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
				}
			}
			
			var nextBt32 = {
					xtype : 'button',
					width : 100,
					text : '退回',
					id : 'nextBt32',
					height : 30,
					handler :function() {
						showConfirm('确认退回吗？',mchntForm, function(bt) {
							if (bt == 'yes') {
								showInputMsg('提示', '请输入退回原因', true, back);
							}
						});
					}
				};

			// 退回按钮执行的方法
			function back(bt, text) {
				if (bt == 'ok') {
					if (getLength(text) > 60 || getLength(text) == ''
							|| text.trim() == '') {
						alert('提交退回原因不能为空且最多可以输入30个汉字或60个字母、数字!');
						showInputMsg('提示', '请输入退回原因', true, back);
						return;
					}
					// showProcessMsg('正在提交审核信息，请稍后......');
//					rec = mchntGrid.getSelectionModel().getSelected();
					Ext.Ajax.request({
						url : 'T20904Action.asp?method=back',
						params : {
							mchntId :mchntId,
							txnId : '20904',
							subTxnId : '02',
							refuseInfo : text
						},
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
//								showSuccessMsg(rspObj.msg, mchntForm);
								showSuccessAlert(rspObj.msg,mchntForm,250,function(){
									window.location.href = Ext.contextPath + '/page/mchnt/T20200.jsp';
								});
							} else {
								showErrorMsg(rspObj.msg, mchntForm);
							}
							// 重新加载商户待审核信息
//							mchntForm.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			}

			var nextArr3 = new Array();
			nextArr3.push(nextBt3); // [0]
			nextArr3.push(nextBt31);
			nextArr3.push(nextBt32);// [0]

			/*
			 * var nextArr4 = new Array(); nextArr4.push(nextBt3); // [0]
			 * nextArr4.push(nextBt31);
			 */// [0]
		
			
			// ******************表单信息******************
			var mchntForm = new Ext.FormPanel(
					{
						region : 'center',
						iconCls : 'mchnt',
						frame : true,
						html : '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
						height : 560,

						// modal: true,
						// height: Ext.getBody().getHeight(true),
						// width: Ext.getBody().getWidth(true),
						autoScroll : true,
						waitMsgTarget : true,
						/*
						 * defaults: { bodyStyle: 'padding-left: 10px' },
						 */
						items : [{
								columnWidth : .99,
								xtype : 'panel',
								id:'refuseReason',
								layout : 'form',
								labelWidth: 0,
								style:'padding-bottom:10',
								hide: true,
								width: 500,
								items : [{
									columnWidth : .99,
									layout : 'column',
									items : [{
							        	xtype: 'button',
										id: 'btn',
										height:30,
										text:'查看审批过程',
										handler : function() {
											showApproveInfo(mchntId);
										}
									},{
							        	xtype: 'label',
							        	columnWidth : .88,
							        	width:400,
										id: 'refuseReasonTxt',
										style:'color:red;font-weight:bold;padding:10 0 0 20'
									}]
								}]},
								// ******************第一步注册信息******************
								{
									id : 'baseInfo',
									xtype : 'fieldset',
									title : '注册信息',
									layout : 'column',
									collapsible : true,
									// width: 1000,
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
											{
												columnWidth : .25,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'MCHT_GROUP_FLAG',
													fieldLabel : '商户种类*',
													id : 'idmchtGroupFlag',
													hiddenName : 'tblMchtBaseInfTmpTmp.mchtGroupFlag',
													allowBlank : false,
													width : 150,
													listeners : {
														'select' : function() {
															if (Ext
																	.getCmp(
																			"idmchtGroupFlag")
																	.getValue() == '1') {
																Ext
																		.getCmp(
																				'idmchtGroupId')
																		.disable();
																Ext
																		.getCmp(
																				"idmchtGroupId")
																		.setValue(
																				"-");
															} else if (Ext
																	.getCmp(
																			"idmchtGroupFlag")
																	.getValue() == '2') {
																Ext
																		.getCmp(
																				'idmchtGroupId')
																		.disable();
																Ext
																		.getCmp(
																				"idmchtGroupId")
																		.setValue(
																				"-");
															} else if (Ext
																	.getCmp(
																			"idmchtGroupFlag")
																	.getValue() == '3') {
																Ext
																		.getCmp(
																				'idmchtGroupId')
																		.enable();
																if (baseStore
																		.getAt(0).data.mchtGroupId == '-') {
																	Ext
																			.getCmp(
																					"idmchtGroupId")
																			.setValue(
																					"");
																} else {
																	Ext
																			.getCmp(
																					"idmchtGroupId")
																			.setValue(
																					baseStore
																							.getAt(0).data.mchtGroupId);
																}
															}
														}
													}
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												width : 150,
												hidden : true,
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'CONN_TYPE',
													fieldLabel : '商户接入方式*',
													id : 'idconnType',
													hiddenName : 'tblMchtBaseInfTmpTmp.connType',
													allowBlank : true,
													width : 150,
													value : 'J'
												} ]
											},
											
											{
												columnWidth : .75,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'MCHNT_ATTR',
													fieldLabel : '商户类型',
													width : 150,
													hiddenName : 'tblMchtBaseInfTmpTmp.etpsAttr',
													id : 'etpsAttrId',
													listeners : {}
												} ]
											},
											{
												columnWidth : .5,
												xtype : 'panel',
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'MCHT_GROUP_NEW',
													fieldLabel : '所属集团商户*',
													width : 418,
													id : 'idmchtGroupId',
													hiddenName : 'tblMchtBaseInfTmpTmp.mchtGroupId',
													disabled : true,
													allowBlank : false,
													//anchor : '90%'
												} ]
											},{
												columnWidth : .5,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'BRH_BELOW_BRANCH',
													id : 'idbankNo',
													hiddenName : 'tblMchtBaseInfTmpTmp.bankNo',
													fieldLabel : '合作伙伴*',
													allowBlank : false,
													blankText : '请选择合作伙伴',
													width : 200,
													mode : 'local',
													triggerAction : 'all',
													forceSelection : true,
													typeAhead : true,
													selectOnFocus : true,
													editable : true,
													lazyRender : true
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												id : 'compNmPanel',
												items : [ {
													readOnly : true,
													xtype : 'textnotnull',
													fieldLabel : '注册名称*',
													maxLength : '60',
													vtype : 'isOverMax',
													id : 'mchtNm',
													name : 'tblMchtBaseInfTmpTmp.mchtNm',
													//anchor : '90%'
													width:402
												} ]
											},{
												columnWidth: .5,
												layout: 'form',
												items: [{
													xtype: 'textfield',
													fieldLabel: '中文简写',
													allowBlank: true,
													readOnly:true,
													id: 'mchtCnAbbr',
													name: 'tblMchtBaseInfTmpTmp.mchtCnAbbr',
													width: 133
												}]
											},{
												columnWidth : .5,
												layout : 'form',
												id:'accountHide1',
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '注册地址*',
													//anchor : '90%'
													width:402,
													maxLength : 60,
													vtype : 'isOverMax',
													id : 'compaddr',
													name : 'tblMchtBaseInfTmpTmp.compaddr'
												} ]
											},{
												columnWidth : .5,
												id:'accountHide2',
												layout : 'form',
												// labelwidth: 100,
												items : [ {
													readOnly : true,
													xtype : 'numberfield',
													fieldLabel : '注册资金',
													regex : /^(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*))$/,
													regexText : '注册资金必须是正数，如123.45',
													maxLength : 12,
													vtype : 'isOverMax',
													width: 133,
													id : 'busAmt',
													name : 'tblMchtBaseInfTmpTmp.busAmt'
												} ]
											},{
												columnWidth : .5,
												layout : 'form',
												id : 'accountHide3',
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '营业执照编号*',
													width : 150,
													maxLength : 20,
													id : 'licenceNo',
													name : 'tblMchtBaseInfTmpTmp.licenceNo'
												} ]
											},{
												columnWidth: .5,
												layout: 'form',
												id:'accountHide4',
												items: [{
													xtype: 'datefield',
													id :'licenceEndDate',
													editable: false,
													allowblank:true,
													readOnly: true,
													width:150,
													name: 'tblMchtBaseInfTmpTmp.licenceEndDate',
													//vtype: 'daterange',
										            fieldLabel: '营业执照有效期',
										            //endDateField: 'contendDate',
										            format: 'Y-m-d',
//										            minValue:new Date(),
//													blankText: '请选择起始日期',
												}]											
											},{
												columnWidth : 1,
												layout : 'form',
												id : 'accountHide5',
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '税务登记证号码*',
													maxLength : 20,
													allowblank:true,
													vtype : 'isOverMax',
													width : 150,
													id : 'faxNo',
													name : 'tblMchtBaseInfTmpTmp.faxNo'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												hidden:true,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '英文名称',
													maxLength : 40,
													vtype : 'isOverMax',
													regex : /^\w+[\w\s]+\w+$/,
													regexText : '英文名称必须是字母，如Bill Gates',
													id : 'engName',
													name : 'tblMchtBaseInfTmpTmp.engName',
													width : 150
												} ]
											},
											{},
											{
												columnWidth : .33,
												layout : 'form',
												hidden:true,
												items : [ {
													readOnly : true,
													// xtype: 'numberfield',
													xtype : 'textfield',
													fieldLabel : '邮政编码',
													width : 150,
													regex : /^[0-9]{6}$/,
													regexText : '邮政编码必须是6位数字',
													id : 'postCode',
													name : 'tblMchtBaseInfTmpTmp.postCode'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												hidden : true,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '公司网址',
													regex : /^[a-zA-z]+:/,
													regexText : '必须是正确的地址格式，如http://www.xxx.com',
													// maxLength: 60,
													vtype : 'isOverMax',
													width : 150,
													id : 'homePage',
													name : 'tblMchtBaseInfTmpTmp.homePage',
													maxLength : 50
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												
												// labelwidth: 100,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '法人代表*',
													width : 150,
													maxLength: 50,
													vtype: 'lengthRange50',
													id : 'manager',
													name : 'tblMchtBaseInfTmpTmp.manager'
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'CERTIFICATE',
													fieldLabel : '法人证件类型*',
													width : 150,
													allowBlank : false,
													hiddenName : 'tblMchtBaseInfTmpTmp.artifCertifTp',
													id : 'idartifCertifTp',
													value : '01'
												} ]
											},
											{
												columnWidth : .5,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '法人证件号码*',
													width : 150,
													maxLength : 20,
													vtype : 'isOverMax',
													id : 'identityNo',
													name : 'tblMchtBaseInfTmpTmp.identityNo'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												hidden:true,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '企业电话',
													width : 150,
													regex : /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
													regexText : '长度不够，电话必须是7~9位',
													maxLength : 15,
													vtype : 'isOverMax',
													id : 'electrofax',
													name : 'tblMchtBaseInfTmpTmp.electrofax'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												 hidden:true,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '企业传真',
													width : 150,
													regex : /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
													maxLength : 20,
													vtype : 'isOverMax',
													id : 'fax',
													name : 'tblMchtBaseInfTmpTmp.fax'
												} ]
											} ,{
												columnWidth: .5,
												layout: 'form',
												items: [{
													xtype: 'datefield',
													id :'prolDate',
													editable: false,
													readOnly: true,
													width:150,
													name: 'tblMchtBaseInfTmpTmp.prolDate',
													//vtype: 'daterange',
										            fieldLabel: '证件有效期至',
										            //endDateField: 'contendDate',
										            format: 'Y-m-d',
										            //minValue: new Date(),
//													blankText: '请选择起始日期',
													allowBlank: true
												}]											
											}]
								},{


									id: 'manageBaseInfo',
//									width: 1035,
									collapsible: true,
									hidden:true,
									xtype: 'fieldset',
									title: '登记信息',
									layout: 'column',
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items: []
									
								
								},
								{
									id: 'imageInfo1',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '营业执照影像',
									layout: 'column',
//									autoScroll: true,
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
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload1'
													}
												});
											}
										},{}]
								},{
									id: 'imageInfo2',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '税务登记证影像',
									layout: 'column',
//									autoScroll: true,
									hidden:false,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview2,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view2',
											handler:function() {
												storeImg2.reload({
													params: {
														start: 0,
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload2'
													}
												});
											}
										},{}]
								},{

									id: 'imageInfo6',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '组织机构代码影像',
									layout: 'column',
//									autoScroll: true,
									hidden:false,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview6,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view6',
											handler:function() {
												storeImg6.reload({
													params: {
														start: 0,
														imagesId: mchntId,
														mcht:mchntId,
														upload:'upload6'
													}
												});
											}
										}]
								
								},{
									id: 'imageInfo3',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '证件影像',
									layout: 'column',
//									autoScroll: true,
									hidden:false,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview3,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view3',
											handler:function() {
												storeImg3.reload({
													params: {
														start: 0,
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload3'
													}
												});
											}
										},{}]
								},{
									id: 'imageInfo4',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '装机地址影像',
									layout: 'column',
//									autoScroll: true,
									hidden:false,
									defaults: {
										bodyStyle: 'padding-left: 10px',
										visibility : 'hidden' 
									},
									items : dataview4,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view4',
											handler:function() {
												storeImg4.reload({
													params: {
														start: 0,
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload4'
													}
												});
											}
										},{}]
								},
								// ******************第一步经营信息******************
								{
									id : 'manageInfo',
									// width: 1000,
									collapsible : true,
									xtype : 'fieldset',
									title : '经营信息',
									layout : 'column',
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [{
										columnWidth: .99,
										layout: 'form',
										id: 'mchtNmPanel',
										items: [{
											xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '经营名称',
											maxLength: '60',
											vtype: 'isOverMax',
											id: 'compNm',
											name:'tblMchtBaseInfTmpTmp.compNm',
											//anchor: '90%',
											width:402
										}]
									},{				
										columnWidth: .27,
										layout: 'form',
										items: [{
												layout: 'column',
												xtype: 'basecomboselect',
									        	baseParams: 'ADDR_PROVINCE',
												fieldLabel: '装机地址<font color=red>*</font>',
												store: provinceStore,
												hiddenName : 'tblMchtBaseInfTmpTmp.addrProvince',
												displayField: 'displayField',
												valueField: 'valueField',
												id:'provinceAddress',
												width:167,
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
									},{
										columnWidth: .03,
										layout: 'form',
										html:'省'
									},{
										columnWidth: .20,
										layout: 'form',
										labelWidth:20,
										labelPad:16,
										items: [{
										xtype: 'basecomboselect',
							        	baseParams: 'ADDR_CITY',
										fieldLabel: '',
										store: cityStore,
										displayField: 'displayField',
										valueField: 'valueField',
										id:'cityAddress',
										hiddenName : 'tblMchtBaseInfTmpTmp.areaNo',
										width:167,
										readOnly : true,
										listeners: {}}]
						        	
									
									},{
										columnWidth: .03,
										layout: 'form',
										html:'市'
									},{
										columnWidth: .67,
							        	layout: 'form',
							        	items: [{
							        		xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '',
											//anchor: '90%',
											width:402,
											maxLength: 60,
											vtype: 'isOverMax',
											id: 'addr',
	//										allowBlank : false,
	//										blankText:'请输入详细地址',
	//										emptyText:'详细地址',
											name:'tblMchtBaseInfTmpTmp.addr'
							        	}]
									},{
										
											columnWidth: .99,
											layout: 'form',
											items: [{
												xtype: 'textfield',
									        	readOnly:true,
												fieldLabel: '经营内容',
												maxLength: '60',
												vtype: 'isOverMax',
												id: 'busInfo',
												name:'tblMchtBaseInfTmpTmp.busInfo',
												//anchor: '90%'
												width:402
										}]
									},{
						        		columnWidth: .28,
							        	layout: 'form',
						       			items: [{
								        	xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '营业时间(hh:mm)',
											width:150,
//					                        maxLength: 5,
					                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
											regexText: '时间输入错误',
											id: 'openTime',
											name: 'tblMchtBaseInfTmpTmp.openTime',
											value: '00:00'
							        	}]
									},{
						        		columnWidth: .22,
							        	layout: 'form',
							        	labelWidth:27,
										labelPad:30,
						       			items: [{
						       				xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '至',
											width:150,
//					                        maxLength: 5,
					                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
											regexText: '时间输入错误',
											value: '23:59',
											id: 'closeTime',
											name: 'tblMchtBaseInfTmpTmp.closeTime'
							        	}]
									},{
											columnWidth: .5,
											layout: 'form',
											items: [{
												xtype: 'textfield',
									        	readOnly:true,
												width: 150,
												fieldLabel: '经营面积(m²)',
												maxLength: 10,
												vtype: 'isOverMax',
												id: 'busArea',
												name:'tblMchtBaseInfTmpTmp.busArea'
											}]
									},
									{
//										columnWidth : .99,
//										layout : 'form',
//										hidden:true,
//										items : [{
//											xtype : 'dynamicCombo',
//											methodName : 'getAreaCode',
//											fieldLabel : '所在地区',
//											hiddenName : 'tblMchtBaseInfTmpTmp.areaNo',
//											id : 'areaNoId',
//											allowBlank : true,
//											width:260
//										}]
									},{
										columnWidth: .28,
										layout: 'form',
										items: [{
											xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '业务联系人',
											width:150,
											maxLength: 30,
											vtype: 'isOverMax',
											id: 'contact',
											name:'tblMchtBaseInfTmpTmp.contact'
										}]
									},{
										columnWidth: .22,
										layout: 'form',
										labelWidth:30,
										labelPad:27,
										items: [{
											xtype: 'textfield',
								        	readOnly:true,
											fieldLabel: '电话',
											width:150,
											regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
											regexText:'长度不够，电话必须是7~9位',
											maxLength: 18,
											vtype: 'isOverMax',
											id: 'commTel',
											name:'tblMchtBaseInfTmpTmp.commTel'
										}]
									},{
										columnWidth: .5,
										layout: 'form',
										items: [{
												xtype: 'textfield',
									        	readOnly:true,
												fieldLabel: '企业邮箱',
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
										hidden:true,
										items: [{
											xtype: 'displayfield',
											fieldLabel: '财务联系人',
											width:150,
											maxLength: 30,
											vtype: 'isOverMax',
											id: 'finacontact',
											name:'tblMchtBaseInfTmpTmp.finacontact'
										}]
									},{
										columnWidth: .33,
										layout: 'form',
										hidden:true,
										items: [{
											xtype: 'displayfield',
											fieldLabel: '财务联系人电话',
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
										hidden:true,
										items: [{
											xtype: 'displayfield',
												fieldLabel: '财务联系人邮箱',
												width:150,
												maxLength: 40,
												vtype: 'isOverMax',
												id: 'finacommEmail',
												name: 'tblMchtBaseInfTmpTmp.finacommEmail',
												vtype: 'email'
										}]
									}			
//									,{
//										columnWidth: .66,
//										layout: 'form',
//										id: 'mchtNmPanel',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '经营名称',
//											maxLength: '60',
//											vtype: 'isOverMax',
//											id: 'compNm',
//											name:'tblMchtBaseInfTmpTmp.compNm',
//											anchor: '90%'
////											width:476
//										}]
//									},{
//						        		columnWidth: .33,
//							        	layout: 'form',
//						       			items: [{
//								        	xtype: 'displayfield',
//											fieldLabel: '营业开始时间(hh:mm)',
//					                        maxLength: 5,
//					                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
//											regexText: '时间输入错误',
//											id: 'openTime',
//											name: 'openTime',
//											value: '00:00'
//							        	}]
//									},{				
//										columnWidth: .66,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '装机地址',
////											width:476,
//											anchor: '90%',
//											maxLength: 60,
//											vtype: 'isOverMax',
//											id: 'addr',
//											name:'tblMchtBaseInfTmpTmp.addr'
//										}]
//									},{
//						        		columnWidth: .33,
//							        	layout: 'form',
//						       			items: [{
//						       				xtype: 'displayfield',
//											fieldLabel: '营业结束时间(hh:mm)',
//					                        maxLength: 5,
//					                        regex: /^([01]\d|2[0123]):([0-5]\d)$/,
//											regexText: '时间输入错误',
//											value: '23:59',
//											id: 'closeTime',
//											name: 'closeTime'
//							        	}]
//									},{
//										columnWidth: .99,
//							        	layout: 'form',
//							        	xtype: 'panel',
//							       		items: [{
//												xtype: 'combofordispaly',
//									        	baseParams: 'CITY_CODE',
//												fieldLabel: '所在地区',
//												hiddenName: 'areaNo',
//												width:260
//								        	}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '业务联系人',
//											width:150,
//											maxLength: 30,
//											vtype: 'isOverMax',
//											id: 'contact',
//											name:'tblMchtBaseInfTmpTmp.contact'
//										}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '业务联系人电话',
//											width:150,
//											regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//											regexText:'长度不够，电话必须是7~9位',
//											maxLength: 18,
//											vtype: 'isOverMax',
//											id: 'commTel',
//											name:'tblMchtBaseInfTmpTmp.commTel'
//										}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//												fieldLabel: '业务联系人邮箱',
//												width:150,
//												maxLength: 40,
//												vtype: 'isOverMax',
//												id: 'commEmail',
//												name: 'tblMchtBaseInfTmpTmp.commEmail',
//												vtype: 'email'
//										}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '财务联系人',
//											width:150,
//											maxLength: 30,
//											vtype: 'isOverMax',
//											id: 'finacontact',
//											name:'tblMchtBaseInfTmpTmp.finacontact'
//										}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//											fieldLabel: '财务联系人电话',
//											width:150,
//											regex: /(\d{11})|^((\d{7,9})|(\d{4}|\d{3})-(\d{7,9})|(\d{4}|\d{3})-(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,9})-(\d{4}|\d{3}|\d{2}|\d{1}))$/,
//											regexText:'长度不够，电话必须是7~9位',
//											maxLength: 18,
//											vtype: 'isOverMax',
//											id: 'finacommTel',
//											name:'tblMchtBaseInfTmpTmp.finacommTel'
//										}]
//									},{
//										columnWidth: .33,
//										layout: 'form',
//										items: [{
//											xtype: 'displayfield',
//												fieldLabel: '财务联系人邮箱',
//												width:150,
//												maxLength: 40,
//												vtype: 'isOverMax',
//												id: 'finacommEmail',
//												name: 'tblMchtBaseInfTmpTmp.finacommEmail',
//												vtype: 'email'
//										}]
//									}			
									]
								},
								// ******************第一步经营信息******************
								{
									id : 'businessInfo',
									// width: 1000,
									collapsible : true,
									xtype : 'fieldset',
									hidden:true,
									title : '营业状况',
									layout : 'column',
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [/*
											{
												columnWidth : .66,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'textnotnull',
													fieldLabel : '经营内容*',
													maxLength : '60',
													vtype : 'isOverMax',
													id : 'busInfo',
													name : 'tblMchtBaseInfTmpTmp.busInfo',
													anchor : '90%'
												// width:476
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													width : 150,
													fieldLabel : '经营面积(m²)',
													maxLength : 10,
													vtype : 'isOverMax',
													id : 'busArea',
													name : 'tblMchtBaseInfTmpTmp.busArea'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												hidden:true,
												items : [ {
													readOnly : true,
													xtype : 'combo',
													fieldLabel : '月平均营业额*',
													id : 'monaveTrans',
													width : 150,
													hidden:true,
													allowBlank : true,
													editable : false,
													hiddenName : 'tblMchtBaseInfTmpTmp.monaveTrans',
													store : new Ext.data.ArrayStore(
															{
																fields : [
																		'valueField',
																		'displayField' ],
																data : [
																		[ '01',
																				'5万元以下' ],
																		[ '02',
																				'25万元以下' ],
																		[ '03',
																				'50万元以下' ],
																		[ '04',
																				'100万元以下' ],
																		[ '05',
																				'100万元以上' ] ]
															})
												} ]
											},
											{
												columnWidth : .33,
												hidden:true,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'combo',
													fieldLabel : '单笔平均交易额*',
													id : 'sigaveTrans',
													width : 150,
													hidden:true,
													allowBlank : true,
													editable : false,
													hiddenName : 'tblMchtBaseInfTmpTmp.sigaveTrans',
													store : new Ext.data.ArrayStore(
															{
																fields : [
																		'valueField',
																		'displayField' ],
																data : [
																		[ '01',
																				'50元以下' ],
																		[ '02',
																				'200元以下' ],
																		[ '03',
																				'1000元以下' ],
																		[ '04',
																				'5000元以下' ],
																		[ '05',
																				'5000元以上' ] ]
															})
												} ]
											} */]
								},
								// ******************第一步按钮（下一步）信息******************
								{
									xtype : 'fieldset',
									id : 'next1',
									defaults : {
										bodyStyle : 'padding-left: 100px'
									},
									// width: 1000,
									buttonAlign : 'center',
									buttons : nextArr1
								},
								// ******************第二步账户信息******************
								{
									id : 'settleInfo',
									// width: 1000,
									collapsible : true,
									xtype : 'fieldset',
									title : '账户信息',
									layout : 'column',
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
											{
												columnWidth : .30,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'textnotnull',
													fieldLabel : '账户户名*',
													width : 150,
													maxLength : 80,
													vtype : 'isOverMax',
													id : 'settleAcctNm',
													name : 'tblMchtSettleInfTmpTmp.settleAcctNm'
												} ]
											},
											{
												columnWidth : .7,
												layout : 'form',
												items : [ {
													readOnly : true,
													// xtype: 'numberfield',
													xtype : 'textfield',
													fieldLabel : '账户号*',
													maxLength : 40,
													allowBlank : false,
													vtype : 'isOverMax',
													regex : /^[0-9]+$/,
													regexText : '该输入框只能输入数字0-9',
													maskRe : /^[0-9]+$/,
													width : 150,
													id : 'settleAcct',
													name : 'tblMchtSettleInfTmpTmp.settleAcct'
												} ]
											},
											{
												columnWidth : .50,
												layout : 'form',
												hidden:true,
												items : [ {
													readOnly : true,
													// xtype: 'numberfield',
													xtype : 'textfield',
													fieldLabel : '账户号(确认)*',
													maxLength : 40,
													value:0,
													allowBlank : true,
													vtype : 'isOverMax',
													regex : /^[0-9]+$/,
													regexText : '该输入框只能输入数字0-9',
													maskRe : /^[0-9]+$/,
													width : 150,
													id : 'settleAcctConfirm',
													// name:'tblMchtSettleInfTmpTmp.settleAcctConfirm',
													listeners : {
														'change' : function() {/*
															if (mchntForm
																	.findById(
																			'settleAcct')
																	.getValue() != this
																	.getValue()) {
																showErrorMsg(
																		"两次输入商户账户账号不一致，请确认！",
																		mchntForm);
																this
																		.setValue("");
															}
														*/}
													}
												} ]
											},{
												columnWidth : .3,
												layout : 'form',
												// hidden:true,
												items : [ {
													readOnly : true,
													xtype : 'textfield',
													fieldLabel : '账户开户行号',
													width : 150,
													regex : /^[0-9]{12}$/,
													regexText : '请输入12位数字0-9',
													// maskRe: /^[0-9]{12}$/,
													allowBlank : true,
													id : 'openStlno',
													name : 'tblMchtSettleInfTmpTmp.openStlno',
													listeners : {
														'change' : function() {
															var checkStr = Ext
																	.getCmp(
																			"openStlno")
																	.getValue();
															T20100
																	.checkCnapsId(
																			checkStr,
																			function(
																					ret) {
																				if (ret == 'F') {
																					showErrorMsg(
																							"无效开户行号，请重新确认！",
																							mchntForm);
																					Ext
																							.getCmp(
																									"openStlno")
																							.setValue(
																									"");
																				}
																			})
														}
													}
												} ]
											},
											{
												columnWidth : .7,
												layout : 'form',
												items : [ {
													readOnly : true,
													xtype : 'textnotnull',
													fieldLabel : '账户开户行名称*',
													id : 'settleBankNm',
													name : 'tblMchtSettleInfTmpTmp.settleBankNm',
													maxLength : 80,
													vtype : 'isOverMax',
													width:372
													//anchor : '90%'
												} ]
											},
											{
												columnWidth : .55,
												layout : 'form',
												hidden : true,
												items : [ {
													readOnly : true,
													xtype : 'basecomboselect',
													baseParams : 'CERTIFICATE',
													fieldLabel : '证件类型*',
													id : 'certifTypeId',
													hiddenName : 'certifType',
													allowBlank : true,
													width : 150,
													value : '01'
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												hidden : true,
												items : [ {
													readOnly : true,
													xtype : 'textnotnull',
													fieldLabel : '证件号码*',
													width : 150,
													allowBlank : true,
													id : 'certifNoId',
													name : 'certifNoId'
												} ]
											},
											
											{
												columnWidth: .3,
												layout: 'form',
												hidden:false,
												items: [{
												
													columnWidth: .3,
													layout: 'form',
													items: [{
													xtype : 'combo',
													readOnly:true,
													fieldLabel : '结算账户类型',
													id : 'clearType',
													hiddenName: 'clearTypeNm',
													allowBlank : false,
													blankText:'至少选择一项',
													width: 168,
													value:'0',
													store : new Ext.data.ArrayStore({
														fields : [ 'valueField', 'displayField' ],
														data : [['0', '对公账户' ], [ '1', '对私账户' ]]
													})
													}]
										}]
											},//

											{
															columnWidth: .3,
															layout: 'form',
															hidden:false,
															items: [{
															
																columnWidth: .3,
																layout: 'form',
																items: [{
																	readOnly:true,
																	xtype : 'combo',
																	fieldLabel : '结算类型',
																	id : 'mchtFunctionId',
																	hiddenName : 'mchtFunctionType',
																	allowBlank : true,
																	blankText:'至少选择一项',
																	width: 168,
																	store : new Ext.data.ArrayStore({
																		fields : [ 'valueField',
																				'displayField' ],
																		data : [ [ '0', 'T+N' ],
																				[ '1', '周期结算' ] ]
																	}),
																	listeners: {
																		'select': function(combo,record,number) {
																			if(number==0){
																				Ext.getCmp('mchtFunctionId0').show();
																				Ext.getCmp('mchtFunctionId1').hide();
																				Ext.getCmp('T_N').allowBlank=false;
																				Ext.getCmp('periodId').allowBlank=true;
																			}else if(number==1){
																				Ext.getCmp('mchtFunctionId0').hide();
																				Ext.getCmp('mchtFunctionId1').show();
																				Ext.getCmp('T_N').allowBlank=true;
																				Ext.getCmp('periodId').allowBlank=false;
																			}
																		},'change': function(combo,number,orignal) {
																			if(number==0){
																				Ext.getCmp('mchtFunctionId0').show();
																				Ext.getCmp('mchtFunctionId1').hide();
																				Ext.getCmp('T_N').allowBlank=false;
																				Ext.getCmp('periodId').allowBlank=true;
																			}else if(number==1){
																				Ext.getCmp('mchtFunctionId0').hide();
																				Ext.getCmp('mchtFunctionId1').show();
																				Ext.getCmp('T_N').allowBlank=true;
																				Ext.getCmp('periodId').allowBlank=false;
																			}
																		}
																	}
																}]
													}]
														
														},{
															columnWidth: .30,
															layout: 'form',
															id : 'mchtFunctionId0',
															hidden:true,
															labelWidth:60,
															labelPad:10,
															items: [{
																xtype: 'textfield',
																fieldLabel: 'T+N',
																readOnly:true,
																width:80,
//																anchor: '90%',
																allowBlank : true,
																maxLength: 3,
																vtype: 'isNumber',
																regex: /^(([0-9]{1})|([1-9]{1}\d{0,2}))$/,
																regexText: '请输入0-999的数字',
																id: 'T_N',
																name:'T_N'
															}]
														
														},{
															columnWidth: .30,
															hidden:true,
															layout: 'form',
															id : 'mchtFunctionId1',
															labelWidth:60,
															labelPad:10,
															items: [{
																xtype : 'combo',
																readOnly:true,
																fieldLabel : '周期结算',
																id : 'periodId',
																editable:false,
																hiddenName : 'period',
																allowBlank : true,
																blankText:'至少选择一项',
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
												
														}]
								},
								// ******************第二步合同有效期******************
//								{
//									id : 'contdateInfo',
//									// width: 1000,
//									collapsible : true,
//									xtype : 'fieldset',
//									title : '合同有效期',
//									layout : 'column',
//									hidden : true,
//									defaults : {
//										bodyStyle : 'padding-left: 20px'
//									},
//									items : [
//											{
//												columnWidth : .33,
//												layout : 'form',
//												items : [ {
//													readOnly : true,
//													xtype : 'datefield',
//													id : 'contstartDate',
//													editable : false,
//													width : 150,
//													name : 'tblMchtBaseInfTmpTmp.contstartDate',
//													vtype : 'daterange',
//													//fieldLabel : '合同起始日期*',
//													endDateField : 'contendDate',
//													blankText : '请选择起始日期',
//													allowBlank : true,
//													hidden:true
//												} ]
//											},
//											{
//												columnWidth : .33,
//												layout : 'form',
//												items : [ {
//													readOnly : true,
//													xtype : 'datefield',
//													id : 'contendDate',
//													editable : false,
//													width : 150,
//													name : 'tblMchtBaseInfTmpTmp.contendDate',
//													vtype : 'daterange',
//													//fieldLabel : '合同终止日期*',
//													startDateField : 'contstartDate',
//													blankText : '请选择终止日期',
//													allowBlank : true,
//													hidden:true
//												} ]
//											} ]
//								},
								{
									//************************费率信息************************///
									id: 'fee',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '费率信息',
									layout: 'column',
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 20px'
									},
									items: [{
										columnWidth: 1,
										layout: 'form',
										items: [{
										xtype : 'combo',
										readOnly:true,
										fieldLabel : '手续费方式',
										id : 'feeId',
										hiddenName: 'tblMchtBaseInfTmpTmp.poundage',//新加字段对应数据库mcht_bak2
										width: 172,
										store : new Ext.data.ArrayStore({
											fields : [ 'valueField', 'displayField' ],
											data : [['0', '按比例' ], [ '1', '比例+封顶' ], [ '2', '固定金额' ]]
										}),
										listeners: {
											'select': function() {
												var type = this.getValue().trim();
												if (type=="0") {
									        		Ext.getCmp('jFee').enable();
									        		Ext.getCmp('jFee').isValid();
									        		Ext.getCmp('jMaxFee').disable();
									        		Ext.getCmp('jMaxFee').setValue('');
									        		Ext.getCmp('jMaxFee').isValid();
									        		
									        		Ext.getCmp('dFee').enable();
									        		Ext.getCmp('dFee').isValid();
									        		Ext.getCmp('dMaxFee').disable();
									        		Ext.getCmp('dMaxFee').setValue('');
									        		Ext.getCmp('dMaxFee').isValid();
												}else if ("2"==type) {
													Ext.getCmp('jFee').setValue('');
									        		Ext.getCmp('jFee').disable();
									        		Ext.getCmp('jFee').isValid();
									        		Ext.getCmp('jMaxFee').enable();
									        		Ext.getCmp('jMaxFee').isValid();
									        		
									        		Ext.getCmp('dFee').setValue('');
									        		Ext.getCmp('dFee').disable();
									        		Ext.getCmp('dFee').isValid();
									        		Ext.getCmp('dMaxFee').enable();
									        		Ext.getCmp('dMaxFee').isValid();
												}else {
									        		Ext.getCmp('jFee').enable();
									        		Ext.getCmp('jFee').isValid();
									        		Ext.getCmp('jMaxFee').enable();
									        		Ext.getCmp('jMaxFee').isValid();
									        		
									        		Ext.getCmp('dFee').enable();
									        		Ext.getCmp('dFee').isValid();
									        		Ext.getCmp('dMaxFee').enable();
									        		Ext.getCmp('dMaxFee').isValid();
												}
											},
											'change': function() {}
										}
									}]
									
									},{
										columnWidth: .06,
										layout: 'form',
										html:'借记卡:',
										//width:40
									},{
										columnWidth: .10,
										layout: 'form',
										id:'feeHide1',
										labelWidth:20,
										labelPad:20,
										items: [{
											xtype: 'textfield',
											fieldLabel: '',
											maxLength: 40,
											vtype: 'isOverMax',
											regex: /^([+-]?)\d*\.?\d+$/,
											regexText:'请输入数字',
											readOnly:true,
											id: 'jFee',
											name: 'jFee',
											width:50
											//anchor: '99%'
										}]
									
									},{
										columnWidth: .03,
										layout: 'form',
										html:'<font height=20 >%</font>'
									},{
										columnWidth: .07,
										layout: 'form',
										labelWidth:1,
										id:'feeHide2',
										labelPad:2,
										items: [{
											xtype: 'textfield',
											fieldLabel: '',
											maxLength: 40,
											vtype: 'isOverMax',
											regex: /^([+-]?)\d*\.?\d+$/,
											regexText:'请输入数字',
											readOnly:true,
											id: 'jMaxFee',
											name: 'jMaxFee',
											width:50
											//anchor: '99%'
										}]
									
									},{
										columnWidth: .03,
										id:'feeHide5',
										layout: 'form',
										html:'元'
									},{
										columnWidth: .02,
										layout: 'form',
										html:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'
									
									},{
										columnWidth: .06,
										layout: 'form',
										html:'贷记卡:'
									},{
										columnWidth: .07,
										layout: 'form',
										id:'feeHide3',
										labelWidth:2,
										labelPad:2,
										items: [{
											xtype: 'textfield',
											fieldLabel: '',
											readOnly:true,
											maxLength: 40,
											vtype: 'isOverMax',
											regex: /^([+-]?)\d*\.?\d+$/,
											regexText:'请输入数字',
											id: 'dFee',
											name: 'dFee',
											width:50
//											anchor: '99%'
										}]
									
									},{

										columnWidth: .03,
										layout: 'form',
										html:'%'
									
									},{
										columnWidth: .07,
										layout: 'form',
										labelWidth:2,
										id:'feeHide4',
										labelPad:2,
										items: [{
											xtype: 'textfield',
											readOnly:true,
											fieldLabel: '',
											maxLength: 40,
											vtype: 'isOverMax',
											regex: /^([+-]?)\d*\.?\d+$/,
											regexText:'请输入数字',
											id: 'dMaxFee',
											name: 'dMaxFee',
											width:50
//											anchor: '99%'
										}]
									
									},{

										columnWidth: .03,
										layout: 'form',
										id:'feeHide6',
										html:'元'
									
									}]
									
								},{
									id: 'profitInfo',
//									width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '分润信息',
									layout: 'column',
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 20px'
									},
									items: [
											{
												columnWidth: 1,
												layout: 'form',
												items: [{
												xtype : 'combo',
												fieldLabel : '分润方式',
												id : 'profitType_id',
												hiddenName: 'profitType',
												readOnly:true,
												allowBlank : false,
												editable:false,
												width: 172,
												store : new Ext.data.ArrayStore({
													fields : [ 'valueField', 'displayField' ],
													data : [['0', '按比例' ], [ '1', '比例+封顶' ], [ '2', '固定金额' ]]
												}),
												listeners: {
													'select': function() {
														
//														var profitType = Ext.getCmp('profitType_id').getValue();
//														if (profitType=="0") {
//															Ext.getCmp('capId').setValue('');
//											        		Ext.getCmp('capId').disable();
//											        		Ext.getCmp('rateId_id').enable();
//														}else if ("2"==profitType) {
//											        		Ext.getCmp('capId').enable();
//											        		Ext.getCmp('rateId_id').setValue('');
//											        		Ext.getCmp('rateId_id').disable();
//											        		
//														}else {
//											        		Ext.getCmp('capId').enable();
//											        		Ext.getCmp('rateId_id').enable();
//														}
													},
													'change': function() {/*
														var profitType = Ext.getCmp('profitType_id').getValue();
														if (profitType=="0") {
															Ext.getCmp('capId').setValue('');
											        		Ext.getCmp('capId').disable();
											        		Ext.getCmp('rateId_id').enable();
														}else if ("2"==profitType) {
											        		Ext.getCmp('capId').enable();
											        		Ext.getCmp('rateId_id').setValue('');
											        		Ext.getCmp('rateId_id').disable();
														}else {
											        		Ext.getCmp('capId').enable();
											        		Ext.getCmp('rateId_id').enable();
														}
													*/}
												}
											}]
											},{
											columnWidth: .3,
											layout: 'form',
											id:'profitTypeHide1',
											items: [{
												xtype: 'basecomboselect',
												id: 'rateId_id',
												hiddenName : 'rateId',
												store: rateIdStore1,
												allowBlank : true,
												readOnly:true,
												fieldLabel : '比例',
												valueField: 'valueField',
												displayField: 'displayField',
												width : 172,
												maxLength : 60
											}]
											},{
											columnWidth: .32,
											id:'profitTypeHide2',
											layout: 'form',
											items: [{
												xtype: 'basecomboselect',
												id: 'capId',
												hiddenName : 'cap_id',
												store: rateIdStore2,
												readOnly:true,
												allowBlank : true,
												fieldLabel : '封顶',
												valueField: 'valueField',
												displayField: 'displayField',
												width : 172,
												maxLength : 50
											}]
											}
									        ]
								
								
								
								},{
									id: 'statement_integral_Info',
//									width: 1035,
									hidden:true,
									collapsible: true,
									xtype: 'fieldset',
									title: '积分对账单信息',
									layout: 'column',
									defaults: {
										bodyStyle: 'padding-left: 20px'
									},
									items: [{
										columnWidth : .3,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否需要对账单',
											width : 172,
											editable:false,
											blanText:'请选择是否需要对账单',
											msgTarget:"under",
											id : 'bankStatement',
											readOnly:true,
											blankText : '至少选择一项',
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
														     Ext.getCmp('bankStatementReason').allowBlank = false;
														    	 }
														     if(checked=='0'){
														    	 Ext.getCmp("bankStatementReasonId").hide();
														    	 Ext.getCmp('bankStatementReason').allowBlank = true;
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
										columnWidth : .33,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否积分',
											editable:false,
											width : 172,
											blanText:'请选择是否积分',
											readOnly:true,
											msgTarget:"under",
											hiddenName: 'tblMchtSettleInfTmpTmp.integral',
											id : 'integral',
											blankText : '至少选择一项',
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
														 }else if(checked=='1'){
												    		 Ext.getCmp("integralReasonId").hide();
												    		 Ext.getCmp('integralReason').allowBlank = true;
												    	 }     },
												 'change': function() {
														var checked=Ext.getCmp('integral').getValue();
															if(checked=='0'){
															 Ext.getCmp("integralReasonId").show();
															 Ext.getCmp('integralReason').allowBlank = false;
															 }else if(checked=='1'){
													    		 Ext.getCmp("integralReasonId").hide();
													    		 Ext.getCmp('integralReason').allowBlank = true;
													    	 }     
												 }
														}
													}]
									},{ 
										columnWidth : .30,
										layout : 'form',
										labelWidth:60,
										labelPad:10,
										items : [ {
											xtype:'checkboxgroup',
											name:'tblMchtSettleInfTmpTmp.emergency',   
											id: 'emergency', 
											disabled:true,
											fieldLabel: '是否紧急',
											items: [  
											        { boxLabel: '是', name: 'tblMchtSettleInfTmpTmp.emergency', inputValue: '1' }] 
										}]
									},{				
										columnWidth: 1,
										layout: 'form',
										id: 'bankStatementReasonId',
										hidden:true,
										style:'display:block',
										items: [{
											xtype: 'textfield',
											fieldLabel: '需要对账单理由',
											width:700,
//											anchor: '90%',
											readOnly:true,
											maxLength: 200,
											vtype: 'isOverMax',
											id: 'bankStatementReason',
											name:'tblMchtSettleInfTmpTmp.bankStatementReason'
										}]
									},{				
										columnWidth: 1,
										layout: 'form',
										id: 'integralReasonId',
										hidden:true,
										style:'display:block',
										items: [{
											xtype: 'textfield',
											fieldLabel: '需要积分理由',
											readOnly:true,
											width:700,
//											anchor: '90%',
											maxLength: 200,
											vtype: 'isOverMax',
											id: 'integralReason',      
											name:'tblMchtSettleInfTmpTmp.integralReason'
										}]
									}]
								},{

									id: 'imageInfo10',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '银行卡或开户许可证影像',
									layout: 'column',
//									autoScroll: true,
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview10,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view10',
											handler:function() {
												storeImg10.reload({
													params: {
														start: 0,
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload10'
													}
												});
											}
										}]
								
								},{

									id: 'imageInfo7',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '委托入款授权证明影像',
									layout: 'column',
//									autoScroll: true,
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview7,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view7',
											handler:function() {
												storeImg7.reload({
													params: {
														start: 0,
														imagesId: mchntId,
														mcht:mchntId,
														upload:'upload7'
													}
												});
											}
										}]
								
								},
								// ******************第二步证书影像信息******************
								{
									id: 'imageInfo5',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '协议影像',
									layout: 'column',
//									autoScroll: true,
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview5,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view5',
											handler:function() {
												storeImg5.reload({
													params: {
														start: 0,
														imagesId: imagesId,
														mcht : mchntId,
														upload:'upload5'
													}
												});
											}
										}]
								},{

									id: 'imageInfo8',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '房屋租赁协议影像',
									layout: 'column',
//									autoScroll: true,
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview8,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view8',
											handler:function() {
												storeImg8.reload({
													params: {
														start: 0,
														imagesId: mchntId,
														mcht:mchntId,
														upload:'upload8'
													}
												});
											}
										}]
								
								},{
									id: 'imageInfo9',
									//width: 1035,
									collapsible: true,
									xtype: 'fieldset',
									title: '水电煤账单影像',
									layout: 'column',
//									autoScroll: true,
									hidden:true,
									defaults: {
										bodyStyle: 'padding-left: 10px'
									},
									items : dataview9,
									tbar: [{
						    				xtype: 'button',
											text: '刷新',
											width: '80',
											id: 'view9',
											handler:function() {
												storeImg9.reload({
													params: {
														start: 0,
														imagesId: mchntId,
														mcht:mchntId,
														upload:'upload9'
													}
												});
											}
										}]
								
								
								},
								{
									id : 'imageInfo',
									// width: 1000,
									collapsible : true,
									xtype : 'fieldset',
									title : '其他影像',
									layout : 'column',
									// autoScroll: true,
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : dataview,
									tbar : [ {
										xtype : 'button',
										text : '刷新',
										width : '80',
										id : 'view',
										handler : function() {
											storeImg.reload({
												params : {
													start : 0,
													imagesId : imagesId,
													mcht : mchntId
												}
											});
										}
									} ]
								},
								// ******************第二步按钮信息******************
								{
									xtype : 'fieldset',
									id : 'next2',
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 100px'
									},
									// width: 1000,
									buttonAlign : 'center',
									buttons : nextArr2

								},
								// ******************第三步商户组别，MCC信息******************
								
								{
									id : 'mccInfo',
									// width: 1000,
									collapsible : true,
									xtype : 'fieldset',
									title : '商户MCC',
									layout : 'column',
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
											{
												columnWidth : .66,
												layout : 'form',
												xtype : 'panel',
												items : [ {} ]
											},{
												columnWidth : .33,
												xtype : 'panel',
												layout : 'form',
												items : [ {
													xtype : 'basecomboselect',
													baseParams : 'MCHNT_GRP_ALL',
													fieldLabel : '商户组别*',
													allowBlank : false,
													blanText:'请选择商户组别',
//													msgTarget:"under",
													hiddenName : 'tblMchtBaseInfTmpTmp.mchtGrp',
													anchor : '90%',
													id : 'mchtGrpId',
													listeners : {
														'select' : function() {
															mchntMccStore
																	.removeAll();
															var mchtGrpId = Ext
																	.getCmp(
																			'mchtGrpId')
																	.getValue();
															Ext.getCmp('idmcc')
																	.setValue(
																			'');
															Ext
																	.getDom(
																			Ext
																					.getDoc())
																	.getElementById(
																			'idmcc').value = '';
															SelectOptionsDWR
																	.getComboDataWithParameter(
																			'MCHNT_TP',
																			mchntForm
																					.getForm()
																					.findField(
																							'mchtGrpId')
																					.getValue(),
																			function(
																					ret) {
																				mchntMccStore
																						.loadData(Ext
																								.decode(ret));
																			});

														
														},
														'change' : function() {
															mchntMccStore
																	.removeAll();
															var mchtGrpId = Ext
																	.getCmp(
																			'mchtGrpId')
																	.getValue();
															Ext.getCmp('idmcc')
																	.setValue(
																			'');
															Ext
																	.getDom(
																			Ext
																					.getDoc())
																	.getElementById(
																			'idmcc').value = '';
															SelectOptionsDWR
																	.getComboDataWithParameter(
																			'MCHNT_TP',
																			mchntForm
																					.getForm()
																					.findField(
																							'mchtGrpId')
																					.getValue(),
																			function(
																					ret) {
																				mchntMccStore
																						.loadData(Ext
																								.decode(ret));
																			});
														}
													}
												} ]
											},
											{
												columnWidth : .66,
												layout : 'form',
												items : [ {
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
//													msgTarget:"under",
													id : 'idmcc',
													// readOnly: true,
													hiddenName : 'tblMchtBaseInfTmpTmp.mcc',
													anchor : '90%',
													listeners : {
														'select' : function() {
															gridStore.load();
															Ext.getCmp(
																	'discCode')
																	.setValue(
																			'');
														}
													}
												} ]
											},
											{
												columnWidth : .33,
												layout : 'form',
												items : [ {
													xtype : 'basecomboselect',
													baseParams : 'RISL_LVL',
													fieldLabel : '商户风险级别*',
													id : 'rislLvlId',
													hiddenName : 'tblMchtBaseInfTmpTmp.rislLvl',
													allowBlank : false,
													blanText: '请选择商户风险级别',
//													msgTarget:"under",
													editable : false,
													anchor : '90%'
												} ]
											},{

												columnWidth : .33,
												layout : 'form',
												items : [ {
													xtype : 'radiogroup',
													fieldLabel : '是否县乡商户*',
													width : 150,
													allowBlank : false,
													blanText:'请选择是否是县乡商户',
													msgTarget:'qtip',
													id : 'country',
													blankText : '至少选择一项',
													items : [
															{
																boxLabel : '是',
																inputValue : '0',
																name : 'tblMchtSettleInfTmpTmp.country'
															},
															{
																boxLabel : '否',
																inputValue : '1',
																name : 'tblMchtSettleInfTmpTmp.country',
																checked:true
															} ]
												} ]
//											
											},{


												columnWidth : .33,
												layout : 'form',
												items : [ {
													xtype : 'radiogroup',
													fieldLabel : '是否完全合规*',
													width : 150,
													allowBlank : false,
													blanText:'请选择是否完全合规',
													msgTarget:'qtip',
													id : 'compliance',
													blankText : '至少选择一项',
													items : [
															{
																boxLabel : '是',
																inputValue : '0',
																name : 'tblMchtSettleInfTmpTmp.compliance'
															},
															{
																boxLabel : '否',
																inputValue : '1',
																name : 'tblMchtSettleInfTmpTmp.compliance',
																checked:true
															} ]
												} ]
//											
											
											},
//											{
//												columnWidth : .33,
//												layout : 'form',
//												items : [ {
//													xtype : 'radiogroup',
//													fieldLabel : '是否标准入网*',
//													width : 200,
//													allowBlank : false,
//													blanText:'请选择是否标准入网',
//													msgTarget:"under",
//													id : 'tccId',
//													blankText : '至少选择一项',
//													items : [
//															{
//																boxLabel : '是',
//																inputValue : '0',
//																name : 'tblMchtBaseInfTmpTmp.tcc'
//															},
//															{
//																boxLabel : '否',
//																inputValue : '1',
//																name : 'tblMchtBaseInfTmpTmp.tcc'
//															} ]
//												} ]
//											},
											{/*
												columnWidth : .33,
												layout : 'form',
												hidden:true,
												items : [ {
													xtype : 'radiogroup',
													fieldLabel : '业务类型*',
													width : 200,
													allowBlank : false,
													id : 'mchtFunctionId',
													blankText : '必须选择一项',
													
													items : [
															{
																boxLabel : 'T+0业务',
																inputValue : '0',
																name : 'tblMchtBaseInfTmpTmp.mchtFunction'
															},
															{
																boxLabel : 'T+1业务',
																inputValue : '1',
																checked:true,
																name : 'tblMchtBaseInfTmpTmp.mchtFunction'
															} ]
												} ]
											*/} ]
								},
								{

									collapsible : true,
									xtype : 'fieldset',
									id : 'limitDateInfo',
									title : '漏缺资料提醒',
									layout : 'column',
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
											{
												columnWidth : .33,
												layout : 'form',
												items : [ {
													xtype : 'datefield',
													editable : false,
													width : 150,
													name : 'limitdate',
													fieldLabel : '到期日期',
													maxValue : twoMonday,
													listeners : {
														'change' : function(th,
																newValue,
																oldValue) {
															if (null != newValue
																	&& newValue > preDate) {
																Ext.Msg
																		.alert(
																				'系统提示',
																				'到期日期最大设置为两个月以内');
																th.setValue('');
																return;
															}
														}
													}
												} ]
											}, {
												columnWidth : .66,
												layout : 'form',
												items : [ {
													xtype : 'textarea',
													editable : false,
													width : 523,
													margin : '0 0 0 2',
													name : 'reserve',
													maxLength : 80,
													fieldLabel : '漏缺备注'

												} ]
											} ]
								},
								// ******************第三步商户费率信息******************
								{
									xtype : 'tabpanel',
									id : 'feeInfo',
									frame : true,
									plain : false,
									activeTab : 0,
									height : 350,
									hidden : true,
									deferredRender : false,
									enableTabScroll : true,
    	items:[{
										// width: 1000,
										height : 400,
										title : '商户费率',
										// autoScroll: true,
										// collapsible: true,
										frame : true,
										layout : 'border',
										items : [
												{
													region : 'north',
													height : 50,
													layout : 'column',
													items : [
															{
																xtype : 'panel',
																layout : 'form',
																labelWidth : 70,
																columnWidth : .2,
																items : [ {
																	xtype : 'textfield',
																	fieldLabel : '计费代码*',
																	id : 'discCode',
																	name : 'discCode',
																	readOnly : true
																} ]
															},
															{
																xtype : 'panel',
																columnWidth : .1,
																layout : 'form',
																items : [ {
																	xtype : 'button',
																	iconCls : 'recover',
																	text : '重置',
																	id : 'resetbu',
																	width : 60,
																	handler : function() {
																		Ext
																				.getCmp(
																						'discCode')
																				.reset();
																	}
																} ]
															},
															{
																xtype : 'panel',
																columnWidth : .15,
																layout : 'form',
																items : [ {
																	xtype : 'button',
																	iconCls : 'accept',
																	text : '设为该商户计费算法',
																	id : 'setup',
																	width : 60,
																	disabled : true,
																	handler : function() {
																		Ext
																				.getCmp(
																						'discCode')
																				.setValue(
																						gridPanel
																								.getSelectionModel()
																								.getSelected().data.discCd);
																	}
																} ]
															},
															{
																xtype : 'panel',
																columnWidth : .15,
																layout : 'form',
																items : [ {
																	xtype : 'button',
																	iconCls : 'detail',
																	text : '计费算法配置说明',
																	id : 'detailbu',
																	width : 60,
																	handler : function() {
																		Ext.MessageBox
																				.show({
																					msg : '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>'
																							+ '<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>'
																							+ '<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>'
																							+ '<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>'
																							+ '<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。',
																					title : '计费算法配置说明',
																					animEl : Ext
																							.get(mchntForm
																									.getEl()),
																					buttons : Ext.MessageBox.OK,
																					modal : true,
																					width : 650
																				});
																	}
																} ]
															},
															{
																xtype : 'panel',
																layout : 'form',
																labelWidth : 100,
																columnWidth : .4,
																items : [ {
																	xtype : 'displayfield',
																	fieldLabel : '费率参考信息',
																	id : 'reserved',
																	name : 'tblMchtSettleInfTmpTmp.reserved',
																	anchor : '90%'
																} ]
															} ]
												}, {
													region : 'center',
													items : [ gridPanel ]
												}, {
													region : 'east',
													width : 600,
													items : [ detailGrid ]
												} ]
									} ]
								},{
									id:'next4',
									xtype: 'tabpanel',
									activeTab : 0,
									hidden:true,
									height : 300,
//				                	region: 'center',
				                	items:[termGridPanel]
							},
								// ******************第三步按钮信息******************
								{
									xtype : 'fieldset',
									id : 'next3',
									hidden : true,
									defaults : {
										bodyStyle : 'padding-left: 100px'
									},
									// width: 1000,
									buttonAlign : 'center',
									buttons : nextArr3
								}
								// ******************第4步按钮信息******************
								/*
								 * { xtype: 'fieldset', id: 'next4',
								 * hidden:true, defaults: { bodyStyle:
								 * 'padding-left: 100px' }, // width: 1000,
								 * buttonAlign: 'center', buttons: nextArr4 }
								 */
								// **************终端列表************************************************
								 ]
					});
			
			gridStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
					start : 0,
					discCd : Ext.getCmp('serdiscCd').getValue(),
					discNm : Ext.getCmp('serdiscNm').getValue(),
					mccCode : Ext.getCmp('idmcc').getValue()
				});
			});

			baseStore.load({
				params : {
					mchntId : mchntId
				},
				callback : function(records, options, success) {
					if (success) {
						
						
						

						
					
						var type = baseStore.getAt(0).json.poundage.trim();
						if (type=="0") {
			        		Ext.getCmp('feeHide1').show();
			        		Ext.getCmp('feeHide2').hide();
			        		Ext.getCmp('feeHide3').show();
			        		Ext.getCmp('feeHide4').hide();
			        		Ext.getCmp('feeHide5').hide();
			        		Ext.getCmp('feeHide6').hide();
						}else if ("1"==type) {
			        		Ext.getCmp('feeHide1').show();
			        		Ext.getCmp('feeHide2').show();
			        		Ext.getCmp('feeHide3').show();
			        		Ext.getCmp('feeHide4').show();
						}else {
			        		Ext.getCmp('feeHide1').hide();
			        		Ext.getCmp('feeHide2').show();
			        		Ext.getCmp('feeHide3').hide();
			        		Ext.getCmp('feeHide4').show();
						}
					
						//设置分润信息的值
						var profit=baseStore.getAt(0).json.speSettleTp;
						if(profit==''){
							profit='     ';
						}
						Ext.getCmp('profitType_id').setValue(profit.substr(0,1));
						if(profit.substr(0,1)=='0'){
							Ext.getCmp('profitTypeHide1').show();
			        		Ext.getCmp('profitTypeHide2').hide();
							Ext.getCmp('rateId_id').setValue(profit.substr(1,2));
						}else if(profit.substr(0,1)=='2'){
							Ext.getCmp('capId').setValue(profit.substr(3,2));
						}else if(profit.substr(0,1)=='1'){
							Ext.getCmp('profitTypeHide1').show();
			        		Ext.getCmp('profitTypeHide2').show();
							Ext.getCmp('rateId_id').setValue(profit.substr(1,2));
							Ext.getCmp('capId').setValue(profit.substr(3,2));
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
						Ext.getCmp('feeId').setValue(baseStore.getAt(0).json.poundage);
						var settleAcct=baseStore.getAt(0).json.settleAcct;
						mchntForm.getForm().loadRecord(baseStore.getAt(0));
						mchntForm.getForm().clearInvalid();
						var clearTypeTmp = baseStore.getAt(0).data.settleAcct.substring(0,1);
						Ext.getCmp('clearType').setValue(clearTypeTmp);//
						var etpsAttrId=baseStore.getAt(0).data.etpsAttr;
						if(etpsAttrId=='01'|etpsAttrId=='02'){
							
							Ext.getCmp('imageInfo1').show();
							Ext.getCmp('imageInfo2').show();
							Ext.getCmp('imageInfo6').show();
						}else if(etpsAttrId=='03'){
							Ext.getCmp('imageInfo1').hide();
							Ext.getCmp('imageInfo2').hide();
							Ext.getCmp('imageInfo6').hide();
							
							Ext.getCmp('accountHide1').hide();
							Ext.getCmp('accountHide2').hide();
							Ext.getCmp('accountHide3').hide();
							Ext.getCmp('accountHide4').hide();
							Ext.getCmp('accountHide5').hide();
						}
						Ext.getCmp('idmchtGroupFlag').setValue(
								baseStore.getAt(0).data.mchtGroupFlag);
						if (Ext.getCmp("idmchtGroupFlag").getValue() == '3') {
							Ext.getCmp('idmchtGroupId').enable();
						}
						Ext.getCmp('idmchtGroupId').setValue(
								baseStore.getAt(0).data.mchtGroupId);
						Ext.getCmp('settleAcctConfirm').setValue(
								settleAcct.substring(1,settleAcct.lenght));
						Ext.getCmp('settleAcct').setValue(
								settleAcct.substring(1,settleAcct.lenght));
						Ext.getCmp('etpsAttrId').setValue(
								baseStore.getAt(0).data.etpsAttr);
						Ext.getCmp('idbankNo').setValue(
								baseStore.getAt(0).data.bankNo);
						Ext.getCmp('idartifCertifTp').setValue(
								baseStore.getAt(0).data.artifCertifTp);
						//Ext.getCmp('areaNoId').setValue(baseStore.getAt(0).data.areaNo);
					
						//Ext.getCmp('validatyDate').setValue((baseStore.getAt(0).data.validatyDate).replaceAll('-',''));
						//Ext.getCmp('validatyDate').setReadOnly(true);
//						alert(baseStore.getAt(0).data.status);
						var status=baseStore.getAt(0).data.status;//商户tmp表中的状态
						var sta=baseStore.getAt(0).data.mchtStatus;//商户tmptmp表中的状态
						
						if(status==='2'|status==='A'){
							Ext.getCmp('mchtCnAbbr').setValue((baseStore.getAt(0).data.mchtCnAbbr1).trim());
							Ext.getCmp('mchtGrpId').setValue((baseStore.getAt(0).data.mchtGrp1).trim());
							Ext.getCmp('idmcc').setValue((baseStore.getAt(0).data.mcc1).trim());
							Ext.getCmp('rislLvlId').setValue((baseStore.getAt(0).data.rislLvl1).trim());
							Ext.getCmp('mchtFunctionId').setValue((baseStore.getAt(0).data.mchtFunction1).trim());
							Ext.getCmp('country').setValue((baseStore.getAt(0).data.country).trim());
							Ext.getCmp('compliance').setValue((baseStore.getAt(0).data.compliance).trim());
						}
						if(sta==='3'){
							Ext.getCmp('editMenu').hide();
							Ext.getCmp('mchtCnAbbr').setValue((baseStore.getAt(0).data.mchtCnAbbr1).trim());
							Ext.getCmp('mchtCnAbbr').setReadOnly(true);
							Ext.getCmp('mchtGrpId').setValue((baseStore.getAt(0).data.mchtGrp1).trim());
							Ext.getCmp('mchtGrpId').setReadOnly(true);
							Ext.getCmp('idmcc').setValue((baseStore.getAt(0).data.mcc1).trim());
							Ext.getCmp('idmcc').setReadOnly(true);
							Ext.getCmp('rislLvlId').setValue((baseStore.getAt(0).data.rislLvl1).trim());
							Ext.getCmp('rislLvlId').setReadOnly(true);
							Ext.getCmp('mchtFunctionId').setValue((baseStore.getAt(0).data.mchtFunction1).trim());
							Ext.getCmp('mchtFunctionId').setReadOnly(true);
							Ext.getCmp('country').setValue((baseStore.getAt(0).data.country).trim());
							Ext.getCmp('country').disable() ;
							Ext.getCmp('compliance').setValue((baseStore.getAt(0).data.compliance).trim());
							Ext.getCmp('compliance').disable() ;
						}
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
								 
								//设置结算类型
									var mchtFunction =baseStore.getAt(0).data.mchtFunction;
									if(mchtFunction!=null&&mchtFunction.trim()!=''){
										var mchtFunctionType=mchtFunction.substr(0,1);
										if(mchtFunctionType==0){
											Ext.getCmp('mchtFunctionId0').show();
											Ext.getCmp('mchtFunctionId1').hide();
//											Ext.getCmp('T_N').allowBlank=false;
//											Ext.getCmp('periodId').allowBlank=true;
										}else if(mchtFunctionType==1){
											Ext.getCmp('mchtFunctionId0').hide();
											Ext.getCmp('mchtFunctionId1').show();
//											Ext.getCmp('T_N').allowBlank=true;
//											Ext.getCmp('periodId').allowBlank=false;
										}
										Ext.getCmp('mchtFunctionId').fireEvent('select',this,this.store,mchtFunctionType);//combo,record,number
										Ext.getCmp('mchtFunctionId').setValue(mchtFunctionType);//combo,record,number
										Ext.getCmp('periodId').setValue(mchtFunction.substr(1,1));
										Ext.getCmp('T_N').setValue(parseInt((mchtFunction.substr(2,3).trim()==''?'0':mchtFunction.substr(2,3).trim()),10));
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
						// 将数据库中保存的4位营业时间加载为带有：的4位时间格式（HH：MM）
						var OT = baseStore.getAt(0).data.openTime;
						var CT = baseStore.getAt(0).data.closeTime;
						if (null != OT & '' != OT) {
							Ext.getCmp('openTime').setValue(
									OT.substring(0, 2) + ':'
											+ OT.substring(2, 4));
						}
						if (null != CT & '' != CT) {
							Ext.getCmp('closeTime').setValue(
									CT.substring(0, 2) + ':'
											+ CT.substring(2, 4));
						}

						/*custom = new Ext.Resizable('showBigPic', {
							wrap : true,
							pinned : true,
							minWidth : 50,
							minHeight : 50,
							preserveRatio : true,
							dynamic : true,
							handles : 'all',
							draggable : true
						});
						customEl = custom.getEl();
						customEl.on('dblclick', function() {
							customEl.puff();
							rollTimes = 0;
						});
						customEl.hide(true);*/

						imagesId = baseStore.getAt(0).data.mchtNo;

						storeImg.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload'
							}
						});
						storeImg1.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload1'
							}
						});
						storeImg2.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload2'
							}
						});
						storeImg3.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload3'
							}
						});
						storeImg4.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload4'
							}
						});
						storeImg5.reload({
							params : {
								start : 0,
								imagesId : mchntId,
								mcht : mchntId,
								upload:'upload5'
							}
						});
						storeImg6.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload6'
							}
						});
						storeImg7.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload7'
							}
						});
						storeImg8.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload8'
							}
						});
						storeImg9.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload9'
							}
						});
						storeImg10.reload({
							params: {
								start: 0,
								imagesId: mchntId,
								mcht:mchntId,
								upload:'upload10'
							}
						});
						termStore.on('beforeload', function() {
							Ext.apply(this.baseParams, {
								start : 0,
								termId : Ext.getCmp('termNoQ').getValue(),
								termSta : Ext.getCmp('state').getValue(),
								startTime : topQueryPanel.getForm().findField('startTime')
										.getValue(),
								endTime : topQueryPanel.getForm().findField('endTime')
										.getValue(),
								mchnNo : baseStore.getAt(0).data.mchtNo,//Ext.getCmp('mchnNoQ').getValue(),
								termTp : Ext.getCmp('idtermtpsearch').getValue(),
								notEqsta:'7'
							});
						});
						termStore.load();
					
						// if(baseStore.getAt(0).data.feeRate!=0){
						// Ext.getCmp('discCode').setValue(baseStore.getAt(0).data.feeRate);
						// }

					} else {
						showErrorMsg("加载商户信息失败，请刷新数据后重试", mchntForm);
					}
				}
			});
			// 主界面
			new Ext.Viewport({
				layout : 'fit',
				items : [ mchntForm ]
			});
		});