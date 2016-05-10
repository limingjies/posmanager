Ext.onReady(function() {

//	-----------------------------------费率配置----------------------------------------------
	var feeCtlStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agentFeeCtl'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'feeName',mapping: 'feeName'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'amt1',mapping: 'amt1'},
			{name: 'amt2',mapping: 'amt2'},
			{name: 'misc',mapping: 'misc'},
			{name: 'crt_time',mapping: 'crt_time'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'}
		]),
		autoLoad: true
	}); 
	
	var brhCashRateStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhCashRateInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'name',mapping: 'name'},
			{name: 'type',mapping: 'type'},
			{name: 'rate',mapping: 'rate'},
			{name: 'crt_time',mapping: 'crt_time'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'}
		]),
		autoLoad: true
	}); 
	
	//复选框选择模式  
	var checkboxSM = new Ext.grid.CheckboxSelectionModel({  
	    checkOnly: false,  
	    singleSelect: false  
	});  
	
	var feeCtlColModel = new Ext.grid.ColumnModel([
   		checkboxSM,
   		new Ext.grid.RowNumberer(),
   		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
   		{header: '费率名称',dataIndex: 'feeName',align: 'center',width: 120,id:'feeName'},
   		{header: '费率类型',dataIndex: 'feeType',align: 'left',width: 120,id:'feeType',renderer:feeType },
   		{header: '手续费',dataIndex: 'feeRate',align: 'left',width: 120,id:'feeRate',
   			editor:new Ext.form.NumberField({
	   			allowBlank : false,
	   			maxValue : 100,
				minValue : 0, 
				maxText : '不能超过100',
				minText : '请输入大于0的值',
				decimalPrecision : 3,
				listeners : {  
		            'blur' : function() { 
		            	var selects=new Array();
		        		var selections = grid.getSelectionModel().getSelections();
		        		var store = grid.getStore();
		        		for (var i = 0; i < selections.length; i++) {
		        			var selectData = selections[i];
		        			var dataIndex = store.indexOf(selectData);
		        			selects[i]=dataIndex;
		        		}
//		        		var isSelected=grid.getSelectionModel().isSelected(row);
//		        		alert(isSelected);
//		        		var length=selects.length;
//		        		selects[length]=row;
		        		//alert("当前行号为 ----- " + dataIndex); isSelected
		        		grid.getSelectionModel().selectRows(selects);
		         		}
				}
   			})  }
   	]);
	var tbar1 = new Ext.Toolbar({  
    	renderTo: Ext.grid.EditorGridPanel.tbar,  
			items:['',	'费率代码：',{
				xtype: 'textfield',
				id: 'queryDiscId',
				name: 'queryDiscId',
				vtype:'isEngNum',
				maxLength: 5,
				width: 120
			},'-','费率名称：',{
				xtype: 'textfield',
				id: 'queryDiscName',
				name: 'queryDiscName',
				maxLength: 30,
				width: 120
			}]  
	}) 
	
	 var feeCtlGrid = new Ext.grid.EditorGridPanel({
		 	height:400,
			collapsible: false,
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
			region: 'west',
			clicksToEdit: true,
			autoExpandColumn: 'feeName',
			store: feeCtlStore,
			sm: checkboxSM,
			cm: feeCtlColModel,
			forceValidation: true,
			loadMask: {
				msg: '正在加载分润费率信息......'
			},
			listeners : {  
	         	'cellclick':selectableCell
	        }  ,
	        tbar: 	[{
				xtype: 'label',
				text: '分润费率开通',
				name: 'queryFeeCtlI',
				id: 'queryFeeCtlI',
				iconCls: 'query',
				width: 80,
				handler:function() {
					feeCtlStore.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: feeCtlStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
	/*feeCtlGrid.on('cellclick', function(grid, row, col, e) {
		

	})*/
	feeCtlGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(feeCtlGrid.getView().getRow(feeCtlGrid.getSelectionModel().last)).frame();
//			feeCfgStore.load();
		}
	});
	
	
	feeCtlStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0/*,
			queryDiscId: Ext.getCmp('queryDiscId').getValue(),
			queryDiscName: Ext.getCmp('queryDiscName').getValue()*/
		});
	});
	
	//复选框选择模式  
	var checkboxSM1 = new Ext.grid.CheckboxSelectionModel({  
	    checkOnly: false,  
	    singleSelect: false  
	});  
	var brhCashRateColModel = new Ext.grid.ColumnModel([
	  		checkboxSM1,
	  		new Ext.grid.RowNumberer(),
	  		{header: '费率ID',dataIndex: 'rateId',align: 'center',width: 120,id:'rateId',hidden:true},
	  		{header: '费率名称',dataIndex: 'name',align: 'center',width: 120,id:'name'},
	  		{header: '费率类型',dataIndex: 'type',align: 'left',width: 120,id:'type',renderer:feeType },
	  		{header: '手续费',dataIndex: 'rate',align: 'left',width: 120,id:'rate',
	  			editor:new Ext.form.NumberField({
	   			allowBlank : false,
	   			maxValue : 100,
				minValue : 0, 
				maxText : '不能超过100',
				minText : '请输入大于0的值',
				decimalPrecision : 3,
				listeners : {  
		            'blur' : function() { 
		            	var selects=new Array();
		        		var selections = grid.getSelectionModel().getSelections();
		        		var store = grid.getStore();
		        		for (var i = 0; i < selections.length; i++) {
		        			var selectData = selections[i];
		        			var dataIndex = store.indexOf(selectData);
		        			selects[i]=dataIndex;
		        		}
		        		grid.getSelectionModel().selectRows(selects);
		         		}
				}
	  			})  }
	  	]);
	
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
			store: brhCashRateStore,
			sm: checkboxSM1,
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
				text: '提现费率开通',
				name: 'queryBrhCashRate',
				id: 'queryBrhCashRate',
				iconCls: 'query',
				width: 80,
				handler:function() {
					brhCashRateStore.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: brhCashRateStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});

	 brhCashRateGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(brhCashRateGrid.getView().getRow(brhCashRateGrid.getSelectionModel().last)).frame();
	//	                                       			feeCfgStore.load();
		}
	});
	
	
	brhCashRateStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0
		});
	});	
	
