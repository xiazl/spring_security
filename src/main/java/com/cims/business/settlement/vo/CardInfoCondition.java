package com.cims.business.settlement.vo;

import com.cims.business.card.vo.Condition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-20 19:55
 * @description TODO
 **/

@Setter
@Getter
public class CardInfoCondition extends Condition {
    private Integer type = 1; // 默认入库结算 2 出库结算 显示结算时间用
    private Integer channel;  // 渠道
    private Integer projectId;  //  项目
}
