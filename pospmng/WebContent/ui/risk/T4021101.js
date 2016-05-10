Ext.onReady(function() {
	
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
	
	var riskAlarmLvl = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('ALARM_LVL',function(ret){
		riskAlarmLvl.loadData(Ext.decode(ret));
	});
	
//========================================回执图片查看开始========================================	
	
	var custom = new Ext.Resizable('showBigPic', {
		    wrap:true,
		    pinned:true,
		    minWidth: 50,
		    hidden:true,
		    minHeight: 50,
		    preserveRatio: true,
		    dynamic:true,
		    handles: 'all',
		    resizable:true,
		    draggable:true
		});
	var customEl = custom.getEl();
	document.body.insertBefore(customEl.dom, document.body.firstChild);
	
	var rollTimes=0; //向上滚加1，向下滚减1
	var originalWidth =0; //图片缩放前的原始宽度
	var originalHeight = 0; //图片缩放前的原始高度
	
	customEl.on('dblclick', function(){
		customEl.puff();
		rollTimes =0;
//		custom.destroy(); 
		customEl.hide(true);
//		Ext.getCmp('receiptDtl').enable();
	});
	
	function showPIC(imagPath,width,height){
//		Ext.getCmp('receiptDtl').disable();
 		custom.resizeTo(width, height);

 		document.getElementById('showBigPic').src = "";
 		document.getElementById('showBigPic').src = Ext.contextPath + '/PrintImage?fileName=' + imagPath;
 		document.getElementById('showBigPic').onmousewheel = bigimg;
 		originalWidth = document.getElementById('showBigPic').width;
 		originalHeight = document.getElementById('showBigPic').height;
 		
// 		custom.draggable=true;
// 		customEl.draggable=true;
 		customEl.center();
	    customEl.show(true);
 	}
 	
	function bigimg(){
		var zoom = 0.05;
		var rollDirect = event.wheelDelta;
		if(rollDirect>0){
			rollTimes++;
		}
		if(rollDirect<0){
			rollTimes--;
		}
		if(this.height<50 && rollDirect<0){
			return false;
		}
		var cwidth = originalWidth*(1+zoom*rollTimes);
		var cheight = originalHeight*(1+zoom*rollTimes);
		custom.resizeTo(cwidth, cheight);
		return false;
	}
//========================================回执图片查看结束========================================
 	
 	
	//******************关联交易部分**********开始*********************************************
	
	var alarmTxnStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=alarmTxn'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'transState',mapping: 'transState'},
			{name: 'rspCode',mapping: 'rspCode'},
			{name: 'cheatTp',mapping: 'cheatTp'},
			{name: 'cheatFlag',mapping: 'cheatFlag'},
			{name: 'cautionFlag',mapping: 'cautionFlag'},
			{name: 'receiptFlag',mapping: 'receiptFlag'},
			{name: 'blockFlag',mapping: 'blockFlag'},
			{name: 'imagPath',mapping: 'imagPath'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'settleFlag',mapping: 'settleFlag'},
			{name: 'txnNo',mapping: 'txnNo'}
			
		]),
		autoLoad: true
	}); 
	
	var alarmTxnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '交易类型',dataIndex: 'txnNum',align: 'left',width: 80,id:'txnNum'},
		{header: '交易时间',dataIndex: 'instDate',align: 'center',width: 140,renderer:formatDt},
		{header: '系统流水',dataIndex: 'sysSeqNum',align: 'left',width: 80,id:'txnNum'},
		{header: '所属商户',dataIndex: 'mchtNm',id:'mchtNames',align: 'left',width:150},
		{header: '交易金额',dataIndex: 'amtTrans',width: 80,align: 'right'},
		{header: '交易卡号',dataIndex: 'pan',width: 140,align: 'left'},
		{header: '交易结果',dataIndex: 'transState',width: 70,align: 'center',renderer: txnSta},
		{header: '清算状态',dataIndex: 'settleFlag',width: 60,align: 'center',renderer: settleStatus},
		{header: '提示状态',dataIndex: 'cautionFlag',width: 60,align: 'center',renderer: cautionFlag},
		{header: '调单状态',dataIndex: 'receiptFlag',width: 60,align: 'center',renderer: receiptFlag},
		{header: '冻结状态',dataIndex: 'blockFlag',width: 60,align: 'center',renderer: blockFlag},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 70 ,align: 'left'},
		{header: '交易应答',dataIndex: 'rspCode',width: 120,align: 'left'}
	]);
	
	 var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','系统流水：       ',{
					xtype: 'textfield',
					name: 'querySysSeqNum',
					id: 'querySysSeqNum',
					vtype: 'isNumber',
					width: 120
				},
               			'-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					 vtype: 'isNumber',
					width: 120
				},
				/*,'-','商户编号：'
				,{
					xtype: 'dynamicCombo',
					methodName: 'getMchntId',
					hiddenName: 'queryCardAccpId',
					width: 255,
					editable: true,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true
				},*/
				'-','提示状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未提示'],['1','已提示']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryCautionFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryCautionFlagId',
					width: 140
				},'-','调单状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未调单'],['1','已调单'],['2','已回执']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryReceiptFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryReceiptFlagId',
					width: 140
				},'-','冻结状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['1','未冻结'],['0','已冻结']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryBlockFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryBlockFlagId',
					width: 140
				}
				]  
         }) 
         
	
         
	var alarmTxnGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 480,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'mchtNames',
		store: alarmTxnStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: alarmTxnColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险警报相关交易列表......'
		},
		tbar: 	[{
				xtype: 'button',
				text : '风险提醒',
				id:'remindTxn',
				width : 80,
				iconCls : 'mchnt',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此交易风险提醒吗？', alarmTxnGrid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40211Action.asp?method=remindTxn',
									params : {
										alarmId: alarmId,
			 							alarmSeq: alarmSeq,
										instDate: alarmTxnGrid.getSelectionModel().getSelected().get('instDate'),
										sysSeqNum: alarmTxnGrid.getSelectionModel().getSelected().get('sysSeqNum'),
										txnId : '40211',
										subTxnId : '02'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, alarmTxnGrid);

										} else {
											showErrorMsg(rspObj.msg, alarmTxnGrid);
										}
										alarmTxnGrid.getStore().reload();
									}
								});
							}
						})
				}
			},'-',{
				xtype: 'button',
				text : '调单',
				id:'receiptTxn',
				width : 80,
				iconCls : 'script_16x16',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此交易进行调单吗？', alarmTxnGrid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40211Action.asp?method=receiptTxn',
									waitMsg : '正在提交，请稍后......',
									params : {
										alarmId: alarmId,
			 							alarmSeq: alarmSeq,
										instDate: alarmTxnGrid.getSelectionModel().getSelected().get('instDate'),
										sysSeqNum: alarmTxnGrid.getSelectionModel().getSelected().get('sysSeqNum'),
										txnId : '40211',
										subTxnId : '03'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, alarmTxnGrid);

										} else {
											showErrorMsg(rspObj.msg, alarmTxnGrid);
										}
										alarmTxnGrid.getStore().reload();
									}
								});
							}
						})
				}
			},'-',{
				xtype: 'button',
				text : '查看回执',
				id:'receiptDtl',
				width : 80,
				iconCls : 'detail',//recover
				disabled : true,
				handler:function() {
//					showInputMsg('提示','请输入调账备注',true,acct);
					var imagPath=alarmTxnGrid.getSelectionModel().getSelected().get('imagPath');
					var width=alarmTxnGrid.getSelectionModel().getSelected().get('width');
					var height=alarmTxnGrid.getSelectionModel().getSelected().get('height');
					if(imagPath!=null&&imagPath!=''){
//						showRiskImg( imagPath,alarmTxnGrid);
						showPIC(imagPath,width,height);
					}else{
						showErrorMsg('该回执图片路径不存在，请重新刷新选择！', alarmTxnGrid);
					}
				}
			},'-',{
				xtype: 'button',
				text : '冻结交易',
				id:'freezeTxn',
				width : 80,
				iconCls : 'lock',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此交易进行冻结吗？', alarmTxnGrid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40211Action.asp?method=freezeTxn',
									waitMsg : '正在提交，请稍后......',
									params : {
										alarmId: alarmId,
			 							alarmSeq: alarmSeq,
										instDate: alarmTxnGrid.getSelectionModel().getSelected().get('instDate'),
										sysSeqNum: alarmTxnGrid.getSelectionModel().getSelected().get('sysSeqNum'),
										txnId : '40211',
										subTxnId : '11'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, alarmTxnGrid);

										} else {
											showErrorMsg(rspObj.msg, alarmTxnGrid);
										}
										alarmTxnGrid.getStore().reload();
									}
								});
							}
						})
				}
			},'-',{
				xtype: 'button',
				text : '解冻交易',
				id:'recoverTxn',
				width : 80,
				iconCls : 'unlock',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此交易进行解冻吗？', alarmTxnGrid, function(bt) {
							// 如果点击了提示的确定按钮
							if (bt == "yes") {
								Ext.Ajax.request({
									url : 'T40211Action.asp?method=recoverTxn',
									waitMsg : '正在提交，请稍后......',
									params : {
										alarmId: alarmId,
			 							alarmSeq: alarmSeq,
										instDate: alarmTxnGrid.getSelectionModel().getSelected().get('instDate'),
										sysSeqNum: alarmTxnGrid.getSelectionModel().getSelected().get('sysSeqNum'),
										txnId : '40211',
										subTxnId : '12'
									},
									success : function(rsp, opt) {
										var rspObj = Ext
												.decode(rsp.responseText);
										if (rspObj.success) {
											showSuccessMsg(rspObj.msg, alarmTxnGrid);

										} else {
											showErrorMsg(rspObj.msg, alarmTxnGrid);
										}
										alarmTxnGrid.getStore().reload();
									}
								});
							}
						})
				}
			},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				alarmTxnStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('querySysSeqNum').setValue('');
				Ext.getCmp('queryCautionFlagId').setValue('');
				Ext.getCmp('queryReceiptFlagId').setValue('');
				Ext.getCmp('queryBlockFlagId').setValue('');
				
				alarmTxnStore.load();
			}	
		}],
		listeners : {       
            'render' : function() {  
					tbar1.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: alarmTxnStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	alarmTxnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(alarmTxnGrid.getView().getRow(alarmTxnGrid.getSelectionModel().last)).frame();
			
			var cautionFlag=alarmTxnGrid.getSelectionModel().getSelected().get('cautionFlag');
			if(cautionFlag=='0'){
				Ext.getCmp('remindTxn').enable();
			}else{
				Ext.getCmp('remindTxn').disable();
			}
			
			var receiptFlag=alarmTxnGrid.getSelectionModel().getSelected().get('receiptFlag');
			if(receiptFlag=='0'){
				Ext.getCmp('receiptTxn').enable();
				Ext.getCmp('receiptDtl').disable();
			}else if(receiptFlag=='2'){
				Ext.getCmp('receiptTxn').disable();
				Ext.getCmp('receiptDtl').enable();
			}else {
				Ext.getCmp('receiptTxn').disable();
				Ext.getCmp('receiptDtl').disable();
			}
			
			var blockFlag=alarmTxnGrid.getSelectionModel().getSelected().get('blockFlag');
			if(blockFlag=='0'){
				var txnNo=alarmTxnGrid.getSelectionModel().getSelected().get('txnNo');
				var transState=alarmTxnGrid.getSelectionModel().getSelected().get('transState');
				var settleFlag=alarmTxnGrid.getSelectionModel().getSelected().get('settleFlag');
				if((txnNo=='1101'||txnNo=='1091')&&transState=='1'&&settleFlag!='2'){
					Ext.getCmp('freezeTxn').enable();
				}else{
					Ext.getCmp('freezeTxn').disable();
				}
				
				Ext.getCmp('recoverTxn').disable();
			}else if(blockFlag=='1'){
				Ext.getCmp('freezeTxn').disable();
				Ext.getCmp('recoverTxn').enable();
			}else {
				Ext.getCmp('freezeTxn').disable();
				Ext.getCmp('recoverTxn').disable();
			}
		}
	});
	// 禁用编辑按钮
	alarmTxnGrid.getStore().on('beforeload',function() {
		Ext.getCmp('remindTxn').disable();
		Ext.getCmp('receiptTxn').disable();
		Ext.getCmp('receiptDtl').disable();
		Ext.getCmp('freezeTxn').disable();
		Ext.getCmp('recoverTxn').disable();
	});
	alarmTxnStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			querySysSeqNum: Ext.getCmp('querySysSeqNum').getValue(),
			queryCautionFlag: Ext.get('queryCautionFlag').getValue(),
			queryReceiptFlag: Ext.get('queryReceiptFlag').getValue(),
			queryBlockFlag: Ext.get('queryBlockFlag').getValue(),
			queryAlarmId:alarmId,
			queryAlarmSeq:alarmSeq
			
				
		});
	});
	//******************关联交易部分**********结束********
	
	
	//******************关联商户部分**********开始********
	var alarmMchtStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=alarmMcht'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'cardAccpIdName',mapping: 'cardAccpIdName'},
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'alarmNum',mapping: 'alarmNum'},
			{name: 'alarmNumTotal',mapping: 'alarmNumTotal'},
			{name: 'cautionFlag',mapping: 'cautionFlag'},
			{name: 'blockSettleFlag',mapping: 'blockSettleFlag'},
			{name: 'blockMchtFlag',mapping: 'blockMchtFlag'},
			{name: 'cheatNum',mapping: 'cheatNum'}
			
		]),
		autoLoad: true
	}); 
	
	var alarmMchtColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户号',dataIndex: 'cardAccpIdName',align: 'left',width: 120,id:'cardAccpIdName'},
		{header: '风险等级',dataIndex: 'riskLvl',align: 'center',width: 100},
		{header: '当日违规次数',dataIndex: 'alarmNum',width: 120,align: 'center'},
		{header: '违规总次数',dataIndex: 'alarmNumTotal',width: 100,align: 'center'},
		{header: '涉及欺诈次数',dataIndex: 'cheatNum',width: 100 ,align: 'center'},
		{header: '提示状态',dataIndex: 'cautionFlag',width: 80,align: 'center',renderer: cautionFlag},
		{header: '冻结结算状态',dataIndex: 'blockSettleFlag',width: 100,align: 'center',renderer: blockMchtFlag},
		{header: '冻结交易状态',dataIndex: 'blockMchtFlag',width: 100,align: 'center',renderer: blockMchtFlag}
	]);
	
	 var tbarMcht1 = new Ext.Toolbar({  
                renderTo: Ext.grid.GridPanel.tbar,  
                items:[
               			
				/*,'-','商户编号：'
				,{
					xtype: 'dynamicCombo',
					methodName: 'getMchntId',
					hiddenName: 'queryCardAccpId',
					width: 255,
					editable: true,
					id: 'idqueryCardAccpId',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true
				},*/
				'-','提示状态：',
					{
					xtype: 'combo',
					id:'queryMchtCautionFlagId',
					hiddenName: 'queryMchtCautionFlag',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未提示'],['1','已提示']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					editable: false,
					emptyText: '请选择',
					width: 140
					
				},'------------------------------','-','冻结结算状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未冻结'],['1','已冻结']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryBlockSettleFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryBlockSettleFlagId',
					width: 140
				},'------------------------------','-','冻结交易状态：',
					{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','未冻结'],['1','已冻结']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'queryBlockMchtFlag',
					editable: false,
					emptyText: '请选择',
					id:'queryBlockMchtFlagId',
					width: 140
				}
				]  
         }) 
         
	
         
	var alarmMchtGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 480,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'cardAccpIdName',
		store: alarmMchtStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: alarmMchtColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险警报相关商户列表......'
		},
		tbar: 	[{
				xtype: 'button',
				text : '风险提醒',
				id:'remindMcht',
				width : 80,
				iconCls : 'mchnt',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户风险提醒吗？', alarmMchtGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=remindMcht',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									cardAccpId: alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId'),
									txnId : '40211',
									subTxnId : '04'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmMchtGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmMchtGrid);
									}
									alarmMchtGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '商户冻结',
				id:'blockMcht',
				width : 80,
				iconCls : 'stop',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户冻结吗？', alarmMchtGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=blockMcht',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									cardAccpId: alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId'),
									txnId : '40211',
									subTxnId : '05'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmMchtGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmMchtGrid);
									}
									alarmMchtGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '商户解冻',
				id:'recoverMcht',
				width : 80,
				iconCls : 'accept',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户解冻吗？', alarmMchtGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=recoverMcht',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									cardAccpId: alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId'),
									txnId : '40211',
									subTxnId : '06'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmMchtGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmMchtGrid);
									}
									alarmMchtGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '结算冻结',
				id:'blockSettle',
				width : 80,
				iconCls : 'lock',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户的结算冻结吗？', alarmMchtGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=blockSettle',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									cardAccpId: alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId'),
									txnId : '40211',
									subTxnId : '09'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmMchtGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmMchtGrid);
									}
									alarmMchtGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '结算解冻',
				id:'recoverSettle',
				width : 80,
				iconCls : 'unlock',//recover
