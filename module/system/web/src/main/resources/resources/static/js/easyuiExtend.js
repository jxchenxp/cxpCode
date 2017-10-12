//表格
function loadFilter(data){
	var gridData = {};
	if(data.result){
		gridData.rows = data.result;
		gridData.total = data.size;
	}else{
		gridData = data;
	}
	return gridData;
};

//重写form ajax提交success方法，统一信息提示
(function($){
	var _submit = $.fn.form.methods['submit'];
	$.extend($.fn.form.methods,{
		submit: function(jq, options){
			var _succ = options.success;
			var _error = options.error;
			options.success = function(result){
				result = eval("(" + result + ")");
				var isErr = app.errDeal(result);
				if(isErr){
					if(_error){
						_error(result);
					}
				}else{
					$.messager.show({ 
			            title: '提示', 
			            msg: '操作成功', 
			            showType: 'show', 
			            timeout: 1000
			          }); 
					if(_succ){
						_succ(result);
					}
				}
			}
			return _submit(jq, options);
		}
	});
})(jQuery);