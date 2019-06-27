package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author belly
 * @create 5/13/19 5:29 PM
 * @description 停用统计
 */

@Getter
@Setter
public class ChannelDisableTypeVO {
    // 渠道Id
    private Integer channelId;
    // 渠道名称
    private String channelName;
    // 总数
    private Integer totalCount;
    // 手动拉停数量
    private Integer proactiveDisableCount;
    // 渠道拉停数量
    private Integer channelDisableCount;
}
