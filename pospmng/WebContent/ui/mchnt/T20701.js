Ext.onReady(function() {
	
	var baseStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getFeeInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
		},[
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'}
		]),
		autoLoad: false
	});
	
	
	var curOp;
	
	var flagStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('DISC_FLAG',function(ret){
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
        name: 'txnNum',
        type: 'string'
	},{
        name: 'floorMount',
        type: 'float',
        id: 'floorMount'
    }, {
        name: 'flag',
        type: 'string'
    }, {
        name: 'feeValue',
        type: 'float'
    },{
        name: 'minFee',
        type: 'float'
    },{
        name: 'maxFee',
        type: 'float'
    }]);
	
    var store = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getDiscInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'txnNum',mapping: 'txnNum',type:'string'},
			{name: 'floorMount',mapping: 'floorMount',type:'float'},
			{name: 'minFee',mapping: 'minFee'},
			{name: 'maxFee',mapping: 'maxFee'},
			{name: 'flag',mapping: 'flag'},
			{name: 'feeValue',mapping: 'feeValue'},
			{name: 'cardType',mapping: 'cardType'}
			
		]),
		autoLoad: false
	});
	
	var cm = new Ext.grid.ColumnModel({
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
            editor: {
					xtype: 'basecomboselect',
			        store: txnStore,
					id: 'idTxnNum',
					hiddenName: 'txnNum',
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
            id: 'floorMount',
            header: '最低交易金额(元)',
            dataIndex: 'floorMount',
            width: 100,
            sortable: true,
            editor: new fm.NumberField({
                    allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000000
                })
        },{
            header: '回佣类型',
            dataIndex: 'flag',
            width: 100,
            editor: {
					xtype: 'basecomboselect',
			        store: flagStore,
					id: 'idflagcom',
					hiddenName: 'flagcom',
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
            header: '回佣值',
            dataIndex: 'feeValue',
            width: 80,
            sortable: true,
            editor: new fm.NumberField({
            		allowBlank: false,
                    allowNegative: false,
                    maxValue: 100000
                })
        },{
            header: '按比最低收费(元)',
            dataIndex: 'minFee',
            width: 110,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100000
                })
        },{
            header: '按比最高收费(元)',
            dataIndex: 'maxFee',
            width: 110,
            sortable: true,
            editor: new fm.NumberField({
                    allowNegative: false,
                    maxValue: 100000
                })
        }]
	});
	
	var grid = new Ext.grid.EditorGridPanel({
		region: 'center',
        title: '计费信息',
        store: store,
        cm: cm,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: 'floorMount', 
        frame: true,
        columnLines: true,
//		border: true,
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
    	floorMount: 0
    }));
    
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
						fieldLabel: '计费代码*',
						id: 'discCd',
						name: 'discCd'
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
						fieldLabel: '计费名称*',
						id: 'discNm',
						width: 360
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [grid]
		},{
			xtype: 'panel',
			title: '配置说明',
			height: 140,
			header: true,
			frame: true,
			html: '<font color=red>交易卡种</font>：指定执行该计费算法的交易卡种，优先选择单独配置的卡种，如没有配置则选择全部卡种。<br>' +
					'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
					'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
					'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
					'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。'
		}]
	});

	var groupWin = new Ext.Window({
		title: '计费算法维护',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 760,
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
					var min = new Array();
					var dataArray = new Array();
					var zeroCount = 0;
					var txnArray = new Array();
					
					//把每条中的交易卡种代码放进这个数组中
					var txnNumArrayTmp = new Array();
					for(var i=0;i<store.getCount();i++){
//						grid.getSelectionModel().selectRow(i);
//						var re = grid.getSelectionModel().getSelected();
						
						var re = store.getAt(i);
						
						if(re.data.cardType == null){showMsg("第" + (i+1) + "行的[卡类型]不能为空",grid);return;}
						if(re.data.txnNum == null){showMsg("第" + (i+1) + "行的[交易类型]不能为空",grid);return;}
						if(re.data.floorMount == null){showMsg("第" + (i+1) + "行的[最低交易金额]不能为空",grid);return;}
						if(re.data.flag == null){showMsg("第" + (i+1) + "行的[回佣类型]不能为空",grid);return;}
						if(re.data.feeValue == null){showMsg("第" + (i+1) + "行的[回佣值]不能为空",grid);return;}
                        
						//把所有的记录中的卡种类型放进数组txnNumArrayTmp中
						txnNumArrayTmp[i] = re.data.txnNum;

                        //当回佣类型是按比例且回佣值不为0时最低金额不能是0
//						|| re.data.minFee == '0'
//						alert(re.data.flag+"---"+re.data.feeValue+"----"+re.data.minFee);
						
						if( re.data.minFee!='0'){
	                        if(re.data.flag == '2' && Number(re.data.feeValue) !=0 && (re.data.minFee == null||re.data.minFee =='') ){
								showMsg("第" + (i+1) + "行[当回佣类型是按比例且回佣值不为0时最低收费金额不能为空]",grid);
								return;
							}
						}

						//当回佣类型是按比例且回佣值为0时最低金额应该为0
                        if(re.data.flag == '2' && Number(re.data.feeValue) == 0 && re.data.minFee != '0'){
							showMsg("第" + (i+1) + "行[当回佣类型是按比例且回佣值为0时最低收费金额应该为0]",grid);
							return;
						}
						//当回佣类型是按比例且回佣值为0时最高金额应该为0
                        if(re.data.flag == '2' && Number(re.data.feeValue) == 0 && re.data.maxFee != '0'){
							showMsg("第" + (i+1) + "行[当回佣类型是按比例且回佣值为0时最高收费金额应该为0]",grid);
							return;
						}
						
                        if(re.data.flag == '2' && re.data.minFee != null && re.data.maxFee != null 
                        	&& re.data.minFee != '' && re.data.maxFee != '' && Number(re.data.minFee) > Number(re.data.maxFee)){
							showMsg("第" + (i+1) + "行[最低收费高于了最高收费]",grid);
							return;
						}
                        
						
						//统计最低交易金额为0的记录
						if(re.data.floorMount == 0){
							zeroCount++;							
						}
						//判断同一[交易类型]所对应的[最低交易金额]是否相当
						for(var j=0;j<min.length;j++){
							if(re.data.cardType + "_" + re.data.floorMount == min[j]){
								showMsg("同一[卡类型]所对应的[最低交易金额]不能相等",grid);
								return;
							}
						}
						min.push(re.data.cardType + "_" + re.data.floorMount);
						//记录交易类型的种类
						if(txnArray.getIndex(re.data.cardType) == -1){
							txnArray.push(re.data.cardType);
						}
						if(re.data.flag == null || re.data.flag == ''){showMsg("第" + (i+1) + "行的[回佣类型]不能为空",grid);return;}
						if(re.data.feeValue == null){showMsg("第" + (i+1) + "行的[回佣值]不能为空",grid);return;}
						var data = {
							cardType: re.data.cardType,
							txnNum: re.data.txnNum,
							floorMount: re.data.floorMount,
							flag: re.data.flag,
							feeValue: re.data.feeValue,
							minFee: re.data.minFee,
							maxFee: re.data.maxFee
						};
						dataArray.push(data);
					}
					
					//判断卡种，如果没有”全部卡种“，而且其他四种卡种也没配置全，就不通过
					if(!validTxnNum(txnNumArrayTmp)) {
						showMsg("[交易卡种]：如果您没有配置{全部卡种}，请配置全其他卡种",grid);
						return;
					}
					
					if(zeroCount != txnArray.length){
						showMsg("每种[交易卡种]都应对应唯一的一条[最低交易金额]为0的记录",grid);
						return;
					}
					if(1!=1){
						
					}else{
						frm.findField("discCd").enable();
						frm.submitNeedAuthorise({
							url: 'T20701Action_' + (curOp=='01'?'add':'update') + '.asp',
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
    					floorMount: 0
    				}));
				}else if("02" == curOp){
					feeForm.getForm().loadRecord(baseStore.getAt(0));
					store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
				}else{
					showAlertMsg("没有获得当前的操作状态，重置失败",mchntForm);
				}
			}
		},{
			text: '取消',
			handler: function() {
				groupWin.hide(gridPanel);
				feeForm.getForm().resetAll();
//				store.removeAll();
				store.insert(0, new Fee({
    				floorMount: 0
    			}));
			}
		}]
	});

	//validTxnNum方法用来验证“交易卡种”，txnNumArray为数组Array类型
	//如果txnNumArray中有'0000'(全部卡种)，则返回true
	//如果txnNumArray中没有'0000',但有4种不同卡种，也返回true
	//否则，返回false
	function validTxnNum(txnNumArray) {
		
		var count = 0;
		
		for(var i=0; i<txnNumArray.length; i++) {
			
			if(txnNumArray[i] == '0000') {
				
				return true;
			} else {
				
				for(var j=0; j<=i; j++) {
					if(i == j) count++;
					if(txnNumArray[j] == txnNumArray[i]) break;
				}
			}
		}
		
		if(count ==7) return true;
		else return false;
	};
		
	var detailGrid = new Ext.grid.GridPanel({
		region: 'center',
        title: '计费信息',
        store: store,
        cm: cm,
        columnLines: true,
//		border: true,
        stripeRows: true,
        sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
        autoExpandColumn: 'floorMount', 
        frame: true
    });
	
	
	var detailForm = new Ext.FormPanel({
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
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费代码',
						id: 'dediscCd'
		        	}]
	        	}]
			},{
	        	columnWidth: 1,
	       		items: [{
		        	xtype: 'panel',
		        	layout: 'form',
					items: [{
						xtype: 'displayfield',
						labelStyle: 'padding-left: 5px',
						fieldLabel: '计费名称',
						id: 'dediscNm'
		        	}]
	        	}]
			}]
		},{
			xtype: 'panel',
			height: 280,
			layout: 'border',
			items: [detailGrid]
		},{
			xtype: 'panel',
			title: '配置说明',
			height: 140,
			header: true,
			frame: true,
			html: '<font color=red>交易类型</font>：指定执行该计费算法的交易类型。<br>' +
					'<font color=red>最低交易金额</font>：指定执行该计费算法的最低交易金额，如已配置最低交易金额为0和5000的两条计费算法信息，那么当交易金额在0-5000(含5000)时，执行最低交易金额为0的计费算法，大于5000时，执行最低交易金额为5000的计费算法。<br>' +
					'<font color=red>回佣类型</font>：指定该算法计算回佣值时的计算方式。<br>' +
					'<font color=red>回佣值</font>：当回佣类型为“按笔收费”时，回佣为回佣值所示金额(此时不需输入按比最低收费/按比最高收费)；当回佣类型为“按比例收费时”，回佣为当前 交易金额x回佣值(需满足最低和最高收费的控制)。<br>' +
					'<font color=red>按比最低收费/按比最高收费</font>：指定当回佣类型为“按比例收费”时的最低/最高收费金额。'
		}]
	});
	
	
	var detailWin = new Ext.Window({
		title: '计费算法详细信息',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
//		width: 670,
		width: 760,
		autoHeight: true,
		items: [detailForm],
		buttonAlign: 'center',
		closeAction: 'hide',
		closable: true,
		resizable: false
	});
	

	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		handler:function() {
			curOp = '01';
			store.removeAll();
			store.insert(0, new Fee({
    			floorMount: 0
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
					discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				},
				callback: function(records, options, success){
					if(success){
						curOp = "02";//update
						feeForm.getForm().loadRecord(baseStore.getAt(0));
						store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
						feeForm.getForm().findField("discCd").disable();
						groupWin.show(gridPanel);
					}else{
						showErrorMsg("加载计费算法信息失败，请刷新数据后重试",gridPanel);
					}
				}
			});
		}
	};
	
	var delMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
			var discCd = gridPanel.getSelectionModel().getSelected().data.discCd;
			showConfirm('确定要删除该计费算法信息吗吗？计费代码：' + discCd,grid,function(bt) {
					//如果点击了提示的确定按钮
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20701Action_delete.asp',
							success : function(form, action) {
								var rspObj = Ext.util.JSON.decode(form.responseText);
								if(rspObj.success){
									showSuccessMsg(rspObj.msg,gridPanel);
									gridStore.reload();
								}else{
									showErrorMsg(rspObj.msg,gridPanel);
								}
							},
							failure : function(form,action) {
								showErrorMsg(action.result.msg,gridPanel);
							},
							params: { 
								discCd: discCd,
								txnId: '20701',
								subTxnId: '03'
							}
						});
					}
				});
		}
	};
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function() {
			//重新载入以获得最新数据
			baseStore.load({
				params: {
					discCd: gridPanel.getSelectionModel().getSelected().data.discCd
				},
				callback: function(records, options, success){
					if(success){
						detailForm.getForm().findField('dediscCd').setValue(baseStore.getAt(0).data.discCd);
						detailForm.getForm().findField('dediscNm').setValue(baseStore.getAt(0).data.discNm);
						store.load({
							params: {
								start: 0,
								discCd: gridPanel.getSelectionModel().getSelected().data.discCd
								}
						});
						detailWin.show(gridPanel);
					}else{
						showErrorMsg("加载计费算法信息失败，请刷新数据后重试",gridPanel);
					}
				}
			});
		}
	};
	
	var queryCondition = {
		text: '录入查询条件',
		width: 85,
		id: 'query',
		iconCls: 'query',
		handler:function() {
			queryWin.show();
		}
	};
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 300,
		autoHeight: true,
		items: [{
			xtype: 'textfield',
			id: 'qudiscCd',
			name: 'qudiscCd',
			vtype: 'alphanum',
			fieldLabel: '计费代码'
		},{
			xtype: 'textfield',
			id: 'qudiscNm',
			name: 'qudiscNm',
			vtype: 'alphanum',
			fieldLabel: '计费名称'
		}]
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 300,
		autoHeight: true,
		items: [queryForm],
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
		}],
		buttons: [{
			text: '查询',
			handler: function() {
				gridStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
	
	
	menuArr.push(addMenu);     //[0]
	menuArr.push('-'); 	       //[1]
	menuArr.push(editMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(delMenu);     //[4]
	menuArr.push('-');         //[5]
	menuArr.push(detailMenu);  //[6]
	menuArr.push('-');         //[7]
	menuArr.push(queryCondition);  //[8]
	
	


	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tblInfDiscCd2'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[															
			{name: 'discCd',mapping: 'discCd'},
			{name: 'discNm',mapping: 'discNm'},
			{name: 'discOrg',mapping: 'discOrg'},
			{name: 'lastOperIn',mapping: 'lastOperIn'},
			{name: 'recUpdUserId',mapping: 'recUpdUserId'},				
			{name: 'recUpdTs',mapping: 'recUpdTs'},				
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	gridStore.load({
		params: {
			start: 0
		}
	});
	
	var gridColumnModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),									
		{header: '计费代码',dataIndex: 'discCd',sortable: true},
		{header: '计费名称',dataIndex: 'discNm',sortable: true,id:'discNm'},
		{header: '操作柜员号',dataIndex: 'recUpdUserId',sortable: true},
		{header: '修改时间',dataIndex: 'recUpdTs',sortable: true,renderer:formatDt},
		{header: '创建时间',dataIndex: 'recCrtTs',sortable: true,renderer:formatDt}
	]);

	var gridPanel = new Ext.grid.GridPanel({
		title: '计费算法信息维护',
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
			msg: '正在加载计费算法信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	gridStore.on('beforeload', function() {
	    Ext.apply(this.baseParams, {
		    start: 0
		});
		gridPanel.getTopToolbar().items.items[2].disable();
		gridPanel.getTopToolbar().items.items[4].disable();
		gridPanel.getTopToolbar().items.items[6].disable();
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
				gridPanel.getTopToolbar().items.items[4].enable();
				gridPanel.getTopToolbar().items.items[6].enable();
			}else{
				gridPanel.getTopToolbar().items.items[2].disable();
				gridPanel.getTopToolbar().items.items[4].disable();
				gridPanel.getTopToolbar().items.items[6].enable();
			}
		}
	});
	
	gridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			discCd: queryForm.getForm().findField('qudiscCd').getValue(),
			discNm: queryForm.getForm().findField('qudiscNm').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [gridPanel],
		renderTo: Ext.getBody()
	});
	
});