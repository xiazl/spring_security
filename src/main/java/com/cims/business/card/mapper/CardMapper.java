package com.cims.business.card.mapper;

import com.cims.business.card.entity.Card;
import com.cims.business.card.vo.CardReturnListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardMapper {
    /**
     * 批量保存
     * @param list
     * @param createUserId
     * @return
     */
    int insertBatch(@Param("list") List<Card> list, @Param("createUserId") Long createUserId);

    /**
     * 根据卡号来查询卡片信息
     * @param cardNo
     * @return
     */
    Card selectByCardNo(String cardNo);

    /**
     * 根据卡号来查询卡片信息
     * @param cardNos
     * @return
     */
    List<Card> selectByCardNos(List<String> cardNos);

    /**
     * 根据id来查询卡片信息
     * @param ids
     * @return
     */
    List<Card> selectByIds(List<Long> ids);

    /**
     * 通过id更新卡信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Card record);

    /**
     * 退库更新
     * @param list
     * @return
     */
    int updateBatchForReturn(@Param("list") List<CardReturnListVO> list,@Param("cardNos")List<String> cardNos);

    /**
     * 更新状态（停用、冻结）
     * @param status
     * @return
     */
    int updateStatusBatch(@Param("status") Integer status,@Param("cardNos")List<String> cardNos);

    /**
     * 更新卡片使用状态
     * @param archive 1 归档
     * @return
     */
    int updateArchiveBatch(@Param("cardNos")List<String> cardNos,@Param("archive") Integer archive);

    /**
     * 根据卡号批量删除
     * @param cardNos
     * @return
     */
    int deleteByCardNos(List<String> cardNos);
}