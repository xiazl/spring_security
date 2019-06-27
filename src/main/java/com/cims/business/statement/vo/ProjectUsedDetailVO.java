package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 5/13/19 7:18 PM
 * @description 使用项目在用统计表 - 查询原始数据
 */

@Getter
@Setter
public class ProjectUsedDetailVO {
    // 银行名称
    private String bankName;
    // 项目名称
    private String projectName;
    // 卡片数量
    private Integer cardCount;
}
