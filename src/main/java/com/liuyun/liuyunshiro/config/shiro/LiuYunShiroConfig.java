package com.liuyun.liuyunshiro.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
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
     * @description Shiro 过滤器
     * @author 王栋
     * @date 2019/9/25 13:41
     * @param securityManager
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     **/
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
     * @description 管理配置
     * @author 王栋
     * @date 2019/9/25 13:41
     * @param liuYunShiroRealm
     * @return org.apache.shiro.mgt.SecurityManager
     **/
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
     * @description 加密
     * @author 王栋
     * @date 2019/9/25 13:41
     * @param
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     **/
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
     * @description shiro对特定路径的拦截
     * @author 王栋
     * @date 2019/9/25 13:41
     * @param
     * @return java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> shiroFilterMap() {
        // 设置路径映射，注意这里要用LinkedHashMap 保证有序
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/logout", "logout");
        // 对所有请求拦截到自定义的过滤器
        filterChainDefinitionMap.put("/**", FILTER_NAME);
        return filterChainDefinitionMap;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
