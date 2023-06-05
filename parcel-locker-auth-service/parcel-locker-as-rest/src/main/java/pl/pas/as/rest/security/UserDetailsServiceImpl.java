package pl.pas.as.rest.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.ports.incoming.UserService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = userService.getUserByTelNumber(username).orElseThrow(() ->
            new UsernameNotFoundException(username));

        return User
            .withUsername(client.getTelNumber())
            .disabled(!client.isActive())
            .password(client.getPassword())
            .authorities(client.getRole())
            .build();
    }

}
