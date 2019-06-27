package com.cims.business.card.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.CardTypeEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 4/21/19 9:32 PM
 * @description
 */

@Getter
@Setter
public class CardSaleListVO {

    // 主键id
    private Long id;
    // 类型 0 - 个人银行，1 - 手机银行， 2 - 支付宝
    private Integer mainType;
    // 二级分类: 个人银行分类下包含 [0 - A, 1 - B, 2 - C], 手机银行分类下包含[3-手机银行], 支付宝分类下包含 [4-支付宝主卡, 5-支付宝下挂卡]
    private Integer secondaryType;
    // 银行名称
    private String bankName;
    // 户名
    private String owner;
    // 卡号
    private String cardNo;
    // 身份证号
    private String idNo;
    // U盾密码
    private String ukeyPassword;
    // 状态0未用,1在用,2停用,3冻结,4问题
    private Integer status;
    // 手机号
    private String phoneNo;
    // 手机密码
    private String phonePassword;
    // 仓库名文本
    private String warehouseName;
    // 渠道文本
    private String  channelName;


    // 类型文本
    private String mainTypeStr;
    // 二级分类文本
    private String secondaryTypeStr;
    // 状态文本
    private String statusStr;


    public String getMainTypeStr(){
        if (getMainType() != null){
            return EnumUtils.getEnum(CardTypeEnum.MainType.class, getMainType()).getViewValue();
        }
        return mainTypeStr;
    }

    public String getSecondaryTypeStr(){
        if (getSecondaryType() != null){
            return EnumUtils.getEnum(CardTypeEnum.SecondaryType.class, getSecondaryType()).getViewValue();
        }
        return secondaryTypeStr;
    }

    public String getStatusStr(){
        if (getStatus() != null){
            return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
        }
        return statusStr;
    }


}
