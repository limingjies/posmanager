Ext.onReady(function() {
	
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=txnTodayInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'instDate',mapping: 'instDate'},
			{name: 'instDateTime',mapping: 'instDateTime'},
			{name: 'pan',mapping: 'pan'},
			{name: 'cardAccpId',mapping: 'cardAccpId'},
			{name: 'cardAccpTermId',mapping: 'cardAccpTermId'},
			{name: 'termSsn',mapping: 'termSsn'},
			{name: 'sysSeqNum',mapping: 'sysSeqNum'},
			{name: 'brhId',mapping: 'brhId'},
			
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'termName',mapping: 'termName'},
			{name: 'mchtBrhId',mapping: 'mchtBrhId'},
			{name: 'mchtBrhName',mapping: 'mchtBrhName'},
			//{name: 'settleBrhName',mapping: 'settleBrhName'},
			{name: 'mchtGroup',mapping: 'mchtGroup'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'cardOrg',mapping: 'cardOrg'},
			
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'txnName',mapping: 'txnName'},
			{name: 'transState',mapping: 'transState'},
			{name: 'respName',mapping: 'respName'},			
//			{name: 'firstMchtName',mapping: 'firstMchtName'},
			{name: 'settleBrh',mapping: 'settleBrh'},
			{name: 'settleBrhNm',mapping: 'settleBrhNm'},
			{name: 'revsalSsn',mapping: 'revsalSsn'},
			{name: 'revsalFlag',mapping: 'revsalFlag'},
			{name: 'cancelSsn',mapping: 'cancelSsn'},
			{name: 'cancelFlag',mapping: 'cancelFlag'},
			{name: 'msgSrcId',mapping: 'msgSrcId'},
			{name: 'posEntryMode',mapping: 'posEntryMode'},
			{name: 'msgSrcRetrivlId',mapping: 'msgSrcRetrivlId'},
			{name: 'track3Data',mapping: 'track3Data'},
			
			{name: 'settleType',mapping: 'settleType'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'acctType',mapping: 'acctType'}
		]),
		autoLoad: true,
		listeners: {
			'load':function (me, records, success, opts) {
				tip.update('');
				if (txnGridStore.getCount() > 0){
					var totalRec = txnGridStore.getAt(txnGridStore.getCount()-1);
					var msg = "";
					msg += totalRec.get('instDate') + '&emsp;&emsp;' + totalRec.get('instDateTime') + totalRec.get('pan') + '&emsp;&emsp;' + totalRec.get('cardAccpTermId') + totalRec.get('termSsn');
					txnGridStore.remove(totalRec);
					tip.update(msg);
				}
			}
		}
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});
	*/
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '操作',dataIndex:'detail',width:80, align: 'center',
			renderer: function(){return "<a href='#'>[查看详细]</a>";}
		},
		{header: '卡号',dataIndex: 'pan',align: 'left',width: 120},
		{header: '发卡行',dataIndex: 'bankName',width: 150,align: 'left'},
		{header: '卡组织',dataIndex: 'cardOrg',width: 80,align: 'center',renderer:function(val){return (val)?(val=='0'?'银联':'外卡'):val}},
		{header: '账户类型',dataIndex: 'acctType',align: 'left',width: 80, renderer: acctType},
		{header: '交易日期',dataIndex: 'instDate',align: 'center',renderer: formatDt,width: 80},
		{header: '交易时间',dataIndex: 'instDateTime',align: 'center',renderer: formatDt,width: 80},
		{header: '交易类型',dataIndex: 'txnName',align: 'center',width: 80,id:'txnName',},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'right',width: 100},
		//20160504 guoyu upd
