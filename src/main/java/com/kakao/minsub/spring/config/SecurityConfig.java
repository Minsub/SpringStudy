package com.kakao.minsub.spring.config;


import com.kakao.minsub.spring.config.security.UserAuthenticatedProcessingFilter;
import com.kakao.minsub.spring.config.security.UserAuthenticationProvider;
import com.kakao.minsub.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;

@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, proxyTargetClass = true)
@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/profile/**").hasAnyRole("ADMIN","NORMAL")
//                .antMatchers(HttpMethod.GET, "/post/**").hasAnyRole("ADMIN")
//                .antMatchers(HttpMethod.GET, "/page/index").permitAll()
//                .antMatchers(HttpMethod.GET, "/page/**").authenticated()
//                .antMatchers("/user/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new UserAuthenticatedProcessingFilter(authenticationManager()))
//                .httpBasic()
//        ;
    
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                .addFilter(new UserAuthenticatedProcessingFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(userAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**")
                .antMatchers("/resources/**")
                .antMatchers("/swagger-ui/**")
                .antMatchers("/page/login")
//                .anyRequest()
        ;
    }
    
    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> {
            String requestUrl = request.getRequestURL().toString();
            response.sendRedirect("/login?return_url=" + requestUrl);
        };
    }
}
