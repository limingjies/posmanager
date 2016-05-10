Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	//var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	
	var brhErrGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=brhErrInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
		   	{name: 'uuid',mapping: 'uuid'},
			{name: 'brhNo',mapping: 'brhNo'},
			{name: 'tradeType',mapping: 'tradeType'},
			{name: 'money',mapping: 'money'},
			{name: 'aproveStatus',mapping: 'aproveStatus'},
			{name: 'aproveOpr',mapping: 'aproveOpr'},
			{name: 'approveTime',mapping: 'approveTime'},
			{name: 'approveAdvice',mapping: 'approveAdvice'},
			{name: 'postStatus',mapping: 'postStatus'},
			{name: 'postTime',mapping: 'postTime'},
			{name: 'vcTranNo',mapping: 'vcTranNo'},			
			{name: 'reserver',mapping: 'reserver'},
			{name: 'crtOpr',mapping: 'crtOpr'}
		]),
		autoLoad: true
	});
	
	/*txnGridStore.load({
		params:{start: 0}
	});*/
	
	
	
	
	var brhErrColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'uuid',header: '主键',dataIndex: 'uuid',hidden: true,width: 0},
		{header: '<center>合作伙伴号</center>',dataIndex: 'brhNo',align: 'left',width: 180},
		{header: '<center>交易类型</center>',dataIndex: 'tradeType',align: 'center',renderer: tradeType,width: 120},
		{header: '<center>金额</center>',dataIndex: 'money',align: 'right',width: 120},
		{header: '<center>审批状态</center>',dataIndex: 'aproveStatus',align: 'center',renderer: aproveStatus,width: 80},
		{header: '<center>审批人</center>',dataIndex: 'aproveOpr',align: 'center',width: 70},
		{header: '<center>审批时间</center>',dataIndex: 'approveTime',align: 'center',renderer: formatDt,width: 140},
		{header: '<center>审批意见</center>',dataIndex: 'approveAdvice',align: 'left',width: 150},
		{header: '<center>入账状态</center>',dataIndex: 'postStatus',align: 'center',renderer: postStatus,width: 80},
		{header: '<center>入账时间</center>',dataIndex: 'postTime',align: 'center',renderer: formatDt,width: 140},
		{header: '<center>交易流水号</center>',dataIndex: 'vcTranNo',align: 'center',width: 80},
		{header: '<center>备注</center>',dataIndex: 'reserver',align: 'left',width: 150},
		{header: '<center>操作</center>',dataIndex: 'opration',align: 'center',renderer: opration,width: 100},
		{header: '<center>创建人</center>',dataIndex: 'crtOpr',hidden: true,width: 0}
	]);
	
	function timeShowFormat(val){
		if(val==''|val==null){
			return '';
		}
		return val.substr(0,4)+'-'+val.substr(4,2)+'-'+val.substr(6,2);
	}
	
	function tradeType(val) {
		if (val == '00')
			return '分润内扣(-)';
		if (val == '01')
			return '营销费用扣除(-)';
		if (val == '02')
			return '调退单损失(-)';
		if (val == '03')
			return '追偿损失(-)';
		if (val == '04')
			return '误划款正(+)';
		if (val == '05')
			return '误划款负(-)';
		return val;
	}
	
	function aproveStatus(val) {
		if (val == '0')
			return '未审批';
		if (val == '1')
			return '审批通过';
		if (val == '2')
			return '审批未通过';
		return val;
	}
  
	function postStatus(val) {
		if (val == '0')
			return '未入账';
		if (val == '1')
			return '已入账';
		return val;
	}
	
	function opration(val) {
        
        return "<button type='button' onclick=''>审核</button>";
    }
    
	  var tbar3 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	       			'-','合作伙伴号：',{
						xtype: 'dynamicCombo',
						methodName: 'getBrhInfoBelowBranch',
						id: 'idQuerybrhNo',
						hiddenName: 'querybrhNo',
						editable: true,
						width: 180
					},
					'-','交易类型：',{
						xtype: 'combo',
						store : new Ext.data.ArrayStore({
							fields : ['valueField',
									'displayField'],
							data : [['00', '分润内扣(-)'],
									['01', '营销费用扣除(-)'],
									['02', '调退单损失(-)'],
									['03', '追偿损失(-)'],
									['04', '误划款正(+)'],
									['05', '误划款负(-)']],
							reader : new Ext.data.ArrayReader()
						}),
						id: 'idQueryTradeType',
						hiddenName: 'queryTradeType',
						editable: true,
						width: 180
		           	},
		           	'-','审批状态：',{
						xtype: 'combo',
						store : new Ext.data.ArrayStore({
							fields : ['valueField',
									'displayField'],
							data : [['0', '未审批'],
									['1', '审批通过'],
									['2', '审批未通过']],
							reader : new Ext.data.ArrayReader()
						}),
						id: 'idQueryAproveStatus',
						hiddenName: 'queryAproveStatus',
						editable: true,
						width: 180
		           	}]  
	 }); 

	  var tbar4 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	       			'-','入账状态：',{
						xtype: 'combo',
						store : new Ext.data.ArrayStore({
							fields : ['valueField',
									'displayField'],
							data : [['0', '未入账'],
									['1', '已入账']],
							reader : new Ext.data.ArrayReader()
						}),
						id: 'idQueryPostStatus',
						hiddenName: 'queryPostStatus',
						editable: true,
						width: 180
					},'-','审核时间：',{
			       		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					endDateField: 'endDate',
//						value:timeYesterday,
						editable: false,
						id: 'startDate',
						name: 'startDate',
						width: 120
		           	},'—',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					startDateField: 'startDate',
