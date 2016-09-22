package com.table20.remed.customclass;

/**
 * Created by noelchew on 22/09/2016.
 */

public class Medicine {

    private String id;
    private String name;
    private String imageUrl;
    private String expiryDate;

    public Medicine() {
    }

    public Medicine(String id, String name, String imageUrl, String expiryDate) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.expiryDate = expiryDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
