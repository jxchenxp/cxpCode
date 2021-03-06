package com.cxp.module.system.params.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.params.pojo.Params;
import com.cxp.page.PageInfo;

public interface ParamsDao {
	void save(Params params);
	void remove(String code);
	void update(Params params);
	Params get(String code);
	List<Params> list(@Param("params")Params params, @Param("page")PageInfo page);
	int count(@Param("params") Params params);
}
