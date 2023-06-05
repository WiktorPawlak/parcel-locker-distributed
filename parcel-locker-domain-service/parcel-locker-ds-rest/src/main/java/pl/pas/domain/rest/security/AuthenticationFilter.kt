package pl.pas.domain.rest.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.regex.Pattern

@Slf4j
@Component
class AuthenticationFilter(private val jwtDecoder: JwtDecoder) : OncePerRequestFilter() {
    private val tokenPattern = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            val authorizationHeader = request.getHeader(AUTHORIZATION)

            if (authorizationHeader != null && tokenPattern.matcher(authorizationHeader).matches()) {
                val jwt = jwtDecoder.decode(extractJwtToken(authorizationHeader)).orElseThrow()
                SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                    jwt.username,
                    null,
                    jwt.roles
                )
            }
        } catch (e: Exception) {
            logger.error("Could not set user authentication in security context: " + e.message)
        }
        filterChain.doFilter(request, response)
    }

    private fun extractJwtToken(authorizationHeader: String): String = authorizationHeader.split(" ".toRegex()).last()
}
