package com.fakeapistoreapi.tests.user;

import com.fakeapistoreapi.utils.TestDataUtil;
import com.fakestoreapi.models.User;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakeapistoreapi.testdata.UserTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Users")
@Feature("Create User")
public class CreateUserTest {

    @Test
    @Story("Create a valid user")
    @Description("POST /users with a valid payload should return 200/201 and include an id")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create user → returns 2xx and id")
    void createValidUser_shouldReturn2xxAndId() {
        User user = UserTestData.validUser();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(user)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        // Status can be 200 or 201 depending on the backend
        assertThat(response.statusCode()).isIn(200, 201);

        // Content-Type should be JSON-ish
        assertThat(response.contentType()).contains("application/json");

        // Response should contain a positive id
        Integer id = response.jsonPath().getInt("id");
        assertThat(id).as("created user id").isNotNull().isGreaterThan(0);
    }

    @Test
    @Story("Create two valid users")
    @Description("Creating two users with unique data should return different ids")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create two users → different ids")
    void createTwoValidUsers_shouldReturnDifferentIds() {
        User user1 = UserTestData.validUser();
        User user2 = UserTestData.validUser(); // unique username/email by generator

        Response resp1 = given().spec(RequestSpecs.defaultSpec()).body(user1)
                .when().post("/users").then().extract().response();
        Response resp2 = given().spec(RequestSpecs.defaultSpec()).body(user2
        ).when().post("/users").then().extract().response();

        assertThat(resp1.statusCode()).isIn(200, 201);
        assertThat(resp2.statusCode()).isIn(200, 201);

        Integer id1 = resp1.jsonPath().getInt("id");
        Integer id2 = resp2.jsonPath().getInt("id");

        assertThat(id1).isNotNull().isGreaterThan(0);
        assertThat(id2).isNotNull().isGreaterThan(0);
        assertThat(id1).as("ids should be different for different users").isNotEqualTo(id2);
    }

    @Test
    @Story("Create user response format")
    @Description("Verify the create user response at least has id and is JSON")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Create user → minimal response contract")
    void createUser_minimalContract_shouldHold() {
        User user = UserTestData.validUser();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(user)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        assertThat(response.statusCode()).isIn(200, 201);
        assertThat(response.contentType()).contains("application/json");
        assertThat(response.jsonPath().getMap("$"))
                .as("response body should at least contain 'id'")
                .containsKey("id");
        assertThat(response.jsonPath().getInt("id")).isGreaterThan(0);
    }

    @Test
    @Story("Ensure a user exists dynamically")
    @Description("Use TestDataUtil to create a user and return its id for downstream tests")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Ensure dynamic user exists")
    void ensureDynamicUserExists() {
        User user = UserTestData.validUser();
        int userId = TestDataUtil.ensureUserExists(user);
        assertThat(userId).isGreaterThan(0);
    }
}
