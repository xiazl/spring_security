package com.cims.framework.page;

import org.apache.commons.lang3.StringUtils;

/**
 * 分页对象bean
 *
 * @author baidu
 */

public class PageBean {
    /**
     * current page number
     */
    private int current = 1;

    /**
     * number of data items per page
     */
    private int pageSize = 50;

    /**
     * total number of data items
     */
    private long total;

    /**
     * sort filed
     */
    private String orderBy = "id";

    /**
     * sort type
     */
    private String orderType = "desc";

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {

        if (StringUtils.isEmpty(orderBy)){
            return orderBy;
        }

        StringBuilder sb = new StringBuilder(orderBy);
        int temp = 0;
        for(int i=0; i<orderBy.length(); i++){
            if(Character.isUpperCase(orderBy.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toLowerCase();
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
