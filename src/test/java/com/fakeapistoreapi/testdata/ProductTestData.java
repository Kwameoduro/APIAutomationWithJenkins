package com.fakeapistoreapi.testdata;

import com.fakestoreapi.models.Product;

import java.math.BigDecimal;
import java.util.Map;

public final class ProductTestData {

    private ProductTestData() {
        // Prevent instantiation
    }

    // -------------------- Create Product Test Data --------------------
    public static Product validProduct() {
        return new Product(
                0, // id (assigned by API)
                "Wireless Bluetooth Headphones",
                BigDecimal.valueOf(59.99),
                "High-quality over-ear headphones with noise cancellation.",
                "electronics",
                null,   // image (optional)
                null    // rating (optional)
        );
    }

    public static Product productWithoutTitle() {
        return new Product(
                0,
                null, // missing title
                BigDecimal.valueOf(59.99),
                "High-quality over-ear headphones with noise cancellation.",
                "electronics",
                null,
                null
        );
    }

    public static Product productWithNegativePrice() {
        return new Product(
                0,
                "Wireless Bluetooth Headphones",
                BigDecimal.valueOf(-10.00), // invalid price
                "High-quality over-ear headphones with noise cancellation.",
                "electronics",
                null,
                null
        );
    }

    // -------------------- Endpoints --------------------
    public static final String GET_ALL_PRODUCTS_ENDPOINT = "/products";
    public static final String GET_SINGLE_PRODUCT_ENDPOINT = "/products/{id}";
    public static final String DELETE_PRODUCT_ENDPOINT = "/products/{id}";
    public static final String CREATE_PRODUCT_ENDPOINT = "/products";
    public static final String UPDATE_PRODUCT_ENDPOINT = "/products/{id}";

    // -------------------- General Test IDs & Limits --------------------
    public static final int VALID_PRODUCT_ID = 1;
    public static final int INVALID_PRODUCT_ID = 99999;
    public static final int PRODUCT_LIMIT = 5;

    // -------------------- Update Product Test Data --------------------
    public static final String UPDATED_TITLE = "Updated Test Product";
    public static final String PARTIALLY_UPDATED_TITLE = "Partially Updated Product";

    public static Map<String, Object> getValidUpdateProductPayload() {
        return Map.of(
                "title", UPDATED_TITLE,
                "price", 29.99,
                "description", "Updated description",
                "category", "electronics"
        );
    }

    public static Map<String, Object> getMissingFieldsUpdatePayload() {
        return Map.of(
                "title", UPDATED_TITLE
                // Missing price, description, category
        );
    }

    public static Map<String, Object> getInvalidDataTypesUpdatePayload() {
        return Map.of(
                "title", 12345, // Should be string
                "price", "twenty", // Should be number
                "description", true,
                "category", 456
        );
    }

    public static Map<String, Object> getPartialUpdateProductPayload() {
        return Map.of(
                "title", PARTIALLY_UPDATED_TITLE
        );
    }

    public static final String NON_NUMERIC_PRODUCT_ID = "abc";

}
