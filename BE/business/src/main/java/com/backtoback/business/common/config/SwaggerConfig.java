package com.backtoback.business.common.config;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/*
 * http://localhost:8080/api/swagger-ui/index.html
 * */
@Configuration
@EnableWebMvc
public class SwaggerConfig {

	// Authorization Header
	public static final String SECURITY_SCHEMA_NAME = "JWT";
	public static final String AUTHORIZATION_SCOPE_GLOBAL = "global";
	public static final String AUTHORIZATION_SCOPE_GLOBAL_DESC = "accessEverything";

	private ApiInfo swaggerInfo() {
		return new ApiInfoBuilder().title("BackToBack API")
			.description("BackToBack API Docs").build();
	}

	@Bean
	public Docket swaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(swaggerInfo()).select()
			.apis(RequestHandlerSelectors.basePackage("com.backtoback.business"))
			.paths(PathSelectors.any())
			.build()
			.useDefaultResponseMessages(false)
			// Authorization Header
			.securityContexts(newArrayList(securityContext()))
			.securitySchemes(newArrayList(apiKey()));
	}

	private ApiKey apiKey() {
		return new ApiKey(SECURITY_SCHEMA_NAME, "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE_GLOBAL,
			AUTHORIZATION_SCOPE_GLOBAL_DESC);
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference(SECURITY_SCHEMA_NAME, authorizationScopes));
	}

}
