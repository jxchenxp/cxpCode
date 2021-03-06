package com.cxp.service.tree;

import org.apache.commons.lang3.StringUtils;

import com.cxp.exception.BizException;

public abstract class BasePathTree extends BaseTree{
	/**
	 * 计算下一个path
	 * @param path
	 * @return
	 */
	protected String getNextPath(String parentPath, String path){
		return getNextPath(parentPath, path, 4);
	}
	/**
	 * 计算下一个path
	 * @param path
	 * @return
	 */
	protected String getNextPath(String parentPath, String path,int length){
		if(StringUtils.isBlank(path)){
			return parentPath + String.format("%0"+ length +"d", 1);
		}
		String suf = path.substring(path.length() - length, path.length());
		int num = Integer.parseInt(suf);
		if(num >= 9999){
			throw new BizException("0001","部门path编号已超出最大值");
		}
		return parentPath + String.format("%0"+ length +"d", num + 1);
	}
}
