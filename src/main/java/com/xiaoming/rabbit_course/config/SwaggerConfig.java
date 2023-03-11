package com.xiaoming.rabbit_course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2  //开启swagger2
public class SwaggerConfig {

    //配置swagger 的Docket Bean 实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("分组名")//配置多个分组
                .enable(true) //是否开启swagger
                .select()
                //RequestHandlerSelectors,配置要扫描的接口的方式
                // basePackage() 指定要扫描的包  any()扫描全部 //none()不扫描 //withClassAnnotation() 指定要扫描的类注解
                .apis(RequestHandlerSelectors.basePackage("com.xiaoming.rabbit_course.controller"))
//                .paths()//过滤什么路径
                .build();
    }
    //配置wagger 信息=apiInfo
    private ApiInfo apiInfo() {
        Contact contact = new Contact("zaina01", "https://github.com/zaina01", "2623338082@qq.com");
        return new ApiInfo(
                "rabbit_course-API文档",
                "课程管理系统", //描述
                "1.0", //版本
                "urn:tos",
                contact,//作者信息
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList() //默认空的
        );
    }
}
