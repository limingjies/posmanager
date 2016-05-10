Ext.onReady(function() {

	var termStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=termInfoForMaint'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount'
		}, [ {
			name : 'termId',
			mapping : 'termId'
		}, {
			name : 'productCd',
			mapping : 'productCd'
		}, {
			name : 'mchntNo',
			mapping : 'mchntNo'
		}, {
			name : 'mchntName',
			mapping : 'mchntName'
		}, {
			name : 'termSerialNum',
			mapping : 'termSerialNum'
		}, {
			name : 'termSta',
			mapping : 'termSta'
		}, {
			name : 'termSignSta',
			mapping : 'termSignSta'
		}, {
			name : 'termIdId',
			mapping : 'termIdId'
		}, {
			name : 'termFactory',
			mapping : 'termFactory'
		}, {
			name : 'termMachTp',
			mapping : 'termMachTp'
		}, {
			name : 'termVer',
			mapping : 'termVer'
		}, {
			name : 'termTp',
			mapping : 'termTp'
		}, {
			name : 'termBranch',
			mapping : 'termBranch'
		}, {
			name : 'termIns',
			mapping : 'termIns'
		}, {
			name : 'recCrtTs',
			mapping : 'recCrtTs'
		}, {
			name : 'recUpdTs',
			mapping : 'recUpdTs'
		}, {
			name : 'propTp',
			mapping : 'propTp'
		}, {
			name : 'termAddr',
			mapping : 'termAddr'
		}, {
			name : 'param',
			mapping : 'param'
		}, {
			name : 'modelName',
			mapping : 'modelName'
		}
		
		])
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),
		{header: '终端类型',dataIndex: 'termTp',width: 70,renderer:termForType},
			{header: '产权属性',dataIndex: 'propTp',width: 100,renderer:
				function settle(val){
				switch(val){
				case '0':return '钱宝所有';
				case '1':return '合作伙伴所有';
				case '2':return '商户自有';
				}
			}},
			{header: '安装地址',dataIndex: 'termAddr',width: 230},
			{
				id : 'productCd_id',
				header : '终端SN号',
				dataIndex : 'productCd',
				width : 100
			},
			{
				id : 'termId',
				header : '终端号',
				dataIndex : 'termId',
				width : 100
			}, {
				header : '商户号',
				//hidden:true,
				dataIndex : 'mchntNo',
				width : 120,
				id : 'mchntNo'
			}, {
				header : '商户名',
				//hidden:true,
				dataIndex : 'mchntName',
				width : 200,
				id : 'mchntName'
			}, {
				header : '终端状态',
				//hidden:true,
				dataIndex : 'termSta',
				width : 80,
				renderer : termSta
			}, {
				header : '终端序列号',
				hidden:true,
				dataIndex : 'termSerialNum',
				width : 200,
				renderer : termSta
			}, {
				header : '终端所属合作伙伴',
				//hidden:true,
				dataIndex : 'termBranch',
				width: 180
			}, {
				header : '机具库存编号',
				hidden:true,
				dataIndex : 'termIdId',
					width: 0
			}, {
				header : '机具厂商',
				hidden:true,
				dataIndex : 'termFactory',
				width : 280
			}, {
				header : '机具型号',
				hidden:true,
				dataIndex : 'termMachTp',
				width : 200
			}, {
				header : '入网审核通过日期',
				//hidden:true,
				dataIndex : 'recUpdTs',
				width : 150,
				renderer : formatDt
			}, {
				header : '录入日期',
				hidden:true,
				dataIndex : 'recCrtTs',
				width : 150,
				renderer : formatDt
			}, {
				header : '签购单模板',
				dataIndex : 'modelName',
				align:'center',
				width : 150
			}
			
			]);
	
	var queryMenu = {
			text : '录入查询条件',
			width : 85,
			id : 'query',
			iconCls : 'query',
			handler : function() {
				queryWin.show();
			}
		};
	
	var topQueryPanel = new Ext.form.FormPanel({
		frame : true,
		border : true,
		width : 500,
		autoHeight : true,
		labelWidth : 80,
		items : [new Ext.form.TextField({
							id : 'termNoQ',
							name : 'termNo',
							fieldLabel : '终端号',
							anchor : '60%'
						}), {
					xtype : 'dynamicCombo',
					methodName : 'getMchntNo',
					fieldLabel : '商户编号',
					hiddenName : 'mchnNo',
					id : 'mchnNoQ',
					editable : true,
					width : 300,
					
				}, {
					xtype : 'combo',
					fieldLabel : '终端状态',
					id : 'state',
					name : 'state',
					anchor : '60%',
					store : new Ext.data.ArrayStore({
								fields : ['valueField', 'displayField'],
								data : [['0', '新增未审核'], ['1', '启用'],
										['2', '修改未审核'], ['3', '冻结未审核'],
										['4', '冻结'], ['5', '恢复未审核'],
										['6', '注销未审核'], ['7', '注销']]
							})
				}, {
					xtype : 'basecomboselect',
					baseParams : 'TERM_TYPE',
					fieldLabel : '终端类型',
					id : 'idtermtpsearch',
					hiddenName : 'termtpsearch',
					anchor : '60%'
				},{
					xtype: 'textfield',
					id : 'productCd_query',
					name : 'productCd',
					fieldLabel : '终端SN号',
					anchor : '60%'
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '创建起始时间',
					format : 'Y-m-d',
					name : 'startTime',
					id : 'startTime',
					anchor : '60%'
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '创建截止时间',
					format : 'Y-m-d',
					name : 'endTime',
					id : 'endTime',
					anchor : '60%'
				},{
					/*xtype : 'basecomboselect',
					baseParams: 'MCHNT_MODEL',*/
 					xtype : 'dynamicCombo',
 					methodName : 'getModelInfo',
					fieldLabel : '签购单模板',
					hiddenName : 'queryMchtModel',
					id : 'queryMchtModelId',
					//store:modelStore,
					editable : true,
					emptyText : '请选择模板',
					width : 200			
				}
				
				],
		buttons : [{
					text : '查询',
					handler : function() {
						termStore.load();
						queryWin.hide();
					}
				}, {
					text : '重填',
					handler : function() {
						topQueryPanel.getForm().reset();
					}
				}]
	});
	
	var queryWin = new Ext.Window({
		title : '查询条件',
		layout : 'fit',
		width : 500,
		autoHeight : true,
		items : [topQueryPanel],
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
		}]
	});
	
	var qryMenu = {
			text : '详细信息',
			width : 85,
			hidden:false,
			iconCls : 'detail',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGridPanel.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
				bt.disable();
				setTimeout(function() {
							bt.enable();
						}, 2000);
				selectTermInfoNew(selectedRecord.get('termId'), selectedRecord
								.get('recCrtTs'));
			}
		};
	
	var editMenu = {
			text : '修改',
			width : 85,
			iconCls : 'edit',
			disabled : true,
			handler : function(bt) {
				var selectedRecord = termGridPanel.getSelectionModel().getSelected();
				if (selectedRecord == null) {
					showAlertMsg("没有选择记录", termGridPanel);
					return;
				}
//				bt.disable();
				updateTmpDealInfo(selectedRecord.get('termId'), selectedRecord.get('recCrtTs'),termGridPanel);
			}
		};
	
	var menuArr = new Array();
	menuArr.push(queryMenu); 
	menuArr.push(qryMenu); 
	menuArr.push(editMenu); 


	// 终端模板修改
	var termModelForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 500,
		defaultType : 'textfield',
		labelWidth : 100,
		waitMsgTarget : true,
		items : [{
				xtype : 'dynamicCombo',
				methodName : 'getModelInfo',
				fieldLabel : '签购单模板*',
				hiddenName : 'misc1',
				id : 'mchnModelUpd',
				blankText : '模板不能为空',
				allowBlank : false,
				editable : true,
				emptyText : '请选择模板',
				width : 134			
			}]
	});


	// 终端模板修改窗口
	var termModelWin = new Ext.Window({
		title : '终端模板修改',
		initHidden : true,
		header : true,
		frame : true,
		modal : true,
		autoHeight : true,
		width : 400,
		layout : 'fit',
		items : [ termModelForm ],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'edit',
		closable : true,
		resizable : false,
		buttons : [ {
			text : '确定',
			handler : function() {
				//var modelId = termModelForm.getForm().findField('misc1').getValue();
				var modelId = Ext.getCmp('mchnModelUpd').getValue();
				
				//取多选记录方法 用逗号分开 后台处理
				var rows=termGridPanel.getSelectionModel().getSelections();//获取所有选中行
				var str = '';
				for(var i=0;i <rows.length;i++){
					str = str+rows[i].get('termId')+','+rows[i].get('recCrtTs')+';';
				}
				if (termModelForm.getForm().isValid()) {
					termModelForm.getForm().submit({
						url : 'MchtTmpInfoAction_termModelUpd.asp',
						waitMsg : '正在加载信息，请稍后......',
						params : {
							"str" : str,
							"modelId":modelId
						},
						method: 'POST',
						success : function(form, action) {
							showSuccessMsg(action.result.msg, termModelForm);
							termModelForm.getForm().reset();
							termModelWin.hide(termModelForm);
							termStore.reload();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg, termModelForm);
							termModelForm.getForm().reset();
							termModelWin.hide(termModelForm);
							termStore.reload();
						}
					});

				}
			}
		}, {
			text : '重置',
			handler : function() {
				termModelForm.getForm().reset();
			}
		} ]
	});
	
	
	// 终端信息列表
	var termGridPanel = new Ext.grid.GridPanel({
		title : '终端信息',
		iconCls : 'T301',
		region : 'center',
		frame : false,
		border : false,
		columnLines : true,
		stripeRows : true,
		store : termStore,
		sm : new Ext.grid.CheckboxSelectionModel({
			singleSelect : false
		}),
		cm : termColModel,
		tbar : [menuArr,'-',{
			xtype: 'button',
			text: '批量终端模板修改',
			name: 'modelUpd',
			id: 'modelUpd',
			iconCls: 'edit',
			disabled : true,
			width: 80,
			handler:function() {
				var rows=termGridPanel.getSelectionModel().getSelections();
				if(rows.length<=0){
					showAlertMsg("请至少选择一条记录！", termGridPanel);
				}else{
					termModelWin.show();
					termModelWin.center();
				}
			}
		}],
		loadMask : {
			msg : '正在加载终端信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : termStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : true,
			displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg : '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell,
		}
	});

	termGridPanel.getSelectionModel().on(
			{
				'rowselect' : function() {
					// 行高亮
					Ext.get(termGridPanel.getView().getRow(termGridPanel.getSelectionModel().last)).frame();
					// 根据商户状态判断哪个编辑按钮可用
					rec = termGridPanel.getSelectionModel().getSelected();
					termGridPanel.getTopToolbar().items.items[1].enable();
					termGridPanel.getTopToolbar().items.items[2].enable();
					Ext.getCmp('modelUpd').enable();
					// disabled : true,
				}
			});
	

	termStore.on('beforeload', function() {
		Ext.apply(this.baseParams, {
					start : 0,
					termId : Ext.getCmp('termNoQ').getValue(),
					productCd : Ext.getCmp('productCd_query').getValue(),
					termSta : Ext.getCmp('state').getValue(),
					startTime : topQueryPanel.getForm()
							.findField('startTime').getValue(),
					endTime : topQueryPanel.getForm()
							.findField('endTime').getValue(),
					mchnNo : Ext.getCmp('mchnNoQ').getValue(),
					termTp : Ext.getCmp('idtermtpsearch').getValue(),
					modelId : Ext.getCmp('queryMchtModelId').getValue()
				});
	});
	termStore.load();
	
	
	var mainUI = new Ext.Viewport({
		layout : 'border',
		renderTo : Ext.getBody(),
		items : [termGridPanel]
	});
});

