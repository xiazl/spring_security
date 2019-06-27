package com.cims.business.card.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author baidu
 * @date 2019-04-18 16:36
 * @description 卡片维护模块
 **/

@Setter
@Getter
public class MaintainCondition extends Condition {
    private Integer status;
    private String idNo;
    private String phoneNo;
}
