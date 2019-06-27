package com.cims.business.settlement.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-19 14:33
 * @description 结算选卡列表
 **/

@Setter
@Getter
public class CardInfoListVO {
    private String bankName;

    private String owner;

    private String cardNo;

    private Integer status;            // 卡状态

    private String channelName;        // 入库渠道

    private String ckr;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;         // 入库日期

    private BigDecimal purchasePrice;  // 入库价

    private String projectName;        //  出库项目
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;             // 出库日期

    private BigDecimal salePrice;      // 出库价

    private String receiver;           // 接收人
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastDate;             // 上次结算终止日

    public String getStatusStr() {
        return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
    }

}
