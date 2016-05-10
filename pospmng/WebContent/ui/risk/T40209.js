Ext.onReady(function() {
	
	// 数据集
	var riskWhiteStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskParamMcc'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mcc',mapping: 'mcc'},
			{name: 'mccDesc',mapping: 'mccDesc'},
			{name: 'mchtCoAmt',mapping: 'mchtCoAmt'},
			{name: 'mchtSeAmt',mapping: 'mchtSeAmt'},
			{name: 'mchtNlAmt',mapping: 'mchtNlAmt'},
			{name: 'updOpr',mapping: 'updOpr'},
			{name: 'updTime',mapping: 'updTime'},
			{name: 'remark',mapping: 'remark'}
		]),
	autoLoad: true
	}); 
	
	
	var riskWhiteColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		/*//多选框
		    new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),*/
		{header: 'mcc',dataIndex: 'mcc',hidden:true,width: 240},
		{header: 'MCC',dataIndex: 'mccDesc',align: 'left',width: 180},
		{header: '公司商户限额',dataIndex: 'mchtCoAmt',width: 120,align: 'right' },
		{header: '个体商户限额',dataIndex: 'mchtSeAmt',width: 120 ,align: 'right'},
		{header: '无执照商户限额',dataIndex: 'mchtNlAmt',width: 120,align: 'right' },
		{header: '操作人',dataIndex: 'updOpr',width: 100,align: 'center' },
		{header: '操作时间',dataIndex: 'updTime',width: 140,align: 'center',renderer:formatDt },
		{header: '备注',dataIndex: 'remark',width: 240 ,align: 'left'}
	]);
	
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'MCC：',{
				  			xtype: 'dynamicCombo',
				  			methodName: 'getMchtMcc',
               				id: 'mcc_query',
               				name: 'mchtIdUp',
				  			mode:'local',
				  			triggerAction: 'all',
				  			editable: true,
				  			lazyRender: true,
				  			width: 240
               		}]  
     }) 
	
	
	var grid = new Ext.grid.GridPanel({
		title: 'MCC风控参数管理',
		iconCls: 'logo',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: riskWhiteStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		/*//多选框
		   sm : new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),*/
		cm: riskWhiteColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载MCC风控参数列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				riskWhiteStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('mcc_query').setValue('');
				riskWhiteStore.load();
			}
		}, '-', {
					xtype : 'button',
					text : '新增',
					name : 'add',
					id : 'add',
					iconCls : 'add',
					width : 80,
					handler : function() {
						addWin.show();
						addWin.center();
					}
		},'-', {
			xtype : 'button',
			text : '修改',
			name : 'updButton',
			id : 'updButtonId',
			disabled : true,
			iconCls : 'edit',
			width : 80,
			handler : function() {
				var rows=grid.getSelectionModel().getSelections();//获取所有选中行
				if(rows.length>1){
					showAlertMsg("请单独选中要修改的数据！", grid);
					return;
				}
				
				var rec = grid.getSelectionModel().getSelected();//获取选择的行
				if (rec == null) {
					showAlertMsg("没有选择记录", grid);
					return;
				}
				updMccForm.findById('mccupd').setValue(rec.get('mcc'));
				updMccForm.findById('mcc_upd').setValue(rec.get('mccDesc'));

				//选中记录修改时显示的限额值去掉逗号","和后面的".00"
				var coAmt = rec.get('mchtCoAmt').replaceAll(",", "");
				var seAmt = rec.get('mchtSeAmt').replaceAll(",", "");
				var nlAmt = rec.get('mchtNlAmt').replaceAll(",", "");
				var coAmt_new = coAmt.substring(0,coAmt.length-3);
				var seAmt_new = seAmt.substring(0,seAmt.length-3);
				var nlAmt_new = nlAmt.substring(0,nlAmt.length-3);
				updMccForm.findById('mchtCoAmt_upd').setValue(coAmt_new);
				updMccForm.findById('mchtSeAmt_upd').setValue(seAmt_new);
				updMccForm.findById('mchtNlAmt_upd').setValue(nlAmt_new);
				
				updMccForm.findById('remark_upd').setValue(rec.get('remark'));
				updMccWin.show();
				updMccWin.center();
			}
		},'-', {
					xtype : 'button',
					text : '删除',
					name : 'delete',
					id : 'delete',
					disabled : true,
					iconCls : 'delete',
					width : 80,
					handler : function() {
						showConfirm('确定要删除选中的MCC数据吗？',grid,function(bt) {
								//取多选记录方法 用逗号分开 后台处理
								var rows=grid.getSelectionModel().getSelections();//获取所有选中行
								var str = '';
								for(var i=0;i <rows.length;i++){
									str = str+rows[i].get('mcc')+',';
								}
								//如果点击了提示的确定按钮
								if(bt == "yes") {
									Ext.Ajax.request({
										url : 'T40209Action_delMcc.asp',
										params : {
											"str" : str
										},
										method: 'POST',
										success : function(response) {
											showSuccessMsg("删除成功！", addForm);
											riskWhiteStore.reload();
										},
										failure : function(response) {
											showErrorMsg("删除失败！", addForm);
											riskWhiteStore.reload();
										}
									});
								}
						});
					}
		}],
		listeners : {//將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar);
                } ,
            'cellclick':selectableCell
        },
		bbar: new Ext.PagingToolbar({
			store: riskWhiteStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
			Ext.getCmp('delete').disable();
			Ext.getCmp('updButtonId').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			Ext.getCmp('delete').enable();
			Ext.getCmp('updButtonId').enable();
		}
	});
	
	
	riskWhiteStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mccQuery: Ext.getCmp('mcc_query').getValue()
		});
	});
	
	
	// 添加
	var addForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight: true,
		labelWidth: 100,
		//width: 900,
		autoScroll : true,
		//layout: 'column',
		waitMsgTarget : true,
		defaultType : 'textfield',
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items : [{
		  			xtype: 'dynamicCombo',
		  			methodName: 'getMchtMcc',
					id: 'mccId',
					hiddenName : 'mcc',
		  			mode:'local',
					fieldLabel : 'MCC<font color="red">*</font>',
		  			triggerAction: 'all',
		  			allowBlank : false,
		  			editable: true,
		  			lazyRender: true,
		  			width: 240
				},{
					xtype : 'textfield',
					id: 'mchtCoAmtId',
					name : 'mchtCoAmt',
					allowBlank : false,
					fieldLabel : '公司商户限额<font color="red">*</font>',
					width : 200,
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype : 'textfield',
					id: 'mchtSeAmtId',
					name : 'mchtSeAmt',
					allowBlank : false,
					fieldLabel : '个体商户限额<font color="red">*</font>',
					width : 200,
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype : 'textfield',
					id: 'mchtNlAmtId',
					name : 'mchtNlAmt',
					allowBlank : false,
					fieldLabel : '无执照商户限额<font color="red">*</font>',
					width : 200,
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype: 'textarea',
					fieldLabel : '备注',
					allowBlank : true,
					id: 'remark',
					name: 'remark',
					vtype: 'length100B',
					emptyText : '最大长度为100，一个汉字算2个字符',
					blankText: '最大长度为100，一个汉字算2个字符',
					maxLength: 100,
					width: 240
				}
		]
	})
	
	
	var addWin = new Ext.Window({
				title : 'MCC风控参数新增',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
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
							if(Ext.getCmp('remark').getValue() ==''){
								Ext.getCmp('remark').setValue(' ');
							}
							var mcc = addForm.getForm().findField('mccId').getValue();
							if(4 != mcc.length){
								alert("MCC必须为四位！");
								return;
							}
							addForm.getForm().submit({
								url : 'T40209Action_addMcc.asp',
								waitMsg : '正在添加信息，请稍后......',
								params : {
									txnId : 'T40208'
								},
								success : function(form, action) {
									addForm.getForm().reset();//重置表单
									addWin.hide(addForm);//addWin.hide();
									showSuccessMsg("添加成功", addForm);
									riskWhiteStore.reload();
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, addForm);
								}
							});

						}
					}
				}, {
					text : '重置',
					handler : function() {
						addForm.getForm().reset();
					}
				}]
			});
			


	// 渠道商户信息修改
	var updMccForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
		  			xtype: 'combofordispaly',//dynamicCombo
		  			methodName: 'getMchtMcc',
					id: 'mcc_upd',
					name : 'mcc_upd_name',
					readOnly : true,
		  			mode:'local',
					fieldLabel : 'MCC<font color="red">*</font>',
		  			triggerAction: 'all',
		  			allowBlank : false,
		  			editable: true,
		  			lazyRender: true,
		  			width: 240
				},{
					xtype : 'textfield',
					id: 'mccupd',
					name : 'mcc',
					hidden : true,
					width : 240
				},{
					xtype : 'textfield',
					id: 'mchtCoAmt_upd',
					name : 'mchtCoAmt',
					allowBlank : false,
					fieldLabel : '公司商户限额<font color="red">*</font>',
					width : 200,
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype : 'textfield',
					id: 'mchtSeAmt_upd',
					name : 'mchtSeAmt',
					allowBlank : false,
					fieldLabel : '个体商户限额<font color="red">*</font>',
					width : 200,
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype : 'textfield',
					id: 'mchtNlAmt_upd',
					name : 'mchtNlAmt',
					allowBlank : false,
					fieldLabel : '无执照商户限额<font color="red">*</font>',
					regex:/^[0-9]*$/,	// /^\d+(\.\d+)?$/
					width : 200,
					maxLength :13,
					listeners: {
			    	 	render: function(obj) {
			    	 	var font=document.createElement("font");
						font.setAttribute("color","red");
						var redStar=document.createTextNode('元');
						font.appendChild(redStar);    
						obj.el.dom.parentNode.appendChild(font);
						}
					}
				},{
					xtype : 'textarea',
					id: 'remark_upd',
					name : 'remark',
					allowBlank : true,
					fieldLabel : '备注',
					emptyText : '最大长度为100，一个汉字算2个字符',
					blankText: '最大长度为100，一个汉字算2个字符',
					vtype: 'length100B',
					width : 240,
					maxLength :100
				}]
	});
	
	
	var updMccWin = new Ext.Window({
		title : 'MCC风控参数修改',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		width : 400,
		autoHeight : true,
		layout : 'fit',
		items : [ updMccForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'edit',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				var mcc = updMccForm.getForm().findField('mcc_upd').getValue();
				var mchtCoAmt = updMccForm.getForm().findField('mchtCoAmt_upd').getValue();
				var mchtSeAmt = updMccForm.getForm().findField('mchtSeAmt_upd').getValue();
				var mchtNlAmt = updMccForm.getForm().findField('mchtNlAmt_upd').getValue();
				var remark = updMccForm.getForm().findField('remark_upd').getValue();
				if (updMccForm.getForm().isValid()) {
					updMccForm.getForm().submit({
						url : 'T40209Action_updMcc.asp',
						waitMsg : '正在加载信息，请稍后......',
						params : {
							txnId : ''
						},
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updMccForm);
							updMccForm.getForm().reset();
							updMccWin.hide(updMccForm);
							riskWhiteStore.reload();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updMccForm);
							updMccForm.getForm().reset();
							updMccWin.hide(updMccForm);
							riskWhiteStore.reload();
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updMccForm.getForm().reset();
			}
		} ]
	});

	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});