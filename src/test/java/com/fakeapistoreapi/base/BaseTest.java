package com.fakeapistoreapi.base;

import com.fakestoreapi.specs.ResponseSpecs;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.lessThan;

public abstract class BaseTest {

    protected static RequestSpecification requestSpec;

    // Common reusable response specs
    protected static ResponseSpecification responseSpec;
    protected static ResponseSpecification responseSpec200;
    protected static ResponseSpecification responseSpec201;
    protected static ResponseSpecification responseSpec400;
    protected static ResponseSpecification responseSpec404;

    @BeforeAll
    @Step("Setting up base configuration for all API tests")
    public static void setup() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        // Generic baseline responseSpec (performance & logging)
        responseSpec = new io.restassured.builder.ResponseSpecBuilder()
                .expectResponseTime(lessThan(5000L))
                .build();

        // Pre-configured specs from ResponseSpecs
        responseSpec200 = ResponseSpecs.success200();
        responseSpec201 = ResponseSpecs.success201();
        responseSpec400 = ResponseSpecs.badRequest();
        responseSpec404 = ResponseSpecs.notFound();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;
    }

    @Attachment(value = "API Response", type = "application/json")
    protected String attachResponseToAllure(Response response) {
        if (response != null) {
            return response.asPrettyString();
        }
        return "No response captured";
    }

    @Attachment(value = "API Request Details", type = "text/plain")
    protected String attachRequestDetails(String requestDetails) {
        return requestDetails;
    }
}
