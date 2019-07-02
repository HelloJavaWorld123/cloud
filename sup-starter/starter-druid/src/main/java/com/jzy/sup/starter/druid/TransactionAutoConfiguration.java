package com.jzy.sup.starter.druid;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.Properties;


/**
 * <b>功能：</b>事务管理器配置类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Configuration
@AutoConfigureAfter(DruidAutoConfiguration.class)
@EnableTransactionManagement
public class TransactionAutoConfiguration implements TransactionManagementConfigurer {

    //数据源
    private final DataSource dataSource;

    @Autowired
    public TransactionAutoConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * <b>功能描述：</b>配置事务管理器<br>
     * <b>修订记录：</b><br>
     * <li>20190610&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
    
    /**
     * <b>功能描述：</b>配置事务的传播特性<br>
     * <b>修订记录：</b><br>
     * <li>20190610&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public TransactionInterceptor myTransactionInterceptor() {
        TransactionInterceptor ti = new TransactionInterceptor();
        ti.setTransactionManager(annotationDrivenTransactionManager());
        Properties properties = new Properties();
        properties.setProperty("find*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("get*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("select*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("query*", "PROPAGATION_REQUIRED, readOnly");
        properties.setProperty("insert*", "PROPAGATION_REQUIRED");
        properties.setProperty("save*", "PROPAGATION_REQUIRED");
        properties.setProperty("add*", "PROPAGATION_REQUIRED");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED");
        properties.setProperty("update*", "PROPAGATION_REQUIRED");
        properties.setProperty("dispose*", "PROPAGATION_REQUIRED");
        ti.setTransactionAttributes(properties);
        return ti;
    }

    
    /**
     * <b>功能描述：</b>配置事务的代理<br>
     * <b>修订记录：</b><br>
     * <li>20190610&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Bean
    public BeanNameAutoProxyCreator transactionAutoProxy() {
        BeanNameAutoProxyCreator transactionAutoProxy = new BeanNameAutoProxyCreator();
        transactionAutoProxy.setProxyTargetClass(false);
        transactionAutoProxy.setBeanNames("*ServiceImpl");
        transactionAutoProxy.setInterceptorNames("myTransactionInterceptor");
        return transactionAutoProxy;
    }
}
