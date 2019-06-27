package com.cims.common.enums;

/**
 * @author belly
 * @create 2018/3/29 上午11:02
 */

public interface BaseEnum<E extends Enum<E>> {
    /**
     * backValue 页面隐藏值，数据库中贮存值
     * @return
     */
    Integer getBackValue();

    /**
     * viewValue 页面显示值
     * @return
     */
    String getViewValue();
}