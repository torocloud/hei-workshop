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

/**
 * Loads user-specific data from an internal Map.
 * 
 * Other implementations include fetching users from an external database, a social login (facebook, twitter, etc),
 * and virtually anything, as long as it satisfies the method contract of UserDetailsService interface.
 */
public class InMemoryUserDetailsService implements UserDetailsService {

    private final Map<String, User> userStore;

    public InMemoryUserDetailsService() {
        userStore = new LinkedHashMap<>();

        // TODO Initialize userStore map with an user called admin/admin
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        if ( username == null || username.isEmpty() )
            throw new UsernameNotFoundException( "Username can't be empty" );

        // TODO fetch user by username from userStore

        return user;
    }

}
