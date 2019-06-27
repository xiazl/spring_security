package com.cims.business.card.controller;

import com.cims.business.card.entity.CardChannel;
import com.cims.business.card.service.CardChannelService;
import com.cims.common.Constants;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:19
 * @description 入库渠道
 **/

@RestController
@RequestMapping("/channel")
@RolesAllowed({Constants.IN_CARD,Constants.ADMIN,Constants.OP})
public class CardChannelController {

    @Autowired
    private CardChannelService channelService;

    /**
     * 渠道列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(CardChannel condition, PageBean pageBean) {
        PageDataResult<CardChannel> list = channelService.list(condition,pageBean);
        return ResponseData.success(list);
    }

    /**
     * 渠道新增
     * @param channelName
     * @return
     */
    @RequestMapping(value = "/add")
    public Result add(@RequestParam(value = "channelName") String channelName) throws BizException {
        CardChannel channel  = new CardChannel();
        channel.setChannelName(channelName);
        channelService.add(channel);
        return ResponseData.success();
    }

    /**
     * 渠道修改
     * @param id
     * @param channelName
     * @return
     */
    @RequestMapping(value = "/update")
    public Result update(@RequestParam(value = "id")Integer id,
                         @RequestParam(value = "channelName")String channelName) throws BizException {
        CardChannel channel  = new CardChannel();
        channel.setId(id);
        channel.setChannelName(channelName);
        channelService.update(channel);
        return ResponseData.success();
    }


    /**
     * 渠道删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") List<Integer> ids) {
        channelService.delete(ids);
        return ResponseData.success();
    }
}
