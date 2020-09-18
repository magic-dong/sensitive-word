package com.cnstock;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 * web容器中进行部署
 * @author lzd
 * @date 2019年3月29日
 * @version
 */
public class SensitiveWordServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SensitiveWordApplication.class);
    }

}
