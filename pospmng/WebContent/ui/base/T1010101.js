

function showBrhInfUpd(brhId, El) {
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
//	-----------------------------------费率配置----------------------------------------------
	
	var feeCtlStoreUpd = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=agentFeeForUpdCtl&brhId='+brhId
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'feeName',mapping: 'feeName'},
			{name: 'feeType',mapping: 'feeType'},
			{name: 'feeRate',mapping: 'feeRate'},
			{name: 'selected',mapping: 'selected'}
		]),
		autoLoad: false
//		,
//		listeners: {
//            load: function (me, records, success, opts) {
//            	
//            	var selected = [];
//            	
//                if (!success || !records || records.length == 0)
//                    return;
//                
//                var selModel = feeCtlGridUpd.getSelectionModel();
//                if(!feeCtlGridUpd.store.data)return;
//                for(var i=0;i<records.length;i++){
//                	var record = records[i];
//                	if (record.get("selected") == "1") {
////                		selModel.selectRow(i, true);    //选中record，并且保持现有的选择，不触发选中事件
////                            selected.push(record);
//                		selected.push(this.indexOf(record));
//                        }
//                }
//                try{
//                	feeCtlGridUpd.getSelectionModel().selectRows(selected,true);    //选中record
//                }catch(e){
////                	feeCtlGridUpd.getSelectionModel().selectRows(selected,true);    //选中record
//                }
//                
//            }
//        }
	});
	
//	feeCtlStore.on('beforeload', function(){
//		Ext.apply(this.baseParams, {
//			start: 0/*,
//			queryDiscId: Ext.getCmp('queryDiscId').getValue(),
//			queryDiscName: Ext.getCmp('queryDiscName').getValue()*/
//		});
//	});
	
//	复选框选择模式  
//	var checkboxSM = new Ext.grid.CheckboxSelectionModel({  
//	    //checkOnly: false,  
//	    singleSelect: false  
//	});  

	
	var feeCtlColModel = new Ext.grid.ColumnModel([
   		new Ext.grid.CheckboxSelectionModel({  	    singleSelect: false	}),
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
				decimalPrecision : 3})   }
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
	
	 var feeCtlGridUpd = new Ext.grid.EditorGridPanel({
		 	id:'feeCtlGridUpd',
//			width: 430,
		 	height:400,
			collapsible: false,
//			title: '规则商户映射控制',
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
//			region:'center',
			region: 'west',
			clicksToEdit: true,
			autoExpandColumn: 'feeName',
//			plugins: feeCtlExpander,
			store: feeCtlStoreUpd,
			sm: new Ext.grid.CheckboxSelectionModel({  	    singleSelect: false	}),
			cm: feeCtlColModel,
			forceValidation: true,
			deferRowRender:false,
			loadMask: {
				msg: '正在加载分润费率信息......'
			},
			listeners : {  
	            'render' : function() {  
//						tbar1.render(this.tbar); 
	         		}
	        }  ,
	        tbar: 	[{
				xtype: 'label',
				text: '分润费率开通',
				name: 'queryUpd',
				id: 'queryUpd',
				iconCls: 'query',
				width: 80,
				handler:function() {
//					feeCtlStoreUpd.load();
				}
			}],
			bbar: new Ext.PagingToolbar({
				store: feeCtlStoreUpd,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});

	feeCtlGridUpd.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(feeCtlGridUpd.getView().getRow(feeCtlGridUpd.getSelectionModel().last)).frame();
//			feeCfgStore.load();
		}
	});
	
	var brhCashRateStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhCashRateForUpd&brhId='+brhId
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'rateId',mapping: 'rateId'},
			{name: 'name',mapping: 'name'},
			{name: 'type',mapping: 'type'},
			{name: 'rate',mapping: 'rate'},
			{name: 'selected',mapping: 'selected'}
		]),
		autoLoad: false
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
	// 人员属性
	var memPropertyStoreUpd = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MEM_PROPERTY', function(ret) {
		memPropertyStoreUpd.loadData(Ext.decode(ret));
	});
	
