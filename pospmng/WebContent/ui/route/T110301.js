Ext.onReady(function() {
	
	var dialog ;
	function showUploadWin() {
	 // 文件上传窗口
		dialog = new UploadDialog({
			uploadUrl : 'T110301Action_upload.asp',
			filePostName : 'files',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '10 MB',
			fileTypes : '*.csv',
			fileTypesDescription : 'csv文件',
			title: '上传商户文件',
			scope : this,
			onEsc: function() {
				this.hide();
			},
			exterMsgMethod: function(msgs) {
				var mchtIds=msgs.toString();
				var newGid=routeRuleGrid.getSelectionModel().getSelected().get('mchtGid');
				var store=routeRuleStore;
				this.hide();
				batchAddProperty(mchtIds,newGid,store);
			}
		});
		dialog.show();
//		dialog.on('uploadsuccess', onUploadSuccess);
//		dialog.on('uploadfailed', onUploadSuccess);
		 
	}
	
//	==================================主规则====================================
	// 数据集
	var routeRuleStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteMchtg'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtGid',mapping: 'mchtGid'},
			{name: 'mchtGname',mapping: 'mchtGname'},
			{name: 'mchtGdsp',mapping: 'mchtGdsp'}
		]),
		autoLoad: true
	});
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户组编号',dataIndex: 'mchtGid',align: 'center',width: 200,id:'msc1'},
		{header: '商户组名称',dataIndex: 'mchtGname',width: 150,align: 'center' },
		{header: '商户组描述',dataIndex: 'mchtGdsp',id:'srvId',align: 'center',width: 150 }
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'商户组名称：',{
					xtype: 'textfield',
					id: 'mchtGroupNm',
					name: 'mchtGroupNm',
					maxLength: 10,
					width: 140
				}
	            ]  
        
	
          
 })     
         
    var routeRuleGrid = new Ext.grid.GridPanel({
		width:300,
		id:'routeRuleGrid',
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'msc1',
		store: routeRuleStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeRuleStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('mchtGroupNm').setValue('');
				Ext.getCmp('mchtNo').setValue('');
				Ext.getCmp('mchtNm').setValue('');
				routeRuleStore.load();
			}
		},'-', {
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
				name : 'edit',
				id : 'edit',
				disabled : true,
				iconCls : 'edit',
				width : 80,
				handler : function() {
					if (!routeRuleGrid.getSelectionModel().hasSelection()) {
						showMsg("请选择一条记录后再进行操作。", routeRuleGrid);
						return;
					}
					var select = routeRuleGrid.getSelectionModel().getSelected();
					var data = select.data;
					updForm.getForm().findField('tblRouteMchtg.mchtGid.upd').setValue(data.mchtGid);
					updForm.getForm().findField('tblRouteMchtg.mchtGname.upd').setValue(data.mchtGname);
					updForm.getForm().findField('tblRouteMchtg.mchtGdsp.upd').setValue(data.mchtGdsp);
					
					updWin.show();
					updWin.center();
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
					showConfirm('确定要删除该商户组吗？', routeRuleGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T110301Action_delete.asp',
								params : {
									'routeMchtg.mchtGid' : routeRuleGrid.getSelectionModel().getSelected().get('mchtGid')
//									txnId : '10211',
//									subTxnId : '03'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, routeRuleGrid);
										routeRuleGrid.getStore().reload();
									} else {
										showErrorMsg(rspObj.msg, routeRuleGrid);
									}
								}
							});
						}
					})
				}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
					tbar1.render(this.tbar); 
                }  ,
            'cellclick':selectableCell,     
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	
//	==============================主规则结束=======================================
	
	
	
	
//	==============================详细规则商户=======================================
	// 数据集
	var routeDetailStore = new Ext.data.Store({
		id:'routeDetailStore',
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=routeMchtgDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'mchtAddr',mapping: 'mchtAddr'},
			{name: 'mchtMcc',mapping: 'mchtMcc'}
		])
	}); 
	
	var routeRuleDtlColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		{header: '商户编号',dataIndex: 'mchtNo',id:'firstMchtNm',align: 'center',width: 180 },
		{header: '商户名称',dataIndex: 'mchtName',width: 150 ,align: 'center',renderer:routeStatus},
		{header: '商户地区',dataIndex: 'mchtAddr',align: 'center',width: 170 },
		{header: '商户Mcc',dataIndex: 'mchtMcc',width: 120 ,align: 'center',renderer:routeStatus}
	]);
	
	
	var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
              			'-',	'商户名称：',{
        					xtype: 'textfield',
        					id: 'mchtNm',
        					name: 'mchtNm',
        					//vtype:'isEngNum',
        					maxLength: 15,
        					width: 140
        				},'-',	'商户编号：',{
        					xtype: 'textfield',
        					id: 'mchtNo',
        					name: 'mchtNo',
        					//vtype:'isEngNum',
        					maxLength: 15,
        					width: 140
        				}
        	            ]  
         }) 
	var tbar4 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
      			'-',	'商户名称：',{
					xtype: 'textfield',
					id: 'mchtNmDetail',
					name: 'mchtNm',
					//vtype:'isEngNum',
					width: 140
				},'-',	'商户编号：',{
					xtype: 'textfield',
					id: 'mchtNoDetail',
					name: 'mchtNo',
					//vtype:'isEngNum',
					maxLength: 15,
					width: 140
				}
	            ]  
 }) 
	var tbar3 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
      			'-',	'商户地区：',{
					xtype: 'textfield',
					id: 'mchtAddr',
					name: 'mchtAddr',
					//vtype:'isEngNum',
					width: 140
				},'-',	'商户MCC：',{
					xtype: 'textfield',
					id: 'mchtMcc',
					name: 'mchtMcc',
					vtype:'isEngNum',
					width: 140
				}
	            ]  
 }) 
	var sm=new Ext.grid.CheckboxSelectionModel({singleSelect: false});;
	var grid = new Ext.grid.GridPanel({
		region: 'east',
		width: 640,
		split: true,
		collapsible: true,
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		autoExpandColumn: 'firstMchtNm',
		store: routeDetailStore,
		sm: sm,
		cm: routeRuleDtlColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			disabled:true,
			name: 'query',
			id: 'queryDetail',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeDetailStore.load();
			}
		},{
			
			xtype: 'button',
			text: '添加商户',
			hidden:true,
			disabled:true,
			name: 'add',
			id: 'addDetail',
			iconCls: 'add',
			width: 80,
			handler:function() {
				showMchnt();
				//Ext.getCmp('routeDetailStore').reload();
			}
		},{
			
			xtype: 'button',
			disabled:true,
			hidden:true,
			text: '删除商户',
			name: 'delete',
			id: 'deleteDetail',
			iconCls: 'delete',
			width: 80,
			handler:function() {
				var rows=grid.getSelectionModel().getSelections(); 
				if(rows.length===0){
					Ext.Msg.alert('提示信息：','请选择商户！');
					return;
				}
				showConfirm('确定要删除该商户吗？', routeRuleGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
				var mchtGid=Ext.getCmp('routeRuleGrid').getSelectionModel().getSelected().get('mchtGid');
				var mchtId=grid.getSelectionModel().getSelected().get('mchtNo');
				Ext.Ajax.request({
	                url: 'T110301Action_deleteMchtFromGroup.asp',
	                headers: {
	                    'userHeader': 'userMsg'
	                },
	                params: {mchtId:mchtId,mchtGid:mchtGid},
	                method: 'POST',
	                timeout: 180000,
	                success: function (response, options) {
	                	Ext.MessageBox.hide();
	                	var msg=Ext.decode(response.responseText);
	                    Ext.MessageBox.alert('提示信息：', '' +msg.msg);
	                    var store=Ext.StoreMgr.get('routeDetailStore');
	                    store.load();
	                    detailWin.hide();
	                },
	                failure: function (response, options) {
	                	Ext.MessageBox.hide();
	                	var msg=Ext.decode(response.responseText);
	                    Ext.MessageBox.alert('添加商户失败', '请求超时或网络故障,错误编号：' + msg.msg);
	                    var store=Ext.StoreMgr.get('routeDetailStore');
	                    store.load();
	                }
	            });
				routeDetailStore.load();
					}
				})	
			}
		},{
			
			xtype: 'button',
			text: '转出',
			disabled:true,
			name: 'transferOut',
			id: 'transferOut',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				var mchtGid=Ext.getCmp('routeRuleGrid').getSelectionModel().getSelected().get('mchtGid');
				var records= grid.getSelectionModel().getSelections();
				//商户转出
				showMchntDetailS(mchtGid,records,routeDetailStore);
			}
		},'-',{
			xtype: 'button',
			text: '按文件转入',
			name: 'transferIn',
			id: 'transferIn',
			iconCls: 'add',
			width: 80,
			handler:function() {
				showUploadWin();
			}
		}
		],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar4.render(this.tbar); 
					tbar3.render(this.tbar);
                } ,
            'cellclick':selectableCell,
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeDetailStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
//	====================================详细规则商户结束=================================================
	// 文件上传窗口
	
	
	
