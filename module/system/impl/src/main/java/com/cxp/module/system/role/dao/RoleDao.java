package com.cxp.module.system.role.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.role.pojo.Role;
import com.cxp.page.PageInfo;

public interface RoleDao {
	int save(Role role);
	int remove(int id);
	int update(Role role);
	Role get(int id);
	int count(@Param("role") Role role);
	List<Role> list(@Param("role") Role role, @Param("page") PageInfo pageInfo);
}
