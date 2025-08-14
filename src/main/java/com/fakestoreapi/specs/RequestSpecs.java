package com.fakestoreapi.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static com.fakestoreapi.constants.ApiPaths.BASE_URL;

public class RequestSpecs {

    private RequestSpecs() {
        // prevent instantiation
    }

    /** Default request spec for APIs without auth */
    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    /** Request spec for APIs requiring Bearer token authentication */
    public static RequestSpecification authSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .log(LogDetail.ALL)
                .build();
    }

    /** Request spec for POST requests with a body object */
    public static RequestSpecification bodySpec(Object body) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .setBody(body)
                .log(LogDetail.ALL)
                .build();
    }

    /** Request spec for POST requests with auth and body */
    public static RequestSpecification authBodySpec(String token, Object body) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + token)
                .setBody(body)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification basic() {
        return new RequestSpecBuilder()
                .setBaseUri("https://fakestoreapi.com")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
    // Generic JSON request
    public static RequestSpecification jsonRequest() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
