package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardSale;
import com.cims.business.card.vo.CardSaleDetailVO;
import com.cims.business.card.vo.CardSaleListVO;
import com.cims.business.card.vo.SaleCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardSaleMapper {
    /**
     * 批量更新
     * @param list
     * @param createUserId
     * @return
     */
    int insertBatch(@Param("list") List<CardSale> list, @Param("createUserId") Long createUserId);

    /**
     * 通过主键查询
     * @param id
     * @return
     */
    CardSale selectByPrimaryKey(Long id);

    /**
     * 通过卡号查询结算价
     * @param cardNos
     * @return
     */
    List<CardSale> listByCardNos(@Param("cardNos") List<String> cardNos);

    /**
     * 未出卡片列表
     * @param conditionVO
     * @return
     */
    List<CardSaleListVO> list(SaleCondition conditionVO);

    /**
     * 出库记录列表
     * @param conditionVO
     * @return
     */
    List<CardSaleDetailVO> listDetail(SaleCondition conditionVO);

    /**
     * 根据卡片Id来查询出库信息
     * @param cardNo
     * @return
     */
    CardSale selectByCardNo(String cardNo);

    /**
     * 通过id更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(CardSale record);
}