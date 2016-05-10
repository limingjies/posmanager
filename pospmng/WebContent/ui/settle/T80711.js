Ext.apply(Ext.form.VTypes,{
	isNumber: function(value,f) {
		var reg = new RegExp("^\\d+$");
		return reg.test(value);
	},
	isNumberText: '该输入项只能包含数字'
});
	
Ext.onReady(function() {
	var timeShowFormat=function(val){
		if(val==''|val==null){
			return '';
		}
		return val.substr(0,4)+'-'+val.substr(4,2)+'-'+val.substr(6,2)+' '+val.substr(8,2)+':'+val.substr(10,2)+':'+val.substr(12,2);
	}
	var tradeType=function(val){

		if(val=='00'){
			return '误划款正向(+)';
		}else if(val=='01'){
			return '误划款反向(-)';
		}else if(val=='02'){
			return '退货(-)';
		}else if(val=='03'){
			return '商户冻结';
		}else if(val=='04'){
			return '商户资金冻结';
		}else if(val=='05'){
			return '商户解冻';
		}else if(val=='06'){
			return '商户资金解冻';
		}else return '';
		
	}
	var approveStatus=function(val){
		if(val=='0'){
			return '未审批';
		}else if(val=='1'){
			return '审批通过';
		}else if(val=='2'){
			return '审批未通过';
		}else return '';
	}
	var postStatus=function(val){
		if(val=='0'){
			return '未入账';
		}else if(val=='1'){
			return '已入账';
		}else return '';
	}
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000); 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	
	
	var gridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtErrAdjustAll'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'brhId'
		},[
			{name: 'uuid',mapping: 'uuid'},
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'cardNo',mapping :'cardNo'},
			{name: 'authNo',mapping: 'authNo'},
			{name: 'tradeConsultNo',mapping :'tradeConsultNo'},
			{name: 'tradeType',mapping: 'tradeType'},
			{name: 'money',mapping: 'money'},
			{name: 'approveStatus',mapping: 'approveStatus'},
			{name: 'approveOpr',mapping: 'approveOpr'},
			{name: 'approveTime',mapping: 'approveTime'},
			{name: 'approveAdvice',mapping: 'approveAdvice'},
			{name: 'postTime',mapping: 'postTime'},
			{name: 'postStatus',mapping: 'postStatus'},
			{name: 'reserver',mapping: 'reserver'},
			{name: 'vcTranNo',mapping: 'vcTranNo'},
		]),
		autoLoad: true
	});
	
	
	var brhColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		{id: 'uuid',header: '主键',dataIndex: 'uuid',hidden: true,width: 150},
		{id: 'brhId',header: '商户号',dataIndex: 'mchtNo',align: 'center',sortable: true,width: 120},
		{header : '<center>卡号</center>',dataIndex : 'cardNo',align: 'left',width : 150},
		{header: '<center>授权号</center>',dataIndex: 'authNo',align: 'left',width: 80},
		{header: '<center>交易参考号</center>',dataIndex: 'tradeConsultNo',align: 'left',width: 100},
		{header: '<center>交易类型</center>',dataIndex: 'tradeType',width: 100,align: 'center',renderer: tradeType},
		{header: '<center>金额</center>',dataIndex: 'money',align: 'right',width: 100},
		{header: '<center>审批状态</center>',dataIndex: 'approveStatus',align: 'center',renderer: approveStatus,width: 80},
		{header: '<center>审批人</center>',dataIndex: 'approveOpr',align: 'center',width: 100},
		{header: '<center>审批时间</center>',dataIndex: 'approveTime',align: 'center',width: 130,renderer:timeShowFormat},
		{header: '<center>审批意见</center>',dataIndex: 'approveAdvice',align: 'left',width: 150},
		{header: '<center>入账状态</center>',dataIndex: 'postStatus',align: 'center',width: 60,renderer:postStatus},
		{header: '<center>入账时间</center>',dataIndex: 'postTime',align: 'center',width: 130,renderer:timeShowFormat},
		{header: '<center>交易流水号</center>',dataIndex: 'vcTranNo',align: 'center',width: 100},
		{header: '<center>备注</center>',dataIndex: 'reserver',align: 'left',width: 160},
		{header: '<center>操作</center>',dataIndex:'detail',align: 'center',width:100,id:'brhAddr',
			renderer: function(){return "<button type='button' onclick=''>审核</button>";}
		}
	]);


	

	
	

	 var tbar1 = new Ext.Toolbar({
           renderTo: Ext.grid.EditorGridPanel.tbar,  
           items: ['商户号：'
					,{
						xtype: 'dynamicCombo',
						methodName: 'getMchntNoAll',
						fieldLabel: '商户号',
						id: 'idqueryMchtNo',
						hiddenName: 'queryMchtNo',
						editable: true,
						width: 300
					},'-','卡号:',{
						xtype: 'textfield',
						fieldLabel: '卡号',
						id: 'cardNo',
						editable: true,
						width: 200
					},'-','授权号:',{
						xtype: 'textfield',
						fieldLabel: '授权号',
						id: 'authNo',
						editable: true,
						width: 200
					},'-','交易参考号:',{
						xtype: 'textfield',
						fieldLabel: '交易参考号',
						id: 'tradeConsultNo',
						editable: true,
						width: 200
					}]
    });
	 var tbar2 = new Ext.Toolbar({
         renderTo: Ext.grid.EditorGridPanel.tbar,  
         items: ['-',	'交易类型：',{
				xtype : 'combo',
				id : 'tradeTypeId',
				hiddenName: 'tradeType',
				width: 150,
				store : new Ext.data.ArrayStore({//

					fields : [ 'valueField', 'displayField' ],
					data : [ [ '00', '误划款正向(+)' ], [ '01', '误划款反向(-)' ],[ '02', '退货(-)' ], [ '03', '商户冻结' ],[ '04', '商户资金冻结' ], [ '05', '商户解冻' ], [ '06', '商户资金解冻' ] ]
				})
			},'-',	'审批状态：',{
				xtype : 'combo',
				id : 'approveStatusId',
				hiddenName: 'approveStatus',
				width: 150,
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '0', '未审批' ], [ '1', '审批通过' ], [ '2', '审批未通过' ] ]
				})
			},'-','入账状态：',{
				xtype : 'combo',
				id : 'postStatusId',
				hiddenName: 'postStatus',
				width: 150,
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '0', '未入账' ], [ '1', '已入账' ] ]
				})
			},
		   		'-','审批时间：',{
			       		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
//						vtype: 'daterange',
//						endDateField: 'endDate',
						value:'',
						editable: false,
						id: 'startDate',
						name: 'startDate',
						width: 120
			           	},'—',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
