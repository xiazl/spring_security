package com.cims.business.card.vo;

import com.cims.common.enums.FreezeTypeEnum;
import com.cims.common.enums.ProcessingStatusEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-18 15:50
 * @description 卡片冻结展示对象
 **/

@Setter
@Getter
public class CardFreezeVO {
    private Long id;
    @ExcelTitle(value = "银行", order = 0)
    private String bankName;
    @ExcelTitle(value = "户名", order = 1)
    private String owner;
    @ExcelTitle(value = "卡号", order = 2)
    private String cardNo;
    @ExcelTitle(value = "省", order = 3)
    private String province;
    @ExcelTitle(value = "市", order = 4)
    private String city;
    @ExcelTitle(value = "行", order = 5)
    private String branchName;
    @ExcelTitle(value = "冻结日期", order = 6)
    @NotNull(message = "date不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @NotNull(message = "freezeType不能为空")
    @Range(min=1, max=5,message = "freezeType请选择指定的值")
    private Integer freezeType;
    @ExcelTitle(value = "冻结显示原因", order = 8)
    @NotEmpty(message = "reason不能为空")
    private String reason;
    @ExcelTitle(value = "冻结金额", order = 9)
    @NotNull(message = "freezeAmount不能为空")
    private BigDecimal freezeAmount;

    @NotNull(message = "processStatus不能为空")
    @Range(min=1, max=6,message = "processStatus请选择指定的值")
    private Integer processStatus;

    private BigDecimal overHead;         // 处理开销

    private BigDecimal paybackAmount;    // 回款金额

    private String department;           // 回款部门

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date finishDate;             // 付款日期

    private String account;              // 付款账号
    @Range(min=1, max=4,message = "processMethod请选择指定的值")
    private Integer processMethod;       // 处理方式

    @NotEmpty(message = "cardNos不能为空")
    private List<String> cardNos;

    private String comment;
    @ExcelTitle(value = "冻结类型", order = 7)
    private String freezeTypeStr;
    @ExcelTitle(value = "处理状态", order = 10)
    private String processStatusStr;

    public Integer getFreezeType() {
        if(!StringUtils.isEmpty(freezeTypeStr)){
            return EnumUtils.getEnum(FreezeTypeEnum.class, getFreezeTypeStr()).getBackValue();
        }
        return freezeType;
    }

    public Integer getProcessStatus() {
        if(!StringUtils.isEmpty(processStatusStr)){
            return EnumUtils.getEnum(ProcessingStatusEnum.class, getProcessStatusStr()).getBackValue();
        }
        return processStatus;
    }
}
