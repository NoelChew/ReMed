package com.table20.remed.customclass;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineWithoutExpiry {

    private String id;
    private String name;
    private String imageUrl;

    public MedicineWithoutExpiry() {

    }

    public MedicineWithoutExpiry(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
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
}
