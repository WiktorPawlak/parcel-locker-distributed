package pl.pas.domain.infrastructure.repositories.hibernate;

import static pl.pas.domain.infrastructure.configuration.PersistenceUtil.UNIT_NAME;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityManagerUtil {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(UNIT_NAME, configOverrides());

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    private static Map<String, Object> configOverrides() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        for (String envName : env.keySet()) {
            if (envName.contains("db.port")) {
                configOverrides.put(
                    "jakarta.persistence.jdbc.url", "jdbc:postgresql://" + env.get(envName) + "/database"
                );
            }
        }
        return configOverrides;
    }
}
