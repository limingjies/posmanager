



//商户详细信息，在外部用函数封装
function newMchntConfig(mchntId,store){

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
				Ext.getCmp('baseInfo').hide();
				Ext.getCmp('next1').hide();
				Ext.getCmp('upbrhMcht').show();
				Ext.getCmp('next2').show();
			}
	}
	nextArr1.push(nextBt1);
	
	var nextArr2 = new Array();
	var nextBt2 = {
			xtype: 'button',
			width: 100,
			text: '保存',
			id: 'save',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				var mchtUpbrhIds='';
				var propertyIds='';
				//循环
				for (var i = 0; i < ruleAndMchtStore.getCount(); i++) {
					var text=ruleAndMchtStore.getAt(i).get('text');
					if(text==='请点击选择渠道商户'){
						Ext.MessageBox.alert('提示信息：', '' + '请选择渠道商户后再保存！');
						return;
					}else {
						var proper=ruleAndMchtStore.getAt(i).get('propertyId');
						var mchtUpbrh=text.split(',')[0];
						mchtUpbrhIds=mchtUpbrhIds+mchtUpbrh+',';
						propertyIds=propertyIds+proper+',';
					}
				}
				Ext.MessageBox.wait('正在保存','请稍后...');
				Ext.Ajax.request({//发送请求，sum并插入数据库
	                url: 'T110321Action_setNewMchtConfig.asp',
	                headers: {
	                    'userHeader': 'userMsg'
	                },
	                params: {
	                	//商户号
	                	mchtId:mchntId,
	                	//商户组号
	                	mchtgId:mchntGroupGrid.getSelectionModel().getSelected().get('mchtGid'),
	                	//渠道商户号
	                	mchtUpbrhIds:mchtUpbrhIds,
	                	//性质Id
	                	propertyIds:propertyIds
	                	
	                	
	                },
	                method: 'POST',
	                timeout: 180000,
	                success: function (response, options) {
	                	Ext.MessageBox.hide();
	                	detailWin.hide();
	                	var msg=Ext.decode(response.responseText);
	                	store.reload();
	                    Ext.MessageBox.alert('提示信息：', '' + msg.msg);
	                },
	                failure: function (response, options) {
	                	Ext.MessageBox.hide();
	                    Ext.MessageBox.alert('数据导入失败', '请求超时或网络故障,错误编号：' + response.status);
	                    store.reload();
	                }
	            });
			

				
			
			}
	}
	var nextBt3 = {
			xtype: 'button',
			width: 100,
			text: '上一步',
			id: 'nextBt3',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				Ext.getCmp('baseInfo').show();
				Ext.getCmp('next1').show();
				Ext.getCmp('upbrhMcht').hide();
				Ext.getCmp('next2').hide();
			}
	}
	nextArr2.push(nextBt3);
	nextArr2.push(nextBt2);
	// 数据集
	var mchntGroupStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getRouteMchtg'//TODO 更改Url,查询正确数据
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
		{header: '商户组编号',dataIndex: 'mchtGid',align: 'center',width: 300,id:'msc1'},
		{header: '商户组名称',dataIndex: 'mchtGname',width: 250,align: 'center' },
		{header: '商户组描述',dataIndex: 'mchtGdsp',id:'srvId',align: 'center',width:250 }
	]);
	  var mchntGroupGrid = new Ext.grid.GridPanel({
			width:800,
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
			renderTo: Ext.getBody(),
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
	  //商户性质以及渠道商户
		var ruleAndMchtStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getRouteUpbrh'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'channel',mapping: 'channel'},
				{name: 'business',mapping: 'business'},
				{name: 'property',mapping: 'property'},
				{name: 'propertyId',mapping: 'propertyId'},
				{name: 'text',mapping: 'text'}
			]),
			autoLoad: false
		}); 
		
		var ruleAndMchtColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: '支付渠道',dataIndex: 'channel',align: 'center',width: 200,id:'msc2'},
			{header: '业务',dataIndex: 'business',width: 100,align: 'center' },
			{header: '性质',dataIndex: 'property',align: 'center',width: 100 },
			{header: '性质Id',dataIndex: 'propertyId',hidden:true,align: 'center',width: 100 },
			{header: '渠道商户',dataIndex: 'text',id:'srvId2',align: 'center',width: 400 }
		]);
		  var ruleAndMchtGrid = new Ext.grid.GridPanel({
				width:780,
				height:400,
//				title: '规则商户映射控制',
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
				},{
				    id: 'upbrhMcht',
				    hidden:true,
				    labelAlign:'center',
					xtype: 'fieldset',
					title: '请设置渠道商户',
					layout: 'column',
					collapsible: true,
					width: 800,
					defaults: {
						bodyStyle: 'padding-left: 10px'
					},
					items: [ruleAndMchtGrid]
					},{
						hidden:true,
						xtype: 'fieldset',
						id: 'next2',
						disabled : false,
						defaults: {
							bodyStyle: 'padding-left: 100px'
						},
						//width: 800, 
						buttonAlign: 'center',
						buttons: nextArr2
					}],
			buttonAlign: 'center',
