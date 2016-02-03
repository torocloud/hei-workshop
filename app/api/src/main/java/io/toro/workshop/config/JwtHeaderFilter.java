package io.toro.workshop.config;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtHeaderFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    JwtHeaderFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        // parse the token
        String username = Jwts.parser()
                .setSigningKey("sup3rs3cr3t")
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        // resolve actual user
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // let's authenticate this request
        Authentication auth = new PreAuthenticatedAuthenticationToken(userDetails, token, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        // and allow the response the continue
        chain.doFilter(request, response);

        // remove authentication afterwards; RESTful APIs are stateless
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
