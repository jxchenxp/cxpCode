package com.cxp.module.system.dict.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxp.exception.NoFindException;
import com.cxp.module.system.dict.DictService;
import com.cxp.module.system.dict.dao.DictDao;
import com.cxp.module.system.dict.pojo.Dict;
import com.cxp.page.PageInfo;
import com.cxp.page.PageResult;
import com.cxp.service.tree.BasePathTree;

@Service
public class DictServiceImpl extends BasePathTree implements DictService{
	@Resource
	private DictDao dictDao;

	@Override
	public void save(Dict dict) {
		int sort = dictDao.getMaxSort(dict.getParentPath());
		dict.setSort(sort + 1);
		if(StringUtils.isBlank(dict.getParentPath())){
			dict.setPath("#" + dict.getCode());
		}else{
			dict.setPath(dict.getParentPath() + "#" + dict.getCode());
		}
		
		//判断记录是否存在 
		Dict exist = dictDao.get(dict.getPath());
		if(exist != null){
			throw new RuntimeException("存在重复的code【"+ dict.getCode() +"】");
		}
		
		dictDao.save(dict);
	}

	@Override
	@Transactional
	public void update(Dict dict) {
		//parentPath不为空时，要判断是否修改了数据字典的上级
		if(!StringUtils.isBlank(dict.getParentPath())){
			Dict old = dictDao.get(dict.getPath());
			if(!old.getParentPath().equals(dict.getParentPath())){
				int sort = dictDao.getMaxSort(dict.getParentPath());
				dict.setSort(sort + 1);
				//重新设置path
				dict.setNewPath(dict.getParentPath() + "#" + dict.getCode());
				
				//判断记录是否存在 
				Dict exist = dictDao.get(dict.getNewPath());
				if(exist != null){
					throw new RuntimeException("存在重复的code【"+ dict.getCode() +"】");
				}
			}
		}else if(!StringUtils.isBlank(dict.getCode())){
			//code不为空要判断，是否已经存在
			//判断记录是否存在 
			Dict exist = dictDao.get(dict.getNewPath());
			if(exist != null){
				throw new RuntimeException("存在重复的code【"+ dict.getCode() +"】");
			}
		}
		dictDao.update(dict);
	}

	@Override
	public void remove(String path) {
		path += "%";
		dictDao.remove(path);
	}

	@Override
	public Dict get(String path) {
		return dictDao.get(path);
	}

	@Override
	public Dict getNotNull(String code) {
		Dict dict = get(code);
		if(dict == null){
			throw new NoFindException("数据字典", code);
		}
		return dict;
	}

	@Override
	public PageResult<Dict> list(Dict dict, PageInfo page) {
		PageResult<Dict> result = new PageResult<Dict>();
		int size = dictDao.count(dict);
		if(size > 0){
			result.setTotalSize(size);
			result.setResult(dictDao.list(dict, page));
		}else{
			result.setResult(new ArrayList<Dict>(0));
		}
		return result;
	}
	
	@Override
	public List<Dict> listSub(Dict dict) {
		return dictDao.listSub(dict);
	}
}
