/**
 * 合作伙伴批量开户
 * 显示：审核通过的未开户的合作伙伴
 * 操作：选择需要开户的合作伙伴进行开户
 */

Ext.onReady(function() {
	
//	==================================主规则====================================
	// 数据集
	var storeUnOpenBrh = new Ext.data.Store({
		//id:'storeUnOpenBrh',
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getUnOpenVirtualAcctBrh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'newBrhNo',mapping: 'newBrhNo'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhStatus',mapping: 'brhStatus'}
		]),
		autoLoad: true
	}); 
	
	
	var colModelUnOpenBrh = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '合作伙伴号',dataIndex: 'brhId',width: 150,align: 'center'},
		{header: '合作伙伴编号',dataIndex: 'newBrhNo',width: 200,align: 'center'},
		{header: '合作伙伴名称',dataIndex: 'brhName',width: 250,align: 'left'},
		{header: '合作伙伴状态',dataIndex: 'brhStatus',width: 100,align: 'center',renderer:function(val){
			switch(val){
			case '0':
				return '待审核';
			case '1':
				return '通过';
			case '2':
				return '退回';
			default:
				return '待审核'
			}
		}}
	]);  
         
    var gridUnOpenBrh = new Ext.grid.GridPanel({
		width:460,
		title: '批量开户-合作伙伴',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		sm : new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		store: storeUnOpenBrh,
		cm: colModelUnOpenBrh,
		forceValidation: true,
		loadMask: {
			msg: '正在加载合作伙伴记录列表......'
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
					var brhIds = "";
					var records = gridUnOpenBrh.getSelectionModel().getSelections();
					var len = records.length;
					for (var i = 0; i < len; i++) {
						 var brhNo=records[i].get('brhId');
						 if(brhIds == ""){
							 brhIds += brhNo;
						 }else{							 
							 brhIds += ',' + brhNo ;
						 }
					  }
					if(len < 1){
						Ext.Msg.alert('提示信息：','    请选择需要开户的合作伙伴!   ');
						return;
					}
					showConfirm('确认要进行虚拟账户开户吗？',gridUnOpenBrh,function(bt) {
						if(bt == 'yes') {
							showProcessMsg('正在提交信息，请稍后......');
							Ext.Ajax.request({
								url: 'T80002Action.asp?method=open',
								params: {
									brhIds: brhIds,
									txnId: '80002',
									subTxnId: '01'
								},
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										//showSuccessMsg(rspObj.msg,gridUnOpenBrh);
										Ext.Msg.alert('提示信息：','    '+rspObj.msg+'!   ');
										// 重新加载合作伙伴信息
										gridUnOpenBrh.getStore().reload();
									} else {
										Ext.Msg.alert('提示信息：','    开户服务调用失败!   ');
									}
								},
								failure:function(){
									Ext.Msg.alert('提示信息：','    开户服务调用失败!   ');
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
			store: storeUnOpenBrh,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
    gridUnOpenBrh.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridUnOpenBrh.getView().getRow(gridUnOpenBrh.getSelectionModel().last)).frame();
		//	Ext.getCmp('edit').enable();
		}
	});
    
	storeUnOpenBrh.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
		this.removeAll();
	});
	
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridUnOpenBrh],
		renderTo: Ext.getBody()
	});
});

