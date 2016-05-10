



//商户详细信息，在外部用函数封装
function showMchntDetailS(mchntGroupId,records,store){
	var mchntGroupId=mchntGroupId;
	var records=records;
	var store=store;
	//***********商户组列表********************************//
	var nextArr1 = new Array();
	var nextBt1 = {
			xtype: 'button',
			width: 100,
			text: '下一页',
			id: 'nextBt1',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				
				var oldGid=mchntGroupId;
				var newGid=mchntGroupGrid.getSelectionModel().getSelected().get('mchtGid');
				batchAddProperty(records,newGid,oldGid,detailWin,store);
			}
	}
	nextArr1.push(nextBt1);
	// 数据集
	var mchntGroupStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteMchtgWithProperty'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtGid',mapping: 'mchtGid'},
			{name: 'mchtGname',mapping: 'mchtGname'},
			{name: 'mchtGdsp',mapping: 'mchtGdsp'}
		]),
		autoLoad: true
	}); 
	
	var mchntGroupColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户组编号',dataIndex: 'mchtGid',align: 'center',width: 200,id:'msc1'},
		{header: '商户组名称',dataIndex: 'mchtGname',width: 150,align: 'center' },
		{header: '商户组描述',dataIndex: 'mchtGdsp',id:'srvId',align: 'center',width: 150 }
	]);
	  var mchntGroupGrid = new Ext.grid.GridPanel({
			//width:300,
			height:400,
//			title: '规则商户映射控制',
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
			region:'center',
			clicksToEdit: true,
			autoExpandColumn: 'msc1',
			store: mchntGroupStore,
			sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
			cm: mchntGroupColModel,
			forceValidation: true,
//			renderTo: Ext.getBody(),
			loadMask: {
				msg: '正在加载规则商户映射记录列表......'
			},
			tbar: 	[],
			listeners : {}  ,
			bbar: new Ext.PagingToolbar({
				store: mchntGroupStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
	  var detailWin = new Ext.Window({
	    	title: '商户转出',
			initHidden: true,
			header: true,
			frame: true,
			modal: true,
			width: 900,
			height:550,
			autoScroll: true,
			//autoHeight: 680,
			items: [
			        {
			    id: 'baseInfo',
			    labelAlign:'center',
				xtype: 'fieldset',
				title: '选择商户组',
				layout: 'column',
				collapsible: true,
				width: 800,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [mchntGroupGrid]
				},{
					xtype: 'fieldset',
					id: 'next1',
					disabled : true,
					defaults: {
						bodyStyle: 'padding-left: 100px'
					},
					//width: 800, 
					buttonAlign: 'center',
					buttons: nextArr1
				}],
			buttonAlign: 'center',
//			closable: false,
			closeAction: 'close',
			closable: true,
			resizable: false
	    });
	  detailWin.show();
	  mchntGroupStore.on('beforeload', function(){
		  mchntGroupStore.removeAll();
			Ext.apply(this.baseParams, {
				start: 0,
				mchtGid:mchntGroupId
			});
		});
	  mchntGroupGrid.getSelectionModel().on({
			'rowselect': function() {
				//行高亮
				Ext.get(mchntGroupGrid.getView().getRow(mchntGroupGrid.getSelectionModel().last)).frame();
				Ext.getCmp('next1').enable();
				//routeRuleDtlStore.load();
				/*routeRuleDtlStore.load({
					params: {
						start: 0,
						queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
					}
				})*/
			}
		});
		function batchAddProperty(records,newGid,oldGid,detailWin,store){
			
			//***********商户组列表********************************//
			var records=records;
			var newGid=newGid;
			var oldGid=oldGid;
			var mchtIds='';
			var detailWin=detailWin;
			var store=store;
			//重新给ruleAndMchtStore赋值，然后load
			  for (var i = 0; i < records.length; i++) {
				  var mchtNo=records[i].get('mchtNo');
				 if(records.length==1|records.length-1==i){
					 mchtIds+=mchtNo;
				 }else{
					 mchtIds=mchtIds+mchtNo+',';
				 }
			  }
			var nextArr2 = new Array();
			
			var nextBt2 = {
					xtype: 'button',
					width: 100,
					text: '保存',
					id: 'save',
//					columnWidth: .5,
					height: 30,
					handler: function() {
						
						var mchtIds='';
						var mchtUpbrhIds='';
						var propertyIds='';
						var termIds='';
						//循环
						for (var i = 0; i < ruleAndMchtStore.getCount(); i++) {
							var text=ruleAndMchtStore.getAt(i).get('text');
							if(text==='请点击选择渠道商户'){
								Ext.MessageBox.alert('提示信息：', '' + '请选择渠道商户后再保存！');
								return;
							}
							var mchtNo=ruleAndMchtStore.getAt(i).get('mchtNo');
							var mchtUpbrhId=ruleAndMchtStore.getAt(i).get('text').split(',')[0];
							var propertyId=ruleAndMchtStore.getAt(i).get('propertyId');
							var termId=ruleAndMchtStore.getAt(i).get('termId');
							mchtUpbrhIds+=mchtUpbrhId+',';
							propertyIds+=propertyId+',';
							mchtIds+=mchtNo+',';
							termIds+=termId+',';
						}
							Ext.MessageBox.wait('正在保存','请稍后...');
							Ext.Ajax.request({//发送请求，sum并插入数据库
				                url: 'T110301Action_bacthAddMchtUpbrh.asp',
				                headers: {
				                    'userHeader': 'userMsg'
				                },
				                params: {
				                	//商户号
				                	mchtIds:mchtIds,
				                	//商户组号
				                	newGid:newGid,
				                	oldGid:oldGid,
				                	//渠道商户号
				                	mchtUpbrhIds:mchtUpbrhIds,
				                	//性质Id
				                	propertyIds:propertyIds,
				                	//渠道终端号
				                	termIds:termIds
				                },
				                method: 'POST',
				                timeout: 180000,
				                success: function (response, options) {
				                	Ext.MessageBox.hide();
				                	detailWin.hide();
				                	detailWin1.hide();
				                	var msg=Ext.decode(response.responseText);
				                	store.reload();
				                	Ext.MessageBox.alert('提示信息：', '' + msg.msg);
				                },
				                failure: function (response, options) {
				                	Ext.MessageBox.hide();
				                	Ext.MessageBox.alert('提示信息：', '请求超时或网络故障,错误编号：' + response.status);
				                    store.reload();
				                }
			            });
					}
			}
			
			nextArr2.push(nextBt2);
			
			
			  //商户性质以及渠道商户
				var ruleAndMchtStore = new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({
						url: 'gridPanelStoreAction.asp?storeId=getRouteUpbrhAndMcht'
					}),
					reader: new Ext.data.JsonReader({
						root: 'data',
						totalProperty: 'totalCount'
					},[
						{name: 'mchtNo',mapping: 'mchtNo'},
						{name: 'mchtNm',mapping: 'mchtNm'},
						{name: 'channel',mapping: 'channel'},
						{name: 'business',mapping: 'business'},
						{name: 'property',mapping: 'property'},
						{name: 'propertyId',mapping: 'propertyId'},
						{name: 'ruleId',mapping: 'ruleId'},
						{name: 'text',mapping: 'text'},
						{name: 'termId',mapping: 'text'}
					]),
					autoLoad: true
				}); 
				
				var ruleAndMchtColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '商户号',dataIndex: 'mchtNo',align: 'center',width: 120,id:'msc2'},
					{header: '商户名称',dataIndex: 'mchtNm',width: 120,align: 'center' },
					{header: '支付渠道',dataIndex: 'channel',align: 'center',width: 100},
					{header: '业务',dataIndex: 'business',width: 100,align: 'center' },
					{header: '规则编号',dataIndex: 'ruleId',width: 100,align: 'center',hidden:true },
					{header: '性质',dataIndex: 'property',align: 'center',width: 80 },
					{header: '性质Id',dataIndex: 'propertyId',hidden:true,align: 'center',width: 100 },
					{header: '渠道商户',dataIndex: 'text',id:'srvId2',align: 'center',width: 220 },
					{header: '渠道终端号',dataIndex: 'termId',align: 'center',width: 220,hidden:true }
				]);
				  var ruleAndMchtGrid = new Ext.grid.GridPanel({
						width:860,
						height:400,
//						title: '规则商户映射控制',
						iconCls: 'risk',
						frame: true,
						border: true,
						columnLines: true,
						stripeRows: true,
						region:'center',
						clicksToEdit: true,
						autoExpandColumn: 'msc2',
						store: ruleAndMchtStore,
						sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
						cm: ruleAndMchtColModel,
						forceValidation: true,
						renderTo: Ext.getBody(),
						loadMask: {
							msg: '正在加载规则商户映射记录列表......'
						},
						tbar: 	[],
						listeners : {}  ,
						bbar: new Ext.PagingToolbar({
							store: ruleAndMchtStore,
							pageSize: System[QUERY_RECORD_COUNT],
							displayInfo: true,
							displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg: '没有找到符合条件的记录'
						})
					});
			  var detailWin1 = new Ext.Window({
			    	title: '商户映射',
					initHidden: true,
					header: true,
					frame: true,
					modal: true,
					width: 900,
					height:550,
					autoScroll: true,
					//autoHeight: 680,
					items: [
					        {
						    id: 'upbrhMcht',
						    hidden:false,
						    labelAlign:'center',
							xtype: 'fieldset',
							title: '请设置渠道商户',
							layout: 'column',
							collapsible: true,
							width: 880,
							defaults: {
								bodyStyle: 'padding-left: 10px'
							},
							items: [ruleAndMchtGrid]
							},{
								hidden:false,
								xtype: 'fieldset',
								id: 'next2',
								disabled : true,
								defaults: {
									bodyStyle: 'padding-left: 100px'
								},
								//width: 800, 
								buttonAlign: 'center',
								buttons: nextArr2
							}],
					buttonAlign: 'center',
//					closable: false,
					closeAction: 'close',
					closable: true,
					resizable: false
			    });
			 
			  detailWin1.show();
			
			  //业务性质查询store
			  ruleAndMchtStore.on('beforeload', function(){
					Ext.getCmp('next2').disable();
					ruleAndMchtStore.removeAll();
					Ext.apply(this.baseParams, {
						start: 0,
						//商户组号
						mchtGid :newGid,
						//商户号
		            	mchtIds:mchtIds,
		            	//性质Id
//		            	propertyIds:propertyIds            	
					});
				});
			  
			  ruleAndMchtGrid.addListener('cellclick',cellclick);
				function cellclick(grid, rowIndex, columnIndex, e){

					var record = grid.getStore().getAt(rowIndex);
					var select=record.id;
					var store=ruleAndMchtStore;
					var propertyId=record.get('propertyId');
					//Ext.getCmp('next2').enable();
					//弹出选择渠道商户窗口,参数：性质
					
	//****************************************************************渠道商户选择*******************
					detailWin1.hide();
					var nextArrAdd = new Array();
					var goback = {
							xtype: 'button',
							width: 100,
							text: '返回',
							id: 'goback',
//							columnWidth: .5,
							height: 30,
							handler: function() {
								detailWin1.show();
								selectMchtUpbrhWin.hide();
							}
					}
					var select1 = {
							xtype: 'button',
							width: 100,
							text: '选择',
							id: 'select1',
//							columnWidth: .5,
							height: 30,
							handler: function() {
								var upbrhMchtNo=mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNo');
								var upbrhMchtNm=mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNm');
								var termId=mchntUpbrhGrid.getSelectionModel().getSelected().get('termId');
//								var idStr = ruleAndMchtGrid.getSelectionModel().getSelected().id;
								ruleAndMchtStore.getById(select).set("text",upbrhMchtNo+','+upbrhMchtNm);
								ruleAndMchtStore.getById(select).set("termId",termId);
								detailWin1.show();
								Ext.getCmp('next2').enable();
								selectMchtUpbrhWin.hide();
							}
					}
					nextArrAdd.push(goback);
					nextArrAdd.push(select1);
//					ruleAndMchtStore.load();
					var tbar1 = new Ext.Toolbar({  
				        renderTo: Ext.grid.EditorGridPanel.tbar,  
				        items:[	
						          '-','渠道商户：',{
							  			xtype: 'dynamicCombo',
							  			methodName: 'getUpbrhMcht',
							  			hiddenName: 'queryMchtUp',
							  			id: 'qmchtNo',
							  			mode:'local',
							  			triggerAction: 'all',
							  			editable: true,
							  			lazyRender: true,
							  			width: 250
							            }]  
				 }) 
					var tbar3 = new Ext.Toolbar({  
				        renderTo: Ext.grid.EditorGridPanel.tbar,  
				        items:[
				       			'-',	'合规商户编号  ：  ',{
							xtype: 'textfield',
							id: 'mchtId',
							name: 'mchtId',
							vtype:'isEngNum',
							//maxLength: 10,
							width: 140
						},
				       			'-',	'合规商户名称：',{
							xtype: 'textfield',
							id: 'mchtNm',
							name: 'mchtNm',
							//vtype:'isEngNum',
							//maxLength: 10,
							width: 140
						},
				       			'-',	'所属地区  ：  ',{
							xtype: 'textfield',
							id: 'area',
							name: 'area',
							//vtype:'isEngNum',
							//maxLength: 10,
							width: 140
						}
				        
				        
				        
				        ]  
				 }) 
				var mchntUpbrhStore = new Ext.data.Store({
					proxy: new Ext.data.HttpProxy({
						url: 'gridPanelStoreAction.asp?storeId=getmchtUpbrh'
					}),
					reader: new Ext.data.JsonReader({
						root: 'data',
						totalProperty: 'totalCount'
					},[
						{name: 'mchtUpbrhNo',mapping: 'mchtUpbrhNo'},
						{name: 'mchtUpbrhNm',mapping: 'mchtUpbrhNm'},
						{name: 'termId',mapping: 'termId'},
						{name: 'area',mapping: 'area'},
						{name: 'mchtId',mapping: 'mchtId'},
						{name: 'mchtNm',mapping: 'mchtNm'}
						
					]),
					autoLoad: false
				}); 

				var mchntUpbrhColModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer(),
					{header: '渠道商户号',dataIndex: 'mchtUpbrhNo',align: 'center',width: 130,id:'msc3'},
					{header: '渠道商户名',dataIndex: 'mchtUpbrhNm',width: 130,align: 'center' },
					{header: '渠道终端号',dataIndex: 'termId',align: 'center',width: 130},
					{header: '所属地区',dataIndex: 'area',width: 130,align: 'center' },
					{header: '合规商户编号',dataIndex: 'mchtId',align: 'center',width: 130},
					{header: '合规商户名称',dataIndex: 'mchtNm',width: 130,align: 'center' ,id:'nm1'}
				]);
				  var mchntUpbrhGrid = new Ext.grid.GridPanel({
						width:780,
						height:400,
//						title: '规则商户映射控制',
						iconCls: 'risk',
						frame: true,
						border: true,
						columnLines: true,
						stripeRows: true,
						region:'center',
						clicksToEdit: true,
						autoExpandColumn: 'msc3',
						store: mchntUpbrhStore,
						sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
						cm: mchntUpbrhColModel,
						forceValidation: true,
						renderTo: Ext.getBody(),
						loadMask: {
							msg: '正在加载规则商户映射记录列表......'
						},
						tbar: 	[{
							
							xtype: 'button',
							text: '查询',
							disabled:false,
							name: 'query',
							id: 'queryDetail',
							iconCls: 'query',
							width: 80,
							handler:function() {
								mchntUpbrhStore.load();
							}
						}],
						listeners : {
						     //將第二個bar渲染到tbar裏面，通过listeners事件  
				            'render' : function() {  
									tbar1.render(this.tbar);
									tbar3.render(this.tbar);
				                }  
						}  ,
						bbar: new Ext.PagingToolbar({
							store: mchntUpbrhStore,
							pageSize: System[QUERY_RECORD_COUNT],
							displayInfo: true,
							displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
							emptyMsg: '没有找到符合条件的记录'
						})
					});
				  
				  var selectMchtUpbrhWin = new Ext.Window({
				  		title: '选择渠道商户',
						initHidden: true,
						header: true,
						frame: true,
						modal: true,
						width: 800,
						//height:650,
						autoScroll: true,
						autoHeight: 580,
						items: [
						        {
						    id: 'mchtUpbrhInfo',
						    labelAlign:'center',
							xtype: 'fieldset',
							title: '选择渠道商户',
							layout: 'column',
							collapsible: true,
							width: 780,
							defaults: {
								bodyStyle: 'padding-left: 10px'
							},
							items: [mchntUpbrhGrid]
							},{
									xtype: 'fieldset',
									id: 'select2',
									disabled : false,
									defaults: {
										bodyStyle: 'padding-left: 100px'
									},
									//width: 800, 
									buttonAlign: 'center',
									buttons: nextArrAdd
								}],
						buttonAlign: 'center',
//						closable: false,
						closeAction: 'close',
						closable: true,
						resizable: false
				  });
				  mchntUpbrhStore.on('beforeload', function(){
						Ext.getCmp('select1').disable();
						Ext.getCmp('goback').enable();
//						var date=Ext.getCmp('applyTime').getValue();
//						var applyDate=dateFormat(date);
						mchntUpbrhStore.removeAll();
						Ext.apply(this.baseParams, {
							start: 0,
							properTyId:propertyId,
							mchtUpbrhNo:Ext.getCmp('qmchtNo').getValue(),
							area:Ext.getCmp('area').getValue(),
							mchtId:Ext.getCmp('mchtId').getValue(),
							mchtNm:Ext.getCmp('mchtNm').getValue(),
						});
					});
				  mchntUpbrhStore.load();
				  mchntUpbrhGrid.getSelectionModel().on({
						'rowselect': function() {
							//行高亮
							Ext.get(mchntUpbrhGrid.getView().getRow(mchntUpbrhGrid.getSelectionModel().last)).frame();
							Ext.getCmp('select1').enable();
							
						}
					});
				  selectMchtUpbrhWin.show();

				
				
				}

		

		}
	
	}