//				iconCls : 'start',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对此商户的结算解冻吗？', alarmMchtGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=recoverSettle',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									cardAccpId: alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId'),
									txnId : '40211',
									subTxnId : '10'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmMchtGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmMchtGrid);
									}
									alarmMchtGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
			xtype: 'button',
			text: '查询',
			name: 'queryMcht',
			id: 'queryMcht',
			iconCls: 'query',
			width: 80,
			handler:function() {
				alarmMchtStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetMcht',
			id: 'resetMcht',
			iconCls: 'reset',
			width: 80,
			handler:function() {
//				Ext.getCmp('idqueryCardAccpId').setValue('');
				Ext.getCmp('queryMchtCautionFlagId').setValue('');
				Ext.getCmp('queryBlockSettleFlagId').setValue('');
				Ext.getCmp('queryBlockMchtFlagId').setValue('');
				
				alarmMchtStore.load();
			}	
		},'-',{
			
			xtype: 'button',
			text: '查看详细信息',
			name: 'detailMcht',
			id: 'detailMcht',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {

//				bt.disable();
//				setTimeout(function(){bt.enable()},2000);
				var cardAccpId=alarmMchtGrid.getSelectionModel().getSelected().get('cardAccpId');
				var alarmNum=alarmMchtGrid.getSelectionModel().getSelected().get('alarmNum');
				var alarmNumTotal=alarmMchtGrid.getSelectionModel().getSelected().get('alarmNumTotal');
				var cheatNum=alarmMchtGrid.getSelectionModel().getSelected().get('cheatNum');
				showRiskMchtDetail(alarmId,alarmSeq,cardAccpId,alarmNum,alarmNumTotal,cheatNum,alarmMchtGrid);
			}
		}],
		listeners : {       
            'render' : function() {  
					tbarMcht1.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: alarmMchtStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	alarmMchtGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(alarmMchtGrid.getView().getRow(alarmMchtGrid.getSelectionModel().last)).frame();
			Ext.getCmp('detailMcht').enable();
			
			var cautionFlagMcht=alarmMchtGrid.getSelectionModel().getSelected().get('cautionFlag');
			if(cautionFlagMcht=='0'){
				Ext.getCmp('remindMcht').enable();
			}else{
				Ext.getCmp('remindMcht').disable();
			}
			
			var blockMchtFlag=alarmMchtGrid.getSelectionModel().getSelected().get('blockMchtFlag');
			if(blockMchtFlag=='0'){
				Ext.getCmp('blockMcht').enable();
				Ext.getCmp('recoverMcht').disable();
			}else if(blockMchtFlag=='1'){
				Ext.getCmp('blockMcht').disable();
				Ext.getCmp('recoverMcht').enable();
			}else {
				Ext.getCmp('blockMcht').disable();
				Ext.getCmp('recoverMcht').disable();
			}
			
			var blockSettleFlag=alarmMchtGrid.getSelectionModel().getSelected().get('blockSettleFlag');
			if(blockSettleFlag=='0'){
				Ext.getCmp('blockSettle').enable();
				Ext.getCmp('recoverSettle').disable();
			}else if(blockSettleFlag=='1'){
				Ext.getCmp('blockSettle').disable();
				Ext.getCmp('recoverSettle').enable();
			}else {
				Ext.getCmp('blockSettle').disable();
				Ext.getCmp('recoverSettle').disable();
			}
			
			
			
		}
	});
	
	// 禁用编辑按钮
	alarmMchtGrid.getStore().on('beforeload',function() {
		Ext.getCmp('blockMcht').disable();
		Ext.getCmp('recoverMcht').disable();
		Ext.getCmp('remindMcht').disable();
		Ext.getCmp('detailMcht').disable();
		Ext.getCmp('blockSettle').disable();
		Ext.getCmp('recoverSettle').disable();
		
	});
	
	alarmMchtStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
