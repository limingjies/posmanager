Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	// 数据集
	var payChannelStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=frontBat'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		
			{name: 'instDate',mapping: 'instDate'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'checkName',mapping: 'checkName'},
			{name: 'transCode',mapping: 'transCode'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'status',mapping: 'status'},
			{name: 'sendCode',mapping: 'sendCode'}
			
		]),
		autoLoad: true
	}); 
	
				
	var payChannelColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '前置跑批时间',dataIndex: 'instDate',align: 'center',width: 150,renderer: formatDt},
		{header: '前置流水号',dataIndex: 'sysSeqNum',align: 'center',width: 120},
		{header: '对账日期',dataIndex: 'transDate',width: 100,renderer: formatDt},
		{header: '对账交易类型',dataIndex: 'transCode',width: 120,renderer:frontTransCode},
		{header: '对账文件名称',dataIndex: 'checkName',id:'checkName',width: 150 },
		{header: '对账状态',dataIndex: 'status',width: 150 ,renderer:frontBatStatus }
	]);
	
	/**
	 * 对账状态结果
	 */
	function frontBatStatus(val) {
		switch(val) {
			case '0': return '<font color="green">成功</font>';
			case '1': return '<font color="gray">处理中</font>';
			case '2': return '<font color="red">失败</font>';
			default : return val;
		}
	}
	
	
	/**
	 * 对账状态结果
	 */
	function frontTransCode(val) {
		switch(val) {
			case '5001': return '5001-与账户对账';
			case '5002': return '5002-商户入账';
			case '5003': return '5003-通道入账';
			default : return val;
		}
	}
	
	
	var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['-','对账日期：',{
	               		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
						value:timeYesterday,
						maxValue:preDate,
						editable: false,
						id: 'date',
						name: 'date',
						width: 120
	               	}]
         })
         
	var grid = new Ext.grid.GridPanel({
		title: '前置批量任务管理',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'checkName',
		store: payChannelStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: payChannelColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
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
					payChannelStore.load();
				}
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('date').setValue('');
					payChannelStore.load();
				}
			},'-',{
				xtype : 'button',
				text : '启动批量',
				name : 'start',
				id : 'start',
//				disabled : true,
				iconCls : 'accept',
				width : 80,
				handler : function() {
//					var rec = grid.getSelectionModel().getSelected().data;
//					updForm.getForm().findField('transCodeUpdId').setValue(rec.transCode);
//					updForm.getForm().findField('transDate').setValue(formatDt(rec.transDate));
					updWin.show();
					updWin.center();
				}
			}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: payChannelStore,
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
//			grid.getTopToolbar().items.items[4].enable();
		}
	});
	
	
	payChannelStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			date: typeof(Ext.getCmp('date').getValue()) == 'string' ? '' : Ext.getCmp('date').getValue().format('Ymd')
		});
		// 禁用编辑按钮
//		grid.getTopToolbar().items.items[4].disable();
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
                    xtype: 'combo',
                    fieldLabel: '对账交易类型',
					allowBlank : false,
					displayField: 'displayField',
					valueField: 'valueField',
                    id: 'transCodeId',
                    hiddenName: 'transCode',
                    store: new Ext.data.ArrayStore({
                        fields: ['valueField','displayField'],
                        data: [['5001','5001-账户对账'],['5002','5002-商户入账'],['5003','5003-通道入账']]
                    }),
					width: 250
                }]
			},{
				columnWidth : .99,
				layout : 'form',
				items : [{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					editable: false,
					fieldLabel : '对账日期',
					allowBlank : false,
					blankText: '对账日期不能为空',
					emptyText : '请输入对账日期',
					id: 'transDate',
					name: 'transDate',
					width: 250
				}]
			}
		]
	});
			
	var updWin = new Ext.Window({
		title : '批任务确认',
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
						url : 'T100201Action.asp?method=start',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, updForm);
							// 重置表单
							updForm.getForm().reset();
							// 重新加载参数列表
							payChannelStore.load();
							updWin.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, updForm);
						},
						params : {
//							transCode : grid.getSelectionModel().getSelected().get('transCode'),
//							transDate : grid.getSelectionModel().getSelected().get('transDate'),
							txnId : '100201',
							subTxnId : '01'
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