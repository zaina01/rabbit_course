package com.xiaoming.rabbit_course.config;

import com.xiaoming.rabbit_course.filter.JwtAuthenticationTokenFilter;
import com.xiaoming.rabbit_course.globalException.JwtAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    //认证用户的来源
//    @Resource
//    private UserDetailsService userDetailsService;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
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

    //配置springSecurity跨域访问
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //所有的请求都允许跨域
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    //配置springSecurity相关信息
    @Override
    public void configure(HttpSecurity http) throws Exception {
                //关闭csrf
        http.csrf().disable()
//                开启跨域
                .cors()
//                 配置跨域
                .configurationSource(corsConfigurationSource()).and()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //对于登录接口 只允许匿名访问
                .antMatchers("/user/login","/user/signIn","/user/exists","/common/Download/*").anonymous()
                .antMatchers("/webjars/**","/swagger-ui.html","/v2/api-docs","/swagger-resources/**").permitAll()
                //除了上面外的所有请求全部需要鉴权
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);//未登录异常处理



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
