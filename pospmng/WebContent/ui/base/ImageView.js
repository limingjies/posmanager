var folderArray = new Array();
var curPicIdx = -1;
var dataArray = new Array();
var pageInited = false;
var selectedPicSrc = '';
var imgBathPathDesc = ''; //图片根路径描述符

var image_window = new Ext.Window({  
    id: 'image-window',  
    title : '图片浏览',  
    width : 750,  
    height : 500,  
    resizable : false,  
    closeAction :'hide',  
    layout:'border',
    buttonAlign: 'center',
    items:[{  
        xtype: 'panel',  
        region: 'center',  
        layout:'fit',  
        bodyStyle : 'background-color:#E5E3DF;',  
        frame:false,  
        border:false,  
        html :'<div id="mapPic"><div class="nav">'  
//            +'<div class="up" id="up"></div><div class="right" id="right"></div>'  
//            +'<div class="down" id="down"></div><div class="left" id="left"></div>'  
//            +'<div class="zoom" id="zoom"></div><div class="in" id="in"></div>'  
//            +'<div class="out" id="out"></div></div>'  
            +'<div class="in" id="in"></div><div class="zoom" id="zoom"></div><div class="out" id="out"></div>'  
            +'</div>'  
            +'<img id="view-image" src="" border="0" style="cursor: url(../../ext/resources/images/imgview/openhand_8_8.cur), default;" width="745" height="495"> </div>'  
    }],
    buttons: [{  
        text: '上一张',
        handler: function() {
        	checkCurPicIdx();
        	if (curPicIdx == 0) {
        		//alert("已经是第一张了。");
        		//showAlertMsg('已经是第一张了.', image_window);
        		Ext.Msg.alert("提示", "已经是第一张了");
        		return;
        	}
        	curPicIdx -= 1;
        	Ext.get('view-image').dom.src = dataArray[curPicIdx][0] + '&time=' + (new Date()).pattern("yyyyMMddhhmmssS");
         	Ext.get('view-image').dom.title=dataArray[curPicIdx][1];
       }  
    },{
    	text:'下一张',
        handler: function() {  
        	checkCurPicIdx();
        	if (curPicIdx == (dataArray.length -1)) {
        		//showAlertMsg('已经是最后一张了。', image_window);
        		Ext.Msg.alert("提示", "已经是最后一张了");
        		return;
        	}
        	curPicIdx += 1;
        	Ext.get('view-image').dom.src = dataArray[curPicIdx][0] + '&time=' + (new Date()).pattern("yyyyMMddhhmmssS");;
         	Ext.get('view-image').dom.title=dataArray[curPicIdx][1];
         }  
    }],  
    listeners: {  
        'show': function() {
        	//if (!pageInited) {//只初始化一次
                pageInit();  
        	//}
        }  
    }  
});  

/**
 * 显示图片函数，
 * 第一个参数为马上要显示的图片路径，后面的参数为其它图片所在的路径（相对于上传文件夹的相对路径），一个文件夹一个参数
 * 如：showImage(srcToshow, folder1,folder2,.....)
 */
function showImage() {
	if (folderArray && folderArray.length > 0) {
		folderArray.splice(0,folderArray.length);//先清空
	}
	curPicIdx = -1;
	for (i =1 ; i < arguments.length; i++) {
		var str = arguments[i] + "";
		folderArray.push(str);
	}
	selectedPicSrc =  arguments[0];
	image_window.show();
	
	Ext.get('view-image').dom.src = arguments[0] + '&time=' + (new Date()).pattern("yyyyMMddhhmmssS");//第一个参数是指定显示的图片路径
	if (dataArray && dataArray.length > 0) {
		for (var i = 0; i < dataArray.length; i++) {
		    if (selectedPicSrc.indexOf(dataArray[i][0]) >= 0) {
		    	curPicIdx = i;
		    	Ext.get('view-image').dom.title=dataArray[i][1];
		    }
		}
	}
}

function checkCurPicIdx() {
	if (curPicIdx != -1) {
		return;
	}
	var curPicSrc = Ext.get('view-image').dom.src;
	for (var i=0; i<dataArray.length; i++) {
		if (curPicSrc.indexOf(dataArray[i][0]) >= 0) {
			curPicIdx = i;
			return;
		}
	}
	//没有找到则显示第一张
	curPicIdx = 0;
}

/** 
 * 控件初始化，在一个页面里只初始化一次 
 */  
