package com.cims.business.card.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author baidu
 * @date 2019-04-17 14:26
 * @description 列表查询
 **/

@Getter
@Setter
public class Condition {
    private String cardNo;    // 卡号
    private String bankName;  // 银行
    private String owner;     // 户名
}
