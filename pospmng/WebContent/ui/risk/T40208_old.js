Ext.onReady(function() {
	
	//所属分行数据源
	/*var bankStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BANK_ID',function(ret){
			bankStore.loadData(Ext.decode(ret));
	});*/
		
	// 当前选择记录
	var record;
	
	// 商户黑名单数据集
	var mchntRiskStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntRiskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saMerNo',mapping: 'saMerNo'},
			{name: 'saMerChName',mapping: 'saMerChName'},
			{name: 'saMerEnName',mapping: 'saMerEnName'},
			{name: 'saLimitAmt',mapping: 'saLimitAmt'},
			{name: 'saAction',mapping: 'saAction'},
			{name: 'saZoneNo',mapping: 'saZoneNo'},
			{name: 'saAdmiBranNo',mapping: 'saAdmiBranNo'},
			{name: 'saConnOr',mapping: 'saConnOr'},
			{name: 'saConnTel',mapping: 'saConnTel'}
		])
	});
	
	mchntRiskStore.load({
		params: {
			start: 0
		}
	});
	
	var mchntRiskColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户编号',dataIndex: 'saMerNo',width: 150},
		{header: '商户中文名称',dataIndex: 'saMerChName',width: 200,id:'saMerChName'},
		{header: '商户英文名称',dataIndex: 'saMerEnName',width: 150},
		{header: '受控金额',dataIndex: 'saLimitAmt',width: 150,editor: new Ext.form.TextField({
			vtype: 'isMoney',
			maxLength: 10,
			allowBlank: false,
			blankText: '受控金额不能为空'	
		})},
		{header: '受控动作',dataIndex: 'saAction',width: 120,renderer: clcAction,editor: new Ext.form.ComboBox({
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['3','预警'],['2','拒绝']]
			}),
			editable: false,
			allowBlank: false,
			blankText: '请选择一个受控动作',
			hiddenName: 'saActionEdit'
		})},
