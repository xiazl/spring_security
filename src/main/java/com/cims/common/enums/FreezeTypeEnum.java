package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 20:04
 * @description 冻结类型
 **/
public enum FreezeTypeEnum implements BaseEnum<FreezeTypeEnum> {

    RISK_CONTROL(1,"风控"),
    MALFUNCTION(2,"故障"),
    ARTIFICIAL(3,"人为"),
    JUDICIAL(4,"司法"),
    SUSPECTED_JUSTICE(5,"疑似司法");

    private Integer backValue;
    private String viewValue;

    FreezeTypeEnum(Integer backValue, String viewValue) {
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
