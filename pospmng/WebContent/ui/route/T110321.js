Ext.onReady(function() {
	
//	==================================主规则====================================
	// 数据集
	var routeRuleStore = new Ext.data.Store({
		id:'routeRuleStore',
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getMchtDetail'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'addr',mapping: 'addr'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'settle',mapping: 'settle'},
			{name: 'bill',mapping: 'bill'},
			{name: 'integral',mapping: 'integral'},
			{name: 'bankNo',mapping: 'bankNo'},
			{name: 'bankNm',mapping: 'bankNm'},
			{name: 'passTime',mapping: 'passTime'}
		]),
		autoLoad: true
	}); 
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '商户号',dataIndex: 'mchtNo',align: 'center',width: 150,id:'msc1'},
		{header: '商户名称',dataIndex: 'mchtNm',width: 150,align: 'center' },
		{header: '商户地区',dataIndex: 'addr',id:'srvId',align: 'center',width: 90 },
		{header: 'MCC',dataIndex: 'mcc',align: 'center',width: 60},
		{header: '结算扣率',dataIndex: 'settle',width: 100,align: 'center' ,renderer:profitSelect},
		{header: '是否对账单',dataIndex: 'bill',id:'srvId',align: 'center',width: 80,renderer:yesOrNo },
		{header: '是否积分',dataIndex: 'integral',align: 'center',width: 80,renderer:yesOrNo},
		{header: '合作伙伴号',dataIndex: 'bankNo',width: 80,align: 'center' },
		{header: '合作伙伴名称',dataIndex: 'bankNm',align: 'center',width: 190 },
		{header: '审批通过时间',dataIndex: 'passTime',id:'srvId',align: 'center',width: 90 }
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'商户号：',{
					xtype: 'textfield',
					id: 'mchtNo',
					name: 'mchtNo',
					vtype:'isEngNum',
					//maxLength: 10,
					width: 140
				},'-',	'商户名称：',{
					xtype: 'textfield',
					id: 'mchtNm',
					name: 'mchtNm',
					//vtype:'isEngNum',
					//maxLength: 10,
					width: 140
				},
       			'-',	'合作伙伴号：',{
					xtype: 'textfield',
					id: 'partnerNo',
					name: 'partnerNo',
					vtype:'isEngNum',
					//maxLength: 10,
					width: 140
				},'-',	'合作伙伴名称：',{
					xtype: 'textfield',
					id: 'partnerNm',
					name: 'partnerNm',
					format:'Ymd',
					//vtype:'isEngNum',
					//maxLength: 10,
					width: 140
				}
	            ]  
         }) 
	var tbar4 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
              
       			'-','M C C：',{
			xtype: 'textfield',
			id: 'mcc',
			name: 'mcc',
			vtype:'isEngNum',
			//maxLength: 10,
			width: 140
		},'-',	'商户地区：',{
			xtype: 'textfield',
			id: 'mchtAddr',
			name: 'mchtAddr',
			//vtype:'isEngNum',
			//maxLength: 10,
			width: 140
		}, '-',	'审批通过时间：  ',{
			xtype: 'datefield',
			vtype: 'daterange',
			editable: false,
			id: 'applyTime',
			name: 'applyTime',
			//vtype:'isEngNum',
			//maxLength: 10,
			width: 140
		}
        
        ]  
 }) 
	

        
         

    
         
    var routeRuleGrid = new Ext.grid.GridPanel({
		width:460,
//		title: '规则商户映射控制',
		iconCls: 'risk',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		region:'center',
		clicksToEdit: true,
		autoExpandColumn: 'msc1',
		store: routeRuleStore,
		sm : new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		cm: routeRuleColModel,
		forceValidation: true,
//		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载规则商户映射记录列表......'
		},
		tbar: 	[{
			
			xtype: 'button',
			text: '查询',
			name: 'query',
			id: 'query',
			iconCls: 'query',
			width: 80,
			handler:function() {
				routeRuleStore.load();
			}
		},'-',{
			xtype: 'button',
			text: '重置',
			name: 'reset',
			id: 'reset',
			iconCls: 'reset',
			width: 80,
			handler:function() {
				Ext.getCmp('mchtNo').setValue('');
				Ext.getCmp('partnerNo').setValue('');
				Ext.getCmp('mchtNm').setValue('');
				Ext.getCmp('partnerNm').setValue('');
				Ext.getCmp('mchtAddr').setValue('');
				Ext.getCmp('applyTime').setValue('');
				Ext.getCmp('mcc').setValue('');
				routeRuleStore.load();
			}
		},'-', {
				xtype : 'button',
				text : '配置',
				disabled:true,
				name : 'edit',
				id : 'edit',
				iconCls : 'edit',
				width : 80,
				handler : function() {
					var records=routeRuleGrid.getSelectionModel().getSelections();
					var store=routeRuleStore;
					batchConfigMchnt(records,store);
					//newMchntConfig(mchntId,store);
				}
		}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
					tbar1.render(this.tbar); 
					tbar4.render(this.tbar); 
					
                },
               'cellclick':selectableCell,
			
        }  ,
		bbar: new Ext.PagingToolbar({
			store: routeRuleStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	routeRuleGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(routeRuleGrid.getView().getRow(routeRuleGrid.getSelectionModel().last)).frame();
			Ext.getCmp('edit').enable();
//			Ext.getCmp('transferOut').enable();
			//var onFlag=grid.getSelectionModel().getSelected().get('msc2');
		}
	});
	routeRuleStore.on('beforeload', function(){
		Ext.getCmp('edit').disable();
		var date=Ext.getCmp('applyTime').getValue();
		var applyDate=dateFormat(date);
		routeRuleStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtNo:Ext.getCmp('mchtNo').getValue(),
			mchtNm: Ext.getCmp('mchtNm').getValue(),
			partnerNo:Ext.getCmp('partnerNo').getValue(),
			partnerNm:Ext.getCmp('partnerNm').getValue(),
			mcc: Ext.getCmp('mcc').getValue(),
			mchtAddr:Ext.getCmp('mchtAddr').getValue(),
			applyTime: applyDate
		});
	});
	
//	==============================主规则结束=======================================
	


	function dateFormat(value){ 
	    if(null != value && '' != value){ 
	        return Ext.util.Format.date(new Date(value),'Ymd'); 
	    }else{ 
	        return null; 
	    } 
	} 
	
	
//	=============================================新增商户组=================================================	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [routeRuleGrid],
		renderTo: Ext.getBody()
	});
});