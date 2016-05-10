Ext.onReady(function() {
	function mchntIntegral(val){
		if (val == '0') {
			return '是';
		} else if (val == '1') {
			return '否';
		}else  {
			return '';
		}
	}
//	==================================主规则====================================
	// 数据集
	
	var mchntMccStore = new Ext.data.JsonStore({
		fields : ['valueField', 'displayField'],
		root : 'data'
	});
		SelectOptionsDWR.getComboData('MCC', function(ret) {
		mchntMccStore.loadData(Ext.decode(ret));
	});
	var routeRuleStore = new Ext.data.Store({
		id:'routeRuleStore',
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=mchtCompliance'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'mchtNo',mapping: 'mchtNo'},
			{name: 'mchtNm',mapping: 'mchtNm'},
			{name: 'addr',mapping: 'addr'},
			{name: 'mcc',mapping: 'mcc'},
			{name: 'country',mapping: 'country'},
			{name: 'mchtSta',mapping: 'mchtSta'},
			{name: 'qmchtNum',mapping: 'qmchtNum'}
		]),
		autoLoad: true
	}); 
	
	var routeRuleColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		//new Ext.grid.CheckboxSelectionModel({singleSelect : false}),
		{header: '商户号',dataIndex: 'mchtNo',align: 'center',width: 150,id:'msc1'},
		{header: '商户名称',dataIndex: 'mchtNm',width: 250,align: 'center' },
		{header: '商户地区',dataIndex: 'addr',align: 'center',width: 100 },
		{header: 'MCC',dataIndex: 'mcc',align: 'center',width: 100},
		{header: '是否县乡',dataIndex: 'country',width: 100,align: 'center',renderer: mchntIntegral},
		{header: '商户状态',dataIndex: 'mchtSta',align: 'center',width: 100,renderer: mchntSt },
		{header: '关联渠道商户数',dataIndex: 'qmchtNum',align: 'center',width: 150,id:'srvId'}
	]);
	
	var tbar1 = new Ext.Toolbar({  
                renderTo: Ext.grid.EditorGridPanel.tbar,  
                items:[
               			'-',	'商户号：',{
               				id:'mchtId',
               				xtype: 'dynamicCombo',
               				methodName: 'getMchntIdAll',
               				hiddenName: 'mchtNo',
               				editable: true,
               				width: 300
               			},
       			'-',	'是否县乡：',{
				    xtype: 'basecomboselect',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','是'],['1','否']],
						reader: new Ext.data.ArrayReader()
					}),
					id:'countryId',
					hiddenName: 'country',
					editable: false,
					width: 150
				
				},'-',	'商户状态：',{
					xtype: 'basecomboselect',
					id: 'mchtStatus',
					store: new Ext.data.ArrayStore({
						fields: ['valueField','displayField'],
						data: [['0','正常'],['B','添加待初审'],['1','添加待终审'],['3','修改待审核'],['5','冻结待审核'],['6','冻结'],['7','恢复待审核'],['8','注销'],['9','注销待审核']],
						reader: new Ext.data.ArrayReader()
					}),
					width: 180
				}
	            ]  
         }) 
	var tbar4 = new Ext.Toolbar({  
        renderTo: Ext.grid.EditorGridPanel.tbar,  
        items:[
              
       			'-','M C C ：',{
       				xtype: 'basecomboselect',
       				store: mchntMccStore,
       				editable: true,
       				lazyRender: true,
       				width: 300,
       				id: 'queryIdmcc',
       				hiddenName: 'queryMcc'
       			},'-',	'商户地区：',{
       				xtype: 'dynamicCombo',
       				methodName: 'getAreaCode',
       				editable: true,
       				id: 'queryMchtAreaId',
       				hiddenName:'queryMchtArea',
       				width: 150
       			}, '-',	'关联渠道商户数：  ',{
    					xtype: 'numberfield',
    					id: 'minNum',
    					name: 'min',
    					vtype:'isEngNum',
    					//maxLength: 10,
    					width: 60
    				},'——',{

    					xtype: 'numberfield',
    					id: 'maxNum',
    					name: 'max',
    					vtype:'isEngNum',
    					//maxLength: 10,
    					width: 60
    				
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
				Ext.getCmp('mchtId').setValue('');
				Ext.getCmp('countryId').setValue('');
				Ext.getCmp('mchtStatus').setValue('');
				Ext.getCmp('queryIdmcc').setValue('');
				Ext.getCmp('queryMchtAreaId').setValue('');
				Ext.getCmp('minNum').setValue('');
				Ext.getCmp('maxNum').setValue('');
				routeRuleStore.reload();
			}
		},'-', {
				xtype : 'button',
				text : '导出文件',
				name : 'exportFile',
				id : 'exportFile',
				iconCls : 'edit',
				width : 80,
				handler : function() {
					Ext.Ajax.request({
						url: 'T110351Action_exportData.asp',
						timeout: 60000,
						params: {
							mchtId:Ext.getCmp('mchtId').getValue(),
							countryId:Ext.getCmp('countryId').getValue(),
							mchtStatus:Ext.getCmp('mchtStatus').getValue(),
							queryIdmcc:Ext.getCmp('queryIdmcc').getValue(),
							queryMchtAreaId:Ext.getCmp('queryMchtAreaId').getValue(),
							minNum:Ext.getCmp('minNum').getValue(),
							maxNum:Ext.getCmp('maxNum').getValue()
						},
						success: function(rsp,opt) {
							var rspObj = Ext.decode(rsp.responseText);
							if(rspObj.success) {
								window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+rspObj.msg;
								//gridStore.load();
							} else {
								showErrorMsg(rspObj.msg,gridPanel);
							}
						},
						failure: function(){
							showErrorMsg(rspObj.msg,gridPanel);
						}
					});
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
		}
	});
	routeRuleStore.on('beforeload', function(){
		routeRuleStore.removeAll();
		Ext.apply(this.baseParams, {
			start: 0,
			mchtId:Ext.getCmp('mchtId').getValue(),
			countryId:Ext.getCmp('countryId').getValue(),
			mchtStatus:Ext.getCmp('mchtStatus').getValue(),
			queryIdmcc:Ext.getCmp('queryIdmcc').getValue(),
			queryMchtAreaId:Ext.getCmp('queryMchtAreaId').getValue(),
			minNum:Ext.getCmp('minNum').getValue(),
			maxNum:Ext.getCmp('maxNum').getValue()
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