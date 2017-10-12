var grantResources = {};
grantResources.table = null;
grantResources.div = null;
grantResources.isQury = false;
grantResources.project = false;
grantResources.grantType = null;
grantResources.grantId = null;

grantResources.grantType_role = 'grantResourceForRole';
grantResources.grantType_account = 'grantResourceForAccount';

$(function(){
	grantResources.table = $('#systemGrantResourceTable');
	grantResources.div = $('#systemGrantResources');
	grantResources.grantId = window.top.grantId;
	grantResources.grantType = window.top.grantResourceType;
	
	grantResources.useCheckAll = false;
	grantResources.grantCheckAll = false;
});

grantResources.query = function(){
	//输入验证
	var isValid = $('#systemGrantResourceQueryForm').form('validate'); 
    if (!isValid) { 
    	$.messager.alert('提示','请选择系统');
    	return;
    } 
	
	var formData = $('#systemGrantResourceQueryForm').serializeJson();
	if(grantResources.grantType_role == grantResources.grantType){
		formData.roleId = this.grantId;
	}else{
		formData.account = this.grantId;
	}
	
	grantResources.project = formData['project'];
	
	var data = {};
	if(grantResources.grantType_role == grantResources.grantType){
		data.url = app.rootPath + 'listResourceForRole';
	}else{
		data.url = app.rootPath + 'listResourceForAccount';
	}
	
	data.data = formData;
	data.success = function(jsonData){
		grantResources.showTreeData(grantResources.convertResource(jsonData));
		grantResources.isQuery = true;
	}
	$.ajax(data);
};

//展示树数据
grantResources.showTreeData = function(data){
	grantResources.table.treegrid('loadData',data);
	grantResources.initTreeData(grantResources.table.treegrid('getRoots'));
};

//格式化树数据，符合easyui树的格式
grantResources.convertResource = function(resources){
	var newResMap = {};
	var newReses = [];
	var res, newRes;
	var len = resources.length;
	for(var i = 0; i < len; i++){
		res = resources[i];
		newRes = {};
		newRes.id = res.menuResourceCode == ' ' ? res.menuCode : (res.menuCode + '_' + res.menuResourceCode);
		newRes.project = res.project;
		newRes.text = res.name;
		newRes.menuCode = res.menuCode;
		newRes.menuResourceCode = res.menuResourceCode;
		newRes.useResource = res.useCheck;
		newRes.useResourceByRole = res.useResourceByRole;
		newRes.grantResource = res.grantCheck;
		newRes.grantResourceByRole = res.grantResourceByRole;
		newRes.children = [];
		if(app.str.isBlank(res.menuResourceCode)){
			newResMap[res.menuCode] = newRes;
		}
		
		if(newResMap[res.parentCode]){
			newResMap[res.parentCode].children.push(newRes);
		}else{
			newReses.push(newRes);
		}
	}
	return newReses;  
};

grantResources.formatUseCheck = function(val, row, index){
	return '<span id="useResource_'+ row.id +'" onclick="grantResources.clickCellById(\'useResource\',\''+ row.id +'\')"></span>';
};

grantResources.formatGrantCheck = function(val, row, index){
	return '<span id="grantResource_'+ row.id +'" onclick="grantResources.clickCellById(\'grantResource\',\''+ row.id +'\')"></span>';
};
//使用权限全选改变事件
grantResources.changeAllUse = function(){
	grantResources.useCheckAll = grantResources.useCheckAll ? false : true;
	grantResources.checkAllColumn('useResource',grantResources.useCheckAll);
}; 
//分配权限全选改变事件
grantResources.changeAllGrant = function(){
	grantResources.grantCheckAll = grantResources.grantCheckAll ? false : true;
	grantResources.checkAllColumn('grantResource',grantResources.grantCheckAll);
};
//全选某一列，或全不选某一列
grantResources.checkAllColumn = function(field,checked){
	var checkSpan = grantResources.div.find('#' + field + '_check_title');
	checkSpan.removeClass();
	var cls = 'tree-checkbox tree-checkbox' + (checked ? 1 : 0);
	checkSpan.addClass(cls);
	
	var roots = grantResources.table.treegrid('getRoots');
	for(var index in roots){
		grantResources.changeCheck(field,roots[index], checked);
	}
};

//保存
grantResources.save = function(){
	if(this.grantId == undefined){
		$.messager.alert('提示','请先选择要被分配权限的记录!');
		return;
	}
	if(!grantResources.isQuery){
		$.messager.alert('提示','请先加载数据!');
		return;
	}
	
	var rows = this.table.treegrid('getRoots');
	var checkCodeMap = {};
	checkCodeMap.useMenuCodes = '';
	checkCodeMap.useMenuResourceCodes = '';
	checkCodeMap.grantMenuCodes='';
	checkCodeMap.grantMenuResourceCodes='';
	for (var i = 0; i < rows.length; i++) {  
		grantResources.getCheckStr(rows[i],checkCodeMap);
    } 
	
	
	var formData = {};
	formData.project = grantResources.project;
	if(grantResources.grantType_role == grantResources.grantType){
		formData.roleId = this.grantId;
	}else{
		formData.account = this.grantId;
	}
	formData.menuCodes = checkCodeMap.useMenuCodes;
	formData.menuResourceCodes = checkCodeMap.useMenuResourceCodes;
	formData.grantMenuCodes = checkCodeMap.grantMenuCodes;
	formData.grantMenuResourceCodes = checkCodeMap.grantMenuResourceCodes;
	
	
	var data = {};
	data.type = "POST";
	if(grantResources.grantType_role == grantResources.grantType){
		data.url = app.rootPath + 'grantResourceForRole';
	}else{
		data.url = app.rootPath + 'grantResourceForAccount';
	}
	data.data = formData;
	data.success = function(jsonData){
		app.successMsg();
	}
	$.ajax(data);
};

