package pl.pas.core.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pl.pas.core.applicationmodel.exceptions.ClientException;
import pl.pas.core.applicationmodel.model.user.Client;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @ParameterizedTest(name = "when firstName = {0}, lastName = {1}, telNumber = {2} should throw exception")
    @CsvSource({
        "'',a,123456",
        "a,'',123",
        "a,a,''"
    })
    void Should_ThrowException_WhenIncorrectData(String firstName, String lastName, String telNumber) {
        assertThrows(ClientException.class, () -> new Client(firstName, lastName, telNumber));
    }
}
