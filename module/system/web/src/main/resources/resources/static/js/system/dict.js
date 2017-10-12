var systemDict = {};
systemDict.table = null;
systemDict.div = null;
systemDict.path = '/system/dict/';
systemDict.type = null;

systemDict.loadDict = function(){
	var data = {};
	data.url = app.rootPath + systemDict.path + 'listSub';
	data.success = function(jsonData){
		systemDict.table.treegrid('loadData',app.convertDictNode(jsonData));
	}
	$.ajax(data);
};


$(function(){
	systemDict.table = $('#systemDictTable');
	systemDict.div = $('#systemDict');
	//资源
	app.defaultValidateResource(systemDict.div.find('#operation'),systemDict.path);
	
	systemDict.loadDict();
});

//格式化status列
systemDict.formatStatus = function(val,row){
	if(val == 1){
		return '正常';
	}else if(val == 2){
		return '禁用';
	}else{
		return '未知';
	}
};

systemDict.query = function(){	
	var formData = $('#systemDictQueryForm').serializeJson();
	systemDict.table.datagrid('load',formData);
};

systemDict.addEditWinLoad = function(){
	//加载部门数据
	app.loadDict($('#systemDictAddEdit').find('#parentPath'),{});
};

systemDict.toAdd = function (){
	systemDict.type = 'add';
	var win = $('#systemDictAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	win.window({title:'新增'}).window('open');
}

systemDict.toEdit = function(){
	systemDict.type = 'edit';
	var row = systemDict.table.treegrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}
	
	var win = $('#systemDictAddEdit');
	var form = win.find('#addEditForm');
	form.form('clear');
	
	//把不能修改的改为灰色
	//加载form数据
	app.loadFromData(form,app.rootPath + '/system/dict/get?path=' + encodeURIComponent(row.path));
	
	win.window({title:'修改'}).window('open');
}

//保存
systemDict.save = function(){
	var url = '';
	if(systemDict.type == 'add'){
		url = app.rootPath + 'system/dict/save';
	}else{
		url = app.rootPath + 'system/dict/update';
	}
	
	var win = $('#systemDictAddEdit');
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
		var win = $('#systemDictAddEdit');
		win.window('close');		
		//刷新表格数据
		systemDict.loadDict();
	};
	
	form.form('submit',formData);
}

//取消保存
systemDict.cancel = function(){
	$('#systemDictAddEdit').window('close');
}

systemDict.remove = function(){
	var row = systemDict.table.datagrid('getSelected');
	if(!row){
		$.messager.alert('提示','请选择一条记录操作');
		return;
	}	
	
	var data = {};
	data.url = app.rootPath + systemDict.path + 'remove';
	data.data = {id:row.id};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDict.loadDept();
	};
	$.ajax(data);
}

systemDict.disable = function(path){
	var data = {};
	data.url = app.rootPath + systemDict.path + 'disable';
	data.data = {path:path};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDict.loadDict();
	};
	$.ajax(data);
};

systemDict.enable = function(path){
	var data = {};
	data.url = app.rootPath + systemDict.path + 'enable';
	data.data = {path:path};
	data.success = function(response){
		app.successMsg();
		//刷新表格数据
		systemDict.loadDict();
	};
	$.ajax(data);
};

//操作列
systemDict.formatOper = function(val,row,index){
	var opers = '';
	
	console.log(app.validateOper(systemDict.path, 'update'));
	
	if(app.validateOper(systemDict.path, 'update')){
		if(row.status == 1){
			opers += '<a href="#" class="easyui-linkbutton" onclick="systemDict.disable(\''+ row.path +'\')">禁用</a>';
		}else{
			opers += '<a href="#" class="easyui-linkbutton" onclick="systemDict.enable(\''+ row.path +'\')">启用</a>';
		}
	}
	return opers;
};


