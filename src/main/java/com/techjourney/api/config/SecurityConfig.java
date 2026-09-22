package com.techjourney.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Configuration
public class SecurityConfig {

    /*
     * PasswordEncoder
     *
     * Spring Security should never compare or store passwords as plain text.
     * BCrypt hashes the demo passwords before Spring Security stores them
     * in memory.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*
     * Demo users for Engineering Lab Experiment 03.
     *
     * These accounts intentionally exist only in memory.
     * They are not stored in H2 and are recreated whenever
     * the application starts.
     *
     * demo-user  -> ROLE_USER
     * demo-admin -> ROLE_ADMIN
     */
    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails demoUser = User.builder()
                .username("demo-user")
                .password(
                        passwordEncoder.encode("demo123"))
                .roles("USER")
                .build();

        UserDetails demoAdmin = User.builder()
                .username("demo-admin")
                .password(
                        passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                demoUser,
                demoAdmin);
    }


    /*
     * AuthenticationManager
     *
     * Our future Security Lab login endpoint will use this
     * to submit the username/password to Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration
                .getAuthenticationManager();
    }
    
    /*
     * SecurityContextRepository
     *
     * Stores the authenticated user's SecurityContext
     * in the HTTP session so authentication survives
     * beyond the original login request.
     */
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }


    /*
     * Main Spring Security configuration.
     *
     * Most of Tech Journey remains public.
     * Only the dedicated Spring Security experiment
     * receives role-based access rules.
     */
    @Bean SecurityFilterChain securityFilterChain(
            HttpSecurity http,SecurityContextRepository securityContextRepository)
            throws Exception {

        http
        
        .securityContext(securityContext ->
        securityContext.securityContextRepository(
                securityContextRepository
        )
)

                /*
                 * Tech Journey is primarily a REST API.
                 *
                 * Ignoring CSRF for /api/** prevents the existing
                 * Sandbox POST/PUT/DELETE requests from suddenly
                 * being blocked after adding Spring Security.
                 *
                 * The H2 console also requires CSRF exclusion.
                 */
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/api/**",
                                "/h2-console/**"
                        )
                )

                /*
                 * Authorization rules.
                 */
                .authorizeHttpRequests(auth -> auth

                        /*
                         * Spring Security Lab endpoints.
                         */

                		.requestMatchers(
                		        "/api/lab/security/login",
                		        "/api/lab/security/logout",
                		        "/api/lab/security/public",
                		        "/api/lab/security/session"
                		)
                		.permitAll()

                        /*
                         * USER and ADMIN may access
                         * the USER resource.
                         */
                        .requestMatchers(
                                "/api/lab/security/user"
                        )
                        .hasAnyRole(
                                "USER",
                                "ADMIN"
                        )

                        /*
                         * Only ADMIN may access
                         * the ADMIN resource.
                         */
                        .requestMatchers(
                                "/api/lab/security/admin"
                        )
                        .hasRole("ADMIN")

                        /*
                         * Keep the H2 console available.
                         */
                        .requestMatchers(
                                "/h2-console/**"
                        )
                        .permitAll()

                        /*
                         * Everything else in Tech Journey
                         * stays public.
                         */
                        .anyRequest()
                        .permitAll()
                )

                /*
                 * We do NOT want Spring Security's default
                 * generated HTML login page.
                 *
                 * Angular will provide the Engineering Lab
                 * login interface.
                 */
                .formLogin(
                        AbstractHttpConfigurer::disable
                )

                /*
                 * We also do not want browser Basic Auth.
                 *
                 * Authentication will be handled explicitly
                 * by our lab login endpoint.
                 */
                .httpBasic(
                        AbstractHttpConfigurer::disable
                )

                /*
                 * The H2 console uses frames.
                 */
                .headers(headers -> headers
                        .frameOptions(frame ->
                                frame.sameOrigin()
                        )
                )

                /*
                 * Make the teaching outcomes explicit:
                 *
                 * Not authenticated -> 401
                 * Authenticated but wrong role -> 403
                 */
                .exceptionHandling(exceptions -> exceptions

                        .authenticationEntryPoint(
                                (request,
                                 response,
                                 exception) ->
                                        response.sendError(
                                                401,
                                                "Unauthorized"
                                        )
                        )

                        .accessDeniedHandler(
                                (request,
                                 response,
                                 exception) ->
                                        response.sendError(
                                                403,
                                                "Forbidden"
                                        )
                        )
                );

        return http.build();
    }
}