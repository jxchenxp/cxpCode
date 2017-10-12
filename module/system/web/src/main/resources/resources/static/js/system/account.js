var systemAccount = {};
systemAccount.table = null;
systemAccount.div = null;
systemAccount.path = '/system/account/';
systemAccount.type = null;

$(function(){
	systemAccount.table = $('#systemAccountTable');
	systemAccount.div = $('#systemAccount');
	//资源
	app.defaultValidateResource(systemAccount.div.find('#operation'),systemAccount.path,['grantRole','grantResources']);
	
	//加载查询条件的部门列表
	app.loadSubDept($('#systemAccountQueryForm').find('#deptId'));
});
 
systemAccount.query = function(){	
	var formData = $('#systemAccountQueryForm').serializeJson();
	systemAccount.table.datagrid('load',formData);
};

systemAccount.addEditWinLoad = function(){
	//加载部门数据
	app.loadSubDept($('#systemAccountAddEdit').find('#deptId'));
};

systemAccount.toAdd = function (){
	systemAccount.type = 'add';
	var win = $('#systemAccountAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	form.find('#account').textbox({'editable':true,required:true,validType:{zhLength:[1,32],unique:['system/account/check','account']}});
	
	win.window({title:'新增'}).window('open');
}

systemAccount.toEdit = function(){
	systemAccount.type = 'edit';
	var row = systemAccount.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	
	var win = $('#systemAccountAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	//把不能修改的改为灰色
	form.find('#account').textbox({'editable':false,required:false,validType:{}});
	
	//加载form数据
	app.loadFromData(form,app.rootPath + '/system/account/get?account=' + row.account);
	
	win.window({title:'修改'}).window('open');
}

//保存
systemAccount.save = function(){
	var url = '';
	if(systemAccount.type == 'add'){
		url = app.rootPath + 'system/account/save';
	}else{
		url = app.rootPath + 'system/account/update';
	}
	
	var win = $('#systemAccountAddEdit');
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
		var win = $('#systemAccountAddEdit');
		win.window('close');		
		//刷新表格数据
		systemAccount.table.datagrid('reload');
	};
	
	form.form('submit',formData);
}

//取消保存
systemAccount.cancel = function(){
	$('#systemAccountAddEdit').window('close');
}

//删除
systemAccount.remove = function(){
	var row = systemAccount.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}	
	
	var data = {};
	data.url = app.rootPath + systemAccount.path + 'remove';
	data.data = {account:row.account};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemAccount.table.datagrid('reload');
	};
	$.ajax(data);
}

//进入分配角色页面
systemAccount.toGrantRole = function(type){
	var row = systemAccount.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	app.closeTabIfExist(window.top.grantRoleTabName);
	
	window.top.grantRoleTabName = row.name + '-角色分配' + (type == 'use' ? '(使用)' : '(分配)');
	window.top.grantRoleForAccount = row.account;
	window.top.grantRoleForAccountType = type;
	
	app.addTab('grantRole', window.top.grantRoleTabName, app.rootPath + 'system/grantResource/grantRole.html');
};

//进入分配权限页面
systemAccount.toGrantResource = function(){
	var row = systemAccount.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	app.closeTabIfExist(window.top.grantResourceTabName);
	
	window.top.grantResourceTabName = row.name + '-权限分配';
	window.top.grantResourceType = 'grantResourceForAccount';
	window.top.grantId = row.account;
	
	app.addTab('grantResources', window.top.grantResourceTabName, app.rootPath + 'system/grantResource/grantResources.html');
};
