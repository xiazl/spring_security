package com.cims.business.card.vo;

import com.cims.common.enums.DisableTypeEnum;
import com.cims.common.excel.annotation.ExcelTitle;
import com.cims.common.util.EnumUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-18 15:50
 * @description 卡片停用展示对象
 **/

@Setter
@Getter
public class CardDisableVO {
    private Long id;
    @ExcelTitle(value = "银行", order = 0)
    private String bankName;
    @ExcelTitle(value = "户名", order = 1)
    private String owner;
    @ExcelTitle(value = "卡号", order = 2)
    private String cardNo;
    @ExcelTitle(value = "省", order = 3)
    private String province;
    @ExcelTitle(value = "市", order = 4)
    private String city;
    @ExcelTitle(value = "行", order = 5)
    private String branchName;
    @ExcelTitle(value = "停用日期", order = 6)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "date不能为空")
    private Date date;
    @NotNull(message = "disableType不能为空")
    @Range(min=1, max=2,message = "disableType请选择指定的值")
    private Integer disableType;
    @ExcelTitle(value = "停用备注", order = 8)
    private String comment;
    @NotEmpty(message = "cardNos不能为空")
    private List<String> cardNos;
    @ExcelTitle(value = "停用原因", order = 7)
    private String disableTypeStr;

    public Integer getDisableType() {
        if(!StringUtils.isEmpty(disableTypeStr)){
            return EnumUtils.getEnum(DisableTypeEnum.class, getDisableTypeStr()).getBackValue();
        }
        return disableType;
    }

}
