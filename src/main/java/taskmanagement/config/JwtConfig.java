package taskmanagement.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

import com.nimbusds.jose.jwk.RSAKey;

@Configuration
public class JwtConfig {

    @Bean
    public KeyPair keyPair() throws Exception {
                KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
                rsa.initialize(2048);
                return  rsa.generateKeyPair();
    }

    @Bean
    public JwtDecoder jwtDecoder(KeyPair keyPair) throws Exception {
        return NimbusJwtDecoder.withPublicKey((RSAPublicKey)keyPair.getPublic()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder(KeyPair keyPair) throws Exception {
        RSAKey rsaKey = new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey((RSAPrivateCrtKey) keyPair.getPrivate())
                .build();

        JWKSet jwkSet = new JWKSet(rsaKey);
        ImmutableJWKSet<SecurityContext> immutableJWKSet = new ImmutableJWKSet<>(jwkSet);
        return new NimbusJwtEncoder(immutableJWKSet);
    }

}
