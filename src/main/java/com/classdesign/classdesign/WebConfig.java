package com.classdesign.classdesign;

import com.classdesign.classdesign.interceptor.LoginInterceptor;
import com.classdesign.classdesign.interceptor.ManagerInterceptor;
import com.classdesign.classdesign.interceptor.SuperManagerInterceptor;
import com.classdesign.classdesign.interceptor.TeacherInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private SuperManagerInterceptor superManagerInterceptor;

    @Autowired
    private ManagerInterceptor managerInterceptor;

    @Autowired
    private TeacherInterceptor teacherInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/login");
        registry.addInterceptor(superManagerInterceptor)
                .addPathPatterns("/api/super_manager/**");
        registry.addInterceptor(managerInterceptor)
                .addPathPatterns("/api/manager/**");
        registry.addInterceptor(teacherInterceptor)
                .addPathPatterns("/api/teacher/**");
    }
}
