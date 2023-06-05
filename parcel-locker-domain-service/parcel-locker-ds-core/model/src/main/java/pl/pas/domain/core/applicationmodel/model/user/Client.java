package pl.pas.domain.core.applicationmodel.model.user;


import java.util.UUID;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.domain.core.applicationmodel.exceptions.ClientException;


@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Client {

    private UUID id;
    private Long version;
    private String firstName;
    private String lastName;
    private String telNumber;

    @Setter
    private boolean active;

    @Builder
    public Client(String firstName, String lastName, String telNumber) {
        this(UUID.randomUUID(), 0L, firstName, lastName, telNumber, true);
    }

    public Client(UUID id, Long version, String firstName, String lastName, String telNumber, boolean active) {
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.id = id;
        this.version = version;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
    }

    private void validateName(String name) {
        if (name.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }
}
