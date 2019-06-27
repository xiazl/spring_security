package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 20:05
 * @description 处理状态 处理状态 1 处理中 2 未完成 3 按时完成 4 逾期完成 5 被代扣 6 不需处理
 **/
public enum ProcessingStatusEnum implements BaseEnum<ProcessingStatusEnum> {

    PROCESSING(1,"处理中"),
    UN_COMPLETE(2,"未完成"),
    COMPLETE_ON_TIME(3,"按时完成"),
    OVERDUE_COMPLETION(4,"逾期完成"),
    BETAINED(5,"被代扣"),
    NO_DEAL(6,"不需处理");

    private Integer backValue;
    private String viewValue;

    ProcessingStatusEnum(Integer backValue, String viewValue) {
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
