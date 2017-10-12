var systemDept = {};
systemDept.table = null;
systemDept.div = null;
systemDept.path = '/system/dept/';
systemDept.type = null;

systemDept.loadDept = function(){
	var data = {};
	data.url = 'listSubDept';
	data.success = function(jsonData){
		systemDept.table.treegrid('loadData',app.convertDeptNode(jsonData));
	}
	$.ajax(data);
};

$(function(){
	systemDept.table = $('#systemDeptTable');
	systemDept.div = $('#systemDept');
	//资源
	app.defaultValidateResource(systemDept.div.find('#operation'),systemDept.path);
	
	systemDept.loadDept();
});

//格式化status列
systemDept.formatStatus = function(val,row){
	if(val == 1){
		return '正常';
	}else if(val == 2){
		return '禁用';
	}else{
		return '未知';
	}
};

systemDept.query = function(){	
	var formData = $('#systemDeptQueryForm').serializeJson();
	systemDept.table.datagrid('load',formData);
};

systemDept.addEditWinLoad = function(){
	//加载部门数据
	app.loadSubDept($('#systemDeptAddEdit').find('#parentId'));
};

systemDept.toAdd = function (){
	systemDept.type = 'add';
	var win = $('#systemDeptAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	form.find('#code').textbox({'editable':true});
	
	win.window({title:'新增'}).window('open');
}

systemDept.toEdit = function(){
	systemDept.type = 'edit';
	var row = systemDept.table.treegrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	
	var win = $('#systemDeptAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	//把不能修改的改为灰色
	form.find('#code').textbox({'readonly':true});
	form.find('#parentId').combotree({'readonly':true});
	
	//加载form数据
	app.loadFromData(form,app.rootPath + '/system/dept/get?id=' + row.id);
	
	win.window({title:'修改'}).window('open');
}

//保存
systemDept.save = function(){
	var url = '';
	if(systemDept.type == 'add'){
		url = app.rootPath + 'system/dept/save';
	}else{
		url = app.rootPath + 'system/dept/update';
	}
	
	var win = $('#systemDeptAddEdit');
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
		var win = $('#systemDeptAddEdit');
		win.window('close');		
		//刷新表格数据
		systemDept.loadDept();
	};
	
	form.form('submit',formData);
}

//取消保存
systemDept.cancel = function(){
	$('#systemDeptAddEdit').window('close');
}

systemDept.remove = function(){
	var row = systemDept.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}	
	
	var data = {};
	data.url = app.rootPath + systemDept.path + 'remove';
	data.data = {id:row.id};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDept.loadDept();
	};
	$.ajax(data);
}

systemDept.disable = function(id){
	var data = {};
	data.url = app.rootPath + systemDept.path + 'disable';
	data.url = '/manager/system/dept/disable';
	data.data = {id:id};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDept.loadDept();
	};
	$.ajax(data);
};

systemDept.enable = function(id){
	var data = {};
	data.url = app.rootPath + systemDept.path + 'enable';
	data.data = {id:id};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDept.loadDept();
	};
	$.ajax(data);
};

//操作列
systemDept.formatOper = function(val,row,index){
	var opers = '';
	
	console.log(app.validateOper(systemDept.path, 'update'));
	
	if(app.validateOper(systemDept.path, 'update')){
		if(row.status == 1){
			opers += '<a href="#" class="easyui-linkbutton" onclick="systemDept.disable('+ row.id +')">禁用</a>';
		}else{
			opers += '<a href="#" class="easyui-linkbutton" onclick="systemDept.enable('+ row.id +')">启用</a>';
		}
	}
	return opers;
};


