package com.g6.nfp121.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurations {
    @Configuration
    @Order(1)
    public class JWTConfiguration extends WebSecurityConfigurerAdapter {

        private final String[] AUTH_WHITELIST = {
                "/api/users/signin",
                "/api/users/signup",
                "/xml",
                "/webjars/**"
        };

        @Autowired
        private JwtTokenProvider jwtTokenProvider;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.requestMatchers(matchers -> matchers
                    .antMatchers("/api/**")
                    .antMatchers("/xml"));

            // No session will be created or used by spring security
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            // Entry points
            http.cors().and().csrf().disable();

            http.authorizeRequests()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    // Disallow everything else..
                    .anyRequest().authenticated();

            // If a user try to access a resource without having enough permissions
            http.exceptionHandling()
                    .accessDeniedPage("/");

            // Apply JWT
            http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // Allow swagger to be accessed without authentication
        }
    }

    @Configuration
    public class WebSecurityTemplateConfig extends WebSecurityConfigurerAdapter {

        private final String[] AUTH_WHITELIST = {
                "/",
                "/login",
                "/index"
        };

        public void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests((requests) -> requests
                            .antMatchers(AUTH_WHITELIST).permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin()
                    .defaultSuccessUrl("/index")
            ;
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            // Allow swagger to be accessed without authentication
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(12);
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            final CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
            // setAllowCredentials(true) is important, otherwise:
            // The value of the 'Access-Control-Allow-Origin' header in the response must
            // not be the wildcard '*' when the request's credentials mode is 'include'.
            // configuration.setAllowCredentials(true);
            // setAllowedHeaders is important! Without it, OPTIONS preflight request
            // will fail with 403 Invalid CORS request
            configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }
}