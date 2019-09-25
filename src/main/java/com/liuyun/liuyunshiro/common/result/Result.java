package com.liuyun.liuyunshiro.common.result;

import com.liuyun.liuyunshiro.common.constant.GlobalConstants;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: liuyun-shiro
 * @description: 接口返回数据格式
 * @author: WangDong
 * @create: 2019-09-22 13:21
 **/
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回处理消息
     */
    private String message;

    /**
     * 时间戳
     */
    private long timestamp;

    /**
     * 返回数据对象 data
     */
    private T result;

    public Result() {
        this.code = GlobalConstants.SUCCESS_CODE;
        this.message = GlobalConstants.SUCCESS_MSG;
        this.timestamp = System.currentTimeMillis();
        this.result = null;
    }

    public static Result success() {
        return new Result();
    }

    public static Result<Object> success(Object data) {
        Result<Object> r = new Result<>();
        r.setResult(data);
        return r;
    }

    public static Result<Object> success(String message, Object data) {
        Result<Object> r = new Result<>();
        r.setMessage(message);
        r.setResult(data);
        return r;
    }

    public static Result<Object> fail(String message) {
        return fail(GlobalConstants.FAIL_CODE, message);
    }

    public static Result<Object> fail(Integer code, String msg) {
        Result<Object> r = new Result<Object>();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }
}
