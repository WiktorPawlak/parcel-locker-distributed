package pl.pas.rest.dupa;

import static org.hamcrest.Matchers.is;
import static io.restassured.RestAssured.given;

import java.math.BigDecimal;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import pl.pas.core.applicationmodel.model.locker.Locker;
import pl.pas.core.applicationmodel.model.user.Client;
import pl.pas.rest.controllers.dto.ClientDto;
import pl.pas.rest.controllers.dto.DeliveryListDto;
import pl.pas.rest.controllers.dto.ListDto;
import pl.pas.rest.controllers.dto.LockerDto;

class Test {

    private static final String basePath = "/api/deliveries";

    String baseUri = "http://localhost:8080/rest-1.0-SNAPSHOT";

    RequestSpecification requestSpecification = new RequestSpecBuilder()
        .setBaseUri(baseUri)
        .build();

    Client receiver = new Client("Tony", "Stark", "1234567890");
    Client shipper = new Client("Steven", "Rogers", "9987654321");
    Locker locker = new Locker("PLO1", "Piotrkow", 5);
    String deliveryId;
    String deliveryId2;
    String deliveryId3;

    @org.junit.jupiter.api.Test
    void getTest2() {
        setup();
        String accessCode = "12345";

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .log().all()
            .put(
                baseUri
                    + "/"
                    + deliveryId3
                    + "/put-in?lockerId="
                    + locker.getIdentityNumber()
                    + "&accessCode="
                    + accessCode)
            .then()
            .log().all()
            .statusCode(200);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .put(
                baseUri
                    + "/"
                    + deliveryId3
                    + "/take-out?telNumber="
                    + receiver.getTelNumber()
                    + "&accessCode="
                    + accessCode)
            .then()
            .statusCode(200);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .get(baseUri + "/received?telNumber=" + receiver.getTelNumber())
            .then()
            .statusCode(200)
            .body("isEmpty()", is(false));

        var lol3 = 2 + 2;
    }

    public void setup() {

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(LockerDto.builder()
                .identityNumber(locker.getIdentityNumber())
                .address(locker.getAddress())
                .numberOfBoxes(locker.countEmpty())
                .build())
            .when()
            .post("/api/lockers");

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(ClientDto.builder()
                .firstName(shipper.getFirstName())
                .lastName(shipper.getLastName())
                .telNumber(shipper.getTelNumber())
                .build())
            .when()
            .post("/api/clients");

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(ClientDto.builder()
                .firstName(receiver.getFirstName())
                .lastName(receiver.getLastName())
                .telNumber(receiver.getTelNumber())
                .build())
            .when()
            .post("/api/clients");

        DeliveryListDto deliveryListDto =
            DeliveryListDto.builder()
                .lockerId(locker.getIdentityNumber())
                .pack(ListDto.builder().basePrice(BigDecimal.TEN).isPriority(false).build())
                .receiverTel(receiver.getTelNumber())
                .shipperTel(shipper.getTelNumber())
                .build();

        deliveryId =
            given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(deliveryListDto)
                .when()
                .post(basePath + "/list")
                .then()
                .extract()
                .path("id");

        deliveryId2 =
            given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(deliveryListDto)
                .when()
                .post(basePath + "/list")
                .then()
                .extract()
                .path("id");

        deliveryId3 =
            given(requestSpecification)
                .contentType(ContentType.JSON)
                .body(deliveryListDto)
                .when()
                .post(basePath + "/list")
                .then()
                .extract()
                .path("id");
    }
}
