package com.zyz.JwtDemo.config;


import com.zyz.JwtDemo.aop.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticateInterceptor())
                .excludePathPatterns("/user/accountLogin")
                .addPathPatterns("/**");
    }

    @Bean
    public AuthenticationInterceptor authenticateInterceptor() {
        return new AuthenticationInterceptor();
    }

}
