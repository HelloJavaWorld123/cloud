package com.jzy.sup.mybatis.plus.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;


/**
 * <b>功能：</b>mybatis-plus<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190515&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;田星亮&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Configuration
@MapperScan(basePackages = "com.jzy.**.dao")
@EnableConfigurationProperties({MybatisPlusProperties.class})
public class MybatisPlusConfig {

    private MybatisPlusProperties properties;

    private Interceptor[] interceptors;

    private ResourceLoader resourceLoader;

    private DatabaseIdProvider databaseIdProvider;

    private List<ConfigurationCustomizer> configurationCustomizers;

    public MybatisPlusConfig(MybatisPlusProperties properties, ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader, ObjectProvider<DatabaseIdProvider> databaseIdProvider, ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
        this.properties = properties;
        this.interceptors = interceptorsProvider.getIfAvailable();
        this.resourceLoader = resourceLoader;
        this.databaseIdProvider = databaseIdProvider.getIfAvailable();
        this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
    }

    /**
     * <b>功能描述：</b>配置sqlSessionFactory<br>
     * <b>修订记录：</b><br>
     * <li>20190610&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setVfs(SpringBootVFS.class);
        factory.setMapperLocations(properties.resolveMapperLocations());
        factory.setTypeAliasesPackage(properties.getTypeAliasesPackage());
        factory.setPlugins(interceptors);
        if(org.springframework.util.StringUtils.hasText(properties.getConfigLocation())) {
            factory.setConfigLocation(resourceLoader.getResource(properties.getConfigLocation()));
        }

        org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
        if(configuration == null && !org.springframework.util.StringUtils.hasText(properties.getConfigLocation())) {
            configuration = new org.apache.ibatis.session.Configuration();
        }
        if(configuration != null && !CollectionUtils.isEmpty(configurationCustomizers)) {

            for (ConfigurationCustomizer customizer : configurationCustomizers) {
                customizer.customize(configuration);
            }
        }
        factory.setConfiguration(configuration);

        if(properties.getConfigurationProperties() != null) {
            factory.setConfigurationProperties(properties.getConfigurationProperties());
        }

        if(this.databaseIdProvider != null) {
            factory.setDatabaseIdProvider(databaseIdProvider);
        }

        if(!StringUtils.isEmpty(properties.getTypeHandlersPackage())) {
            factory.setTypeHandlersPackage(properties.getTypeHandlersPackage());
        }
        return factory.getObject();
    }


    /**
     * <b>功能描述：</b>性能<br>
     * <b>修订记录：</b><br>
     * <li>20190515&nbsp;&nbsp;|&nbsp;&nbsp;田星亮&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        /*<!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->*/
        performanceInterceptor.setMaxTime(1000);
        /*<!--SQL是否格式化 默认false-->*/
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }


}
