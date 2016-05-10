//
//
// CardIssuerg - 发卡行

function multiSelectPopup(popupId,name,limit,txtfeild,initCallback,setDataCallback,param1) {
	
	var url = 'gridPanelStoreAction.asp?storeId=get' + popupId;
	
	var AllSelectedRecords = [];
	
	var proxy = null;
	
	// 卡类型（固定数据）
	if (popupId == 'CardType') {
		var data = [{id:'1',name:'借记卡'},
		            {id:'2', name:'贷记卡'},
		            {id:'3', name:'准贷记卡'},
		            {id:'4', name:'预付费卡'},
		            {id:'5', name:'公务卡'},
		            {id:'6', name:'未指明卡'}];
		proxy = new Ext.data.MemoryProxy(data);
	// 交易类型（固定数据）
	} else if (popupId == 'Txng') {
		var data = [{id:'1161',name:'余额查询'},
		            {id:'1101', name:'消费'},
		            //{id:'3101', name:'消费撤销'},
		            {id:'1011', name:'预授权'},
		            //{id:'1091', name:'预授权完成'},
		            //{id:'3011', name:'预授权撤销'},
		            //{id:'3091', name:'预授权完成撤销'},
		            {id:'5151', name:'退货'}];
		proxy = new Ext.data.MemoryProxy(data);
	// 其他
	} else {
		// 从后台获取数据
		proxy = new Ext.data.HttpProxy({
			url: url
		})
	}
	// data store && model
	var popupStore = new Ext.data.Store({
		proxy: proxy,
		reader: new Ext.data.JsonReader({
			root: 'data',
			idProperty: 'name',
			totalProperty: 'totalCount'
		},[
			{name: 'id',mapping: 'id'},
			{name: 'name',mapping: 'name'}
		]),
		autoLoad: true,
		listeners: {
			load:function (me, records, success, opts) {
			      
				  var selected = [];
				
				  if (!success || !records || records.length == 0)
			          return;

			      //根据全局的选择，初始化选中的列
			      var selModel = popupGrid.getSelectionModel();
			      for (var j = 0; j < records.length; j++) {
		              var record = records[j];
		              for(var i = 0; i < AllSelectedRecords.length; i++) {
		            	  var selRecord = AllSelectedRecords[i];
			              if (record.get("id") == selRecord.get("id")) {
			            	  selected.push(this.indexOf(record));
			              }
			          }
			      }
			      selModel.selectRows(selected);    //选中record
			}
		}
	});
	
	var popupColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		{header: 'id',dataIndex: 'id',id:'id',align: 'center',hidden:true},
		{header: name,dataIndex: 'name',width: 300,align: 'left'}
	]);
	
	var tbar1 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
      			'-',	'查询条件(ID)：',{
					xtype: 'textfield',
					id: 'queryParamId',
					name: 'queryParamId',
					//vtype:'isEngNum',
					maxLength: 120,
					width: 140
				}
      		]  
	});
	
	var tbar2 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
      			'-',	'查询条件(Name)：',{
					xtype: 'textfield',
					id: 'queryParamName',
					name: 'queryParamName',
					//vtype:'isEngNum',
					maxLength: 120,
					width: 140
				}
      		]  
	});
	
	// grid
	  var popupGrid = new Ext.grid.GridPanel({
		//width:300,
		height:400,
		id:'grid',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		store: popupStore,
		sm: new Ext.grid.CheckboxSelectionModel({singleSelect: false}),
		cm: popupColModel,
		forceValidation: true,
		deferRowRender:false,
		loadMask: {
			msg: '正在加载' + name + '列表......'
		},
		tbar: 	[{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				popupStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryParamId').setValue('');
				Ext.getCmp('queryParamName').setValue('');
				popupStore.load();
			}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
					tbar2.render(this.tbar); 
                }  
        },
		bbar: new Ext.PagingToolbar({
			store: popupStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	// window
  var popupWin = new Ext.Window({
    	title: '选择' + name,
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 350,
		height:550,
		autoScroll: true,
		//autoHeight: 680,
		items: [popupGrid],
		buttonAlign: 'center',
		closeAction: 'close',
		closable: true,
		resizable: false,
		buttons : [{
			text : '确定',
			id: 'btnOk',
			disable: true,
			handler : function() {
				var idList = '';
				
				if (AllSelectedRecords.length > limit) {
					showErrorMsg(name + '选择不能超过' + limit + '个', popupGrid);
					return;
				}
				
				for(var i = 0; i < AllSelectedRecords.length; i++) {
					if (idList != '') {
						idList += ',' + AllSelectedRecords[i].get("id");
					} else {
						idList += AllSelectedRecords[i].get("id");
					}
				}
				//
				if (setDataCallback != null) {
					idList = setDataCallback(idList);
				}
				
				txtfeild.setValue(idList);
				
				popupWin.hide();
			}
		}]
    });
  
  popupGrid.getSelectionModel().on({
		'rowselect': function(me, index, record, opts) {
			var exist = false;
			//行高亮
			Ext.get(popupGrid.getView().getRow(popupGrid.getSelectionModel().last)).frame();
			Ext.getCmp('btnOk').enable();
			for(var i = 0; i < AllSelectedRecords.length; i++) {
		    	var selrecord = AllSelectedRecords[i];
			    if (selrecord.get("id") == record.get("id")) {
			    	exist = true;
			    	break;
			    }
			}
			if (!exist) {
				AllSelectedRecords.push(record);
			}
			//routeRuleDtlStore.load();
			/*routeRuleDtlStore.load({
				params: {
					start: 0,
					queryRuleCode: routeRuleGrid.getSelectionModel().getSelected().get('ruleCode')
				}
			})*/
		},
		'rowdeselect': function (me, index, record, opts) {
			var selectedRecords = [];
	        for(var i = 0; i < AllSelectedRecords.length; i++) {
		    	var selrecord = AllSelectedRecords[i];
			    if (selrecord.get("id") != record.get("id")) {
	            	selectedRecords.push(selrecord);    //选中record，并且保持现有的选择，不触发选中事件
	        	}
	        }
	        AllSelectedRecords = selectedRecords;
	    }
	});
  
	popupStore.on('beforeload', function(){
		var txt = txtfeild.getValue();
		var idList = null;
		
		//AllSelectedRecords = [];
		if (AllSelectedRecords.length == 0) {
			if (initCallback != null) {
				txt = initCallback(txt);
			}
			if (txt != ''){
				idList = txt.split(',');
	
				for (var i = 0; i < idList.length; i++){
					AllSelectedRecords.push(new Ext.data.Record({id:idList[i], name:''}));
				}
			}
		}
		//
		Ext.apply(this.baseParams, {
			start: 0,
			param1: param1,
			queryParamId: Ext.getCmp('queryParamId').getValue(),
			queryParamName: Ext.getCmp('queryParamName').getValue()
		});
		//
		if (popupId == 'CardType' || popupId == 'Txng') {
			popupStore.loadData(proxy);
			
			popupGrid.tbar.hide();
			popupGrid.tbar.dom.style.height = 0;
		}
		//
		if (popupId == 'CardBin') {
			Ext.getCmp('queryParamName').disable();
		}
	});
    
    popupWin.show();
}