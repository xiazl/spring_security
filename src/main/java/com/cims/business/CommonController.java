package com.cims.business;

import com.cims.business.card.entity.Bank;
import com.cims.business.card.entity.CardChannel;
import com.cims.business.card.entity.CardProject;
import com.cims.business.card.entity.Warehouse;
import com.cims.business.card.service.BankService;
import com.cims.business.card.service.CardChannelService;
import com.cims.business.card.service.CardProjectService;
import com.cims.business.card.service.WarehouseService;
import com.cims.common.enums.*;
import com.cims.common.vo.KeyValueVO;
import com.cims.framework.exception.BizException;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author baidu
 * @date 2019/4/17 下午2:33
 * @description 公共接口
 **/

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private BankService bankService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private CardChannelService cardChannelService;
    @Autowired
    private CardProjectService cardProjectService;


    /**
     * 获取银行
     *
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/listBank", method = RequestMethod.GET)
    public Result listBank() {
        List<Bank> banks = bankService.listBank();
        return ResponseData.success(banks);
    }

    /**
     * 获取仓库
     *
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/listWarehouse", method = RequestMethod.GET)
    public Result listWarehouse() {
        List<Warehouse> warehouses = warehouseService.list();
        return ResponseData.success(warehouses);
    }

    /**
     * 获取渠道
     *
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/listChannel", method = RequestMethod.GET)
    public Result listChannel() {
        List<CardChannel> channels = cardChannelService.list();
        return ResponseData.success(channels);
    }

    /**
     * 获取项目
     *
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/listProject", method = RequestMethod.GET)
    public Result listProject() {
        List<CardProject> projects = cardProjectService.list();
        Collections.sort(projects, Comparator.comparing(CardProject::getProjectName));
        return ResponseData.success(projects);
    }


    /**
     * 卡片类型（一级分类）
     *
     * @return
     */
    @RequestMapping("/cardMainType")
    public Result cardMainType() {
        List<KeyValueVO> list = new ArrayList<>();

        for (CardTypeEnum.MainType mainType : CardTypeEnum.MainType.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(mainType.getBackValue());
            keyValueVO.setValue(mainType.getViewValue());

            list.add(keyValueVO);
        }
        return ResponseData.success(list);
    }

    /**
     * 卡片分类（二级分类）
     *
     * @return
     */
    @RequestMapping("/cardSecondaryType")
    public Result cardSecondaryType() {
        Map<Integer, List<KeyValueVO>> map = new HashMap<>();

        for (CardTypeEnum.SecondaryType secondaryType : CardTypeEnum.SecondaryType.values()) {
            List<KeyValueVO> list = map.computeIfAbsent(secondaryType.getMainType(), k -> new ArrayList<>());

            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(secondaryType.getBackValue());
            keyValueVO.setValue(secondaryType.getViewValue());

            list.add(keyValueVO);

        }

        return ResponseData.success(map);
    }

    /**
     * 卡片状态
     *
     * @return
     */
    @RequestMapping("/cardStatus")
    public Result cardStatus() {
        List<KeyValueVO> list = new ArrayList<>();

        for (CardStatusEnum statusEnum : CardStatusEnum.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(statusEnum.getBackValue());
            keyValueVO.setValue(statusEnum.getViewValue());

            list.add(keyValueVO);
        }
        return ResponseData.success(list);
    }

    /**
     * 停用状态
     *
     * @return
     */
    @RequestMapping("/disableType")
    public Result disableType() {

        List<KeyValueVO> list = new ArrayList<>();

        for (DisableTypeEnum statusEnum : DisableTypeEnum.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(statusEnum.getBackValue());
            keyValueVO.setValue(statusEnum.getViewValue());

            list.add(keyValueVO);
        }

        return ResponseData.success(list);
    }

    /**
     * 冻结状态
     *
     * @return
     */
    @RequestMapping("/freezeType")
    public Result freezeType() {

        List<KeyValueVO> list = new ArrayList<>();

        for (FreezeTypeEnum statusEnum : FreezeTypeEnum.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(statusEnum.getBackValue());
            keyValueVO.setValue(statusEnum.getViewValue());

            list.add(keyValueVO);
        }

        return ResponseData.success(list);
    }

    /**
     *  处理方式
     *
     * @return
     */
    @RequestMapping("/processMethod")
    public Result processMethod() {

        List<KeyValueVO> list = new ArrayList<>();

        for (ProcessingEnum statusEnum : ProcessingEnum.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(statusEnum.getBackValue());
            keyValueVO.setValue(statusEnum.getViewValue());

            list.add(keyValueVO);
        }

        return ResponseData.success(list);
    }

    /**
     *  处理方式
     *
     * @return
     */
    @RequestMapping("/processStatus")
    public Result processStatus() {

        List<KeyValueVO> list = new ArrayList<>();

        for (ProcessingStatusEnum statusEnum : ProcessingStatusEnum.values()) {
            KeyValueVO keyValueVO = new KeyValueVO();
            keyValueVO.setKey(statusEnum.getBackValue());
            keyValueVO.setValue(statusEnum.getViewValue());

            list.add(keyValueVO);
        }

        return ResponseData.success(list);
    }

    /**
     * 获取后台当前时间
     */
    @RequestMapping(value = "/getTime", method = RequestMethod.GET)
    public Result getCurDay(){
        return ResponseData.success(new Date());
    }


}
