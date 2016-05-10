Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天

	// 数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtSettleDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateStlm',mapping: 'dateStlm'},
			{name: 'brhIdNm',mapping: 'brhIdNm'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'instCode',mapping: 'instCode'},
			{name: 'stlmFlag',mapping: 'stlmFlag'},
			{name: 'dateStlmTrue',mapping: 'dateStlmTrue'},
			{name: 'stlmNum',mapping: 'stlmNum'},
			{name: 'stlmAmtTrans',mapping: 'stlmAmtTrans'},
			{name: 'stlmAmtFee',mapping: 'stlmAmtFee'},
			{name: 'stlmAmt',mapping: 'stlmAmt'}
			
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});*/
	
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateStlm',align: 'center',renderer: formatDt,width: 90},
		{header: '商户名',dataIndex: 'mchtNoNm',width: 250,align: 'left',id:'mchtNoNm'},
		{header: '受理机构',dataIndex: 'brhIdNm',align: 'left',width: 200},
		{header: '实际结算日期',dataIndex: 'dateStlmTrue',align: 'center',renderer: formatDt,width: 90},
		{header: '结算标示',dataIndex: 'stlmFlag',align: 'center',width: 80,renderer: stlmFlag},
		{header: '交易笔数',dataIndex: 'stlmNum',align: 'right',width: 80},
		{header: '交易金额',dataIndex: 'stlmAmtTrans',align: 'right',width: 120},
		{header: '手续费',dataIndex: 'stlmAmtFee',align: 'right'},
		{header: '清算金额',dataIndex: 'stlmAmt',align: 'right',width: 120},
		{header: '交易通道',dataIndex: 'instCode',align: 'center'}
	]);
	    
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:['-','交易金额：',{
					xtype: 'textfield',
					id: 'queryAmtTransLow',
					name: 'queryAmtTransLow',
					vtype:'isMoney',
					width: 120
               	},'—',{
					xtype: 'textfield',
					id: 'queryAmtTransUp',
					name: 'queryAmtTransUp',
					vtype:'isMoney',
					width: 120
               	},'-','受理机构：'
				,{
//					xtype: 'basecomboselect',
					id: 'idqueryBrhId',
					hiddenName: 'queryBrhId',
//				    baseParams: 'BRH_BELOW_BRANCH',
					xtype : 'dynamicCombo',
					methodName : 'getBrhId',
				    editable: true,
				    mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true,
					width: 285
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
			            },
			            'select' : function() {
								mchtStore.removeAll();
								SelectOptionsDWR.getComboDataWithParameter(
										'MCHT_BELOW', this.value, function(ret) {
											mchtStore.loadData(Ext.decode(ret));
										});

						}
					}*/
				}]  
         }); 
         
          var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','清算日期：',{
               		xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					endDateField: 'endDate',
					value:timeYesterday,
					editable: false,
					id: 'startDate',
					name: 'startDate',
					width: 120
	               	},'—',{
					xtype: 'datefield',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
//					vtype: 'daterange',
//					startDateField: 'startDate',
					value:timeYesterday,
					editable: false,
					id: 'endDate',
					name: 'endDate',
					width: 120
	               	}
				,'-','商户编号：'
				,{
//					xtype: 'combo',
//					store : mchtStore,
					xtype : 'dynamicCombo',
					methodName : 'getMchntNoAll',
					hiddenName: 'queryMchtNoNm',
					width: 285,
					editable: true,
					id: 'idqueryMchtNoNm',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
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
			        }*/
				}]  
         }); 
	
	
	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
		autoExpandColumn:'mchtNoNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: txnColModel,
		clicksToEdit: true,
		forceValidation: true,
        renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		tbar: [{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",txnGrid);
				if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
				Ext.Ajax.request({
					url: 'T100303Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
						queryMchtNoNm: Ext.get('queryMchtNoNm').getValue(),
						queryBrhId: Ext.get('queryBrhId').getValue(),
//						querySettleFlag: Ext.get('querySettleFlag').getValue(),
						queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
						queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue()
						
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,txnGrid);
						}
					},
					failure: function(){
						hideMask();
						showErrorMsg(rspObj.msg,txnGrid);
					}
				});
//				txnGridStore.load();
			    } else {
			    	hideMask();
			    	Ext.MessageBox.show({
						msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
						title: '报表下载说明',
						buttons: Ext.MessageBox.OK,
						modal: true,
						width: 220
					});
				}
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				txnGridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryMchtNoNm').setValue('');
				Ext.getCmp('idqueryBrhId').setValue('');
//				Ext.getCmp('idquerySettleFlag').setValue('');
				Ext.getCmp('queryAmtTransLow').setValue('');
				Ext.getCmp('queryAmtTransUp').setValue('');
				
//				Ext.getCmp('actStartDate').setValue('');
//				Ext.getCmp('actEndDate').setValue('');
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
//				txnGridStore.load();
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});
				Ext.getCmp('idqueryBrhId').getStore().reload();*/
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar3.render(this.tbar);  
					tbar2.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: txnGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'			
		})
	});
	
	txnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
		}
	});
	
	txnGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			queryMchtNoNm: Ext.get('queryMchtNoNm').getValue(),
			queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
			queryBrhId: Ext.get('queryBrhId').getValue(),
			queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue()/*,
			querySettleFlag: Ext.get('querySettleFlag').getValue(),
			actStartDate: typeof(Ext.getCmp('actStartDate').getValue()) == 'string' ? '' : Ext.getCmp('actStartDate').getValue().format('Ymd'),
			actEndDate: typeof(Ext.getCmp('actEndDate').getValue()) == 'string' ? '' : Ext.getCmp('actEndDate').getValue().format('Ymd')*/
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});