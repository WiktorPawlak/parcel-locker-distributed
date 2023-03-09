package pl.pas.parcellocker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.NoResultException;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.security.PermissionValidator;

class UserServiceTest extends TestsConfig {

    private final PermissionValidator permissionValidator = new PermissionValidator(clientRepository);
    private final UserService userService = new UserServiceImpl(clientRepository, permissionValidator);

    private final String TEST_NAME = "Bartosh";
    private final String TEST_SURNAME = "Byniowski";
    private final String TEST_TEL_NUMBER = "123456789";
    private final String TEST_WRONG_TEL_NUMBER = "987654321";

    private User admin;

    @BeforeEach
    void init() {
        admin = new Administrator("Administrator", "surname", "666789123");
        clientRepository.add(admin);
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_RegisterClient() {
        userService.registerClient(admin.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(TEST_TEL_NUMBER, userService.getUser(TEST_TEL_NUMBER).getTelNumber());
    }

    @Test
    void Should_UnregisterClient() {
        userService.registerClient(admin.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertTrue(userService.getUser(TEST_TEL_NUMBER).isActive());

        userService.unregisterClient(admin.getId(), userService.getUser(TEST_TEL_NUMBER));
        assertFalse(userService.getUser(TEST_TEL_NUMBER).isActive());
    }

    @Test
    void Should_GetClient() {
        userService.registerClient(admin.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(userService.getUser(TEST_TEL_NUMBER).getFirstName(), TEST_NAME);
        assertThrows(NoResultException.class, () -> userService.getUser(TEST_WRONG_TEL_NUMBER));
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        userService.registerClient(admin.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertThrows(ClientManagerException.class, () -> userService.getUser(""));
        assertThrows(ClientManagerException.class, () -> userService.unregisterClient(admin.getId(), null));
    }

}
