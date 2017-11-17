package com.jmgits.payment.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        resolver.setFallbackPageable(new PageRequest(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE));
        argumentResolvers.add(resolver);

        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/swagger-ui.html");
    }
}