//						vtype: 'daterange',
//						startDateField: 'startDate',
						value:'',
						editable: false,
						id: 'endDate',
						name: 'endDate',
						width: 120
			           	}]
  });
	//合作伙伴列表
	var grid = new Ext.grid.EditorGridPanel({
		title: '商户手工差错维护',
		iconCls: 'T10100',
		region: 'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: gridStore,
		//autoExpandColumn:'brhAddr',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: brhColModel,
		clicksToEdit: true,
		forceValidation: true,
		renderTo: Ext.getBody(),
//		tbar: menuArr,
		loadMask: {
			msg: '正在加载合作伙伴信息列表......'
		},
		tbar: ['-',{
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				gridStore.load();
			    }
			},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('idqueryMchtNo').setValue('');
				Ext.getCmp('cardNo').setValue('');
				Ext.getCmp('authNo').setValue('');
				Ext.getCmp('tradeConsultNo').setValue('');
				Ext.getCmp('tradeTypeId').setValue('');
				Ext.getCmp('approveStatusId').setValue('');
				Ext.getCmp('postStatusId').setValue('');
				Ext.getCmp('startDate').setValue('');
				Ext.getCmp('endDate').setValue('');
			}	
		},'-',{

			xtype: 'button',
			text: '导出excel',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				Ext.Ajax.request({
					url: 'T80711Action_exportData.asp',
					timeout: 60000,
					params: {
						queryMchtNo:Ext.getCmp('idqueryMchtNo').getValue(),
						cardNo:Ext.getCmp('cardNo').getValue(),
						authNo:Ext.getCmp('authNo').getValue(),
						tradeConsultNo:Ext.getCmp('tradeConsultNo').getValue(),
						tradeType:Ext.getCmp('tradeTypeId').getValue(),
						approveStatus:Ext.getCmp('approveStatusId').getValue(),
						postStatus:Ext.getCmp('postStatusId').getValue(),
						startDate:typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
						endDate:typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd')
					},
					success: function(rsp,opt) {
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
							//gridStore.load();
						} else {
							showErrorMsg(rspObj.msg,grid);
						}
					},
					failure: function(){
						showErrorMsg(rspObj.msg,gridPanel);
					}
				});
			}
		},'-',{
			xtype: 'button',
			text: '新增',
			name: 'add',
			id: 'add',
			iconCls: 'add',
			width: 80,
			handler:function() {
				brhWin.show();
			}
			},'-',{
				xtype: 'button',
				text: '修改',
				name: 'update',
				disabled:true,
				id: 'update',
				iconCls: 'edit',
				width: 80,
				handler:function() {
					var uuid = grid.getSelectionModel().getSelected().get('uuid');
					var mchtNo = grid.getSelectionModel().getSelected().get('mchtNo');
					var tradeType = grid.getSelectionModel().getSelected().get('tradeType');
					var cardNo = grid.getSelectionModel().getSelected().get('cardNo');
					var authNo = grid.getSelectionModel().getSelected().get('authNo');
					var tradeConsultNo = grid.getSelectionModel().getSelected().get('tradeConsultNo');
					var money = grid.getSelectionModel().getSelected().get('money');
					var reserver = grid.getSelectionModel().getSelected().get('reserver');
					Ext.getCmp('mchtNoUpd').setValue(mchtNo);
					Ext.getCmp('tradeTypeUpd').setValue(tradeType);
					Ext.getCmp('cardNoUpd').setValue(cardNo);
					Ext.getCmp('authNoUpd').setValue(authNo);
					Ext.getCmp('tradeConsultNoUpd').setValue(tradeConsultNo);
					Ext.getCmp('moneyUpd').setValue(money);
					Ext.getCmp('reserverUpd').setValue(reserver);
					if(tradeType==='03'|tradeType==='05'){
						Ext.getCmp('moneyUpd').setValue('');
						Ext.getCmp('moneyUpd').disable();
						Ext.getCmp('moneyUpd').allowBlank=true;
					}else {
						Ext.getCmp('moneyUpd').enable();
						Ext.getCmp('moneyUpd').allowBlank=false;
					}
					updWin.show();
				}
				}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {
				tbar1.render(this.tbar);
				tbar2.render(this.tbar);
            },
            'cellclick':selectableCell
        },
		bbar: new Ext.PagingToolbar({
			store: gridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	

	
    grid.addListener('cellclick',cellclick); //添加触发的函数     
    function cellclick(grid, rowIndex, columnIndex, e) {
    	//这里得到的是一个对象，即你点击的某一行的一整行数据
        //var record = grid.getStore().getAt(rowIndex);
        var fieldName = grid.getColumnModel().getDataIndex(columnIndex);    
        //通过加判断条件限制当点击某个列表内容的时候触发，不然只要你点列表的随便一个地方就触发了。  
        if (fieldName == 'detail'){
        	var approveStatus = grid.getSelectionModel().getSelected().get('approveStatus');
			if(approveStatus!='0'){
				Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
				return;
			}
			var uuid = grid.getSelectionModel().getSelected().get('uuid');
			var mchtNo = grid.getSelectionModel().getSelected().get('mchtNo');
			var tradeType = grid.getSelectionModel().getSelected().get('tradeType');
			var cardNo = grid.getSelectionModel().getSelected().get('cardNo');
			var authNo = grid.getSelectionModel().getSelected().get('authNo');
			var tradeConsultNo = grid.getSelectionModel().getSelected().get('tradeConsultNo');
			var money = grid.getSelectionModel().getSelected().get('money');
			Ext.getCmp('mchtNoCheck').setValue(mchtNo);
			Ext.getCmp('tradeTypeCheck').setValue(tradeType);
			Ext.getCmp('cardNoCheck').setValue(cardNo);
			Ext.getCmp('authNoCheck').setValue(authNo);
			Ext.getCmp('tradeConsultNoCheck').setValue(tradeConsultNo);
			Ext.getCmp('moneyCheck').setValue(money);
			checkWin.show();
        }
    }   
	
	/************************************************以下是添加窗口************************************************************/
 // 合作伙伴添加表单
	var brhInfoForm = new Ext.form.FormPanel({
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
							layout : 'form',
							items : [{
								xtype: 'dynamicCombo',
								methodName: 'getMchntNoAll',
								editable: true,
								fieldLabel : '商户号*',
								allowBlank : false,
								emptyText : '请输入商户号',
								id : 'mchtNoAdd',
								hiddenName : 'tblMchtErrAdjust.mchtNo',
								width : 250,
//								maxLengthText : '机构代码最多可以输入3个数字001~999',
//								vtype : 'isNumber',
//								regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
								blankText : '请输入商户号',
							}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						fieldLabel : '交易类型*',
						xtype : 'combo',
						id : 'tradeTypeAdd',
						allowBlank : false,
						emptyText : '请选择交易类型',
						editable:false,
						hiddenName: 'tblMchtErrAdjust.tradeType',
						width: 250,
						blankText : '请选择交易类型',
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [ [ '00', '误划款正向(+)' ], [ '01', '误划款反向(-)' ],[ '02', '退货(-)' ], [ '03', '商户冻结' ],[ '04', '商户资金冻结' ], [ '05', '商户解冻' ], [ '06', '商户资金解冻' ] ]
						}),
						listeners:{
							'select':function(combo,record,index){
								if(combo.value==='03'|combo.value==='05'){
									Ext.getCmp('moneyAdd').disable();
									Ext.getCmp('moneyAdd').allowBlank=true;
								}else {
									Ext.getCmp('moneyAdd').enable();
									Ext.getCmp('moneyAdd').allowBlank=false;
								}
							},
							'change':function(combo,val,value){
								if(val==='03'|val==='05'){
									Ext.getCmp('moneyAdd').disable();
									Ext.getCmp('moneyAdd').allowBlank=true;
								}else {
									Ext.getCmp('moneyAdd').enable();
									Ext.getCmp('moneyAdd').allowBlank=false;
									}
							}}
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '卡号',
						allowBlank : true,
//						emptyText : '请输入卡号',
						id : 'cardNoAdd',
						name : 'tblMchtErrAdjust.cardNo',
						width : 250,
						maxLength : 40,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
						vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
						blankText : '请输入卡号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '授权号',
						allowBlank : true,
//						emptyText : '请输入授权号',
						id : 'authNoAdd',
						name : 'tblMchtErrAdjust.authNo',
						width : 250,
						maxLength : 6,
		//				maxLengthText : '机构代码最多可以输入3个数字001~999',
						vtype : 'isNumber',
		//				regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
						blankText : '请输入授权号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '交易参考号',
						allowBlank : true,
//						emptyText : '请输入交易参考号',
						id : 'tradeConsultNoAdd',
						name : 'tblMchtErrAdjust.tradeConsultNo',
						width : 250,
						maxLength : 12,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
						vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
						blankText : '请输入交易参考号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'textfield',
						fieldLabel : '金额*',
						allowBlank : false,
						emptyText : '请输入金额(只允许为正，带小数)',
						id : 'moneyAdd',
						name : 'tblMchtErrAdjust.money',
						width : 250,
						maxLength : 15,
						regexText: '金额必须是正数，带小数,如123.45,且整数部分长度不大于10位',
						//regex:/^(([1-9]{1}\d{0,9})|([0]{1}))(\.(\d[1-9]|[1-9]\d))$/,
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
						name: 'tblMchtErrAdjust.reserver',
						maxLength: 100,
						vtype: 'isOverMax',
						anchor: '90%',
						blankText : '请输入备注信息'
					}]
				}]
			});

	// 合作伙伴添加窗口
	var brhWin = new Ext.Window({
				title : '添加商户手工差错',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 515,
				// autoHeight: true,
				// layout: 'fit',
//				layout : 'column',
				items : [brhInfoForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				defaults : {
					bodyStyle : 'overflow-y:auto;'
				},
				buttons : [{
					text : '保存',
					handler : function() {
						if (brhInfoForm.getForm().isValid()) {
							
							brhInfoForm.getForm().submit({
								url : 'T80711Action_add.asp',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									
									// 重置表单
									brhInfoForm.getForm().reset();
									brhWin.hide();
									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
									// 重新加载合作伙伴列表
									grid.getStore().reload();
									
								},
								failure : function(form, action) {
									Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，新增失败！ </font>');
									
								},
								params : {
									txnId : '10105',
									subTxnId : '01',
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
					handler : function() {
						brhInfoForm.getForm().reset();
						brhWin.hide(grid);
					}
				}]
			});
	// 合作伙伴添加表单
	var updForm = new Ext.form.FormPanel({
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
			layout : 'form',
			items : [{
				xtype: 'combofordispaly',
				methodName: 'getMchntNoAll',
//				editable: true,
				fieldLabel : '商户号*',
//				allowBlank : false,
//				emptyText : '请输入商户号',
				id : 'mchtNoUpd',
				hiddenName : 'tblMchtErrAdjust.mchtNo',
				width : 250,
//								maxLengthText : '机构代码最多可以输入3个数字001~999',
//								vtype : 'isNumber',
//								regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
//				blankText : '请输入商户号',
			}]
		},{
			columnWidth : .9,
			layout : 'form',
			items : [{
				fieldLabel : '交易类型*',
				xtype : 'combo',
				id : 'tradeTypeUpd',
				allowBlank : false,
				emptyText : '请选择交易类型',
				editable:false,
				hiddenName: 'tblMchtErrAdjust.tradeType',
				width: 250,
				blankText : '请选择交易类型',
				store : new Ext.data.ArrayStore({
					fields : [ 'valueField', 'displayField' ],
					data : [ [ '00', '误划款正向(+)' ], [ '01', '误划款反向(-)' ],[ '02', '退货(-)' ], [ '03', '商户冻结' ],[ '04', '商户资金冻结' ], [ '05', '商户解冻' ], [ '06', '商户资金解冻' ] ]
				}),
				listeners:{
					'select':function(combo,record,index){
						if(combo.value==='03'|combo.value==='05'){
							Ext.getCmp('moneyUpd').setValue('');
							Ext.getCmp('moneyUpd').disable();
							Ext.getCmp('moneyUpd').allowBlank=true;
						}else {
							Ext.getCmp('moneyUpd').enable();
							Ext.getCmp('moneyUpd').allowBlank=false;
						}
					},
					'change':function(combo,val,value){
						if(val==='03'|val==='05'){
							Ext.getCmp('moneyUpd').setValue('');
							Ext.getCmp('moneyUpd').disable();
							Ext.getCmp('moneyUpd').allowBlank=true;
						}else {
							Ext.getCmp('moneyUpd').enable();
							Ext.getCmp('moneyUpd').allowBlank=false;
							}
					}}
			}]
		},{
			columnWidth : .9,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '卡号',
				allowBlank : true,
//						emptyText : '请输入卡号',
				id : 'cardNoUpd',
				name : 'tblMchtErrAdjust.cardNo',
				width : 250,
				maxLength : 40,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
				vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
				blankText : '请输入卡号',
			}]
		},{
			columnWidth : .9,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '授权号',
				allowBlank : true,
//						emptyText : '请输入授权号',
				id : 'authNoUpd',
				name : 'tblMchtErrAdjust.authNo',
				width : 250,
				maxLength : 6,
				//				maxLengthText : '机构代码最多可以输入3个数字001~999',
				vtype : 'isNumber',
				//				regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
				blankText : '请输入授权号',
			}]
		},{
			columnWidth : .9,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '交易参考号',
				allowBlank : true,
//						emptyText : '请输入交易参考号',
				id : 'tradeConsultNoUpd',
				name : 'tblMchtErrAdjust.tradeConsultNo',
				width : 250,
				maxLength : 12,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
				vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
				blankText : '请输入交易参考号',
			}]
		},{
			columnWidth : .9,
			layout : 'form',
			items : [{
				xtype: 'textfield',
				fieldLabel : '金额*',
				allowBlank : false,
				emptyText : '请输入金额(只允许为正，带小数)',
				id : 'moneyUpd',
				name : 'tblMchtErrAdjust.money',
				width : 250,
				maxLength : 15,
				regexText: '金额必须是正数，带小数,如123.45,且整数部分长度不大于10位',
				//regex:/^(([1-9]{1}\d{0,9})|([0]{1}))(\.(\d[1-9]|[1-9]\d))$/,
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
				id: 'reserverUpd',
				name: 'tblMchtErrAdjust.reserver',
				maxLength: 100,
				vtype: 'isOverMax',
				anchor: '90%',
				blankText : '请输入备注信息'
			}]
		}]
	});
	
	// 修改商户手工差错
	var updWin = new Ext.Window({
		title : '修改商户手工差错',
		initHidden : true,
		header : true,
		frame : true,
		closable : false,
		modal : true,
		width : 515,
		// autoHeight: true,
		// layout: 'fit',
//				layout : 'column',
		items : [updForm],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		defaults : {
			bodyStyle : 'overflow-y:auto;'
		},
		buttons : [{
			text : '保存',
			handler : function() {
				if (updForm.getForm().isValid()) {
					var uuid = grid.getSelectionModel().getSelected().get('uuid');
					updForm.getForm().submit({
						url : 'T80711Action_upd.asp',
						waitMsg : '正在提交，请稍后......',
						success : function(form, action) {
							
							// 重置表单
							updForm.getForm().reset();
							updWin.hide();
							Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
							// 重新加载合作伙伴列表
							grid.getStore().reload();
							
						},
						failure : function(form, action) {
							Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，新增失败！ </font>');
							
						},
						params : {
							txnId : '10105',
							subTxnId : '01',
							'tblMchtErrAdjust.id':uuid
						}
					});
				}else {
					var foundInvalid = false;
					updForm.getForm().items.eachKey(function(key,item){  
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
			handler : function() {
			updForm.getForm().reset();
			updWin.hide(grid);
			}
		}]
	});
	// 审核表单
	var checkForm = new Ext.form.FormPanel({
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
							layout : 'form',
							items : [{
								xtype: 'combofordispaly',
								methodName: 'getMchntNoAll',
//								editable: true,
								fieldLabel : '商户号',
//								allowBlank : false,
//								emptyText : '请输入商户号',
								id : 'mchtNoCheck',
//								hiddenName : 'tblMchtErrAdjust.mchtNo',
								width : 250,
//								maxLengthText : '机构代码最多可以输入3个数字001~999',
//								vtype : 'isNumber',
//								regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
//								blankText : '请输入商户号',
							}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						fieldLabel : '交易类型',
						xtype : 'combofordispaly',
						id : 'tradeTypeCheck',
//						allowBlank : false,
//						emptyText : '请选择交易类型',
//						editable:false,
//						hiddenName: 'tblMchtErrAdjust.tradeType',
						width: 250,
//						blankText : '请选择交易类型',
						store : new Ext.data.ArrayStore({
							fields : [ 'valueField', 'displayField' ],
							data : [ [ '00', '误划款正向(+)' ], [ '01', '误划款反向(-)' ],[ '02', '退货(-)' ], [ '03', '商户冻结' ],[ '04', '商户资金冻结' ], [ '05', '商户解冻' ], [ '06', '商户资金解冻' ] ]
						})
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'displayfield',
						fieldLabel : '卡号',
//						allowBlank : true,
//						emptyText : '请输入卡号',
						id : 'cardNoCheck',
						width : 250,
//						maxLength : 40,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
//						vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
//						blankText : '请输入卡号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'displayfield',
						fieldLabel : '授权号',
//						allowBlank : true,
//						emptyText : '请输入授权号',
						id : 'authNoCheck',
						width : 250,
//						maxLength : 6,
		//				maxLengthText : '机构代码最多可以输入3个数字001~999',
//						vtype : 'isNumber',
		//				regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
//						blankText : '请输入授权号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'displayfield',
						fieldLabel : '交易参考号',
//						allowBlank : true,
//						emptyText : '请输入交易参考号',
						id : 'tradeConsultNoCheck',
						width : 250,
//						maxLength : 12,
//						maxLengthText : '机构代码最多可以输入3个数字001~999',
//						vtype : 'isNumber',
//						regex:/^[0-9]{0,2}[1-9][0-9]{0,2}$/,
//						blankText : '请输入交易参考号',
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items : [{
						xtype: 'displayfield',
						fieldLabel : '金额',
//						allowBlank : false,
//						emptyText : '请输入金额(只允许为正，带两位小数)',
						id : 'moneyCheck',
						width : 250,
//						maxLength : 16,
//						regexText: '注册资金必须是正数，带两位小数,如123.45',
//						regex:/^(([1-9]{1}\d{0,12})|([0]{1}))(\.(\d){2})$/,
//						blankText : '请输入金额(只允许为正，带两位小数)',
					}]
				},{
					columnWidth: .99,
					layout: 'form',
					items: [{
						xtype: 'textarea',
						fieldLabel: '审批意见',
						allowBlank : true,
//						emptyText : '请简要描述备注相关信息，50个汉字以内',
						id: 'approvedvice',
						name: 'tblMchtErrAdjust.approveAdvice',
						maxLength: 100,
						vtype: 'isOverMax',
						anchor: '90%',
						blankText : '请输入备注信息'
					}]
				}]
			});

	// 审核窗口
	var checkWin = new Ext.Window({
				title : '商户手工差错审核',
				initHidden : true,
				header : true,
				frame : true,
				closable : false,
				modal : true,
				width : 515,
				// autoHeight: true,
				// layout: 'fit',
//				layout : 'column',
				items : [checkForm],
				buttonAlign : 'center',
				closeAction : 'hide',
				iconCls : 'logo',
				resizable : false,
				defaults : {
					bodyStyle : 'overflow-y:auto;'
				},
				buttons : [{
					text : '通过',
					handler : function() {
						if (checkForm.getForm().isValid()) {
							var uuid = grid.getSelectionModel().getSelected().get('uuid');
							var approveStatus = grid.getSelectionModel().getSelected().get('approveStatus');
							if(approveStatus!='0'){
								// 重置表单
								checkForm.getForm().reset();
								checkWin.hide();
								Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
								return;
							}
							checkForm.getForm().submit({
								url : 'T80711Action_check.asp',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									
									// 重置表单
									checkForm.getForm().reset();
									checkWin.hide();
									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
									// 重新加载合作伙伴列表
									grid.getStore().reload();
									
								},
								failure : function(form, action) {
									Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，审核失败！ </font>');
									
								},
								params : {
									'tblMchtErrAdjust.id':uuid,
									txnId : '10105',
									subTxnId : '01',
								}
							});
					}else {
						var foundInvalid = false;
						checkForm.getForm().items.eachKey(function(key,item){  
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
					handler : function() {
						if (checkForm.getForm().isValid()) {
							var uuid = grid.getSelectionModel().getSelected().get('uuid');
							var approveStatus = grid.getSelectionModel().getSelected().get('approveStatus');
							if(approveStatus!='0'){
								// 重置表单
								checkForm.getForm().reset();
								checkWin.hide();
								Ext.Msg.alert('<font size=4>提示</font>','<font color=red,size=6> &nbsp;&nbsp;该记录已经被审核过，请选择其他记录！&nbsp;&nbsp; </font>');
								return;
							}
							checkForm.getForm().submit({
								url : 'T80711Action_refuse.asp',
								waitMsg : '正在提交，请稍后......',
								success : function(form, action) {
									
									// 重置表单
									checkForm.getForm().reset();
									checkWin.hide();
									Ext.Msg.alert('<font size=4>提示</font>','<font color=green,size=6> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+action.result.msg+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </font>');
									// 重新加载合作伙伴列表
									grid.getStore().reload();
									
								},
								failure : function(form, action) {
									Ext.Msg.alert('<font size=4>提示</font>','<font color=red> 服务器异常，审核失败！ </font>');
									
								},
								params : {
									'tblMchtErrAdjust.id':uuid,
									txnId : '10105',
									subTxnId : '01',
								}
							});
					}else {
						var foundInvalid = false;
						checkForm.getForm().items.eachKey(function(key,item){  
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
					handler : function() {
						checkForm.getForm().reset();
						checkWin.hide(grid);
					}
				}]
			});
	
	/********************************主界面*************************************************/
	
	
	
	gridStore.on('beforeload', function(){
		Ext.getCmp('update').disable();
		Ext.apply(this.baseParams, {
			start: 0,	
			queryMchtNo:Ext.getCmp('idqueryMchtNo').getValue(),
			cardNo:Ext.getCmp('cardNo').getValue(),
			authNo:Ext.getCmp('authNo').getValue(),
			tradeConsultNo:Ext.getCmp('tradeConsultNo').getValue(),
			tradeType:Ext.getCmp('tradeTypeId').getValue(),
			approveStatus:Ext.getCmp('approveStatusId').getValue(),
			postStatus:Ext.getCmp('postStatusId').getValue(),
			startDate:typeof(Ext.getCmp('startDate').getValue()) == 'string' ? '' : Ext.getCmp('startDate').getValue().format('Ymd'),
			endDate:typeof(Ext.getCmp('endDate').getValue()) == 'string' ? '' : Ext.getCmp('endDate').getValue().format('Ymd')
		});
	});
	grid.getSelectionModel().on({
		'rowselect': function() {
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			var approveStatus = grid.getSelectionModel().getSelected().get('approveStatus');
			if(approveStatus=='0'){
				Ext.getCmp('update').enable();
			}else Ext.getCmp('update').disable();
		}
	});
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});
});