package io.toro.workshop.security.config;

import static org.mockito.Mockito.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Test
public class JwtHeaderFilterTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    private JwtSigningKeyProvider signingKeyProvider;
    private UserDetailsService userDetailsService;

    @BeforeMethod
    void beforeMethod() {
        request = mock( HttpServletRequest.class );
        response = mock( HttpServletResponse.class );
        filterChain = mock( FilterChain.class );

        signingKeyProvider = mock( JwtSigningKeyProvider.class );
        userDetailsService = mock( UserDetailsService.class );
    }

    @Test
    void testFilterSkipsWhenDoesNotHaveValidHeader() throws Exception {
        JwtHeaderFilter filter = new JwtHeaderFilter( signingKeyProvider, userDetailsService );

        when( request.getHeader( eq( "Authorization" ) ) ).thenReturn( null );

        filter.doFilterInternal( request, response, filterChain );

        verify( request, atLeastOnce() ).getHeader( eq( "Authorization" ) );
        verify( filterChain, atLeastOnce() ).doFilter( eq( request ), eq( response ) );
    }

}
