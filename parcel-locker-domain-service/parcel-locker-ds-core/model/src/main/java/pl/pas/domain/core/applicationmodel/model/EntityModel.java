package pl.pas.domain.core.applicationmodel.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class EntityModel implements Serializable {

    protected UUID id;

    public UUID getId() {
        return id;
    }
}
