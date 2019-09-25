package com.liuyun.liuyunshiro.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunExceptionAdvice
 * @Description 全局异常捕捉
 * @Author WangDong
 * @Date 2019/9/23 15:57
 * @Version 2.1.3
 **/
@Slf4j
@RestControllerAdvice("com.liuyun.liuyunshiro")
public class LiuYunExceptionAdvice {

    /**
     * 捕捉GlobalException自定义异常
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    public Result globalException(GlobalException e) {
        return Result.fail(e.getMessage());
    }

    /**
     * 捕捉GlobalException自定义异常
     * @return
     */
    @ExceptionHandler(TokenExpiredException.class)
    public Result tokenExpiredException(GlobalException e) {
        return Result.fail(e.getMessage());
    }

}