function updateTmpDealInfo(termId,recCrtTs,grid){
	var termInfoStoreUpd = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'termIdUpd',mapping: 'id.termId'},
            {name: 'recCrtTsUpd',mapping: 'id.recCrtTs'},
            {name: 'mchnNoUpd',mapping: 'mchtCd'},
            {name: 'termMccUpd',mapping: 'termMcc'},
            {name: 'termBranchUpd',mapping: 'termBranch'},
            {name: 'termSignStaUpd',mapping: 'termSignSta'},
            {name: 'termFactoryUpd',mapping: 'termFactory'},
            {name: 'termMachTpUpd',mapping: 'termMachTp'},
            {name: 'termIdIdUpd',mapping: 'termIdId'},
            {name: 'termVerUpd',mapping: 'termVer'},
            {name: 'termTpUpd',mapping: 'termTp'},
            {name: 'contTelUpd',mapping: 'contTel'},
            {name: 'propTpUpd',mapping: 'propTp'},
            {name: 'propInsNmUpd',mapping: 'propInsNm'},
            {name: 'termBatchNmUpd',mapping: 'termBatchNm'},
            {name: 'termStlmDtUpd',mapping: 'termStlmDt'},
            {name: 'connectModeUpd',mapping: 'connectMode'},
            {name: 'financeCard1Upd',mapping: 'financeCard1'},
            {name: 'financeCard2Upd',mapping: 'financeCard2'},
            {name: 'financeCard3Upd',mapping: 'financeCard3'},
            {name: 'bindTel1Upd',mapping: 'bindTel1'},
            {name: 'bindTel2Upd',mapping: 'bindTel2'},
            {name: 'bindTel3Upd',mapping: 'bindTel3'},
            {name: 'termAddrUpd',mapping: 'termAddr'},
            {name: 'productCdDtl',mapping: 'productCd'},
            {name: 'termPlaceUpd',mapping: 'termPlace'},
            {name: 'oprNmUpd',mapping: 'oprNm'},
            {name: 'termParaUpd',mapping: 'termPara'},
            {name: 'termPara1Upd',mapping: 'termPara2'},
            {name: 'termPara11Upd',mapping: 'termPara1'},
            {name: 'keyDownSignUpd',mapping: 'keyDownSign'},
            {name: 'paramDownSignUpd',mapping: 'paramDownSign'},
            {name: 'icDownSignUpd',mapping: 'icDownSign'},
            {name: 'reserveFlag1Upd',mapping: 'reserveFlag1'},
            {name: 'misc2Upd',mapping: 'misc2'},
            {name: 'misc1',mapping: 'misc1'},
            {name: 'misc2',mapping: 'misc2'},
            {name: 'misc3',mapping: 'misc3'},
            {name: 'txn22Upd',mapping: 'txn22Dtl'},
            {name: 'txn27Upd',mapping: 'txn27Dtl'},
            {name: 'termSerialNum',mapping: 'termSerialNum'},
            {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
            {name: 'creditDayAmt',mapping: 'creditDayAmt'},
            {name: 'creditMonAmt',mapping: 'creditMonAmt'},
            {name: 'debitSingleAmt',mapping: 'debitSingleAmt'},
            {name: 'debitDayAmt',mapping: 'debitDayAmt'},
            {name: 'debitMonAmt',mapping: 'debitMonAmt'},
            {name: 'remark',mapping: 'remark'}
        ]),
        autoLoad: false
    });
	
	//终端型号数据集
	var storeTermModel = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data'
	});

	// 终端类型数据集
	var termTypeStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('TERM_TYPE', function(ret) {
				termTypeStore.loadData(Ext.decode(ret));
			});
	
	// 模板数据集
	var modelStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
	SelectOptionsDWR.getComboData('MCHNT_MODEL', function(ret) {
		modelStore.loadData(Ext.decode(ret));
	});
	
	
	/** ************** 终端修改布局 ************************ */
	var updTermPanel = new Ext.TabPanel({
		activeTab : 0,
		height : 430,
//		width: 610,
		defaults : {
			bodyStyle : 'padding-left: 10px'
		},
		frame : true,
		items : [{
			title : '基本信息',
			id : 'info1Upd',
			layout : 'column',
                frame: true,
                items: [{
                    columnWidth: 1, 
                    layout: 'form',
                    items: [{
	                    xtype: 'displayfield',
			            fieldLabel: '签购单名称',
			            id: 'txn22Upd',
			            width:350,
			            name: 'txn22Upd'
             }]
           },{
                    columnWidth: 1, 
                    layout: 'form',
                    hidden:true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '商户英文名称',
                        id: 'txn27Upd',
                        width:350,
                        name: 'txn27Upd'
                    }]
            },{
                    columnWidth: .5, 
                    layout: 'form',
                    hidden: true,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '终端MCC码',
                        id: 'termMccUpd',
                        name: 'termMccUpd'
                    }]
            },{
                columnWidth: .5,
                layout: 'form',
                items:[{
	                xtype: 'displayfield',
	                fieldLabel: '终端名称',
	                name: 'misc2',
	                id: 'misc2'
                }]
            },{
                 columnWidth: .5,
                 layout: 'form',
                 items:[{
                 	xtype: 'combofordispaly',
                    fieldLabel: '终端所属合作伙伴',
                    baseParams: 'BRH_BELOW_BRANCH',
                    hiddenName: 'termBranchUpd'
                 }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '联系电话',
                    id: 'contTelUpd',
                    name: 'contTelUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '产权属性',
                    baseParams: 'PROP_TP',
                    id:'idpropTpUpd',
                    hiddenName: 'propTpUpd'
                }]
            }
