Ext.onReady(function() {
	
	var whiteMchtDtl;
	function createWhiteMchtDtl(r){
		whiteMchtDtl =  '<p style="text-align:center;"><font style="font-weight:bold;">商户：</font>'+r.get('mchtNoNm')+'</p>' +
				'<div>' +
				'<fieldset style="padding:0px 8px 0px 8px;">' +
				'<legend><font style="font-weight: bold;">审核信息</font></legend>' +
				'<ul><p><font color=blue>商户情况描述：</font>'+r.get('mchtCaseDesp')+'</p></ul>' +
				'<ul><p><font color=blue>拒绝原因：</font>'+r.get('refuseReason')+'</p></ul>' +
				'</fieldset>' +
				'<fieldset style="padding:0px 8px 0px 8px;">' +
				'<legend><font style="font-weight: bold;">申请信息</font></legend>' +
				'<ul><table width="100%">' +
				'<tr><td><font color=green font-weight=bold>申请人员：</font>'+r.get('applyOpr')+'</td>'  +
				'<td><font color=green>申请时间：</font>'+formatDt(r.get('applyTime'))+'</td></tr>' +
				'<tr><td><font color=green>日均交易金额：</font>'+r.get('dayAveAmt')+'</td>' +
				'<td><font color=green>单笔最小金额：</font>'+r.get('sigMinAmt')+'</td></tr>' +
				'<tr><td><font color=green>月均交易金额：</font>'+r.get('monAveAmt')+'</td>' +
				'<td><font color=green>单笔最大金额：</font>'+r.get('sigMaxAmt')+'</td></tr>' +
				'</table></ul>' +
				'<ul><p><font color=green>商户经营内容描述：</font>'+r.get('servDisp')+'</ul>' +
				'<ul><p><font color=green>申请原因：</font>'+r.get('applyReason')+'</ul>' +
				'</fieldset>' +
				'</div>'
	}
	
	// 商户数据集
	/*var mchtStore = new Ext.data.JsonStore({
				fields : ['valueField', 'displayField'],
				root : 'data'
			});
	SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
				mchtStore.loadData(Ext.decode(ret));
			});*/
	
	var checkStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=whiteMchtCheck'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'mchtNoNm',mapping: 'mchtNoNm'},
			{name: 'bankNoNm',mapping: 'bankNoNm'},
			{name: 'checkTime',mapping: 'checkTime'},
			{name: 'checkOpr',mapping: 'checkOpr'},
			{name: 'checkStatus',mapping: 'checkStatus'},
			{name: 'mchtCaseDesp',mapping: 'mchtCaseDesp'},
			{name: 'refuseReason',mapping: 'refuseReason'},
			{name: 'dayAveAmt',mapping: 'dayAveAmt'},
			{name: 'monAveAmt',mapping: 'monAveAmt'},
			{name: 'sigMinAmt',mapping: 'sigMinAmt'},
			{name: 'sigMaxAmt',mapping: 'sigMaxAmt'},
			{name: 'servDisp',mapping: 'servDisp'},
			{name: 'applyReason',mapping: 'applyReason'},
			{name: 'applyOpr',mapping: 'applyOpr'},
			{name: 'applyTime',mapping: 'applyTime'}
		]),
		autoLoad: true
	});

