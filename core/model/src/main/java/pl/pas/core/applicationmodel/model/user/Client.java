package pl.pas.core.applicationmodel.model.user;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.exceptions.ClientException;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Client {

    private UUID id;
    private String firstName;
    private String lastName;
    private String telNumber;
    private boolean active;

    @Builder
    public Client(String firstName, String lastName, String telNumber) {
        validateName(firstName);
        validateName(lastName);
        validateTelNumber(telNumber);

        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;

        active = true;
    }

    private void validateName(String name) {
        if (name.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    private void validateTelNumber(String telNumber) {
        if (telNumber.isEmpty())
            throw new ClientException("Empty lastName variable!");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public boolean isActive() {
        return active;
    }

    public UUID getId() {
        return id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " phone: " + telNumber + (active ? " Actual" : " Archived");
    }
}
