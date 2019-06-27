package com.cims.framework.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 *  分页返回参数封装
 *
 * @author baidu
 */
public class PageHelp {

	/**
	 * 分页参数返回封装
	 * @param data
	 * @param <T>
	 * @return
	 */
	public final static <T> PageDataResult<T> getDataResult(List<T> data,PageBean pageBean) {
		PageDataResult<T> dataResult = new PageDataResult<>();
		dataResult.setList(data);

		PageInfo<T> page = new PageInfo<T>(data);
		pageBean.setCurrent(page.getPageNum());//当前页
		pageBean.setTotal(page.getTotal());//总记录数
		pageBean.setPageSize(page.getPageSize());//每页条数
		dataResult.setPagination(pageBean);

		return dataResult;
	}

	/**
	 * 空数据返回
	 * @param <T>
	 * @return
	 */
	public final static <T> PageDataResult<T> getDataResult(PageBean pageBean) {
		PageDataResult<T> dataResult = new PageDataResult<>();
		dataResult.setList(Collections.EMPTY_LIST);

		PageInfo<T> page = new PageInfo<T>();
		pageBean.setCurrent(page.getPageNum());//当前页
		pageBean.setTotal(page.getTotal());//总记录数
		pageBean.setPageSize(page.getPageSize());//每页条数
		dataResult.setPagination(pageBean);

		return dataResult;
	}


	/**
	 * 分页、字段排序
	 * @param pageBean
	 */
	public static void pageAndOrderBy(PageBean pageBean){
		if (StringUtils.isNotEmpty(pageBean.getOrderBy()) && StringUtils.isNotEmpty(pageBean.getOrderBy())) {
			PageHelper.startPage(pageBean.getCurrent(), pageBean.getPageSize(),
					pageBean.getOrderBy() + " " + pageBean.getOrderType());
		} else {
			PageHelper.startPage(pageBean.getCurrent(), pageBean.getPageSize());
		}
	}

}
