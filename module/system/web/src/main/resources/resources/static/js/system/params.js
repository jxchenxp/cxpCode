var systemParams = {};
systemParams.table = null;
systemParams.div = null;
systemParams.path = '/system/params/';
systemParams.type = null;

$(function(){
	systemParams.table = $('#systemParamsTable');
	systemParams.div = $('#systemParams');
	//资源
	app.defaultValidateResource(systemParams.div.find('#operation'),systemParams.path);
});
 
systemParams.query = function(){	
	var formData = $('#systemParamsQueryForm').serializeJson();
	systemParams.table.datagrid('load',formData);
};

systemParams.addEditWinLoad = function(){
	
};

systemParams.toAdd = function (){
	systemParams.type = 'add';
	var win = $('#systemParamsAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	form.find('#code').textbox({'editable':true,required:true,validType:{zhLength:[1,32],unique:['system/params/check','code']}});
	
	win.window({title:'新增'}).window('open');
}

systemParams.toEdit = function(){
	systemParams.type = 'edit';
	var row = systemParams.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	
	var win = $('#systemParamsAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	//把不能修改的改为灰色
	form.find('#code').textbox({'editable':false,required:false,validType:{}});
	
	//加载form数据
	app.loadFromData(form,app.rootPath + '/system/params/get?code=' + row.code);
	
	win.window({title:'修改'}).window('open');
}

//保存
systemParams.save = function(){
	var url = '';
	if(systemParams.type == 'add'){
		url = app.rootPath + 'system/params/save';
	}else{
		url = app.rootPath + 'system/params/update';
	}
	
	var win = $('#systemParamsAddEdit');
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
		var win = $('#systemParamsAddEdit');
		win.window('close');		
		//刷新表格数据
		systemParams.table.datagrid('reload');
	};
	
	form.form('submit',formData);
}

//取消保存
systemParams.cancel = function(){
	$('#systemParamsAddEdit').window('close');
}

//删除
systemParams.remove = function(){
	var row = systemParams.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}	
	
	var data = {};
	data.url = app.rootPath + systemParams.path + 'remove';
	data.data = {code:row.code};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemParams.table.datagrid('reload');
	};
	$.ajax(data);
}

systemParams.isValidate = function(row){
	return row.status == 1;
}