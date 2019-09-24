package com.liuyun.liuyunshiro.common.util.jwt;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64工具
 * @ProjectName liuyun-shiro
 * @ClassName Base64ConvertUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/24 16:12
 * @Version 2.1.3
 **/
public class Base64ConvertUtils {

    private static final String CHARSE = "UTF-8";

    /**
     * @description 加密JDK1.8
     * @author 王栋
     * @date 2019/9/24 16:12
     * @param str
     * @return java.lang.String
     **/
    public static String encode(String str) throws UnsupportedEncodingException {
        byte[] encodeBytes = Base64.getEncoder().encode(str.getBytes(CHARSE));
        return new String(encodeBytes);
    }

    /**
     * @description 解密JDK1.8
     * @author 王栋
     * @date 2019/9/24 16:12
     * @param str
     * @return java.lang.String
     **/
    public static String decode(String str) throws UnsupportedEncodingException {
        byte[] decodeBytes = Base64.getDecoder().decode(str.getBytes(CHARSE));
        return new String(decodeBytes);
    }

}
