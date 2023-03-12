package pl.pas.infrastructure.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.pas.core.applicationmodel.exceptions.ClientException;
import pl.pas.infrastructure.model.EntityModel;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClientEnt extends EntityModel {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String telNumber;
    private boolean active;

    public ClientEnt(UUID id, String firstName, String lastName, String telNumber, boolean active) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
    }

    protected ClientEnt(String firstName, String lastName, String telNumber) {
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

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " phone: " + telNumber + (active ? " Actual" : " Archived");
    }
}
