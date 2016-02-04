package io.toro.workshop.security.config;

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

        Collection<GrantedAuthority> adminAuthorities = new HashSet<>();
        adminAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        User adminUser = new User("admin", "admin", adminAuthorities);

        userStore.put(adminUser.getUsername(), adminUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty())
            throw new UsernameNotFoundException("Username can't be empty");

        User user = userStore.get(username);
        if (user == null)
            throw new UsernameNotFoundException("Username '" + username + "' wasn't found");

        return user;
    }

}
