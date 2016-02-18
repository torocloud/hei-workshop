package io.toro.workshop.config.security;

import java.util.UUID;

public class UuidBasedJwtSigningKeyProvider implements JwtSigningKeyProvider {

    private final String signingKey;

    public UuidBasedJwtSigningKeyProvider() {
        this.signingKey = UUID.randomUUID().toString();
    }

    @Override
    public String getSigningKey() {
        return signingKey;
    }

}
