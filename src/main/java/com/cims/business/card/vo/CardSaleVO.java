package com.cims.business.card.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author belly
 * @create 4/21/19 10:38 PM
 * @description 卡片出库对象
 */

@Getter
@Setter
public class CardSaleVO {
    // id
    private Long id;
    // 卡号
    private List<String> cardNos;
    // 项目id
    @NotNull(message = "项目不能为空")
    private Integer projectId;
    // 价格
    @NotNull(message = "价格不能为空")
    private BigDecimal price;
    // 出库日期
    @NotNull(message = "出库日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;
    // 接收人名称
    @NotNull(message = "接收人不能为空")
    private String receiver;
}
