package com.cims.common.enums;

/**
 * @author baidu
 * @date 2019-04-16 19:20
 * @description TODO
 **/

public class CardTypeEnum {

    public enum MainType implements BaseEnum<MainType>{
        PERSONAL_BANK(1,"个人银行"),
        MOBILE_BANK(2,"手机银行"),
        ALIPAY(3,"支付宝");

        private Integer backValue;
        private String viewValue;

        MainType(Integer backValue, String viewValue) {
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


    public enum SecondaryType implements BaseEnum<SecondaryType>{
        TYPE_A(1, 1,"A"),
        TYPE_B(1, 2,"B"),
        TYPE_C(1, 3,"C"),
        MOBILE_BANK(2, 4,"手机银行"),
        ALIPAY_MAIN(3, 5,"支付宝主卡"),
        ALIPAY_SUB(3, 6,"支付宝下挂卡");

        private Integer mainType;
        private Integer backValue;
        private String viewValue;

        SecondaryType(Integer mainType, Integer backValue, String viewValue) {
            this.mainType = mainType;
            this.backValue = backValue;
            this.viewValue = viewValue;
        }

        public Integer getMainType() {
            return mainType;
        }

        public void setMainType(Integer mainType) {
            this.mainType = mainType;
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

}

