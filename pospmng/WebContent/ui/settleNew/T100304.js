Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	var paySettleGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=paySettleNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		
			{name: 'instDate',mapping: 'instDate'},
			{name: 'channelId',mapping: 'channelId'},
			{name: 'channelIdNm',mapping: 'channelIdNm'},
			{name: 'amtTotal',mapping: 'amtTotal'}
			
		]),
		autoLoad: true
	});
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=paySettleDtlNew'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			
//			{name: 'instDate',mapping: 'instDate'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'setDtBeg',mapping: 'setDtBeg'},
			{name: 'setDtEnd',mapping: 'setDtEnd'},
//			{name: 'batchNo',mapping: 'batchNo'},
//			{name: 'channelId',mapping: 'channelId'},
			{name: 'amtSettlmt',mapping: 'amtSettlmt'},
			{name: 'mchtAcctNo',mapping: 'mchtAcctNo'},
			{name: 'mchtAcctNm',mapping: 'mchtAcctNm'},
			{name: 'cnapsId',mapping: 'cnapsId'},
//			{name: 'dfAcctNo',mapping: 'dfAcctNo'},
//			{name: 'dfAcctNm',mapping: 'dfAcctNm'},
//			{name: 'dfMchtNo',mapping: 'dfMchtNo'},
			{name: 'payType',mapping: 'payType'}
		])
	});
	
	
	
	/*var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>申请人：<font color=green>{applyOpr}</font></p>',
			'<p>审核人：<font color=green>{checkOpr}</font></p>',
			'<p>审核日期：<font color=green>{checkDate:this.formatDate()}</font></p>',
			'<p>备注：<font color=red>{misc1}</font></p>'
			,{
			formatDate : function(val){
				 if(val!=null &&val.length == 8){
					return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
				+ val.substring(6, 8);
				}else{
					return val;
				}
			 }
			}
		)
	});*/
	
	
	var detailColModel = new Ext.grid.ColumnModel([
//		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '所属商户',dataIndex: 'mchtNm',align: 'left',id:'mchtNm'},
		{header: '清算净额',dataIndex: 'amtSettlmt',align: 'right',width: 120 },
		{header: '清算起始日期',dataIndex: 'setDtBeg',align: 'center',renderer: formatDt,width: 100},
		{header: '清算结束日期',dataIndex: 'setDtEnd',align: 'center',renderer: formatDt,width: 100 }
//		{header: '商户开户行',dataIndex: 'cnapsId',align: 'left',width: 120 },
//		{header: '商户账号',dataIndex: 'mchtAcctNo',align: 'left',width: 130 },
//		{header: '商户账号名',dataIndex: 'mchtAcctNm',align: 'left',width: 120 }
	]);
	
	
	
	
	var paySettleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
//		rowExpander,
		{header: '划付清算日期',dataIndex: 'instDate',align: 'center',renderer: formatDt,width: 110},
		{header: '结算通道',dataIndex: 'channelIdNm',align: 'center',width: 150,id:'channelIdNm'},
		{header: '清算总金额',dataIndex: 'amtTotal',align: 'right',width: 120}
		
		
	]);
	
	
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','划付清算日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					editable: false,
					id: 'settleDate',
					name: 'settleDate',
					value:timeYesterday,
					maxValue:preDate,
					width: 120
	               	}
	            ]  
         }) 
         
        
        
     
    
	var detailGrid = new Ext.grid.GridPanel({
		title: '划付明细信息',
		region: 'east',
//		region:'center',
		width: 650,
//		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
//		plugins: rowExpander,
		forceValidation: true,
		loadMask: {
			msg: '正在加载划付明细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	var paySettleGrid = new Ext.grid.EditorGridPanel({
//		iconCls: 'T104',
		region:'center',
//		region:'west',
//		width: 430,
//		title: '差错信息',
		autoExpandColumn:'channelIdNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: paySettleGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: paySettleColModel,
		clicksToEdit: true,
//		plugins: rowExpander,
		forceValidation: true,
		renderTo : Ext.getBody(),
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {

				paySettleGridStore.load();
			}
		},'-',{
			xtype: 'button',
			text : '下载全部文件',
			id:'donwloadAll',
			width : 100,
			iconCls : 'download',//recover
//			disabled : true,
			handler:function() {
				showConfirm('确定要下载一下全部划付文件吗？', paySettleGrid, function(bt) {
						// 如果点击了提示的确定按钮
						if (bt == "yes") {
							var stlDate=typeof(Ext.getCmp('settleDate').getValue()) == 'string' ? '' : Ext.getCmp('settleDate').getValue().format('Ymd');
							Ext.Ajax.request({
								url : 'T100304Action.asp?method=downloadAll',
								params : {
									instDate: stlDate,
									txnId : '100304',
									subTxnId : '01'
								},
								success : function(rsp, opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if (rspObj.success) {
//										showSuccessMsg(rspObj.msg,paySettleGrid);
										window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
									} else {
										showErrorMsg(rspObj.msg, paySettleGrid);
									}
//									paySettleGrid.getStore().reload();
								}
							});
						}
					})
			}},'-',{
			xtype: 'button',
			text : '下载指定文件',
			id:'download',
			width : 100,
			iconCls : 'edit',//recover
//			disabled : true,
			handler:function() {
				var settleDt=paySettleGrid.getSelectionModel().getSelected().get('instDate');
				var settleChanl=paySettleGrid.getSelectionModel().getSelected().get('channelIdNm');
				Ext.getCmp('settleDt').setValue(formatDt(settleDt));
				Ext.getCmp('settleChanl').setValue(settleChanl);
				downloadWin.show();
				downloadWin.center();
			}}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
						tbar2.render(this.tbar); 
  		            }  
        },
		loadMask: {
			msg: '正在加载划付明细信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: paySettleGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
//		renderTo: Ext.getBody()
	});
	
	paySettleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(paySettleGrid.getView().getRow(paySettleGrid.getSelectionModel().last)).frame();
			detailGridStore.load({
				params: {
					start: 0,
					instDate: paySettleGrid.getSelectionModel().getSelected().get('instDate'),
					channelId: paySettleGrid.getSelectionModel().getSelected().get('channelId')
				}
			})
