package io.toro.workshop.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtHeaderFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    JwtHeaderFilter( UserDetailsService userDetailsService ) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
            throws ServletException, IOException {
        // TODO Determine and intercept if request has header

        String username;
        try {
            // TODO parse the token
            username = "";
            throw new UnsupportedOperationException ("Still a TODO" );
        } catch ( Exception ex ) {
            throw new BadCredentialsException( "Invalid token", ex );
        }
        
        // TODO resolve actual user

        // let's authenticate this request
        Authentication auth = new PreAuthenticatedAuthenticationToken( userDetails, token, userDetails.getAuthorities() );
        SecurityContextHolder.getContext().setAuthentication( auth );
        // and allow the response the continue
        chain.doFilter( request, response );

        // remove authentication afterwards; RESTful APIs are stateless
        SecurityContextHolder.getContext().setAuthentication( null );
    }

}
