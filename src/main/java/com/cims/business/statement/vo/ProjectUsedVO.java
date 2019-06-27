package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author belly
 * @create 5/13/19 6:49 PM
 * @description 使用项目在用统计表
 */

@Getter
@Setter
public class ProjectUsedVO {

    private List<Map<String, Object>> body;

    private Map<String, Object> total;

}
