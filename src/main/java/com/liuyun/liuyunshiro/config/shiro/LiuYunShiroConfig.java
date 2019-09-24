package com.liuyun.liuyunshiro.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunShiroConfig
 * @Description shiro 配置
 * @Author WangDong
 * @Date 2019/9/23 14:52
 * @Version 2.1.3
 **/
@Configuration
public class LiuYunShiroConfig {


    /**
     * 自己的过滤器
     */
    private static final String FILTER_NAME = "jwtFilter";

    /**
     * Shiro 过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();


        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put(FILTER_NAME, new LiuYunJwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setLoginUrl("/user/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroFilterMap());
        shiroFilterFactoryBean.setSecurityManager(securityManager);


        return shiroFilterFactoryBean;
    }

    /**
     * 管理配置
     */
    @Bean
    public SecurityManager securityManager(LiuYunShiroRealm liuYunShiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(liuYunShiroRealm);

        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        // 设置自定义Cache缓存
        securityManager.setCacheManager(new LiuYunShiroCacheManager());
        return securityManager;
    }

    /**
     * 加密
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //采用MD5 进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    /**
     * shiro对特定路径的拦截
     */
    public static Map<String, String> shiroFilterMap() {
        // 设置路径映射，注意这里要用LinkedHashMap 保证有序
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        //filterChainDefinitionMap.put("/user/add", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        //user允许 记住我和授权用户 访问，但在进行下单和支付时建议使用authc
        filterChainDefinitionMap.put("/**", FILTER_NAME);
        return filterChainDefinitionMap;
    }

}