//	-----------------------------费率信息END--------------------------------------
/*	// 合作伙伴级别
	var brhLvlStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO', function(ret) {
		brhLvlStore.loadData(Ext.decode(ret));
	});*/
	// 上级合作伙伴编号
	var brhStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	var brhNewNoStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH_SHORT',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH_NEW_NO',function(ret){
		brhNewNoStore.loadData(Ext.decode(ret));
	});
	
	// 人员属性
	var memPropertyStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MEM_PROPERTY', function(ret) {
			memPropertyStore.loadData(Ext.decode(ret));
			});
	
/*	// 职务类型
	var jobTypeStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('JOB_TYPE', function(ret) {
		jobTypeStore.loadData(Ext.decode(ret));
	});*/
	

	// 合作伙伴改变了之后改变下拉列表
//	var cupBrhStore = new Ext.data.JsonStore({
//				fields : ['valueField', 'displayField'],
//				root : 'data',
//				autoLoad : false
//			});
//	var cupBrhStore1 = new Ext.data.JsonStore({
//				fields : ['valueField', 'displayField'],
//				root : 'data',
//				autoLoad : false
//			});

	// 银联合作伙伴编号
//	SelectOptionsDWR.getComboData('CUP_BRH', function(ret) {
//				cupBrhStore.loadData(Ext.decode(ret));
//			});


/*	// 上级合作伙伴编号
	var upBrhStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_UP', function(ret) {
		upBrhStore.loadData(Ext.decode(ret));
	});*/


    // 开户行信息
    var bankNameStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboData('BANKNAME',function(ret){
//    	bankNameStore.loadData(Ext.decode(ret));
//    });
    var provinceStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    SelectOptionsDWR.getComboData('PROVINCE',function(ret){
    	provinceStore.loadData(Ext.decode(ret));
    });
    var cityStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboDataWithParameter('CITY','',function(ret){
//    	cityStore.loadData(Ext.decode(ret));
//    });
    var subbranchStore = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',' , , ',function(ret){
//    	subbranchStore.loadData(Ext.decode(ret));
//    });
    
	var gridStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'gridPanelStoreAction.asp?storeId=brhInfo'
						}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
						// idProperty: 'brhId'
					}, [
						{name : 'brhId',mapping : 'brhId'},
						{name : 'createNewNo',mapping :'createNewNo'},
						{name : 'brhLvl',mapping : 'brhLvl'},
//						{name : 'upBrhId',mapping : 'upBrhId'},
						{name : 'upCreateNewNo',mapping :'upCreateNewNo'},
						{name : 'brhName',mapping : 'brhName'},
						{name : 'brhAddr',mapping : 'brhAddr'},
						{name : 'brhTelNo',mapping : 'brhTelNo'},
						{name : 'postCode',mapping : 'postCode'},
						{name : 'brhContName',mapping : 'brhContName'},
//						{name : 'cupBrhId',mapping : 'cupBrhId'},
						{name : 'resv1',mapping : 'resv1'},
						{name : 'resv2',mapping : 'resv2'},
						{name : 'status',mapping : 'status'},
						{name : 'statusCode',mapping : 'statusCode'},
						{name: 'regDate',mapping: 'regDate'}
					]),
				autoLoad : true
			});

	// gridStore.load();

	var brhColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), 
			{id : 'brhId',header : '编号',dataIndex : 'brhId',sortable : true,width : 70},
			{header : '合作伙伴号',dataIndex : 'createNewNo',width : 100},
//			{header : '级别',dataIndex : 'brhLvl',renderer : brhLvlRender,width : 100},
//			{header : '上级编号',dataIndex : 'upBrhId',width : 80},
//			{header : '上级合作伙伴号',dataIndex: 'upCreateNewNo',width: 100},
			{header : '合作伙伴名称',dataIndex : 'brhName',width : 150},
			{id : 'brhAddr',header : '合作伙伴地址',dataIndex : 'brhAddr',width : 108,id : 'brhAddr'},
			{header : '联系方式',dataIndex : 'brhTelNo',width : 110},
			{header : '联系人',dataIndex : 'brhContName',width : 130},
			{header : '所在地区',dataIndex : 'resv1',width : 80},
