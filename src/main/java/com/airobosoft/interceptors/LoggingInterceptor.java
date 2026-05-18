package com.airobosoft.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {

        long startTime = System.currentTimeMillis();

        // store start time
        request.setAttribute("startTime", startTime);

        System.out.println("===================================");

        System.out.println("Before Controller Execution");

        // HTTP Method
        System.out.println("Method : "
                + request.getMethod());

        // API URL
        System.out.println("Request URL : "
                + request.getRequestURL());

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {

        long startTime =
                (long) request.getAttribute("startTime");

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        System.out.println("After Controller Execution");

        // Response Status
        System.out.println("Response Status : "
                + response.getStatus());

        // Total API execution time
        System.out.println("Execution Time : "
                + executionTime + " ms");

        System.out.println("===================================");
    }
}