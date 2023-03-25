package pl.pas.rest.dupa;

class ScratchTest {

//    private static final String baseUri = "/api/deliveries";
//
//    String basePath = "http://localhost:8080/rest-1.0-SNAPSHOT";
//
//    RequestSpecification requestSpecification = new RequestSpecBuilder()
//        .setBaseUri(basePath)
//        .build();
//
//    Client receiver = new Client("Tony", "Stark", "1234567890");
//    Client shipper = new Client("Steven", "Rogers", "9987654321");
//    Locker locker = new Locker("PLO1", "Piotrkow", 5);
//    String deliveryId;
//    String deliveryId2;
//    String deliveryId3;
//
//    @org.junit.jupiter.api.Test
//    void getTest2() {
//        setup();//todo przy zmianie zawartości testu nie usuwać tej linijki
//        String accessCode = "12345";
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .when()
//            .log().all()
//            .put(
//                baseUri
//                    + "/"
//                    + deliveryId3
//                    + "/put-in?lockerId="
//                    + locker.getIdentityNumber()
//                    + "&accessCode="
//                    + accessCode)
//            .then()
//            .log().all()
//            .statusCode(200);
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .when()
//            .put(
//                baseUri
//                    + "/"
//                    + deliveryId3
//                    + "/take-out?telNumber="
//                    + receiver.getTelNumber()
//                    + "&accessCode="
//                    + accessCode)
//            .then()
//            .statusCode(200);
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .when()
//            .get(baseUri + "/received?telNumber=" + receiver.getTelNumber())
//            .then()
//            .statusCode(200)
//            .body("isEmpty()", is(false));
//    }
//
//    public void setup() {
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .body(LockerDto.builder()
//                .identityNumber(locker.getIdentityNumber())
//                .address(locker.getAddress())
//                .numberOfBoxes(locker.countEmpty())
//                .build())
//            .when()
//            .post("/api/lockers");
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .body(ClientDto.builder()
//                .firstName(shipper.getFirstName())
//                .lastName(shipper.getLastName())
//                .telNumber(shipper.getTelNumber())
//                .build())
//            .when()
//            .post("/api/clients");
//
//        given(requestSpecification)
//            .contentType(ContentType.JSON)
//            .body(ClientDto.builder()
//                .firstName(receiver.getFirstName())
//                .lastName(receiver.getLastName())
//                .telNumber(receiver.getTelNumber())
//                .build())
//            .when()
//            .post("/api/clients");
//
//        DeliveryListDto deliveryListDto =
//            DeliveryListDto.builder()
//                .lockerId(locker.getIdentityNumber())
//                .pack(ListDto.builder().basePrice(BigDecimal.TEN).isPriority(false).build())
//                .receiverTel(receiver.getTelNumber())
//                .shipperTel(shipper.getTelNumber())
//                .build();
//
//        deliveryId =
//            given(requestSpecification)
//                .contentType(ContentType.JSON)
//                .body(deliveryListDto)
//                .when()
//                .post(baseUri + "/list")
//                .then()
//                .extract()
//                .path("id");
//
//        deliveryId2 =
//            given(requestSpecification)
//                .contentType(ContentType.JSON)
//                .body(deliveryListDto)
//                .when()
//                .post(baseUri + "/list")
//                .then()
//                .extract()
//                .path("id");
//
//        deliveryId3 =
//            given(requestSpecification)
//                .contentType(ContentType.JSON)
//                .body(deliveryListDto)
//                .when()
//                .post(baseUri + "/list")
//                .then()
//                .extract()
//                .path("id");
//    }
}
