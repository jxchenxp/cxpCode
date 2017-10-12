var systemRole = {};
systemRole.table = null;
systemRole.div = null;
systemRole.path = '/system/role/';
systemRole.type = null;

$(function(){
	systemRole.table = $('#systemRoleTable');
	systemRole.div = $('#systemRole');
	//资源
	app.defaultValidateResource(systemRole.div.find('#operation'),systemRole.path,['grantResources','grantAccount']);
});
 
systemRole.query = function(){	
	var formData = $('#systemRoleQueryForm').serializeJson();
	systemRole.table.datagrid('load',formData);
};

systemRole.addEditWinLoad = function(){
	//加载部门数据
	app.loadSubDept($('#systemRoleAddEdit').find('#belongToDeptId'));
};

systemRole.toAdd = function (){
	systemRole.type = 'add';
	var win = $('#systemRoleAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	win.window({title:'新增'}).window('open');
}

systemRole.toEdit = function(){
	systemRole.type = 'edit';
	var row = systemRole.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	
	var win = $('#systemRoleAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	//加载form数据
	app.loadFromData(form,app.rootPath + '/system/role/get?id=' + row.id);
	
	win.window({title:'修改'}).window('open');
}

//保存
systemRole.save = function(){
	var url = '';
	if(systemRole.type == 'add'){
		url = app.rootPath + 'system/role/save';
	}else{
		url = app.rootPath + 'system/role/update';
	}
	
	var win = $('#systemRoleAddEdit');
	var form = win.find('#addEditForm');
	var formData = {};
	formData.url = url;
	formData.onSubmit = function(){
		var isValid = $(this).form('validate'); 
        if (!isValid) { 
        	$.messager.alert('提示','验证未通过');
        } 
        return isValid;
	};
	formData.success = function(result){
		var win = $('#systemRoleAddEdit');
		win.window('close');		
		//刷新表格数据
		systemRole.table.datagrid('reload');
	};
	
	form.form('submit',formData);
}

//取消保存
systemRole.cancel = function(){
	$('#systemRoleAddEdit').window('close');
}

//删除
systemRole.remove = function(){
	var row = systemRole.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}	
	
	var data = {};
	data.url = app.rootPath + systemRole.path + 'remove';
	data.data = {id:row.id};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemRole.table.datagrid('reload');
	};
	$.ajax(data);
}

//进入权限分配页面
systemRole.toGrantResources = function(){
	var row = systemRole.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	if(!systemRole.isValidate(row)){
		$.messager.alert('提示','角色状态无效，不能分配资源');
		return;
	}
	app.closeTabIfExist(window.top.grantResourceTabName);
	
	this.roleId = row.id;
	window.top.grantResourceTabName = row.name + '-权限分配';
	window.top.grantResourceType = 'grantResourceForRole';
	window.top.grantId = row.id;
	
	app.addTab('grantResources', window.top.grantResourceTabName, app.rootPath + 'system/grantResource/grantResources.html');
}

//进入账号分配页面
systemRole.toGrantAccount = function(type){
	var row = systemRole.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	if(!systemRole.isValidate(row)){
		$.messager.alert('提示','角色状态无效，不能分配账号');
		return;
	}
	
	app.closeTabIfExist(window.top.grantAccountForRoleTabName);
	
	this.roleId = row.id;
	window.top.grantAccountForRoleTabName = row.name + '-账号分配';
	window.top.grantAccountForRoleType = type;
	window.top.grantAccountForRoleId = row.id;
	
	app.addTab('grantAccountForRole', window.top.grantAccountForRoleTabName, app.rootPath + 'system/grantResource/grantAccount.html');
}

systemRole.isValidate = function(row){
	return row.status == 1;
}