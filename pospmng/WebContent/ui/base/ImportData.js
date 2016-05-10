Ext.onReady(function() {
	var timer;
	var dialog ;
	 // 文件上传窗口
		dialog = new UploadDialog({
			uploadUrl : 'DataImportAction.asp',
			filePostName : 'files',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '20 MB',
			fileTypes : '*.csv',
			fileTypesDescription : 'csv文件',
			title: '上传商户文件',
			scope : this,
			onEsc: function() {
				this.hide();
			},
			onUploadSuccess : function(file, serverData){
				hideProgressBar();
				resultProc(serverData);
				dialog.clearList();
				dialog.hide();
			},
			onUploadStart : function(file){
				showProgressBar('导入');
			}
		});
		//dialog.show();
	function hideProgressBar(){
		Ext.MessageBox.updateProgress(100, '100%');  
		clearInterval(timer);
//		setTimeout(function(){
//			Ext.MessageBox.hide();  
//		},800);
	}
	function showProgressBar(title){
		Ext.MessageBox.progress(title, "正在"+title+"数据，请稍后...", 0+"%");
//		setTimeout(function(){
			timer = setInterval(function(){
					Ext.Ajax.request({
						url: Ext.contextPath+'/ProgressData?' + Math.random(),
						params: {
							
						},
						success: function(rsp,opt) {
							var p = Ext.decode(rsp.responseText);
							p = p*1.0;
							Ext.MessageBox.updateProgress(p/100, p+'%');  
						}
					});
			},1000);
//		},1000);
	}
	
	function resultProc(data) {
		var resObj = Ext.util.JSON.decode(data);
		if(resObj.success){
			reasonStore.removeAll();
			var result = Ext.util.JSON.decode(resObj.msg);
			var errList = result.errInfoList;
//			alert(resultString);
			var msg="总的记录数:"+(result.totalRows);
			msg+=" 导入成功的记录数:"+result.successRows;
			msg+=" 导入出错的记录数:"+result.failRows;
			if (result.failRows > 100){
				msg+="（列表只显示部分错误信息，请下载文件查看全部错误信息。）";
			}
			tip.setText(msg);
			for (var i = 0; i < errList.length; i++) {
				var newRec=new resultCreate();
				newRec.set('index',errList[i].index);
				newRec.set('reason',errList[i].reason);
				reasonStore.insert(i,newRec);
			}

			hideMask();
			
			var icon = Ext.MessageBox.ERROR;
			var message = '';
			if (result.totalRows == 0) {
				message = '没有符合条件的数据被处理。';
			}else if (result.failRows == 0){
				icon = 'message-success';
				message = '成功处理完成。';
			}else if (result.successRows == 0){
				message = '数据处理全部失败,请查看详细出错信息。';
			}else{
				message = '部分数据处理失败,请查看详细出错信息。';
			}
			Ext.MessageBox.show({
				msg: message,
				title: '结束',
				icon: icon,
				modal: true,
				width: 250,
				buttons: Ext.MessageBox.OK
			});
			
			var resultFile = result.resultFile;
//			alert(resultFile);
			if(resultFile != null) {
				window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path=' + resultFile;
			}
		} else {
			Ext.Msg.alert("提示信息：","系统错误！");
		}
	}
	
//	==============================详细规则商户=======================================
	// 数据集
	var reasonStore = new Ext.data.Store({
		id:'routeDetailStore',
		proxy: new Ext.data.HttpProxy({
			url: ''
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'index',mapping: 'index'},
			{name: 'reason',mapping: 'reason'}
		])
	}); 
	
	var reasonColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{header: '行号/编号',dataIndex: 'index',align: 'center',width: 120 },
		{header: '失败原因',dataIndex: 'reason',id:'reason',width: 450 ,align: 'left'}
	]);
	
	var tip= new Ext.form.Label({text: '数据导入:暂无导入信息'});
	var tipBar = new Ext.Toolbar({items:[tip]});
	
	var sm=new Ext.grid.RowSelectionModel({singleSelect: true});
	var grid = new Ext.grid.GridPanel({
		title: '数据导入',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'reason',
		store: reasonStore,
		sm: sm,
		cm: reasonColModel,
		forceValidation: true,
		loadMask: {
			msg: '正在加载数据导入信息......'
		},
		tbar: 	['数据类型',{
			xtype: 'combo',
			fieldLabel :'数据类型',
			displayField: 'displayField',
			valueField: 'valueField',
			hiddenName: 'choose',
			id:'chooseId',
			editable: false,
			blankText: '请选择',
			width: 140,
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				//data: [['0','合作伙伴'],['1','商户'],['2','终端'],['3','商户照片']],
				data: [['1','商户'],['2','终端'],['3','商户照片']],
				reader: new Ext.data.ArrayReader()
			}),
			listeners:{
				'select':function(){
					if(''===Ext.getCmp('chooseId').getValue()){
						Ext.Msg.alert('提示信息：','请选择要导入的数据类型！');
						return;
					}
					if('3'===Ext.getCmp('chooseId').getValue()){
						Ext.getCmp('dataImport').hide();
						Ext.getCmp('picImport').show();
						Ext.getCmp('path').show();
						Ext.getCmp('dataSyn').hide();
						Ext.getCmp('importTime').hide();						
					} else {
						dialog.postParams={
								method:Ext.getCmp('chooseId').getValue(),
								importTime:Ext.getCmp('importTime').getValue()
						}
						dialog.refreshParams();
						Ext.getCmp('dataImport').show();
						Ext.getCmp('dataImport').enable();
						Ext.getCmp('dataSyn').show();
						Ext.getCmp('dataSyn').enable();
						Ext.getCmp('picImport').hide();
						Ext.getCmp('path').hide();
						Ext.getCmp('importTime').show();
					}
				},
				'change':function(){
					if(''===Ext.getCmp('chooseId').getValue()){
						Ext.Msg.alert('提示信息：','请选择要导入的数据类型！');
						return;
					}
					if('3'===Ext.getCmp('chooseId').getValue()){
						Ext.getCmp('dataImport').hide();
						Ext.getCmp('picImport').show();
						Ext.getCmp('path').show();
						Ext.getCmp('dataSyn').hide();
						Ext.getCmp('importTime').hide();
					} else {
						dialog.postParams={
								method:Ext.getCmp('chooseId').getValue(),
								importTime:Ext.getCmp('importTime').getValue()
						}
						dialog.refreshParams();
						Ext.getCmp('dataImport').show();
						Ext.getCmp('dataImport').enable();
						Ext.getCmp('dataSyn').show();
						Ext.getCmp('dataSyn').enable();
						Ext.getCmp('picImport').hide();
						Ext.getCmp('path').hide();
						Ext.getCmp('importTime').show();
					}
				}
			}
		},'-',{
			xtype: 'textfield',
			fieldlabel: '时间',
			name: 'importTime',
			id: 'importTime',
			maxLength:14,
			minLength:14,
			allowBlank:false,
			blankText: '请输入处理时间',
			emptyText : '请输入处理时间',
			width: 120,
		},'-',{
			xtype: 'button',
			text: '导入',
			name: 'dataImport',
			disabled:true,
			id: 'dataImport',
			iconCls: 'add',
			width: 80,
			handler:function() {
				
				if(''===Ext.getCmp('chooseId').getValue()){
					Ext.Msg.alert('提示信息：','请选择要导入的数据类型！');
					return;
				}
				if(!Ext.getCmp('importTime').isValid()){
					Ext.Msg.alert('提示信息：','请选择要导入的时间！');
					return;
				}
				dialog.postParams={
						method:Ext.getCmp('chooseId').getValue(),
						importTime:Ext.getCmp('importTime').getValue()
				}
				dialog.refreshParams();
				dialog.show();
			}
		},'-',{
			xtype: 'button',
			text: '数据同步',
			name: 'dataSyn',
			disabled:true,
			id: 'dataSyn',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				if(''===Ext.getCmp('chooseId').getValue()){
					Ext.Msg.alert('提示信息：','请选择要同步的数据类型！');
					return;
				}
				if(!Ext.getCmp('importTime').isValid()){
					Ext.Msg.alert('提示信息：','请选择要同步的时间！');
					return;
				}
				
				Ext.Ajax.request({
					async:false,
					url: 'DataImportAction.asp?method=SynData',
					params: {
						importTime:Ext.getCmp('importTime').getValue(),
						dataType:Ext.getCmp('chooseId').getValue()
					},
					waitMsg:'请稍后......',
					timeout:1000000000000000,
					success: function(rsp,opt) {
						resultProc(rsp.responseText);
						hideProgressBar();
					}
				});
				showProgressBar('同步');
			}
		},{
			xtype: 'textfield',
			name: 'path',
			hidden:true,
			hideLabel:true,
			id: 'path',
			width: 200,
			allowBlank:false,
			blankText: '图片路径不能为空',
			emptyText : '请输入图片所在路径',
		},'-',{
			xtype: 'button',
			text: '导入',
			name: 'picImport',
			hidden:true,
			id: 'picImport',
			iconCls: 'add',
			width: 80,
			handler:function() {
				
				Ext.Ajax.request({
					url : 'DataImportAction.asp',
					params:{
						path:Ext.getCmp('path').getValue(),
						method:Ext.getCmp('chooseId').getValue()
					},
					timeout:1000000000000000,
					success : function(rsp, opt) {
//						alert(rsp.responseText);
						resultProc(rsp.responseText);
						hideProgressBar();
					},
					failure : function() {
						hideProgressBar();
						Ext.Msg.alert("提示信息：","系统错误！");
					}
				});

				showProgressBar('导入');
			}
		}
		],
		listeners : {     
            'render' : function() {  
            		tipBar.render(this.tbar);
                } ,
            'cellclick':selectableCell,
        }  ,
		/*bbar: new Ext.PagingToolbar({
			store: reasonStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})*/
	});
	var resultCreate = Ext.data.Record.create([
{
    name: 'index', //出错编号
    type: 'string'
}, {
    name: 'reason', //错误内容
    type: 'string'
}]);
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});