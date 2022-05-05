package com.zhu.demo.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 河南张国荣
 * @create 2022-03-14 21:37
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.zhu.demo.mapper") //指定mapper位置
public class MybatisplusConfig {

    //初始化数据源1
    @Bean(name = "first")
    // @Primary
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSource db1() {
        return DruidDataSourceBuilder.create().build();
    }

    //初始化数据源2
    @Bean(name = "second")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource db2() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     *
     * @return
     */
    @Bean
    @Primary
    public DataSource multipleDataSource(@Qualifier("first") DataSource first, @Qualifier("second") DataSource second) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        //放入数据源1
        targetDataSources.put(DBTypeEnum.first.getValue(), first);
        //放入数据源2
        targetDataSources.put(DBTypeEnum.second.getValue(), second);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(first);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(db1(), db2()));
        /**
         * setMapperLocations必须加，解决报错：invalid bound statement (not found)问题
         * 指定mapper所在位置
         */
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        sqlSessionFactory.setConfiguration(configuration);
        //添加分页功能
        return sqlSessionFactory.getObject();
    }
}

