package com.jmgits.payment.base.config;

import com.fasterxml.classmate.TypeResolver;
import com.jmgits.payment.base.plugin.PageableParameterBuilderPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

/**
 * Created by javi.more.garc on 12-11-17.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(
            @Value("${application.environment}") String environment,
            @Value("${application.version}") String version,
            @Value("${application.swagger.title}") String title,
            @Value("${application.swagger.description}") String description) {

        return new Docket(SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(basePackage("com.jmgits.payment"))
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .apiInfo(apiInfo(title + " - " + environment, description, version))
                ;
    }

    @Bean
    public PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
        return new PageableParameterBuilderPlugin(nameExtractor, resolver);
    }

    //
    //  private methods

    private ApiInfo apiInfo(String title, String description, String version) {

        return new ApiInfoBuilder().title(title)
                .description(description)
                .version(version)
                .build();
    }
}
