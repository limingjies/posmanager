//工具栏对象
var toolBarStr;
//菜单树对象
var menuTree;
var initMask = new Ext.LoadMask(Ext.getBody(),{});
//父工具栏调用方法
function changeTree(button) {
	Ext.get("mainUI").dom.src = imageBaseUrl + "/page/system/T00001.jsp";
	initMask.msg = '正在加载菜单栏，请稍后......';
	initMask.show();
	menuTree.getRootNode().attributes.loader.dataUrl = 'tree.asp?id=' + button.id;
	menuTree.getRootNode().reload(function(){
		hideToolInitMask.defer(1000);
	});
	menuTree.getRootNode().expand(true);
}

//子工具栏调用方法
function changeTxn(button) {
	initMask.msg = '系统界面加载中，请稍后......';
	initMask.show();
	Ext.get("mainUI").dom.src = button.url;
	hideToolInitMask.defer(2000);
}
//隐藏加载图层
function hideToolInitMask() {
	initMask.hide();
}