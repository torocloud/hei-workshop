package io.toro.workshop.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, User> userStore;

    public InMemoryUserDetailsService() {
        userStore = new LinkedHashMap<>();

        // TODO Initialize userStore with an user called admin/admin
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        if ( username == null || username.isEmpty() )
            throw new UsernameNotFoundException( "Username can't be empty" );

        // TODO fetch user by username from userStore

        return user;
    }

}
