package com.ljc.security.config;

import com.ljc.security.filter.CustomAuthenticationFilter;
import com.ljc.security.handler.LoginFailureHandler;
import com.ljc.security.handler.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityAdapterConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()  //此方法有多个子项，每个匹配器按其声明的顺序判定优先级
                .antMatchers("/resources/**", "/signup", "/about").permitAll() //允许指定路径
                .antMatchers("/admin/**").hasRole("ADMIN")  //指定路径所需的角色
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated()    //任何尚未匹配的URL只需要对用户进行身份验证
                .and()
                .formLogin()                         //支持表单登录，WebSecurityConfigurerAdapter默认应用
                .loginPage("/login").permitAll()     //指定登录页面的路径，以及允许所有请求访问该路径 PS：如果不指定则使用自带的默认页
                //.successHandler(loginSuccessHandler())   //指定登录成功、失败之后的处理器(对自定义过滤器不管用)
                //.failureHandler(loginFailureHandler())
                .and().httpBasic()
                .and().csrf().disable();

        //自定义登出操作
        http
                .logout()  //提供注销支持。使用WebSecurityConfigurerAdapter时会自动应用此选项
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
                /*
                .logoutSuccessHandler(logoutSuccessHandler) //指定一个自定义LogoutSuccessHandler，如果指定了此项，则忽略logoutSuccessUrl()
                .addLogoutHandler(logoutHandler)  //添加参与注销处理的LogoutHandler对象。默认情况下，SecurityContextLogoutHandler被添加为最后一个LogoutHandler
                .and()...*/

        //自定义登录过滤器
        http.addFilterBefore(this.customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        //第三方登录过滤器
        /*http.addFilterBefore(this.qqAuthenticationFilter(), MemberUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.wechatAuthenticationFilter(), MemberUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.registerSuccessFilter(), MemberUsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(this.fanliAuthenticationFilter(), MemberUsernamePasswordAuthenticationFilter.class);*/
    }

    @Bean
    public Filter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(super.authenticationManagerBean());
        authenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        authenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return authenticationFilter;
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("admin").password("111111").roles("USER").build());
        return manager;
    }

}
