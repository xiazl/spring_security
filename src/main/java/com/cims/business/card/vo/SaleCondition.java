package com.cims.business.card.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 4/21/19 9:05 PM
 * @description 出卡查询条件
 */

@Getter
@Setter
public class SaleCondition extends Condition {
    //类型
    private Integer mainType;
    //分类
    private Integer secondaryType;
    //仓库Id
    private Integer warehouseId;
    //渠道Id
    private Integer channelId;
    //卡片状态
    private Integer status;
}
