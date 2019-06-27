package com.cims.business.card.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-18 20:50
 * @description 问题卡
 **/

@Setter
@Getter
public class CardProblemVO {
    @NotEmpty(message = "cardNos不能为空")
    private List<String> cardNos;

    private String cardNo;

    @NotNull(message = "date不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String problemDesc;
}
