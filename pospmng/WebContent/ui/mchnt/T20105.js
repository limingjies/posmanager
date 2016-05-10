Ext.onReady(function() {
	
	// 文件上传窗口
	var dialog = new UploadDialog({
		uploadUrl : 'T20105Action.asp?method=upload',
		filePostName : 'files',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '10 MB',
		fileTypes : '*.txt;*.TXT',
		fileTypesDescription : '文本文件(*.txt;*.TXT)',
		title: '商户信息文件',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
//			mchntStore.load();
//			mchntGrid.getStore().reload();
			this.hide();
		},
		exterMethod: function() {
			mchntGrid.getStore().reload();
		},
		postParams: {
			txnId: '20105',
			subTxnId: '01'
		}
	});
	
	//商户数据部分
	var mchntStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtCupInf'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
//			idProperty: 'MCHT_NO'
		},[
		
			{name: 'MCHT_NO',mapping: 'MCHT_NO'},
			{name: 'ACQ_INST_ID',mapping: 'ACQ_INST_ID'},
			{name: 'ACQ_INST_CD',mapping: 'ACQ_INST_CD'},
			{name: 'MCHT_NM',mapping: 'MCHT_NM'},
			{name: 'MCHT_SHORT_CN',mapping: 'MCHT_SHORT_CN'},
			
			{name: 'STLM_INS_ID',mapping: 'STLM_INS_ID'},
			{name: 'MCHT_TYPE',mapping: 'MCHT_TYPE'},
			{name: 'STAT',mapping: 'STAT'},
			{name: 'ACQ_AREA_CD',mapping: 'ACQ_AREA_CD'},
			{name: 'MCHNT_TP_GRP',mapping: 'MCHNT_TP_GRP'},
			{name: 'MCC',mapping: 'MCC'},
			{name: 'SETTLE_AREA_CD',mapping: 'SETTLE_AREA_CD'},
			{name: 'LICENCE_NO',mapping: 'LICENCE_NO'},
			{name: 'CONTACT_ADDR',mapping: 'CONTACT_ADDR'},
			{name: 'MCHT_PERSON',mapping: 'MCHT_PERSON'},
			{name: 'COMM_TEL',mapping: 'COMM_TEL'},
			{name:'MANAGER',mapping:'MANAGER'},
			{name: 'IDENTITY_NO',mapping: 'IDENTITY_NO'},
			{name: 'ADDR',mapping: 'ADDR'},
			{name: 'LICENCE_STAT',mapping: 'LICENCE_STAT'},
			{name: 'MCHT_STLM_ACCT',mapping: 'MCHT_STLM_ACCT'},
			{name: 'MCHT_ACCT_NM',mapping: 'MCHT_ACCT_NM'},
			{name: 'SETTLE_BANK_NM',mapping: 'SETTLE_BANK_NM'},
			{name: 'SETTLE_BANK_NO',mapping: 'SETTLE_BANK_NO'},
			{name: 'APPLY_DATE',mapping: 'APPLY_DATE'}
			
			
		
			
			
			
		])
	});
	
	var mchntRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
