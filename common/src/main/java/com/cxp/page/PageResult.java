package com.cxp.page;

import java.util.List;

public class PageResult<T> {
	private int totalSize;
	private List<T> result;
	
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
}