//						value:timeYesterday,
						editable: false,
						id: 'endDate',
						name: 'endDate',
						width: 120
		           	}]  
	 }); 
	
	var brhErrGrid = new Ext.grid.EditorGridPanel({
		iconCls: '',
		region:'center',
//		autoExpandColumn:'txnName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: brhErrGridStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhErrColModel,
		clicksToEdit: true,
		forceValidation: true,
        renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载合作伙伴差错信息列表......'
		},
		tbar: ['-',{
				xtype: 'button',
				text: '查询',
				name: 'query',
				id: 'query',
				iconCls: 'query',
				width: 80,
				handler:function() {
					brhErrGridStore.load();
				}
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('startDate').setValue('');
					Ext.getCmp('endDate').setValue('');
					Ext.getCmp('idQuerybrhNo').setValue('');
					Ext.getCmp('idQueryTradeType').setValue('');
					Ext.getCmp('idQueryAproveStatus').setValue('');
					Ext.getCmp('idQueryPostStatus').setValue('');
				}	
			},'-',{
				xtype: 'button',
				text: '导出Excel',
				name: 'download',
				id: 'download',
				iconCls: 'download',
				width: 80,
				handler:function() {
					showMask("正在为您准备报表，请稍后。。。",brhErrGrid);
					if (brhErrGridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
					Ext.Ajax.request({
						url: 'T80721Action_exportData.asp',
						timeout: 60000,
						params: {
							startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
							endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
							querybrhNo: Ext.get('querybrhNo').getValue(),
							queryTradeType: Ext.get('queryTradeType').getValue(),
							queryAproveStatus: Ext.get('queryAproveStatus').getValue(),
							queryPostStatus: Ext.get('queryPostStatus').getValue(),
							
							txnId: '80721',
							subTxnId: '01'
						},
						success: function(rsp,opt) {
							hideMask();
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
															rspObj.msg;
							} else {
								showErrorMsg(rspObj.msg,brhErrGrid);
							}
						},
						failure: function(){
							hideMask();
							showErrorMsg(rspObj.msg,brhErrGrid);
						}
					});
//					txnGridStore.load();
				    } else {
				    	hideMask();
				    	Ext.MessageBox.show({
							msg: '数据量超过限定值,请输入限制条件再进行此操作!!!',
							title: '报表下载说明',
							buttons: Ext.MessageBox.OK,
							modal: true,
							width: 220
						});
					}
				
				}	
			},'-',{
				xtype: 'button',
				text: '新增',
				name: 'add',
				id: 'add',
				iconCls: 'add',
				width: 80,
				handler:function() {
					showAddWin();
				}
		},'-',{
			xtype: 'button',
			text: '修改',
			name: 'edit',
			id: 'edit',
			iconCls: 'edit',
			disabled: true,
			width: 80,
			handler:function() {
	        	var approveStatus = brhErrGrid.getSelectionModel().getSelected().get('aproveStatus');
				if(approveStatus!='0'){
					Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，不能修改！&nbsp;&nbsp; </font>');
					return;
				}
				var uuid = brhErrGrid.getSelectionModel().getSelected().get('uuid');
				var brhNo = brhErrGrid.getSelectionModel().getSelected().get('brhNo');
				var tradeType = brhErrGrid.getSelectionModel().getSelected().get('tradeType');
				var money = brhErrGrid.getSelectionModel().getSelected().get('money');
				var reserver = brhErrGrid.getSelectionModel().getSelected().get('reserver');
				// 显示修改窗口
				showEditWin();
				// 设置值
				Ext.getCmp('uuid').setValue(uuid);
				Ext.getCmp('brhNo').setValue(brhNo);
				Ext.getCmp('tradeType').setValue(tradeType);
				Ext.getCmp('money').setValue(money);
				Ext.getCmp('reserver').setValue(reserver);
			}
	}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar3.render(this.tbar);
		    	  	tbar4.render(this.tbar);
                },
                'cellclick':selectableCell
        },
		bbar: new Ext.PagingToolbar({
			store: brhErrGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'			
		})
	});
	
	brhErrGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(brhErrGrid.getView().getRow(brhErrGrid.getSelectionModel().last)).frame();
			// 
			var approveStatus = brhErrGrid.getSelectionModel().getSelected().get('aproveStatus');
			if(approveStatus=='0'){
				Ext.getCmp('edit').setDisabled(false);
			} else {
				Ext.getCmp('edit').setDisabled(true);
			}
		}
	});
	
	brhErrGrid.addListener('cellclick',cellclick); //添加触发的函数     
    function cellclick(grid, rowIndex, columnIndex, e) {
    	//这里得到的是一个对象，即你点击的某一行的一整行数据
        //var record = grid.getStore().getAt(rowIndex);
        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);    
        //通过加判断条件限制当点击某个列表内容的时候触发，不然只要你点列表的随便一个地方就触发了。  
        if (fieldName == 'opration'){
        	var approveStatus = grid.getSelectionModel().getSelected().get('aproveStatus');
			if(approveStatus!='0'){
				Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
				return;
			}
			var uuid = grid.getSelectionModel().getSelected().get('uuid');
			var brhNo = grid.getSelectionModel().getSelected().get('brhNo');
			var tradeType = grid.getSelectionModel().getSelected().get('tradeType');
			var money = grid.getSelectionModel().getSelected().get('money');
			var reserver = grid.getSelectionModel().getSelected().get('reserver');
			// 显示审核窗口
			showApproveWin();
			// 设置值
			Ext.getCmp('uuid').setValue(uuid);
			Ext.getCmp('brhNo').setValue(brhNo);
			Ext.getCmp('tradeType').setValue(tradeType);
			Ext.getCmp('money').setValue(money);
			Ext.getCmp('reserver').setValue(reserver);			
        }
    }
    /************************************************以下是添加窗口************************************************************/
    var brhInfoForm = null;
    var brhWin = null;
    
    function createForm() {
    	 
        // 合作伙伴差错添加表单
       	brhInfoForm = new Ext.form.FormPanel({
       				frame : true,
       				width: 500,
       				height: 400,
       				labelWidth : 120,
       				layout : 'column',
       				defaults : {
       					bodyStyle : 'padding-left: 20px;overflow-y:auto;'
       				},
       				waitMsgTarget : true,
       				items : [{
							columnWidth : .9,
							hidden: true,
   							layout : 'form',
   							items : [{
   								xtype: 'textfield',
   								editable: true,
   								fieldLabel : 'id*',
   								id : 'uuid',
   								name : 'tblBrhErrAdjust.id',
   								width : 250
   							}]
   				},{
       							columnWidth : .9,
       							layout : 'form',
       							items : [{
       								xtype: 'dynamicCombo',
       								methodName: 'getBrhInfoBelowBranch',
       								editable: true,
       								fieldLabel : '合作伙伴号*',
       								allowBlank : false,
       								id : 'brhNo',
       								hiddenName : 'tblBrhErrAdjust.brhNo',
       								value: '',
       								width : 250
       							}]
       				},{
       					columnWidth : .9,
       					layout : 'form',
       					items : [{
       						fieldLabel : '交易类型*',
       						xtype : 'combo',
       						id : 'tradeType',
       						allowBlank : false,
       						emptyText : '请选择交易类型',
       						editable:false,
       						hiddenName: 'tblBrhErrAdjust.tradeType',
       						width: 250,
       						blankText : '请选择交易类型',
       						store : new Ext.data.ArrayStore({
       							fields : [ 'valueField', 'displayField' ],
       							data : [ ['00', '分润内扣(-)'],
    									['01', '营销费用扣除(-)'],
    									['02', '调退单损失(-)'],
    									['03', '追偿损失(-)'],
    									['04', '误划款正(+)'],
    									['05', '误划款负(-)'] ]
       						})
       					}]
       				},{
       					columnWidth : .9,
       					layout : 'form',
       					items : [{
       						xtype: 'textfield',
       						fieldLabel : '金额*',
       						allowBlank : false,
       						emptyText : '请输入金额(只允许为正，带两位小数)',
       						id : 'money',
       						name : 'tblBrhErrAdjust.money',
       						width : 250,
       						maxLength : 13,
    						regexText: '金额必须是正数，带两位小数,如123.45,且整数部分长度不大于10位',
    						//regex:/^(([1-9]{1}\d{0,9})|([0]{1}))(\.(\d){2})$/,
    						regex:/^(((([1-9]\d{0,9})|([0]{1}))(\.(\d[1-9]|[1-9]\d)))|(\d{1,10}\.\d{2}))$/,
       						blankText : '请输入金额(只允许为正，带两位小数)',
       					}]
       				},{
       					columnWidth: .99,
       					layout: 'form',
       					items: [{
       						xtype: 'textarea',
       						fieldLabel: '备注*',
       						allowBlank : false,
       						emptyText : '请简要描述备注相关信息，50个汉字以内',
       						id: 'reserver',
       						name: 'tblBrhErrAdjust.reserver',
       						maxLength: 100,
       						vtype: 'isOverMax',
       						anchor: '90%',
       						blankText : '请输入备注信息'
       					}]
       				},{
       					columnWidth: .99,
       					layout: 'form',
       					items: [{
       						xtype: 'textarea',
       						fieldLabel: '审批意见*',
       						allowBlank : false,
       						id: 'approveAdvice',
       						name: 'tblBrhErrAdjust.approveAdvice',
       						maxLength: 100,
       						vtype: 'isOverMax',
       						anchor: '90%',
       						blankText : '请输入审批意见'
       					}]
       				}]
       			});
    }
    
    function createWin(type) {
    	var submitUrl = 'T80721Action_';
    	submitUrl += type + '.asp';
       	// 合作伙伴添加窗口
       	brhWin = new Ext.Window({
       				title : '添加合作伙伴手工差错',
       				initHidden : true,
       				header : true,
       				frame : true,
       				closable : true,
       				modal : true,
       				width : 515,
       				// autoHeight: true,
       				// layout: 'fit',
//       				layout : 'column',
       				items : [brhInfoForm],
       				buttonAlign : 'center',
       				closeAction : 'close',
       				iconCls : 'logo',
       				resizable : false,
       				defaults : {
       					bodyStyle : 'overflow-y:auto;'
       				},
       				buttons : [{
       					text : '保存',
       					id: 'btnSave',
       					handler : function() {
       						if (brhInfoForm.getForm().isValid()) {
       							
       							brhInfoForm.getForm().submit({
       								url : submitUrl,
       								waitMsg : '正在提交，请稍后......',
       								success : function(form, action) {
       									
       									// 重置表单
       									brhInfoForm.getForm().reset();
       									brhWin.close();
       									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
       									// 重新加载合作伙伴列表
       									brhErrGrid.getStore().reload();
       									
       								},
       								failure : function(form, action) {
       									if (action.result.msg != null || action.result.msg != '') {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red>'+action.result.msg+' </font>');
       									} else {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，新增失败！ </font>');
       									}       									
       								},
       								params : {
       									txnId : '80721',
       									subTxnId : '02',
       								}
       							});
       					}else {
       						var foundInvalid = false;
       						brhInfoForm.getForm().items.eachKey(function(key,item){  
       							//console.log(item.fieldLabel)  
       							if (!foundInvalid && !item.isValid()) {
       								foundInvalid = true;
       								var strFeildName = item.fieldLabel;
       								strFeildName = strFeildName.replace(/[\*|%]/g, '')
       								showErrorMsg('【' + strFeildName + "】未填写或数据不合法，请检查无误后再提交！", brhInfoForm);
       								item.focus();
       							}
       						});
       					}
       					}
       				},  {
       					text : '取消',
       					id: 'btnCancel',
       					handler : function() {
       						brhInfoForm.getForm().reset();
       						brhWin.close();
       					}
       				},{
    					text : '通过',
    					id: 'btnAccept',
    					handler : function() {
    						if (brhInfoForm.getForm().isValid()) {
    							var uuid = brhErrGrid.getSelectionModel().getSelected().get('uuid');
    							var approveStatus = brhErrGrid.getSelectionModel().getSelected().get('aproveStatus');
    							if(approveStatus!='0'){
    								// 重置表单
    								brhInfoForm.getForm().reset();
    								brhWin.close();
    								Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
    								return;
    							}
    							brhInfoForm.getForm().submit({
    								url : 'T80721Action_accept.asp',
    								waitMsg : '正在提交，请稍后......',
    								success : function(form, action) {
    									
    									// 重置表单
    									brhInfoForm.getForm().reset();
    									brhWin.close();
    									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
    									// 重新加载合作伙伴列表
    									brhErrGrid.getStore().reload();
    									
    								},
    								failure : function(form, action) {
       									if (action.result.msg != null || action.result.msg != '') {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red>'+action.result.msg+' </font>');
       									} else {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，审核通过失败！ </font>');
       									}      									
    								},
    								params : {
//    									'tblBrhErrAdjust.id':uuid,
    									txnId : '80721',
    									subTxnId : '03',
    								}
    							});
    					}else {
    						var foundInvalid = false;
    						brhInfoForm.getForm().items.eachKey(function(key,item){  
    							//console.log(item.fieldLabel)  
    							if (!foundInvalid && !item.isValid()) {
    								foundInvalid = true;
    								var strFeildName = item.fieldLabel;
    								strFeildName = strFeildName.replace(/[\*|%]/g, '')
    								showErrorMsg('【' + strFeildName + "】未填写或数据不合法，请检查无误后再提交！", brhInfoForm);
    								item.focus();
    							}
    						});
    					}
    					}
    				},{
    					text : '不通过',
    					id: 'btnRefuse',
    					handler : function() {
    						if (brhInfoForm.getForm().isValid()) {
    							var uuid = brhErrGrid.getSelectionModel().getSelected().get('uuid');
    							var approveStatus = brhErrGrid.getSelectionModel().getSelected().get('aproveStatus');
    							if(approveStatus!='0'){
    								// 重置表单
    								brhInfoForm.getForm().reset();
    								brhWin.close();
    								Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
    								return;
    							}
    							brhInfoForm.getForm().submit({
    								url : 'T80721Action_refuse.asp',
    								waitMsg : '正在提交，请稍后......',
    								success : function(form, action) {
    									
    									// 重置表单
    									brhInfoForm.getForm().reset();
    									brhWin.close();
    									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
    									// 重新加载合作伙伴列表
    									brhErrGrid.getStore().reload();
    									
    								},
    								failure : function(form, action) {
       									if (action.result.msg != null || action.result.msg != '') {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red>'+action.result.msg+' </font>');
       									} else {
       										Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，审核拒绝失败！ </font>');
       									}  
    									
    								},
    								params : {
//    									'tblBrhErrAdjust.id':uuid,
    									txnId : '80721',
    									subTxnId : '04',
    								}
    							});
    					}else {
    						var foundInvalid = false;
    						brhInfoForm.getForm().items.eachKey(function(key,item){  
    							//console.log(item.fieldLabel)  
    							if (!foundInvalid && !item.isValid()) {
    								foundInvalid = true;
    								var strFeildName = item.fieldLabel;
    								strFeildName = strFeildName.replace(/[\*|%]/g, '')
    								showErrorMsg('【' + strFeildName + "】未填写或数据不合法，请检查无误后再提交！", brhInfoForm);
    								item.focus();
    							}
    						});
    					}
    					}
    				},  {
    					text : '返回',
    					id: 'btnReturn',
    					handler : function() {
    						brhInfoForm.getForm().reset();
    						brhWin.close();
    					}
    				}]
       			});
    }
    // 显示新增窗口
    function showAddWin() {
    	// 创建form和window
    	createForm();
    	createWin('add');
	    
    	// 显示‘保存’‘取消’按钮
//    	Ext.getCmp('btnSave').show();
//    	Ext.getCmp('btnCancel').show();
    	// 隐藏‘通过’‘不通过’‘返回’按钮
    	Ext.getCmp('btnAccept').hide();
    	Ext.getCmp('btnRefuse').hide();
    	Ext.getCmp('btnReturn').hide();
    	Ext.getCmp('approveAdvice').hide();
    	Ext.getCmp('approveAdvice').hideLabel = true;
    	Ext.getCmp('approveAdvice').allowBlank = true;
    	brhWin.setTitle('新增合作伙伴差错信息');
    	
	    brhWin.show();
    }
    
    // 显示新增窗口
    function showEditWin() {
    	// 创建form和window
    	createForm();
    	createWin('edit');
	    
    	// 显示‘保存’‘取消’按钮
//    	Ext.getCmp('btnSave').show();
//    	Ext.getCmp('btnCancel').show();
    	// 隐藏‘通过’‘不通过’‘返回’按钮
    	Ext.getCmp('btnAccept').hide();
    	Ext.getCmp('btnRefuse').hide();
    	Ext.getCmp('btnReturn').hide();
    	Ext.getCmp('approveAdvice').hide();
    	Ext.getCmp('approveAdvice').hideLabel = true;
    	Ext.getCmp('approveAdvice').allowBlank = true;
    	
    	Ext.getCmp('brhNo').setDisabled(true);
    	
    	brhWin.setTitle('修改合作伙伴差错信息');
    	
	    brhWin.show();
    }
    
    function showApproveWin() {    	
    	// 创建form和window
    	createForm();
    	createWin('');
    	// 隐藏‘保存’‘取消’按钮
    	Ext.getCmp('btnSave').hide();
    	Ext.getCmp('btnCancel').hide();
    	// 除‘审批意见’之外，其他信息不可修改
    	Ext.getCmp('brhNo').setDisabled(true);
    	Ext.getCmp('tradeType').readOnly  = true;
    	Ext.getCmp('money').readOnly  = true;
    	Ext.getCmp('reserver').readOnly  = true;
    	
    	brhWin.setTitle('合作伙伴差错信息审核');

	    brhWin.show();
    }

    /************************************************添加/审批窗口结束************************************************************/
    
    /************************************************以下是显示主界面************************************************************/
	brhErrGridStore.on('beforeload', function(){
		// 
		Ext.getCmp('edit').setDisabled(true);
		Ext.apply(this.baseParams, {
			start: 0,
			startDate: typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate: typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd'),
			querybrhNo: Ext.get('querybrhNo').getValue(),
			queryTradeType: Ext.get('queryTradeType').getValue(),
			queryAproveStatus: Ext.get('queryAproveStatus').getValue(),
			queryPostStatus: Ext.get('queryPostStatus').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [brhErrGrid],
		renderTo: Ext.getBody()
	});
});