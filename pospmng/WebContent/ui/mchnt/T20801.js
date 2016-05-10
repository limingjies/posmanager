Ext.onReady(function() {
    var actInf;
    var mchtInf;

	// 所属机构
	var brhStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});
	SelectOptionsDWR.getComboData('BRH_BELOW_BRANCH',function(ret){
		brhStore.loadData(Ext.decode(ret));
	});
	
	// 商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    
    // 商户类别
    var mchntGrpStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_GRP',function(ret){
        mchntGrpStore.loadData(Ext.decode(ret));
    });
    
    
    
    var mchtStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoForActivity'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtGrp',mapping: 'mchtGrp'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'connType',mapping: 'connType'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'actFee',mapping: 'actFee'}
		])
	});
	mchtStore.load({
		params: {
			start: 0
		}
	});
	
    var sm = new Ext.grid.CheckboxSelectionModel();
    sm.handleMouseDown = Ext.emptyFn;
	var mchtColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		sm,
		{header: '商户号',dataIndex: 'mchtNo',sortable: true,width: 130},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200},
		{header: '商户类型',dataIndex: 'mchtGrp',width: 70},
		{header: 'MCC',dataIndex: 'mcc',width: 70},
		{header: '所属机构',dataIndex: 'bankNo',width: 70},
		{header: '联接类型',dataIndex: 'connType',width: 70},
		{header: '计费算法',dataIndex: 'discNm',width:90}
	]);
	
	
	
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=actInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'devNum',mapping: 'devNum'},
			{name: 'devProd',mapping: 'devProd'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'actContent',mapping: 'actContent'},
			{name: 'actFee',mapping: 'actFee'}
		])
	});
	actStore.load({
		params: {
			start: 0
		}
	});
	
	var actColModel = new Ext.grid.ColumnModel([
	    {id: 'actNo',header: '活动编号',dataIndex: 'actNo',width: 90},
        {header: '活动名称',dataIndex: 'actName',width: 160
        ,editor: new Ext.form.TextField({
    			editable: true,
				maxLength: 40,
    			vtype: 'isOverMax'
    		 })
        },
	    {header: '活动开始日期',dataIndex: 'startDate',width:110,renderer: formatDt
//	    ,editor: new Ext.form.TextField ({
//				editable: true
//	    		})
	    },
	    {header: '活动截止日期',dataIndex: 'endDate',width: 110,renderer: formatDt
//	    ,editor: new Ext.form.TextField ({
//		    	editable: true
//	    		})
		},
	    {header: '活动类型',dataIndex: 'actContent',renderer: actType,width: 110
//	    ,editor: new Ext.form.ComboBox({
//				store: new Ext.data.ArrayStore({
//					fields: ['valueField','displayField'],
//					data: [['0','积分消费'],['1','分期消费'],['2','折扣消费']]
//					}),
//				editable: true,
//				allowBlank: false,
//				blankText: '请选择一个活动类型',
//				hiddenName: 'actContentE'
//	    		})
	    },
	    {header: '分期期数',dataIndex: 'devNum',width: 80},
	    {header: '分期产品号',dataIndex: 'devProd',width: 110},
	    {header: '活动费率(倍率)',dataIndex: 'actFee',width: 100
//	    ,
//		    editor: new Ext.form.TextField({
//		    	editable: true,
//				maxLength: 6,
//    			vtype: 'isOverMax'
//    		 })
	    }
	]);
	
	//活动新增按钮
	var addMenu = {
		text: '新增',
		width: 85,
		id:'add',
		iconCls: 'add',
		handler:function() {
			actAddWin.show();
//			actAddWin.center();
		}
	};
	
	//活动保存按钮
	var upMenu = {
		text: '保存',
		width: 85,
		iconCls: 'reload',
		disabled: true,
		handler: function() {
			var modifiedRecords = grid.getStore().getModifiedRecords();
			if(modifiedRecords.length == 0) {
				return;
			}
			

			//存放要修改的信息
			var array = new Array();
			for(var index = 0; index < modifiedRecords.length; index++) {
				var record = modifiedRecords[index];
				
				if(record.get('startDate') > record.get('endDate')){
					showErrorMsg("活动开始日期不能大于活动截止日期",grid);
					return;
				}
				
				var data = {
					actNo : record.get('actNo'),
					actName : record.get('actName')
//					devNum : record.get('devNum'),
//					devProd : record.get('devProd'),
//					startDate:record.get('startDate'),
//					endDate: record.get('endDate'),
//					actFee:record.get('actFee'),
//					actContent: record.get('actContent')
				};
				array.push(data);
			}
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20801Action.asp?method=update',
				method: 'post',
				timeout: '10000',
				params: {
				    actList: Ext.encode(array),
					txnId: '20801',
					subTxnId: '02'
				},
				failure: function(form,action) {
					showErrorMsg(action.result.msg,grid);
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						grid.getStore().commitChanges();
						showSuccessMsg(rspObj.msg,grid);
					} else {
						grid.getStore().rejectChanges();
						showErrorMsg(rspObj.msg,grid);
					}
					grid.getTopToolbar().items.items[2].disable();
					grid.getStore().reload();
					hideProcessMsg();
				}
			});
		}
	};
	
	//活动删除
	var deleteMenu = {
		text: '删除活动',
		width: 85,
		id: 'deleteAct',
		iconCls: 'delete',
		disabled: true,
		handler:function() {
			if(grid.getSelectionModel().hasSelection()) {
				var record = grid.getSelectionModel().getSelected();
				var actNo = record.get('actNo');
						
				showConfirm('确定要删除该营销活动吗？活动编号：' + actNo,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.request({
							url: 'T20801Action.asp?method=delete',
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									grid.getTopToolbar().items.items[4].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								actNo: actNo,
								txnId: '20801',
								subTxnId: '03'
							}
						});
					}
				});
			}
		}
	};
	
	//活动查询
	var queryActMenu = {
		text: '活动查询',
		width: 85,
		id: 'queryAct',
		iconCls: 'query',
		handler:function() {
			queryActWin.show();
		}
	};
	
	//绑定活动
	var acceptMenu = {
        id: 'acceptButton',
		text: '绑定商户',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		handler:function() {
            
            var selectedOptions = new Array();
            var selectedMchtName = new Array();
            var rec = grid.getSelectionModel().getSelected();
			var rec2 = grid2.getSelectionModel().getSelections();
			
			if(rec == null){
				showAlertMsg('请选择活动',grid);
			}
			if(rec2.length <= 0){
				showAlertMsg('请选择要绑定的商户',grid);
			}
			
			for(var i = 0;i<rec2.length;i++){
				selectedOptions[i] = rec2[i].get('mchtNo');
				selectedMchtName[i] = rec2[i].get('mchtNm');
			}
			var actNoACC = rec.get('actNo');
			var  actContentACC=rec.get('actContent');
    		T20801.isExist(actNoACC,selectedOptions,actContentACC,function(ret){
				if(ret == "03"){
					showAlertMsg('商户已经绑定同类型的营销活动,请先到商户参与表解绑！',grid);
				}
				if(ret == "04"){
					showAlertMsg('该商户已经绑定该活动,请先到商户参与表解绑！',grid);
				}
				if(ret == "05"){
					showAlertMsg('该商户已经绑定的同期数活动,请先到商户参与表解绑！',grid);
				}
				if(ret == "00"){
					showConfirm('确定绑定？',grid,
					function(bt) {
						if(bt == "yes") {
							showProcessMsg('正在绑定，请稍后......');
							
							Ext.Ajax.request({
		                        url: 'T20801Action.asp?method=accept',
		                        waitMsg: '正在提交，请稍后......',
		                        success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,grid);
									} else {
										showErrorMsg(rspObj.msg,grid);
									}
									// 重新加载信息
									grid.getStore().reload();
		                            grid2.getStore().reload();
		                            grid.getTopToolbar().items.items[8].disable();
								},
		                        params: {
		                        	actNoACC: actNoACC,
		                        	selectedOptions: selectedOptions,
		                            txnId: '20801',
		                            subTxnId: '04'
		                        }
		                    });
		                    hideProcessMsg();
						}
					});
				}
			});			
		}
	};
	
	
	var menuArr = new Array();
	menuArr.push(addMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(upMenu);		// [2]	
	menuArr.push('-');  //[3]
	menuArr.push(deleteMenu);		// [4]	
	menuArr.push('-');  //[5]
	menuArr.push(queryActMenu);  //[6]
	menuArr.push('-');  //[7]
	menuArr.push(acceptMenu);  //[8]
	
	
	// 活动信息列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '营销活动信息',
		region: 'north',
		iconCls: 'act',
		frame: true,
		height: 240,
		border: true,
		columnLines: true,
		stripeRows: true,
		clicksToEdit: true,
		store: actStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: actColModel,
		forceValidation: true,
		renderTo: Ext.getBody(),
		tbar: menuArr,
		loadMask: {
			msg: '正在加载活动信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
    
	
	//商户查询
	var queryMchtMenu = {
		text: '商户查询',
		width: 85,
		id: 'queryMcht',
		iconCls: 'query',
		handler:function() {
			queryMchtWin.show();
		}
	};
	
	//商户信息列表
    var grid2 = new Ext.grid.GridPanel({
        title: '商户信息',
        region: 'center',
        iconCls: 'mchnt',
        frame: true,
        border: true,
        columnLines: true,
        stripeRows: true,
        store: mchtStore,
        sm: sm,
        cm: mchtColModel,
        forceValidation: true,
        tbar: [queryMchtMenu],
        loadMask: {
            msg: '正在加载商户信息列表......'
        },
        bbar: new Ext.PagingToolbar({
            store: mchtStore,
            pageSize: System[QUERY_RECORD_COUNT],
            displayInfo: true,
            displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
            emptyMsg: '没有找到符合条件的记录'
        })
    });
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			var actInf = grid.getSelectionModel().getSelected();
            var mchtInf = grid2.getSelectionModel().getSelected();
			if(actInf != null&&mchtInf != null) {
				Ext.getCmp('acceptButton').enable();
			} else {
				Ext.getCmp('acceptButton').disable();
			}
		}
	});
	
    grid2.getSelectionModel().on({
        'rowselect': function() {
            var actInf = grid.getSelectionModel().getSelected();
            var mchtInf = grid2.getSelectionModel().getSelected();
            if(actInf != null&&mchtInf != null) {
                Ext.getCmp('acceptButton').enable();
            } else {
                Ext.getCmp('acceptButton').disable();
            }
        }
    });
    
    
	//每次在列表信息加载前都将保存按钮屏蔽
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getStore().rejectChanges();
	});
	
	grid.on({
		//单击行，使删除按钮可用
		'rowclick': function() {
			if(grid.getTopToolbar().items.items[4] != undefined) {
				grid.getTopToolbar().items.items[4].enable();
			}
		},
		//在编辑单元格后使保存按钮可用
		'afteredit': function(e) {
			if(grid.getTopToolbar().items.items[2] != undefined) {
				grid.getTopToolbar().items.items[2].enable();
			}
		}
	});

    //活动新增表单
    var actAddPanel = new Ext.form.FormPanel({
        region: 'north',
        height: 160,
        frame: true,
        layout: 'column',
        items: [
//        	{
//                columnWidth: .5,
//                layout: 'form',
//                items: [{	
//			         	xtype: 'textfield',
//			 			fieldLabel: '营销活动编号*',
//			 			id: 'actNo',
//			 			name: 'actNo',
//			 			allowBlank: false,
//			 			width: 200,
//			 			maxLength: 10,
//			 			blankText: '活动编号不能为空'
//				}]
//            },
            	{
			    columnWidth: .5,
            	layout: 'form',
			    items: [{	
			         	xtype: 'textfield',
			 			fieldLabel: '营销活动名称*',
			 			id: 'actName',
			 			name: 'actName',
			 			allowBlank: false,
			 			width: 200,
			 			maxLength: 40,
			 			blankText: '活动名称不能为空'
				}]
			},{
        	columnWidth: .5,
            layout: 'form',
            items: [{
				width: 200,
				xtype: 'datefield',
				vtype: 'daterange',
				fieldLabel: '活动开始时间*',
				allowBlank: false,
				endDateField: 'endDate',
				format : 'Ymd',
				name :'startDate',
				id :'startDate'
		  }]
      	  },{
        	columnWidth: .5,
            layout: 'form',
            items: [{											
				width: 200,
				xtype: 'datefield',
				vtype: 'daterange',
				fieldLabel: '活动截止时间*',
				allowBlank: false,
				format : 'Ymd',
				startDateField: 'startDate',
				name :'endDate',
				id :'endDate'
		   }]
	   	 },{
	   	 	columnWidth: .5,
            layout: 'form',
            items: [{
            		width: 200,
                    xtype: 'combo',
	                fieldLabel: '营销活动类型*',
	                id: 'actContentA',
	                hiddenName: 'actContent',
		            allowBlank: false,
	                store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['0','活动0：积分消费'],['1','活动1：分期消费'],['2','活动2：折扣消费'],['3','活动3：持卡人抽奖']]
	                }),
	                listeners:{
                        'select': function() {
                        	
	                        var args = Ext.getCmp('actContentA').getValue();
	                        if(args == 1)
	                        {
	                            Ext.getCmp("devNumF").show();
                                Ext.getCmp("devProdF").show();
                                Ext.getCmp('actFeeF').hide();
                                Ext.getCmp('actFee').reset();
                                Ext.getCmp('devNum').allowBlank = false;
                                Ext.getCmp('devProd').allowBlank = false;
                                Ext.getCmp('actFee').allowBlank = true;
	                        }else if(args=='3'){
	                        	Ext.getCmp('actFeeF').hide();
                                Ext.getCmp('actFee').reset();
                                Ext.getCmp('actFee').allowBlank = true;
                                 Ext.getCmp("devNumF").hide();
                                Ext.getCmp("devProdF").hide();
                                 Ext.getCmp("devNum").reset();
                                Ext.getCmp("devProd").reset();
                                 Ext.getCmp('devNum').allowBlank = true;
                                Ext.getCmp('devProd').allowBlank = true;
	                        }
                            else 
                            {
                                Ext.getCmp("devNumF").hide();
                                Ext.getCmp("devNum").reset();
                                Ext.getCmp("devProdF").hide();
                                Ext.getCmp("devProd").reset();
                                Ext.getCmp('actFeeF').show();
                                Ext.getCmp('devNum').allowBlank = true;
                                Ext.getCmp('devProd').allowBlank = true;
                                Ext.getCmp('actFee').allowBlank = false;
                            }
                        }
                   }
                 }]
        },{
		    layout: 'form',
		    id:'actFeeF',
		    columnWidth: .5,
		    items: [{	
		    		xtype: 'textfield',
//		         	vtype: 'isNumber',
//		    		vtype: 'double',
		 			fieldLabel: '活动费率(倍率)*',
		 			id: 'actFee',
		 			name: 'actFee',
//		 			regex: /^[0-9]+(.[0-9]{1,2})$/, 
		 			regex: /^\d{1,4}(\.\d{1,2})?$/, 
               		regexText: '请输入整数或者小数',
		 			allowBlank: false,
		 			width: 200
		 			}]
		 },{
		    layout: 'form',
		    id:'devNumF',
		    xtype: 'panel',
		    hidden:true,
		    columnWidth: .5,
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '分期期数',
		 			id: 'devNum',
		 			name: 'devNum',
		 			regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字0-9',
					maskRe: /^[0-9]+$/,
		 			maxLength: 2,
		 			width: 200
		 			}]
		 },{
		    layout: 'form',
		    id:'devProdF',
		    xtype: 'panel',
		    hidden:true,
		    columnWidth: .5,
		    items: [{	
		         	xtype: 'textfield',
		 			fieldLabel: '分期产品号',
		 			id: 'devProd',
		 			name: 'devProd',
		 			maxLength: 20,
		 			width: 200
		 			}]
		 }]
    });
    
    //活动新增窗口
	var actAddWin = new Ext.Window({
		title: '活动新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		autoHeight: true,
		layout: 'fit',
		animateTarget: 'add',
		items: [actAddPanel],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(actAddPanel.getForm().isValid()) {
					actAddPanel.getForm().submit({
						url: 'T20801Action.asp?method=add',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							actAddPanel.getForm().reset();
							showSuccessMsg(action.result.msg,actAddWin);
							actAddWin.hide();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,actAddWin);
						},
						params: {
							txnId: '20801',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
					actAddPanel.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				actAddWin.hide();
			}
		}]
	});
    
    //活动查询表单
    var queryActForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 100,
		items: [{	
	         	xtype: 'textfield',
	 			fieldLabel: '营销活动编号',
	 			id: 'actNoAQ',
	 			name: 'actNoAQ',
	 			width: 200,
	 			maxLength:10
		},{
            xtype: 'combo',
	        fieldLabel: '营销活动类型*',
	        id: 'actContentAQ',
	        hiddenName: 'actContentQ',
		    width: 200,
	        store: new Ext.data.ArrayStore({
	        	fields: ['valueField','displayField'],
	            data: [['0','活动0：积分消费'],['1','活动1：分期消费'],['2','活动2：折扣消费'],['3','活动3：持卡人抽奖']]
	        })
         },{
			xtype: 'datefield',
			id: 'startDateQ',
			name: 'startDateQ',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			vtype: 'daterange',
			endDateField: 'endDateQ',
			fieldLabel: '活动开始日期'
		},{
			xtype: 'datefield',
			id: 'endDateQ',
			name: 'endDateQ',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			width: 200,
			vtype: 'daterange',
			startDateField: 'startDateQ',
			fieldLabel: '活动截止日期'
		}]
	});
	
	//活动查询信息
	var queryActWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 360,
		autoHeight: true,
		items: [queryActForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		resizable: false,
		closable: true,
		animateTarget: 'query',
		tools: [{
			id: 'minimize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.maximize.show();
				toolEl.hide();
				queryActWin.collapse();
				queryActWin.getEl().pause(1);
				queryActWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
			},
			qtip: '最小化',
			hidden: false
		},{
			id: 'maximize',
			handler: function(event,toolEl,panel,tc) {
				panel.tools.minimize.show();
				toolEl.hide();
				queryActWin.expand();
				queryActWin.center();
			},
			qtip: '恢复',
			hidden: true
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				actStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryActForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				queryActWin.hide();
			}
		}]
	});
 
	
	//商户查询表单 
    var mchntForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 100,
		items: [{
             columnWidth: .6,
             layout: 'form',
             items:[{
                xtype: 'combo',
                fieldLabel: '所属分行',
                id: 'branchQ',
                hiddenName: 'branch',
                store: brhStore,
                displayField: 'displayField',
                valueField: 'valueField',
                mode: 'local',
                width: 360
             	}]
        	 },{
		        columnWidth: .6,
		        layout: 'form',
		        items:[{
		        xtype: 'combo',
		        store: mchntGrpStore,
		        fieldLabel: '商户类型',
		        displayField: 'displayField',
                valueField: 'valueField',
                mode: 'local',
		        id: 'mchtGrpQ',
	            name: 'mchtGrp',
	            width: 360
	            }]
	        },{
	             columnWidth: .9,
	             layout: 'form',
	             items:[{
	               	xtype: 'dynamicCombo',
	        		methodName: 'getMchntNo',
	                fieldLabel: '商户号',
	                hiddenName: 'mchnNo',
	                id: 'mchnNoQ',
	                displayField: 'displayField',
	                valueField: 'valueField',
	                width: 360
	           }]
	        },{
	            columnWidth: .6,
	            layout: 'form',
	            items: [{
	                xtype: 'combo',
	                fieldLabel: '连接类型',
	                id: 'connTypeQ',
	                hiddenName: 'connType',
	                store: new Ext.data.ArrayStore({
	                    fields: ['valueField','displayField'],
	                    data: [['Z','直联'],['J','间联']]
	                }),
	                width: 360
            }]
        }],
	   buttons: [{
	        text: '查询',
	        handler: function() 
	        {
	            mchtStore.load();
	            queryMchtWin.hide();
	        }
	    },{
	        text: '重填',
	        handler: function() {
	            mchntForm.getForm().reset();
	        }
	    },{
			text: '关闭',
			handler: function() {
				queryMchtWin.hide();
			}
		}]
	});
	//商户查询窗口
	var queryMchtWin = new Ext.Window({
		title: '商户查询',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 520,
		autoHeight: true,
		layout: 'fit',
		items: [mchntForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false
	});

    mchtStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            bankNo: Ext.getCmp('branchQ').getValue(),
            mchtGrp: Ext.getCmp('mchtGrpQ').getValue(),
            connType: Ext.getCmp('connTypeQ').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue()
        });
    }); 
    
    actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNoAQ').getValue(),
            actContent: Ext.getCmp('actContentAQ').getValue(),
            startDate: typeof(queryActForm.findById('startDateQ').getValue()) == 'string' ? '' : queryActForm.findById('startDateQ').getValue().dateFormat('Ymd'),
			endDate: typeof(queryActForm.findById('endDateQ').getValue()) == 'string' ? '' : queryActForm.findById('endDateQ').getValue().dateFormat('Ymd')
        });
    });
	var mainView = new Ext.Viewport({
        title: '营销活动配置',
		layout: 'border',
		items: [grid,grid2],
		renderTo: Ext.getBody()
	});
    
});