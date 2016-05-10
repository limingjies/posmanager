Ext.onReady(function() {
	
	// 查询一级商户信息
	oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=firstMchtInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'firstMchtCd'
		},[
			{name: 'firstMchtCd',mapping: 'firstMchtCd'},
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'firstTermId',mapping: 'firstTermId'},
			{name: 'acqInstId',mapping: 'acqInstId'},
			{name: 'mchtTp',mapping: 'mchtTp'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'reserved',mapping: 'reserved'},
			{name: 'reserved1',mapping: 'reserved1'},
			{name: 'reserved1Id',mapping: 'reserved1Id'},
			{name: 'reserved2',mapping: 'reserved2'},
			{name: 'ICFlag',mapping: 'ICFlag'}
			
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	var firMchtStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('FIRST_MCHT_NO',function(ret){
		firMchtStore.loadData(Ext.decode(ret));
	});
	
	var firMchtCombo = new Ext.form.ComboBox({
			store: firMchtStore,
			displayField: 'displayField',
			valueField: 'valueField',
			emptyText: '请选择一级商户编号',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			width:250,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个一级商户编号',
			fieldLabel: '一级商户编号',
			id: 'firMchtNO',
			name: 'firMchtNO',
			hiddenName: 'firMchtIdEdit'
	});
	
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '一级商户编号',dataIndex: 'firstMchtCd',align: 'center',width: 130 ,sortable: true},
		{header: '一级商户名称',dataIndex: 'firstMchtNm',align: 'left',width: 250,hidden:false,id:'firstMchtNm'},
		{header: '一级商户终端编号',dataIndex: 'firstTermId',align: 'left',width: 110,hidden:false},
		{header: '银联入网机构号',dataIndex: 'acqInstId',align: 'left',hidden:false,width: 110},
		{header: '银联入网机构名称',dataIndex: 'reserved2',align: 'left',hidden:false,width: 120},
		{header: '通道机构',dataIndex: 'reserved1Id',width: 120,align: 'left'},
		{header: '商户类型',dataIndex: 'mchtTp',align: 'left',hidden:false,width: 70},
		{header: '商户费率',dataIndex: 'feeValue',align: 'left',hidden:false,width: 70},
		{header: '是否支持IC卡',dataIndex: 'ICFlag',align: 'left',hidden:false,width: 90,renderer:ICFlag},
		{header: '备注',dataIndex: 'reserved',align: 'left',hidden:false,width: 150}
	]);
	
	
	/*********************************	菜单选项栏	*************************************/
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
		firstMchtWin.show();
		firstMchtWin.center();
		}
	};
	
	// 一级商户表单
	var firstMchtForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
