package com.cims.business.card.service.impl;

import com.cims.business.card.entity.CardChannel;
import com.cims.business.card.mapper.CardChannelMapper;
import com.cims.business.card.service.CardChannelService;
import com.cims.common.context.UserContext;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:21
 * @description TODO
 **/

@Service("channelService")
public class CardChannelServiceImpl implements CardChannelService {

    @Autowired
    private CardChannelMapper channelMapper;
    @Override
    public PageDataResult<CardChannel> list(CardChannel condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardChannel> list = channelMapper.list(condition);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    public List<CardChannel> list() {
        List<CardChannel> list = channelMapper.list(new CardChannel());
        return list;
    }

    @Override
    public void add(CardChannel cardChannel) throws BizException {
        String channelName =cardChannel.getChannelName();
        CardChannel channel = channelMapper.selectByChannelName(channelName);
        if(channel != null){
            throw new BizException("渠道名已存在");
        }
        cardChannel.setCreateUserId(UserContext.getUserId());
        channelMapper.insert(cardChannel);
    }

    @Override
    public void update(CardChannel cardChannel) throws BizException {
        CardChannel channel = channelMapper.selectByChannelName(cardChannel.getChannelName());
        if(channel != null && channel.getId().longValue() != cardChannel.getId()){
            throw new BizException("渠道名已存在");
        }
        channelMapper.updateByPrimaryKey(cardChannel);
    }

    @Override
    public void delete(List<Integer> ids) {
        channelMapper.deleteByPrimaryKeys(ids);
    }
}
