Ext.onReady(function() {
	
	// 数据集
	var firstBrhDestStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=firstBrhDestInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'destId',mapping: 'destId'},
			{name: 'firstBrhId',mapping: 'firstBrhId'},
			{name: 'firstBrhName',mapping: 'firstBrhName'},
			{name: 'bak1',mapping: 'bak1'},
			{name: 'bak2',mapping: 'bak2'}
			
		]),
		autoLoad: true
	}); 
	
	var firstBrhDestColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '通道编号',dataIndex: 'destId',align: 'center',width: 100},
		{header: '通道名称',dataIndex: 'firstBrhName',id:'firstBrhName',width: 150 },
		{header: '结算编号',dataIndex: 'firstBrhId',align: 'center',width: 100},
		{header: '退货类型',dataIndex: 'bak1',align: 'center',width: 100,renderer:function(val){
			if(val == '0') {
				return '<font color="red">不支持退货</font>';
			} else if(val == '1') {
				return '<font color="green">联机退货</font>';
			} else if(val == '2') {
				return '<font color="blue">准退货</font>';
			}
			return '未知类型';} 
		},
		{header: '最大退货天数',dataIndex: 'bak2',width: 150,align: 'center',renderer:function(val){
				if(val != '') {
					return val+'天';
				}
			}
		}
	]);
	
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['通道编号：',{
					xtype: 'textfield',
					emptyText : '检索通道编号',
					id: 'idqueryDestId',
					name: 'queryDestId',
					width: 180
/*				},'-','通道类型：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','交易通道'],['1','结算通道']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryChannelFlag',
					editable: false,
					emptyText: '请选择通道类型',
					id:'idqueryChannelFlag',
					width: 140
				},'-','通道状态：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','停用'],['1','启用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryChannelSta',
					editable: false,
					emptyText: '检索通道状态',
					id:'idqueryChannelSta',
					width: 140*/
				}]
         })
         
	var grid = new Ext.grid.GridPanel({
		title: '交易通道维护',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'firstBrhName',
		store: firstBrhDestStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: firstBrhDestColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar:[{
				xtype: 'button',
				text: '查询',
				name: 'query',
				id: 'query',
				iconCls: 'query',
				width: 80,
				handler:function() {
					firstBrhDestStore.load();
				}
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('idqueryDestId').setValue('');
/*					Ext.getCmp('idqueryChannelFlag').setValue('');
					Ext.getCmp('idqueryChannelSta').setValue('');*/
					firstBrhDestStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '新增通道',
				name : 'add',
				id : 'add',
				iconCls : 'add',
				width : 80,
				handler : function() {
					addWin.show();
					addWin.center();
					Ext.getCmp('bak2AddId').hide();
				}
			},'-',{
				xtype : 'button',
				text : '修改通道',
				name : 'edit',
				id : 'edit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					var rec = grid.getSelectionModel().getSelected().data;
					updForm.getForm().findField('tblFirstBrhDestIdUpd.destId').setValue(rec.destId);
					updForm.getForm().findField('tblFirstBrhDestIdUpd.firstBrhName').setValue(rec.firstBrhName);
					updForm.getForm().findField('tblFirstBrhDestIdUpd.firstBrhId').setValue(rec.firstBrhId);
					updForm.getForm().findField('tblFirstBrhDestIdUpd.bak1').setValue(rec.bak1);
					updWin.show();
					updWin.center();
					if(rec.bak1 == '2'){
						Ext.getCmp('bak2UpdId').show();
						updForm.getForm().findField('tblFirstBrhDestIdUpd.bak2').setValue(rec.bak2);
					}else{
						Ext.getCmp('bak2UpdId').hide();
						updForm.getForm().findField('tblFirstBrhDestIdUpd.bak2').setValue('');
					}
				}
			},'-',{
				xtype : 'button',
				text : '删除通道',
				name : 'delete',
				id : 'delete',
				disabled : true,
				iconCls : 'delete',
				width : 80,
				handler : function() {
					showConfirm('确定要删除该通道吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T80905Action.asp?method=delete',
								params : {
									destId : grid.getSelectionModel().getSelected().get('destId'),
									txnId : '80905',
									subTxnId : '03'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, grid);

									} else {
										showErrorMsg(rspObj.msg, grid);
									}
									firstBrhDestStore.load();
								}
							});
						}
					})
				}
			}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: firstBrhDestStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[6].enable();
			grid.getTopToolbar().items.items[8].enable();
		}
	});
	
	
	firstBrhDestStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryDestId: Ext.getCmp('idqueryDestId').getValue()/*,
			queryChannelFlag: Ext.getCmp('idqueryChannelFlag').getValue(),
			queryChannelSta:Ext.getCmp('idqueryChannelSta').getValue()*/
		});
		// 禁用编辑按钮
		grid.getTopToolbar().items.items[6].disable();
		grid.getTopToolbar().items.items[8].disable();
	});
	
	
	// 添加
	var addForm = new Ext.form.FormPanel({
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
					xtype: 'textfield',
					fieldLabel : '通道编号*',
					allowBlank : false,
					blankText: '通道编号不能为空',
					emptyText : '请输入通道编号',
					id: 'destIdAdd',
					name: 'tblFirstBrhDestIdAdd.destId',
					minLength: '4',
					maxLength: '4',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道名称*',
					allowBlank : false,
					blankText: '通道名称不能为空',
					emptyText : '请输入通道名称',
					id: 'firstBrhNameAdd',
					name: 'tblFirstBrhDestIdAdd.firstBrhName',
					maxLength: 100,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '结算编号*',
					allowBlank : false,
					blankText: '结算编号不能为空',
					emptyText : '请输入结算编号',
					id: 'firstBrhIdAdd',
					name: 'tblFirstBrhDestIdAdd.firstBrhId',
					minLength: '4',
					maxLength: '4',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'combo',
					fieldLabel : '退货类型*',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','不支持退货'],['1','联机退货'],['2','准退货']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'tblFirstBrhDestIdAdd.bak1',
					allowBlank : false,
					editable: false,
					emptyText: '请选择退货类型',
					id:'bak1Add',
					width: 250,
					listeners : {
	                    'select': function(){
	                    	if(this.getValue()=='2'){
								Ext.getCmp('bak2AddId').show();
	                    	}else{
								Ext.getCmp('bak2Add').setValue('');
								Ext.getCmp('bak2AddId').hide();
	                    	}
	                    }
			        }
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				id: 'bak2AddId',
				items : [{
					xtype: 'textfield',
					fieldLabel : '最大退货天数',
					regex: /^[1-9][0-9]?$/,
					regexText: '该输入框只能输入1-99的整数',
					name: 'tblFirstBrhDestIdAdd.bak2',
					id: 'bak2Add',
					width: 250
				}]
			}
		]
	})
	
	
	var addWin = new Ext.Window({
		title : '新增交易通道',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		shadow : false,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [addForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url : 'T80905Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							firstBrhDestStore.load();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
							txnId : '80905',
							subTxnId : '01'
						}
					});
				}
			}
		},{
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
				Ext.getCmp('bak2AddId').hide();
			}
		}]
	});
			
	var updForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		labelWidth : 100,
		layout : 'column',
		autoScroll : true,
		waitMsgTarget : true,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道编号*',
					allowBlank : false,
					blankText: '通道编号不能为空',
					emptyText : '请输入通道编号',
					id: 'destIdUpd',
					name: 'tblFirstBrhDestIdUpd.destId',
					minLength: '4',
					maxLength: '4',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '通道名称*',
					allowBlank : false,
					blankText: '通道名称不能为空',
					emptyText : '请输入通道名称',
					id: 'firstBrhNameUpd',
					name: 'tblFirstBrhDestIdUpd.firstBrhName',
					maxLength: 100,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '结算编号*',
					allowBlank : false,
					blankText: '结算编号不能为空',
					emptyText : '请输入结算编号',
					id: 'firstBrhIdUpd',
					name: 'tblFirstBrhDestIdUpd.firstBrhId',
					minLength: '4',
					maxLength: '4',
					vtype:'isNumber',
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'combo',
					fieldLabel : '退货类型*',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','不支持退货'],['1','联机退货'],['2','准退货']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'tblFirstBrhDestIdUpd.bak1',
					allowBlank : false,
					editable: false,
					emptyText: '请选择退货类型',
					id:'bak1Upd',
					width: 250,
					listeners : {
	                    'select': function(){
	                    	if(this.getValue()=='2'){
								Ext.getCmp('bak2UpdId').show();
								Ext.getCmp('bak2Upd').setValue(grid.getSelectionModel().getSelected().get('bak2'));
	                    	}else{
								Ext.getCmp('bak2Upd').setValue('');
								Ext.getCmp('bak2UpdId').hide();
	                    	}
	                    }
			        }
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				id: 'bak2UpdId',
				items : [{
					xtype: 'textfield',
					fieldLabel : '最大退货天数',
					regex: /^[1-9][0-9]?$/,
					regexText: '该输入框只能输入1-99的整数',
					name: 'tblFirstBrhDestIdUpd.bak2',
					id: 'bak2Upd',
					width: 250
				}]
			}
		]
	});
			
	var updWin = new Ext.Window({
		title : '修改结算通道',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		shadow : false,
		width : 420,
		autoHeight : true,
		layout : 'fit',
		items : [updForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			handler : function() {
				if (updForm.getForm().isValid()) {
					updForm.getForm().submit({
						url : 'T80905Action.asp?method=update',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							firstBrhDestStore.load();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
							destId : grid.getSelectionModel().getSelected().get('destId'),
							txnId : '80905',
							subTxnId : '02'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
			}
		}]
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});