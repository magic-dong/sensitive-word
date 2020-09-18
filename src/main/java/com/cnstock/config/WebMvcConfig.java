package com.cnstock.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Web配置
 * 
 * @author lzd
 * @date 2019年4月2日
 * @version
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * 方式一
	 * 跨域支持
	 * 若集成shiro，此方式会失效（原因是shiro的过滤拦截器优先）
	 * 请使用方式二
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				 		.allowCredentials(true)  
				 		.allowedHeaders("*")  
				 		.allowedOrigins("*")  
					    .allowedMethods("*");
			}
		};
	}
	
//	/**
//	 * 方式二
//	 * 跨域支持
//	 * @author lzd
//	 * @date 2020年4月29日:下午2:44:04
//	 * @return
//	 * @description
//	 */
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    final CorsConfiguration config = new CorsConfiguration();
//	    // 允许cookies跨域
//	    config.setAllowCredentials(true);
//	    // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
//	    config.addAllowedOrigin("*");
//	    // #允许访问的头信息,*表示全部
//	    config.addAllowedHeader("*");
//	    // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
//	    //config.setMaxAge(3600L);
//	    // 允许提交请求的方法，*表示全部允许
//	    config.addAllowedMethod("*");
//	    source.registerCorsConfiguration("/**", config);
//
//	    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//	    // 设置监听器的优先级
//	    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//	    return bean;
//	}

	/**
	 * 设置 http:127.0.0.1:8080默认访问页
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addViewController("/").setViewName("redirect:index.html");
		//设置自定义页面访问优先（不设置会优先访问默认的index.html，Order值越低优先级越高）
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}

	@Bean
	public HttpMessageConverter<String> responseBodyConverter() {
		// 解决中文乱码问题
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(responseBodyConverter());
		super.configureMessageConverters(converters);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        /** 文件上传路径 */
        registry.addResourceHandler("/profile/**").addResourceLocations("file:" + Global.getProfile());
    }
}
