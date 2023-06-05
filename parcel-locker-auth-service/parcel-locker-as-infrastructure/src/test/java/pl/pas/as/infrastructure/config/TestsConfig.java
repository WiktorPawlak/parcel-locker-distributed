package pl.pas.as.infrastructure.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import pl.pas.as.infrastructure.repositories.hibernate.UserRepositoryHibernate;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig {

    protected final UserRepositoryHibernate clientRepository = new UserRepositoryHibernate();

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

    @AfterAll
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

}