/*	// 职务类型
	var jobTypeStoreUpd = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('JOB_TYPE', function(ret) {
		jobTypeStoreUpd.loadData(Ext.decode(ret));
	});
	//银联机构号
	var cupBrhStore1Upd = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data',
		autoLoad : false
	});
	SelectOptionsDWR.loadCupBrhIdOptData(brhId, function(ret) {
			cupBrhStore1Upd.loadData(Ext.decode(ret));
	});
	// 合作伙伴级别	
	var brhLvlStoreUpd = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_LVL_BRH_INFO', function(ret) {
		brhLvlStoreUpd.loadData(Ext.decode(ret));
	});
	// 上级合作伙伴编号
	var upBrhStoreUpd = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('BRH_UP', function(ret) {
		upBrhStoreUpd.loadData(Ext.decode(ret));
	});*/
	
    // 支行反查省市开户行信息
	var subbranIdStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=SubbranchInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'serialNo',mapping: 'serialNo'},
			{name: 'bankName',mapping: 'bankName'},
			{name: 'province',mapping: 'province'},
			{name: 'city',mapping: 'city'}
		])
	});
	
	 // 开户行信息
    var bankNameStoreUpd = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboData('BANKNAME',function(ret){
//    	bankNameStoreUpd.loadData(Ext.decode(ret));
//    });
    var provinceStoreUpd = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
    SelectOptionsDWR.getComboData('PROVINCE',function(ret){
    	provinceStoreUpd.loadData(Ext.decode(ret));
    });
    var cityStoreUpd = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboDataWithParameter('CITY','',function(ret){
//    	cityStoreUpd.loadData(Ext.decode(ret));
//    });
    var subbranchStoreUpd = new Ext.data.JsonStore({
    	fields: ['valueField','displayField'],
    	root: 'data'
    });
//    SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',' , , ',function(ret){
//    	subbranchStore.loadData(Ext.decode(ret));
//    });
    
	var updGridStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
					url : 'loadRecordAction.asp?storeId=getAgentInf&brhId='+brhId
				}),
		reader : new Ext.data.JsonReader({
			root : 'data'
			}, [
				{name : 'tblBrhInfoUpd.brhId',mapping : 'brhId'}, 
				{name : 'tblBrhInfoUpd.brhLevel',mapping : 'brhLevel'},
				{name : 'tblBrhInfoUpd.upBrhId',mapping : 'upBrhId'},
				{name : 'tblBrhInfoUpd.brhName',mapping : 'brhName'},
				{name : 'tblBrhInfoUpd.brhAddr',mapping : 'brhAddr'},
				{name : 'tblBrhInfoUpd.brhTelNo',mapping : 'brhTelNo'},
				{name : 'tblBrhInfoUpd.postCode',mapping : 'postCode'},
				{name : 'tblBrhInfoUpd.brhContName',mapping : 'brhContName'},
				{name : 'tblBrhInfoUpd.cupBrhId',mapping : 'cupBrhId'},
				{name : 'tblBrhInfoUpd.resv1',mapping : 'resv1'},
				{name : 'tblBrhInfoUpd.resv2',mapping : 'resv2'},
				
				{name : 'tblBrhInfoUpd.settleOrgNoDt',mapping : 'settleOrgNo'},
				{name : 'tblBrhInfoUpd.settleJobType',mapping : 'settleJobType'},
				{name : 'tblBrhInfoUpd.settleMemProperty',mapping : 'settleMemProperty'},
				{name : 'tblBrhInfoUpd.createNewNo',mapping : 'createNewNo'},
				{name : 'tblBrhInfoUpd.status',mapping : 'status'},
				
				{name : 'resv1_5Upd',mapping : 'resv1_5'},
				{name : 'resv1_6Upd',mapping : 'resv1_6'},
				{name : 'resv1_7Upd',mapping : 'resv1_7'},
				{name : 'resv1_8Upd',mapping : 'resv1_8'},
				{name : 'tblBrhSettleInfUpd.settleFlag',mapping : 'settleFlag'},
				{name : 'tblBrhSettleInfUpd.settleAcctNm',mapping : 'settleAcctNm'},
				{name : 'tblBrhSettleInfUpd.settleAcct',mapping : 'settleAcct'},
				{name : 'misc',mapping : 'misc'},
				{name : 'settleAcctCheckUpd',mapping : 'settleAcct'},
				{name : 'tblBrhSettleInfUpd.settleBankNm',mapping : 'settleBankNm'},
				{name : 'tblBrhSettleInfUpd.settleBankNo',mapping : 'settleBankNo'},
				{name : 'brhFeeUpd',mapping : 'brhFee'},
				
				
				{name:'tblAgentFeeCfgUpd.allotRate',mapping:'allotRate'},
				{name:'tblAgentFeeCfgUpd.baseAmtMonth',mapping:'baseAmtMonth'},
				{name:'tblAgentFeeCfgUpd.extJcbRate',mapping:'extJcbRate'},
				{name:'tblAgentFeeCfgUpd.extMasterRate',mapping:'extMasterRate'},
				{name:'tblAgentFeeCfgUpd.extVisaRate',mapping:'extVisaRate'},
				{name:'tblAgentFeeCfgUpd.gradeAmtMonth',mapping:'gradeAmtMonth'},
				{name:'tblAgentFeeCfgUpd.name',mapping:'name'},
				{name:'tblAgentFeeCfgUpd.promotionBegDate',mapping:'promotionBegDate'},
				{name:'tblAgentFeeCfgUpd.promotionEndDate',mapping:'promotionEndDate'},
				{name:'tblAgentFeeCfgUpd.promotionRate',mapping:'promotionRate'},
				{name:'tblAgentFeeCfgUpd.specFeeRate',mapping:'specFeeRate'},
				
				{name:'tblAgentFeeCfgUpd.agentNo',mapping:'agentNo'},
				{name:'tblAgentFeeCfgUpd.amt1',mapping:'amt1'},
				{name:'tblAgentFeeCfgUpd.amt2',mapping:'amt2'},
				{name:'tblAgentFeeCfgUpd.crtTime',mapping:'crtTime'},
				{name:'tblAgentFeeCfgUpd.discId',mapping:'discId'},
				{name:'tblAgentFeeCfgUpd.enableFlag',mapping:'enableFlag'},
				{name:'tblAgentFeeCfgUpd.extRate1',mapping:'extRate1'},
				{name:'tblAgentFeeCfgUpd.extRate2',mapping:'extRate2'},
				{name:'tblAgentFeeCfgUpd.extRate3',mapping:'extRate3'},
				{name:'tblAgentFeeCfgUpd.flag1',mapping:'flag1'},
				{name:'tblAgentFeeCfgUpd.flag2',mapping:'flag2'},
				{name:'tblAgentFeeCfgUpd.mchtGrp',mapping:'mchtGrp'},
				{name:'tblAgentFeeCfgUpd.mchtNo',mapping:'mchtNo'},
				{name:'tblAgentFeeCfgUpd.misc2',mapping:'misc2'},
				{name:'tblAgentFeeCfgUpd.seq',mapping:'seq'},
				{name:'tblAgentFeeCfgUpd.uptOpr',mapping:'uptOpr'},
				{name:'tblAgentFeeCfgUpd.uptTime',mapping:'uptTime'},
				
				{name:'bank_name',mapping:'bank_name'},
				{name:'subbranch_name',mapping:'subbranch_name'},
				{name:'province',mapping:'province'},
				{name:'subbranch_id',mapping:'subbranch_id'},
				{name:'city',mapping:'city'},
				{name : 'settleType',mapping : 'resv4'},
			]),
		autoLoad : false
	});
	
	var updBrhInfoForm = new Ext.form.FormPanel({
				frame : true,
				width: 865,
				height: 400,
				labelWidth : 120,
				layout : 'column',
				waitMsgTarget : true,
				defaults : {
					bodyStyle : 'padding-left: 20px;overflow-y:auto;'
				},
				items : [{
					xtype : 'fieldset',
					title : '基本信息',
					layout : 'column',
					labelWidth : 120,
					width : 850,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴编码<font color="red">*</font>',
							id : 'tblBrhInfoUpd.brhId',
							name : 'tblBrhInfoUpd.brhId',
							width : 200,
							allowBlank : false,
							emptyText : '请输入合作伙伴编号',
							maxLength : 5,
							maxLengthText : '合作伙伴编号最多可以输入5个数字',
							vtype : 'isNumber',
							blankText : '该输入项只能包含数字'/*,
							listeners : {
								'change' : function() {
									try {
										// Ext.MessageBox.alert("change event");
										var cupBrh = Ext.getCmp('tblBrhInfoUpd.cupBrhId');
										// alert(cupBrh +"-"+brhId);
										cupBrh.clearValue();
										Ext.getCmp('tblBrhInfoUpd.cupBrhId').setValue('');
										cupBrh.bindStore(cupBrhStore1Upd);
										SelectOptionsDWR.loadCupBrhIdOptData(this.value, function(ret) {
											cupBrhStore1Upd.loadData(Ext.decode(ret));
										});
									} catch (e) {
										Ext.MessageBox.alert("出错了！", e.message);
									}
								}
							}*/
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '合作伙伴号',
							id : 'tblBrhInfoUpd.createNewNo',
							name : 'tblBrhInfoUpd.createNewNo',
							width : 200
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'dynamicCombo',
							store : memPropertyStoreUpd,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'tblBrhInfoUpd.settleMemProperty',
							fieldLabel : '人员属性',
							width:216,
							editable:false,
							readOnly:true,
						}]
					},/*{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combofordispaly',
							store : jobTypeStoreUpd,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'tblBrhInfoUpd.settleJobType',
							fieldLabel : '职务类别',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true
							
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'displayfield',
							fieldLabel : '机构代码',
							id : 'tblBrhInfoUpd.settleOrgNoDt',
							name : 'tblBrhInfoUpd.settleOrgNoDt',
							width : 200
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : brhLvlStoreUpd,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'tblBrhInfoUpd.brhLevel',
							fieldLabel : '合作伙伴级别*',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true,
							selectOnFocus : true,
							editable : false,
							allowBlank : false,
							emptyText : '请选择合作伙伴级别',
							blankText : '请选择一个合作伙伴级别',
							listeners : {
								'select' : function() {
									// 上级营业网点编号
									SelectOptionsDWR.getComboDataWithParameter('UP_BRH_ID', this.value, function(ret) {
												var retObj = Ext.decode(ret)
												var retData = retObj.data;
												for(var i = 0;i<retData.length;i++){
													if(retData[i].valueField == Ext.getCmp("tblBrhInfoUpd.brhId").getValue()){
														retData.removeAt(i);
													}
												}
												upBrhStoreUpd.loadData(retObj);
									});
								},
								'change' : function() {
									updBrhInfoForm.getForm().findField('tblBrhInfoUpd.upBrhId').setValue('');
								}
							}
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'combo',
							store : upBrhStoreUpd,
							displayField : 'displayField',
							valueField : 'valueField',
							hiddenName : 'tblBrhInfoUpd.upBrhId',
							fieldLabel : '上级合作伙伴编码*',
							mode : 'local',
							width : 200,
							triggerAction : 'all',
							forceSelection : true,
							typeAhead : true,
							selectOnFocus : true,
							editabled : false,
							emptyText : '请选择上级合作伙伴编码',
							allowBlank : false
						}]
					},*/ {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '合作伙伴名称<font color="red">*</font>',
							allowBlank : false,
							vtype:'lengthRange',
							blankText : '合作伙伴名称不能为空',
							width : 200,
							id : 'tblBrhInfoUpd.brhName',
							name : 'tblBrhInfoUpd.brhName',
							emptyText : '请输入合作伙伴名称',
							maxLength : 60,
							maxLengthText : '总字数不能超过60个(汉字算2个)!',
							
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype : 'dynamicCombo',
//							baseParams : 'CITY_CODE',
							methodName : 'getAreaCode',
							fieldLabel : '合作伙伴地区码<font color="red">*</font>',
							hiddenName : 'tblBrhInfoUpd.resv1',
							displayField : 'displayField',
							valueField : 'valueField',
							editable:false,
							readOnly:true,
							width : 216
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '合作伙伴地址<font color="red">*</font>',
							id : 'tblBrhInfoUpd.brhAddr',
							name : 'tblBrhInfoUpd.brhAddr',
							allowBlank : false,
							blankText : '合作伙伴地址不能为空',
							emptyText : '请输入合作伙伴地址',
							maxLength : 40,
							maxLengthText : '合作伙伴地址最多可以输入40个字符',
							width : 200
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '合作伙伴联系电话<font color="red">*</font>',
							id : 'tblBrhInfoUpd.brhTelNo',
							name : 'tblBrhInfoUpd.brhTelNo',
							allowBlank : false,
							emptyText : '请输入合作伙伴联系电话',
							width : 200,
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
							id : 'tblBrhInfoUpd.brhContName',
							name : 'tblBrhInfoUpd.brhContName',
							width : 200,
							maxLength : 20,
							maxLengthText : '负责人姓名最多可以输入20个字符'
						}]
