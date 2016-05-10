Ext.onReady(function() {
	// 查询列表显示区域  主任务
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'id',header: '批量编号',dataIndex: 'batId',sortable : sortableFlag,width:100},
		{header: '批量描述',dataIndex: 'desc',id:'desc',sortable : sortableFlag,width:100},
		{header: '依赖批量编号',dataIndex: 'relBatId',sortable : sortableFlag,width:130},
		{header: '备注',dataIndex: 'misc',sortable : sortableFlag,width:100}
	]);
	// 主任务
	var gridStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=batTaskRelNew'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'batId',mapping: 'batId'},
					{name: 'desc',mapping: 'desc'},
					{name: 'relBatId',mapping: 'relBatId'},
					{name: 'misc',mapping: 'misc'}
				]),
				autoLoad: true
		});

		
	/**********************************第一个TabPanel*************************************/
		var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.GridPanel.tbar,  
                items:[
               			'-','批量编号：',{
					xtype: 'textfield',
					maxLength: 6,
					regex: /^[0-9]*$/,
					regexText: '批量编号必须是数字',
					id: 'batId2',
					name: 'batId2',
					width: 120
	               	},'-','依赖批量编号：',{
					xtype: 'textfield',
					maxLength: 6,
					regex: /^[0-9]*$/,
					regexText: '依赖批量编号必须是数字',
					id: 'batId1',
					name: 'batId1',
					width: 120
	               	}
	            ]  
         }) 
	
	
	
	//转译批处理运行状态
	function batStatus(v) {
		var bat = v.substring(0,1);
		var id = v.substring(2,v.Length);
		if (bat == '0') {
			return '未执行';
		} else if (bat == 'R') {
			return '执行中';
		} else if (bat == 'U') {
			return '未知';
		} else if (bat == 'S') {
			return '<font color="green">执行成功</font>';
		} else if (bat == 'F') {
			return '<font color="red">执行失败</font>'; 
		} else {
			return bat;
		}
	}
	
	
	//列表
    var gridPanel = new Ext.grid.GridPanel({
		title: '新清结算批处理信息列表',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		autoExpandColumn:'desc',
//		enableHdMenu : enableHdMenuFlag,
//		height: 310,
//		buttonAlign: button_Align,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
//		autoScroll: true,
		
		tbar:[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				gridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('batId2').setValue('');
				Ext.getCmp('batId1').setValue('');
				gridStore.load();
			}
			
        
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar2.render(this.tbar); 
                }  
		},
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			Ext.getCmp('butLook').enable();
		}
	});
	gridStore.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				start: 0,
				batId1: Ext.getCmp('batId1').getValue(),
				id: Ext.getCmp('batId2').getValue()
			});
		});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridPanel],
		renderTo: Ext.getBody()
	});
});