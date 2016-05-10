Ext.onReady(function() {

	var actStores = new Ext.data.JsonStore({
		fields: ['valueField','displayField'],
		root: 'data',
		id: 'valueField'
	});
	
	SelectOptionsDWR.getComboData('ACT_NO_NAME_draw',function(ret){
		actStores.loadData(Ext.decode(ret));
	});
	
	
    var topQueryPanel = new Ext.form.FormPanel({
        frame: true,
        border: true,
        width: 500,
        autoHeight: true,
        labelWidth: 80,
        buttonAlign: 'center',
        items: [{
			xtype: 'basecomboselect',
			store: actStores,
			id:'actNoQuerrys',
//			labelStyle: 'padding-left: 5px',
			fieldLabel: '活动编号',
			hiddenName: 'actNoQuerry',
			editable: true,
			anchor: '90%'
		},{
        		width: 150,
                xtype: 'datefield',
                fieldLabel: '起始时间',
                format : 'Y-m-d',
                name :'startTimeQuerrys',
                id :'startTimeQuerrys',
                anchor :'60%'   
       		},{
       			width: 150,
                xtype: 'datefield',
                fieldLabel: '截止时间',
                format : 'Y-m-d',
                name :'endTimeQuerrys',
                id :'endTimeQuerrys',
                anchor :'60%' 
       		}],
        buttons: [{
            text: '查询',
            handler: function() 
            {
                actStore.load();
                queryWin.hide();
            }
        },{
            text: '重填',
            handler: function() {
                topQueryPanel.getForm().reset();
            }
        }]
    });
    
	var actStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=HOLDER_DRAW_ACT'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'actNo',mapping: 'actNo'},
			{name: 'actName',mapping: 'actName'},
			{name: 'startDate',mapping: 'startDate'},
			{name: 'endDate',mapping: 'endDate'},
			{name: 'drawAmt',mapping: 'drawAmt'},
			
			{name: 'aGrade',mapping: 'aGrade'},
			{name: 'aRate',mapping: 'aRate'},
			{name: 'aNum',mapping: 'aNum'},
			{name: 'aNumLeft',mapping: 'aNumLeft'},
			{name: 'aDesc',mapping: 'aDesc'},
			
			{name: 'bGrade',mapping: 'bGrade'},
			{name: 'bRate',mapping: 'bRate'},
			{name: 'bNum',mapping: 'bNum'},
			{name: 'bNumLeft',mapping: 'bNumLeft'},
			{name: 'bDesc',mapping: 'bDesc'},
			
			{name: 'cGrade',mapping: 'cGrade'},
			{name: 'cRate',mapping: 'cRate'},
			{name: 'cNum',mapping: 'cNum'},
			{name: 'cNumLeft',mapping: 'cNumLeft'},
			{name: 'cDesc',mapping: 'cDesc'},
			
			{name: 'dGrade',mapping: 'dGrade'},
			{name: 'dRate',mapping: 'dRate'},
			{name: 'dNum',mapping: 'dNum'},
			{name: 'dNumLeft',mapping: 'dNumLeft'},
			{name: 'dDesc',mapping: 'dDesc'},
			
			{name: 'eGrade',mapping: 'eGrade'},
			{name: 'eRate',mapping: 'eRate'},
			{name: 'eNum',mapping: 'eNum'},
			{name: 'eNumLeft',mapping: 'eNumLeft'},
			{name: 'eDesc',mapping: 'eDesc'}
		])
	});
	
	actStore.on('beforeload', function() {
        Ext.apply(this.baseParams, {
            start: 0,
            actNo: Ext.getCmp('actNoQuerrys').getValue(),
            startDate: Ext.getCmp('startTimeQuerrys').getValue(),
            endDate: Ext.getCmp('endTimeQuerrys').getValue()
        });
        grid.getTopToolbar().items.items[2].disable();
		grid.getTopToolbar().items.items[4].disable();
    }); 
	
