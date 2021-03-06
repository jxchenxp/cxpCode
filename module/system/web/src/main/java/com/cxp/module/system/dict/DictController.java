package com.cxp.module.system.dict;

import static com.cxp.web.common.ReqUtils.listPage;
import static com.cxp.web.common.ReqUtils.success;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.consts.GlobalConsts;
import com.cxp.module.system.dict.pojo.Dict;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;
import com.cxp.web.common.ReqResult;

@RestController
@RequestMapping("/system/dict")
public class DictController {
	@Resource
	private DictService dictService;
	
	@RequestMapping("/list")
	public ReqResult list(HttpServletRequest request, Dict dict,PageInfo page){
		PageResult<Dict> result = dictService.list(dict, page);
		return listPage(result);
	}
	
	@RequestMapping("/listSub")
	public List<Dict> listSub(HttpServletRequest request, Dict dict){
		List<Dict> list = dictService.listSub(dict);
		return list;
	}
	
	@RequestMapping("/save")
	public ReqResult save(HttpServletRequest request,@Validated Dict dict){
		dictService.save(dict);
		return success();
	} 
	
	@RequestMapping("/update")
	public ReqResult update(HttpServletRequest request,@Validated Dict dict){
		dictService.update(dict);
		return success();
	}
	
	@RequestMapping("/disable")
	public ReqResult disable(HttpServletRequest request,String path){
		Dict dict = new Dict();
		dict.setPath(path);
		dict.setStatus(GlobalConsts.STATUS_DISABLE);
		dictService.update(dict);
		return success();
	}
	
	@RequestMapping("/enable")
	public ReqResult enable(HttpServletRequest request,String path){
		Dict dict = new Dict();
		dict.setPath(path);
		dict.setStatus(GlobalConsts.STATUS_NORMAL);
		dictService.update(dict);
		return success();
	}
	
	@RequestMapping("/remove")
	public ReqResult remove(String path){
		dictService.remove(path);
		return success();
	}
	
	@RequestMapping("/get")
	public Dict get(String path){
		return dictService.get(path);
	}
	
	@RequestMapping("/check")
	public boolean check(String path){
		Dict dict = dictService.get(path);
		return dict == null ? true : false;
	} 
}