//			'<p>商户英文名称：{engName}</p>',
//			'<p>商户MCC：{mcc:this.getmcc()}</p>',
			
			'<table width=1050>',
			'<tr><td><font color=green>商户中文简称：</font> {MCHT_SHORT_CN}</td>',
			'<td><font color=green>交易商户类型： </font>{MCHT_TYPE}</td>',
			'<td><font color=green>受理地区代码：</font>{ACQ_AREA_CD}</td>',
			'<td><font color=green>商户类型： </font>{MCC}</td></tr>',
			
			
			'<tr><td><font color=green>清算地区代码：</font>{SETTLE_AREA_CD}</td>',
			'<td><font color=green>营业执照号码：</font>{LICENCE_NO}</td>',
			'<td><font color=green>商户联系人通讯地址：</font>{CONTACT_ADDR}</td>',
			'<td><font color=green>商户联系人：</font>{MCHT_PERSON}</td></tr>',
			
			
			'<tr><td><font color=green>商户联系人电话：</font>{COMM_TEL}</td>',
			'<td><font color=green>法人代表姓名：</font>{MANAGER}</td>',
			'<td><font color=green>法人代表证件号码：</font>{IDENTITY_NO}</td>',
			'<td><font color=green>营业地址：</font>{ADDR}</td></tr>',
			
			'<tr><td><font color=green>营业执照号码重复标识：</font>{LICENCE_STAT}</td>',
			'<td><font color=green>商户账号：</font>{MCHT_STLM_ACCT}</td>',
			'<td><font color=green>商户账户名称：</font>{MCHT_ACCT_NM}</td>',
			'<td><font color=green>商户开户行名称：</font>{SETTLE_BANK_NM}</td></tr>',
			'<tr><td><font color=green>商户开户行行号：</font>{SETTLE_BANK_NO}</td>',
			'<td><font color=green>申请日期：</font>{APPLY_DATE:this.date}</td></tr>',
					
			{
				getmcc : function(val){return getRemoteTrans(val, "mcc");},
				
				date : function(val){return formatDt(val);}
			}
		)
	});
	
	var mchntColModel = new Ext.grid.ColumnModel([
		mchntRowExpander,
		{id: 'MCHT_NO',header: '商户编号',dataIndex: 'MCHT_NO',sortable: true,width: 140},
		{header: '商户名称',dataIndex: 'MCHT_NM',width: 250},
		{header: '收单机构代码',dataIndex: 'ACQ_INST_ID',width:150,align: 'center',renderer:brhId},
		{header: '受理机构代码',dataIndex: 'ACQ_INST_CD',width:150,align: 'center',renderer:brhId},
		{header: '内卡结算结构代码',dataIndex: 'STLM_INS_ID',width:150,align: 'center',renderer:brhId},
		{header: '商户状态',dataIndex: 'STAT',width: 120,align: 'center',renderer:mchtStatus},
		{header: '商户组别',dataIndex: 'MCHNT_TP_GRP',width: 120,align: 'center',renderer:mchtTpGrp}
//		{header: '商户服务类型',dataIndex: 'MCHT_TYPE',width: 250,renderer:mchntSrvTp,align: 'center'},
//		{header: '套用商户类型原因',dataIndex: 'MCHT_TP_REASON',width: 130,align: 'center',renderer:mchntTp}
//		{header: 'MCC套用依据',dataIndex: 'MCC_MD',width: 80}
//		{header: '扣率算法标识',dataIndex: 'REBATE_FLAG',renderer: mchntSt},
//		{header: '登记原因码',dataIndex: 'REASON_CODE',width: 60}
	]);
	
	/**
	 * 商户等级
	 */
	function mchtLvl(val) {
		switch(val) {
			case '1' : return '大商户';
			case '2' : return '普通商户';
			case '3' : return '小商户';
			case '4' : return '超级商户';
			case '9': return '未定级';
			case '0': return '未定级';
		}
	}
		function brhId(val) {
		if(val.length==10){
			return val.substring(2,10);	
		}else{
			return val;	
		}
	}
	function mchtStatus(val) {
		switch(val) {
			case '1' : return '1-启用';
			case '2' : return '2-冻结';
			case '0': return '0-注销';
			default : return val;
		}
	}
	
	function mchntSrvTp(val) {
		switch(val) {
			case '00' : return '00-真实公益类';
			case '01' : return '01-入网优惠期套用';
			case '02': return '02-公共事业类商户';
			case '03' : return '03-批发市场类商户';
			case '04' : return '04-资金归集类商户';
			case '05': return '05-历史遗留类套用';
//			case '06' : return '服务提供机构＋接入渠道机构';
//			case '07': return '多渠道直联终端商户';
			default : return val;
		}
	}
	
	function mchntTp(val) {
		switch(val) {
			case '00' : return '00-传统POS商户';
			case '01' : return '01-服务提供机构';
			case '02': return '02-接入渠道机构';
			case '03' : return '03-CUPSECURE商户';
			case '04' : return '04-虚拟商户';
			case '05': return '05-行业商户';
			case '06' : return '06-服务提供机构＋接入渠道机构';
			case '07': return '07-多渠道直联终端商户';
			default : return val;
		}
	}
	function mchtTpGrp(val) {
		switch(val) {
			case '00' : return '00-综合零售';
			case '01' : return '01-专门零售';
			case '02': return '02-批发类';
			case '03' : return '03-住宅餐饮';
			case '04' : return '04-房地产业';
			case '05': return '05-房地产业';
			case '06' : return '06-金融业';
			case '07' : return '07-政府服务';
			case '08': return '08-教育';
			case '09' : return '09-卫生';
			case '10' : return '10-公共事业';
			case '11': return '11-居民服务';
			case '12' : return '12-商业服务';
			case '13': return '13-交通物流';
			case '14' : return '14-直销类商户';
			case '15' : return '15-租赁服务';
			case '16': return '16-维修服务';
			case '17' : return '17-其他';
			default : return val;
		}
	}
	
	/*//终端数据部分
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
	});*/
