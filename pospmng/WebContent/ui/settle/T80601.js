var gridPanel;
var gridStore;

function infoWindow2(id2){
		var detailForm2 = new Ext.form.FormPanel({
			frame: true,
			height: 80,
			width: 250,
			labelWidth:100,
			layout: 'form',
			waitMsgTarget: true,
			defaults: {
				bodyStyle: 'padding-left: 10px'
			},
			items: [{
				layout: 'form',
				items: [{
					xtype: 'textfield',
					id: 'idUpd2',
					fieldLabel: '任务分配状态',
					width: 100,
					readOnly: true,
					value:'未分配'
				}]
			}],
			buttonAlign: 'center',
			buttons: [{
				text: '提交',
				handler: function() {
					if(detailForm2.getForm().isValid()) {
						detailForm2.getForm().submit({
							url: 'T80601Action.asp?method=resetAsn&id2='+id2,
							waitMsg: '正在更新信息，请稍后......',
							params: {
								txnId: '80601',
								subTxnId: '01'
							},
							success: function(form,action) {
								var config = {
				    					title : "成功提示",
				    					msg : "任务分配状态重置成功",
				    					width : 250,
				    					buttons : Ext.MessageBox.OK,
				    				    icon : "message-success"
				    			};
				    			Ext.MessageBox.show(config);
				    			detailWin2.close();
								gridStore.reload();
							},
							failure: function(form,action) {
								Ext.Msg.alert("失败提示","重置失败");
							}
						});
					}
				}
			}]
		});
		
		
		
		var detailWin2 = new Ext.Window( {
			title : '重置信息',
			layout : 'form',
			width : 250,
			height : 110,
			mode : true,
			closable : true,
			resizable : false,
			items : [detailForm2]
		});
		detailWin2.show();
	};
	
	
