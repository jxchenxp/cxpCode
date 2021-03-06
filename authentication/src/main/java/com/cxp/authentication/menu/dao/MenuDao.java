package com.cxp.authentication.menu.dao;

import java.util.List;

import com.cxp.authentication.menu.model.Menu;
import com.cxp.authentication.menu.model.MenuBo;
import com.cxp.authentication.menu.pojo.MenuResource;

public interface MenuDao {
	List<Menu> listMenu(MenuBo menu);
	
	List<MenuResource> listUri(MenuBo menu);
}
