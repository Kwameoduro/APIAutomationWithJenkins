package com.fakeapistoreapi.utils;

import com.fakestoreapi.models.Cart;
import com.fakestoreapi.models.User;
import com.fakestoreapi.specs.RequestSpecs;
import com.fakestoreapi.specs.ResponseSpecs;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import com.fakeapistoreapi.testdata.UserTestData;
import com.fakeapistoreapi.testdata.CartTestData;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Test utility class for generating and managing test data.
 * Placed under src/test/java to avoid main code dependencies.
 */
public final class TestDataUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Random random = new Random();

    private TestDataUtil() {
        // Prevent instantiation
    }

    // -------------------- JSON Test Data --------------------

    /**
     * Load JSON test data from src/test/resources/testdata/
     */
    public static Map<String, Object> loadJsonTestData(String fileName) {
        try (InputStream is = TestDataUtil.class.getClassLoader().getResourceAsStream("testdata/" + fileName)) {
            if (is == null) {
                throw new RuntimeException("Test data file not found: " + fileName);
            }
            return objectMapper.readValue(is, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading test data file: " + fileName, e);
        }
    }

    // -------------------- Random Generators --------------------

    public static String generateRandomEmail() {
        return "user" + System.currentTimeMillis() + "@example.com";
    }

    public static String generateRandomString(int length) {
        String letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(random.nextInt(letters.length())));
        }
        return sb.toString();
    }

    public static String generateRandomPassword() {
        return generateRandomString(8) + random.nextInt(100);
    }

    public static int generateRandomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static String generateRandomDate() {
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        long days = random.nextInt(365 * 3);
        return startDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static String generateRandomPhone() {
        return "+1" + (1000000000L + random.nextInt(900000000));
    }

    public static double generateRandomPrice() {
        return Math.round((10 + (1000 - 10) * random.nextDouble()) * 100.0) / 100.0;
    }

    public static String getRandomCategory(List<String> categories) {
        return categories.get(random.nextInt(categories.size()));
    }

    // -------------------- Dynamic Test Data --------------------

    /**
     * Ensures a User exists in the system. Creates it if it doesn't exist.
     * Returns the ID of the created/existing user.
     */
    public static int ensureUserExists(User user) {
        return RestAssured
                .given()
                .spec(RequestSpecs.defaultSpec())
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(ResponseSpecs.success201())
                .extract()
                .path("id");
    }

    /**
     * Ensures a default valid User exists in the system.
     * Uses UserTestData.validUser() as the default.
     */
    public static int ensureUserExists() {
        return ensureUserExists(UserTestData.validUser());
    }

    /**
     * Ensures a Cart exists in the system. Creates it if it doesn't exist.
     * Returns the ID of the created/existing cart.
     */
    public static int ensureCartExists(Cart cart) {
        return RestAssured
                .given()
                .spec(RequestSpecs.defaultSpec())
                .body(cart)
                .when()
                .post("/carts")
                .then()
                .spec(ResponseSpecs.success201())
                .extract()
                .path("id");
    }

    /**
     * Ensures a default valid Cart exists in the system.
     * Uses CartTestData.validCart() as the default.
     */
    public static int ensureCartExists() {
        return ensureCartExists(CartTestData.validCart());
    }
}
