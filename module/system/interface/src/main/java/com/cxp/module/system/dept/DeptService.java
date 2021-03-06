package com.cxp.module.system.dept;

import java.util.List;

import com.cxp.module.system.dept.pojo.Dept;
import com.cxp.module.system.dept.pojo.DeptQuery;
import com.cxp.page.PageQry;
import com.cxp.page.PageResult;

public interface DeptService {
	void save(Dept dept);
	void remove(int id);
	void update(Dept dept);
	Dept getNotNull(int id);
	Dept get(int id);
	PageResult<Dept> list(PageQry<DeptQuery> query); 
	List<Dept> listSubDept(DeptQuery dept);
	void exchange(int dept1, int sort1, int dept2, int sort2);
}
