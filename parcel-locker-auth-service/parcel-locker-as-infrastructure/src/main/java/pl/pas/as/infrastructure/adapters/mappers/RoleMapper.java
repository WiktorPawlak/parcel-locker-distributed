package pl.pas.as.infrastructure.adapters.mappers;

import pl.pas.as.core.model.user.Role;
import pl.pas.as.infrastructure.model.user.RoleEntity;

public class RoleMapper {

    public static Role mapToDomain(RoleEntity roleEntity) {
        return Role.valueOf(roleEntity.name());
    }

    public static RoleEntity mapToEntity(Role role) {
        return RoleEntity.valueOf(role.name());
    }

}
