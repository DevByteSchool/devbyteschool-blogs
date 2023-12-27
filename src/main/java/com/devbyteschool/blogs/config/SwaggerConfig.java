package com.devbyteschool.blogs.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.devbyteschool.blogs.controller"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaData());
    }


    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("DevByteSchool API Documentation.")
                .description("")
                .version("2.0.1")
                .license("©Copyright DevByteSchool. All Rights Reserved")
                .licenseUrl("")
                .contact(new Contact("DevByteSchool", "https://www.devbyteschool.com/", "contact@devbyteschool.com"))
                .build();

    }

}
