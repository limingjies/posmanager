
/**
 * 当为商户组添加新规则时：
 * 	1.如果组内的所有商户都有针对新规则对应性质的映射，则直接添加规则并启用；
 *  2.如果组内有商户没有针对新规则对应性质的映射
 *  	1）为没有映射的商户配置映射关系；
 *  	2）保存新增规则并启用；
 *  
 * @param groupId		商户组Id
 * @param propertyId	性质ID
 * @param parentWin		父窗口
 * @param callBackFunc	回调函数
 */
function batchConfigMchnt4NewRule(groupId,propertyId,parentWin,callBackFunc){
	//映射结果数据集：商户、性质以及渠道商户
	var ruleAndMchtStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getNoRouteMapMchtInGroup'
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
			{name: 'uprMcht',mapping: 'text'},
			{name: 'uprTermId',mapping: 'text'}
		]),
		autoLoad: false
	}); 
	//映射结果数据显示模型			
	var ruleAndMchtColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '商户号',dataIndex: 'mchtNo',align: 'center',width: 120,id:'mchtNo'},
		{header: '商户名称',dataIndex: 'mchtNm',align: 'center',width: 220 },
		{header: '支付渠道',dataIndex: 'channel',align: 'center',width: 100},
		{header: '业务',dataIndex: 'business',align: 'center',width: 100},
		{header: '规则编号',dataIndex: 'ruleId',align: 'center',width: 100,hidden:true },
		{header: '性质',dataIndex: 'property',align: 'center',width: 80 },
		{header: '性质Id',dataIndex: 'propertyId',align: 'center',width: 100 ,hidden:true},
		{header: '渠道商户',dataIndex: 'uprMcht',align: 'center',width: 220},
		{header: '渠道终端号',dataIndex: 'uprTermId',align: 'center',width: 220,hidden:true }
	]);
	//映射结果显示列表
	var ruleAndMchtGrid = new Ext.grid.GridPanel({
		width:860,
		height:400,
		region:'center',
		store: ruleAndMchtStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: ruleAndMchtColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		}/*,
		tbar: 	[],
		listeners : {}  ,
		bbar: new Ext.PagingToolbar({
			store: ruleAndMchtStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
	 var goBackBtn = {
		xtype: 'button',
		width: 100,
		text: '返回',
		height: 30,
		handler: function() {
			parentWin.show();	//显示父窗口
			ruleAndMchtWin.close();	//关闭当前窗口
		}
	 }
	 var submitBtn = {
		xtype: 'button',
		width: 100,
		text: '确定',
		height: 30,
		handler: function() {
			for(var i=0;i<ruleAndMchtStore.getCount();i++){
				var rec = ruleAndMchtStore.getAt(i);
				if(!rec.get('uprMcht') || '请点击选择渠道商户' == rec.get('uprMcht')){
					Ext.get(ruleAndMchtGrid.getView().getRow(i)).frame();
					alert('还有映射没有配置，全部配置完成后才可提交！');
					return;
				}
			}
			parentWin.show();	//显示父窗口
			ruleAndMchtWin.close();	//关闭当前窗口
			callBackFunc(ruleAndMchtStore);
		}
	 };
	 var bottomMenuArr = [];
	 bottomMenuArr.push(goBackBtn);
	 bottomMenuArr.push(submitBtn);
	//映射结果显示窗口
	var ruleAndMchtWin = new Ext.Window({
		title: '商户映射',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 900,
		height:550,
		autoScroll: true,
		items: [
		{
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
			xtype: 'fieldset',
			defaults: {
				bodyStyle: 'padding-left: 100px'
			},
			buttonAlign: 'center',
			buttons: bottomMenuArr
		}],
		buttonAlign: 'center',
		//closeAction: 'close',
		closable: false,
		resizable: false
    });
			
	 //设置映射结果集查询条件(未配置映射关系)查询store
	 ruleAndMchtStore.on('beforeload', function(){
		ruleAndMchtStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,		
			groupId :groupId,	//商户组号
			propertyId:propertyId	//性质Id
		});
	 });
	//为映射结果记录添加选择事件处理：打开渠道商户配置		  
	 ruleAndMchtGrid.addListener('cellclick',function(grid, rowIndex, columnIndex, e){
		var record = grid.getStore().getAt(rowIndex);	//选择的规则映射数据记录
		var select = record.id;
		var store = ruleAndMchtStore;
		var propertyId = record.get('propertyId');
		/* selUprMcht = { upbrhMchtNo : '',  upbrhMchtNm : '',  upbrhTermId : '' };  //渠道商户配置结果*/
		configUprMcht(propertyId,ruleAndMchtWin,function(selUprMcht){
			ruleAndMchtStore.getById(select).set("uprMcht",selUprMcht.upbrhMchtNo + ',' + selUprMcht.upbrhMchtNm);
			ruleAndMchtStore.getById(select).set("uprTermId",selUprMcht.upbrhTermId);
		});
	 });
	 ruleAndMchtStore.load({
		 'params':{
			 start: 0,
			 groupId :groupId,	//商户组号
			 propertyId:propertyId	//性质Id
		 },
		 'callback':function(recs){
			 if(recs && recs.length>0){
				 ruleAndMchtWin.show();
				 parentWin.hide();
			 }else{
				 ruleAndMchtWin.close();
				 callBackFunc();
			 }
		 }
	 });
	
}  
	 
 /**
  * 配置渠道商户
  * 	1、打开新窗口，隐藏父窗口，在新窗口中显示指定业务性质对应的所有渠道商户。
  * 	2、选择指定的渠道商户后调用回调函数；关闭当前渠道商户窗口；显示父窗口。
  * 	3、点击返回则关闭当前窗口；显示父窗口。
  * 
  * @param propertyId	性质ID
  * @param parentWin	父窗口
  * @param outParamObj	回调函数
  */
 function configUprMcht(propertyId,parentWin,callBackFun){
	 parentWin.hide();
	 var bottomMenuArr = new Array();
	 var goBackBtn = {
		xtype: 'button',
		width: 100,
		text: '返回',
		id: 'goBackBtn',
		height: 30,
		handler: function() {
			parentWin.show();	//显示父窗口
			mchntUpbrhWin.close();	//关闭当前窗口
		}
	 }
	 var selectBtn = {
		xtype: 'button',
		width: 100,
		text: '选择',
		id: 'selectBtn',
		height: 30,
		handler: function() {
			var upbrhMchtNo = mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNo');
			var upbrhMchtNm = mchntUpbrhGrid.getSelectionModel().getSelected().get('mchtUpbrhNm');
			var upbrhTermId = mchntUpbrhGrid.getSelectionModel().getSelected().get('termId');
			parentWin.show();	//显示父窗口
			mchntUpbrhWin.close();	//关闭当前窗口
			var outParamObj = {
				'upbrhMchtNo' : upbrhMchtNo,
				'upbrhMchtNm' : upbrhMchtNm,
				'upbrhTermId' : upbrhTermId
			};
			callBackFun(outParamObj);
		}
	 };
	 bottomMenuArr.push(goBackBtn);
	 bottomMenuArr.push(selectBtn);

	 var topToolBar1 = new Ext.Toolbar({  
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
	 }) ;
	 var topToolBar2 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
       	'-',	'合规商户编号  ：  ',{
			xtype: 'textfield',
			id: 'mchtId',
			name: 'mchtId',
			vtype:'isEngNum',
			width: 140
		},'-',	'合规商户名称：',{
			xtype: 'textfield',
			id: 'mchtNm',
			name: 'mchtNm',
			width: 140
		},'-',	'所属地区  ：  ',{
			xtype: 'textfield',
			id: 'area',
			name: 'area',
			width: 140
		}]
	 });
	//渠道商户数据集			
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
	//渠道商户显示模板
	var mchntUpbrhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '渠道商户号',dataIndex: 'mchtUpbrhNo',align: 'center',width: 130,id:'msc3'},
		{header: '渠道商户名',dataIndex: 'mchtUpbrhNm',width: 130,align: 'center' },
		{header: '渠道终端号',dataIndex: 'termId',align: 'center',width: 130},
		{header: '所属地区',dataIndex: 'area',width: 130,align: 'center' },
		{header: '合规商户编号',dataIndex: 'mchtId',align: 'center',width: 130},
		{header: '合规商户名称',dataIndex: 'mchtNm',width: 130,align: 'center' ,id:'nm1'}
	]);
	//渠道商户显示列表
	var mchntUpbrhGrid = new Ext.grid.GridPanel({
		width:780,
		height:400,
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
			msg: '正在加载渠道商户记录列表......'
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
			//將bar渲染到tbar裏面，通过listeners事件  
			'render' : function() {  
				topToolBar2.render(this.tbar);
				topToolBar2.render(this.tbar);
			}  
		},
		bbar: new Ext.PagingToolbar({
			store: mchntUpbrhStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	//渠道商户显示窗口
	var mchntUpbrhWin = new Ext.Window({
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
			buttonAlign: 'center',
			buttons: bottomMenuArr
		}],
		buttonAlign: 'center',
		//closeAction: 'close',
		closable: false,
		resizable: false
	});
	//设置渠道商户结果集查询参数
	mchntUpbrhStore.on('beforeload', function(){
		Ext.getCmp('selectBtn').disable();
		Ext.getCmp('goBackBtn').enable();
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
	//为渠道商户列表选择模型添加事件处理
	mchntUpbrhGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntUpbrhGrid.getView().getRow(mchntUpbrhGrid.getSelectionModel().last)).frame();
			Ext.getCmp('selectBtn').enable();
			
		}
	});
	mchntUpbrhStore.load();
	mchntUpbrhWin.show();
}
	
