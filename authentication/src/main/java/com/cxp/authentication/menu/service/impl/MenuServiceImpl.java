package com.cxp.authentication.menu.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cxp.authentication.menu.dao.MenuDao;
import com.cxp.authentication.menu.model.Menu;
import com.cxp.authentication.menu.model.MenuBo;
import com.cxp.authentication.menu.pojo.MenuResource;
import com.cxp.authentication.menu.service.MenuSerice;

@Service
public class MenuServiceImpl implements MenuSerice{
	@Resource
	private MenuDao menuDao;

	@Override
	public List<Menu> listMenu(String project, String path, String account, String plat, int level) {
		MenuBo menu = new MenuBo();
		menu.setProject(project);
		menu.setNodeLevel(level);
		menu.setPlat(plat);
		menu.setAccount(account);
		menu.setPath(StringUtils.isBlank(path) ? null : path + "%");
		return menuDao.listMenu(menu);
	}

	@Override
	public List<String> listUri(String project, String account, String plat) {
		MenuBo menu = new MenuBo();
		menu.setProject(project);
		menu.setPlat(plat);
		menu.setAccount(account);
		List<MenuResource> list = menuDao.listUri(menu);
		Iterator<MenuResource> it = list.iterator();
		
		List<String> urlList = new ArrayList<String>();
		
		String[] relationResources = null;
		String url = null;
		MenuResource menuResource = null;
		int len = 0;
		while(it.hasNext()){
			menuResource = it.next();
			url = menuResource.getMenuUrl();
			urlList.add(url + menuResource.getResourceCode());
			
			if(!StringUtils.isBlank(menuResource.getRelationResource())){
				relationResources = menuResource.getRelationResource().split(",");
				len = relationResources.length;
				for(int i = 0; i < len; i ++){
					if(relationResources[i].startsWith("/")){
						urlList.add(relationResources[i]);
					}else{
						urlList.add(url + relationResources[i]);
					}
				}
			}
		}
		return urlList;
	}

}
