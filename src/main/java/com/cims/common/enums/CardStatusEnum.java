package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 19:18
 * @description 卡片状态
 **/
public enum CardStatusEnum implements BaseEnum<CardStatusEnum> {
    UNUSED(0,"未用"),
    USED(1,"在用"),
    DISABLE(2,"停用"),
    FREEZE(3,"冻结"),
    PROBLEM(4,"问题");

    private Integer backValue;
    private String viewValue;

    CardStatusEnum(Integer backValue, String viewValue) {
        this.backValue = backValue;
        this.viewValue = viewValue;
    }

    public Integer getBackValue() {
        return backValue;
    }

    public void setBackValue(Integer backValue) {
        this.backValue = backValue;
    }

    public String getViewValue() {
        return viewValue;
    }

    public void setViewValue(String viewValue) {
        this.viewValue = viewValue;
    }
}

