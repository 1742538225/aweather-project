package com.id0304.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MyMvcConfig extends WebMvcConfigurationSupport {
    //资源处理器,当请求一些静态资源时会默认在以下路径 寻找
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //视图解析器在url访问时会自动 补上 前后缀,记得这个不是WebMvcConfigurationSupport类下的,需要手动注入工厂
    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setPrefix("/html/");
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

    //视图控制器,在访问对应的url时会跳转到对应的定义的新视图,受视图解析器的影响
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
}