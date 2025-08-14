package com.fakeapistoreapi.tests.product;

import com.fakeapistoreapi.base.BaseTest;
import com.fakestoreapi.models.Product;
import com.fakestoreapi.specs.ResponseSpecs;
import com.fakeapistoreapi.testdata.ProductTestData;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Product Management")
@Feature("Create Product")
public class CreateProductTest extends BaseTest {

    @Test
    @Story("Create product with valid data")
    @Description("Verify that a product can be successfully created with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create Product - Valid Data")
    public void createProductWithValidData() {
        Product product = ProductTestData.validProduct();

        Response response = given()
                .spec(requestSpec)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(anyOf(is(200), is(201))) // FakeStore may return 200 or 201
                .body("title", equalTo(product.getTitle()))
                .body("price", equalTo(product.getPrice()))
                .body("description", equalTo(product.getDescription()))
                .body("category", equalTo(product.getCategory()))
                .extract().response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Create product and verify ID is returned")
    @Description("Verify that the API returns an ID after creating a product")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Create Product - ID Returned")
    public void createProductReturnsId() {
        Product product = ProductTestData.validProduct();

        Response response = given()
                .spec(requestSpec)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("id", notNullValue())
                .extract().response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Create product with different category")
    @Description("Verify that creating a product with a different category still works")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Create Product - Different Category")
    public void createProductWithDifferentCategory() {
        Product product = ProductTestData.validProduct();
        product.setCategory("electronics");

        Response response = given()
                .spec(requestSpec)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("category", equalTo("electronics"))
                .extract().response();

        attachResponseToAllure(response);
    }
}
