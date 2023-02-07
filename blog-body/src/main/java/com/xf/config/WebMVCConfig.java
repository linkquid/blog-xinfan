package com.xf.config;

import com.xf.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 心烦
 * @ProjectName blog-xinfan
 * @Introduce : MVC配置类
 */

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //  跨越配置
        registry.addMapping("/**")
                .allowedOrigins("http://43.139.20.203","http://localhost","https://43.139.20.203")
                .allowedOrigins("https://www.xinfan.fun","http://www.xinfan.fun", "http://xinfan.fun")
                .allowedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("GET","POST","DELETE","PUT","OPTIONS");
    }

    /**
     * 静态资源过滤
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");

    }
}
