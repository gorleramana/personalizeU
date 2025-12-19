/**
 * 
 */
package com.rg.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author gorle
 */
@Configuration
@EnableWebSecurity
@Slf4j
//@EnableWebFluxSecurity
public class SecurityConfiguration {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
         .csrf(csrf -> csrf.disable())
         .cors().and()
         .authorizeHttpRequests(auth -> auth
             .requestMatchers("/health", "/actuator/**", "/users/register").permitAll()
             .anyRequest().authenticated()
         )
         .httpBasic(httpBasic -> {});
     return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        log.info("Configuring in-memory user details service");
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin123")
            //.roles("USER")
            .build();
        log.debug("Created default admin user for authentication");
        return new InMemoryUserDetailsManager(user);
    }
    
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http
//            .oauth2Login()    // Enables OAuth2 login flow (SSO)
//            .and()
//            .authorizeExchange(exchange -> exchange
//                .pathMatchers("/login", "/public/**").permitAll()
//                .anyExchange().authenticated()
//            );
//        return http.build();
//    }
}
