Ext.onReady(function() {
	var selectedRecord ;

	// 直联商户
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('CUP_MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });     
    
    //界面显示
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=termCupTmpAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'termId',mapping: 'termId'},
			{name: 'termTp',mapping: 'termTp'},
			{name: 'mchntNo',mapping: 'mchntNo'},
			{name: 'termSta',mapping: 'termSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	
	var termColModel = new Ext.grid.ColumnModel([
		{id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
		{header: '终端类型',dataIndex: 'termTp',renderer:termCupType},
		{header: '所属商户',dataIndex: 'mchntNo',width: 280,id:'mchntNo'},
		{header: '终端状态',dataIndex: 'termSta',width: 150,renderer: termCupSta},
		{header: '入网日期',dataIndex: 'recCrtTs',width: 140}
	]);
	
	
	
	
	var termInfoStoreEdt = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'term_idE',mapping: 'term_id'},
            {name: 'mcht_noE',mapping: 'mcht_no'},
            {name: 'in_inst_idE',mapping: 'in_inst_id'},
            {name: 'inst_idE',mapping: 'inst_id'},
            {name: 'term_typeE',mapping: 'term_type'},
            {name: 'term_stateE',mapping: 'term_state'},
            {name: 'out_flagE',mapping: 'out_flag'},
            {name: 'IC_flagE',mapping: 'IC_flag'},
            {name: 'call_typeE',mapping: 'call_type'},
            {name: 'currcy_code_transE',mapping: 'currcy_code_trans'},
            {name: 'key_indexE',mapping: 'key_index'},
            {name: 'enc_key_modeE',mapping: 'enc_key_mode'},
            {name: 'trans_key_modeE',mapping: 'trans_key_mode'},
            {name: 'term_enc_typeE',mapping: 'term_enc_type'},
            {name: 'term_enc_key_1E',mapping: 'term_enc_key_1'},
            {name: 'term_trans_key_1E',mapping: 'term_trans_key_1'},
            {name: 'open_timeE',mapping: 'open_time'},
            {name: 'close_timeE',mapping: 'close_time'},
            {name: 'para_down_flagE',mapping: 'para_down_flag'},
            {name: 'tms_down_flagE',mapping: 'tms_down_flag'},
            {name: 'pub_key_flagE',mapping: 'pub_key_flag'},
            {name: 'IC_para_flagE',mapping: 'IC_para_flag'},
            {name: 'emv_flagE',mapping: 'emv_flag'},
            {name: 'key_lengthE',mapping: 'key_length'},
            {name: 'pik_lengthE',mapping: 'pik_length'},
            {name: 'mak_lengthE',mapping: 'mak_length'},
            {name: 'mac_algE',mapping: 'mac_alg'},
            {name: 'country_codeE',mapping: 'country_code'},
            {name: 'connect_modeE',mapping: 'connect_mode'},
            {name: 'track_key_lenE',mapping: 'track_key_len'},
            {name: 'term_shareE',mapping: 'term_share'},
            {name: 'term_call_numE',mapping: 'term_call_num'},
            {name: 'country_codeE',mapping: 'country_code'},
            {name: 'addressE',mapping: 'address'},
            {name: 'nameE',mapping: 'name'},
            {name: 'tel_phoneE',mapping: 'tel_phone'}
        ]),
        autoLoad: false
    });
	
	
	var addMenu = {
		text: '添加',
		width: 85,
		iconCls: 'add',
		handler:function() {
//			termPanel.setActiveTab(0);
			termWin.show();
			termWin.center();
		}
	};

  
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
		
            selectedRecord = grid.getSelectionModel().getSelected();
            if(selectedRecord == null){
                showAlertMsg("没有选择记录",grid);
                return;
            }    
            termInfoStoreEdt.load({
                params: {
                     storeId: 'getTermCupTmp',
                     termId: selectedRecord.get('termId')
                },
                callback: function(records, options, success){
                    if(success){
                       updTermForm.getForm().loadRecord(records[0]);
                       updTermPanel.setActiveTab(0);
                       updTermWin.show();
                    }else{
                       updTermWin.hide();
                    }
                }
            });
			//        TermCupInfoEdit(selectedRecord.get('termId'));
		}
	};

	var stopMenu = {
		text: '冻结',
		width: 85,
		iconCls: 'stop',
		disabled: true,
		handler:function() {
			showConfirm('确定冻结吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30401Action.asp?method=stop',
						params: {
							term_id: grid.getSelectionModel().getSelected().get('termId'),
							txnId: '30401',
							subTxnId: '03'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载商户信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	
	var recoverMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30401Action.asp?method=recover',
						params: {
							term_id: grid.getSelectionModel().getSelected().get('termId'),
							txnId: '30401',
							subTxnId: '04'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载商户信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
				}
			});
		}
	};
	
	var delMenu = {
			text: '注销',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('注销商户信息后不可恢复,确定注销吗？',grid,function(bt) {
					if(bt == 'yes') {
						
						showProcessMsg('正在提交信息，请稍后......');
						Ext.Ajax.requestNeedAuthorise({
							url: 'T30401Action.asp?method=del',
							params: {
								term_id: grid.getSelectionModel().getSelected().get('termId'),
								txnId: '30401',
								subTxnId: '05'
							},
							success: function(rsp,opt) {
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
								// 重新加载商户信息
								grid.getStore().reload();
							}
						});
						hideProcessMsg();
					}
				});
			}
	};
     
    var qryMenu = {
            text: '详细信息',
            width: 85,
            iconCls: 'detail',
            disabled: true,
            handler:function() {
                var selectedRecord = grid.getSelectionModel().getSelected();
                if(selectedRecord == null)
                {
                    showAlertMsg("没有选择记录",grid);
                    return;
                }
//                bt.disable();
//    			setTimeout(function(){bt.enable();},2000);
                selectTermCupInfo(selectedRecord.get('termId'));	
            }
        };
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	
    var report = {
			text: '导出直联终端信息',
			width: 160,
			id: 'report',
			iconCls: 'download',
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",grid);
				Ext.Ajax.requestNeedAuthorise({
					url: 'T3040101Action.asp',
					params: {
						termId: Ext.getCmp('termNoQ').getValue(),
			            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
			            termTp: Ext.getCmp('termTypeQ').getValue(),
			            termSta: Ext.getCmp('stateQ').getValue(),
			            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
			            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
						txnId: '3040101',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						hideMask();
					}
				});
			}
		}
	
	var menuArr = new Array();
	
	menuArr.push(addMenu);		//[0]新增
	menuArr.push('-'); 			//[1]
	menuArr.push(editMenu);		//[2]修改
	menuArr.push('-'); 		//[3]
    menuArr.push(stopMenu);      //[4]冻结
    menuArr.push('-'); 		//[5]
	menuArr.push(recoverMenu);	//[6]恢复
	menuArr.push('-'); 		//[7]
    menuArr.push(delMenu);      //[8]注销
    menuArr.push('-'); 		//[9]
    menuArr.push(qryMenu);      //[10]详细
    menuArr.push('-'); 		//[11]
	menuArr.push(queryMenu);	//[12]查询
	
	
	
	var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getTermCupNo',
                fieldLabel: '终端编号',
                hiddenName: 'termNo',
                id: 'termNoQ',
                editable: true,
                anchor: '90%'
           },{
	        	xtype: 'dynamicCombo',
	        	methodName: 'getMchntCupIdAll',
                fieldLabel: '商户编号',
                hiddenName: 'mchnNo',
                id: 'mchnNoQ',
                editable: true,
                anchor: '90%'
           },{                                         
            xtype: 'combo',
			id:'stateQ',
			hiddenName: 'state',
			fieldLabel: '终端状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['1','正常'],['9','新增待审核'],['8','新增拒绝'],['0','注销'],['2','冻结'],['7','修改待审核'],
					   ['6','修改拒绝'],['5','冻结待审核'],['3','注销待审核'],['H','恢复待审核']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'TERM_CUP_TYPE',
			fieldLabel: '终端类型',
			id: 'termTypeQ',
			hiddenName: 'termType',
			anchor: '70%'
		},{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor: '70%'      
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
              anchor: '70%'       
        }],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                termStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel],
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
                queryWin.collapse();
                queryWin.getEl().pause(1);
                queryWin.setPosition(10,Ext.getBody().getViewSize().height - 30);
            },
            qtip: '最小化',
            hidden: false
        },{
            id: 'maximize',
            handler: function(event,toolEl,panel,tc) {
                panel.tools.minimize.show();
                toolEl.hide();
                queryWin.expand();
                queryWin.center();
            },
            qtip: '恢复',
            hidden: true
        }]
    });
    
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端信息维护',
		iconCls: 'T301',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	grid.getStore().on('beforeload',function() {
		grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[4].disable();
		grid.getTopToolbar().items.items[6].disable();
		grid.getTopToolbar().items.items[8].disable();
		grid.getTopToolbar().items.items[10].disable();
	});
	
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelected();
			
			if(rec.get('termSta') == '1' || rec.get('termSta') == '9' || rec.get('termSta') == '8' || rec.get('termSta') == '7' || rec.get('termSta') == '6') {
				grid.getTopToolbar().items.items[2].enable();
			} else {
				grid.getTopToolbar().items.items[2].disable();
			}
			if(rec.get('termSta') == '1') {//正常可冻结和查看详细
				grid.getTopToolbar().items.items[4].enable();
			} else {
				grid.getTopToolbar().items.items[4].disable();
			}
			if(rec.get('termSta') == '2') {//冻结时才能恢复
				grid.getTopToolbar().items.items[6].enable();
			} else {
				grid.getTopToolbar().items.items[6].disable();
			}
			if(rec.get('termSta') == '1'||rec.get('termSta') == '2') {//正常和冻结时可以注销
				grid.getTopToolbar().items.items[8].enable();
			} else {
				grid.getTopToolbar().items.items[8].disable();
			}
			grid.getTopToolbar().items.items[10].enable();
		}
	});
    

	//新增**************************************************
    var termPanel = new Ext.TabPanel({
        width: 780,
        frame: true,
        plain: false,
        activeTab: 0,
        height: 500,
        deferredRender: false,
        enableTabScroll: true,
        labelWidth: 150,
        items: [{
                title: '基本信息',
                id: 'basic',
                layout: 'column',
                items: [{
	        		columnWidth: .99,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
			        	xtype: 'combo',
			        	store: mchntStore,
	                    displayField: 'displayField',
	                    valueField: 'valueField',
	                    mode: 'local',
	                    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户号*',
						id: 'idmcht_no',
						hiddenName: 'mcht_no',
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单机构*',
						readOnly:true,
						id: 'in_inst_idI',
						hiddenName: 'in_inst_id',
						allowBlank: false,
						anchor: '90%'
					}]
			    },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-人民币']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '默认交易货币*',
						id: 'currcy_code_transI',
						hiddenName: 'currcy_code_trans',
						readOnly:true,
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-中国']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '国家代码*',
						readOnly:true,
						id: 'country_codeI',
						hiddenName: 'country_code',
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
					    baseParams: 'CUP_CONN_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联接方式*',
						readOnly:true,
						id: 'connect_modeI',
						hiddenName: 'connect_mode',
						allowBlank: false,
						anchor: '90%',
						value: 'P'
			        }]
			     },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_STATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端状态*',
						readOnly:true,
						id: 'term_stateI',
						hiddenName: 'term_state',
						allowBlank: false,
						anchor: '90%',
						value: '9'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端类型*',
						id: 'term_typeI',
						hiddenName: 'term_type',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '签约',
                layout: 'column',
                id: 'sign',
                items: [{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否开启外卡机构收单*',
	                        id: 'out_flagI',
	                        hiddenName: 'out_flag',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否支持IC卡*',
	                        id: 'IC_flagI',
	                        hiddenName: 'IC_flag',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '开通时间',
						format : 'Ymd',
						id: 'open_time',
						name: 'open_time',
						anchor: '90%'
					}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '注销时间',
						format : 'Ymd',
						id: 'close_time',
						name: 'close_time',
						anchor: '90%'
					}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CALL_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '拨入类型*',
						id: 'call_typeI',
						hiddenName: 'call_type',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端分润方式*',
						id: 'term_share',
						name: 'term_share',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端正常拨入号码*',
						id: 'term_call_num',
						name: 'term_call_num',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地址*',
						id: 'address',
						name: 'address',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人*',
						id: 'name',
						name: 'name',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系电话*',
						id: 'tel_phone',
						name: 'tel_phone',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '参数配置',
                id: 'paragram',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_KEY_INDEX',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '当前密钥信息索引*',
						id: 'key_indexI',
						hiddenName: 'key_index',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_KEY_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥方式*',
						id: 'enc_key_modeI',
						hiddenName: 'enc_key_mode',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['00','手工传输'],['01','ftp文件'],
								   ['02','自定义']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥方式*',
						id: 'trans_key_modeI',
						hiddenName: 'trans_key_mode',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','DES'],['3','3DES']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密类型*',
						id: 'term_enc_typeI',
						hiddenName: 'term_enc_type',
						allowBlank: false,
						value:'3',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥1*',
						id: 'term_enc_key_1',
						name: 'term_enc_key_1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥1*',
						id: 'term_trans_key_1I',
						name: 'term_trans_key_1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '参数下载标识*',
						id: 'para_down_flagI',
						hiddenName: 'para_down_flag',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'TMS参数下载标识*',
						id: 'tms_down_flagI',
						hiddenName: 'tms_down_flag',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公钥下载标识*',
						id: 'pub_key_flagI',
						hiddenName: 'pub_key_flag',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'IC参数下载标识*',
						id: 'IC_para_flagI',
						hiddenName: 'IC_para_flag',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMV参数下载标识*',
						id: 'emv_flagI',
						hiddenName: 'emv_flag',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端主密钥长度*',
						id: 'key_lengthI',
						hiddenName: 'key_length',
						allowBlank: false,
						value:'02',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'PIK长度*',
						id: 'pik_lengthI',
						hiddenName: 'pik_length',
						allowBlank: false,
						value:'02',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAK长度*',
						id: 'mak_lengthI',
						hiddenName: 'mak_length',
						allowBlank: false,
						value:'01',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','ANSI9.9'],['02','ANSI9.19'],['03','XOR']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAC算法*',
						id: 'mac_algI',
						hiddenName: 'mac_alg',
						allowBlank: false,
						value:'01',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '磁道加密密钥长度*',
						id: 'track_key_lenI',
						hiddenName: 'track_key_len',
						anchor: '90%'
			    	}]
				}]
            }]
    });
     
    //外部加入监听
    Ext.getCmp("idmcht_no").on('select',function(){
    	T30401.getMchnt(Ext.getCmp("idmcht_no").getValue(),function(ret){
    		if(ret=='0'){
    				showErrorMsg("找不到相应商户",grid);
    				termForm.getForm().reset();
    				return;
    		}
    		
            var mchntInfo = Ext.decode(ret.substring(1,ret.length-1));
            Ext.getCmp("in_inst_idI").setValue(mchntInfo.inner_acq_inst_id);
  
        });
    });
     
    /**************  终端新增表单  *********************/
	var termForm = new Ext.form.FormPanel({
		frame: true,
		height: 450,
		width: 800,
		labelWidth: 100,
		waitMsgTarget: true,
		layout: 'column',
		items: [termPanel]
	});
   
    /***********  终端信息窗口  *****************/
	var termWin = new Ext.Window({
		title: '终端添加',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 800,
		height: 450,
		layout: 'fit',
		items: [termForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				
//				termPanel.setActiveTab("basic");
//				termPanel.setActiveTab("sign"); 
//				termPanel.setActiveTab("paragram"); 
				if(termForm.getForm().isValid()) {
					
					termForm.getForm().submitNeedAuthorise({
						url: 'T30401Action.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							showSuccessDtl(action.result.msg,termWin);
							//重新加载参数列表
							grid.getStore().reload();
							//重置表单
							termForm.getForm().reset();
                            termWin.hide();
						},
						failure: function(form,action) {
//							termPanel.setActiveTab('paragram');
							showErrorMsg(action.result.msg,termWin);
						},
						params: {
							method: 'add',
							txnId: '30401',
							subTxnId: '01'
						}
					});
				}else {
                    var finded = true;
	                termForm.getForm().items.each(function(f){
	                    if(finded && !f.validate()){
	                        var tab = f.ownerCt.ownerCt.id;
	                        if(tab == 'basic' || tab == 'sign' || tab == 'paragram' ){
	                             termPanel.setActiveTab(tab);
	                        }
	                        finded = false;
	                    }
	                });
                }
			}
		},{
			text: '重置',
			handler: function() {
				termForm.getForm().reset();
			}
		},{
			text: '关闭',
			handler: function() {
				termWin.hide();
			}
		}]
	});
	
	//修改************************************************************************************************
	//****************************************************************************************************
	var updTermPanel = new Ext.TabPanel({
		id: 'tabEdt',
    	frame: true,
        plain: false,
        activeTab: 0,
        height: 450,
        width: 760,
        deferredRender: false,
        enableTabScroll: true,
        labelWidth: 150,
        items: [{
                title: '基本信息',
                id: 'basicE',
                layout: 'column',
                items: [{
	        		columnWidth: .99,
		        	layout: 'form',
		        	xtype: 'panel',
		       		items: [{
			        	xtype: 'combo',
			        	store: mchntStore,
	                    displayField: 'displayField',
	                    valueField: 'valueField',
	                    allowBlank: false,
						labelStyle: 'padding-left: 5px',
						fieldLabel: '商户号*',
						readOnly:true,
						id: 'idmcht_noE',
						hiddenName: 'mcht_noE',
						allowBlank: false,
						anchor: '90%'
		        	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CUP_BRH_S',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '收单机构*',
						readOnly:true,
						id: 'in_inst_idIE',
						hiddenName: 'in_inst_idE',
						allowBlank: false,
						anchor: '90%'
					}]
			    },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-人民币']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '默认交易货币*',
						id: 'currcy_code_transIE',
						hiddenName: 'currcy_code_transE',
						readOnly:true,
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['156','156-中国']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '国家代码*',
						readOnly:true,
						id: 'country_codeIE',
						hiddenName: 'country_codeE',
						allowBlank: false,
						anchor: '90%',
						value: '156'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
					    baseParams: 'CUP_CONN_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联接方式*',
						readOnly:true,
						id: 'connect_modeIE',
						hiddenName: 'connect_modeE',
						allowBlank: false,
						anchor: '90%',
						value: 'P'
			        }]
			     },{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_STATE_CUP',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端状态*',
						readOnly:true,
						id: 'term_stateIE',
						hiddenName: 'term_stateE',
						allowBlank: false,
						anchor: '90%',
						value: '9'
			        }]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端类型*',
						id: 'term_typeIE',
						hiddenName: 'term_typeE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '签约',
                layout: 'column',
                id: 'signE',
                items: [{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否开启外卡机构收单*',
	                        id: 'out_flagIE',
	                        hiddenName: 'out_flagE',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
                	xtype: 'panel',
	                layout: 'form',
	                items: [{
	                        xtype: 'combo',
							store: new Ext.data.ArrayStore({
								fields: ['valueField','displayField'],
								data: [['0','否'],['1','是']],
								reader: new Ext.data.ArrayReader()
							}),
	                        labelStyle: 'padding-left: 5px',
	                        fieldLabel: '是否支持IC卡*',
	                        id: 'IC_flagIE',
	                        hiddenName: 'IC_flagE',
	                        value:'0',
	                        anchor: '90%'
	                    }]
                },{
                	columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '开通时间',
						format : 'Ymd',
						id: 'open_timeE',
						name: 'open_timeE',
						anchor: '90%'
					}]
				},{
					columnWidth: .5,
		        	xtype: 'panel',
		        	layout: 'form',
	       			items: [{
	       				xtype: 'datefield',
	       				labelStyle: 'padding-left: 5px',
						fieldLabel: '注销时间',
						format : 'Ymd',
						id: 'close_timeE',
						name: 'close_timeE',
						anchor: '90%'
					}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'CALL_TYPE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '拨入类型*',
						id: 'call_typeIE',
						hiddenName: 'call_typeE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端分润方式*',
						id: 'term_shareE',
						name: 'term_shareE',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端正常拨入号码*',
						id: 'term_call_numE',
						name: 'term_call_numE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '地址*',
						id: 'addressE',
						name: 'addressE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系人*',
						id: 'nameE',
						name: 'nameE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '联系电话*',
						id: 'tel_phoneE',
						name: 'tel_phoneE',
						allowBlank: false,
						anchor: '90%'
			    	}]
				}]
            },{
                title: '参数配置',
                id: 'paragramE',
                layout: 'column',
                items: [{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_CUP_KEY_INDEX',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '当前密钥信息索引*',
						id: 'key_indexIE',
						hiddenName: 'key_indexE',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'basecomboselect',
				        baseParams: 'TERM_KEY_MODE',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥方式*',
						id: 'enc_key_modeIE',
						hiddenName: 'enc_key_modeE',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['00','手工传输'],['01','ftp文件'],
								   ['02','自定义']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥方式*',
						id: 'trans_key_modeIE',
						hiddenName: 'trans_key_modeE',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','DES'],['3','3DES']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密类型*',
						id: 'term_enc_typeIE',
						hiddenName: 'term_enc_typeE',
						allowBlank: false,
						value:'3',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端加密密钥1*',
						id: 'term_enc_key_1E',
						name: 'term_enc_key_1E',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'textfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端传输密钥1*',
						id: 'term_trans_key_1IE',
						name: 'term_trans_key_1E',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '参数下载标识*',
						id: 'para_down_flagIE',
						hiddenName: 'para_down_flagE',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'TMS参数下载标识*',
						id: 'tms_down_flagIE',
						hiddenName: 'tms_down_flagE',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '公钥下载标识*',
						id: 'pub_key_flagIE',
						hiddenName: 'pub_key_flagE',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'IC参数下载标识*',
						id: 'IC_para_flagIE',
						hiddenName: 'IC_para_flagE',
						value:'1',
						allowBlank: false,
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','否'],['1','是']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'EMV参数下载标识*',
						id: 'emv_flagIE',
						hiddenName: 'emv_flagE',
						allowBlank: false,
						value:'1',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '终端主密钥长度*',
						id: 'key_lengthIE',
						hiddenName: 'key_lengthE',
						allowBlank: false,
						value:'02',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'PIK长度*',
						id: 'pik_lengthIE',
						hiddenName: 'pik_lengthE',
						allowBlank: false,
						value:'02',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAK长度*',
						id: 'mak_lengthIE',
						hiddenName: 'mak_lengthE',
						allowBlank: false,
						value:'01',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','ANSI9.9'],['02','ANSI9.19'],['03','XOR']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: 'MAC算法*',
						id: 'mac_algIE',
						hiddenName: 'mac_algE',
						allowBlank: false,
						value:'01',
						anchor: '90%'
			    	}]
				},{
	        		columnWidth: .5,
	            	layout: 'form',
	            	xtype: 'panel',
	        		items: [{
				        xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['01','单倍长'],['02','双倍长'],['03','三倍长']],
							reader: new Ext.data.ArrayReader()
						}),
						labelStyle: 'padding-left: 5px',
						fieldLabel: '磁道加密密钥长度*',
						id: 'track_key_lenIE',
						hiddenName: 'track_key_lenE',
						anchor: '90%'
			    	}]
				}]
            }]
    });
    
    /*******************  终端修改表单  *********************/
    var updTermForm = new Ext.form.FormPanel({
        frame: true,
        height: 450,
        width: 800,
        labelWidth: 100,
        waitMsgTarget: true,
        layout: 'column',
        items: [{
        	layout: 'form',
        	xtype: 'panel',
    		items: [{
            xtype: 'displayfield',
            fieldLabel: '终端号*',
            labelStyle: 'padding-left: 5px',
            name: 'term_idE',
            id: 'term_idE'
	    	}]
		},updTermPanel]
    });
   
	/*******************  终端修改信息 *********************/
    var updTermWin = new Ext.Window({
        title: '终端修改',
        initHidden: true,
        header: true,
        frame: true,
        closable: false,
        modal: true,
        width: 800,
        autoHeight: true,
        layout: 'fit',
        items: [updTermForm],
        buttonAlign: 'center',
        closeAction: 'hide',
        iconCls: 'logo',
        resizable: false,
        buttons: [{
            text: '确定',
            handler: function() {
                updTermPanel.setActiveTab("signE"); 
                updTermPanel.setActiveTab("paragramE");
                if(updTermForm.getForm().isValid()) {
                    updTermForm.getForm().submitNeedAuthorise({
                        url: 'T30401Action.asp?method=update',
                        waitMsg: '正在提交，请稍后......',
                        success: function(form,action) {
                            showSuccessMsg(action.result.msg,updTermForm);
                            grid.getStore().reload();
                            updTermForm.getForm().reset();
                            updTermWin.hide();
                            grid.getTopToolbar().items.items[2].disable();
                        },
                        failure: function(form,action) {
                            updTermPanel.setActiveTab('paragramE');
                            showErrorMsg(action.result.msg,updTermForm);
                        },
                        params: {
                        	term_id: selectedRecord.get('termId'),
                            txnId: '30401',
                            subTxnId: '02'
                        }
                    });
                }
                else
                {
                    var finded = true;
                    updTermForm.getForm().items.each(function(f){
                        if(finded && !f.validate()){
                            var tab = f.ownerCt.ownerCt.id;
                            if(tab == 'basicE' || tab == 'signE' || tab == 'paragramE' ){
                                 updTermPanel.setActiveTab(tab);
                            }
                            finded = false;
                        }
                    });
                }
            }
        },{
            text: '关闭',
            handler: function() {
                updTermWin.hide();
            }
        }]
    });
    
    
    
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            termTp: Ext.getCmp('termTypeQ').getValue(),
            termSta: Ext.getCmp('stateQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue()
            
        });
    }); 
	termStore.load();

	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});