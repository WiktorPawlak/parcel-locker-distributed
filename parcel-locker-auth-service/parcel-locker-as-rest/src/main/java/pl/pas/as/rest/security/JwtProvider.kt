package pl.pas.`as`.rest.security

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.security.oauth2.jwt.JwtException
import pl.pas.`as`.core.model.user.Role
import java.time.Instant
import java.util.Optional

@Configuration
@RequiredArgsConstructor
open class JwtProvider(private val jwtEncoder: JwtEncoder, private val jwtDecoder: JwtDecoder) {

    private val ISSUER = "Parcel-locker"

    @Value("\${jwt.expiration.time}")
    private val estimationTime = 0

    fun decode(stringJwt: String): Optional<SimpleJwt> {
        return try {
            val jwt: Jwt = jwtDecoder.decode(stringJwt)
            val username: String = jwt.subject
            val roles: List<Role> = jwt.getClaimAsStringList("roles").map { Role.valueOf(it) }
            Optional.of<SimpleJwt>(SimpleJwt(username, roles))
        } catch (e: JwtException) {
            Optional.empty<SimpleJwt>()
        }
    }

    fun encode(username: String, roles: MutableCollection<out GrantedAuthority>): String {
        val stringRoles: String = roles.joinToString(" ")

        val now = Instant.now()
        val claims: JwtClaimsSet = JwtClaimsSet.builder()
            .issuedAt(now)
            .issuer(ISSUER)
            .expiresAt(now.plusSeconds(estimationTime.toLong()))
            .subject(username)
            .claim("roles", stringRoles)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }

}

data class SimpleJwt(val username: String, val roles: List<Role>)
