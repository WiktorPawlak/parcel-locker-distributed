package pl.pas.infrastructure.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
public abstract class EntityModel implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    protected UUID id;

    @Version
    protected Long version;

    public EntityModel(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
