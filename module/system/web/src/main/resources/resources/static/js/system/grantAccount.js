var grantAccount = { 
		selectData:{},
		data:{
			grant:{
				use:'useCheck',
				grant:'grantCheck'
			},
			roleIdsName:{
				use:'roleIds',
				grant:'grantAccountIds'
			},
			checkName:{
				use:'useCheck',
				grant:'grantCheck'
			},
			idsName:{
				use:'ids',
				grant:'grantIds'
			},
			unidsName:{
				use:'unids',
				grant:'grantUnids'
			}
		},
		
		init: function(){
			
		},
		
		queryAccount: function(){
			var data = {};
			data.url = app.rootPath + 'listAccountForRole';
			var formData = grantAccount.div.find('#grantAccountTable1Form').serializeJson();
			formData.roleId = grantAccount.id;
			data.queryParams = formData;
			
			grantAccount.table1.datagrid(data);
		},
		
		dbClickTable1Row: function(index,row){
			if(grantAccount.selectData[row.account]){
				grantAccount.selectData[row.account] = null;
				grantAccount.table1.datagrid('updateRow',{index:index,row:{check:row[grantAccount.data.checkName[grantAccount.type]]}});
				//删除已选择的行
				var rows = grantAccount.table2.datagrid('getRows');
				var index2 = -1;
				for(var i in rows){
					if(rows[i].account == row.account){
						index2 = i;
						break;
					}
				}
				if(index2 > -1){
					grantAccount.table2.datagrid('deleteRow',index2);
				}
			}else{
				grantAccount.table1.datagrid('deleteRow',index);
				grantAccount.table2.datagrid('appendRow',row);
				grantAccount.selectData[row.account] = row;
			}
		},
		dbClickTable2Row: function(index,row){
			grantAccount.selectData[row.account] = null;
			grantAccount.table2.datagrid('deleteRow',index);
			
			//判断左边表格中是否存在，存在则更新，否则添加 
			var rows = grantAccount.table1.datagrid('getRows');
			var existIndex = -1, existRow = null;
			for(var i in rows){
				if(rows[i].account == row.account){
					existIndex = i;
					existRow = rows[i];
					break;
				}
			}
			if(existIndex > -1){
				grantAccount.table1.datagrid('updateRow',{index:existIndex,row:{check:row[grantAccount.data.checkName[grantAccount.type]]}});
			}else{
				grantAccount.table1.datagrid('appendRow',row);
			}
		},
		save: function(){
			var data = {};
			data.url = app.rootPath + 'grantAccountForRole';
			data.data = {roleId:grantAccount.id,type:grantAccount.type};
			var idsMap = grantAccount.getSelectAccount();
			data.data[grantAccount.data.idsName[grantAccount.type]] = idsMap.ids;
			data.data[grantAccount.data.unidsName[grantAccount.type]] = idsMap.unids;
			data.success = function(response){
				app.successMsg();
			};
			$.ajax(data);
		},
		getSelectAccount: function(){
			var rows = grantAccount.table2.datagrid('getRows');
			var ids = '', unids = '';
			for(var index in rows){
				if(rows[index][grantAccount.data.checkName[grantAccount.type]]){
					unids += ';' + rows[index].account;
				}else{ 
					ids += ';' + rows[index].account;
				}
			}
			if(ids != ''){
				ids = ids.substring(1);
			}
			if(unids != ''){
				unids = unids.substring(1);
			}
			return {ids:ids,unids:unids};
		},
		formatterCheck2: function(val,row,index){
			if(row[grantAccount.data.checkName[grantAccount.type]]){
				return '解除分配';
			}else {
				return '添加分配';
			}
		},
		formatterCheck1: function(val,row,index){
			if(grantAccount.selectData[row.account]){
				if(row[grantAccount.data.checkName[grantAccount.type]]){
					return '未分配,未保存';
				}else {
					return '已分配,未保存';
				}
			}else{
				if(row[grantAccount.data.checkName[grantAccount.type]]){
					return '已分配';
				}else {
					return '未分配';
				}
			}
		}
};

$(function(){
	grantAccount.div = $('#systemGrantAccount');
	grantAccount.table1 = grantAccount.div.find('#grantAccountTable');
	grantAccount.table2 = grantAccount.div.find('#grantAccountTable2');
	grantAccount.id = window.top.grantAccountForRoleId;
	grantAccount.type = window.top.grantAccountForRoleType;
	window.top.grantAccountForRoleId = null;
	window.top.grantAccountForRoleType = null;
	
	//加载查询条件的部门列表
	app.loadSubDept(grantAccount.div.find('#grantAccountTable1Form').find('#deptId'));
	
	//加载数据
	//grantAccount.loadRole();
});