//		{header: '分行号',dataIndex: 'saZoneNo',width: 80},
//		{header: '主管分行号',dataIndex: 'saInitZoneNo',width: 80},
		{header: '联系人',dataIndex: 'saConnOr',width: 120},
		{header: '联系电话',dataIndex: 'saConnTel',width: 120}
	]);
	
	
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		text: '添加',
		width: 85,
		handler: function() {
			mchntRiskWin.show();
			mchntRiskWin.center();
		}
	};
	var uploadMenu = {
		text: '批量上传商户黑名单信息',
		width: 85,
		iconCls: 'upload',
		id: 'upload',
		disabled: false,
		handler:function() {
			dialog.show();
		}
	};
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T40208Action.asp?method=uploadFile',
		filePostName : 'xlsFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB', 
		fileTypes : '*.xls',
		fileTypesDescription : '微软Excel文件(1997-2003)',
		title: '商户黑名单上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		postParams: {
			txnId: '40208',
			subTxnId: '01'
		}
	});
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var saMerNo = record.get('saMerNo');
						
				showConfirm('确定要删除该黑名单商户吗？商户号：' + saMerNo,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T40208Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								saMerNo: saMerNo,
								txnId: '40208',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};
		
	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			var store = grid.getStore();
			var len = store.getCount();
			for(var i = 0; i < len; i++) {
				var record = store.getAt(i);
			}
			showProcessMsg('正在保存商户黑名单信息，请稍后......');
					
			//存放要修改的卡黑名单信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					id : record.get('saMerNo'),
					saMerChName: record.get('saMerChName'),
					saMerEnName: record.get('saMerEnName'),
					saLimitAmt: record.get('saLimitAmt'),
					saAction: record.get('saAction'),
					saZoneNo: record.get('saZoneNo'),
					saAdmiBranNo: record.get('saAdmiBranNo'),
					saConnOr: record.get('saConnOr'),
					saConnTel: record.get('saConnTel'),
					saInitZoneNo: record.get('saInitZoneNo'),
					saInitOprId: record.get('saInitOprId'),
					saInitTime: record.get('saInitTime')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T40208Action.asp?method=update',
				method: 'post',
				params: {
					mchtInfList : Ext.encode(array),
					txnId: '40208',
					subTxnId: '02'
				},	
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					grid.enable();
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[4].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var queryMenu = {
		text: '查询',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	menuArray.add(addMenu);
	menuArray.add('-');
//	menuArray.add(uploadMenu);
//	menuArray.add('-');
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
	
	// 商户黑名单列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户黑名单信息',
		iconCls: 'mchnt',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn: 'saMerChName',
		clicksToEdit: true,
		store: mchntRiskStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntRiskColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载卡黑名单列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: mchntRiskStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		/*'rowclick': function() {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		},*/
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	grid.getSelectionModel().on({
		//单击行，使删除按钮可用
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
		}
	});
	
	/*// 编辑完成后自动提交
	grid.on('afteredit',function() {
		record = grid.getSelectionModel().getSelected();
		showProcessMsg('正在提交信息，请稍后......');
		Ext.Ajax.request({
			url: 'T40202Action.asp?method=update',
			params: {
				saMerNo: record.get('saMerNo'),
				saLimitAmt: record.get('saLimitAmt'),
				saAction: record.get('saAction'),
				txnId: '40202',
				subTxnId: '02'
			},
			success: function(rsp,opt) {
				hideProcessMsg();
				var rspObj = Ext.decode(rsp.responseText);
				if(rspObj.success) {
					grid.getStore().commitChanges();
					showSuccessMsg(rspObj.msg,grid);
				} else {
					grid.getStore().rejectChanges();
					showErrorMsg(rspObj.msg,grid);
				}
				// 重新加载终端信息
				grid.getStore().reload();
			}
		});
	})*/
	
	// 商户数据集
	var mchntStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
		mchntStore.loadData(Ext.decode(ret));
	});
	
	// 商户黑名单添加表单
	var mchntRiskForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		autoHeight: true,
		waitMsgTarget: true,
		defaults: {
			width: 320
		},
		items: [{
			xtype: 'combo',
			fieldLabel: '受控商户号*',
			allowBlank: false,
			store: mchntStore,
			hiddenName: 'saMerNo',
			editable: true,
			blankText: '受控商户号不能为空',
			emptyText: '请选择受控商户号',
			listeners: {
				'select': function() {
					T40202.getMchntInfo(this.getValue(),function(ret){
					    var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
						Ext.getCmp('saMerChName').setValue(mchntInfo.mchtNm);
						Ext.getCmp('saMerEnName').setValue(mchntInfo.engName);
						Ext.getCmp('saConnOr').setValue(mchntInfo.contact);
						Ext.getCmp('saConnTel').setValue(mchntInfo.commTel);
						Ext.getCmp('saZoneNo').setValue(mchntInfo.bankNo);
					});
				}
			}
		},{
			xtype: 'textfield',
			id: 'saMerChName',
			name: 'saMerChName',
			fieldLabel: '商户中文名称',
			readOnly: true
		},{
			xtype: 'textfield',
			id: 'saMerEnName',
			name: 'saMerEnName',
			fieldLabel: '商户英文名称',
			readOnly: true
		},{
			xtype: 'textfield',
			id: 'saConnOr',
			name: 'saConnOr',
			fieldLabel: '联系人',
			readOnly: true
		},{
			xtype: 'textfield',
			id: 'saConnTel',
			name: 'saConnTel',
			fieldLabel: '联系电话',
			readOnly: true
		},{
			xtype: 'textfield',
			id: 'saZoneNo',
			name: 'saZoneNo',
			fieldLabel: '所属机构号',
			readOnly: true
		},{
			xtype: 'textfield',
			id: 'saLimitAmt',
			name: 'saLimitAmt',
			vtype: 'isMoney',
			maxLength: 10,
			fieldLabel: '受控金额*',
			allowBlank: false,
			blankText: '受控金额不能为空',
			emptyText: '请输入受控金额'
		},{
			xtype: 'combo',
			id: 'saActionId',
			hiddenName: 'saAction',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['3','预警'],['2','拒绝']]
			}),
			editable: false,
			fieldLabel: '受控动作*',
			allowBlank: false,
			blankText: '请选择一个受控动作',
			emptyText: '请选择受控动作'
		}]
	});
	
	// 商户黑名单添加窗口
	var mchntRiskWin = new Ext.Window({
		title: '添加商户黑名单',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		resizable: false,
		autoHeight: true,
		items: [mchntRiskForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(mchntRiskForm.getForm().isValid()) {
					mchntRiskForm.getForm().submit({
						url: 'T40208Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,mchntRiskWin);
							//重新加载参数列表
							grid.getStore().reload();
							mchntRiskForm.getForm().reset();
							mchntRiskWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,mchntRiskWin);
						},
						params: {
							txnId: '40208',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				mchntRiskForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				mchntRiskWin.hide();
			}
		}]
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'combo',
			id: 'srActionId',
			hiddenName: 'srAction',
			width: 150,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['3','预警'],['2','拒绝']]
			}),
			editable: false,
			fieldLabel: '受控动作'
		},{
			xtype: 'combo',
			fieldLabel: '受控商户号',
			allowBlank: false,
			store: mchntStore,
			id: 'srMerNo1',
			name: 'srMerNo',
			width: 150,
			hiddenName: 'srMerNo',
			editable: true,
			listWidth: 250,
			blankText: '受控商户号不能为空',
			emptyText: '请选择受控商户号'
			
//			xtype: 'textfield',
//			id: 'srMerNo',
//			name: 'srMerNo',
//			width: 150,
//			fieldLabel: '商户号'
		}
//		,{
//			xtype: 'basecomboselect',
//			store: bankStore,
//			displayField: 'displayField',
//			valueField: 'valueField',
//			emptyText: '请选择机构',
//			mode: 'local',
//			width: 150,
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: true,
//			allowBlank: false,
//			listWidth: 200,
//			blankText: '请选择一个机构',
//			fieldLabel: '所属分行*',
//			id: 'srBrhNo1',
//			name: 'srBrhNo',
//			hiddenName: 'srBrhNo'
//		}
		]
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
				mchntRiskStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	mchntRiskStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			srMerNo: queryForm.findById('srMerNo1').getValue(),
//			srBrhNo: queryForm.findById('srBrhNo1').getValue(),
			srAction: queryForm.findById('srActionId').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});