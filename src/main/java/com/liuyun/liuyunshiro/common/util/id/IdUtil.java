package com.liuyun.liuyunshiro.common.util.id;

/**
 * @program: liuyun-shiro
 * @description: id工具类
 * @author: WangDong
 * @create: 2019-09-22 13:55
 **/
public class IdUtil {

    /**
     * @description twitter项目snowflake
     * @author 王栋
     * @date 2019/4/24 19:09
     * @param
     * @return long
     **/
    public static long getNextId (){
        IdWorker idWorker = new IdWorker(0,0);
        return idWorker.nextId();
    }
    public static String getUUID (){
        return UUIDGenerator.generate();
    }

    public static void main(String[] args) {
        System.out.println(getNextId());
        System.out.println(getUUID());
    }

}
