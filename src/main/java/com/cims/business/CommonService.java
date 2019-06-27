package com.cims.business;

import com.cims.business.card.entity.*;
import com.cims.business.card.mapper.*;
import com.cims.framework.exception.AppException;
import com.cims.framework.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author baidu
 * @date 2019/4/17 下午2:03
 * @description 公共处理方法
 **/
public class CommonService {
    @Autowired
    protected CardMapper cardMapper;
    @Autowired
    protected CardProjectMapper cardProjectMapper;
    @Autowired
    protected WarehouseMapper warehouseMapper;
    @Autowired
    protected CardChannelMapper cardChannelMapper;
    @Autowired
    protected CardMaintainMapper cardMaintainMapper;

    /**
     * 卡号检查
     * @param cardNos
     * @return
     */
    protected List<String> checkCardStatus(List<String> cardNos) throws BizException {
        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        List<Card> checkCard = cards.stream().filter(item -> item.getStatus().intValue() > 1).collect(Collectors.toList());
        if(checkCard.size() > 0){
            throw new BizException("银行卡状态流转非法，只准许未用、启用的银行卡："+checkCard.get(0).getCardNo());
        }

        return checkCard(cardNos,cards);
    }

    /**
     * 返回卡号检查错误信息
     * @param cardNos
     * @param cards
     * @return
     */
    protected List<String> checkCard(List<String> cardNos,List<Card> cards){
        List<String> messages = new ArrayList<>();
        if(cardNos.size() != cards.size()){
            Map<String,String> map = cards.stream().collect(Collectors.toMap(Card::getCardNo,Card::getCardNo));
            for(String cardNo: cardNos){
                if (!map.containsKey(cardNo)){
                    messages.add("卡号不存在："+cardNo);
                }
            }
        }
        return messages;
    }

    /**
     * 设定项目名显示值 - 带方法名
     * @param list
     * @param projectIds
     * @param projectIdMethod
     * @param projectNameMethod
     * @param <T>
     */
    protected <T> void updateListProjectName(List<T> list, List<Integer> projectIds, String projectIdMethod, String projectNameMethod) {
        if(CollectionUtils.isEmpty(projectIds) || StringUtils.isEmpty(projectIdMethod) || StringUtils.isEmpty(projectNameMethod)){
            return;
        }

        List<CardProject> projectList = cardProjectMapper.listByIds(projectIds);
        Map<Integer,String> map = projectList.stream().filter( m -> m.getProjectName() != null).collect(Collectors.toMap(CardProject::getId,CardProject::getProjectName));
        invokeProperty(list,map,projectIdMethod,projectNameMethod);
    }

    /**
     * 设定项目名显示值 - 使用默认方法名
     * @param list
     * @param projectIds
     * @param <T>
     */
    protected <T> void updateListProjectName(List<T> list, List<Integer> projectIds) {
        updateListProjectName(list, projectIds, "getProjectId", "setProjectName");
    }

    /**
     * 设定仓库名显示值 - 带方法名
     * @param list
     * @param warehouseIdMethod
     * @param warehouseNameMethod
     * @param <T>
     */
    protected <T> void updateListWarehouseName(List<T> list, String warehouseIdMethod, String warehouseNameMethod) {
        if(StringUtils.isEmpty(warehouseIdMethod) || StringUtils.isEmpty(warehouseNameMethod)){
            return;
        }

        List<Warehouse> warehouseList = warehouseMapper.listAll();
        Map<Integer,String> map = warehouseList.stream().filter( m -> m.getName() != null).collect(Collectors.toMap(Warehouse::getId,Warehouse::getName));
        invokeProperty(list,map,warehouseIdMethod,warehouseNameMethod);
    }

    /**
     * 设定仓库名显示值 - 使用默认方法名
     * @param list
     * @param <T>
     */
    protected <T> void updateListWarehouseName(List<T> list) {
        updateListWarehouseName(list, "getWarehouseId", "setWarehouseName");
    }

    /**
     * 设定渠道名显示值 - 带方法名
     * @param list
     * @param channelIdMethod
     * @param channelNameMethod
     * @param <T>
     */
    protected <T> void updateListChannelName(List<T> list, String channelIdMethod, String channelNameMethod) {
        if(StringUtils.isEmpty(channelIdMethod) || StringUtils.isEmpty(channelNameMethod)){
            return;
        }

        List<CardChannel> channelList = cardChannelMapper.listAll();
        Map<Integer,String> map = channelList.stream().filter( m -> m.getChannelName() != null).collect(Collectors.toMap(CardChannel::getId,CardChannel::getChannelName));
        invokeProperty(list,map,channelIdMethod,channelNameMethod);
    }

    /**
     * 通过id反射设置name值
     * @param list
     * @param map
     * @param idMethod
     * @param nameMethod
     * @param <T>
     */
    private <T> void invokeProperty(List<T> list, Map<Integer,String> map, String idMethod, String nameMethod){
        list.forEach(item -> {
            try {
                Method method1 = item.getClass().getMethod(idMethod);
                Integer projectId = (Integer) method1.invoke(item);

                Method method2 = item.getClass().getMethod(nameMethod,String.class);
                method2.invoke(item, map.get(projectId));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new AppException(e.getMessage(),e);
            }
        });
    }

    /**
     * 设定渠道名显示值 - 使用默认方法名
     * @param list
     * @param <T>
     */
    protected <T> void updateListChannelName(List<T> list) {
        updateListChannelName(list, "getChannelId", "setChannelName");
    }

    /**
     * 设定问题描述值 - 带方法名
     * @param list
     * @param cardNos
     * @param cardNoMethod
     * @param problemDescMethod
     * @param <T>
     */
    protected <T> void updateListProblemDesc(List<T> list, List<String> cardNos, String cardNoMethod, String problemDescMethod) {
        if(CollectionUtils.isEmpty(cardNos) || StringUtils.isEmpty(cardNoMethod) || StringUtils.isEmpty(problemDescMethod)){
            return;
        }

        List<CardMaintain> maintainList = cardMaintainMapper.listByCardNos(cardNos);
        if (CollectionUtils.isEmpty(maintainList)){
            return;
        }
        Map<String,String> map = maintainList.stream().filter( m -> m.getProblemDesc() != null).collect(Collectors.toMap(CardMaintain::getCardNo,CardMaintain::getProblemDesc));
        if (CollectionUtils.isEmpty(map)){
            return;
        }
        list.forEach(item -> {
            try {

                Method method1 = item.getClass().getMethod(cardNoMethod);
                String cardNo = (String) method1.invoke(item);

                if (map.containsKey(cardNo)){
                    Method method2 = item.getClass().getMethod(problemDescMethod,String.class);
                    method2.invoke(item, map.get(cardNo));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new AppException(e.getMessage(),e);
            }
        });
    }

    /**
     * 设定问题描述值 - 使用默认方法名
     * @param list
     * @param <T>
     */
    protected <T> void updateListProblemDesc(List<T> list, List<String> cardNos) {
        updateListProblemDesc(list, cardNos, "getCardNo", "setProblemDesc");
    }


}
