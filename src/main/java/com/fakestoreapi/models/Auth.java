package com.fakestoreapi.models;



import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Auth implements Serializable {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("token")
    private String token;

    public Auth() {}

    public Auth(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        return "Auth{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                ", token='" + token + '\'' +
                '}';
    }
}
