package com.fakestoreapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;

    public User() {
    }

    // Full constructor for all fields
    public User(int id, String email, String username, String password,
                Name name, Address address, String phone) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Builder pattern for easy test data creation
    public static class Builder {
        private int id;
        private String email;
        private String username;
        private String password;
        private Name name;
        private Address address;
        private String phone;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder address(Address address) {
            this.address = address;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            return new User(id, email, username, password, name, address, phone);
        }
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Name getName() { return name; }
    public void setName(Name name) { this.name = name; }

    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    // Nested classes remain unchanged
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Name {
        private String firstname;
        private String lastname;

        public Name() {}
        public Name(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }

        public String getFirstname() { return firstname; }
        public void setFirstname(String firstname) { this.firstname = firstname; }
        public String getLastname() { return lastname; }
        public void setLastname(String lastname) { this.lastname = lastname; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String city;
        private String street;
        private int number;
        private String zipcode;
        private GeoLocation geolocation;

        public Address() {}
        public Address(String city, String street, int number, String zipcode, GeoLocation geolocation) {
            this.city = city;
            this.street = street;
            this.number = number;
            this.zipcode = zipcode;
            this.geolocation = geolocation;
        }

        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getStreet() { return street; }
        public void setStreet(String street) { this.street = street; }
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
        public String getZipcode() { return zipcode; }
        public void setZipcode(String zipcode) { this.zipcode = zipcode; }
        public GeoLocation getGeolocation() { return geolocation; }
        public void setGeolocation(GeoLocation geolocation) { this.geolocation = geolocation; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GeoLocation {
        private String lat;
        private String longi;

        public GeoLocation() {}
        public GeoLocation(String lat, String longi) {
            this.lat = lat;
            this.longi = longi;
        }

        @JsonProperty("lat")
        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }

        @JsonProperty("long")
        public String getLongi() { return longi; }
        public void setLongi(String longi) { this.longi = longi; }
    }
}
