package pl.pas.as.core.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pl.pas.as.core.model.exceptions.ClientException;
import pl.pas.as.core.model.user.Client;

class UserTest {

    @ParameterizedTest(name = "when firstName = {0}, lastName = {1}, telNumber = {2} should throw exception")
    @CsvSource({
        "'',a,123456,super",
        "a,'',123,super",
        "a,a,'',super"
    })
    void Should_ThrowException_WhenIncorrectData(String firstName, String lastName, String telNumber, String password) {
        assertThrows(ClientException.class, () -> new Client(firstName, lastName, telNumber, password));
    }
}
