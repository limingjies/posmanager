Ext.onReady(function() {
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblUpbrhFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[//discId,discNm,brhId2,status,crtTime,crtOpr,uptTime,uptOpr
			{name: 'discId',mapping: 'discId'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'brhId2',mapping: 'brhId2'},
			{name: 'status',mapping: 'status'},
			{name: 'crtTime',mapping: 'crtTime'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'uptTime',mapping: 'uptTime'},
			{name: 'uptOpr',mapping: 'uptOpr'}
		]),
		autoLoad: false
	});
	
	var curOp;
	
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('FLAG',function(ret){
		flagStore.loadData(Ext.decode(ret));
	});
	
	var txnStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('feeTxnNum',function(ret){
		txnStore.loadData(Ext.decode(ret));
	});
	
	var cardTypeStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	SelectOptionsDWR.getComboData('feeCardType',function(ret){
		cardTypeStore.loadData(Ext.decode(ret));
	});
	
	var fm = Ext.form;
	var Fee = Ext.data.Record.create([{
        name: 'discId',
        type: 'string'
	},{
        name: 'cardType',
        type: 'string'
    },{
        name: 'txnNum',
        type: 'string'
    }, {
        name: 'flag',
        type: 'integer'
    }, {
        name: 'feeValue',
        type: 'double'
    },{
        name: 'maxFee',
        type: 'double',
        id: 'maxFee'
    }]);
	
	//业务成本扣率明细查询(TBL_UPBRH_FEE_DETAIL表查询)数据源
    var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getUpbrhFeeDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discId',mapping: 'discId',type:'string'},
			{name: 'cardType',mapping: 'cardType'},
			{name: 'txnNum',mapping: 'txnNum'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'maxFee',mapping: 'maxFee'}
		]),
		autoLoad: false
	});
	
    //添加和修改的columnModel
	var coluMdel = new Ext.grid.ColumnModel({
		columns: [{
            header: '卡类型',
            dataIndex: 'cardType',
            width: 90,
            editor: {
					xtype: 'basecomboselect',
			        store: cardTypeStore,
					id: 'idCardType',
					hiddenName: 'cardType',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardTypeStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '交易类型',
            dataIndex: 'txnNum',
            width: 100,
            hiddenName:'txnNum',
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					name: 'txnNum',
					width: 130
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = txnStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '结算方式',
            dataIndex: 'flag',
            width: 100,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idflag',
					hiddenName: 'flag',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = flagStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
		},{
            header: '百分比扣率',
            dataIndex: 'feeValue',
            width: 80,
			id: 'idFeeValue',
			name: 'feeValue',
            editor: new fm.NumberField({
            		allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000,
                    //设置允许保留3位小数
                    allowDecimals: true,
                    decimalPrecision: 3
                })
        },{
            id: 'idUpperAmount',
			name: 'maxFee',
            header: '封顶值',
            dataIndex: 'maxFee',
            width: 100,
            //sortable: true,
            editor: new fm.NumberField({
                    allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000000
                })
        }]
	});
	
	//添加的gridPanel
	var grid = new Ext.grid.EditorGridPanel({
		region: 'center',
        title: '扣率信息',
        store: store,
        cm: coluMdel,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
//        autoExpandColumn: 'maxFee', 
        frame: true,
        columnLines: true,
        stripeRows: true,
        clicksToEdit: 1,
        tbar: [{
            text: '添加一行',
            handler : function(){
                var p = new Fee();
                grid.stopEditing();
                store.insert(store.getCount(), p);
                grid.startEditing(store.getCount() - 1, 0);
            }
        },'-',{
            text: '删除一行',
            handler : function(){
            	store.remove(grid.getSelectionModel().getSelected());
            }
        }]
    });
	
    store.insert(0, new Fee({
    	maxFee: 0
    }));
    
    //添加、修改弹出的formPanel
	var feeForm = new Ext.FormPanel({
		autoHeight: true,
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		labelAlign: 'left',
		items: [{
			xtype: 'panel',
			layout: 'column',
			items: [{
	        	columnWidth: .5,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'hidden',
						fieldLabel: '手续费ID',
						id: 'discId',
						name: 'discId'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'textnotnull',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '档位名称*',
						id: 'discNm',
						name: 'discNm',
						width: 360
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [grid]
		}]
	});

	//添加、修改提交的 window
	var groupWin = new Ext.Window({
		title: '成本扣率维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 560,
		autoHeight: true,
		items: [feeForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		buttons: [{
			text: '保存',
			id: 'saveBt',
			handler: function(btn) {
				grid.focus();
				grid.stopEditing();
				var frm = feeForm.getForm();
				var records = '';
				if(feeForm.getForm().isValid()) {
					grid.getStore().commitChanges();
					var dataArray = new Array();
					
					//把每条中的交易卡种代码放进这个数组中
					//var txnNumArrayTmp = new Array();
					for(var i=0;i<store.getCount();i++){
						var re = store.getAt(i);
						if(re.data.cardType == null){showMsg("第" + (i+1) + "行的[卡类型]不能为空",grid);return;}
						if(re.data.txnNum == null){showMsg("第" + (i+1) + "行的[交易类型]不能为空",grid);return;}
						if(re.data.flag == null){showMsg("第" + (i+1) + "行的[结算方式]不能为空",grid);return;}
						
						//当结算方式选择百分比'1'时，封顶值应为不可输入状态
						if(re.data.flag == '2' && re.data.maxFee != 0){
							showMsg("第" + (i+1) + "行的[结算方式]为“百分比”时，[封顶值]应为0",grid);
							return;
						}
						//当结算方式选择固定金额时'3'，百分比扣率应为不可输入状态
						if(re.data.flag == '1' && re.data.feeValue != 0){
							showMsg("第" + (i+1) + "行的[结算方式]为“固定金额”时，[百分比扣率]应为0",grid);
							return;
						}
						
						
						if(re.data.feeValue == null){showMsg("第" + (i+1) + "行的[百分比扣率]不能为空",grid);return;}
						if(re.data.maxFee == null){showMsg("第" + (i+1) + "行的[封顶值]不能为空",grid);return;}

						var data = {
							discId: re.data.discId,
							cardType: re.data.cardType,
							txnNum: re.data.txnNum,
							flag: re.data.flag,
							feeValue: re.data.feeValue,
							maxFee: re.data.maxFee
						};
						dataArray.push(data);
					}

					if(1!=1){
					}else{
						frm.findField("discId").enable();
						frm.submitNeedAuthorise({
							url: 'T110111Action_' + (curOp=='01'?'add':'update') + '.asp',
							waitTitle : '请稍候',
							waitMsg : '正在提交表单数据,请稍候...',
							success : function(form, action) {
								gridStore.reload();
								showSuccessMsg(action.result.msg,gridPanel);
								groupWin.hide(grid);
								feeForm.getForm().resetAll();
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,gridPanel);
							},
							params: {
								txnId: '20701',
								subTxnId: curOp,
								record: records,
								data: Ext.encode(dataArray)
							}
						});
					}
					
				}
				
			}
		},{
			text: '重置',
			id: 'resetBt',
			handler: function() {
				if("01" == curOp){
					feeForm.getForm().resetAll();
					store.removeAll();
					store.insert(0, new Fee({
    					maxFee: 0
    				}));
				}else if("02" == curOp){
					feeForm.getForm().loadRecord(baseStore.getAt(0));
					store.load({
							params: {
								start: 0,
								discId: gridPanel.getSelectionModel().getSelected().data.discId
								}
						});
				}else{
					showAlertMsg("没有获得当前的操作状态，重置失败",feeForm);
				}
			}
		},{
			text: '取消',
			handler: function() {
				groupWin.hide(gridPanel);
				feeForm.getForm().resetAll();
				store.removeAll();
				store.insert(0, new Fee({
    				maxFee: 0
    			}));
			}
		}]
	});


	
	// 菜单集合
	var menuArr = new Array();
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			curOp = '01';
			store.removeAll();
			store.insert(0, new Fee({
    			maxFee: 0
    		}));
			groupWin.show();
			grid.focus();
		}
	};
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			baseStore.load({
				params: {
					discId: gridPanel.getSelectionModel().getSelected().data.discId
				},
				callback: function(records, options, success){
					if(success){
						curOp = "02";//update
						feeForm.getForm().loadRecord(baseStore.getAt(0));
						store.load({
							params: {
								start: 0,
								discId: gridPanel.getSelectionModel().getSelected().data.discId
								}
						});
						//feeForm.getForm().findField("discId").disable();
						var discId = gridPanel.getSelectionModel().getSelected().data.discId;
						feeForm.getForm().findField("discId").setValue(discId);
						var discNm = gridPanel.getSelectionModel().getSelected().data.discNm;
						feeForm.getForm().findField("discNm").setValue(discNm);
						groupWin.show(gridPanel);
					}else{
						showErrorMsg("加载成本扣率信息失败，请刷新数据后重试",gridPanel);
					}
				}
			});
		}
	};
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(editMenu);    //[2]
	
	//主页面列表查询数据源
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblUpbrhFee'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'discId',mapping: 'discId'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'brhId2',mapping: 'brhId2'},
			{name: 'status',mapping: 'status'},
			{name: 'crtTime',mapping: 'crtTime'},
			{name: 'crtOpr',mapping: 'crtOpr'},
			{name: 'uptTime',mapping: 'uptTime'},				
			{name: 'uptOpr',mapping: 'uptOpr'}
		])
	});
	
	gridStore.load({
		params: {
			start: 0
		}
	});

	//主页面显示的列表 <![CDATA[discId,discNm,brhId2,status,crtTime,crtOpr,uptTime,uptOpr]]>
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '档位编号',dataIndex: 'discId',width:80},
		{header: '档位名称',dataIndex: 'discNm',sortable: true,id:'discNm',width:50},
		{header: '状态',dataIndex: 'status',sortable: true,renderer:feeStatus},
		{header: '操作员号',dataIndex: 'uptOpr',sortable: true},
		{header: '修改时间',dataIndex: 'uptTime',sortable: true,renderer:formatDt,width:140},
		{header: '创建时间',dataIndex: 'crtTime',sortable: true,renderer:formatDt,width:140}
	]);

	//主页面列表panel
	var gridPanel = new Ext.grid.GridPanel({
		title: '成本扣率维护',
		region: 'center',
		iconCls: 'T207',
		frame: true,
		border: true,
		columnLines: true,	
		autoExpandColumn: 'discNm',
		stripeRows: true,
		store: gridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: gridColumnModel,
		forceValidation: true,
		tbar:[menuArr],
		loadMask: {
			msg: '正在加载成本扣率维护信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
	
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
		gridPanel.getTopToolbar().items.items[2].disable();
	}); 
	
	var rec;
	gridPanel.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(gridPanel.getView().getRow(gridPanel.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = gridPanel.getSelectionModel().getSelected();
			if(null != rec){
				gridPanel.getTopToolbar().items.items[2].enable();
			}else{
				gridPanel.getTopToolbar().items.items[2].disable();
			}
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridPanel],
		renderTo: Ext.getBody()
	});
	
});