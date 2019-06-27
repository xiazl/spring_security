package com.cims.framework.page;

import java.util.List;

/**
 * 分页查询返回bean
 *
 * @author baidu
 */
public class PageDataResult<T> {
	/**
	 * page parameter
	 */
	private PageBean pagination;

	/**
	 * An array that contains the actual objects
	 */
	private List<T> list = null;

	public PageBean getPagination() {
		return pagination;
	}

	public void setPagination(PageBean pagination) {
		this.pagination = pagination;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
