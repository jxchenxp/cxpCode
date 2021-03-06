package com.cxp.module.system.params;

import com.cxp.module.system.params.pojo.Params;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

public interface ParamsService {
	void save(Params params);
	void update(Params params);
	void remove(String code);
	Params get(String code);
	Params getNotNull(String code);
	PageResult<Params> list(Params params, PageInfo page); 
}