Ext.onReady(function() {
	
	var grpIdName = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('GRP_ID_NAME',function(ret){
		grpIdName.loadData(Ext.decode(ret));
	});
	
	// 查询列表显示区域  主任务
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'id',header: '批量编号',dataIndex: 'idUpd',sortable : sortableFlag,width:60},
		{header: '组编号',dataIndex: 'grpId',id:'grpId',sortable : sortableFlag,width:100},
		{header: '批量描述',dataIndex: 'batDescUpd',sortable : sortableFlag,width:130},
		{header: '批处理运行状态',dataIndex: 'batStatusUpd',sortable : sortableFlag,renderer:batStatus,width:100},
		{header: '批量优先级别',dataIndex: 'batLevelUpd',sortable : sortableFlag,width:90},
//		{header: '批处理程序',dataIndex: 'processFuncUpd',sortable : sortableFlag},
//		{header: '任务标志',dataIndex: 'batFlagUpd',id:'batFlags',sortable : sortableFlag,renderer:batFlag,width:150},
		{header: '清算日期',dataIndex: 'stlmDate',sortable : sortableFlag,width:80,renderer: formatDt,align: 'center'},
		{header: '开始时间',dataIndex: 'begTimeUpd',sortable : sortableFlag,width:130,renderer: formatDt,align: 'center'},
		{header: '结束时间',dataIndex: 'endTimeUpd',sortable : sortableFlag,width:130,renderer: formatDt,align: 'center'},
		{header: '时长',dataIndex: 'shichang',sortable : sortableFlag,renderer:toss,width:70},
		{header: '单次提交记录数',dataIndex: 'numCommitUpd',sortable : sortableFlag,width:100},
		{header: '并发进程数',dataIndex: 'childConntUpd',sortable : sortableFlag,width:75}
//		{header: '操作',dataIndex: 'startFlag',sortable:true, renderer: startBegin}
	]);
	// 主任务
	 gridStore = new Ext.data.Store({
				proxy: new Ext.data.HttpProxy({
					url: 'gridPanelStoreAction.asp?storeId=batTaskCtl'
				}),
				reader: new Ext.data.JsonReader({
					root: 'data',
					totalProperty: 'totalCount'
//					idProperty: 'id'
				},[
					{name: 'idUpd',mapping: 'batId'},
					{name: 'grpId',mapping: 'grpId'},
					{name: 'batLevelUpd',mapping: 'batLevel'},
					{name: 'batStatusUpd',mapping: 'batStatus'},
					{name: 'sanStatusUpd',mapping: 'sanStatus'},
					{name: 'batFlagUpd',mapping: 'batFlag'},
					{name: 'begTimeUpd',mapping: 'begTime'},
					{name: 'endTimeUpd',mapping: 'endTime'},
					{name: 'shichang',mapping: 'shichang'},
					{name: 'numCommitUpd',mapping: 'numCommit'},
					{name: 'childConntUpd',mapping: 'childConnt'},
					{name: 'stlmDate',mapping: 'stlmDate'},
					{name: 'batDescUpd',mapping: 'batDesc'}
				]),
				autoLoad: true
		});

	
		var rec;
		// 子任务
		var gridStoreChd = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=batTaskChd'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount',
				idProperty: 'id'
			},[
				{name: 'stlmDt',mapping: 'stlmDt'},
				{name: 'batId',mapping: 'batId'},
				{name: 'chdId',mapping: 'chdId'},
				{name: 'status',mapping: 'status'},
				{name: 'param',mapping: 'param'},
				{name: 'commitPoint',mapping: 'commitPoint'},
				{name: 'failPoint',mapping: 'failPoint'}
			])
	});
		
		gridStoreChd.on('beforeload', function() {
			Ext.apply(this.baseParams, {
				start: 0,
				id: rec.get('idUpd')
			});
		});
		
		// 查询子任务列表显示区域   子任务
		var gridColumnModelChd = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: '清算日期',dataIndex: 'stlmDt',sortable : sortableFlag,renderer: formatDt},
			{header: '批量编号',dataIndex: 'batId',sortable : sortableFlag},
			{header: '子任务编号',dataIndex: 'chdId',sortable : sortableFlag},
			{header: '子任务运行状态',dataIndex: 'status',sortable : sortableFlag,renderer:statusUpd},
			{header: '子任务参数',dataIndex: 'param',sortable : sortableFlag,width:120},
			{header: '断点现场',dataIndex: 'commitPoint',sortable : sortableFlag},
			{header: '失败现场',dataIndex: 'failPoint',sortable : sortableFlag}
		]);

		
		var gridPanelChd = new Ext.grid.EditorGridPanel({
			title: '子任务信息列表',
			store: gridStoreChd,
			height :300,
			enableHdMenu : enableHdMenuFlag,
			autoScroll: true,
			cm: gridColumnModelChd,
			loadMask: {
				msg: '正在加载子任务信息列表......'
			},
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			bbar: new Ext.PagingToolbar({
				store: gridStoreChd,
				pageSize: 10,
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			}),
			buttonAlign: 'center',
			buttons: [{			
				text: '关闭',
			    handler: function() {
				infoWindow3.hide();
			}}]
		});
	
		//转译子任务运行状态
		function statusUpd(val) {
			if (val == '0') {
				return '未执行';
			} else if (val == 'R') {
				return '执行中';
			} else if (val == 'S') {
				return '<font color="green">执行成功</font>';
			} else if (val == 'F') {
				return '<font color="red">执行失败</font>'; 
			} else {
				return val;
			}
		}
		
		// 转义时长
		function toss(val) {
			if (val != '' && val != null) {
				return val + '秒';
			} else {
				return val;
			}
		}
	/**********************************第一个TabPanel*************************************/
	// 顶部查询面板
	var topQueryPanel = new Ext.form.FormPanel({
		frame: true,
		layout: 'column',
		labelWidth: 120,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		items: [{
			columnWidth: .4,
			layout: 'form',
			items: [new Ext.form.TextField({
				id: 'batId2',
				maxLength: 6,
				width: 155,
				regex: /^[0-9]*$/,
				regexText: '批量编号必须是数字',
				fieldLabel: '批量编号'
			})]
		},{
			columnWidth: .6,
			layout: 'form',
			items: [{
				xtype: 'textfield',
				id: 'time',
				vtype: 'isNumber',
				allowBlank: false,
				value: '10',
				width: 155,
				emptyText : '时间为空时无刷新...',
				blankText: '请输入定时刷新间隔时间',
				fieldLabel: '定时刷新时间（秒）'
			}]
		},{
			columnWidth: .6,
			layout: 'form',
			items: [{
				xtype: 'combo',
				store: grpIdName,
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryGrpId',
				id:'queryGrpIds',
				editable: false,
				width: 155,
				emptyText: '请选择',
				fieldLabel: '组编号'
			}]
		}],
		buttons: [{
			id: 'refresh',
			iconCls: 'monitor',
			text: '停止定时刷新',
			enableToggle: true,
			toggleHandler: function(bt,state) {
				if(Ext.getCmp('time').getValue() == '0'){
					Ext.MessageBox.alert("提示","刷新间隔时间必须大于0！");
					this.toggle(false);
					return;
				}	
				if(!Ext.getCmp('time').isValid()){
					Ext.MessageBox.alert("提示","请输入正确的刷新间隔时间！");
					this.toggle(false);
					return;
				}
				// 监控模式
				if(state) {
					var time = Ext.getCmp('time').getValue();
					//Ext.getCmp('time').setReadOnly(true);
					Ext.getCmp('time').disable();
					this.setText('停止定时刷新');
					this.setIconClass('monitor');
					var a = parseFloat(Ext.getCmp('time').getValue())*1000;
					
					init(a);
				} else {
					var time2 = Ext.getCmp('time').getValue();
					//Ext.getCmp('time').setReadOnly(false);
					Ext.getCmp('time').enable();
					this.setText('启动定时刷新');
					this.setIconClass('play');
					 
					clearInt();
				}
			}
		},{
			text: '查询',
			handler: function() {
				if(topQueryPanel.getForm().isValid()){
					gridStore.load();
				}
			}
		},{
			text: '重填',
			handler: function() {
				//alert(parseInt(Ext.getCmp('time').getValue()));
				//alert(Ext.getCmp('time').disabled);
				if(Ext.getCmp('time').disabled){
					Ext.getCmp('batId2').setValue('');
					Ext.getCmp('queryGrpIds').setValue('');
				}else{
					topQueryPanel.getForm().reset();
				}
			}
		}]
	});
	// 加载就查询
