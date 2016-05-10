/*
 * 业务性质维护
 * zhaofachun
 * 2015-10-26
 */
function showDetailInfo(val) {
	return '<span title="' + val.replace(/"/g,'&#34;') + '">' + val + '</span>';
}

Ext.onReady(function() {
	
//	==================================业务信息主页面布局开始====================================
	// 数据集
	var busiInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=busiAllInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'busiId',mapping: 'busiId'},
			{name: 'chlName',mapping: 'chlName'},
			{name: 'busiName',mapping: 'busiName'},
			{name: 'busiId1',mapping: 'busiId1'},
		]),
		autoLoad: true
	}); 
	
	var busiInfoColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '支付渠道编码',dataIndex: 'busiId1',align: 'center', width: 85,hidden:true},
		{header: '支付渠道',dataIndex: 'chlName',align: 'left',width: 200,renderer:showDetailInfo },
		{header: '业务编码',dataIndex: 'busiId',align: 'center',width: 80,hidden:true},
		{header: '业务',dataIndex: 'busiName',align: 'left',width: 200,renderer:showDetailInfo }
	]);
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
    			'-','支付渠道：',{
    					xtype: 'basecomboselect',
    					baseParams: 'CHANNEL_ALL',
    					fieldLabel: '支付渠道',
    					hiddenName: 'queryChannel',
    					id:'queryChannelId',
    					editable: true,
    					width: 140
    			},'-',	'业务：',{
					xtype: 'textfield',
					id: 'queryBusiNameId',
					hiddenName: 'queryBusiNam',
					editable: true,
					width: 100
				}]  
         }) 
         
         
    var busInfoGrid = new Ext.grid.GridPanel({
		width: 400,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
//		autoExpandColumn: 'busiAllInfo',
		store: busiInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: busiInfoColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载业务记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				busiInfoStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				//Ext.getCmp('queryRuleCode').setValue('');
				Ext.getCmp('queryChannelId').setValue('');
				Ext.getCmp('queryBusiNameId').setValue('');
				busiInfoStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
                },
             'cellclick':selectableCell
        }  ,
		bbar: new Ext.PagingToolbar({
			store: busiInfoStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
//	==============================业务信息主页面布局结束=======================================
	
//	==============================性质页面布局开始=======================================
	// 数据集
	var characterInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=characterInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   {name: 'charId',mapping: 'charId'},
		   {name: 'charName',mapping: 'charName'},
		   {name: 'charDesc',mapping: 'charDesc'},
		   {name: 'uptTime',mapping: 'uptTime'},
		   {name: 'uptOper',mapping: 'uptOper'},
		   {name: 'charStatus',mapping: 'charStatus'},
		   {name: 'onFlag',mapping: 'onFlag'}
		])
	}); 
	
	var characterColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '性质编码',dataIndex: 'charId',align: 'center',width:100,hidden:true},
		{header: '性质名称',dataIndex: 'charName',align: 'left',width:250,renderer:showDetailInfo },
		{header: '描述',dataIndex: 'charDesc',align: 'left',width:260,renderer:showDetailInfo },
		{header: '性质状态',dataIndex: 'charStatus',align: 'center',width:60,renderer:branchStatus},
		{header: '最后修改时间',dataIndex: 'uptTime',align: 'center',width:130,renderer:formatDt},
		{header: '修改人',dataIndex: 'uptOper',align: 'center',width:80},
		{header: '是否可停用',dataIndex: 'onFlag',align: 'center',width:100,hidden:true}
	]);

	var characterInfoGrid = new Ext.grid.GridPanel({
		region: 'east',
		width: 680,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
//		autoExpandColumn: 'chaAllInfo',
		store: characterInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: characterColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载性质记录列表......'
		},
		tbar: 	[{
			xtype : 'button',
			text : '新增',
			name : 'btAddChar',
			id : 'btAddChar',
			disabled : true,
			iconCls : 'add',
			width : 80,
			handler : function() {
				winAddChar.show();
				winAddChar.center();
				Ext.getCmp('fieldBusiName').setValue(busInfoGrid.getSelectionModel().getSelected().get('busiName'));
			}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'btEditChar',
			id: 'btEditChar',
			iconCls: 'edit',
			disabled : true,
			width: 80,
			handler:function() {
				winUpdateChar.show();
				winUpdateChar.center();
				Ext.getCmp("fieldBusiName4Up").setValue(busInfoGrid.getSelectionModel().getSelected().get("busiName"));
				var charId=characterInfoGrid.getSelectionModel().getSelected().get("charId");
				var charName=characterInfoGrid.getSelectionModel().getSelected().get("charName");
				Ext.getCmp("fieldCharName4Up").setValue(charName.replace(charId+"-",""));
				Ext.getCmp("fieldCharDesc4Up").setValue(characterInfoGrid.getSelectionModel().getSelected().get("charDesc"));
			}
		},'-', {
			xtype : 'button',
			text : '删除',
			name : 'btDeleteChar',
			id : 'btDeleteChar',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				var onFlag = characterInfoGrid.getSelectionModel().getSelected().get('onFlag');
				if(onFlag == "F"){
					showAlertMsg("有路由规则对应该性质，不可删除！",characterInfoGrid);
					return;
				}
				showConfirm('确定要删除该性质吗？', characterInfoGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T110131Action.asp?method=delete',
							params : {
								brhId : characterInfoGrid.getSelectionModel().getSelected().get('charId')
								//firstMchtCdDtl : characterInfoGrid.getSelectionModel().getSelected().get('firstMchtNo')
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessMsg(rspObj.msg, characterInfoGrid);
									characterInfoGrid.getStore().reload();
								} else {
									showErrorMsg(rspObj.msg, characterInfoGrid);
								}
								Ext.getCmp('btAddChar').enable();
							}
						});
					}
				});
				
			}
		},'-',{
			xtype: 'button',
			text: '启用',
			name: 'btStartChar',
			id: 'btStartChar',
			iconCls: 'accept',
			disabled:true,
			width: 80,
			handler:function() {
				showConfirm('确定要启用该性质吗？', characterInfoGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
									url : 'T110131Action.asp?method=start',
									params : {
										brhId: characterInfoGrid.getSelectionModel().getSelected().get('charId'),
									},
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg,characterInfoGrid);
										} else {
											showErrorMsg(rspObj.msg, characterInfoGrid);
										}
										characterInfoGrid.getStore().reload();
										Ext.getCmp('btStartChar').disable();
										Ext.getCmp('btStopChar').disable();
										Ext.getCmp('btAddChar').enable();
									}
								});
					}
				});
				
			}
		},'-',{
			xtype: 'button',
			text: '停用',
			name: 'btStopChar',
			id: 'btStopChar',
			iconCls: 'stop',
			disabled:true,
			width: 80,
			handler:function() {
				var onFlag = characterInfoGrid.getSelectionModel().getSelected().get('onFlag');
				if(onFlag == "F"){
					showAlertMsg("有路由规则对应该性质，不可停用！",characterInfoGrid);
					return;
				}
				showConfirm('确定要停用该性质吗？', characterInfoGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
									url : 'T110131Action.asp?method=stop',
									params : {
										brhId: characterInfoGrid.getSelectionModel().getSelected().get('charId'),
									},
									success : function(rsp, opt) {
										var rspObj = Ext.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg,characterInfoGrid);
										} else {
											showErrorMsg(rspObj.msg, characterInfoGrid);
										}
										characterInfoGrid.getStore().reload();
										Ext.getCmp('btStartChar').disable();
										Ext.getCmp('btStopChar').disable();
										Ext.getCmp('btAddChar').enable();
									}
								});
					}
				});
				
			}
		}
		],
		bbar: new Ext.PagingToolbar({
			store: characterInfoStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
//	====================================性质页面布局结束=================================================
	
	
	
//	====================================数据加载=================================================	
	busInfoGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(busInfoGrid.getView().getRow(busInfoGrid.getSelectionModel().last)).frame();
			//显示选择业务对应的性质
			characterInfoStore.load();
			//启用性质新增按钮
			Ext.getCmp('btAddChar').enable();
		}
	});
	
	//设置业务查询参数
	busiInfoStore.on('beforeload', function(){
		characterInfoGrid.getStore().removeAll();
		Ext.getCmp('btAddChar').disable();
		Ext.apply(this.baseParams, {
			start: 0,
			queryChannelId: Ext.getCmp('queryChannelId').getValue(),
			queryBusiName: Ext.getCmp('queryBusiNameId').getValue(),
		});
	});
	
	//	-----------------------------------------------------------------------------------------
	
	// 禁用性质功能按钮
	characterInfoGrid.getStore().on('beforeload',function() {
			Ext.getCmp('btDeleteChar').disable();
			Ext.getCmp('btStartChar').disable();
			Ext.getCmp('btStopChar').disable();
			Ext.getCmp('btAddChar').disable();
			Ext.getCmp('btEditChar').disable();
	});
	characterInfoGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(characterInfoGrid.getView().getRow(characterInfoGrid.getSelectionModel().last)).frame();
			Ext.getCmp('btDeleteChar').enable();
			Ext.getCmp('btStartChar').enable();
			Ext.getCmp('btStopChar').enable();
			//Ext.getCmp('btAddChar').enable();
			Ext.getCmp('btEditChar').enable();
			var charStatus = characterInfoGrid.getSelectionModel().getSelected().get('charStatus');
			if(charStatus == '0'){
				Ext.getCmp('btStartChar').disable();
				Ext.getCmp('btStopChar').enable();
			}else if(charStatus=="1"){
				Ext.getCmp('btStopChar').disable();
				Ext.getCmp('btStartChar').enable();
			}
		}
	});
	//设置性质查询参数
	characterInfoStore.on('beforeload', function(){
		characterInfoStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			busiId:busInfoGrid.getSelectionModel().getSelected().get('busiId')
		});
	});
	
