package com.aidilude.example.component;

import com.aidilude.example.cache.ehcache.CustomerIdCache;
import com.aidilude.example.cache.ehcache.TokenCache;
import com.aidilude.example.annotation.MustLogin;
import com.aidilude.example.property.SystemProperties;
import com.aidilude.example.utils.Result;
import com.aidilude.example.utils.ResultCode;
import com.aidilude.example.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class ApiInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

    @Resource
    private CustomerIdCache customerIdCache;

    @Resource
    private TokenCache tokenCache;

    @Resource
    private ApplicationComponent applicationComponent;

    @Resource
    private SystemProperties systemProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /******************************************系统维护******************************************/

        /******************************************接口放行******************************************/
        String uri = request.getRequestURI();
        if(uri.contains("/test"))
            return true;
        /******************************************secret验证******************************************/
        String secret = request.getHeader("secret");
        String timestamp = request.getHeader("timestamp");
        if(!applicationComponent.isLegalAccess(secret, timestamp, systemProperties.getCustomerAccessKey())){
            Result.returnMsg(response, ResultCode.IllegalAccess, "非法访问.");
            return false;
        }
        /******************************************登录验证******************************************/
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        MustLogin ml = method.getAnnotation(MustLogin.class);
        String token = request.getHeader("token");
        if (ml != null) {
            if(!isLogin(token)){
                Result.returnMsg(response, ResultCode.NotLogin, "登录已过期，请重新登录.");
                return false;
            }
        }
        return true;
    }

    public boolean isLogin(String token){
        if(StringUtils.isEmpty(token))
            return false;
        Integer customerId = customerIdCache.getCustomerId(token);
        if(customerId == -1)
            return false;
        String previousToken = tokenCache.getToken(customerId);
        if(previousToken.equals("no exist"))
            return false;
        if(!previousToken.equals(token))
            return false;
        return true;
    }

}