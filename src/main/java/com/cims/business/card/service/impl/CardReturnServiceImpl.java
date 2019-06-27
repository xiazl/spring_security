package com.cims.business.card.service.impl;

import com.cims.business.card.entity.Card;
import com.cims.business.card.entity.CardReturn;
import com.cims.business.card.mapper.CardMapper;
import com.cims.business.card.mapper.CardReturnMapper;
import com.cims.business.card.service.CardReturnService;
import com.cims.business.card.vo.CardReturnListVO;
import com.cims.business.card.vo.Condition;
import com.cims.common.context.UserContext;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author baidu
 * @date 2019-04-17 19:39
 * @description TODO
 **/

@Service("returnService")
public class CardReturnServiceImpl implements CardReturnService {

    @Autowired
    private CardReturnMapper returnMapper;
    @Autowired
    private CardMapper cardMapper;

    @Override
    public PageDataResult<CardReturnListVO> list(Condition condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardReturnListVO> list = returnMapper.list(condition);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CardReturnListVO returnVO) throws BizException {
        Card card =  cardMapper.selectByCardNo(returnVO.getCardNo());
        if(card == null){
            throw new BizException("卡号不存在");
        }

        CardReturn cardReturn = returnMapper.selectByCardNo(returnVO.getCardNo());
        if(cardReturn != null){
            throw new BizException("该卡已经退库，请不要重复操作");
        }

        cardReturn = copyProperties(returnVO);
        cardReturn.setCreateUserId(UserContext.getUserId());
        returnMapper.insert(cardReturn);

        updateCard(card,returnVO);
    }

    @Override
    public void update(CardReturnListVO returnVO) {
        CardReturn cardReturn = copyProperties(returnVO);
        returnMapper.updateByPrimaryKey(cardReturn);

        Card card =  cardMapper.selectByCardNo(returnVO.getCardNo());
        updateCard(card,returnVO);
    }

    @Override
    public void delete(List<Long> ids) {
        returnMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> addBatch(List<CardReturnListVO> list) throws BizException {
        List<String> cardNos = list.stream().map(CardReturnListVO::getCardNo).distinct().collect(Collectors.toList());
        if (cardNos.size() == 0){
            throw new BizException("卡号不存在");
        }
        List<Card> cards = cardMapper.selectByCardNos(cardNos);

        List<String> messages = new ArrayList<>();

        if(cardNos.size() != cards.size()){
            Map<String,String> map = cards.stream().collect(Collectors.toMap(Card::getCardNo,Card::getCardNo));
            for(CardReturnListVO temp: list){
                String key = temp.getCardNo();
                if (!map.containsKey(key)){
                    messages.add("卡号不存在："+key);
                }
            }
        }

        if(messages.size() > 0){
            return messages;
        }

        returnMapper.insertBatch(list, UserContext.getUserId());

        cardMapper.updateBatchForReturn(list,cardNos);

        return messages;

    }

    /**
     * 属性复制
     * @param cardVO
     * @return
     */
    private CardReturn copyProperties(CardReturnListVO cardVO){
        CardReturn cardReturn = new CardReturn();
        BeanUtils.copyProperties(cardVO,cardReturn);
        return cardReturn;
    }

    /**
     * 更新卡片基本信息
     * @param card
     * @param returnVO
     */
    private void updateCard(Card card, CardReturnListVO returnVO){
        Card updateCard = new Card();
        updateCard.setId(card.getId());
        updateCard.setLoginPassword(returnVO.getLoginPassword());
        updateCard.setPayPassword(returnVO.getPayPassword());
        updateCard.setUkeyPassword(returnVO.getUkeyPassword());
        cardMapper.updateByPrimaryKeySelective(updateCard);
    }
}
