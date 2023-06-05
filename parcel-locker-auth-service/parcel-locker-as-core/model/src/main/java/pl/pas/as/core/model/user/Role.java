package pl.pas.as.core.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMINISTRATOR, CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
