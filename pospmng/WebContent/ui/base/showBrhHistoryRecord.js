function showBrhHistoryRecord(brhId){
	var popupWin = null;
	var detailPanel = null;	
	var detailCompareForm = null;
    var compareDtlItems = [];

	var detailStore1 = new Ext.data.Store();
	var detailStore2 = new Ext.data.Store();
	
	var feeCtlStore1 = null;
	var feeCtlStore2 = null;
	var feeCtlGrid1 = null;
	var feeCtlGrid2 = null;
	
	var historyStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhHistoryRecord'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
//			idProperty: 'brhId'
		},[
			{name: 'brhId',mapping: 'brhId'},
			{name: 'seqId',mapping: 'seqId'},
			{name: 'createNewNo',mapping :'createNewNo'},
			{name: 'brhLvl',mapping: 'brhLvl'},
//			{name: 'upBrhId',mapping: 'upBrhId'},
			{name: 'upCreateNewNo',mapping :'upCreateNewNo'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'brhAddr',mapping: 'brhAddr'},
			{name: 'brhTelNo',mapping: 'brhTelNo'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'brhContName',mapping: 'brhContName'},
			{name: 'resv1',mapping: 'resv1'},
			{name: 'cupBrhId',mapping: 'cupBrhId'},
			{name: 'resv2',mapping: 'resv2'},
			{name: 'discName',mapping: 'discName'},
			{name: 'regDate',mapping: 'regDate'},
			{name: 'lastUpdTs',mapping: 'lastUpdTs'}
		]),
//		autoLoad: true,
		listeners: {
			load:function (me, records, success, opts) {
				if (historyStore.data.length >= 2) {
					grid.getSelectionModel().selectRows([0,1]);
					showCompareDetail();
				}
			}
		}
	});
		
	historyStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			brhId: brhId
		});
	});

	historyStore.load({
		params: {
			start: 0,
			brhId: brhId
		}
	});
	
	var historyColModel = new Ext.grid.ColumnModel([
   		new Ext.grid.CheckboxSelectionModel(),
   		{header: '最后更新时间',dataIndex: 'lastUpdTs',sortable: true,renderer: formatDt, width: 140},
		{id: 'brhId',header: '编号',dataIndex: 'brhId',sortable: true,width: 70},
		{id: 'seqId',dataIndex: 'seqId',hidden: true,width: 0},
		{header : '合作伙伴号',dataIndex : 'createNewNo',width : 100},
		{header: '级别',dataIndex: 'brhLvl',hidden:true,renderer: brhLvlRender,width: 100},
//	                                    		{header: '上级编号',dataIndex: 'upBrhId',width: 80},
		{header: '上级合作伙伴号',hidden:true,dataIndex: 'upCreateNewNo',width: 100},
		{header: '合作伙伴名称',dataIndex: 'brhName',width: 150},
		{id:'brhAddr',header: '合作伙伴地址',dataIndex: 'brhAddr',width: 108},
		{header: '联系方式',dataIndex: 'brhTelNo',width: 110},
		{header: '联系人',dataIndex: 'brhContName',width: 130},
		{header: '所在地区',dataIndex: 'resv1',width: 80},
//	                                    		{header: '银联编号',dataIndex: 'cupBrhId',width: 80},
//	                                    		{header: '密钥索引',dataIndex: 'resv2',width: 60},
		{header: '费率名称',dataIndex: 'discName',width: 80,hidden:true},
		{header: '申请日期',dataIndex: 'regDate',width: 80,renderer:formatDt},
		{header: '操作',dataIndex:'detail',width:80,
			renderer: function(){return "<button type='button' onclick=''>详细信息</button>";}
		}
	]);
	
	
