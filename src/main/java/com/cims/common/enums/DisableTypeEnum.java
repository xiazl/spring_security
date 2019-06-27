package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 19:43
 * @description 停用原因类型
 **/
public enum DisableTypeEnum implements BaseEnum<DisableTypeEnum> {
    PROACTIVE_DISABLE(1,"主动拉停"),
    CHANNEL_DISABLE(2,"渠道拉停");

    private Integer backValue;
    private String viewValue;

    DisableTypeEnum(Integer backValue, String viewValue) {
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
