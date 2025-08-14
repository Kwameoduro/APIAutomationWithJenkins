package com.fakeapistoreapi.tests.cart;



import com.fakeapistoreapi.testdata.CartTestData;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakestoreapi.specs.ResponseSpecs;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@Epic("FakeStore API Automation")
@Feature("Cart Resource")
public class GetCartTest {

    @Test
    @Story("Retrieve single cart")
    @Description("Verify retrieving a valid cart by ID returns 200 and correct cart details")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Get Cart by Valid ID")
    void getCartByValidId() {
        given()
                .spec(RequestSpecs.basic())
                .pathParam("id", CartTestData.VALID_CART_ID)
                .when()
                .get("/carts/{id}")
                .then()
                .spec(ResponseSpecs.success200());
    }

    @Test
    @Story("Retrieve all carts")
    @Description("Verify retrieving all carts returns 200 and a non-empty list")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get All Carts")
    void getAllCarts() {
        given()
                .spec(RequestSpecs.basic())
                .when()
                .get("/carts")
                .then()
                .spec(ResponseSpecs.success200());
    }

    @Test
    @Story("Retrieve carts by user ID")
    @Description("Verify retrieving carts for a valid user ID returns 200")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get Carts by User ID")
    void getCartsByUserId() {
        given()
                .spec(RequestSpecs.basic())
                .queryParam("userId", CartTestData.VALID_USER_ID)
                .when()
                .get("/carts")
                .then()
                .spec(ResponseSpecs.success200());
    }
}