//		{header: '交易结果',dataIndex: 'transState',renderer: txnSta,align: 'center',width: 80},
		{header: '交易结果',dataIndex: 'transState',renderer: txnSta_ForFail,align: 'center',width: 80},
		{header: '交易应答',dataIndex: 'respName',width: 130},
		{header: '冲正结果',dataIndex: 'revsalFlag',align: 'center',width: 60,renderer:function(val){
			if(val == '0') {
				return '<font color="grey">未冲正</font>';
			} else if(val == '1') {
				return '<font color="red">已冲正</font>';
			}
			return '';}
		},
		{header: '终端号',dataIndex: 'cardAccpTermId',width: 80,align: 'center'},
		{header: '终端名称',dataIndex: 'termName',width: 250,align: 'left'},
		{header: '终端类型',dataIndex: 'termTp',width: 60,align: 'center',renderer: termForType},
		{header: '商户号',dataIndex: 'cardAccpId',width: 120,align: 'center',id:'cardAccpId',},
		{header: '商户名称',dataIndex: 'mchtName',width: 250,align: 'left',id:'mchtName',},
		{header: '合作伙伴',dataIndex: 'brhId',align: 'left',width: 180},
		{header: '结算类型',dataIndex: 'settleType',width: 80,align: 'center'},
		{header: '参考号',dataIndex: 'retrivlRef',align: 'center',width: 100},
		{header: '系统流水号',dataIndex: 'sysSeqNum',width: 80,align: 'center'},
		{header: '终端流水号',dataIndex: 'termSsn',width: 80,align: 'center'},
		{header: '渠道商户号',dataIndex: 'mchtBrhId',align: 'center',width: 120},
		{header: '渠道商户名称',dataIndex: 'mchtBrhName',width: 250,align: 'left'},	
	//	{header: '结算商户号',dataIndex: 'settleBrhName',width: 120,align: 'center'},
//		{header: '商户类型',dataIndex: 'mchtGroup',width: 120,align: 'center',renderer:mchtGroupFlag},
		{header: '交易通道',dataIndex: 'settleBrhNm',width: 90},
		{header: '通道检索参考号',dataIndex: 'msgSrcRetrivlId',width: 190,align: 'left'},
		{header: '账户系统应答信息',dataIndex: 'track3Data',width: 190,align: 'center'},
		{header: '冲正流水号',dataIndex: 'revsalSsn',width: 80,align: 'center',hidden:true},
		{header: '撤销流水号',dataIndex: 'cancelSsn',width: 80,align: 'center',hidden:true},
		{header: '撤销结果',dataIndex: 'cancelFlag',align: 'center',width: 60,hidden:true,renderer:function(val){
			if(val == '0') {
				return '<font color="grey">未撤销</font>';
			} else if(val == '1') {
				return '<font color="red">已撤销</font>';
			}
			return '';}
		},
		{header: '通信ID',dataIndex: 'msgSrcId',width: 60,align: 'center',hidden:true},
		{header: '输入方式',dataIndex: 'posEntryMode',width: 60,align: 'center',hidden:true}
	]);
	 
	function acctType(val){
		if (val == '0') {
			return "对公账户"
		} else if (val == '1') {
			return "对私账户"
		} else {
			return val;
		}
	}
/*         
	var tbar4 = new Ext.Toolbar({
		renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','冲正结果：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未冲正'],['1','已冲正']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryRevsalFlag',
				editable: false,
				emptyText: '请选择',
				id:'idqueryRevsalFlag',
				width: 120
			},'-','撤销流水：',{
				xtype: 'textfield',
				id: 'queryCancelSsn',
				name: 'queryCancelSsn',
				vtype: 'isNumber',
				width: 120
			},'-','撤销结果：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未撤销'],['1','已撤销']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryCancelFlag',
				editable: false,
				emptyText: '请选择',
				id:'idqueryCancelFlag',
				width: 120
			}
		]
	});
	*/
