package com.cims.business.card.vo;

import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.CardTypeEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.DateUtils;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author belly
 * @create 4/22/19 3:33 PM
 * @description 卡片出库记录
 */

@Getter
@Setter
public class CardSaleDetailVO {

    // 主键id
    private Long id;
    // 类型 0 - 个人银行，1 - 手机银行， 2 - 支付宝
    private Integer mainType;
    // 银行名称
    @ExcelTitle(value = "银行", order = 1)
    private String bankName;
    // 户名
    @ExcelTitle(value = "名字", order = 2)
    private String owner;
    // 卡号
    @ExcelTitle(value = "卡号", order = 3)
    private String cardNo;
    // 开户省
    @ExcelTitle(value = "开户省", order = 4)
    private String province;
    // 开户市
    @ExcelTitle(value = "开户市", order = 5)
    private String city;
    // 开户行
    @ExcelTitle(value = "开户行", order = 6)
    private String branchName;
    // 身份证号
    @ExcelTitle(value = "身份证号", order = 7)
    private String idNo;
    // 登陆密码
    @ExcelTitle(value = "登陆密码", order = 8)
    private String loginPassword;
    // 支付密码
    @ExcelTitle(value = "支付密码", order = 9)
    private String payPassword;
    // U盾密码
    @ExcelTitle(value = "U盾密码", order = 10)
    private String ukeyPassword;
    // 类型用户名
    @ExcelTitle(value = "类型用户名", order = 11)
    private String typeUserName;
    // 类型登录密码
    @ExcelTitle(value = "类型登陆密码", order = 12)
    private String typeLoginPassword;
    // 类型支付密码
    @ExcelTitle(value = "类型支付密码", order = 13)
    private String typePayPassword;
    // 二级分类: 个人银行分类下包含 [0 - A, 1 - B, 2 - C], 手机银行分类下包含[3-手机银行], 支付宝分类下包含 [4-支付宝主卡, 5-支付宝下挂卡]
    private Integer secondaryType;
    // 状态0未用,1在用,2停用,3冻结,4问题
    private Integer status;
    // 手机号
    @ExcelTitle(value = "手机号", order = 16)
    private String phoneNo;
    // 手机密码
    @ExcelTitle(value = "手机密码", order = 17)
    private String phonePassword;
    // 国际漫游到期时间
    @ExcelTitle(value = "国际漫游到期时间", order = 18)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date roamingEndDate;
    // 描述处于status问题状态不能使用原因
    @ExcelTitle(value = "不能使用原因", order = 19)
    private String problemDesc;
    // 备注、支付宝密保问题及答案
    @ExcelTitle(value = "备注和密保", order = 20)
    private String comment;
    // 采购日期
    @ExcelTitle(value = "来卡日期", order = 21)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;
    // 渠道Id
    private Integer channelId;
    // 渠道名
    @ExcelTitle(value = "渠道", order = 22)
    private String channelName;
    // 卡片余额
    @ExcelTitle(value = "余额", order = 23)
    private BigDecimal balance;
    // ckr信息
    @ExcelTitle(value = "ckr", order = 24)
    private String ckr;
    // 仓库id
    private Integer warehouseId;
    // 仓库名文本
    @ExcelTitle(value = "仓库名称", order = 25)
    private String warehouseName;
    // 项目id
    private Integer projectId;
    // 项目名
    @ExcelTitle(value = "使用项目", order = 26)
    private String projectName;
    // 价格
    @ExcelTitle(value = "出库价格", order = 27)
    private BigDecimal price;
    // 出库日期
    @ExcelTitle(value = "出库日期", order = 28)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleDate;
    // 接收人名称
    @ExcelTitle(value = "接收人", order = 29)
    private String receiver;


    // 类型文本
    @ExcelTitle(value = "类型", order = 0)
    private String mainTypeStr;
    // 二级分类文本
    @ExcelTitle(value = "二级分类文本", order = 14)
    private String secondaryTypeStr;
    // 状态文本
    @ExcelTitle(value = "状态文本", order = 15)
    private String statusStr;

    public String getMainTypeStr(){
        if (getMainType() != null){
            return EnumUtils.getEnum(CardTypeEnum.MainType.class, getMainType()).getViewValue();
        }
        return mainTypeStr;
    }

    public String getSecondaryTypeStr(){
        if (getSecondaryType() != null){
            return EnumUtils.getEnum(CardTypeEnum.SecondaryType.class, getSecondaryType()).getViewValue();
        }
        return secondaryTypeStr;
    }

    public String getStatusStr(){
        if (getStatus() != null){
            return EnumUtils.getEnum(CardStatusEnum.class, getStatus()).getViewValue();
        }
        return statusStr;
    }

    public void setRoamingEndDate(Date date) {
        this.roamingEndDate = date;
    }

    public void setRoamingEndDate(String data){
        this.roamingEndDate = DateUtils.tryParseDate(data);
    }

    public void setPurchaseDate(Date date){
        this.purchaseDate = date;
    }

    public void setPurchaseDate(String data){
        this.purchaseDate = DateUtils.tryParseDate(data);
    }

    public void setSaleDate(Date data){
        this.saleDate = data;
    }

    public void setSaleDate(String data){
        this.saleDate = DateUtils.tryParseDate(data);
    }
}
