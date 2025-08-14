package com.fakeapistoreapi.tests.user;


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
@Feature("Delete User")
public class DeleteUserTest {

    @Test
    @Story("Delete an existing user")
    @Description("Verify that an existing user can be deleted successfully")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Delete an existing user")
    void deleteExistingUser() {
        int userId = TestDataUtil.ensureUserExists();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .delete("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.success200())
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(200);
    }

    @Test
    @Story("Delete a non-existing user")
    @Description("Verify that deleting a non-existing user returns 404 Not Found")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete a non-existing user")
    void deleteNonExistingUser() {
        int nonExistingUserId = 999999;

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .delete("/users/{id}", nonExistingUserId)
                .then()
                .spec(ResponseSpecs.notFound())
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @Story("Delete the same user twice")
    @Description("Verify that deleting a user twice returns 404 the second time")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete a user twice")
    void deleteUserTwice() {
        int userId = TestDataUtil.ensureUserExists();

        // First delete
        given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .delete("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.success200());

        // Second delete attempt
        Response responseSecond = given()
                .spec(RequestSpecs.defaultSpec())
                .when()
                .delete("/users/{id}", userId)
                .then()
                .spec(ResponseSpecs.notFound())
                .extract()
                .response();

        assertThat(responseSecond.statusCode()).isEqualTo(404);
    }
}
