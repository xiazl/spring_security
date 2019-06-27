package com.cims.configuration.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
//        Object dataSource = DynamicDataSourceHolder.getDataSourceKey();
        // 移除当前数据源
//        DynamicDataSourceHolder.removeDataSource();
        return DynamicDataSourceHolder.getDataSourceKey();
    }
}
