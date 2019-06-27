package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author belly
 * @create 5/13/19 2:04 PM
 * @description 报表统计模块查询条件
 */

@Getter
@Setter
public class StatementCondition {
    // 开始时间
    private Date startDate;
    // 结束时间
    private Date stopDate;
}
