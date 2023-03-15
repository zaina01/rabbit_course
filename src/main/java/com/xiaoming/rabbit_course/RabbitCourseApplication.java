package com.xiaoming.rabbit_course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@EnableGlobalMethodSecurity(securedEnabled = true)//使用Spring Security 开启方法级动态授权
public class RabbitCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitCourseApplication.class, args);
    }

}
