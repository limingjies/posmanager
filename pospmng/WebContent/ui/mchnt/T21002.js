Ext.onReady(function() {
	
	
	var mchtNoStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_NO',function(ret){
		mchtNoStore.loadData(Ext.decode(ret));
	});
	
	var oprGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=oprInfoTl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'oprId'
		},[
			{name: 'oprId',mapping: 'oprId'},
			{name: 'mchtBrhFlag',mapping: 'mchtBrhFlag'},
			{name: 'oprName',mapping: 'oprName'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'roleId',mapping: 'roleId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'brhIdName',mapping: 'brhIdName'},
			{name: 'oprRoleName',mapping: 'oprRoleName'},
			{name: 'oprStatus',mapping: 'oprStatus'},
			{name: 'oprSex',mapping: 'oprSex'},
			{name: 'cerType',mapping: 'cerType'},
			{name: 'cerNo',mapping: 'cerNo'},
			{name: 'contactPhone',mapping: 'contactPhone'},
			{name: 'email',mapping: 'email'},
			{name: 'pwdOutDate',mapping: 'pwdOutDate'},
			{name: 'mchtNoName',mapping: 'mchtNoName'}
		])
	});
	
	oprGridStore.load({
		params:{start: 0}
	});
	
	
	
	
	var oprColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		
		{id: 'oprId',header: '操作员编号',dataIndex: 'oprId',align: 'left'},
		{header: '所属方式',dataIndex: 'mchtBrhFlag',renderer:mchntBrhState,width: 100,align: 'left'},
		{header: '操作员名称',dataIndex: 'oprName',width: 120,align: 'left'},
		{header: '所属商户',dataIndex: 'mchtNoName',width: 250,id:'mchtNoName'},
		{header: '合作伙伴',dataIndex: 'brhIdName',width: 250,align: 'left'},
		{header: '角色',dataIndex: 'oprRoleName',id:'oprRoleName',align: 'left'},