//			queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
			
			queryMchtCautionFlag: Ext.get('queryMchtCautionFlag').getValue(),
			queryBlockSettleFlag: Ext.get('queryBlockSettleFlag').getValue(),
			queryBlockMchtFlag: Ext.get('queryBlockMchtFlag').getValue(),
			queryAlarmId:alarmId,
			queryAlarmSeq:alarmSeq
				
		});
	});
	
	//******************关联商户部分**********结束********
	
	
	//******************关联银行部分**********开始********
	var alarmCardStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=alarmCard'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'pan',mapping: 'pan'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'alarmNum',mapping: 'alarmNum'},
			{name: 'alarmNumTotal',mapping: 'alarmNumTotal'},
			{name: 'cheatNum',mapping: 'cheatNum'},
			{name: 'cardSta',mapping: 'cardSta'}
		]),
		autoLoad: true
	}); 
	
	var alarmCardColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '卡号',dataIndex: 'pan',id:'pan',align: 'left',width: 100},
		{header: '卡类型',dataIndex: 'cardTp',width: 150,align: 'center',renderer:routeCardTp},
		{header: '当日违规次数',dataIndex: 'alarmNum',width: 120 ,align: 'center'},
		{header: '违规总次数',dataIndex: 'alarmNumTotal',width: 120,align: 'center'},
		{header: '欺诈次数',dataIndex: 'cheatNum',width: 120,align: 'center'},
		{header: '卡状态',dataIndex: 'cardSta',width: 150,align: 'center',renderer: blackCard}
	]);
	
	 var tbarCard = new Ext.Toolbar({  
                renderTo: Ext.grid.GridPanel.tbar,  
                items:[
				'-','卡号：',
					{
					xtype: 'textfield',
					name: 'queryPan',
					id: 'queryPan',
					vtype: 'isNumber',
					width: 120
				}
				]  
         }) 
         
	
         
	var alarmCardGrid = new Ext.grid.GridPanel({
//		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		height: 480,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'pan',
		store: alarmCardStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: alarmCardColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载风险警报相关卡列表......'
		},
		tbar: 	[
			{
				xtype: 'button',
				text : '加入黑名单',
				id:'blockCard',
				width : 80,
				iconCls : 'stop',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要把此卡加入黑名单吗？', alarmCardGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=blockCard',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									pan: alarmCardGrid.getSelectionModel().getSelected().get('pan'),
									txnId : '40211',
									subTxnId : '07'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmCardGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmCardGrid);
									}
									alarmCardGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
				xtype: 'button',
				text : '解除黑名单',
				id:'recoverCard',
				width : 80,
				iconCls : 'accept',//recover
				disabled : true,
				handler:function() {
					showConfirm('确定要对解除此卡的黑名单吗？', alarmCardGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							Ext.Ajax.request({
								url : 'T40211Action.asp?method=recoverCard',
								params : {
									alarmId: alarmId,
		 							alarmSeq: alarmSeq,
									pan: alarmCardGrid.getSelectionModel().getSelected().get('pan'),
									txnId : '40211',
									subTxnId : '08'
								},
								success : function(rsp, opt) {
									var rspObj = Ext
											.decode(rsp.responseText);
									if (rspObj.success) {
										showSuccessMsg(rspObj.msg, alarmCardGrid);

									} else {
										showErrorMsg(rspObj.msg, alarmCardGrid);
									}
									alarmCardGrid.getStore().reload();
								}
							});
						}
					})
				}
			},'-',{
			xtype: 'button',
			text: '查询',
			name: 'queryCard',
			id: 'queryCard',
			iconCls: 'query',
			width: 80,
			handler:function() {
				alarmCardStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'resetCard',
			id: 'resetCard',
			iconCls: 'reset',
			width: 80,
			handler:function() {
//				Ext.getCmp('idqueryCardAccpId').setValue('');
				Ext.getCmp('queryPan').setValue('');
				
				alarmCardStore.load();
			}	
		},'-',{
			
			xtype: 'button',
			text: '查看详细信息',
			name: 'detailCard',
			id: 'detailCard',
			iconCls: 'detail',
			width: 80,
			disabled: true,
			handler:function(bt) {

//				bt.disable();
//				setTimeout(function(){bt.enable()},2000);
				var pan=alarmCardGrid.getSelectionModel().getSelected().get('pan');
				var alarmCardNum=alarmCardGrid.getSelectionModel().getSelected().get('alarmNum');
				var alarmCardNumTotal=alarmCardGrid.getSelectionModel().getSelected().get('alarmNumTotal');
				var cheatCardNum=alarmCardGrid.getSelectionModel().getSelected().get('cheatNum');
				showRiskCardDetail(alarmId,alarmSeq,pan,alarmMchtGrid);
			}
		}],
		listeners : {       
            'render' : function() {  
					tbarCard.render(this.tbar); 
                }  
        }  ,
		bbar: new Ext.PagingToolbar({
			store: alarmCardStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	alarmCardGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(alarmCardGrid.getView().getRow(alarmCardGrid.getSelectionModel().last)).frame();
			Ext.getCmp('detailCard').enable();
			var cardSta=alarmCardGrid.getSelectionModel().getSelected().get('cardSta');
			
			if(cardSta=='0'){
				Ext.getCmp('blockCard').enable();
				Ext.getCmp('recoverCard').disable();
			}else {
				Ext.getCmp('blockCard').disable();
				Ext.getCmp('recoverCard').enable();
			}
		}
	});
	// 禁用编辑按钮
	alarmCardGrid.getStore().on('beforeload',function() {
		Ext.getCmp('blockCard').disable();
		Ext.getCmp('recoverCard').disable();
		Ext.getCmp('detailCard').disable();
	});
	alarmCardStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
