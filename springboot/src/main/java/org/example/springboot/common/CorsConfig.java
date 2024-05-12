package org.example.springboot.common;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration // 声明这是一个Spring配置类
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean // 声明这是一个Spring Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(); // 创建一个基于URL的跨域配置源
        CorsConfiguration corsConfiguration = new CorsConfiguration(); // 创建一个新的跨域配置
        corsConfiguration.addAllowedOrigin("*"); // 允许所有源进行跨域请求
        corsConfiguration.addAllowedHeader("*"); // 允许所有请求头
        corsConfiguration.addAllowedMethod("*"); // 允许所有请求方法
        corsConfiguration.setMaxAge(MAX_AGE); // 设置跨域请求的最大有效时长
        source.registerCorsConfiguration("/**", corsConfiguration); // 对所有路径应用这个跨域配置
        return new CorsFilter(source); // 创建并返回一个新的跨域过滤器，使用我们刚刚创建的配置源
    }
}