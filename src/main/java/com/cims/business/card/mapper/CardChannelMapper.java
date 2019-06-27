package com.cims.business.card.mapper;

import com.cims.business.card.entity.CardChannel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CardChannelMapper {
    /**
     * 渠道列表查询
     * @param condition
     * @return
     */
    List<CardChannel> list(CardChannel condition);

    /**
     * 渠道列表查询 - 含删除
     * @return
     */
    List<CardChannel> listAll();

    /**
     * 删除
     * @param ids
     * @return
     */
    int deleteByPrimaryKeys(List<Integer> ids);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(CardChannel record);

    /**
     * 通过id查询
     * @param id
     * @return
     */
    CardChannel selectByPrimaryKey(Integer id);

    /**
     * 通过名称查询
     * @param channelName
     * @return
     */
    CardChannel selectByChannelName(String channelName);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(CardChannel record);
}