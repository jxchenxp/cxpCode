package com.cxp.module.system.params;

import static com.cxp.web.common.ReqUtils.listPage;
import static com.cxp.web.common.ReqUtils.success;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.module.system.params.pojo.Params;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;
import com.cxp.web.common.ReqResult;

@RestController
@RequestMapping("/system/params")
public class ParamsController {
	@Resource
	private ParamsService paramsService;
	
	@RequestMapping("/save")
	public ReqResult save(HttpServletRequest request,@Validated Params params){
		paramsService.save(params);
		return success();
	}
	
	@RequestMapping("/remove")
	public ReqResult remove(@NotNull String code){
		paramsService.remove(code);
		return success();
	}
	
	@RequestMapping("/update")
	public ReqResult update(@Validated Params params){
		paramsService.update(params);
		return success();
	}
	
	@RequestMapping("/list")
	public ReqResult list(Params params, PageInfo pageInfo){
		PageResult<Params> page = paramsService.list(params, pageInfo);
		return listPage(page);
	}
	
	@RequestMapping("/get")
	public Params get(String code){
		Params params = paramsService.getNotNull(code);
		return params;
	}
	
	@RequestMapping("/check")
	public boolean check(String code){
		Params params = paramsService.get(code);
		return params == null ? true : false;
	}
}
