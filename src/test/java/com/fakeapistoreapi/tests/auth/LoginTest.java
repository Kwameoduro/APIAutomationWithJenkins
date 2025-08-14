package com.fakeapistoreapi.tests.auth;

import com.fakeapistoreapi.base.BaseTest;
import com.fakestoreapi.models.Auth;
import com.fakeapistoreapi.testdata.AuthTestData;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("FakeStoreAPI Authentication")
@Feature("Login Endpoint")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {


    @Test
    @Order(1)
    @Story("Successful login")
    @Description("Valid login should return 201 and a non-empty token.")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Login: 201 + token for valid credentials")
    void validLoginReturnsToken() {
        Auth payload = AuthTestData.validAuth();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().response();

        String token = response.jsonPath().getString("token");
        assertThat(token, notNullValue());
        assertThat(token.trim(), not(isEmptyString()));
    }

    @Test
    @Order(2)
    @Story("Successful login")
    @Description("Token should not be null or empty for valid credentials.")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Login: token not null/empty")
    void tokenNotNullOrEmpty() {
        Auth payload = AuthTestData.validAuth();

        String token = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("token");

        assertThat(token, notNullValue());
        assertThat(token.trim().length(), greaterThan(0));
    }

    @Test
    @Order(3)
    @Story("Successful login")
    @Description("Content-Type header should include application/json.")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Login: Content-Type contains application/json")
    void contentTypeIsJson() {
        Auth payload = AuthTestData.validAuth();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .header("Content-Type", containsString("application/json"));
    }

    @Test
    @Order(4)
    @Story("Successful login")
    @Description("Response time for valid login should be under 5 seconds.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login: response time < 5s")
    void responseTimeUnder5Seconds() {
        Auth payload = AuthTestData.validAuth();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .time(lessThan(5000L));
    }

    @Test
    @Order(5)
    @Story("Repeated login")
    @Description("Multiple logins with same valid credentials should each return a token.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Login: repeat login returns token")
    void repeatLoginReturnsToken() {
        Auth payload = AuthTestData.validAuth();

        String t1 = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("token");

        String t2 = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(AuthTestData.AUTH_LOGIN_ENDPOINT)
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("token");

        assertThat(t1, not(isEmptyOrNullString()));
        assertThat(t2, not(isEmptyOrNullString()));
    }

    @Test
    @Order(6)
    @Story("Invalid login")
    @Description("Invalid password should return 401 (this will fail with FakeStoreAPI)")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Invalid password returns 401")
    public void invalidPasswordShouldFail() {
        Auth auth = AuthTestData.invalidPassword();

        given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(AuthTestData.AUTH_ENDPOINT)
                .then()
                .statusCode(202); // will fail because FakeStoreAPI returns 200
    }

    @Test
    @Order(7)
    @Story("Invalid login")
    @Description("Invalid username should return 401 (this will fail with FakeStoreAPI)")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Invalid username returns 401")
    public void invalidUsernameShouldFail() {
        Auth auth = AuthTestData.invalidUsername();

        given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post(AuthTestData.AUTH_ENDPOINT)
                .then()
                .statusCode(202);
    }

    @Test
    @Order(8)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Invalid password should return 401 Unauthorized")
    public void testInvalidPassword() {
        Auth auth = new Auth(AuthTestData.VALID_USERNAME, AuthTestData.INVALID_PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test
    @Order(9)
    @Severity(SeverityLevel.CRITICAL)
    @Description("Invalid username should return 401 Unauthorized")
    public void testInvalidUsername() {
        Auth auth = new Auth(AuthTestData.INVALID_USERNAME, AuthTestData.VALID_PASSWORD);

        given()
                .contentType(ContentType.JSON)
                .body(auth)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }
}
