package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardReturn;
import com.cims.business.card.vo.CardReturnListVO;
import com.cims.business.card.vo.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author baidu
 * @date 2019-04-17 18:24
 * @description 退库Mapper
 **/

@Mapper
public interface CardReturnMapper {

    /**
     * 退库列表查询
     * @param condition
     * @return
     */
    List<CardReturnListVO> list(Condition condition);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Long> ids);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(CardReturn record);

    /**
     * 插入
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<CardReturnListVO> list, @Param("createUserId")Long createUserId);

    /**
     * 单个查询
     * @param cardNo
     * @return
     */
    CardReturn selectByCardNo(String cardNo);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CardReturn record);

}