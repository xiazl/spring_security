package com.cims.configuration.datasource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author baidu
 * @date 2018/7/19 下午5:12
 * @description mybatis绑定到动态数据源
 **/

@Configuration
@AutoConfigureBefore({MybatisAutoConfiguration.class})
public class MybatisConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(MybatisConfiguration.class);

    @Autowired
    private MybatisAutoConfiguration configuration;

    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("routingDataSource") DataSource dataSource) throws Exception {
        return configuration.sqlSessionFactory(dataSource);
    }

}
