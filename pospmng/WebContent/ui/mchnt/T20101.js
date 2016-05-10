Ext.onReady(function() {
	
	// 商户MCC数据集
	var mchntMccStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCC', function(ret) {
				mchntMccStore.loadData(Ext.decode(ret));
			});
			
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20101Action_uploadMchntInfo.asp',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '5 MB',
		fileTypes : '*.txt;*.TXT',
		fileTypesDescription : '文本文件(*.txt;*.TXT)',
		title: '商户信息文件',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
		},
		postParams: {
			txnId: '20101',
			subTxnId: '04'
		}
	});
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntInfoTmp'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'mchtNo'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtLvl',mapping: 'mchtLvl'},
			{name: 'engName',mapping: 'engName'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'licenceNo',mapping: 'licenceNo'},
			{name: 'addr',mapping: 'addr'},
			{name: 'postCode',mapping: 'postCode'},
			{name: 'commEmail',mapping: 'commEmail'},
			{name: 'manager',mapping: 'manager'},
			{name: 'contact',mapping: 'contact'},
			{name: 'commTel',mapping: 'commTel'},
			{name: 'recCrtTs',mapping: 'recCrtTs'},
			{name: 'mchtStatus',mapping: 'mchtStatus'},
			{name: 'termCount',mapping: 'termCount'},
			{name: 'crtOprId',mapping: 'crtOprId'},
			{name: 'updOprId',mapping: 'updOprId'},
			{name: 'updTs',mapping: 'updTs'},
			{name:'mchtGroupFlag',mapping:'mchtGroupFlag'},
			{name: 'rislLvl',mapping: 'rislLvl'},
			{name: 'termTmpCount',mapping: 'termTmpCount'},
			{name: 'refuseReason',mapping: 'refuseReason'}
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
		'<table width=500>',
		'<tr><td><font color=green>商户MCC：</font>{mcc:this.getmcc()}</td>',
		'<td><font color=green>商户地址：</font>{addr}</td></tr>',
		
		'<tr><td><font color=green>邮编：</font>{postCode}</td>',
		'<!--<td><font color=green>商户英文名称： </font>{engName}</td>--></tr>',
		'<tr><td><font color=green>电子邮件：</font>{commEmail}</td>',
		
		'<td><font color=green>法人代表名称：</font>{manager}</td></tr>',
		'<tr><td><font color=green>联系人姓名：</font>{contactu}</td>',
		'<td><font color=green>联系人电话：</font>{commTel}</td></tr>',
		'<tr><td><font color=green>录入柜员：</font>{crtOprId}</td>',
		'<td><font color=green>审核柜员：</font>{updOprId}</td></tr>',
		
		'<tr><td><font color=green>注册日期：</font>{recCrtTs:this.dateFromt}</td>' ,
		'<td><font color=green>最近更新时间：</font>{updTs:this.dateFromt}</td>',
		'</tr>',
		'<tr><td><font color=green>最近一次拒绝原因：</font>{refuseReason}</td>' ,
		'</tr>',
	
		
		'</table>',	
			/*'<p>商户英文名称：{engName}</p>',
			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			'<p>商户地址：{addr}</p>',
			'<p>邮编：{postCode}</p>',
			'<p>电子邮件：{commEmail}</p>',
			'<p>法人代表名称：{manager}</p>',
			'<p>联系人姓名：{contact}</p>',
			'<p>联系人电话：{commTel}</p>',
			'<p>录入柜员：{crtOprId}&nbsp;&nbsp;&nbsp;&nbsp;审核柜员：{updOprId}</p>',
			'<p>最近更新时间：{updTs}</p>',*/
				{
			getmcc : function(val){return getRemoteTrans(val, "mcc");},
			dateFromt: function(val) {
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
								+ val.substring(10, 12) + ':' + val.substring(12, 14);
					} else if (val.length == 10) {
						return val.substring(0, 2) + '-' + val.substring(2, 4) + ' '
								+ val.substring(4, 6) + ':' + val.substring(6, 8) + ':'
								+ val.substring(8, 10);
				
					} else if(val.length == 8){
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8);
					}else if(val.length == 6){
						return val.substring(0, 2) + ':' + val.substring(2, 4) + ':'
								+ val.substring(4, 6);
					}else if(val.length == 4){
						return val.substring(0, 2) + ':' + val.substring(2, 4) ;
					}else {
						return val;
					}
				}	
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'mchtNo',header: '商户编号',dataIndex: 'mchtNo',sortable: true,width: 130},
		{header: '商户名称',dataIndex: 'mchtNm',width: 200,id: 'mchtNm'},
		{header: '商户种类',dataIndex: 'mchtGroupFlag',width: 80,id: 'mchtGroupFlag',align: 'center',renderer:mchtGroupFlag},
		{header: '营业执照编号',dataIndex: 'licenceNo',width: 130},
