package com.pingchuan.weatherservice.config;

import com.pingchuan.weatherservice.service.impl.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                        Authentication authentication) throws IOException, ServletException {
                                        response.setContentType("application/json;charset=utf-8");
                                        RequestCache cache = new HttpSessionRequestCache();
                                        SavedRequest savedRequest = cache.getRequest(request, response);
                                        String url = savedRequest.getRedirectUrl();
                                        response.sendRedirect(url);
                                    }
                                })
                .and()
                .logout().logoutSuccessUrl("/index.html")
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "/index.html", "/").permitAll()
                .antMatchers("/baseSearch/**", "/other/**", "/stat/**", "/user/**", "/interface/findAll").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/3rds/**", "/css/**", "/images/**", "/js/**", "/json/**");
    }
}
