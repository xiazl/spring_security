package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 19:45
 * @description 结算类型
 **/
public enum SettlementTypeEnum implements BaseEnum<SettlementTypeEnum> {
    SETTLEMENT_IN(1,"入库结算"),
    SETTLEMENT_OUT(2,"出库结算");

    private Integer backValue;
    private String viewValue;

    SettlementTypeEnum(Integer backValue, String viewValue) {
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
