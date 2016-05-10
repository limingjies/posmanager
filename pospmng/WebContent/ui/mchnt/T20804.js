Ext.onReady(function() {

	var actStores = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ACT_NO_NAME_draws',function(ret){
		actStores.loadData(Ext.decode(ret));
	});
	
	var cardStores = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_ID_NAME',function(ret){
		cardStores.loadData(Ext.decode(ret));
	});
	
	
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        buttonAlign: 'center',
        items: [{
			xtype: 'basecomboselect',
			store: actStores,
			id:'actNoQuerrys',
//			labelStyle: 'padding-left: 5px',
			fieldLabel: '活动编号',
			hiddenName: 'actNoQuerry',
			editable: true,
			anchor: '90%'
		},{
        	xtype: 'basecomboselect',
			store: cardStores,
			id:'cardIdQuerrys',
//			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡BIN',
			hiddenName: 'cardBinQuerry',
			editable: true,
			anchor: '90%'  
       		}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                actStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=HOLDER_CARD_BIN'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'actType',mapping: 'actType'},
			{name: 'cardId',mapping: 'cardId'},
			{name: 'cardName',mapping: 'cardName'},
			{name: 'cardType',mapping: 'cardType'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNoQuerrys').getValue(),
            cardId: Ext.getCmp('cardIdQuerrys').getValue()
        });
        grid.getTopToolbar().items.items[2].disable();
//		grid.getTopToolbar().items.items[4].disable();
    }); 
	
//	var sm = new Ext.grid.CheckboxSelectionModel();

   
	var actColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		sm,
		{header: '活动编号',dataIndex: 'actNo',width: 120},
		{header: '活动名称',dataIndex: 'actName',width: 150},
		{header: '活动类型',dataIndex: 'actType',width: 150,renderer:actContentType},
		{header: '卡BIN号',dataIndex: 'cardId',width: 120},
		{header: '卡BIN名称',dataIndex: 'cardName',width: 150},
		{header: '卡类型',dataIndex: 'cardType',width: 150,renderer:cardType}
		
	]);
	
	
	function actContentType(val) {
		switch(val) {
			case '1' : return '1-分期消费';
			case '2' : return '2-折扣消费';
			case '0': return '0-积分消费';
			case '3': return '3-持卡人抽奖';
			default : return val;
		}
	}
	
	
	function cardType(val) {
		switch(val) {
			case '01' : return '01-借记卡';
			case '02' : return '02-贷记卡';
			default : return val;
		}
	}
	
	var deleteMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
           var selects = grid.getSelectionModel().getSelected();
           if(selects == null)
           {
           		showAlertMsg("请选择要删除记录",activityForm);
           		return;
           }
		   showConfirm('确定要解除该卡BIN的绑定吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
		   	
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20804Action_delete.asp',
//							url: 'T20803Action.asp?method=delete',
							success: function(rsp,opt) {
								
					
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[2].disable();
//									grid.getTopToolbar().items.items[4].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								txnId: '20804',
								subTxnId: '02',
								actNo:selects.get('actNo'),
								cardId:selects.get('cardId')
							}
						}
						);
					}
				});
		}
	}
   
	var oprInfoForm = new Ext.FormPanel({
		frame: true,
		height: 80,
		layout: 'column',
		waitMsgTarget: true,
		defaults: {
			bodyStyle: 'padding-left: 10px'
		},
		items: [{
			columnWidth: .9,
			layout: 'form',
			labelWidth: 100,
			items: [{
				
				xtype: 'basecomboselect',
				store: actStores,
//				id:'actNoQuerrys',
//				labelStyle: 'padding-left: 5px',
				fieldLabel: '活动编号',
				hiddenName: 'actNoAdd',
				editable: true,
//				anchor: '90%',
				width: 200,
				minListWidth:200,
				allowBlank: false,
				blankText: '请选择活动号'
				
//				vtype: 'alphanum'
			}]
		},{
			columnWidth: .9,
			layout: 'form',
			labelWidth: 100,
			items: [{
				xtype: 'basecomboselect',
				store: cardStores,
//				id:'actNoQuerrys',
//				labelStyle: 'padding-left: 5px',
				fieldLabel: '卡BIN',
				hiddenName: 'cardIdAdd',
				editable: true,
//				anchor: '90%',
				width: 200,
				minListWidth:200,
				allowBlank: false,
				blankText: '请选择卡BIN号'
			}]
		}]
	});
	
	
	
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '活动卡BIN绑定新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 400,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		animateTarget: 'add',
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submit({
//						url: 'T20803Action.asp?method=add',
						url: 'T20804Action_add.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							oprInfoForm.getForm().reset();
							oprWin.hide();
							showSuccessMsg(action.result.msg,grid);
							//重置表单
							//重新加载列表
							topQueryPanel.getForm().reset();
							grid.getStore().reload();
							grid.getTopToolbar().items.items[2].disable();
//							grid.getTopToolbar().items.items[4].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '20804',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide();
			}
		}]
	});
	
	
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
    
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    
    
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		id:'add',
		handler:function() {
			oprWin.show();
//			oprWin.center();
		}
	};
    
    
	var menuArr = new Array();
	
	menuArr.push(addMenu);
	menuArr.push('-');	
	menuArr.push(deleteMenu);
	menuArr.push('-');	
	menuArr.push(queryMenu);	//[0]
	

	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '营销活动卡BIN绑定',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
//		autoExpandColumn: 'mchtNm',
		cm: actColModel,
		tbar: menuArr,
		clicksToEdit: true,
		forceValidation: true,
//		plugins: termRowExpander,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载营销活动卡BIN信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	actStore.load();
	grid.getTopToolbar().items.items[2].disable();
//	grid.getTopToolbar().items.items[4].disable();
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelections();
			if(rec != null ) {
				grid.getTopToolbar().items.items[2].enable();
//				grid.getTopToolbar().items.items[4].enable();
			} else {
				grid.getTopToolbar().items.items[2].disable();
//				grid.getTopToolbar().items.items[4].disable();
			}
		}
	});
   
	var mainView = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})