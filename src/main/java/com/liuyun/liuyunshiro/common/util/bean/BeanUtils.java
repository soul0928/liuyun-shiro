package com.liuyun.liuyunshiro.common.util.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName liuyun-shiro
 * @ClassName BeanUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/26 16:55
 * @Version 2.1.3
 **/
public class BeanUtils {

    /**
     * @description 对象转换
     * @author 王栋
     * @date 2019/9/26 16:56
     * @param source 原对象
     * @param target 目标对象
     * @return void
     **/
    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * @description 对象转换
     * @author 王栋
     * @date 2019/9/26 16:58
     * @param source 原对象
     * @param clazz 目标对象类型
     * @return T
     **/
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
        } catch (Exception e) {
        }
        copyProperties(source, target);
        return target;
    }

    /**
     * @description  给定集合转换为指定类型的集合
     * @author 王栋
     * @date 2019/9/26 16:59
     * @param sourceList 原对象
     * @param clazz 目标对象类型
     * @return java.util.List<T>
     **/
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> clazz) {
        List<T> targetList;
        if (sourceList.size() > 0) {
            targetList = new ArrayList<>(sourceList.size());
            for (S item : sourceList) {
                T target = copyProperties(item, clazz);
                targetList.add(target);
            }
        } else {
            targetList = new ArrayList<>(0);
        }
        return targetList;
    }
}
