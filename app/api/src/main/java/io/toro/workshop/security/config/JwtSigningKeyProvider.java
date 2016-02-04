package io.toro.workshop.security.config;

public interface JwtSigningKeyProvider {
    
    String getSigningKey();
}