//	=======================================华丽的分割线=======================================================

			
//	============================================新增性质=================================================
	var formAddChar = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		// width: 380,
		// defaultType: 'textfield',
		labelWidth: 100,
		// width: 900,
		height : 430,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel : '业务名称',
					allowBlank : false,
					//blankText: '规则代码不能为空',
					//emptyText : '请输入规则代码',
					id: 'fieldBusiName',
					name: 'fieldBusiName',
					//vtype:'isEngNum',
					maxLength: 40,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '性质名称',
					allowBlank : false,
					blankText: '性质名称不能为空',
					emptyText : '请输入性质名称',
					id: 'fieldCharName',
					name: 'fieldCharName',
					maxLength: 40,
					width: 250,
					regex : new RegExp("^[\\d\\w\\u4e00-\\u9fa5]+$"),
					regexText:'请输入字母数字汉字！'
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textarea',
					fieldLabel: '性质描述',
					allowBlank: false,
					blankText: '性质描述不能为空',
					emptyText: '请输入性质描述',
					id: 'fieldCharDesc',
					name: 'fieldCharDesc',
					vtype: 'isOverMax',
					maxLength: 120,
					width: 250
				}]
			}
		]
	});
	var winAddChar = new Ext.Window({
		title : '新增性质',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [formAddChar],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				
				
				
				if (formAddChar.getForm().isValid()) {
					formAddChar.getForm().submit({
						url : 'T110131Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formAddChar);
							// 重置表单
							formAddChar.getForm().reset();
							// 重新加载参数列表
							characterInfoGrid.getStore().reload();
							winAddChar.hide();
							Ext.getCmp('btAddChar').enable();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formAddChar);
							// 重置表单
							formAddChar.getForm().reset();
							// 重新加载参数列表
							characterInfoGrid.getStore().reload();
							winAddChar.hide();
							Ext.getCmp('btAddChar').enable();
						},
						params : {
							brhId : busInfoGrid.getSelectionModel().getSelected().get('busiId'),
							name : Ext.get('fieldCharName').getValue(),
							brhDesc : Ext.get('fieldCharDesc').getValue()
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				formAddChar.getForm().reset();
				Ext.getCmp('fieldBusiName').setValue(busInfoGrid.getSelectionModel().getSelected().get('busiName'));
			}
		}
	]});
