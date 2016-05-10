Ext.onReady(function() {
//新增数据集##########################
	// 一级菜单数据集
	var menuLvl1Store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'saModelKind',mapping: 'saModelKind'},
			{name: 'saModelDesc',mapping: 'saModelDesc'}
		]),
		autoLoad: true
	});
	// 二级菜单数据库集
	var menuLvl2Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['saModelKind','saModelKindName','op']
	});
	var editMenuLvl2Store = new Ext.data.JsonStore({
		root: 'data',
		fields: ['saModelKind','saModelKindName','op']
	});
	
	//商户风险级别数据集
	var riskInfoGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskLvlDist'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'resved',mapping: 'resved'}
		]),
		autoLoad: true
	});
	
	//商户风险参数信息数据集
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskParamInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'modelKind',mapping: 'modelKind'},
			{name: 'modelSeq',mapping: 'modelSeq'},
			{name: 'paramName',mapping: 'paramName'},
			{name: 'paramLen',mapping: 'paramLen'},
			{name: 'paramValue',mapping: 'paramValue'}
		])
	});
	var detailColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '风控模型',dataIndex: 'modelKind',width: 100 ,align: 'center'},
		{header: '模型类别',dataIndex: 'modelKind',id:'riskIdName',width: 200,renderer:riskIdName,align: 'left' },
		{header : '参数名称',dataIndex : 'paramName',width : 150},
		{header : '参数长度上限',dataIndex : 'paramLen',width : 120},
		{header : '参数值',dataIndex : 'paramValue',width : 120,
		 editor: new Ext.form.TextField({
				allowBlank: false,
				id:'paramValues',
				blankText: '请输入参数值',
//				maxLength: '2',
//				vtype: 'isOverMax',
				regex: /^(([1-9]\d*)|0)(\.\d{1,2})?$/,
				regexText: '只能输入整数或者最多保留2位的小数'
			})
		}
	]);

	var riskInfoColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '风控级别',dataIndex: 'riskLvl',align: 'center',width: 120},
		{id:'riskLvlName',header: '级别名称',dataIndex: 'resved',align: 'center',width: 200 }
	]);
	
	var update = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = detailGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			//存放要修改的监控模型
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					riskLvl: riskInfoGrid.getSelectionModel().getSelected().data.riskLvl,
					modelKind : record.get('modelKind'),
					modelSeq: record.get('modelSeq'),
					paramName: record.get('paramName'),
					paramLen: record.get('paramLen'),
					paramValue: record.get('paramValue')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T40201Action.asp?method=update',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40206',
					subTxnId: '04'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					detailGrid.enable();
					if(rspObj.success) {
						detailGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,detailGrid);
					} else {
						detailGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,detailGrid);
					}
					detailGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var detailGrid = new Ext.grid.EditorGridPanel({
		title: '关联风控模型',
		region: 'east',
		width: 750,
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [update],
		loadMask: {
			msg: '正在加载关联风控模型列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	//##########  以下为修改风险级别关联风控模型信息  ##########
	
	var editMenuLvl1Col = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
   		{id: 'saModelKind',header: '监控模型',dataIndex: 'saModelKind',align: 'center',width: 100},
   		{id: 'saModelKindName',header: '受控组别',dataIndex:'saModelKind',width: 160,renderer:riskIdName}
	]);
	
	var editMenuLvl2Col = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
   		{id: 'saModelKind',header: '监控模型',dataIndex: 'saModelKind',align: 'center',width: 100},
   		{id: 'saModelKindName',header: '受控组别',dataIndex:'saModelKind',width: 160,renderer:riskIdName},
   		{id: 'op',header: '删除',dataIndex:'op',width: 40,renderer: renderUpdateDelete}
	]);
    	function renderUpdateDelete(val) {
    		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
    		'title="删除此模型" onclick=deleteUpdateMenu(\''+ val + '\') onmouseover="this.style.cursor=\'pointer\'"/>';
    	}
    	window.deleteUpdateMenu = function(val) {
    		var rec = editSelectedMenuGrid.getStore().getById(val);
    		editSelectedMenuGrid.getStore().remove(rec);
    	};
    	var editMenuLvl1Grid = new Ext.grid.GridPanel({
    		store: menuLvl1Store,
			title: '<center>可选风控模型</center>',
    		frame: true,
    		border: true,
    		columnLines: true,
    		stripeRows: true,
	        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
    		height: 300,
    		width: 330,
    		cm: editMenuLvl1Col,
    		keys: [{
    			key: Ext.EventObject.ENTER,
    			handler: editSelectedMenu
    		}],
    		lastSelectedRowIndex: -1,
    		ddGroup: 'editMenu',
    		enableDragDrop: true,
			loadMask: {
				msg: '正在加载可选风控模型列表......'
			},
			bbar: new Ext.PagingToolbar({
				store: menuLvl1Store,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
    	});
    	editMenuLvl1Grid.getSelectionModel().on('rowselect',function() {
    		if(editMenuLvl1Grid.lastSelectedRowIndex != editMenuLvl1Grid.getSelectionModel().last) {
    			editMenuLvl1Grid.lastSelectedRowIndex = editMenuLvl1Grid.getSelectionModel().last;
    		}
    	});
    	
    	editMenuLvl1Grid.on('rowdblclick', editSelectedMenu);
    	
    	var SelectedMenuRecord = new Ext.data.Record.create([
	 		{name: 'saModelKind',type: 'string'},
	 		{name: 'op',type: 'string'}
     	]);
    	function editSelectedMenu() {
    		var rec = editMenuLvl1Grid.getSelectionModel().getSelected();
    		var itemIndex = editSelectedMenuGrid.getStore().findExact('saModelKind',rec.data.saModelKind);
    		if(itemIndex == -1) {
				var selectedRec = new SelectedMenuRecord();
				selectedRec.id = rec.get('saModelKind');
				selectedRec.set('saModelKind',rec.get('saModelKind'));
				selectedRec.set('saModelKindName',rec.get('saModelKind'));
				selectedRec.set('op',rec.get('saModelKind'));
				editSelectedMenuGrid.getStore().add(selectedRec);
				editSelectedMenuGrid.getStore().sort('saModelKind','asc');
    		}
    	}
    	var editSelectedMenuGrid = new Ext.grid.GridPanel({
    		store: editMenuLvl2Store,
			title: '<center>已选风控模型</center>',
    		frame: true,
    		border: true,
    		columnLines: true,
    		stripeRows: true,
    		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
    		height: 300,
    		width:370,
    		cm: editMenuLvl2Col
    	});
    	editSelectedMenuGrid.on('render',function() {
    		var selectedMenuDDEL = editSelectedMenuGrid.getView().el.dom;
    		var selectedMenuDDTarget = new Ext.dd.DropTarget(selectedMenuDDEL,{
    			ddGroup: 'editMenu',
    			notifyDrop: function(ddSource,e,data) {
    				function addRow(record,index,allItem) {
    					var itemIndex = editSelectedMenuGrid.getStore().findExact("saModelKind",record.data.saModelKind);
    					if(itemIndex == -1) {
							var selectedRec = new SelectedMenuRecord();
							selectedRec.id = rec.get('saModelKind');
							selectedRec.set('saModelKind',rec.get('saModelKind'));
							selectedRec.set('saModelKindName',rec.get('saModelKind'));
							selectedRec.set('op',rec.get('saModelKind'));
							editSelectedMenuGrid.getStore().add(selectedRec);
							editSelectedMenuGrid.getStore().sort('saModelKind','asc');
    					}
    				}
    				Ext.each(ddSource.dragData.selections,addRow);
    				return (true);
    			}
    		});
    	});
// 风险级别关联风控模型修改表单
		var updateForm = new Ext.form.FormPanel({
			frame: true,
			autoHeight: true,
			autoWidth: true,
			waitMsgTarget: true,
	        labelWidth: 120,
			items: [{
				xtype: 'displayfield',
				fieldLabel: '商户风险级别*',
				labelStyle: 'padding-left: 20%',
				allowBlank: false,
				id: 'riskLvlUpd',
				name: 'riskLvl',
				width: 250
			},{
				xtype: 'textfield',
				fieldLabel : '级别名称*',
				labelStyle: 'padding-left: 20%',
				allowBlank : false,
				blankText: '级别名称不能为空',
				emptyText : '请输入级别名称',
				id: 'resvedUpd',
				name: 'resved',
				maxLength: 50,
				width: 250
			},new Ext.Panel({
				layout: 'table',
				items: [editMenuLvl1Grid,editSelectedMenuGrid]
			})]
		});
		
		var updateWin = new Ext.Window({
			title: '<center>修改商户风险级别</center>',
			initHidden: true,
			header: true,
			frame: true,
			closable: false,
			modal: true,
			layout: 'fit',
			items: [updateForm],
			buttonAlign: 'center',
			closeAction: 'hide',
//			iconCls: 'logo',
			resizable: false,
			buttons: [{
				text: '确定',
				handler: function() {
					if(updateForm.getForm().isValid()) {
						if(editSelectedMenuGrid.getStore().getCount() == 0) {
							showMsg('您没有为该风控级别选择任何风控模型，请选择！',updateForm);
						} else {
							updateSubmit();
						}
					}
				}
			},{
				text: '重置',
				handler: updateFormReset
			},{
				text: '关闭',
				handler: function() {
					updateWin.hide(riskInfoGrid);
				}
			}]
		});
		
		updateWin.on('show',function(){
			var selectedRec = riskInfoGrid.getSelectionModel().getSelected();
			updateFormReset();
			updateWin.getEl().mask('正在加载菜单信息......');
			setTimeout(function() {
				updateWin.getEl().unmask();
			},600);
	    });
	    
		// 提交编辑风险级别关联风控模型信息表单
		function updateSubmit() {
			var menuArray = new Array();		
			for(var i = 0,n = editSelectedMenuGrid.getStore().getCount(); i < n; i++) {
				var rec = editSelectedMenuGrid.getStore().getAt(i);
				var data = {
					saModelKind: rec.get('saModelKind')
				};
				menuArray.push(data);
			}
			updateForm.getForm().submit({
				url: 'T40206Action.asp?method=update',
				waitMsg: '正在提交，请稍后......',
				success: function(form,action) {
					showSuccessMsg(action.result.msg,updateForm);
					//重新加载列表
					riskInfoGrid.getStore().reload();
					//关闭修改窗口
					updateWin.hide(riskInfoGrid);
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,updateForm);
				},
				params: {
					txnId: '40206',
					subTxnId: '02',
					riskLvl: riskInfoGrid.getSelectionModel().getSelected().get('riskLvl'),
					menuList: Ext.encode(menuArray)
				}
			});
		}
		
	    // 重置编辑风险级别关联风控模型信息表单
		function updateFormReset() {
			var selectedRec = riskInfoGrid.getSelectionModel().getSelected();
			updateForm.items.items[0].setValue(selectedRec.get('riskLvl'));
			updateForm.items.items[1].setValue(selectedRec.get('resved'));
			SelectOptionsDWR.getComboDataWithParameter('RISK_PARAM_INF',selectedRec.get('riskLvl'),function(ret) {
				var json = Ext.decode(ret);
				editMenuLvl2Store.removeAll();
				if(json.data[0].valueField == "") {
					return;
				}
				var recordCreator = new Ext.data.Record.create([
			 		{name: 'saModelKind',type: 'string'},
			 		{name: 'op',type: 'string'}
				]);
				for(var i = 0,n = json.data.length; i < n; i++) {
					var rec = new recordCreator();
					var value = json.data[i].valueField;;
					rec.id = value;
					rec.set('saModelKind',value);
					rec.set('saModelKindName',value);
					rec.set('op',value);
					editMenuLvl2Store.add(rec);
				}
			});
		}
		
	    
//##############################以下为添加风险级别关联风控模型信息####################################
	    
	var menuLvl1Col = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
   		{id: 'saModelKind',header: '监控模型',dataIndex: 'saModelKind',align: 'center',width: 100},
   		{id: 'saModelKindName',header: '受控组别',dataIndex:'saModelKind',width: 160,renderer:riskIdName}
	]);
	
	var menuLvl2Col = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
   		{id: 'saModelKind',header: '监控模型',dataIndex: 'saModelKind',align: 'center',width: 100},
   		{id: 'saModelKindName',header: '受控组别',dataIndex:'saModelKind',width: 160,renderer:riskIdName},
   		{id: 'op',header: '删除',dataIndex:'op',width: 40,renderer: renderDelete}
	]);

	function renderDelete(val) {
		return '<img src="' + Ext.contextPath + '/ext/resources/images/recycle.png" ' +
		'title="删除此模型" onclick=deleteMenu(\''+ val + '\') onmouseover="this.style.cursor=\'pointer\'"/>';
	}
	
	window.deleteMenu = function(val) {
		var rec = selectedMenuGrid.getStore().getById(val);
		selectedMenuGrid.getStore().remove(rec);
	};
	
	var menuLvl1Grid = new Ext.grid.GridPanel({
		store: menuLvl1Store,
		title: '<center>可选风控模型</center>',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 330,
		cm: menuLvl1Col,
		keys: [{
			key: Ext.EventObject.ENTER,
			handler: addSelectedMenu
		}],
		lastSelectedRowIndex: -1,
		ddGroup: 'menu',
		enableDragDrop: true,
		loadMask: {
			msg: '正在加载可选风控模型列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: menuLvl1Store,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	menuLvl1Grid.getSelectionModel().on('rowselect',function() {
		if(menuLvl1Grid.lastSelectedRowIndex != menuLvl1Grid.getSelectionModel().last) {
			menuLvl1Grid.lastSelectedRowIndex = menuLvl1Grid.getSelectionModel().last;
		}
	});
	
	menuLvl1Grid.on('rowdblclick', addSelectedMenu);
	
	var SelectedMenuRecord = new Ext.data.Record.create([
 		{name: 'saModelKind',type: 'string'},
 		{name: 'op',type: 'string'}
 	]);
 	
	function addSelectedMenu() {
		var rec = menuLvl1Grid.getSelectionModel().getSelected();
		var itemIndex = selectedMenuGrid.getStore().findExact('saModelKind',rec.data.saModelKind);
		if(itemIndex == -1) {
			var selectedRec = new SelectedMenuRecord();
			selectedRec.id = rec.get('saModelKind');
			selectedRec.set('saModelKind',rec.get('saModelKind'));
			selectedRec.set('saModelKindName',rec.get('saModelKind'));
			selectedRec.set('op',rec.get('saModelKind'));
			selectedMenuGrid.getStore().add(selectedRec);
			selectedMenuGrid.getStore().sort('saModelKind','asc');
		}
	}
	
	var selectedMenuGrid = new Ext.grid.GridPanel({
		store: menuLvl2Store,
		title: '<center>已选风控模型</center>',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		height: 300,
		width: 370,
		cm: menuLvl2Col	
	});
	
	selectedMenuGrid.on('render',function() {
		var selectedMenuDDEL = selectedMenuGrid.getView().el.dom;
		var selectedMenuDDTarget = new Ext.dd.DropTarget(selectedMenuDDEL,{
			ddGroup: 'menu',
			notifyDrop: function(ddSource,e,data) {
				function addRow(record,index,allItem) {
					var itemIndex = selectedMenuGrid.getStore().findExact('saModelKind',rec.data.saModelKind);
					if(itemIndex == -1) {
						var selectedRec = new SelectedMenuRecord();
						selectedRec.id = rec.get('saModelKind');
						selectedRec.set('saModelKind',rec.get('saModelKind'));
						selectedRec.set('saModelKindName',rec.get('saModelKind'));
						selectedRec.set('op',rec.get('saModelKind'));
						selectedMenuGrid.getStore().add(selectedRec);
						selectedMenuGrid.getStore().sort('saModelKind','asc');
					}
				}
				Ext.each(ddSource.dragData.selections,addRow);
				return (true);
			}
		});
	});
	
	
	// 风险级别关联风控模型添加表单
	var addForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		autoWidth: true,
		waitMsgTarget: true,
        labelWidth: 120,
		items: [{
			xtype: 'textfield',
			fieldLabel: '商户风险级别*',
			labelStyle: 'padding-left: 20%',
			allowBlank: false,
			blankText: '商户风险级别不能为空',
			emptyText: '请输入商户风险级别',
			id: 'riskLvl',
			name: 'riskLvl',
			vtype:'isNumber',
			maxLength: 1,
			width: 250
		},{
			xtype: 'textfield',
			fieldLabel : '级别名称*',
			labelStyle: 'padding-left: 20%',
			allowBlank : false,
			blankText: '级别名称不能为空',
			emptyText : '请输入级别名称',
			id: 'resved',
			name: 'resved',
			maxLength: 50,
			width: 250
		},new Ext.Panel({
			layout: 'table',
			items: [menuLvl1Grid,selectedMenuGrid]
		})]
	});
	
	var addWin = new Ext.Window({
		title: '<center>新增商户风险级别</center>',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		layout: 'fit',
		items: [addForm],
		buttonAlign: 'center',
		closeAction: 'hide',
//		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(addForm.getForm().isValid()) {
					if(selectedMenuGrid.getStore().getCount() == 0) {
						showMsg('您没有为该风控级别选择任何风控模型，请选择！',addForm);
					} else {
						addSubmit();
					}
				}
			}
		},{
			text: '重置',
			handler: addFormReset
		
		},{
			text: '关闭',
			handler: function() {
				addWin.hide(riskInfoGrid);
			}
		}]
	});
	// 提交新增表单
	function addSubmit() {
		var menuArray = new Array();		
		for(var i = 0,n = selectedMenuGrid.getStore().getCount(); i < n; i++) {
			var rec = selectedMenuGrid.getStore().getAt(i);
			var data = {
				saModelKind: rec.get('saModelKind')
			};
			menuArray.push(data);
		}
		addForm.getForm().submit({
			url: 'T40206Action.asp?method=add',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,addForm);
				//重新加载列表
				riskInfoGrid.getStore().reload();
				// 重置表单
				addFormReset();
				//关闭新增窗口
				addWin.hide(riskInfoGrid);
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,addForm);
			},
			params: {
				txnId: '40206',
				subTxnId: '01',
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 重置表单
	function addFormReset() {
		addForm.getForm().reset();
		selectedMenuGrid.getStore().removeAll();
	}
//#############################      主页面      ##############################
	
	var riskInfoGrid = new Ext.grid.GridPanel({
		title: '风险级别管理',
		region:'center',
		autoExpandColumn:'riskLvlName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: riskInfoGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskInfoColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '新增',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 80,
			handler:function() {
				addWin.show();
				addWin.center();
			}
		 },'-',{
		    xtype: 'button',
			text: '修改',
			name: 'edit',
			id: 'edit',
			iconCls: 'edit',
			width: 80,
			handler:function() {
				updateWin.show();
				updateWin.center();
			}
		 },'-', {
			xtype: 'button',
			text: '删除',
			name: 'delete',
			id: 'delete',
			iconCls: 'delete',
			width: 80,
			handler:function() {
				showConfirm('风控级别及其关联风控模型将被删除且不可恢复,确定删除吗？',riskInfoGrid,function(bt) {
					if(bt == 'yes') {
						Ext.Ajax.request({
							url: 'T40206Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,riskInfoGrid);
									riskInfoGrid.getStore().reload();				
									detailGrid.getStore().removeAll();
								} else {
									showErrorMsg(rspObj.msg,riskInfoGrid);
								}
								// 重新加载商户信息
								riskInfoGrid.getStore().reload();
							},
							params: {
								txnId: '40206',
								subTxnId: '03',
								riskLvl: riskInfoGrid.getSelectionModel().getSelected().get('riskLvl')
							}
							
						});
					}
				});
			}
		}],
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: riskInfoGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	
	riskInfoGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(riskInfoGrid.getView().getRow(riskInfoGrid.getSelectionModel().last)).frame();
			var rec = riskInfoGrid.getSelectionModel().getSelected();
			if(rec.get('riskLvl')=='4'){
				riskInfoGrid.getTopToolbar().items.items[2].enable();
				riskInfoGrid.getTopToolbar().items.items[4].disable();
			}else{
				riskInfoGrid.getTopToolbar().items.items[2].enable();
				riskInfoGrid.getTopToolbar().items.items[4].enable();
			}
			detailGrid.getTopToolbar().items.items[0].disable();
			detailGridStore.load({
				params: {
					start: 0,
					riskLvl: riskInfoGrid.getSelectionModel().getSelected().data.riskLvl
				}
			})
		}
	});
	
	
	
	detailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last)).frame();
			if(detailGrid.getSelectionModel().getSelected().data.modelKind=='A10'){
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0\d)$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者00-23';
			}else{
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0)(\.\d{1,2})?$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者最多保留2位的小数';
			}
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			riskLvl: riskInfoGrid.getSelectionModel().getSelected().data.riskLvl
		});
	});

	
	riskInfoGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
		detailGridStore.removeAll();
		riskInfoGrid.getTopToolbar().items.items[2].disable();
		riskInfoGrid.getTopToolbar().items.items[4].disable();
		
	});
	
	menuLvl1Store.on('beforeload', function() {
		menuLvl1Store.removeAll();
		Ext.apply(this.baseParams, {
			start: 0
		});
	});
	

	detailGrid.on({'afteredit': function(e) {
			detailGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [riskInfoGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});