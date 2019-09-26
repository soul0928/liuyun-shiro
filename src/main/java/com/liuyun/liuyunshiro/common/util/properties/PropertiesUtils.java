package com.liuyun.liuyunshiro.common.util.properties;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

/**
 * @ProjectName liuyun-shiro
 * @ClassName PropertiesUtils
 * @Description Properties工具类
 * @Author WangDong
 * @Date 2019/9/24 16:52
 * @Version 2.1.3
 **/
@Slf4j
public class PropertiesUtils {

    private static String PROPERTY_NAME = "application.yml";

    public static String getYml(String key){
        Resource resource = new ClassPathResource(PROPERTY_NAME);
        Properties properties = null;
        try {
            YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
            yamlFactory.setResources(resource);
            properties =  yamlFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        assert properties != null;
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println((String) getYml("shiro.cache.expire-time"));
    }

}
