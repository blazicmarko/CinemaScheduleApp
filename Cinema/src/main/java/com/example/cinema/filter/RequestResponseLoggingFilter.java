package com.example.cinema.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(2)
public class RequestResponseLoggingFilter implements Filter {
    public static Logger logger = LogManager.getLogger("Transaction");

    private Logger getLogger() {
        return logger;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        getLogger().info(
                "Logging Request  {} : {}", req.getMethod(),
                req.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);
        getLogger().info(
                "Logging Response :{}",
                res.getContentType());
    }
}
