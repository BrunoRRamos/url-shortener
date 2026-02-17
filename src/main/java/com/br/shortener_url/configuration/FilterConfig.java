package com.br.shortener_url.configuration;


import com.br.shortener_url.infrastructure.RateLimitingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class FilterConfig {

    @Bean
    public RateLimitingFilter rateLimitingFilterBean() {
        return new RateLimitingFilter();
    }

    @Bean
    public FilterRegistrationBean<RateLimitingFilter> rateLimitingFilter(
            RateLimitingFilter filter) {

        FilterRegistrationBean<RateLimitingFilter> registration =
                new FilterRegistrationBean<>();

        registration.setFilter(filter);
        registration.addUrlPatterns("/api/shorten/*");
        registration.setOrder(1);

        return registration;
    }
}

