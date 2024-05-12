package org.example.springboot.common;

import org.example.springboot.mapper.UserMapper;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.springboot.entity.User;
import org.example.springboot.common.Result;
import org.example.springboot.exception.ServiceException;
import org.example.springboot.mapper.UserMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.method.HandlerMethod;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)){
            token= request.getParameter("token");
        }

        if(!(handler instanceof HandlerMethod)){
            AuthAccess annotation= ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if(annotation==null){
                return true;
            }
        }
        if(StrUtil.isBlank(token)){
            throw new ServiceException("401", "无token，请重新登录");
        }
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "token无效，请重新登录");
        }
        User user = userMapper.selectById(Integer.valueOf(userId));
        if(user==null){
            throw new ServiceException("401", "用户不存在，请重新登录");
        }
        JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try{
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "token无效，请重新登录");
        }
        return true;
    }
}
