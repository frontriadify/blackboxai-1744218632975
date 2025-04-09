package com.example.petadoptionapp.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Pet {
    private String id;
    private String name;
    private String type;
    private String description;
    private String imageUrl;
    private GeoPoint location;
    private String userId;
    private Timestamp timestamp;

    // Empty constructor needed for Firestore
    public Pet() {}

    public Pet(String name, String type, String description, String imageUrl, GeoPoint location, String userId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.userId = userId;
        this.timestamp = Timestamp.now();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public GeoPoint getLocation() { return location; }
    public void setLocation(GeoPoint location) { this.location = location; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Timestamp getTimestamp() { return timestamp; }
    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }
}
