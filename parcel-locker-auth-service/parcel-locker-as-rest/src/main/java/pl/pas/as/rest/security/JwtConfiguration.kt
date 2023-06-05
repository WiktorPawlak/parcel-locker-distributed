package pl.pas.`as`.rest.security

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@Configuration
open class JwtConfiguration(@Value("\${jwt.public.key}") private val rsaPublicKey: RSAPublicKey,
                            @Value("\${jwt.private.key}") private val rsaPrivateKey: RSAPrivateKey) {

    @Bean
    open fun jwtEncoder(): JwtEncoder {
        val jwk = RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).build()
        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))
        return NimbusJwtEncoder(jwks)
    }

    @Bean
    open fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withPublicKey(rsaPublicKey).build()



}
