package com.techie.auth.server.controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenRequestLoggingFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(TokenRequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, jakarta.servlet.ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (httpRequest.getRequestURI().equals("/oauth2/token")) {
            System.out.println("🔍 Token Request Headers:");
            httpRequest.getHeaderNames().asIterator().forEachRemaining(header -> {
                System.out.println(header + ": " + httpRequest.getHeader(header));
                logger.info("Header::"+ httpRequest.getHeader(header));
            });

            System.out.println("🔍 Token Request Parameters:");
            httpRequest.getParameterMap().forEach((key, value) -> {
                System.out.println(key + ": " + String.join(",", value));
                logger.info(key + ": Value::" + String.join(",", value));
            });
        }

        chain.doFilter(request, response);
    }
}