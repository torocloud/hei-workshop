package io.toro.workshop.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.toro.workshop.config.security.JwtSigningKeyProvider;

@RestController
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    @Autowired
    AuthenticationController( UserDetailsService userDetailsService ) {
        this.userDetailsService = userDetailsService;
    }

    // FIXME Cross Origin
    @RequestMapping( value = "/api/login", method = RequestMethod.POST )
    ResponseEntity<Map> login( @Valid @RequestBody LoginRequest request ) {
        // TODO Fetch user details and validate if password matches

        // TODO Generate a token
        String token = "";
        
        Map payload = new HashMap<>();
        payload.put( "token", token );

        return ResponseEntity.ok( payload );
    }
}
