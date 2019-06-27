package com.cims.business.card.vo;

import com.cims.common.excel.annotation.ExcelTitle;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-17 19:41
 * @description 退库列表展示对象
 **/

@Setter
@Getter
public class CardReturnListVO {
    private Long id;
    @ExcelTitle(value = "银行", order = 0)
    private String bankName;
    @ExcelTitle(value = "户名", order = 1)
    private String owner;
    @ExcelTitle(value = "卡号", order = 2)
    @NotEmpty(message = "cardNo不能为空")
    private String cardNo;
    @NotEmpty(message = "loginPassword不能为空")
    @ExcelTitle(value = "登录密码", order = 3)
    private String loginPassword;
    @NotEmpty(message = "payPassword不能为空")
    @ExcelTitle(value = "支付密码", order = 4)
    private String payPassword;
    @NotEmpty(message = "ukeyPassword不能为空")
    @ExcelTitle(value = "U盾密码", order = 5)
    private String ukeyPassword;
    @NotNull(message = "verifyAmount不能为空")
    @ExcelTitle(value = "核实金额", order = 6)
    private BigDecimal verifyAmount;
    @NotNull(message = "diffAmount不能为空")
    @ExcelTitle(value = "差异金额", order = 7)
    private BigDecimal diffAmount;
    @NotNull(message = "returnDate不能为空")
    @ExcelTitle(value = "退库时间", order = 8)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date returnDate;
    @NotNull(message = "verifyUser不能为空")
    @ExcelTitle(value = "退库核实人", order = 9)
    private String verifyUser;
    @ExcelTitle(value = "备注", order = 10)
    private String comment;

    private Long createUserId;

    private Date createTime;
}
