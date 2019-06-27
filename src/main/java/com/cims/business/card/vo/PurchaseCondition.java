package com.cims.business.card.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 4/15/19 5:56 PM
 * @description 卡片入库信息
 */

@Getter
@Setter
public class PurchaseCondition extends Condition{

    private Integer warehouseId; // 仓库Id

}
