Ext.onReady(function() {
	
	var curDate = new Date();
	var preDate = new Date(curDate.getTime() - 24*60*60*1000);  
 
	//获取当前日期的前一天
	var timeYesterday = preDate.format("Y") +"-" +preDate.format("m") + "-"+(preDate.format("d"));//昨天
	
	
	var txnGridStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=bthChkInfo'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount'
		},[
			{name: 'settlmtDate',mapping: 'settlmtDate'},
			{name: 'brhId',mapping: 'brhId'},
			{name: 'createNewNo',mapping: 'createNewNo'},
			{name: 'brhName',mapping: 'brhName'},
			{name: 'mchtId',mapping: 'mchtId'},
			{name: 'mchtName',mapping: 'mchtName'},
			{name: 'pospPan',mapping: 'pospPan'},
			{name: 'instPan',mapping: 'instPan'},
			{name: 'pospAmtTrans',mapping: 'pospAmtTrans'},
			{name: 'instAmtTrans',mapping: 'instAmtTrans'},
			{name: 'mchtFee',mapping: 'mchtFee'},
			{name: 'insAllot',mapping: 'insAllot'},
			{name: 'transDate',mapping: 'transDate'},
			{name: 'transTime',mapping: 'transTime'},
			{name: 'instTransDate',mapping: 'instTransDate'},
			{name: 'instTransTime',mapping: 'instTransTime'},
			{name: 'seqNum',mapping: 'seqNum'},
			{name: 'refNum',mapping: 'refNum'},
			{name: 'instRefNum',mapping: 'instRefNum'},
			{name: 'checkResult',mapping: 'checkResult'},
			{name: 'stlmFlg',mapping: 'stlmFlg'},
			{name: 'keyInst',mapping: 'keyInst'},
			{name: 'ajustStatus',mapping: 'ajustStatus'},
			{name: 'ajustUsr',mapping: 'ajustUsr'},
			{name: 'ajustTime',mapping: 'ajustTime'},
			{name: 'vcTranNo',mapping: 'vcTranNo'},
			{name: 'adjustMode',mapping: 'adjustMode'},
			{name: 'adjustMsg',mapping: 'adjustMsg'}
		]),
		autoLoad: true
	});
	/*txnGridStore.load({
		params:{start: 0}
	});*/
	
	
	
	
	var txnColModel = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer(),
		new Ext.grid.CheckboxSelectionModel({
			singleSelect : false,
			 renderer : function(v, p, record){
				 var checkResult = record.data['checkResult'];
				 var selAjustStatus = record.data['ajustStatus'];
		          if ('不平' == checkResult && '未调账' == selAjustStatus){  
		               return '<div class="x-grid3-row-checker">&#160;</div>';  
		          }  
		          return '';  
		      }	      
			}),
		{header: '清算日期',dataIndex: 'settlmtDate',align: 'center',width: 90},
		{header: '合作伙伴编号',dataIndex: 'brhId',hidden:true,align: 'center',width: 100},
		{header: '合作伙伴号',dataIndex: 'createNewNo',align: 'left',width: 100},
		{header: '合作伙伴名称',dataIndex: 'brhName',align: 'left',width: 150},
		{header: '商户号',dataIndex: 'mchtId',align: 'center',width: 130},
		{header: '商户名称',dataIndex: 'mchtName',align: 'left',width: 200},
		{header: '收单卡号',dataIndex: 'pospPan',align: 'center',width: 150},
		{header: '通道卡号',dataIndex: 'instPan',align: 'center',width: 150},
		{header: '收单交易金额',dataIndex: 'pospAmtTrans',align: 'right',width: 100},
		{header: '通道交易金额',dataIndex: 'instAmtTrans',align: 'right',width: 100},
		{header: '商户手续费',dataIndex: 'mchtFee',align: 'right',width: 100},
		{header: '合作伙伴分润佣金',dataIndex: 'insAllot',align: 'right',width: 120},
		{header: '交易日期',dataIndex: 'transDate',align: 'center',width: 90},
		{header: '交易时间',dataIndex: 'transTime',align: 'center',width: 90},
		{header: '渠道交易日期',dataIndex: 'instTransDate',align: 'center',width: 90},
		{header: '渠道交易时间',dataIndex: 'instTransTime',align: 'center',width: 90},
		{header: '流水号',dataIndex: 'seqNum',align: 'center',width: 100},
		{header: '参考号',dataIndex: 'refNum',align: 'center',width: 150},
		{header: '渠道参考号',dataIndex: 'instRefNum',align: 'center',width: 150},
		{header: '对账状态',dataIndex: 'checkResult',align: 'center',width: 80},
		{header: '不平（存疑）类型',dataIndex: 'stlmFlg',align: 'center',width: 150},
		{header: '主键',dataIndex: 'keyInst',align: 'center',width: 150, hidden: true},
		{header: '调账状态',dataIndex: 'ajustStatus',align: 'center',width: 150},
		{header: '调账方式',dataIndex: 'adjustMode',align: 'center',width: 150},
		{header: '调账人',dataIndex: 'ajustUsr',align: 'center',width: 150},
		{header: '调账时间',dataIndex: 'ajustTime',align: 'center',width: 150,renderer:formatDt},
		{header: '交易流水号',dataIndex: 'vcTranNo',align: 'center',width: 150},
		{header: '操作',dataIndex: 'adjustMode',align: 'center',renderer: function (val){ 
			if(val == '人工处理'){
				return "<button type='button' onclick=''>查看处理结果</button>";
			}else{
				return '';
			}
		},width: 120}
	]);
	/************************************************人工处理窗口************************************************************/
	var imgBatchId = 0;
	var selRecord = null;
	//文件上传窗口
	var	dialog = null;
	 dialog = new UploadDialog({
		uploadUrl : 'T80732Action.asp',
		filePostName : 'imgFile',
		flashUrl : Ext.contextPath + '/ext/upload/swfupload.swf',
		fileSize : '5 MB',
		fileTypes : '*.jpg',
		fileTypesDescription : '图片文件(大小不超过5M)',
		title: '人工调账',
		scope : this,
		animateTarget: 'upload',
		onEsc: function() {
			this.hide();
		},
		exterMethod: function() {
			
		},
		completeMethod: function() {
			//oprGrid.getStore().reload();
		},
		onUploadSuccess : function(file, serverData){			
			hideMask();
			var thiz = dialog;
			thiz.progressInfo.filesUploaded += 1;			
			if(Ext.util.JSON.decode(serverData).success){
				var record = thiz.fileList.getStore().getById(Ext.getDom('fileId_' + file.id).parentNode.id);
				record.set('fileState',file.filestatus);
				record.commit();
				Ext.getCmp('uploadId').setText('<font color="red">已上传</font>');
			} else {
				Ext.getCmp('UploadDialog').stopUpload();
				var record = thiz.fileList.getStore().getById(Ext.getDom('fileId_' + file.id).parentNode.id);
				record.set('fileState',-3);
				record.commit();
				thiz.progressInfo.filesTotal -= thiz.progressInfo.filesUploaded;
				this.settings.post_params['fileNum'] = thiz.progressInfo.filesTotal;
				Ext.MessageBox.show({
					msg: Ext.util.JSON.decode(serverData).msg,
					title: '错误提示',
					buttons: Ext.MessageBox.OK,
					icon: Ext.MessageBox.ERROR,
					modal: true,
					width: 250
				});
			}
			thiz.updateProgressInfo();
		}
	});
    var formHandleAdjust = new Ext.form.FormPanel({
		frame : true,
		width: 400,
		height: 200,
		labelWidth : 80,
		layout : 'form',
       //	waitMsgTarget : true,
       	items : [
   	        {
				xtype: 'textarea',
				editable: true,
				vtype:'length100B',
				allowBlank:false,
				fieldLabel : '处理内容<font color="red">*</font>',
				id : 'solveMsgId',
				width : 250
			},{
				xtype: 'button',
				fieldLabel : '上传附件',
				text:'上传图片',
				width : 80,
				id:'uploadId',
				handler :function(){
					dialog.postParams={
						method:'upload',
						txnId: '80732',
						subTxnId: '02',
						mchtIds :txnGrid.getSelectionModel().getSelections()[0].get('mchtId'),
					 	settlDates : txnGrid.getSelectionModel().getSelections()[0].get('settlmtDate'),
					 	keyInsts : txnGrid.getSelectionModel().getSelections()[0].get('keyInst'),
					 	imgBatchId : imgBatchId
					};
					dialog.refreshParams();
					dialog.show();
				}
			}]
    	});
    
	var winHandleAdjust = new Ext.Window({
		title : '添加手工差错调账信息',
		//initHidden : true,
		header : true,
		frame : true,
		closable : true,
		//modal : true,
		width : 400,
		items : [formHandleAdjust],
		buttonAlign : 'center',
		closeAction : 'hide',
		iconCls : 'logo',
		resizable : false,
		buttons : [{
			text : '确定',
			id: 'btnSaveHandle',
			handler : function() {
				 var records =  txnGrid.getSelectionModel().getSelections();
				 var mchtIds = records[0].get('mchtId');
				 var settlDates = records[0].get('settlmtDate');
				 var keyInsts = records[0].get('keyInst');
				 var mchtNms = records[0].get('mchtName');
				 var transDates = records[0].get('transDate');
				 var transTimes = records[0].get('transTime');
				 var transAmounts = records[0].get('pospAmtTrans');		
				 var solveMsg = Ext.getCmp('solveMsgId').getValue();

				 var instAmtTrans = records[0].get('instAmtTrans');//通道交易金额
				 var instTransDate = records[0].get('instTransDate');//渠道交易日期
				 var instTransTime = records[0].get('instTransTime');//渠道交易时间
				 
				 if(solveMsg == null || solveMsg.trim() == ''){
					 Ext.MessageBox.show({
							msg: '处理内容不可为空！',
							title: '错误提示',
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR,
							modal: true,
							width: 250
						});
					 return ;
				 }else if(Checkstrlenght(solveMsg)>100){
					 Ext.MessageBox.show({
							msg: '处理内容为100个字符（汉字50）以内！',
							title: '错误提示',
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR,
							modal: true,
							width: 250
						});
					 return ;
				 }
				 showConfirm('确定要对所选交易进行调账吗？', txnGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						showMask("正在处理中，请稍后......", txnGrid);
						Ext.Ajax.request({
							url : 'T80732Action.asp?method=handle',
							params : {
								mchtIds : mchtIds,
								settlDates : settlDates,
								keyInsts : keyInsts,
								mchtNms : mchtNms,
								transDates : transDates,
								transTimes : transTimes,
								transAmounts : transAmounts,
								solveMsg : solveMsg,
								imgBatchId : imgBatchId,
								instAmtTrans : instAmtTrans,
								instTransDate : instTransDate,
								instTransTime : instTransTime,
								txnId : '80732',
								subTxnId : '02'
							},
							success : function(rsp, opt) {
								hideMask();
								var rspObj = Ext.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessAlert(rspObj.msg, txnGrid,1500);
								} else {
									showErrorMsg(rspObj.msg, txnGrid,1500);
								}
								winHandleAdjust.hide();
								txnGridStore.reload();
								if(dialog.fileList.getStore().data.length > 0){
									dialog.clearList();
								}
								
							},
							failure : function(){
								hideMask();
								Ext.MessageBox.show({
									msg: '信息保存失败！',
									title: '错误提示',
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR,
									modal: true,
									width: 250
								});
							}
						});
					}
				})				
			}
		},{
			text : '关闭',
			id: 'btnClose',
			handler : function() {
				winHandleAdjust.hide();
			}
		}]
	});
	//=========查看详情============

	var storeImg1 = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: 'gridPanelStoreAction.asp?storeId=getAdjustErrTrad'
		}),
		reader: new Ext.data.JsonReader({
			root: 'data',
			totalProperty: 'totalCount',
			idProperty: 'fileName'
		},[
			//{name: 'id',mapping: 'id'},
			{name: 'btBig',mapping: 'btBig'},
			//{name: 'btDel',mapping: 'btDel'},
			{name: 'width',mapping: 'width'},
			{name: 'height',mapping: 'height'},
			{name: 'fileName',mapping: 'fileName'}
		])
	});


	var formHandleDetail = new Ext.form.FormPanel({
			frame : true,
			width: 400,
			height: 200,
			labelWidth : 80,
			layout : 'form',
	       	items : [
	   	        {
					xtype: 'textarea',
					fieldLabel : '处理内容<font color="red">*</font>',
					id : 'solveMsgDtlId',
					readOnly:true,
					width : 250
				},{
					xtype: 'button',
					fieldLabel:'图片信息',
					width: '150',
					text: '查看影像',
					id: 'btImgShow',
					handler:function() {
						storeImg1.removeAll();
			    		storeImg1.load({
			    			params:{
			    				start: 0,
			    				settleDate : selRecord.get('settlmtDate'),
			    				keyInst : selRecord.get('keyInst')
			    			},callback: function(records, options, success){
			    				if(success){
			    					var settleDate = selRecord.get('settlmtDate');
				    				var keyInst = selRecord.get('keyInst');
				    				var path = settleDate.replace(/-/g,'') + '_' + keyInst ;
			    					var record = storeImg1.getAt(0);
			    					if (record && record.data &&  record.data.fileName) {
			    						var imgSrc = Ext.contextPath + '/PrintImage?func=adjustErrTrad&fileName='+ path + '/' + record.data.fileName;
			    						imgBathPathDesc = 'adjustErrTrad';
			    						pageInited = false;
			    						showImage(imgSrc,path);
			    					} else {
			    						Ext.Msg.alert("提示","没有找到相关图片信息。");
			    					}
			    				}		
			    			}	
			    		});
					}
				}]
	    	});

		var winHandleDetail = new Ext.Window({
			title : '手工差错调账信息',
			//initHidden : true,
			header : true,
			frame : true,
			closable : true,
			//modal : true,
			width : 400,
			items : [formHandleDetail],
			buttonAlign : 'center',
			closeAction : 'hide',
			iconCls : 'logo',
			resizable : false,
			buttons : [{
				text : '关闭',
				id: 'btnCloseDtl',
				handler : function() {
					winHandleDetail.hide();
				}
			}]
		});
    function cellclick(grid, rowIndex, columnIndex, e) {
    	//这里得到的是一个对象，即你点击的某一行的一整行数据
        var filedName = grid.getColumnModel().getDataIndex(columnIndex);    
        var record = grid.getStore().getAt(rowIndex);
        var settleDate = record.data.settlmtDate;    
        var keyInst = record.data.keyInst;  
        var adjustMode = record.data.adjustMode;
        var adjustMsg = record.data.adjustMsg;
        selRecord = record;
        if(columnIndex == 28 && adjustMode == '人工处理'){
    		// 显示审核窗口
    		winHandleDetail.show();
    		// 设置值
    		Ext.getCmp('solveMsgDtlId').setValue(adjustMsg);
    		
        }
	}
 
	function ajustStatusName(val) {
        if(val == '0')
            return "<font color='blue'>未调账</font>";
        if(val == '1')
            return "<font color='blue'>已调账</font>";
        return val;
    }
	
	function instCode(val) {
        if(val == '0001')
            return "<font color='blue'>"+val+"-中信</font>";
        if(val == '0002')
            return "<font color='blue'>"+val+"-银生宝</font>";
        if(val == '0003')
            return "<font color='blue'>"+val+"-轩宸</font>";
        if(val == '0004')
            return "<font color='blue'>"+val+"-聚财通</font>";
        return val;
    }

