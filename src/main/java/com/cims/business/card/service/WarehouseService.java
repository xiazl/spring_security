package com.cims.business.card.service;

import com.cims.business.card.entity.Warehouse;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-17 14:59
 * @description 银行
 **/
public interface WarehouseService {
    /**
     * 银行下来列表
     * @return
     */
    List<Warehouse> list();
}
