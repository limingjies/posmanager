Ext.onReady(function() {
	
	// 商户状态值集合
	var statusArray = [];
	// 商户状态组
	var checkGroup;
	
	Ext.apply(Ext.form.Checkbox.prototype,{
		listeners: {
			check: function() {
				if(Ext.getCmp('checkboxgroup').validate()) {
					showCmpProcess(checkboxPanel,'正在生成图表，请稍后......');
					statusArray.length = 0;
					checkGroup = Ext.getCmp('checkboxgroup').getValue();
					for(var i = 0; i <  checkGroup.length; i++) {
						statusArray.add(checkGroup[i].inputValue);
					}
					ChartUtilDWR.load('generateMchntStatus',statusArray.toString(),function(ret) {
						if(ret != '-1') {
							store.loadData(Ext.decode(ret));
						} else {
							showErrorMsg('图表加载失败',chartPanel);
						}
					});
					hideCmpProcess(checkboxPanel,1000);
				}
			}
		}
	});
	
	
	// 图表数据集
	var store = new Ext.data.JsonStore({
		fields: ['status','number']
	});
	
	// 复选框面板
	var checkboxPanel = new Ext.Panel({
		title: '商户信息状态对比',
		region: 'north',
		frame: true,
		height:50,
//		autoHeight: true,
		layout: 'hbox',
		items: {
			xtype: 'checkboxgroup',
			allowBlank: false,
			blankText: '请至少选择一个商户状态',
			id: 'checkboxgroup',
			items: [{
				xtype: 'checkbox',
				boxLabel: '正常',
				inputValue: '0',
				id: 'chk0',
				name: 'chk0',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '1',
				boxLabel: '添加待审核',
				id: 'chk1',
				name: 'chk1',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '2',
				boxLabel: '添加审核退回',
				id: 'chk2',
				name: 'chk2',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '3',
				boxLabel: '修改待审核',
				id: 'chk3',
				name: 'chk3',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '4',
				boxLabel: '修改审核退回',
				id: 'chk4',
				name: 'chk4',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '5',
				boxLabel: '冻结待审核',
				id: 'chk5',
				name: 'chk5',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '6',
				boxLabel: '冻结',
				id: 'chk6',
				name: 'chk6',
				checked: true,
				width: 120
			},{
				xtype: 'checkbox',
				inputValue: '7',
				boxLabel: '恢复待审核',
				id: 'chk7',
				name: 'chk7',
				checked: true,
				width: 120
			}]
		}
	});
	
	// 图表面板
	var chartPanel = new Ext.Panel({
		region: 'center',
		frame: true,
		layout: 'fit',
		width: Ext.getBody().getWidth(),
		items: new Ext.chart.ColumnChart({
			store: store,
			xField: 'status',
			yField: 'number',			
			yAxis: new Ext.chart.NumericAxis({
				majorUnit: 1
			}),
			chartStyle: {
				dataTip: {
					font: {
						name: '宋体',
						size: 15,
						bold: true,
						color: 0x6CA6CD
					}
				},
				font: {
					color: 0x6B6B6B,
					name: '宋体',
					size : 12
				},
				xAxis: {
					color: 0x99bbe8,
					majorTicks: {
						color : 0x99bbe8,
						length: 4
					},
					minorTicks: {
						color: 0x99bbe8,
						length: 2
					},
					majorGridLines: {
						size: 1,
						color: 0x99bbe8
					}
				},
				yAxis: {
					color: 0x99bbe8,
					majorTicks: {
						color : 0x99bbe8,
						length: 4
					},
					minorTicks: {
						color: 0x99bbe8,
						length: 2
					},
					majorGridLines: {
						size: 1,
						color: 0x99bbe8
					}
				}
			}
		}),
		listeners: {
			render: function() {
				ChartUtilDWR.load('generateMchntStatus','0,1,2,3,4,5,6,7',function(ret) {
					if(ret != '-1') {
						store.loadData(Ext.decode(ret));
					} else {
						showErrorMsg('图表加载失败',chartPanel);
					}
				});
			}
		}
	});
	
	
	var view = new Ext.Viewport({
		title: '商户信息状态对比',
		frame: true,
		renderTo: Ext.getBody(),
		layout: 'border',
		items: [checkboxPanel,chartPanel]
	})
});