package com.cims.business.settlement.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.CardTypeEnum;
import com.cims.common.enums.DisableTypeEnum;
import com.cims.common.enums.FreezeTypeEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-19 15:10
 * @description 卡片出库结算对象
 **/

@Setter
@Getter
public class CardOutSettlementVO {
    @ExcelTitle(value = "序号", order = 0)
    private Long id;
    private Integer mainType;
    @ExcelTitle(value = "银行", order = 2)
    private String bankName;
    @ExcelTitle(value = "户名", order = 3)
    private String owner;
    @ExcelTitle(value = "卡号", order = 4)
    private String cardNo;
    private Integer status; //  卡状态
    @ExcelTitle(value = "日租价", order = 6)
    private BigDecimal price;
    @ExcelTitle(value = "停用日期", order = 7)
    private Date disableDate;
    private Integer disableType;
    @ExcelTitle(value = "冻结日期", order = 9)
    private Date freezeDate;
    private Integer freezeType;
    @ExcelTitle(value = "上次结算日期", order = 11)
    private Date startDate;
    @ExcelTitle(value = "本次结算日期", order = 12)
    @NotNull(message = "stopDate不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date stopDate;
    @ExcelTitle(value = "结算天数", order = 13)
    private Integer days;
    @ExcelTitle(value = "应付款", order = 14)
    private BigDecimal realAmount;
    @ExcelTitle(value = "付款日期", order = 15)
    private Date paymentDate;
    @NotEmpty(message = "cardNos不能为空")
    private List<String> cardNos;

    @ExcelTitle(value = "类型", order = 1)
    public String getMainTypeStr() {
        if(mainType != null){
            return EnumUtils.getEnum(CardTypeEnum.MainType.class, getMainType()).getViewValue();
        }
        return null;
    }

    @ExcelTitle(value = "卡状态", order = 5)
    public String getStatusStr() {
        if(status != null){
            return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
        }
        return null;
    }

    @ExcelTitle(value = "停用原因", order = 8)
    public String getDisableTypeStr() {
        if(disableType != null){
            return EnumUtils.getEnum(DisableTypeEnum.class, getDisableType()).getViewValue();
        }
        return null;
    }

    @ExcelTitle(value = "冻结类型", order = 10)
    public String getFreezeTypeStr() {
        if(freezeType != null){
            return EnumUtils.getEnum(FreezeTypeEnum.class, getFreezeType()).getViewValue();
        }
        return null;
    }

}
