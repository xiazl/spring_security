package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 5/13/19 4:46 PM
 * @description 各仓库银行未用统计表
 */

@Getter
@Setter
public class WarehouseBankUnusedVO {
    // 银行名称
    private String bankName;
    // 总数
    private Integer totalCount;
    // 金边
    private Integer phnomPenhCount;
    // 金木棉
    private Integer goldKapokCount;
    // 菲律宾
    private Integer philippinesCount;
}
