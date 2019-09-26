package com.liuyun.liuyunshiro.common.util.jwt;

import com.liuyun.liuyunshiro.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @ProjectName liuyun-shiro
 * @ClassName SerializableUtils
 * @Description Serializable工具(JDK)
 * @Author WangDong
 * @Date 2019/9/25 16:31
 * @Version 2.1.3
 **/
@Slf4j
public class SerializableUtils {

    /**
     * @description 序列化
     * @author 王栋
     * @date 2019/9/26 14:34
     * @param object
     * @return byte[]
     **/
    public static byte[] serializable(Object object) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            log.error("SerializableUtil工具类序列化出现IOException异常:" + e.getMessage());
            throw new GlobalException("SerializableUtil工具类序列化出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                log.error("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
                throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
            }
        }
    }

    /**
     * @description 反序列化
     * @author 王栋
     * @date 2019/9/26 14:34
     * @param bytes
     * @return java.lang.Object
     **/
    public static Object unserializable(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (ClassNotFoundException e) {
            log.error("SerializableUtil工具类反序列化出现ClassNotFoundException异常:" + e.getMessage());
            throw new GlobalException("SerializableUtil工具类反序列化出现ClassNotFoundException异常:" + e.getMessage());
        } catch (IOException e) {
            log.error("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
            throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                log.error("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
                throw new GlobalException("SerializableUtil工具类反序列化出现IOException异常:" + e.getMessage());
            }
        }
    }
}