/*	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','金额下限：',{
					xtype: 'textfield',
					id: 'queryAmtTransLow',
					name: 'queryAmtTransLow',
					vtype:'isMoney',
					width: 120
	               	},'-','金额上限：',{
					xtype: 'textfield',
					id: 'queryAmtTransUp',
					name: 'queryAmtTransUp',
					vtype:'isMoney',
					width: 120
	               	},'-',	'交易类型：',{
					xtype: 'basecomboselect',
					id: 'idqueryTxnName',
					baseParams: 'TXN_TYPES',
					hiddenName: 'queryTxnName',
					width: 120
				    },'-','交易结果：',{
					xtype: 'basecomboselect',
					id: 'idqueryTransState',
					baseParams: 'TXN_STATUS',
					hiddenName: 'queryTransState',
					width: 120
					},'-','交易应答：',{
					xtype: 'textfield',
					id: 'queryRespCode',
					name: 'queryRespCode',
					maxLength: 2,
					minLength: 2,
					width: 120
					},'-','撤销结果：',{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','未撤销'],['1','已撤销']],
							reader: new Ext.data.ArrayReader()
						}),
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'queryCancelFlag',
						editable: false,
						emptyText: '请选择',
						id:'idqueryCancelFlag',
						width: 120
					}
				]  
         }) 
         */
    /*      var tbar3 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','系统流水：',{
					xtype: 'textfield',
					id: 'querySysSeqNum',
					name: 'querySysSeqNum',
					vtype: 'isNumber',
					width: 120
					},'-','终端编号：       ',{
					xtype: 'textfield',
					name: 'queryCardAccpTermId',
					id: 'queryCardAccpTermId',
					 vtype: 'isNumber',
					width: 120
				},'-','终端流水：',{
					xtype: 'textfield',
					id: 'queryTermSsn',
					name: 'queryTermSsn',
					 vtype: 'isNumber',
					width: 120
				},'-','交易卡号：',{
					xtype: 'textfield',
					id: 'queryPan',
					 vtype: 'isNumber',
					name: 'queryPan',
					width: 120
				},'-','交易通道：',{
					xtype: 'basecomboselect',
					id: 'querySettleBrhId',
					baseParams: 'SETTLE_BRH',
					hiddenName: 'querySettleBrh',
					width: 120
				},'-','参&nbsp;&nbsp;考&nbsp;&nbsp;号：',{
					xtype: 'textfield',
					id: 'queryRetrivlRef',
					name: 'queryRetrivlRef',
					vtype: 'isNumber',
					width: 120
					}] 
         }) 
	*/
	
      var tbar1 = new Ext.Toolbar({  
            renderTo: Ext.grid.EditorGridPanel.tbar,  
            items:[
            '-','卡&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号：',{
				xtype : 'textfield',
				id: 'queryCardPanId',
				hiddenName: 'queryCardPan',
				width: 150,
				maxLength:25,
				vtype: 'isNumber'
			},'-',	'卡&nbsp;&nbsp;组&nbsp;&nbsp;织：',{
		    	xtype: 'combo',
				id: 'queryCardOrgId',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','银联'],['1','外卡']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryCardOrg',
				editable: false,
				emptyText: '请选择',
				width: 150
//			},'-','商户类型： ',{
//				xtype: 'combo',
//				id:'queryMcntGroupId',
//				store: new Ext.data.ArrayStore({
//					fields: ['valueField','displayField'],
//					data: [['1','普通商户'],['2','集团商户'],['3','集团子商户']],
//					reader: new Ext.data.ArrayReader()
//				}),
//				displayField: 'displayField',
//				valueField: 'valueField',
//				hiddenName: 'queryMcntGroup',
//				editable: false,
//				emptyText: '请选择',
//				width: 150
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
      var tbar2 = new Ext.Toolbar({  
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
          '-','终端编号：',{
				xtype: 'textfield',
				hiddenName: 'queryCardAccpTerm',
				id: 'queryCardAccpTermId',
				vtype: 'isNumber',
				maxLength:8,
				width: 150
			},'-','终端名称：',{
				xtype: 'textfield',
				hiddenName: 'queryCardAccpTermName',
				id: 'queryCardAccpTermNameId',
				//vtype: 'isNumber',
				maxLength:20,
				width: 150
			},'-','参&nbsp;&nbsp;考&nbsp;&nbsp;号：',{
				xtype: 'textfield',
				id: 'queryRetrivlRefId',
				hiddenName: 'queryRetrivlRef',
				vtype: 'isNumber',
				maxLength:12,
				width: 150
			},'-','授&nbsp;&nbsp;权&nbsp;&nbsp;号：',{
				xtype: 'textfield',
				hiddenName: 'queryAuthNo',
				id: 'queryAuthNoId',
				vtype: 'isNumber',
				maxLength:6,
				width: 150
			},'-','结算类型：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['T+0','T+0'],['T+1','T+1']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'querySettleType',
				editable: false,
				emptyText: '请选择',
				id:'querySettleTypeId',
				width: 150
			}
         	]
   }); 
      var tbar3 = new Ext.Toolbar({  
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
           '-','商&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户：',{
				xtype : 'dynamicCombo',
				methodName : 'getAllMchntId',
				hiddenName: 'queryCardAccpt',
				width: 300,
				editable: true,
				id: 'queryCardAccptId',
				mode:'local',
				triggerAction: 'all',
				forceSelection: true,
				selectOnFocus: true,
				lazyRender: true
			},'-','合作伙伴：',{
				xtype : 'dynamicCombo',
				methodName : 'getBrhId',
				id: 'queryBrhId',
				hiddenName: 'queryBrh',
			    editable: true,
			    mode:'local',
				triggerAction: 'all',
				forceSelection: true,
				selectOnFocus: true,
				lazyRender: true,
				width: 250
			},'-','渠道商户：',{
	  			xtype: 'dynamicCombo',
	  			methodName: 'getUpbrhMcht',
	  			hiddenName: 'queryMchtUp',
	  			id: 'queryMchtUpId',
	  			mode:'local',
	  			triggerAction: 'all',
	  			editable: true,
	  			lazyRender: true,
	  			width: 300
	            }
         	]
      });   
    
    var tbar4 = new Ext.Toolbar({
    	renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
           '-',	'交易类型：',{
				xtype: 'basecomboselect',
				id: 'queryTxnNameId',
				baseParams: 'TXN_TYPES',
				hiddenName: 'queryTxnName',
				width: 150
			},'-','交易结果：',{
				xtype: 'basecomboselect',
				id: 'queryTransStateId',
				baseParams: 'TXN_STATUS',
				hiddenName: 'queryTransState',
				width: 150,
				editable: false,
			},'-','冲正结果：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未冲正'],['1','已冲正']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryRevsalFlag',
				editable: false,
				emptyText: '请选择',
				id:'queryRevsalFlagId',
				width: 150
			},'-','撤销结果：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','未撤销'],['1','已撤销']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'queryCancelFlag',
				editable: false,
				emptyText: '请选择',
				id:'queryCancelFlagId',
				width: 150
			}
        ]
    });
    var tbar5 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','终端流水号：',{
				xtype: 'textfield',
				id: 'queryTermSsnId',
				hiddenName: 'queryTermSsn',
				vtype: 'isNumber',
				maxLength:6,
				minLength:6,
				width: 138
			},'-','系统流水号：',{
				xtype: 'textfield',
				id: 'querySysSeqNumId',
				hiddenName: 'querySysSeqNum',
				vtype: 'isNumber',
				maxLength:6,
				minLength:6,
				width: 140
			}]
        });     
    
	var tip= new Ext.form.Label();
	var tipBar = new Ext.Toolbar({items:[tip]});
    
    
    var tbar6 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
       			'-','金额下限：',{
				xtype: 'textfield',
				id: 'queryAmtTransLow',
				name: 'queryAmtTransLow',
				regex:/^\d+(\.\d+)?$/,	// /^[0-9]*$/
				width: 120
           	},'-','金额上限：',{
				xtype: 'textfield',
				id: 'queryAmtTransUp',
				name: 'queryAmtTransUp',
				regex:/^\d+(\.\d+)?$/,	// /^[0-9]*$/
				width: 120
           	},'-','交易应答：',{
	  			xtype: 'dynamicCombo',
	  			methodName: 'getRespName',
	  			hiddenName: 'respName',
	  			id: 'respNameId',
	  			mode:'local',
	  			triggerAction: 'all',
	  			editable: true,
	  			lazyRender: true,
	  			width: 200
	  		},'-','账户类型：',{
				xtype: 'combo',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['0','对公账户'],['1','对私账户']],
					reader: new Ext.data.ArrayReader()
				}),
				displayField: 'displayField',
				valueField: 'valueField',
				hiddenName: 'acctType',
				editable: false,
				emptyText: '请选择',
				id:'acctTypeId',
				width: 100
			},'-','终端类型：',{
				xtype : 'basecomboselect',
				baseParams : 'TERM_TYPE',
				fieldLabel : '终端类型',
				id : 'termTypeId',
				hiddenName : 'termType',
				anchor : '50%',
				width: 100
			}] 
    }) 

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
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",txnGrid);
//				if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
				Ext.Ajax.request({
					url: 'T50106Action.asp',
					timeout: 60000,
					params: {
						queryCardAccptId: Ext.get('queryCardAccpt').getValue(),
						queryBrhId: Ext.get('queryBrh').getValue(),
						queryCardAccpTermId: Ext.getCmp('queryCardAccpTermId').getValue(),
						queryPan: Ext.getCmp('queryCardPanId').getValue(),
					//	queryTermSsn: Ext.get('queryTermSsn').getValue(),
						queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),//金额下限
						queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),//金额上限
						respName: Ext.getCmp('respNameId').getValue(),//交易应答
						acctType: Ext.getCmp('acctTypeId').getValue(),//账户类型
						termType: Ext.getCmp('termTypeId').getValue(),//终端类型
						queryTxnName: Ext.getCmp('queryTxnNameId').getValue(),			
						queryTransState: Ext.get('queryTransState').getValue(),
					//	queryRespCode: Ext.getCmp('queryRespCode').getValue(),
					//	querySettleBrh: Ext.get('querySettleBrh').getValue(),
					//	queryRevsalSsn: Ext.get('queryRevsalSsn').getValue(),
						queryRevsalFlag: Ext.get('queryRevsalFlag').getValue(),
						queryRetrivlRef: Ext.getCmp('queryRetrivlRefId').getValue(),
					//	queryCancelSsn: Ext.get('queryCancelSsn').getValue(),
						queryCancelFlag: Ext.get('queryCancelFlag').getValue(),
					//	querySysSeqNum:Ext.get('querySysSeqNum').getValue(),
						queryBankName:Ext.get('queryBankName').getValue(),
						queryCardOrg:Ext.get('queryCardOrg').getValue(),
//						queryMcntGroup:Ext.get('queryMcntGroup').getValue(),
						queryCardAccpTermName:Ext.getCmp('queryCardAccpTermNameId').getValue(),
						queryAuthNo:Ext.getCmp('queryAuthNoId').getValue(),
					//	queryCardSettleNo:Ext.get('queryCardSettleNoId').getValue(),
						queryMchtUp:Ext.get('queryMchtUp').getValue().replace(/\*/g,''),
						querySettleType: Ext.get('querySettleType').getValue(),
						queryTermSsn   : Ext.get('queryTermSsnId').getValue(),
						querySysSeqNum: Ext.get('querySysSeqNumId').getValue(),
						txnId: '50106',
						subTxnId: '01'
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
					failure: function(){
						hideMask();
						showErrorMsg(rspObj.msg,txnGrid);
					}
				});
