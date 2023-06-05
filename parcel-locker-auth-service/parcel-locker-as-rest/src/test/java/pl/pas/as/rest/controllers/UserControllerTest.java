package pl.pas.as.rest.controllers;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.pas.as.core.model.user.Client;
import pl.pas.as.core.model.user.Role;
import pl.pas.as.rest.config.DbContainerInitializer;
import pl.pas.as.rest.controllers.dto.ClientDto;
import pl.pas.as.rest.security.JwtProvider;

class UserControllerTest extends DbContainerInitializer {

    private static final String basePath = "api/clients";

    @Test
    void Should_CreateClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .password("siema")
            .build();

        RestAssured.given(requestSpecification)
            .contentType(ContentType.JSON)
            .header(AUTHORIZATION, getClientToken())
            .body(client)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Jan"));
    }

    @Test
    void Should_GetClientByPhoneNumber() {
        Client client = Client.builder()
            .firstName("Dariusz")
            .lastName("Szpak")
            .password("siema")
            .telNumber("321321")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .header(AUTHORIZATION, getClientToken())
            .body(client)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Dariusz"));

        RestAssured.given(requestSpecification)
            .header(AUTHORIZATION, getClientToken())
            .when()
            .get(basePath + "/" + client.getTelNumber())
            .then()
            .statusCode(200)
            .body("firstName", equalTo("Dariusz"));
    }

    @Test
    void Should_GetClientsByPhoneNumberPattern() {
        Client client1 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .password("siema")
            .telNumber("111222333")
            .build();
        Client client2 = Client.builder()
            .firstName("Dariusz")
            .lastName("Szpak")
            .password("siema")
            .telNumber("111333222")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .header(AUTHORIZATION, getClientToken())
            .body(client1)
            .when()
            .post(basePath);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .header(AUTHORIZATION, getClientToken())
            .body(client2)
            .when()
            .post(basePath);

        RestAssured.given(requestSpecification).queryParam("telNumber", "111")
            .header(AUTHORIZATION, getClientToken())
            .when().get(basePath)
            .then().statusCode(200).body(
                "[0].telNumber", equalTo("111222333"),
                "[1].telNumber", equalTo("111333222")
            );
    }

    @Test
    void Should_UnregisterClient() {
        Client client = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("333333")
            .password("siema")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .header(AUTHORIZATION, getClientToken())
            .body(client)
            .when()
            .post(basePath);

        RestAssured.given(requestSpecification)
            .with()
            .contentType(ContentType.TEXT)
            .header(AUTHORIZATION, getClientToken())
            .body(client.getTelNumber())
            .when()
            .put(basePath)
            .then()
            .statusCode(200)
            .body("firstName", equalTo("Jan"));
    }

    @Autowired
    private JwtProvider jwtProvider;

    private String getClientToken() {
        return "Bearer " + jwtProvider.encode("123456", List.of(Role.CLIENT));
    }

}