/*	var rowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<div style="width:500px;line-height: 150%;">' +
			'<ul><p><font color=blue>商户情况描述： </font>{mchtCaseDesp}</p></ul>' +
			'<ul><p><font color=red>拒绝原因：</font>{refuseReason}</p></ul>' +
			'</div><hr/>' +
			'<div style="width:500px;line-height: 150%;">' +
			'<ul><li><font color=green>日均交易金额：</font>{dayAveAmt}</li>' +
			'<li><font color=green>日均交易金额：</font>{dayAveAmt}</li></ul>' +
			'<ul><p><font color=green>日均交易金额：</font>{dayAveAmt}</p></ul>' +
			'</div>',
			{
				dateFromt: function(val) {
					if (val.length == 14) {
						return val.substring(0, 4) + '-' + val.substring(4, 6) + '-'
								+ val.substring(6, 8) + ' ' + val.substring(8, 10) + ':'
								+ val.substring(10, 12) + ':' + val.substring(12, 14);
					}else {
						return val;
					}
				}	
			}
		)
	});*/
	
	var reasonColModel = new Ext.grid.ColumnModel([
//		rowExpander,
		new Ext.grid.RowNumberer(),
		{header: '审核时间',dataIndex: 'checkTime',sortable: true,width: 150,renderer: formatDt},
		{id:'mchtNoNm',header: '商户编号',dataIndex: 'mchtNoNm',width: 250},
		{header: '商户所属机构',dataIndex: 'bankNoNm',width: 250},
		{header: '审核操作员',dataIndex: 'checkOpr'},
		{header: '审核结果',dataIndex: 'checkStatus',renderer: whiteMchtCS,width: 150}
	]);
	
	var grid = new Ext.grid.GridPanel({
		title: '白名单商户审核信息查询',
		region: 'center',
		iconCls: 'T20103',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: checkStore,
		autoExpandColumn: 'mchtNoNm',
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		cm: reasonColModel,
//		plugins: rowExpander,
		tbar: ['-','商户名称：',{
//					xtype: 'combo',
//					store : mchtStore, 
					xtype : 'dynamicCombo',
					methodName : 'getMchntNoAll',
					hiddenName: 'queryMchtNo',
					width: 300,
					id: 'idMchtNo',
					mode:'local',
					triggerAction: 'all',
					forceSelection: true,
					selectOnFocus: true,
					lazyRender: true,
					listeners : {
			            'beforequery':function(e){
			                var combo = e.combo;    
			                if(!e.forceAll){
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }
			            },
	                    'select': function(){
							checkStore.load();
	                    }
			        }
		        },'-','签约机构：',{
					xtype: 'basecomboselect',
					baseParams: 'BRH_BELOW_BRANCH',
//					xtype : 'dynamicCombo',
//					methodName : 'getBrhId',
					id: 'idbankNo',
					width: 250,
					hiddenName: 'bankNo',
					listeners : {
						 'beforequery':function(e){
			                var combo = e.combo;    
			                if(!e.forceAll){
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    combo.expand();    
			                    return false;  
			                }
			            },
	                    'select': function(){
							checkStore.load();
	                    }
			        }
				},'-','审核状态：',{
					xtype: 'combo',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['2','初审通过'],['3','初审不通过'],['4','终审通过'],['5','终审不通过']],
						reader: new Ext.data.ArrayReader()
					}),
					id: 'chackSta',
					name: 'chackSta',
					editable: false,
					width: 100,
					listeners : {
	                    'select': function(){
							checkStore.load();
	                    }
			        }
				/*},'-',{
					xtype: 'button',
					text: '查询',
					name: 'query',
					id: 'query',
					iconCls: 'query',
					width: 80,
					handler:function() {
						checkStore.load();
					}*/
				},'-',{
					xtype: 'button',										
					text: '重置',
					name: 'reset',
					id: 'reset',
					iconCls: 'reset',
					width: 80,
					handler:function() {							
						Ext.getCmp('idMchtNo').setValue('');	
						Ext.getCmp('idbankNo').setValue('');		
						Ext.getCmp('chackSta').setValue('');
						checkStore.reload();
						/*mchtStore.removeAll();
						SelectOptionsDWR.getComboData('MCHT_BELOW', function(ret) {
							mchtStore.loadData(Ext.decode(ret));
						});*/
					}
				}],
		loadMask: {
			msg: '正在加载白名单商户审核信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: checkStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		}),
		listeners:{
			'cellclick':selectableCell
		}
	});
	
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
		}
	});
	
	grid.on('mouseover',function(e){//添加mouseover事件
	    var view = grid.getView();
	    grid.tip = new Ext.ToolTip({
            target:view.mainBody,   
            delegate:'.x-grid3-row',   
            trackMouse :true,   
            width:450,   
            autoHide:true,    
//          title:"白名单商户审核信息详情",
            dismissDelay:0,  //默认5秒(值：5000)后提示框消失   
//	        frame:true,
            renderTo:document.body,
	        bodyStyle:'padding:5px;line-height: 200%;',
            listeners:{
                beforeshow:function updateTipBody(tip){
                    var rowIndex = view.findRowIndex(tip.triggerElement);
					createWhiteMchtDtl(checkStore.getAt(rowIndex));
                    tip.body.dom.innerHTML = whiteMchtDtl;
                }
            }
        })
	});
	
	checkStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			queryMchtNo: Ext.getCmp('idMchtNo').getValue(),
			queryBankNo: Ext.getCmp('idbankNo').getValue(),
			chackSta: Ext.getCmp('chackSta').getValue()
		});
	});
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [grid],
		renderTo: Ext.getBody()
	});
});