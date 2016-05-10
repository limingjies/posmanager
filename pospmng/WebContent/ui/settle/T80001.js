/**
 * 商户批量开户
 * 显示：审核通过的未开户的商户
 * 操作：选择需要开户的商户进行开户
 */

Ext.onReady(function() {
	
//	==================================主规则====================================
	// 数据集
	var storeUnOpenMcht = new Ext.data.Store({
		//id:'storeUnOpenMcht',
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getUnOpenVirtualAcctMcht'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'mchtStatus',mapping: 'mchtStatus'}
		]),
		autoLoad: true
	}); 
	
	
	var colModelUnOpenMcht = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '商户号',dataIndex: 'mchtNo',width: 200,align: 'center'},
		{header: '商户名称',dataIndex: 'mchtName',width: 250,align: 'left',id:'unopenMcht'},
		{header: '商户状态',dataIndex: 'mchtStatus',width: 100,align: 'center', renderer: mchntSt}
	]);  
         
    var gridUnOpenMcht = new Ext.grid.GridPanel({
		width:460,
		title: '批量开户-商户',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		sm : new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		store: storeUnOpenMcht,
		cm: colModelUnOpenMcht,
		forceValidation: true,
		loadMask: {
			msg: '正在加载商户记录列表......'
		},
		tbar: [
		       {
				xtype: 'button',
				text: '开户',
				name: 'btOpen',
				id: 'btOpen',
				iconCls: 'accept',
				width: 80,
				handler: function() {
					var mchtIds = "";
					var records = gridUnOpenMcht.getSelectionModel().getSelections();
					var len = records.length;
					for (var i = 0; i < len; i++) {
						 var mchtNo=records[i].get('mchtNo');
						 if(mchtIds == ""){
							 mchtIds += mchtNo;
						 }else{							 
							 mchtIds += ',' + mchtNo ;
						 }
					  }
					if(len < 1){
						Ext.Msg.alert('提示信息：','    请选择需要开户的商户!   ');
						return;
					}
					showConfirm('确认要进行虚拟账户开户吗？',gridUnOpenMcht,function(bt) {
						if(bt == 'yes') {
							showProcessMsg('正在提交信息，请稍后......');
							Ext.Ajax.request({
								url: 'T80001Action.asp?method=open',
								params: {
									mchtIds: mchtIds,
									txnId: '80001',
									subTxnId: '01'
								},
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										//showSuccessMsg(rspObj.msg,gridUnOpenMcht);
										Ext.Msg.alert('提示信息：','    '+rspObj.msg+'!   ');
										// 重新加载商户信息
										gridUnOpenMcht.getStore().reload();
									} else {
										Ext.Msg.alert('提示信息：','    开户服务调用失败!   ');
									}
								}
							});
							hideProcessMsg();
						}
					});
				 }
		       }
		],
		listeners : {
			'cellclick':selectableCell
        } ,
		bbar: new Ext.PagingToolbar({
			store: storeUnOpenMcht,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
    gridUnOpenMcht.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridUnOpenMcht.getView().getRow(gridUnOpenMcht.getSelectionModel().last)).frame();
		//	Ext.getCmp('edit').enable();
		}
	});
    
	storeUnOpenMcht.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
		this.removeAll();
	});
	
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridUnOpenMcht],
		renderTo: Ext.getBody()
	});
});

