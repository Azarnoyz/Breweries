package com.example.testapplication.data.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Breweries {

    private int id;
    @NonNull
    @PrimaryKey
    public String name;
    private String breweryType;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String longitude;
    private String latitude;
    private String phone;
    private String websiteUrl;
    private String updatedAt;

    public Breweries(int id, @NonNull String name, String breweryType, String street, String city, String state, String postalCode, String country, String longitude, String latitude, String phone, String websiteUrl, String updatedAt) {
        this.id = id;
        this.name = name;
        this.breweryType = breweryType;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
        this.phone = phone;
        this.websiteUrl = websiteUrl;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getBreweryType() {
        return breweryType;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
