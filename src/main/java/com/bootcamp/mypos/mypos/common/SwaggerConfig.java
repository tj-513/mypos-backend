package com.bootcamp.mypos.mypos.common;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(metaData())
                .tags(
                        new Tag("Greeting Controller", "A simple greeting controller to test the API availability"),
                        new Tag("Item Controller", "Operations pertaining to Items in in System"),
                        new Tag("User Controller", "Operations pertaining to Users in in System"),
                        new Tag("Order Controller", "Operations pertaining to Orders in System")
                        );
    }


    private ApiInfo metaData() {

        return new ApiInfoBuilder()
                .contact(new Contact("Tharindu Ranaweera","-","tharindu.ranaweera@syscolabs.com"))
                .description("Spring Boot REST API for MyPOS POS System")
                .title("MyPOS Backend-API")
                .build();

    }
}