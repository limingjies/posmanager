Ext.onReady(function() {
	
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE_IN',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	// 当前选择记录
	var record;
	
	// 本外行卡表数据集
	var cardRouteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cardRouteInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'usageKey',mapping: 'usageKey'},
			{name: 'cardId',mapping: 'cardId'},
			{name: 'cardIdOffset',mapping: 'cardIdOffset'},
			{name: 'cardIdLen',mapping: 'cardIdLen'},
			{name: 'branchNo',mapping: 'branchNo'},
			{name: 'branchNoOffset',mapping: 'branchNoOffset'},
			{name: 'branchNoLen',mapping: 'branchNoLen'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'cardName',mapping: 'cardName'},
			{name: 'destSrvId',mapping: 'destSrvId'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'cardType',mapping: 'cardType'}
		])
	});
	
	cardRouteStore.load({
		params: {
			start: 0
		}
	});
	
	var cardRouteColModel = new Ext.grid.ColumnModel([
//		{id: 'usageKey',header: '使用范围',dataIndex: 'usageKey',renderer: useType,width: 100},
		{header: '卡BIN',dataIndex: 'cardId',width: 80},
		{header: '卡BIN偏移量',dataIndex: 'cardIdOffset',width: 100,
		 editor: new Ext.form.TextField({
		 	maxLength: 200,
			allowBlank: false,
			vtype: 'isOverMax'
		 })},
		{header: '卡BIN在卡中长度',dataIndex: 'cardIdLen',id:'cardIdLen',width: 110,
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			maxLength: 60,
			vtype: 'isOverMax'
		 })},
		 {header: '行号',dataIndex: 'branchNo',width: 100,
			 editor: new Ext.form.TextField({
			 maxLength: 200,
			 allowBlank: false,
			 vtype: 'isOverMax'
		 })},
		 {header: '行号偏移量',dataIndex: 'branchNoOffset',width: 100,
			 editor: new Ext.form.TextField({
			 maxLength: 200,
			 allowBlank: false,
			 vtype: 'isOverMax'
		 })},
		 {header: '行号在卡中长度',dataIndex: 'branchNoLen',width: 100,
			 editor: new Ext.form.TextField({
			 maxLength: 200,
			 allowBlank: false,
			 vtype: 'isOverMax'
		 })},
		 {header: '银联机构码',dataIndex: 'instCode',width: 100,
			 editor: new Ext.form.TextField({
			 maxLength: 200,
			 allowBlank: false,
			 vtype: 'isOverMax'
		 })},
		 {header: '卡BIN名称',dataIndex: 'cardName',id:'cardName',width: 100,
			 editor: new Ext.form.TextField({
			 allowBlank: false,
			 maxLength: 60,
			 vtype: 'isOverMax'
		 })},
		 {header: '目标ID',dataIndex: 'destSrvId',width: 100,
			 editor: new Ext.form.TextField({
			 maxLength: 200,
			 allowBlank: false,
			 vtype: 'isOverMax'
		 })},
//		 {header: '交易代码',dataIndex: 'txnNum',width: 100,
//			 editor: new Ext.form.TextField({
//			 maxLength: 200,
//			 allowBlank: false,
//			 vtype: 'isOverMax'
//		 })},
		 {header: '卡类型',dataIndex: 'cardType',width: 120,renderer: cardData,
			 editor: new Ext.form.ComboBox({
				    store: cardStore,
					hiddenName: 'cardType',
					width: 160
			 })
		 }
	]);
	
