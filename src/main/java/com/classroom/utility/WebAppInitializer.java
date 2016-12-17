package com.classroom.utility;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import com.classroom.utility.config.WebConfig;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebAppInitializer implements WebApplicationInitializer {

    private static final String CONFIG_LOCATION = "com.classroom.utility.config";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        System.out.println("***** Initializing Application for " + servletContext.getServerInfo() + " *****");

        // Create ApplicationContext
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
        applicationContext.setConfigLocation(CONFIG_LOCATION);

        // Add the servlet mapping manually and make it initialize automatically
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("mvc-dispatcher", dispatcherServlet);

        servlet.addMapping("/");
        servlet.setAsyncSupported(true);
        servlet.setLoadOnStartup(1);
        //servlet.setMultipartConfig((MultipartConfigElement) applicationContext.getBean("multipartResolver"));
        
    }
}
