package com.example.cinema.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class TransactionFilter implements Filter {
    public static Logger logger = LogManager.getLogger("Transaction");

    private Logger getLogger() {
        return logger;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        getLogger().info(
                "Starting a transaction for req : {}",
                req.getRequestURI());

        chain.doFilter(request, response);
        getLogger().info(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }
}
