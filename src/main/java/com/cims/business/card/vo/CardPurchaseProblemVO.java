package com.cims.business.card.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.CardTypeEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author belly
 * @create 4/18/19 5:10 PM
 * @description 问题卡片入库对象 - 用于验证必填字段
 */

@Getter
@Setter
public class CardPurchaseProblemVO extends CardPurchaseBaseVO{

    // 描述处于status问题状态不能使用原因
    private String problemDesc;

    public void setProblemDesc(String problemDesc) {
        Assert.hasText(problemDesc, "不能使用原因字段不能为空");
        this.problemDesc = problemDesc;
    }
}
