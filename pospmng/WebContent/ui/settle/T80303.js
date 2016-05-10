Ext.onReady(function() {
	
	
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=amtbackApply'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'checkOpr',mapping: 'checkOpr'},
			{name: 'checkDate',mapping: 'checkDate'},
			{name: 'applyOpr',mapping: 'applyOpr'},
			{name: 'applyDate',mapping: 'applyDate'},
			{name: 'applyTime',mapping: 'applyTime'},
			{name: 'applyState',mapping: 'applyState'},
			{name: 'resState',mapping: 'resState'},
			{name: 'misc1',mapping: 'misc1'},
			{name: 'misc2',mapping: 'misc2'},
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instTime',mapping: 'instTime'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'dateSettlmt8',mapping: 'dateSettlmt8'},
			{name: 'keyCup',mapping: 'keyCup'}
			
			
			
			
		]),
		autoLoad: true
	});
	
	
	
	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>申请日期：<font color=green>{applyDate:this.formatDate()}</font></p>',
			'<p>申请时间：<font color=green>{applyTime:this.formatDate()}</font></p>',
			'<p>申请人：<font color=green>{applyOpr}</font></p>',
			'<p>审核人：<font color=green>{checkOpr}</font></p>',
			'<p>审核日期：<font color=green>{checkDate:this.formatDate()}</font></p>',
			'<p>备注：<font color=red>{misc1}</font></p>'
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
	
	
	
	var detailColModel = new Ext.grid.ColumnModel([
		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'instDate',align: 'center',renderer: formatDt,width: 80},
		{header: '交易时间',dataIndex: 'instTime',align: 'center',renderer: formatDt,width: 80},
		{header: '退货类型',dataIndex: 'misc2',align: 'center',renderer: amtBackFlag,width: 80},
		{header: '商户号',dataIndex: 'mchtName',align: 'left',width: 250,id:'mchtName'},
		{header: '卡号',dataIndex: 'pan',align: 'left',width: 130},
		{id:'txnName',header: '交易类型',dataIndex: 'txnName',align: 'left',width: 100},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right',width: 80},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 80,align: 'center'},
		{header: '参考号',dataIndex: 'retrivlRef',align: 'center'},
//		{header: '申请日期',dataIndex: 'applyDate',align: 'center',renderer: formatDt,width: 80},
//		{header: '申请时间',dataIndex: 'applyTime',align: 'center',renderer: formatDt,width: 80},
		{header: '审核状态',dataIndex: 'applyState',align: 'center',width: 80,renderer:applyState },
		{header: '退货结果',dataIndex: 'resState',align: 'center',width: 80,renderer:backState },
		{header: '清算日期',dataIndex: 'dateSettlmt8',hidden: true,renderer: formatDt},
		{header: '银联关键字',dataIndex: 'keyCup',hidden: true}
//		
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','起始日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					endDateField: 'endDate',
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'-','结束日期：',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					startDateField: 'startDate',
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	},'-','参考号：       ',{
					xtype: 'textfield',
					name: 'queryRetrivlRef',
					id: 'queryRetrivlRef',
					vtype: 'isNumber',
					width: 120
				}
	               /*	,'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'idqueryTxnName',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnName',
					width: 140
				}*/
	            ]  
         }) 
         
          var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					 vtype: 'isNumber',
					width: 120
				},'-','交易卡号：',{
					xtype: 'textfield',
					id: 'queryPan',
					 vtype: 'isNumber',
					name: 'queryPan',
					width: 120
				},'-',{text:'商户编号：',id:'mchtText'}
				,{
//					xtype: 'basecomboselect',
//					baseParams: 'MCHT_NO',
//					store : mchtStore,
					xtype : 'dynamicCombo',
					methodName : 'getAllMchntId',
					hiddenName: 'queryCardAccpId',
//					hiddenName : 'queryAccpId',
//					hidden:true,
					width: 300,
					id: 'idqueryCardAccpId',
//					mode:'local',
//					triggerAction: 'all',
//					forceSelection: true,
//					typeAhead: true,
//					selectOnFocus: true,
					editable: true,
			//			allowBlank: true,
					lazyRender: true
					/*listeners : {  
			            'beforequery':function(e){  
			                   
			                var combo = e.combo;    
			                if(!e.forceAll){    
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }  
			            }  
			        } */}
				
				]  
         }) 
         
        
     
    
	// 拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
