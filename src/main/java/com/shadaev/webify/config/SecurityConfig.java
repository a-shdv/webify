package com.shadaev.webify.config;

import com.shadaev.webify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests((requests) -> requests
                        .antMatchers("/categories",
                                "/categories/**",
                                "/products",
                                "/products/**",
                                "/registration",
                                "/images/**",
                                "/uploads/**",
                                "/user/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .csrf().disable()
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

    }

//    @Override
//    public void configure (WebSecurity web) {
//        web
//                .ignoring()
//                .antMatchers(
//                        "/VAADIN/**",
//                        "/vaadinServlet/**",
//                        "/vaadinServlet/UIDL/**",
//                        "/vaadinServlet/HEARTBEAT/**",
//                        "/favicon.ico",
//                        "/robots.txt",
//                        "/manifest.webmanifest",
//                        "/sw.js",
//                        "/offline.html",
//                        "/icons/**",
//                        "/images/**",
//                        "/styles/**",
//                        "/h2-console/**");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


}