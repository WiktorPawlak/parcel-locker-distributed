package pl.pas.as.infrastructure.model.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.as.infrastructure.model.EntityModel;

@Entity
@Getter
@Setter
@Builder
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
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEntity role;

    public ClientEntity(UUID id, Long version, String firstName, String lastName, String telNumber, String password, RoleEntity role, boolean active) {
        super(id, version);
        this.firstName = firstName;
        this.lastName = lastName;
        this.telNumber = telNumber;
        this.active = active;
        this.password = password;
        this.role = role;
    }
}
