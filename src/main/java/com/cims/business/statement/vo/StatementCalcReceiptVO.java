package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 4/24/19 10:05 PM
 * @description 项目应收款
 */

@Getter
@Setter
public class StatementCalcReceiptVO {
    // 项目Id
    private Integer projectId;
    // 应收款
    private BigDecimal amount;

    // 项目名称
    private String projectName;
}