//	=============================================新增性质结束=================================================	
	var formUpdateChar = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 100,
		height : 430,
		autoScroll : true,
		layout: 'column',
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'displayfield',
					fieldLabel : '业务名称',
					allowBlank : false,
					id: 'fieldBusiName4Up',
					name: 'fieldBusiName4Up',
					maxLength: 40,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '性质名称',
					allowBlank : false,
					blankText: '性质名称不能为空',
					emptyText : '请输入性质名称',
					id: 'fieldCharName4Up',
					name: 'fieldCharName4Up',
					maxLength: 40,
					width: 250,
					regex : new RegExp("^[\\d\\w\\u4e00-\\u9fa5]+$"),
					regexText:'请输入字母数字汉字！'
					
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textarea',
					fieldLabel: '性质描述',
					allowBlank: false,
					blankText: '性质描述不能为空',
					emptyText: '请输入性质描述',
					id: 'fieldCharDesc4Up',
					name: 'fieldCharDesc4Up',
					vtype: 'isOverMax',
					maxLength: 120,
					width: 250
				}]
			}
		]
	});
			
	var winUpdateChar = new Ext.Window({
		title : '性质修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [formUpdateChar],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				
				
				if (formUpdateChar.getForm().isValid()) {
					formUpdateChar.getForm().submit({
						url : 'T110131Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formUpdateChar);
							// 重置表单
							formUpdateChar.getForm().reset();
							// 重新加载参数列表
							characterInfoGrid.getStore().reload();
							winUpdateChar.hide();
							Ext.getCmp('btAddChar').enable();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formUpdateChar);
							// 重置表单
							formUpdateChar.getForm().reset();
							// 重新加载参数列表
							characterInfoGrid.getStore().reload();
							winUpdateChar.hide();
							Ext.getCmp('btAddChar').enable();
						},
						params : {
							brhId : characterInfoGrid.getSelectionModel().getSelected().get('charId'),
							name : Ext.get('fieldCharName4Up').getValue(),
							brhDesc : Ext.get('fieldCharDesc4Up').getValue()	
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				formAddChar.getForm().reset();
				Ext.getCmp("fieldBusiName4Up").setValue(busInfoGrid.getSelectionModel().getSelected().get("busiName"));
				var charId=characterInfoGrid.getSelectionModel().getSelected().get("charId");
				var charName=characterInfoGrid.getSelectionModel().getSelected().get("charName");
				Ext.getCmp("fieldCharName4Up").setValue(charName.replace(charId+"-",""));
				Ext.getCmp("fieldCharDesc4Up").setValue(characterInfoGrid.getSelectionModel().getSelected().get("charDesc"));
			}
		}
		
		]
	});
//	=============================================主规则修改结束=================================================
		
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [busInfoGrid,characterInfoGrid],
		renderTo: Ext.getBody()
	});
});