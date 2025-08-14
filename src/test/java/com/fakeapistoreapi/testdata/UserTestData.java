package com.fakeapistoreapi.testdata;

import com.fakestoreapi.models.User;
import java.util.UUID;

public final class UserTestData {

    private UserTestData() {}

    private static String uniqueUsername(String base) {
        return base + "_" + UUID.randomUUID().toString().substring(0, 8);
    }

    private static String uniqueEmail(String base) {
        return base + "+" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
    }

    public static User validUser() {
        return new User.Builder()
                .email(uniqueEmail("john.doe"))
                .username(uniqueUsername("johndoe"))
                .password("Password123")
                .name(new User.Name("John", "Doe"))
                .address(new User.Address(
                        "kilcoole",
                        "7835 new road",
                        3,
                        "12926-3874",
                        new User.GeoLocation("-37.3159", "81.1496")
                ))
                .phone("1-570-236-7033")
                .build();
    }

    public static User userWithoutAddressAndPhone() {
        // FakeStoreAPI may require address object, so send minimal valid one
        return new User.Builder()
                .email(uniqueEmail("jane.doe"))
                .username(uniqueUsername("janedoe"))
                .password("Password123")
                .name(new User.Name("Jane", "Doe"))
                .address(new User.Address(
                        "city",
                        "street",
                        1,
                        "00000",
                        new User.GeoLocation("0", "0")
                ))
                .phone("") // empty but still present
                .build();
    }

    public static User invalidUser() {
        // Missing required fields (email, username, password)
        return new User.Builder()
                .name(new User.Name("", ""))
                .address(new User.Address(
                        "city",
                        "street",
                        1,
                        "00000",
                        new User.GeoLocation("0", "0")
                ))
                .phone("123456")
                .build();
    }

    public static final int EXISTING_USER_ID = 1;
    public static final int NON_EXISTENT_USER_ID = 99999;
    public static final String INVALID_USER_ID = "abc";
    public static final int ZERO_USER_ID = 0;

    public static User updatedUser() {
        return new User.Builder()
                .email(uniqueEmail("john.updated"))
                .username(uniqueUsername("johnupdated"))
                .password("NewPassword456")
                .name(new User.Name("John", "Updated"))
                .address(new User.Address(
                        "Los Angeles",
                        "Sunset Blvd",
                        456,
                        "90001",
                        new User.GeoLocation("34.0522", "-118.2437")
                ))
                .phone("987-654-3210")
                .build();
    }

    public static User invalidUpdateUser() {
        return new User.Builder()
                .username("")
                .email("not-an-email")
                .build();
    }
}
