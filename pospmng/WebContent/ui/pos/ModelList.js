Ext.onReady(function() {

	/*Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'id';*/
	
			var selectedRecord;

			// 录入查询条件的form表单
			var topQueryPanel = new Ext.form.FormPanel({
				frame : true,
				border : true,
				width : 500,
				autoHeight : true,
				labelWidth : 80,
				items : [ new Ext.form.TextField({
					id : 'modelName_query',
					name : 'modelName',
					fieldLabel : '模板名称',
					anchor : '60%'
				}), {
					xtype : 'combo',
					fieldLabel : '状态',
					id : 'status_query',
					name : 'status',
					anchor : '60%',
					store : new Ext.data.ArrayStore({
						fields : [ 'valueField', 'displayField' ],
						data : [ [ '0', '停用' ], [ '1', '启用' ] ]
					})
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '创建时间',
					format : 'Y-m-d',
					name : 'createTime',
					id : 'createTime_query',
					anchor : '60%'
				}, {
					width : 150,
					xtype : 'datefield',
					fieldLabel : '更新时间',
					format : 'Y-m-d',
					name : 'updateTime',
					id : 'updateTime_query',
					anchor : '60%'
				} ],
				buttons : [ {
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
				} ]
			});

			var termStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'modelInfoAction.asp?modelId=modelList'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'modelId',
					mapping : 'modelId'
				}, {
					name : 'modelName',
					mapping : 'modelName'
				}, {
					name : 'version',
					mapping : 'version'
				}, {
					name : 'createTime',
					mapping : 'createTime'
				}, {
					name : 'updateTime',
					mapping : 'updateTime'
				}, {
					name : 'operateId',
					mapping : 'operateId'
				}, {
					name : 'status',
					mapping : 'status'
				}, {
					name : 'printNumber',
					mapping : 'printNumber'
				} ])
			});

			termStore.on('beforeload', function() {
				Ext.apply(this.baseParams, {
					modelName : topQueryPanel.getForm().findField('modelName').getValue(),
								// Ext.getCmp('modelName').getValue(),
					status : Ext.getCmp('status_query').getValue(),
					createTime : topQueryPanel.getForm().findField('createTime').getValue(),
					updateTime : topQueryPanel.getForm().findField('updateTime').getValue()
				});
			});

			termStore.load();
			var termColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(), {
						id : 'modelId',
						header : '模板ID',
						dataIndex : 'modelId',
						width : 100
					}, {
						header : '模板名称',
						dataIndex : 'modelName',
						width : 130,
						id : 'modelName'
					}, {
						header : '版本号',
						dataIndex : 'version',
						width : 130,
						id : 'version'
					}, {
						header : '创建时间',
						dataIndex : 'createTime',
						width : 200,
						renderer : formatDt
					}, {
						header : '更新时间',
						dataIndex : 'updateTime',
						width : 200,
						renderer : formatDt
					}, {
						header : '操作人ID',
						dataIndex : 'operateId'
					}, {
						header : '状态',
						dataIndex : 'status',
						renderer: renderStatus
					}, {
						header : '打印联数',
						dataIndex : 'printNumber',
						id : 'printNumber'
					} ]);


			function renderStatus(value) {
				if (value == '0') {
					return "<font color='red'>停用</font>";
				} else {
					return "<font color='green'>启用</font>";
				}
			}

			//添加模板时商户参数的字段顺序值比较方法
			function compare(val,flag) {
			    var oEle = document.getElementsByName('fieldOrder');
				for(var i=0;i<20;i++) {
					if(flag!=i) {
						if(val==(oEle[i].value)){
							Ext.getCmp('fieldOrder'+flag).setValue();
						}
					}
				}
			}

			//添加模板时持卡人参数的字段顺序值比较方法
			function compareTwo(val,flag) {
			    var oEle = document.getElementsByName('fieldOrder');
				for(var i=20;i<40;i++) {
					if(flag!=i) {
						if(val==(oEle[i].value)){
							Ext.getCmp('fieldOrder'+flag).setValue();
						}
					}
				}
			}

			//修改模板时商户参数的字段顺序值比较方法
			function compareUpd(val,flag) {
			    var oEle = document.getElementsByName('fieldOrder');
				for(var i=0;i<20;i++) {
					if(flag!=i) {
						if(val==(oEle[i].value)){
							Ext.getCmp('fieldOrderUpd'+flag).setValue();
						}
					}
				}
			}

			//修改模板时持卡人参数的字段顺序值比较方法
			function compareUpdTwo(val,flag) {
			    var oEle = document.getElementsByName('fieldOrder');
				for(var i=20;i<40;i++) {
					if(flag!=i) {
						if(val==(oEle[i].value)){
							Ext.getCmp('fieldOrderUpd'+flag).setValue();
						}
					}
				}
			}
			
			var addMenu = {
				text : '添加',
				width : 85,
				iconCls : 'add',
				handler : function() {
					for(var i=0;i<40;i++) {
						Ext.getCmp('fieldName'+i).enable();
						//Ext.getCmp('status'+i).enable();
						Ext.getCmp('fontSize'+i).enable();
						Ext.getCmp('fieldOrder'+i).enable();
						Ext.getCmp('contFormatType'+i).enable();
						Ext.getCmp('contFormat'+i).enable();

						//新需求：模板新增时，默认保留1-保留5这几个字段为停用状态
                		if(i>=15 && i<=19){
    						Ext.getCmp('fieldName'+i).disable();
    						Ext.getCmp('fontSize'+i).disable();
    						Ext.getCmp('fieldOrder'+i).disable();
    						Ext.getCmp('contFormatType'+i).disable();
    						Ext.getCmp('contFormat'+i).disable();
                		}else if(i>=35 && i<=39){
    						Ext.getCmp('fieldName'+i).disable();
    						Ext.getCmp('fontSize'+i).disable();
    						Ext.getCmp('fieldOrder'+i).disable();
    						Ext.getCmp('contFormatType'+i).disable();
    						Ext.getCmp('contFormat'+i).disable();
                			
                		}
					}
					termWin.show();
					termWin.center();
				}
			};

			// 修改的数据源
			var termInfoStore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : 'modelParameterAction.asp?flag=load'
				}),
				reader : new Ext.data.JsonReader({
					root : 'data',
					totalProperty : 'totalCount'
				}, [ {
					name : 'modelId',
					mapping : 'modelId'
				}, {
					name : 'modelName',
					mapping : 'modelName'
				}, {
					name : 'fieldName',
					mapping : 'fieldName'
				}, {
					name : 'fontSize',
					mapping : 'fontSize'
				}, {
					name : 'fieldOrder',
					mapping : 'fieldOrder'
				}, {
					name : 'contFormatType',
					mapping : 'contFormatType'
				}, {
					name : 'contFormat',
					mapping : 'contFormat'
				}, {
					name : 'status',
					mapping : 'status'
				} ])
			// autoLoad : false
			});

			var editMenu = {
				text : '修改',
				width : 85,
				iconCls : 'edit',
				// disabled : true,
				handler : function() {
					selectedRecord = grid.getSelectionModel().getSelected();
					if (selectedRecord == null) {
						showAlertMsg("没有选择记录", grid);
						return;
					}
					termInfoStore.load({
						params : {
							// storeId : 'getModelInfo',
							modelId : selectedRecord.get('modelId'),
							fieldName : selectedRecord.get('fieldName'),
							status : selectedRecord.get('status'),
							fontSize : selectedRecord.get('fontSize'),
							fieldOrder : selectedRecord.get('fieldOrder'),
							contFormatType : selectedRecord.get('contFormatType'),
							contFormat : selectedRecord.get('contFormat')
						},
						callback : function(data) {
							updTermForm.getForm().loadRecord(data[0]);
							var size = data.length;
							Ext.getCmp('printNumber_upd').setValue(selectedRecord.get('printNumber'));
							for (var i = 0; i < size; i++) {
								updTermForm.getForm().findField("fieldNameUpd" + i).setValue(data[i].get("fieldName"));
								updTermForm.getForm().findField("statusUpd" + i).setValue(data[i].get("status"));
								updTermForm.getForm().findField("fontSizeUpd" + i).setValue(data[i].get("fontSize"));
								updTermForm.getForm().findField("fieldOrderUpd" + i).setValue(data[i].get("fieldOrder"));
								updTermForm.getForm().findField("contFormatTypeUpd" + i).setValue(data[i].get("contFormatType"));
								updTermForm.getForm().findField("contFormatUpd" + i).setValue(data[i].get("contFormat"));

							    var status = data[i].get("status");
		                    	if(status == '0'){
		                    		Ext.getCmp('fieldNameUpd' + i).allowBlank = true;
		                    		Ext.getCmp('fieldOrderUpd' + i).allowBlank = true;
		                    		Ext.getCmp('fontSizeUpd' + i).allowBlank = true;
		                    		Ext.getCmp('contFormatUpd' + i).allowBlank = true;
		                    		Ext.getCmp('fieldNameUpd' + i).disable();
		                    		Ext.getCmp('fieldOrderUpd' + i).disable();
		                    		Ext.getCmp('fontSizeUpd' + i).disable();
		                    		Ext.getCmp('contFormatUpd' + i).disable();
		                    	}else{
		                    		Ext.getCmp('fieldNameUpd' + i).enable();
		                    		Ext.getCmp('fieldOrderUpd' + i).enable();
		                    		Ext.getCmp('fontSizeUpd' + i).enable();
		                    		Ext.getCmp('contFormatUpd' + i).enable();
		                    		if(13<i<20 || 33<i<40){
			                    		//Ext.getCmp('fieldNameUpd' + i).allowBlank = false;
			                    		Ext.getCmp('fontSizeUpd' + i).allowBlank = false;
			                    		Ext.getCmp('fieldOrderUpd' + i).allowBlank = false;
			                    		//Ext.getCmp('contFormatUpd' + i).allowBlank = false;
		                    		}else {
			                    		Ext.getCmp('fieldNameUpd' + i).allowBlank = false;
			                    		Ext.getCmp('fontSizeUpd' + i).allowBlank = false;
			                    		Ext.getCmp('fieldOrderUpd' + i).allowBlank = false;
			                    		Ext.getCmp('contFormatUpd' + i).allowBlank = false;
									}
	                     		}
							}
							updTermForm.getForm().clearInvalid(); 
							//updTermPanel.setActiveTab(0);
							updTermWin.show();
							updTermWin.center();
						}
					});
				}
			};

			var queryWin = new Ext.Window({
				title : '查询条件',
				layout : 'fit',
				width : 500,
				autoHeight : true,
				items : [ topQueryPanel ],
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
								queryWin.setPosition(10, Ext.getBody()
										.getViewSize().height - 30);
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
						} ]
			});

			/*var qryMenu = {
				text : '详细信息',
				width : 85,
				iconCls : 'detail',
				disabled : true,
				handler : function(bt) {
					var selectedRecord = grid.getSelectionModel().getSelected();
					if (selectedRecord == null) {
						showAlertMsg("没有选择记录", grid);
						return;
					}
					bt.disable();
					setTimeout(function() {bt.enable();}, 2000);
					selectTermInfoNew(selectedRecord.get('termId'),selectedRecord.get('recCrtTs'));
				}
			};*/

			var publish = {
				text : '发布',
				width : 85,
				iconCls : 'accept',
				disabled : false,
				handler : function(bt) {
					showConfirm('确认发布吗？', grid, function(bt) {
						if (bt == 'yes') {
							var selectedRecord = grid.getSelectionModel()
									.getSelected();
							if (selectedRecord == null) {
								showAlertMsg("没有选择记录", grid);
								return;
							}
							if (selectedRecord.get("status") == '1') {
								showAlertMsg("已是发布状态", grid);
								return;
							}
							var mid = selectedRecord.get('modelId');
							Ext.Ajax.request({
								url : 'modelParameterAction.asp',
								params : {
									flag : "publish",
									modelId : mid
								},
								success : function(response) {
									Ext.Msg.alert('提示', '发布成功！');
									termStore.reload();
								}
							});
						}
					});
				}
			};

			var stop = {
				text : '停用',
				width : 85,
				iconCls : 'stop',
				disabled : false,
				handler : function(bt) {
					showConfirm('确认停用吗？', grid, function(bt) {
						if (bt == 'yes') {
							var selectedRecord = grid.getSelectionModel()
									.getSelected();
							if (selectedRecord == null) {
								showAlertMsg("没有选择记录", grid);
								return;
							}
							if (selectedRecord.get("status") == '0') {
								showAlertMsg("已是停用状态", grid);
								return;
							}
							var mid = selectedRecord.get('modelId');
							if('1'===mid){
								showAlertMsg("系统默认的银联标准签购单模板不能停用！", grid);
								return;
							}
							Ext.Ajax.request({
								url : 'modelParameterAction.asp',
								params : {
									flag : "stop",
									modelId : mid
								},
								success: function (response, options) {
									if(response.responseText==null||response.responseText==""){
										Ext.Msg.alert('提示', '停用成功！');
										termStore.reload();
									}
									var rspObj = Ext.decode(response.responseText);
									if (rspObj.msg=="failure") {
										showAlertMsg('存在终端使用该模板,不可停用！',grid);
										termStore.reload();
									}
								}
							});
						}
					});
				}
			};
			
			var deleteButton = {
					text : '删除',
					width : 85,
					iconCls : 'delete',
					disabled : true,
					handler : function(bt) {
						showConfirm('确认删除吗？', grid, function(bt) {
							if (bt == 'yes') {
								var selectedRecord = grid.getSelectionModel()
										.getSelected();
								if (selectedRecord == null) {
									showAlertMsg("没有选择记录", grid);
									return;
								}
								if (selectedRecord.get("status") == '1') {
									showAlertMsg("只能删除已停用的模板！", grid);
									return;
								}
								var mid = selectedRecord.get('modelId');
								if('1'===mid){
									showAlertMsg("系统默认的银联标准签购单模板不能删除！", grid);
									return;
								}
								Ext.Ajax.request({
									url : 'modelParameterAction.asp',
									params : {
										flag : "delete",
										modelId : mid
									},
									success: function (response, options) {
										var rspObj = Ext.decode(response.responseText);
										if(rspObj.success){
											showAlertMsg("删除成功！", grid);
											termStore.reload();
										}
										if (rspObj.msg=="failure") {
											showAlertMsg('存在终端使用该模板,不可删除！',grid);
											termStore.reload();
										}
									}
								});
							}
						});
					}
				};
			
			var queryMenu = {
				text : '录入查询条件',
				width : 85,
				id : 'query',
				iconCls : 'query',
				handler : function() {
					queryWin.show();
				}
			};

			var menuArr = new Array();
			menuArr.push(addMenu); // [0]
			menuArr.push(queryMenu); // [1]
			menuArr.push(editMenu); // [2]
			menuArr.push(publish); // [3]发布按钮
			menuArr.push(stop); // [4]停用按钮
			menuArr.push(deleteButton); // [5]删除按钮
			// menuArr.push(qryMenu); // [5]

			// 终端信息列表
			var grid = new Ext.grid.GridPanel({
				title : '打印模板管理列表',
				region : 'center',
				frame : true,
				border : true,
				columnLines : true,
				stripeRows : true,
				store : termStore,
				sm : new Ext.grid.RowSelectionModel({
					singleSelect : true
				}),
				cm : termColModel,
				clicksToEdit : true,
				forceValidation : true,
				tbar : menuArr,
				renderTo : Ext.getBody(),
				loadMask : {
					msg : '正在加载签购单模板列表......'
				},
				bbar : new Ext.PagingToolbar({
					store : termStore,
					pageSize : System[QUERY_RECORD_COUNT],
					displayInfo : true,
					displayMsg : '显示第{0}-{1}条记录，共{2}条记录',
					emptyMsg : '没有找到符合条件的记录'
				}),
				listeners:{
					'cellclick':selectableCell
				}
			});

			grid.getSelectionModel().on({
						'rowselect' : function() {
							// 行高亮
							Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
							// 根据商户状态判断哪个编辑按钮可用
							rec = grid.getSelectionModel().getSelected();
							 if (rec.get('status') == '0') {
								 grid.getTopToolbar().items.items[5].enable();
								 grid.getTopToolbar().items.items[3].enable();
								 grid.getTopToolbar().items.items[4].disable();
							 } else {
								 grid.getTopToolbar().items.items[5].disable();
								 grid.getTopToolbar().items.items[4].enable();
								 grid.getTopToolbar().items.items[3].disable();
							 }
							 //grid.getTopToolbar().items.items[3].enable();
						}
					});

			var termPanel = new Ext.TabPanel({
						activeTab : 0,
						height : 500,
						width : 1150,
						defaults : {
							bodyStyle : 'padding-left: 5px;overflow-y: auto; '
						},
						frame : true,
						items : [ {
							title : '添加签购单打印模板',
							id : 'info1New',
							layout : 'column',
							frame : false,
							items : [
								{
									id : 'baseInfo1',
									xtype : 'fieldset',
									title : '商户参数配置',
									layout : 'column',
									collapsible : true,
							        //autoHeight:true,
							        style:'width:1130px;',
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
									{
										columnWidth : .4,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '模板名称',
											blankText : '模板名称不能为空',
											maxLength : 25,
											allowBlank : false,
											id : 'modelName0',
											name : 'modelName',
											readOnly : false,
											width : 300,
											listeners: {
												blur:function(textfield){
												     var value= textfield.getValue();
												     Ext.Ajax.request({
														url: 'modelParameterAction.asp',	   
														//async:false,
														params: {flag:"checkName","name" :value},
														method: 'POST',
														success: function (response, options) {
															var rspObj = Ext.decode(response.responseText);
															if (rspObj.msg=="failure") {
																showAlertMsg("名称:“"+value+"”已存在，请重新输入", grid);
																Ext.getCmp("modelName").setValue();
																return false;
															}
														}
												   });
												}
											}
										} ]
									},{
										columnWidth : .5,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '打印联数',
											allowBlank : false,
											id : 'printNumber_add',
											name : 'printNumber',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '2', '2' ],
														[ '3', '3' ] ]
											})
										} ]
									},{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '抬头',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName0',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status0',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status0').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName0').disable();
								                    		Ext.getCmp('fontSize0').disable();
								                    		Ext.getCmp('fieldOrder0').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName0').enable();
								                    		Ext.getCmp('fontSize0').enable();
							                     			Ext.getCmp('fieldOrder0').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '3',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize0',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ 
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '1',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder0',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder0').getValue(),0);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType0',
											hiddenName : 'contFormatType',
											value : '1',
											//readOnly : true,
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ]]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 100,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat0',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户名',
											value : '商户名',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName1',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status1',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status1').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName1').disable();
								                    		Ext.getCmp('fieldOrder1').disable();
								                    		Ext.getCmp('fontSize1').disable();
								                    		Ext.getCmp('contFormat1').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName1').enable();
							                     			Ext.getCmp('fieldOrder1').enable();
								                    		Ext.getCmp('fontSize1').enable();
								                    		Ext.getCmp('contFormat1').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize1',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '2',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder1',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder1').getValue(),1);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType1',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormat1',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户号',
											value : '商户号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName2',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status2',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status2').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName2').disable();
								                    		Ext.getCmp('fieldOrder2').disable();
								                    		Ext.getCmp('fontSize2').disable();
								                    		Ext.getCmp('contFormat2').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName2').enable();
							                     			Ext.getCmp('fieldOrder2').enable();
								                    		Ext.getCmp('fontSize2').enable();
								                    		Ext.getCmp('contFormat2').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize2',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '3',
											maxLength : 2,
											blankText: '不能为空且不可重复',
											allowBlank : false,
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder2',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder2').getValue(),2);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											id : 'contFormatType2',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormat2',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											}),
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '终端号',
											value : '终端号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName3',
											name : 'fieldName',
											width : 100
										}]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status3',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status3').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName3').disable();
								                    		Ext.getCmp('fieldOrder3').disable();
								                    		Ext.getCmp('fontSize3').disable();
								                    		Ext.getCmp('contFormat3').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName3').enable();
							                     			Ext.getCmp('fieldOrder3').enable();
								                    		Ext.getCmp('fontSize3').enable();
								                    		Ext.getCmp('contFormat3').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize3',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '4',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder3',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder3').getValue(),3);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType3',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormat3',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '发卡行',
											value : '发卡行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName4',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status4',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status4').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName4').disable();
								                    		Ext.getCmp('fieldOrder4').disable();
								                    		Ext.getCmp('fontSize4').disable();
								                    		Ext.getCmp('contFormat4').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName4').enable();
							                     			Ext.getCmp('fieldOrder4').enable();
								                    		Ext.getCmp('fontSize4').enable();
								                    		Ext.getCmp('contFormat4').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize4',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '5',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder4',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder4').getValue(),4);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType4',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat4',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '收单行',
											value : '收单行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName5',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status5',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status5').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName5').disable();
								                    		Ext.getCmp('fieldOrder5').disable();
								                    		Ext.getCmp('fontSize5').disable();
								                    		Ext.getCmp('contFormat5').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName5').enable();
							                     			Ext.getCmp('fieldOrder5').enable();
								                    		Ext.getCmp('fontSize5').enable();
								                    		Ext.getCmp('contFormat5').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize5',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]  ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '6',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder5',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder5').getValue(),5);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType5',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormat5',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '卡号',
											value : '卡号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName6',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status6',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status6').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName6').disable();
								                    		Ext.getCmp('fieldOrder6').disable();
								                    		Ext.getCmp('fontSize6').disable();
								                    		Ext.getCmp('contFormat6').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName6').enable();
							                     			Ext.getCmp('fieldOrder6').enable();
								                    		Ext.getCmp('fontSize6').enable();
								                    		Ext.getCmp('contFormat6').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize6',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [
														[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '7',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder6',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder6').getValue(),6);
											       }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType6',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat6',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '批次号',
											value : '批次号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName7',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status7',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status7').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName7').disable();
								                    		Ext.getCmp('fieldOrder7').disable();
								                    		Ext.getCmp('fontSize7').disable();
								                    		Ext.getCmp('contFormat7').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName7').enable();
							                     			Ext.getCmp('fieldOrder7').enable();
								                    		Ext.getCmp('fontSize7').enable();
								                    		Ext.getCmp('contFormat7').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize7',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '8',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder7',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder7').getValue(),7);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType7',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat7',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '流水号',
											value : '流水号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName8',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status8',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status8').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName8').disable();
								                    		Ext.getCmp('fieldOrder8').disable();
								                    		Ext.getCmp('fontSize8').disable();
								                    		Ext.getCmp('contFormat8').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName8').enable();
							                     			Ext.getCmp('fieldOrder8').enable();
								                    		Ext.getCmp('fontSize8').enable();
								                    		Ext.getCmp('contFormat8').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize8',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '9',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder8',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder8').getValue(),8);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType8',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat8',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '授权码',
											value : '授权码',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName9',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status9',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status9').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName9').disable();
								                    		Ext.getCmp('fieldOrder9').disable();
								                    		Ext.getCmp('fontSize9').disable();
								                    		Ext.getCmp('contFormat9').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName9').enable();
							                     			Ext.getCmp('fieldOrder9').enable();
								                    		Ext.getCmp('fontSize9').enable();
								                    		Ext.getCmp('contFormat9').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize9',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '10',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder9',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder9').getValue(),9);
											      }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType9',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat9',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '参考号',
											value : '参考号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName10',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status10',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status10').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName10').disable();
								                    		Ext.getCmp('fieldOrder10').disable();
								                    		Ext.getCmp('fontSize10').disable();
								                    		Ext.getCmp('contFormat10').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName10').enable();
							                     			Ext.getCmp('fieldOrder10').enable();
								                    		Ext.getCmp('fontSize10').enable();
								                    		Ext.getCmp('contFormat10').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize10',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .35,//15
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '11',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder10',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder10').getValue(),10);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType10',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,//35
										hidden: true,//false
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 4,
											regex : /^[+|-]?[0-9]+([.][0-9]+){0,1}$/,
											allowBlank : true,
											id : 'contFormat10',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '日期时间',
											value : '日期时间',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName11',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status11',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status11').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName11').disable();
								                    		Ext.getCmp('fieldOrder11').disable();
								                    		Ext.getCmp('fontSize11').disable();
								                    		Ext.getCmp('contFormat11').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName11').enable();
							                     			Ext.getCmp('fieldOrder11').enable();
								                    		Ext.getCmp('fontSize11').enable();
								                    		Ext.getCmp('contFormat11').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize11',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '12',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder11',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder11').getValue(),11);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType11',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormat11',
											name : 'contFormat',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '-30', '-30' ],
														[ '-29', '-29' ], 
														[ '-28', '-28' ],
														[ '-27', '-27' ], 
														[ '-26', '-26' ],
														[ '-25', '-25' ], 
														[ '-24', '-24' ],
														[ '-23', '-23' ], 
														[ '-22', '-22' ],
														[ '-21', '-21' ],
												        [ '-20', '-20' ],
														[ '-19', '-19' ], 
														[ '-18', '-18' ],
														[ '-17', '-17' ], 
														[ '-16', '-16' ],
														[ '-15', '-15' ], 
														[ '-14', '-14' ],
														[ '-13', '-13' ], 
														[ '-12', '-12' ],
														[ '-11', '-11' ],
												        [ '-10', '-10' ],
														[ '-09', '-09' ], 
														[ '-08', '-08' ],
														[ '-07', '-07' ], 
														[ '-06', '-06' ],
														[ '-05', '-05' ], 
														[ '-04', '-04' ],
														[ '-03', '-03' ], 
														[ '-02', '-02' ],
														[ '-01', '-01' ],
														[ '0', '0' ],
												        [ '1', '1' ],
														[ '2', '2' ], 
														[ '3', '3' ],
														[ '4', '4' ], 
														[ '5', '5' ],
														[ '6', '6' ], 
														[ '7', '7' ],
														[ '8', '8' ], 
														[ '9', '9' ],
														[ '10', '10' ],
												        [ '11', '11' ],
														[ '12', '12' ], 
														[ '13', '13' ],
														[ '14', '14' ], 
														[ '15', '15' ],
														[ '16', '16' ], 
														[ '17', '17' ],
														[ '18', '18' ], 
														[ '19', '19' ],
														[ '20', '20' ],
												        [ '21', '21' ],
														[ '22', '22' ], 
														[ '23', '23' ],
														[ '24', '24' ], 
														[ '25', '25' ],
														[ '26', '26' ], 
														[ '27', '27' ],
														[ '28', '28' ], 
														[ '29', '29' ],
														[ '30', '30' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '金额',
											value : '金额',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldName12',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status12',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status12').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName12').disable();
								                    		Ext.getCmp('fieldOrder12').disable();
								                    		Ext.getCmp('fontSize12').disable();
								                    		Ext.getCmp('contFormat12').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName12').enable();
							                     			Ext.getCmp('fieldOrder12').enable();
								                    		Ext.getCmp('fontSize12').enable();
								                    		Ext.getCmp('contFormat12').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize12',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '13',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder12',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder12').getValue(),12);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType12',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat12',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '备注',
											value : '备注',
											maxLength : 10,
											allowBlank : true,
											id : 'fieldName13',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status13',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status13').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName13').disable();
								                    		Ext.getCmp('fieldOrder13').disable();
								                    		Ext.getCmp('fontSize13').disable();
								                    		Ext.getCmp('contFormat13').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName13').enable();
							                     			Ext.getCmp('fieldOrder13').enable();
								                    		Ext.getCmp('fontSize13').enable();
								                    		Ext.getCmp('contFormat13').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize13',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '14',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder13',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder13').getValue(),13);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType13',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat13',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '说明文字',
											maxLength : 10,
											hidden: true,
											allowBlank : true,
											id : 'fieldName14',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status14',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status14').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName14').disable();
								                    		Ext.getCmp('fieldOrder14').disable();
								                    		Ext.getCmp('fontSize14').disable();
								                    		Ext.getCmp('contFormat14').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName14').enable();
							                     			Ext.getCmp('fieldOrder14').enable();
								                    		Ext.getCmp('fontSize14').enable();
								                    		Ext.getCmp('contFormat14').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize14',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '15',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder14',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder14').getValue(),14);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType14',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormat14',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留1',
											maxLength : 10,
											id : 'fieldName15',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status15',
											hiddenName : 'status',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status15').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName15').disable();
								                    		Ext.getCmp('fieldOrder15').disable();
								                    		Ext.getCmp('fontSize15').disable();
								                    		Ext.getCmp('contFormat15').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName15').enable();
							                     			Ext.getCmp('fieldOrder15').enable();
								                    		Ext.getCmp('fontSize15').enable();
								                    		Ext.getCmp('contFormat15').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize15',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '16',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder15',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder15').getValue(),15);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType15',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormat15',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留2',
											maxLength : 10,
											id : 'fieldName16',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status16',
											hiddenName : 'status',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status16').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName16').disable();
								                    		Ext.getCmp('fieldOrder16').disable();
								                    		Ext.getCmp('fontSize16').disable();
								                    		Ext.getCmp('contFormat16').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName16').enable();
							                     			Ext.getCmp('fieldOrder16').enable();
								                    		Ext.getCmp('fontSize16').enable();
								                    		Ext.getCmp('contFormat16').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize16',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '17',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder16',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder16').getValue(),16);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType16',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormat16',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留3',
											maxLength : 10,
											id : 'fieldName17',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status17',
											hiddenName : 'status',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status17').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName17').disable();
								                    		Ext.getCmp('fieldOrder17').disable();
								                    		Ext.getCmp('fontSize17').disable();
								                    		Ext.getCmp('contFormat17').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName17').enable();
							                     			Ext.getCmp('fieldOrder17').enable();
								                    		Ext.getCmp('fontSize17').enable();
								                    		Ext.getCmp('contFormat17').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize17',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '18',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder17',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder17').getValue(),17);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType17',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormat17',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留4',
											maxLength : 10,
											id : 'fieldName18',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status18',
											hiddenName : 'status',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status18').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName18').disable();
								                    		Ext.getCmp('fieldOrder18').disable();
								                    		Ext.getCmp('fontSize18').disable();
								                    		Ext.getCmp('contFormat18').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName18').enable();
							                     			Ext.getCmp('fieldOrder18').enable();
								                    		Ext.getCmp('fontSize18').enable();
								                    		Ext.getCmp('contFormat18').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize18',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '19',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder18',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder18').getValue(),18);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType18',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormat18',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留5',
											maxLength : 10,
											id : 'fieldName19',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'status19',
											hiddenName : 'status',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('status19').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldName19').disable();
								                    		Ext.getCmp('fieldOrder19').disable();
								                    		Ext.getCmp('fontSize19').disable();
								                    		Ext.getCmp('contFormat19').disable();
								                    	}else{
							                     			Ext.getCmp('fieldName19').enable();
							                     			Ext.getCmp('fieldOrder19').enable();
								                    		Ext.getCmp('fontSize19').enable();
								                    		Ext.getCmp('contFormat19').enable();
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSize19',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '20',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrder19',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compare(Ext.getCmp('fieldOrder19').getValue(),19);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatType19',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									}, {
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormat19',
											name : 'contFormat',
										} ]
									}
									
									]}

					,{
						id : 'baseInfo2',
						xtype : 'fieldset',
						title : '持卡人参数配置',
						layout : 'column',
						collapsible : true,
				        //autoHeight:true,
				        style:'width:1130px;',
						defaults : {
							bodyStyle : 'padding-left: 20px'
						},
						items : [
							{
								columnWidth : .9,
								layout : 'form',
								items : [{
									xtype: 'button',
									text: '复制当前商户模板所有参数到持卡人模板',
									width: '200',
									id: 'view3',
									handler:function() {
										//将商户模板的参数值赋值到持卡人模板参数中
										for (var i = 20; i < 40; i++) {
											termForm.getForm().findField("fieldName" + i).setValue(Ext.getCmp('fieldName' + (i-20)).getValue());
											termForm.getForm().findField("status" + i).setValue(Ext.getCmp('status' + (i-20)).getValue());
											termForm.getForm().findField("fontSize" + i).setValue(Ext.getCmp('fontSize' + (i-20)).getValue());
											termForm.getForm().findField("fieldOrder" + i).setValue(Ext.getCmp('fieldOrder' + (i-20)).getValue());
											termForm.getForm().findField("contFormatType" + i).setValue(Ext.getCmp('contFormatType' + (i-20)).getValue());
											termForm.getForm().findField("contFormat" + i).setValue(Ext.getCmp('contFormat' + (i-20)).getValue());
											//若字段状态为停用，则对应的其他几个字段改为不可用状态
											var status = Ext.getCmp('status' + i).getValue();
					                    	if('0' == status){
					                    		Ext.getCmp('fieldName'+i).disable();
					                    		Ext.getCmp('fontSize'+i).disable();
					                    		Ext.getCmp('fieldOrder'+i).disable();
					                    		Ext.getCmp('contFormat'+i).disable();
					                    	}else {
					                    		Ext.getCmp('fieldName'+i).enable();
					                    		Ext.getCmp('fontSize'+i).enable();
					                    		Ext.getCmp('fieldOrder'+i).enable();
					                    		Ext.getCmp('contFormat'+i).enable();

					                    		if(13<i<20 || 33<i<40){
						                    		//Ext.getCmp('fieldName' + i).allowBlank = false;
						                    		Ext.getCmp('fontSize' + i).allowBlank = false;
						                    		Ext.getCmp('fieldOrder' + i).allowBlank = false;
						                    		//Ext.getCmp('contFormat' + i).allowBlank = false;
					                    		}else {
						                    		Ext.getCmp('fieldName' + i).allowBlank = false;
						                    		Ext.getCmp('fontSize' + i).allowBlank = false;
						                    		Ext.getCmp('fieldOrder' + i).allowBlank = false;
						                    		Ext.getCmp('contFormat' + i).allowBlank = false;
												}
											
											}
										}
									}
								
								}]
							},
        
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '抬头',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName20',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status20',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status20').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName20').disable();
					                    		Ext.getCmp('fontSize20').disable();
					                    		Ext.getCmp('fieldOrder20').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName20').enable();
					                    		Ext.getCmp('fontSize20').enable();
				                     			Ext.getCmp('fieldOrder20').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '3',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize20',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ 
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '1',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder20',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder20').getValue(),20);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType20',
								hiddenName : 'contFormatType',
								value : '1',
								//readOnly : true,
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ]]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 100,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat20',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '商户名',
								value : '商户名',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName21',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status21',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status21').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName21').disable();
					                    		Ext.getCmp('fieldOrder21').disable();
					                    		Ext.getCmp('fontSize21').disable();
					                    		Ext.getCmp('contFormat21').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName21').enable();
				                     			Ext.getCmp('fieldOrder21').enable();
					                    		Ext.getCmp('fontSize21').enable();
					                    		Ext.getCmp('contFormat21').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize21',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '2',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder21',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder21').getValue(),21);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType21',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容',
								width : 256,
								maxLength : 50,
								allowBlank : false,
								id : 'contFormat21',
								hiddenName : 'contFormat',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '使用钱宝系统的' ],
											[ '2', '使用上游系统的' ] ]
								})
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '商户号',
								value : '商户号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName22',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status22',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status22').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName22').disable();
					                    		Ext.getCmp('fieldOrder22').disable();
					                    		Ext.getCmp('fontSize22').disable();
					                    		Ext.getCmp('contFormat22').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName22').enable();
				                     			Ext.getCmp('fieldOrder22').enable();
					                    		Ext.getCmp('fontSize22').enable();
					                    		Ext.getCmp('contFormat22').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize22',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '3',
								maxLength : 2,
								blankText: '不能为空且不可重复',
								allowBlank : false,
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder22',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder22').getValue(),22);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								id : 'contFormatType22',
								hiddenName : 'contFormatType',
								value : '2',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容',
								width : 256,
								maxLength : 50,
								allowBlank : false,
								id : 'contFormat22',
								hiddenName : 'contFormat',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '使用钱宝系统的' ],
											[ '2', '使用上游系统的' ] ]
								}),
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '终端号',
								value : '终端号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName23',
								name : 'fieldName',
								width : 100
							}]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status23',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status23').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName23').disable();
					                    		Ext.getCmp('fieldOrder23').disable();
					                    		Ext.getCmp('fontSize23').disable();
					                    		Ext.getCmp('contFormat23').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName23').enable();
				                     			Ext.getCmp('fieldOrder23').enable();
					                    		Ext.getCmp('fontSize23').enable();
					                    		Ext.getCmp('contFormat23').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize23',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '4',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder23',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder23').getValue(),23);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType23',
								hiddenName : 'contFormatType',
								value : '2',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容',
								width : 256,
								maxLength : 50,
								allowBlank : false,
								id : 'contFormat23',
								hiddenName : 'contFormat',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '使用钱宝系统的' ],
											[ '2', '使用上游系统的' ] ]
								})
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '发卡行',
								value : '发卡行',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName24',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status24',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status24').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName24').disable();
					                    		Ext.getCmp('fieldOrder24').disable();
					                    		Ext.getCmp('fontSize24').disable();
					                    		Ext.getCmp('contFormat24').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName24').enable();
				                     			Ext.getCmp('fieldOrder24').enable();
					                    		Ext.getCmp('fontSize24').enable();
					                    		Ext.getCmp('contFormat24').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize24',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '5',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder24',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder24').getValue(),24);
								      }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType24',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat24',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '收单行',
								value : '收单行',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName25',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status25',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status25').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName25').disable();
					                    		Ext.getCmp('fieldOrder25').disable();
					                    		Ext.getCmp('fontSize25').disable();
					                    		Ext.getCmp('contFormat25').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName25').enable();
				                     			Ext.getCmp('fieldOrder25').enable();
					                    		Ext.getCmp('fontSize25').enable();
					                    		Ext.getCmp('contFormat25').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize25',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]  ]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '6',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder25',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder25').getValue(),25);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType25',
								hiddenName : 'contFormatType',
								value : '2',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容',
								width : 256,
								maxLength : 50,
								allowBlank : false,
								id : 'contFormat25',
								hiddenName : 'contFormat',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '使用钱宝系统的' ],
											[ '2', '使用上游系统的' ] ]
								})
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '卡号',
								value : '卡号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName26',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status26',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status26').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName26').disable();
					                    		Ext.getCmp('fieldOrder26').disable();
					                    		Ext.getCmp('fontSize26').disable();
					                    		Ext.getCmp('contFormat26').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName26').enable();
				                     			Ext.getCmp('fieldOrder26').enable();
					                    		Ext.getCmp('fontSize26').enable();
					                    		Ext.getCmp('contFormat26').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize26',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [
											[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '7',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder26',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder26').getValue(),26);
								       }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType26',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat26',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '批次号',
								value : '批次号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName27',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status27',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status27').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName27').disable();
					                    		Ext.getCmp('fieldOrder27').disable();
					                    		Ext.getCmp('fontSize27').disable();
					                    		Ext.getCmp('contFormat27').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName27').enable();
				                     			Ext.getCmp('fieldOrder27').enable();
					                    		Ext.getCmp('fontSize27').enable();
					                    		Ext.getCmp('contFormat27').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize27',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '8',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder27',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder27').getValue(),27);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType27',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat27',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '流水号',
								value : '流水号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName28',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status28',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status28').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName28').disable();
					                    		Ext.getCmp('fieldOrder28').disable();
					                    		Ext.getCmp('fontSize28').disable();
					                    		Ext.getCmp('contFormat28').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName28').enable();
				                     			Ext.getCmp('fieldOrder28').enable();
					                    		Ext.getCmp('fontSize28').enable();
					                    		Ext.getCmp('contFormat28').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize28',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '9',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder28',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder28').getValue(),28);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType28',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat28',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '授权码',
								value : '授权码',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName29',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status29',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status29').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName29').disable();
					                    		Ext.getCmp('fieldOrder29').disable();
					                    		Ext.getCmp('fontSize29').disable();
					                    		Ext.getCmp('contFormat29').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName29').enable();
				                     			Ext.getCmp('fieldOrder29').enable();
					                    		Ext.getCmp('fontSize29').enable();
					                    		Ext.getCmp('contFormat29').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize29',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ] ]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '10',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder29',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder29').getValue(),29);
								      }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType29',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat29',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '参考号',
								value : '参考号',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName30',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status30',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status30').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName30').disable();
					                    		Ext.getCmp('fieldOrder30').disable();
					                    		Ext.getCmp('fontSize30').disable();
					                    		Ext.getCmp('contFormat30').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName30').enable();
				                     			Ext.getCmp('fieldOrder30').enable();
					                    		Ext.getCmp('fontSize30').enable();
					                    		Ext.getCmp('contFormat30').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize30',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .35,//15
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '11',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder30',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder30').getValue(),30);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType30',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .15,//35
							layout : 'form',
							hidden: true,//false
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 4,
								regex : /^[+|-]?[0-9]+([.][0-9]+){0,1}$/,
								allowBlank : true,
								id : 'contFormat30',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '日期时间',
								value : '日期时间',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName31',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status31',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status31').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName31').disable();
					                    		Ext.getCmp('fieldOrder31').disable();
					                    		Ext.getCmp('fontSize31').disable();
					                    		Ext.getCmp('contFormat31').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName31').enable();
				                     			Ext.getCmp('fieldOrder31').enable();
					                    		Ext.getCmp('fontSize31').enable();
					                    		Ext.getCmp('contFormat31').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize31',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '12',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder31',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder31').getValue(),31);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType31',
								hiddenName : 'contFormatType',
								value : '2',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容',
								width : 256,
								maxLength : 50,
								allowBlank : false,
								id : 'contFormat31',
								name : 'contFormat',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '-30', '-30' ],
											[ '-29', '-29' ], 
											[ '-28', '-28' ],
											[ '-27', '-27' ], 
											[ '-26', '-26' ],
											[ '-25', '-25' ], 
											[ '-24', '-24' ],
											[ '-23', '-23' ], 
											[ '-22', '-22' ],
											[ '-21', '-21' ],
									        [ '-20', '-20' ],
											[ '-19', '-19' ], 
											[ '-18', '-18' ],
											[ '-17', '-17' ], 
											[ '-16', '-16' ],
											[ '-15', '-15' ], 
											[ '-14', '-14' ],
											[ '-13', '-13' ], 
											[ '-12', '-12' ],
											[ '-11', '-11' ],
									        [ '-10', '-10' ],
											[ '-09', '-09' ], 
											[ '-08', '-08' ],
											[ '-07', '-07' ], 
											[ '-06', '-06' ],
											[ '-05', '-05' ], 
											[ '-04', '-04' ],
											[ '-03', '-03' ], 
											[ '-02', '-02' ],
											[ '-01', '-01' ],
											[ '0', '0' ],
									        [ '1', '1' ],
											[ '2', '2' ], 
											[ '3', '3' ],
											[ '4', '4' ], 
											[ '5', '5' ],
											[ '6', '6' ], 
											[ '7', '7' ],
											[ '8', '8' ], 
											[ '9', '9' ],
											[ '10', '10' ],
									        [ '11', '11' ],
											[ '12', '12' ], 
											[ '13', '13' ],
											[ '14', '14' ], 
											[ '15', '15' ],
											[ '16', '16' ], 
											[ '17', '17' ],
											[ '18', '18' ], 
											[ '19', '19' ],
											[ '20', '20' ],
									        [ '21', '21' ],
											[ '22', '22' ], 
											[ '23', '23' ],
											[ '24', '24' ], 
											[ '25', '25' ],
											[ '26', '26' ], 
											[ '27', '27' ],
											[ '28', '28' ], 
											[ '29', '29' ],
											[ '30', '30' ] ]
								})
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '金额',
								value : '金额',
								blankText : '不能为空',
								maxLength : 10,
								allowBlank : false,
								id : 'fieldName32',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status32',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status32').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName32').disable();
					                    		Ext.getCmp('fieldOrder32').disable();
					                    		Ext.getCmp('fontSize32').disable();
					                    		Ext.getCmp('contFormat32').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName32').enable();
				                     			Ext.getCmp('fieldOrder32').enable();
					                    		Ext.getCmp('fontSize32').enable();
					                    		Ext.getCmp('contFormat32').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize32',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .45,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '13',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder32',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder32').getValue(),32);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType32',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat32',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '备注',
								value : '备注',
								maxLength : 10,
								allowBlank : true,
								id : 'fieldName33',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status33',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status33').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName33').disable();
					                    		Ext.getCmp('fieldOrder33').disable();
					                    		Ext.getCmp('fontSize33').disable();
					                    		Ext.getCmp('contFormat33').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName33').enable();
				                     			Ext.getCmp('fieldOrder33').enable();
					                    		Ext.getCmp('fontSize33').enable();
					                    		Ext.getCmp('contFormat33').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize33',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '14',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder33',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder33').getValue(),33);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType33',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat33',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '说明文字',
								maxLength : 10,
								hidden: true,
								allowBlank : true,
								id : 'fieldName34',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status34',
								hiddenName : 'status',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status34').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName34').disable();
					                    		Ext.getCmp('fieldOrder34').disable();
					                    		Ext.getCmp('fontSize34').disable();
					                    		Ext.getCmp('contFormat34').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName34').enable();
				                     			Ext.getCmp('fieldOrder34').enable();
					                    		Ext.getCmp('fontSize34').enable();
					                    		Ext.getCmp('contFormat34').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize34',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '15',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder34',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder34').getValue(),34);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType34',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								allowBlank : true,
								id : 'contFormat34',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '保留1',
								maxLength : 10,
								id : 'fieldName35',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status35',
								hiddenName : 'status',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status35').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName35').disable();
					                    		Ext.getCmp('fieldOrder35').disable();
					                    		Ext.getCmp('fontSize35').disable();
					                    		Ext.getCmp('contFormat35').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName35').enable();
				                     			Ext.getCmp('fieldOrder35').enable();
					                    		Ext.getCmp('fontSize35').enable();
					                    		Ext.getCmp('contFormat35').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize35',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '16',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder35',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder35').getValue(),35);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType35',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								id : 'contFormat35',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '保留2',
								maxLength : 10,
								id : 'fieldName36',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status36',
								hiddenName : 'status',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status36').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName36').disable();
					                    		Ext.getCmp('fieldOrder36').disable();
					                    		Ext.getCmp('fontSize36').disable();
					                    		Ext.getCmp('contFormat36').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName36').enable();
				                     			Ext.getCmp('fieldOrder36').enable();
					                    		Ext.getCmp('fontSize36').enable();
					                    		Ext.getCmp('contFormat36').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize36',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '17',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder36',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder36').getValue(),36);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType36',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								id : 'contFormat36',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '保留3',
								maxLength : 10,
								id : 'fieldName37',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status37',
								hiddenName : 'status',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status37').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName37').disable();
					                    		Ext.getCmp('fieldOrder37').disable();
					                    		Ext.getCmp('fontSize37').disable();
					                    		Ext.getCmp('contFormat37').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName37').enable();
				                     			Ext.getCmp('fieldOrder37').enable();
					                    		Ext.getCmp('fontSize37').enable();
					                    		Ext.getCmp('contFormat37').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize37',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '18',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder37',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder37').getValue(),37);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType37',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								id : 'contFormat37',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '保留4',
								maxLength : 10,
								id : 'fieldName38',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status38',
								hiddenName : 'status',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status38').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName38').disable();
					                    		Ext.getCmp('fieldOrder38').disable();
					                    		Ext.getCmp('fontSize38').disable();
					                    		Ext.getCmp('contFormat38').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName38').enable();
				                     			Ext.getCmp('fieldOrder38').enable();
					                    		Ext.getCmp('fontSize38').enable();
					                    		Ext.getCmp('contFormat38').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize38',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '19',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder38',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder38').getValue(),38);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType38',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						},
						{
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								id : 'contFormat38',
								name : 'contFormat',
							} ]
						},
						{
							columnWidth : .20,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '保留5',
								maxLength : 10,
								id : 'fieldName39',
								name : 'fieldName',
								width : 100
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '是否启用*',
								id : 'status39',
								hiddenName : 'status',
								value : '0',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '启用' ],
											[ '0', '停用' ] ]
								}),
								listeners: {
								      'select': function() {
										    var status = Ext.getCmp('status39').getValue();
					                    	if(status == '0'){
					                    		Ext.getCmp('fieldName39').disable();
					                    		Ext.getCmp('fieldOrder39').disable();
					                    		Ext.getCmp('fontSize39').disable();
					                    		Ext.getCmp('contFormat39').disable();
					                    	}else{
				                     			Ext.getCmp('fieldName39').enable();
				                     			Ext.getCmp('fieldOrder39').enable();
					                    		Ext.getCmp('fontSize39').enable();
					                    		Ext.getCmp('contFormat39').enable();
				                     		}
								        }
								},
								width : 50
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'combo',
								fieldLabel : '字体大小*',
								width : 50,
								value : '1',
								displayField : 'displayField',
								valueField : 'valueField',
								emptyText : '请选择字体大小',
								blankText : '字体大小不能为空',
								id : 'fontSize39',
								allowBlank : false,
								hiddenName : 'fontSize',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [[ '1', '小' ],
											[ '2', '中' ],
											[ '3', '大' ]]
								})
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								fieldLabel : '字段顺序',
								value : '20',
								maxLength : 2,
								allowBlank : false,
								blankText: '不能为空且不可重复',
								regex : /(\d{1,2})$/,
								width : 40,
								id : 'fieldOrder39',
								name : 'fieldOrder',
								listeners: {
								      blur: function() {
								    	  compareTwo(Ext.getCmp('fieldOrder39').getValue(),39);
								        }
								}
							} ]
						},
						{
							columnWidth : .15,
							layout : 'form',
							hidden: true,
							items : [ {
								xtype : 'combo',
								fieldLabel : '内容类型*',
								width : 40,
								id : 'contFormatType39',
								hiddenName : 'contFormatType',
								value : '1',
								store : new Ext.data.ArrayStore({
									fields : [ 'valueField',
											'displayField' ],
									data : [ [ '1', '自定义' ],
											[ '2', '枚举' ] ]
								}),
								width : 80
							} ]
						}, {
							columnWidth : .35,
							layout : 'form',
							items : [ {
								xtype : 'textfield',
								width : 256,
								fieldLabel : '内容',
								maxLength : 50,
								id : 'contFormat39',
								name : 'contFormat',
							} ]
						}]

						}
									
							]} 
						]
					});

			/** ************ 终端表单 ******************** */
			var termForm = new Ext.form.FormPanel({
				frame : true,
				height : 450,
				width : 620,
				labelWidth : 60,
				waitMsgTarget : true,
				layout : 'fit',
				items : [ termPanel ]
			});

			/** ********* 终端信息窗口 **************** */
			var termWin = new Ext.Window({
						title : '签购单模板添加',
						initHidden : true,
						header : true,
						frame : true,
						closable : false,
						modal : true,
						width : 1150,
						/*closeAction: 'hide',
						autoHeight : true,*/
						layout : 'fit',
						items : [ termForm ],
						buttonAlign : 'center',
						closeAction : 'hide',
						iconCls : 'logo',
						resizable : false,
						buttons : [
								{
									text : '确定',
									handler : function() {
										if (termForm.getForm().isValid()) {
											termForm.getForm().submitNeedAuthorise({
																url : 'modelParameterAction.asp?flag=add',
																waitMsg : '正在提交，请稍后......',
																success : function(form,action) {
																	showSuccessMsg(action.result.msg,termWin);
																	// 重新加载参数列表
																	grid.getStore().reload();
																	// 重置表单
																	termForm.getForm().reset();
																	//termForm.getForm().findField("modelName0").reset();
																	//termForm.getForm().findField("fieldName0").reset();
																	termWin.hide();
																},
																failure : function(form,action) {
																	showErrorMsg(action.result.msg,termWin);
																},
																params : {
																	txnId : '30101',
																	subTxnId : '01'
																}
															});
										} else {
											var finded = true;
											termForm.getForm().items.each(function(f) {
														if (finded&& !f.validate()) {
															var tab = f.ownerCt.ownerCt.id;
															finded = false;
														}
													});
											showErrorMsg("页面数据不合法，或存在必填字段未填写，请核对后再提交！",termForm);
										}
									}
								}, {
									text : '重置',
									handler : function() {
										termForm.getForm().reset();
									}
								}, {
									text : '关闭',
									handler : function() {
										termWin.hide();
									}
								} ]
					});

			/** ************** 签购单模板数据修改 ************************ */
			var updTermPanel = new Ext.TabPanel({
						activeTab : 0,
						height : 430,
						width : 1130,
		                //bodyStyle : 'overflow-x:hidden; overflow-y:scroll',
						defaults : {
							bodyStyle : 'padding-left: 5px; overflow-y: auto; '
						},
						frame : true,
						items : [ {
							title : '签购单模板数据修改',
							id : 'info1Upd',
							layout : 'column',
							items : [
								{
									id : 'baseInfo3',
									xtype : 'fieldset',
									title : '商户参数配置',
									layout : 'column',
									collapsible : true,
							        //autoHeight:true,
							        style:'width:1130px;',
									defaults : {
										bodyStyle : 'padding-left: 20px'
									},
									items : [
									{
										columnWidth : .4,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '模板名称',
											blankText : '模板名称不能为空',
											maxLength : 25,
											allowBlank : false,
											id : 'modelNameUpd0',
											name : 'modelName',
											//readOnly : false,
											width : 300,
											listeners: {
												'change' :function(textfield){
												     var value= textfield.getValue();
												     Ext.Ajax.request({
														url: 'modelParameterAction.asp',	   
														//async:false,
														params: {flag:"checkName","name" :value},
														method: 'POST',
														success: function (response, options) {
															var rspObj = Ext.decode(response.responseText);
															if (rspObj.msg=="failure") {
																showAlertMsg("名称:“"+value+"”已存在，请重新输入", grid);
																Ext.getCmp("modelNameUpd0").setValue();
																return false;
															}
														}
												   });
												}
											}
										} ]
										},{
											columnWidth : .5,
											layout : 'form',
											items : [ {
												xtype : 'combo',
												fieldLabel : '打印联数',
												allowBlank : false,
												id : 'printNumber_upd',
												name : 'printNumber',
												//hiddenName : 'printNumber',
												value : '2',
												store : new Ext.data.ArrayStore({
													fields : [ 'valueField',
															'displayField' ],
													data : [ [ '2', '2' ],
															[ '3', '3' ] ]
												})
											} ]
										
										},{
										columnWidth : .3,
										layout : 'form',
										hidden : true,
										items : [ {
											xtype : 'textfield',
											fieldLabel : '模板ID',
											id : 'modelIdUpd0',
											name : 'modelId',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '抬头',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd0',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd0',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd0').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd0').disable();
								                    		Ext.getCmp('fieldOrderUpd0').disable();
								                    		Ext.getCmp('fontSizeUpd0').disable();
								                    		Ext.getCmp('contFormatUpd0').disable();
															Ext.getCmp('fieldNameUpd0').allowBlank = true;
															Ext.getCmp('fieldOrderUpd0').allowBlank = true;
															Ext.getCmp('fontSizeUpd0').allowBlank = true;
														}else{
								                    		Ext.getCmp('fieldNameUpd0').enable();
								                    		Ext.getCmp('fieldOrderUpd0').enable();
								                    		Ext.getCmp('fontSizeUpd0').enable();
								                    		Ext.getCmp('contFormatUpd0').enable();
															Ext.getCmp('fieldNameUpd0').allowBlank = false;
															Ext.getCmp('fieldOrderUpd0').allowBlank = false;
															Ext.getCmp('fontSizeUpd0').allowBlank = false;
							                     		}
													},
													'change': function() {
													    var status = Ext.getCmp('statusUpd0').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd0').disable();
								                    		Ext.getCmp('fieldOrderUpd0').disable();
								                    		Ext.getCmp('fontSizeUpd0').disable();
								                    		Ext.getCmp('contFormatUpd0').disable();
															Ext.getCmp('fieldNameUpd0').allowBlank = true;
															Ext.getCmp('fieldOrderUpd0').allowBlank = true;
															Ext.getCmp('fontSizeUpd0').allowBlank = true;
														}else{
								                    		Ext.getCmp('fieldNameUpd0').enable();
								                    		Ext.getCmp('fieldOrderUpd0').enable();
								                    		Ext.getCmp('fontSizeUpd0').enable();
								                    		Ext.getCmp('contFormatUpd0').enable();
															Ext.getCmp('fieldNameUpd0').allowBlank = false;
															Ext.getCmp('fieldOrderUpd0').allowBlank = false;
															Ext.getCmp('fontSizeUpd0').allowBlank = false;
							                     		}
													}
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '3',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd0',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd0',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd0').getValue(),0);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd0',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd0',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户名',
											value : '商户名',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd1',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd1',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd1').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd1').disable();
								                    		Ext.getCmp('fieldOrderUpd1').disable();
								                    		Ext.getCmp('fontSizeUpd1').disable();
								                    		Ext.getCmp('contFormatUpd1').disable();
								                    		Ext.getCmp('fieldNameUpd1').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd1').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd1').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd1').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd1').enable();
								                    		Ext.getCmp('fieldOrderUpd1').enable();
								                    		Ext.getCmp('fontSizeUpd1').enable();
								                    		Ext.getCmp('contFormatUpd1').enable();
								                    		Ext.getCmp('fieldNameUpd1').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd1').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd1').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd1').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd1').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd1').disable();
								                    		Ext.getCmp('fieldOrderUpd1').disable();
								                    		Ext.getCmp('fontSizeUpd1').disable();
								                    		Ext.getCmp('contFormatUpd1').disable();
								                    		Ext.getCmp('fieldNameUpd1').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd1').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd1').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd1').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd1').enable();
								                    		Ext.getCmp('fieldOrderUpd1').enable();
								                    		Ext.getCmp('fontSizeUpd1').enable();
								                    		Ext.getCmp('contFormatUpd1').enable();
								                    		Ext.getCmp('fieldNameUpd1').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd1').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd1').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd1').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd1',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd1',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd1').getValue(),1);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd1',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd1',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户号',
											value : '商户号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd2',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd2',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd2').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd2').disable();
								                    		Ext.getCmp('fieldOrderUpd2').disable();
								                    		Ext.getCmp('fontSizeUpd2').disable();
								                    		Ext.getCmp('contFormatUpd2').disable();
								                    		Ext.getCmp('fieldNameUpd2').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd2').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd2').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd2').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd2').enable();
								                    		Ext.getCmp('fieldOrderUpd2').enable();
								                    		Ext.getCmp('fontSizeUpd2').enable();
								                    		Ext.getCmp('contFormatUpd2').enable();
								                    		Ext.getCmp('fieldNameUpd2').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd2').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd2').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd2').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd2').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd2').disable();
								                    		Ext.getCmp('fieldOrderUpd2').disable();
								                    		Ext.getCmp('fontSizeUpd2').disable();
								                    		Ext.getCmp('contFormatUpd2').disable();
								                    		Ext.getCmp('fieldNameUpd2').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd2').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd2').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd2').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd2').enable();
								                    		Ext.getCmp('fieldOrderUpd2').enable();
								                    		Ext.getCmp('fontSizeUpd2').enable();
								                    		Ext.getCmp('contFormatUpd2').enable();
								                    		Ext.getCmp('fieldNameUpd2').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd2').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd2').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd2').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd2',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd2',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd2').getValue(),2);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd2',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {

											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd2',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '终端号',
											value : '终端号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd3',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd3',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											        'select': function() {
													    var status = Ext.getCmp('statusUpd3').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd3').disable();
								                    		Ext.getCmp('fieldOrderUpd3').disable();
								                    		Ext.getCmp('fontSizeUpd3').disable();
								                    		Ext.getCmp('contFormatUpd3').disable();
								                    		Ext.getCmp('fieldNameUpd3').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd3').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd3').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd3').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd3').enable();
								                    		Ext.getCmp('fieldOrderUpd3').enable();
								                    		Ext.getCmp('fontSizeUpd3').enable();
								                    		Ext.getCmp('contFormatUpd3').enable();
								                    		Ext.getCmp('fieldNameUpd3').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd3').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd3').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd3').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd3').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd3').disable();
								                    		Ext.getCmp('fieldOrderUpd3').disable();
								                    		Ext.getCmp('fontSizeUpd3').disable();
								                    		Ext.getCmp('contFormatUpd3').disable();
								                    		Ext.getCmp('fieldNameUpd3').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd3').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd3').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd3').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd3').enable();
								                    		Ext.getCmp('fieldOrderUpd3').enable();
								                    		Ext.getCmp('fontSizeUpd3').enable();
								                    		Ext.getCmp('contFormatUpd3').enable();
								                    		Ext.getCmp('fieldNameUpd3').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd3').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd3').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd3').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd3',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd3',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd3').getValue(),3);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd3',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd3',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '发卡行',
											value : '发卡行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd4',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd4',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
												      'select': function() {
														    var status = Ext.getCmp('statusUpd4').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd4').disable();
									                    		Ext.getCmp('fieldOrderUpd4').disable();
									                    		Ext.getCmp('fontSizeUpd4').disable();
									                    		Ext.getCmp('contFormatUpd4').disable();
									                    		Ext.getCmp('fieldNameUpd4').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd4').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd4').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd4').enable();
									                    		Ext.getCmp('fieldOrderUpd4').enable();
									                    		Ext.getCmp('fontSizeUpd4').enable();
									                    		Ext.getCmp('contFormatUpd4').enable();
									                    		Ext.getCmp('fieldNameUpd4').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd4').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd4').allowBlank = false;
								                     		}
												        },
												        'change': function() {
															    var status = Ext.getCmp('statusUpd4').getValue();
										                    	if(status == '0'){
										                    		Ext.getCmp('fieldNameUpd4').disable();
										                    		Ext.getCmp('fieldOrderUpd4').disable();
										                    		Ext.getCmp('fontSizeUpd4').disable();
										                    		Ext.getCmp('contFormatUpd4').disable();
										                    		Ext.getCmp('fieldNameUpd4').allowBlank = true;
										                    		Ext.getCmp('fieldOrderUpd4').allowBlank = true;
										                    		Ext.getCmp('fontSizeUpd4').allowBlank = true;
										                    	}else{
										                    		Ext.getCmp('fieldNameUpd4').enable();
										                    		Ext.getCmp('fieldOrderUpd4').enable();
										                    		Ext.getCmp('fontSizeUpd4').enable();
										                    		Ext.getCmp('contFormatUpd4').enable();
										                    		Ext.getCmp('fieldNameUpd4').allowBlank = false;
										                    		Ext.getCmp('fieldOrderUpd4').allowBlank = false;
										                    		Ext.getCmp('fontSizeUpd4').allowBlank = false;
									                     		}
													        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd4',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd4',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd4').getValue(),4);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd4',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd4',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '收单行',
											value : '收单行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd5',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd5',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
									      'select': function() {
											    var status = Ext.getCmp('statusUpd5').getValue();
						                    	if(status == '0'){
						                    		Ext.getCmp('fieldNameUpd5').disable();
						                    		Ext.getCmp('fieldOrderUpd5').disable();
						                    		Ext.getCmp('fontSizeUpd5').disable();
						                    		Ext.getCmp('contFormatUpd5').disable();
						                    		Ext.getCmp('fieldNameUpd5').allowBlank = true;
						                    		Ext.getCmp('fieldOrderUpd5').allowBlank = true;
						                    		Ext.getCmp('fontSizeUpd5').allowBlank = true;
						                    		Ext.getCmp('contFormatUpd5').allowBlank = true;
						                    	}else{
						                    		Ext.getCmp('fieldNameUpd5').enable();
						                    		Ext.getCmp('fieldOrderUpd5').enable();
						                    		Ext.getCmp('fontSizeUpd5').enable();
						                    		Ext.getCmp('contFormatUpd5').enable();
						                    		Ext.getCmp('fieldNameUpd5').allowBlank = false;
						                    		Ext.getCmp('fieldOrderUpd5').allowBlank = false;
						                    		Ext.getCmp('fontSizeUpd5').allowBlank = false;
						                    		Ext.getCmp('contFormatUpd5').allowBlank = false;
					                     		}
									        },
									        'change': function() {
												    var status = Ext.getCmp('statusUpd5').getValue();
							                    	if(status == '0'){
							                    		Ext.getCmp('fieldNameUpd5').disable();
							                    		Ext.getCmp('fieldOrderUpd5').disable();
							                    		Ext.getCmp('fontSizeUpd5').disable();
							                    		Ext.getCmp('contFormatUpd5').disable();
							                    		Ext.getCmp('fieldNameUpd5').allowBlank = true;
							                    		Ext.getCmp('fieldOrderUpd5').allowBlank = true;
							                    		Ext.getCmp('fontSizeUpd5').allowBlank = true;
							                    		Ext.getCmp('contFormatUpd5').allowBlank = true;
							                    	}else{
							                    		Ext.getCmp('fieldNameUpd5').enable();
							                    		Ext.getCmp('fieldOrderUpd5').enable();
							                    		Ext.getCmp('fontSizeUpd5').enable();
							                    		Ext.getCmp('contFormatUpd5').enable();
							                    		Ext.getCmp('fieldNameUpd5').allowBlank = false;
							                    		Ext.getCmp('fieldOrderUpd5').allowBlank = false;
							                    		Ext.getCmp('fontSizeUpd5').allowBlank = false;
							                    		Ext.getCmp('contFormatUpd5').allowBlank = false;
						                     		}
										        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd5',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd5',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd5').getValue(),5);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd5',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd5',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '卡号',
											value : '卡号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd6',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd6',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd6').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd6').disable();
								                    		Ext.getCmp('fieldOrderUpd6').disable();
								                    		Ext.getCmp('fontSizeUpd6').disable();
								                    		Ext.getCmp('contFormatUpd6').disable();
								                    		Ext.getCmp('fieldNameUpd6').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd6').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd6').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd6').enable();
								                    		Ext.getCmp('fieldOrderUpd6').enable();
								                    		Ext.getCmp('fontSizeUpd6').enable();
								                    		Ext.getCmp('contFormatUpd6').enable();
								                    		Ext.getCmp('fieldNameUpd6').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd6').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd6').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd6').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd6').disable();
									                    		Ext.getCmp('fieldOrderUpd6').disable();
									                    		Ext.getCmp('fontSizeUpd6').disable();
									                    		Ext.getCmp('contFormatUpd6').disable();
									                    		Ext.getCmp('fieldNameUpd6').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd6').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd6').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd6').enable();
									                    		Ext.getCmp('fieldOrderUpd6').enable();
									                    		Ext.getCmp('fontSizeUpd6').enable();
									                    		Ext.getCmp('contFormatUpd6').enable();
									                    		Ext.getCmp('fieldNameUpd6').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd6').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd6').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd6',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd6',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd6').getValue(),6);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd6',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd6',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '批次号',
											value : '批次号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd7',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd7',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd7').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd7').disable();
								                    		Ext.getCmp('fieldOrderUpd7').disable();
								                    		Ext.getCmp('fontSizeUpd7').disable();
								                    		Ext.getCmp('contFormatUpd7').disable();
								                    		Ext.getCmp('fieldNameUpd7').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd7').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd7').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd7').enable();
								                    		Ext.getCmp('fieldOrderUpd7').enable();
								                    		Ext.getCmp('fontSizeUpd7').enable();
								                    		Ext.getCmp('contFormatUpd7').enable();
								                    		Ext.getCmp('fieldNameUpd7').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd7').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd7').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd7').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd7').disable();
									                    		Ext.getCmp('fieldOrderUpd7').disable();
									                    		Ext.getCmp('fontSizeUpd7').disable();
									                    		Ext.getCmp('contFormatUpd7').disable();
									                    		Ext.getCmp('fieldNameUpd7').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd7').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd7').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd7').enable();
									                    		Ext.getCmp('fieldOrderUpd7').enable();
									                    		Ext.getCmp('fontSizeUpd7').enable();
									                    		Ext.getCmp('contFormatUpd7').enable();
									                    		Ext.getCmp('fieldNameUpd7').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd7').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd7').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd7',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd7',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd7').getValue(),7);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd7',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd7',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '流水号',
											value : '流水号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd8',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd8',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd8').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd8').disable();
								                    		Ext.getCmp('fieldOrderUpd8').disable();
								                    		Ext.getCmp('fontSizeUpd8').disable();
								                    		Ext.getCmp('contFormatUpd8').disable();
								                    		Ext.getCmp('fieldNameUpd8').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd8').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd8').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd8').enable();
								                    		Ext.getCmp('fieldOrderUpd8').enable();
								                    		Ext.getCmp('fontSizeUpd8').enable();
								                    		Ext.getCmp('contFormatUpd8').enable();
								                    		Ext.getCmp('fieldNameUpd8').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd8').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd8').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd8').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd8').disable();
									                    		Ext.getCmp('fieldOrderUpd8').disable();
									                    		Ext.getCmp('fontSizeUpd8').disable();
									                    		Ext.getCmp('contFormatUpd8').disable();
									                    		Ext.getCmp('fieldNameUpd8').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd8').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd8').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd8').enable();
									                    		Ext.getCmp('fieldOrderUpd8').enable();
									                    		Ext.getCmp('fontSizeUpd8').enable();
									                    		Ext.getCmp('contFormatUpd8').enable();
									                    		Ext.getCmp('fieldNameUpd8').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd8').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd8').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd8',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd8',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd8').getValue(),8);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd8',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd8',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '授权码',
											value : '授权码',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd9',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd9',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd9').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd9').disable();
								                    		Ext.getCmp('fieldOrderUpd9').disable();
								                    		Ext.getCmp('fontSizeUpd9').disable();
								                    		Ext.getCmp('contFormatUpd9').disable();
								                    		Ext.getCmp('fieldNameUpd9').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd9').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd9').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd9').enable();
								                    		Ext.getCmp('fieldOrderUpd9').enable();
								                    		Ext.getCmp('fontSizeUpd9').enable();
								                    		Ext.getCmp('contFormatUpd9').enable();
								                    		Ext.getCmp('fieldNameUpd9').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd9').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd9').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd9').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd9').disable();
									                    		Ext.getCmp('fieldOrderUpd9').disable();
									                    		Ext.getCmp('fontSizeUpd9').disable();
									                    		Ext.getCmp('contFormatUpd9').disable();
									                    		Ext.getCmp('fieldNameUpd9').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd9').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd9').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd9').enable();
									                    		Ext.getCmp('fieldOrderUpd9').enable();
									                    		Ext.getCmp('fontSizeUpd9').enable();
									                    		Ext.getCmp('contFormatUpd9').enable();
									                    		Ext.getCmp('fieldNameUpd9').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd9').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd9').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd9',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd9',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd9').getValue(),9);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd9',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd9',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '参考号',
											value : '参考号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd10',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd10',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd10').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd10').disable();
								                    		Ext.getCmp('fieldOrderUpd10').disable();
								                    		Ext.getCmp('fontSizeUpd10').disable();
								                    		Ext.getCmp('contFormatUpd10').disable();
								                    		Ext.getCmp('fieldNameUpd10').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd10').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd10').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd10').enable();
								                    		Ext.getCmp('fieldOrderUpd10').enable();
								                    		Ext.getCmp('fontSizeUpd10').enable();
								                    		Ext.getCmp('contFormatUpd10').enable();
								                    		Ext.getCmp('fieldNameUpd10').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd10').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd10').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd10').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd10').disable();
									                    		Ext.getCmp('fieldOrderUpd10').disable();
									                    		Ext.getCmp('fontSizeUpd10').disable();
									                    		Ext.getCmp('contFormatUpd10').disable();
									                    		Ext.getCmp('fieldNameUpd10').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd10').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd10').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd10').enable();
									                    		Ext.getCmp('fieldOrderUpd10').enable();
									                    		Ext.getCmp('fontSizeUpd10').enable();
									                    		Ext.getCmp('contFormatUpd10').enable();
									                    		Ext.getCmp('fieldNameUpd10').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd10').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd10').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd10',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .35,//15
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd10',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd10').getValue(),10);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd10',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,//35
										layout : 'form',
										hidden: true,//false
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 4,
											regex : /^[+|-]?[0-9]+([.][0-9]+){0,1}$/,
											allowBlank : true,
											id : 'contFormatUpd10',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '日期时间',
											value : '日期时间',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd11',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd11',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd11').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd11').disable();
								                    		Ext.getCmp('fieldOrderUpd11').disable();
								                    		Ext.getCmp('fontSizeUpd11').disable();
								                    		Ext.getCmp('contFormatUpd11').disable();
								                    		Ext.getCmp('fieldNameUpd11').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd11').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd11').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd11').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd11').enable();
								                    		Ext.getCmp('fieldOrderUpd11').enable();
								                    		Ext.getCmp('fontSizeUpd11').enable();
								                    		Ext.getCmp('contFormatUpd11').enable();
								                    		Ext.getCmp('fieldNameUpd11').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd11').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd11').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd11').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd11').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd11').disable();
									                    		Ext.getCmp('fieldOrderUpd11').disable();
									                    		Ext.getCmp('fontSizeUpd11').disable();
									                    		Ext.getCmp('contFormatUpd11').disable();
									                    		Ext.getCmp('fieldNameUpd11').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd11').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd11').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd11').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd11').enable();
									                    		Ext.getCmp('fieldOrderUpd11').enable();
									                    		Ext.getCmp('fontSizeUpd11').enable();
									                    		Ext.getCmp('contFormatUpd11').enable();
									                    		Ext.getCmp('fieldNameUpd11').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd11').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd11').allowBlank = false;
									                    		Ext.getCmp('contFormatUpd11').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd11',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd11',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd11').getValue(),11);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd11',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd11',
											name : 'contFormat',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '-30', '-30' ],
														[ '-29', '-29' ], 
														[ '-28', '-28' ],
														[ '-27', '-27' ], 
														[ '-26', '-26' ],
														[ '-25', '-25' ], 
														[ '-24', '-24' ],
														[ '-23', '-23' ], 
														[ '-22', '-22' ],
														[ '-21', '-21' ],
												        [ '-20', '-20' ],
														[ '-19', '-19' ], 
														[ '-18', '-18' ],
														[ '-17', '-17' ], 
														[ '-16', '-16' ],
														[ '-15', '-15' ], 
														[ '-14', '-14' ],
														[ '-13', '-13' ], 
														[ '-12', '-12' ],
														[ '-11', '-11' ],
												        [ '-10', '-10' ],
														[ '-09', '-09' ], 
														[ '-08', '-08' ],
														[ '-07', '-07' ], 
														[ '-06', '-06' ],
														[ '-05', '-05' ], 
														[ '-04', '-04' ],
														[ '-03', '-03' ], 
														[ '-02', '-02' ],
														[ '-01', '-01' ],
														[ '0', '0' ],
												        [ '1', '1' ],
														[ '2', '2' ], 
														[ '3', '3' ],
														[ '4', '4' ], 
														[ '5', '5' ],
														[ '6', '6' ], 
														[ '7', '7' ],
														[ '8', '8' ], 
														[ '9', '9' ],
														[ '10', '10' ],
												        [ '11', '11' ],
														[ '12', '12' ], 
														[ '13', '13' ],
														[ '14', '14' ], 
														[ '15', '15' ],
														[ '16', '16' ], 
														[ '17', '17' ],
														[ '18', '18' ], 
														[ '19', '19' ],
														[ '20', '20' ],
												        [ '21', '21' ],
														[ '22', '22' ], 
														[ '23', '23' ],
														[ '24', '24' ], 
														[ '25', '25' ],
														[ '26', '26' ], 
														[ '27', '27' ],
														[ '28', '28' ], 
														[ '29', '29' ],
														[ '30', '30' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '金额',
											value : '金额',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd12',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd12',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd12').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd12').disable();
								                    		Ext.getCmp('fieldOrderUpd12').disable();
								                    		Ext.getCmp('fontSizeUpd12').disable();
								                    		Ext.getCmp('contFormatUpd12').disable();
								                    		Ext.getCmp('fieldNameUpd12').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd12').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd12').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd12').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd12').enable();
								                    		Ext.getCmp('fieldOrderUpd12').enable();
								                    		Ext.getCmp('fontSizeUpd12').enable();
								                    		Ext.getCmp('contFormatUpd12').enable();
								                    		Ext.getCmp('fieldNameUpd12').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd12').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd12').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd12').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd12').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd12').disable();
									                    		Ext.getCmp('fieldOrderUpd12').disable();
									                    		Ext.getCmp('fontSizeUpd12').disable();
									                    		Ext.getCmp('contFormatUpd12').disable();
									                    		Ext.getCmp('fieldNameUpd12').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd12').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd12').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd12').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd12').enable();
									                    		Ext.getCmp('fieldOrderUpd12').enable();
									                    		Ext.getCmp('fontSizeUpd12').enable();
									                    		Ext.getCmp('contFormatUpd12').enable();
									                    		Ext.getCmp('fieldNameUpd12').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd12').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd12').allowBlank = false;
									                    		Ext.getCmp('contFormatUpd12').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd12',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd12',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd12').getValue(),12);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd12',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd12',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '备注',
											value : '备注',
											maxLength : 10,
											allowBlank : true,
											id : 'fieldNameUpd13',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd13',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd13').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd13').disable();
								                    		Ext.getCmp('fieldOrderUpd13').disable();
								                    		Ext.getCmp('fontSizeUpd13').disable();
								                    		Ext.getCmp('contFormatUpd13').disable();
								                    		Ext.getCmp('fieldOrderUpd13').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd13').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd13').enable();
								                    		Ext.getCmp('fieldOrderUpd13').enable();
								                    		Ext.getCmp('fontSizeUpd13').enable();
								                    		Ext.getCmp('contFormatUpd13').enable();
								                    		Ext.getCmp('fieldOrderUpd13').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd13').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd13').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd13').disable();
									                    		Ext.getCmp('fieldOrderUpd13').disable();
									                    		Ext.getCmp('fontSizeUpd13').disable();
									                    		Ext.getCmp('contFormatUpd13').disable();
									                    		Ext.getCmp('fieldOrderUpd13').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd13').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd13').enable();
									                    		Ext.getCmp('fieldOrderUpd13').enable();
									                    		Ext.getCmp('fontSizeUpd13').enable();
									                    		Ext.getCmp('contFormatUpd13').enable();
									                    		Ext.getCmp('fieldOrderUpd13').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd13').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd13',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd13',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd13').getValue(),13);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd13',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ]]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd13',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											//blankText : '不能为空',
											fieldLabel : '说明文字',
											maxLength : 10,
											hidden: true,
											allowBlank : true,
											id : 'fieldNameUpd14',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd14',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd14').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd14').disable();
								                    		Ext.getCmp('fieldOrderUpd14').disable();
								                    		Ext.getCmp('fontSizeUpd14').disable();
								                    		Ext.getCmp('contFormatUpd14').disable();
								                    		Ext.getCmp('fieldOrderUpd14').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd14').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd14').enable();
								                    		Ext.getCmp('fieldOrderUpd14').enable();
								                    		Ext.getCmp('fontSizeUpd14').enable();
								                    		Ext.getCmp('contFormatUpd14').enable();
								                    		Ext.getCmp('fieldOrderUpd14').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd14').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd14').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd14').disable();
									                    		Ext.getCmp('fieldOrderUpd14').disable();
									                    		Ext.getCmp('fontSizeUpd14').disable();
									                    		Ext.getCmp('contFormatUpd14').disable();
									                    		Ext.getCmp('fieldOrderUpd14').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd14').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd14').enable();
									                    		Ext.getCmp('fieldOrderUpd14').enable();
									                    		Ext.getCmp('fontSizeUpd14').enable();
									                    		Ext.getCmp('contFormatUpd14').enable();
									                    		Ext.getCmp('fieldOrderUpd14').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd14').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd14',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd14',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd14').getValue(),14);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd14',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd14',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留1',
											maxLength : 10,
											id : 'fieldNameUpd15',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd15',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd15').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd15').disable();
								                    		Ext.getCmp('fieldOrderUpd15').disable();
								                    		Ext.getCmp('fontSizeUpd15').disable();
								                    		Ext.getCmp('contFormatUpd15').disable();
								                    		/*Ext.getCmp('fieldNameUpd15').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd15').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd15').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd15').allowBlank = true;*/
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd15').enable();
								                    		Ext.getCmp('fieldOrderUpd15').enable();
								                    		Ext.getCmp('fontSizeUpd15').enable();
								                    		Ext.getCmp('contFormatUpd15').enable();
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd15').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd15').disable();
									                    		Ext.getCmp('fieldOrderUpd15').disable();
									                    		Ext.getCmp('fontSizeUpd15').disable();
									                    		Ext.getCmp('contFormatUpd15').disable();
									                    		/*Ext.getCmp('fieldNameUpd15').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd15').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd15').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd15').allowBlank = true;*/
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd15').enable();
									                    		Ext.getCmp('fieldOrderUpd15').enable();
									                    		Ext.getCmp('fontSizeUpd15').enable();
									                    		Ext.getCmp('contFormatUpd15').enable();
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd15',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd15',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd15').getValue(),15);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd15',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd15',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留2',
											maxLength : 10,
											id : 'fieldNameUpd16',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd16',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd16').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd16').disable();
								                    		Ext.getCmp('fieldOrderUpd16').disable();
								                    		Ext.getCmp('fontSizeUpd16').disable();
								                    		Ext.getCmp('contFormatUpd16').disable();
								                    		/*Ext.getCmp('fieldNameUpd16').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd16').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd16').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd16').allowBlank = true;*/
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd16').enable();
								                    		Ext.getCmp('fieldOrderUpd16').enable();
								                    		Ext.getCmp('fontSizeUpd16').enable();
								                    		Ext.getCmp('contFormatUpd16').enable();
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd16').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd16').disable();
									                    		Ext.getCmp('fieldOrderUpd16').disable();
									                    		Ext.getCmp('fontSizeUpd16').disable();
									                    		Ext.getCmp('contFormatUpd16').disable();
									                    		/*Ext.getCmp('fieldNameUpd16').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd16').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd16').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd16').allowBlank = true;*/
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd16').enable();
									                    		Ext.getCmp('fieldOrderUpd16').enable();
									                    		Ext.getCmp('fontSizeUpd16').enable();
									                    		Ext.getCmp('contFormatUpd16').enable();
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd16',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd16',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd16').getValue(),16);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd16',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd16',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留3',
											maxLength : 10,
											id : 'fieldNameUpd17',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd17',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd17').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd17').disable();
								                    		Ext.getCmp('fieldOrderUpd17').disable();
								                    		Ext.getCmp('fontSizeUpd17').disable();
								                    		Ext.getCmp('contFormatUpd17').disable();
								                    		/*Ext.getCmp('fieldNameUpd17').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd17').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd17').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd17').allowBlank = true;*/
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd17').enable();
								                    		Ext.getCmp('fieldOrderUpd17').enable();
								                    		Ext.getCmp('fontSizeUpd17').enable();
								                    		Ext.getCmp('contFormatUpd17').enable();
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd17').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd17').disable();
									                    		Ext.getCmp('fieldOrderUpd17').disable();
									                    		Ext.getCmp('fontSizeUpd17').disable();
									                    		Ext.getCmp('contFormatUpd17').disable();
									                    		/*Ext.getCmp('fieldNameUpd17').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd17').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd17').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd17').allowBlank = true;*/
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd17').enable();
									                    		Ext.getCmp('fieldOrderUpd17').enable();
									                    		Ext.getCmp('fontSizeUpd17').enable();
									                    		Ext.getCmp('contFormatUpd17').enable();
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd17',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd17',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd17').getValue(),17);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd17',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd17',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留4',
											maxLength : 10,
											id : 'fieldNameUpd18',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd18',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd18').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd18').disable();
								                    		Ext.getCmp('fieldOrderUpd18').disable();
								                    		Ext.getCmp('fontSizeUpd18').disable();
								                    		Ext.getCmp('contFormatUpd18').disable();
								                    		/*Ext.getCmp('fieldNameUpd18').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd18').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd18').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd18').allowBlank = true;*/
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd18').enable();
								                    		Ext.getCmp('fieldOrderUpd18').enable();
								                    		Ext.getCmp('fontSizeUpd18').enable();
								                    		Ext.getCmp('contFormatUpd18').enable();
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd18').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd18').disable();
									                    		Ext.getCmp('fieldOrderUpd18').disable();
									                    		Ext.getCmp('fontSizeUpd18').disable();
									                    		Ext.getCmp('contFormatUpd18').disable();
									                    		/*Ext.getCmp('fieldNameUpd18').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd18').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd18').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd18').allowBlank = true;*/
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd18').enable();
									                    		Ext.getCmp('fieldOrderUpd18').enable();
									                    		Ext.getCmp('fontSizeUpd18').enable();
									                    		Ext.getCmp('contFormatUpd18').enable();
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd18',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd18',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd18').getValue(),18);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd18',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd18',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留5',
											maxLength : 10,
											id : 'fieldNameUpd19',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd19',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd19').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd19').disable();
								                    		Ext.getCmp('fieldOrderUpd19').disable();
								                    		Ext.getCmp('fontSizeUpd19').disable();
								                    		Ext.getCmp('contFormatUpd19').disable();
								                    		/*Ext.getCmp('fieldNameUpd19').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd19').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd19').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd19').allowBlank = true;*/
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd19').enable();
								                    		Ext.getCmp('fieldOrderUpd19').enable();
								                    		Ext.getCmp('fontSizeUpd19').enable();
								                    		Ext.getCmp('contFormatUpd19').enable();
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd19').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd19').disable();
									                    		Ext.getCmp('fieldOrderUpd19').disable();
									                    		Ext.getCmp('fontSizeUpd19').disable();
									                    		Ext.getCmp('contFormatUpd19').disable();
									                    		/*Ext.getCmp('fieldNameUpd19').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd19').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd19').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd19').allowBlank = true;*/
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd19').enable();
									                    		Ext.getCmp('fieldOrderUpd19').enable();
									                    		Ext.getCmp('fontSizeUpd19').enable();
									                    		Ext.getCmp('contFormatUpd19').enable();
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd19',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											value : '1',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd19',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpd(Ext.getCmp('fieldOrderUpd19').getValue(),19);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd19',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									}, {
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd19',
											name : 'contFormat',
										} ]
									}
								//123
							
							]}

			,{
				id : 'baseInfo4',
				xtype : 'fieldset',
				title : '持卡人参数配置',
				layout : 'column',
				collapsible : true,
		        //autoHeight:true,
		        style:'width:1130px;',
				defaults : {
					bodyStyle : 'padding-left: 20px'
				},
				items : [
							{
								columnWidth : .9,
								layout : 'form',
								items : [{
									xtype: 'button',
									text: '复制当前商户模板所有参数到持卡人模板',
									width: '200',
									id: 'view4',
									handler:function() {
										//将商户模板的参数值赋值到持卡人模板参数中
										for (var i = 20; i < 40; i++) {
											updTermForm.getForm().findField("fieldNameUpd" + i).setValue(Ext.getCmp('fieldNameUpd' + (i-20)).getValue());
											updTermForm.getForm().findField("statusUpd" + i).setValue(Ext.getCmp('statusUpd' + (i-20)).getValue());
											updTermForm.getForm().findField("fontSizeUpd" + i).setValue(Ext.getCmp('fontSizeUpd' + (i-20)).getValue());
											updTermForm.getForm().findField("fieldOrderUpd" + i).setValue(Ext.getCmp('fieldOrderUpd' + (i-20)).getValue());
											updTermForm.getForm().findField("contFormatTypeUpd" + i).setValue(Ext.getCmp('contFormatTypeUpd' + (i-20)).getValue());
											updTermForm.getForm().findField("contFormatUpd" + i).setValue(Ext.getCmp('contFormatUpd' + (i-20)).getValue());
											//若字段状态为停用，则对应的其他几个字段改为不可用状态
											var status = Ext.getCmp('statusUpd' + i).getValue();
					                    	if('0' == status){
					                    		Ext.getCmp('fieldNameUpd'+i).disable();
					                    		Ext.getCmp('fontSizeUpd'+i).disable();
					                    		Ext.getCmp('fieldOrderUpd'+i).disable();
					                    		Ext.getCmp('contFormatUpd'+i).disable();
					                    	}else {
					                    		Ext.getCmp('fieldNameUpd'+i).enable();
					                    		Ext.getCmp('fontSizeUpd'+i).enable();
					                    		Ext.getCmp('fieldOrderUpd'+i).enable();
					                    		Ext.getCmp('contFormatUpd'+i).enable();

					                    		if(13<i<20 || 33<i<40){
						                    		//Ext.getCmp('fieldNameUpd' + i).allowBlank = false;
						                    		Ext.getCmp('fontSizeUpd' + i).allowBlank = false;
						                    		Ext.getCmp('fieldOrderUpd' + i).allowBlank = false;
						                    		//Ext.getCmp('contFormatUpd' + i).allowBlank = false;
					                    		}else {
						                    		Ext.getCmp('fieldNameUpd' + i).allowBlank = false;
						                    		Ext.getCmp('fontSizeUpd' + i).allowBlank = false;
						                    		Ext.getCmp('fieldOrderUpd' + i).allowBlank = false;
						                    		Ext.getCmp('contFormatUpd' + i).allowBlank = false;
												}
											}
										}
										updTermForm.getForm().clearInvalid(); 
									}
								}]
							},
							{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '抬头',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd20',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd20',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd20').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd20').disable();
								                    		Ext.getCmp('fieldOrderUpd20').disable();
								                    		Ext.getCmp('fontSizeUpd20').disable();
								                    		Ext.getCmp('contFormatUpd20').disable();
															Ext.getCmp('fieldNameUpd20').allowBlank = true;
															Ext.getCmp('fieldOrderUpd20').allowBlank = true;
															Ext.getCmp('fontSizeUpd20').allowBlank = true;
														}else{
								                    		Ext.getCmp('fieldNameUpd20').enable();
								                    		Ext.getCmp('fieldOrderUpd20').enable();
								                    		Ext.getCmp('fontSizeUpd20').enable();
								                    		Ext.getCmp('contFormatUpd20').enable();
															Ext.getCmp('fieldNameUpd20').allowBlank = false;
															Ext.getCmp('fieldOrderUpd20').allowBlank = false;
															Ext.getCmp('fontSizeUpd20').allowBlank = false;
							                     		}
													},
													'change': function() {
													    var status = Ext.getCmp('statusUpd20').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd20').disable();
								                    		Ext.getCmp('fieldOrderUpd20').disable();
								                    		Ext.getCmp('fontSizeUpd20').disable();
								                    		Ext.getCmp('contFormatUpd20').disable();
															Ext.getCmp('fieldNameUpd20').allowBlank = true;
															Ext.getCmp('fieldOrderUpd20').allowBlank = true;
															Ext.getCmp('fontSizeUpd20').allowBlank = true;
														}else{
								                    		Ext.getCmp('fieldNameUpd20').enable();
								                    		Ext.getCmp('fieldOrderUpd20').enable();
								                    		Ext.getCmp('fontSizeUpd20').enable();
								                    		Ext.getCmp('contFormatUpd20').enable();
															Ext.getCmp('fieldNameUpd20').allowBlank = false;
															Ext.getCmp('fieldOrderUpd20').allowBlank = false;
															Ext.getCmp('fontSizeUpd20').allowBlank = false;
							                     		}
													}
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '3',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd20',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd20',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd20').getValue(),20);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd20',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd20',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户名',
											value : '商户名',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd21',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd21',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd21').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd21').disable();
								                    		Ext.getCmp('fieldOrderUpd21').disable();
								                    		Ext.getCmp('fontSizeUpd21').disable();
								                    		Ext.getCmp('contFormatUpd21').disable();
								                    		Ext.getCmp('fieldNameUpd21').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd21').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd21').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd21').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd21').enable();
								                    		Ext.getCmp('fieldOrderUpd21').enable();
								                    		Ext.getCmp('fontSizeUpd21').enable();
								                    		Ext.getCmp('contFormatUpd21').enable();
								                    		Ext.getCmp('fieldNameUpd21').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd21').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd21').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd21').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd21').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd21').disable();
								                    		Ext.getCmp('fieldOrderUpd21').disable();
								                    		Ext.getCmp('fontSizeUpd21').disable();
								                    		Ext.getCmp('contFormatUpd21').disable();
								                    		Ext.getCmp('fieldNameUpd21').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd21').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd21').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd21').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd21').enable();
								                    		Ext.getCmp('fieldOrderUpd21').enable();
								                    		Ext.getCmp('fontSizeUpd21').enable();
								                    		Ext.getCmp('contFormatUpd21').enable();
								                    		Ext.getCmp('fieldNameUpd21').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd21').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd21').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd21').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd21',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd21',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd21').getValue(),21);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd21',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd21',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '商户号',
											value : '商户号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd22',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd22',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
													'select': function() {
													    var status = Ext.getCmp('statusUpd22').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd22').disable();
								                    		Ext.getCmp('fieldOrderUpd22').disable();
								                    		Ext.getCmp('fontSizeUpd22').disable();
								                    		Ext.getCmp('contFormatUpd22').disable();
								                    		Ext.getCmp('fieldNameUpd22').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd22').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd22').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd22').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd22').enable();
								                    		Ext.getCmp('fieldOrderUpd22').enable();
								                    		Ext.getCmp('fontSizeUpd22').enable();
								                    		Ext.getCmp('contFormatUpd22').enable();
								                    		Ext.getCmp('fieldNameUpd22').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd22').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd22').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd22').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd22').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd22').disable();
								                    		Ext.getCmp('fieldOrderUpd22').disable();
								                    		Ext.getCmp('fontSizeUpd22').disable();
								                    		Ext.getCmp('contFormatUpd22').disable();
								                    		Ext.getCmp('fieldNameUpd22').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd22').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd22').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd22').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd22').enable();
								                    		Ext.getCmp('fieldOrderUpd22').enable();
								                    		Ext.getCmp('fontSizeUpd22').enable();
								                    		Ext.getCmp('contFormatUpd22').enable();
								                    		Ext.getCmp('fieldNameUpd22').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd22').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd22').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd22').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd22',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd22',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd22').getValue(),22);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd22',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {

											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd22',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '终端号',
											value : '终端号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd23',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd23',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											        'select': function() {
													    var status = Ext.getCmp('statusUpd23').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd23').disable();
								                    		Ext.getCmp('fieldOrderUpd23').disable();
								                    		Ext.getCmp('fontSizeUpd23').disable();
								                    		Ext.getCmp('contFormatUpd23').disable();
								                    		Ext.getCmp('fieldNameUpd23').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd23').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd23').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd23').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd23').enable();
								                    		Ext.getCmp('fieldOrderUpd23').enable();
								                    		Ext.getCmp('fontSizeUpd23').enable();
								                    		Ext.getCmp('contFormatUpd23').enable();
								                    		Ext.getCmp('fieldNameUpd23').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd23').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd23').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd23').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd23').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd23').disable();
								                    		Ext.getCmp('fieldOrderUpd23').disable();
								                    		Ext.getCmp('fontSizeUpd23').disable();
								                    		Ext.getCmp('contFormatUpd23').disable();
								                    		Ext.getCmp('fieldNameUpd23').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd23').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd23').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd3').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd23').enable();
								                    		Ext.getCmp('fieldOrderUpd23').enable();
								                    		Ext.getCmp('fontSizeUpd23').enable();
								                    		Ext.getCmp('contFormatUpd23').enable();
								                    		Ext.getCmp('fieldNameUpd23').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd23').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd23').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd23').allowBlank = false;
							                     		}
											        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd23',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd23',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd23').getValue(),23);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd23',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd23',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]

									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '发卡行',
											value : '发卡行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd24',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd24',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
												      'select': function() {
														    var status = Ext.getCmp('statusUpd24').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd24').disable();
									                    		Ext.getCmp('fieldOrderUpd24').disable();
									                    		Ext.getCmp('fontSizeUpd24').disable();
									                    		Ext.getCmp('contFormatUpd24').disable();
									                    		Ext.getCmp('fieldNameUpd24').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd24').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd24').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd24').enable();
									                    		Ext.getCmp('fieldOrderUpd24').enable();
									                    		Ext.getCmp('fontSizeUpd24').enable();
									                    		Ext.getCmp('contFormatUpd24').enable();
									                    		Ext.getCmp('fieldNameUpd24').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd24').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd24').allowBlank = false;
								                     		}
												        },
												        'change': function() {
															    var status = Ext.getCmp('statusUpd24').getValue();
										                    	if(status == '0'){
										                    		Ext.getCmp('fieldNameUpd24').disable();
										                    		Ext.getCmp('fieldOrderUpd24').disable();
										                    		Ext.getCmp('fontSizeUpd24').disable();
										                    		Ext.getCmp('contFormatUpd24').disable();
										                    		Ext.getCmp('fieldNameUpd24').allowBlank = true;
										                    		Ext.getCmp('fieldOrderUpd24').allowBlank = true;
										                    		Ext.getCmp('fontSizeUpd24').allowBlank = true;
										                    	}else{
										                    		Ext.getCmp('fieldNameUpd24').enable();
										                    		Ext.getCmp('fieldOrderUpd24').enable();
										                    		Ext.getCmp('fontSizeUpd24').enable();
										                    		Ext.getCmp('contFormatUpd24').enable();
										                    		Ext.getCmp('fieldNameUpd24').allowBlank = false;
										                    		Ext.getCmp('fieldOrderUpd24').allowBlank = false;
										                    		Ext.getCmp('fontSizeUpd24').allowBlank = false;
									                     		}
													        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd24',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd24',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd24').getValue(),24);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd24',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd24',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '收单行',
											value : '收单行',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd25',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd25',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
									      'select': function() {
											    var status = Ext.getCmp('statusUpd25').getValue();
						                    	if(status == '0'){
						                    		Ext.getCmp('fieldNameUpd25').disable();
						                    		Ext.getCmp('fieldOrderUpd25').disable();
						                    		Ext.getCmp('fontSizeUpd25').disable();
						                    		Ext.getCmp('contFormatUpd25').disable();
						                    		Ext.getCmp('fieldNameUpd25').allowBlank = true;
						                    		Ext.getCmp('fieldOrderUpd25').allowBlank = true;
						                    		Ext.getCmp('fontSizeUpd25').allowBlank = true;
						                    		Ext.getCmp('contFormatUpd25').allowBlank = true;
						                    	}else{
						                    		Ext.getCmp('fieldNameUpd25').enable();
						                    		Ext.getCmp('fieldOrderUpd25').enable();
						                    		Ext.getCmp('fontSizeUpd25').enable();
						                    		Ext.getCmp('contFormatUpd25').enable();
						                    		Ext.getCmp('fieldNameUpd25').allowBlank = false;
						                    		Ext.getCmp('fieldOrderUpd25').allowBlank = false;
						                    		Ext.getCmp('fontSizeUpd25').allowBlank = false;
						                    		Ext.getCmp('contFormatUpd25').allowBlank = false;
					                     		}
									        },
									        'change': function() {
												    var status = Ext.getCmp('statusUpd25').getValue();
							                    	if(status == '0'){
							                    		Ext.getCmp('fieldNameUpd25').disable();
							                    		Ext.getCmp('fieldOrderUpd25').disable();
							                    		Ext.getCmp('fontSizeUpd25').disable();
							                    		Ext.getCmp('contFormatUpd25').disable();
							                    		Ext.getCmp('fieldNameUpd25').allowBlank = true;
							                    		Ext.getCmp('fieldOrderUpd25').allowBlank = true;
							                    		Ext.getCmp('fontSizeUpd25').allowBlank = true;
							                    		Ext.getCmp('contFormatUpd25').allowBlank = true;
							                    	}else{
							                    		Ext.getCmp('fieldNameUpd25').enable();
							                    		Ext.getCmp('fieldOrderUpd25').enable();
							                    		Ext.getCmp('fontSizeUpd25').enable();
							                    		Ext.getCmp('contFormatUpd25').enable();
							                    		Ext.getCmp('fieldNameUpd25').allowBlank = false;
							                    		Ext.getCmp('fieldOrderUpd25').allowBlank = false;
							                    		Ext.getCmp('fontSizeUpd25').allowBlank = false;
							                    		Ext.getCmp('contFormatUpd25').allowBlank = false;
						                     		}
										        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd25',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd25',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd25').getValue(),25);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd25',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd25',
											hiddenName : 'contFormat',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '使用钱宝系统的' ],
														[ '2', '使用上游系统的' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '卡号',
											value : '卡号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd26',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd26',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd26').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd26').disable();
								                    		Ext.getCmp('fieldOrderUpd26').disable();
								                    		Ext.getCmp('fontSizeUpd26').disable();
								                    		Ext.getCmp('contFormatUpd26').disable();
								                    		Ext.getCmp('fieldNameUpd26').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd26').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd26').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd26').enable();
								                    		Ext.getCmp('fieldOrderUpd26').enable();
								                    		Ext.getCmp('fontSizeUpd26').enable();
								                    		Ext.getCmp('contFormatUpd26').enable();
								                    		Ext.getCmp('fieldNameUpd26').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd26').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd26').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd26').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd26').disable();
									                    		Ext.getCmp('fieldOrderUpd26').disable();
									                    		Ext.getCmp('fontSizeUpd26').disable();
									                    		Ext.getCmp('contFormatUpd26').disable();
									                    		Ext.getCmp('fieldNameUpd26').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd26').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd26').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd26').enable();
									                    		Ext.getCmp('fieldOrderUpd26').enable();
									                    		Ext.getCmp('fontSizeUpd26').enable();
									                    		Ext.getCmp('contFormatUpd26').enable();
									                    		Ext.getCmp('fieldNameUpd26').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd26').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd26').allowBlank = false;
								                     		}
												        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd26',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd26',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd26').getValue(),26);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd26',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd26',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '批次号',
											value : '批次号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd27',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd27',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd27').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd27').disable();
								                    		Ext.getCmp('fieldOrderUpd27').disable();
								                    		Ext.getCmp('fontSizeUpd27').disable();
								                    		Ext.getCmp('contFormatUpd27').disable();
								                    		Ext.getCmp('fieldNameUpd27').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd27').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd27').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd27').enable();
								                    		Ext.getCmp('fieldOrderUpd27').enable();
								                    		Ext.getCmp('fontSizeUpd27').enable();
								                    		Ext.getCmp('contFormatUpd27').enable();
								                    		Ext.getCmp('fieldNameUpd27').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd27').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd27').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd27').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd27').disable();
									                    		Ext.getCmp('fieldOrderUpd27').disable();
									                    		Ext.getCmp('fontSizeUpd27').disable();
									                    		Ext.getCmp('contFormatUpd27').disable();
									                    		Ext.getCmp('fieldNameUpd27').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd27').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd27').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd27').enable();
									                    		Ext.getCmp('fieldOrderUpd27').enable();
									                    		Ext.getCmp('fontSizeUpd27').enable();
									                    		Ext.getCmp('contFormatUpd27').enable();
									                    		Ext.getCmp('fieldNameUpd27').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd27').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd27').allowBlank = false;
								                     		}
												        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd27',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd27',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd27').getValue(),27);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd27',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd27',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '流水号',
											value : '流水号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd28',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd28',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd28').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd28').disable();
								                    		Ext.getCmp('fieldOrderUpd28').disable();
								                    		Ext.getCmp('fontSizeUpd28').disable();
								                    		Ext.getCmp('contFormatUpd28').disable();
								                    		Ext.getCmp('fieldNameUpd28').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd28').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd28').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd28').enable();
								                    		Ext.getCmp('fieldOrderUpd28').enable();
								                    		Ext.getCmp('fontSizeUpd28').enable();
								                    		Ext.getCmp('contFormatUpd28').enable();
								                    		Ext.getCmp('fieldNameUpd28').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd28').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd28').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd28').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd28').disable();
									                    		Ext.getCmp('fieldOrderUpd28').disable();
									                    		Ext.getCmp('fontSizeUpd28').disable();
									                    		Ext.getCmp('contFormatUpd28').disable();
									                    		Ext.getCmp('fieldNameUpd28').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd28').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd28').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd28').enable();
									                    		Ext.getCmp('fieldOrderUpd28').enable();
									                    		Ext.getCmp('fontSizeUpd28').enable();
									                    		Ext.getCmp('contFormatUpd28').enable();
									                    		Ext.getCmp('fieldNameUpd28').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd28').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd28').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd28',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd28',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd28').getValue(),28);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd28',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd28',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '授权码',
											value : '授权码',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd29',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd29',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd29').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd29').disable();
								                    		Ext.getCmp('fieldOrderUpd29').disable();
								                    		Ext.getCmp('fontSizeUpd29').disable();
								                    		Ext.getCmp('contFormatUpd29').disable();
								                    		Ext.getCmp('fieldNameUpd29').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd29').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd29').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd29').enable();
								                    		Ext.getCmp('fieldOrderUpd29').enable();
								                    		Ext.getCmp('fontSizeUpd29').enable();
								                    		Ext.getCmp('contFormatUpd29').enable();
								                    		Ext.getCmp('fieldNameUpd29').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd29').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd29').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd29').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd29').disable();
									                    		Ext.getCmp('fieldOrderUpd29').disable();
									                    		Ext.getCmp('fontSizeUpd29').disable();
									                    		Ext.getCmp('contFormatUpd29').disable();
									                    		Ext.getCmp('fieldNameUpd29').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd29').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd29').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd29').enable();
									                    		Ext.getCmp('fieldOrderUpd29').enable();
									                    		Ext.getCmp('fontSizeUpd29').enable();
									                    		Ext.getCmp('contFormatUpd29').enable();
									                    		Ext.getCmp('fieldNameUpd29').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd29').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd29').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd29',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd29',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd29').getValue(),29);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd29',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd29',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '参考号',
											value : '参考号',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd30',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd30',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd30').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd30').disable();
								                    		Ext.getCmp('fieldOrderUpd30').disable();
								                    		Ext.getCmp('fontSizeUpd30').disable();
								                    		Ext.getCmp('contFormatUpd30').disable();
								                    		Ext.getCmp('fieldNameUpd30').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd30').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd30').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd30').enable();
								                    		Ext.getCmp('fieldOrderUpd30').enable();
								                    		Ext.getCmp('fontSizeUpd30').enable();
								                    		Ext.getCmp('contFormatUpd30').enable();
								                    		Ext.getCmp('fieldNameUpd30').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd30').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd30').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd30').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd30').disable();
									                    		Ext.getCmp('fieldOrderUpd30').disable();
									                    		Ext.getCmp('fontSizeUpd30').disable();
									                    		Ext.getCmp('contFormatUpd30').disable();
									                    		Ext.getCmp('fieldNameUpd30').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd30').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd30').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd30').enable();
									                    		Ext.getCmp('fieldOrderUpd30').enable();
									                    		Ext.getCmp('fontSizeUpd30').enable();
									                    		Ext.getCmp('contFormatUpd30').enable();
									                    		Ext.getCmp('fieldNameUpd30').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd30').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd30').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd30',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .35,//15
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd30',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd30').getValue(),30);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd30',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .15,//35
										layout : 'form',
										hidden: true,//false
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 4,
											regex : /^[+|-]?[0-9]+([.][0-9]+){0,1}$/,
											allowBlank : true,
											id : 'contFormatUpd30',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '日期时间',
											value : '日期时间',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd31',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd31',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd31').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd31').disable();
								                    		Ext.getCmp('fieldOrderUpd31').disable();
								                    		Ext.getCmp('fontSizeUpd31').disable();
								                    		Ext.getCmp('contFormatUpd31').disable();
								                    		Ext.getCmp('fieldNameUpd31').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd31').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd31').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd31').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd31').enable();
								                    		Ext.getCmp('fieldOrderUpd31').enable();
								                    		Ext.getCmp('fontSizeUpd31').enable();
								                    		Ext.getCmp('contFormatUpd31').enable();
								                    		Ext.getCmp('fieldNameUpd31').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd31').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd31').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd31').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd31').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd31').disable();
									                    		Ext.getCmp('fieldOrderUpd31').disable();
									                    		Ext.getCmp('fontSizeUpd31').disable();
									                    		Ext.getCmp('contFormatUpd31').disable();
									                    		Ext.getCmp('fieldNameUpd31').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd31').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd31').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd31').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd31').enable();
									                    		Ext.getCmp('fieldOrderUpd31').enable();
									                    		Ext.getCmp('fontSizeUpd31').enable();
									                    		Ext.getCmp('contFormatUpd31').enable();
									                    		Ext.getCmp('fieldNameUpd31').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd31').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd31').allowBlank = false;
									                    		Ext.getCmp('contFormatUpd31').allowBlank = false;
								                     		}
												        }
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd31',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd31',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd31').getValue(),31);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd31',
											hiddenName : 'contFormatType',
											value : '2',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容',
											width : 256,
											maxLength : 50,
											allowBlank : false,
											id : 'contFormatUpd31',
											name : 'contFormat',
											value : '0',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '-30', '-30' ],
														[ '-29', '-29' ], 
														[ '-28', '-28' ],
														[ '-27', '-27' ], 
														[ '-26', '-26' ],
														[ '-25', '-25' ], 
														[ '-24', '-24' ],
														[ '-23', '-23' ], 
														[ '-22', '-22' ],
														[ '-21', '-21' ],
												        [ '-20', '-20' ],
														[ '-19', '-19' ], 
														[ '-18', '-18' ],
														[ '-17', '-17' ], 
														[ '-16', '-16' ],
														[ '-15', '-15' ], 
														[ '-14', '-14' ],
														[ '-13', '-13' ], 
														[ '-12', '-12' ],
														[ '-11', '-11' ],
												        [ '-10', '-10' ],
														[ '-09', '-09' ], 
														[ '-08', '-08' ],
														[ '-07', '-07' ], 
														[ '-06', '-06' ],
														[ '-05', '-05' ], 
														[ '-04', '-04' ],
														[ '-03', '-03' ], 
														[ '-02', '-02' ],
														[ '-01', '-01' ],
														[ '0', '0' ],
												        [ '1', '1' ],
														[ '2', '2' ], 
														[ '3', '3' ],
														[ '4', '4' ], 
														[ '5', '5' ],
														[ '6', '6' ], 
														[ '7', '7' ],
														[ '8', '8' ], 
														[ '9', '9' ],
														[ '10', '10' ],
												        [ '11', '11' ],
														[ '12', '12' ], 
														[ '13', '13' ],
														[ '14', '14' ], 
														[ '15', '15' ],
														[ '16', '16' ], 
														[ '17', '17' ],
														[ '18', '18' ], 
														[ '19', '19' ],
														[ '20', '20' ],
												        [ '21', '21' ],
														[ '22', '22' ], 
														[ '23', '23' ],
														[ '24', '24' ], 
														[ '25', '25' ],
														[ '26', '26' ], 
														[ '27', '27' ],
														[ '28', '28' ], 
														[ '29', '29' ],
														[ '30', '30' ] ]
											})
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '金额',
											value : '金额',
											blankText : '不能为空',
											maxLength : 10,
											allowBlank : false,
											id : 'fieldNameUpd32',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd32',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd32').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd32').disable();
								                    		Ext.getCmp('fieldOrderUpd32').disable();
								                    		Ext.getCmp('fontSizeUpd32').disable();
								                    		Ext.getCmp('contFormatUpd32').disable();
								                    		Ext.getCmp('fieldNameUpd32').allowBlank = true;
								                    		Ext.getCmp('fieldOrderUpd32').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd32').allowBlank = true;
								                    		Ext.getCmp('contFormatUpd32').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd32').enable();
								                    		Ext.getCmp('fieldOrderUpd32').enable();
								                    		Ext.getCmp('fontSizeUpd32').enable();
								                    		Ext.getCmp('contFormatUpd32').enable();
								                    		Ext.getCmp('fieldNameUpd32').allowBlank = false;
								                    		Ext.getCmp('fieldOrderUpd32').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd32').allowBlank = false;
								                    		Ext.getCmp('contFormatUpd32').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd32').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd32').disable();
									                    		Ext.getCmp('fieldOrderUpd32').disable();
									                    		Ext.getCmp('fontSizeUpd32').disable();
									                    		Ext.getCmp('contFormatUpd32').disable();
									                    		Ext.getCmp('fieldNameUpd32').allowBlank = true;
									                    		Ext.getCmp('fieldOrderUpd32').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd32').allowBlank = true;
									                    		Ext.getCmp('contFormatUpd32').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd32').enable();
									                    		Ext.getCmp('fieldOrderUpd32').enable();
									                    		Ext.getCmp('fontSizeUpd32').enable();
									                    		Ext.getCmp('contFormatUpd32').enable();
									                    		Ext.getCmp('fieldNameUpd32').allowBlank = false;
									                    		Ext.getCmp('fieldOrderUpd32').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd32').allowBlank = false;
									                    		Ext.getCmp('contFormatUpd32').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd32',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .45,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd32',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd32').getValue(),32);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd32',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd32',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '备注',
											value : '备注',
											maxLength : 10,
											allowBlank : true,
											id : 'fieldNameUpd33',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd33',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd33').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd33').disable();
								                    		Ext.getCmp('fieldOrderUpd33').disable();
								                    		Ext.getCmp('fontSizeUpd33').disable();
								                    		Ext.getCmp('contFormatUpd33').disable();
								                    		Ext.getCmp('fieldOrderUpd33').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd33').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd33').enable();
								                    		Ext.getCmp('fieldOrderUpd33').enable();
								                    		Ext.getCmp('fontSizeUpd33').enable();
								                    		Ext.getCmp('contFormatUpd33').enable();
								                    		Ext.getCmp('fieldOrderUpd33').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd33').allowBlank = false;
							                     		}
											        },
											        'change': function() {
														    var status = Ext.getCmp('statusUpd33').getValue();
									                    	if(status == '0'){
									                    		Ext.getCmp('fieldNameUpd33').disable();
									                    		Ext.getCmp('fieldOrderUpd33').disable();
									                    		Ext.getCmp('fontSizeUpd33').disable();
									                    		Ext.getCmp('contFormatUpd33').disable();
									                    		Ext.getCmp('fieldOrderUpd33').allowBlank = true;
									                    		Ext.getCmp('fontSizeUpd33').allowBlank = true;
									                    	}else{
									                    		Ext.getCmp('fieldNameUpd33').enable();
									                    		Ext.getCmp('fieldOrderUpd33').enable();
									                    		Ext.getCmp('fontSizeUpd33').enable();
									                    		Ext.getCmp('contFormatUpd33').enable();
									                    		Ext.getCmp('fieldOrderUpd33').allowBlank = false;
									                    		Ext.getCmp('fontSizeUpd33').allowBlank = false;
								                     		}
												        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd33',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd33',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd33').getValue(),33);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd33',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ]]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd33',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											//blankText : '不能为空',
											fieldLabel : '说明文字',
											maxLength : 10,
											hidden: true,
											allowBlank : true,
											id : 'fieldNameUpd34',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd34',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd34').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd34').disable();
								                    		Ext.getCmp('fieldOrderUpd34').disable();
								                    		Ext.getCmp('fontSizeUpd34').disable();
								                    		Ext.getCmp('contFormatUpd34').disable();
								                    		Ext.getCmp('fieldOrderUpd34').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd34').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd34').enable();
								                    		Ext.getCmp('fieldOrderUpd34').enable();
								                    		Ext.getCmp('fontSizeUpd34').enable();
								                    		Ext.getCmp('contFormatUpd34').enable();
								                    		Ext.getCmp('fieldOrderUpd34').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd34').allowBlank = false;
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd34').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd34').disable();
								                    		Ext.getCmp('fieldOrderUpd34').disable();
								                    		Ext.getCmp('fontSizeUpd34').disable();
								                    		Ext.getCmp('contFormatUpd34').disable();
								                    		Ext.getCmp('fieldOrderUpd34').allowBlank = true;
								                    		Ext.getCmp('fontSizeUpd34').allowBlank = true;
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd34').enable();
								                    		Ext.getCmp('fieldOrderUpd34').enable();
								                    		Ext.getCmp('fontSizeUpd34').enable();
								                    		Ext.getCmp('contFormatUpd34').enable();
								                    		Ext.getCmp('fieldOrderUpd34').allowBlank = false;
								                    		Ext.getCmp('fontSizeUpd34').allowBlank = false;
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd34',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd34',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd34').getValue(),34);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd34',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											allowBlank : true,
											id : 'contFormatUpd34',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留1',
											maxLength : 10,
											id : 'fieldNameUpd35',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd35',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd35').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd35').disable();
								                    		Ext.getCmp('fieldOrderUpd35').disable();
								                    		Ext.getCmp('fontSizeUpd35').disable();
								                    		Ext.getCmp('contFormatUpd35').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd35').enable();
								                    		Ext.getCmp('fieldOrderUpd35').enable();
								                    		Ext.getCmp('fontSizeUpd35').enable();
								                    		Ext.getCmp('contFormatUpd35').enable();
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd35').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd35').disable();
								                    		Ext.getCmp('fieldOrderUpd35').disable();
								                    		Ext.getCmp('fontSizeUpd35').disable();
								                    		Ext.getCmp('contFormatUpd35').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd35').enable();
								                    		Ext.getCmp('fieldOrderUpd35').enable();
								                    		Ext.getCmp('fontSizeUpd35').enable();
								                    		Ext.getCmp('contFormatUpd35').enable();
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd35',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ] ]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd35',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd35').getValue(),35);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd35',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd35',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留2',
											maxLength : 10,
											id : 'fieldNameUpd36',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd36',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd36').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd36').disable();
								                    		Ext.getCmp('fieldOrderUpd36').disable();
								                    		Ext.getCmp('fontSizeUpd36').disable();
								                    		Ext.getCmp('contFormatUpd36').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd36').enable();
								                    		Ext.getCmp('fieldOrderUpd36').enable();
								                    		Ext.getCmp('fontSizeUpd36').enable();
								                    		Ext.getCmp('contFormatUpd36').enable();
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd36').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd36').disable();
								                    		Ext.getCmp('fieldOrderUpd36').disable();
								                    		Ext.getCmp('fontSizeUpd36').disable();
								                    		Ext.getCmp('contFormatUpd36').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd36').enable();
								                    		Ext.getCmp('fieldOrderUpd36').enable();
								                    		Ext.getCmp('fontSizeUpd36').enable();
								                    		Ext.getCmp('contFormatUpd36').enable();
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd36',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd36',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd36').getValue(),36);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd36',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd36',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留3',
											maxLength : 10,
											id : 'fieldNameUpd37',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd37',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd37').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd37').disable();
								                    		Ext.getCmp('fieldOrderUpd37').disable();
								                    		Ext.getCmp('fontSizeUpd37').disable();
								                    		Ext.getCmp('contFormatUpd37').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd37').enable();
								                    		Ext.getCmp('fieldOrderUpd37').enable();
								                    		Ext.getCmp('fontSizeUpd37').enable();
								                    		Ext.getCmp('contFormatUpd37').enable();
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd37').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd37').disable();
								                    		Ext.getCmp('fieldOrderUpd37').disable();
								                    		Ext.getCmp('fontSizeUpd37').disable();
								                    		Ext.getCmp('contFormatUpd37').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd37').enable();
								                    		Ext.getCmp('fieldOrderUpd37').enable();
								                    		Ext.getCmp('fontSizeUpd37').enable();
								                    		Ext.getCmp('contFormatUpd37').enable();
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd37',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd37',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd37').getValue(),37);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd37',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd37',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留4',
											maxLength : 10,
											id : 'fieldNameUpd38',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd38',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd38').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd38').disable();
								                    		Ext.getCmp('fieldOrderUpd38').disable();
								                    		Ext.getCmp('fontSizeUpd38').disable();
								                    		Ext.getCmp('contFormatUpd38').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd38').enable();
								                    		Ext.getCmp('fieldOrderUpd38').enable();
								                    		Ext.getCmp('fontSizeUpd38').enable();
								                    		Ext.getCmp('contFormatUpd38').enable();
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd38').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd38').disable();
								                    		Ext.getCmp('fieldOrderUpd38').disable();
								                    		Ext.getCmp('fontSizeUpd38').disable();
								                    		Ext.getCmp('contFormatUpd38').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd38').enable();
								                    		Ext.getCmp('fieldOrderUpd38').enable();
								                    		Ext.getCmp('fontSizeUpd38').enable();
								                    		Ext.getCmp('contFormatUpd38').enable();
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd38',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd38',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd38').getValue(),38);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd38',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									},
									{
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd38',
											name : 'contFormat',
										} ]
									},
									{
										columnWidth : .20,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '保留5',
											maxLength : 10,
											id : 'fieldNameUpd39',
											name : 'fieldName',
											width : 100
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '是否启用*',
											id : 'statusUpd39',
											hiddenName : 'status',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '启用' ],
														[ '0', '停用' ] ]
											}),
											listeners: {
											      'select': function() {
													    var status = Ext.getCmp('statusUpd39').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd39').disable();
								                    		Ext.getCmp('fieldOrderUpd39').disable();
								                    		Ext.getCmp('fontSizeUpd39').disable();
								                    		Ext.getCmp('contFormatUpd39').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd39').enable();
								                    		Ext.getCmp('fieldOrderUpd39').enable();
								                    		Ext.getCmp('fontSizeUpd39').enable();
								                    		Ext.getCmp('contFormatUpd39').enable();
							                     		}
											        },
											        'change': function() {
													    var status = Ext.getCmp('statusUpd39').getValue();
								                    	if(status == '0'){
								                    		Ext.getCmp('fieldNameUpd39').disable();
								                    		Ext.getCmp('fieldOrderUpd39').disable();
								                    		Ext.getCmp('fontSizeUpd39').disable();
								                    		Ext.getCmp('contFormatUpd39').disable();
								                    	}else{
								                    		Ext.getCmp('fieldNameUpd39').enable();
								                    		Ext.getCmp('fieldOrderUpd39').enable();
								                    		Ext.getCmp('fontSizeUpd39').enable();
								                    		Ext.getCmp('contFormatUpd39').enable();
							                     		}
											        }
												
											},
											width : 50
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'combo',
											fieldLabel : '字体大小*',
											width : 50,
											value : '1',
											displayField : 'displayField',
											valueField : 'valueField',
											emptyText : '请选择字体大小',
											blankText : '字体大小不能为空',
											id : 'fontSizeUpd39',
											allowBlank : false,
											hiddenName : 'fontSize',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [[ '1', '小' ],
														[ '2', '中' ],
														[ '3', '大' ]]
											})
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											fieldLabel : '字段顺序',
											maxLength : 2,
											allowBlank : false,
											blankText: '不能为空且不可重复',
											regex : /(\d{1,2})$/,
											width : 40,
											id : 'fieldOrderUpd39',
											name : 'fieldOrder',
											listeners: {
											      blur: function() {
											    	  compareUpdTwo(Ext.getCmp('fieldOrderUpd39').getValue(),39);
											        }
											}
										} ]
									},
									{
										columnWidth : .15,
										layout : 'form',
										hidden: true,
										items : [ {
											xtype : 'combo',
											fieldLabel : '内容类型*',
											width : 40,
											id : 'contFormatTypeUpd39',
											hiddenName : 'contFormatType',
											value : '1',
											store : new Ext.data.ArrayStore({
												fields : [ 'valueField',
														'displayField' ],
												data : [ [ '1', '自定义' ],
														[ '2', '枚举' ] ]
											}),
											width : 80
										} ]
									}, {
										columnWidth : .35,
										layout : 'form',
										items : [ {
											xtype : 'textfield',
											width : 256,
											fieldLabel : '内容',
											maxLength : 50,
											id : 'contFormatUpd39',
											name : 'contFormat',
										} ]
									}]
									}
										]
									} ]
								});


			/** ***************** 终端修改表单 ******************** */
			var updTermForm = new Ext.form.FormPanel({
				frame : true,
				height : 500,
				width : 1150,
				//layout : 'fit',
				labelWidth : 60,
				waitMsgTarget : true,
				items : [ updTermPanel ]
			});

			/** ***************** 签购单模板信息修改 ******************** */
			var updTermWin = new Ext.Window({
						title : '签购单模板信息修改',
						initHidden : true,
						header : true,
						frame : true,
						closable : false,
						modal : true,
						width : 1150,
						height : 500,
						items : [ updTermForm ],
						buttonAlign : 'center',
						closeAction : 'hide',
						iconCls : 'logo',
						resizable : false,
						buttons : [
								{
									text : '确定',
									handler : function() {
										if (updTermForm.getForm().isValid()) {
											updTermForm.getForm().submit({
																url : 'modelParameterAction.asp?flag=upd',
																waitMsg : '正在提交，请稍后......',
																success : function(form,action) {
																	showSuccessMsg(action.result.msg,updTermForm);
																	grid.getStore().reload();
																	updTermForm.getForm().reset();
																	updTermForm.getForm().clearInvalid(); 
																	updTermWin.hide();

																	// 重置表单
																	/*updTermForm.getForm().reset();
																	updTermForm.getForm().findField("modelNameUpd0").reset();
																	updTermForm.getForm().findField("fieldNameUpd0").reset();
																	updTermWin.hide();*/
																},
																failure : function(form,action) {
																	showErrorMsg(action.result.msg,updTermForm);
																},
																params : {
																	txnId : '30101',
																	subTxnId : '02'
																}
															});
										} else {
											for(var i=0;i<20;i++) {
												var a = Ext.getCmp('fieldNameUpd'+i).getValue();
												//Ext.getCmp('statusUpd'+i).getValue();
												var b = Ext.getCmp('fontSizeUpd'+i).getValue();
												var c = Ext.getCmp('fieldOrderUpd'+i).getValue();
												var d = Ext.getCmp('contFormatTypeUpd'+i).getValue();
												var e = Ext.getCmp('contFormatUpd'+i).getValue();
												//alert("第"+i+"个数据 a="+a+" b="+b+" c="+c+" d="+d+" e="+e);
											}
											showErrorMsg("页面数据不合法，或存在必填字段未填写，请核对后再提交！",updTermForm);
										}
									}
								}, {
									text : '关闭',
									handler : function() {
										updTermWin.hide();
									}
								} ]
					});

			var mainUI = new Ext.Viewport({
				layout : 'border',
				renderTo : Ext.getBody(),
				items : [ grid ]
			});

		});