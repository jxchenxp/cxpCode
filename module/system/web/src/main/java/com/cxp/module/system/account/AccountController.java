package com.cxp.module.system.account;

import static com.cxp.web.common.ReqUtils.listPage;
import static com.cxp.web.common.ReqUtils.success;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.module.system.account.pojo.Account;
import com.cxp.module.system.account.pojo.AccountVo;
import com.cxp.page.PageInfo;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;
import com.cxp.project.config.ProjectConfig;
import com.cxp.web.common.ReqResult;

@RestController
@RequestMapping("/system/account")
public class AccountController {
	
	@Resource         
	private AccountService accountService;
	
	@Resource
	private ProjectConfig projectConfig;
	
	
	@RequestMapping("/save")
	public ReqResult save(HttpServletRequest request,@Validated Account account){
		//获取默认密码
		account.setPassword(projectConfig.getDefaultPassword());
		
		accountService.save(account);
		return success();
	}
	
	@RequestMapping("/remove")
	public ReqResult remove(@NotNull String account){
		accountService.remove(account);
		return success();
	}
	
	@RequestMapping("/update")
	public ReqResult update(@Validated Account account){
		accountService.update(account);
		return success();
	}
	
	@RequestMapping("resetPwd")
	public ReqResult resetPwd(String account){
		accountService.updatePassword(account, projectConfig.getDefaultPassword());
		return success();
	}
	
	@RequestMapping("/list")
	public ReqResult list(Account account, PageInfo pageInfo){
		PageQry<Account> qry = new PageQry<Account>();
		qry.setPage(pageInfo);
		qry.setQuery(account);
		PageResult<Account> page = accountService.list(qry);
		
		//把Account转换成vo
		List<Account> list = page.getResult();
		List<AccountVo> list2 = new ArrayList<AccountVo>(list.size());
		AccountVo accVo = null;
		for(Account item : list){
			accVo = new AccountVo();
			BeanUtils.copyProperties(item, accVo);
			list2.add(accVo);
		}
		PageResult<AccountVo> result = new PageResult<AccountVo>();
		result.setResult(list2);
		result.setTotalSize(page.getTotalSize());
		return listPage(result);
	}
	
	@RequestMapping("/get")
	public AccountVo get(String account){
		Account acc = accountService.getNotNull(account);
		AccountVo accVo = new AccountVo();
		BeanUtils.copyProperties(acc, accVo);
		return accVo;
	}
	
	@RequestMapping("/check")
	public boolean check(String account){
		Account acc = accountService.get(account);
		return acc == null ? true : false;
	}
}
