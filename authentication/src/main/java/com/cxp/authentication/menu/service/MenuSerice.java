package com.cxp.authentication.menu.service;

import java.util.List;

import com.cxp.authentication.menu.model.Menu;

public interface MenuSerice {
	List<Menu> listMenu(String project, String path, String account, String plat, int level);
	
	List<String> listUri(String project, String account, String plat);
}
