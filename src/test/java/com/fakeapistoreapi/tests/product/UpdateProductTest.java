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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Epic("Product Management")
@Feature("Update Product")
public class UpdateProductTest extends BaseTest {

    @Test
    @Story("Update product with valid data")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Successfully update an existing product")
    @Description("Verify that updating an existing product with valid details returns status 200 and correct updated fields.")
    void updateProductWithValidData() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("id", ProductTestData.VALID_PRODUCT_ID)
                .body(ProductTestData.getValidUpdateProductPayload())
                .when()
                .put("/products/{id}")
                .then()
                .spec(responseSpec200)
                .body("title", equalTo(ProductTestData.UPDATED_TITLE))
                .extract()
                .response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Update product with missing required fields")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Update fails when required fields are missing")
    @Description("Verify that updating a product without mandatory fields returns status 400.")
    void updateProductMissingFields() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("id", ProductTestData.VALID_PRODUCT_ID)
                .body(ProductTestData.getMissingFieldsUpdatePayload())
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(400)
                .extract()
                .response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Update product with invalid ID")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Update fails with invalid product ID")
    @Description("Verify that updating a product using a non-existent or invalid product ID returns status 404.")
    void updateProductInvalidId() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("id", ProductTestData.INVALID_PRODUCT_ID)
                .body(ProductTestData.getValidUpdateProductPayload())
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(404)
                .extract()
                .response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Update product with invalid data types")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Update fails with invalid data types in payload")
    @Description("Verify that updating a product with wrong data types in fields returns status 400.")
    void updateProductInvalidDataTypes() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("id", ProductTestData.VALID_PRODUCT_ID)
                .body(ProductTestData.getInvalidDataTypesUpdatePayload())
                .when()
                .put("/products/{id}")
                .then()
                .statusCode(400)
                .extract()
                .response();

        attachResponseToAllure(response);
    }

    @Test
    @Story("Partial product update")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Successfully update only some fields of an existing product")
    @Description("Verify that updating only certain fields of a product works as expected and leaves other fields unchanged.")
    void partialUpdateProduct() {
        Response response = given()
                .spec(requestSpec)
                .pathParam("id", ProductTestData.VALID_PRODUCT_ID)
                .body(ProductTestData.getPartialUpdateProductPayload())
                .when()
                .put("/products/{id}")
                .then()
                .spec(responseSpec200)
                .body("title", equalTo(ProductTestData.PARTIALLY_UPDATED_TITLE))
                .extract()
                .response();

        attachResponseToAllure(response);
    }
}
