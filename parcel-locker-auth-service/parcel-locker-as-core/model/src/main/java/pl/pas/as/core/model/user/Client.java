package pl.pas.as.core.model.user;


import static pl.pas.as.core.model.user.Role.CLIENT;

import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.as.core.model.exceptions.ClientException;


@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Client {

    private UUID id;
    private Long version;
    private String firstName;
    private String lastName;
    private String telNumber;
    private String password;
    private Role role;

    @Setter
    private boolean active;

    @Builder
    public Client(String firstName, String lastName, String telNumber, String password) {
        this(UUID.randomUUID(), 0L, firstName, lastName, telNumber, password, CLIENT, true);
    }

    public Client(UUID id, Long version, String firstName, String lastName, String telNumber, String password, Role role, boolean active) {
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
        this.role = role;
        this.password = password;
    }

    private void validateName(String name) {
        if (name.isEmpty()) {
            throw new ClientException("Empty lastName variable!");
        }
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty()) {
            throw new ClientException("Empty lastName variable!");
        }
    }
}
