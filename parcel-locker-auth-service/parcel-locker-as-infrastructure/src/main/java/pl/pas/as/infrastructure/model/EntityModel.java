package pl.pas.as.infrastructure.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
public abstract class EntityModel implements Serializable {

    @Id
    @Getter
    protected UUID id;

    @Version
    @Getter
    protected Long version;

    protected EntityModel(UUID id, Long version) {
        this.id = id;
        this.version = version;
    }
}
