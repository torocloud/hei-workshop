package io.toro.workshop.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

@RestController
public class AuthenticationController {

    private final UserDetailsService userDetailsService;

    @Autowired
    AuthenticationController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    ResponseEntity<Map> login(@Valid @RequestBody LoginRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username);
        if (!userDetails.getPassword().equals(request.password)) {
            throw new BadCredentialsException("Invalid credentials");
        }

        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .signWith(SignatureAlgorithm.HS512, "sup3rs3cr3t")
                .compact();
        Map payload = new HashMap<>();
        payload.put("token", token);

        return ResponseEntity.ok(payload);
    }
}
