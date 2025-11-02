package com.course.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GenInterceptor genInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(genInterceptor)
        .addPathPatterns("/**")
                // 排除积分测试观察接口
        .excludePathPatterns("/current/user/**","/error")
                .excludePathPatterns("/user/query/**","/error")
                .excludePathPatterns("/BloodSugar/**","/error")
                .excludePathPatterns("/YdgnNote/**","/error")
                .excludePathPatterns("/points/**","/error")
                .excludePathPatterns("/user/bloodSugar/**","/error")
                .excludePathPatterns("/api/bloodSugar/**","/error")
                .excludePathPatterns("/api/points/**","/error")
                .excludePathPatterns("/bfzNode/**","/error")
                .excludePathPatterns("/followUp/**","/error")
                .excludePathPatterns("/followUp/**","/error")
                .excludePathPatterns("/extendedActivity/**","/error")
                .excludePathPatterns("/researchRecruitment/**","/error")
        .excludePathPatterns("/account/**", "/error");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:5174")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
