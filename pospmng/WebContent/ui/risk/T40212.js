Ext.onReady(function() {
	var beforePan = '';
	var presentPan = '';
	
	var panStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	// 数据集
	var cardBlackStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankCardBlack'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'cardNo',mapping: 'cardNo'},
			{name: 'insIdCd',mapping: 'insIdCd'},
			{name: 'cardDis',mapping: 'cardDis'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'binNo',mapping: 'binNo'},
			{name: 'remarkInfo',mapping: 'remarkInfo'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'crtDateTime',mapping: 'crtDateTime'}
		]),
	autoLoad: true
	});
	
	// #####################  银行卡黑名单操作日志  开始  #####################

	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankCardBlackOptLog'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'optTime',mapping: 'optTime'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'optFlag',mapping: 'optFlag'},
			{name: 'cardNoLog',mapping: 'cardNo'},
			{name: 'remarkInfoLog',mapping: 'remarkInfo'}
		])
	});
	
	var logRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table width=600>' +
				'<tr>' +
					'<td><font color=gray>备注信息：</font>{remarkInfoLog}</td>' +
				'</tr>' +
			'</table>'
		)
	});

	var gridColumnModel = new Ext.grid.ColumnModel([
		logRowExpander,
		new Ext.grid.RowNumberer(),
		{header: '操作时间',dataIndex: 'optTime',align: 'center',sortable: true,width: 180,renderer:formatDt},
		{header: '操作人员',dataIndex: 'oprId',align: 'center',width: 120},
		{header: '操作类型',dataIndex: 'optFlag',align: 'center',width: 120,renderer:blackCardOptFlag},
		{header: '银行卡号',dataIndex: 'cardNoLog',width: 180}
	]);
	
	var tbar2 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['操作日期：',{
       		xtype: 'datefield',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			editable: false,
			id: 'startDate',
			name: 'startDate',
			width: 100
       	},'—',{
			xtype: 'datefield',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			editable: false,
			id: 'endDate',
			name: 'endDate',
			width: 100
       	},'-','操作类型：',{
			xtype: 'combo',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','新增'],['0','删除']],
				reader: new Ext.data.ArrayReader()
			}),
			displayField: 'displayField',
			valueField: 'valueField',
			id: 'optFlagId',
			hiddenName: 'optFlag',
			mode: 'local',
			triggerAction: 'all',
			forceSelection: true,
			typeAhead: true,
			selectOnFocus: true,
			editable: false,
			width: 80
       	},'-','银行卡号：',{
			xtype: 'textfield',
			emptyText : '模糊检索卡号',
			id: 'cardNoId',
			width: 120
		}]
	})
	
	var gridPanel = new Ext.grid.GridPanel({
		frame: true,
		border: true,
		width: 670,
		height: 450,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		plugins: logRowExpander,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载银行卡黑名单操作日志列表......'
		},
		tbar: [{
			xtype: 'button',
			iconCls: 'query',
			text:'查询',
			id: 'widfalg',
			width: 60,
			handler: function(){
				gridStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				Ext.getCmp('optFlagId').setValue('');
				Ext.getCmp('cardNoId').setValue('');
				gridStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridStore.on('beforeload',function() {
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			optFlagId: Ext.get('optFlag').getValue(),
			cardNoId: Ext.getCmp('cardNoId').getValue()
		});
	});
	
	// #####################  银行卡黑名单操作日志  结束  #####################
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table width=700>' +
				'<tr>' +
					'<td><font color=gray>录入操作员：</font>{crtOprId}</td>' +
					'<td><font color=gray>录入日期： </font>{crtDateTime:this.formatDate}</td>' +
				'</tr>' +
			'</table>',
			{formatDate: function(val){
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
						+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
						+ val.substring(10, 12) + ':' + val.substring(12, 14);
					}else{
						return val;
					}
				}
			}
		)
	});
	
	var cardBlackColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '银行卡号',dataIndex: 'cardNo',width: 200},
		{header: '发卡行名称',dataIndex: 'cardDis',width: 150},
		{header: '交易卡种',dataIndex: 'cardTp',width: 120,align: 'center',renderer:cardTypeTl},
		{header: '卡BIN值',dataIndex: 'binNo',width: 120,align: 'center' },
		{header: '备注信息',dataIndex: 'remarkInfo',id:'remarkInfo',width: 250}
	]);
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['银行卡号：',{
			xtype: 'textfield',
			emptyText : '模糊检索卡号',
			id: 'cardNoQuery',
			width: 180
		}]
	})
	
	var grid = new Ext.grid.GridPanel({
		title: '银行卡黑名单管理',
		iconCls: 'logo',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'remarkInfo',
		store: cardBlackStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: cardBlackColModel,
		plugins: rowExpander,
		forceValidation: true,
		loadMask: {
			msg: '正在加载银行卡黑名单列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				cardBlackStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('cardNoQuery').setValue('');
				cardBlackStore.load();
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
			text : '删除',
			name : 'delete',
			id : 'delete',
			disabled : true,
			iconCls : 'delete',
			width : 80,
			handler : function() {
				showConfirm('确定要删除该银行卡黑名单吗？', grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.request({
							url : 'T40212Action.asp?method=delete',
							params : {
								cardNo: grid.getSelectionModel().getSelected().get('cardNo'),
								txnId : '40212',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
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
		}, '->', {
			xtype : 'button',
			text : '历史操作记录',
			name : 'hisOpt',
			id : 'hisOpt',
			iconCls : 'search',
			width : 80,
			handler : function() {
				hisOpt.show();
				hisOpt.center();
				gridStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: cardBlackStore,
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
			Ext.getCmp('delete').enable();
		}
	});
	
	cardBlackStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			cardNoQuery: Ext.getCmp('cardNoQuery').getValue()
		});
		// 禁用编辑按钮
		Ext.getCmp('delete').disable();
	});
	
	// 添加银行卡黑名单
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
				fieldLabel : '银行卡号*',
				allowBlank : false,
				blankText: '银行卡号不能为空',
				emptyText : '请输入银行卡号',
				id: 'cardNoAdd',
				name: 'tblBankCardBlackAdd.cardNo',
				vtype:'isNumber',
				maxLength: 20,
				width: 250,
				enableKeyEvents:true,
				listeners:{
					'keyup':function(){
						var cho = Ext.getCmp('cardNoAdd').getValue();
						if(cho.length >= 6){
							presentPan = cho.substring(0,6);
							if(presentPan != beforePan){
								SelectOptionsDWR.getComboDataWithParameter('BANK_BIN',cho,function(ret){
									panStore.loadData(Ext.decode(ret));
				    				var record = panStore.getAt(0);
				    				if(record.data.valueField == ''){
										Ext.getCmp('cardDis').setValue('未匹配到发卡行');
										Ext.getCmp('cardTp').setValue('未匹配到卡种');
				    				}else{
										Ext.getCmp('cardDis').setValue(record.data.valueField);
										Ext.getCmp('cardTp').setValue(record.data.displayField);
				    				}
								});
							}
						}else{
							Ext.getCmp('cardDis').setValue('');
							Ext.getCmp('cardTp').setValue('');
							presentPan = cho;
						}
						beforePan = presentPan;
					}
				}
			}]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '发卡行名称',
				emptyText : '自动匹配名称',
				id: 'cardDis',
				disabled: true,
				width: 250
			}]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '交易卡种',
				emptyText : '自动匹配卡种',
				id: 'cardTp',
				disabled: true,
				width: 250
			}]
		},{
			columnWidth : .99,
			layout : 'form',
			items : [{
				xtype: 'textarea',
				fieldLabel : '备注信息',
				allowBlank : true,
				id: 'remarkInfoAdd',
				name: 'tblBankCardBlackAdd.remarkInfo',
				maxLength: 200,
				width: 250
			}]
		}]
	})
	
	var addWin = new Ext.Window({
		title : '新增银行卡黑名单',
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
						url : 'T40212Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,addForm);
							// 重置表单
							addForm.getForm().reset();
							beforePan = '';
							// 重新加载参数列表
							grid.getStore().reload();
							addWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,addForm);
						},
						params : {
							txnId : '40212',
							subTxnId : '01'
						}
					});
				}
			}
		},{
			text : '重置',
			handler : function() {
				addForm.getForm().reset();
			}
		}]
	});
	
	var hisOpt = new Ext.Window({
		title : '历史操作记录',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		autoWidth : true,
		autoHeight : true,
		layout : 'fit',
		items : [gridPanel],
		closeAction : 'hide',
		iconCls : 'search',
		resizable : false
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});