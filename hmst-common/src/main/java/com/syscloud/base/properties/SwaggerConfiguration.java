package com.syscloud.base.properties;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * The class Swagger configuration.
 *
 * @author paascloud.net@gmail.com
 */
@EnableSwagger2
public class SwaggerConfiguration {

	/**
	 * Reservation api docket.
	 *
	 * @return the docket
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
				.paths(PathSelectors.any())
				.build().enable(true)
				.securitySchemes(securitySchemes());
	}

	private List<ApiKey> securitySchemes() {
		List<ApiKey> apiKeyList= new ArrayList();
		apiKeyList.add(new ApiKey("x-auth-token", "token", "header"));
		return apiKeyList;
	}
	private ApiInfo apiInfo() {
		SwaggerProperties swagger = new SwaggerProperties();
		return new ApiInfoBuilder()
				.title(swagger.getTitle())
				.description(swagger.getDescription())
				.version(swagger.getVersion())
				.license(swagger.getLicense())
				.licenseUrl(swagger.getLicenseUrl())
				.contact(new Contact(swagger.getContactName(), swagger.getContactUrl(), swagger.getContactEmail()))
				.build();
	}
}