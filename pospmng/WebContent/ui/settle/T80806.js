Ext.onReady(function() {//对平交易明细查询 
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	

	var sumStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchSumTrans'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateSettlmt',mapping: 'dateSettlmt'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNoName',mapping: 'mchtNoName'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'mchtFee',mapping: 'mchtFee'},
			{name: 'brhInsIdCd',mapping: 'brhInsIdCd'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'settleAcct',mapping: 'settleAcct'},
			{name: 'acqInsAllot',mapping: 'acqInsAllot'},
			{name: 'settlAmt',mapping: 'settlAmt'}
		]),
		autoLoad: true
	});
	
	var infileGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchFlatTrans'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'dateSettlmt',mapping: 'dateSettlmt'},
			{name: 'brhInsIdCd',mapping: 'brhInsIdCd'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'mchtCd',mapping: 'mchtCd'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'amtTrans',mapping: 'amtTrans'},
			{name: 'mchtFee',mapping: 'mchtFee'},
			{name: 'acqInsAllot',mapping: 'acqInsAllot'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transDateTime',mapping: 'transDateTime'},
			{name: 'txnSsn',mapping: 'txnSsn'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'settleFlag',mapping: 'settleFlag'},
			{name: 'settlAmt',mapping: 'settlAmt'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'misc2Flag',mapping: 'misc2Flag'}
		]),
		autoLoad: false
	});


	var sumColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateSettlmt',align: 'center',renderer: formatDt,width: 80},
		{header: '清算商户',dataIndex: 'mchtNoName',id:'mchtNoName',width: 250},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center',width: 100},
		{header: '手续费',dataIndex: 'mchtFee',align: 'center',width: 80},
		{header: '清算金额',dataIndex: 'settlAmt',align: 'center',width:100},
		{header: '商户结算账号',dataIndex: 'settleAcct',align: 'center',width:160},
		{header: '合作伙伴',dataIndex: 'brhName',align: 'center',width:160},
		{header: '合作伙伴分润',dataIndex: 'acqInsAllot',align: 'center',width: 100,hidden:true}
	]);
	
	var infileColModel = new Ext.grid.ColumnModel([
		//rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'transDate',align: 'center',renderer: formatDt,width: 80},
		{header: '交易时间',dataIndex: 'transDateTime',align: 'center',renderer: formatDt,width:80},
		{header: '流水号',dataIndex: 'txnSsn',align: 'center',width: 80},
		{header: '参考号',dataIndex: 'retrivlRef',align: 'center',width:100},
		{header: '交易金额',dataIndex: 'amtTrans',align: 'center',width: 100},
		{header: '商户手续费',dataIndex: 'mchtFee',align: 'center',width: 100},
		//{header: '商户号和名称',dataIndex: 'mchtNm',align: 'center',width: 240},
		//{header: '清算日期',dataIndex: 'dateSettlmt',align: 'center',renderer: formatDt,width:80},
		{header: '清算金额',dataIndex: 'settlAmt',align: 'center',width:100},
		{header: '交易类型',dataIndex: 'txnNum',align: 'center',width: 100,renderer:txnType},
		{header: '结算类型',dataIndex: 'misc2Flag',align: 'center',width: 70,renderer:misc2Flag},
		{header: '合作伙伴',dataIndex: 'brhName',align: 'center',width: 160},
		{header: '合作伙伴分润佣金',dataIndex: 'acqInsAllot',align: 'center',width: 120,hidden:true},
		{header: '是否结算',dataIndex: 'settleFlag',align: 'center',width:70,renderer: settleFlag}
	]);

	
	function txnType(value) {
		if (""==value.trim() || null==value) {
			return " ";
		}else{
			if("1101"==value){
				return "POS消费";
			}else if("1091"==value){
				return "预授权完成";
			}else if("3101"==value){
				return "消费撤销";
			}else if("3091"==value){
				return "预授权完成撤销";
			}else if("5151"==value){
				return "POS退货";
			}else {
				return " ";
			}
		}
	}
	
	function misc2Flag(value) {
		if (""==value.trim() || null==value) {
			return " ";
		}else{
			if("0"==value.substring(0,1)){
				var numb=value.substring(1,4);
				for (i= 0; i < numb.length; i++) {
					if ("0"==numb.substring(0,1)) {
						numb=numb.replace(0,'');
					}
				}
				if(""==numb || null==numb){
					return "T+0";
				}else{
					return "T+"+numb;
				}
			}else if("1"==value.substring(0,1)){
				var endNumber= value.substring(4,5);
				if("0"==endNumber){
					return "周结";
				}else if("1"==endNumber){
					return "月结";
				}else if("2"==endNumber){
					return "季结";
				}else{
					return " ";
				}
			}else {
				return " ";
			}
		}
	}
	
	function settleFlag(value) {
		if (value == "" || value == null) {
			return "<font color='red'>未结算</font>";
		}else{
			return "<font color='green'>已结算</font>";
		}
	}
	
         var tbar1 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		'-','清算日期：',{
	               		xtype: 'datefield',
						format: 'Ymd',
						altFormats: 'Y-m-d',
						/*vtype: 'daterange',
						endDateField: 'endDate',*/
						value:timeYesterday,//,curDate
						editable: false,
						id: 'dateSettlmt',
						name: 'dateSettlmt'
	               	},
	               	'',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
						vtype: 'daterange',
						startDateField: 'dateSettlmt',
						value:timeYesterday,//,curDate
						hidden : true,
						editable: false,
						id: 'endDate',
						name: 'endDate',
	               	},
             	 	'-','合作伙伴：',{
	 					xtype : 'dynamicCombo',
	 					methodName : 'getBrhId',
	 					hiddenName: 'queryBrhId',
	 					width: 200,
	 					id: 'idqueryBrhId',
	 					editable: true,
	 					//allowBlank: true,
	 					lazyRender: true
 			        }]  
         }) 


         var tbar2 = new Ext.Toolbar({
               renderTo: Ext.grid.EditorGridPanel.tbar,  
               items:[
	               	'-','商户编号：',{
						xtype : 'dynamicCombo',
						methodName : 'getAllMchntId',
						hiddenName: 'mchtNo',
						width: 300,
						id: 'idMchtNo',
						mode:'local',
						triggerAction: 'all',
						forceSelection: true,
						selectOnFocus: true,
						editable: true,
						lazyRender: true
			        }]  
        }) 

     	var tbar3 = new Ext.Toolbar({
             renderTo: Ext.grid.EditorGridPanel.tbar,  
             items:[
           			'-','结算账号：',{
 						xtype : 'textfield',
 	 					//methodName : 'getBrhId',
 	 					hiddenName: 'panId',
 	 					width: 150,
 	 					id: 'queryPanId',
 	 					editable: true,
 	 					//allowBlank: true,
 	 					lazyRender: true
 				   },'-','结算类型:',{
 						xtype : 'combo',
 						width : 100,
 						id : 'misc2_T',
 						hiddenName : 'misc2',
 						displayField : 'displayField',
 						valueField : 'valueField',
 						//allowBlank : false,
 						//value : '0',
 						store : new Ext.data.ArrayStore({
 							fields : [ 'valueField',
 									'displayField' ],
 							data : [[ '0', 'T+0' ],
 									[ '1', 'T+1' ]]
 						})
 					}]  
     	}) 
         
  
     	var detailGrid = new Ext.grid.GridPanel({
     		title: '详细列表',
     		region: 'east',
     		width: 550,
     		//iconCls: 'T301',
     		split: true,
     		collapsible: true,
     		frame: true,
     		border: true,
     		columnLines: true,
     		stripeRows: true,
     		store: infileGridStore,
     		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
     		cm: infileColModel,
     		clicksToEdit: true,
     		forceValidation: true,
     		loadMask: {
     			msg: '正在加载结算单明细列表......'
     		},
     		tbar: [/*
     		    '-','结算账号：',{
					xtype : 'textfield',
					//methodName : 'getBrhId',
					hiddenName: 'panId',
					width: 150,
					id: 'queryPanId',
					editable: true,
					//allowBlank: true,
					lazyRender: true
			   },{
	    			xtype: 'button',
	    			text: '查询',
	    			//disabled:true,
	    			name: 'query',
	    			id: 'queryDetail',
	    			iconCls: 'query',
	    			width: 80,
	    			handler:function() {
	    				infileGridStore.load();
	    			}
	    		}*/],
    		listeners : {
                'cellclick':selectableCell,
            },
     		bbar: new Ext.PagingToolbar({
     			store: infileGridStore,
     			pageSize: System[QUERY_RECORD_COUNT],
     			displayInfo: true,
     			displayMsg: '共{2}条记录',
     			emptyMsg: '没有找到符合条件的记录'
     		})
     	});
     	
         
	var infileGrid = new Ext.grid.GridPanel({
		iconCls: 'T104',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: sumStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: sumColModel,
		clicksToEdit: true,
		//plugins: rowExpander,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '发起入账申请',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			disabled : true,
			handler:function() {
				Ext.getCmp('download').disable();
				//setTimeout(function(){Ext.getCmp('download').enable();}, 1000 );
				Ext.Ajax.request({
					url: 'T80806Action.asp?method=getFileNotice',
					timeout: 60000,
					params: {
						dateSettlmt: Ext.getCmp('dateSettlmt').getValue()
					},
					success: function(rsp,opt) {
						Ext.getCmp('download').enable();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.msg=="0000") {//if(rspObj.success)
							showSuccessMsg("入账申请成功！",infileGrid);
						}else if(rspObj.msg=="02"){
							showAlertMsg("该清算日期已经申请过入账！",infileGrid,function(){});
						}else if(rspObj.msg=="-1"){
							showAlertMsg("该清算日期没有交易明细数据",infileGrid,function(){});
						}else {
							//showErrorMsg(rspObj.msg,infileGrid);
							showErrorMsg("处理失败！",infileGrid);
						}
					},
					failure: function(){
						Ext.getCmp('download').enable();
						showErrorMsg("处理失败！",infileGrid);
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
				Ext.Ajax.request({
					url: 'T80806Action.asp?method=queryDate',
					timeout: 60000,
					params: {
						dateSettlmt: Ext.getCmp('dateSettlmt').getValue()
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.msg=="0000") {
							sumStore.load();
						}else if(rspObj.msg=="02"){
							showAlertMsg("该清算日期已经申请过入账！",infileGrid,function(){});
						}else {
							showErrorMsg("处理失败！",infileGrid);
						}
					},
					failure: function(){
						showErrorMsg("处理失败！",infileGrid);
					}
				});
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idMchtNo').setValue('');
				Ext.getCmp('idqueryBrhId').setValue('');
				Ext.getCmp('queryPanId').setValue('');
				Ext.getCmp('dateSettlmt').setValue(timeYesterday);//curDate
				Ext.getCmp('queryPanId').setValue();
				Ext.getCmp('misc2_T').setValue();
				//sumStore.reload();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
					tbar3.render(this.tbar);
             },
 			'cellclick':selectableCell,
        },
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: sumStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
		
	sumStore.on('beforeload', function(){
		infileGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			dateSettlmt: Ext.getCmp('dateSettlmt').getValue(),
			mchtNo: Ext.getCmp('idMchtNo').getValue(),
			brhId: Ext.getCmp('idqueryBrhId').getValue(),
			pan: Ext.getCmp('queryPanId').getValue(),
			misc2T: Ext.getCmp('misc2_T').getValue()
		});
	});

	sumStore.addListener('load', function(st, rds, opts) {
		if(sumStore.getCount()>0){
			Ext.getCmp('download').enable();
		}else{
			Ext.getCmp('download').disable();
		}
	});

	infileGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(infileGrid.getView().getRow(infileGrid.getSelectionModel().last)).frame();
			infileGridStore.load({
				params: {
					start: 0,
					dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt,
					mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
					brhId:infileGrid.getSelectionModel().getSelected().data.brhInsIdCd
				}
			})
		}
	});

	infileGridStore.on('beforeload', function(){
		infileGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			//dateSettlmt: Ext.getCmp('dateSettlmt').getValue(),
			//mchtNo: Ext.getCmp('idMchtNo').getValue(),
			//brhId: Ext.getCmp('idqueryBrhId').getValue(),
			dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt,
			mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
			brhId: infileGrid.getSelectionModel().getSelected().data.brhInsIdCd,
			pan: Ext.getCmp('queryPanId').getValue()
		});
	});
	
	
		Ext.getCmp('idMchtNo').show();
		Ext.getCmp('idqueryBrhId').show();
		Ext.getCmp('queryPanId').show();
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [infileGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});