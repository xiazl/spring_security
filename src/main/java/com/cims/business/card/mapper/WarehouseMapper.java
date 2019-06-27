package com.cims.business.card.mapper;

import com.cims.business.card.entity.Warehouse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author belly
 * @create 4/16/19 9:09 PM
 * @description 仓库
 */

@Mapper
public interface WarehouseMapper {

    /**
     * 仓库列表
     * @return
     */
    List<Warehouse> list();

    /**
     * 仓库列表 - 含删除
     * @return
     */
    List<Warehouse> listAll();

    /**
     * 查询仓库信息
     * @param userId
     * @return
     */
    Warehouse getByUserId(Long userId);
}
