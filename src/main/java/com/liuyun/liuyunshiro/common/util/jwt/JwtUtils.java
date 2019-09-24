package com.liuyun.liuyunshiro.common.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.liuyun.liuyunshiro.common.constant.ShiroConstants;
import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.common.util.properties.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JAVA-JWT工具类
 * @ProjectName liuyun-shiro
 * @ClassName JwtUtils
 * @Description
 * @Author WangDong
 * @Date 2019/9/24 16:07
 * @Version 2.1.3
 **/
@Slf4j
@Component
public class JwtUtils {

    /**
     * 过期时间改为从配置文件获取
     */
    private static String accessTokenExpireTime = (String) PropertiesUtils.getYml("shiro.access.token.expire-time");

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey = (String) PropertiesUtils.getYml("shiro.encryptJWTKey");

    /**
     * @description 校验token是否正确
     * @author 王栋
     * @date 2019/9/24 16:14
     * @param token Token
     * @return boolean 是否正确
     **/
    public static boolean verify(String token) {
        try {
            // 帐号加JWT私钥解密
            String secret = getClaim(token, ShiroConstants.ACCOUNT) + Base64ConvertUtils.decode(encryptJWTKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new GlobalException("JWTToken认证解密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    /**
     * @description 获得Token中的信息无需secret解密也能获得
     * @author 王栋
     * @date 2019/9/24 16:14
     * @param token
     * @param claim
     * @return java.lang.String
     **/
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出String类型，如果是其他类型返回null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new GlobalException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * @description 生成签名
     * @author 王栋
     * @date 2019/9/24 16:14
     * @param account 帐号
     * @param currentTimeMillis
     * @return java.lang.String 返回加密的Token
     **/
    public static String sign(String account, String currentTimeMillis) {
        try {
            // 帐号加JWT私钥加密
            String secret = account + Base64ConvertUtils.decode(encryptJWTKey);
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带account帐号信息
            return JWT.create()
                    .withClaim("account", account)
                    .withClaim("currentTimeMillis", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new GlobalException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(PropertiesUtils.getYml("shiro.encryptJWTKey"));
    }

}
