package com.xiaming.rabbit_course.config;

import com.xiaming.rabbit_course.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //认证用户的来源
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //配置springSecurity相关信息

    @Override
    public void configure(HttpSecurity http) throws Exception {
                //关闭csrf
        http.csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口 只允许匿名访问
                .antMatchers("/user/login","/user/signIn").anonymous()
                //除了上面外的所有请求全部需要鉴权
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);




//                authorizeRequests()
//                .antMatchers("/login","logout")
//                .permitAll()
////                .antMatchers("/**").hasAnyRole("USER","ADMIN")
//                .anyRequest()
//                .authenticated()
////                .and()
////                .formLogin().loginPage("/login.html").loginProcessingUrl("/login").successForwardUrl("/index.html").failureForwardUrl("/failer.html")
////                .permitAll()
////                .and()
////                .logout()
////                .logoutUrl("/logout")
////                .logoutSuccessUrl("/login.html")
////                .invalidateHttpSession(true)
////                .permitAll()
//                .and()
//                .csrf()
//                .disable()
//                //基于token不需要session
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .headers()
//                .cacheControl();
//
//        //添加jwt 登录授权过滤器
//        http.addFilterBefore();
//        //添加自定义未授权和未登录结果返回
//        http.exceptionHandling()
//                .accessDeniedHandler()
//                .authenticationEntryPoint();
    }
}