//	-----------------------------------费率配置----------------------------------------------

	function getFeeCtlStore(brhId, seqId, nextStep) {
		var feeCtlStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=agentSelFeeCtlHis&brhId='+brhId+ '&seqId=' + seqId
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'rateId',mapping: 'rateId'},
				{name: 'feeName',mapping: 'feeName'},
				{name: 'feeType',mapping: 'feeType'},
				{name: 'feeRate',mapping: 'feeRate'},
				{name: 'discId',mapping: 'discId'}
			]),
			autoLoad: true
		}); 
		
		feeCtlStore.load({callback:function(){
				nextStep();
			}
    	});
		
		feeCtlStore.on('beforeload', function(){
			Ext.apply(this.baseParams, {
				start: 0
			});
		});
		
		return feeCtlStore;
	}
	
	var feeCtlColModel = new Ext.grid.ColumnModel([
   		new Ext.grid.RowNumberer(),
   		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
   		{header: '费率名称',dataIndex: 'feeName',align: 'center',width: 120,id:'feeName'},
   		{header: '费率类型',dataIndex: 'feeType',align: 'left',width: 120,id:'feeType',renderer:feeType },
   		{header: '手续费',dataIndex: 'feeRate',align: 'left',width: 120,id:'feeRate'  }
   	]);
	
	
	function getFeeCtlGrid(dataStore, date) {
		var feeCtlGrid = new Ext.grid.GridPanel({
		 	height:200,
			collapsible: false,
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
			region: 'west',
			clicksToEdit: true,
			autoExpandColumn: 'feeName',
			store: dataStore,
			cm: feeCtlColModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载分润费率信息......'
			},
			listeners : {  
	            'render' : function() {  
	         		}
	        }  ,
	        tbar: 	[{
				xtype: 'label',
				text: date + '已开通分润费率',
				style: 'color:red;font-weight:bold;',
				name: 'queryFeeCtlD',
				id: 'queryFeeCtlD',
				iconCls: 'query',
				width: 80,
				handler:function() {
					dataStore.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: dataStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});

		return feeCtlGrid;
	}
	
	function getBrhCashRateStore(brhId, seqId, nextStep) {
		var brhCashStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=brhCashRateInfSel&brhId='+brhId + '&seqId=' + seqId
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'rateId',mapping: 'rateId'},
				{name: 'name',mapping: 'name'},
				{name: 'type',mapping: 'type'},
				{name: 'rate',mapping: 'rate'}
			]),
			autoLoad: true
		}); 
		
		brhCashStore.load({callback:function(){
				nextStep();
			}
    	});
		
		brhCashStore.on('beforeload', function(){
			Ext.apply(this.baseParams, {
				start: 0
			});
		});
		
		return brhCashStore;
	}
	
	var brhCashRateColModel = new Ext.grid.ColumnModel([
  		new Ext.grid.RowNumberer(),
  		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
  		{header: '费率名称',dataIndex: 'name',align: 'center',width: 120,id:'name'},
  		{header: '费率类型',dataIndex: 'type',align: 'left',width: 120,id:'type',renderer:feeType },
  		{header: '手续费',dataIndex: 'rate',align: 'left',width: 120,id:'rate'}
  	]);
	
	
	function getBrhCashRateGrid(dataStore, date) {
		 var brhCashRateGrid = new Ext.grid.EditorGridPanel({
			 	height:400,
				collapsible: false,
				iconCls: 'risk',
				frame: true,
				border: true,
				columnLines: true,
				stripeRows: true,
				region: 'west',
				clicksToEdit: true,
				autoExpandColumn: 'name',
				store: dataStore,
//				sm: checkboxSM1,
				cm: brhCashRateColModel,
				forceValidation: true,
				loadMask: {
					msg: '正在加载合作伙伴提现费率信息......'
				},
				listeners : {  
		         	'cellclick':selectableCell
		        }  ,
		        tbar: 	[{
					xtype: 'label',
					text: date + '已开通提现费率',
					style: 'color:red;font-weight:bold;',
					name: 'queryBrhCashRate',
					id: 'queryBrhCashRate',
					iconCls: 'query',
					width: 80,
					handler:function() {
						dataStore.load();
					}
				}],
				bbar: new Ext.PagingToolbar({
					store: dataStore,
					pageSize: System[QUERY_RECORD_COUNT],
					displayInfo: true,
					displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg: '没有找到符合条件的记录'
				})
			});

		return brhCashRateGrid;
	}	
	
	
	
