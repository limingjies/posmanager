

function multiRowEditPopup(popupId,name,limit,txtfeild,initCallback,setDataCallback) {

	var rowItemsPanel = [];
	var rowPanel = null;
	var editForm = null;
	var editWin = null;
	
	var itemCount = 0;
	var itemIdList = [];
	
	var newRowItemFunc = function(){
		var row = null;
		var items = [];
		
		itemCount++;
		
		itemIdList.push('dataPanel_' + (itemCount));
		
		// 时间段的行
		var timeRangePanel = [{
			xtype : 'panel',
			id: 'dataPanel_' + itemCount,
			name:'timeRangeRow',
			layout : 'form',
			items : [{
			    columnWidth : .99,
				layout : 'column',
				items:[{
					layout : 'form',
					items : [{
						xtype : 'timefield',
						name : 'startTime',
						fieldLabel : '开始时间',
						increment: 10,
					    autoShow: true,
					    format: 'H:i',
						width : 120
					}]
				},{
					layout : 'form',
					items : [{
						xtype : 'timefield',
						name : 'endTime',
						fieldLabel : '结束时间',
						increment: 10,
					    autoShow: true,
					    format: 'H:i',
						width : 120
					}]
				}]
			}]
		}];
		
		var addBtnPanel = [{
			xtype : 'panel',
			name:'buttonRow',
			layout : 'column',
			items : [{
				xtype : 'button',
				id: 'addRow_' + itemCount,
				name : 'addRow',
				text : '＋',
				width : 60,
				handler:function() {

					Ext.getCmp("editForm").items.add(newRowItemFunc());
					editForm.doLayout();
					editWin.doLayout();
					
				}
			}]
		}];
		
		var subBtnPanel = [{
			xtype : 'panel',
			name:'buttonRow',
			layout : 'column',
			items : [{
				id: 'subRow_' + itemCount,
				xtype : 'button',
				name : 'subRow',
				text : '－',
				width : 60,
				handler:function(bt) {
					var idNum = this.id.split('_')[1];
					 Ext.getCmp('row_' + idNum).removeAll();
					 // remove id from id list
					 {
						 var retIdList = [];
						 for (var i = 0; i < itemCount; i++) {
							 if ('dataPanel_' + idNum != itemIdList[i]){
								 retIdList.push(itemIdList[i]);
							 }
						 }
						 itemIdList = retIdList;
					 }
					 // counter
					 itemCount--;
				}
			}]
		}];
		
		if (popupId == 'TimeRange') {
			items.push(timeRangePanel);
		}
		

		if (itemCount == 1) {
			items.push(addBtnPanel);
		} else {
			items.push(subBtnPanel);
		}
		
		row = new Ext.Panel({
			id: 'row_' + itemCount,
			name: 'row',
			layout: 'table',
			region: 'center',
			items: items
		});
		
		return row;
	}
	
	if(txtfeild.getValue() == ''){
		rowItemsPanel.push(newRowItemFunc());
	} else {
		var items = txtfeild.getValue().split(',');
		for (var i = 0; i < items.length; i++) {
			rowItemsPanel.push(newRowItemFunc());
		}
	}
	
	rowPanel = new Ext.Panel({
		name: 'row',
		layout: 'form',
		region: 'center',
		items: rowItemsPanel
	});

	editForm = new Ext.form.FormPanel({
			id:'editForm',
			frame : true,
			autoHeight: true,
			autoWidth: true,
			// width: 380,
			// defaultType: 'textfield',
			labelWidth: 100,
			width: 1000,
			height : 430,
			autoScroll : true,
			layout: 'form',
			waitMsgTarget : true,
//			defaults : {
//				bodyStyle : 'padding-left: 10px'
//			},
			items : rowPanel
		});
	
	editForm.getForm().reset();
	
	editWin = new Ext.Window({
		title : name,
		initHidden : true,
		header : true,
		frame : true,
		closable : true,
		modal : true,
		width : 900,
		autoHeight : true,
		layout : 'fit',
		items : [editForm],
		buttonAlign : 'center',
		closeAction : 'close',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			id: 'btnOk',
			handler : function() {
				var retText = '';
				
				if (itemIdList.length > limit) {
					showErrorMsg(name + '选择不能超过' + limit + '个', editForm);
					return;
				}
				
				for (var i = 0; i < itemIdList.length; i++) {
					var row = Ext.getCmp(itemIdList[i]); 
					var rowText = '';
//					row.items.eachKey(function(key,item){
//						if (item.getXType() == 'textfield'){
//							rowText += item.getValue();
//						}
//					});
					var items = row.findByType('timefield');
					if (items[0].getValue() >= items[1].getValue()){
						showErrorMsg('第' + itemIdList[i].split('_')[1] + '行开始时间和结束时间的大小关系不正确！', editForm);
						return;
					}
					for (var j = 0; j < items.length; j++) {
						rowText += items[j].getValue().replace(':', '');
					}
					
					if (retText != ''){
						retText += ',' + rowText;
					} else {
						retText += rowText;
					}
				}
				txtfeild.setValue(retText);
				editWin.hide();
			}
		}, {
			text : '重置',
			name: 'btnReset',
			id: 'btnReset',
			handler : function() {
				
				for (var i = 0; i < itemIdList.length; i++) {
					var row = Ext.getCmp(itemIdList[i]);

					var txtFeilditems = row.findByType('timefield');
					for (var j = 0; j < txtFeilditems.length; j++) {
						txtFeilditems[j].setValue('');
					}
				}
				
				editForm.getForm().reset();
			}
		}]
	});
	
	if(txtfeild.getValue() != ''){
		var items = txtfeild.getValue().split(',');
		for (var i = 0; i < itemIdList.length; i++) {
			var row = Ext.getCmp(itemIdList[i]);
			var start = 0;
			var step = 4;

			var txtFeilditems = row.findByType('timefield');
			for (var j = 0; j < txtFeilditems.length; j++) {
				var textVal = items[i].substr(start, step);
				txtFeilditems[j].setValue(textVal.substr(0,2) + ':' + textVal.substr(2,2));
				start += step;
			}
		}
	}
	
	editWin.show();
	
}