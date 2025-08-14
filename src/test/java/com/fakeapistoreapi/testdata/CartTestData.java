package com.fakeapistoreapi.testdata;

import com.fakeapistoreapi.utils.TestDataUtil;
import com.fakestoreapi.models.Cart;

import java.util.Arrays;
import java.util.Collections;

public final class CartTestData {

    private CartTestData() {
        // Prevent instantiation
    }

    // -------------------- DeleteCartTest constants --------------------
    /** Endpoint used by DeleteCartTest with a path parameter placeholder. */
    public static final String DELETE_CART_ENDPOINT = "/carts/{id}";

    /** A cart id that should not exist (for negative delete scenarios). */
    public static final int NON_EXISTENT_CART_ID = 888888;

    // -------------------- CreateCartTest data --------------------

    /** Returns a valid cart with products */
    public static Cart validCart() {
        int userId = TestDataUtil.ensureUserExists(); // ensure a valid user exists
        return new Cart(
                0, // id will be assigned by API
                userId,
                "2025-08-13", // example date; could be dynamic
                Arrays.asList(
                        new Cart.CartProduct(1, 2),
                        new Cart.CartProduct(2, 1)
                )
        );
    }

    /** Returns a cart with missing optional fields (e.g., empty products) */
    public static Cart cartWithMissingFields() {
        int userId = TestDataUtil.ensureUserExists();
        return new Cart(
                0,
                userId,
                "2025-08-13",
                Collections.emptyList() // no products
        );
    }

    /** Returns an invalid cart (e.g., negative quantity) */
    public static Cart invalidCart() {
        int userId = TestDataUtil.ensureUserExists();
        return new Cart(
                0,
                userId,
                "2025-08-13",
                Arrays.asList(
                        new Cart.CartProduct(1, -2)
                )
        );
    }

    /** Returns a cart with pre-existing products */
    public static Cart cartWithExistingProducts(int userId) {
        return new Cart(
                0,
                userId,
                "2025-08-13",
                Arrays.asList(
                        new Cart.CartProduct(1, 1),
                        new Cart.CartProduct(2, 2)
                )
        );
    }

    // -------------------- GetCartTest data --------------------

    /** Returns an existing cart ID for "Get cart by valid ID" */
    public static int existingCartId() {
        Cart cart = validCart();
        return TestDataUtil.ensureCartExists(cart);
    }

    /** Returns a cart ID that does not exist (kept for compatibility; uses the constant) */
    public static int nonExistentCartId() {
        return NON_EXISTENT_CART_ID;
    }

    /** Returns a valid cart ID with no products */
    public static int cartWithNoProducts() {
        Cart emptyCart = cartWithMissingFields();
        return TestDataUtil.ensureCartExists(emptyCart);
    }

    /** Returns a valid user ID for "Get all carts for a valid user" */
    public static int validUserIdForCarts() {
        return TestDataUtil.ensureUserExists();
    }

    // -------------------- UpdateCartTest data --------------------

    /** Returns updated valid cart data */
    public static Cart validUpdatedCart() {
        int userId = TestDataUtil.ensureUserExists();
        return new Cart(
                0,
                userId,
                "2025-08-14", // updated date
                Arrays.asList(
                        new Cart.CartProduct(1, 3),
                        new Cart.CartProduct(3, 2)
                )
        );
    }

    /** Returns invalid cart update data (negative quantity) */
    public static Cart invalidCartData() {
        int userId = TestDataUtil.ensureUserExists();
        return new Cart(
                0,
                userId,
                "2025-08-14",
                Arrays.asList(
                        new Cart.CartProduct(1, -5)
                )
        );
    }

    /** Returns empty cart update data */
    public static Cart emptyCartData() {
        int userId = TestDataUtil.ensureUserExists();
        return new Cart(
                0,
                userId,
                "2025-08-14",
                Collections.emptyList()
        );
    }

    // -------------------- DeleteCartTest helpers (optional) --------------------

    /**
     * Convenience: create a new cart and return its ID for deletion tests.
     * Not used by your current DeleteCartTest (which calls TestDataUtil.ensureCartExists()),
     * but kept here if you prefer to reference data only from CartTestData later.
     */
    public static int cartIdToDelete() {
        return TestDataUtil.ensureCartExists(validCart());
    }



    public static int nonExistingCartId() {
        return 9999; // unlikely to exist
    }

    public static String invalidCartId() {
        return "abc"; // invalid format for ID
    }

    public static final int VALID_CART_ID = 1;
    public static final int VALID_USER_ID = 1;
}
