package com.cims.business.card.service.impl;

import com.cims.business.card.entity.Warehouse;
import com.cims.business.card.mapper.WarehouseMapper;
import com.cims.business.card.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-17 15:00
 * @description 仓库
 **/

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;

    @Override
    public List<Warehouse> list() {
        return warehouseMapper.list();
    }

}
