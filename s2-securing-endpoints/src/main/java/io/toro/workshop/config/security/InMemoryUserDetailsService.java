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

        Collection<GrantedAuthority> adminAuthorities = new HashSet<>();
        adminAuthorities.add( new SimpleGrantedAuthority( "ROLE_ADMIN" ) );
        User adminUser = new User( "admin", "admin", adminAuthorities );

        userStore.put( adminUser.getUsername(), adminUser );
    }

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
        if ( username == null || username.isEmpty() )
            throw new UsernameNotFoundException( "Username can't be empty" );

        User user = userStore.get( username );
        if ( user == null )
            throw new UsernameNotFoundException( "Username '" + username + "' wasn't found" );

        return user;
    }

}
