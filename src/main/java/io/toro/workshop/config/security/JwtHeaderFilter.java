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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.toro.workshop.exception.ApiException;

public class JwtHeaderFilter extends OncePerRequestFilter {

    private final JwtSigningKeyProvider signingKeyProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    JwtHeaderFilter( JwtSigningKeyProvider signingKeyProvider, UserDetailsService userDetailsService,
            ObjectMapper objectMapper ) {
        this.signingKeyProvider = signingKeyProvider;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
            throws ServletException, IOException {
        String header = request.getHeader( HttpHeaders.AUTHORIZATION );
        if ( header == null || !header.startsWith( "Bearer " ) ) {
            chain.doFilter( request, response );
            return;
        }

        String token = header.substring( 7 );
        String username;
        try {
            // parse the token
            username = Jwts.parser()
                    .setSigningKey( signingKeyProvider.getSigningKey() )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();
        } catch ( Exception ex ) {
            // let's render a JSON response, indicating that the token is invalid; 
            // hence user is unauthorized
            response.setContentType( MediaType.APPLICATION_JSON_VALUE );
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

            ApiException apiEx = new ApiException( HttpServletResponse.SC_UNAUTHORIZED, "Invalid token" );
            objectMapper.writeValue( response.getOutputStream(), apiEx );
            return;
        }
        
        // resolve actual user
        UserDetails userDetails = userDetailsService.loadUserByUsername( username );

        // let's authenticate this request
        Authentication auth = new PreAuthenticatedAuthenticationToken( userDetails, token, userDetails.getAuthorities() );
        SecurityContextHolder.getContext().setAuthentication( auth );
        // and allow the response the continue
        chain.doFilter( request, response );

        // remove authentication afterwards; RESTful APIs are stateless
        SecurityContextHolder.getContext().setAuthentication( null );
    }

}
