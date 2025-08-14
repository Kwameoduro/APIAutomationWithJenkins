package com.fakeapistoreapi.tests.user;



import com.fakestoreapi.models.User;
import com.fakeapistoreapi.testdata.UserTestData;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakestoreapi.specs.ResponseSpecs;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fakeapistoreapi.utils.TestDataUtil;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Users")
@Feature("Update User")
public class UpdateUserTest {

    @Test
    @Story("Update an existing user with valid data")
    @Description("Verify that an existing user can be updated successfully")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Update user with valid data")
    void updateUserWithValidData() {
        User existingUser = UserTestData.validUser();
        int userId = TestDataUtil.ensureUserExists(existingUser);

        User updatedUser = UserTestData.updatedUser();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(updatedUser)
                .when()
                .put("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.success200())
                .extract()
                .response();

        assertThat(response.jsonPath().getString("username")).isEqualTo(updatedUser.getUsername());
        assertThat(response.jsonPath().getString("email")).isEqualTo(updatedUser.getEmail());
    }

    @Test
    @Story("Update an existing user with invalid data")
    @Description("Verify that updating a user with invalid data returns a bad request")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Update user with invalid data")
    void updateUserWithInvalidData() {
        User existingUser = UserTestData.validUser();
        int userId = TestDataUtil.ensureUserExists(existingUser);

        User invalidUser = UserTestData.invalidUpdateUser();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(invalidUser)
                .when()
                .put("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.badRequest())
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @Story("Update non-existent user")
    @Description("Verify that updating a non-existent user returns a 404 Not Found")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Update non-existent user")
    void updateNonExistentUser() {
        User updatedUser = UserTestData.updatedUser();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(updatedUser)
                .when()
                .put("/users/{id}", 999999)
                .then()
                .spec(ResponseSpecs.notFound())
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(404);
    }
}
