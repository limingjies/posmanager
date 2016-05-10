Ext.onReady(function() {
	
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	
	function showDetailInfo(val) {
		return '<span title="' + val.replace(/"/g,'&#34;') + '">' + val + '</span>';
	}
	// 数据集
	var channelInfoStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getChannel'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'busiId',mapping: 'busiId'},
			{name: 'chlName',mapping: 'chlName'},
			{name: 'busiName',mapping: 'busiName'},
			{name: 'area',mapping: 'area'},
			{name: 'busiStatus',mapping: 'busiStatus'},
			{name: 'enDate',mapping: 'enDate'},
			{name: 'keyType',mapping: 'keyType'},
			{name: 'onFlag',mapping: 'onFlag'},
			{name: 'dealType',mapping: 'dealType'},
			{name: 'chlStatus',mapping: 'chlStatus'},
			{name: 'busiId1',mapping: 'busiId1'}
		]),
		autoLoad: true
	}); 
	
	var channelColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '是否可停用',dataIndex: 'onFlag',align: 'center',width: 80,hidden:true},		
		{header: '支付渠道编码',dataIndex: 'busiId1',align: 'center', width: 85,hidden:true},
		{header: '支付渠道',dataIndex: 'chlName',align: 'left', width: 200,renderer:showDetailInfo },
		{header: '业务编码',dataIndex: 'busiId',align: 'center', width: 80,hidden:true},
		{header: '业务',dataIndex: 'busiName',align: 'left', width: 200 ,renderer:showDetailInfo },
		{header: '支持收单区域',dataIndex: 'area',align: 'left', width: 200,renderer:showDetailInfo },
		{header: '启用日期',dataIndex: 'enDate',align: 'center', width:130, renderer:formatDt},
		{header: '密钥类型',dataIndex: 'keyType',align: 'center', width: 100 },
		{header: '渠道状态',dataIndex: 'chlStatus',align: 'center',width: 60,renderer:branchStatus},
		{header: '业务状态',dataIndex: 'busiStatus',align: 'center',width: 60,renderer:branchStatus},
		{header: '支持交易类型',dataIndex: 'dealType',align: 'left',width: 150,renderer:showDetailInfo}
	]);
	
	/*
	 * 查询条件
	 * 支付渠道（下拉选择）、业务、支持交易类型（模糊查询）、渠道状态（启用、停用）
	 */
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[	
				'-','支付渠道：',
				{
					xtype: 'basecomboselect',
					baseParams: 'CHANNEL_ALL',
					fieldLabel: '支付渠道',
					hiddenName: 'queryChannel',
					id:'queryChannelId',
					width: 140,
					editable: true,
					listeners: {
						'select': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('queryChannelId').getValue();
							Ext.getCmp('queryBusinessId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value = '';
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						},
						'change': function() {
							busiStore.removeAll();
							var chlId = Ext.getCmp('queryChannelId').getValue();
							Ext.getCmp('queryBusinessId').setValue('');
							Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value = '';
							SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',chlId,function(ret){
							busiStore.loadData(Ext.decode(ret));
							});
						}
					}
				},'-','业务：',{
					xtype: 'basecomboselect',
					store: busiStore,
					displayField: 'displayField',
					valueField: 'valueField',
					id: 'queryBusinessId',
					hiddenName: 'queryBusiness',
					value:'',
					editable: true,
					width: 140
				},'-',	'支持交易类型：',{
					xtype: 'textfield',
					id: 'queryDealTypeId',
					hiddenName:'queryDealType',
					width: 140
				},'-',	'渠道状态：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','启用'],['1','停用']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryChlStatus',
					id:'queryChlStatusId',
					editable: true,
					//emptyText: '请选择',
					value: "",
					width: 140
				}
	            ]  
         }) 

	
	var grid = new Ext.grid.GridPanel({
		title: '支付渠道查询',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
//		autoExpandColumn: 'channelInfo',
		store: channelInfoStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: channelColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载支付渠道记录列表......'
		},
		tbar: 	[{			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				channelInfoStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryChannelId').setValue('');
				Ext.getCmp('queryBusinessId').setValue('');
				Ext.getCmp('queryDealTypeId').setValue('');			
				Ext.getCmp('queryChlStatusId').setValue('');
				Ext.getDom(Ext.getDoc()).getElementById('queryChannel').value = '';
				Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value = '';
				Ext.getDom(Ext.getDoc()).getElementById('queryDealTypeId').value = '';
				Ext.getDom(Ext.getDoc()).getElementById('queryChlStatus').value = '';
				//重新获取下拉列表
				Ext.getCmp('queryChannelId').store.reload();
				SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
					busiStore.loadData(Ext.decode(ret));
				});
			}							
		},'-',{
			xtype: 'button',
			text: '启用',
			name: 'start',
			id: 'start',
			iconCls: 'accept',
			width: 80,
			disabled: true,
			handler:function(bt) {
				showConfirm('确定要启用该支付渠道吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T110101Action.asp?method=start',
										params : {
											busiId: grid.getSelectionModel().getSelected().get('busiId'),
//											txnId : '110101',
//											subTxnId : '01'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											Ext.getCmp('start').disable();
											Ext.getCmp('stop').disable();
										}
									});
						}
					});
			}
		},'-',{
			xtype: 'button',
			text: '停用',
			name: 'stop',
			id: 'stop',
			iconCls: 'stop',
			width: 80,
			disabled: true,
			handler:function(bt) {
				var onFlag=grid.getSelectionModel().getSelected().get('onFlag');
				if(onFlag == "F"){
					//Ext.getCmp('stop').disable();
					showAlertMsg("有路由规则对应该业务，不可停用！",grid);
					return;
				}
				showConfirm('确定要停用该支付渠道吗？', grid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T110101Action.asp?method=stop',
										params : {
											busiId: grid.getSelectionModel().getSelected().get('busiId'),
//											txnId : '110101',
//											subTxnId : '02'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,grid);
												
											} else {
												showErrorMsg(rspObj.msg, grid);
											}
											grid.getStore().reload();
											Ext.getCmp('start').disable();
											Ext.getCmp('stop').disable();
										}
									});
						}
					});
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
                }  ,
             'cellclick':selectableCell,
        }  ,
		bbar: new Ext.PagingToolbar({
			store: channelInfoStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// 禁用详情显示、启用、停用按钮
	grid.getStore().on('beforeload',function() {
		Ext.getCmp('start').disable();
		Ext.getCmp('stop').disable();
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			/* onFlag:
			 *1.可对“停用”状态的渠道进行“启用”操作，操作后状态变为“启用”	
			 *2.可对“启用”状态的渠道进行“停用”操作，操作后状态变为“停用”。如果有“启用”状态的路由规则对应到该业务，则提示“有路由规则对应该业务，不可停用！”。
			 */
			var busiStatus = grid.getSelectionModel().getSelected().get('busiStatus');
			if(busiStatus == '0'){
				Ext.getCmp('start').disable();
				Ext.getCmp('stop').enable();
			}else if(busiStatus=="1"){
				Ext.getCmp('stop').disable();
				Ext.getCmp('start').enable();
			}
		}
	});
	
	channelInfoStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryChannel: Ext.getCmp('queryChannelId').getValue(),
			queryBusiness: Ext.getDom(Ext.getDoc()).getElementById('queryBusiness').value,
			queryDealType:Ext.get('queryDealTypeId').getValue(),
			queryChlStatus:Ext.getDom(Ext.getDoc()).getElementById('queryChlStatus').value

		});
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});