//					}, {
//						columnWidth : .5,
//						layout : 'form',
//						items : [{
//							xtype : 'combo',
//							fieldLabel : '银联机构号*',
//							store : cupBrhStore1Upd,
//							id : 'tblBrhInfoUpd.cupBrhIdId',
//							hiddenName : 'tblBrhInfoUpd.cupBrhId',
//							allowBlank : false,
//							maskRe : /^[0-9]$/,
//							blankText : '请选择一个银联机构号',
//							width : 200
//						}]
//					}, {
//						columnWidth : .5,
//						layout : 'form',
//						items : [{
//							xtype: 'textfield',
//							fieldLabel : '合作伙伴密钥索引*',
//							id : 'tblBrhInfoUpd.resv2',
//							name : 'tblBrhInfoUpd.resv2',
//							allowBlank : false,
//							emptyText : '请输入合作伙伴密钥索引',
//							width : 200,
//							maxLength : 3,
//							minLength : 3,
//							maxLengthText : '只能输入3个的数字',
//							vtype : 'isNumber',
//							blankText : '该输入项只能包含数字'
//						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '是否系统级签到<font color="red">*</font>',
							width : 200,
							allowBlank : false,
							name : 'resv1_5Upd',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '否',
									name : 'resv1_5Upd',
									inputValue : '0',
									listeners : {
										'check' : function(checkbox, checked) {
											if(checked){
												Ext.getCmp('updHint').hide();
											}
										}
										}
