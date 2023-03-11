package pl.pas.infrastructure.model.user;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdministratorEnt extends UserEnt {

    @Builder
    public AdministratorEnt(final String firstName, final String lastName, final String telNumber) {
        super(firstName, lastName, telNumber);
    }
}
