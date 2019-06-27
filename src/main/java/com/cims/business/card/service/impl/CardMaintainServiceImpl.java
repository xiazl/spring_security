package com.cims.business.card.service.impl;

import com.cims.business.CommonService;
import com.cims.business.card.entity.Card;
import com.cims.business.card.entity.CardMaintain;
import com.cims.business.card.mapper.CardMaintainMapper;
import com.cims.business.card.service.CardMaintainService;
import com.cims.business.card.vo.*;
import com.cims.common.context.UserContext;
import com.cims.common.enums.CardStatusEnum;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author baidu
 * @date 2019-04-18 15:39
 * @description 卡片维护
 **/

@Service("maintainService")
public class CardMaintainServiceImpl extends CommonService implements CardMaintainService {

    @Autowired
    private CardMaintainMapper maintainMapper;

    @Override
    public PageDataResult<CardMaintainListVO> list(Condition condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardMaintainListVO> list = maintainMapper.list(condition);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> disable(CardDisableVO disableVO) throws BizException {
        List<String> cardNos = disableVO.getCardNos();
        List<String> messages = checkCardStatus(cardNos);
        if(messages.size() > 0){
            return messages;
        }

        List<CardDisableVO> disableVOS = new ArrayList<>();
        for (String cardNo: cardNos){
            CardDisableVO cardDisableVO = new CardDisableVO();
            BeanUtils.copyProperties(disableVO,cardDisableVO);
            cardDisableVO.setCardNo(cardNo);
            disableVOS.add(cardDisableVO);
        }

        maintainMapper.insertBatchDisable(disableVOS,UserContext.getUserId());

        cardMapper.updateStatusBatch(CardStatusEnum.DISABLE.getBackValue(), cardNos);

        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> freeze(CardFreezeVO freezeVO) throws BizException {
        List<String> cardNos = freezeVO.getCardNos();
        List<String> messages = checkCardStatus(cardNos);
        if(messages.size() > 0){
            return messages;
        }

        List<CardFreezeVO> freezeVOS = new ArrayList<>();
        for (String cardNo: cardNos){
            CardFreezeVO cardFreezeVO = new CardFreezeVO();
            BeanUtils.copyProperties(freezeVO,cardFreezeVO);
            cardFreezeVO.setCardNo(cardNo);
            freezeVOS.add(cardFreezeVO);
        }
        maintainMapper.insertBatchFreeze(freezeVOS,UserContext.getUserId());

        cardMapper.updateStatusBatch(CardStatusEnum.FREEZE.getBackValue(), cardNos);

        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> convertProblem(CardProblemVO problemVO) throws BizException {
        List<String> cardNos = problemVO.getCardNos();
        List<String> messages = checkCardStatus(cardNos);
        if(messages.size() > 0){
            return messages;
        }

        List<CardProblemVO> problemVOS = new ArrayList<>();
        for (String cardNo: cardNos){
            CardProblemVO cardProblemVO = new CardProblemVO();
            BeanUtils.copyProperties(problemVO,cardProblemVO);
            cardProblemVO.setCardNo(cardNo);
            problemVOS.add(cardProblemVO);
        }
        maintainMapper.insertBatchProblem(problemVOS,UserContext.getUserId());

        cardMapper.updateStatusBatch(CardStatusEnum.PROBLEM.getBackValue(), cardNos);

        return Collections.emptyList();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> disable(List<CardDisableVO> disableVOs) throws BizException {
        List<String> disableTypeNull = disableVOs.stream().filter(item -> StringUtils.isEmpty(item.getDisableType())
                || item.getDate() == null)
                .map(CardDisableVO::getCardNo).collect(Collectors.toList());
        if(disableTypeNull.size() != 0){
            throw new BizException("停用类型、停用日期不能为空，卡号："+disableTypeNull);
        }
        List<String> cardNos = disableVOs.stream().map(CardDisableVO::getCardNo).distinct().collect(Collectors.toList());

        List<String> messages = checkDisableCardStatus(cardNos);
        if(messages.size() > 0){
            return messages;
        }
        maintainMapper.insertBatchDisable(disableVOs,UserContext.getUserId());

        cardMapper.updateStatusBatch(CardStatusEnum.DISABLE.getBackValue(), cardNos);

        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> freeze(List<CardFreezeVO> freezeVOs) throws BizException {
        checkFreezeData(freezeVOs);
        List<String> cardNos = freezeVOs.stream().map(CardFreezeVO::getCardNo).distinct().collect(Collectors.toList());
        List<String> messages = checkFreezeCardStatus(cardNos);
        if(messages.size() > 0){
            return messages;
        }
        maintainMapper.insertBatchFreeze(freezeVOs,UserContext.getUserId());

        cardMapper.updateStatusBatch(CardStatusEnum.FREEZE.getBackValue(), cardNos);

        return Collections.emptyList();

    }

    /**
     * 数据非空检查
     * @param freezeVOs
     * @throws BizException
     */
    private void checkFreezeData(List<CardFreezeVO> freezeVOs) throws BizException {
        List<String> dataNull = freezeVOs.stream().filter(item -> {
            if(StringUtils.isEmpty(item.getFreezeType())
                || item.getDate() == null){
                return true;
            } else if(StringUtils.isEmpty(item.getReason())
                    || item.getProcessStatus() == null){
                return true;
            }

            return false;
        }).map(CardFreezeVO::getCardNo).collect(Collectors.toList());

        if(dataNull.size() != 0){
            throw new BizException("冻结类型、冻结日期、冻结显示后原因、处理状态不能为空，卡号："+dataNull);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CardMaintainListVO maintainListVO) {
        CardMaintain maintain = maintainMapper.selectByPrimaryKey(maintainListVO.getId());
        Assert.notNull(maintain,"id不存在");
        maintainMapper.updateByPrimaryKey(maintainListVO);
        Integer status = maintainListVO.getStatus();
        if (status != null) {
            cardMapper.updateStatusBatch(status, Arrays.asList(maintain.getCardNo()));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long id,Integer status) {
        CardMaintain cardMaintain = maintainMapper.selectByPrimaryKey(id);
        Assert.notNull(cardMaintain,"id不存在");
        maintainMapper.deleteByPrimaryKeys(Arrays.asList(id));
        if (status != null) {
            cardMapper.updateStatusBatch(status, Arrays.asList(cardMaintain.getCardNo()));
        }

    }

    @Override
    public void delete(List<Long> ids) {
        maintainMapper.deleteByPrimaryKeys(ids);
    }


    /**
     * 冻结卡号检查
     * @param cardNos
     * @return
     */
    protected List<String> checkFreezeCardStatus(List<String> cardNos) throws BizException {
        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        List<Card> checkCard = cards.stream().filter(item ->
                (item.getStatus().intValue() > 1) && (item.getStatus().intValue() !=3 )).collect(Collectors.toList());
        if(checkCard.size() > 0){
            throw new BizException("银行卡状态流转非法，只准许未用、启用、冻结的银行卡："+checkCard.get(0).getCardNo());
        }

        return checkCard(cardNos,cards);
    }

    /**
     * 停用卡号检查
     * @param cardNos
     * @return
     */
    protected List<String> checkDisableCardStatus(List<String> cardNos) throws BizException {
        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        List<Card> checkCard = cards.stream().filter(item -> item.getStatus().intValue() > 2).collect(Collectors.toList());
        if(checkCard.size() > 0){
            throw new BizException("银行卡状态流转非法，只准许未用、启用、停用的银行卡："+checkCard.get(0).getCardNo());
        }

        return checkCard(cardNos,cards);
    }

}
