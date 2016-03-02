package io.toro.workshop.config.security;

public interface JwtSigningKeyProvider {

    String getSigningKey();
}
