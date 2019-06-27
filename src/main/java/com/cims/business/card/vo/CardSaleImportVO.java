package com.cims.business.card.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.DateUtils;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author belly
 * @create 5/16/19 4:26 PM
 * @description 销售记录导入
 */

@Getter
@Setter
public class CardSaleImportVO {
    // 卡号
    @NotEmpty(message = "卡号不能为空")
    @ExcelTitle(value = "卡号", order = 3)
    private String cardNo;

    // 状态 - 0未用,1在用,2停用,3冻结,4问题
    private Integer status;
    // 状态文本
    @ExcelTitle(value = "状态", order = 15)
    private String statusStr;

    // 项目Id
    private Integer projectId;
    // 项目名称
    @NotEmpty(message = "项目名称不能为空")
    @ExcelTitle(value = "项目名称", order = 26)
    private String projectName;

    // 价格
    @NotNull(message = "价格不能为空")
    @ExcelTitle(value = "价格", order = 27)
    private BigDecimal price;

    // 销售日期
    @NotNull(message = "销售日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ExcelTitle(value = "销售日期", order = 28)
    private Date saleDate;

    // 接收人
    @NotEmpty(message = "接收人不能为空")
    @ExcelTitle(value = "接收人", order = 29)
    private String receiver;

    public void setStatusStr(String statusStr){
        this.statusStr = statusStr;
        CardStatusEnum cardStatusEnum = EnumUtils.getEnum(CardStatusEnum.class, statusStr);
        if (cardStatusEnum != null){
            this.status = cardStatusEnum.getBackValue();
        }
    }

    public void setSaleDate(String saleDate){
        this.saleDate = DateUtils.parse(saleDate, "yyyy/MM/dd");
    }
}
