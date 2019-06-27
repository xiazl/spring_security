package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 5/13/19 5:20 PM
 * @description 渠道存量统计表
 */

@Getter
@Setter
public class ChannelStockVO {
    // 渠道Id
    private Integer channelId;
    // 渠道名称
    private String channelName;
    // 总数
    private Integer totalCount;
    // 未用卡数量
    private Integer unusedCount;
    // 已用卡数量
    private Integer usedCount;
}
