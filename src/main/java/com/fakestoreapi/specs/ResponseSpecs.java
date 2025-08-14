package com.fakestoreapi.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

public class ResponseSpecs {

    private ResponseSpecs() {
        // prevent instantiation
    }

    /** 200 OK */
    public static ResponseSpecification success200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .build();
    }

    /** 201 Created */
    public static ResponseSpecification success201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType("application/json")
                .build();
    }

    /** 400 Bad Request */
    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectContentType("application/json")
                .build();
    }

    /** 404 Not Found */
    public static ResponseSpecification notFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .expectContentType("application/json")
                .build();
    }

    /** 500 Internal Server Error */
    public static ResponseSpecification serverError() {
        return new ResponseSpecBuilder()
                .expectStatusCode(500)
                .expectContentType("application/json")
                .build();
    }

    /** 200 OK with single field validation */
    public static ResponseSpecification successWithField(String field, Object value) {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json")
                .expectBody(field, Matchers.equalTo(value))
                .build();
    }

    /** 201 Created with single field validation */
    public static ResponseSpecification createdWithField(String field, Object value) {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType("application/json")
                .expectBody(field, Matchers.equalTo(value))
                .build();
    }

    /** 200 OK with multiple field validations */
    public static ResponseSpecification successWithFields(String[][] fieldsAndValues) {
        ResponseSpecBuilder builder = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType("application/json");
        for (String[] pair : fieldsAndValues) {
            builder.expectBody(pair[0], Matchers.equalTo(pair[1]));
        }
        return builder.build();
    }

    public static ResponseSpecification notFound404() {
        return notFound();
    }


    // 200 OK
    public static ResponseSpecification ok200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .log(LogDetail.ALL)
                .build();
    }

    // 401 Unauthorized
    public static ResponseSpecification unauthorized401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .log(LogDetail.ALL)
                .build();
    }

    // 400 Bad Request
    public static ResponseSpecification badRequest400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .log(LogDetail.ALL)
                .build();
    }
}