//	gridStore.load();
	// 加载就刷新
	initb();
	
	function initb() {
		Ext.getCmp('refresh').toggle(true);
	}
	//定时刷新
	var setInt;
	function init(a){
		setInt = setInterval(function() {
			gridStore.reload();
		}, parseInt(a));
	}

	function clearInt(){
		clearInterval(setInt);
	}
	
	
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
//			return '<font color="red">执行失败</font>&nbsp;&nbsp;&nbsp;' + "<a href=\"javascript:infoWindow('"+id+"');\">" + "重置" + "</a>"; 
			return '<font color="red">执行失败</font>'; 
		} else {
			return bat;
		}
	}
	
	//转译任务分配状态
	function asnStatus(v) {
		var asn = v.substring(0,1);
		var id2 = v.substring(2,v.Length);
		if (asn == '0') {
			return '未分配';
		} else if (asn == 'R') {
			return '分配中';
		} else if (asn == 'S') {
			return '<font color="green">分配成功</font>';
		} else if (asn == 'F') {
			return '<font color="red">分配失败</font>&nbsp;&nbsp;&nbsp;' + "<a href=\"javascript:infoWindow2('"+id2+"');\">" + "重置" + "</a>"; 
		} else {
			return asn;
		}
	}
	
	//转译任务标志
	function batFlag(val) {
		if (val == '00') {
			return '定时任务不支持断点';
		} else if (val == '01') {
			return '定时任务支持断点';
		} else if (val == '10') {
			return '批量运行任务不支持断点';
		} else if (val == '11') {
			return '批量运行任务支持断点';
		} else if (val == '20') {
			return '人工任务不支持断点';
		} else if (val == '21') {
			return '人工任务支持断点';
		} else if (val == '30') {
			return '联机批量不支持断点';
		} else if (val == '31') {
			return '联机批量支持断点';
		} else {
			return val;
		}
	}
	//列表
	    gridPanel = new Ext.grid.GridPanel({
		title: '批处理信息列表',
		store: gridStore,
		height: 400,
//		autoHeight:true,
		enableHdMenu : enableHdMenuFlag,
//		buttonAlign: button_Align,
		cm: gridColumnModel,
		autoExpandColumn:'grpId',
		autoScroll: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		forceValidation: true,
		//loadMask: {
			//msg: '正在加载信息列表......'
		//},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
//			pageSize: System[QUERY_RECORD_COUNT],
			pageSize: 10,
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		tbar:  [{
			text: '查看子任务信息',
			xtype: 'button',
			id : 'butLook',
//			disabled:true,
			iconCls: 'detail',
			handler: function() {
				if(!gridPanel.getSelectionModel().hasSelection()){
					showAlertMsg('请先选择记录!', gridPanel);
					return;
				}
				rec = gridPanel.getSelectionModel().getSelected();
				gridStoreChd.load();
				infoWindow3.show();
			}
		}],
		listeners:{
			'cellclick':selectableCell
		}
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
			id: Ext.getCmp('batId2').getValue(),
			queryGrpId:Ext.getCmp('queryGrpIds').getValue()
		});
//		Ext.getCmp('butLook').disable();
	});
		
	var infoWindow3 = new Ext.Window( {
		title : '子任务信息',
		animateTarget : 'butLook',
		width : 800,
		//height :400,
		autoHeight : true,
		frame: true,
		border: true,
		initHidden: true,
		resizable: false,
		modal: true,
		layout: 'fit',
		closeAction: 'hide',
		items : [gridPanelChd]
});
	// 第二个主面板
	var secondMainPanel = new Ext.Panel({
		title: '批处理查询',
		frame: true,
		borde: true,
		autoHeight: true,
		autoScroll: true,
		items: [topQueryPanel,gridPanel]
	});
	
	var mainPanel = new Ext.Viewport({
		layout: 'fit',
		autoScroll: true,
		frame: false,
		borde: false,
		renderTo: Ext.getBody(),
		items: [secondMainPanel]
	});
	
	
});