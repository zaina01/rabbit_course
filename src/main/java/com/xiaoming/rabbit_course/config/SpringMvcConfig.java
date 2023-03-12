package com.xiaoming.rabbit_course.config;


import com.xiaoming.rabbit_course.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
@Configuration
public class SpringMvcConfig {
    @Bean
    public LocalValidatorFactoryBean getValidatorFactory() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
        return localValidatorFactoryBean;
    }
    @Bean
    public WebMvcConfigurer enableMatrixVariable(){
        return new WebMvcConfigurer() {
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                log.info("扩展消息转换器...");
                MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
                //设置对象转换器,底层使用Jackson将Java对象转为json
                jackson2HttpMessageConverter.setObjectMapper(new JacksonObjectMapper());
                converters.add(0,jackson2HttpMessageConverter);
            }
        };
    }
}
