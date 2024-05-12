package org.example.springboot.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration // 表明这是一个配置类
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 在拦截器注册表中添加一个拦截器，这个拦截器是通过jwtInterceptor()方法获取的
        // addPathPatterns("/**")表示这个拦截器会拦截所有的请求
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register"); // 除了/user/login之外的所有请求都会被拦截
        super.addInterceptors(registry); // 调用父类的方法，将这个拦截器注册表添加到Spring Boot的配置中
    }

    @Bean // 表明这个方法会返回一个Bean，Spring Boot会将这个Bean添加到应用上下文中
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor(); // 创建一个新的JwtInterceptor对象并返回
    }
}