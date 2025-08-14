package com.fakeapistoreapi.tests.cart;



import com.fakestoreapi.models.Cart;
import com.fakeapistoreapi.testdata.CartTestData;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakestoreapi.specs.ResponseSpecs;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.fakeapistoreapi.utils.TestDataUtil;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;



import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Carts")
@Feature("Create Cart")
public class CreateCartTest {

    private static final String CART_SCHEMA_PATH = "schemas/CartSchema.json";

    @Test
    @Story("Create a valid cart")
    @Description("Verify that a new valid cart can be created successfully")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create a valid cart")
    void createValidCart() {
        Cart cart = CartTestData.validCart();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(cart)
                .when()
                .post("/carts")
                .then()
                .spec(ResponseSpecs.success201())
                .extract()
                .response();

        assertThat(response.jsonPath().getInt("userId")).isEqualTo(cart.getUserId());
        assertThat(response.jsonPath().getList("products")).isNotEmpty();
    }

    @Test
    @Story("Create a cart with missing optional fields")
    @Description("Verify that a cart can be created even if optional fields are missing")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Create cart with missing optional fields")
    void createCartWithMissingOptionalFields() {
        Cart cart = CartTestData.cartWithMissingFields();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(cart)
                .when()
                .post("/carts")
                .then()
                .spec(ResponseSpecs.success201())
                .extract()
                .response();

        assertThat(response.jsonPath().getInt("userId")).isEqualTo(cart.getUserId());
    }

    @Test
    @Story("Create an invalid cart")
    @Description("Verify that creating an invalid cart fails")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create an invalid cart")
    void createInvalidCart() {
        Cart cart = CartTestData.invalidCart();

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(cart)
                .when()
                .post("/carts")
                .then()
                .spec(ResponseSpecs.badRequest())
                .extract()
                .response();

        assertThat(response.statusCode()).isEqualTo(400);
    }

    @Test
    @Story("Create a cart with pre-existing products")
    @Description("Verify that creating a cart with existing products succeeds and returns correct totals")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Create cart with pre-existing products")
    void createCartWithExistingProducts() {
        // Ensure a valid user exists
        int userId = TestDataUtil.ensureUserExists();

        Cart cart = CartTestData.cartWithExistingProducts(userId);

        Response response = given()
                .spec(RequestSpecs.defaultSpec())
                .body(cart)
                .when()
                .post("/carts")
                .then()
                .spec(ResponseSpecs.success201())
                .extract()
                .response();

        assertThat(response.jsonPath().getInt("userId")).isEqualTo(cart.getUserId());
        assertThat(response.jsonPath().getList("products")).isNotEmpty();
        assertThat(response.jsonPath().getDouble("total")).isGreaterThan(0);
    }
}
