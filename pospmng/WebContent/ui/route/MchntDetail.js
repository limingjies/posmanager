



//商户详细信息，在外部用函数封装
function showMchnt(){
	//***********商户组列表********************************//
	var nextArr2 = new Array();
	var nextBt2 = {
			xtype: 'button',
			width: 100,
			text: '添加',
			id: 'next2',
//			columnWidth: .5,
			height: 30,
			handler: function() {
				var mchtGid=Ext.getCmp('routeRuleGrid').getSelectionModel().getSelected().get('mchtGid');
				var rows=Ext.getCmp('grid').getSelectionModel().getSelections(); 
				if(rows.length===0){
					Ext.Msg.alert('提示信息：','请选择商户！');
					return;
				}
				var str="";
				for(var i=0;i <rows.length;i++){
					if(rows.length===1|i===rows.length-1){
						str =str+rows[i].get('mchtNo');
					}else {
						str =str+rows[i].get('mchtNo')+',';
					}
					//alert(str);
					}
				Ext.Ajax.request({
	                url: 'T110301Action_addMchtToGroup.asp',
	                headers: {
	                    'userHeader': 'userMsg'
	                },
	                params: {mchtIds:str,mchtGid:mchtGid},
	                method: 'POST',
	                timeout: 180000,
	                success: function (response, options) {
	                	Ext.MessageBox.hide();
	                	var msg=Ext.decode(response.responseText);
	                    Ext.MessageBox.alert('提示信息：', '' +msg.msg);
	                    var store=Ext.StoreMgr.get('routeDetailStore');
	                    store.load();
	                    detailWin.hide();
	                },
	                failure: function (response, options) {
	                	Ext.MessageBox.hide();
	                	var msg=Ext.decode(response.responseText);
	                    Ext.MessageBox.alert('添加商户失败', '请求超时或网络故障,错误编号：' + msg.msg);
	                    var store=Ext.StoreMgr.get('routeDetailStore');
	                    store.load();
	                }
	            });
			}
	}
	nextArr2.push(nextBt2);
	// 数据集
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchtDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'addr',mapping: 'addr'},
			{name: 'mcc',mapping: 'mcc'}
		]),
		autoLoad: true
	}); 
	
	var mchntColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		{header: '商户编号',dataIndex: 'mchtNo',id:'msc1',align: 'center',width: 180 },
		{header: '商户名称',dataIndex: 'mchtNm',width: 150 ,align: 'center'},
		{header: '商户地区',dataIndex: 'addr',align: 'center',width: 170 },
		{header: '商户Mcc',dataIndex: 'mcc',width: 120 ,align: 'center',id:'detail2'}
	]);
	  var mchntGrid = new Ext.grid.GridPanel({
			//width:300,
			height:400,
			id:'grid',
//			title: '规则商户映射控制',
			iconCls: 'risk',
			frame: true,
			border: true,
			columnLines: true,
			stripeRows: true,
			region:'center',
			clicksToEdit: true,
			autoExpandColumn: 'msc1',
			store: mchntStore,
			sm: new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
			cm: mchntColModel,
			forceValidation: true,
//			renderTo: Ext.getBody(),
			loadMask: {
				msg: '正在加载规则商户映射记录列表......'
			},
			tbar: 	[],
			listeners : {}  ,
			bbar: new Ext.PagingToolbar({
				store: mchntStore,
				pageSize: System[QUERY_RECORD_COUNT],
				displayInfo: true,
				displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
				emptyMsg: '没有找到符合条件的记录'
			})
		});
	  var detailWin = new Ext.Window({
	    	title: '添加商户到商户组',
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
				title: '选择商户',
				layout: 'column',
				collapsible: true,
				width: 800,
				defaults: {
					bodyStyle: 'padding-left: 10px'
				},
				items: [mchntGrid]
				},{
					xtype: 'fieldset',
					id: 'nextBt2',
					disabled : true,
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
	  mchntGrid.getSelectionModel().on({
			'rowselect': function() {
				//行高亮
				Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
				Ext.getCmp('nextBt2').enable();
				//routeRuleDtlStore.load();
				/*routeRuleDtlStore.load({
					params: {
						start: 0,
						queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
					}
				})*/
			}
		});
	
	}