//	====================================加载=================================================
	// 禁用编辑按钮
	routeRuleGrid.getStore().on('beforeload',function() {
		routeDetailStore.removeAll();
		Ext.getCmp('edit').disable();
		Ext.getCmp('delete').disable();
		Ext.getCmp('queryDetail').disable();
		Ext.getCmp('addDetail').disable();
		Ext.getCmp('transferOut').disable();
	});
	
	routeRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			Ext.getCmp('edit').enable();
			Ext.getCmp('transferIn').enable();
			Ext.getCmp('delete').enable();
			Ext.getCmp('queryDetail').enable();
			Ext.getCmp('addDetail').enable();
			routeDetailStore.load();
//			Ext.getCmp('addDtl').enable();
			/*routeDetailStore.load({
				params: {
					start: 0,
					queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
				}
			})*/
		}
	});
	
	
	routeRuleStore.on('beforeload', function(){
		Ext.getCmp('transferIn').disable();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: Ext.getCmp('mchtNo').getValue(),
			mchtNm:Ext.get('mchtNm').getValue(),
			mchtGroupNm:Ext.get('mchtGroupNm').getValue()
		});
	});
	
//	-----------------------------------------------------------------------------------------
	
	// 禁用编辑按钮
	grid.getStore().on('beforeload',function() {
		Ext.getCmp('deleteDetail').disable();
		Ext.getCmp('transferOut').disable();
		
			
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			Ext.getCmp('deleteDetail').enable();
			Ext.getCmp('transferOut').enable();
			
			//var onFlag=grid.getSelectionModel().getSelected().get('msc2');
		},
		'rowdeselect':function(){
			if(!this.hasSelection()){
				Ext.getCmp('transferOut').disable();
			}
		}
	});
	
	
	routeDetailStore.on('beforeload', function(){
		routeDetailStore.removeAll();
		Ext.getCmp('deleteDetail').disable();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtAddr:Ext.getCmp('mchtAddr').getValue(),
			mchtMcc: Ext.getCmp('mchtMcc').getValue(),
			mchtNoDetail:Ext.getCmp('mchtNoDetail').getValue(),
			mchtNmDetail: Ext.getCmp('mchtNmDetail').getValue(),
			mchtGid:routeRuleGrid.getSelectionModel().getSelected().get('mchtGid')
		});
	});
	
