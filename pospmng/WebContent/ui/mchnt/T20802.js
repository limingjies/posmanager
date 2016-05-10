Ext.onReady(function() {

	var actStores = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ACT_NO_NAME',function(ret){
		actStores.loadData(Ext.decode(ret));
	});
	
	var mchtStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('MCHT_NO_NAME',function(ret){
		mchtStore.loadData(Ext.decode(ret));
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
			labelStyle: 'padding-left: 5px',
			fieldLabel: '活动编号',
			hiddenName: 'actNoQuerry',
			editable: true,
			anchor: '90%'
		},{
			xtype: 'dynamicCombo',
	        methodName: 'getMchntNo',
			id:'mchtNoQuerrys',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户号',
			editable: true,
			hiddenName: 'mchtNoQuerry',
			anchor: '90%'
		},{
			xtype: 'basecomboselect',
			id: 'mchtType',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '活动类型',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','0-积分消费'],['1','1-分期消费'],['2','2-折扣消费'],['3','3-持卡人抽奖']],
				reader: new Ext.data.ArrayReader()
			}),
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
			url: 'gridPanelStoreAction.asp?storeId=holdMchtParticipatStore'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'actFee',mapping: 'actFee'},
			{name: 'devNum',mapping: 'devNum'},
			{name: 'devProd',mapping: 'devProd'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNoQuerry: Ext.getCmp('actNoQuerrys').getValue(),
            mchtNoQuerry: Ext.getCmp('mchtNoQuerrys').getValue(),
            mchtTypeQuerry: Ext.getCmp('mchtType').getValue()
        });
    }); 
	
	var sm = new Ext.grid.CheckboxSelectionModel();

	var actColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '活动编号',dataIndex: 'actNo',width: 120},
		{header: '活动名称',dataIndex: 'actName',width: 200},
		{header: '商户号',dataIndex: 'mchtNo',width: 120},
		{id: 'mchtNm',header: '商户名称',dataIndex: 'mchtNm',width: 250},
		{header: '活动类型',dataIndex: 'actContent',renderer:actContentType ,align: 'center'},
		{header: '活动费率(%)',dataIndex: 'actFee',align: 'center'},
		{header: '分期期数',dataIndex: 'devNum',align: 'center'},
		{header: '分期产品号',dataIndex: 'devProd',align: 'center'}
//		{header: '费率(%)',dataIndex: 'actFee'}
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
	var deleteMenu = {
		text: '解除绑定',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
           var selects = grid.getSelectionModel().getSelections();
           var actNoList = new Array();
           var mchntNoList = new Array();
            var actContentList = new Array();
           if(selects == null)
           {
           		showAlertMsg("请选择要删除记录",activityForm);
           		return;
           }
           for(var i = 0;i<selects.length;i++){
			   	mchntNoList[i] = selects[i].get('mchtNo');
		   		actNoList[i] = selects[i].get('actNo');
//		   		actContentList[i]=selects[i].get('actContent');
		   }
		   showConfirm('确定要解除绑定这些商户吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20802Action_delete.asp',
//							url: 'T20802Action.asp?method=delete',
							success: function(rsp,opt) {
								
					
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[1].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								mchntNoList: mchntNoList,
								actNoList: actNoList,
//								actContentList:actContentList,
								txnId: '20802',
								subTxnId: '01'
							}
						}
						);
					}
				});
		}
	}
   
    
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
	var menuArr = new Array();
	
	menuArr.push(queryMenu);	//[0]
	menuArr.push(deleteMenu);		//[1]
	

	// 终端信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '营销活动商户删除',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: sm,
		autoExpandColumn: 'mchtNm',
		cm: actColModel,
		tbar: menuArr,
		clicksToEdit: true,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载参与营销活动的商户信息列表......'
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
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelections();
			if(rec != null ) {
				grid.getTopToolbar().items.items[1].enable();
			} else {
				grid.getTopToolbar().items.items[1].disable();
			}
		}
	});
   
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})