//			closable: false,
			closeAction: 'close',
			closable: true,
			resizable: false
	    });
	 
	  detailWin.show();
	  mchntGroupStore.on('beforeload', function(){
			Ext.getCmp('next1').disable();
//			var date=Ext.getCmp('applyTime').getValue();
//			var applyDate=dateFormat(date);
		  mchntGroupStore.removeAll();
			Ext.apply(this.baseParams, {
				start: 0
			});
		});
	  mchntGroupGrid.getSelectionModel().on({
			'rowselect': function() {
				//行高亮
				Ext.get(mchntGroupGrid.getView().getRow(mchntGroupGrid.getSelectionModel().last)).frame();
				Ext.getCmp('next1').enable();
				ruleAndMchtStore.load();
				
			}
		});
	  //业务性质查询store
	  ruleAndMchtStore.on('beforeload', function(){
			Ext.getCmp('save').disable();
//			var date=Ext.getCmp('applyTime').getValue();
//			var applyDate=dateFormat(date);
			ruleAndMchtStore.removeAll();
			Ext.apply(this.baseParams, {
				start: 0,
				mchtGid:mchntGroupGrid.getSelectionModel().getSelected().get('mchtGid')
			});
		});
	  ruleAndMchtGrid.addListener('cellclick',cellclick);
		function cellclick(grid, rowIndex, columnIndex, e){

			var record = grid.getStore().getAt(rowIndex);

			Ext.getCmp('next1').enable();
			var propertyId=record.get('propertyId');
			//弹出选择渠道商户窗口,参数：性质
			//alert(ruleAndMchtGrid.getSelectionModel().getSelected().get('propertyId'));
			
//****************************************************************渠道商户选择*******************
			detailWin.hide();
			var nextArrAdd = new Array();
			var goback = {
					xtype: 'button',
					width: 100,
					text: '返回',
					id: 'goback',
//					columnWidth: .5,
					height: 30,
					handler: function() {
						detailWin.show();
						Ext.getCmp('next2').enable();
						selectMchtUpbrhWin.hide();
					}
			}
			var select3 = {
					xtype: 'button',
					disabled:true,
					width: 100,
					text: '选择',
					id: 'select3',
//					columnWidth: .5,
					height: 30,
					handler: function() {
						var upbrhMchtNo=mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNo');
						var upbrhMchtNm=mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNm');
						var idStr = ruleAndMchtGrid.getSelectionModel().getSelected().id;
						ruleAndMchtStore.getById(idStr).set("text",upbrhMchtNo+','+upbrhMchtNm)
						detailWin.show();
						Ext.getCmp('save').enable();
						selectMchtUpbrhWin.hide();
					}
			}
			var select3=select3;
			nextArrAdd.push(goback);
			nextArrAdd.push(select3);
		//商户性质以及渠道商户
		var mchntUpbrhStore = new Ext.data.Store({
			proxy: new Ext.data.HttpProxy({
				url: 'gridPanelStoreAction.asp?storeId=getmchtUpbrh'
			}),
			reader: new Ext.data.JsonReader({
				root: 'data',
				totalProperty: 'totalCount'
			},[
				{name: 'mchtUpbrhNo',mapping: 'mchtUpbrhNo'},
				{name: 'mchtUpbrhNm',mapping: 'mchtUpbrhNm'}
				
			]),
			autoLoad: false
		}); 

		var mchntUpbrhColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
			{header: '渠道商户号',dataIndex: 'mchtUpbrhNo',align: 'center',width: 300,id:'msc3'},
			{header: '渠道商户名',dataIndex: 'mchtUpbrhNm',width: 300,align: 'center' ,id:'nm1'}
		]);
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
		  var mchntUpbrhGrid = new Ext.grid.GridPanel({
				width:800,
				height:400,
//				title: '规则商户映射控制',
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
							}}
						],
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
		  mchntUpbrhStore.on('beforeload', function(){
//				var date=Ext.getCmp('applyTime').getValue();
//				var applyDate=dateFormat(date);
				mchntUpbrhStore.removeAll();
				Ext.apply(this.baseParams, {
					start: 0,
					mchtUpbrhNo:Ext.getCmp('qmchtNo').getValue(),
					area:Ext.getCmp('area').getValue(),
					mchtId:Ext.getCmp('mchtId').getValue(),
					mchtNm:Ext.getCmp('mchtNm').getValue(),
					properTyId:ruleAndMchtGrid.getSelectionModel().getSelected().get('propertyId')
				});
			});
		  mchntUpbrhStore.load();
		  mchntUpbrhGrid.getSelectionModel().on({
				'rowselect': function() {
					//行高亮
					Ext.get(mchntGroupGrid.getView().getRow(mchntGroupGrid.getSelectionModel().last)).frame();
					Ext.getCmp('select3').enable();
					
				}
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
					width: 800,
					defaults: {
						bodyStyle: 'padding-left: 10px'
					},
					items: [mchntUpbrhGrid]
					},{
							xtype: 'fieldset',
							id: 'select',
							disabled : false,
							defaults: {
								bodyStyle: 'padding-left: 100px'
							},
							//width: 800, 
							buttonAlign: 'center',
							buttons: nextArrAdd
						}],
				buttonAlign: 'center',
//				closable: false,
				closeAction: 'close',
				closable: true,
				resizable: false
		  });
		  selectMchtUpbrhWin.show();

		
		
		}

	}
