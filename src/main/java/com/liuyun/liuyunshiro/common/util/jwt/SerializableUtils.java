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
     * 序列化
     * @param object
     * @return byte[]
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
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
     * 反序列化
     * @param bytes
     * @return java.lang.Object
     * @author dolyw.com
     * @date 2018/9/4 15:14
     */
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

    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            return null;
        }
        long beginTime = System.currentTimeMillis();
        byte[] bytes = null;
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                baos.close();
            } catch (IOException e) {
            }
        }
        return bytes;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        long beginTime = System.currentTimeMillis();
        Object object = null;
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            if (bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ois = new ObjectInputStream(bais);
                object = ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
                bais.close();
            } catch (IOException e) {
            }
        }
        return object;
    }
}
