package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardMaintain;
import com.cims.business.card.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardMaintainMapper {
    /**
     * 维护列表查询
     * @param condition
     * @return
     */
    List<CardMaintainListVO> list(Condition condition);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Long> ids);

    /**
     * 根据卡号删除
     * @param cardNos
     * @return
     */
    int deleteByCardNos(List<String> cardNos);

    /**
     * 插入停用
     * @param list
     * @return
     */
    int insertBatchDisable(@Param("list") List<CardDisableVO> list, @Param("createUserId")Long createUserId);

    /**
     * 插入
     * @param list
     * @return
     */
    int insertBatchFreeze(@Param("list") List<CardFreezeVO> list, @Param("createUserId")Long createUserId);

    /**
     * 插入
     * @param list
     * @return
     */
    int insertBatchProblem(@Param("list") List<CardProblemVO> list, @Param("createUserId")Long createUserId);

    /**
     * 单个查询
     * @param id
     * @return
     */
    CardMaintain selectByPrimaryKey(Long id);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CardMaintainListVO record);

    /**
     * 查询卡片维护记录
     * @param cardNos
     * @return
     */
    List<CardMaintain> listByCardNos(@Param("cardNos")List<String> cardNos);
}