//			showProcessMsg('正在提交审核信息，请稍后......');
			rec = detailGrid.getSelectionModel().getSelected();
			Ext.Ajax.request({
				url : 'T80303Action.asp?method=refuse',
										
				params : {
					dateSettlmt8: detailGrid.getSelectionModel().getSelected().data.dateSettlmt8,
					keyCup: detailGrid.getSelectionModel().getSelected().data.keyCup,
					applyDate: detailGrid.getSelectionModel().getSelected().data.applyDate,
					applyTime: detailGrid.getSelectionModel().getSelected().data.applyTime,
					applyOpr: detailGrid.getSelectionModel().getSelected().data.applyOpr,
					refuseInfo: text,
					txnId : '80303',
					subTxnId : '02'
				},
				success : function(rsp, opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if (rspObj.success) {
						showSuccessMsg(rspObj.msg,detailGrid);
						Ext.getCmp('pass').disable();
						Ext.getCmp('refuse').disable();
						
					} else {
						showErrorMsg(rspObj.msg, detailGrid);
					}
					detailGrid.getStore().reload();
				}
			});
//			hideProcessMsg();
		}
	}
	
	
	var detailGrid = new Ext.grid.EditorGridPanel({
//		iconCls: 'T104',
		region:'center',
//		title: '差错信息',
		autoExpandColumn:'mchtName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		plugins: rowExpander,
		forceValidation: true,
		renderTo : Ext.getBody(),
		tbar: [{
			xtype: 'button',
			text : '通过',
			id:'pass',
			width : 85,
			iconCls : 'accept',//recover
			disabled : true,
			handler:function() {
				showConfirm('确定要通过此差错退货申请吗？', detailGrid, function(bt) {
						// 如果点击了提示的确定按钮
//							showProcessMsg('正在提交审核信息，请稍后......');
						if (bt == "yes") {
							Ext.Ajax.request({
										url : 'T80303Action.asp?method=pass',
										
										params : {
											dateSettlmt8: detailGrid.getSelectionModel().getSelected().data.dateSettlmt8,
											keyCup: detailGrid.getSelectionModel().getSelected().data.keyCup,
											applyDate: detailGrid.getSelectionModel().getSelected().data.applyDate,
											applyTime: detailGrid.getSelectionModel().getSelected().data.applyTime,
											applyOpr: detailGrid.getSelectionModel().getSelected().data.applyOpr,
											
											txnId : '80303',
											subTxnId : '01'
										},
										success : function(rsp, opt) {
											var rspObj = Ext.decode(rsp.responseText);
											if (rspObj.success) {
												showSuccessMsg(rspObj.msg,detailGrid);
												Ext.getCmp('pass').disable();
												Ext.getCmp('refuse').disable();
												
											} else {
												showErrorMsg(rspObj.msg, detailGrid);
											}
											detailGrid.getStore().reload();
										}
										
									});
									
//									hideProcessMsg();
						}
					})
			}},'-',{
			xtype: 'button',
			text : '拒绝',
			id:'refuse',
			width : 85,
			iconCls : 'stop',//recover
			disabled : true,
			handler:function() {
				showInputMsg('提示','请输入拒绝原因',true,refuse);
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				detailGridStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryCardAccpId').setValue('');
				
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('queryPan').setValue('');
				
//				Ext.getCmp('idqueryTxnName').setValue('');
				Ext.getCmp('queryRetrivlRef').setValue('');
				
				
				
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
				
				detailGridStore.reload();
//				Ext.getCmp('idMchtNo').getStore().reload()
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});*/
				Ext.getCmp('idqueryCardAccpId').getStore().reload();
			}

		
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					
							tbar2.render(this.tbar); 
							tbar3.render(this.tbar); 
							
                }  ,
                'cellclick':selectableCell,
        }  ,
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'

			
		})
//		renderTo: Ext.getBody()
	});
	
	detailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last)).frame();
			
//				detailGrid.getTopToolbar().items.items[0].enable();
			
			Ext.getCmp('pass').enable();
			Ext.getCmp('refuse').enable();
		}
	});
	
	

	
	detailGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
			queryPan: Ext.getCmp('queryPan').getValue(),
			
			queryCardAccpId: Ext.getCmp('idqueryCardAccpId').getValue(),
//			queryTxnName: Ext.get('queryTxnName').getValue()
			queryRetrivlRef: Ext.get('queryRetrivlRef').getValue(),
			queryApplyState:'0'
			
		});
//		detailGridStore.removeAll();
		Ext.getCmp('pass').disable();
		Ext.getCmp('refuse').disable();
//		detailGrid.getTopToolbar().items.items[0].disable();
	});
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [detailGrid],
		renderTo: Ext.getBody()
	});
});