//		{header: '性别',dataIndex: 'oprSex',align: 'center',renderer: gender},
//		{header: '邮箱',dataIndex: 'email',width: 130,align: 'center'},
//		{header: '联系电话',dataIndex: 'contactPhone',align: 'center'},
//		{header: '密码有效期',dataIndex: 'pwdOutDate',renderer: formatDt,align: 'center'},
		{header: '状态',dataIndex: 'oprStatus',renderer: oprState_new,align: 'center'}
		
	]);
	
	
	// 菜单集合
	var menuArr = new Array();
		var queryCondition = {
			text: '录入查询条件',
			width: 85,
			id: 'query',
			iconCls: 'query',
			handler:function() {
				queryWin.show();
			}
		};
	var addMenu = {
		text: '新增操作员',
		width: 85,
		iconCls: 'add',
		id:'add',
		handler:function() {
		addOprWin.show();	
		addOprForm.getForm().reset();
		Ext.getCmp('addBrhId').getEl().up('.x-form-item').setDisplayed(false);
		Ext.getCmp('addMchtNo').setValue('');

		}
	};
	var updMenu = {
		text: '修改操作员',
		width: 85,
		disabled:true,
		iconCls: 'edit',
		id:'update',
		handler:function() {
			var selectedRecord = oprGrid.getSelectionModel().getSelected();
				/** modify by xiaojian.yu 当没有选择记录时，js报错bug修复 20101116 start* */
				if(null!=selectedRecord){
					if(selectedRecord.get("oprStatus")=='1' ){
						showAlertMsg('该操作员已经注销，不能再修改!', oprGrid, function(bt) {return false;});
						return false;
					}
					updOprForm.getForm().loadRecord(selectedRecord);
					updOprWin.show();
				}else{
					showAlertMsg('请先选择记录!', oprGrid, function(bt) {return false;});
				}
				if(selectedRecord.get("mchtBrhFlag")=='0' ){
					Ext.getCmp('brhId').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('mchtNo').getEl().up('.x-form-item').setDisplayed(true);
				}else{
					Ext.getCmp('mchtNo').getEl().up('.x-form-item').setDisplayed(false);
					Ext.getCmp('brhId').getEl().up('.x-form-item').setDisplayed(true);
				}
				
			
		}
	};
	var delMenu = {
		text: '注销',
		width: 85,
		iconCls: 'delete',
		disabled:true,
		id:'delete',
		handler:function() {
			
			var rec = oprGrid.getSelectionModel().getSelected();

				if(null==rec){
					showAlertMsg('请先选择记录!', oprGrid, function(bt) {return false;});
					return false;
				}
				else{
					if(rec.get("oprStatus")=='1'){
						showAlertMsg('该操作员已经注销!', oprGrid, function(bt) {return false;});
						return false;
					}
				}
			showConfirm('确认注销用户['+oprGrid.getSelectionModel().getSelected().get('oprId')+']？',oprGrid,function(bt) {
					if(bt == 'yes') {
			
					
						Ext.Ajax.request({
			                url: 'T21002Action.asp?method=delete',
			                waitMsg: '正在注销，请稍后......',
			                success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
								// 重新加载信息
								oprGrid.getStore().reload();
							},
			                params: {
			                    txnId: 'T21002',
								subTxnId: '03' ,
								oprId:rec.get("oprId"),
								mchtNo:rec.get("mchtNo"),
								brhId:rec.get("brhId")
			                }
			            });
//			            hideProcessMsg();
					}
			});
		}
	};
	var resMenu = {
		text: '解锁并重置',
		width: 85,
		iconCls: 'reset',
		disabled:true,
		id:'reset',
		handler:function() {
					var rec = oprGrid.getSelectionModel().getSelected();

				if(null==rec){
					showAlertMsg('请先选择记录!', oprGrid, function(bt) {return false;});
					return false;
				}
				else{
					if(rec.get("oprStatus")=='1'){
						showAlertMsg('该操作员已经注销!', oprGrid, function(bt) {return false;});
						return false;
					}
				}
			showConfirm('确认用户['+oprGrid.getSelectionModel().getSelected().get('oprId')+']解锁并重置？',oprGrid,function(bt) {
					if(bt == 'yes') {
			
					
						Ext.Ajax.request({
			                url: 'T21002Action.asp?method=reset',
			                waitMsg: '正在解锁并重置，请稍后......',
			                success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,oprGrid);
								} else {
									showErrorMsg(rspObj.msg,oprGrid);
								}
								// 重新加载信息
								oprGrid.getStore().reload();
							},
			                params: {
			                    txnId: 'T21002',
								subTxnId: '04' ,
								oprId:rec.get("oprId"),
								mchtNo:rec.get("mchtNo"),
								brhId:rec.get("brhId")
			                }
			            });
//			            hideProcessMsg();
					}
			});	
		}
	};
		menuArr.push(queryCondition);  //[0]
		menuArr.push('-');			// [1]
		menuArr.push(addMenu);		// [2]
		menuArr.push('-');			// [3]
		menuArr.push(updMenu);		// [4]	
		menuArr.push('-');			// [5]
		menuArr.push(delMenu);		// [6]
		menuArr.push('-');			// [7]
		menuArr.push(resMenu);		// [8]
		
	
	// 操作员信息列表
	var oprGrid =  new Ext.grid.GridPanel({
		//title: '操作员信息查询',
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'mchtNoName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: oprGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
//		clicksToEdit: true,
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
		renderTo: Ext.getBody(),
		listeners:{
			'cellclick':selectableCell,
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
			//激活菜单
			oprGrid.getTopToolbar().items.items[4].enable();
			oprGrid.getTopToolbar().items.items[6].enable();
			oprGrid.getTopToolbar().items.items[8].enable();
//			var rec = oprGrid.getSelectionModel().getSelected();
//			if(rec.get('oprSta') == '1') {
//				oprGrid.getTopToolbar().items.items[8].enable();
//			} else {
//				oprGrid.getTopToolbar().items.items[2].disable();
//			}
		}
	});
	
