package com.cxp.page;

public class PageQry<T> {
	private T query;
	private PageInfo page;
	
	public T getQuery() {
		return query;
	}
	public void setQuery(T query) {
		this.query = query;
	}
	public PageInfo getPage() {
		return page;
	}
	public void setPage(PageInfo page) {
		this.page = page;
	}
}
