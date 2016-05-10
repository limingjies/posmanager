Ext.onReady(function() {
	
	// 数据集
	var riskParamStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=riskParamDef'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'riskId',mapping: 'riskId'},
			{name: 'paramSeq',mapping: 'paramSeq'},
			{name: 'paramLen',mapping: 'paramLen'},
			{name: 'defaultValue',mapping: 'defaultValue'},
			{name: 'paramName',mapping: 'paramName'},
			{name: 'riskGrp',mapping: 'riskGrp'}
		]),
	autoLoad: true
	}); 
	
	var riskParamColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '风控模型',dataIndex: 'riskId',width: 100 ,align: 'center'},
		{header: '模型类别',dataIndex: 'riskGrp',id:'riskIdName',width: 200,renderer:ModelGroup,align: 'left' },
		{header : '参数名称',dataIndex : 'paramName',width : 250},
		{header : '参数长度上限',dataIndex : 'paramLen',width : 120},
		{header : '参数值',dataIndex : 'defaultValue',width : 90,
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
	
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,
        items:['风控模型类别：',{
				xtype: 'basecomboselect',
				baseParams: 'kindGrp',
				id:'riskGrpIdQuery',
				hiddenName: 'riskGrpId',
				width: 200,
				editable: false
	        }
        ]
	})
	
	var update = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			//存放要修改的监控模型
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				var data = {
					riskId : record.get('riskId'),
					paramSeq: record.get('paramSeq'),
					paramName: record.get('paramName'),
					paramLen: record.get('paramLen'),
					defaultValue: record.get('defaultValue')
				};
				array.push(data);
			}
			Ext.Ajax.request({
				url: 'T40200Action.asp?method=update',
				method: 'post',
				params: {
					modelDataList : Ext.encode(array),
					txnId: '40200',
					subTxnId: '03'
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
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	var grid = new Ext.grid.EditorGridPanel({
		title: '风险参数定义',
		region:'center',
		iconCls: 'logo',
		autoExpandColumn: 'riskIdName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: riskParamStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: riskParamColModel,
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载风险参数列表......'
		},
		tbar:[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				riskParamStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('riskGrpIdQuery').setValue('');
				riskParamStore.load();
			}
		}/*,'-',{
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
		},'-',{
			xtype : 'button',
			text : '删除',
			name : 'delete',
			id : 'delete',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该模型对应的风险参数吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T40208Action.asp?method=delete',
							params : {
								mchtNo:grid.getSelectionModel().getSelected().get('mchtNo'),
								txnId : '40208',
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
								grid.getStore().reload();
								Ext.getCmp('delete').disable();
							}
						});
					}
				})
			}
		}*/,'-',update],
		listeners: {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
				tbar1.render(this.tbar);
            }
        },
		bbar: new Ext.PagingToolbar({
			store: riskParamStore,
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
//			Ext.getCmp('delete').enable();
			grid.getTopToolbar().items.items[4].disable();
			if(grid.getSelectionModel().getSelected().data.riskId=='A10'){
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0\d)$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者00-23';
			}else{
				Ext.getCmp('paramValues').regex=/^(([1-9]\d*)|0)(\.\d{1,2})?$/;
				Ext.getCmp('paramValues').regexText= '只能输入整数或者最多保留2位的小数';
			}
		}
	});
	
	
	riskParamStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			riskGrpIdQuery: Ext.getCmp('riskGrpIdQuery').getValue()
		});
		// 禁用编辑按钮
//		Ext.getCmp('delete').disable();
	});
	

	grid.on({'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		}
	});
			
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});