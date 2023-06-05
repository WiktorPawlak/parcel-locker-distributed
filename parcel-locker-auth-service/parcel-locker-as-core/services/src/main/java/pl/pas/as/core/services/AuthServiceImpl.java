package pl.pas.as.core.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;
import pl.pas.as.ports.incoming.AuthService;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    @Override
    public Authentication login(String telNumber, String password) {
        Authentication authentication =
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(telNumber, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return authentication;
    }
}