//	-----------------------------费率信息END--------------------------------------
	
	// -----------------------------------------详细对比用------开始---------------------------------
    // 
	// val: mix string
	function getSettleType(val) {
		if(val != null && val != '')
			return val.substr(0,1)=='0'?'T+N':'周期结算';
		return '　';
	}
	// val: mix string
	function getSettleTN(val) {
		if(val == null || val == '')
			return '　';
		return val.substr(0,1)=='0'?parseInt(val.substr(2,3),10):'　';
	}
	// val: mix string
	function getSettlePeroid(val) {	
		if(val == null || val == '')
			return '　';
		if (val.substr(0,1) != '1')
			return '　';
		var period = val.substr(1,1);
		if (period == '0')
			return '周结';
		if (period == '1')
			return '月结';
		if (period == '2')
			return '季结';
		return '　';
	}
	
	function yesNo(val) {
		return val == '0'?'否':'是';
	}
	
	function genYesNo(val) {
		return val == '0'?'不生成':'生成';
	}
	
	function settllYesNo(val) {
		return val == '0'?'不清算':'清算';
	}
	
	function settleAccType(val) {
		return val == '0'?'对公账户':'对私账户';
	}
	
    var compareColumn = [
 			{name:'brhIdDt', text:'合作伙伴编码', funcFormat:null},
			{name:'createNewNoDt', text:'合作伙伴号', funcFormat:null},
			/*{name:'brhLevelDt', text:'合作伙伴级别', funcFormat:null},
			{name:'upBrhIdDt', text:'上级合作伙伴编码', funcFormat:null},*/
			{name:'brhNameDt', text:'合作伙伴名称', funcFormat:null},
			{name:'settleOrgNoDt', text:'机构代码', funcFormat:null},
			{name:'settleMemPropertyDt', text:'人员属性', funcFormat:null},
			{name:'settleJobTypeDt', text:'职务类别', funcFormat:null},
			{name:'resv1Dt', text:'合作伙伴地区码', funcFormat:null},
			{name:'brhAddrDt', text:'合作伙伴地址', funcFormat:null},
			{name:'brhTelNoDt', text:'合作伙伴联系电话', funcFormat:null},
			{name:'brhContNameDt', text:'负责人姓名', funcFormat:null},
			{name:'resv1_5Dt', text:'是否系统级签到', funcFormat:yesNo},
			{name:'resv1_6Dt', text:'生成对账流水', funcFormat:genYesNo},
			{name:'resv1_7Dt', text:'清算到机构账号', funcFormat:settllYesNo},
			{name:'settleFlagDt', text:'结算账户类型', funcFormat:settleAccType},
			{name:'settleType', text:'结算类型', funcFormat:getSettleType},
			{name:'settleType', text:'T+N', funcFormat:getSettleTN},
			{name:'settleType', text:'周期结算', funcFormat:getSettlePeroid},
			{name:'settleAcctNmDt', text:'账户户名', funcFormat:null},
			{name:'settleAcctDt', text:'账户号', funcFormat:null},
			{name:'province', text:'开户银行-省', funcFormat:null},
			{name:'city', text:'开户银行-市', funcFormat:null},
			{name:'bank_name', text:'开户银行', funcFormat:null},
			{name:'settleBankNmDt', text:'账户开户行名称', funcFormat:null},
			{name:'settleBankNoDt', text:'账户开户行号', funcFormat:null},
			{name:'promotionBegDate', text:'激励开始日期', funcFormat:null},
			{name:'promotionEndDate', text:'激励结束日期', funcFormat:null},
			{name:'baseAmtMonth', text:'月交易额(万元)*', funcFormat:null},
			{name:'gradeAmtMonth', text:'提档交易额(万元)*', funcFormat:null},
			{name:'promotionRate', text:'激励系数%*', funcFormat:null},
			{name:'allotRate', text:'分润比率%*', funcFormat:null},
			{name:'misc', text:'分润封顶值%', funcFormat:null},
			{name:'extRate1', text:'提现垫资分润比例%', funcFormat:null},
			{name:'extVisaRate', text:'VISA成本费率%', funcFormat:null},
			{name:'extMasterRate', text:'MASTER成本费率%', funcFormat:null},
			{name:'extJcbRate', text:'JCB成本费率%', funcFormat:null}];
    //alert(compareColumn.length + '  ' + compareColumn[0].name + ':' + compareColumn[0].text);
    //
    function getBrhInfoStore(brhId, seqId, nextStep) {
    	
    	var brhDetailStore = new Ext.data.Store({
    		proxy : new Ext.data.HttpProxy({
    					url : 'loadRecordAction.asp?storeId=getAgentInfHis&brhId='+brhId+ '&seqId=' + seqId
    				}),
    		reader : new Ext.data.JsonReader({
    			root : 'data'
    			}, [
    				{name : 'brhIdDt',mapping : 'id.brhId'}, 
    				{name : 'brhLevelDt',mapping : 'brhLevel'},
    				{name : 'upBrhIdDt',mapping : 'upBrhId'},
    				{name : 'brhNameDt',mapping : 'brhName'},
    				{name : 'brhAddrDt',mapping : 'brhAddr'},
    				{name : 'brhTelNoDt',mapping : 'brhTelNo'},
    				{name : 'postCodeDt',mapping : 'postCode'},
    				{name : 'brhContNameDt',mapping : 'brhContName'},
    				{name : 'cupBrhIdDt',mapping : 'cupBrhId'},
    				{name : 'resv1Dt',mapping : 'resv1'},
    				{name : 'resv2Dt',mapping : 'resv2'},
    				
    				{name : 'settleOrgNoDt',mapping : 'settleOrgNo'},
    				{name : 'settleJobTypeDt',mapping : 'settleJobType'},
    				{name : 'settleMemPropertyDt',mapping : 'settleMemProperty'},
    				{name : 'createNewNoDt',mapping : 'createNewNo'},
    				
    				{name : 'resv1_5Dt',mapping : 'resv1_5'},
    				{name : 'resv1_6Dt',mapping : 'resv1_6'},
    				{name : 'resv1_7Dt',mapping : 'resv1_7'},
    				{name : 'resv1_8Dt',mapping : 'resv1_8'},
    				{name : 'settleFlagDt',mapping : 'settleFlag'},
    				{name : 'settleAcctNmDt',mapping : 'settleAcctNm'},
    				{name : 'settleAcctDt',mapping : 'settleAcct'},
    				{name : 'settleBankNmDt',mapping : 'settleBankNm'},
    				{name : 'settleBankNoDt',mapping : 'settleBankNo'},
    				{name : 'brhFeeDt',mapping : 'brhFee'},
    				
    				{name:'agentNo',mapping:'agentNo'},
    				{name:'allotRate',mapping:'allotRate'},
    				{name:'amt1',mapping:'amt1'},
    				{name:'amt2',mapping:'amt2'},
    				{name:'baseAmtMonth',mapping:'baseAmtMonth'},
    				{name:'crtTime',mapping:'crtTime'},
    				{name:'discId',mapping:'discId'},
    				{name:'enableFlag',mapping:'enableFlag'},
    				{name:'extJcbRate',mapping:'extJcbRate'},
    				{name:'extMasterRate',mapping:'extMasterRate'},
    				{name:'extRate1',mapping:'extRate1'},
    				{name:'extRate2',mapping:'extRate2'},
    				{name:'extRate3',mapping:'extRate3'},
    				{name:'extVisaRate',mapping:'extVisaRate'},
    				{name:'flag1',mapping:'flag1'},
    				{name:'flag2',mapping:'flag2'},
    				{name:'gradeAmtMonth',mapping:'gradeAmtMonth'},
    				{name:'mchtGrp',mapping:'mchtGrp'},
    				{name:'mchtNo',mapping:'mchtNo'},
    				{name:'misc',mapping:'misc'},
    				{name:'misc2',mapping:'misc2'},
    				{name:'name',mapping:'name'},
    				{name:'promotionBegDate',mapping:'promotionBegDate'},
    				{name:'promotionEndDate',mapping:'promotionEndDate'},
    				{name:'promotionRate',mapping:'promotionRate'},
    				{name:'seq',mapping:'seq'},
    				{name:'specFeeRate',mapping:'specFeeRate'},
    				{name:'uptOpr',mapping:'uptOpr'},
    				{name:'uptTime',mapping:'uptTime'},
    				
    				{name:'bank_name',mapping:'bank_name'},
    				{name:'subbranch_name',mapping:'subbranch_name'},
    				{name:'province',mapping:'province'},
    				{name:'subbranch_id',mapping:'subbranch_id'},
    				{name:'city',mapping:'city'},
    				
    				{name : 'settleType',mapping : 'resv4'},
    			]),
    		autoLoad : false
    	});
    	brhDetailStore.load({callback:function(){
    			nextStep();
    		}
    	});
    	//alert(detailGridStore.getAt(0).data.brhId);
    	return brhDetailStore;
    }
    
    function makeCompareRow(text1, text2, text3) {
    	var style = 'text-algin:left;';
    	if (text2 != text3) {
    		style += 'marging-top:20;color:red;font-weight:bold;'
    	}
    	var items = [{
			columnWidth : .99,
			layout : 'column',
			items:[{
				columnWidth : .2,
				layout : 'form',
				items : [{
					xtype: 'label',
					text : text1,
				}]
	    	},{
				columnWidth : .4,
				layout : 'form',
				items : [{
					xtype: 'label',
					text : text2,
				}]
	    	},{
				columnWidth : .4,
				layout : 'form',
				items : [{
					xtype: 'label',
					text : text3,
				}]
	    	}]
    	}];
    	var row = new Ext.Panel({
			name: 'row',
			layout: 'form',
			style: style,
			items: items
		});
    	compareDtlItems.push(row);
    }
    
    function getCompareDetail(dt1, dt2, compareColumn, detailStore1, detailStore2) {
    	makeCompareRow('最终更新日期', dt1, dt2);
    	for (var i = 0; i < compareColumn.length; i++){
    		var cmpValue1 = detailStore1.getAt(0).get(compareColumn[i].name);
    		var cmpValue2 = detailStore2.getAt(0).get(compareColumn[i].name);
    		if (compareColumn[i].funcFormat != null) {
    			cmpValue1 = compareColumn[i].funcFormat(cmpValue1);
    			cmpValue2 = compareColumn[i].funcFormat(cmpValue2);
    		}
    		makeCompareRow(compareColumn[i].text, cmpValue1, cmpValue2);
    	}
    }
    
	detailCompareForm = new Ext.form.FormPanel({
		region: 'center',
		frame : true,
		autoScroll : true,
		height: 280,
		layout : 'form',
		waitMsgTarget : true,
		width:1005,
		defaults : {
			bodyStyle : 'padding:35 0 35 0;overflow-y:auto;'
		},items : compareDtlItems
	});
	
	function showCompareDetail(){
		var rows = grid.getSelectionModel().getSelections();
		if (rows.length > 2 || rows.length < 2) {
			showErrorMsg('请选择两条记录进行对比！', grid);
			return;
		}
		var dt1 = formatDt(rows[0].get('lastUpdTs'));
		var dt2 = formatDt(rows[1].get('lastUpdTs'));			
		var loadMask = new Ext.LoadMask(Ext.getBody(), {
			msg: '正在加载详细对比信息...',
			removeMask: true
		});
		Ext.getCmp('detailCmp').setDisabled(true);
		// 清空内容
		detailCompareForm.items.eachKey(function(key,item){  
			Ext.getCmp(key).removeAll();
		});
		detailCompareForm.doLayout();
		loadMask.show();
		// 加载选中记录的详细信息
		detailStore1 = getBrhInfoStore(rows[0].get('brhId'), rows[0].get('seqId'), function(){
			detailStore2 = getBrhInfoStore(rows[1].get('brhId'), rows[1].get('seqId'), function(){
				// 清空项目列表
				compareDtlItems = [];
				// 将要显示的内容填入compareDtlItems
				getCompareDetail(dt1, dt2, compareColumn, detailStore1, detailStore2);
				// 显示分润信息
				showFeeCtlCompare(dt1, dt2, rows[0], rows[1], function() {				
//					// 分润回调，显示基本信息
//					detailCompareForm.items.addAll(compareDtlItems);
//					// 页面重绘
//					detailCompareForm.doLayout();
//					popupWin.doLayout();
//					// 按钮有效化
//					Ext.getCmp('detailCmp').setDisabled(false);
//					loadMask.hide();
				});
				showBrhCashRateCompare(dt1, dt2, rows[0], rows[1], function() {				
					// 分润回调，显示基本信息
					detailCompareForm.items.addAll(compareDtlItems);
					// 页面重绘
					detailCompareForm.doLayout();
					popupWin.doLayout();
					// 按钮有效化
					Ext.getCmp('detailCmp').setDisabled(false);
					loadMask.hide();
				});
			});
		});
	}
	
	function showFeeCtlCompare(date1, date2, row0, row1, callback) {
		feeCtlStore1 = getFeeCtlStore(row0.get('brhId'), row0.get('seqId'), function(){
			feeCtlStore2 = getFeeCtlStore(row1.get('brhId'), row1.get('seqId'), function(){
				feeCtlGrid1 = getFeeCtlGrid(feeCtlStore1, date1);
				feeCtlGrid2 = getFeeCtlGrid(feeCtlStore2, date2);
				
		    	var row = new Ext.Panel({
					name: 'row',
					layout: 'form',
					items: [{
						columnWidth : .99,
						layout : 'column',
						items:[{
							columnWidth : .1,
							layout : 'form',
							items : [{
								xtype: 'label',
								text : '分润信息',
							}]
				    	},{
							columnWidth : .45,
							layout : 'form',
							items : feeCtlGrid1
				    	},{
							columnWidth : .45,
							layout : 'form',
							items : feeCtlGrid2
				    	}]
			    	}]
				});
		    	compareDtlItems.push(row);
		    	callback();
			});
		});
	}
	function showBrhCashRateCompare(date1, date2, row0, row1, callback) {
		brhCashStore1 = getBrhCashRateStore(row0.get('brhId'), row0.get('seqId'), function(){
			brhCashStore2 = getBrhCashRateStore(row1.get('brhId'), row1.get('seqId'), function(){
				brhCashGrid1 = getBrhCashRateGrid(brhCashStore1, date1);
				brhCashGrid2 = getBrhCashRateGrid(brhCashStore2, date2);
				
		    	var row = new Ext.Panel({
					name: 'row',
					layout: 'form',
					items: [{
						columnWidth : .99,
						layout : 'column',
						items:[{
							columnWidth : .1,
							layout : 'form',
							items : [{
								xtype: 'label',
								text : '提现费率信息',
							}]
				    	},{
							columnWidth : .45,
							layout : 'form',
							items : brhCashGrid1
				    	},{
							columnWidth : .45,
							layout : 'form',
							items : brhCashGrid2
				    	}]
			    	}]
				});
		    	compareDtlItems.push(row);
		    	callback();
			});
		});
	}
    // -----------------------------------------详细对比用------结束---------------------------------
	
	var grid = new Ext.grid.GridPanel({
		region: 'center',
		height:175,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: historyStore,
		//autoExpandColumn: 'oprInfo',
		sm: new Ext.grid.CheckboxSelectionModel(),
		cm: historyColModel,
		loadMask: {
			msg: '正在加载合作伙伴修改历史信息列表......'
		},
		tbar: ['-',{
			xtype: 'button',
			text: '查看详细对比',
			name: 'detail',
			id: 'detailCmp',
			iconCls: 'detail',
			width: 80, 
			listeners:{
				click:function() {
					if (historyStore.data.length >= 2) {
						showCompareDetail();
					}
				}
			}
		}],
		bbar: new Ext.PagingToolbar({
			store: historyStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
//			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
    grid.addListener('cellclick',cellclick); //添加触发的函数     
    function cellclick(grid, rowIndex, columnIndex, e) {
    	//这里得到的是一个对象，即你点击的某一行的一整行数据
        //var record = grid.getStore().getAt(rowIndex);
        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);    
        //通过加判断条件限制当点击某个列表内容的时候触发，不然只要你点列表的随便一个地方就触发了。  
        if (fieldName == 'detail'){
			var brhId = grid.getSelectionModel().getSelected().get('brhId');
			var seqId = grid.getSelectionModel().getSelected().get('seqId');
			showBrhInfDetailS( brhId, grid, 'history', null, seqId);
        }
    }  

	// window
	popupWin = new Ext.Window({
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1020,
		height:530,
		autoScroll: true,
		title: '合作伙伴修改历史查询',
		iconCls: 'T20103',
		items: [grid, detailCompareForm],
		buttonAlign: 'center',
		closeAction: 'close',
		closable: true,
		resizable: false,
		buttons : [{
			text : '关闭',
			id: 'btnOk',
				disable: true,
				handler : function() {
					popupWin.hide();
				}
			}]
	});
	
	popupWin.show();
	
}