//			{header : '银联机构号',dataIndex : 'cupBrhId',width : 80}, 
//			{header : '密钥索引',dataIndex : 'resv2',width : 60},
			{header : '审核状态',dataIndex : 'status',width : 100},
			{header : '审核状态编码',dataIndex : 'statusCode',hidden : true},
			{header: '申请日期',dataIndex: 'regDate',width: 80,renderer:formatDt}
		]);

	var addMenu = {
		text : '添加',
		width : 85,
		iconCls : 'add',
		handler : function() {
			Ext.override(Ext.grid.CheckboxSelectionModel, { 
				handleMouseDown : function(g, rowIndex, e){/*   
				    if(e.button !== 0 || this.isLocked()){   
				        return;   
				    }   
				    var view = this.grid.getView();   
				    if(e.shiftKey && !this.singleSelect && this.last !== false){   
				        var last = this.last;   
				        this.selectRange(last, rowIndex, e.ctrlKey);   
				        this.last = last; // reset the last   
				        view.focusRow(rowIndex);   
				    }else{   
				        var isSelected = this.isSelected(rowIndex);   
				    if(isSelected){   
				       // this.deselectRow(rowIndex);   
				}else if(!isSelected || this.getCount() > 1){   
				        this.selectRow(rowIndex, true);   
				        view.focusRow(rowIndex);   
				        }   
				    }   
				*/} 
			});
			brhInfoForm.getForm().reset();
			brhInfoForm.getForm().clearInvalid();
			brhWin.show();
			brhWin.center();
		}
	};
	var delMenu = {
		text : '删除',
		width : 85,
		iconCls : 'delete',
		hidden:true,
		disabled : true,
		handler : function() {
			if (grid.getSelectionModel().hasSelection()) {
				var rec = grid.getSelectionModel().getSelected();
				var brhId = rec.get('brhId');
				showConfirm('确定要删除该合作伙伴吗？合作伙伴编号：' + brhId, grid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url : 'T10101Action.asp?method=delete',
							success : function(rsp, opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									/*SelectOptionsDWR.getComboData('UP_BRH_ID',function(ret) {
														upBrhStore.loadData(Ext.decode(ret));
									});*/
//													grid.getTopToolbar().items.items[2].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params : {
								brhId : brhId,
								txnId : '10101',
								subTxnId : '02'
							}
						});
					}
				});
			}
		}
	};
	
	var approveMenu = {
			text: '审核',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
		if (grid.getSelectionModel().hasSelection()) {
			var rec = grid.getSelectionModel().getSelected();
			var brhId = rec.get('brhId');
			showBrhInfDetailS(brhId, grid, 'approve', function(){
				grid.getStore().reload();
			});
		}
	}
	};
	
	var acceptMenu = {
			text: '通过',
			width: 85,
			iconCls: 'accept',
			disabled: true,
			handler:function() {
		if (grid.getSelectionModel().hasSelection()) {
			var rec = grid.getSelectionModel().getSelected();
			var brhId = rec.get('brhId');
			showConfirm('确认审核通过吗？合作伙伴编号：' + brhId, grid, function(bt) {
				// 如果点击了提示的确定按钮
				if (bt == "yes") {
					Ext.Ajax.requestNeedAuthorise({
						url : 'T10101Action.asp?method=accept',
						success : function(rsp, opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {
								Ext.Msg.alert("提示",rspObj.msg);
								//showSuccessMsg(rspObj.msg,grid);
								grid.getStore().reload();
							} else {
								Ext.Msg.alert("提示",rspObj.msg);
								//showErrorMsg(rspObj.msg,grid);
							}
						},
						params : {
							brhId : brhId,
							txnId : '10101',
							subTxnId : '04'
						}
					});
				}
			});
		}
	}
	};
		
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		handler:function() {
		if (grid.getSelectionModel().hasSelection()) {
			var rec = grid.getSelectionModel().getSelected();
			var brhId = rec.get('brhId');
			showConfirm('确认拒绝此申请吗？合作伙伴编号：' + brhId, grid, function(bt) {
				// 如果点击了提示的确定按钮
				if (bt == "yes") {
					Ext.Ajax.requestNeedAuthorise({
						url : 'T10101Action.asp?method=refuse',
						success : function(rsp, opt) {	
							var rspObj = Ext.decode(rsp.responseText);
							if (rspObj.success) {showSuccessMsg(rspObj.msg,grid);
								grid.getStore().reload();
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
						},
						params : {
							brhId : brhId,
							txnId : '10101',
							subTxnId : '05'
						}
					});
				}
			});
		}
	}
	};
	
	var upMenu = {
			text : '修改',
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function() {
				var brhId = grid.getSelectionModel().getSelected().get('brhId');
				showBrhInfUpd( brhId, grid);
			}
		};

	var queryCondition = {
		text : '录入查询条件',
		width : 85,
		id : 'query',
		iconCls : 'query',
		handler : function() {
			queryWin.show();
		}
	};

	var detail = {
		text: '查看详细信息',
		width: 85,
		id: 'detail',
		iconCls: 'detail',
		handler:function() {
//			queryWin.show();
			var brhId = grid.getSelectionModel().getSelected().get('brhId');
			showBrhInfDetailS( brhId, grid);
		}
	};
	
	var menuArr = new Array();
	menuArr.push(addMenu);
//	menuArr.push('-');
	menuArr.push('');
	menuArr.push(delMenu);
	menuArr.push('-');
	menuArr.push(upMenu);
	menuArr.push('-');
	menuArr.push(detail);  
	menuArr.push('-');  
	menuArr.push(queryCondition);
	menuArr.push('-');  
	menuArr.push(approveMenu);
