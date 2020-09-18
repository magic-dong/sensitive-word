package com.cnstock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger接口文档
 * @author lzd
 * @date 2019年4月2日
 * @version
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				 		// 详细定制
                		.apiInfo(apiInfo())
                		.select()
                		// 指定当前包路径
                		.apis(RequestHandlerSelectors.basePackage("com.cnstock.controller"))
                		// 扫描所有 .apis(RequestHandlerSelectors.any())
                		.paths(PathSelectors.any())
                		.build();
	}
	
    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                .title("标题：Sensitive-Word系统_接口文档")
                .description("描述：用于Sensitive-Word系统.....")
                .contact(new Contact("Sensitive-Word", null, null))
                .version("版本号:1.0")
                .build();
    }
}
