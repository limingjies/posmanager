

//商户详细信息，在外部用函数封装
function showMchntHisDetailS(mchntId,El){
	var fieldLableArray = ['是否开通提现','商户类型','集团商户','商户名称','中文简称','签约机构','商户组别','风险级别','MCC','结算方式','T+N','营业执照号码','营业执照有效期',
	                       '税务登记证号码','商户类型','电子邮件','邮政编码','法人代表','注册资金','法人代表证件类型','法人代表证件号码','证件有效期','手续费方式',
	                       '经营名称','经营内容','经营面积','联系人姓名','联系人电话','注册地址','经营单位','营业开始时间','营业结束时间','详细地址','商户结算账户类型',
	                       '商户账户开户行号','商户账户开户行名称','装机地址-省','装机地址-市','商户结算帐户开户行号','商户账户户名','分润方式','分润比例档位','分润封顶档位',
							'对账单','对账单理由','积分','积分理由','紧急','是否合规','是否县乡','借记卡手续费比例','借记卡手续费封顶','贷记卡手续费比例','贷记卡手续费封顶',
							'提现方式','手续费方式','垫资日息（%）','垫资天数计算方式','单笔手续费金额（元）','分润垫资档位','分润垫资天数计算方式','未提现余额处理方式'];
	var diffArray = new Array();
	function diffInfRec(xtype,fieldLabel,id,value){
		var diffRec =  {columnWidth : .5,
		layout : 'form',
		width : 150,
		labelWidth : 180,
		hidden : false,
		items : [ {
			xtype : xtype,
			fieldLabel : fieldLabel,
			id : id,
			//hiddenName : 'tblMchtBaseInfTmpTmp.connType',
			allowBlank : true,
			width : 350,
			value : value
		} ]};
		return diffRec;
	}
	//历史记录
	// 终端数据部分
	var hisStore = new Ext.data.Store({
		proxy : new Ext.data.HttpProxy({
			url : 'gridPanelStoreAction.asp?storeId=mchntHisInfo'
		}),
		reader : new Ext.data.JsonReader({
			root : 'data',
			totalProperty : 'totalCount',
			idProperty : 'termNo'
		}, [ {
			name : 'mchtStatus',
			mapping : 'mchtStatus'
		}, {
			name : 'checkOpr',
			mapping : 'checkOpr'
		}, {
			name : 'checkTime',
			mapping : 'checkTime'
		},  {
			name : 'updOpr',
			mapping : 'updOpr'
		}, {
			name : 'updTime',
			mapping : 'updTime'
		} ,{
			name : 'mchtNoNew',
			mapping : 'mchtNoNew'
		} ])
	});
	var hisColModel = new Ext.grid.ColumnModel([
			new Ext.grid.RowNumberer(), {
				header : '商户状态',
				dataIndex : 'mchtStatus',
				sortable : true,
				width : 100,
				renderer:mchntSt
			}, {
				id : 'checkOpr',
				header : '审核人',
				dataIndex : 'checkOpr',
				renderer : termSta,
				width : 120
			}, {
				id : 'checkTime',
				header : '审核时间',
				dataIndex : 'checkTime',
				width : 150
			}, {
				id : 'updOpr',
				header:'修改人',
				width : 150,
				dataIndex : 'updOpr',
			},{
				id : 'updTime',
				header:'修改时间',
				width : 150,
				dataIndex : 'updTime',
				id : 'updTime',
			} ,{
				id : 'mchtNoNew',
				header:'修改序号',
				width : 150,
				hidden:true,
				dataIndex : 'mchtNoNew',
			} ]);
	var hisGrid = new Ext.grid.GridPanel({
		title : '变更历史信息',
		region : 'east',
		//width : 260,
		iconCls : 'T301',
		height:350,
		split : true,
		collapsible : true,
		frame : true,
		border : true,
		columnLines : true,
		autoExpandColumn : 'updTime',
		stripeRows : true,
		store : hisStore,
		sm : new Ext.grid.RowSelectionModel({
			singleSelect : true
		}),
		cm : hisColModel,
		clicksToEdit : true,
		forceValidation : true,
		loadMask : {
			msg : '正在加载终端信息列表......'
		},
		bbar : new Ext.PagingToolbar({
			store : hisStore,
			pageSize : System[QUERY_RECORD_COUNT],
			displayInfo : false
		}),
		listeners : {
			'cellclick' : selectableCell,
		}
	});

//	// 禁用编辑按钮
	hisGrid.getStore().on('beforeload', function() {
		Ext.apply(this.baseParams,
				{
					start : 0,
					mchtNo : mchntId,
				});
	});
	hisGrid.getStore().reload();
	hisGrid.getSelectionModel().on({
		'rowselect' : function() {
			var mchtNoNew = hisGrid.getSelectionModel().getSelected().data.mchtNoNew;
			  diffStore.load({
					params: {
						mchtNo: mchntId,
						mchtNoNew:mchtNoNew
					},
					callback: function(records, options, success){
						if(success){
							mchntForm.items.items[0].removeAll();
							for (var i = 1; i <= 50; i++) {
								if(records[0].get('col'+i)==''&&records[1].get('col'+i)==''){
									continue;
								}else {
									var org = records[0].get('col'+i)==''?'':records[0].get('col'+i);
									var update = records[1].get('col'+i)==''?'':records[1].get('col'+i);
									if(fieldLableArray[i-1] === '风险级别'&& org ==='0'){
										org = '';
									}
									if((fieldLableArray[i-1] ==='商户组别'||fieldLableArray[i-1] ==='风险级别'
										||fieldLableArray[i-1] ==='是否合规'
											||fieldLableArray[i-1] ==='是否县乡')&&(org === ''&& update!='')){
										continue;
									}
									if(fieldLableArray[i-1] ==='MCC'&&(org === '-'&& update!='')){
										continue;
									}
									var diffRec = diffInfRec('displayfield',fieldLableArray[i-1],'insert'+i,'<font color=green>'+org+'</font>————><font color=red>'+update+'</font>');
									mchntForm.items.items[0].add(diffRec);
								}
							}
							mchntForm.items.items[0].doLayout();
						
						}else{
							showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
						}
					}
				});
		}
	});
	
	//差分信息
	var diffStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtInfDiffByHis'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'col1',mapping: 'col1'},
			{name: 'col2',mapping: 'col2'},
			{name: 'col3',mapping: 'col3'},
			{name: 'col4',mapping: 'col4'},
			{name: 'col5',mapping: 'col5'},
			{name: 'col6',mapping: 'col6'},
			{name: 'col7',mapping: 'col7'},
			{name: 'col8',mapping: 'col8'},
			{name: 'col9',mapping: 'col9'},
			{name: 'col10',mapping: 'col10'},
			{name: 'col11',mapping: 'col11'},
			{name: 'col12',mapping: 'col12'},
			{name: 'col13',mapping: 'col13'},
			{name: 'col14',mapping: 'col14'},
			{name: 'col15',mapping: 'col15'},
			{name: 'col16',mapping: 'col16'},
			{name: 'col17',mapping: 'col17'},
			{name: 'col18',mapping: 'col18'},
			{name: 'col19',mapping: 'col19'},
			{name: 'col20',mapping: 'col20'},
			{name: 'col21',mapping: 'col21'},
			{name: 'col22',mapping: 'col22'},
			{name: 'col23',mapping: 'col23'},
			{name: 'col24',mapping: 'col24'},
			{name: 'col25',mapping: 'col25'},
			{name: 'col26',mapping: 'col26'},
			{name: 'col27',mapping: 'col27'},
			{name: 'col28',mapping: 'col28'},
			{name: 'col29',mapping: 'col29'},
			{name: 'col30',mapping: 'col30'},
			{name: 'col31',mapping: 'col31'},
			{name: 'col32',mapping: 'col32'},
			{name: 'col33',mapping: 'col33'},
			{name: 'col34',mapping: 'col34'},
			{name: 'col35',mapping: 'col35'},
			{name: 'col36',mapping: 'col36'},
			{name: 'col37',mapping: 'col37'},
			{name: 'col38',mapping: 'col38'},
			{name: 'col39',mapping: 'col39'},
			{name: 'col40',mapping: 'col40'},
			{name: 'col41',mapping: 'col41'},
			{name: 'col42',mapping: 'col42'},
			{name: 'col43',mapping: 'col43'},
			{name: 'col44',mapping: 'col44'},
			{name: 'col45',mapping: 'col45'},
			{name: 'col46',mapping: 'col46'},
			{name: 'col47',mapping: 'col47'},
			{name: 'col48',mapping: 'col48'},
			{name: 'col49',mapping: 'col49'},
			{name: 'col50',mapping: 'col50'},
		])
	});

	
	var mchntForm = new Ext.FormPanel({
		region: 'center',
		iconCls: 'mchnt',
		frame: true,
		labelWidth: 100,
		waitMsgTarget: true,
		autoScroll : true,
		height : 475,
		labelAlign: 'left',
		html: '<img id="showBigPic" src="" width="0" height="0" style="position:absolute;left:0;top:0;"/>',
        items: [{
		       	 title:'变更信息',
		         xtype: 'fieldset',
		         id: 'diffInf',
		         frame: true,
		         autoHeight: true,
				 layout:'column',
		         items: []
         },{
	    	xtype:'fieldset',
			title:'',
            id: 'his',
            frame: true,
            autoScroll: false,
            items: [hisGrid]
    },
        {
        	html:'<br>'
        },{ 
        	align:'center',
        	xtype: 'panel',
        	layout: 'column',
        	items:[{xtype: 'label',width: 480,html:'&nbsp;&nbsp;&nbsp;'},
        	      {
				xtype : 'button',
				text : '关闭',
				id : 'closeBut',
				width : 100,
				height : 30,
				handler : function() {
					detailWin.close();
				}
			}] //items表示指定布局内的表单组件集合，在此有三个 
        	}
       ]
    });
	var mchtDetailMenu = {
			text : '商户详细信息',
			width : 85,
			iconCls : 'detail',
			disabled : false,
			handler : function(bt) {
				MchntDetailsQuery(mchntId,null);
			}
		};
    var detailWin = new Ext.Window({
    	title: '商户历史信息[商户编号：' + mchntId + ']',
		initHidden: true,
		header: true,
		frame: true,
		modal: true,
		width: 1150,
		height:900,
		autoHeight: true,
		items: [mchntForm],
		buttonAlign: 'center',
		closable: true,
		tbar : [ mchtDetailMenu ],
		resizable: false
    });

 /*   diffStore.load({
		params: {
			start : 0,
			mchntId: mchntId
//			mchtNoNew:'2'
		},
		callback: function(records, options, success){
			if(success){}else{
				showErrorMsg("加载商户信息失败，请刷新数据后重试",mchntForm);
			}
		}
	});*/
	detailWin.show();
}