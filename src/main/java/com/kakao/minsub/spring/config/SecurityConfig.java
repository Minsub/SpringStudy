package com.kakao.minsub.spring.config;


import com.kakao.minsub.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;

/**
 * Created by kakao on 2017. 7. 25..
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/profile/**").hasAnyRole("ADMIN","NORMAL")
                .antMatchers(HttpMethod.GET, "/post/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(userService.passwordEncoder());

//        auth.inMemoryAuthentication()
//                .withUser("kakao")
//                .password("kakao")
//                .authorities("ROLE_ADMIN","ROLE_USER")
//                .and()
//                .withUser("admin")
//                .password("admin")
//                .authorities("ROLE_ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/static/**")
            .antMatchers("/user/**");
    }
}
