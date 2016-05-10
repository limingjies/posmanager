Ext.onReady(function() {
	
	function createWhiteMchtDtl(){
		var rec = whiteMchtGrid.getSelectionModel().getSelected();
		whiteMchtWin.setTitle('[商户编号：' + rec.get('mchtNoNm') + ']');
		Ext.getCmp('dayAveAmt').setValue(rec.get('dayAveAmt'));
		Ext.getCmp('monAveAmt').setValue(rec.get('monAveAmt'));
		Ext.getCmp('sigMinAmt').setValue(rec.get('sigMinAmt'));
		Ext.getCmp('sigMaxAmt').setValue(rec.get('sigMaxAmt'));
		
		Ext.getCmp('bankNoNm').setValue(rec.get('bankNoNm'));
		Ext.getCmp('applyOpr').setValue(rec.get('applyOpr'));
		Ext.getCmp('applyTime').setValue(formatDt(rec.get('applyTime')));
		Ext.getCmp('servDisp').setValue(rec.get('servDisp'));
		Ext.getCmp('applyReason').setValue(rec.get('applyReason'));
	}
	
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var whiteMchtGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=whiteMchtFQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'bankNoNm',mapping: 'bankNoNm'},
			{name: 'dayAveAmt',mapping: 'dayAveAmt'},
			{name: 'monAveAmt',mapping: 'monAveAmt'},
			{name: 'sigMinAmt',mapping: 'sigMinAmt'},
			{name: 'sigMaxAmt',mapping: 'sigMaxAmt'},
			{name: 'servDisp',mapping: 'servDisp'},
			{name: 'applyReason',mapping: 'applyReason'},
			{name: 'applyOpr',mapping: 'applyOpr'},
			{name: 'applyTime',mapping: 'applyTime'}
		]),
		autoLoad: true
	});
	
	var whiteMchtColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '申请日期',dataIndex: 'applyTime',align: 'center',renderer: formatDt,width:150},
		{header: '目的商户',dataIndex: 'mchtNoNm',id:'mchtNoNm',width: 250},
		{header: '日均交易金额',dataIndex: 'dayAveAmt',align: 'left',width: 150},
		{header: '月均交易金额',dataIndex: 'monAveAmt',align: 'left',width: 150},
		{header: '单笔最小金额',dataIndex: 'sigMinAmt',align: 'left',width: 150},
		{header: '单笔最大金额',dataIndex: 'sigMaxAmt',align: 'left',width: 150},
		{header: '所属机构',dataIndex: 'bankNoNm',width: 250}
	]);
	
	var tbar1 = new Ext.Toolbar({
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:['-','商户名称：',{
//				xtype: 'combo',
//				store : mchtStore, 
				hiddenName: 'queryMchtNo',
				width: 300,
				id: 'idMchtNo',
				xtype : 'dynamicCombo',
				methodName : 'getMchntNoAll',
				lazyRender: true
				/*listeners : {
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
		        } */
	        },'-','签约机构：',{
//				xtype: 'basecomboselect',
//				baseParams: 'BRH_BELOW_BRANCH',
				xtype : 'dynamicCombo',
				methodName : 'getBrhId',
				id: 'idbankNo',
				width: 250,
				hiddenName: 'bankNo'
			}
		]  
	})
	
	var whiteMchtForm = new Ext.form.FormPanel({
		frame : true,
		autoHeight : true,
		width : 600,
		layout : 'form',
		waitMsgTarget : true,
		items : [{
			id : 'applyInfo',
			xtype : 'fieldset',
			title : '申请信息',
			layout : 'column',
			labelWidth : 80,
			width : 600,
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '日均交易金额',
						xtype : 'displayfield',
						id : 'dayAveAmt',
						width : 200
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '月均交易金额',
						xtype : 'displayfield',
						id : 'monAveAmt',
						width : 200
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '单笔最小金额',
						xtype : 'displayfield',
						id : 'sigMinAmt',
						width : 200
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '单笔最大金额',
						xtype : 'displayfield',
						id : 'sigMaxAmt',
						width : 200
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					items : [{
						fieldLabel : '所属机构',
						xtype : 'displayfield',
						id : 'bankNoNm',
						width : 450
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '申请人',
						xtype : 'displayfield',
						id : 'applyOpr',
						width : 200
					}]
				},{
					columnWidth : .5,
					layout : 'form',
					items : [{
						fieldLabel : '申请日期',
						xtype : 'displayfield',
						id : 'applyTime',
						width : 200
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 120,
					items : [{
						fieldLabel : '商户经营内容描述',
						xtype : 'displayfield',
						id : 'servDisp',
						width : 450
					}]
				},{
					columnWidth : 1,
					layout : 'form',
					items : [{
						fieldLabel : '申请原因',
						xtype : 'displayfield',
						id : 'applyReason',
						width : 450
					}]
				}]
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			items : [{
				xtype: 'textarea',
				width: 480,
				fieldLabel: '商户情况描述',
				maxLength: 240,
				vtype: 'isOverMax',
				labelStyle: 'padding-left: 10px',
				id: 'mchtCaseDesp',
				name: 'mchtCaseDesp'
			}]
		},{
			columnWidth : 1,
			layout : 'form',
			id : 'case',
			items : [{
				xtype: 'textarea',
				width: 480,
				fieldLabel: '拒绝原因',
				maxLength: 240,
				vtype: 'isOverMax',
				labelStyle: 'padding-left: 10px',
				id: 'refuseReason',
				name: 'refuseReason'
			}]
		}]
	});
	
	var whiteMchtWin = new Ext.Window({
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		shadow : false,
		modal : true,
		width : 630,
		autoHeight : true,
		layout : 'fit',
		items : [whiteMchtForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			id : 'action',
			handler : function() {
				whiteMchtForm.getForm().submit({
					url : 'T40401Action.asp?method='+method,
					waitMsg : '正在提交，请稍后......',
					success : function(form, action) {
						showSuccessMsg(action.result.msg,whiteMchtForm);
						// 重置表单
						whiteMchtForm.getForm().reset();
						// 重新加载参数列表
						whiteMchtGridStore.load();
						whiteMchtWin.hide();
					},
					failure : function(form, action) {
						showErrorMsg(action.result.msg,whiteMchtForm);
					},
					params : {
						txnId : '40401',
						subTxnId : (method=='accept'?'01':'02'),
						mchtNo : whiteMchtGrid.getSelectionModel().getSelected().get('mchtNo')
					}
				});
			}
		}, {
			text : '取消',
			handler : function() {
				whiteMchtWin.hide();
			}
		}]
	});
	
	var method ;
	
	var whiteMchtGrid = new Ext.grid.GridPanel({
		region:'center',
		autoExpandColumn:'mchtNoNm',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: whiteMchtGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: whiteMchtColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				whiteMchtGridStore.load();
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
				Ext.getCmp('idbankNo').setValue('');
				whiteMchtGridStore.reload();
				/*mchtStore.removeAll();
				SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
					mchtStore.loadData(Ext.decode(ret));
				});*/
			}
		},'-',{
			xtype: 'button',
			text: '通过',
			id:'accept',
			iconCls: 'accept',
			width: 85,
			handler:function() {
				whiteMchtWin.show();
				whiteMchtWin.center();
				Ext.getCmp('action').setText('通过');
				createWhiteMchtDtl();
				Ext.getCmp('case').hide();
				method = 'accept';
			}
		},'-',{	
			xtype: 'button',
			text: '拒绝',
			id:'refuse',
			iconCls: 'refuse',
			width: 85,
			handler:function() {
				whiteMchtWin.show();
				whiteMchtWin.center();
				Ext.getCmp('action').setText('拒绝');
				createWhiteMchtDtl();
				Ext.getCmp('case').show();
				method = 'refuse';
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
						tbar1.render(this.tbar);
                }  ,
            'cellclick':selectableCell
        },
		loadMask: {
			msg: '正在加载白名单商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: whiteMchtGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	whiteMchtGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(whiteMchtGrid.getView().getRow(whiteMchtGrid.getSelectionModel().last)).frame();
			//激活菜单
			var rec = whiteMchtGrid.getSelectionModel().getSelected();
			Ext.getCmp('accept').enable();
			Ext.getCmp('refuse').enable();
		}
	});
	
	whiteMchtGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: Ext.getCmp('idMchtNo').getValue(),
			queryBankNo: Ext.getCmp('idbankNo').getValue()
		});
		Ext.getCmp('accept').disable();
		Ext.getCmp('refuse').disable();
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [whiteMchtGrid],
		renderTo: Ext.getBody()
	});
});