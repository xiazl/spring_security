package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardPurchase;
import com.cims.business.card.vo.CardPurchaseVO;
import com.cims.business.card.vo.Condition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardPurchaseMapper {

    /**
     * 入库页面列表数据
     * @param conditionVO
     * @return
     */
    List<CardPurchaseVO> list(Condition conditionVO);

    /**
     * 通过卡号查询入库结算价,购卡时间
     * @param cardNos
     * @return
     */
    List<CardPurchase> listByCardNos(@Param("cardNos") List<String> cardNos);

    /**
     * 根据卡片Id来查询入库信息
     * @param cardNo
     * @return
     */
    CardPurchase selectByCardNo(String cardNo);

    /**
     * 批量插入
     * @param list
     * @param createUserId
     * @return
     */
    int insertBatch(@Param("list") List<CardPurchase> list, @Param("createUserId") Long createUserId);

    /**
     * 批量更新入库价格
     * @param list
     * @param cardNos
     */
    void updatePriceBatch(@Param("list") List<CardPurchase> list, @Param("cardNos") List<String> cardNos);

    /**
     * 根据卡号批量删除
     * @param cardNos
     * @return
     */
    int deleteByCardNos(List<String> cardNos);
}