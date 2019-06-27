package com.cims.business.card.mapper;

import com.cims.business.card.entity.UserWarehouse;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author baidu
 * @date 2019-04-17 21:19
 * @description 用户仓库关系
 **/

@Mapper
public interface UserWarehouseMapper {
    /**
     * 删除关系
     * @param userId
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 插入用户仓库关系
     * @param record
     * @return
     */
    int insert(UserWarehouse record);

}
