package com.zyz.JwtDemo.aop;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zyz.JwtDemo.annotation.PassToken;
import com.zyz.JwtDemo.constants.StateCode;
import com.zyz.JwtDemo.constants.UserConstant;
import com.zyz.JwtDemo.exception.LoginException;
import com.zyz.JwtDemo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 拦截器
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有PassToken注释，有则跳过认证
        Annotation[] annotations = method.getAnnotations();
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                if (!StringUtils.isEmpty(token)) {
                    try {
                        String userId = JWT.decode(token).getAudience().get(0);
                        request.setAttribute(UserConstant.USER_ID, userId);
                    } catch (JWTDecodeException j) {
                        //允许无token
                    }
                }
                return true;
            }
        }

        if (StringUtils.isEmpty(token)) {
            throw new LoginException(StateCode.LOGIN_NOT_TOKEN);
        }

        try {
            //校验token
            String userId = tokenService.verifyToken(token);

            // 校验黑名单...

            request.setAttribute(UserConstant.USER_ID, userId);
        } catch (JWTDecodeException d) {
            throw new LoginException(StateCode.LOGIN_PARAM_ERROR);
        } catch (JWTVerificationException v) {
            throw new LoginException(StateCode.TOKEN_INVALID);
        }

        return true;
    }

}
