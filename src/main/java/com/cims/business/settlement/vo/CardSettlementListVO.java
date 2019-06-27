package com.cims.business.settlement.vo;

import com.cims.common.enums.*;
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
 * @date 2019-04-19 14:33
 * @description 结算记录
 **/

@Setter
@Getter
public class CardSettlementListVO {
    @NotEmpty(message = "id不能为空")
    private List<Long> ids;

    private Long id;

    private Integer status;   // 卡片状态

    private Integer mainType;

    private String bankName;

    private String owner;

    private String cardNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @NotNull(message = "stopDate不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date stopDate;
    @NotNull(message = "type不能为空")
    private Integer type;

    private Integer days;          // 结算天数

    private BigDecimal price;      // 结算日价

    private Date disableDate;          // 停用日期
    private Integer disableType;       // 停用原因
    private Date freezeDate;           // 冻结日期
    private Integer freezeType;        // 冻结类型
    private BigDecimal freezeAmount;   // 冻结金额
    private Integer processStatus;      // 处理状态
    private Integer processMethod;      // 处理方式

    private BigDecimal amount;     // 结算金额（出、入库结算）

    private BigDecimal deductAmount;   //  扣减金额（入库结算）

    private BigDecimal realAmount;     // 实际结算金额

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;          // 付款日期

    private Integer isLast;

    private Integer isDelete;

    private Long createUserId;

    private Date createTime;

    private Date date;

    public String getStatusStr() {
        return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
    }

    public String getDisableTypeStr() {
        if(disableType == null){
            return  null;
        }
        return EnumUtils.getEnum(DisableTypeEnum.class, getDisableType()).getViewValue();
    }

    public String getFreezeTypeStr() {
        if(freezeType == null){
            return null;
        }
        return EnumUtils.getEnum(FreezeTypeEnum.class, getFreezeType()).getViewValue();
    }

    public String getProcessStatusStr() {
        if(processStatus == null){
            return null;
        }
        return EnumUtils.getEnum(ProcessingStatusEnum.class, getProcessStatus()).getViewValue();
    }

    public String getProcessMethodStr() {
        if(processMethod == null){
            return null;
        }
        return EnumUtils.getEnum(ProcessingEnum.class, getProcessMethod()).getViewValue();
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDisableDate() {
        if(disableType != null) {
            return date;
        }
        return disableDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getFreezeDate() {
        if(freezeType != null) {
            return date;
        }
        return freezeDate;
    }
}
