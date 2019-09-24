package com.liuyun.liuyunshiro.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName liuyun-shiro
 * @ClassName JsonConvertUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/24 19:10
 * @Version 2.1.3
 **/
public class GsonConvertUtils {

    private static Gson gson = null;

    static {
        // 当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的
        // 显示形式是"key":null，而直接new出来的就没有"key":null的
        gson= new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private GsonConvertUtils() {}

    /**
     * @description 将对象转成json格式
     * @author 王栋
     * @date 2019/9/24 19:21
     * @param obj
     * @return java.lang.String
     **/
    public static String toJson(Object obj) {
        String json = null;
        if (gson != null) {
            json = gson.toJson(obj);
        }
        return json;
    }

    /**
     * @description 将json转成特定的cls的对象
     * @author 王栋
     * @date 2019/9/24 19:23
     * @param json
     * @param cls
     * @return T
     **/
    public static <T> T toBean(String json, Class<T> cls) {
        T t = null;
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson.fromJson(json, cls);
        }
        return t;
    }

    /**
     * @description json字符串转成list
     * @author 王栋
     * @date 2019/9/24 19:25
     * @param json
     * @param cls
     * @return java.util.List<T>
     **/
    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson.fromJson(json, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }

    /**
     * @description json字符串转成list中有map的
     * @author 王栋
     * @date 2019/9/24 19:25
     * @param json
     * @return java.util.List<java.util.Map<java.lang.String,T>>
     **/
    public static <T> List<Map<String, T>> toListMaps(String json) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(json,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * @description json字符串转成map的
     * @author 王栋
     * @date 2019/9/24 19:25
     * @param json
     * @return java.util.Map<java.lang.String,T>
     **/
    public static <T> Map<String, T> toMaps(String json) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(json, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
