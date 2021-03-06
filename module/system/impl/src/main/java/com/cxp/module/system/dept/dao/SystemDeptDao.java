package com.cxp.module.system.dept.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.dept.pojo.Dept;
import com.cxp.module.system.dept.pojo.DeptQuery;
import com.cxp.page.PageInfo;

public interface SystemDeptDao {
	int save(Dept dept);
	int remove(String path);
	int update(Dept dept);
	Dept get(int id);
	int count(@Param("dept") DeptQuery dept);
	List<Dept> list(@Param("dept") DeptQuery dept, @Param("page") PageInfo pageInfo);
	List<Dept> listSubDept(@Param("dept") DeptQuery dept);
	int getMaxSort(int parentId);
	String getMaxPath(int parentId);
}
