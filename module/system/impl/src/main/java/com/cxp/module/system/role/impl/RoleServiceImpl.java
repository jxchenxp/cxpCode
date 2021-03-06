package com.cxp.module.system.role.impl;

import java.util.ArrayList;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cxp.exception.NoFindException;
import com.cxp.module.system.role.RoleService;
import com.cxp.module.system.role.dao.RoleDao;
import com.cxp.module.system.role.pojo.Role;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

@Service
public class RoleServiceImpl implements RoleService{
	@Resource
	private RoleDao roleDao;

	@Override
	public void save(Role role) {
		if(StringUtils.isEmpty(role.getComment())){
			role.setComment(" ");
		}
		role.setGmtCreate(new Date());
		role.setGmtModify(new Date());
		
		roleDao.save(role);
	}

	@Override
	public void remove(int id) {
		roleDao.remove(id);
	}

	@Override
	public void update(Role role) {
		role.setGmtModify(new Date());
		roleDao.update(role);
	}

	@Override
	public Role getNotNull(int id) {
		Role role = this.get(id);
		if(role == null){
			throw new NoFindException("角色",id);
		}
		return role;
	}

	@Override
	public Role get(int id) {
		return roleDao.get(id);
	}

	@Override
	public PageResult<Role> list(Role role, PageInfo page) {
		role.setAuthPath(role.getAuthPath() + "%");
		
		PageResult<Role> result = new PageResult<Role>();
		int size = roleDao.count(role);
		result.setTotalSize(size);
		
		if(size > 0){
			result.setResult(roleDao.list(role,page));
		}else{
			result.setResult(new ArrayList<Role>(0));
		}
		return result;
	}

}
