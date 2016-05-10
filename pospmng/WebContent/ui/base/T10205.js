Ext.onReady(function() {	

	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T10205Action.asp?method=upload',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.txt;*.TXT',
		fileTypesDescription : '文本文件(*.txt;*.TXT)',
		title: '银联卡表文件上传',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			
		},
		completeMethod: function() {
			oprGrid.getStore().reload();
		},
		postParams: {
			txnId: '10205',
			subTxnId: '04'
		}
	});

	
	
	var cardStore = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('CARD_TYPE_TL',function(ret){
		cardStore.loadData(Ext.decode(ret));
	});
	
	
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bankBinInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'ind',mapping: 'ind'},	
			{name: 'insIdCd',mapping: 'insIdCd'},	
			{name: 'cardDis',mapping: 'cardDis'},
			{name: 'cardTp',mapping: 'cardTp'},
			{name: 'acc1Offset',mapping: 'acc1Offset'},
			{name: 'acc1Len',mapping: 'acc1Len'},
			{name: 'acc1Tnum',mapping: 'acc1Tnum'},
			{name: 'acc2Offset',mapping: 'acc2Offset'},
			{name: 'acc2Len',mapping: 'acc2Len'},
			{name: 'acc2Tnum',mapping: 'acc2Tnum'},
			{name: 'binOffSet',mapping: 'binOffSet'},
			{name: 'binLen',mapping: 'binLen'},
			{name: 'binStaNo',mapping: 'binStaNo'},	
			{name: 'binEndNo',mapping: 'binEndNo'},	
			{name: 'binTnum',mapping: 'binTnum'}
		]),
		autoLoad: true
	});
	
	
	var oprColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(),