//            ,{
//				columnWidth : .5,
//				layout : 'form',
//				items : [{
//					xtype : 'displayfield',
//					fieldLabel : '产权属性*',
//					width : 134,
//					displayField: 'displayField',
//					valueField: 'valueField',
//					emptyText: '请选择产权属性',
//					blankText: '产权属性不能为空',
//					id : 'idpropTpUpd',
//					allowBlank : false,
//					hiddenName : 'propTpUpd',
//					store : new Ext.data.ArrayStore({
//								fields : ['valueField', 'displayField'],
//								data: [['0','钱宝所有'],['1','合作伙伴所有'],['2','商户自有']]
//							}),
//					listeners : {
//						'select' : function() {
//							
//							//Ext.getCmp("termPara1New").hide();
//							//Ext.getCmp("flagBox1").hide();
//						}
//					}
//				}]
//			}
            ,{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'combofordispaly',
                    fieldLabel: '收单服务机构',
                    baseParams: 'ORGAN',
                    id: 'idpropInsNmUpd',
                    hiddenName: 'propInsNmUpd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '第三方分成比例',
                    id: 'termPara1Upd',
                    name: 'termPara1Upd'
                }]
            },{
                columnWidth: .5,
                hidden: true,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '连接类型',
                    baseParams: 'CONNECT_MODE',
                    hiddenName: 'connectModeUpd'
                }]
            }
