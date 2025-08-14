package com.fakeapistoreapi.tests.cart;

import com.fakeapistoreapi.base.BaseTest;
import com.fakestoreapi.specs.ResponseSpecs;
import com.fakeapistoreapi.testdata.CartTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("Cart Management")
@Feature("Delete Cart")
public class DeleteCartTest extends BaseTest {

    @Test
    @Story("Delete an existing cart")
    @Description("Verify that an existing cart can be successfully deleted")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Delete Cart - Existing Cart")
    public void deleteExistingCart() {
        int cartId = CartTestData.existingCartId(); // data comes from testdata

        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/carts/" + cartId)
                .then()
                .spec(ResponseSpecs.success200())
                .extract().response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete non-existing cart")
    @Description("Verify that deleting a non-existing cart still returns 200 OK in FakeStoreAPI")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete Cart - Non-existing Cart")
    public void deleteNonExistingCart() {
        int cartId = CartTestData.nonExistingCartId();

        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/carts/" + cartId)
                .then()
                .spec(ResponseSpecs.success200()) // FakeStoreAPI still returns 200
                .extract().response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete cart with invalid ID format")
    @Description("Verify that deleting a cart with invalid ID format returns 404 Not Found")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Delete Cart - Invalid ID Format")
    public void deleteCartWithInvalidId() {
        String invalidId = CartTestData.invalidCartId();

        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/carts/" + invalidId)
                .then()
                .spec(ResponseSpecs.notFound404())
                .extract().response();

        attachResponseToAllure(response);
    }

}