//	function cardData(val){
//		switch(val){
//		case '00':return '国内他行卡';
//		case '01':return '本行借记卡';
//		case '02':return '本行准贷记卡';
//		case '03':return '本行人民币贷记卡';
//		case '04':return '本行双币种贷记卡';
//		case '05':return '本行深发信用卡';
//		case '51':return 'AMEX';
//		case '52':return 'DC';
//		case '53':return 'JCB';
//		case '54':return 'VISA';
//		case '55':return 'MASTER';
//		}
//	}
		 
	function cardData(val){
		switch(val){
		case '01':return '本行借记卡';
		case '02':return '本行贷记卡';
//		case '03':return '他行借记卡';
//		case '04':return '他行贷记卡';
//		case '05':return '他行准贷记卡';
//		case '06':return '他行其他';
		}
	}
	
	function useType(val){
		switch(val){
		case '0':return '他代本';
		case '1':return '本代他银联';
		case '2':return '本代他外卡ATM';
		case '3':return '本代他外卡POS';
		}
	}
	
	var menuArray = new Array();
	
	var addMenu = {
		iconCls: 'add',
		text: '添加',
		width: 85,
		handler: function() {
			cardRouteWin.show();
			cardRouteWin.center();
		}
	};

	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {		
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
						
				showConfirm('确定要删除本行卡表信息吗？' ,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T10209Action.asp?method=delete',
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
								usageKey: record.get('usageKey'),
								cardId: record.get('cardId'),
								txnId: '10209',
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
			
			showProcessMsg('正在保存本外行卡表信息，请稍后......');
					
			//存放要修改的本外行卡表信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					usageKey : record.get('usageKey'),
					cardId: record.get('cardId'),
					cardIdOffset: record.get('cardIdOffset'),
					cardIdLen: record.get('cardIdLen'),
					branchNo: record.get('branchNo'),
					branchNoOffset: record.get('branchNoOffset'),
					branchNoLen: record.get('branchNoLen'),
					instCode: record.get('instCode'),
					cardName: record.get('cardName'),
					destSrvId: record.get('destSrvId'),
//					txnNum: record.get('txnNum'),
					cardType: record.get('cardType')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T10209Action.asp?method=update',
				method: 'post',
				params: {
					parameterList : Ext.encode(array),
					txnId: '10209',
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
					grid.getTopToolbar().items.items[3].disable();
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
	menuArray.add(delMenu);
	menuArray.add('-');
	menuArray.add(upMenu);
	menuArray.add('-');
	menuArray.add(queryMenu);
	
	// 本外行卡表列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '本外行卡表信息',
		iconCls: 'card',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: cardRouteStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardRouteColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载本外行卡表列表......'
		},
		tbar: menuArray,
		bbar: new Ext.PagingToolbar({
			store: cardRouteStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[6] != undefined) {
				grid.getTopToolbar().items.items[6].enable();
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
	
	
	// 本外行卡表添加表单
	var cardRouteForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 400,
		labelWidth: 110,
		autoHeight: true,
		waitMsgTarget: true,
		items: [
//		        {
//			fieldLabel: '使用范围*',
//			xtype: 'combo',
//			width: 200,
//			store: new Ext.data.ArrayStore({
//				fields: ['valueField','displayField'],
//				data: [['0','他代本'],['1','本代他银联'],['2','本代他外卡ATM'],['3','本代他外卡POS']],
//				reader: new Ext.data.ArrayReader()
//			}),
//			displayField: 'displayField',
//			valueField: 'valueField',
//			hiddenName: 'usageKey',
//			emptyText: '请选择使用范围',
//			fieldLabel: '使用范围*',
//			mode: 'local',
//			triggerAction: 'all',
//			forceSelection: true,
//			typeAhead: true,
//			selectOnFocus: true,
//			editable: true,
//			allowBlank: false,
//			blankText: '请选择一个使用范围'
//		},
		{
			fieldLabel: '卡BIN*',
			xtype: 'textfield',
			id: 'cardId',
			name: 'cardId',
			width: 200,
			maxLength: 10,
			allowBlank: false,
			blankText: '卡BIN不能为空',
			emptyText: '请输入卡BIN',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '卡BIN偏移量',
			id: 'cardIdOffset',
			name: 'cardIdOffset',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '卡BIN偏移量不能为空',
//			emptyText: '请输入卡BIN偏移量',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '卡BIN在卡中长度',
			id: 'cardIdLen',
			name: 'cardIdLen',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '卡BIN在卡中长度不能为空',
//			emptyText: '请输入卡BIN在卡中长度',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '行号',
			id: 'branchNo',
			name: 'branchNo',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '行号不能为空',
//			emptyText: '请输入行号',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '行号偏移量',
			id: 'branchNoOffset',
			name: 'branchNoOffset',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '行号偏移量不能为空',
//			emptyText: '请输入行号偏移量',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '行号在卡中长度',
			id: 'branchNoLen',
			name: 'branchNoLen',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '行号在卡中长度不能为空',
//			emptyText: '请输入行号在卡中长度',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '银联机构码',
			id: 'instCode',
			name: 'instCode',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '机构码不能为空',
//			emptyText: '请输入机构码',
			vtype: 'isOverMax'
		},{
			xtype: 'textfield',
			fieldLabel: '卡BIN名称',
			id: 'cardName',
			name: 'cardName',
			width: 200,
			maxLength: 20,
//			allowBlank: false,
//			blankText: '卡BIN名称不能为空',
//			emptyText: '请输入卡BIN名称',
			vtype: 'isOverMax'
		},{
			fieldLabel: '卡类型*',
			xtype: 'combo',
			width: 200,
			store: cardStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'cardType',
			emptyText: '请选择卡类型',
			fieldLabel: '卡类型',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: true,
			allowBlank: false,
			blankText: '请选择一个参数类型'
		},{
			xtype: 'textfield',
			fieldLabel: '目标ID',
			id: 'destSrvId',
			name: 'destSrvId',
			width: 200,
			maxLength: 10,
//			allowBlank: false,
//			blankText: '目标ID不能为空',
//			emptyText: '请输入目标ID',
			vtype: 'isOverMax'
		}
//		,{
//			xtype: 'textfield',
//			fieldLabel: '交易代码',
//			allowBlank: false,
//			blankText: '交易代码不能为空',
//			emptyText: '请输入交易代码',
//			id: 'txnNum',
//			name: 'txnNum',
//			width: 200,
//			maxLength: 10,
//			vtype: 'isOverMax'
//		}
		]
	});
	
	// 本外行卡表添加窗口
	var cardRouteWin = new Ext.Window({
		title: '添加本外行卡信息',
		iconCls: 'logo',
		layout: 'fit',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		resizable: false,
		autoHeight: true,
		items: [cardRouteForm],
		closeAction: 'hide',
		buttonAlign: 'center',
		buttons: [{
			text: '确定',
			handler: function() {
				if(cardRouteForm.getForm().isValid()) {
					cardRouteForm.getForm().submit({
						url: 'T10209Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cardRouteWin);
							//重新加载参数列表
							cardRouteForm.getForm().reset();
							grid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cardRouteWin);
						},
						params: {
							txnId: '10209',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cardRouteForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cardRouteWin.hide();
			}
		}]
	});
	
	// 查询条件
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [
//		        {
//			xtype: 'combo',
//			id: 'usageKey',
//			hiddenName: 'HusageKey',
//			store: new Ext.data.ArrayStore({
//				fields: ['valueField','displayField'],
//				data: [['0','他代本'],['1','本代他银联'],['2','本代他外卡ATM'],['3','本代他外卡POS']],
//				reader: new Ext.data.ArrayReader()
//			}),
//			emptyText: '请选择使用范围',
//			editable: false,
//			width: 200,
//			fieldLabel: '使用范围*'
//		},
		{
			fieldLabel: '卡BIN*',
			xtype: 'textfield',
			id: 'cardIdQuery',
			name: 'cardIdQuery',
			width: 200,
			maxLength: 20,
			emptyText: '请输入卡BIN',
			vtype: 'isOverMax'
		}]
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
				cardRouteStore.load();
				queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	cardRouteStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0, 
//			usageKey: queryForm.findById('usageKey').getValue(),
			cardId: queryForm.findById('cardIdQuery').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});