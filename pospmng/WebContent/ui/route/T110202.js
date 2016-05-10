Ext.onReady(function() {
	//取业务制作下拉列表
	var busiStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
		busiStore.loadData(Ext.decode(ret));
	});
	
	function cartTypeConvert(val){
		if(val.length==2){
			switch(val){
				case '00': return '借记卡';
				case '01': return '贷记卡';
				case '02': return '准贷记卡';
				case '03': return '预付费卡';
				case '04': return '公务卡';
				case '05': return '未知卡';
				default: return '未知卡';
			}
		}else{
			return val;
		}
	}
	//取性质制作下拉列表
	var propStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboDataWithParameter('PROP_SEL','',function(ret){
		propStore.loadData(Ext.decode(ret));
	});
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnTodayInfo4Brh'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			
			{name: 'pan',mapping: 'pan'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'transState',mapping: 'transState'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'instDatetime',mapping: 'instDatetime'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'mchtTermId',mapping: 'mchtTermId'},
			{name: 'mchtBrhName',mapping: 'mchtBrhName'},
			{name: 'upBrhTermId',mapping: 'upBrhTermId'},
			{name: 'brhName1',mapping: 'brhName1'},
			{name: 'brhName2',mapping: 'brhName2'},
			{name: 'brhName3',mapping: 'brhName3'},
			{name: 'ruleId',mapping: 'ruleId'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'respName',mapping: 'respName'},
			{name: 'settleBrhNm',mapping: 'settleBrhNm'},
			{name: 'revsalSsn',mapping: 'revsalSsn'},
			{name: 'revsalFlag',mapping: 'revsalFlag'},
			{name: 'cancelSsn',mapping: 'cancelSsn'},
			{name: 'cancelFlag',mapping: 'cancelFlag'}
		]),
		autoLoad: false
	});
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '卡号',dataIndex: 'pan',align: 'left',width: 150},
		{header: '发卡行',dataIndex: 'bankName',width: 250,align: 'left'},
		{header: '账户类型',dataIndex: 'cardType',width: 100,align: 'center',renderer:cartTypeConvert},
		{header: '交易状态',dataIndex: 'transState',renderer: txnSta,align: 'center',width: 100},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right',width: 150},
		{header: '交易时间',dataIndex: 'instDatetime',align: 'center',renderer: formatDt,width: 150},
		{header: '交易类型',dataIndex: 'txnName',align: 'center',width: 100,id:'txnName',},
		{header: '本地商户',dataIndex: 'mchtName',width: 250,align: 'left',id:'mchtName',},
		{header: '本地终端号',dataIndex: 'mchtTermId',width: 100,align: 'center'},
		{header: '渠道商户',dataIndex: 'mchtBrhName',width: 250,align: 'left'},
		{header: '渠道终端号',dataIndex: 'upBrhTermId',width: 100,align: 'center'},
		{header: '支付渠道',dataIndex: 'brhName1',align: 'center',width: 100 },
		{header: '业务',dataIndex: 'brhName2',align: 'center',width: 100 },
		{header: '性质',dataIndex: 'brhName3',align: 'center',width: 100 },
		{header: '路由规则号',dataIndex: 'ruleId',width: 100,align: 'center'},
		{header: '交易参考号',dataIndex: 'retrivlRef',align: 'center',width: 100},
		{header: '终端返回码',dataIndex: 'respName',align: 'center',width: 130},
		{header: '交易通道',dataIndex: 'settleBrhNm',align: 'center',width: 130},
		{header: '冲正流水号',dataIndex: 'revsalSsn',width: 80,align: 'center'},
		{header: '冲正结果',dataIndex: 'revsalFlag',align: 'center',width: 60,renderer:function(val){
			if(val == '0') {
				return '<font color="grey">未冲正</font>';
			} else if(val == '1') {
				return '<font color="red">已冲正</font>';
			}
			return '';}
		},
		{header: '撤销流水号',dataIndex: 'cancelSsn',width: 80,align: 'center'},
		{header: '撤销结果',dataIndex: 'cancelFlag',align: 'center',width: 60,renderer:function(val){
			if(val == '0') {
				return '<font color="grey">未撤销</font>';
			} else if(val == '1') {
				return '<font color="red">已撤销</font>';
			}
			return '';}
		}
	]);
	 
	
      var tbar1 = new Ext.Toolbar({  
            renderTo: Ext.grid.EditorGridPanel.tbar,  
            items:[
            '-','卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;BIN：',{
				xtype : 'textfield',
				id: 'queryCardBINId',
				hiddenName: 'queryCardBIN',
				width: 120,
				maxLength:6,
				minLength:6,
				vtype: 'isNumber'
			},'-','交易额（区间）：',{
				xtype: 'textfield',
				id: 'queryAmtTransLowId',
				name: 'queryAmtTransLow',
				vtype:'isMoney',
				width: 120,
				
            },'~',{
				xtype: 'textfield',
				id: 'queryAmtTransUpId',
				name: 'queryAmtTransUp',
				vtype:'isMoney',
				width: 120,
				maxLength:12
            },'-','路由规则号：',{
				xtype: 'textfield',
				hiddenName: 'queryRouteId',
				id: 'queryRouteIdId',
				vtype: 'isNumber',
				maxLength:8,
				width: 120
			},'-','交易时间：',{
           		xtype: 'datefield',
           		format: 'Y-m-d',
				altFormats: 'Y-m-d',
				//vtype: 'daterange',
				editable: true,
				id: 'queryTransDateId',
				name: 'queryTransDate',
				width: 110
		    },{
           		xtype: 'timefield',
           		format: 'H:i:s',
				altFormats: 'H:i:s',
				editable: true,
				id: 'queryTransTimeId',
				name: 'queryTransTime',
				width: 80
		    }
         ]
     }); 
      var tbar2 = new Ext.Toolbar({  
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
          '-','参&nbsp;&nbsp;考&nbsp;&nbsp;号：',{
				xtype: 'textfield',
				id: 'queryRetrivlRefId',
				hiddenName: 'queryRetrivlRef',
				vtype: 'isNumber',
				maxLength:12,
				width: 120
			},'-','交易类型：',{
				xtype: 'basecomboselect',
				id: 'queryTxnNameId',
				baseParams: 'TXN_TYPES',
				hiddenName: 'queryTxnName',
				width: 120
			},'-','交易状态：',{
				xtype: 'basecomboselect',
				id: 'queryTransStateId',
				baseParams: 'TXN_STATUS',
				hiddenName: 'queryTransState',
				width: 120
			},'-','终端返回码：',{
				xtype: 'textfield',
				id: 'queryRespCodeId',
				name: 'queryRespCode',
				maxLength: 2,
			    minLength: 2,
				width: 120
		    },'-',	'发&nbsp;&nbsp;卡&nbsp;&nbsp;行：',{
	  			xtype: 'dynamicCombo',
	  			methodName: 'getDepositBank',
				id: 'queryBankNameId',
				hiddenName: 'queryBankName',
				width:250,
	  			mode:'local',
	  			triggerAction: 'all',
	  			editable: true,
	  			lazyRender: true
		    }
         ]
   }); 
      var tbar3 = new Ext.Toolbar({  
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
          '-','本地终端号：',{
				xtype: 'textfield',
				hiddenName: 'queryMchtTermId',
				id: 'queryMchtTermIdId',
				vtype: 'isNumber',
				maxLength:8,
				width: 120
			},'-','渠道终端号：',{
				xtype: 'textfield',
				hiddenName: 'queryUpbrhTermId',
				id: 'queryUpbrhTermIdId',
				vtype: 'isNumber',
				maxLength:8,
				width: 120
			}, '-','本地商户：',{
				xtype : 'dynamicCombo',
				methodName : 'getAllMchntId',
				hiddenName: 'queryCardAccpt',
				width: 250,
				editable: true,
				id: 'queryCardAccptId',
				mode:'local',
				triggerAction: 'all',
				forceSelection: true,
				selectOnFocus: true,
				lazyRender: true
			},'-','渠道商户：',{
	  			xtype: 'dynamicCombo',
	  			methodName: 'getUpbrhMcht',
	  			hiddenName: 'queryMchtUpBrh',
	  			id: 'queryMchtUpBrhId',
	  			mode:'local',
	  			triggerAction: 'all',
	  			editable: true,
	  			lazyRender: true,
	  			width: 250
	        }
         	]
   });    
      var tbar4 = new Ext.Toolbar({  
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
              '-','支付渠道：',{
  				xtype: 'basecomboselect',
  				baseParams: 'CHANNEL_ALL',
  				fieldLabel: '支付渠道',
  				hiddenName: 'queryBrh1',
  				id:'queryBrh1Id',
  				width: 150,
  				listeners: {
  					'select': function() {
  						busiStore.removeAll();
  						var brhId = Ext.getCmp('queryBrh1Id').getValue();
  						Ext.getCmp('queryBrh2Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh2').value = '';
  						Ext.getCmp('queryBrh3Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
  						SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
  						busiStore.loadData(Ext.decode(ret));
  						});
  					}/*,
  					'change': function() {
  						busiStore.removeAll();
  						var brhId = Ext.getCmp('queryBrhId').getValue();
  						Ext.getCmp('queryBrh2Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh2').value = '';
  						Ext.getCmp('queryBrh3Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
  						SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL',brhId,function(ret){
  						busiStore.loadData(Ext.decode(ret));
  						});
  					}*/
  				}
  			},'-','业务：',{
  				xtype: 'basecomboselect',
  				store: busiStore,
  				displayField: 'displayField',
  				valueField: 'valueField',
  				id: 'queryBrh2Id',
  				hiddenName: 'queryBrh2',
  				value:'',
  				width: 150,
  				listeners: {
  					'select': function() {
  						propStore.removeAll();
  						var brhId2 = Ext.getCmp('queryBrh2Id').getValue();
  						Ext.getCmp('queryBrh3Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
  						SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
  							propStore.loadData(Ext.decode(ret));
  						});
  					}/*,
  					'change': function() {
  						propStore.removeAll();
  						var brhId2 = Ext.getCmp('queryBrh2Id').getValue();
  						Ext.getCmp('queryBrh3Id').setValue('');
  						Ext.getDom(Ext.getDoc()).getElementById('queryBrh3').value = '';
  						SelectOptionsDWR.getComboDataWithParameter('PROP_SEL',brhId2,function(ret){
  							propStore.loadData(Ext.decode(ret));
  						});
  					}*/
  				}
  			},'-',	'性质：',{
  				xtype: 'basecomboselect',
  				store: propStore,
  				displayField: 'displayField',
  				valueField: 'valueField',
  				id: 'queryBrh3Id',
  				hiddenName: 'queryBrh3',
  				value:'',
  				width: 250
  			},'-','渠道地域：',{
				xtype: 'dynamicCombo',
				methodName: 'getAreaCode',
				id: 'queryAreaCodeId',
				hiddenName : 'queryAreaCode',
				mode:'local',
	  			triggerAction: 'all',
	  			editable: true,
	  			lazyRender: true,
	  			width: 120
			},'-','特殊计费：',{
				xtype : 'combo',
				fieldLabel : '特殊计费',
				id : 'misc01_query',
				hiddenName : 'misc1',
				//value : '0',
				width : 120,
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField',
							'displayField' ],
					data : [[ '0', '无' ],
					        [ '1', '县乡普通' ],
							[ '2', '县乡三农' ] ]
				})
			}
          ]  
  	});
          
	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'txnName',
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
			text: '导出',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				var mLow = Ext.getCmp('queryAmtTransLowId').getValue();
				var mUp = Ext.getCmp('queryAmtTransUpId').getValue();
				if(mLow && mUp && mLow > mUp){
					alert('金额上限不可小于金额下限！');
					return false;
				}
				showMask("正在为您准备报表，请稍后。。。",txnGrid);
				Ext.Ajax.request({
					url: 'T110202Action.asp',
					timeout: 60000,
					params: {
						queryCardBIN:Ext.getCmp('queryCardBINId').getValue(),
						queryAmtTransLow:Ext.getCmp('queryAmtTransLowId').getValue(),
						queryAmtTransUp:Ext.getCmp('queryAmtTransUpId').getValue(),
						queryRouteId:Ext.getCmp('queryRouteIdId').getValue(),
						queryTransDate:typeof(Ext.getCmp('queryTransDateId').getValue()) == 'string' ? '' : Ext.getCmp('queryTransDateId').getValue().format('Ymd'),
						queryTransTime:Ext.getCmp('queryTransTimeId').getValue().replace(/:/g,''),

						queryRetrivlRef:Ext.getCmp('queryRetrivlRefId').getValue(),
						queryTxnName:Ext.get('queryTxnName').getValue(),
						queryTransState:Ext.get('queryTransState').getValue(),
						queryRespCode:Ext.getCmp('queryRespCodeId').getValue(),
						queryBankName:Ext.get('queryBankName').getValue(),

						queryMchtTermId:Ext.getCmp('queryMchtTermIdId').getValue(),
						queryUpbrhTermId:Ext.getCmp('queryUpbrhTermIdId').getValue(),
						queryCardAccptId:Ext.get('queryCardAccpt').getValue(),
						queryMchtUpBrhId:Ext.get('queryMchtUpBrh').getValue().replace(/\*/g,''),

						queryBrh1Id:Ext.get('queryBrh1').getValue(),
						queryBrh2Id:Ext.get('queryBrh2').getValue(),
						queryBrh3Id:Ext.get('queryBrh3').getValue(),
						queryAreaCode:Ext.get('queryAreaCode').getValue()
						
						//txnId: '50106',
						//subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,txnGrid);
						}
					},
					failure: function(rsp,opt){
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						showErrorMsg(rspObj.msg,txnGrid);
					}
				});
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				var mLow = Ext.getCmp('queryAmtTransLowId').getValue();
				var mUp = Ext.getCmp('queryAmtTransUpId').getValue();
				if(mLow && mUp && mLow > mUp){
					alert('金额上限不可小于金额下限！');
					return false;
				}
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
				
				Ext.getCmp('queryCardBINId').setValue('');
				Ext.getCmp('queryAmtTransLowId').setValue('');
				Ext.getCmp('queryAmtTransUpId').setValue('');
				Ext.getCmp('queryRouteIdId').setValue('');
				Ext.getCmp('queryTransDateId').setValue('');
				Ext.getCmp('queryTransTimeId').setValue('');

				Ext.getCmp('queryRetrivlRefId').setValue('');
				Ext.getCmp('queryTxnNameId').setValue('');
				Ext.getCmp('queryTransStateId').setValue('');
				Ext.getCmp('queryRespCodeId').setValue('');
				Ext.getCmp('queryBankNameId').setValue('');

				Ext.getCmp('queryMchtTermIdId').setValue('');
				Ext.getCmp('queryUpbrhTermIdId').setValue('');
				Ext.getCmp('queryCardAccptId').setValue('');
				Ext.getCmp('queryMchtUpBrhId').setValue('');

				Ext.getCmp('queryBrh1Id').setValue('');
				Ext.getCmp('queryBrh2Id').setValue('');
				Ext.getCmp('queryBrh3Id').setValue('');
				Ext.getCmp('queryAreaCodeId').setValue('');
				Ext.getCmp('misc01_query').setValue('');
				
				Ext.getCmp('queryBankNameId').getStore().reload(); 
				Ext.getCmp('queryCardAccptId').getStore().reload();
				Ext.getCmp('queryMchtUpBrhId').getStore().reload();
				Ext.getCmp('queryBrh1Id').getStore().reload();
				SelectOptionsDWR.getComboDataWithParameter('BUSI_SEL','',function(ret){
					busiStore.loadData(Ext.decode(ret));
				});
				SelectOptionsDWR.getComboDataWithParameter('PROP_SEL','',function(ret){
					propStore.loadData(Ext.decode(ret));
				});
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar1.render(this.tbar);  
					tbar2.render(this.tbar);
					tbar3.render(this.tbar);
					tbar4.render(this.tbar);
                },
             'cellclick':selectableCell,    
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
			queryCardBIN:Ext.getCmp('queryCardBINId').getValue(),
			queryAmtTransLow:Ext.getCmp('queryAmtTransLowId').getValue(),
			queryAmtTransUp:Ext.getCmp('queryAmtTransUpId').getValue(),
			queryRouteId:Ext.getCmp('queryRouteIdId').getValue(),
			queryTransDate:typeof(Ext.getCmp('queryTransDateId').getValue()) == 'string' ? '' : Ext.getCmp('queryTransDateId').getValue().format('Ymd'),
			queryTransTime:Ext.getCmp('queryTransTimeId').getValue().replace(/:/g,''),

			queryRetrivlRef:Ext.getCmp('queryRetrivlRefId').getValue(),
			queryTxnName:Ext.get('queryTxnName').getValue(),
			queryTransState:Ext.get('queryTransState').getValue(),
			queryRespCode:Ext.getCmp('queryRespCodeId').getValue(),
			queryBankName:Ext.get('queryBankName').getValue(),

			queryMchtTermId:Ext.getCmp('queryMchtTermIdId').getValue(),
			queryUpbrhTermId:Ext.getCmp('queryUpbrhTermIdId').getValue(),
			queryCardAccptId:Ext.get('queryCardAccpt').getValue(),
			queryMchtUpBrhId:Ext.get('queryMchtUpBrh').getValue().replace(/\*/g,''),

			queryBrh1Id:Ext.get('queryBrh1').getValue(),
			queryBrh2Id:Ext.get('queryBrh2').getValue(),
			queryBrh3Id:Ext.get('queryBrh3').getValue(),
			misc1:Ext.getCmp('misc01_query').getValue(),
			queryAreaCode:Ext.get('queryAreaCode').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});

