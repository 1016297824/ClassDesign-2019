package com.classdesign.classdesign.interceptor;

import com.classdesign.classdesign.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TeacherInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String authority = (String) request.getAttribute("authority");

        if (!(authority.equals(User.Teacher))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "无权限！");
        }
        return true;
    }
}
