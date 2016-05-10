var tmkStore;
var tmkGrid;

function sendTmk(value,sta){
	if(sta==1){
		Ext.MessageBox.confirm("请确认","密钥已生成，是否再次更新?",function(button,text){ 
			if(button=="yes"){
				DownloadTmk.downloadTmk(value, callbackSendLoad);
			}
		});
	}else{
		DownloadTmk.downloadTmk(value, callbackSendLoad);
	}
};

function callbackSendLoad(returnvalue){
	
	// 密钥管理机构
//	var cupBrhId = returnvalue.split('|')[0];
	// 密钥
//	var downloadTmk = returnvalue.split('|')[1];
	
	var ret = returnvalue.split('|')[0];
	
	var termId2 = returnvalue.split('|')[1];
	
	var mchtid2 = returnvalue.split('|')[2];
	
	if(returnvalue == "-1"){
		alert("获得tmk失败！");
		return ;
	}

	// 向pos注入密钥
//	var ret=paybox.setWorkKey(cupBrhId,cupBrhId,mchtid2,termId2,downloadTmk);
	if(ret=="00"){
		DownloadTmk.updateStatus(mchtid2,termId2,saveTmkStatus);
	}
	else{
		alert("注入密钥失败");
	}
};

function saveTmkStatus(returnvalue){
	if(returnvalue=="00"){
		alert("更新密钥成功");
	}
	else{
		alert("更新密钥失败");
	}
	tmkStore.reload();
};

Ext.onReady(function(){
	
	// 查询TMK信息
	tmkStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=tmkInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termId'
		},[
//		   	{name: 'mchtNo',mapping: 'mchtNo'},//客户编号
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'termId',mapping: 'termId'},
			{name: 'tmkValue',mapping: 'tmkValue'},
			{name: 'sta',mapping: 'sta'},
			{name: 'oprate',mapping: 'oprate'}
		])
//		autoLoad: true
	});

	
	function oprate(val,metadata,record,rowIndex){
		var mchtid = record.get('mchtId');
		var termid = record.get('termId');
		var sta = record.get('sta');
		var value = mchtid + "@" + termid;
		return "<button class=btn_2k3 id="+value+" name="+ sta +" onclick='javascript:sendTmk(this.id,this.name)'>生成</button>";
	};
	
	var selectionModel = new Ext.grid.CheckboxSelectionModel();
	var colModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		selectionModel,
		{header: '商户',dataIndex: 'mchtId',width: 120 ,sortable: true},
		{header: '商户名',dataIndex: 'mchtNm',width: 250},
		{header: '终端编号',dataIndex: 'termId',width: 90},
		{header: 'TMK',dataIndex: 'tmkValue',width: 300},
		{header: '状态',dataIndex: 'sta',width: 60,renderer:function(val) {
			if("1" == val) 
			  {return "<font color='green'>已生成</font>";}
			else 
			  {return "<font color='red'>未生成</font>";}
		}},
		{header: '操作',dataIndex: 'oprate',width: 50,renderer:oprate}
	]);
	
	 var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        buttonAlign: 'center',
        items: [new Ext.form.TextField({
                id: 'termNoQ',
                name: 'termNo',
                fieldLabel: '终端号',
                anchor: '90%'
            }),{
			xtype: 'dynamicCombo',
	        methodName: 'getMchntNo',
			id:'mchnNoQ',
//			labelStyle: 'padding-left: 5px',
			fieldLabel: '商户号',
			hiddenName: 'mchnNo',
			editable: true,
			anchor: '90%'
		}
//		,{
//        	xtype: 'basecomboselect',
//			store: cardStores,
//			id:'cardIdQuerrys',
////			labelStyle: 'padding-left: 5px',
//			fieldLabel: '终端号',
//			hiddenName: 'cardIdQuerry',
//			editable: true,
//			anchor: '90%'  
//       		}
       		],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                tmkStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
	
    tmkStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
            start: 0,
             mchnNo: Ext.getCmp('mchnNoQ').getValue(),
             termId: Ext.getCmp('termNoQ').getValue()
        });
	});
	
	tmkStore.load();
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
	var createTmkFileMenu = {
		text: '生成终端密钥文件',
        width: 85,
        id: 'createTmkFile',
        iconCls: 'add',
        handler:function() {
        	var records = tmkGrid.getSelectionModel().getSelections();
        	var value = records[0].data.mchtId + "@" + records[0].data.termId;
        	for(var i=1; i<records.length; i++){
        		var com = records[i].data.mchtId + "@" + records[i].data.termId;
        		value = value + "#" + com;
        	}
        	DownloadTmk.createTmkFile(value, callbackCreateTmkFile);
        }
	};
//新增终端密钥删除by xpf
	var deleteTermTmkMenu = {
		text: '删除终端密钥',
        width: 85,
        id: 'deleteTermTmk',
        iconCls: 'delete',
        disabled: true,
        handler:function() {
        	var records1 = tmkGrid.getSelectionModel().getSelections();
        	var value1 = records1[0].data.mchtId + "@" + records1[0].data.termId;
        	for(var i=1; i<records1.length; i++){
        		var com1 = records1[i].data.mchtId + "@" + records1[i].data.termId;
        		value1 = value1 + "#" + com1;
        	}
        	showConfirm('删除终端密钥后不可恢复,确定删除吗？',tmkGrid,function(bt) {
				if(bt == 'yes') {
					T30204.deleteTermTmk(value1, callbackDeleteTermTmk);
//					tmkStore.load();
					
				}
			});
        }
	};
	
	function callbackCreateTmkFile(returnvalue){
		if(returnvalue=="" || returnvalue==(null)){
			alert("生成终端密钥文件失败!");	
		}else{
			// TODO：下载文件
			alert("生成终端密钥文件成功!");	
			window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + returnvalue;
		}	
//		if(returnvalue=="00"){alert("生成终端密钥文件成功!");}
//		else{alert("生成终端密钥文件失败!");}
	};
	
	function callbackDeleteTermTmk(returnvalue1){
		if(returnvalue1=="success"){
//			alert("删除终端密钥成功!");	
			showSuccessMsg("删除终端密钥成功",tmkGrid);
			tmkStore.reload();
		}else{
//			alert("删除终端密钥失败!");	
			showErrorMsg("删除终端密钥失败",tmkGrid);
		}
	};
	
	var menuArr = new Array();
	
	menuArr.push(queryMenu);
	menuArr.push(createTmkFileMenu);
//	menuArr.push(deleteTermTmkMenu);
	tmkGrid = new Ext.grid.GridPanel({
		title: '商户终端密钥生成',
		region: 'center',
		iconCls: 'T30204',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: tmkStore,
		tbar: menuArr,
		cm: colModel,
		sm: selectionModel,
		clicksToEdit: true,
		forceValidation: true,
		loadMask: {
			msg: '正在加载信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: tmkStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell,
		}
	});
	
	tmkGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(tmkGrid.getView().getRow(tmkGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = tmkGrid.getSelectionModel().getSelected();
//			Ext.getCmp('deleteTermTmk').enable();
		}
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [tmkGrid],
		renderTo: Ext.getBody()
	});
});