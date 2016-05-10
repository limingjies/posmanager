Ext.onReady(function() {
	
	// 查询一级商户信息
	firstToSecondMchtGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=firstToSecondMchtInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'insIdCd'
		},[
		   	{name: 'mchtNo',mapping: 'mchtNo'},
		   	{name: 'mchtNm',mapping: 'mchtNm'},	
		   	{name: 'mcc',mapping: 'mcc'},					
		   	{name: 'feeRate',mapping: 'feeRate'},
		   	{name: 'insIdCd',mapping: 'insIdCd'},
		   	{name: 'firstMchtCd',mapping: 'firstMchtCd'},
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'firstTermId',mapping: 'firstTermId'},
			{name: 'mchtTp',mapping: 'mchtTp'},
			{name: 'feeValue',mapping:'feeValue'}
		]),
		autoLoad: false
	});
	
	firstToSecondMchtGridStore.load({
		params:{start: 0}
	});
	
	//二级商户编号下拉列表
	var mchtIdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	var mchtDataStore = new Ext.data.JsonStore({
		fields:['mchtNm','mchtMCC','mchtFeeRate'],
		root: 'data'
	});
	
	var firMchtDataStore = new Ext.data.JsonStore({
		fields:['firMchtNm','firstTermNomber','firstMccNo','firstMchtFeeRate'],
		root: 'data'
	});
	
	SelectOptionsDWR.getComboData('MCHT_NO',function(ret){
		mchtIdStore.loadData(Ext.decode(ret));
	});
	var mchtNoCombo = new Ext.form.ComboBox({
			store: mchtIdStore,
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择二级商户编号',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个二级商户编号',
			fieldLabel: '二级商户编号',
			id: 'mchtNO',
			name: 'mchtNO',
			hiddenName: 'mchtNoIdEdit'
	});
	
	//一级商户所属机构号下拉列表
	var insIdCdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('INS_ID_CD',function(ret){
		insIdCdStore.loadData(Ext.decode(ret));
	});
	var insIdCdCombo = new Ext.form.ComboBox({
			store: insIdCdStore,
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择一级商户所属机构编号',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			blankText: '请选择一个一级商户所属机构号',
			fieldLabel: '一级商户所属机构号',
			id: 'insIdCd',
			name: 'insIdCd',
			hiddenName: 'insIdCdEdit'
	});
	
	//一级商户编号下拉列表
	var firstMchtCdStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('FIRST_MCHT_NO',function(ret){
		firstMchtCdStore.loadData(Ext.decode(ret));
	});
	var firstMchtNoCombo = new Ext.form.ComboBox({
			store: firstMchtCdStore,
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择一级商户编号',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个一级商户编号',
			fieldLabel: '一级商户编号',
			id: 'firstMchtCd',
			name: 'firstMchtCd',
			hiddenName: 'firstMchtCdEdit'
	});
	
	var selectionModel = new Ext.grid.CheckboxSelectionModel();
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '二级商户编号',dataIndex: 'mchtNo',align: 'center',width: 140, hidden:false	},
		{header: '二级商户名称',dataIndex: 'mchtNm',align: 'center',width: 140,hidden:false},
		{header: '二级商户MCC',dataIndex: 'mcc',align: 'center',hidden:false},
		{header: '二级商户费率',dataIndex: 'feeRate',align: 'center',width: 150,hidden:false	},
		{header: '一级商户所属机构',dataIndex: 'insIdCd',align: 'center',width: 140,hidden:false	},
		{header: '一级商户编号',dataIndex: 'firstMchtCd',align: 'center',width: 140,hidden:false},
		{header: '一级商户名称',dataIndex: 'firstMchtNm',align: 'center',width: 140,hidden:false	},
		{header: '一级商户终端编号',dataIndex: 'firstTermId',align: 'center',width: 140,hidden:false},
		{header: '一级商户MCC',dataIndex: 'mchtTp',align: 'center',hidden:false},
		{header: '一级商户费率',dataIndex: 'feeValue',align: 'center',hidden:false}
	]);

	
	/*********************************	菜单选项栏	*************************************/
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			firstToSecondMchtWin.show();
			firstToSecondMchtWin.center();
		}
	};
	// 一级商户表单
	var firstToSecondMchtForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
				xtype: 'combo',
				store: mchtIdStore,
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'mchtNo',
				id: 'mchtNoId',
				emptyText: '请选择二级商户编号',
				fieldLabel: '二级商户编号*',
				mode: 'local',
				width: 180,
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: false,
				blankText: '请选择一个二级商户编号',
				listeners: {
					'select': function() {
						try{
							var mchtNoId = Ext.getCmp('mchtNoId');
							SelectOptionsDWR.loadSecMchtdataData(mchtNoId.getValue(),function(ret){
								mchtDataStore.loadData(Ext.decode(ret));
//								alert(mchtDataStore.getAt(0).get('mchtNm'));
								Ext.getCmp('mchtNm').setValue(mchtDataStore.getAt(0).get('mchtNm'));
								Ext.getCmp('mcc').setValue(mchtDataStore.getAt(1).get('mchtMCC'));
								Ext.getCmp('feeRate').setValue(mchtDataStore.getAt(2).get('mchtFeeRate'));	
							});

						}catch(e){
							Ext.MessageBox.alert("出错了！",e.message);
						}
					}
				}
			},
			{
				fieldLabel: '二级商户名称',
				emptyText: '请输入二级商户名称',
				id: 'mchtNm',
				width: 180,
				readOnly: true
			},{
				fieldLabel: '二级商户MCC*',
				emptyText: '请输入二级商户MCC',
				id: 'mcc',
				width: 180,
				readOnly: true
			},{
				fieldLabel: '二级商户费率*',
				emptyText: '请输入二级商户费率',
				id: 'feeRate',
				width: 180,
				readOnly: true
			},{
				xtype: 'combo',
				store: insIdCdStore,
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'insIdCd',
				emptyText: '请选择一级商户所属机构编号',
				fieldLabel: '一级商户所属机构编号*',
				mode: 'local',
				width: 180,
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: false,
				blankText: '请选择一个一级商户所属机构编号'
			},{
				xtype: 'combo',
				store: firstMchtCdStore,
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'firstMchtCd',
				emptyText: '请选择一级商户编号',
				fieldLabel: '一级机构商户编号*',
				mode: 'local',
				width: 180,
				triggerAction: 'all',
				forceSelection: true,
				typeAhead: true,
				selectOnFocus: true,
				editable: true,
				allowBlank: false,
				blankText: '请选择一个一级商户编号',
				id: 'firMchtNoId',
				listeners: {
					'select': function() {
						try{
							var firstMchtCd = Ext.getCmp('firMchtNoId');
							SelectOptionsDWR.loadFirMchtdataData(firstMchtCd.getValue(),function(ret){
								firMchtDataStore.loadData(Ext.decode(ret));
//								alert(mchtDataStore.getAt(0).get('mchtNm'));
								Ext.getCmp('firstMchtNm').setValue(firMchtDataStore.getAt(0).get('firMchtNm'));
								Ext.getCmp('firstTermId').setValue(firMchtDataStore.getAt(1).get('firstTermNomber'));
								Ext.getCmp('mchtTp').setValue(firMchtDataStore.getAt(2).get('firstMccNo'));
								Ext.getCmp('firFeeValue').setValue(firMchtDataStore.getAt(3).get('firstMchtFeeRate'));
							});

						}catch(e){
							Ext.MessageBox.alert("出错了！",e.message);
						}
					}
				}
			},
			{
				fieldLabel: '一级商户名称*',
				emptyText: '请输入一级商户名称',
				id: 'firstMchtNm',
				width: 180,
				readOnly: true
			},
			{
				fieldLabel: '一级商户终端号*',
				emptyText: '请输入一级商户终端号',
				allowBlank: false,
				id: 'firstTermId',
				width: 180,
				readOnly: true
			},
			{
				fieldLabel: '一级商户MCC*',
				emptyText: '请输入一级商户MCC',
				allowBlank: false,
				width: 180,
				id: 'mchtTp',
				readOnly: true
			},
			{
				fieldLabel: '一级商户费率*',
				emptyText: '请输入一级商户费率',
				allowBlank: false,
				width: 180,
				id: 'firFeeValue',
				readOnly: true
			}]
	});
	
	/***************************一级商户信息查询条件*************************/
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 140,
		items: [
		        mchtNoCombo,
//		        insIdCdCombo,
		        firstMchtNoCombo
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
				firstToSecondMchtGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	//查询之前的传值
	firstToSecondMchtGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			firstMchtCd: queryForm.findById('firstMchtCd').getValue(),
			mchtNo: queryForm.findById('mchtNO').getValue()
//			insIdCd: queryForm.findById('insIdCd').getValue()
		});
	});
	
	
	// 一级商户信息添加窗口
	var firstToSecondMchtWin = new Ext.Window({
		title: '一二级商户映射信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [firstToSecondMchtForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(firstToSecondMchtForm.getForm().isValid()) {
					firstToSecondMchtForm.getForm().submitNeedAuthorise({
						url: 'T20504Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,firstToSecondMchtForm);
							//重置表单
							firstToSecondMchtForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,firstToSecondMchtForm);
						},
						params: {
							txnId: '20504',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				firstToSecondMchtForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				firstToSecondMchtWin.hide(oprGrid);
			}
		}]
	});
	//删除按钮的设计
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var rec = oprGrid.getSelectionModel().getSelected();				
					showConfirm('确定要删除该条一二级商户映射信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T20504Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									mchtNo: rec.get('mchtNo'),
									firstMchtCd: rec.get('firstMchtCd'),
									txnId: '20504',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
	
	//录入查查询条件的弹出框设计
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(queryCondition);  //[6]
	
	
	/*************************	 信息栏	********************************/
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '一二级商户映射维护信息',
		region: 'center',
		iconCls: 'cityCode',
//		autoExpandColumn: 'mchtNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: firstToSecondMchtGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载一二级商户映射信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: firstToSecondMchtGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
});