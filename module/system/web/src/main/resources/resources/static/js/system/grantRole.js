var grantRole = { 
		data:{
			grant:{
				use:'useCheck',
				grant:'grantCheck'
			},
			roleIdsName:{
				use:'roleIds',
				grant:'grantRoleIds'
			}
		},
		
		init: function(){
			
		},
		
		loadRole: function(){
			var data = {};
			data.url = app.rootPath + 'listRoleForAccount';
			data.data = {account:grantRole.account};
			data.success = function(response){
				var selectedData = [];
				var unselectedData = [];
				var data = response.result;
				for(var index in data){
					if(data[index][grantRole.data.grant[grantRole.type]]){
						selectedData.push(data[index]);
					}else{
						unselectedData.push(data[index]);
					}
				}
				
				//加载表格数据
				grantRole.table1.datagrid('loadData',unselectedData);
				grantRole.table2.datagrid('loadData',selectedData);
			};
			$.ajax(data);
		},
		
		dbClickTable1Row: function(index,row){
			grantRole.table1.datagrid('deleteRow',index);
			grantRole.table2.datagrid('appendRow',row);
		},
		dbClickTable2Row: function(index,row){
			grantRole.table2.datagrid('deleteRow',index);
			grantRole.table1.datagrid('appendRow',row);
		},
		save: function(){
			var data = {};
			data.url = app.rootPath + 'grantRoleForAccount';
			data.data = {account:grantRole.account,type:grantRole.type};
			data.data[grantRole.data.roleIdsName[grantRole.type]] = grantRole.getSelectRole();
			data.success = function(response){
				app.successMsg();
			};
			$.ajax(data);
		},
		getSelectRole: function(){
			var rows = grantRole.table2.datagrid('getRows');
			console.log(rows);
			var ids = '';
			for(var index in rows){
				console.log(rows[index]);
				ids += ';' + rows[index].id;
			}
			if(ids != ''){
				ids = ids.substring(1);
			}
			return ids;
		}
};

$(function(){
	grantRole.div = $('#systemGrantRole');
	grantRole.table1 = grantRole.div.find('#grantRoleTable');
	grantRole.table2 = grantRole.div.find('#grantRoleTable2');
	grantRole.account = window.top.grantRoleForAccount;
	grantRole.type = window.top.grantRoleForAccountType;
	window.top.grantRoleForAccount = null;
	window.top.grantRoleForAccountType = null;
	
	//加载数据
	grantRole.loadRole();
});