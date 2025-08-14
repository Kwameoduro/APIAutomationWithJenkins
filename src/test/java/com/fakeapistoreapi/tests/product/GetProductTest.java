package com.fakeapistoreapi.tests.product;



import com.fakeapistoreapi.base.BaseTest;
import com.fakeapistoreapi.testdata.ProductTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("FakeStore API")
@Feature("Products")
public class GetProductTest extends BaseTest {

    @Test
    @Story("Retrieve a specific product")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Get product by valid ID")
    @Description("Verify that retrieving a product by a valid ID returns status code 200 and correct product details.")
    public void getProductByValidId() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, ProductTestData.VALID_PRODUCT_ID);

        response.then()
                .spec(responseSpec200)
                .body("id", equalTo(ProductTestData.VALID_PRODUCT_ID))
                .body("title", notNullValue());

        attachResponseToAllure(response);
    }

    @Test
    @Story("Retrieve a specific product")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Get product by invalid ID")
    @Description("Verify that retrieving a product by an invalid ID returns status code 404.")
    public void getProductByInvalidId() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, ProductTestData.INVALID_PRODUCT_ID);

        response.then()
                .spec(responseSpec404);

        attachResponseToAllure(response);
    }

    @Test
    @Story("Retrieve all products")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Get all products")
    @Description("Verify that retrieving all products returns status code 200 and a non-empty list.")
    public void getAllProducts() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .get(ProductTestData.GET_ALL_PRODUCTS_ENDPOINT);

        response.then()
                .spec(responseSpec200)
                .body("size()", greaterThan(0));

        attachResponseToAllure(response);
    }

    @Test
    @Story("Retrieve products with limit")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Get products with a limit parameter")
    @Description("Verify that retrieving products with a limit parameter returns only the specified number of products.")
    public void getProductsWithLimit() {
        Response response = given()
                .spec(requestSpec)
                .queryParam("limit", ProductTestData.PRODUCT_LIMIT)
                .when()
                .get(ProductTestData.GET_ALL_PRODUCTS_ENDPOINT);

        response.then()
                .spec(responseSpec200)
                .body("size()", equalTo(ProductTestData.PRODUCT_LIMIT));

        attachResponseToAllure(response);
    }
}
