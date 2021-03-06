package com.cxp.module.system.dept.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cxp.exception.NoFindException;
import com.cxp.module.system.dept.DeptService;
import com.cxp.module.system.dept.dao.SystemDeptDao;
import com.cxp.module.system.dept.pojo.Dept;
import com.cxp.module.system.dept.pojo.DeptQuery;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;
import com.cxp.service.tree.BasePathTree;

@Service
public class SystemDeptServiceImpl extends BasePathTree implements DeptService{
	@Resource
	private SystemDeptDao systemDeptDao;

	@Override
	public void save(Dept dept) {
		Dept parent = this.getNotNull(dept.getParentId());
		dept.setNodeLevel(parent.getNodeLevel() + 1);
		int maxSort = systemDeptDao.getMaxSort(dept.getParentId());
		dept.setSort(maxSort + 1);
		String maxPath = systemDeptDao.getMaxPath(dept.getParentId());
		dept.setPath(getNextPath(parent.getPath(), maxPath));
		dept.setNodeLevel(parent.getNodeLevel() + 1);
		
		//如果没有输入编号，则编号默认为路径
		if(StringUtils.isBlank(dept.getCode())){
			dept.setCode(dept.getPath());
		}
		
		dept.setGmtCreate(new Date());
		dept.setGmtModify(new Date());
		systemDeptDao.save(dept);
	}
	
	@Override
	public void remove(int id) {
		Dept dept = this.getNotNull(id);
		systemDeptDao.remove(dept.getPath() + "%");
	}

	@Override
	public void update(Dept dept) {
		dept.setGmtModify(new Date());
		systemDeptDao.update(dept);
	}

	@Override
	public Dept getNotNull(int id) {
		Dept dept = get(id);
		if(dept == null){
			throw new NoFindException("部门", id);
		}
		return dept;
	}

	@Override
	public Dept get(int id) {
		return systemDeptDao.get(id);
	}

	@Override
	public PageResult<Dept> list(PageQry<DeptQuery> query) {
		if(!StringUtils.isBlank(query.getQuery().getPath())){
			query.getQuery().setPath(query.getQuery().getPath() + "%");
		}
		query.getQuery().setAuthPath(query.getQuery().getAuthPath() + "%");
		
		PageResult<Dept> result = new PageResult<Dept>();
		int size = systemDeptDao.count(query.getQuery());
		result.setTotalSize(size);
		if(size > 0){
			result.setResult(systemDeptDao.list(query.getQuery(), query.getPage()));
		}else{
			result.setResult(new ArrayList<Dept>(0));
		}
		return result;
	}
	
	@Override
	public List<Dept> listSubDept(DeptQuery dept) {
		if(!StringUtils.isBlank(dept.getPath())){
			dept.setPath(dept.getPath() + "%");
		}
		dept.setAuthPath(dept.getAuthPath() + "%");
		return systemDeptDao.listSubDept(dept);
	}
	
	@Override
	public void exchange(int dept1, int sort1, int dept2, int sort2) {
		Dept dept = new Dept();
		dept.setId(dept1);
		dept.setSort(sort2);
		systemDeptDao.update(dept);
		
		dept.setId(dept2);
		dept.setSort(sort1);
		systemDeptDao.update(dept);
	}
	
}
