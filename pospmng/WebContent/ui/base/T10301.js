Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});

Ext.onReady(function() {
	
	// 权限级别
	var roleLevelStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	
	// 权限级别
	SelectOptionsDWR.getComboData('BRH_LVL',function(ret){
		roleLevelStore.loadData(Ext.decode(ret));
	});
	//角色数据集
	var roleGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=roleInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'roleId'
		},[
			{name: 'roleId',mapping: 'roleId'},
			{name: 'roleName',mapping: 'roleName'},
			{name: 'roleType',mapping: 'roleType'},
			{name: 'description',mapping: 'description'},
			{name: 'recUpdOpr',mapping: 'recUpdOpr'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'recUpdTs',mapping: 'recUpdTs'}
		])
	});
	
	roleGridStore.load({
		params:{start: 0}
	});
	//角色列表数据显示模板
	var roleColModel = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
		{id: 'roleId',header: '角色编号',dataIndex: 'roleId',hidden: true},
		{header: '角色名称',dataIndex: 'roleName',
		 editor: new Ext.form.TextField({
		 	allowBlank: false,
			blankText: '角色名称不能为空',
			maxLength: 64,
			vtype: 'isOverMax'
		 })},
		{header: '角色级别',dataIndex: 'roleType',renderer:roleLevel},
		{header: '角色描述',dataIndex: 'description',width: 357,id:'description',
		 editor: new Ext.form.TextField({
		 	allowBlank: true,
			maxLength: 1024,
			vtype: 'isOverMax'
		 })},
		{header: '最后更新操作员',dataIndex: 'recUpdOpr'},
		{header: '创建时间',dataIndex: 'recCrtTs',renderer:formatTs,width: 145},
		{header: '最后更新时间',dataIndex: 'recUpdTs',renderer:formatTs,width: 145}
	]);
	
	/**
	 * 角色级别
	 */
	function roleLevel(val) {
		switch(val) {
			case '0' : return '钱宝金融';
			case '1' : return '商户代理';
		}
	}
	
	//勾选子节点时，同步处理父节点的勾选状态；勾选父节点时，同步处理子节点的勾选状态
	function checkMenu(node,checked){
		var parent = node.parentNode;
		while(parent !=null && parent.attributes.checked && typeof parent.attributes.checked != 'undefined' && checked != parent.attributes.checked && checked == false){
			parent.attributes.checked = checked;
			parent.ui.checkbox.checked = checked; 
			parent = parent.parentNode;
		}
		node.expand();  
		node.attributes.checked = checked;  
		node.eachChild(function(child) {  
			child.ui.toggleCheck(checked);  
			child.attributes.checked = checked;  
			child.fireEvent('checkchange', child, checked);  
		}); 
	}
	//获取指定根节点下的所有已选择的叶子节点
	function getCheckedMenuArray(root){
		var menuArray = new Array();
		var nodeStack = new Array();
		if(!root){	//根节点为空
			return menuArray;
		} else if(root.attributes.leaf==true){	//根节点为叶子节点
			if(root.atttibutes.checked == true){
				var data = {
						valueId: root.attributes.id
					};
				menuArray.push(data);
			}
			return menuArray;
		} else {	//将root节点的所有子节点压栈
			root.eachChild(function(child) {  
				nodeStack.push(child);
			});
		}
		//遍历根节点下的所有节点
		while(nodeStack.length > 0){
			var currNode = nodeStack.pop();
			if(currNode.attributes.leaf == true){	//当前节点为叶子节点
				if(currNode.attributes.checked == true){
					var data = {
							valueId: currNode.attributes.id
						};
					menuArray.push(data);
				}
			} else {	//当前节点为非叶子节点
				currNode.eachChild(function(child) {  
					nodeStack.push(child);
				});
			}
		}
		return menuArray;
	}
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
			roleWin.show();
			roleWin.center();
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler: function() {
			if(roleGrid.getSelectionModel().hasSelection()) {
				var rec = roleGrid.getSelectionModel().getSelected();
				
				showConfirm('确定要删除该条角色信息吗？',roleGrid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T10301Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,roleGrid);
									roleGrid.getStore().reload();
									roleGrid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,roleGrid);
								}
							},
							params: { 
								roleId: rec.get('roleId'),
								txnId: '10301',
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
			var modifiedRecords = roleGrid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			//存放要修改的机构信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					roleId : record.get('roleId'),
					roleName : record.get('roleName'),
					roleType: record.get('roleType'),
					description: record.get('description'),
					recCrtTs: record.get('recCrtTs')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T10301Action.asp?method=update',
				method: 'post',
				params: {
					roleInfoList: Ext.encode(array),
					txnId: '10301',
					subTxnId: '03'
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					roleGrid.enable();
					if(rspObj.success) {
						roleGrid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,roleGrid);
					} else {
						roleGrid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,roleGrid);
					}
					roleGrid.getTopToolbar().items.items[4].disable();
					roleGrid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var edit = {
		text: '编辑角色',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler: function() {
			editRoleWin.show();
			editRoleWin.center();
			setTimeout(resetSelectedMenu,500);
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
	menuArr.push('-');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('-');
	menuArr.push(edit);
	
	// 角色列表显示面板
	var roleGrid = new Ext.grid.EditorGridPanel({
		title: '角色信息',
		iconCls: 'T103',
		region:'center',
		frame: true,
		border: true,
		autoExpandColumn:'description',
		columnLines: true,
		stripeRows: true,
		store: roleGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: roleColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载角色信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: roleGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	roleGrid.getStore().on('beforeload',function() {
		roleGrid.getTopToolbar().items.items[4].disable();
		roleGrid.getStore().rejectChanges();
	});
	
	roleGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(roleGrid.getTopToolbar().items.items[4] != undefined) {
				roleGrid.getTopToolbar().items.items[4].enable();
			}
		}
	});
	
	roleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(roleGrid.getView().getRow(roleGrid.getSelectionModel().last)).frame();
			//激活菜单
			roleGrid.getTopToolbar().items.items[2].enable();
			roleGrid.getTopToolbar().items.items[6].enable();
			resetSelectedMenu;
		}
	});
	
	var treeloader=new Ext.tree.TreeLoader({
		dataUrl:'T10301Action.asp?method=allmenu',
		
	});
	/******************************************************角色添加信息********************************************************/
	var addRoleFunctree = new Ext.tree.TreePanel({
		region: 'center',
		title:'请为该角色选择权限',
		useArrows: true,
		autoScroll: true,
		animate: true,
		containerScroll: true,
		width: 310,
		split: true,
		collapsible: true,
		rootVisible: false,
		loader:treeloader,
		listeners:{
			'checkchange':checkMenu
		}
	});
	// set the root node
	var addMenuRoot = new Ext.tree.AsyncTreeNode({
		text: '系统菜单',
		draggable:false,
		id:'source'
	});
	addRoleFunctree.setRootNode(addMenuRoot);
	// 角色添加表单
	var roleInfoForm = new Ext.form.FormPanel({
		frame: true,
		autoScroll : true,
		autoHeight: false,
		height:400,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '角色名称*',
			allowBlank: false,
			blankText: '角色名称不能为空',
			emptyText: '请输入角色名称',
			id: 'roleName',
			name: 'roleName',
			width: 200,
			maxLength: 64,
			vtype: 'isOverMax'
		},{
			xtype: 'combo',
			store: roleLevelStore,
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'roleType',
			id: 'roleTypeLvl1',
			emptyText: '请选择角色级别',
			width: 200,
			fieldLabel: '角色级别*',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			allowBlank: false,
			blankText: '请选择一个角色级别',
			value: '0'
		},{
			fieldLabel: '角色描述',
			maxLength: 1024,
			xtype: 'textarea',
			id: 'description',
			name: 'description',
			vtype: 'isOverMax',
			width: 200
		},
		addRoleFunctree
		]
	});
	
	// 角色添加窗口
	var roleWin = new Ext.Window({
		title: '角色添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [roleInfoForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {			
				if(roleInfoForm.getForm().isValid()) {
					if(getCheckedMenuArray(addRoleFunctree.root).length == 0) {
						showConfirm('您没有为该角色选择任何菜单信息，确定要提交吗？',roleInfoForm,function(bt) {
							if(bt == 'yes') {
								addSubmit();
							}
						})
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
				roleWin.hide(roleGrid);
			}
		}]
	});
	
	// 添加角色提交表单
	function addSubmit() {
		var menuArray = getCheckedMenuArray(addRoleFunctree.root);
		roleInfoForm.getForm().submitNeedAuthorise({
			url: 'T10301Action.asp?method=add',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,roleInfoForm);
				//重置表单
				roleInfoForm.getForm().reset();
				//重新加载列表
				roleGrid.getStore().reload();
				// 重置表单
				addFormReset();
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,roleInfoForm);
			},
			params: {
				txnId: '10301',
				subTxnId: '01',
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 重置表单
	function addFormReset() {
		roleInfoForm.getForm().reset();
		checkMenu(addRoleFunctree.root,false);
	}
	
	
	/******************************************************编辑菜单信息********************************************************/
	
	//var editRoleFuncTree = Ext.tree;
	var editRoleFuncTree = new Ext.tree.TreePanel({
		region: 'center',
		title:'请为该角色选择权限',
		useArrows: true,
		autoScroll: true,
		animate: true,
		containerScroll: true,
		width: 310,
		split: true,
		collapsible: true,
		rootVisible: false,
		loader: new Ext.tree.TreeLoader({
			dataUrl:'T10301Action.asp?method=allmenu'
		}),
		listeners:{
			'checkchange':checkMenu
		}
	});
	
	var editMenuRoot = new Ext.tree.AsyncTreeNode({
		text: '系统菜单',
		draggable:false,
		id:'source'
	});
	editRoleFuncTree.setRootNode(editMenuRoot);
	
	// 编辑菜单表单
	var editMenuForm = new Ext.form.FormPanel({
		frame: true,
		autoScroll : true,
		autoHeight: false,
		height:400,
		width: 300,
		defaultType: 'textfield',
		labelWidth: 80,
		waitMsgTarget: true,
		items: [{
			fieldLabel: '角色名称',
			width: '200',
			id:'roleNameUpd',
			readOnly: true
		},{
			fieldLabel: '角色级别',
			id: 'roleTypeLvl',
			width: '200',
			readOnly: true
		},{
			fieldLabel: '角色描述',
			xtype: 'textarea',
			width: '200',
			id:'roleDescUpd',
			readOnly: true
		},
		editRoleFuncTree
		]
	});
	
	// 角色编辑窗口
	var editRoleWin = new Ext.Window({
		title: '角色维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 350,
		autoHeight: true,
		layout: 'fit',
		items: [editMenuForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(getCheckedMenuArray(editRoleFuncTree.root).length == 0) {
					showConfirm('您没有为该角色选择任何菜单信息，确定要提交吗？',editSelectedMenuGrid,function(bt) {
						if(bt == 'yes') {
							editSubmit();
						}
					})
				} else {
					editSubmit();
				}
			}
		},{
			text: '重置',
			handler: resetSelectedMenu
		},{
			text: '关闭',
			handler: function() {
				editRoleWin.hide(roleGrid);
			}
		}]
	});

	editRoleWin.on('show',function(){
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		if(selectedRec.get('roleType') == '0') {
			editMenuForm.items.items[1].setValue('钱宝金融');
		} else if(selectedRec.get('roleType') == '1') {
			editMenuForm.items.items[1].setValue('商户代理');
		}
		editMenuForm.items.items[0].setValue(selectedRec.get('roleName'));
		
		editMenuForm.items.items[2].setValue(selectedRec.get('description'));
		
		resetSelectedMenu();
		editRoleWin.getEl().mask('正在加载菜单信息......');
		setTimeout(function() {
		    editRoleWin.getEl().unmask();
		},600);
    });
      
	// 	编辑提交
	function editSubmit() {
		var menuArray = getCheckedMenuArray(editRoleFuncTree.root);
		editMenuForm.getForm().submit({
			url: 'T10301Action.asp?method=edit',
			waitMsg: '正在提交，请稍后......',
			success: function(form,action) {
				showSuccessMsg(action.result.msg,editMenuForm);
				editRoleWin.hide(roleGrid);
			},
			failure: function(form,action) {
				showErrorMsg(action.result.msg,editMenuForm);
			},
			params: {
				txnId: '10301',
				subTxnId: '04',
				roleId: roleGrid.getSelectionModel().getSelected().get('roleId'),
				menuList: Ext.encode(menuArray)
			}
		});
	}
	
	// 初始化已选菜单
	function resetSelectedMenu() {
		var selectedRec = roleGrid.getSelectionModel().getSelected();
		SelectOptionsDWR.getComboDataWithParameter('ROLE_MENU',selectedRec.get('roleId'),function(ret) {
			var json = Ext.decode(ret);
			var root = editRoleFuncTree.root;
			checkMenu(root,false);
			if(json.data[0].valueField == "") {
				return;
			}
			var nodeStack = new Array();
			if(!root){	//根节点为空
				return ;
			} else if(root.attributes.leaf==true){	//根节点为叶子节点
				for(var i = 0,n = json.data.length; i < n; i++) {
					if(root.attributes.id == json.data[i].valueField){
						root.ui.toggleCheck(true);  
						root.attributes.checked = true;  
					}
				}
			} else {	//将root节点的所有子节点压栈
				root.eachChild(function(child) {  
					nodeStack.push(child);
				});
			}
			//遍历根节点下的所有节点
			while(nodeStack.length > 0){
				var currNode = nodeStack.pop();
				if(currNode.attributes.leaf == true){	//当前节点为叶子节点
					for(var i = 0,n = json.data.length; i < n; i++) {
						if(currNode.attributes.id == json.data[i].valueField){
							currNode.ui.toggleCheck(true);  
							currNode.attributes.checked = true;  
						}
					}
				} else {	//当前节点为非叶子节点
					currNode.eachChild(function(child) {  
						nodeStack.push(child);
					});
				}
			}
		});
	}
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [roleGrid],
		renderTo: Ext.getBody()
	});
});



