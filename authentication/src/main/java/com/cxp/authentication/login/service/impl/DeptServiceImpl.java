package com.cxp.authentication.login.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cxp.authentication.login.dao.DeptDao;
import com.cxp.authentication.login.model.Dept;
import com.cxp.authentication.login.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService{
	@Resource
	private DeptDao deptDao;

	@Override
	public Dept getDept(int id) {
		return deptDao.getDept(id);
	}

}
