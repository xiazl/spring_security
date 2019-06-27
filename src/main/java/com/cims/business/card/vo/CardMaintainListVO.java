package com.cims.business.card.vo;

import com.cims.common.enums.*;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-18 15:41
 * @description 维护列表展示对象
 **/

@Setter
@Getter
public class CardMaintainListVO {
    @NotNull(message = "id不能为空")
    private Long id;
    private Long maintainId;
    private Integer mainType;
    @ExcelTitle(value = "渠道", order = 1)
    private String channelName;
    @ExcelTitle(value = "银行", order = 2)
    private String bankName;
    @ExcelTitle(value = "户名", order = 3)
    private String owner;
    @NotEmpty
    @ExcelTitle(value = "卡号", order = 4)
    private String cardNo;
    private Integer status;
    @ExcelTitle(value = "身份证号", order = 6)
    private String idNo;
    @ExcelTitle(value = "手机号", order = 7)
    private String phoneNo;
    @ExcelTitle(value = "交易密码", order = 8)
    private String payPassword;
    @ExcelTitle(value = "来卡日期", order = 9)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;
    @ExcelTitle(value = "发生日期", order = 10)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @ExcelTitle(value = "出库日期", order = 11)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;
    @ExcelTitle(value = "项目", order = 12)
    private String projectName;
    private Integer disableType;

    private Integer freezeType;
    @ExcelTitle(value = "显示冻结原因", order = 15)
    private String reason;
    @ExcelTitle(value = "冻结金额", order = 16)
    private BigDecimal freezeAmount;

    private Integer processStatus;
    @ExcelTitle(value = "处理开销", order = 19)
    private BigDecimal overHead;
    @ExcelTitle(value = "回款金额", order = 20)
    private BigDecimal paybackAmount;
    @ExcelTitle(value = "回款部门", order = 21)
    private String department;
    @ExcelTitle(value = "回款账号", order = 22)
    private String account;
    @ExcelTitle(value = "完成日期", order = 23)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;

    private Integer processMethod;
    private String problemDesc;
    @ExcelTitle(value = "备注", order = 24)
    private String comment;

    private Date createTime;

    private String createUser;

    @ExcelTitle(value = "类型", order = 0)
    public String getMainTypeStr() {
        return EnumUtils.getEnum(CardTypeEnum.MainType.class, getMainType()).getViewValue();
    }

    @ExcelTitle(value = "状态", order = 5)
    public String getStatusStr() {
        return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
    }

    @ExcelTitle(value = "停用类型", order = 13)
    public String getDisableTypeStr() {
        if(getDisableType() == null){
            return null;
        }
        return EnumUtils.getEnum(DisableTypeEnum.class, getDisableType()).getViewValue();
    }

    @ExcelTitle(value = "冻结类型", order = 14)
    public String getFreezeTypeStr() {
        if(getFreezeType() == null){
            return null;
        }
        return EnumUtils.getEnum(FreezeTypeEnum.class, getFreezeType()).getViewValue();
    }

    @ExcelTitle(value = "处理方式", order = 17)
    public String getProcessMethodStr() {
        if(getProcessMethod() == null){
            return null;
        }
        return EnumUtils.getEnum(ProcessingEnum.class, getProcessMethod()).getViewValue();
    }

    @ExcelTitle(value = "处理状态", order = 18)
    public String getProcessStatusStr() {
        if(getProcessStatus() == null){
            return null;
        }
        return EnumUtils.getEnum(ProcessingStatusEnum.class, getProcessStatus()).getViewValue();
    }
}