/*	var termColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'termNo',header: '终端编号',dataIndex: 'termNo',sortable: true,width: 60},
		{id: 'termSta',header: '终端状态',dataIndex: 'termStatus',renderer: termSta,width: 90},
		{id: 'termSta',header: '签到状态',dataIndex: 'termSignSta',renderer: termSignSta,width: 60},
		{id: 'recCrtTs',dataIndex:'recCrtTs',hidden:true}
	]);*/
	
	
	
	// 菜单集合
	var menuArr = new Array();
	var childWin;
	
	/*var editMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		disabled: true,
		handler:function() {
			window.location.href = Ext.contextPath + '/page/mchnt/T2010102.jsp?mchntId='+mchntGrid.getSelectionModel().getSelected().get('mchtNo');
		}
	};*/
	
	/*var stopMenu = {
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
	};*/
	
	/*function stopBack(bt,text) {
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
	}*/
	
	
	/*var recoverMenu = {
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
	};*/
	
	/*function recoverBack(bt,text) {
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
	}*/
	
	/*var delMenu = {
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
		};*/
	/*function delBack(bt,text) {
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
	}*/
	
//	var detailMenu = {
//		text: '查看详细信息',
//		width: 85,
//		iconCls: 'detail',
//		disabled: true,
//		handler:function(bt) {
//			bt.disable();
//			setTimeout(function(){bt.enable();},2000);
//			showMchntDetailS(mchntGrid.getSelectionModel().getSelected().get('mchtNo'),mchntGrid);	
//		}
//	};
	
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
			text: '商户信息导入',
			width: 85,
			id: 'upload',
			iconCls: 'upload',
			handler:function() {
				dialog.show();
			}
		};
	
	/*menuArr.push(editMenu);    //[0]
	menuArr.push('-');         //[1]
	menuArr.push(stopMenu);    //[2]
	menuArr.push('-');         //[3]
	menuArr.push(recoverMenu); //[4]
	menuArr.push('-');         //[5]
	menuArr.push(delMenu);     //[6]
	menuArr.push('-');         //[7]
	menuArr.push(detailMenu);  //[8]
	menuArr.push('-');         //[9]
*/	
	 menuArr.push(queryCondition);  //[10]
	 menuArr.push('-');         //[11]
	 menuArr.push(upload);  //[12]
	
	/*var termDetailMenu = {
		text: '详细信息',
		width: 85,
		iconCls: 'detail',
		disabled: true,
		handler:function(bt) {
			bt.disable();
			setTimeout(function(){bt.enable();},2000);
            selectTermInfoNew(termGrid.getSelectionModel().getSelected().get('termNo'),termGrid.getSelectionModel().getSelected().get('recCrtTs'));	
		}
	};*/
	
	
	/*termGrid = new Ext.grid.GridPanel({
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
	*/
