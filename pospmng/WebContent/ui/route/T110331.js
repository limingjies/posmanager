Ext.onReady(function() {
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	// ==================================待进行映射维护商户信息页面====================================
	// 待进行映射维护商户信息数据集
	var routeRuleStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy(
				{
					url : 'gridPanelStoreAction.asp?storeId=getMchtDetailWithConditions'
				}),
		reader : new Ext.data.JsonReader(
			{
				root : 'data',
				totalProperty : 'totalCount'
			},
			[
				{name : 'mchtNo',mapping : 'mchtNo'},
				{name : 'mchtNm',mapping : 'mchtNm'},
				{name : 'addr',mapping : 'addr'},
				{name : 'mcc',mapping : 'mcc'},
				{name : 'settle',mapping : 'settle'},
				{name : 'bill',mapping : 'bill'},
				{name : 'integral',mapping : 'integral'},
				{name : 'bankNo',mapping : 'brhNo'},
				{name : 'bankNm',mapping : 'brhName'},
				{name : 'passTime',mapping : 'passTime'}
			]),
		autoLoad : true
	});
	// 待进行映射维护商户信息数据显示模型
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header : '商户号',dataIndex : 'mchtNo',align : 'center',width : 150,id : 'msc1',},
		{header : '商户名称',dataIndex : 'mchtNm',width : 150}, 
		{header : '商户地区',dataIndex : 'addr',id : 'srvId',align : 'center',width : 90},
		{header : 'MCC',dataIndex : 'mcc',align : 'center',width : 60},
		{header : '结算扣率',dataIndex : 'settle',width : 80,align : 'center'},
		{header : '是否对账单',dataIndex : 'bill',align : 'center',width : 80,renderer:yesOrNo},
		{header : '是否积分',dataIndex : 'integral',align : 'center',width : 80,renderer:yesOrNo},
		{header : '合作伙伴号',dataIndex : 'bankNo',width : 120,align : 'left'},
		{header : '合作伙伴名称',dataIndex : 'bankNm',width : 150,id : 'srvId'}
	]);
	//// 待进行映射维护商户信息查询条件
	var tbar1 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '本地商户：', {
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdRoute',
			hiddenName : 'queryMchtId',
			id : 'mchtNo',
			mode : 'local',
			triggerAction : 'all',
			editable : true,
			lazyRender : true,
			width : 250
		}, '-', '合作伙伴：', {
			xtype: 'dynamicCombo',
			triggerAction : 'all',
			mode : 'local',
			lazyRender : true,
			methodName: 'getPartner',
			editable: true,
			id : 'partnerNo',
			hiddenName : 'queryPartnerNo',
			width : 250
		} ]
	});
	var tbar5 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [ '-', '商户Mcc ：', {
			xtype: 'basecomboselect',
			baseParams: 'MCC',
			editable: true,
			lazyRender: true,
			id: 'mcc',
			hiddenName:'queryMchtMCC',
			width: 250
		},'-','渠道商户：',{
			xtype: 'dynamicCombo',
			methodName: 'getUpbrhMcht',
			hiddenName: 'queryMchtUp',
			id: 'qmchtNo',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 250
		}]
	});
	var tbar7 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [
		'-','商户地区：',{
			xtype: 'dynamicCombo',
			methodName: 'getAreaCode',
			editable: true,
			id: 'mchtAddr',
			hiddenName:'queryMchtArea',
			width: 140
		},'-','商&nbsp;户&nbsp;组：',{
			xtype: 'basecomboselect',
			baseParams: 'ROUTE_MCHNT_GRP_ALL',
			editable: true,
			id: 'mchtGroup',
			width: 180
		},'-','支付渠道：',{
			xtype: 'basecomboselect',
			baseParams: 'CHANNEL_ALL',
			hiddenName: 'queryChannel',
			editable: true,
			id:'payRoad',
			width: 140,
			listeners: {
				'select': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('payRoad').getValue();
					Ext.getCmp('business').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('business').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
						busiStore.loadData(Ext.decode(ret));
					});
				},
				'change': function() {
					busiStore.removeAll();
					var chlId = Ext.getCmp('payRoad').getValue();
					Ext.getCmp('business').setValue('');
					Ext.getDom(Ext.getDoc()).getElementById('business').value = '';
					SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
						});
					}
			}
		}]
	});
	var tbar8 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : [
		'-','&nbsp;业&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务&nbsp;：',{
  			xtype: 'basecomboselect',
  			store: busiStore,
  			displayField: 'displayField',
  			valueField: 'valueField',
  			editable: true,
  			id: 'business',
  			hiddenName: 'queryBusiness',
  			value:'',
  			width: 140
  		},'-','性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质： ',{
  			xtype: 'textfield',
  			editable: true,
  			id: 'proper',
  			hiddenName:'queryCharName',
  			width: 180
  		},{
  			xtype: 'radiogroup',
  			id: 'queryCharConGId',
  			items:[
  			    {
  			    	xtype:'radio',
  			    	boxLabel:'包含',
  			    	inputValue:'0',
  			    	checked:true,
  			    	id:'queryCharConAndId',
  			    	name:'queryChrCon',
  			    	value:'',
  			    	width:60
  			    }, {
  			    	xtype:'radio',
  			    	boxLabel:'不包含',
  			    	inputValue:'1',
  			    	id:'queryCharConNotId',
  			    	name:'queryChrCon',
  			    	value:'',
  			    	width:60
  			    }
  			       
  			],
  			//hiddenName: 'queryBusiness',
  			//value:'',
  			width: 125
  		}]
	});
	var tbar4 = new Ext.Toolbar({
		renderTo : Ext.grid.EditorGridPanel.tbar,
		items : []
	})
	//导入映射对话框
	var	dialog = new UploadDialog({
		uploadUrl : 'T110331Action_upload.asp',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '5 MB',
		fileTypes : '*.xls;*.xlsx',
		fileTypesDescription : 'Excel文件（大小不超过5M）',
		title: '上传商户映射关系文件',
		scope : this,
		onEsc: function() {
			this.hide();
		},
		completeMethod: function() {
			//oprGrid.getStore().reload();
		},
		onUploadSuccess : function(file, serverData){	
			var rspObj = Ext.util.JSON.decode(serverData);
			hideMask();
			var thiz = dialog;
			thiz.progressInfo.filesUploaded += 1;			
			if(rspObj.success){
				var record = thiz.fileList.getStore().getById(Ext.getDom('fileId_' + file.id).parentNode.id);
				record.set('fileState',file.filestatus);
				record.commit();
				showSuccessMsg(rspObj.msg,routeRuleGrid);
				if(rspObj.filename){
					window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + rspObj.filename;
				}
			} else {
				Ext.getCmp('UploadDialog').stopUpload();
				var record = thiz.fileList.getStore().getById(Ext.getDom('fileId_' + file.id).parentNode.id);
				record.set('fileState',-3);
				record.commit();
				thiz.progressInfo.filesTotal -= thiz.progressInfo.filesUploaded;
				this.settings.post_params['fileNum'] = thiz.progressInfo.filesTotal;
				showErrorMsg(rspObj.msg,routeRuleGrid);
			}
			thiz.updateProgressInfo();
			thiz.hide();
		}
	});
	var routeRuleGrid = new Ext.grid.GridPanel({
		width : 1600,
		title: '规则商户映射控制',
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		region : 'center',
		clicksToEdit : true,
		autoExpandColumn : 'srvId',
		store : routeRuleStore,
		sm : new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		cm : routeRuleColModel,
		forceValidation : true,
		viewConfig:{ enableTextSelection:true },
		loadMask : {
			msg : '正在加载规则商户映射记录列表......'
		},
		tbar : [{
				xtype : 'button',
				text : '查询',
				name : 'query',
				id : 'query',
				iconCls : 'query',
				width : 80,
				handler : function() {
					routeRuleStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '重置',
				name : 'reset',
				id : 'reset',
				iconCls : 'reset',
				width : 80,
				handler : function() {
					Ext.getCmp('mchtNo').setValue('');//
					Ext.getCmp('mchtAddr').setValue('');
					Ext.getCmp('mcc').setValue('');//
					Ext.getCmp('partnerNo').setValue('');//
					Ext.getCmp('partnerNm').setValue('');
					Ext.getCmp('payRoad').setValue('');//
					Ext.getCmp('business').setValue('');//
					Ext.getCmp('proper').setValue('');
					Ext.getCmp('qmchtNo').setValue('');//
					Ext.getCmp('mchtGroup').setValue('');//
					// routeRuleStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '批量修改',
				name : 'edit',
				id : 'manyEdit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					if (!routeRuleGrid.getSelectionModel().hasSelection()) {
						showMsg("请选择至少一条记录后再进行操作。", routeRuleGrid);
						return;
					}
					var mchtRecords = routeRuleGrid.getSelectionModel().getSelections();
					var store=routeRuleDtlStore;
					showMchntDetailS('',mchtRecords,store);
					for (var i = 0; i < mchtRecords.length; i++) {
						var mchtNo = mchtRecords[i].get('mchtNo');
					}
				}
			},'-',{
				xtype : 'button',
				text : '导入映射',
				id : 'btnImport',
				disabled : false,
				iconCls : 'add',
				width : 80,
				handler : function() {
					if(dialog.fileList.getStore().data.length > 0){
						dialog.clearList();
					}
					dialog.show();			
				}
			},'-',{
				xtype : 'button',
				text : '导出商户',
				id : 'btnExport',
				disabled : false,
				iconCls : 'download',
				width : 80,
				handler : function() {
					if(!Ext.getCmp('queryCharConAndId').getValue() && !Ext.getCmp('proper').getValue()){
						alert('性质条件不可为空！');
						return;
					}
					showMask("正在为您准备报表，请稍后。。。",routeRuleGrid);
					Ext.Ajax.request({
						url: 'T1103311Action.asp',
						timeout: 60000,
						params: {
							mchtNo :Ext.getCmp('mchtNo').getValue(),
							mchtAddr : Ext.getCmp('mchtAddr').getValue(),
							mcc : Ext.getCmp('mcc').getValue(),
							partnerNo : Ext.getCmp('partnerNo').getValue(),
							payRoad : Ext.getCmp('payRoad').getValue(),
							business : Ext.getCmp('business').getValue(),
							proper : Ext.getCmp('proper').getValue(),
							queryCharConAndId : Ext.getCmp('queryCharConAndId').getValue(),
							qmchtNo : Ext.getCmp('qmchtNo').getValue(),
							mchtGroup : Ext.getCmp('mchtGroup').getValue(),
							txnId: '1103311',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg,routeRuleGrid);
							}
						},
						failure: function(){
							hideMask();
							showErrorMsg(rspObj.msg,routeRuleGrid);
						}
					});
				}
			}
		],
		listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
			'render' : function() {
				tbar1.render(this.tbar);
				tbar4.render(this.tbar);
				tbar5.render(this.tbar);
				tbar7.render(this.tbar);
				tbar8.render(this.tbar);
			},
			'cellclick':selectableCell
		},
		bbar : new Ext.PagingToolbar({
			store : routeRuleStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});

	// ==============================待进行映射维护商户信息=======================================

	// ==============================商户映射关系页面=======================================
	// 商户映射关系数据集
	var routeRuleDtlStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=getRouteUpbrh1'
		}),
		reader : new Ext.data.JsonReader({
				root : 'data',
				totalProperty : 'totalCount'
			},
			[ 
				{name : 'channel',mapping : 'channel'}, {name : 'business',mapping : 'business'},
				{name : 'property',mapping : 'property'},
				{name : 'propertyId',mapping : 'propertyId'},
				{name : 'mchtUpbrhNo',mapping : 'mchtUpbrhNo'},
				{name : 'mchtUpbrhNm',mapping : 'mchtUpbrhNm'},
				{name : 'applyTime',mapping : 'applyTime'},
				{name : 'termId',mapping : 'termId'}
			]
		)
	});
	// 商户映射关系数据显示模型
	var routeRuleDtlColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),//
		{header : '支付渠道',dataIndex : 'channel',id : 'firstMchtNm',align : 'center',width : 120},
		{header : '业务',dataIndex : 'business',width : 80,align : 'center',renderer : routeStatus}, 
		{header : '性质',dataIndex : 'property',id : 'firstMchtNm',align : 'center',width : 80},
		{header : '性质Id',dataIndex : 'propertyId',align : 'center',width : 80,hidden : true},
		{header : '渠道商户号',dataIndex : 'mchtUpbrhNo',width : 80,align : 'center',renderer : routeStatus},
		{header : '渠道商户名称',dataIndex : 'mchtUpbrhNm',id : 'firstMchtNm',align : 'center',width : 80},
		{header : '渠道终端号',dataIndex : 'termId',align : 'center',width : 80}, 
		{header : '启用时间',dataIndex : 'applyTime',width : 80,align : 'center',renderer : routeStatus}
	]);
	var sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : false
	});
	//	商户映射关系数据显示面板	
	var grid = new Ext.grid.GridPanel({
		title : '商户映射关系',
		region : 'east',
		width : 430,
		split : true,
		collapsible : true,
		iconCls : 'risk',
		frame : true,
		border : true,
		columnLines : true,
		stripeRows : true,
		clicksToEdit : true,
		autoExpandColumn : 'firstMchtNm',
		store : routeRuleDtlStore,
		sm : sm,
		cm : routeRuleDtlColModel,
		forceValidation : true,
		loadMask : {
			msg : '正在加载规则商户映射记录列表......'
		},
		tbar : ['-',
		{
			xtype : 'button',
			disabled : true,
			text : '新增',
			name : 'add',
			id : 'add',
			iconCls : 'add',
			width : 80,
			handler : function() {
				if (!routeRuleGrid.getSelectionModel().hasSelection()) {
					showMsg("请选择一条记录后再进行操作。", routeRuleGrid);
					return;
				}
				var mchtRecords = routeRuleGrid.getSelectionModel().getSelections();
				var store=routeRuleDtlStore;
				if(mchtRecords.length > 1){
					showMsg("只可选择一条记录进行操作。", routeRuleGrid);
					return;
				}else{
					var mchtNo = mchtRecords[0].get('mchtNo');
					//添加商户映射
					addProperty(mchtNo,routeRuleDtlStore);
				}
				/*//routeRuleDtlStore.reload();
				// 发送ajax请求，判断是否属于一个组，是就新增，不是就提示
				Ext.Ajax.request({// 发送请求，sum并插入数据库
					url : 'T110331Action_checkHasGroup.asp',
					headers : {
						'userHeader' : 'userMsg'
					},
					params : {
						// 商户号
						mchtId : routeRuleGrid.getSelectionModel().getSelected().get('mchtNo'),
					},
					method : 'POST',
					timeout : 180000,
					success : function(response, options) {
						Ext.MessageBox.hide();
						// detailWin.hide();
						var msg = Ext.decode(response.responseText);
						if (msg.msg != 'no') {
							// 可以新增性质
							var mchtGid = msg.msg;//
							var mchntId = routeRuleGrid.getSelectionModel().getSelected().get('mchtNo');
							var store = routeRuleDtlStore;
							// 获得所有grid中已有的性质id
							var propertyIds = '';
							for (var i = 0; i < store.getCount(); i++) {
								var record = store.getAt(i);
								var p = record.get('propertyId');
								if (store.getCount() === 1|(i === (store.getCount() - 1))) {
									propertyIds = propertyIds+ p;
								} else {
									propertyIds =  propertyIds+p+',';
								}
							}
							addProperty(mchntId,propertyIds,store,mchtGid);

						} else if (msg.msg === 'no') {
							Ext.MessageBox.alert('提示信息：',''+ '请先将商户添加到商户组！');
						}
					},
					failure : function(response, options) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('添加失败','请求超时或网络故障,错误编号：'+ response.status);
						store.reload();
					}
				});*/
			}
		},'-',{
			xtype : 'button',
			text : '修改',
			name : 'edit',
			id : 'edit',
			disabled : true,
			iconCls : 'edit',
			width : 80,
			handler : function() {
				var select = grid.getSelectionModel().getSelected();
				var data = select.data;
				// 根据所选渠道商户号查找对应的的性质
				var mchtUpbrhNo = grid.getSelectionModel().getSelected().get('mchtUpbrhNo');
				var mchntId = routeRuleGrid.getSelectionModel().getSelected().get('mchtNo');
				var propertyId = grid.getSelectionModel().getSelected().get('propertyId');
				var oldTermId=grid.getSelectionModel().getSelected().get('termId');
				var store = routeRuleDtlStore;
				editProperty(mchtUpbrhNo, mchntId,propertyId, store,oldTermId);
			}
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'delete',
			id : 'delete',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该性质吗？',grid,function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						var mchtUpbrhNo = grid.getSelectionModel().getSelected().get('mchtUpbrhNo');
						var termId = grid.getSelectionModel().getSelected().get('termId');
						var mchntId = routeRuleGrid.getSelectionModel().getSelected().get('mchtNo');
						var propertyId = grid.getSelectionModel().getSelected().get('propertyId');
						Ext.Ajax.request({
							url : 'T110331Action_delete.asp',
							params : {
								mchtUpbrhNo : mchtUpbrhNo,
								mchntId : mchntId,
								propertyId : propertyId,
								termId:termId
							},
							method : 'POST',
							timeout : 180000,
							success : function(response,options) {
								Ext.MessageBox.hide();
								var msg = Ext.decode(response.responseText);
								grid.getStore().reload();
								Ext.MessageBox.alert('提示信息：',''+ msg.msg);
							},
							failure : function(response,options) {
								Ext.MessageBox.hide();
								Ext.MessageBoxalert('提示信息：','请求超时或网络故障,错误编号：'+ response.status);
								grid.getStore().reload();
							}
						});
					}
				});
			}
		} ],
		listeners : { // 將第二個bar渲染到tbar裏面，通过listeners事件
			'render' : function() {

			}
		},
		bbar : new Ext.PagingToolbar({
			store : routeRuleDtlStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		})
	});
	// ====================================详细规则商户结束=================================================

	// ====================================加载=================================================
	// 禁用编辑按钮
	routeRuleGrid.getStore().on('beforeload', function() {
		Ext.getCmp('manyEdit').disable();
		Ext.getCmp('add').disable();
	});
	routeRuleGrid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			Ext.getCmp('add').enable();
			Ext.getCmp('manyEdit').enable();
			routeRuleDtlStore.load();
		}
	});
	//待进行映射维护商户信息查询条件加载
	routeRuleStore.on('beforeload', function() {
		routeRuleDtlStore.removeAll();
		Ext.getCmp('manyEdit').disable();
		Ext.getCmp('add').disable();
		Ext.getCmp('edit').disable();
		Ext.getCmp('delete').disable();
		Ext.apply(this.baseParams, {
			// 商户号、商户名称、商户地区、MCC、合作伙伴号、合作伙伴名称、支付渠道、业务、性质（包含/不包含）、渠道商户号、渠道商户名称
			start : 0,
			mchtNo :Ext.getCmp('mchtNo').getValue(),//
			mchtAddr : Ext.getCmp('mchtAddr').getValue(),
			mcc : Ext.getCmp('mcc').getValue(),//
			partnerNo : Ext.getCmp('partnerNo').getValue(),//
			payRoad : Ext.getCmp('payRoad').getValue(),//
			business : Ext.getCmp('business').getValue(),//
			proper : Ext.getCmp('proper').getValue(),
			queryCharConAndId : Ext.getCmp('queryCharConAndId').getValue(),
			qmchtNo : Ext.getCmp('qmchtNo').getValue(),//
			mchtGroup : Ext.getCmp('mchtGroup').getValue()
		});
	});

	// -----------------------------------------------------------------------------------------

	// 禁用编辑按钮
	grid.getStore().on('beforeload', function() {
		Ext.getCmp('edit').disable();
		Ext.getCmp('delete').disable();
	});
	grid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			Ext.getCmp('edit').enable();
			Ext.getCmp('delete').enable();
			var onFlag = grid.getSelectionModel().getSelected().get('msc2');
		}
	});

	routeRuleDtlStore.on('beforeload', function() {
		routeRuleDtlStore.removeAll();
		Ext.apply(this.baseParams, {
			start : 0,
			mchtId : routeRuleGrid.getSelectionModel().getSelected().get('mchtNo')
		});
	});

	//界面显示
	var mainView = new Ext.Viewport({
		layout : 'border',
		items : [ routeRuleGrid, grid ],
		renderTo : Ext.getBody()
	});
	
	
	/**
	 * 新增商户映射关系
	 * @param mchntId		商户ID
	 * @param dtlMapStore			商户映射关系数据
	 */		
	function addProperty(mchntId,dtlMapStore) {

		var bottomToolBar = new Array();
		var btnSubmit = {
			xtype : 'button',
			width : 100,
			text : '保存',
			id : 'btnAddMapSubmit',
			disabled : true,
			height : 30,
			handler : function() {
				var mchtUpbrhIds = '';
				var propertyIds = '';
				var termIds = '';
				// 循环提交保存
				var flag='请点击选择渠道商户';
				for (var i = 0; i < ruleAndMchtStore.getCount(); i++) {
					var text = ruleAndMchtStore.getAt(i).get('text');
					if (text == flag) {
						continue;
					}
					var mchtUpbrhId = ruleAndMchtStore.getAt(i).get('text').split(',')[0];
					var propertyId = ruleAndMchtStore.getAt(i).get('propertyId');
					var termId = ruleAndMchtStore.getAt(i).get('termId');
					mchtUpbrhIds += mchtUpbrhId + ',';
					propertyIds += propertyId + ',';
					termIds += termId + ',';
				}
				Ext.MessageBox.wait('正在保存', '请稍后...');
				Ext.Ajax.request({// 发送请求，sum并插入数据库
					url : 'T110331Action_addMchtUpbrh.asp',
					headers : {
						'userHeader' : 'userMsg'
					},
					params : {
						mchtId : mchntId,				// 商户号
						mchtUpbrhIds : mchtUpbrhIds,	// 渠道商户号
						propertyIds : propertyIds,		// 性质Id
						termIds:termIds
					},
					method : 'POST',
					timeout : 180000,
					success : function(response, options) {
						Ext.MessageBox.hide();
						detailWin.close();
						var msg = Ext.decode(response.responseText);
						dtlMapStore.reload();
						Ext.MessageBox.alert('提示信息：', '' + msg.msg);
					},
					failure : function(response, options) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示信息：','请求超时或网络故障,错误编号：' + response.status);
						dtlMapStore.reload();
						detailWin.close();
					}
				});
			}
		};
		bottomToolBar.push(btnSubmit);

		//性质-渠道商户配置数据集
		var ruleAndMchtStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy(
				{
					url : 'gridPanelStoreAction.asp?storeId=getMchtAndAllProerty'
				}),
			reader : new Ext.data.JsonReader(
				{
					root : 'data',
					totalProperty : 'totalCount'
				},
				[
				    {name : 'channel',mapping : 'channel'}, 
				    {name : 'business',mapping : 'business'}, 
					{name : 'property',mapping : 'property'},
					{name : 'propertyId',mapping : 'propertyId'},
					{name : 'ruleId',mapping : 'ruleId'},
					{name : 'text',mapping : 'text'},
					{name : 'termId',mapping : 'text'}
				]
			),
			autoLoad : true
			}
		);
		//性质-渠道商户配置数据显示模型
		var ruleAndMchtColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header : '支付渠道',dataIndex : 'channel',align : 'center',width : 200,id : 'msc2'},
			{header : '业务',dataIndex : 'business',width : 100,align : 'center'},
			{header : '规则编号',dataIndex : 'ruleId',width : 100,align : 'center',hidden : true},
			{header : '性质',dataIndex : 'property',align : 'center',width : 100},
			{header : '性质Id',dataIndex : 'propertyId',hidden : true,width : 100},
			{header : '渠道商户',dataIndex : 'text',id : 'srvId2',align : 'center',width : 400},
			{header : '渠道终端号',dataIndex: 'termId',align: 'center',width: 220,hidden:true }
		]);
		//性质-渠道商户配置数据面板
		var ruleAndMchtGrid = new Ext.grid.GridPanel({
			width : 780,
			height : 400,
			// title: '规则商户映射控制',
			iconCls : 'risk',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			region : 'center',
			clicksToEdit : true,
			autoExpandColumn : 'msc2',
			store : ruleAndMchtStore,
			sm : new Ext.grid.RowSelectionModel({
				singleSelect : true
			}),
			cm : ruleAndMchtColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载规则商户映射记录列表......'
			},
			tbar : [],
			listeners : {},
			bbar : new Ext.PagingToolbar({
				store : ruleAndMchtStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});
		//商户-性质-渠道商户配置窗口
		var detailWin = new Ext.Window({
			title : '商户映射',
			initHidden : true,
			header : true,
			frame : true,
			modal : true,
			width : 900,
			height : 550,
			autoScroll : true,
			// autoHeight: 680,
			items : [ {
				id : 'upbrhMcht',
				hidden : false,
				labelAlign : 'center',
				xtype : 'fieldset',
				title : '请设置渠道商户',
				layout : 'column',
				collapsible : true,
				width : 800,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [ ruleAndMchtGrid ]
			}, {
				hidden : false,
				xtype : 'fieldset',
				id : 'bottomAddMapToolBar',
				defaults : {
					bodyStyle : 'padding-left: 100px'
				},
				buttonAlign : 'center',
				buttons : bottomToolBar
			} ],
			buttonAlign : 'center',
			closeAction : 'close',
			closable : true,
			resizable : false
		});

		detailWin.show();

		//性质-渠道商户数据集查询条件设置
		ruleAndMchtStore.on('beforeload', function() {
			Ext.getCmp('btnAddMapSubmit').disable();
			ruleAndMchtStore.removeAll();
			Ext.apply(this.baseParams, {
				start : 0,
				mchtNo : mchntId
			});
		});
		
		ruleAndMchtGrid.getSelectionModel().on({
			'rowselect' : function() {
				// 行高亮
				Ext.get(ruleAndMchtGrid.getView().getRow(ruleAndMchtGrid.getSelectionModel().last)).frame();
				
				var select = ruleAndMchtGrid.getSelectionModel().getSelected().id;
				var store = ruleAndMchtStore;
				var propertyId = ruleAndMchtGrid.getSelectionModel().getSelected().get('propertyId');
				// 弹出选择渠道商户窗口;参数：性质,性质-渠道商户窗口，
				/* selUprMcht = { upbrhMchtNo : '',  upbrhMchtNm : '',  upbrhTermId : '' };  //渠道商户配置结果*/
				configUprMcht(propertyId,detailWin,function(selUprMcht){
					store.getById(select).set("text",selUprMcht.upbrhMchtNo + ',' + selUprMcht.upbrhMchtNm);
					store.getById(select).set("termId",selUprMcht.upbrhTermId);
					Ext.getCmp('btnAddMapSubmit').enable();
					
				});
			}
		});
	}
	
	/**
	 * 
	 * 修改商户映射关系
	 * @param mchtUpbrhNo 	渠道商户号
	 * @param mchntId 		商户号
	 * @param propertyId	性质ID
	 * 
	 */
	function editProperty(mchtUpbrhNo, mchntId, propertyId, store,oldTermId) {
		var bottomEditMapToolBar = new Array();
		var btnEditMapSubmit = {
			xtype : 'button',
			width : 100,
			text : '保存',
			id : 'btnEditMapSubmit',
			height : 30,
			handler : function() {
				Ext.MessageBox.wait('正在保存', '请稍后...');
				Ext.Ajax.request({// 发送请求，sum并插入数据库
					url : 'T110331Action_editMchtUpbrh.asp',
					headers : {
						'userHeader' : 'userMsg'
					},
					params : {
						// 商户号
						mchtId : mchntId,
						// 商户组号
						ruleId : ruleAndMchtGrid.getSelectionModel().getSelected().get('ruleId'),
						// 渠道商户号
						mchtUpbrhId : ruleAndMchtGrid.getSelectionModel().getSelected().get('text'),
						// 性质Id
						propertyId : propertyId,
						oldTermId : oldTermId,
						termId : ruleAndMchtGrid.getSelectionModel().getSelected().get('termId')
					},
					method : 'POST',
					timeout : 180000,
					success : function(response, options) {
						Ext.MessageBox.hide();
						detailWin.hide();
						var msg = Ext.decode(response.responseText);
						store.reload();
						Ext.MessageBox.alert('提示信息：', '' + msg.msg);
					},
					failure : function(response, options) {
						Ext.MessageBox.hide();
						Ext.MessageBox.alert('提示信息：', '请求超时或网络故障,错误编号：' + response.status);
						store.reload();
					}
				});

			}
		};
		bottomEditMapToolBar.push(btnEditMapSubmit);

		// 商户性质以及渠道商户
		var ruleAndMchtStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy(
				{
					url : 'gridPanelStoreAction.asp?storeId=getRouteUpbrhByMchtUpbrhId'
				}),
			reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				},
				[
					{name : 'channel',mapping : 'channel'},
					{name : 'business',mapping : 'business'},
					{name : 'property',mapping : 'property'},
					{name : 'propertyId',mapping : 'propertyId'}, 
					{name : 'ruleId',mapping : 'ruleId'},
					{name : 'text',mapping : 'text'},
					{name: 'termId',mapping: 'text'}
				]
			),
			autoLoad : true
		});

		var ruleAndMchtColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header : '支付渠道',dataIndex : 'channel',align : 'center',width : 200,id : 'msc2'},
			{header : '业务',dataIndex : 'business',width : 100,align : 'center'},
			{header : '规则编号',dataIndex : 'ruleId',width : 100,align : 'center',hidden : true},
			{header : '性质',dataIndex : 'property',align : 'center',width : 100},
			{header : '性质Id',dataIndex : 'propertyId',hidden : true,align : 'center',width : 100}, 
			{header : '渠道商户',dataIndex : 'text',id : 'srvId2',align : 'center',width : 400},
			{header: '渠道终端号',dataIndex: 'termId',align: 'center',width: 220,hidden:true }
		]);
		var ruleAndMchtGrid = new Ext.grid.GridPanel({
			width : 780,
			height : 400,
			// title: '规则商户映射控制',
			iconCls : 'risk',
			frame : true,
			border : true,
			columnLines : true,
			stripeRows : true,
			region : 'center',
			clicksToEdit : true,
			autoExpandColumn : 'msc2',
			store : ruleAndMchtStore,
			sm : new Ext.grid.RowSelectionModel({
				singleSelect : true
			}),
			cm : ruleAndMchtColModel,
			forceValidation : true,
			renderTo : Ext.getBody(),
			loadMask : {
				msg : '正在加载规则商户映射记录列表......'
			},
			tbar : [],
			listeners : {},
			bbar : new Ext.PagingToolbar({
				store : ruleAndMchtStore,
				pageSize : System[QUERY_RECORD_COUNT],
				displayInfo : true,
				displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg : '没有找到符合条件的记录'
			})
		});
		var detailWin = new Ext.Window({
			title : '商户映射',
			initHidden : true,
			header : true,
			frame : true,
			modal : true,
			width : 900,
			height : 550,
			autoScroll : true,
			// autoHeight: 680,
			items : [ {
				id : 'upbrhMcht',
				hidden : false,
				labelAlign : 'center',
				xtype : 'fieldset',
				title : '请设置渠道商户',
				layout : 'column',
				collapsible : true,
				width : 800,
				defaults : {
					bodyStyle : 'padding-left: 10px'
				},
				items : [ ruleAndMchtGrid ]
			}, {
				hidden : false,
				xtype : 'fieldset',
				id : 'next2',
				disabled : false,
				defaults : {
					bodyStyle : 'padding-left: 100px'
				},
				// width: 800,
				buttonAlign : 'center',
				buttons : bottomEditMapToolBar
			} ],
			buttonAlign : 'center',
			// closable: false,
			closeAction : 'close',
			closable : true,
			resizable : false
		});

		detailWin.show();

		// 业务性质查询store
		ruleAndMchtStore.on('beforeload', function() {
			Ext.getCmp('btnEditMapSubmit').disable();
			ruleAndMchtStore.removeAll();
			Ext.apply(this.baseParams, {
				start : 0,
				// 商户号
				mchtId : mchntId,
				// 渠道商户号
				mchtUpbrhNo : mchtUpbrhNo,
				// 性质Id
				propertyId : propertyId
			});
		});
		ruleAndMchtGrid.addListener('cellclick',cellclick);
				
		function cellclick(grid, rowIndex, columnIndex, e){
			var record = grid.getStore().getAt(rowIndex);
			var select = record.id;
			var store = ruleAndMchtStore;
			var propertyId = store.getById(select).get('propertyId');
			// 弹出选择渠道商户窗口;参数：性质,性质-渠道商户窗口，
			/* selUprMcht = { upbrhMchtNo : '',  upbrhMchtNm : '',  upbrhTermId : '' };  //渠道商户配置结果*/
			configUprMcht(propertyId,detailWin,function(selUprMcht){
				store.getById(select).set("text",selUprMcht.upbrhMchtNo + ',' + selUprMcht.upbrhMchtNm);
				store.getById(select).set("termId",selUprMcht.upbrhTermId);
				Ext.getCmp('btnEditMapSubmit').enable();
			});
			
		}

	}
});

