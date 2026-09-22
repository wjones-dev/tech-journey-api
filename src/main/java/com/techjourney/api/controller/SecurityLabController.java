package com.techjourney.api.controller;

import com.techjourney.api.dto.SecurityLoginRequest;
import com.techjourney.api.dto.SecurityLoginResponse;
import com.techjourney.api.dto.SecuritySessionResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lab/security")
public class SecurityLabController {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;


    public SecurityLabController(
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository) {

        this.authenticationManager =
                authenticationManager;

        this.securityContextRepository =
                securityContextRepository;
    }


    /*
     * LOGIN
     *
     * The visitor submits credentials to the real
     * Spring Security AuthenticationManager.
     *
     * Successful authentication is stored inside
     * the SecurityContext and persisted in the
     * HTTP session.
     */
    @PostMapping("/login")
    public ResponseEntity<SecurityLoginResponse> login(
            @Valid @RequestBody SecurityLoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {

            /*
             * This token represents credentials that
             * have NOT been authenticated yet.
             */
            Authentication authenticationRequest =
                    UsernamePasswordAuthenticationToken
                            .unauthenticated(
                                    loginRequest.username(),
                                    loginRequest.password()
                            );


            /*
             * Spring Security verifies the credentials
             * using our UserDetailsService and
             * PasswordEncoder.
             */
            Authentication authenticationResponse =
                    authenticationManager.authenticate(
                            authenticationRequest
                    );


            /*
             * Create a fresh SecurityContext and place
             * the authenticated user inside it.
             */
            SecurityContext securityContext =
                    SecurityContextHolder
                            .createEmptyContext();

            securityContext.setAuthentication(
                    authenticationResponse
            );

            SecurityContextHolder.setContext(
                    securityContext
            );


            /*
             * Persist the SecurityContext in the
             * HTTP session.
             *
             * This is what allows the next request
             * to still know who the user is.
             */
            securityContextRepository.saveContext(
                    securityContext,
                    request,
                    response
            );


            List<String> roles =
                    authenticationResponse
                            .getAuthorities()
                            .stream()
                            .map(authority ->
                                    authority.getAuthority()
                            )
                            .toList();


            SecurityLoginResponse loginResponse =
                    new SecurityLoginResponse(
                            true,
                            authenticationResponse.getName(),
                            roles,
                            "Authentication successful."
                    );


            return ResponseEntity.ok(
                    loginResponse
            );

        } catch (AuthenticationException exception) {

            /*
             * Authentication failed.
             *
             * Clear any temporary authentication state
             * and return the status we want the lab
             * visitor to learn:
             *
             * 401 = You have not successfully proven
             * who you are.
             */
            SecurityContextHolder.clearContext();

            SecurityLoginResponse loginResponse =
                    new SecurityLoginResponse(
                            false,
                            loginRequest.username(),
                            List.of(),
                            "Invalid username or password."
                    );


            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(loginResponse);
        }
    }


    /*
     * PUBLIC RESOURCE
     *
     * Authentication is not required.
     */
    @GetMapping("/public")
    public ResponseEntity<String> publicResource() {

        return ResponseEntity.ok(
                "Public security lab resource."
        );
    }


    /*
     * USER RESOURCE
     *
     * Spring Security only allows this controller
     * method to be reached by USER or ADMIN.
     */
    @GetMapping("/user")
    public ResponseEntity<String> userResource(
            Authentication authentication) {

        return ResponseEntity.ok(
                "USER resource accessed by "
                        + authentication.getName()
        );
    }


    /*
     * ADMIN RESOURCE
     *
     * Only ROLE_ADMIN reaches this method.
     *
     * A logged-in USER will be stopped by
     * Spring Security before this controller
     * method executes.
     */
    @GetMapping("/admin")
    public ResponseEntity<String> adminResource(
            Authentication authentication) {

        return ResponseEntity.ok(
                "ADMIN resource accessed by "
                        + authentication.getName()
        );
    }
    
    /*
     * LOGOUT
     *
     * Removes the authenticated SecurityContext
     * and invalidates the current HTTP session.
     *
     * After logout, protected resources should
     * once again return 401 Unauthorized.
     */
    @PostMapping("/logout")
    public ResponseEntity<SecurityLoginResponse> logout(
            HttpServletRequest request) {

        /*
         * Invalidate the session containing the
         * persisted Spring Security context.
         */
        var session =
                request.getSession(false);

        if (session != null) {
            session.invalidate();
        }


        /*
         * Remove authentication from the current
         * request thread as well.
         */
        SecurityContextHolder.clearContext();


        SecurityLoginResponse logoutResponse =
                new SecurityLoginResponse(
                        false,
                        null,
                        List.of(),
                        "Logout successful."
                );


        return ResponseEntity.ok(
                logoutResponse
        );
    }
    
    
    /*
     * SESSION STATUS
     *
     * Allows the Angular lab to ask Spring Security
     * whether the current HTTP session contains an
     * authenticated user.
     *
     * This endpoint itself is public so it can also
     * report the unauthenticated state.
     */
    @GetMapping("/session")
    public ResponseEntity<SecuritySessionResponse> session(
            Authentication authentication) {

        /*
         * Spring Security may represent a visitor who
         * has not logged in with an anonymous
         * Authentication object.
         *
         * We therefore check both that Authentication
         * exists and that it is not anonymous.
         */
        boolean authenticated =
                authentication != null
                        && authentication.isAuthenticated()
                        && !(authentication
                                instanceof AnonymousAuthenticationToken);


        if (!authenticated) {

            SecuritySessionResponse sessionResponse =
                    new SecuritySessionResponse(
                            false,
                            null,
                            List.of()
                    );

            return ResponseEntity.ok(
                    sessionResponse
            );
        }


        List<String> roles =
                authentication
                        .getAuthorities()
                        .stream()
                        .map(authority ->
                                authority.getAuthority()
                        )
                        .toList();


        SecuritySessionResponse sessionResponse =
                new SecuritySessionResponse(
                        true,
                        authentication.getName(),
                        roles
                );


        return ResponseEntity.ok(
                sessionResponse
        );
    }
    
    
    
    
}