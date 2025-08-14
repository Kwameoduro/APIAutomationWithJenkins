package com.fakestoreapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart implements Serializable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("userId")
    private int userId;

    @JsonProperty("date")
    private String date; // ISO date string

    @JsonProperty("products")
    private List<CartProduct> products;

    public Cart() {
    }

    public Cart(int id, int userId, String date, List<CartProduct> products) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", products=" + products +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CartProduct implements Serializable {

        @JsonProperty("productId")
        private int productId;

        @JsonProperty("quantity")
        private int quantity;

        public CartProduct() {
        }

        public CartProduct(int productId, int quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "CartProduct{" +
                    "productId=" + productId +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}
