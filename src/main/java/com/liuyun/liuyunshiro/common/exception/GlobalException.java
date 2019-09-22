package com.liuyun.liuyunshiro.common.exception;

import com.liuyun.liuyunshiro.common.constant.GlobalConstants;
import lombok.Data;

/**
 * @program: liuyun-shiro
 * @description: 自定义全局异常类
 * @author: WangDong
 * @create: 2019-09-22 13:11
 **/
@Data
public class GlobalException extends RuntimeException{

    /**
     * 错误编码
     */
    private Integer code;

    public GlobalException() {
        super(GlobalConstants.FAIL_MSG);
        this.code = GlobalConstants.FAIL_CODE;
    }

    public GlobalException(String message) {
        super(message);
        this.code = GlobalConstants.FAIL_CODE;
    }
}
