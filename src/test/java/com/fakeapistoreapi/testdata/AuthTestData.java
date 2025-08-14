package com.fakeapistoreapi.testdata;

import com.fakestoreapi.models.Auth;

import java.util.HashMap;
import java.util.Map;

public final class AuthTestData {

    public static final String AUTH_LOGIN_ENDPOINT = "/auth/login";

    private AuthTestData() {
        // Prevent instantiation
    }

    // --- Constants for direct field use in LoginTest ---
    public static final String VALID_USERNAME = "mor_2314";
    public static final String VALID_PASSWORD = "83r5^_";

    public static final String INVALID_USERNAME = "wrong_user";
    public static final String INVALID_PASSWORD = "wrong_pass";

    // --- Auth object builders ---

    /**
     * ✅ Valid credentials (expected to pass)
     */
    public static Auth validAuth() {
        return new Auth(VALID_USERNAME, VALID_PASSWORD);
    }

    /**
     * ❌ Invalid username, valid password
     */
    public static Auth invalidUsernameAuth() {
        return new Auth(INVALID_USERNAME, VALID_PASSWORD);
    }

    /**
     * ❌ Valid username, invalid password
     */
    public static Auth invalidPasswordAuth() {
        return new Auth(VALID_USERNAME, INVALID_PASSWORD);
    }

    /**
     * ❌ Both invalid
     */
    public static Auth invalidUsernameAndPasswordAuth() {
        return new Auth(INVALID_USERNAME, INVALID_PASSWORD);
    }

    /**
     * ❌ Blank username
     */
    public static Auth blankUsernameAuth() {
        return new Auth("", VALID_PASSWORD);
    }

    /**
     * ❌ Blank password
     */
    public static Auth blankPasswordAuth() {
        return new Auth(VALID_USERNAME, "");
    }

    /**
     * ❌ Both blank
     */
    public static Auth blankUsernameAndPasswordAuth() {
        return new Auth("", "");
    }

    // Endpoint for login
    public static final String AUTH_ENDPOINT = "/auth/login";

    public static Map<String, String> getValidLoginPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", VALID_USERNAME);
        payload.put("password", VALID_PASSWORD);
        return payload;
    }


    public static Auth blankAuth() {
        return new Auth("", "");
    }

    // ❌ Invalid username, valid password
    public static Auth invalidUsername() {
        return new Auth("wrong_user", "83r5^_");
    }

    // ❌ Valid username, invalid password
    public static Auth invalidPassword() {
        return new Auth("mor_2314", "wrong_password");
    }
}
