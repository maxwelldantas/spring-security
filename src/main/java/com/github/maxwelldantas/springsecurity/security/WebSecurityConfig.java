package com.github.maxwelldantas.springsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
@EnableGlobalAuthentication
public class WebSecurityConfig {

    public static final String MANAGERS = "MANAGERS";
    public static final String USERS = "USERS";

    @Bean
    public UserDetailsService configure() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}user123") // {noop} significa a estratégia de criptografia
                .roles(USERS)
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}master123")
                .roles(MANAGERS)
                .build();

        /*
        Existem algumas implementações de criptografias utilizadas pelo Spring Security:
        {bcrypt} for BCryptPasswordEncoder (mais comum);
        {noop} for NoOpPasswordEncoder;
        {pbkdf2} for Pbkdf2PasswordEncoder;
        {scrypt} for SCryptPasswordEncoder;
        {sha256} for StandardPasswordEncoder.
        */

        return new InMemoryUserDetailsManager(user, admin);
    }

/*    protected void configusre(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/managers").hasAnyRole("MANAGERS")
                .antMatchers("/users").hasAnyRole("USERS","MANAGERS")
                .anyRequest().authenticated().and().formLogin();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/users")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasAnyRole(USERS, MANAGERS)
                )
                .formLogin(withDefaults()).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/managers")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().hasRole(MANAGERS)
                )
                .formLogin(withDefaults()).build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain3(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/login")
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .formLogin(withDefaults()).build();
    }

/*
    // Pode usar este também
    @Bean
    @Primary
    protected AuthenticationManagerBuilder configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("{noop}user123")
                .roles("USERS")
                .and()
                .withUser("admin")
                .password("{noop}master123")
                .roles("MANAGERS");

        return auth;
    }
*/

}
