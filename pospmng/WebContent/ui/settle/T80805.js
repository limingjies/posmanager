Ext.onReady(function() {
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
	});*/
	
	var infileGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchSettelHis'
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
	
	var detailGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchSettelHisDtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transDateTime',mapping: 'transDateTime'},
			{name: 'txnSsn',mapping: 'txnSsn'},
			{name: 'retrivlRef',mapping: 'retrivlRef'},
			{name: 'brhInsIdCd',mapping: 'brhInsIdCd'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'acqInsAllot',mapping: 'acqInsAllot'},
			{name: 'settlAmt',mapping: 'settlAmt'},
			{name: 'txnAmt',mapping: 'txnAmt'},
			{name: 'mchtFee',mapping: 'mchtFee'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'misc2Flag',mapping: 'misc2Flag'}
		]),
		autoLoad: false
	});
	
	/*var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<p>批次号：<font color=green>1</font></p>',
			'<p>商户所属机构：<font color=green>{brhName}</font></p>'
		)
	});*/

	var infileColModel = new Ext.grid.ColumnModel([
		//rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '清算日期',dataIndex: 'dateSettlmt',align: 'center',renderer: formatDt,width: 80},
		{header: '清算商户',dataIndex: 'mchtNoName',id:'mchtNoName',width: 250},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center',width: 100},
		{header: '手续费',dataIndex: 'mchtFee',align: 'center',width: 80},
		{header: '清算金额',dataIndex: 'settlAmt',align: 'center',width:100},
		{header: '商户结算账号',dataIndex: 'settleAcct',align: 'center',width:160},
		{header: '合作伙伴号',dataIndex: 'brhInsIdCd',align: 'center',width:160,hidden:true},
		{header: '合作伙伴',dataIndex: 'brhName',align: 'left',width:200},
		{header: '合作伙伴分润',dataIndex: 'acqInsAllot',align: 'center',width: 100,hidden:true}
	]);
	
	var detailColModel = new Ext.grid.ColumnModel([
	    //rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '交易日期',dataIndex: 'transDate',align: 'center',renderer: formatDt,width: 80},
		{header: '交易时间',dataIndex: 'transDateTime',align: 'center',renderer: formatDt,width: 80},
		{header: '流水号',dataIndex: 'txnSsn',align: 'center',width: 120},
		{header: '参考号',dataIndex: 'retrivlRef',align: 'center',width: 100},
		{header: '交易金额',dataIndex: 'txnAmt',align: 'center',width: 100},
		{header: '手续费',dataIndex: 'mchtFee',align: 'center',width: 80},
		{header: '清算金额',dataIndex: 'settlAmt',align: 'center',width:100},
		{header: '交易类型',dataIndex: 'txnNum',align: 'center',width: 100,renderer:txnType},
		{header: '结算类型',dataIndex: 'misc2Flag',align: 'center',width: 70,renderer:misc2Flag},
		//{header: '合作伙伴号',dataIndex: 'brhInsIdCd',align: 'center',width: 80},
		{header: '合作伙伴名称',dataIndex: 'brhName',align: 'left',width: 200},
		{header: '合作伙伴分润佣金',dataIndex: 'acqInsAllot',align:'center',width: 140,hidden:true}
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
				if(""==numb.trim() || null==numb){
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
	
	
    var tbar1 = new Ext.Toolbar({
          renderTo: Ext.grid.EditorGridPanel.tbar,  
          items:[
             	'-','商户编号：',{
					xtype : 'dynamicCombo',
					methodName : 'getAllMchntId',
					hiddenName: 'mchtNo',
					width: 323,
					id: 'idMchtNo',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					editable: true,
					lazyRender: true
		        },'-','结算类型:',{
					xtype : 'combo',
					width : 100,
					id : 'misc2_T',
					hiddenName : 'misc2',
					displayField : 'displayField',
					valueField : 'valueField',
					//emptyText : '不能为空',
					//blankText : '不能为空',
					//allowBlank : false,
					//value : '0',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [[ '0', 'T+0' ],
								[ '1', 'T+1' ]]
					})
				}
          ]  
   })
    
	 var tbar2 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-','清算起始日期：',{
    	               		xtype: 'datefield',
    						format: 'Y-m-d',
    						altFormats: 'Y-m-d',
    						editable: false,
    						value:timeYesterday,
							id: 'startDate',
							name: 'startDate'
		               	},'-','清算结束日期：',{
							xtype: 'datefield',
							format: 'Y-m-d',
							altFormats: 'Y-m-d',
							vtype: 'daterange',
							startDateField: 'startDate',
							editable: false,
							value:timeYesterday,
							id: 'endDate',
							name: 'endDate'
		               	},'-',{
		            		text: '下载所有明细',
		            		xtype: 'button',
		            		width: 85,
		            		iconCls: 'download',
		            		disabled: false,
		            		handler:function() {
		            			var startDate =typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd');
		        				var	endDate = typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd');
		        				if(!startDate){
		        					Ext.MessageBox.show({
		        						msg: '清算起始日期:不可为空！',
		        						title: '错误提示',
		        						buttons: Ext.MessageBox.OK,
		        						icon: Ext.MessageBox.ERROR,
		        						modal: true,
		        						width: 250
		        					});
		        				}
		        				if(!endDate){
		        					Ext.MessageBox.show({
		        						msg: '清算结束日期:不可为空！',
		        						title: '错误提示',
		        						buttons: Ext.MessageBox.OK,
		        						icon: Ext.MessageBox.ERROR,
		        						modal: true,
		        						width: 250
		        					});
		        				}
		            			Ext.Ajax.request({
		            					url: 'T8080502Action.asp',
		            					timeout: 60000,
		            					params: {
		            						'startDate': startDate,
		            						'endDate': endDate,
		            						txnId: '80805',
		            						subTxnId: '03'
		            					},
		            					success: function(rsp,opt) {
		            						var rspObj = Ext.decode(rsp.responseText);
		            						if(rspObj.success) {
		            							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
		            						} else {
		            							showErrorMsg(rspObj.msg,infileGrid);
		            						}
		            					},
		            					failure: function(){
		            						showErrorMsg("操作失败！",infileGrid,function(){});
		            					}
		            				});
		            		}
		               	}
	            ]  
         })
     
          var tbar3 = new Ext.Toolbar({
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
	               	 '-','合作伙伴：',{
					xtype : 'dynamicCombo',
					methodName : 'getBrhId',
					hiddenName: 'queryBrhId',
					width: 200,
					id: 'idqueryBrhId',
					editable: true,
					//allowBlank: true,
					lazyRender: true
			        },
           			'-','结算账号：',{
 						xtype : 'textfield',
 	 					//methodName : 'getBrhId',
 	 					hiddenName: 'panId',
 	 					width: 150,
 	 					id: 'queryPanId',
 	 					editable: true,
 	 					//allowBlank: true,
 	 					lazyRender: true
 				   }
	            ]  
         }) 
	 
     var detailMenu = {
		text: '结算单明细下载',
		width: 85,
		iconCls: 'download',
		disabled: true,
		handler:function() {
			if(infileGrid.getSelectionModel().getSelected()==null){
				showErrorMsg("请先选择一条结算单！",detailGrid);
			}
			Ext.Ajax.request({
					url: 'T8080501Action.asp',
					timeout: 60000,
					params: {
						mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
						dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt,
						mchtNoName: infileGrid.getSelectionModel().getSelected().data.mchtNoName,
						brhInsIdCd: infileGrid.getSelectionModel().getSelected().data.brhInsIdCd,
						txnId: '80805',
						subTxnId: '02'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg("操作失败！",infileGrid,function(){});
					}
				});
		}
	};
	 
	 
	var detailGrid = new Ext.grid.GridPanel({
		title: '结算单明细',
		region: 'east',
		width: 550,
//		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: detailGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: detailColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [detailMenu],
		loadMask: {
			msg: '正在加载结算单明细列表......'
		},
		listeners : {
            'cellclick':selectableCell,
        },
		bbar: new Ext.PagingToolbar({
			store: detailGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	
	var infileGrid = new Ext.grid.GridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn: 'mchtNoName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: infileGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: infileColModel,
		clicksToEdit: true,
		//plugins: rowExpander,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			disabled:true,
			width: 80,
			handler:function() {
				if(infileGridStore.getTotalCount()>System[REPORT_MAX_COUNT]){
					showMsg("数据量太大，请过滤条件分批下载！",infileGrid);
					return ;
				}
				Ext.Ajax.request({
					url: 'T80805Action.asp',
					timeout: 60000,
					params: {
						startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
						mchtNo: Ext.getCmp('idMchtNo').getValue(),
						brhId: Ext.getCmp('idqueryBrhId').getValue(),
						pan: Ext.getCmp('queryPanId').getValue(),
						misc2T: Ext.getCmp('misc2_T').getValue(),
						txnId: '80805',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,infileGrid);
						}
					},
					failure: function(){
						showErrorMsg("操作失败！",infileGrid,function(){});
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
				infileGridStore.load();
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
				Ext.getCmp('startDate').setValue(timeYesterday);
				Ext.getCmp('endDate').setValue(timeYesterday);
				Ext.getCmp('queryPanId').setValue('');
				Ext.getCmp('misc2_T').setValue();
				//infileGridStore.reload();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar2.render(this.tbar); 
					tbar1.render(this.tbar); 
					tbar3.render(this.tbar);
             },
            'cellclick':selectableCell
        },
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: infileGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
//		renderTo: Ext.getBody()
	});

	infileGridStore.addListener('load', function(st, rds, opts) {
		if(infileGridStore.getCount()>0){
			Ext.getCmp('download').enable();
		}else{
			Ext.getCmp('download').disable();
		}
	});

	infileGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(infileGrid.getView().getRow(infileGrid.getSelectionModel().last)).frame();
			detailGridStore.load({
				params: {
					start: 0,
					mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
					dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt
				}
			})
			detailGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	detailGridStore.on('beforeload', function() {
		detailGridStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo: infileGrid.getSelectionModel().getSelected().data.mchtNo,
			brhInsIdCd: infileGrid.getSelectionModel().getSelected().data.brhInsIdCd,
			dateSettlmt: infileGrid.getSelectionModel().getSelected().data.dateSettlmt
		});
	});

	
	infileGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			mchtNo: Ext.getCmp('idMchtNo').getValue(),
			brhId: Ext.getCmp('idqueryBrhId').getValue(),
			pan: Ext.getCmp('queryPanId').getValue(),
			misc2T: Ext.getCmp('misc2_T').getValue()
		});
		detailGridStore.removeAll();
		detailGrid.getTopToolbar().items.items[0].disable();
	});
	
	
//		Ext.getCmp('mchtText').show();
		Ext.getCmp('idMchtNo').show();
//		Ext.getCmp('brhText').show();
		Ext.getCmp('idqueryBrhId').show();

	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [infileGrid,detailGrid],
		renderTo: Ext.getBody()
	});
});