grantResources.getCheckStr = function(row,checkCodeMap){
	if(row['useResource' + '_check_status'] > 1){
		//如果是账号分配权限，则权限如果已经在角色中分配了，则不需要保存。
		if(grantResources.grantType_account == grantResources.grantType
				&& row['useResourceByRole']){
			
		}else{
			checkCodeMap.useMenuCodes += row['menuCode'] + ';' ;
			checkCodeMap.useMenuResourceCodes += row['menuResourceCode'] + ';';
		}
    }
    if(row['grantResource' + '_check_status'] > 1){
    	if(grantResources.grantType_account == grantResources.grantType
				&& row['grantResourceByRole']){
			
		}else{
	    	checkCodeMap.grantMenuCodes += row['menuCode'] + ';' ;
	    	checkCodeMap.grantMenuResourceCodes += row['menuResourceCode'] + ';';
		}
    }
	
    var children = row.children;
    if(children.length == 0){
    	console.log(checkCodeMap.useMenuCodes);
    	console.log(checkCodeMap.useMenuResourceCodes);
    	return;
    }
	for (var i = 0; i < children.length; i++) {  
		grantResources.getCheckStr(children[i],checkCodeMap);
    } 
};

//初始化树的选择情况
grantResources.initTreeData = function(root){
	var row;
	for(var index in root){
		row = root[index];
		this.initTreeRowData(row,'useResource');
		this.initTreeRowData(row,'grantResource');
	}
};
//初始化行的选择情况
grantResources.initTreeRowData = function(row,field){
	
	var children = row.children;
	if(children.length <= 0){
		row[field + '_check_status'] = row[field] ? 3 : 1;
		//根据状态显示
		grantResources.showCheck(row,field,row[field + '_check_status']);
		
		return row[field + '_check_status'];
	}
	
	var child;
	var checkStatuses = {};
	var checkStatus = 0;
	for(var index in children){
		child = children[index];
		checkStatus = this.initTreeRowData(child, field);
		checkStatuses[checkStatus] = true;
	}
	if(checkStatuses[2] || (checkStatuses[1] && checkStatuses[3])){
		checkStatus = 2;
	}else if(checkStatuses[3]){
		checkStatus = 3;
	}else {
		checkStatus = 1;
	}
	//设备状态
	row[field + '_check_status'] = checkStatus; 
	//根据状态显示
	grantResources.showCheck(row,field,checkStatus);
	return checkStatus;
};

//单击事件,传入field,row.id
grantResources.clickCellById = function(field, id){
	grantResources.clickGrantCell(field,grantResources.table.treegrid('find',id));
}

//单击选择事件,传入field,row
grantResources.clickGrantCell = function(field, row){
	if(field == 'useResource' || field == 'grantResource'){
		grantResources.changeCheck(field, row);
	}
};

//单击选择事件
grantResources.changeCheck = function(field, row,checked){
	var checkStatus = row[field + '_check_status'];
	//有传入checked，根据checked判断，没有则根据check_status判断,兼容全选和单击事件
	if(checked != undefined && checked != null){
		checkStatus = checked ? 3 : 1;
	}else{
		if(checkStatus == 3){
			checkStatus = 1;
		}else{
			checkStatus = 3;
		}
	}
	
	//改变状态
	row[field + '_check_status'] = checkStatus;
	
	//根据状态改变显示
	grantResources.showCheck(row,field,checkStatus);
	
	grantResources.changePCheck(field, checkStatus == 3, row);
	grantResources.changeCCheck(field, checkStatus == 3, row);
};

//改变父级选择情况 
grantResources.changePCheck = function(field, checked, row){
	var parent = grantResources.table.treegrid('getParent',row.id);
	if(parent == null){
		return; 
	}
	
	var checkStatus = parent[field + '_check_status'];
	parent[field + '_check_status'] = grantResources.getCheckStatusByChildren(parent,field);
	//根据状态设置背景色
	grantResources.showCheck(parent,field,parent[field + '_check_status']);
	
	if(checkStatus != parent[field + '_check_status']){
		grantResources.changePCheck(field, checked, parent);
	}
};

//改变子级选择情况 
grantResources.changeCCheck = function(field, checked, row){
	var children = row.children;
	var child;
	for(var index in children){
		child = children[index];
		
		//改变状态
		child[field + '_check_status'] = checked ? 3 : 1;
		
		//根据状态改变显示
		grantResources.showCheck(child,field,child[field + '_check_status']);
		
		grantResources.changeCCheck(field, checked, child);
	}
};

//根据子级节点判断父级节点的选择情况
grantResources.getCheckStatusByChildren = function(row,field){
	var children = row.children;
	var checkStatus = 0;
	for(var index in children){
		child = children[index];
		
		if(index > 0){
			if(checkStatus == 2){
				return 2;
			}else if(checkStatus != child[field + '_check_status']){
				return 2;
			}
		}
		
		checkStatus = child[field + '_check_status'];
	}
	return checkStatus;
};

//显示checkbox状态
grantResources.showCheck = function(row, field, status){
	var checkSpan = row[field + '_check_span'];
	if(!checkSpan){
		//获取对应的checkinput对象
		checkSpan = grantResources.div.find('#' + field + '_' + row.id);
		row[field + '_check_span'] = checkSpan;
	}
	checkSpan.removeClass();
	
	//匹配选中状态
	if(status == 3){
		status = 1;
	}else if(status == 2){
		status = 2;
	}else if(status == 1){
		status = 0;
	}
	
	var cls = 'tree-checkbox tree-checkbox' + status;
	checkSpan.addClass(cls);
};