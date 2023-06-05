package pl.pas.`as`.rest.security

import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
open class WebSecurityConfig(private val userDetailsServiceImpl: UserDetailsServiceImpl,
                             private val jwtProvider: JwtProvider) {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.cors().and()
            .csrf().disable()
            .addFilterBefore(
                authenticationFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(BearerTokenAuthenticationEntryPoint())
            .accessDeniedHandler(BearerTokenAccessDeniedHandler())
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/actuator/**").permitAll()
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
        return http.build()
    }

    @Bean
    open fun authenticationManager(
        http: HttpSecurity, bCryptPasswordEncoder: BCryptPasswordEncoder): AuthenticationManager {
        return http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .userDetailsService(userDetailsServiceImpl)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build()
    }

    private fun authenticationFilter(): AuthenticationFilter {
        return AuthenticationFilter(jwtProvider)
    }

    @Bean
    open fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}