//				txnGridStore.load();
			   /* } else {
			    	hideMask();
			    	Ext.MessageBox.show({
						msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
						title: '报表下载说明',
						buttons: Ext.MessageBox.OK,
						modal: true,
						width: 220
					});
				}*/
			}},'-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				var pan = Ext.getCmp('queryCardPanId').getValue().trim();
				var l = pan.length;
				if(l> 0 && (l <6 || l>6 && l<10)){
					alert('请输入6位卡BIN、或10位短卡号、或全卡号！' );
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
				Ext.getCmp('queryCardAccptId').setValue('');
				Ext.getCmp('queryBrhId').setValue('');
				Ext.getCmp('queryCardAccpTermId').setValue('');
				Ext.getCmp('queryCardPanId').setValue('');
			//	Ext.getCmp('queryTermSsn').setValue('');
				Ext.getCmp('queryAmtTransLow').setValue('');
				Ext.getCmp('queryAmtTransUp').setValue('');
				Ext.getCmp('respNameId').setValue('');//交易应答
				Ext.getCmp('acctTypeId').setValue('');//账户类型
				Ext.getCmp('termTypeId').setValue('');//终端类型
			//	Ext.getCmp('querySysSeqNum').setValue('');
				Ext.getCmp('queryTxnNameId').setValue('');
				Ext.getCmp('queryTransStateId').setValue('');
			//	Ext.getCmp('queryRespCode').setValue('');
			//	Ext.getCmp('querySettleBrhId').setValue('');
			//	Ext.getCmp('queryRevsalSsn').setValue('');
				Ext.getCmp('queryRetrivlRefId').setValue('');
			//	Ext.getCmp('queryCancelSsn').setValue('');
				Ext.getCmp('queryCancelFlagId').setValue('');
				Ext.getCmp('queryRevsalFlagId').setValue('');
				Ext.getCmp('queryBankNameId').setValue('');
				Ext.getCmp('queryBankNameId').getStore().reload(); 
				Ext.getCmp('queryCardOrgId').setValue('');
			//	Ext.getCmp('queryCardOrgId').getStore().reload();
//				Ext.getCmp('queryMcntGroupId').setValue('');
			//	Ext.getCmp('queryMcntGroupId').getStore().reload();
				Ext.getCmp('queryCardAccpTermNameId').setValue('');
				Ext.getCmp('queryAuthNoId').setValue('');
			//	Ext.getCmp('queryCardSettleNoId').setValue(''),
				Ext.getCmp('queryMchtUpId').setValue('');
				
				Ext.getCmp('querySettleTypeId').setValue('');
//				txnGridStore.load();
//				mchtStore.removeAll();
//				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
//					mchtStore.loadData(Ext.decode(ret));
//				});
				Ext.getCmp('queryCardAccptId').getStore().reload();
				Ext.getCmp('queryMchtUpId').getStore().reload();
				Ext.getCmp('queryBrhId').getStore().reload();
				Ext.getCmp('queryTermSsnId').setValue('');
				Ext.getCmp('querySysSeqNumId').setValue('');
			}	
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar1.render(this.tbar);  
					tbar2.render(this.tbar);
					tbar4.render(this.tbar);
					tbar3.render(this.tbar);
					tbar6.render(this.tbar);
					tbar5.render(this.tbar);
					tipBar.render(this.tbar);
                }  ,
				'cellclick': function(grid, rowIndex, columnIndex, e) {
		        	selectableCell();
			    	//这里得到的是一个对象，即你点击的某一行的一整行数据
			        var record = grid.getSelectionModel().getSelected();
			        var columns = txnColModel;
			        var clickFieldName = columns.getDataIndex(columnIndex);    
			        //alert(clickFieldName);
			        //通过加判断条件限制当点击某个列表内容的时候触发，不然只要你点列表的随便一个地方就触发了。  
			        if (clickFieldName == 'detail'){
			        	Ext.MessageBox.show({
			        		msg: getGridRowDetail(record, columns),
			        		title: '详细信息',
			        		buttons: Ext.MessageBox.OK,
			        		modal: true
			        	});
			        }
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
			queryCardAccpt: Ext.get('queryCardAccpt').getValue(),
			queryBrh: Ext.get('queryBrh').getValue(),
			queryCardAccpTerm: Ext.getCmp('queryCardAccpTermId').getValue(),
			queryPan: Ext.getCmp('queryCardPanId').getValue(),
		//	queryTermSsn: Ext.get('queryTermSsn').getValue(),
			queryAmtTransLow: Ext.getCmp('queryAmtTransLow').getValue(),
			queryAmtTransUp: Ext.getCmp('queryAmtTransUp').getValue(),
			respName: Ext.getCmp('respNameId').getValue(),//交易应答
			acctType: Ext.getCmp('acctTypeId').getValue(),//账户类型
			termType: Ext.getCmp('termTypeId').getValue(),//终端类型
			queryTxnName: Ext.getCmp('queryTxnNameId').getValue(),			
			queryTransState: Ext.get('queryTransState').getValue(),
		//	queryRespCode: Ext.getCmp('queryRespCode').getValue(),
		//	querySettleBrh: Ext.get('querySettleBrh').getValue(),
		//	queryRevsalSsn: Ext.get('queryRevsalSsn').getValue(),
			queryRevsalFlag: Ext.get('queryRevsalFlag').getValue(),
			queryRetrivlRef: Ext.getCmp('queryRetrivlRefId').getValue(),
	    //  queryCancelSsn: Ext.get('queryCancelSsn').getValue(),
			queryCancelFlag: Ext.get('queryCancelFlag').getValue(),
	    //	querySysSeqNum:Ext.get('querySysSeqNum').getValue(),
			queryBankName:Ext.get('queryBankName').getValue(),
			queryCardOrg:Ext.get('queryCardOrg').getValue(),
//			queryMcntGroup:Ext.get('queryMcntGroup').getValue(),
			queryCardAccpTermName:Ext.getCmp('queryCardAccpTermNameId').getValue(),
			queryAuthNo:Ext.getCmp('queryAuthNoId').getValue(),
        //	queryCardSettleNo:Ext.get('queryCardSettleNoId').getValue(),
			queryMchtUp:Ext.get('queryMchtUp').getValue().replace(/\*/g,''),
			querySettleType: Ext.get('querySettleType').getValue(),
			queryTermSsn   : Ext.get('queryTermSsnId').getValue(),
			querySysSeqNum: Ext.get('querySysSeqNumId').getValue()
		});
		
		
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});