//	menuArr.push('-');  
//	menuArr.push(refuseMenu);
	
	// 可选合作伙伴下拉列表
	var brhCombo = new Ext.form.ComboBox({
				store : brhStore,
				displayField : 'displayField',
				valueField : 'valueField',
				mode : 'local',
				width : 200,
				triggerAction : 'all',
				forceSelection : true,
				typeAhead : true,
				selectOnFocus : true,
				editable : true,
				allowBlank : true,
				fieldLabel : '合作伙伴编号',
				id : 'editBrh',
				name : 'editBrh',
				hiddenName : 'brhIdEdit'
			});
	// 可选合作伙伴号下拉列表
	var brhNewNoCombo = new Ext.form.ComboBox({
				store : brhNewNoStore,
				displayField : 'displayField',
				valueField : 'valueField',
				mode : 'local',
				width : 200,
				triggerAction : 'all',
				forceSelection : true,
				typeAhead : true,
				selectOnFocus : true,
				editable : true,
				allowBlank : true,
				fieldLabel : '合作伙伴号',
				id : 'editBrhNewNo',
				name : 'editBrhNewNo',
				hiddenName : 'brhNewNoIdEdit'
			});
	
	var queryForm = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 500,
				autoHeight : true,
				labelWidth : 100,
				items : [brhCombo, brhNewNoCombo , {
							xtype : 'textfield',
							id : 'searchBrhName',
							name : 'searchBrhName',
							maxLength : 40,
							width : 200,
							fieldLabel : '合作伙伴名称'
						}/*, {
							xtype : 'combo',
							store : brhLvlStore,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'brhLvl',
							fieldLabel : '合作伙伴级别',
							mode : 'local',
							width : 160,
							id : 'searchBrhLevel',
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true,
							selectOnFocus : true,
							editable : false
						}*/]
			});

	// 合作伙伴列表
	var grid = new Ext.grid.EditorGridPanel({
				title : '合作伙伴信息维护',
				iconCls : 'T10101',
				region : 'center',
				frame : true,
				border : true,
				columnLines : true,
				stripeRows : true,
				store : gridStore,
				autoExpandColumn : 'brhAddr',
				sm : new Ext.grid.RowSelectionModel({
							singleSelect : true
						}),
				cm : brhColModel,
				clicksToEdit : true,
				forceValidation : true,
				tbar : menuArr,
				loadMask : {
					msg : '正在加载合作伙伴信息列表......'
				},
				bbar : new Ext.PagingToolbar({
							store : gridStore,
							pageSize : System[QUERY_RECORD_COUNT],
							displayInfo : true,
							displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg : '没有找到符合条件的记录'
						}),
				listeners:{
					'cellclick':selectableCell,
				}
			});

	var queryWin = new Ext.Window({
				title : '查询条件',
				layout : 'fit',
				width : 400,
				autoHeight : true,
				items : [queryForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				resizable : false,
				closable : true,
				animateTarget : 'query',
				tools : [{
					id : 'minimize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.maximize.show();
						toolEl.hide();
						queryWin.collapse();
						queryWin.getEl().pause(1);
						queryWin.setPosition(10,
						Ext.getBody().getViewSize().height - 30);
					},
					qtip : '最小化',
					hidden : false
				}, {
					id : 'maximize',
					handler : function(event, toolEl, panel, tc) {
						panel.tools.minimize.show();
						toolEl.hide();
						queryWin.expand();
						queryWin.center();
					},
					qtip : '恢复',
					hidden : true
				}],
				buttons : [{
							text : '查询',
							handler : function() {
								gridStore.load();
							}
						}, {
							text : '清除查询条件',
							handler : function() {
								queryForm.getForm().reset();
							}
						}]
			});

	// 每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload', function() {
				grid.getTopToolbar().items.items[4].disable();
				grid.getTopToolbar().items.items[2].disable();
				grid.getTopToolbar().items.items[6].disable();
			});

	gridStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
			start : 0,
			brhId : queryForm.findById('editBrh').getValue(),
			brhNewNoId: queryForm.findById('editBrhNewNo').getValue(),
			brhName : queryForm.findById('searchBrhName').getValue()/*,
			brhLevel : queryForm.findById('searchBrhLevel').getValue()*/
		});
	});

	grid.getSelectionModel().on({
		'rowselect' : function() {
			// 行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			grid.getTopToolbar().items.items[2].enable();
			grid.getTopToolbar().items.items[4].enable();
			grid.getTopToolbar().items.items[6].enable();
			
			var statusCode = grid.getSelectionModel().getSelected().get('statusCode');
			if (statusCode == '0'){
				grid.getTopToolbar().items.items[10].enable();
//				grid.getTopToolbar().items.items[12].enable();
			}else{
				grid.getTopToolbar().items.items[10].disable();
//				grid.getTopToolbar().items.items[12].disable();
			}
		}
	});

	// 合作伙伴添加表单
	var brhInfoForm = new Ext.form.FormPanel({
			frame : true,
			width: 865,
			height: 400,
			labelWidth : 120,
			layout : 'column',
			defaults : {
				bodyStyle : 'padding-left: 20px;overflow-y:auto;'
			},
			waitMsgTarget : true,
			items : [{
				xtype : 'fieldset',
				title : '基本信息',
				layout : 'column',
				width : 850,
				items : [/*{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '机构代码*',
						allowBlank : false,
						emptyText : '001~999必须为3位数字',
						id : 'orgNoId',
						name : 'orgNo',
						width : 250,
						maxLength : 3,
						maxLengthText : '机构代码最多可以输入3个数字001~999',
						vtype : 'isNumber',
						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
						blankText : '请输入数字001~999',
						listeners : {
							'change' : function() {
							}
						}
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						store : brhLvlStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'brhLvl',
						emptyText : '请选择合作伙伴级别',
						fieldLabel : '合作伙伴级别*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editable : false,
						allowBlank : false,
						blankText : '请选择一个合作伙伴级别',
						listeners : {
							'select' : function() {
								// 上级营业网点编号
								SelectOptionsDWR.getComboDataWithParameter('UP_BRH_ID', this.value, function(ret) {
											upBrhStore.loadData(Ext.decode(ret));
								});
							},
					'change' : function() {
						brhInfoForm.getForm().findField('upBrhId').setValue('');
					}
						}
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : upBrhStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'upBrhId',
						emptyText : '请选择上级合作伙伴编码',
						fieldLabel : '上级合作伙伴编码*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				}, */{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴名称<font color="red">*</font>',
						allowBlank : false,
						vtype:'lengthRange',
						blankText : '合作伙伴名称不能为空',
						emptyText : '请输入合作伙伴名称',
						id : 'brhName',
						name : 'brhName',
						width : 250,
						maxLength : 60,
						maxLengthText : '总字数不能超过60个(汉字算2个)!'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : memPropertyStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'memProperty',
						emptyText : '请选择人员属性',
						fieldLabel : '人员属性<font color="red">*</font>',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				},/* {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'combo',
						// store: BrhStore1,
						store : jobTypeStore,
						displayField : 'displayField',
						valueField : 'valueField',
						hiddenName : 'jobType',
						emptyText : '请选择职务类别',
						fieldLabel : '职务类别*',
						mode : 'local',
						width : 250,
						triggerAction : 'all',
						forceSelection : true,
						typeAhead : true,
						selectOnFocus : true,
						editabled : false,
						allowBlank : false
					}]
				}, */{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype : 'dynamicCombo',
						methodName : 'getAreaCode',
						fieldLabel : '合作伙伴地区码<font color="red">*</font>',
						allowBlank : false,
						blankText : '合作伙伴地区码不能为空',
						emptyText : '请输入合作伙伴地区码',
						hiddenName : 'resv1',
						width : 250
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴地址<font color="red">*</font>',
						allowBlank : false,
						blankText : '合作伙伴地址不能为空',
						emptyText : '请输入合作伙伴地址',
						id : 'brhAddr',
						name : 'brhAddr',
						maxLength : 40,
						maxLengthText : '合作伙伴地址最多可以输入40个字符',
						width : 250
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '合作伙伴联系电话<font color="red">*</font>',
						allowBlank : false,
						emptyText : '请输入合作伙伴联系电话',
						id : 'brhTelNo',
						name : 'brhTelNo',
						width : 250,
						maxLength : 20,
						maxLengthText : '合作伙伴联系电话最多可以输入20个数字',
						vtype : 'isNumber'
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '负责人姓名',
						// emptyText: '请输入负责人姓名',
						id : 'brhContName',
						name : 'brhContName',
						width : 250,
						maxLength : 20,
						maxLengthText : '负责人姓名最多可以输入20个字符'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '是否系统级签到<font color="red">*</font>',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '否',
								inputValue : '0',
								checked:true,
								name : "resv1_5",
								listeners : {
									'check' : function(checkbox, checked) {
										if(checked){
											Ext.getCmp('addHint').hide();
										}
										
									}
									}
							}, {
								boxLabel : '是',
								inputValue : '1',
								name : "resv1_5",
								listeners : {
									'check' : function(checkbox, checked) {
										if(checked){
											Ext.getCmp('addHint').show();
										}
									}
									}
						}]
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '生成对账流水<font color="red">*</font>',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '不生成',
								inputValue : '0',
								checked:true,
								name : "resv1_6"
							}, {
								boxLabel : '生成',
								inputValue : '1',
								name : "resv1_6"
						}]
					}]
				},{

					id:'addHint',
					xtype: 'displayfield',
					hidden:true,
					columnWidth : 1,
					html:'<font color=red>系统级签到选择"是"，表示该合作伙伴和我司是系统对系统接入，<br>仅有一个系统秘钥，不是一机一密模式。</font><br>&nbsp;',
					items: []
			
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '清算到合作伙伴账号<font color="red">*</font>',
						allowBlank : false,
						width : 250,
						blankText : '至少选择一项',
						items: [{
								boxLabel : '不清算',
								inputValue : '0',
								checked:true,
								name : "resv1_7"
							}, {
								boxLabel : '清算',
								inputValue : '1',
								name : "resv1_7",
								listeners : {
								'check' : function(checkbox, checked) {
								}
							}
						}]
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					id:'settleFlagIds',
//					hidden:true,
					items : [{
						xtype: 'radiogroup',
						fieldLabel : '结算账户类型<font color="red">*</font>',
						id:'settleFlagId',
						allowBlank:false,
						width : 250,
						items: [{
								boxLabel : '对公账户',
								inputValue : '0',
								width :100,
								name : "settleFlag"
							}, {
								boxLabel : '对私账户',
								inputValue : '1',
								width :100,
								name : "settleFlag"
						}]
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					hidden: true,
					items : [{
						xtype: 'textfield',
						id : 'settleType',
						name : 'settleType'
					}]
				},{
					
						columnWidth: .5,
						layout: 'form',
						items: [{
							xtype : 'combo',
							fieldLabel : '结算类型<font color="red">*</font>',
							id : 'settleTypeId',
							hiddenName : 'settleTypeType',
							allowBlank : false,
							blankText:'至少选择一项',
							width: 250,
							store : new Ext.data.ArrayStore({
								fields : [ 'valueField',
										'displayField' ],
								data : [ [ '0', 'T+N' ],
										[ '1', '周期结算' ] ]
							}),
							listeners: {
								'select': function(combo,record,number) {
									if(number==0){
										Ext.getCmp('settleTypeId0').show();
										Ext.getCmp('settleTypeId1').hide();
										Ext.getCmp('T_N').allowBlank=false;
										Ext.getCmp('T_N').setValue('1');
										Ext.getCmp('periodId').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeId0').hide();
										Ext.getCmp('settleTypeId1').show();
										Ext.getCmp('T_N').allowBlank=true;
										Ext.getCmp('periodId').allowBlank=false;
									}
								},'change': function(combo,number,orignal) {
									if(number==0){
										Ext.getCmp('settleTypeId0').show();
										Ext.getCmp('settleTypeId1').hide();
										Ext.getCmp('T_N').allowBlank=false;
										Ext.getCmp('T_N').setValue('1');
										Ext.getCmp('periodId').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeId0').hide();
										Ext.getCmp('settleTypeId1').show();
										Ext.getCmp('T_N').allowBlank=true;
										Ext.getCmp('periodId').allowBlank=false;
									}
								}
							}
						}]
			},{
				columnWidth: .5,
				layout: 'form',
				id : 'settleTypeId0',
				hidden:true,
//				lableWidth:2,
//				lablePad:2,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'T+N<font color="red">*</font>',
					width:250,
//					anchor: '90%',
					allowBlank : false,
					maxLength: 3,
					vtype: 'isNumber',
					readOnly:true,
					value:'1',
					regex: /^(([0-9]{1})|([1-9]{1}\d{0,2}))$/,
					regexText: '请输入0-999的数字',
					id: 'T_N',
					name:'T_N'
				}]
			
			},{
				columnWidth: .5,
				hidden:true,
				layout: 'form',
				id : 'settleTypeId1',
				items: [{
					xtype : 'combo',
					fieldLabel : '周期结算<font color="red">*</font>',
					id : 'periodId',
					editable:false,
					hiddenName : 'period',
					allowBlank : false,
					blankText:'至少选择一项',
					width: 250,
					value:'0',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField','displayField' ],
						data : [ 	[ '0', '周结' ],
									[ '1', '月结' ]/*,
									[ '2', '季结' ]*/]
					}),
					listeners: {}
				}]
	
			}, {
					columnWidth : 1,
					layout : 'form',
					id:'settleAcctNmId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户户名<font color="red">*</font>',
						allowBlank:false,
						id : 'settleAcctNm',
						name : 'settleAcctNm',
						width : 250,
						maxLength : 80,
						vtype : 'isOverMax'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					id:'settleAcctId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户号<font color="red">*</font>',
						allowBlank:false,
						id : 'settleAcct',
						name : 'settleAcct',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						width : 250,
						maxLength : 40,
						vtype : 'isOverMax'
					}]
				}, {
					columnWidth : .5,
					layout : 'form',
					id:'settleAcctCheckId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '账户号(确认)<font color="red">*</font>',
						allowBlank:false,
						id : 'settleAcctCheck',
						name : 'settleAcctCheck',
						regex: /^[0-9]+$/,
						regexText: '该输入框只能输入数字0-9',
						maskRe: /^[0-9]+$/,
						width : 250,
						maxLength : 40,
						vtype : 'isOverMax',
						listeners: {
	                     'change': function(){
	                     		if(brhInfoForm.findById('settleAcct').getValue()!=brhInfoForm.findById('settleAcctCheck').getValue()){
	                     			showErrorMsg("两次输入账号不一致，请确认！", brhInfoForm);
	                     			Ext.getCmp("settleAcctCheck").setValue(""); 
	                     		}
	                    	}
	                    }
					}]
				}, {
					columnWidth: 1,
		        	layout: 'form',
		       		items: [{
							xtype: 'basecomboselect',
				        	baseParams: 'PROVINCE',
							fieldLabel: '开户银行-省<font color="red">*</font>',
							store: provinceStore,
							displayField: 'displayField',
							valueField: 'valueField',
							id:'province',
							width:250,
							editable : true,
							listeners: {
								'select': function() {
									cityStore.removeAll();
									var province = this.getValue();
									SelectOptionsDWR.getComboDataWithParameter('CITY',province,function(ret){
										cityStore.loadData(Ext.decode(ret));
									});
								},'change': function() {
									bankNameStore.removeAll();
									subbranchStore.removeAll();

									Ext.getCmp('city').setValue('');
									Ext.getCmp('bankName').setValue('');
									Ext.getCmp('settleBankNoSub').setValue('');
								}
							}
			        	}]
				},{
	        	columnWidth: 1,
	        	layout: 'form',
	       		items: [{
						xtype: 'basecomboselect',
			        	baseParams: 'CITY',
						fieldLabel: '开户银行-市<font color="red">*</font>',
						store: cityStore,
						displayField: 'displayField',
						valueField: 'valueField',
						id:'city',
						width:250,
						editable : true,
						listeners: {
							'select': function() {
								bankNameStore.removeAll();

								var province = Ext.getCmp('province').getValue();
								if(province == ''){
									province=' ';
								}
								var city = this.getValue();
								if(city == ''){
									city=' ';
								}
								SelectOptionsDWR.getComboDataWithParameter('BANKNAME',province+','+city,function(ret){
									bankNameStore.loadData(Ext.decode(ret));
								});
							},
							'change':function() {
								subbranchStore.removeAll();
								
								Ext.getCmp('bankName').setValue('');
								Ext.getCmp('settleBankNoSub').setValue('');
							}
						}
		        	}]
			},{
		        	columnWidth: 1,
		        	layout: 'form',
		       		items: [{
							xtype: 'basecomboselect',
				        	baseParams: 'BANKNAME',
							fieldLabel: '开户银行<font color="red">*</font>',
							store: bankNameStore,
							displayField: 'displayField',
							valueField: 'valueField',
							id:'bankName',
							width:250,
							editable : true,
							listeners: {
								'select': function() {
									subbranchStore.removeAll();
									var bankName = Ext.getCmp('bankName').getValue();
									if(bankName == ''){
										bankName=' ';
									}
									var province = Ext.getCmp('province').getValue();
									if(province == ''){
										province=' ';
									}
									var city = Ext.getCmp('city').getValue();
									if(city == ''){
										city=' ';
									}
									SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',bankName+','+province+','+city,function(ret){
										subbranchStore.loadData(Ext.decode(ret));
									});
									Ext.getCmp('settleBankNoSub').onTriggerClick();
								},'change': function() {
									Ext.getCmp('settleBankNoSub').setValue('');
								}
							}
			        	}]
				},{
				columnWidth: 1,
		        layout: 'form',
	       		items: [{
			        	xtype: 'basecomboselect',
						fieldLabel: '开户支行名<font color="red">*</font>',
						store: subbranchStore,
						displayField: 'displayField',
						valueField: 'valueField',
						allowBlank: false,
						emptyText: '请先选择省市和开户银行',
						id: 'settleBankNoSub',
						width:400,
						editable : true,
						hiddenName: 'settleBankNo',
						listeners : {
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
				        } 
		        	}]
				},
				{
					xtype: 'radiogroup',
					fieldLabel : '是否计算分润<font color="red">*</font>',
					allowBlank : false,
					width : 250,
					hidden: true,
					blankText : '至少选择一项',
					items: [{
							boxLabel : '否',
							inputValue : '0',
							name : "resv1_8"
						}, {
							boxLabel : '是',
							inputValue : '1',
							checked:true,
							name : "resv1_8"}]},
				{
					columnWidth : .5,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						hidden:true,
						id : 'settleBankNm',
						name : 'settleBankNm'
					}]}
				]},
				{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '费率信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					items : [{
						columnWidth : 1,
						hidden:true,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							
							fieldLabel : '规则名称<font color="red">*</font>',
							id:'tblAgentFeeCfgZlfAdd.name',
							name : 'tblAgentFeeCfgZlfAdd.name',
							allowBlank : true,
							maxLength : 30,
							value:'-',
							blankText : '费率名称不能为空',
							width : 636
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励开始日期',
							id:'tblAgentFeeCfgZlfAdd.promotionBegDate',
							name : 'tblAgentFeeCfgZlfAdd.promotionBegDate',
							//allowBlank : true,
							vtype : 'daterange',
							endDateField : 'tblAgentFeeCfgZlfAdd.promotionEndDate',
							format : 'Y-m-d',
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励结束日期',
							id:'tblAgentFeeCfgZlfAdd.promotionEndDate',
							name : 'tblAgentFeeCfgZlfAdd.promotionEndDate',
							allowBlank : true,
							vtype : 'daterange',
							startDateField : 'tblAgentFeeCfgZlfAdd.promotionBegDate',
							format : 'Y-m-d',
							//blankText : '激励结束日期不能为空',
							width : 250
						}]
					},{
						columnWidth : .99,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '月交易额(万元)<font color="red">*</font>',
							id:'tblAgentFeeCfgZlfAdd.baseAmtMonth',
							name : 'tblAgentFeeCfgZlfAdd.baseAmtMonth',
							allowBlank : false,
							blankText : '月交易额不能为空',
//							maxLength : 15,
							maxValue : 9999999999999.99,
							minValue : 0, 
							maxText : '不能超过9999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 2, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '提档交易额(万元)<font color="red">*</font>',
							id:'tblAgentFeeCfgZlfAdd.gradeAmtMonth',
							name : 'tblAgentFeeCfgZlfAdd.gradeAmtMonth',
							allowBlank : false,
							blankText : '提档交易额不能为空',
//							maxLength : 15,
							maxValue : 9999999999999.99,
							minValue : 0, 
							maxText : '不能超过9999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 2, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '激励系数%',
							id:'tblAgentFeeCfgZlfAdd.promotionRate',
							name : 'tblAgentFeeCfgZlfAdd.promotionRate',
							allowBlank : true,
							//blankText : '激励系数不能为空',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '分润比率%<font color="red">*</font>',
							id:'tblAgentFeeCfgZlfAdd.allotRate',
							name : 'tblAgentFeeCfgZlfAdd.allotRate',
							allowBlank : false,
//							maxLength : 12,
							regex: /^(([0-9]|[1-8][0-9])(\.[0-9]{0,2})?|100|90)$/,
							regexText: '该输入框只能输入数字0-90或者100',
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'JCB成本费率%',
							id:'tblAgentFeeCfgZlfAdd.extJcbRate',
							name : 'tblAgentFeeCfgZlfAdd.extJcbRate',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '分润封顶值%',
							id:'misc',
							allowBlank : true,
							name : 'misc',
							regex: /^(([0-9]|[1-8][0-9])(\.[0-9]{0,2})?|100|90)$/,
							regexText: '该输入框只能输入数字0-90或者100',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过分润比率值',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250,
							listener :{
								'blur' :function(){
								}
							}
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						hidden:true,
						items : [{
							xtype: 'numberfield',
							fieldLabel : '特殊费率%<font color="red">*</font>',
							id:'tblAgentFeeCfgZlfAdd.specFeeRate',
							name : 'tblAgentFeeCfgZlfAdd.specFeeRate',
							allowBlank : true,
							value:'0',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'VISA成本费率%',
							id:'tblAgentFeeCfgZlfAdd.extVisaRate',
							name : 'tblAgentFeeCfgZlfAdd.extVisaRate',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					},{
	   					columnWidth : .5,
	   					layout : 'form',
	   					items : [{
	   						xtype: 'numberfield',
	   						fieldLabel : '提现垫资分润比例%<font color="red">*</font>',
	   						allowBlank : false,
	   						id : 'tblAgentFeeCfgZlfAdd.extRate1',
	   						name : 'tblAgentFeeCfgZlfAdd.extRate1',
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 2, 
							width : 250
	   					}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : 'MASTER成本费率%',
							id:'tblAgentFeeCfgZlfAdd.extMasterRate',
							name : 'tblAgentFeeCfgZlfAdd.extMasterRate',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}]
				},{
					// id: 'baseInfo',
					xtype : 'fieldset',
					title : '分润信息',
					layout : 'column',
					// collapsible : true,
					labelWidth : 120,
					width : 850,
					items : [{
						width : 800,
						items : feeCtlGrid
					}]
				},{
					xtype : 'fieldset',
					title : '提现费率信息',
					layout : 'column',
					labelWidth : 120,
					width : 850,
					items : [{
						width : 800,
						items : brhCashRateGrid
					}]
				}]
			});

	// 合作伙伴添加窗口
	var brhWin = new Ext.Window({
				title : '合作伙伴添加',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 875,
				// autoHeight: true,
				// layout: 'fit',
//				layout : 'column',
				items : [brhInfoForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				defaults : {
					bodyStyle : 'overflow-y:auto;'
				},
				buttons : [{
					text : '确定',
					handler : function() {
						if (brhInfoForm.getForm().isValid()) {

                     		if(brhInfoForm.findById('settleAcct').getValue()!=brhInfoForm.findById('settleAcctCheck').getValue()){
                     			showErrorMsg("两次输入账号不一致，请确认！", brhInfoForm);
                     			Ext.getCmp("settleAcctCheck").setValue(""); 
                     			return;
                     		}
                     		var allotRate = Ext.getCmp('tblAgentFeeCfgZlfAdd.allotRate').getValue();
                     		var misc = Ext.getCmp('misc').getValue();
                     		//upd by yww 160414 加判断：分润封顶值不为空时才比较--分润比例不能大分润于封顶值
                     		if(null!=misc && ""!=misc){
                         		if(allotRate>misc){
                         			showErrorMsg("分润比率不能大于分润封顶值！", brhInfoForm);
                         			return;
                         		};
                     		}
							
							var store=feeCtlGrid.getSelectionModel().getSelections();
							var storBrhCashRateSel = brhCashRateGrid.getSelectionModel().getSelections();
							var jsonArray=[];
							var tempgrid="";
							
							//20160323修改：加判断：合作伙伴添加或修改提交的时候，分润费率必须选一个，没选不让提交 --yww
							if (store.length<=0) {
								showErrorMsg("必须至少选择一项分润费率！", brhInfoForm);
                     			return;
							}
                    	
							if (storBrhCashRateSel.length <= 0){
								showErrorMsg("必须至少选择一项提现费率！", brhInfoForm);
                     			return;
							}
							
							for(var i=0;i<store.length;i++){
								var record = store[i];
								//得到当前记录判断是否为空，及新增的最后一条
								var currentRecord=record.get("rateId")+record.get("feeType")
				                                 +record.get("feeRate");
								currentRecord=Ext.util.Format.trim(currentRecord);
								if(currentRecord!=""){
									tempgrid=tempgrid+currentRecord;
									jsonArray.push(Ext.util.JSON.encode(record.data));
								}	
							}
							tempgrid=Ext.util.Format.trim(tempgrid);
							var resultJson=Ext.util.JSON.encode(jsonArray);

							///////// 提现费率信息处理
							var cashRateJsonArray=[];
							for(var i=0;i<storBrhCashRateSel.length;i++){
								var record = storBrhCashRateSel[i];
								//得到当前记录判断是否为空，及新增的最后一条
								var currentRecord=record.get("rateId")+record.get("type")
				                                 +record.get("rate");
								currentRecord=Ext.util.Format.trim(currentRecord);
								if(currentRecord!=""){
//									tempgrid=tempgrid+currentRecord;
									cashRateJsonArray.push(Ext.util.JSON.encode(record.data));
								}	
							}
							var cashRateResultJson=Ext.util.JSON.encode(cashRateJsonArray);
							
//							//结算类型 数据处理
							var settleType='';
							var settleTypeType=Ext.getCmp('settleTypeId').getValue();
							var T_N=Ext.getCmp('T_N').getValue();
							var period=Ext.getCmp('periodId').getValue();
							
							//补齐
							var fillWith=function(val,length,type){
								for (var i = val.length; i < length; i++) {
									val=type+val;
								}
								return val;
							} 
							
							if(settleTypeType=='0'){
								settleType=settleTypeType+' '+fillWith(T_N,3,'0');
							}else if(settleTypeType=='1'){
								settleType=settleTypeType+period+'   ';
							}
							Ext.getCmp('settleType').setValue(settleType);
							//设置开户行名称
							Ext.getCmp('settleBankNm').setValue(Ext.getCmp('settleBankNoSub').getRawValue());
							brhInfoForm.getForm().submit({
								url : 'T10105Action.asp?method=add',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									// 重置表单
									brhInfoForm.getForm().reset();
									brhWin.hide();
									showSuccessMsg(action.result.msg,brhInfoForm);
									// 重新加载合作伙伴列表
									grid.getStore().reload();
									// 重新加载上级合作伙伴信息
									/*SelectOptionsDWR.getComboData('UP_BRH_ID',
											function(ret) {
												upBrhStore.loadData(Ext
														.decode(ret));
									});*/
								},
								failure : function(form, action) {
									showErrorMsg(action.result.msg, brhInfoForm);
								},
								params : {
									txnId : '10105',
									subTxnId : '01',
									gridJson:resultJson,
									cashRateJson:cashRateResultJson
								}
							});
					}else {
						var foundInvalid = false;
						brhInfoForm.getForm().items.eachKey(function(key,item){  
							//console.log(item.fieldLabel)  
							if (!foundInvalid && !item.isValid()) {
								foundInvalid = true;
								var strFeildName = item.fieldLabel;
								strFeildName = strFeildName.replace(/[\*|%]/g, '')
								showErrorMsg('【' + strFeildName + "】未填写或数据不合法，请检查无误后再提交！", brhInfoForm);
								item.focus();
							}
						});
					}
					}
				}, {
					text : '重置',
					handler : function() {
						brhInfoForm.getForm().reset();
					}
				}, {
					text : '关闭',
					handler : function() {
						brhWin.hide(grid);
					}
				}]
			});
	/** **********************************************以下是合作伙伴相关操作员信息*********************************************************** */

	/** ******************************主界面************************************************ */

	

	var mainUI = new Ext.Viewport({
				layout : 'border',
				renderTo : Ext.getBody(),
				items : [grid]
			});
});