//	var sm = new Ext.grid.CheckboxSelectionModel();

    var termRowExpander = new Ext.ux.grid.RowExpander({
		tpl: new Ext.Template(
			'<table width=1000>',
			'<tr><td><font color=green>二等奖中奖几率：</font>{bRate}</td>',
			'<td><font color=green>二等奖总数：</font>{bNum}</td>',
			'<td><font color=green>二等奖剩余个数：</font>{bNumLeft}</td>',
			'<td><font color=green>二等奖奖品说明：</font>{bDesc}</td></tr>',
			
			'<tr><td><font color=green>三等奖中奖几率：</font>{cRate}</td>',
			'<td><font color=green>三等奖总数：</font>{cNum}</td>',
			'<td><font color=green>三等奖剩余个数：</font>{cNumLeft}</td>',
			'<td><font color=green>三等奖奖品说明：</font>{cDesc}</td></tr>',
			
			'<tr><td><font color=green>四等奖中奖几率：</font>{dRate}</td>',
			'<td><font color=green>四等奖总数：</font>{dNum}</td>',
			'<td><font color=green>四等奖剩余个数：</font>{dNumLeft}</td>',
			'<td><font color=green>四等奖奖品说明：</font>{dDesc}</td></tr>',
			
			'<tr><td><font color=green>五等奖中奖几率：</font>{eRate}</td>',
			'<td><font color=green>五等奖总数：</font>{eNum}</td>',
			'<td><font color=green>五等奖剩余个数：</font>{eNumLeft}</td>',
			'<td><font color=green>五等奖奖品说明：</font>{eDesc}</td></tr>',
			
			'</table>',
			{
				date : function(val){return formatDt(val);}
			}
		)
	});
	var actColModel = new Ext.grid.ColumnModel([
		termRowExpander,
		new Ext.grid.RowNumberer(),
//		sm,
		{header: '活动编号',dataIndex: 'actNo',width: 100},
		{header: '活动名称',dataIndex: 'actName',width: 120},
		{header: '起始日期',dataIndex: 'startDate',width: 80,renderer:formatDt},
		{header: '截止日期',dataIndex: 'endDate',width: 80,renderer:formatDt},
		{header: '抽奖金额',dataIndex: 'drawAmt',align: 'center'},
		{header: '一等奖中奖几率',dataIndex: 'aRate',align: 'center',width: 100},
		{header: '一等奖总数',dataIndex: 'aNum',align: 'center',width: 80},
		{header: '一等奖剩余个数',dataIndex: 'aNumLeft',align: 'center',width: 100},
		{id: 'mchtNm',header: '一等奖奖品说明',dataIndex: 'aDesc'},
		{header: '等级',dataIndex: 'aGrade',hidden:true},
		{header: '等级',dataIndex: 'bGrade',hidden:true},
		{header: '等级',dataIndex: 'cGrade',hidden:true},
		{header: '等级',dataIndex: 'dGrade',hidden:true},
		{header: '等级',dataIndex: 'eGrade',hidden:true}
		
//		{header: '费率(%)',dataIndex: 'actFee'}
	]);
	
	var deleteMenu = {
		text: '删除',
		width: 85,
		iconCls: 'delete',
		disabled: true,
		handler:function() {
           var selects = grid.getSelectionModel().getSelected();
           if(selects == null)
           {
           		showAlertMsg("请选择要删除记录",activityForm);
           		return;
           }
		   showConfirm('确定要解除绑定这些商户吗？',grid,function(bt) {
					//如果点击了提示的确定按钮
		   	
					if(bt == "yes") {
						Ext.Ajax.requestNeedAuthorise({
							url: 'T20803Action_delete.asp',
//							url: 'T20803Action.asp?method=delete',
							success: function(rsp,opt) {
								
					
								var rspObj = Ext.decode(rsp.responseText);
								if(rspObj.success) {
									showSuccessMsg(rspObj.msg,grid);
									grid.getStore().reload();
									topQueryPanel.getForm().reset();
									grid.getTopToolbar().items.items[2].disable();
									grid.getTopToolbar().items.items[4].disable();
								} else {
									showErrorMsg(rspObj.msg,grid);
								}
							},
							params: { 
								txnId: '20803',
								subTxnId: '03',
								actNo:selects.get('actNo')
							}
						}
						);
					}
				});
		}
	}
   
	var oprInfoForm = new Ext.FormPanel({
		frame: true,
		height: 350,
		layout: 'column',
		waitMsgTarget: true,
		defaults: {
			bodyStyle: 'padding-left: 10px'
		},
		items: [{
			columnWidth: .5,
			layout: 'form',
			labelWidth: 100,
			items: [{
				
				xtype: 'basecomboselect',
				store: actStores,
//				id:'actNoQuerrys',
//				labelStyle: 'padding-left: 5px',
				fieldLabel: '活动编号',
				hiddenName: 'actNoAdd',
				editable: true,
				anchor: '90%',
				minListWidth:200,
				allowBlank: false,
				blankText: '请选择活动号'
				
//				vtype: 'alphanum'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			labelWidth: 100,
			items: [{
				xtype: 'textfield',
				id: 'drawAmtAdd',
				maxLength: 12,
//				minLength: 6,
				fieldLabel: '抽奖限额',
				allowBlank: false,
				blankText: '请输入抽奖限额',
				vtype: 'isRate'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			labelWidth: 100,
			items: [{
				xtype: 'basecomboselect',
				hiddenName: 'gradeAdd',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['1','一个奖项'],['2','二个奖项'],['3','三个奖项'],['4','四个奖项'],['5','五个奖项']],
					reader: new Ext.data.ArrayReader()
				}),
//				maxLength: 12,
//				minLength: 6,
				fieldLabel: '奖项等级数',
				allowBlank: false,
				anchor: '90%',
				blankText: '请输入奖项等级数',
				listeners : {
					'select' : function() {
						if (this.getValue() == '1') {
							oprInfoForm.findById('aRateAdd').enable();
							oprInfoForm.findById('aNumAdd').enable();
							oprInfoForm.findById('aDescAdd').enable();
							oprInfoForm.findById('aDescAdd').allowBlank = false;
							oprInfoForm.findById('bDescAdd').allowBlank = true;
							oprInfoForm.findById('cDescAdd').allowBlank = true;
							oprInfoForm.findById('dDescAdd').allowBlank = true;
							oprInfoForm.findById('eDescAdd').allowBlank = true;
							
							oprInfoForm.findById('bRateAdd').setValue("");
							oprInfoForm.findById('bNumAdd').setValue("");
							oprInfoForm.findById('bDescAdd').setValue("");
							oprInfoForm.findById('cRateAdd').setValue("");
							oprInfoForm.findById('cNumAdd').setValue("");
							oprInfoForm.findById('cDescAdd').setValue("");
							oprInfoForm.findById('dRateAdd').setValue("");
							oprInfoForm.findById('dNumAdd').setValue("");
							oprInfoForm.findById('dDescAdd').setValue("");
							oprInfoForm.findById('eRateAdd').setValue("");
							oprInfoForm.findById('eNumAdd').setValue("");
							oprInfoForm.findById('eDescAdd').setValue("");
							
							oprInfoForm.findById('bRateAdd').clearInvalid();
							oprInfoForm.findById('bNumAdd').clearInvalid();
							oprInfoForm.findById('bDescAdd').clearInvalid();
							oprInfoForm.findById('cRateAdd').clearInvalid();
							oprInfoForm.findById('cNumAdd').clearInvalid();
							oprInfoForm.findById('cDescAdd').clearInvalid();
							oprInfoForm.findById('dRateAdd').clearInvalid();
							oprInfoForm.findById('dNumAdd').clearInvalid();
							oprInfoForm.findById('dDescAdd').clearInvalid();
							oprInfoForm.findById('eRateAdd').clearInvalid();
							oprInfoForm.findById('eNumAdd').clearInvalid();
							oprInfoForm.findById('eDescAdd').clearInvalid();
							
							oprInfoForm.findById('bRateAdd').disable();
							oprInfoForm.findById('bNumAdd').disable();
							oprInfoForm.findById('bDescAdd').disable();
							oprInfoForm.findById('cRateAdd').disable();
							oprInfoForm.findById('cNumAdd').disable();
							oprInfoForm.findById('cDescAdd').disable();
							oprInfoForm.findById('dRateAdd').disable();
							oprInfoForm.findById('dNumAdd').disable();
							oprInfoForm.findById('dDescAdd').disable();
							oprInfoForm.findById('eRateAdd').disable();
							oprInfoForm.findById('eNumAdd').disable();
							oprInfoForm.findById('eDescAdd').disable();
							
						} else if (this.getValue() == '2') {
							oprInfoForm.findById('aRateAdd').enable();
							oprInfoForm.findById('aNumAdd').enable();
							oprInfoForm.findById('aDescAdd').enable();
							oprInfoForm.findById('aDescAdd').allowBlank = false;
							
							oprInfoForm.findById('bRateAdd').enable();
							oprInfoForm.findById('bNumAdd').enable();
							oprInfoForm.findById('bDescAdd').enable();
							oprInfoForm.findById('bDescAdd').allowBlank = false;
							oprInfoForm.findById('cDescAdd').allowBlank = true;
							oprInfoForm.findById('dDescAdd').allowBlank = true;
							oprInfoForm.findById('eDescAdd').allowBlank = true;
							
							oprInfoForm.findById('cRateAdd').setValue("");
							oprInfoForm.findById('cNumAdd').setValue("");
							oprInfoForm.findById('cDescAdd').setValue("");
							oprInfoForm.findById('dRateAdd').setValue("");
							oprInfoForm.findById('dNumAdd').setValue("");
							oprInfoForm.findById('dDescAdd').setValue("");
							oprInfoForm.findById('eRateAdd').setValue("");
							oprInfoForm.findById('eNumAdd').setValue("");
							oprInfoForm.findById('eDescAdd').setValue("");
							
							oprInfoForm.findById('cRateAdd').clearInvalid();
							oprInfoForm.findById('cNumAdd').clearInvalid();
							oprInfoForm.findById('cDescAdd').clearInvalid();
							oprInfoForm.findById('dRateAdd').clearInvalid();
							oprInfoForm.findById('dNumAdd').clearInvalid();
							oprInfoForm.findById('dDescAdd').clearInvalid();
							oprInfoForm.findById('eRateAdd').clearInvalid();
							oprInfoForm.findById('eNumAdd').clearInvalid();
							oprInfoForm.findById('eDescAdd').clearInvalid();
							
							oprInfoForm.findById('cRateAdd').disable();
							oprInfoForm.findById('cNumAdd').disable();
							oprInfoForm.findById('cDescAdd').disable();
							oprInfoForm.findById('dRateAdd').disable();
							oprInfoForm.findById('dNumAdd').disable();
							oprInfoForm.findById('dDescAdd').disable();
							oprInfoForm.findById('eRateAdd').disable();
							oprInfoForm.findById('eNumAdd').disable();
							oprInfoForm.findById('eDescAdd').disable();
						} else if (this.getValue() == '3') {
							oprInfoForm.findById('aRateAdd').enable();
							oprInfoForm.findById('aNumAdd').enable();
							oprInfoForm.findById('aDescAdd').enable();
							oprInfoForm.findById('aDescAdd').allowBlank = false;
							
							oprInfoForm.findById('bRateAdd').enable();
							oprInfoForm.findById('bNumAdd').enable();
							oprInfoForm.findById('bDescAdd').enable();
							oprInfoForm.findById('bDescAdd').allowBlank = false;
							
							oprInfoForm.findById('cRateAdd').enable();
							oprInfoForm.findById('cNumAdd').enable();
							oprInfoForm.findById('cDescAdd').enable();
							oprInfoForm.findById('cDescAdd').allowBlank = false;
							oprInfoForm.findById('dDescAdd').allowBlank = true;
							oprInfoForm.findById('eDescAdd').allowBlank = true;
							
							oprInfoForm.findById('dRateAdd').setValue("");
							oprInfoForm.findById('dNumAdd').setValue("");
							oprInfoForm.findById('dDescAdd').setValue("");
							oprInfoForm.findById('eRateAdd').setValue("");
							oprInfoForm.findById('eNumAdd').setValue("");
							oprInfoForm.findById('eDescAdd').setValue("");
							
							oprInfoForm.findById('dRateAdd').clearInvalid();
							oprInfoForm.findById('dNumAdd').clearInvalid();
							oprInfoForm.findById('dDescAdd').clearInvalid();
							oprInfoForm.findById('eRateAdd').clearInvalid();
							oprInfoForm.findById('eNumAdd').clearInvalid();
							oprInfoForm.findById('eDescAdd').clearInvalid();
							
							oprInfoForm.findById('dRateAdd').disable();
							oprInfoForm.findById('dNumAdd').disable();
							oprInfoForm.findById('dDescAdd').disable();
							oprInfoForm.findById('eRateAdd').disable();
							oprInfoForm.findById('eNumAdd').disable();
							oprInfoForm.findById('eDescAdd').disable();
						}else if (this.getValue() == '4') {
							oprInfoForm.findById('aRateAdd').enable();
							oprInfoForm.findById('aNumAdd').enable();
							oprInfoForm.findById('aDescAdd').enable();
							oprInfoForm.findById('aDescAdd').allowBlank = false;
							
							oprInfoForm.findById('bRateAdd').enable();
							oprInfoForm.findById('bNumAdd').enable();
							oprInfoForm.findById('bDescAdd').enable();
							oprInfoForm.findById('bDescAdd').allowBlank = false;
							
							oprInfoForm.findById('cRateAdd').enable();
							oprInfoForm.findById('cNumAdd').enable();
							oprInfoForm.findById('cDescAdd').enable();
							oprInfoForm.findById('cDescAdd').allowBlank = false;
							
							oprInfoForm.findById('dRateAdd').enable();
							oprInfoForm.findById('dNumAdd').enable();
							oprInfoForm.findById('dDescAdd').enable();
							oprInfoForm.findById('dDescAdd').allowBlank = false;
							oprInfoForm.findById('eDescAdd').allowBlank = true;
							
							oprInfoForm.findById('eRateAdd').setValue("");
							oprInfoForm.findById('eNumAdd').setValue("");
							oprInfoForm.findById('eDescAdd').setValue("");
							oprInfoForm.findById('eRateAdd').clearInvalid();
							oprInfoForm.findById('eNumAdd').clearInvalid();
							oprInfoForm.findById('eDescAdd').clearInvalid();
							oprInfoForm.findById('eRateAdd').disable();
							oprInfoForm.findById('eNumAdd').disable();
							oprInfoForm.findById('eDescAdd').disable();
						}else if (this.getValue() == '5') {
							oprInfoForm.findById('aRateAdd').enable();
							oprInfoForm.findById('aNumAdd').enable();
							oprInfoForm.findById('aDescAdd').enable();
							oprInfoForm.findById('aDescAdd').allowBlank = false;
							
							oprInfoForm.findById('bRateAdd').enable();
							oprInfoForm.findById('bNumAdd').enable();
							oprInfoForm.findById('bDescAdd').enable();
							oprInfoForm.findById('bDescAdd').allowBlank = false;
							
							oprInfoForm.findById('cRateAdd').enable();
							oprInfoForm.findById('cNumAdd').enable();
							oprInfoForm.findById('cDescAdd').enable();
							oprInfoForm.findById('cDescAdd').allowBlank = false;
							
							oprInfoForm.findById('dRateAdd').enable();
							oprInfoForm.findById('dNumAdd').enable();
							oprInfoForm.findById('dDescAdd').enable();
							oprInfoForm.findById('dDescAdd').allowBlank = false;
							
							oprInfoForm.findById('eRateAdd').enable();
							oprInfoForm.findById('eNumAdd').enable();
							oprInfoForm.findById('eDescAdd').enable();
							oprInfoForm.findById('eDescAdd').allowBlank = false;
						}
					}
				}
//				vtype: 'isRate'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'aRateAdd',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
               	disabled:true,
				fieldLabel: '一等奖中奖几率',
				blankText: '请输入一等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'aNumAdd',
				xtype: 'numberfield',
				fieldLabel: '一等奖总数',
				allowBlank: false,
				disabled:true,
//				maxLength: 4,
				blankText: '请输入一等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'aDescAdd',
				xtype: 'textfield',
				disabled:true,
				fieldLabel: '一等奖奖品说明',
				allowBlank: true,
				width: 414,	
				maxLength: 20,
				blankText: '请输入一等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'bRateAdd',
				allowBlank: false,
				disabled:true,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '二等奖中奖几率',
				blankText: '请输入二等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'bNumAdd',
				xtype: 'numberfield',
				fieldLabel: '二等奖总数',
				allowBlank: false,
				disabled:true,
//				maxLength: 4,
				blankText: '请输入二等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'bDescAdd',
				xtype: 'textfield',
				fieldLabel: '二等奖奖品说明',
				allowBlank: true,
				disabled:true,
				width: 414,	
				maxLength: 20,
				blankText: '请输入二等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'cRateAdd',
				allowBlank: false,
				disabled:true,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '三等奖中奖几率',
				blankText: '请输入三等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'cNumAdd',
				xtype: 'numberfield',
				fieldLabel: '三等奖总数',
				allowBlank: false,
				disabled:true,
//				maxLength: 4,
				blankText: '请输入三等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'cDescAdd',
				xtype: 'textfield',
				fieldLabel: '三等奖奖品说明',
				allowBlank: true,
				disabled:true,
				maxLength: 20,
				width: 414,	
				blankText: '请输入三等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'dRateAdd',
				allowBlank: false,
				disabled:true,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '四等奖中奖几率',
				blankText: '请输入四等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'dNumAdd',
				xtype: 'numberfield',
				fieldLabel: '四等奖总数',
				allowBlank: false,
				disabled:true,
//				maxLength: 4,
				blankText: '请输入四等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'dDescAdd',
				xtype: 'textfield',
				fieldLabel: '四等奖奖品说明',
				allowBlank: true,
				disabled:true,
				maxLength: 20,
				width: 414,	
				blankText: '请输入四等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'eRateAdd',
				allowBlank: false,
				disabled:true,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '五等奖中奖几率',
				blankText: '请输入五等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'eNumAdd',
				xtype: 'numberfield',
				fieldLabel: '五等奖总数',
				allowBlank: false,
				disabled:true,
//				maxLength: 4,
				blankText: '请输入五等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'eDescAdd',
				xtype: 'textfield',
				fieldLabel: '五等奖奖品说明',
				allowBlank: true,
				disabled:true,
				maxLength: 20,
				width: 414,	
				blankText: '请输入五等奖奖品说明'
			}]
		}]
	});
	
	
	var devModifyForm = new Ext.FormPanel({
		frame: true,
		height: 350,
		layout: 'column',
		waitMsgTarget: true,
		defaults: {
			bodyStyle: 'padding-left: 10px'
		},
		items: [{
			columnWidth: .5,
			layout: 'form',
			labelWidth: 100,
			items: [{
				
				xtype: 'basecomboselect',
				store: actStores,
//				id:'actNoQuerrys',
//				labelStyle: 'padding-left: 5px',
				fieldLabel: '活动编号',
				hiddenName: 'actNo',
				readOnly:true,
				editable: true,
				anchor: '90%',
				allowBlank: false,
				blankText: '请选择活动号'
				
//				vtype: 'alphanum'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			labelWidth: 100,
			items: [{
				xtype: 'textfield',
				id: 'drawAmt',
				name:'drawAmt',
				maxLength: 12,
//				minLength: 6,
				fieldLabel: '抽奖金额',
				allowBlank: false,
				blankText: '请输入抽奖金额',
				vtype: 'isRate'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			labelWidth: 100,
			items: [{
				xtype: 'basecomboselect',
				id:'grades',
				hiddenName: 'grade',
				store: new Ext.data.ArrayStore({
					fields: ['valueField','displayField'],
					data: [['1','一个奖项'],['2','二个奖项'],['3','三个奖项'],['4','四个奖项'],['5','五个奖项']],
					reader: new Ext.data.ArrayReader()
				}),
//				maxLength: 12,
//				minLength: 6,
				fieldLabel: '奖项等级数',
				allowBlank: false,
				anchor: '90%',
				blankText: '请输入奖项等级数',
				listeners : {
					'select' : function() {
						if (this.getValue() == '1') {
							devModifyForm.findById('aRate').enable();
							devModifyForm.findById('aNum').enable();
							devModifyForm.findById('aDesc').enable();
							
							
							devModifyForm.findById('bRate').setValue("");
							devModifyForm.findById('bNum').setValue("");
							devModifyForm.findById('bDesc').setValue("");
							devModifyForm.findById('cRate').setValue("");
							devModifyForm.findById('cNum').setValue("");
							devModifyForm.findById('cDesc').setValue("");
							devModifyForm.findById('dRate').setValue("");
							devModifyForm.findById('dNum').setValue("");
							devModifyForm.findById('dDesc').setValue("");
							devModifyForm.findById('eRate').setValue("");
							devModifyForm.findById('eNum').setValue("");
							devModifyForm.findById('eDesc').setValue("");
							
							devModifyForm.findById('bRate').clearInvalid();
							devModifyForm.findById('bNum').clearInvalid();
							devModifyForm.findById('bDesc').clearInvalid();
							devModifyForm.findById('cRate').clearInvalid();
							devModifyForm.findById('cNum').clearInvalid();
							devModifyForm.findById('cDesc').clearInvalid();
							devModifyForm.findById('dRate').clearInvalid();
							devModifyForm.findById('dNum').clearInvalid();
							devModifyForm.findById('dDesc').clearInvalid();
							devModifyForm.findById('eRate').clearInvalid();
							devModifyForm.findById('eNum').clearInvalid();
							devModifyForm.findById('eDesc').clearInvalid();
							
							devModifyForm.findById('bRate').disable();
							devModifyForm.findById('bNum').disable();
							devModifyForm.findById('bDesc').disable();
							devModifyForm.findById('cRate').disable();
							devModifyForm.findById('cNum').disable();
							devModifyForm.findById('cDesc').disable();
							devModifyForm.findById('dRate').disable();
							devModifyForm.findById('dNum').disable();
							devModifyForm.findById('dDesc').disable();
							devModifyForm.findById('eRate').disable();
							devModifyForm.findById('eNum').disable();
							devModifyForm.findById('eDesc').disable();
						} else if (this.getValue() == '2') {
							devModifyForm.findById('aRate').enable();
							devModifyForm.findById('aNum').enable();
							devModifyForm.findById('aDesc').enable();
							
							devModifyForm.findById('bRate').enable();
							devModifyForm.findById('bNum').enable();
							devModifyForm.findById('bDesc').enable();
							
							
							devModifyForm.findById('cRate').setValue("");
							devModifyForm.findById('cNum').setValue("");
							devModifyForm.findById('cDesc').setValue("");
							devModifyForm.findById('dRate').setValue("");
							devModifyForm.findById('dNum').setValue("");
							devModifyForm.findById('dDesc').setValue("");
							devModifyForm.findById('eRate').setValue("");
							devModifyForm.findById('eNum').setValue("");
							devModifyForm.findById('eDesc').setValue("");
							
							devModifyForm.findById('cRate').clearInvalid();
							devModifyForm.findById('cNum').clearInvalid();
							devModifyForm.findById('cDesc').clearInvalid();
							devModifyForm.findById('dRate').clearInvalid();
							devModifyForm.findById('dNum').clearInvalid();
							devModifyForm.findById('dDesc').clearInvalid();
							devModifyForm.findById('eRate').clearInvalid();
							devModifyForm.findById('eNum').clearInvalid();
							devModifyForm.findById('eDesc').clearInvalid();
							
							devModifyForm.findById('cRate').disable();
							devModifyForm.findById('cNum').disable();
							devModifyForm.findById('cDesc').disable();
							devModifyForm.findById('dRate').disable();
							devModifyForm.findById('dNum').disable();
							devModifyForm.findById('dDesc').disable();
							devModifyForm.findById('eRate').disable();
							devModifyForm.findById('eNum').disable();
							devModifyForm.findById('eDesc').disable();
						} else if (this.getValue() == '3') {
							devModifyForm.findById('aRate').enable();
							devModifyForm.findById('aNum').enable();
							devModifyForm.findById('aDesc').enable();
							
							devModifyForm.findById('bRate').enable();
							devModifyForm.findById('bNum').enable();
							devModifyForm.findById('bDesc').enable();
							
							devModifyForm.findById('cRate').enable();
							devModifyForm.findById('cNum').enable();
							devModifyForm.findById('cDesc').enable();
							
							devModifyForm.findById('dRate').setValue("");
							devModifyForm.findById('dNum').setValue("");
							devModifyForm.findById('dDesc').setValue("");
							devModifyForm.findById('eRate').setValue("");
							devModifyForm.findById('eNum').setValue("");
							devModifyForm.findById('eDesc').setValue("");
							
							devModifyForm.findById('dRate').clearInvalid();
							devModifyForm.findById('dNum').clearInvalid();
							devModifyForm.findById('dDesc').clearInvalid();
							devModifyForm.findById('eRate').clearInvalid();
							devModifyForm.findById('eNum').clearInvalid();
							devModifyForm.findById('eDesc').clearInvalid();
							
							devModifyForm.findById('dRate').disable();
							devModifyForm.findById('dNum').disable();
							devModifyForm.findById('dDesc').disable();
							devModifyForm.findById('eRate').disable();
							devModifyForm.findById('eNum').disable();
							devModifyForm.findById('eDesc').disable();
						}else if (this.getValue() == '4') {
							devModifyForm.findById('aRate').enable();
							devModifyForm.findById('aNum').enable();
							devModifyForm.findById('aDesc').enable();
							
							devModifyForm.findById('bRate').enable();
							devModifyForm.findById('bNum').enable();
							devModifyForm.findById('bDesc').enable();
							
							devModifyForm.findById('cRate').enable();
							devModifyForm.findById('cNum').enable();
							devModifyForm.findById('cDesc').enable();
							
							devModifyForm.findById('dRate').enable();
							devModifyForm.findById('dNum').enable();
							devModifyForm.findById('dDesc').enable();
							
							devModifyForm.findById('eRate').setValue("");
							devModifyForm.findById('eNum').setValue("");
							devModifyForm.findById('eDesc').setValue("");
							devModifyForm.findById('eRate').clearInvalid();
							devModifyForm.findById('eNum').clearInvalid();
							devModifyForm.findById('eDesc').clearInvalid();
							devModifyForm.findById('eRate').disable();
							devModifyForm.findById('eNum').disable();
							devModifyForm.findById('eDesc').disable();
						}else if (this.getValue() == '5') {
							devModifyForm.findById('aRate').enable();
							devModifyForm.findById('aNum').enable();
							devModifyForm.findById('aDesc').enable();
							
							devModifyForm.findById('bRate').enable();
							devModifyForm.findById('bNum').enable();
							devModifyForm.findById('bDesc').enable();
							
							devModifyForm.findById('cRate').enable();
							devModifyForm.findById('cNum').enable();
							devModifyForm.findById('cDesc').enable();
							
							devModifyForm.findById('dRate').enable();
							devModifyForm.findById('dNum').enable();
							devModifyForm.findById('dDesc').enable();
							
							devModifyForm.findById('eRate').enable();
							devModifyForm.findById('eNum').enable();
							devModifyForm.findById('eDesc').enable();
						}
					}
				}
//				vtype: 'isRate'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'aRate',
				name:'aRate',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '一等奖中奖几率',
				blankText: '请输入一等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'aNum',
				name:'aNum',
				xtype: 'numberfield',
				fieldLabel: '一等奖总数',
				allowBlank: false,
//				maxLength: 4,
				blankText: '请输入一等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'aDesc',
				name:'aDesc',
				xtype: 'textfield',
				fieldLabel: '一等奖奖品说明',
				allowBlank: false,
				width: 414,	
				maxLength: 20,
				blankText: '请输入一等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'bRate',
				name:'bRate',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '二等奖中奖几率',
				blankText: '请输入二等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'bNum',
				name:'bNum',
				xtype: 'numberfield',
				fieldLabel: '二等奖总数',
				allowBlank: false,
//				maxLength: 4,
				blankText: '请输入二等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'bDesc',
				name:'bDesc',
				xtype: 'textfield',
				fieldLabel: '二等奖奖品说明',
				allowBlank: false,
				width: 414,	
				maxLength: 20,
				blankText: '请输入二等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'cRate',
				name:'cRate',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '三等奖中奖几率',
				blankText: '请输入三等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'cNum',
				name:'cNum',
				xtype: 'numberfield',
				fieldLabel: '三等奖总数',
				allowBlank: false,
//				maxLength: 4,
				blankText: '请输入三等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'cDesc',
				name:'cDesc',
				xtype: 'textfield',
				fieldLabel: '三等奖奖品说明',
				allowBlank: false,
				width: 414,	
				maxLength: 20,
				blankText: '请输入三等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'dRate',
				name:'dRate',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '四等奖中奖几率',
				blankText: '请输入四等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'dNum',
				name:'dNum',
				xtype: 'numberfield',
				fieldLabel: '四等奖总数',
				allowBlank: false,
//				maxLength: 4,
				blankText: '请输入四等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'dDesc',
				name:'dDesc',
				xtype: 'textfield',
				fieldLabel: '四等奖奖品说明',
				allowBlank: false,
				width: 414,	
				maxLength: 20,
				blankText: '请输入四等奖奖品说明'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
//				xtype: 'numberfield',
				xtype:'textfield',
				id: 'eRate',
				name:'eRate',
				allowBlank: false,
//				maxLength: 6,
//				minLength: 6,
//				regex: /^\d{1}(\.\d{1,4})?$/, 
				regex: /^0\.\d{1,4}$/,
               	regexText: '请输入小数',
				fieldLabel: '五等奖中奖几率',
				blankText: '请输入五等奖中奖几率'
			}]
		},{
			columnWidth: .5,
			layout: 'form',
			items: [{
				id: 'eNum',
				name:'eNum',
				xtype: 'numberfield',
				fieldLabel: '五等奖总数',
				allowBlank: false,
//				maxLength: 4,
				blankText: '请输入五等奖总数'
			}]
		},{
			columnWidth: 1.0,
			layout: 'form',
			items: [{
				id: 'eDesc',
				name:'eDesc',
				xtype: 'textfield',
				fieldLabel: '五等奖奖品说明',
				allowBlank: false,
				width: 414,	
				maxLength: 20,
				blankText: '请输入五等奖奖品说明'
			}]
		}]
	});
	// 操作员添加窗口
	var oprWin = new Ext.Window({
		title: '持卡人抽奖活动新增',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [oprInfoForm],
		animateTarget: 'add',
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(oprInfoForm.getForm().isValid()) {
					oprInfoForm.getForm().submit({
//						url: 'T20803Action.asp?method=add',
						url: 'T20803Action_add.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							oprInfoForm.getForm().reset();
							oprWin.hide();
							showSuccessMsg(action.result.msg,grid);
							//重置表单
							//重新加载列表
							topQueryPanel.getForm().reset();
							grid.getStore().reload();
							grid.getTopToolbar().items.items[2].disable();
							grid.getTopToolbar().items.items[4].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '20803',
							subTxnId: '01'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				oprInfoForm.getForm().reset();
				oprInfoForm.findById('aRateAdd').disable();
				oprInfoForm.findById('aNumAdd').disable();
				oprInfoForm.findById('aDescAdd').disable();
				oprInfoForm.findById('bRateAdd').disable();
				oprInfoForm.findById('bNumAdd').disable();
				oprInfoForm.findById('bDescAdd').disable();
				oprInfoForm.findById('cRateAdd').disable();
				oprInfoForm.findById('cNumAdd').disable();
				oprInfoForm.findById('cDescAdd').disable();
				oprInfoForm.findById('dRateAdd').disable();
				oprInfoForm.findById('dNumAdd').disable();
				oprInfoForm.findById('dDescAdd').disable();
				oprInfoForm.findById('eRateAdd').disable();
				oprInfoForm.findById('eNumAdd').disable();
			}
		},{
			text: '关闭',
			handler: function() {
				oprWin.hide();
			}
		}]
	});
	
	var devModifyWin = new Ext.Window({
		title: '持卡人抽奖活动修改',
		initHidden: true,
		header: true,
		frame: true,
		closable: false,
		modal: true,
		width: 600,
		autoHeight: true,
		layout: 'fit',
		items: [devModifyForm],
		animateTarget: 'update',
		buttonAlign: 'center',
		closeAction: 'hide',
		iconCls: 'logo',
		resizable: false,
		buttons: [{
			text: '确定',
			handler: function() {
				if(devModifyForm.getForm().isValid()) {
					devModifyForm.getForm().submit({
//						url: 'T20803Action.asp?method=update',
						url: 'T20803Action_update.asp',
						waitMsg: '正在提交，请稍后......',
						success: function(form,action) {
							devModifyForm.getForm().reset();
							devModifyWin.hide();
							showSuccessMsg(action.result.msg,grid);
							//重置表单
							//重新加载列表
							topQueryPanel.getForm().reset();
							grid.getStore().reload();
							grid.getTopToolbar().items.items[2].disable();
							grid.getTopToolbar().items.items[4].disable();
						},
						failure: function(form,action) {
							showErrorMsg(action.result.msg,grid);
						},
						params: {
							txnId: '20803',
							subTxnId: '02'
						}
					});
				}
			}
		},{
			text: '重置',
			handler: function() {
				devModifyForm.getForm().reset();
				rec = grid.getSelectionModel().getSelected();
				if(rec.get('aGrade')!='1'){
					devModifyForm.findById('aRate').disable();
					devModifyForm.findById('aNum').disable();
					devModifyForm.findById('aDesc').disable();
				}
				if(rec.get('bGrade')!='2'){
					devModifyForm.findById('bRate').disable();
					devModifyForm.findById('bNum').disable();
					devModifyForm.findById('bDesc').disable();
				}
				if(rec.get('cGrade')!='3'){
					devModifyForm.findById('cRate').disable();
					devModifyForm.findById('cNum').disable();
					devModifyForm.findById('cDesc').disable();
				}
				if(rec.get('dGrade')!='4'){
					devModifyForm.findById('dRate').disable();
					devModifyForm.findById('dNum').disable();
					devModifyForm.findById('dDesc').disable();
				}
				if(rec.get('eGrade')!='5'){
					devModifyForm.findById('eRate').disable();
					devModifyForm.findById('eNum').disable();
					devModifyForm.findById('eDesc').disable();
				}
			}
		},{
			text: '关闭',
			handler: function() {
				devModifyWin.hide();
			}
		}]
	});
	
    
    var queryWin = new Ext.Window({
        title: '查询条件',
        layout: 'fit',
        width: 500,
        autoHeight: true,
        items: [topQueryPanel],
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
        }]
    });
    
    
    
	var queryMenu = {
        text: '录入查询条件',
        width: 85,
        id: 'query',
        iconCls: 'query',
        handler:function() {
            queryWin.show();
        }
    };
    
    
	var addMenu = {
		text: '新增',
		width: 85,
		iconCls: 'add',
		id:'add',
		handler:function() {
			oprWin.show();
//			oprWin.center();
		}
	};
	
	var updateMenu = {
		text: '修改',
		width: 85,
		iconCls: 'edit',
		id:'update',
		handler:function() {
			if(!grid.getSelectionModel().hasSelection()) {
					showAlertMsg('请选择一条记录',grid);
					return;
				}
				devModifyForm.getForm().reset();
				rec = grid.getSelectionModel().getSelected();
				devModifyForm.getForm().loadRecord(rec);
				
				if(rec.get('aGrade')!='1'){
					devModifyForm.findById('aRate').disable();
					devModifyForm.findById('aNum').disable();
					devModifyForm.findById('aDesc').disable();
				}
				if(rec.get('bGrade')!='2'){
					devModifyForm.findById('bRate').disable();
					devModifyForm.findById('bNum').disable();
					devModifyForm.findById('bDesc').disable();
				}
				if(rec.get('cGrade')!='3'){
					devModifyForm.findById('cRate').disable();
					devModifyForm.findById('cNum').disable();
					devModifyForm.findById('cDesc').disable();
				}
				if(rec.get('dGrade')!='4'){
					devModifyForm.findById('dRate').disable();
					devModifyForm.findById('dNum').disable();
					devModifyForm.findById('dDesc').disable();
				}
				if(rec.get('eGrade')!='5'){
					devModifyForm.findById('eRate').disable();
					devModifyForm.findById('eNum').disable();
					devModifyForm.findById('eDesc').disable();
				}
				
				if(rec.get('aGrade')=='1'){
					devModifyForm.findById('grades').setValue('1');
				}
				if(rec.get('bGrade')=='2'){
					devModifyForm.findById('grades').setValue('2');
				}
				if(rec.get('cGrade')=='3'){
					devModifyForm.findById('grades').setValue('3');
				}
				if(rec.get('dGrade')=='4'){
					devModifyForm.findById('grades').setValue('4');
				}
				if(rec.get('eGrade')=='5'){
					devModifyForm.findById('grades').setValue('5');
				}
				devModifyWin.show();
//			oprWin.center();
		}
	};
    
    
	var menuArr = new Array();
	
	menuArr.push(addMenu);
	menuArr.push('-');	
	menuArr.push(updateMenu);
	menuArr.push('-');	//[1]
	menuArr.push(deleteMenu);
	menuArr.push('-');	
	menuArr.push(queryMenu);	//[0]
	

	// 终端信息列表
	var grid = new Ext.grid.GridPanel({
		title: '持卡人抽奖活动管理',
		iconCls: 'T702',
		region:'center',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: actStore,
		sm: new Ext.grid.RowSelectionModel({singleSelect: true}),
		autoExpandColumn: 'mchtNm',
		cm: actColModel,
		tbar: menuArr,
		clicksToEdit: true,
		forceValidation: true,
		plugins: termRowExpander,
		renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载持卡人抽奖活动信息列表......'
		},
		bbar: new Ext.PagingToolbar({
			store: actStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '显示第{0}-{1}条记录，共{2}条记录',
			emptyMsg: '没有找到符合条件的记录'
		})
	});
	
	actStore.load();
	grid.getTopToolbar().items.items[2].disable();
	grid.getTopToolbar().items.items[4].disable();
	grid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(grid.getView().getRow(grid.getSelectionModel().last)).frame();
			// 根据商户状态判断哪个编辑按钮可用
			rec = grid.getSelectionModel().getSelections();
			if(rec != null ) {
				grid.getTopToolbar().items.items[2].enable();
				grid.getTopToolbar().items.items[4].enable();
			} else {
				grid.getTopToolbar().items.items[2].disable();
				grid.getTopToolbar().items.items[4].disable();
			}
		}
	});
   
	var mainUI = new Ext.Viewport({
		layout: 'border',
		renderTo: Ext.getBody(),
		items:[grid]
	});

})