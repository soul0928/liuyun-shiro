package com.liuyun.liuyunshiro.config.shiro;

import com.liuyun.liuyunshiro.common.exception.GlobalException;
import com.liuyun.liuyunshiro.common.result.Result;
import com.liuyun.liuyunshiro.common.util.json.GsonConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ProjectName liuyun-shiro
 * @ClassName LiuYunJwtFilter
 * @Description JWT过滤
 * @Author WangDong
 * @Date 2019/9/24 19:02
 * @Version 2.1.3
 **/
@Slf4j
public class LiuYunJwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 查看当前Header中是否携带Authorization属性(Token)，有的话就进行登录认证授权
        if (!isLoginAttempt(request)) {
            // 没有携带Token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 获取当前请求类型
            String httpMethod = httpServletRequest.getMethod();
            // 获取当前请求URI
            String requestURI = httpServletRequest.getRequestURI();
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, httpMethod);
            // mustLoginFlag = true 开启任何请求必须登录才可访问
            Boolean mustLoginFlag = true;
            if (mustLoginFlag) {
                this.response401(response, "请先登录");
                return false;
            }
        }


        try {
            // 进行 Shiro 的登录 LiuYunShiroRealm
            executeLogin(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * @description 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
     * @author 王栋
     * @date 2019/9/24 19:07
     * @param request
     * @return java.lang.Boolean
     **/
    private Boolean isLoginAttempt(ServletRequest request) {
        String token = this.getAuthzHeader(request);
        return token != null;
    }


    /**
     * @description 直接返回Response信息
     * @author 王栋
     * @date 2019/9/24 19:27
     * @param response
     * @param msg
     * @return void
     **/
    private void response401(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (
            PrintWriter out = httpServletResponse.getWriter()) {
            String data = GsonConvertUtils.toJson(Result.fail(msg));
            out.append(data);
        } catch (IOException e) {
            log.error("直接返回Response信息出现IOException异常:" + e.getMessage());
            throw new GlobalException("直接返回Response信息出现IOException异常:" + e.getMessage());
        }
    }

    /**
     * 进行AccessToken登录认证授权
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        LiuYunJwtToken token = new LiuYunJwtToken(this.getAuthzHeader(request));
        // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
        this.getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * @description 对跨域提供支持
     * @author 王栋
     * @date 2019/9/24 19:29
     * @param request
     * @param response
     * @return boolean
     **/
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