/***************************查询条件*************************/

	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 370,
		labelWidth: 80,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			width: 200,
			id: 'queryOprId',
			name: 'queryOprId',
			//vtype: 'alphanum',
			fieldLabel: '操作员编号'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['1','注销'],['2','初始化']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 200,
			id: 'queryOprStatus',
			name: 'queryOprStatus',
			//vtype: 'alphanum',
			fieldLabel: '状态'
		},{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','商户'],['1','代理']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 200,
			id: 'queryMchtBrhFlag',
			name: 'queryMchtBrhFlag',
			//vtype: 'alphanum',
			fieldLabel: '所属方式'
		},{
			xtype: 'textfield',
			width: 200,
			id: 'queryOprName',
			name: 'queryOprName',
			//vtype: 'alphanum',
			fieldLabel: '操作员名称'
		},{
//			xtype: 'combo',
//			store: mchtNoStore,
			id:'queryMchtNo',
			fieldLabel: '商户号',
			xtype : 'dynamicCombo',
			methodName : 'getMchntIdAll',
			id: 'queryMchtNo',
			lazyRender: true,
			width: 300
		}
		,{
//			xtype: 'basecomboselect',
//			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '合作伙伴',
			xtype : 'dynamicCombo',
			methodName : 'getBrhId',
			id: 'queryBrhId',
			lazyRender: true,
			width: 300
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 420,
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
//			oprGrid.getTopToolbar().items.items[4].disable();
			queryWin.hide();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	var addOprForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'radiogroup',
			fieldLabel: '所属标志*',
			width:250,
//			labelWidth: 90,
//			labelStyle: 'padding-left: 20px',
			allowBlank: false,
			id:'addMchtBrhFlagId',
			items: [{
				boxLabel : '商户',
				inputValue : '0',
				name : "shTblOprInfo.mchtBrhFlag",
				checked : true,
				listeners : {
					'check' : function(checkbox, checked) {
								var txtLabel =Ext.getCmp('addMchtBrhFlagId').getEl().parent().parent().first(); 
							if (!checked) {
								Ext.getCmp('addBrhId').getEl().up('.x-form-item').setDisplayed(true);
								Ext.getCmp('addBrhId').setValue('');
								Ext.getCmp('addMchtNo').getEl().up('.x-form-item').setDisplayed(false);
								Ext.getCmp('addMchtNo').setValue('-');
							}else{
								Ext.getCmp('addMchtNo').getEl().up('.x-form-item').setDisplayed(true);
								Ext.getCmp('addMchtNo').setValue('');
								Ext.getCmp('addBrhId').getEl().up('.x-form-item').setDisplayed(false);
								Ext.getCmp('addBrhId').setValue('-');
							}
					}		
				}
			}, {
				boxLabel : '代理',
				inputValue : '1',
				name : "shTblOprInfo.mchtBrhFlag"
				
			}]
		},{
				xtype: 'textfield',
				id: 'addOprId',
				name:'shTblOprInfo.id.oprId',
				width: 250,
				fieldLabel: '操作员编号*',
				allowBlank: false,
				maxLength: 11,
				blankText: '请输入用户编号'
		}
		,{
			xtype: 'combo',
			store: mchtNoStore,
			hiddenName: 'shTblOprInfo.id.mchtNo',
			id:'addMchtNo',
			allowBlank: false,
			fieldLabel: '所属商户号*',
			width: 250,
//			 editable: true,
			value:'-',
				listeners : {  
		            'beforequery':function(e){  
		                   
		                var combo = e.combo;    
		                if(!e.forceAll){    
		                    var input = e.query;    
		                    // 检索的正则  
		                    var regExp = new RegExp(".*" + input + ".*");  
		                    // 执行检索  
		                    combo.store.filterBy(function(record,id){    
		                        // 得到每个record的项目名称值  
		                        var text = record.get(combo.displayField);    
		                        return regExp.test(text);   
		                    });  
		                    combo.expand();    
		                    return false;  
		                }  
		            }  
		        }
		},{
			fieldLabel: '合作伙伴*',
			xtype: 'basecomboselect',
			id: 'addBrhId',
			allowBlank: false,
			baseParams: 'BRH_BELOW_BRANCH',
			hiddenName: 'shTblOprInfo.id.BrhId',
			width: 250,
			 editable: true,
			value:'-',
			listeners : {  
	            'beforequery':function(e){  
	                   
	                var combo = e.combo;    
	                if(!e.forceAll){    
	                    var input = e.query;    
	                    // 检索的正则  
	                    var regExp = new RegExp(".*" + input + ".*");  
	                    // 执行检索  
	                    combo.store.filterBy(function(record,id){    
	                        // 得到每个record的项目名称值  
	                        var text = record.get(combo.displayField);    
	                        return regExp.test(text);   
	                    });  
	                    combo.expand();    
	                    return false;  
	                }  
	            }  
	        }
		}
		,{
				xtype: 'textfield',
				id: 'addOprName',
				name :'shTblOprInfo.oprName',
				allowBlank: false,
				maxLength: 10,
				width: 250,
				fieldLabel: '操作员名称*'
				
		}]
	});
	
	var updOprForm = new Ext.form.FormPanel({
		frame: true,
		autoHeight: true,
		width: 500,
		defaultType: 'textfield',
		labelWidth: 100,
		waitMsgTarget: true,
		items: [{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','商户'],['1','代理']],
				reader: new Ext.data.ArrayReader()
			}),
			fieldLabel: '所属标志',
			width: 198,
			id: 'mchtBrhFlag',
			readOnly:true,
			hiddenName: 'shTblOprInfoUpd.mchtBrhFlag'
		},{
				xtype: 'textfield',
				id: 'oprId',
				name:'shTblOprInfoUpd.id.oprId',
				width: 180,
				fieldLabel: '操作员编号',
				allowBlank: false,
//				disabled:true,
				readOnly:true,
				maxLength: 11,
				blankText: '请输入用户编号'
		},
		{
			
			
//				xtype: 'textfield',
//				id: 'mchtNo',
//				name:'shTblOprInfoUpd.id.mchtNo',
//				width: 180,
//				fieldLabel: '所属商户号*',
//				allowBlank: false,
////				disabled:true,
//				readOnly:true
				
				
				xtype: 'combo',
				store: mchtNoStore,
				displayField: 'displayField',
				valueField: 'valueField',
				id:'mchtNo',
				hiddenName:'shTblOprInfoUpd.id.mchtNo',
				fieldLabel: '所属商户号',
				typeAhead: true,
				selectOnFocus: true,
				readOnly:true,
				width: 198
		},{
			fieldLabel: '合作伙伴',
			xtype: 'basecomboselect',
			id: 'brhId',
			baseParams: 'BRH_BELOW_BRANCH',
			hiddenName: 'shTblOprInfoUpd.id.brhId',
			width: 198,
			readOnly:true
		},
			{
				xtype: 'textfield',
				id: 'oprName',
				name :'shTblOprInfoUpd.oprName',
				allowBlank: false,
				maxLength: 10,
				width: 180,
				fieldLabel: '操作员名称*',
				blankText: '请输入用户名称'
		}]
	});
	
	// 添加窗口
	var addOprWin = new Ext.Window({
		title: '操作员新增',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 400,
		autoHeight: true,
		shadow : false,
		layout: 'fit',
		items: [addOprForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'add',
		closable: true,
		resizable: false,
		//animateTarget: 'add',
		buttons: [{
			text: '确定',
			handler: function() {
				if(addOprForm.getForm().isValid()) {
					
					addOprForm.getForm().submit({
						url: 'T21002Action.asp?method=add',
						waitMsg: '正在添加用户信息，请稍后......',
						params: {
							txnId: 'T21002',
							subTxnId: '01'
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,addOprForm);
							addOprForm.getForm().reset();//modify by xiaojian.yu 保存成功后记录重置 20101116
							addOprWin.hide(oprGrid);
							oprGrid.getStore().reload();
//							oprGrid.getTopToolbar().items.items[4].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,addOprForm);
						}
					});
					
					
					
				}
			}
		},{
			text: '重置',
			handler: function() {
				addOprForm.getForm().reset();
				Ext.getCmp('addMchtNo').setValue('');
			}
		}
//		,{
//			text: '关闭',
//			handler: function() {
//				addOprForm.getForm().reset();
//				Ext.getCmp('addMchtNo').setValue('');
//				addOprWin.hide(oprGrid);
//			
//			}
//		}
		]
	});
	
		// 修改窗口
	var updOprWin = new Ext.Window({
		title: '操作员修改',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 400,
		autoHeight: true,
		shadow : false,
		layout: 'fit',
		items: [updOprForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'edit',
		closable: true,
		resizable: false,
		//animateTarget: 'update',
		buttons: [{
			text: '确定',
			handler: function() {
//				var brh = Ext.getCmp('mchtBrhFlag').getValue()
//				alert(Ext.getCmp('mchtBrhFlag').getValue());
				if(updOprForm.getForm().isValid()) {
					updOprForm.getForm().submit({
						url: 'T21002Action.asp?method=update',
						waitMsg: '正在修改用户信息，请稍后......',
						params: {
							txnId: 'T21002',
							subTxnId: '02' 
						},
						success: function(form,action) {
							showSuccessMsg(action.result.msg,updOprForm);
							updOprForm.getForm().reset();//modify by xiaojian.yu 保存成功后记录重置 20101116
							updOprWin.hide(oprGrid);
							oprGrid.getStore().reload();
//							oprGrid.getTopToolbar().items.items[4].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,updOprForm);
						}
					});				
				}
			}
		},{
			text: '重置',
			handler: function() {
				updOprForm.getForm().reset();
				var selectedRecord = oprGrid.getSelectionModel().getSelected();
				updOprForm.getForm().loadRecord(selectedRecord);
			}
		}
		/*,{
			text: '关闭',
			handler: function() {
				addOprWin.hide(oprGrid);
			}
		}*/
		]
	});
	
	oprGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryOprId: queryForm.findById('queryOprId').getValue(),
			queryOprStatus: queryForm.findById('queryOprStatus').getValue(),
			queryMchtNo:queryForm.findById('queryMchtNo').getValue(),
			queryBrhId:queryForm.findById('queryBrhId').getValue(),
			queryOprName: queryForm.findById('queryOprName').getValue(),
			queryMchtBrhFlag:queryForm.findById('queryMchtBrhFlag').getValue()
		});
		oprGrid.getTopToolbar().items.items[4].disable();
		oprGrid.getTopToolbar().items.items[6].disable();
		oprGrid.getTopToolbar().items.items[8].disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
});