//				detailGrid.getTopToolbar().items.items[0].enable();
			
			Ext.getCmp('download').enable();
		}
	});
	
	detailGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(detailGrid.getView().getRow(detailGrid.getSelectionModel().last)).frame();
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			instDate: paySettleGrid.getSelectionModel().getSelected().get('instDate'),
			channelId: paySettleGrid.getSelectionModel().getSelected().get('channelId')
			
		});
	});

	
	paySettleGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			settleDate: typeof(Ext.getCmp('settleDate').getValue()) == 'string' ? '' : Ext.getCmp('settleDate').getValue().format('Ymd')
		});
		detailGridStore.removeAll();
		Ext.getCmp('download').disable();
//		detailGrid.getTopToolbar().items.items[0].disable();
	});
	
	
	var downloadForm = new Ext.form.FormPanel({
				frame : true,
				autoHeight : true,
				layout : 'column',
				defaults : {
					bodyStyle : 'padding-left: 20px'
				},
				waitMsgTarget : true,
				labelwith:80,
				items : [{
					columnWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'displayfield',
						fieldLabel : '划付清算日期',
						id : 'settleDt',
						width : 150
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					items : [{
						xtype : 'displayfield',
						fieldLabel : '原划付文件格式',
						id : 'settleChanl',
						width : 150
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					items : [{
						xtype: 'basecomboselect',
						baseParams: 'PAY_FILE_ID',
						fieldLabel : '指划付文件格式',
						emptyText : '请选择指定划付文件格式',
						allowBlank : false,
						id : 'aimChnlIds',
						hiddenName : 'aimChnlId',
						width : 150,
						blankText : '请选择一个定划付文件格式'
					}]
				}]
			});
				
	var downloadWin = new Ext.Window({
				title : '指定通道划付文件',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 350,
				items : [downloadForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				buttons : [{
					text : '下载',
//					iconCls : 'download',//recover
					handler : function() {
						if (downloadForm.getForm().isValid()) {
							downloadForm.getForm().submit({
								url : 'T100304Action.asp?method=download',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									// 重置表单
									downloadForm.getForm().reset();
									downloadWin.hide();
//									showSuccessMsg(action.result.msg,downloadForm);
//									grid.getStore().reload();
									
									window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+action.result.msg;
//									window.location.href = 'download.asp?filename='+action.result.msg;
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, downloadForm);
								},
								params : {
									instDate: paySettleGrid.getSelectionModel().getSelected().get('instDate'),
									channelId: paySettleGrid.getSelectionModel().getSelected().get('channelId'),
									txnId : '100304',
									subTxnId : '02'
								}
							});
						}
					}
				}, {
					text : '取消',
					handler : function() {
						downloadWin.hide();
					}
				}]
			});
			
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [paySettleGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});