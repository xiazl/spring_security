package com.cims.business.settlement.mapper;

import com.cims.business.settlement.entity.CardSettlement;
import com.cims.business.settlement.vo.CardInfoCondition;
import com.cims.business.settlement.vo.CardInfoListVO;
import com.cims.business.settlement.vo.CardSettlementListVO;
import com.cims.business.settlement.vo.SettlementCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CardSettlementMapper {

    /**
     * 入库选卡列表查询
     * @param condition
     * @return
     */
    List<CardInfoListVO> listPurchaseCard(CardInfoCondition condition);

    /**
     * 出库选卡列表查询
     * @param condition
     * @return
     */
    List<CardInfoListVO> listSaleCard(CardInfoCondition condition);

    /**
     * 结算记录列表查询
     * @param condition
     * @return
     */
    List<CardSettlementListVO> listSettlement(SettlementCondition condition);

    /**
     * 插入停用
     * @param list
     * @return
     */
    int insertBatchOutSettlement(@Param("list") List<CardSettlement> list, @Param("createUserId")Long createUserId);

    /**
     * 插入
     * @param list
     * @return
     */
    int insertBatchInSettlement(@Param("list") List<CardSettlement> list, @Param("createUserId")Long createUserId);

    /**
     * 是否是最新一次结算记录还原
     * @param cardNos
     * @return
     */
    int updateIsLast(@Param("cardNos") List<String> cardNos, @Param("type")Integer type);

    /**
     * 通过卡号查询最新一次结算记录
     * @param cardNos
     * @return
     */
    List<CardSettlement> listByCardNos(@Param("cardNos") List<String> cardNos, @Param("type")Integer type);

    /**
     * 通过id查询最新一次结算记录
     * @param ids
     * @return
     */
    List<CardSettlement> listByIds(@Param("ids") List<Long> ids,@Param("isLast")Boolean isLast);

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Long> ids);

    /**
     * 通过id更新入库结算
     * @param ids
     * @return
     */
    int updateInByPrimaryKeys(@Param("ids")List<Long> ids,@Param("list") List<CardSettlement> list);

    /**
     * 通过id更新出库结算
     * @param ids
     * @return
     */
    int updateOutByPrimaryKeys(@Param("ids")List<Long> ids,@Param("list") List<CardSettlement> list);

    /**
     * 通过id更新付款日期
     * @param ids
     * @return
     */
    int updatePayDateByPrimaryKeys(@Param("ids")List<Long> ids,@Param("list") List<CardSettlement> list);

    /**
     * 通过卡号查询维护卡是否进行了最后一次结算
     * @param cardNos
     * @param type 1 入库结算 2 出库结算
     * @return
     */
    List<String> listCardNos(@Param("cardNos") List<String> cardNos, @Param("type")Integer type);

}