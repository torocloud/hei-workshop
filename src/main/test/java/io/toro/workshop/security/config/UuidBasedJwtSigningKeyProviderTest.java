package io.toro.workshop.security.config;

import io.toro.workshop.security.config.JwtSigningKeyProvider;
import io.toro.workshop.security.config.UuidBasedJwtSigningKeyProvider;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.Test;

@Test
public class UuidBasedJwtSigningKeyProviderTest {

    @Test
    void testProviderGeneratesRandomKeys() {
        JwtSigningKeyProvider provider1 = new UuidBasedJwtSigningKeyProvider();
        JwtSigningKeyProvider provider2 = new UuidBasedJwtSigningKeyProvider();

        assertNotEquals( provider1.getSigningKey(), provider2.getSigningKey() );
    }

    @Test
    void testProviderReturnsTheSameKeys() {
        JwtSigningKeyProvider provider1 = new UuidBasedJwtSigningKeyProvider();
        String key = provider1.getSigningKey();
        for ( int i = 0; i < 10; i++ )
            assertEquals( key, provider1.getSigningKey() );
    }
}
