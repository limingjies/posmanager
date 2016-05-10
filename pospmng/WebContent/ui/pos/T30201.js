Ext.onReady(function() {
	var formTermRiskParamStore = new Ext.data.Store({
		
        proxy: new Ext.data.HttpProxy({
            url: 'loadRecordAction.asp'
        }),
        
        reader: new Ext.data.JsonReader({
            root: 'data',
            idProperty: 'id'
        },[
            {name: 'creditSingleAmt',mapping: 'creditSingleAmt'},
            {name: 'deditSingleAmt',mapping: 'deditSingleAmt'},
            {name: 'termPara1',mapping: 'termPara1'}
            ]),
            autoLoad: false
        });
	//修改表单
	var formTermRiskParam = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 700,
		layout : 'form',
		waitMsgTarget : true,
		items : [{
			id : 'riskParamInfo',
			xtype : 'fieldset',
			title : '终端限额信息<font color="red">（0表示不限额）</font>',
			layout : 'column',
			labelWidth : 90,
			items : [{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '月累计借记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditMonAmt',
					name:'tblRiskParamMng.creditMonAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '月累计贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitMonAmt',
					name:'tblRiskParamMng.debitMonAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '日累计借记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditDayAmt',
					name:'tblRiskParamMng.creditDayAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '日累计贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitDayAmt',
					name:'tblRiskParamMng.debitDayAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '单笔借记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'creditSingleAmt',
					name:'tblRiskParamMng.creditSingleAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				items : [{
					fieldLabel : '单笔贷记卡交易限额(元)<font color="red">*</font>',
					xtype: 'numberfield',
					id : 'debitSingleAmt',
					name:'tblRiskParamMng.deditSingleAmt',
					maxLength: 14,
					editable: true,
					allowBlank: false,
		            allowNegative: false,
		            maxValue: 9999999999999,
		            minValue: 0,
		            value: 0,
		            //设置允许保留0位小数
		            allowDecimals: true,
		            decimalPrecision: 0,
		            width : 200
				}]
			}]
		},{
			id : 'tradInfo',
			xtype : 'fieldset',
			title : '交易信息',
			layout : 'column',
			labelWidth : 50,
			items : [
				{
					xtype : 'fieldset',
					title : '交易权限',
					layout : 'column',
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
								id : 'param6Upd',
								name : 'param6Upd',
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '预授权 ',
								id : 'param2Upd',
								name : 'param2Upd',
								inputValue : '1'
							}]
						}, {
							columnWidth : .33,
							layout : 'form',
							items : [{
								xtype : 'checkbox',
								fieldLabel : '退货 ',
								id : 'param8Upd',
								name : 'param8Upd',
								inputValue : '1'
							}]
						}
					]
				},{
					xtype : 'fieldset',
					title : '卡类型权限',
					layout : 'column',
					labelWidth : 70,
					width : 570,
					defaults : {
						bodyStyle : 'padding-left: 10px'
					},
					items : [{
						columnWidth : .33,
						layout : 'form',
						items : [{
							xtype : 'checkbox',
							fieldLabel : '借记卡 ',
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
							id : 'param1Upd4',
							name : 'param1Upd4',
							inputValue : '1'
						}]
					}]
				}
			]
		},{
			id : 'otherInfo',
			xtype : 'fieldset',
			title : '其他信息',
			layout : 'column',
			labelWidth : 50,
			items : [{
				columnWidth : .9,
				layout : 'form',
				items : [{
					xtype: 'textarea',
					fieldLabel: '备注',
					width: 480,
					vtype: 'length100B',
					labelStyle: 'padding-left: 10px',
					id: 'remark',
					name: 'tblRiskParamMng.remark'
				}]
			}]
		}]
	});
	
	var winTermRiskParam = new Ext.Window({
		title:'终端风控参数修改',
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		shadow : false,
		modal : true,
		width : 700,
		autoHeight : true,
		layout : 'fit',
		items : [formTermRiskParam],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			id : 'btnSubmit',
			text : '提交',
			handler : function() {
				if(formTermRiskParam.getForm().isValid()){
					var termParam = getTermParam1();
					var recs = grid.getSelectionModel().getSelections();
					var termIds = '';
					var recCrtTs = '';
					var mchtIds = '';
					for (var i = 0; i < recs.length; i++) {
						if(i == recs.length-1){
							termIds += recs[i].get('termId');
							recCrtTs += recs[i].get('recCrtTs');
							mchtIds += recs[i].get('mchntNo');
						}else {
							termIds += recs[i].get('termId')+'|';
							recCrtTs += recs[i].get('recCrtTs') + '|';
							mchtIds += recs[i].get('mchntNo') + '|';
						}
					}
					formTermRiskParam.getForm().submit({
						url : 'T40407Action.asp?method=add',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							showSuccessMsg(action.result.msg,formTermRiskParam);
							// 重置表单
							formTermRiskParam.getForm().reset();
							winTermRiskParam.hide();
						},
						failure : function(form, action) {
							showErrorMsg(action.result.msg,formTermRiskParam);
							formTermRiskParam.getForm().reset();
							winTermRiskParam.hide();
						},
						params : {
							'txnId' : '40407',
							'subTxnId' : '02',
							'termParam1' : termParam,
							'termIds' : termIds,
							'mchtIds' : mchtIds,
							'recCrtTs' : recCrtTs
							
						}
					});
				}
			}
		}, {
			text : '关闭',
			handler : function() {
				winTermRiskParam.hide();
			}
		}]
	});
    var mchntStore = new Ext.data.JsonStore({
        fields: ['valueField','displayField'],
        root: 'data'
    });
    SelectOptionsDWR.getComboData('MCHNT_NO',function(ret){
        mchntStore.loadData(Ext.decode(ret));
    });
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        items: [new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号',
                anchor :'60%'
            }),{
		        	xtype: 'dynamicCombo',
					methodName: 'getMchntId',
					fieldLabel: '商户编号',
					hiddenName: 'mchtCd',
					id: 'mchnNoQ',
					allowBlank: false,
					editable: true,
					width: 300
           },{
	        	xtype: 'dynamicCombo',
				methodName: 'getMchntId',
				fieldLabel: '商户编号',
				hiddenName: 'mchtCd',
				id: 'mchnNoQ',
				allowBlank: false,
				editable: true,
				width: 300
       },{                                         
                xtype: 'combo',
                fieldLabel: '终端状态',
                id: 'state',
                name: 'state',
                anchor :'60%',
                store: new Ext.data.ArrayStore({
                    fields: ['valueField','displayField'],
                    data: [['0','新增未审核'],['2','修改未审核'],['3','冻结未审核'],['5','恢复未审核'],['6','注销未审核']]
                })
        },{
              width: 150,
              xtype: 'datefield',
              fieldLabel: '创建起始时间',
              format : 'Y-m-d',
              name :'startTime',
              id :'startTime',
              anchor :'60%'       
        },{                                         
              width: 150,
              xtype: 'datefield',
              fieldLabel: '创建截止时间',
              format : 'Y-m-d',
              name :'endTime',
              id :'endTime',
              anchor :'60%'       
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
	var termStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: 'gridPanelStoreAction.asp?storeId=termForCheck'
        }),
        reader: new Ext.data.JsonReader({
            root: 'data',
            totalProperty: 'totalCount'
        },[
            {name: 'termId',mapping: 'termId'},
            {name: 'mchntNo',mapping: 'mchntNo'},
            {name: 'mchntName',mapping: 'mchntName'},
            {name: 'termSerialNum',mapping: 'termSerialNum'},
            {name: 'termSta',mapping: 'termSta'},
            {name: 'termSignSta',mapping: 'termSignSta'},
            {name: 'termIdId',mapping: 'termIdId'},
            {name: 'termFactory',mapping: 'termFactory'},
            {name: 'termMachTp',mapping: 'termMachTp'},
            {name: 'termVer',mapping: 'termVer'},
            {name: 'termTp',mapping: 'termTp'},
            {name: 'termBranch',mapping: 'termBranch'},
            {name: 'termIns',mapping: 'termIns'},
            {name: 'recCrtTs',mapping: 'recCrtTs'},
            {name: 'productCd',mapping: 'productCd'}
        ])
    });
	
	termStore.load({
		params: {
			start: 0
		}
	});

	 var termColModel = new Ext.grid.ColumnModel([
	 		new Ext.grid.RowNumberer(),
	 		//20160419 郭宇 修改 去掉多余的列
//	 		new Ext.grid.RowSelectionModel({singleSelect: true}),
	         {id: 'termId',header: '终端号',dataIndex: 'termId',width: 100},
	         {header: '商户号',dataIndex: 'mchntNo',width: 130},
	         {id: 'mchntName',header: '商户名',dataIndex: 'mchntName',width: 130},
	         {header: '终端状态',dataIndex: 'termSta',width: 100,renderer: termSta},
	         {header: '终端所属合作伙伴',dataIndex: 'termBranch',width : 180},
	         {header : '终端SN号',dataIndex : 'productCd',width: 100},
	        // {header: '终端序列号',dataIndex: 'termSerialNum'},
	        // {header: '终端库存编号',dataIndex: 'termIdId'},
	        // {header: '终端厂商',dataIndex: 'termFactory',width: 100},
	        // {header: '终端型号',dataIndex: 'termMachTp',width: 100},
	         {header: '录入日期',dataIndex: 'recCrtTs',width: 150,renderer: formatDt}
	]);
	
    termStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            termId: Ext.getCmp('termNoQ').getValue(),
            startTime: topQueryPanel.getForm().findField('startTime').getValue(),
            endTime: topQueryPanel.getForm().findField('endTime').getValue(),
            mchnNo: Ext.getCmp('mchnNoQ').getValue(),
            termSta: Ext.getCmp('state').getValue()
        });
    });
    
    
   
	
    
	var qryMenu = {
        text: '审核',
        width: 85,
        iconCls: 'edit',
        disabled: true,
        handler:function(bt) {
            var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }
            bt.disable();
			setTimeout(function(){bt.enable();},2000);
            selectTermInfoNew(rec.get('termId'),rec.get('recCrtTs'),grid);	
        }	
    };
	
	var acceptMenu = {
		text: '通过',
		width: 85,
		iconCls: 'accept',
		disabled: true,
		hidden:true,
		handler:function() {
			showConfirm('确认通过吗？',grid,function(bt) {
				if(bt == 'yes') {
					showProcessMsg('正在提交信息，请稍后......');
					var rec = grid.getSelectionModel().getSelected();
                    if(rec == null)
		            {
		                showAlertMsg("没有选择记录",grid);
		                return;
		            }  
					Ext.Ajax.requestNeedAuthorise({
						url: 'T30201Action.asp',
						params: {
							termId: rec.get('termId'),
                            recCrtTs: rec.get('recCrtTs'),
                            termSta: rec.get('termSta'),
							txnId: '30201',
							subTxnId: '0'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								showSuccessMsg(rspObj.msg,grid);
							} else {
								showErrorMsg(rspObj.msg,grid);
							}
							// 重新加载终端待审核信息
							grid.getStore().reload();
						}
					});
					hideProcessMsg();
                    grid.getTopToolbar().items.items[0].disable();
	                grid.getTopToolbar().items.items[1].disable();
	                grid.getTopToolbar().items.items[2].disable();
				}
			});
		}
	};
	
	var refuseMenu = {
		text: '拒绝',
		width: 85,
		iconCls: 'refuse',
		disabled: true,
		hidden:true,
		handler:function() {
            showConfirm('确认拒绝吗？',grid,function(bt) {
                if(bt == 'yes') {
                	showInputMsg('提示','请输入拒绝原因',true,refuse);
                
                }
            });
		}
	};
	
	// 终端拒绝按钮调用方法
	function refuse(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('拒绝原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入拒绝原因',true,refuse);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
		    var rec = grid.getSelectionModel().getSelected();
            if(rec == null)
            {
                showAlertMsg("没有选择记录",grid);
                return;
            }  
		    Ext.Ajax.requestNeedAuthorise({
		        url: 'T30201Action.asp',
		        params: {
		            termId: rec.get('termId'),
		            recCrtTs: rec.get('recCrtTs'),
		            termSta: rec.get('termSta'),
		            txnId: '30201',
		            subTxnId: '1',
		            refuseInfo: text
		        },
		        success: function(rsp,opt) {
		            var rspObj = Ext.decode(rsp.responseText);
		            if(rspObj.success) {
		                showSuccessMsg(rspObj.msg,grid);
		            } else {
		                showErrorMsg(rspObj.msg,grid);
		            }
		            // 重新加载终端待审核信息
		            grid.getStore().reload();
		        }
		    });
		    hideProcessMsg();
            grid.getTopToolbar().items.items[0].disable();
            grid.getTopToolbar().items.items[1].disable();
            grid.getTopToolbar().items.items[2].disable();
		}
	}
    
	
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
    
    
    
    var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
	var menuArr = new Array();
	var edit = {
		xtype: 'button',
		text: '修改',
		id:'btnEdit',
		iconCls: 'edit',
		hidden: true,
		disabled : true,
		width: 85,
		handler:function() {
			var recs = grid.getSelectionModel().getSelections();
			if(recs && recs.length > 1){
				alert("只可以选择一条记录进行修改！");
				return;
			}else {
				formTermRiskParam.getForm().reset();
				var termId = recs[0].get('termId');
				var mchntNo = recs[0].get('mchntNo');
				var recCrtTs = recs[0].get('recCrtTs');
				formTermRiskParamStore.load({
				    params: {
				         storeId: 'getTermRiskInfo',
				         termId: termId,
				         recCrtTs: recCrtTs,
				         mchntNo : mchntNo
				    },
				    callback: function(records, options, success){
				        if(success){
				        	var creditSingleAmt = records[0].get('creditSingleAmt');
				        	var deditSingleAmt = records[0].get('deditSingleAmt');
				        	var termPara1 = records[0].get('termPara1');
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.creditSingleAmt").setValue(creditSingleAmt);
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.deditSingleAmt").setValue(deditSingleAmt);
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.creditMonAmt").setValue(0);
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.debitMonAmt").setValue(0);
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.creditDayAmt").setValue(0);
				        	formTermRiskParam.getForm().findField("tblRiskParamMng.debitDayAmt").setValue(0);
				        	praseTermParam1(termPara1);
				        	winTermRiskParam.show();
				        }else{
				        	alert("载入终端信息失败，请稍后再试!")
				        }
				    }
				});
			}
		}
	};
	 function praseTermParam1(val)
	    {
		 formTermRiskParam.getForm().findField("param1Upd1").setValue(val.substring(0,1).trim());
		 formTermRiskParam.getForm().findField("param1Upd2").setValue(val.substring(1,2).trim());
		 formTermRiskParam.getForm().findField("param1Upd3").setValue(val.substring(2,3).trim());
		 formTermRiskParam.getForm().findField("param1Upd4").setValue(val.substring(3,4));
	        
		 formTermRiskParam.getForm().findField("param1Upd").setValue(val.substring(10,11).trim());
		 formTermRiskParam.getForm().findField("param6Upd").setValue(val.substring(11,12).trim());
		 formTermRiskParam.getForm().findField("param2Upd").setValue(val.substring(13,14));
		 formTermRiskParam.getForm().findField("param8Upd").setValue(val.substring(17,18));
	        
	    }
	//获取附加信息的新值
		function getTermParam1(){
			var val = '                   ';
			var array=val.split('');
			array.splice(0,1, formTermRiskParam.getForm().findField("param1Upd1").getValue()==true?1:0);
			array.splice(1,1, formTermRiskParam.getForm().findField("param1Upd2").getValue()==true?1:0);
			array.splice(2,1, formTermRiskParam.getForm().findField("param1Upd3").getValue()==true?1:0);
			array.splice(3,1, formTermRiskParam.getForm().findField("param1Upd4").getValue()==true?1:0);
			array.splice(10,1, formTermRiskParam.getForm().findField("param1Upd").getValue()==true?1:0);
			array.splice(11,1, formTermRiskParam.getForm().findField("param6Upd").getValue()==true?1:0);
			array.splice(12,1, formTermRiskParam.getForm().findField("param6Upd").getValue()==true?1:0);
			array.splice(13,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
			array.splice(14,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
			array.splice(15,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
			array.splice(16,1, formTermRiskParam.getForm().findField("param2Upd").getValue()==true?1:0);
			array.splice(17,1, formTermRiskParam.getForm().findField("param8Upd").getValue()==true?1:0);
			array.splice(18,1, formTermRiskParam.getForm().findField("param8Upd").getValue()==true?1:0);
			return array.join('');
		}
	var batchEdit = {
		xtype: 'button',
		text: '批量修改',
		id:'btnBatchEdit',
		iconCls: 'edit',
		hidden: true,
		disabled : true,
		width: 85,
		handler:function() {
			winTermRiskParam.show();
			formTermRiskParam.getForm().reset();
		}
	};
	menuArr.push(acceptMenu);	//[0]
	menuArr.push(refuseMenu);	//[1]
	menuArr.push(qryMenu);      //[2]
	menuArr.push(queryMenu);    //[3]
	menuArr.push(edit);    //[4]
	menuArr.push(batchEdit);    //[5]
	
	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '终端审核',
		iconCls: 'T30201',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: termStore,
		sm:new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		autoExpandColumn: 'mchntName',
		forceValidation: true,
		tbar: menuArr,
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell,
		}
	});
    

	grid.getSelectionModel().on({
		'beforerowselect': function() {
			grid.getTopToolbar().items.items[2].disable();
            grid.getTopToolbar().items.items[4].disable();
            grid.getTopToolbar().items.items[5].disable();
		}
	});
	grid.getSelectionModel().on({
		'beforerowselect': function() {
			grid.getTopToolbar().items.items[2].disable();
            grid.getTopToolbar().items.items[4].disable();
            grid.getTopToolbar().items.items[5].disable();
		},
		'rowselect': function() {},
		'selectionchange' :function(){
			//行高亮
			//Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据所选择的终端状态判断哪个按钮可用
			var records = grid.getSelectionModel().getSelections();
			if(records.length == 1){
				grid.getTopToolbar().items.items[2].enable();
                grid.getTopToolbar().items.items[4].enable();
                grid.getTopToolbar().items.items[5].enable();
			}else if(records.length > 1){
				grid.getTopToolbar().items.items[5].enable();
			}else {
				grid.getTopToolbar().items.items[2].disable();
                grid.getTopToolbar().items.items[4].disable();
                grid.getTopToolbar().items.items[5].disable();
			}
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});