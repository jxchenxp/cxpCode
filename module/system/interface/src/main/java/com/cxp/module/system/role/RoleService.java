package com.cxp.module.system.role;

import com.cxp.module.system.role.pojo.Role;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

public interface RoleService {
	void save(Role role);
	void remove(int id);
	void update(Role role);
	Role getNotNull(int id);
	Role get(int id);
	PageResult<Role> list(Role role, PageInfo page);
}
