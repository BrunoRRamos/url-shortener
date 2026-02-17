package com.br.shortener_url.infrastructure;

import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Duration;

import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;


public class RateLimitingFilter implements Filter {
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Value("${RATE_LIMITER_REFRESH_RATE:100}")
    private Integer REFRESH_RATE;

    @Value("${RATE_LIMITER_LIMIT:100}")
    private  Integer LIMIT;

    @Value("${RATE_LIMITER_SECONDS_TO_REFRESH:60}")
    private Integer SECONDS_TO_REFRESH;

    private Bucket getBucket(String clientId) {
        return this.buckets.computeIfAbsent(clientId, key ->
                Bucket.builder()
                        .addLimit(limit ->
                                limit.capacity(this.LIMIT)
                                    .refillGreedy(this.REFRESH_RATE, Duration.ofSeconds(this.SECONDS_TO_REFRESH)))
                        .build()
        );
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String clientIp = httpRequest.getRemoteAddr();
        Bucket bucket = getBucket(clientIp);
        int TOKENS_TO_CONSUME = 1;

        if (bucket.tryConsume(TOKENS_TO_CONSUME)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse)
                    .setStatus(TOO_MANY_REQUESTS.value());

            servletResponse.getWriter().write("Too Many Requests");
            servletResponse.getWriter().flush();
        }
    }
}
