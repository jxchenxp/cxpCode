var app = {};

app.project = 'manager';

app.str = {};

app.rootPath = getProjectRootPath();

//登录用户所有权限
app.grantUris = {};

//用于页面间传递数据
app.transData = null;

//初始化函数
$(function(){
	loadGrantUrls();
	
	addValidateRule();
});

//加载用户权限
function loadGrantUrls(){
	var data = {};
	data.url = app.rootPath + '/listGrantUrls';
	data.success = function(urls){
		for(var i = 0; i < urls.length; i++){
			app.grantUris[urls[i]] = true;
		}
	};
	$.ajax(data);
};

function getProjectRootPath(){
	var pathname = window.location.pathname;
	var pos = pathname.indexOf('/',1);
	if(pos > 0){
		pathname = pathname.substring(0,pos);
	}
	if(pathname.charAt(pathname.length - 1) != '/'){
		pathname = pathname + '/';
	}
	return pathname;
};

app.str.isBlank = function(str){
	if(Object.prototype.toString.call(str) != "[object String]"){
		return false;
	}
	if(str == null || str == undefined || str == ""){
		return true;
	}
	
	var len = str.length;
	for(var i = 0; i < len; i++){
		if(str.charAt(i) != ' '){
			return false;
		}
	}
	return true;
};

app.isNull = function(obj){
	if(obj == null){
		return true;
	}
	if(obj == undefined){
		return true;
	}
	return false;
};
app.validateOper = function(path, resource){
	return app.grantUris[path + resource] != undefined;
};

//资源权限控制
app.validateResources = function(div, path, resources){
	if(!Array.isArray(resources)){
		//单个资源进行处理
		if(app.grantUris[path + resources]){
			div.find('#' + resources).removeClass('hide');
		}
	}else{
		$(resources).each(function(index, resource){
			if(app.grantUris[path + resource]){
				div.find('#' + resource).removeClass('hide');
			}
		});
	}
};

//默认验证的资源
app.defaultValidateResource = function(div,path, resources){
	var defaultResources = ['save','remove','update','import','export','print'];
	if(!app.isNull(resources)){
		defaultResources = defaultResources.concat(resources);
	}
	app.validateResources(div,path,defaultResources);
};

app.loadFromData = function(form,url,params){
	var data = {};
	data.url = url;
	if(!app.isNull(params)){
		data.data = params;
	};
	data.success = function(jsonData){
		form.form('load',jsonData);
	};
	$.ajax(data);
};

//加载当前用户下的所有部门数据
app.loadSubDept = function(deptTree){
	var data = {};
	data.url = 'listSubDept';
	data.success = function(jsonData){
		deptTree.combotree('loadData',app.convertDeptNode(jsonData));
	}
	$.ajax(data);
};

app.convertDeptNode = function(depts){
	var newDeptMap = {};
	var newDepts = [];
	var dept, newDept;
	var len = depts.length;
	for(var i = 0; i < len; i++){
		dept = depts[i];
		newDept = {};
		newDept.id = dept.id;
		newDept.text = dept.name;
		newDept.code = dept.code;
		newDept.status = dept.status;
		newDept.children = [];
		newDeptMap[dept.id] = newDept;
		
		if(newDeptMap[dept.parentId]){
			newDeptMap[dept.parentId].children.push(newDept);
		}else{
			newDepts.push(newDept);
		}
	}
	return newDepts;  
};

//加载数据字典
app.loadDict = function(dictTree,data){
	var data = {};
	data.url = app.rootPath + 'system/dict/listSub';
	data.success = function(jsonData){
		dictTree.combotree('loadData',app.convertDictNode(jsonData));
	}
	$.ajax(data);
};

app.convertDictNode = function(dicts){
	var newDictMap = {};
	var newDicts = [];
	var dict, newDict;
	var len = dicts.length;
	for(var i = 0; i < len; i++){
		dict = dicts[i];
		newDict = {};
		newDict.id = dict.path;
		newDict.path = dict.path;
		newDict.text = dict.name;
		newDict.code = dict.code;
		newDict.status = dict.status;
		newDict.groups = dict.groups;
		newDict.comment = dict.comment;
		newDict.children = [];
		newDictMap[dict.path] = newDict;
		
		if(newDictMap[dict.parentPath]){
			newDictMap[dict.parentPath].children.push(newDict);
		}else{
			newDicts.push(newDict);
		}
	}
	return newDicts;  
};

app.errDeal = function(data){
	if(data.resCode &&  data.resCode != '0000'){
		$.messager.alert('提示','操作失败:' + data.msg);
		return true;
	}
	return false;
};

//格式化
app.formatter = function(value,row,index){
	var field = this.fName;
	if(field == undefined){
		field = this.field;
	}
	var map = _constant[field];
	if(map == undefined){
		return '';
	}
	var formatV = map[value];
	return formatV == undefined ? '' : formatV;
};

app.parseConsToKy = function(item){
	var map = _constant[item];
	var data = [];
	for(var index in map){
		data.push({value:index,text:map[index]});
	}
	return data;
};


