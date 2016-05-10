Ext.onReady(function() {	
	// 可选合作伙伴数据集
	var brhCityStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		idProperty: 'valueField'
	});
	SelectOptionsDWR.getComboData('CUP_CITY_CODE',function(ret){
	brhCityStore.loadData(Ext.decode(ret));
	});
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=cityCodeInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'intCityCode',mapping: 'intCityCode'},
			{name: 'cupCityCode',mapping: 'cupCityCode'},
			{name: 'mchtCityCode',mapping: 'mchtCityCode'},
			{name: 'cityName',mapping: 'cityName'},
			{name: 'cityFlag',mapping: 'cityFlag'},
			{name: 'initTime',mapping: 'initTime'},
			{name: 'modiTime',mapping: 'modiTime'}
		]),
		autoLoad: true
	});
	
	
	
	
	var oprColModel = new Ext.grid.ColumnModel([
    		new Ext.grid.RowNumberer(),
    		{id: 'intCityCode',header: '省地区码',dataIndex: 'intCityCode',width: 100,sortable: true,
    			editor: new Ext.form.TextField({
        		 	maxLength: 2,
        		 	maskRe:/^[0-9]$/,
					regex:/^[0-9]{2}$/,
        			allowBlank: false,
        			vtype: 'isOverMax'
        		 })},
    		{header: '地区代码',dataIndex: 'cupCityCode',align: 'center',hidden:false},
        	/*{header: '商户地区代码',dataIndex: 'mchtCityCode',align: 'center',hidden:false,
         			editor: new Ext.form.TextField({
             		 	maxLength: 4,
             			allowBlank: false,
             			vtype: 'isOverMax'
             		 })
            },*/
    		{header: '地区名称',dataIndex: 'cityName',id:'cityName',width: 120,
	    		 editor: new Ext.form.TextField({
	    		 	maxLength: 20,
	    			allowBlank: false,
	    			vtype: 'isOverMax'
	    		 })
	    	},
//	    	{header: '作用类别',dataIndex: 'cityFlag',align: 'center',renderer: cityF,width: 100},
    		{header: '创建时间',dataIndex: 'initTime',align: 'center',renderer: formatTs,width: 150},
    		{header: '最近修改时间',dataIndex: 'modiTime',align: 'center',renderer: formatTs,width: 150}
    	]);
    
   /* function cityF(val){
		switch(val){
			case '0':return '常规';
			case '1':return '信用卡还贷';
		}
	}*/
		
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
		cityCodeWin.show();
		cityCodeWin.center();
		}
	};
	
	
	// 作用类别
	/*var cityFlagCombo = new Ext.form.ComboBox({
		store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','常规'],['1','信用卡还贷']],
				reader: new Ext.data.ArrayReader()
			}),
		displayField: 'displayField',
		valueField: 'valueField',
//		emptyText: '请选择合作伙伴',
//		mode: 'local',
//		triggerAction: 'all',
//		forceSelection: true,
		typeAhead: true,
		selectOnFocus: true,
		editable: false,
		allowBlank: false,
		value: '0',
//		blankText: '请选择合作伙伴地区代码',
		fieldLabel: '作用类别',
		id: 'cityF',
		hiddenName: 'cityFlag'
	});*/
	
	// 地区码表单
	var cityCodeForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 600,
		defaultType: 'textfield',
		labelWidth: 160,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '省地区码*',
			id: 'intCityCode',
			name: 'intCityCode',
			allowBlank: false,
			maxLength: 2,
			maskRe:/^[0-9]$/,
			regex:/^[0-9]{2}$/,
			regexText:'合作伙伴地区码为2个数字',
			blankText: '该输入项只能包含数字',
			width:150,
			vtype: 'isOverMax'
		}
//		,cityFlagCombo
		,{
			fieldLabel: '地区代码*',
			id: 'cupCityCode',
			name: 'cupCityCode',
			width: 150,
			maxLength: 4,
			allowBlank: false,
			blankText: '地区代码不能为空',
			emptyText: '请输入地区代码',
			vtype: 'isOverMax'
		},/*{
			fieldLabel: '商户合作伙伴码*',
			id: 'mchtCityCode',
			name: 'mchtCityCode',
			width: 150,
			maxLength: 4,
			allowBlank: false,
			blankText: '商户合作伙伴码不能为空',
			emptyText: '请输入商户合作伙伴码',
			vtype: 'isOverMax'
		},*/{
			fieldLabel: '地区名称*',
			id: 'cityName',
			name: 'cityName',
			width: 300,
			maxLength: 20,
			allowBlank: false,
			blankText: '地区名称不能为空',
			emptyText: '请输入地区名称',
			vtype: 'isOverMax'
		}]
	});
	
	// 地区码信息添加窗口
	var cityCodeWin = new Ext.Window({
		title: '合作伙伴地区码添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [cityCodeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(cityCodeForm.getForm().isValid()) {
					cityCodeForm.getForm().submitNeedAuthorise({
						url: 'T10201Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessMsg(action.result.msg,cityCodeForm);
							//重置表单
							cityCodeForm.getForm().reset();
							//重新加载参数列表
							oprGrid.getStore().reload();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,cityCodeForm);
						},
						params: {
							txnId: '10201',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				cityCodeForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				cityCodeWin.hide(oprGrid);
			}
		}]
	});
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(oprGrid.getSelectionModel().hasSelection()) {
				var rec = oprGrid.getSelectionModel().getSelected();				
				showConfirm('确定要删除该条地区信息吗？',oprGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10201Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
									oprGrid.getStore().reload();
//									oprGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
							},
							params: { 
								cupCityCode: rec.get('cupCityCode'),
								txnId: '10201',
								subTxnId: '02'
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
			var modifiedRecords = oprGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
//			showProcessMsg('正在保存操作员信息，请稍后......');
			//存放要修改的合作伙伴信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					intCityCode : record.get('intCityCode'),
					cupCityCode : record.get('cupCityCode'),
//					mchtCityCode:record.get('mchtCityCode'),
					cityName: record.get('cityName')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10201Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    cityCodeList: Ext.encode(array),
					txnId: '10201',
					subTxnId: '03'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,cityCodeForm);
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						oprGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,oprGrid);
						oprGrid.getStore().reload();
					} else {
						oprGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,oprGrid);
					}
//					oprGrid.getTopToolbar().items.items[4].disable();
					hideProcessMsg();
				}
			});
		}
	};
	
	
	
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
	menuArr.push(upMenu);		// [4]	

	
	// 操作员信息列表
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '地区码信息',
		iconCls: 'cityCode',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
//		autoExpandColumn:'cityName',
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载操作员信息列表......'
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
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[4].enable();
		}
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
	
/***************************查询条件*************************/
	
	
});