//			queryCardAccpId: Ext.get('queryCardAccpId').getValue(),
			queryPan: Ext.get('queryPan').getValue(),
			queryAlarmId:alarmId,
			queryAlarmSeq:alarmSeq
		});
	});
	
	//******************关联银行卡部分**********结束********
	
	
	
	
	
	
	
	

		
	//**************************************************警报store***************************************
	var alarmStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getRiskAlarm'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'mchtNo'
		},[
			{name: 'alarmId',mapping: 'alarmId'},
			{name: 'alarmSeq',mapping: 'alarmSeq'},
			{name: 'riskDate',mapping: 'riskDate'},
			{name: 'riskId',mapping: 'riskId'},
			{name: 'riskLvl',mapping: 'riskLvl'},
			{name: 'alarmLvl',mapping: 'alarmLvl'},
			{name: 'alarmSta',mapping: 'alarmSta'},
			{name: 'saModelDesc',mapping: 'saModelDesc'},
			{name: 'cheatTp',mapping: 'cheatTp'},
			{name: 'cheatFlag',mapping: 'cheatFlag'},
			{name: 'riskParaName1',mapping: 'riskParaName1'},
			{name: 'riskParaValue1',mapping: 'riskParaValue1'},
			{name: 'riskParaName2',mapping: 'riskParaName2'},
			{name: 'riskParaValue2',mapping: 'riskParaValue2'},
			{name: 'misc',mapping: 'misc'}
		]),
		autoLoad: false
	});
	
	alarmStore.load({
		params: {
			alarmId: alarmId,
			alarmSeq:alarmSeq
		},
		callback: function(records, options, success){
			if(success){
				alarmForm.getForm().loadRecord(alarmStore.getAt(0));
				alarmForm.getForm().clearInvalid();
				
				var alarmData = alarmStore.getAt(0).data;
				Ext.getCmp("riskDate").setValue(formatDt(alarmData.riskDate));
//				Ext.getCmp("alarmLvlId").setValue(alarmLvl);
				
				if(alarmData.riskParaName1!=''){
					var txtLabel1 =Ext.getCmp('riskPara1').getEl().parent().parent().first();
					txtLabel1.dom.innerHTML=alarmData.riskParaName1+":";
					Ext.getCmp("riskPara1").setValue(alarmData.riskParaValue1);
					Ext.getCmp("riskPara1Id").show();
				}else{
					Ext.getCmp("riskPara1Id").hide();
				}
				
				if(alarmData.riskParaName2!=''){
					var txtLabel2 =Ext.getCmp('riskPara2').getEl().parent().parent().first();
					txtLabel2.dom.innerHTML=alarmData.riskParaName2+":";
					Ext.getCmp("riskPara2").setValue(alarmData.riskParaValue2);
					Ext.getCmp("riskPara2Id").show();
				}else{
					Ext.getCmp("riskPara2Id").hide();
				}
				
				if(alarmData.cheatFlag=='1'){
					Ext.getCmp("cheatTpId").show();
				}else{
					Ext.getCmp("cheatTpId").hide();
				}
				

				//***************************关联交易*********************************************	
//				cashFeeStore.load({
//					params: {
//						start: 0,
//						alarmId: alarmId
//					}
//				});
				
			
				
				
				

               
			}else{
				showErrorMsg("加载风险警报信息失败，请返回刷新数据后重试",alarmForm,function(){
					window.location.href = Ext.contextPath + '/page/risk/T40211.jsp';
				});
			}
		}
	});

	//*******************************sotre结束********************************
	var botton1 = {
			xtype: 'button',
			width: 100,
			text: '返回',
			id: 'botton1',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				window.location.href = Ext.contextPath + '/page/risk/T40211.jsp';
			}
		};
	var botton2 = {
			text: '提交',
			xtype: 'button',
            id: 'commit',
            width: 100,
            height: 30,
            name: 'save',
			handler: function() {
				if (alarmForm.getForm().isValid()) {
					alarmForm.getForm().submit({
							url : 'T40211Action.asp?method=update',
							waitMsg : '正在提交，请稍后......',
							params : {
//								ruleId: grid.getSelectionModel().getSelected().get('ruleId'),
								alarmId: alarmId,
			 					alarmSeq: alarmSeq,
								txnId : '40211',
								subTxnId : '01'
							},
							success : function(form, action) {
								showSuccessAlert(action.result.msg,alarmForm,250,function(){
									window.location.href = Ext.contextPath + '/page/risk/T40211.jsp';
								});
							},
							failure : function(form, action) {
								showErrorMsg(action.result.msg, alarmForm);
							}
						});
					}
			}
		};
	var buttonArr = new Array();
	buttonArr.push(botton1);
	buttonArr.push(botton2);
	
	var alarmForm = new Ext.FormPanel({
		frame: true,
		modal: true,
		height: Ext.getBody().getHeight(true),
        width: Ext.getBody().getWidth(true),
        autoScroll: true,
//		title: '警报详情',	
		labelAlign: 'left',
		waitMsgTarget: true,
		/*defaults: {
			bodyStyle: 'padding-left: 10px'
		},*/
		items: [
			{
			id: 'alarmDtlInfo',
			width: Ext.getBody().getWidth(true)-40,
			collapsible: true,
			xtype: 'fieldset',
			title: '警报详情',
			layout: 'column',
			defaults: {
				bodyStyle: 'padding-left: 20px'
			},
			items: [
				{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'combofordispaly',
					store: riskAlarmLvl,
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'alarmLvl',
					id:'alarmLvlId',
					fieldLabel: '警报级别',
					width:150
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'combofordispaly',
			        baseParams: 'RISK_LVL',
					fieldLabel: '风控级别',
					id: 'riskLvlId',
					hiddenName: 'riskLvl',
					width:150
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '警报单号',
					id: 'alarmId',
					name:'alarmId',
					width:150
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '警报序列号',
					id: 'alarmSeq',
					name:'alarmSeq',
					width:150
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '警报日期',
					id: 'riskDate',
					name:'riskDate',
					width:150
					
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'combofordispaly',
			        baseParams: 'KIND',
					fieldLabel: '触发风控',
					id: 'riskIdId',
					hiddenName: 'riskId',
					width:150
				}]
			},{
				columnWidth: .75,
				layout: 'form',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
					fieldLabel: '规则说明',
					id: 'saModelDesc',
					name:'saModelDesc'
//					width:150
					
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				hidden:true,
				id:'riskPara1Id',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
//					fieldLabel: '规则说明',
					id: 'riskPara1',
					name:'riskPara1',
					width:150
					
				}]
			},{
				columnWidth: .25,
				layout: 'form',
				hidden:true,
				id:'riskPara2Id',
				labelWidth : 75,
				items: [{
					xtype: 'displayfield',
//					fieldLabel: '规则说明',
					id: 'riskPara2',
					name:'riskPara2',
					width:150
					
				}]
			}]			
		},
		{
		xtype: 'tabpanel',
    	id: 'feeInfo',
    	frame: true,
        plain: false,
        width: Ext.getBody().getWidth(true)-40,
        activeTab: 0,
        height: 525,
        deferredRender: false,
        enableTabScroll: true,
    	items:[
    	{
            id:'alarmTxnInfo',
			title:'关联交易',
//			autoScroll: true,
//			collapsible: true,
        	frame: true,
        	layout: 'border',
			items : [alarmTxnGrid]
		},{
            id:'alarmMchtInfo',
			title:'关联商户',
//			autoScroll: true,
//			collapsible: true,
        	frame: true,
        	layout: 'border',
			items : [alarmMchtGrid]
		}
		,{
            id:'alarmCardInfo',
			title:'关联银行卡',
//			autoScroll: true,
//			collapsible: true,
        	frame: true,
        	layout: 'border',
			items : [alarmCardGrid]
		}
    	]},
    	{
			id: 'alarmDealInfo',
			width: Ext.getBody().getWidth(true)-40,
			collapsible: true,
			xtype: 'fieldset',
			title: '处理信息',
			layout: 'column',
			defaults: {
				bodyStyle: 'padding-left: 20px'
			},
			items: [
				{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['00','未处理'],['01','处理中'],['02','已处理']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'alarmSta',
					editable: false,
					fieldLabel: '风险处理',
					emptyText: '请选择',
					allowBlank: false,
					id:'alarmStaId',
					width:150
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				items: [{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','不属于'],['1','属于']],
						reader: new Ext.data.ArrayReader()
					}),
					displayField: 'displayField',
					valueField: 'valueField',
					hiddenName: 'cheatFlag',
					editable: false,
					fieldLabel: '是否属于欺诈',
					emptyText: '请选择',
					allowBlank: false,
					id:'cheatFlagId',
					width:150,
					listeners: {
                     	'change': function(){
                     		if(this.value == '0'){
                     			Ext.getCmp('cheatTpIds').hide();
                     			Ext.getCmp("cheatTpId").setValue("");
                     		}else if(this.value == '1'){
                     			Ext.getCmp('cheatTpIds').show();
                     			Ext.getCmp("cheatTpId").reset();
                     		}   
                    	},
                    	'select': function(){
                     		if(this.value == '0'){
                     			Ext.getCmp('cheatTpId').hide();
                     			Ext.getCmp("cheatTp").setValue("");
                     		}else if(this.value == '1'){
                     			Ext.getCmp('cheatTpId').show();
                     			Ext.getCmp("cheatTp").reset();
                     		}                    		
                    	}
                    }
				}]
			},{
				columnWidth: .33,
				layout: 'form',
				id:'cheatTpId',
				hidden:true,
				items: [{
					xtype: 'textfield',
					fieldLabel: '欺诈类型',
					id: 'cheatTp',
					name:'cheatTp',
					width:150
				}]
			}
			,{
				columnWidth: .66,
				layout: 'form',
				items: [{
					xtype: 'textarea',
					fieldLabel: '备注信息',
					maxLength: '60',
					id: 'misc',
					name:'misc',
					width:250,
//					width: 250,
					maxLength: 50
				}]
			},{
				columnWidth: 1,
				layout: 'column',
				buttonAlign: 'center',
				defaults: {
				bodyStyle: 'padding-left: 100px'
				},
//				items: [buttonArr]、
				buttons: buttonArr	
			}]			
		},
		{	xtype: 'fieldset',
			id: 'next3',
			width: Ext.getBody().getWidth(true)-40,
			hidden:true,
			defaults: {
				bodyStyle: 'padding-left: 100px'
			},
			buttonAlign: 'center'
//			buttons: nextArr3			
		}]
	});
	
	// 主界面
	
	new Ext.Viewport({
		layout: 'fit',
		items: [alarmForm]
	});
	// 移除主界面初始化图层
	/*var hideMainUIMask = function() {
		Ext.fly('load-mask').fadeOut({
			remove: true,
			easing: 'easeOut',
    		duration: 1

		});
	};
	hideMainUIMask.defer(1000);*/
})