app.successMsg = function(){
	$.messager.show({ 
        title: '提示', 
        msg: '操作成功', 
        showType: 'show', 
        timeout: 1000
      }); 
};

//添加tab标签
app.addTab = function(id, text, url){
	addTab(id, text, url);
};

app.getTab = function(index){
	return getTab(index);
};

app.closeTabIfExist = function(index){
	return closeTabIfExist(index);
};

//全局数据队列，
app.setTransData = function(obj){
	this.transData = obj;
};
app.getTransData = function(){
	return this.transData;
}

/**easyui validate change beg*/
app.events ={
    focus: _a,
    blur: _f,
    mouseenter: _13,
    mouseleave: _16,
    click: function(e) {
        var t = $(e.data.target);
        if (t.attr("type") == "checkbox" || t.attr("type") == "radio") {
            t.focus().validatebox("validate");
        }
    }
};
function _a(e) {
    var _b = e.data.target;
    var _c = $.data(_b, "validatebox");
    var _d = _c.options;
    if ($(_b).attr("readonly")) {
        return;
    }
    _c.validating = true;
    _c.value = _d.val(_b); (function() {
        if (!$(_b).is(":visible")) {
            _c.validating = false;
        }
        if (_c.validating) {
            var _e = _d.val(_b);
            if (_c.value != _e) {
                _c.value = _e;
                if (_c.vtimer) {
                    clearTimeout(_c.vtimer);
                }
                _c.vtimer = setTimeout(function() {
                    $(_b).validatebox("validate");
                },
                _d.delay);
            } else {
                if (_c.message) {
                    _d.err(_b, _c.message);
                }
            }
            //_c.ftimer = setTimeout(arguments.callee, _d.interval);
        }
    })();
};
function _f(e) {
    var _10 = e.data.target;
    var _11 = $.data(_10, "validatebox");
    var _12 = _11.options;
    _11.validating = false;
    if (_11.vtimer) {
        clearTimeout(_11.vtimer);
        _11.vtimer = undefined;
    }
    if (_11.ftimer) {
        clearTimeout(_11.ftimer);
        _11.ftimer = undefined;
    }
    if (_12.validateOnBlur) {
        setTimeout(function() {
            $(_10).validatebox("validate");
        },
        0);
    }
    _12.err(_10, _11.message, "hide");
};
function _13(e) {
    var _14 = e.data.target;
    var _15 = $.data(_14, "validatebox");
    _15.options.err(_14, _15.message, "show");
};
function _16(e) {
    var _17 = e.data.target;
    var _18 = $.data(_17, "validatebox");
    if (!_18.validating) {
        _18.options.err(_17, _18.message, "hide");
    }
};
/**easyui validate change end*/

/**easyui validate add rule start*/
function addValidateRule(){
	$.extend($.fn.validatebox.defaults.rules,{
		unique:{
			validator: function(value, param) {
                var data = {};
                data[param[1]] = value;
                var result = $.ajax({
                    url: app.rootPath + param[0],
                    dataType: "json",
                    data: data,
                    async: false,
                    cache: false,
                    type: "post"
                }).responseText;
                return result == "true";
            },
            message: "已经存在"
		},
		phoneNum: { //验证手机号   
	        validator: function(value, param){ 
	         return /^1[3-8]+\d{9}$/.test(value);
	        },    
	        message: '请输入正确的手机号码。'   
	    },
	    telNum:{ //既验证手机号，又验证座机号
	      validator: function(value, param){ 
	          return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);
	         },    
	         message: '请输入正确的电话号码。' 
	    },
        zhLength: {
            validator: function(value, param) {
            	var len = 0;
            	for(var i =0; i < value.length; i++){
            		if((/[\u4e00-\u9fa5]+/).test(value.charAt(i))){
            			len += 3;
            		}else{
            			len ++;
            		}
            	}
                return len >= param[0] && len <= param[1];
            },
            message: '长度必须在{0}至{1}个字节，中文算3个字节'
        }
	});
}
/**easyui validate add rule end*/


/**jquery 扩展 start*/
(function($){
	$.fn.extend({
		serializeJson: function(){
			var arr = this.serializeArray();
			var json = {};
			
			var len = arr.length;
			var item;
			for(var i = 0; i < len; i++){
				item = arr[i];
				json[item['name']] = item['value'];
			}
			return json;
		}
	});
	
	//重写ajax方法	
	_ajax = $.ajax;
	$.ajax = function(opt){
		var cusErr = opt.error;
		var cusSuc = opt.success;
		var cusComplete = opt.cusComplete;
		
		//增强
		$.extend(opt,{
			//增强
			error : function(XMLHttpRequest, textStatus, e){
				var result = XMLHttpRequest.responseText;
				if(result){
					app.errDeal(result);
				}
				if(cusErr){
					cusErr(result);
				}
			},
			success : function(data, textStatus){
				var isErr = app.errDeal(data);
				if(isErr){
					if(cusErr){
						cusErr(result);
					}
				}else{
					if(cusSuc){
						cusSuc(data, textStatus);
					}
				}
			}
		});
		return _ajax(opt);
	};
})(jQuery);
/**jquery 扩展 end*/