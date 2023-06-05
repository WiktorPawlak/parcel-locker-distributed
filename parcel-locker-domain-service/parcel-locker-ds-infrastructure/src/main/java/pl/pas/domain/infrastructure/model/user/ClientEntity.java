package pl.pas.domain.infrastructure.model.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.domain.infrastructure.model.EntityModel;

@Entity
@Getter
@Setter
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ClientEntity extends EntityModel {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String telNumber;
    private boolean active;

    public ClientEntity(UUID id, Long version, String firstName, String lastName, String telNumber, boolean active) {
        super(id, version);
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
    }
}
