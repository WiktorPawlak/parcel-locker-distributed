package pl.pas.domain.rest.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import pl.pas.domain.rest.config.DbContainerInitializer;
import pl.pas.domain.rest.controllers.dto.LockerDto;

class LockerControllerTest extends DbContainerInitializer {

    private static final String basePath = "/api/lockers";

    @Test
    void Should_CreateLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ01")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("identityNumber", equalTo("LDZ01"));
    }

    @Test
    void Should_GetLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ02")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath);

        given(requestSpecification)
            .when()
            .get(basePath + "/{identityNumber}", "LDZ02")
            .then()
            .statusCode(200)
            .body("identityNumber", equalTo("LDZ02"));
    }

    @Test
    void Should_RemoveLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ03")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath);

        given(requestSpecification).
            when()
            .delete(basePath + "/{identityNumber}", "LDZ03")
            .then()
            .statusCode(204);
    }

    @Test
    void ShouldNot_CreateLocker_WhenThereIsAlreadyLockerWithThatNumber() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ04")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath)
            .then()
            .statusCode(409);
    }

    @Test
    void ShouldNot_GetLocker_WhenThereIsNoLockerWithThatNumber() {
        given(requestSpecification)
            .when()
            .get(basePath + "/{identityNumber}", "LDZ05")
            .then()
            .statusCode(404);
    }

    @Test
    void ShouldNot_RemoveLocker_WhenThereIsNoLockerWithThatNumber() {
        given(requestSpecification)
            .when()
            .delete(basePath + "/{identityNumber}", "LDZ06")
            .then()
            .statusCode(404);
    }
}
