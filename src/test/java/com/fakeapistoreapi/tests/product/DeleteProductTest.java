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
public class DeleteProductTest extends BaseTest {

    @Test
    @Story("Delete an existing product")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Delete Product - Valid Product ID")
    @Description("Verify that a product can be successfully deleted using its valid ID.")
    public void deleteProductWithValidId() {
        int validId = ProductTestData.VALID_PRODUCT_ID;

        Response response = given()
                .spec(requestSpec)
                .when()
                .delete(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, validId);

        response.then()
                .statusCode(200);

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete a non-existent product")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete Product - Invalid Product ID")
    @Description("Verify that deleting a non-existent product returns an error status code.")
    public void deleteProductWithInvalidId() {
        int invalidId = ProductTestData.INVALID_PRODUCT_ID;

        Response response = given()
                .spec(requestSpec)
                .when()
                .delete(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, invalidId);

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete product with invalid ID format")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Delete Product - Non-numeric Product ID")
    @Description("Verify that deleting a product with a non-numeric ID returns a 400 Bad Request or 404 Not Found.")
    public void deleteProductWithNonNumericId() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/products/" + ProductTestData.NON_NUMERIC_PRODUCT_ID);

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete product twice")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Delete Product - Already Deleted")
    @Description("Verify that attempting to delete a product that was already deleted returns an error.")
    public void deleteProductTwice() {
        int validId = ProductTestData.VALID_PRODUCT_ID;

        // First delete
        given()
                .spec(requestSpec)
                .when()
                .delete(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, validId)
                .then()
                .statusCode(200);

        // Second delete
        Response response = given()
                .spec(requestSpec)
                .when()
                .delete(ProductTestData.GET_SINGLE_PRODUCT_ENDPOINT, validId);

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));

        attachResponseToAllure(response);
    }

    @Test
    @Story("Delete product with missing ID")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Delete Product - Missing ID")
    @Description("Verify that attempting to delete a product without specifying an ID returns an error.")
    public void deleteProductMissingId() {
        Response response = given()
                .spec(requestSpec)
                .when()
                .delete("/products/");

        response.then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));

        attachResponseToAllure(response);
    }
}
