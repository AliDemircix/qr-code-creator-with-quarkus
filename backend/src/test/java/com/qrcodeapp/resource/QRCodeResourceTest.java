package com.qrcodeapp.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class QRCodeResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/qrcodes")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

}