function pageInit(){
	var folderPath = "";
	for (var i = 0; i < folderArray.length; i++) {
		folderPath = folderPath + folderArray[i] + "/";
	}
	var func = imgBathPathDesc ? imgBathPathDesc : '';
	picStore = new Ext.data.Store({
	    storeId: 'picStore',
		proxy: new Ext.data.HttpProxy({
			url: 'loadRecordAction.asp?storeId=getPicturePaths&folderPath=' + folderPath + ((func)?('&func=' + func):'')
		}),
		reader: new Ext.data.JsonReader({
			root: 'data'
			//totalProperty: 'totalCount'
		},[	
			{name: 'id',mapping: 'id'},
			{name: 'picUrl',mapping: 'picUrl'},
			{name: 'picBizName',mapping: 'picBizName'}
		]),
	});

	//加载数据是异步操作，加载之后需要做的操作必须写在回调函数中
	picStore.load({
		callback: function(records, options, success){
			if(success){
				if (dataArray && dataArray.length > 0) {
					dataArray.splice(0,dataArray.length);//先清空
				}
				var records = picStore.getRange(0, picStore.getCount()-1);
				for (var i = 0; i < records.length; i++) {
				    var record = records[i];
				    dataArray.push([Ext.contextPath + '/PrintImage?fileName=' + record.get('picUrl') + ((func)?('&func=' + func):''), record.get('picBizName')]);
				    if (selectedPicSrc.indexOf(record.get('picUrl')) >= 0) {
				    	curPicIdx = i;
				    	Ext.get('view-image').dom.title=record.get('picBizName');
				    }
				}
			}		
		}		
	});
	//var picData = picStore['data'];
	//alert(typeof(picData));
    var image = Ext.get('view-image');  
  
    if(image!=null){ 
    	image.on({  
            'mousedown':{fn:function(){this.setStyle('cursor','url(../../ext/resources/images/imgview/closedhand_8_8.cur),default;');},scope:image},  
            'mouseup':{fn:function(){this.setStyle('cursor','url(../../ext/resources/images/imgview/openhand_8_8.cur),move;');},scope:image},  
            'dblclick':{fn:function(){  
                zoom(image,true,1.5);  
            }           
        }, 'mousewheel':{fn:function(){
			var e = Ext.EventObject;
			if(e.getWheelDelta()>0){
				zoom(image,true,1.5);
			}else{
				zoom(image,false,1.5);
			}
		}} 
    	});  
        new Ext.dd.DD(image, 'pic');  
          
        //image.center();//图片居中  

        //获得原始尺寸  
        image.osize = {  
            width:image.getWidth(),  
            height:image.getHeight()
        };
        //alert(image.osize.width);
        //alert(image.osize.height);
      
        //Ext.get('up').on('click',function(){imageMove('up',image);});       //向上移动  
        //Ext.get('down').on('click',function(){imageMove('down',image);});   //向下移动  
        //Ext.get('left').on('click',function(){imageMove('left',image);});   //左移  
        //Ext.get('right').on('click',function(){imageMove('right',image);}); //右移动  
        Ext.get('in').on('click',function(){zoom(image,true,1.5);});        //放大  
        Ext.get('out').on('click',function(){zoom(image,false,1.5);});      //缩小  
        Ext.get('zoom').on('click',function(){restore(image);});            //还原  
    }
    //pageInited = true;
};  
  
  
/** 
 * 图片移动 
 */  
function imageMove(direction, el) {  
    el.move(direction, 50, true);  
}  
  
  
/** 
 *  
 * @param el 图片对象 
 * @param type true放大,false缩小 
 * @param offset 量 
 */  
function zoom(el,type,offset){  
    var width = el.getWidth();  
    var height = el.getHeight();  
    var nwidth = type ? (width * offset) : (width / offset);  
    var nheight = type ? (height * offset) : (height / offset);  
    var left = type ? -((nwidth - width) / 2):((width - nwidth) / 2);  
    var top =  type ? -((nheight - height) / 2):((height - nheight) / 2);   
    el.animate(  
        {  
            height: {to: nheight, from: height},  
            width: {to: nwidth, from: width},  
            left: {by:left},  
            top: {by:top}  
        },  
        null,        
        null,       
        'backBoth',  
        'motion'  
    );  
}  
  
  
/** 
 * 图片还原 
 */  
function restore(el) {  
    var size = el.osize;
    //alert(size.width)
    //alert(size.height)
      
    //自定义回调函数  
    function center(el,callback){  
    	el.alignTo("image-window", "tl");
        callback(el);  
    }  
    el.fadeOut({callback:function(){  
    	el.setSize(size.width, size.height);
        center(el,function(el){//调用回调  
            el.fadeIn();  
        });
    }  
    });  
}  