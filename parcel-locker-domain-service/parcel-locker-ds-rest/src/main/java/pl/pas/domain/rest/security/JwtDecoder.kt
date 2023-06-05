package pl.pas.domain.rest.security

import lombok.RequiredArgsConstructor
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import pl.pas.`as`.core.model.user.Role
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import java.util.Optional


@Configuration
@RequiredArgsConstructor
open class JwtDecoder(@Value("\${public.key.url}") private val publicKeyUrl: String) {

    fun decode(stringJwt: String): Optional<SimpleJwt> {
        return try {
            val jwtDecoder = NimbusJwtDecoder.withPublicKey(getPublicKey()).build()
            val jwt: Jwt = jwtDecoder.decode(stringJwt)
            val username: String = jwt.subject
            val roles: List<Role> = jwt.getClaimAsStringList("roles")
                .map { if (it.isBlank()) Role.CLIENT else Role.valueOf(it) }
            Optional.of<SimpleJwt>(SimpleJwt(username, roles))
        } catch (e: JwtException) {
            Optional.empty<SimpleJwt>()
        }
    }

    private fun getPublicKey(): RSAPublicKey {
        val request = HttpRequest.newBuilder().GET().uri(URI(publicKeyUrl)).build()
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw RuntimeException("Can not fetch public key")
        }

        return response.body().toRsaPublicKey()
    }

    private fun String.toRsaPublicKey(): RSAPublicKey {
        val publicKeyBytes = Base64.decodeBase64(this.trimStringKey())
        val spec = X509EncodedKeySpec(publicKeyBytes)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(spec) as RSAPublicKey
    }

    private fun String.trimStringKey(): String = this
        .replace("-----BEGIN PUBLIC KEY-----", "")
        .replace("-----END PUBLIC KEY-----", "")
        .replace("\n", "")

}

data class SimpleJwt(val username: String, val roles: List<Role>)
