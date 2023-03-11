package pl.pas.infrastructure.model.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("CLIENT")
@EqualsAndHashCode(callSuper = true)
public class ClientEnt extends UserEnt {

    @Builder
    public ClientEnt(final String firstName, final String lastName, final String telNumber) {
        super(firstName, lastName, telNumber);
    }

    public ClientEnt(String firstName, String lastName, String telNumber, boolean active) {
        super()
    }
}
