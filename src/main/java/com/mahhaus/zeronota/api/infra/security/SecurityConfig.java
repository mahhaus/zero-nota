package com.mahhaus.zeronota.api.infra.security;

import com.mahhaus.zeronota.api.infra.security.jwt.JwtAuthenticationFilter;
import com.mahhaus.zeronota.api.infra.security.jwt.JwtAuthorizationFilter;
import com.mahhaus.zeronota.api.infra.security.jwt.handler.AccessDeniedHandler;
import com.mahhaus.zeronota.api.infra.security.jwt.handler.UnauthorizedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

/**
 * @author josias.soares
 * Create 05/09/2019
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authManager = authenticationManager();

        http
                .headers()
//                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "*"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Headers", "Cache-Control,Access-Control-Allow-Headers,  Pragma, Origin, Authorization, Content-Type, X-Requested-With"))
                .addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Methods", "OPTIONS, DELETE, GET, PUT, POST"))
                .and()

                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/login").permitAll()
                .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .permitAll()
                .anyRequest().authenticated().and()
                .cors().and()
                .csrf().disable()
                .addFilter(new JwtAuthenticationFilter(authManager))
                .addFilter(new JwtAuthorizationFilter(authManager, userDetailsService))
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                // Removendo os cookies
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}