//		{header: '注册日期',dataIndex: 'recCrtTs',width: 80,renderer: formatDt},
		{header: '风险级别',dataIndex: 'rislLvl',width: 80},
		{header: '商户状态',dataIndex: 'mchtStatus',renderer: mchntSt,width: 90},
		{header: '终端数量',dataIndex: 'termCount',width: 60},
		{header: '终端添加状态',dataIndex: 'termTmpCount',width: 90,renderer: termAddSta,align: 'center'}
	]);
	
	
	
	//终端数据部分
	var termStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchntTermInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'termNo'
		},[
			{name: 'termNo',mapping: 'termNo'},
			{name: 'termStatus',mapping: 'termStatus'},
			{name: 'termSignSta',mapping: 'termSignSta'},
			{name: 'recCrtTs',mapping: 'recCrtTs'}
		])
	});
	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 100},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 90},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',hidden:true,renderer: termSignSta,width: 60},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);
	
	
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
		}
	};
	
	var stopMenu = {
		text: '冻结',
		width: 85,
		iconCls: 'stop',
		disabled: true,
		handler:function() {
			showConfirm('确定冻结吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				}
			});
		}
	};
	
	function stopBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,stopBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_stop.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '03',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	
	var recoverMenu = {
		text: '恢复',
		width: 85,
		iconCls: 'recover',
		disabled: true,
		handler:function() {
			showConfirm('确定恢复吗？',mchntGrid,function(bt) {
				if(bt == 'yes') {
					showInputMsg('提示','请输入进行该操作的原因',true,recoverBack);
				}
			});
		}
	};
	
	function recoverBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,recoverBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_recover.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '04',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var delMenu = {
			text: '注销',
			width: 85,
			iconCls: 'recycle',
			disabled: true,
			handler:function() {
				showConfirm('注销商户信息后不可恢复,确定注销吗？',mchntGrid,function(bt) {
					if(bt == 'yes') {
						showInputMsg('提示','请输入进行该操作的原因',true,delBack);
					}
				});
			}
		};
	function delBack(bt,text) {
		if(bt == 'ok') {
			if(getLength(text) > 60) {
				alert('操作原因最多可以输入30个汉字或60个字母、数字');
				showInputMsg('提示','请输入进行该操作的原因',true,delBack);
				return;
			}
			showProcessMsg('正在提交信息，请稍后......');
			Ext.Ajax.requestNeedAuthorise({
				url: 'T20101Action_del.asp',
				params: {
					mchtNo: mchntGrid.getSelectionModel().getSelected().get('mchtNo'),
					txnId: '20101',
					subTxnId: '05',
					exMsg: text
				},
				success: function(rsp,opt) {
					var rspObj = Ext.decode(rsp.responseText);
					if(rspObj.success) {
						showSuccessMsg(rspObj.msg,mchntGrid);
					} else {
						showErrorMsg(rspObj.msg,mchntGrid);
					}
					// 重新加载商户信息
					mchntGrid.getStore().reload();
				}
			});
			hideProcessMsg();
		}
	}
	
	var detailMenu = {
		text: '查看详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);	
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
			text: '导入商户信息文件',
			width: 85,
			id: 'upload',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};
	
	menuArr.push(editMenu);    //[0]
	menuArr.push('-');         //[1]
	menuArr.push(stopMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(recoverMenu); //[4]
	menuArr.push('-');         //[5]
	menuArr.push(delMenu);     //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]
	// menuArr.push('-');         //[11]
	// menuArr.push(upload);  //[12]
	
	var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
            selectTermInfoNew(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};
	
	
	termGrid = new Ext.grid.GridPanel({
		title: '终端信息',
		region: 'east',
		width: 260,
		iconCls: 'T301',
		split: true,
		collapsible: true,
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'termSta',
		stripeRows: true,
		store: termStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: termColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: [termDetailMenu],
		loadMask: {
			msg: '正在加载终端信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: termStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: false
		})
	});
	
	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '商户信息维护',
		region: 'center',
		iconCls: 'T20101',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'mchtNm',
		stripeRows: true,
		store: mchntStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: mchntColModel,
		clicksToEdit: true,
		forceValidation: true,
		tbar: menuArr,
		plugins: mchntRowExpander,
		loadMask: {
			msg: '正在加载商户信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: mchntStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	mchntStore.load();
	
	mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
		var id = mchntGrid.getSelectionModel().getSelected().data.mchtNo;
		termStore.load({
			params: {
				start: 0,
				mchntNo: id
				}
			});
	});
	termStore.on('beforeload', function() {
		termStore.removeAll();
	});
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();
	});
	
	var rec;
	
	menuArr.push(editMenu);    //[0]修改
	menuArr.push('-');         //[1]
	menuArr.push(stopMenu);    //[2]停用
	menuArr.push('-');         //[3]
	menuArr.push(recoverMenu); //[4]恢复
	menuArr.push('-');         //[5]
	menuArr.push(delMenu);     //[6]注销
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]详细
	menuArr.push('-');         //[9]
	menuArr.push(queryCondition);  //[10]查询
	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = mchntGrid.getSelectionModel().getSelected();
			if(rec.get('mchtStatus') == '0' || rec.get('mchtStatus') == 'B' || rec.get('mchtStatus') == 'A' || rec.get('mchtStatus') == '2' || rec.get('mchtStatus') == '1' || rec.get('mchtStatus') == '3' || rec.get('mchtStatus') == '4') {
				mchntGrid.getTopToolbar().items.items[0].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[0].disable();
			}
			if(rec.get('mchtStatus') == '0') {//正常可停用和查看详细
				mchntGrid.getTopToolbar().items.items[2].enable();
				mchntGrid.getTopToolbar().items.items[8].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[2].disable();
				mchntGrid.getTopToolbar().items.items[8].disable();
			}
			if(rec.get('mchtStatus') == '6') {//停用时才能恢复
				mchntGrid.getTopToolbar().items.items[4].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[4].disable();
			}
			if(rec.get('mchtStatus') == '0'||rec.get('mchtStatus') == '6') {//正常和停用时可以注销
				mchntGrid.getTopToolbar().items.items[6].enable();
			} else {
				mchntGrid.getTopToolbar().items.items[6].disable();
			}
			mchntGrid.getTopToolbar().items.items[8].enable();
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		defaults : {
			labelStyle: 'padding-left: 10px'
		},
		items: [
			/*{
		    xtype: 'basecomboselect',
	        baseParams: 'CONN_TYPE',
//			fieldLabel: '商户类型',
	        hidden:true,
			hiddenName: 'connType',
			editable: false,
			anchor: '38%'
		},*/{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '创建开始日期',
			editable: false,
			width: 180
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '创建结束日期',
			editable: false,
			width: 180
		},{
		    xtype: 'textfield',
			fieldLabel: '营业执照编号',
			id: 'queryLicenceNo',
			maxLength: 20,
			editable: false,
			width: 180
		},{
		    xtype: 'basecomboselect',
			fieldLabel: '终端添加状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','未添加'],['1','已添加']],
				reader: new Ext.data.ArrayReader()
			}),
			hiddenName: 'termTmpCount',
			editable: false,
			width: 180
		},{
		    xtype: 'basecomboselect',
	        baseParams: 'RISK_LVL',
			fieldLabel: '风险级别',
			hiddenName: 'rislLvl',
			editable: false,
			width: 180
		},{
		    xtype: 'basecomboselect',
	        baseParams: 'MCHT_GROUP_FLAG',
			fieldLabel: '商户种类',
			hiddenName: 'mchtGroupFlag',
			editable: false,
			width: 180
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','正常'],['B','添加待初审'],['1','添加待终审'],['3','修改待审核'],['6','冻结']],
				reader: new Ext.data.ArrayReader()
			}),
			width: 180
		},{
//			xtype: 'basecomboselect',
//			baseParams: 'BRH_BELOW_BRANCH',
			fieldLabel: '签约机构',
			xtype : 'dynamicCombo',
			methodName : 'getBrhId',
			id: 'idbankNo',
			hiddenName: 'bankNo',
			lazyRender: true,
			width: 300
		},{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAll',
			hiddenName: 'mchtNo',
			editable: true,
			width: 300
		},{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 300,
			listeners: {
				'select': function() {
					mchntMccStore.removeAll();
					Ext.getCmp('queryIdmcc').setValue('');
//					Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
					SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',this.value,function(ret){
						mchntMccStore.loadData(Ext.decode(ret));
					});
				},
				'change': function() {
					mchntMccStore.removeAll();
					Ext.getCmp('queryIdmcc').setValue('');
//					Ext.getDom(Ext.getDoc()).getElementById('idmcc').value = '';
					SelectOptionsDWR.getComboDataWithParameter('MCHNT_TP',this.value,function(ret){
						mchntMccStore.loadData(Ext.decode(ret));
					});
				}
			}
		},{
			xtype: 'basecomboselect',
			fieldLabel: '商户MCC',
			store: mchntMccStore,
			editable: true,
			lazyRender: true,
			width: 300,
			id: 'queryIdmcc',
			hiddenName: 'queryMcc'
		}]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 430,
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
	
	mchntStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			mchntId: queryForm.getForm().findField('mchtNo').getValue(),
			mchtStatus: queryForm.findById('mchtStatus').getValue(),
			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
			brhId: queryForm.getForm().findField('bankNo').getValue(),
			mchtGroupFlag: queryForm.getForm().findField('mchtGroupFlag').getValue(),
			rislLvl: queryForm.getForm().findField('rislLvl').getValue(),
			
			licenceNo: queryForm.findById('queryLicenceNo').getValue(),
			termTmpCount: queryForm.getForm().findField('termTmpCount').getValue(),
			mcc: queryForm.getForm().findField('queryMcc').getValue()
//			connType: queryForm.getForm().findField('connType').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid,termGrid],
		renderTo: Ext.getBody()
	});
	
});