package com.cxp.module.system.dict;

import java.util.List;

import com.cxp.module.system.dict.pojo.Dict;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

public interface DictService {
	void save(Dict dict);
	void update(Dict dict);
	void remove(String path);
	Dict get(String path);
	Dict getNotNull(String code);
	PageResult<Dict> list(Dict dict, PageInfo page);
	List<Dict> listSub(Dict dict);
}
