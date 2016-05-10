Ext.onReady(function() {
		
	var keyGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=sendTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},			
			{name: 'destId',mapping: 'destId'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'flag',mapping: 'flag'},
			{name: 'type',mapping: 'type'},
			{name: 'mchtNo',mapping: 'mchtNo'}
		])
	});
	
	keyGridStore.load({
		params:{start: 0}
	});
	
	
	
	
	var keyColModel = new Ext.grid.ColumnModel([
		{header: '终端号',dataIndex: 'termId',align: 'center'},
		{header: '商户号',dataIndex: 'mchtNo',width:200,id: 'mchtNo'},
		{header: '渠道',dataIndex: 'destId',width:200},
		{header: '机构',dataIndex: 'brhId',width:200},
		{header: '交易结果',dataIndex: 'flag',width:200,renderer:result},
		{header: '同步类型',dataIndex: 'type',width:200,renderer:oprtype}
	]);
	
	/**
	 * 操作类型转换
	 */
	function oprtype(val) {
		switch(val) {
			case '1' : return '新增同步';
			case '2' : return '修改同步';
		}
	}	
	
	/**
	 * 结果转换
	 */
	function result(val) {
		if(val == '0'){
			return '<font color="gray">无应答</font>';
		}else {
			return '<font color="red">失败</font>';
		}
	}
	var keyMenu = {
			text: '同步',
			width: 85,
			iconCls: 'download',
			disabled: true,
			handler: function() {
				showConfirm('确定要重新同步设备吗？',keyGrid,function(bt) {
					if(bt == 'yes') {
						rec = keyGrid.getSelectionModel().getSelected();
						//showProcessMsg('正在提交，请稍后......');
						Ext.Ajax.request({
							url: 'T10502Action.asp?method=sSend',
							params: {
								termId:rec.get('termId'),
								brhId:rec.get('brhId'),
								type:rec.get('type'),
								destId:rec.get('destId'),
								txnId: '10502',
								subTxnId: '01'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								interval: 4000;
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,keyGrid);
								} else {
									showErrorMsg(rspObj.msg,keyGrid);
								}
								keyGrid.getStore().reload();
							}
						});
						hideProcessMsg();
						
					}
				});
			}
		};
	
	var menuArr = new Array();
	menuArr.push(keyMenu);		// [0]

	
	// 机构密钥信息列表
	var keyGrid = new Ext.grid.GridPanel({
		title: '设备同步',
		iconCls: 'operator',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn:'mchtNo',
		store: keyGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: keyColModel,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载设备同步信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: keyGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//每次在列表信息加载前都将保存按钮屏蔽
//	keyGrid.getStore().on('beforeload',function() {
//
//	});
	
//	keyGrid.on({
//		//在编辑单元格后使保存按钮可用
//		'afteredit': function() {
//		}
//	});
	
	keyGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(keyGrid.getView().getRow(keyGrid.getSelectionModel().last)).frame();
			keyGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [keyGrid],
		renderTo: Ext.getBody()
	});
	
});