//									name : "resv1_5Dt"
								}, {
									boxLabel : '是',
									name : 'resv1_5Upd',
									inputValue : '1',
									listeners : {
										'check' : function(checkbox, checked) {
											if(checked){
												Ext.getCmp('updHint').show();
											}
										}
										}
//									name : "resv1_5Dt"
							}]
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '生成对账流水<font color="red">*</font>',
							width : 200,
							allowBlank : false,
							name : 'resv1_6Upd',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '不生成',
									name : 'resv1_6Upd',
									inputValue : '0'
								}, {
									boxLabel : '生成',
									name : 'resv1_6Upd',
									inputValue : '1'
							}]
						}]
					},{

						id:'updHint',
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
							width : 200,
							name : 'resv1_7Upd',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '不清算',
									inputValue : '0',
									name : 'resv1_7Upd'
								}, {
									boxLabel : '清算',
									inputValue : '1',
									name : 'resv1_7Upd',
									listeners : {
									'check' : function(checkbox, checked) {
									}
								}
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						id:'settleFlagUpdIds',
						
//						hidden:true,
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '结算账户类型<font color="red">*</font>',
							id:'settleFlagUpdId',
							allowBlank:false,
							width : 200,
							name : 'tblBrhSettleInfUpd.settleFlag',
							items: [{
									boxLabel : '对公账户',
									inputValue : '0',
									name : 'tblBrhSettleInfUpd.settleFlag',
									width :100
								}, {
									boxLabel : '对私账户',
									inputValue : '1',
									name : 'tblBrhSettleInfUpd.settleFlag',
									width :100
							}]
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						hidden: true,
						items : [{
							xtype: 'textfield',
							id : 'settleTypeUpd',
							name : 'settleType',
						}]
					},{
					
						columnWidth: .5,
						layout: 'form',
						items: [{
							xtype : 'combo',
							fieldLabel : '结算类型<font color="red">*</font>',
							id : 'settleTypeUpdId',
							hiddenName : 'settleTypeUpdType',
							allowBlank : false,
							blankText:'至少选择一项',
							width: 200,
							store : new Ext.data.ArrayStore({
								fields : [ 'valueField',
										'displayField' ],
								data : [ [ '0', 'T+N' ],
										[ '1', '周期结算' ] ]
							}),
							listeners: {
								'select': function(combo,record,number) {
									if(number==0){
										Ext.getCmp('settleTypeUpdId0').show();
										Ext.getCmp('settleTypeUpdId1').hide();
										Ext.getCmp('T_Nupd').allowBlank=false;
										Ext.getCmp('T_Nupd').setValue('1');
										Ext.getCmp('periodIdUpd').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeUpdId0').hide();
										Ext.getCmp('settleTypeUpdId1').show();
										Ext.getCmp('T_Nupd').allowBlank=true;
										Ext.getCmp('periodIdUpd').allowBlank=false;
									}
								},'change': function(combo,number,orignal) {
									if(number==0){
										Ext.getCmp('settleTypeUpdId0').show();
										Ext.getCmp('settleTypeUpdId1').hide();
										Ext.getCmp('T_Nupd').allowBlank=false;
										Ext.getCmp('T_Nupd').setValue('1');
										Ext.getCmp('periodIdUpd').allowBlank=true;
									}else if(number==1){
										Ext.getCmp('settleTypeUpdId0').hide();
										Ext.getCmp('settleTypeUpdId1').show();
										Ext.getCmp('T_Nupd').allowBlank=true;
										Ext.getCmp('periodIdUpd').allowBlank=false;
									}
								}
							}
						}]
			},{
				columnWidth: .5,
				layout: 'form',
				id : 'settleTypeUpdId0',
				hidden:true,
//				lableWidth:2,
//				lablePad:2,
				items: [{
					xtype: 'textfield',
					fieldLabel: 'T+N<font color="red">*</font>',
					readOnly:true,
					value:'1',
					width:200,
//					anchor: '90%',
					allowBlank : false,
					maxLength: 3,
					vtype: 'isNumber',
//					regex: /^(([0-9]{1})|([1-9]{1}\d{0,2}))$/,
//					regexText: '请输入0-999的数字',
					id: 'T_Nupd',
					name:'T_Nupd'
				}]
			
			},{
				columnWidth: .5,
				hidden:true,
				layout: 'form',
				id : 'settleTypeUpdId1',
				items: [{
					xtype : 'combo',
					fieldLabel : '周期结算<font color="red">*</font>',
					id : 'periodIdUpd',
					editable:false,
					hiddenName : 'period',
					allowBlank : false,
					blankText:'至少选择一项',
					width: 200,
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
						id:'settleAcctNmUpdId',
//						hidden:true,
						items : [{
							xtype: 'textfield',
							fieldLabel : '账户户名<font color="red">*</font>',
							id : 'tblBrhSettleInfUpd.settleAcctNm',
							name : 'tblBrhSettleInfUpd.settleAcctNm',
							allowBlank:false,
							width : 200,
							maxLength : 80,
							vtype : 'isOverMax'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						id:'settleAcctUpdId',
//						hidden:true,
						items : [{
							xtype: 'textfield',
							fieldLabel : '账户号<font color="red">*</font>',
							id : 'tblBrhSettleInfUpd.settleAcct',
							name : 'tblBrhSettleInfUpd.settleAcct',
							allowBlank:false,
							width : 200,
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							width : 200,
							maxLength : 40,
							vtype : 'isOverMax'
						}]
					}, {
						columnWidth : .5,
						layout : 'form',
						id:'settleAcctCheckUpdId',
//						hidden:true,
						items : [{
							xtype: 'textfield',
							fieldLabel : '账户号(确认)<font color="red">*</font>',
							id : 'settleAcctCheckUpd',
							name : 'settleAcctCheckUpd',
							allowBlank:false,
							width : 200,
							regex: /^[0-9]+$/,
							regexText: '该输入框只能输入数字0-9',
							maskRe: /^[0-9]+$/,
							width : 200,
							maxLength : 40,
							vtype : 'isOverMax',
							listeners: {
		                     'change': function(){
		                     		if(updBrhInfoForm.findById('tblBrhSettleInfUpd.settleAcct').getValue()!=updBrhInfoForm.findById('settleAcctCheckUpd').getValue()){
		                     			showErrorMsg("两次输入账号不一致，请确认！", updBrhInfoForm);
		                     			Ext.getCmp("settleAcctCheckUpd").setValue(""); 
		                     		}
		                    	}
		                    }
						}]
					}, {
//						columnWidth : .5,
//						layout : 'form',
//						id:'settleBankNmUpdId',
//						hidden:true,
//						items : [{
//							xtype: 'textfield',
//							fieldLabel : '账户开户行名称*',
//							id : 'tblBrhSettleInfUpd.settleBankNm',
//							name : 'tblBrhSettleInfUpd.settleBankNm',
//							width : 200,
//							maxLength : 80,
//							vtype : 'isOverMax'
//						}]
//					}, {
//						columnWidth : .5,
//						layout : 'form',
//						id:'settleBankNoUpdId',
//						hidden:true,
//						items : [{
//							xtype: 'textfield',
//							fieldLabel : '账户开户行号*',
//							id : 'tblBrhSettleInfUpd.settleBankNo',
//							name : 'tblBrhSettleInfUpd.settleBankNo',
//							width : 200,
//							regex: /^[0-9]{12}$/,
//							regexText: '请输入12位数字0-9',
//							maxLength : 12,
//							vtype : 'isOverMax'
//						}]
						columnWidth: 1,
			        	layout: 'form',
			       		items: [{
								xtype: 'basecomboselect',
					        	baseParams: 'PROVINCE',
								fieldLabel: '开户银行-省<font color="red">*</font>',
								store: provinceStoreUpd,
								displayField: 'displayField',
								valueField: 'valueField',
								id:'provinceUpd',
								width:200,
								editable : true,
								allowBlank : false,
								listeners: {
//									'select': function() {
//										mchntMccStore.removeAll();
//										var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
//										Ext.getCmp('idmcc').setValue('');
//										Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
//										SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrpId').getValue(),function(ret){
//										mchntMccStore.loadData(Ext.decode(ret));
//										});
//									},
									'select': function() {
										cityStoreUpd.removeAll();
										var provinceUpd = Ext.getCmp('provinceUpd').getValue();
										SelectOptionsDWR.getComboDataWithParameter('CITY',provinceUpd,function(ret){
											cityStoreUpd.loadData(Ext.decode(ret));
										});
									},'change': function() {
										bankNameStoreUpd.removeAll();
										subbranchStoreUpd.removeAll();
										Ext.getCmp('cityUpd').setValue('');
										Ext.getCmp('bankNameUpd').setValue('');
										Ext.getCmp('settleBankNoUpdId').setValue('');
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
							store: cityStoreUpd,
							displayField: 'displayField',
							valueField: 'valueField',
							id:'cityUpd',
							width:200,
							editable : true,
							lazyRender:false,
							allowBlank : false,
							listeners: {
//								'select': function() {
//									mchntMccStore.removeAll();
//									var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
//									Ext.getCmp('idmcc').setValue('');
//									Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
//									SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrpId').getValue(),function(ret){
//									mchntMccStore.loadData(Ext.decode(ret));
//									});
//								},
								'select': function() {
									bankNameStoreUpd.removeAll();
									var province = Ext.getCmp('provinceUpd').getValue();
									if(province == ''){
										province=' ';
									}
									var city = Ext.getCmp('cityUpd').getValue();
									if(city == ''){
										city=' ';
									}
									SelectOptionsDWR.getComboDataWithParameter('BANKNAME', province+','+city, function(ret){
										bankNameStoreUpd.loadData(Ext.decode(ret));
									});

								},'change': function() {
									subbranchStoreUpd.removeAll();
									Ext.getCmp('bankNameUpd').setValue('');
									Ext.getCmp('settleBankNoUpdId').setValue('');
								}
							}
			        	}]
				},{
			        	columnWidth: .5,
			        	layout: 'form',
			       		items: [{
								xtype: 'basecomboselect',
					        	baseParams: 'BANKNAME',
								fieldLabel: '开户银行<font color="red">*</font>',
								store: bankNameStoreUpd,
								displayField: 'displayField',
								valueField: 'valueField',
								id:'bankNameUpd',
								width:200,
								editable : true,
								allowBlank : false,
								listeners: {
//									'select': function() {
//										mchntMccStore.removeAll();
//										var mchtGrpId = Ext.getCmp('mchtGrpId').getValue();
//										Ext.getCmp('idmcc').setValue('');
//										Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
//										SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',mchntForm.getForm().findField('mchtGrpId').getValue(),function(ret){
//										mchntMccStore.loadData(Ext.decode(ret));
//										});
//									},
									'select': function() {
										subbranchStoreUpd.removeAll();
										var bankName = Ext.getCmp('bankNameUpd').getValue();
										if(bankName == ''){
											bankName=' ';
										}
										var province = Ext.getCmp('provinceUpd').getValue();
										if(province == ''){
											province=' ';
										}
										var city = Ext.getCmp('cityUpd').getValue();
										if(city == ''){
											city=' ';
										}
										SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',bankName+','+province+','+city,function(ret){
											subbranchStoreUpd.loadData(Ext.decode(ret));
										});
										Ext.getCmp('settleBankNoUpdId').onTriggerClick();
									},'change': function() {

										Ext.getCmp('settleBankNoUpdId').setValue('');
									}
								}
				        	}]
				}, {
					columnWidth : .5,
					layout : 'form',
					id:'settleBankNmUpdId',
//					hidden:true,
					items : [{
						xtype: 'textfield',
						fieldLabel : '',
						id : 'tblBrhSettleInfUpd.settleBankNm',
						name : 'tblBrhSettleInfUpd.settleBankNm',
						hidden:true
					}]
					},{
						
					columnWidth: 1,
			        layout: 'form',
		       		items: [{
				        	xtype: 'basecomboselect',
							fieldLabel: '开户支行名<font color="red">*</font>',
							store: subbranchStoreUpd,
							displayField: 'displayField',
							valueField: 'valueField',
							allowBlank: false,
							emptyText: '请先选择省市和开户银行',
							id: 'settleBankNoUpdId',
							width:400,
							editable : true,
							hiddenName: 'tblBrhSettleInfUpd.settleBankNo',
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
					},{
						columnWidth : .5,
						layout : 'form',
						hidden:true,
						items : [{
							xtype: 'radiogroup',
							fieldLabel : '是否计算分润',
							allowBlank : false,
							width : 200,
							name : 'resv1_8Upd',
							blankText : '至少选择一项',
							items: [{
									boxLabel : '否',
									inputValue : '0',
									name : "resv1_8Upd"
								}, {
									boxLabel : '是',
									inputValue : '1',
									checked:true,
									name : "resv1_8Upd"
							}]
						}]
					}]},
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
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '',
							hidden:true,
							id:'tblAgentFeeCfgUpd.discId',
							name : 'tblAgentFeeCfgUpd.discId',
//							allowBlank : false,
//							maxLength : 30,
//							blankText : '费率名称不能为空',
							width : 636
						}]
					},{
						columnWidth : 1,
						hidden:true,
						layout : 'form',
						items : [{
							xtype: 'textfield',
							fieldLabel : '规则名称<font color="red">*</font>',
							id:'tblAgentFeeCfgUpd.name',
							name : 'tblAgentFeeCfgUpd.name',
							allowBlank : true,
							maxLength : 30,
							//blankText : '费率名称不能为空',
							width : 636
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励开始日期',
							id:'tblAgentFeeCfgUpd.promotionBegDate',
							name : 'tblAgentFeeCfgUpd.promotionBegDate',
							allowBlank : true,
							vtype : 'daterange',
							//endDateField : 'tblAgentFeeCfgUpd.promotionEndDate',
							format : 'Y-m-d',
							//blankText : '激励开始日期不能为空',
							width : 250
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'datefield',
							fieldLabel : '激励结束日期',
							id:'tblAgentFeeCfgUpd.promotionEndDate',
							name : 'tblAgentFeeCfgUpd.promotionEndDate',
							allowBlank : true,
							vtype : 'daterange',
							//startDateField : 'tblAgentFeeCfgUpd.promotionBegDate',
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
							id:'tblAgentFeeCfgUpd.baseAmtMonth',
							name : 'tblAgentFeeCfgUpd.baseAmtMonth',
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
							id:'tblAgentFeeCfgUpd.gradeAmtMonth',
							name : 'tblAgentFeeCfgUpd.gradeAmtMonth',
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
							id:'tblAgentFeeCfgUpd.promotionRate',
							name : 'tblAgentFeeCfgUpd.promotionRate',
							allowBlank : true,
							blankText : '激励系数不能为空',
//							maxLength : 12,
							maxValue : 100,
							minValue : 0, 
							maxText : '不能超过100',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}/*,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '成本费率(%)*',
							id:'tblAgentFeeCfgUpd.feeRate',
							name : 'tblAgentFeeCfgUpd.feeRate',
							allowBlank : false,
							blankText : '成本费率不能为空',
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
							fieldLabel : '成本封顶值*',
							id:'tblAgentFeeCfgUpd.capFeeValue',
							name : 'tblAgentFeeCfgUpd.capFeeValue',
							allowBlank : false,
							blankText : '成本费率不能为空',
//							maxLength : 12,
							maxValue : 999999999999.99,
							minValue : 0, 
							maxText : '不能超过999999999999.99',
							minText : '请输入大于0的值',
							decimalPrecision : 3, 
							width : 250
						}]
					}*/,{
						columnWidth : .5,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '分润比率%<font color="red">*</font>',
							id:'tblAgentFeeCfgUpd.allotRate',
							name : 'tblAgentFeeCfgUpd.allotRate',
							allowBlank : false,
							regex: /^(([0-9]|[1-8][0-9])(\.[0-9]{0,2})?|100|90)$/,
							regexText: '该输入框只能输入数字0-90或者100',
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
							fieldLabel : 'JCB成本费率%',
							id:'tblAgentFeeCfgUpd.extJcbRate',
							name : 'tblAgentFeeCfgUpd.extJcbRate',
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
						hidden:true,
						layout : 'form',
						items : [{
							xtype: 'numberfield',
							fieldLabel : '特殊费率%<font color="red">*</font>',
							id:'tblAgentFeeCfgUpd.specFeeRate',
							name : 'tblAgentFeeCfgUpd.specFeeRate',
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
							fieldLabel : '分润封顶值%',
							id:'misc_upd',
							name : 'misc',
							regex: /^(([0-9]|[1-8][0-9])(\.[0-9]{0,2})?|100|90)$/,
							regexText: '该输入框只能输入数字0-90或者100',
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
							id:'tblAgentFeeCfgUpd.extVisaRate',
							name : 'tblAgentFeeCfgUpd.extVisaRate',
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
	   						id : 'tblAgentFeeCfgUpd.extRate1',
	   						name : 'tblAgentFeeCfgUpd.extRate1',
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
							id:'tblAgentFeeCfgUpd.extMasterRate',
							name : 'tblAgentFeeCfgUpd.extMasterRate',
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
					xtype : 'fieldset',
					title : '分润信息',
					layout : 'column',
					labelWidth : 120,
					width : 850,
					items : [{
						width : 800,
						items : feeCtlGridUpd
					}]
//				},{
//					columnWidth : .5,
//					layout : 'form',
//					hidden:true,
//					items : [{
//						xtype: 'textfield',
//						fieldLabel : 'bank_name',
//						id : 'bank_name',
//						name : 'bank_name',
//						width : 200
//					}]
//				},{
//					columnWidth : .5,
//					layout : 'form',
//					hidden:true,
//					items : [{
//						xtype: 'textfield',
//						fieldLabel : 'subbranch_name',
//						id : 'subbranch_name',
//						name : 'subbranch_name',
//						width : 200
//					}]
//				},{
//					columnWidth : .5,
//					layout : 'form',
//					hidden:true,
//					items : [{
//						xtype: 'textfield',
//						fieldLabel : '合作伙伴号',
//						id : 'province',
//						name : 'province',
//						width : 200
//					}]
//				},{
//					columnWidth : .5,
//					layout : 'form',
//					hidden:true,
//					items : [{
//						xtype: 'textfield',
//						fieldLabel : 'city',
//						id : 'city',
//						name : 'city',
//						width : 200
//					}]
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

			var updBrhWin = new Ext.Window({
//				title : '合作伙伴信息修改',
				initHidden : true,
				header : true,
				frame : true,
				closable : true,
				modal : true,
				width : 875,
				items : [updBrhInfoForm],
				closeAction : 'close',
				iconCls : 'logo',
				resizable : false,
//				autoScroll : true,
				buttonAlign : 'center',
				defaults : {
					bodyStyle : 'overflow-y:auto;'
				},
				buttons : [{
					text : '确定',
					handler : function() {
						
						if (updBrhInfoForm.getForm().isValid()) {

                     		if(updBrhInfoForm.findById('tblBrhSettleInfUpd.settleAcct').getValue()!=updBrhInfoForm.findById('settleAcctCheckUpd').getValue()){
                     			showErrorMsg("两次输入账号不一致，请确认！", updBrhInfoForm);
                     			Ext.getCmp("settleAcctCheckUpd").setValue(""); 
                     			return;
                     		}
                     		var allotRate = Ext.getCmp('tblAgentFeeCfgUpd.allotRate').getValue();
                     		var misc = Ext.getCmp('misc_upd').getValue();
                     		//upd by yww 160414 加判断：分润封顶值不为空时才比较--分润比例不能大分润于封顶值
                     		if(null!=misc && ""!=misc){
	                     		if(allotRate>misc){
	                     			showErrorMsg("分润比率不能大于分润封顶值！", updBrhInfoForm);
	                     			return;
	                     		};
                     		}
							var begin=Ext.getCmp('tblAgentFeeCfgUpd.promotionBegDate').getValue();
							var end=Ext.getCmp('tblAgentFeeCfgUpd.promotionEndDate').getValue();
							
							if(!!begin&&!!end){
								var s=begin>end;
								if(s){
									Ext.Msg.alert('提示信息','激励结束日期需大于激励开始日期！');
									return;
								}
							}
							
							var store=feeCtlGridUpd.getSelectionModel().getSelections();
							var jsonArray=[];
							var tempgrid="";
							
							//20160323修改：加判断：合作伙伴添加或修改提交的时候，分润费率必须选一个，没选不让提交  --yww
							if (store.length<=0) {
								showErrorMsg("必须至少选择一项分润费率！", updBrhInfoForm);
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
							var storBrhCashRateSel = brhCashRateGrid.getSelectionModel().getSelections();
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
							var settleTypeUpd='';
							var settleTypeUpdType=Ext.getCmp('settleTypeUpdId').getValue();
							var T_Nupd=Ext.getCmp('T_Nupd').getValue();
							var period=Ext.getCmp('periodIdUpd').getValue();
							
							//补齐
							var fillWith=function(val,length,type){
								for (var i = val.length; i < length; i++) {
									val=type+val;
								}
								return val;
							} 
							
							if(settleTypeUpdType=='0'){
								settleTypeUpd=settleTypeUpdType+' '+fillWith(T_Nupd,3,'0');
							}else if(settleTypeUpdType=='1'){
								settleTypeUpd=settleTypeUpdType+period+'   ';
							}
							Ext.getCmp('settleTypeUpd').setValue(settleTypeUpd);
							//设置开户行名称
							Ext.getCmp('tblBrhSettleInfUpd.settleBankNm').setValue(Ext.getCmp('settleBankNoUpdId').getRawValue());
							


							//var nomber=Ext.getCmp('tblBrhInfoUpd.brhId').getValue();//合作伙伴编码
							//alert("合作伙伴号= "+Ext.getCmp('tblBrhInfoUpd.createNewNo').getValue()+"合作伙伴编码= "+nomber);
							//取多选记录方法 用逗号分开 后台处理
							var rows=feeCtlGridUpd.getSelectionModel().getSelections();//获取所有选中行
							var str = '';
							for(var i=0;i <rows.length;i++){
								str = str+rows[i].get('rateId')+',';
							}
							//alert("rateid= "+str);
							Ext.Ajax.request({
								url : 'T10105Action.asp?method=check',
								params : {
									"str" : str,
									brhId:brhId
								},
								//async: false,
								method: 'POST',
			                    success: function(response, options) {
			                         //获取响应的json字符串
			                    	var responseArray = Ext.util.JSON.decode(response.responseText); 
			                    	if(responseArray.msg=="true"){
										updBrhInfoForm.getForm().submit({
											url : 'T10105Action.asp?method=upd',
											waitMsg : '正在提交，请稍后......',
											success : function(form, action) {
												// 重置表单
												//updBrhInfoForm.getForm().reset();
												updBrhWin.close();
												showSuccessMsg(action.result.msg,El);
												El.getStore().reload();	
											},
											failure : function(form, action) {
												showErrorMsg(action.result.msg, El);
											},
											params : {
												txnId : '10105',
												subTxnId : '03',
												brhId:brhId,
												//"str" : str,
												gridJson:resultJson,
												cashRateJson:cashRateResultJson
											}
										});
										//Ext.Msg.alert('提示', '费率档无商户正在使用，可删除');
									}else {
										Ext.Msg.alert('提示', responseArray.msg.substring(5,200));
										return false;
									}
			                    }
							});
							
						}else {
							//showErrorMsg("页面数据不合法或存在必填字段未填写，请检查无误后再提交！", updBrhInfoForm);
							var foundInvalid = false;
							updBrhInfoForm.getForm().items.eachKey(function(key,item){  
								//console.log(item.fieldLabel)  
								if (!foundInvalid && !item.isValid()) {
									var strFeildName = item.fieldLabel; 
									foundInvalid = true;
									strFeildName = strFeildName.replace(/[\*|%]/g, '')
									showErrorMsg('【' + strFeildName + "】未填写或数据不合法，请检查无误后再提交！", updBrhInfoForm);
									item.focus();
								}
							});
						}
					}
				}, {
					text : '重置',
					handler : function() {
						updBrhInfoForm.getForm().reset();
						updBrhInfoForm.getForm().loadRecord(updGridStore.getAt(0));
						Ext.getCmp('provinceUpd').setValue(subbranIdStore.getAt(0).data.province);
						Ext.getCmp('cityUpd').setValue(subbranIdStore.getAt(0).data.city);
						Ext.getCmp('bankNameUpd').setValue(subbranIdStore.getAt(0).data.bankName);
						setTradeType();
						updBrhInfoForm.getForm().clearInvalid();
					}
				}, {
					text : '关闭',
					handler : function() {
						updBrhWin.close(El);
					}
				}]
			});

	function setTradeType() {
		//设置结算类型
		var settleTypeUpd =updGridStore.getAt(0).data.settleType;
		if(settleTypeUpd!=null&&settleTypeUpd.trim()!=''){
			var settleTypeUpdType=settleTypeUpd.substr(0,1);
			Ext.getCmp('settleTypeUpdId').fireEvent('select',this,this.store,settleTypeUpdType);//combo,record,number
			Ext.getCmp('settleTypeUpdId').setValue(settleTypeUpdType);//combo,record,number
			if (settleTypeUpdType == '0') {
				Ext.getCmp('T_Nupd').setValue(parseInt(settleTypeUpd.substr(2,3),10));
			} else if (settleTypeUpdType == '1') {
				Ext.getCmp('periodIdUpd').setValue(settleTypeUpd.substr(1,1));
			}
			
		}
	}
	
	updGridStore.load({
		params : {
			brhId : brhId
		},
		callback : function(records, options, success) {
			if (success) {
				/*var cupBrh = Ext.getCmp('tblBrhInfoUpd.cupBrhIdId');
				cupBrh.bindStore(cupBrhStore1Upd);
				SelectOptionsDWR.loadCupBrhIdOptData(brhId, function(ret) {
					cupBrhStore1Upd.loadData(Ext.decode(ret));
				});*/
				
				updBrhInfoForm.getForm().loadRecord(updGridStore.getAt(0));
				updBrhInfoForm.getForm().clearInvalid();
//				updBrhWin.setTitle('合作伙伴信息修改');
				// 上级营业网点编号
				/*var brhLevel = updGridStore.getAt(0).get('tblBrhInfoUpd.brhLevel');
				SelectOptionsDWR.getComboDataWithParameter('UP_BRH_ID', brhLevel, function(ret) {
							upBrhStoreUpd.loadData(Ext.decode(ret));
				});*/
				updBrhWin.show();
//				if (updGridStore.getAt(0).data.resv1_7Upd == '1') {
					updBrhInfoForm.findById('settleFlagUpdIds').show();
					Ext.getCmp('settleFlagUpdId').clearInvalid();
					updBrhInfoForm.findById('settleAcctNmUpdId').show();
					Ext.getCmp('tblBrhSettleInfUpd.settleAcctNm').clearInvalid();
					updBrhInfoForm.findById('settleAcctUpdId').show();
					Ext.getCmp('tblBrhSettleInfUpd.settleAcct').clearInvalid();
					
					updBrhInfoForm.findById('settleAcctCheckUpdId').show();
					Ext.getCmp('settleAcctCheckUpd').clearInvalid();
					
					updBrhInfoForm.findById('settleBankNmUpdId').show();
					Ext.getCmp('tblBrhSettleInfUpd.settleBankNm').clearInvalid();
					updBrhInfoForm.findById('settleBankNoUpdId').show();
					Ext.getCmp('settleBankNoUpdId').clearInvalid();
					
					
					subbranIdStore.load({
						params: {
							start: 0,
							querySubbranchId:Ext.getCmp('settleBankNoUpdId').getValue()
						},callback : function(records, options, success) {
							if (success) {
								if (subbranIdStore.getAt(0) != null && subbranIdStore.getAt(0).data.serialNo != '') {
									Ext.getCmp('provinceUpd').setValue(subbranIdStore.getAt(0).data.province);
									Ext.getCmp('cityUpd').setValue(subbranIdStore.getAt(0).data.city);
									Ext.getCmp('bankNameUpd').setValue(subbranIdStore.getAt(0).data.bankName);
									

									var province = Ext.getCmp('provinceUpd').getValue();
									if(province != ''){
										SelectOptionsDWR.getComboDataWithParameter('CITY',province,function(ret){
											cityStoreUpd.loadData(Ext.decode(ret));
										});
									}
									
									var city = Ext.getCmp('cityUpd').getValue();
									if(city != ''){
										SelectOptionsDWR.getComboDataWithParameter('BANKNAME',province+','+city,function(ret){
											bankNameStoreUpd.loadData(Ext.decode(ret));
										});
									}
									
									var bankName = Ext.getCmp('bankNameUpd').getValue();
									if(bankName != ''){
										SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH',bankName+','+province+','+city,function(ret){
											subbranchStoreUpd.loadData(Ext.decode(ret));
										});
									}

								}
							}else{
								
							}
						}
					});					
					
				
				var settleBankNoUpdId = Ext.getCmp('settleBankNoUpdId').getValue();
				SelectOptionsDWR.getComboDataWithParameter('SUBBRANCH_UPDATE',settleBankNoUpdId,function(ret){
					subbranchStoreUpd.loadData(Ext.decode(ret));
					Ext.getCmp('settleBankNoUpdId').setValue(settleBankNoUpdId);
				});
				
				setTradeType();
				
				showCmpProcess(updBrhInfoForm, '正在加载合作伙伴信息，请稍后......');
				hideCmpProcess(updBrhInfoForm, 1000);
				
				feeCtlStoreUpd.load({
					callback : function(records, options, success) {
						if (success) {
							var selected = [];
			            	
			                if (!success || !records || records.length == 0)
			                    return;
			                
			                var selModel = feeCtlGridUpd.getSelectionModel();
			                if(!feeCtlGridUpd.store.data)return;
			                for(var i=0;i<records.length;i++){
			                	var record = records[i];
			                	if (record.get("selected") == "1") {
//			                		selModel.selectRow(i, true);    //选中record，并且保持现有的选择，不触发选中事件
//			                            selected.push(record);
			                		selected.push(this.indexOf(record));
			                        }
			                }
			                try{
			                	feeCtlGridUpd.getSelectionModel().selectRows(selected,true);    //选中record
			                }catch(e){
//			                	feeCtlGridUpd.getSelectionModel().selectRows(selected,true);    //选中record
			                }
			                
						}
						}
					});
				brhCashRateStore.load({
					callback : function(records, options, success) {
						if (success) {
							var selected = [];
			            	
			                if (!success || !records || records.length == 0)
			                    return;
			                
			                var selModel = brhCashRateGrid.getSelectionModel();
			                if(!brhCashRateGrid.store.data)return;
			                for(var i=0;i<records.length;i++){
			                	var record = records[i];
			                	if (record.get("selected") == "1") {
//			                		selModel.selectRow(i, true);    //选中record，并且保持现有的选择，不触发选中事件
//			                            selected.push(record);
			                		selected.push(this.indexOf(record));
			                    }
			                }
			                try{
			                	brhCashRateGrid.getSelectionModel().selectRows(selected,true);    //选中record
			                }catch(e){
			                }
						}
						}
					});
			} else {
				showErrorMsg("加载合作伙伴信息失败，请刷新数据后重试", El);
			}
		}
	});
	
	
}