//	=======================================华丽的分割线=======================================================

			
	
//	=============================================新增商户组=================================================
	var addForm = new Ext.form.FormPanel({
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
					xtype: 'textfield',
					fieldLabel : '商户组名称*',
					allowBlank : false,
					blankText: '商户组名称不能为空',
					emptyText : '请输入商户组名称',
					id: 'tblRouteMchtg.mchtGname',
					name: 'routeMchtg.mchtGname',
					//vtype:'isEngNum',
					maxLength: 10,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '商户组描述*',
					allowBlank : false,
					blankText: '商户组描述不能为空',
					emptyText : '请输入商户组描述',
					id: 'tblRouteMchtg.mchtGdsp',
					name: 'routeMchtg.mchtGdsp',
					maxLength: 25,
					width: 250
				}]
			}
		]
	})
	
	var addWin = new Ext.Window({
		title : '新增商户组',
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
					addForm.getForm().submit({
						url : 'T110301Action_add.asp',
						waitMsg : '正在提交，请稍后......',
						
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							routeRuleGrid.getStore().reload();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
			}
		}
		
		]
	});
	var updForm = new Ext.form.FormPanel({
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
					xtype: 'textfield',
					//fieldLabel : '商户组id',
					hidden:true,
					allowBlank : true,
					//blankText: '商户组名称不能为空',
//					emptyText : '请输入商户组名称',
					id: 'tblRouteMchtg.mchtGid.upd',
					name: 'routeMchtg.mchtGid',
					//vtype:'isEngNum',
					maxLength: 10,
					width: 250
				},{
					xtype: 'textfield',
					fieldLabel : '商户组名称*',
					allowBlank : false,
					blankText: '商户组名称不能为空',
					emptyText : '请输入商户组名称',
					id: 'tblRouteMchtg.mchtGname.upd',
					name: 'routeMchtg.mchtGname',
					//vtype:'isEngNum',
					maxLength: 10,
					width: 250
				}]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'textfield',
					fieldLabel : '商户组描述*',
					allowBlank : false,
					blankText: '商户组描述不能为空',
					emptyText : '请输入商户组描述',
					id: 'tblRouteMchtg.mchtGdsp.upd',
					name: 'routeMchtg.mchtGdsp',
					maxLength: 25,
					width: 250
				}]
			}
		]
	})
	var updWin = new Ext.Window({
		title : '修改商户组',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
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
						url : 'T110301Action_update.asp',
						params:{},
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							//var success=Ext.decode(action);
							//alert(success.msg);
							Ext.Msg.alert("提示信息：","商户组修改成功！");
							//showSuccessMsg(success.msg,updForm);
							// 重置表单
							addForm.getForm().reset();
							// 重新加载参数列表
							routeRuleGrid.getStore().reload();
							updWin.hide();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示信息：","商户组修改失败！");
							//showErrorMsg(action.result.msg,updForm);
						},
						params : {
//							txnId : '10211',
//							subTxnId : '01'
						}
					});
				}
			}
		}, {
			text : '重置',
			handler : function() {
				updForm.getForm().reset();
			}
		}
		
		]
	});
	
	
//	=============================================新增商户组=================================================	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [routeRuleGrid,grid],
		renderTo: Ext.getBody()
	});
});