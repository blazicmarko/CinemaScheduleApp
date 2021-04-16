package com.example.cinema.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class LoggerInterceptor implements HandlerInterceptor {
    public static Logger logger = LogManager.getLogger(LoggerInterceptor.class.getName());

    private Logger getLogger() {
        return logger;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        getLogger().info("[preHandle][" + request + "]" + "[" + request.getMethod()
                + "]" + request.getRequestURI());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        getLogger().info("[postHandle][" + request + "]");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            ex.printStackTrace();
        }
        getLogger().info("[afterCompletion][" + request + "][exception: " + ex + "]");
    }

    private String getParameters(HttpServletRequest request) throws IOException {
        String param;
        param = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        return param;
    }


}
