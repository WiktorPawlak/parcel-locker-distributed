package pl.pas.as.ports.incoming;

import org.springframework.security.core.Authentication;

public interface AuthService {

    Authentication login(String telNumber, String password);

}
