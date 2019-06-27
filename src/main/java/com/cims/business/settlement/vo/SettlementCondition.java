package com.cims.business.settlement.vo;

import com.cims.business.card.vo.Condition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-19 14:36
 * @description 卡片结算模块
 **/

@Setter
@Getter
public class SettlementCondition extends Condition {
    private Integer type = 1; //  默认入库结算
    private Date startDate;
    private Date stopDate;
}
