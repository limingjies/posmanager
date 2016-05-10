Ext.onReady(function() {
	
	// 文件上传窗口
	var uploadDialog ;
	
	function showUploadWin(params) {
		uploadDialog = new UploadDialog({
			uploadUrl : 'T20107Action.asp?method=uploadFile',
			filePostName : 'xlsFile',
			flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
			fileSize : '10 MB',
			fileTypes : '*.xls',
			fileTypesDescription : '微软Excel文件(1997-2003)',
			title : '商户信息文件上传',
			scope : this,
			onEsc : function() {
				this.hide();
			},
			completeMethod: function() {
//				uploadDialog.close();
//				checkAddOprWin.hide();
				recInfStore.reload();
			},
			postParams : {
				check : params,
				txnId : '20107',
				subTxnId : '01'
			}
		});
		uploadDialog.show();
	}
	
		
	// 添加窗口
	var checkAddOprWin = new Ext.Window({
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		layout: 'fit',
		items: [new Ext.form.FormPanel({
			frame: true,
			width: 220,
			autoHeight: true,
			labelWidth : 180,
			items: [{
				frame: false,
				xtype : 'checkbox',
				fieldLabel : '系统自动添加此批次商户管理员',
				id : 'checkAddOprFlag'
			}]
		})],
		buttonAlign: 'center',
		closeAction: 'hide',
		closable: false,
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(uploadDialog != null){
					uploadDialog.close();
				}
				showUploadWin(Ext.getCmp('checkAddOprFlag').getValue());
			}
		},{
			text: '关闭',
			handler: function() {
				checkAddOprWin.hide();
			}
		}]
	});
	
	//商户数据部分
	var recInfStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtBlukImpRecInfQuery'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[	
			{name: 'batchNo',mapping: 'batchNo'},
			{name: 'blukDate',mapping: 'blukDate'},
			{name: 'blukTime',mapping: 'blukTime'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'oprId',mapping: 'oprId'},
			{name: 'blukFileName',mapping: 'blukFileName'},
			{name: 'blukMchnNum',mapping: 'blukMchnNum'}
		])
	});

	
	var recInfColModel = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer(),
		{header: '文件批次号',dataIndex: 'batchNo',width: 200,id: 'batchNo',align: 'center'},
		{header: '执行日期',dataIndex: 'blukDate',width: 150,align: 'center',renderer: formatDt},
		{header: '执行时间',dataIndex: 'blukTime',width: 150,align: 'center',renderer: formatDt},
		{header: '执行机构',dataIndex: 'brhId',width: 150,align: 'center'},
		{header: '操作员号',dataIndex: 'oprId',width:  100,align: 'center'},
		{header: '回执文件名称',dataIndex: 'blukFileName',id: 'blukFileName',width: 200,align: 'center'},
		{header: '批导商户数量',dataIndex: 'blukMchnNum',width: 100,align: 'center',renderer:addSuffix}
	]);
	
	function addSuffix(val) {
		return val + ' 家';
	}


	/***************************查询条件*************************/
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               		'-','执行起始日期:',	{
					xtype: 'datefield',
					id: 'queryStartDate',
					name: 'queryStartDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					endDateField: 'queryEndDate',
					editable: false,
					width: 140
				},'-','执行结束日期:',	{
					xtype: 'datefield',
					id: 'queryEndDate',
					name: 'queryEndDate',
					format: 'Y-m-d',
					altFormats: 'Y-m-d',
					vtype: 'daterange',
					startDateField: 'queryStartDate',
					editable: false,
					width: 140 
				}]  
         }) 
	
	var recInfGrid = new Ext.grid.GridPanel({
		title: '商户信息批量录入',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'blukFileName',
		stripeRows: true,
		store: recInfStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: recInfColModel,
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
				recInfStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('queryStartDate').setValue('');
				Ext.getCmp('queryEndDate').setValue('');
			}
		},'-',{
				text : '下载反馈文件',
				id : 'downloadRetFile',
				iconCls : 'download ',
				disabled: true,
				handler : function() {
					Ext.Ajax.request({
						url : 'T20107Action.asp?method=downloadRetFile',
						timeout : 60000,
						waitMsg : '正在下载反馈文件，请稍等......',
						params: {
							blukFileName: recInfGrid.getSelectionModel().getSelected().get('blukFileName'),
							txnId: '20107',
							subTxnId: '02'
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
															rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg,recInfGrid);
							}
						},
						failure: function(){
							showErrorMsg(rspObj.msg,recInfGrid);
						}
					});
				}
		},'-',{
			xtype: 'button',
			iconCls: 'detail',
			text:'批量录入说明 - 帮助',
			id: 'detailbu',
			width: 100,
			handler: function(){
				Ext.MessageBox.show({
					title: '批量录入说明 - 帮助',
					msg: 	'<table border="0">' +
							'<tr>' +
							'<td><font color=green>录入说明</font>：</td>' +
							'<td>① 录入时尽可能按单个文件依次上传</td>' +
							'</tr>' +
							'<tr>' +
							'<td></td>' +
							'<td>② 上传录入成功的文件不要重复录入</td>' +
							'</tr>' +
							'<tr>' +
							'<td><font color=green>提示说明</font>：</td>' +
							'<td>提示<font color=red>系统繁忙，发生并发操作，请稍后执行批量导入</font>时，是由于系统繁忙，可稍候重新执行</td>' +
							'</tr>' +
							'<tr>' +
							'<td></td>' +
							'<td>提示<font color=red>执行批量导入严重错误！！！请尽快联系业务员</font>时，是由于系统错误，请尽快联系业务员</td>' +
							'</tr>' +
							'</table>',
					animEl: Ext.get(recInfGrid.getEl()),
					buttons: Ext.MessageBox.OK,
					modal: true,
					width: 650
				});
			}
		},'-',{
				text : '上传商户信息文件',
				iconCls : 'upload',
				id : 'upload',
				disabled : false,
				handler : function() {
					checkAddOprWin.show();
				}
		},'-',{
				text : '下载模版',
				iconCls : 'download ',
				handler : function() {
					Ext.Ajax.request({
//						url : 'T20107Action.asp?method=downloadFile',
						url: 'T2010701Action.asp',
						timeout : 60000,
						waitMsg : '正在下载模版，请稍等......',
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoadFile.asp?path='+
									rspObj.downLoadFile+'&downloadName='+rspObj.downLoadFileName;
							} else {
								showErrorMsg("下载模板失败",recInfGrid);
							}
						}
					});
				}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
					tbar1.render(this.tbar); 
                }  
        }  ,
		loadMask: {
			msg: '正在加载批量录入信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: recInfStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	recInfStore.load({
		params:{start: 0}
	});
	
	// 禁用编辑按钮
	recInfGrid.getStore().on('beforeload',function() {
		recInfGrid.getTopToolbar().items.items[4].disable();
	});
	
	var rec;
	
	recInfGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(recInfGrid.getView().getRow(recInfGrid.getSelectionModel().last)).frame();
			rec = recInfGrid.getSelectionModel().getSelected();
//			recInfGrid.getTopToolbar().items.items[0].enable();
			recInfGrid.getTopToolbar().items.items[4].enable();
		}
	});
	
	recInfStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('queryStartDate').getValue()) == 'string' ? '' : Ext.getCmp('queryStartDate').getValue().dateFormat('Ymd'),
			endDate: typeof(Ext.getCmp('queryEndDate').getValue()) == 'string' ? '' : Ext.getCmp('queryEndDate').getValue().dateFormat('Ymd')
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [recInfGrid],
		renderTo: Ext.getBody()
	});
	
});