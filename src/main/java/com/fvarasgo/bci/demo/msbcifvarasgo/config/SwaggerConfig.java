package com.fvarasgo.bci.demo.msbcifvarasgo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


public class SwaggerConfig {

    @Value("${swagger.ui.enabled: false}")
    private boolean isEnabled;
    @Value("${swagger.info.name: Nombre de la API}")
    private String apiName;
    @Value("${swagger.info.description: Descripción de la API}")
    private String apiDescription;
    @Value("${swagger.info.version: Versión de la API}")
    private String apiVersion;
    @Value("${swagger.info.contact.name: Nombre del líder técnico}")
    private String contactName;
    @Value("${swagger.info.contact.mail: contacto@email.com}")
    private String contactEmail;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .enable(isEnabled)
                .useDefaultResponseMessages(false)
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                apiName,
                apiDescription,
                apiVersion,
                null,
                new Contact(contactName, null, contactEmail),
                null,
                null,
                Collections.emptyList()
        );
    }

}
