package com.fakeapistoreapi.tests.cart;



import com.fakeapistoreapi.testdata.CartTestData;
import com.fakeapistoreapi.utils.TestDataUtil;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Epic("Cart Management")
@Feature("Update Cart")
public class UpdateCartTest {

    @Test
    @Story("Update existing cart with valid details")
    @Description("Verify that an existing cart can be updated with valid product data.")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Update Cart Successfully")
    void updateCartSuccessfully() {
        int cartId = TestDataUtil.ensureCartExists();

        given()
                .contentType(ContentType.JSON)
                .body(CartTestData.validUpdatedCart())
                .when()
                .put("/carts/" + cartId)
                .then()
                .statusCode(200)
                .body("id", equalTo(cartId))
                .body("products.size()", equalTo(CartTestData.validUpdatedCart().getProducts().size()));
    }

    @Test
    @Story("Update non-existent cart")
    @Description("Verify that updating a non-existent cart returns 404 Not Found.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Update Non-Existent Cart")
    void updateNonExistentCart() {
        given()
                .contentType(ContentType.JSON)
                .body(CartTestData.validUpdatedCart())
                .when()
                .put("/carts/999999")
                .then()
                .statusCode(404);
    }

    @Test
    @Story("Update cart with invalid data")
    @Description("Verify that updating a cart with invalid product data returns 400 Bad Request.")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Update Cart with Invalid Data")
    void updateCartWithInvalidData() {
        int cartId = TestDataUtil.ensureCartExists();

        given()
                .contentType(ContentType.JSON)
                .body(CartTestData.invalidCartData())
                .when()
                .put("/carts/" + cartId)
                .then()
                .statusCode(400);
    }

    @Test
    @Story("Update cart with empty product list")
    @Description("Verify that updating a cart with an empty product list returns 400 Bad Request.")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Update Cart with Empty Product List")
    void updateCartWithEmptyProductList() {
        int cartId = TestDataUtil.ensureCartExists();

        given()
                .contentType(ContentType.JSON)
                .body(CartTestData.emptyCartData())
                .when()
                .put("/carts/" + cartId)
                .then()
                .statusCode(400);
    }
}
