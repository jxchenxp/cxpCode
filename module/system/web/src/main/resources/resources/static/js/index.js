$(function(){
	loadTopMenu();
	
	tabs = $('#tabs');
	
	//添加首页tab
	addTab('homePage','首页','homePage.html',false);
});

var tabs = null;

function loadTopMenu(){
	//从后台获取菜单功能
	var data = {};
	data.url = app.rootPath + '/listMenu';
	data.data = {level:1};
	data.success = showTopMenu;
	$.ajax(data);
}

function showTopMenu(menus){
	var topMenuUl = $('#topMenus ul');
	var li = null;
	//清空
	topMenuUl.empty();
	//添加首页
	li = '<li><a href="javascript:void(0);" style="text-decoration:none"><img src="static/images/icon/menu-item.png"/>首页</a></li>';
	topMenuUl.append(li);
	
	var icon,name,project,code,path;
	$(menus).each(function (index,menu){
		console.log(menu);
		icon = menu.icon;
		if(app.str.isBlank(icon)){
			icon = app.rootPath + "static/images/icon/menu-item.png";
		}
		name = menu.name;
		project = menu.project;
		code = menu.code;
		path = menu.path;
		li = '<li><a href="javascript:loadMenuTree(\''+ project +'\',\''+ path +'\');" style="text-decoration:none"><img src="'+ icon +'"/>'+ name +'</a></li>';
		topMenuUl.append(li);
	});
}

function loadMenuTree(project, path){
	//从后台获取菜单功能
	var data = {};
	data.url = app.rootPath + '/listMenu';
	data.data = {path:path};
	data.method='post';
	data.animate = true;
	//data.success = showMenuTree;
	//$.ajax(data);
	data.loadFilter = convertMenuNode;	
	data.onClick = clickMenu;

	$('#menuTree').tree(data);
}
function convertMenuNode(menus){
	var newMenuMap = {};
	var newMenus = [];
	var menu, newMenu;
	var len = menus.length;
	for(var i = 0; i < len; i++){
		menu = menus[i];
		newMenu = {};
		newMenu.id = menu.code;
		newMenu.parent = menu.parentCode;
		newMenu.text = menu.name;
		newMenu.url = menu.url;
		newMenu.indexUrl = menu.indexUrl;
		newMenu.children = [];
		newMenuMap[menu.code] = newMenu;
		
		if(newMenuMap[menu.parentCode]){
			newMenuMap[menu.parentCode].children.push(newMenu);
		}else{
			newMenus.push(newMenu);
		}
	}
	console.log(newMenus);
	return newMenus;  
}
function clickMenu(node){
	if(node.children != undefined && node.children.length > 0){
		return ;
	}
	
	addTab(node.id, node.text, app.rootPath + node.url + node.indexUrl);
}
 
function addTab(id, text, url, closable){
	var tab = tabs.tabs('getTab',text);
	if(tab != null){
		tabs.tabs('select',text);
		return;
	}
	
	var tabData = {};
	tabData.id = id;
	tabData.title = text;
	tabData.href = url;
	tabData.closable = (closable == undefined ? true : closable);
	tabs.tabs('add',tabData);
}

function getTab(index){
	return tabs.tabs('getTab',index);
}

function closeTabIfExist(index){
	return tabs.tabs('close',index);
}