//			{header: '索引',dataIndex: 'ind',width: 60},
    		{header: '发卡行编号',dataIndex: 'insIdCd',align: 'center',width: 110},
    		{header: '发卡行名称',dataIndex: 'cardDis',width: 140,id:'cardDis'},
    		{
            header: '交易卡种',align: 'center',
            dataIndex: 'cardTp',
            width: 120,
            editor: {
					xtype: 'basecomboselect',
			        store: cardStore,
					id: 'idCardTp',
					hiddenName: 'cardTp',
					width: 160
		       },
		    renderer:function(data){
		    	if(null == data) return '';
		    	var record = cardStore.getById(data);
		    	if(null != record){
		    		return record.data.displayField;
		    	}else{
		    		return '';
		    	}
		    }
			},
    		{header: '二磁起始字节',align: 'center',dataIndex: 'acc1Offset',width: 90
          		 /*editor: new Ext.form.TextField({
          		 	maxLength: 1,
          			allowBlank: false,
          			vtype: 'isOverMax',
          			regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字',
					maskRe: /^[0-9]+$/
          		 })*/},
    		{header: '二磁长度',align: 'center',dataIndex: 'acc1Len',width: 70
       		 /*editor: new Ext.form.TextField({
     		 	maxLength: 2,
     			allowBlank: false,
     			vtype: 'isOverMax',
     			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
     		 })*/},
     		 {header: '三磁起始字节',align: 'center',dataIndex: 'acc2Offset',width: 90},
     		 {header: '三磁长度',align: 'center',dataIndex: 'acc2Len',width: 70
       		 /*editor: new Ext.form.TextField({
     		 	maxLength: 2,
     			allowBlank: false,
     			vtype: 'isOverMax',
     			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
     		 })*/},
    		/*{header: '卡号磁道号',dataIndex: 'acc1Tnum',width: 70,
          		 editor: new Ext.form.TextField({
          		 	maxLength: 1,
          			allowBlank: false,
          			vtype: 'isOverMax',
          			regex: /^[0-9]+$/,
					regexText: '该输入框只能输入数字',
					maskRe: /^[0-9]+$/
          		 })},  */  		
        	{header: '卡号长度',align: 'center',dataIndex: 'binOffSet',width: 80,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 2,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN长度',align: 'center',dataIndex: 'binLen',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 2,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			{header: '卡BIN值',align: 'center',dataIndex: 'binStaNo',width: 100,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 30,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},
			/*{header: '卡BIN终值',dataIndex: 'binEndNo',width: 70,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 30,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })},*/
			{header: '卡BIN磁道号',align: 'center',dataIndex: 'binTnum',width: 80,
      		 editor: new Ext.form.TextField({
      		 	maxLength: 1,
      			allowBlank: false,
      			vtype: 'isOverMax',
      			regex: /^[0-9]+$/,
				regexText: '该输入框只能输入数字',
				maskRe: /^[0-9]+$/
      		 })}
    	]);
		
	var delMenu = {
			text: '删除',
			width: 85,
			iconCls: 'delete',
			disabled: true,
			handler: function() {
				if(oprGrid.getSelectionModel().hasSelection()) {
					var rec = oprGrid.getSelectionModel().getSelected();
					
					showConfirm('确定要删除该条卡表信息吗？',oprGrid,function(bt) {
						//如果点击了提示的确定按钮
						if(bt == "yes") {
							Ext.Ajax.request({
								url: 'T10205Action.asp?method=delete',
								success: function(rsp,opt) {
									var rspObj = Ext.decode(rsp.responseText);
									if(rspObj.success) {
										showSuccessMsg(rspObj.msg,oprGrid);
										oprGrid.getStore().reload();
//										oprGrid.getTopToolbar().items.items[2].disable();
									} else {
										showErrorMsg(rspObj.msg,oprGrid);
									}
								},
								params: { 
									ind: rec.get('ind'),								
									txnId: '10205',
									subTxnId: '02'
								}
							});
						}
					});
				}
			}
		};
		var upMenu = {
			text: '保存',
			width: 85,
			iconCls: 'reload',
			disabled: true,
			handler: function() {
				var modifiedRecords = oprGrid.getStore().getModifiedRecords();
				if(modifiedRecords.length == 0) {
					return;
				}
//				showProcessMsg('正在保存卡表信息，请稍后......');
				//存放要修改的机构信息
				var array = new Array();
				for(var index = 0; index < modifiedRecords.length; index++) {
					var record = modifiedRecords[index];
					var data = {
						ind : record.get('ind'),
						cardTp: record.get('cardTp'),
						acc1Offset : record.get('acc1Offset'),
						acc1Len : record.get('acc1Len'),
						acc1Tnum: record.get('acc1Tnum'),
						acc2Offset : record.get('acc2Offset'),
						acc2Len : record.get('acc2Len'),
						acc2Tnum: record.get('acc2Tnum'),
						binOffSet: record.get('binOffSet'),
						binLen: record.get('binLen'),
						binStaNo: record.get('binStaNo'),
						binEndNo: record.get('binEndNo'),
						binTnum: record.get('binTnum')
					};
					array.push(data);
				}
				Ext.Ajax.request({
					url: 'T10205Action.asp?method=update',
					method: 'post',
					params: {
					tblBankBinInfList: Ext.encode(array),
						txnId: '10205',
						subTxnId: '03'
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
//							oprGrid.getStore().commitChanges();
							showSuccessMsg(rspObj.msg,oprGrid);
							oprGrid.getStore().reload();
						} else {
//							oprGrid.getStore().rejectChanges();
							showErrorMsg(rspObj.msg,oprGrid);
						}
//						oprGrid.getTopToolbar().items.items[4].disable();
//						hideProcessMsg();
					}
				});
			}
		};
	
	
	var download = {
			text: '下载模板',
			width: 85,
			iconCls: 'download',
//			disabled: true,
			handler: function() {
				showMask('正在下载模板，请稍后......',oprGrid);
				Ext.Ajax.request({
					url: 'T10204Action.asp',
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							hideMask();
							window.location.href = Ext.contextPath + '/ajaxDownLoadFile.asp?path='+
								rspObj.downLoadFile+'&downloadName='+rspObj.downLoadFileName;
//							window.location.href = Ext.contextPath + '/page/system/download.jsp?downLoadFile='+
//								rspObj.downLoadFile+'&downLoadFileName='+rspObj.downLoadFileName+
//								'&downLoadFileType='+rspObj.downLoadFileType;
						} else {
							hideMask();
							showErrorMsg('下载模板失败！',oprGrid);
						}
					}
					/*params: { 
						txnId: '10205',
						subTxnId: '05'
					}*/
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
		
	var upload = {
			text: '导入银联卡表文件',
			width: 85,
			id: 'upload',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};
	
	var menuArr = new Array();
	menuArr.push(delMenu);		// [0]
	menuArr.push('-');			// [1]
	menuArr.push(upMenu);		// [2]	
	menuArr.push('-');  //[3]
	menuArr.push(queryCondition);  //[4]
	menuArr.push('-');  //[5]
	menuArr.push(upload);  //[6]
	menuArr.push('-');  //[7]
	menuArr.push(download);  //[8]
	

	
	var oprGrid = new Ext.grid.EditorGridPanel({
		title: '银联卡表信息',
		iconCls: 'T10205',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: oprColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		autoExpandColumn: 'cardDis',
		loadMask: {
			msg: '正在加载卡表信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		renderTo: Ext.getBody()
	});
	//每次在列表信息加载前都将保存按钮屏蔽
	oprGrid.getStore().on('beforeload',function() {
		oprGrid.getTopToolbar().items.items[0].disable();
		oprGrid.getTopToolbar().items.items[2].disable();
		oprGrid.getStore().rejectChanges();
	});
	
	oprGrid.on({
		//在编辑单元格后使保存按钮可用
		'afteredit': function() {
			oprGrid.getTopToolbar().items.items[2].enable();
		}
	});
	
	oprGrid.getSelectionModel().on({
		'rowselect': function() {
		//行高亮
		Ext.get(oprGrid.getView().getRow(oprGrid.getSelectionModel().last)).frame();
		oprGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 450,
		autoHeight: true,
		items: [{
			xtype: 'basecomboselect',
			baseParams: 'INS_ID_CD',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '发卡行编号',
			hiddenName: 'insIdCd',
			editable: true,
			anchor: '90%'
		},{
			xtype: 'textfieldBase',
//			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '发卡行名称',
			maxLength: 20,
			id: 'cardDisName',
			name: 'cardDisName',
			anchor: '90%'
		},{
			xtype: 'basecomboselect',
			baseParams: 'CARD_TYPE_TL',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡类型',
			hiddenName: 'cardType',
			anchor: '90%'
		},{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '卡BIN值',
			id: 'binStaNo',
			name: 'binStaNo',
			vtype: 'isNumber',
			maxLength: 20,
			anchor: '90%'
		}/*,{
			xtype: 'textfield',
			labelStyle: 'padding-left: 5px',
			fieldLabel: '终止卡BIN',
			id: 'binEndNo',
			name: 'binEndNo',
			anchor: '90%'
		}*/]
	});
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [oprGrid],
		renderTo: Ext.getBody()
	});
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			insIdCd: queryForm.getForm().findField('insIdCd').getValue(),
			cardDis: queryForm.getForm().findField('cardDisName').getValue(),
			cardType: queryForm.getForm().findField('cardType').getValue(),
			binStaNo: queryForm.getForm().findField('binStaNo').getValue()
//			binEndNo: queryForm.getForm().findField('binEndNo').getValue()
		});
	});
	
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 450,
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
				mchntStore.load();
			}
		},{
			text: '清除查询条件',
			handler: function() {
				queryForm.getForm().reset();
			}
		}]
	});
});