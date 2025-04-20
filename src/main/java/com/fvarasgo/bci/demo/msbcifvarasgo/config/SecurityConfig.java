package com.fvarasgo.bci.demo.msbcifvarasgo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()

                // ✅ Permitir Swagger y configuración de OpenAPI
                .antMatchers(
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/v3/api-docs/**",        // Incluye swagger-config
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/configuration/**"
                ).permitAll()

                // ✅ consola H2
                .antMatchers("/h2-console/**").permitAll()

                // ✅ Endpoints públicos personalizados
                .antMatchers(HttpMethod.POST, "/usuario").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
