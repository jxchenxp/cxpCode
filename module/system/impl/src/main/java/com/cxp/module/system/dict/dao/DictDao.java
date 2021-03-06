package com.cxp.module.system.dict.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxp.module.system.dict.pojo.Dict;
import com.cxp.page.PageInfo;

public interface DictDao {
	void save(Dict dict);
	void remove(String path);
	void update(Dict dict);
	Dict get(String path);
	List<Dict> listSub(Dict dict);
	List<Dict> list(@Param("dict") Dict dict, @Param("page") PageInfo page);
	int count(@Param("dict")Dict dict);
	int getMaxSort(String parentCode);
	String getMaxpath(String parentCode);
}