//		width: 600,
		labelWidth: 120,
		layout: 'column',
		waitMsgTarget: true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items: [{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '一级商户编号*',
				allowBlank: false,
				emptyText: '请输入一级商户编号',
				id: 'firstMchtCd',
				maxLength: 15,
				minLength: 15,
				blankText: '该输入项只能包含数字',
				width:200,
				vtype: 'isNumber'
			}]
		}
		,{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '一级商户姓名*',
				id: 'firstMchtNm',
				width: 200,
				maxLength: 60,
				allowBlank: false,
				blankText: '一级商户姓名不能为空',
				emptyText: '请输入一级商户姓名'
	//			vtype: 'isNumber'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '一级商户终端编号*',
				id: 'firstTermId',
				width: 200,
				maxLength: 8,
				minLength: 8,
				allowBlank: false,
				blankText: '一级商户终端编号不能为空',
				emptyText: '请输入一级商户终端编号',
				vtype: 'isNumber'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				fieldLabel : '通道机构*',
				/*xtype : 'combo',
				store : new Ext.data.ArrayStore({
							fields : ['valueField', 'displayField'],
							data : [['1601', '1601-银生宝'], ['1701', '1701-中信银行']],
							reader : new Ext.data.ArrayReader()
						}),
				displayField : 'displayField',
				valueField : 'valueField',*/
				xtype: 'basecomboselect',
				baseParams: 'SETTLE_BRH',
				hiddenName : 'reserved1',
				editable : false,
				allowBlank: false,
				emptyText : '请选择',
				id : 'brhIds',
				width : 200
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				fieldLabel : '是否新增商户密钥*',
				id: 'crtTimes',
				xtype : 'combo',
				store : new Ext.data.ArrayStore({
							fields : ['valueField', 'displayField'],
							data : [['0', '否'], ['1', '是']],
							reader : new Ext.data.ArrayReader()
						}),
				displayField : 'displayField',
				valueField : 'valueField',
				hiddenName : 'crtTime',
				editable : false,
				value:'0',
				allowBlank: false,
				emptyText : '请选择',
				width : 200,
				listeners : {
					'select' : function() {
						if (this.value == '1') {
							Ext.getCmp('updtTimeIds').show();
							Ext.getCmp('updtTime').allowBlank = false;
//							Ext.getCmp('updtTime').blankText='通道机构索引号不能为空';
							Ext.getCmp('updtTime').blankText='通道机构密钥不能为空';
//							Ext.getCmp('updtTime').emptyText='请输入通道机构索引号';
						} else {
							Ext.getCmp('updtTimeIds').hide();
							Ext.getCmp('updtTime').allowBlank = true;
//							Ext.getCmp('updtTime').emptyText='';
							Ext.getCmp('updtTime').setValue('');
						}

					},
					'change' : function() {
						if (this.value == '1') {
							Ext.getCmp('updtTimeIds').show();
							Ext.getCmp('updtTime').allowBlank = false;
//							Ext.getCmp('updtTime').blankText='通道机构索引号不能为空';
							Ext.getCmp('updtTime').blankText='通道机构密钥不能为空';
//							Ext.getCmp('updtTime').emptyText='请输入通道机构索引号';
						} else {
							Ext.getCmp('updtTimeIds').hide();
							Ext.getCmp('updtTime').allowBlank = true;
//							Ext.getCmp('updtTime').emptyText='';
							Ext.getCmp('updtTime').setValue('');
						}
					}
				}
			}]
			
		},{
			columnWidth : 1,
			layout : 'form',
			id:'updtTimeIds',
			hidden:true,
			items : [{
				xtype: 'textfield',
//				fieldLabel : '通道机构索引号*',
				fieldLabel : '通道机构密钥*',
				id: 'updtTime',
//				maxLength: 4,
//				minLength: 3,
				maxLength: 32,
				minLength: 16,
				allowBlank: true,
//				blankText: '通道机构索引号不能为空',
//				emptyText: '请输入通道机构索引号',
				width : 200
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '银联入网机构号*',
				id: 'acqInstId',
				width: 200,
				maxLength: 8,
				minLength: 8,
				allowBlank: false,
				blankText: '银联入网机构号不能为空',
				emptyText: '请输入银联入网机构号'
	//			vtype: 'isOverMax'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '银联入网机构名称',
				id: 'reserved2',
				width: 200,
				maxLength: 25,
				allowBlank: true
	//			blankText: '银联入网机构名称不能为空',
	//			emptyText: '请输入银联入网机构名称'
	//			vtype: 'isOverMax'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '商户类型*',
				id: 'mchtTp',
				width: 200,
				maxLength: 4,
				minLength: 4,
				allowBlank: false,
				blankText: '商户类型不能为空',
				emptyText: '请输入商户类型',
				vtype: 'isNumber'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '商户费率*',
				id: 'feeValue',
				width: 200,
				maxLength: 22,
				allowBlank: false,
				blankText: '商户费率不能为空',
				emptyText: '请输入商户费率'
	//			vtype: 'isNumber'
			}]
		},
		{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype : 'checkbox',
				fieldLabel : '是否支持IC卡 ',
				id : 'checkIC',
				name : 'checkIC',
				inputValue : '1',
				checked : true
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel: '备注',
				id: 'reserved',
				name: 'reserved',
				width: 200,
				maxLength: 20
	//			allowBlank: false,
	//			blankText: '地区名称不能为空',
	//			emptyText: '请输入地区名称',
			}]
		}]
	});
	
	// 一级商户信息添加窗口
	var firstMchtWin = new Ext.Window({
		title: '一级商户信息添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [firstMchtForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(firstMchtForm.getForm().isValid()) {
					firstMchtForm.getForm().submitNeedAuthorise({
						url: 'T20503Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,firstMchtForm);
							//重置表单
							firstMchtForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
							firstMchtWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,firstMchtForm);
						},
						params: {
							txnId: '20503',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				firstMchtForm.getForm().reset();
				if (this.value == '1') {
					Ext.getCmp('updtTimeIds').show();
					Ext.getCmp('updtTime').allowBlank = false;
//					Ext.getCmp('updtTime').blankText='通道机构索引号不能为空';
					Ext.getCmp('updtTime').blankText='通道机构密钥不能为空';
//					Ext.getCmp('updtTime').emptyText='请输入通道机构索引号';
				} else {
					Ext.getCmp('updtTimeIds').hide();
					Ext.getCmp('updtTime').allowBlank = true;
//					Ext.getCmp('updtTime').emptyText='';
					Ext.getCmp('updtTime').setValue('');
				}
			}
		},{
			text: '关闭',
			handler: function() {
				firstMchtWin.hide(oprGrid);
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
					showConfirm('确定要删除该条一级商户信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.requestNeedAuthorise({
								url: 'T20503Action.asp?method=delete',
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
									firstMchtCd: rec.get('firstMchtCd'),
									txnId: '20503',
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
	
	/***************************一级商户信息查询条件*************************/
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 370,
		autoHeight: true,
		labelWidth: 80,
		items: [firMchtCombo]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 370,
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
				oprGridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	//查询之前的传值
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			firstMchtCd: queryForm.findById('firMchtNO').getValue()
		});
	});
	
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(delMenu);		// [2]
	menuArr.push('-');			// [3]
	menuArr.push(queryCondition);  //[6]
	
	
	/*************************	 信息栏	********************************/
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '一级商户信息',
		region: 'center',
//		iconCls: 'cityCode',
		iconCls: 'logo',
		autoExpandColumn: 'firstMchtNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: colModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载一级商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: oprGridStore,
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