package pl.pas.as.infrastructure.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.as.infrastructure.config.TestsConfig;
import pl.pas.as.infrastructure.model.user.ClientEntity;
import pl.pas.as.infrastructure.repositories.hibernate.HibernateRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryTest extends TestsConfig {

    private final HibernateRepository<ClientEntity> clientRepository = new HibernateRepository<>(ClientEntity.class);
    private ClientEntity c1;
    private ClientEntity c2;

    @BeforeEach
    void setup() {
        c1 = ClientMapper.mapToEntity(new Client("Tadeusz", "Kaczmarski", "606123654", "super"));
        c2 = ClientMapper.mapToEntity(new Client("Pawel", "Tubiel", "606444654", "super"));
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_AddAndGetClient() {
        clientRepository.add(c1);
        assertEquals(c1, clientRepository.get(c1.getId()));
    }

    @Test
    void Should_RemoveClient() {
        clientRepository.add(c1);
        clientRepository.remove(c1);
        assertThrows(NoResultException.class, () -> clientRepository.get(c1.getId()));
    }

    @Test
    void Should_FindClient_WhenPredicateGivenToFindByMethod() {
        clientRepository.add(c1);
        clientRepository.add(c2);
        assertEquals(c2, clientRepository.findBy(client -> client.getLastName().equals("Tubiel")).get(0));
    }

    @Test
    void Should_ReturnAllClients() {
        clientRepository.add(c1);
        clientRepository.add(c2);
        assertTrue(2 <= clientRepository.findAll().size());
        assertTrue(clientRepository.findAll().containsAll(List.of(c1, c2)));
    }

    @Test
    void Should_TriggerOptimisticLock_WhenUnregisteringClientTwice() {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("parcel-locker-as-unit");
        EntityManager em1 = emf1.createEntityManager();

        EntityManager em2 = emf1.createEntityManager();

        clientRepository.add(c1);

        ClientEntity user1 = em1.find(ClientEntity.class, c1.getId());
        ClientEntity user2 = em2.find(ClientEntity.class, c1.getId());

        em1.getTransaction().begin();
        user1.setActive(false);
        em1.getTransaction().commit();

        RollbackException rollback = new RollbackException();
        try {
            em2.getTransaction().begin();
            user2.setActive(false);
            em2.getTransaction().commit();
        } catch (RollbackException e) {
            rollback = e;
        }
        assertTrue(rollback.getCause() instanceof OptimisticLockException);
    }
}