//            ,{
//				columnWidth : .5,
//				layout : 'form',
//				items : [{
//					xtype : 'combo',
//					fieldLabel : '连接类型*',
//					id : 'connectModeN',
//					hiddenName : 'connectModeUpd',
//					value : ' ',
//					store : new Ext.data.ArrayStore({
//								fields : ['valueField', 'displayField'],
//								data : [['J', '间联'], ['Z', '直联']]
//							}),
//					width : 146,
//					//readOnly : true
//				}]
//			}
            ,{
            	columnWidth : .5,
				layout : 'form',
				items : [{
 					xtype : 'dynamicCombo',
 					methodName : 'getModelInfo',
					fieldLabel : '签购单模板*',
					hiddenName : 'misc1',
					id : 'mchnModel',
					blankText : '模板不能为空',
					allowBlank : false,
					//store:modelStore,
					editable : true,
					emptyText : '请选择模板',
					width : 134			
				}]
			},{
                columnWidth: .5,
                layout: 'form',
                hidden: true,//false
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '保留字段3',
                    id: 'misc3',
                    name: 'misc3'
                }]
            },{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'combo',
					fieldLabel : '是否强制联机报道',
					id : 'misc3Flag2',
					hiddenName : 'misc3_2',
					allowBlank : false,
					value : '2',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [ [ '0', '不需要更新' ],
								[ '1', '建议更新' ],
								[ '2', '必须更新' ] ]
					}),
					width : 134
				} ]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [ {
					xtype : 'combo',
					fieldLabel : '是否强制下载主密钥',
					width : 134,
					value : '1',
					displayField : 'displayField',
					valueField : 'valueField',
					id : 'misc3Flag1',
					allowBlank : false,
					hiddenName : 'misc3_1',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField',
								'displayField' ],
						data : [[ '1', '未下载主密钥' ],
								[ '2', '已下载主密钥' ]]
					})
				} ]
			}
			]},
    //        ------------------------------------附加信息---------------------------------------------------------------------
             {
			title : '附加信息',
			id : 'info2Upd',
			layout : 'column',
                frame: true,
                items: [{
                    columnWidth: .5,
                    layout: 'form',
                    items:[{
                      xtype: 'combofordispaly',
                      fieldLabel: '终端类型',
                      baseParams: 'TERM_TYPE',
                      id: 'idtermTpUpd',
                      hiddenName: 'termTpUpd'
                    }]
                  }
//                {
//        				columnWidth : .5,
//        				layout : 'form',
//        				items : [{
//        					xtype : 'combo',
//        					fieldLabel : '终端类型*',
//        					id : 'idtermTpUpd',
//        					allowBlank : false,
//        					hiddenName : 'termTpUpd',
//        					store : termTypeStore,
//        					width : 150
//        				}]
//        			}
                ,{

                        columnWidth: .5,
                        layout: 'form',
                        hidden: true,
                        items:[{
                          xtype: 'displayfield',
                          fieldLabel: '终端序列号',
                          //baseParams: 'TERM_TYPE',
                          id: 'termSerialNum',
                          hiddenName: 'termSerialNum'
                        }]
                      
                    },{
                        id: 'accountBox4Upd',
                        hidden: true,
                        columnWidth: .5,
                        hidden: true,
                        layout: 'form',
                        items: [{
                            xtype: 'displayfield',
                            fieldLabel: '电话POS版本号',
                            id: 'termVersionUpd',
                            name: 'termVersionUpd'
                        }]
                    },{
                id: 'accountBox2Upd',
                hidden: true,
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号1',
                    id: 'financeCard1Upd',
                    name: 'financeCard1Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号2',
                    id: 'financeCard2Upd',
                    name: 'financeCard2Upd'
                },{
                    xtype: 'displayfield',
                    fieldLabel: '财务账号3',
                    id: 'financeCard3Upd',
                    name: 'financeCard3Upd'
                }]
            },{
                columnWidth: 1,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: '终端安装地址',
                    readOnly: true,
                    width:350,
                    id: 'termAddrUpd',
                    name: 'termAddrUpd'
                }]
            },{				
                columnWidth: .6,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
					fieldLabel : '终端SN号',
                    id: 'productCdDtl',
                    name: 'productCdDtl',
					maxLength : 50
                }]
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '所属厂商',
                    baseParams: 'TERM_FACTURE',
                    hiddenName: 'termFactoryUpd'
                }]
            
            },{

                columnWidth: .5,
                layout: 'form',
                items: [{
                	xtype: 'combofordispaly',
                    fieldLabel: '终端型号',
                   // baseParams: 'TERM_MODEL',
                    hiddenName: 'termMachTpUpd',
                    id:"termMachTpUpd",
                    store: storeTermModel,
                }]
            
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话1',
                    id: 'txn14Upd',
                    name: 'txn14Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话2',
                    id: 'txn15Upd',
                    name: 'txn15Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'displayfield',
                    fieldLabel: 'NAC 电话3',
                    id: 'txn16Upd',
                    name: 'txn16Upd'
                }]
            } ,{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话1',
                    hidden: true,
                    id: 'bindTel1Upd',
                    name: 'bindTel1Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话2',
                    hidden: true,
                    id: 'bindTel2Upd',
                    name: 'bindTel2Upd'
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                items: [{
                    xtype: 'displayfield',
//                    fieldLabel: '绑定电话3',
                    hidden: true,
                    id: 'bindTel3Upd',
                    name: 'bindTel3Upd'
                }]
            } ,{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'CA公钥下载标志',
                    id: 'keyDownSignUpd',
                    name: 'keyDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: '终端参数下载标志',
                    id: 'paramDownSignUpd',
                    name: 'paramDownSignUpd',
                    disabled: true
                }]
            },{
                columnWidth: .5,
                layout: 'form',
                hidden:true,
                items: [{
                    xtype: 'checkbox',
                    fieldLabel: 'IC卡参数下载标志',
                    id: 'icDownSignUpd',
                    name: 'icDownSignUpd',
                    disabled: true
                }]
            },{
				columnWidth : .5,
				layout : 'form',
				hidden: true,
				items : [{
					xtype : 'checkbox',
					fieldLabel : '绑定电话',
					id : 'reserveFlag1Upd',
					name : 'reserveFlag1Upd',
					inputValue : '1',
					disabled: true
				}]
			}]
         },
 // --------------------------交易信息TAB----------------------------
         {
			title : '交易信息',
			id : 'info3Upd',
			// layout: 'column',
			items : [{
						columnWidth : .9,
						layout : 'form',
						items : [{
									xtype : 'textfield',
									hidden : true,
									id:'isNullUpd3',
									width : 350
								}]
					},{
						xtype : 'fieldset',
						title : '交易权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [{
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款期数',
//												vtype : 'isNumber',
												id : 'txn35Upd',
//												maxLength : 2,
												name : 'txn35Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '分期付款限额',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn36Upd',
												name : 'txn36Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '消费单笔上限 ',
//												vtype : 'isMoney',
//												maxLength : 12,
												id : 'txn37Upd',
												name : 'txn37Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货单笔上限',
//												vtype : 'isMoney',
												id : 'txn38Upd',
//												maxLength : 12,
												name : 'txn38Upd'
											}]
								}, {
									columnWidth : .5,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '转帐单笔上限',
//												vtype : 'isMoney',
												id : 'txn39Upd',
//												maxLength : 12,
												name : 'txn39Upd'
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'textfield',
												fieldLabel : '退货时间跨度',
//												vtype : 'isNumber',
												id : 'txn40Upd',
//												maxLength : 2,
												name : 'txn40Upd',
												value : 30
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												disabled: true,
												fieldLabel : '查询 ',
												id : 'param1Upd',
												name : 'param1Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费 ',
												disabled: true,
												id : 'param6Upd',
												name : 'param6Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '消费撤销 ',
												id : 'param7Upd',
												name : 'param7Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权 ',
												disabled: true,
												id : 'param2Upd',
												name : 'param2Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成 ',
												id : 'param4Upd',
												name : 'param4Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权撤销 ',
												id : 'param3Upd',
												name : 'param3Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预授权完成撤销 ',
												id : 'param5Upd',
												name : 'param5Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '退货 ',
												disabled: true,
												id : 'param8Upd',
												name : 'param8Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准退货 ',
												id : 'termPara1P19Upd',
												name : 'termPara1P19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '离线结算 ',
												id : 'param9Upd',
												name : 'param9Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '结算调整 ',
												id : 'param10Upd',
												name : 'param10Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									hidden : true,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '公司卡转个人卡（财务POS） ',
												id : 'param11Upd',
												name : 'param11Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '个人卡转公司卡（财务POS） ',
												id : 'param12Upd',
												name : 'param12Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '卡卡转帐',
												id : 'param13Upd',
												name : 'param13Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '上笔交易查询（财务POS） ',
												id : 'param14Upd',
												name : 'param14Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '交易查询（财务POS） ',
												id : 'param15Upd',
												name : 'param15Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '定向汇款 ',
												id : 'param16Upd',
												name : 'param16Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款 ',
												id : 'param17Upd',
												name : 'param17Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '分期付款撤销 ',
												id : 'param18Upd',
												name : 'param18Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '代缴费 ',
												id : 'param19Upd',
												name : 'param19Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '电子现金 ',
												id : 'param20Upd',
												name : 'param20Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : 'IC现金充值 ',
												id : 'param21Upd',
												name : 'param21Upd',
												disabled : true,
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '指定账户圈存',
												id : 'param22Upd',
												name : 'param22Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非指定账户圈存 ',
												id : 'param23Upd',
												name : 'param23Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									hidden : true,
									items : [{
												xtype : 'checkbox',
												fieldLabel : '非接快速支付 ',
												id : 'param24Upd',
												name : 'param24Upd',
												inputValue : '1'
											}]
								}, {
									columnWidth : .3,
									layout : 'form',
									items : [{
												xtype : 'hidden',
												id : 'termParaUpd',
												name : 'termParaUpd'
											}, {
												xtype : 'hidden',
												id : 'termPara11Upd',
												name : 'termPara11Upd'
											}, {
												xtype : 'hidden',
												id : 'misc2Upd',
												name : 'misc2Upd'
											}]
								}]
					}, {
						xtype : 'fieldset',
						title : '卡类型权限',
						layout : 'column',
						// collapsible : true,
						labelWidth : 70,
						width : 570,
						defaults : {
							bodyStyle : 'padding-left: 10px'
						},
						items : [

						{
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '借记卡 ',
												disabled: true,
												id : 'param1Upd1',
												name : 'param1Upd1',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '贷记卡 ',
												disabled: true,
												id : 'param1Upd2',
												name : 'param1Upd2',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '准贷记卡 ',
												disabled: true,
												id : 'param1Upd3',
												name : 'param1Upd3',
												inputValue : '1'
											}]
								}, {
									columnWidth : .33,
									layout : 'form',
									items : [{
												xtype : 'checkbox',
												fieldLabel : '预付费卡 ',
												disabled: true,
												id : 'param1Upd4',
												name : 'param1Upd4',
												inputValue : '1'
											}]
								}

						]
					},{

		    			id : 'riskParamInfoDtl',
		    			xtype : 'fieldset',
		    			title : '终端限额信息<font color="red">（0表示不限额）</font>',
		    			layout : 'column',
		    			labelWidth : 90,
		    			items : [{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '月累计借记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'debitMonAmtDtl',
		    					name : 'debitMonAmt',
		    		            width : 200
		    				}]
		    			},{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'creditMonAmtDtl',
		    					name : 'creditMonAmt',
		    		            width : 200
		    				}]
		    			},{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'debitDayAmtDtl',
		    					name : 'debitDayAmt',
		    		            width : 200
		    				}]
		    			},{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'creditDayAmtDtl',
		    					name : 'creditDayAmt',
		    		            width : 200
		    				}]
		    			},{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'debitSingleAmtDtl',
		    					name : 'debitSingleAmt',
		    		            width : 200
		    				}]
		    			},{
		    				columnWidth : .5,
		    				layout : 'form',
		    				items : [{
		    					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
		    					xtype: 'displayfield',
		    					id : 'creditSingleAmtDtl',
		    					name :'creditSingleAmt',
		    		            width : 200
		    				}]
		    			},{
		     				columnWidth : .9,
		    				layout : 'form',
		    				items : [{
			    				xtype: 'displayfield',
								fieldLabel: '备注',
								width: 480,
	//							vtype: 'length100B',
	//							labelStyle: 'padding-left: 10px',
								id: 'remark',
								name: 'remark'
		    				}]
		    			}]
					}

			]
		}]
	});
	
	/******************** 终端修改表单 ******************** */
	var updTermForm = new Ext.form.FormPanel({
			frame : true,
			height : 480,
			labelWidth : 120,
			waitMsgTarget : true,
			layout : 'form',
			items : [{
	             xtype: 'displayfield',
	             fieldLabel: '终端号',
	             labelStyle: 'padding-left: 10px',
	             name: 'termIdUpd',
	             id: 'termIdUpd'
	         },{
	             xtype: 'displayfield',
	             fieldLabel: '商户编号',
	             labelStyle: 'padding-left: 10px',
	             name: 'mchnNoUpd',
	             id: 'idmchnNoUpd'
	           },updTermPanel]
		});
	
    function praseTermParamUpd(val) {
//        var array = val.split("|");
//        if(array[4] == undefined)
//        	return 0;
//        array[4] = array[4].substring(2,array[4].length);
//        T30101.getTermParam(array[4],function(ret){
//           var termParam = ret;
//	        updTermForm.getForm().findField("txn14Upd").setValue(array[0].substring(2,array[0].length).trim());
//	        updTermForm.getForm().findField("txn15Upd").setValue(array[1].substring(2,array[1].length).trim());
//	        updTermForm.getForm().findField("txn16Upd").setValue(array[2].substring(2,array[2].length).trim());
//	
	        var value = Ext.getCmp('idtermTpUpd').getValue();
	        //判断终端类型
	        if(value == '3'){//固话POS
	        	updTermForm.get('info3Upd').setDisabled(true);  //交易信息tab
	        	//显示电话pos版本号
	        	Ext.getCmp('accountBox4Upd').show();  
	        	updTermForm.getForm().findField("termVersionUpd").setValue(Ext.getCmp('misc2Upd').getValue().substring(0,4));
	        }
//        });
    };
     function praseTermParam1(val)
    {
    	 //卡类型
    	 updTermForm.getForm().findField("param1Upd1").setValue(val.substring(0,1).trim());
    	 updTermForm.getForm().findField("param1Upd2").setValue(val.substring(1,2).trim());
    	 updTermForm.getForm().findField("param1Upd3").setValue(val.substring(2,3).trim());
    	 updTermForm.getForm().findField("param1Upd4").setValue(val.substring(3,4));
         //交易权限
    	 updTermForm.getForm().findField("param1Upd").setValue(val.substring(10,11).trim());
    	 updTermForm.getForm().findField("param6Upd").setValue(val.substring(11,12).trim());
    	 updTermForm.getForm().findField("param2Upd").setValue(val.substring(13,14));
    	 updTermForm.getForm().findField("param8Upd").setValue(val.substring(17,18));
    }
     
     /** ***************** 终端修改信息 ******************** */
 	var updTermWin = new Ext.Window({
 		title : '终端修改',
 		initHidden : true,
 		header : true,
 		frame : true,
 		closable : false,
 		modal : true,
 		width : 640,
 		autoHeight : true,
 		layout : 'fit',
 		items : [updTermForm],
 		buttonAlign : 'center',
 		closeAction : 'hide',
 		iconCls : 'logo',
 		resizable : false,
 		buttons : [{
 			text : '确定',
 			handler : function() {
 				updTermPanel.setActiveTab("info3Upd");
 				//updTermPanel.setActiveTab("info3Upd");
 				if (updTermForm.getForm().isValid()) {
 					updTermForm.getForm().submitNeedAuthorise({
 								url : 'MchtTmpInfoAction_termUpdate.asp',
 								waitMsg : '正在提交，请稍后......',
 								success : function(form, action) {
 									showSuccessMsg(action.result.msg,grid);
 									grid.getStore().reload();
 									updTermForm.getForm().reset();
 									grid.getTopToolbar().items.items[2].disable();
 									grid.getTopToolbar().items.items[1].disable();
 									updTermWin.close();
 								},
 								failure : function(form, action) {
 									updTermPanel.setActiveTab('info3Upd');
 									showErrorMsg(action.result.msg||'信息保存失败！', updTermForm);
 								},
 								params : {
 									txnId : '30101',
 									subTxnId : '02',
 									termIdUpd : termId,
									recCrtTs : recCrtTs,
									mchnNoUpd: Ext.getCmp("idmchnNoUpd").getValue(),
									termTpUpdForInit: Ext.getCmp('idtermTpUpd').getValue(),
									misc3: Ext.getCmp("misc3").getValue()
 								}
 							});
 				} else {
 					var finded = true;
 					updTermForm.getForm().items.each(function(f) {
 								if (finded && !f.validate()) {
 									var tab = f.ownerCt.ownerCt.id;
 									if (tab == 'info1Upd' || tab == 'info2Upd'
 											|| tab == 'info3Upd') {
 										updTermPanel.setActiveTab(tab);
 									}
 									finded = false;
 								}
 							});
 				}
 			}
 		}, {
 			text : '关闭',
 			handler : function() {
 				updTermWin.close();
 			}
 		}]
 	});
	/******************** 数据加载 ******************** */
	termInfoStoreUpd.load({
	    params: {
	         storeId: 'getTermInfo',
	         termId: termId,
	         recCrtTs: recCrtTs
	    },
	    callback: function(records, options, success){
	        if(success){
	        	storeTermModel.removeAll();
				var tfId = termInfoStoreUpd.getAt(0).data.termFactoryUpd;
				SelectOptionsDWR.getComboDataWithParameter('TERM_MODEL',tfId,function(ret){
					storeTermModel.loadData(Ext.decode(ret));
					Ext.getCmp('termMachTpUpd').setValue(termInfoStoreUpd.getAt(0).data.termMachTpUpd);
				});
	        	updTermForm.getForm().loadRecord(records[0]);

				var misc3Value = Ext.getCmp('misc3').getValue();
				Ext.getCmp('misc3Flag1').setValue(misc3Value.substring(0,1));
				Ext.getCmp('misc3Flag2').setValue(misc3Value.substring(1,2));
				
	        	//updTermForm.getForm().loadRecord(termInfoStoreUpd.getAt(0));
	        	var termPara = updTermForm.getForm().findField("termParaUpd").value;
	        	praseTermParamUpd(termPara);
	        	praseTermParam1(updTermForm.getForm().findField("termPara11Upd").value);
	        	//updTermPanel.setActiveTab('info3Upd');
	        	updTermWin.show();
	        	updTermWin.center();
	        }else{
	        	updTermWin.close();
	        	alert("载入终端信息失败，请稍后再试!");
	        }
	    }
	});
}