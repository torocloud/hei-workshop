package io.toro.workshop.security.config;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.Test;

@Test
public class InMemoryUserDetailsServiceTest {

    @Test
    void testServiceNeverReturnsNullUser() {
        InMemoryUserDetailsService service = new InMemoryUserDetailsService();

        try {
            UserDetails userDetails = service.loadUserByUsername( null );
            fail( "Should've thrown UsernameNotFoundException" );
        } catch ( Exception ex ) {
            assertTrue( ex instanceof UsernameNotFoundException );
        }

        try {
            UserDetails userDetails = service.loadUserByUsername( "" );
            fail( "Should've thrown UsernameNotFoundException" );
        } catch ( Exception ex ) {
            assertTrue( ex instanceof UsernameNotFoundException );
        }

        try {
            UserDetails userDetails = service.loadUserByUsername( "super" );
            fail( "Should've thrown UsernameNotFoundException" );
        } catch ( Exception ex ) {
            assertTrue( ex instanceof UsernameNotFoundException );
        }
    }

    @Test
    void testServiceRetrievesAdmin() {
        InMemoryUserDetailsService service = new InMemoryUserDetailsService();

        UserDetails userDetails = service.loadUserByUsername( "admin" );
        assertNotNull( userDetails );
        assertEquals( "admin", userDetails.getUsername() );
        assertEquals( "admin", userDetails.getPassword() );

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue( !authorities.isEmpty() );
        assertTrue( authorities.contains( new SimpleGrantedAuthority( "ROLE_ADMIN" ) ) );
    }
}
