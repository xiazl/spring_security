package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 5/13/19 4:15 PM
 * @description 各仓库分类未用统计表
 */

@Getter
@Setter
public class WarehouseUnusedVO {
    // 仓库Id
    private Integer warehouseId;
    // 仓库名称
    private String warehouseName;
    // 总数量
    private Integer totalCount;
    // a类卡数量
    private Integer aCount;
    // b类卡数量
    private Integer bCount;
    // c类卡数量
    private Integer cCount;
    // 手机银行卡数量
    private Integer mobileBankCount;
    // 支付宝主卡数量
    private Integer alipayMainCount;
    // 支付宝下挂卡数量
    private Integer alipaySubCount;
}