//	function stlmFlag(val) {
//        if(val == '1')
//            return "本地<font color='green'>成功</font>，通道机构<font color='red'>失败</font>";
//        if(val == '2')
//            return "本地<font color='red'>失败</font>，通道机构<font color='green'>成功</font>";
//        if(val == '3')
//            return "本地与通道机构金额不一致";
//        return val;
//    }
    
	  var tbar1 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	       			'-','清算日期：',{
			       		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					endDateField: 'endDate',
						value:timeYesterday,
						editable: false,
						id: 'startDateSettlmt',
						name: 'startDateSettlmt',
						width: 120
		           	},'~',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					startDateField: 'startDate',
						value:timeYesterday,
						editable: false,
						id: 'endDateSettlmt',
						name: 'endDateSettlmt',
						width: 120
		           	},'-','合作伙伴：',{
						xtype : 'dynamicCombo',
						methodName : 'getBrhId',
						id: 'idqueryBrhId',
						hiddenName: 'queryBrhId',
					    editable: true,
					    mode:'local',
						triggerAction: 'all',
						forceSelection: true,
						selectOnFocus: true,
						lazyRender: true,
						width: 250
					},'-','商户号：',{
						xtype: 'dynamicCombo',
						methodName: 'getMchntNoAll',
						fieldLabel: '商户编号',
						id: 'idqueryMchtId',
						hiddenName: 'queryMchtId',
						editable: true,
						width: 300
					}]  
	 }); 
	
	  var tbar2 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	       			'-','交易日期：',{
			       		xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					endDateField: 'endDate',
						value:timeYesterday,
						editable: false,
						id: 'startDateTrans',
						name: 'startDateTrans',
						width: 120
		           	},'~',{
						xtype: 'datefield',
						format: 'Y-m-d',
						altFormats: 'Y-m-d',
	//					vtype: 'daterange',
	//					startDateField: 'startDate',
						value:timeYesterday,
						editable: false,
						id: 'endDateTrans',
						name: 'endDateTrans',
						width: 120
		           	},'-','流水号：',{
						xtype: 'textfield',
						id: 'querySeqNum',
						vtype: 'isNumber',
						maxLength:6,
						width: 170
					},'-','参考号：',{
						xtype: 'textfield',
						id: 'queryRefNum',
						vtype: 'isNumber',
						maxLength:12,
						width: 170
					},'-','卡号：',{
						xtype: 'textfield',
						id: 'queryCardNum',
						vtype: 'isNumber',
						maxLength:12,
						width: 174
					}]  
	 }); 
	  
		
	  var tbar3 = new Ext.Toolbar({  
	        renderTo: Ext.grid.EditorGridPanel.tbar,  
	        items:[
	               '-','对账状态：',{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','对平'],['1','不平'],['2','存疑']],
							reader: new Ext.data.ArrayReader()
						}),
						listeners: {
		                    'select': function(){
		                    	if(this.getValue() !='0'){
		                    		Ext.getCmp('idTypeLabel').show();
									
									if (this.getValue() =='1') {
										Ext.getCmp('idTypeLabel').setText("不平类型：");
										Ext.getCmp('idCheckResultType').show();
										Ext.getCmp('idCheckResultType1').hide();
										Ext.getCmp('idCheckResultType1').setValue('');
										Ext.getCmp('idAjustStatusLabel').show();
										Ext.getCmp('idAjustStatus').show();
									} else {
										Ext.getCmp('idTypeLabel').setText("存疑类型：");
										Ext.getCmp('idCheckResultType').hide();
										Ext.getCmp('idCheckResultType').setValue('');
										Ext.getCmp('idCheckResultType1').show();

										Ext.getCmp('idAjustStatusLabel').hide();
										Ext.getCmp('idAjustStatus').hide();
										Ext.getCmp('idAjustStatus').setValue('');
									}
		                    	}else{
		                    		Ext.getCmp('idTypeLabel').hide();
									Ext.getCmp('idCheckResultType').hide();
									Ext.getCmp('idCheckResultType1').hide();
									Ext.getCmp('idCheckResultType').setValue('');
									Ext.getCmp('idCheckResultType1').setValue('');

									Ext.getCmp('idAjustStatusLabel').hide();
									Ext.getCmp('idAjustStatus').hide();
									Ext.getCmp('idAjustStatus').setValue('');
									
		                    	}
		                    }
						},
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'checkFlag',
						editable: false,
						value:0,
						//emptyText: '请选择',
						id:'idcheckFlag',
						allowBlank: false,
						autoSelect: true,
						width: 120
					},'-',{
						xtype:'label',
						id:'idTypeLabel',
						hiddenName: 'typeLabel',
						text:'不平类型：',
						hidden:true
					},{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','POSP成功，渠道失败'],['2','POSP失败，渠道成功'],['3','POSP与渠道金额不一致']],
							reader: new Ext.data.ArrayReader()
						}),
						listeners: {
		                    /*'select': function(){
		                    	if(this.getValue() =='1'){
									Ext.getCmp('idAjustStatusLabel').show();
									Ext.getCmp('idAjustStatus').show();
		                    	} else {
									Ext.getCmp('idAjustStatusLabel').hide();
									Ext.getCmp('idAjustStatus').hide();
									Ext.getCmp('idAjustStatus').setValue('');
		                    	}		                    	
		                    }*/
						},
						editable: true,
						hidden:true,
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'checkResultType',
						id:'idCheckResultType',
						emptyText: '请选择',
						width: 150
					},{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['1','POSP有，渠道无'],['2','POSP无，渠道有']],
							reader: new Ext.data.ArrayReader()
						}),
						editable: true,
						hidden:true,
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'checkResultType1',
						id:'idCheckResultType1',
						emptyText: '请选择',
						width: 150
					},'-',{
						xtype:'label',
						id:'idAjustStatusLabel',
						text:'调账状态：',
						hidden:true
					},{
						xtype: 'combo',
						store: new Ext.data.ArrayStore({
							fields: ['valueField','displayField'],
							data: [['0','未调账'],['1','已调账']],
							reader: new Ext.data.ArrayReader()
						}),
						editable: true,
						hidden:true,
						displayField: 'displayField',
						valueField: 'valueField',
						hiddenName: 'ajustStatus',
						id:'idAjustStatus',
						emptyText: '请选择',
						width: 120
					}]  
	 }); 
	  
	var txnGrid = new Ext.grid.EditorGridPanel({
		iconCls: 'T104',
		region:'center',
//		autoExpandColumn:'txnName',
		frame: true,
		border: true,
		columnLines: true,
		stripeRows: true,
		store: txnGridStore,
		sm: new Ext.grid.CheckboxSelectionModel({
			singleSelect: false,
			//checkOnly:true,
			/*selectAll : function() {
				if (this.isLocked()) {
					return
				}
				this.selections.clear();
				for (var b = 0, a = this.grid.store.getCount(); b < a; b++) {
    				 var selStlmFlg = this.grid.store.getAt(b).data['stlmFlg'];
    				 var selAjustStatus = this.grid.store.getAt(b).data['ajustStatus'];
    				 var checkResult = this.grid.store.getAt(b).data['checkResult'];
                      if ('不平' == checkResult && '未调账' == selAjustStatus){  
                           this.selectRow(b, true);  
                      }  					
				}
			},*/
/*			handleMouseDown : function(d, i, h) {
				 var selStlmFlg = this.grid.store.getAt(i).data['stlmFlg'];
				 var selAjustStatus = this.grid.store.getAt(i).data['ajustStatus'];
				 var checkResult = this.grid.store.getAt(i).data['checkResult'];
                 if ('不平' == checkResult && '未调账' == selAjustStatus){  
                      this.selectRow(b, true);  
                 }  					
				if (h.button !== 0 || this.isLocked()) {
					return
				}
				var a = this.grid.getView();
				if (h.shiftKey && !this.singleSelect
						&& this.last !== false) {
					var c = this.last;
					this.selectRange(c, i, h.ctrlKey);
					this.last = c;
					a.focusRow(i)
				} else {
					var b = this.isSelected(i);
					//if (h.ctrlKey && b) {
					if (b) {
						this.deselectRow(i)
					} else {
						if (!b || this.getCount() > 1) {
							//this.selectRow(i, h.ctrlKey || h.shiftKey);
							this.selectRow(i, true);//不按ctrl键也能多选
							a.focusRow(i)
						}
					}
				}
			}*/
			listeners:{
				'beforerowselect':function(thiz,i,b,rec){
					var selStlmFlg = rec.data['stlmFlg'];
   				 	var selAjustStatus = rec.data['ajustStatus'];
   				 	var checkResult = rec.data['checkResult'];
                     if ('不平' == checkResult && '未调账' == selAjustStatus){  
                          return true;  
                     } else{
                    	 return false;
                     }
				}
			}
		}),
		cm: txnColModel,
		clicksToEdit: true,
		forceValidation: true,
        renderTo: Ext.getBody(),
		loadMask: {
			msg: '正在加载交易信息列表......'
		},
		tbar: [{
			xtype: 'button',
			text: '下载报表',
			name: 'download',
			id: 'download',
			iconCls: 'download',
			width: 80,
			handler:function() {
				showMask("正在为您准备报表，请稍后。。。",txnGrid);
				if (txnGridStore.getTotalCount() == 0) {
					hideMask();
					showErrorMsg("没有找到符合条件的记录，无法生成报表",txnGrid);
					return;
				}
				if (txnGridStore.getTotalCount() < System[REPORT_MAX_COUNT]){
				Ext.Ajax.request({
					url: 'T80731Action.asp',
					timeout: 60000,
					params: {
						start: 0,
						startDateSettlmt: typeof(Ext.getCmp('startDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('startDateSettlmt').getValue().format('Ymd'),
						endDateSettlmt: typeof(Ext.getCmp('endDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('endDateSettlmt').getValue().format('Ymd'),
						startDateTrans: typeof(Ext.getCmp('startDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('startDateTrans').getValue().format('Ymd'),
						endDateTrans: typeof(Ext.getCmp('endDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('endDateTrans').getValue().format('Ymd'),
						queryMchtId: Ext.get('queryMchtId').getValue(),
						queryBrhId: Ext.get('queryBrhId').getValue(),
						querySeqNum: Ext.get('querySeqNum').getValue(),
						queryRefNum: Ext.get('queryRefNum').getValue(),
						checkFlag: Ext.get('checkFlag').getValue(),
						queryResultType: Ext.get('checkResultType').getValue(),
						queryResultType1: Ext.get('checkResultType1').getValue(),
						queryCardNum: Ext.get('queryCardNum').getValue(),
						ajustStatus: Ext.get('ajustStatus').getValue(),
						
						txnId: '80731',
						subTxnId: '01'
					},
					success: function(rsp,opt) {
						hideMask();
						var rspObj = Ext.decode(rsp.responseText);
						if(rspObj.success) {
							window.location.href = Ext.contextPath + '/ajaxDownLoad.asp?path='+
														rspObj.msg;
						} else {
							showErrorMsg(rspObj.msg,txnGrid);
						}
					},
					failure: function(){
						hideMask();
						showErrorMsg(rspObj.msg,txnGrid);
					}
				});
//				txnGridStore.load();
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
			}},'-',{
				xtype: 'button',
				text: '查询',
				name: 'query',
				id: 'query',
				iconCls: 'query',
				width: 80,
				handler:function() {
					txnGridStore.load();
					Ext.getCmp('ajust').setDisabled(true);
					Ext.getCmp('handle').setDisabled(true);
					clearCheckGridHead();
				    }
			},'-',{
				xtype: 'button',
				text: '重置',
				name: 'reset',
				id: 'reset',
				iconCls: 'reset',
				width: 80,
				handler:function() {
					Ext.getCmp('startDateSettlmt').setValue(timeYesterday);
					Ext.getCmp('endDateSettlmt').setValue(timeYesterday);
					Ext.getCmp('startDateTrans').setValue(timeYesterday);
					Ext.getCmp('endDateTrans').setValue(timeYesterday);
					Ext.getCmp('idqueryMchtId').setValue('');
					Ext.getCmp('idqueryBrhId').setValue('');
					Ext.getCmp('querySeqNum').setValue('');
					Ext.getCmp('queryRefNum').setValue('');
					Ext.getCmp('queryCardNum').setValue('');
					Ext.getCmp('idcheckFlag').setValue('0');
					Ext.getCmp('idCheckResultType').setValue('');
					Ext.getCmp('idAjustStatus').setValue('');
					Ext.getCmp('idTypeLabel').hide();
					Ext.getCmp('idCheckResultType').hide();
					Ext.getCmp('idCheckResultType1').hide();
					Ext.getCmp('idAjustStatusLabel').hide();
					Ext.getCmp('idAjustStatus').hide();
					txnGridStore.load({
						params:{start: 0}
					});
					clearCheckGridHead();
				}	
		},'-',{
			xtype: 'button',
			text: '调账',
			name: 'ajust',
			id: 'ajust',
			iconCls: 'ajust',
			width: 80,
			disabled:true,
			handler:function() {
				var mchtIds = "";
				var settlDates = "";
				var keyInsts = "";
				
				var mchtNms = "";
				var transDates = "";
				var transTimes = "";
				var transAmounts = "";

				var records =  txnGrid.getSelectionModel().getSelections();
				var len = records.length;
				var confirmMsg = '确定要对所选交易进行调账吗？';
				for (var i = 0; i < len; i++) {
					 var selStlmFlg = records[i].data['stlmFlg'];
					 if("POSP成功，渠道失败" != selStlmFlg){
						 //20160408 guoyu update 提示，不进行调账
//						 confirmMsg = '您选择了“posp成功渠道失败”以外的记录，该类型不能进行系统调账，请确认！';
//						 continue;
						Ext.Msg.alert('提示信息：','您选择了“posp成功渠道失败”以外的记录，该类型不能进行系统调账，请确认！');
						return;
					 }
					 var mchtId=records[i].get('mchtId');
					 var settlDate=records[i].get('settlmtDate');
					 var keyInst=records[i].get('keyInst');

					 var mchtName=records[i].get('mchtName');
					 var transDate=records[i].get('transDate');
					 var transTime=records[i].get('transTime');
					 var pospAmtTrans=records[i].get('pospAmtTrans');

					 if(mchtIds == ""){
						 mchtIds += mchtId;
						 settlDates += settlDate;
						 keyInsts += keyInst;
						 
						 mchtNms += mchtName;
						 transDates += transDate;
						 transTimes += transTime;
						 transAmounts += pospAmtTrans;
						 
					 }else{							 
						 mchtIds += '###' + mchtId ;
						 settlDates += '###' + settlDate ;
						 keyInsts += '###' + keyInst ;

						 mchtNms += '###' + mchtName ;
						 transDates += '###' + transDate ;
						 transTimes += '###' + transTime ;
						 transAmounts += '###' + pospAmtTrans ;
					 }
				  }
				if(len < 1){
					Ext.Msg.alert('提示信息：','    请选择需要调账的交易   ');
					return;
				}
				
				showConfirm(confirmMsg, txnGrid, function(bt) {
					// 如果点击了提示的确定按钮
					if (bt == "yes") {
						showMask("正在处理中，请稍后......", txnGrid);
						Ext.Ajax.request({
							url : 'T80732Action.asp?method=adjust',
							params : {
								mchtIds : mchtIds,
								settlDates : settlDates,
								keyInsts : keyInsts,
								mchtNms : mchtNms,
								transDates : transDates,
								transTimes : transTimes,
								transAmounts : transAmounts,
								txnId : '80732',
								subTxnId : '01'
							},
							success : function(rsp, opt) {
								hideMask();
								var rspObj = Ext
										.decode(rsp.responseText);
								if (rspObj.success) {
									showSuccessAlert(rspObj.msg, txnGrid,1500);

								} else {
									showErrorMsg(rspObj.msg, txnGrid,1500);
								}
								txnGridStore.reload();
							},
							failure : function(){
								hideMask();
								Ext.MessageBox.show({
									msg: '信息保存失败！',
									title: '错误提示',
									buttons: Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR,
									modal: true,
									width: 250
								});
							}
						});
					}
				})				
			    }
			},'-',{
				xtype: 'button',
				text: '人工处理',
				name: 'handle',
				id: 'handle',
				iconCls: 'ajust',
				width: 80,
				disabled:true,
				handler:function() {
					var records =  txnGrid.getSelectionModel().getSelections();
					if(records.length > 1){
						Ext.Msg.alert('提示信息：','    每次仅且可以处理<font color="red">一条不平</font>交易！   ');
						return;
					}
					if(records.length < 1){
						Ext.Msg.alert('提示信息：','    请选择需要调账的一条<font color="red">不平</font>交易！   ');
						return;
					}
					if("不平" != records[0].get('checkResult').trim()){ //非不平
						Ext.Msg.alert('提示信息：','    请选择需要调账的<font color="red">不平</font>的交易！  ');
						return;
					}
					winHandleAdjust.show();
					formHandleAdjust.getForm().reset();
					Ext.getCmp('uploadId').setText('上传图片');
					imgBatchId = Math.round(Math.random() * 1000);
					
				}
			}],
		listeners : {     //將第二個bar渲染到tbar裏面，通过listeners事件  
            'render' : function() {  
		    	  	tbar1.render(this.tbar);
		    	  	tbar2.render(this.tbar);
		    	  	tbar3.render(this.tbar);
                },
            'cellclick':cellclick
        },
		bbar: new Ext.PagingToolbar({
			store: txnGridStore,
			pageSize: System[QUERY_RECORD_COUNT],
			displayInfo: true,
			displayMsg: '共{2}条记录',
			emptyMsg: '没有找到符合条件的记录',
            listeners : {  
                "beforechange" : function(bbar, params){  
                	clearCheckGridHead();
                }  
            }  			
		})
	});
	
	 function clearCheckGridHead(){
		  var hd_checker = txnGrid.getEl().select('div.x-grid3-hd-checker');  
		     var hd = hd_checker.first(); 
		     if(hd != null){ 
	                //清空表格头的checkBox  
	             if(hd.hasClass('x-grid3-hd-checker-on')){
	                 hd.removeClass('x-grid3-hd-checker-on');     
	                 //x-grid3-hd-checker-on
	             }		    	 
                 txnGrid.getSelectionModel().clearSelections();
//	             if(txnGrid.getSelectionModel().getSelections().length != 
//		    	  txnGrid.getStore().getCount()){ 
//
//		         }else{
//		             if(txnGrid.getStore().getCount() == 0){ //没有记录的话清空;
//		              return;
//		             }
//		             hd.addClass('x-grid3-hd-checker-on');
//		             txnGrid.getSelectionModel().selectAll();
//		         }
		    }
		 }
	 
	txnGrid.getSelectionModel().on({
		'rowselect': function() {
			//行高亮
			Ext.get(txnGrid.getView().getRow(txnGrid.getSelectionModel().last)).frame();
			
			var records =  txnGrid.getSelectionModel().getSelections();
			var len = records.length;
			if (len == 0) {
				Ext.getCmp('ajust').setDisabled(true);
				Ext.getCmp('handle').setDisabled(true);
			} else {
				Ext.getCmp('handle').setDisabled(false);
				var count = 0;
				for(var i=0;i<len;i++){
					var selStlmFlg = records[i].data['stlmFlg'];
					if("POSP成功，渠道失败" == selStlmFlg){
						count ++;
					}
				}
				if(count > 0){
					Ext.getCmp('ajust').setDisabled(false);
				}else{
					Ext.getCmp('ajust').setDisabled(true);
				}
			}
		},
		'rowdeselect':function() {
			var records =  txnGrid.getSelectionModel().getSelections();
			var len = records.length;
			if (len == 0) {
				Ext.getCmp('ajust').setDisabled(true);
				Ext.getCmp('handle').setDisabled(true);
			} else {
				Ext.getCmp('handle').setDisabled(false);
				var count = 0;
				for(var i=0;i<len;i++){
					var selStlmFlg = records[i].data['stlmFlg'];
					if("POSP成功，渠道失败" == selStlmFlg){
						count ++;
					}
				}
				if(count > 0){
					Ext.getCmp('ajust').setDisabled(false);
				}else{
					Ext.getCmp('ajust').setDisabled(true);
				}
			}
		}
	});
	
	txnGridStore.on('beforeload', function(){
		Ext.apply(this.baseParams, {
			start: 0,
			startDateSettlmt: typeof(Ext.getCmp('startDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('startDateSettlmt').getValue().format('Ymd'),
			endDateSettlmt: typeof(Ext.getCmp('endDateSettlmt').getValue()) == 'string' ? '' : Ext.getCmp('endDateSettlmt').getValue().format('Ymd'),
			startDateTrans: typeof(Ext.getCmp('startDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('startDateTrans').getValue().format('Ymd'),
			endDateTrans: typeof(Ext.getCmp('endDateTrans').getValue()) == 'string' ? '' : Ext.getCmp('endDateTrans').getValue().format('Ymd'),
			queryMchtId: Ext.get('queryMchtId').getValue(),
			queryBrhId: Ext.get('queryBrhId').getValue(),
			querySeqNum: Ext.get('querySeqNum').getValue(),
			queryRefNum: Ext.get('queryRefNum').getValue(),
			checkFlag: Ext.get('checkFlag').getValue(),
			queryResultType: Ext.get('checkResultType').getValue(),
			queryResultType1: Ext.get('checkResultType1').getValue(),
			queryCardNum: Ext.get('queryCardNum').getValue(),
			ajustStatus: Ext.get('ajustStatus').getValue()
		});
	});
	
	
	
	var mainView = new Ext.Viewport({
		layout: 'border',
		items: [txnGrid],
		renderTo: Ext.getBody()
	});
});