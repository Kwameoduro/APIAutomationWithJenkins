package com.fakeapistoreapi.tests.user;



import com.fakestoreapi.models.User;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakestoreapi.specs.ResponseSpecs;
import com.fakeapistoreapi.testdata.UserTestData;
import com.fakeapistoreapi.utils.TestDataUtil;


import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("User API Tests")
@Feature("Get User")
public class GetUserTest {

//    @Test
//    @Story("Fetch an existing user by ID")
//    @Description("This test verifies that an existing user can be retrieved successfully using the GET /users/{id} endpoint.")
//    @Severity(SeverityLevel.CRITICAL)
//    @DisplayName("Get Existing User by ID")
//    void getExistingUserById() {
//        // Arrange: Ensure user exists via test data
//        User user = UserTestData.validUser();
//        int userId = TestDataUtil.ensureUserExists(user);
//
//        // Act & Assert
//        User fetchedUser = given()
//                .spec(RequestSpecs.defaultSpec())
//                .when()
//                .get("/users/{id}", userId)
//                .then()
//                .spec(ResponseSpecs.success200())
//                .extract()
//                .as(User.class);
//
//        assertThat(fetchedUser.getId()).isEqualTo(userId);
//        assertThat(fetchedUser.getUsername()).isEqualTo(user.getUsername());
//        assertThat(fetchedUser.getEmail()).isEqualTo(user.getEmail());
//    }

    @Test
    @Story("Fetch a non-existent user by ID")
    @Description("This test verifies that fetching a user with an invalid ID returns 404 Not Found.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get Non-Existent User")
    void getNonExistentUser() {
        int invalidUserId = 99999; // assumed non-existent

        given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .get("/users/{id}", invalidUserId)
                .then()
                .spec(ResponseSpecs.notFound());
    }

    @Test
    @Story("Fetch all users")
    @Description("This test verifies that all users can be retrieved successfully using the GET /users endpoint.")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Get All Users")
    void getAllUsers() {
        User[] users = given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .get("/users")
                .then()
                .spec(ResponseSpecs.success200())
                .extract()
                .as(User[].class);

        assertThat(users).isNotEmpty();
    }

    @Test
    @Story("Fetch user and validate required fields")
    @Description("This test verifies that a user fetched by ID contains all required fields.")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Get User Field Validation")
    void getUserFieldValidation() {
        User user = UserTestData.validUser();
        int userId = TestDataUtil.ensureUserExists(user);

        User fetchedUser = given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .get("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.success200())
                .extract()
                .as(User.class);

        assertThat(fetchedUser.getUsername()).isNotEmpty();
        assertThat(fetchedUser.getEmail()).isNotEmpty();
        assertThat(fetchedUser.getPassword()).isNotEmpty();
        assertThat(fetchedUser.getName().getFirstname()).isNotEmpty();
        assertThat(fetchedUser.getName().getLastname()).isNotEmpty();
    }
}
