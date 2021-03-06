package com.cxp.authentication.menu.controller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cxp.authentication.consts.Consts;
import com.cxp.authentication.login.model.UserInfo;
import com.cxp.authentication.menu.model.Menu;
import com.cxp.authentication.menu.service.MenuSerice;
import com.cxp.project.config.ProjectConfig;

@RestController
public class MenuController {
	@Resource
	private MenuSerice menuService;
	
	@Resource
	private ProjectConfig projectConfig;
	
	@RequestMapping("/listMenu")
	@ResponseBody
	public List<Menu> listMenu(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		
		String levelStr = request.getParameter("level");
		int level = StringUtils.isEmpty(levelStr) ? 0 : Integer.parseInt(levelStr);
		String path = request.getParameter("path");
		return menuService.listMenu(projectConfig.getCode(), path, userInfo.getAccount().getAccount(), userInfo.getPlat(), level);
	}
	
	@RequestMapping("/listGrantUrls")
	@ResponseBody
	public Set<String> listGrantUris(HttpServletRequest request){
		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo)session.getAttribute(Consts.USER_SESSION_NAME);
		return userInfo.getGrantUris();
	}
}
