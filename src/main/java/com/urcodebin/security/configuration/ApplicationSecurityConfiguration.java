package com.urcodebin.security.configuration;

import com.urcodebin.backend.service.AccountDetailsService;
import com.urcodebin.security.CustomRequestCache;
import com.urcodebin.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/";
    private static final String LOGOUT_URL = "/logout";


    private final PasswordEncoder passwordEncoder;
    private final AccountDetailsService userAccountManager;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
                                        AccountDetailsService userAccountManager) {
        this.passwordEncoder = passwordEncoder;
        this.userAccountManager = userAccountManager;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .requestCache().requestCache(new CustomRequestCache())
            .and().authorizeRequests()
            .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

            .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
            .and().logout()
                .logoutUrl(LOGOUT_URL)
                .logoutSuccessUrl(LOGOUT_SUCCESS_URL)
                .invalidateHttpSession(true);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/VAADIN/**",
                "/favicon.ico",
                "/robots.txt",
                "/manifest.webmanifest",
                "/sw.js",
                "/offline.html",
                "/icons/**",
                "/images/**",
                "/styles/**");
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        final DaoAuthenticationProvider  userAuthProvider = new DaoAuthenticationProvider ();
        userAuthProvider.setPasswordEncoder(passwordEncoder);
        userAuthProvider.setUserDetailsService(userAccountManager);
        return userAuthProvider;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return userAccountManager;
    }
}
