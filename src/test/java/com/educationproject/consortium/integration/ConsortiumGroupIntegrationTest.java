package com.educationProject.consortium.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ConsortiumGroupIntegrationTest {


    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
        createUser();
        token = loginAndGetToken();
    }

    private void createUser() {
        String userJson = """
                    {
                        "username": "integrationUser",
                        "password": "123456"
                    }
                """;

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/user/create")
                .then()
                .statusCode(200);
    }

    private String loginAndGetToken() {
        String loginJson = """
                    {
                        "username": "integrationUser",
                        "password": "123456"
                    }
                """;

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().response();

        return response.jsonPath().getString("token");
    }

    @Test
    void shouldCreateConsortiumGroup() {
        String requestBody = """
                    {
                        "groupName": "Test Integration",
                        "totalValue": 10000,
                        "quotaQuantity": 10,
                        "monthQuantity": 12
                    }
                """;

        RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/consortiumGroup/create")
                .then()
                .statusCode(201)
                .body(containsString("Consortium created with success"));
    }

    @Test
    void shouldListByID() {
        // Primeiro cria o grupo
        String requestBody = """
                    {
                        "groupName": "Test Integration",
                        "totalValue": 10000,
                        "quotaQuantity": 10,
                        "monthQuantity": 12
                    }
                """;

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/consortiumGroup/create")
                .then()
                .statusCode(201)
                .extract().response();

        String locationHeader = response.getHeader("Location");
        long id = Long.parseLong(locationHeader.replaceAll("\\D+", ""));

        RestAssured
                .given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get("/consortiumGroup/" + id)
                .then()
                .statusCode(200)
                .body("groupName", equalTo("Test Integration"));
    }

}
