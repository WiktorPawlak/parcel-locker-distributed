package pl.pas.domain.infrastructure.repositories.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.domain.core.applicationmodel.model.user.Client;
import pl.pas.domain.infrastructure.adapters.mappers.ClientMapper;
import pl.pas.domain.infrastructure.model.user.ClientEntity;
import pl.pas.domain.infrastructure.repositories.config.TestsConfig;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryHibernateTest extends TestsConfig {
    private ClientEntity c1;
    private ClientEntity c2;
    private ClientEntity c3;
    private ClientEntity c4;
    private ClientEntity c5;

    @BeforeEach
    void setup() {
        c1 = ClientMapper.mapToEntity(new Client("Maciej", "Nowak", "123452137"));
        c2 = ClientMapper.mapToEntity(new Client("Tadeusz", "Byk", "123456"));
        c3 = ClientMapper.mapToEntity(new Client("Krzysztof", "Ryk", "1234567"));
        c4 = ClientMapper.mapToEntity(new Client("Mariusz", "Kwik", "12345678"));
        c5 = ClientMapper.mapToEntity(new Client("Jakub", "Kowalski", "123456789"));
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_ReturnClientWithAppropriateTelNumber_WhenFindByTelNumberCalled() {
        clientRepository.add(c1);

        assertNotNull(clientRepository.findByTelNumber(c1.getTelNumber()));
    }

    @Test
    void Should_ReturnClientsMatchingTelNumber_WhenFindByTelNumberCalled() {
        clientRepository.add(c2);
        clientRepository.add(c3);
        clientRepository.add(c4);


        assertNotNull(clientRepository.findByTelNumberPart(c2.getTelNumber()));
        assertEquals(3, clientRepository.findByTelNumberPart(c2.getTelNumber()).size());
    }

    @Test
    void Should_ArchiveClient_WhenRepositoryArchiveMethodCalled() {
        clientRepository.add(c2);
        clientRepository.archive(c2.getId());

        assertFalse(clientRepository.get(c2.getId()).isActive());
    }

    @Test
    void Should_AddClient_WhenAddMethodCalled() {
        clientRepository.add(c3);

        assertNotNull(clientRepository.get(c3.getId()));
    }

    @Test
    void Should_ReturnAllClients_WhenFindAllCalled() {
        clientRepository.add(c4);
        clientRepository.add(c5);

        assertTrue(clientRepository.findAll().size() > 1);
    }

}
