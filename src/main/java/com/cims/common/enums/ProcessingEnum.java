package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 20:05
 * @description 处理方式
 **/
public enum ProcessingEnum implements BaseEnum<ProcessingEnum> {

    INDEPENDENT_WITHHOLDING(1,"自主代扣"),
    CHANNEL_DEAL(2,"渠道处理"),
    SELF_THAWING(3,"自行解冻"),
    CARD_FEE_DEDUCTION(4,"卡费扣除");

    private Integer backValue;
    private String viewValue;

    ProcessingEnum(Integer backValue, String viewValue) {
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
