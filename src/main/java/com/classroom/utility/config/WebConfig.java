package com.classroom.utility.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan("com.classroom.utility")
public class WebConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/static/js/**")
                .addResourceLocations("/resources/static/js/");
        registry.addResourceHandler("/resources/static/css/**")
                .addResourceLocations("/resources/static/css/");
        registry.addResourceHandler("/resources/static/views/**")
                .addResourceLocations("/resources/static/views/");
        registry.addResourceHandler("/resources/static/**")
                .addResourceLocations("/resources/static/");
        registry.addResourceHandler("/resources/output/**")
        .addResourceLocations("/resources/output/");
        registry.addResourceHandler("/webjars/bootstrap/**")
        .addResourceLocations("/webjars/bootstrap/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/");
        
    }

    @Bean
    public ViewResolver getViewResolver(){
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        commonsMultipartResolver.setMaxUploadSize(50000000);
        return commonsMultipartResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
