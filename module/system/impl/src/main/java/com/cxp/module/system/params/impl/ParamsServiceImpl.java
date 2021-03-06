package com.cxp.module.system.params.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cxp.exception.NoFindException;
import com.cxp.module.system.params.ParamsService;
import com.cxp.module.system.params.dao.ParamsDao;
import com.cxp.module.system.params.pojo.Params;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;

@Service
public class ParamsServiceImpl implements ParamsService{
	@Resource
	private ParamsDao paramsDao;

	@Override
	public void save(Params params) {
		paramsDao.save(params);
	}

	@Override
	public void update(Params params) {
		paramsDao.update(params);
	}

	@Override
	public void remove(String code) {
		paramsDao.remove(code);
	}

	@Override
	public Params get(String code) {
		return paramsDao.get(code);
	}

	@Override
	public Params getNotNull(String code) {
		Params params = paramsDao.get(code);
		if(params == null){
			throw new NoFindException("参数", code);
		}
		return params;
	}

	@Override
	public PageResult<Params> list(Params params, PageInfo page) {
		PageResult<Params> result = new PageResult<Params>();
		
		int size = paramsDao.count(params);
		if(size > 0){
			result.setTotalSize(size);
			result.setResult(paramsDao.list(params, page));
		}else{
			result.setResult(new ArrayList<Params>(0));
		}
		return result;
	}
}
