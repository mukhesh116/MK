package com.keybank.profile.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	@Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("BlockBone Company Profile Service").apiInfo(apiInfo()).select()
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.cloud")))
               .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.data.rest.webmvc")))
               .build();
    }

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("BlockBone Company Profile Service Micro Service").description(
				"BlockBone Company Profile micro service Developed in spring boot. This "
				+ "service will provide apis like fetchCompanyProfile,updateCompanyProfile etc. ")
				.contact("SasikumarChinnamuthu").license("Apache License Version 2.0").version("1.1.0").build();
	}
}
