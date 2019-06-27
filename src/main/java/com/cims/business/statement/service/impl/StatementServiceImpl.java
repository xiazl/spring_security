package com.cims.business.statement.service.impl;

import com.cims.business.CommonService;
import com.cims.business.statement.mapper.StatementMapper;
import com.cims.business.statement.service.StatementService;
import com.cims.business.statement.vo.*;
import com.cims.common.enums.FreezeTypeEnum;
import com.cims.common.util.EnumUtils;
import com.cims.framework.exception.AppException;
import com.cims.framework.exception.BizException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author belly
 * @create 4/22/19 9:10 PM
 * @description 报表
 */

@Service
public class StatementServiceImpl extends CommonService implements StatementService {

    @Autowired
    private StatementMapper statementMapper;

    @Override
    public List<StatementCalcPaymentVO> calcPayment() throws BizException{
        // 未出和启用卡
        List<CalcInUnusedAndUsedVO> unusedCards = statementMapper.queryUnusedAndUsedPayment();
        Map<Integer, BigDecimal> unusedCardsMap = unusedCards.stream().collect(Collectors.groupingBy(
                CalcInUnusedAndUsedVO::getChannelId, Collectors.mapping(
                        CalcInUnusedAndUsedVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 停用卡
        List<CalcInDisabledVO> disabledCards = statementMapper.queryDisabledPayment();
        List<String> errCardNos = disabledCards.stream().filter(m -> m.getDays() == null).map(CalcInDisabledVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("停用卡片" + errCardNos + "停用日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> disabledCardsMap = disabledCards.stream().collect(Collectors.groupingBy(
                CalcInDisabledVO::getChannelId, Collectors.mapping(
                        CalcInDisabledVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 冻结卡
        List<CalcInFreezeVO> freezeCards = statementMapper.queryFreezePayment();
        errCardNos = freezeCards.stream().filter(m -> m.getFreezeType() == null || m.getDays() == null).map(CalcInFreezeVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("冻结卡片" + errCardNos + "对应的冻结原因或者冻结日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> freezeCardsMap = freezeCards.stream().collect(Collectors.groupingBy(
                CalcInFreezeVO::getChannelId, Collectors.mapping(
                        CalcInFreezeVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 问题卡
        List<CalcInProblemVO> problemCards = statementMapper.queryProblemPayment();
        errCardNos = problemCards.stream().filter(m -> m.getDays() == null).map(CalcInProblemVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("问题卡片" + errCardNos + "对应的问题日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> problemCardsMap = problemCards.stream().collect(Collectors.groupingBy(
                CalcInProblemVO::getChannelId, Collectors.mapping(
                        CalcInProblemVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));

        // 汇总
        Map<Integer, BigDecimal> resultMap = Stream.of(unusedCardsMap, disabledCardsMap, freezeCardsMap, problemCardsMap).map(Map::entrySet)
                .flatMap(Collection::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, BigDecimal::add));

        // 转换成VO对象
        List<StatementCalcPaymentVO> result = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> curData : resultMap.entrySet()) {
            StatementCalcPaymentVO curVO = new StatementCalcPaymentVO();
            curVO.setChannelId(curData.getKey());
            curVO.setAmount(curData.getValue().setScale(0, RoundingMode.HALF_UP));
            result.add(curVO);
        }
        updateListChannelName(result);
        return result;
    }


    @Override
    public List<StatementCalcReceiptVO> calcReceipt() throws BizException{
        // 启用卡
        List<CalcOutUsedVO> usedCards = statementMapper.queryUsedReceipt();
        Map<Integer, BigDecimal> usedCardsMap = usedCards.stream().collect(Collectors.groupingBy(
                CalcOutUsedVO::getProjectId, Collectors.mapping(
                        CalcOutUsedVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 停用卡
        List<CalcOutDisabledVO> disabledCards = statementMapper.queryDisabledReceipt();
        List<String> errCardNos = disabledCards.stream().filter(m -> m.getDays() == null).map(CalcOutDisabledVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("停用卡片" + errCardNos + "停用日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> disabledCardsMap = disabledCards.stream().collect(Collectors.groupingBy(
                CalcOutDisabledVO::getProjectId, Collectors.mapping(
                        CalcOutDisabledVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 冻结卡
        List<CalcOutFreezeVO> freezeCards = statementMapper.queryFreezeReceipt();
        errCardNos = freezeCards.stream().filter(m -> m.getDays() == null || m.getFreezeType() == null).map(CalcOutFreezeVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("冻结卡片" + errCardNos + "对应的冻结原因或者冻结日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> freezeCardsMap = freezeCards.stream().collect(Collectors.groupingBy(
                CalcOutFreezeVO::getProjectId, Collectors.mapping(
                        CalcOutFreezeVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 问题卡
        List<CalcOutProblemVO> problemCards = statementMapper.queryProblemReceipt();
        errCardNos = problemCards.stream().filter(m -> m.getDays() == null).map(CalcOutProblemVO::getCardNo).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(errCardNos)){
            throw new BizException("问题卡片" + errCardNos + "对应的问题日期不正确，请修正后重试");
        }
        Map<Integer, BigDecimal> problemCardsMap = problemCards.stream().collect(Collectors.groupingBy(
                CalcOutProblemVO::getProjectId, Collectors.mapping(
                        CalcOutProblemVO::getTotalAmount, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
        ));
        // 汇总
        Map<Integer, BigDecimal> resultMap = Stream.of(usedCardsMap, disabledCardsMap, freezeCardsMap, problemCardsMap).map(Map::entrySet)
                .flatMap(Collection::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, BigDecimal::add));

        // 转换成VO对象
        List<StatementCalcReceiptVO> result = new ArrayList<>();
        for (Map.Entry<Integer, BigDecimal> curData : resultMap.entrySet()){
            StatementCalcReceiptVO curVO = new StatementCalcReceiptVO();
            curVO.setProjectId(curData.getKey());
            curVO.setAmount(curData.getValue().setScale(0, RoundingMode.HALF_UP));
            result.add(curVO);
        }
        List<Integer> projectIds = result.stream().map(StatementCalcReceiptVO::getProjectId).collect(Collectors.toList());
        updateListProjectName(result, projectIds);
        return result;
    }





    @Override
    public List<ChannelProcessMethodVO> channelProcessMethod(StatementCondition condition){
        List<ChannelProcessMethodVO> list = statementMapper.queryChannelProcessMethod(condition);
        updateListChannelName(list);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setChannelName("合计");
            list.get(list.size()-1).setChannelId(-1);
        }
        return list;
    }


    @Override
    public List<ChannelProcessStatusVO> channelProcessStatus(StatementCondition condition) {
        List<ChannelProcessStatusVO> list = statementMapper.queryChannelProcessStatus(condition);
        updateListChannelName(list);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setChannelName("合计");
            list.get(list.size()-1).setChannelId(-1);
        }
        return list;
    }

    @Override
    public List<ChannelFreezeTypeVO> channelFreezeType(StatementCondition condition) {
        List<ChannelFreezeTypeVO> list = statementMapper.queryChannelFreezeType(condition);
        updateListChannelName(list);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setChannelName("合计");
            list.get(list.size()-1).setChannelId(-1);
        }
        return list;
    }

    @Override
    public List<ProjectFreezeTypeVO> projectFreezeType(StatementCondition condition) {
        List<ProjectFreezeTypeVO> list = statementMapper.queryProjectFreezeType(condition);
        List<Integer> projectIds = list.stream().map(ProjectFreezeTypeVO::getProjectId).distinct().collect(Collectors.toList());
        updateListProjectName(list, projectIds);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setProjectName("合计");
            list.get(list.size()-1).setProjectId(-1);
        }
        return list;
    }


    @Override
    public List<BankFreezeTypeVO> bankFreezeType(StatementCondition condition) {
        return statementMapper.queryBankFreezeType(condition);
    }


    @Override
    public List<WarehouseUnusedVO> warehouseUnused() {
        List<WarehouseUnusedVO> list = statementMapper.queryWarehouseUnused();
        updateListWarehouseName(list);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setWarehouseName("合计");
            list.get(list.size()-1).setWarehouseId(-1);
        }
        return list;
    }


    @Override
    public List<WarehouseBankUnusedVO> warehouseBankUnused() {
        return statementMapper.queryWarehouseBankUnused();
    }

    @Override
    public List<ChannelStockVO> channelStock() {
        List<ChannelStockVO> list = statementMapper.queryChannelStock();
        updateListChannelName(list);
        return list;
    }

    @Override
    public List<ChannelDisableTypeVO> channelDisableType(StatementCondition condition) {
        List<ChannelDisableTypeVO> list =statementMapper.queryChannelDisableType(condition);
        updateListChannelName(list);
        list = sumListObject(list);
        if (!CollectionUtils.isEmpty(list)){
            list.get(list.size()-1).setChannelName("合计");
            list.get(list.size()-1).setChannelId(-1);
        }
        return list;
    }

    @Override
    public ProjectUsedVO projectUsed() {
        List<ProjectUsedDetailVO> detailList = statementMapper.queryProjectUsedDetail();

        List<Map<String, Object>> body = new ArrayList<>();

        List<String> bankNameList = detailList.stream().map(ProjectUsedDetailVO::getBankName).distinct().collect(Collectors.toList());
        for (String curBank : bankNameList){
            List<ProjectUsedDetailVO> curDetailList = detailList.stream().filter(m -> m.getBankName().equals(curBank)).collect(Collectors.toList());
            Map<String, Integer> map = curDetailList.stream().collect(Collectors.groupingBy(
                    ProjectUsedDetailVO::getProjectName, Collectors.mapping(
                            ProjectUsedDetailVO::getCardCount, Collectors.reducing(0, Integer::sum))
            ));
            Integer curTotal = curDetailList.stream().map(ProjectUsedDetailVO::getCardCount).reduce(0, Integer::sum);

            Map<String, Object> curMap = new HashMap<>();
            curMap.put("bankName", curBank);
            curMap.putAll(map);
            curMap.put("total", curTotal);
            body.add(curMap);
        }

        Map<String, Integer> tempTotalMap = detailList.stream().collect(Collectors.groupingBy(
                ProjectUsedDetailVO::getProjectName, Collectors.mapping(
                        ProjectUsedDetailVO::getCardCount, Collectors.reducing(0, Integer::sum))
        ));
        Integer totalCount = detailList.stream().map(ProjectUsedDetailVO::getCardCount).reduce(0, Integer::sum);
        Map<String, Object> totalMap = new HashMap<>();
        totalMap.putAll(tempTotalMap);
        totalMap.put("total", totalCount);

        ProjectUsedVO result = new ProjectUsedVO();
        result.setBody(body);
        result.setTotal(totalMap);
        return result;
    }

    /**
     * List对象求和并追加到List尾部
     *
     * 求和范围包括：以Count或Amount结尾，并且是Intger或BigDecimal类型的Field
     * @param list
     * @param <E>
     * @return
     * @throws AppException
     */
    private <E> List<E> sumListObject(List<E> list) throws AppException{
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }
        try {
            Class<?> clz = list.get(0).getClass();
            Constructor con = clz.getConstructor();

            if (list.size() == 1) {
                Object obj = con.newInstance();
                BeanUtils.copyProperties(list.get(0), obj);
                list.add((E)obj);
            } else {
                E total = list.stream().reduce((x, y) -> sumObject(x, y)).orElse(null);
                list.add(total);
            }
            return list;
        } catch (Exception e) {
            throw new AppException("列表求和计算错误");
        }
    }

    /**
     * 求和两个对象
     *
     * 求和范围包括：以Count或Amount结尾，并且是Intger或BigDecimal类型的Field
     * @param x
     * @param y
     * @param <E>
     * @return
     * @throws AppException
     */
    private <E> E sumObject(E x, E y) throws AppException{
        try{
            Class<?> clz = x.getClass();

            Constructor con = clz.getConstructor();
            Object obj = con.newInstance();

            Field[] fields = clz.getDeclaredFields();
            for (Field curField : fields){
                String curFieldName = curField.getName();
                if (!curFieldName.endsWith("Count") && !curFieldName.endsWith("Amount")){
                    continue;
                }
                if (curField.getType().getName().equals("java.lang.Integer")){
                    Method mGet = (Method) clz.getMethod("get" + getMethodName(curFieldName));
                    Integer valx = (Integer)mGet.invoke(x) == null ? 0 : (Integer)mGet.invoke(x);
                    Integer valy = (Integer)mGet.invoke(y) == null ? 0 : (Integer)mGet.invoke(y);
                    Method mSet = (Method) clz.getMethod("set" + getMethodName(curFieldName), Integer.class);
                    mSet.invoke(obj, valx + valy);
                }
                if (curField.getType().getName().equals("java.math.BigDecimal")){
                    Method mGet = (Method) clz.getMethod("get" + getMethodName(curFieldName));
                    BigDecimal valx = (BigDecimal)mGet.invoke(x) == null ? BigDecimal.ZERO : (BigDecimal)mGet.invoke(x);
                    BigDecimal valy = (BigDecimal)mGet.invoke(y) == null ? BigDecimal.ZERO : (BigDecimal)mGet.invoke(y);
                    Method mSet = (Method) clz.getMethod("set" + getMethodName(curFieldName), BigDecimal.class);
                    mSet.invoke(obj, valx.add(valy));
                }
            }
            return (E)obj;
        } catch (Exception e){
            throw new AppException("求和计算错误");
        }
    }

    // 把一个字符串的第一个字母大写
    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
