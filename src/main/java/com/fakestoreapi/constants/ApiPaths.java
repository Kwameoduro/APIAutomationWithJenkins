package com.fakestoreapi.constants;

//  Holds base paths for all FakeStore API endpoints.
public final class ApiPaths {

    // Base URI
    public static final String BASE_URL = "https://fakestoreapi.com";

    // Resource paths
    public static final String PRODUCTS = "/products";
    public static final String CARTS = "/carts";
    public static final String USERS = "/users";
    public static final String CATEGORIES = "/products/categories";
    public static final String CATEGORY_PRODUCTS = "/products/category"; // e.g., /products/category/jewelery
    public static final String AUTHENTICATION = "/auth";

    private ApiPaths() {
        // Prevent instantiation
    }
}