/*	// 禁用编辑按钮
	termGrid.getStore().on('beforeload',function() {
		termGrid.getTopToolbar().items.items[0].disable();
	});
	
	termGrid.getSelectionModel().on({
		'rowselect': function() {
			termGrid.getTopToolbar().items.items[0].enable();
		}
	});*/
	
	
	var mchntGrid = new Ext.grid.GridPanel({
		title: '直联商户信息查询',
		region: 'center',
		iconCls: 'T20101',
		frame: true,
		border: true,
		columnLines: true,
		autoExpandColumn: 'MCHT_NO',
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
	
	/*mchntGrid.getSelectionModel().on('rowselect', function(sm, rowIdx, r) {
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
	});*/
	
	
	// 禁用编辑按钮
	mchntGrid.getStore().on('beforeload',function() {
		/*mchntGrid.getTopToolbar().items.items[0].disable();
		mchntGrid.getTopToolbar().items.items[2].disable();
		mchntGrid.getTopToolbar().items.items[4].disable();
		mchntGrid.getTopToolbar().items.items[6].disable();
		mchntGrid.getTopToolbar().items.items[8].disable();*/
	});
	
	var rec;
	
	/*menuArr.push(editMenu);    //[0]修改
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
*/	
	mchntGrid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(mchntGrid.getView().getRow(mchntGrid.getSelectionModel().last)).frame();
			/*//行高亮
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
			mchntGrid.getTopToolbar().items.items[8].enable();*/
		}
	});
	
	
	/***************************查询条件*************************/
	
	var queryForm = new Ext.form.FormPanel({
		frame: true,
		border: true,
		width: 500,
		autoHeight: true,
		labelWidth: 80,
		items: [
/*			{
		    xtype: 'basecomboselect',
	        baseParams: 'CONN_TYPE',
			fieldLabel: '商户类型',
			hiddenName: 'connType',
			editable: false,
			anchor: '38%'
		},*/
/*			{
			xtype: 'datefield',
			id: 'startDate',
			name: 'startDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			endDateField: 'endDate',
			fieldLabel: '创建开始日期',
			editable: false
		},{
			xtype: 'datefield',
			id: 'endDate',
			name: 'endDate',
			format: 'Y-m-d',
			altFormats: 'Y-m-d',
			vtype: 'daterange',
			startDateField: 'startDate',
			fieldLabel: '创建结束日期',
			editable: false
		},*/
			{
			xtype: 'dynamicCombo',
			fieldLabel: '商户编号',
			methodName: 'getMchntIdAlls',
			hiddenName: 'mchtNo',
			editable: true,
			width: 380
		},{
			xtype: 'basecomboselect',
			id: 'mchtStatus',
			fieldLabel: '商户状态',
			store: new Ext.data.ArrayStore({
				fields: ['valueField','displayField'],
				data: [['0','0-注销'],['1','1-启用'],['2','2-冻结']],
				reader: new Ext.data.ArrayReader()
			}),
			anchor: '70%'
		}
		/*,{
			xtype: 'basecomboselect',
			baseParams: 'InstId',
			fieldLabel: '所属平台机构代码',
			id: 'idbankNo',
			hiddenName: 'bankNo',
			anchor: '70%'
		}*/
		/*,{
			xtype: 'basecomboselect',
			baseParams: 'MCHNT_GRP_ALL',
			fieldLabel: 'MCC类别',
			hiddenName: 'mchtGrp',
			width: 380
		}*/
		]
	});
	var queryWin = new Ext.Window({
		title: '查询条件',
		layout: 'fit',
		width: 500,
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
			mchtStatus: queryForm.findById('mchtStatus').getValue()
//			mchtGrp: queryForm.getForm().findField('mchtGrp').getValue(),
//			startDate: typeof(queryForm.findById('startDate').getValue()) == 'string' ? '' : queryForm.findById('startDate').getValue().dateFormat('Ymd'),
//			endDate: typeof(queryForm.findById('endDate').getValue()) == 'string' ? '' : queryForm.findById('endDate').getValue().dateFormat('Ymd'),
//			brhId: queryForm.getForm().findField('bankNo').getValue()
//			mchtGroupFlag: queryForm.getForm().findField('mchtGroupFlag').getValue(),
//			connType: queryForm.getForm().findField('connType').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [mchntGrid],
		renderTo: Ext.getBody()
	});
	
});