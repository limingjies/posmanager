Ext.onReady(function() {
		
	var keyGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
//			url: 'gridPanelStoreAction.asp?storeId=keyInfo'
			url: 'gridPanelStoreAction.asp?storeId=keyInfoFirstRoute'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'insIdCd'
		},[
			{name: 'insIdCd',mapping: 'insIdCd'},
			{name: 'insKeyIdx',mapping: 'insKeyIdx'},
			{name: 'macKey',mapping: 'macKey'},
			{name: 'pinKey',mapping: 'pinKey'},
			{name: 'firstMchtNo',mapping: 'firstMchtNo'},
			{name: 'firstMchtNm',mapping: 'firstMchtNm'},
			{name: 'firstTermId',mapping: 'firstTermId'},
			{name: 'channelNm',mapping: 'channelNm'}
			
		])
	});
	
	keyGridStore.load({
		params:{start: 0}
	});
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
				'<table width=700>',
				
//				'<tr><td><font color=gray>机构PIN1： </font>{instDate:this.formatDate}</td>',
//				'<td><font color=gray>机构PIN2：</font>{instTime:this.formatDate}</td></tr>',
				
				'<tr><td><font color=gray>机构PIN： </font>{pinKey}</td>',
				'<tr><td><font color=gray>机构MAC： </font>{macKey}</td>'	
				
//				'<tr><td colspan=2><font color=gray>备注：</font>{misc1}</td></tr>' 
				
			,{
			formatDate : function(val){
				 if(val!=null &&val.length == 8){
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
				+ val.substring(6, 8);
				}if(val!=null &&val.length == 6){
					return val.substring(0, 2) + ':' + val.substring(2, 4) + ':'
				+ val.substring(4);
				}else{
					return val;
				}
			 }
			}
		)
	});
	
	
	
	var keyColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{id: 'insIdCd',header: '通道机构',dataIndex: 'channelNm',align: 'center'},
		{header: '上游渠道商户',dataIndex: 'firstMchtNm',width:250},
		{header: '上游渠道终端',dataIndex: 'firstTermId',width:200},
		{header: '终端或机构秘钥密文',dataIndex: 'insKeyIdx',width:200}
//		{header: '状态',dataIndex: 'recOprId',width:200,renderer:instKeyStatus}
//		{id:'insMac1',header: '机构MAC1',dataIndex: 'insMac1',width:200},
//		{header: '机构MAC2',dataIndex: 'insMac2',width:200}
//		{header: '机构PIN1',dataIndex: 'insPin1',width:200},
//		{header: '机构PIN2',dataIndex: 'insPin2',width:200}
	]);
	
	function instKeyStatus(val) {
		switch(val) {
			case '1' : return '<font color="green">已生成</font>';
			default : return '<font color="gray">未生成</font>';
		}
	}
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','通道机构：',{
			xtype: 'basecomboselect',
			id: 'querySettleBrhId',
			baseParams: 'SETTLE_BRH_RT',
			hiddenName : 'querySettleBrh',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width : 200
		},'-','上游渠道商户：',{
			xtype: 'basecomboselect',
			id: 'queryFirstMchtCdId',
			baseParams: 'routeFirstMchtCdRt',
			hiddenName: 'queryFirstMchtCd',
			mode:'local',
			triggerAction: 'all',
			editable: true,
			lazyRender: true,
			width: 250
		},'-','上游渠道终端：',{
			xtype: 'textfield',
			id: 'queryFirstTermId',
			name: 'queryFirstTermId',
			vtype:'isEngNum',
			maxLength: 10,
			width: 140
//		},'-','机构密钥索引：',{
//			xtype: 'textfield',
//			id: 'queryInsKeyIdx',
//			name: 'queryInsKeyIdx',
//			vtype:'isEngNum',
//			maxLength: 10,
//			width: 140
		}]
     });
	
	var keyMenu = {
			text: '密钥重置',
			width: 85,
			iconCls: 'add',
			disabled:true,
			handler: function() {
				if(keyGrid.getSelectionModel().hasSelection()) {
					var rec = keyGrid.getSelectionModel().getSelected();
					showConfirm('确定要重置该密钥吗？',keyGrid,function(bt) {
						if(bt == 'yes') {
							//showProcessMsg('正在提交，请稍后......');
							Ext.Ajax.request({
								url: 'T10501Action.asp?method=syKey',
								method: 'post',
								waitMsg: '正在重置密钥，请稍后......',
								params: {
									firstMchtNo: rec.get('firstMchtNo'),
									insIdCd: rec.get('insIdCd'),
									firstTermId:rec.get('firstTermId'),
									txnId: '10501',
									subTxnId: '01'
								},
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										interval: 4000;
//										keyGrid.getStore().commitChanges();
										showSuccessMsg(rspObj.msg,keyGrid);
										keyGrid.getStore().reload();
									} else {
//										keyGrid.getStore().rejectChanges();
										showErrorMsg(rspObj.msg,keyGrid);
									}
//									hideProcessMsg();
								}
							});
							
						}
					});
				}
			}
		};
	
	// 机构密钥信息列表
	var keyGrid = new Ext.grid.GridPanel({
		title: '机构密钥信息',
		iconCls: 'operator',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn:'insIdCd',
		store: keyGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		forceValidation: true,
		cm: keyColModel,
		plugins: rowExpander,
		tbar:  	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				keyGridStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryFirstMchtCdId').setValue('');
				Ext.getCmp('querySettleBrhId').setValue('');
				Ext.getCmp('queryFirstTermId').setValue('');
				//Ext.getCmp('queryInsKeyIdx').setValue('');
			}
		},'-',keyMenu],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
				tbar1.render(this.tbar); 
            },
            'cellclick':selectableCell,
        },
		loadMask: {
			msg: '正在加载密钥信息列表......'
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
	keyGrid.getStore().on('beforeload',function() {
		keyGrid.getTopToolbar().items.items[4].disable();
		Ext.apply(this.baseParams, {
			start: 0,
			querySettleBrh: Ext.getCmp('querySettleBrhId').getValue(),
			queryFirstMchtCd: Ext.get('queryFirstMchtCd').getValue(),
			queryFirstTermId: Ext.get('queryFirstTermId').getValue(),
			//queryInsKeyIdx: Ext.get('queryInsKeyIdx').getValue()
		});
	});
	
//	keyGrid.on({
//		//在编辑单元格后使保存按钮可用
//		'afteredit': function() {
//		}
//	});
	
	keyGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(keyGrid.getView().getRow(keyGrid.getSelectionModel().last)).frame();
			keyGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [keyGrid],
		renderTo: Ext.getBody()
	});
	
});