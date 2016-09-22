package com.table20.remed.customclass;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineWithExpiry extends Medicine {

    private String expiryDate;

    public MedicineWithExpiry(String id, String name, String imageUrl, String expiryDate) {
        super(id, name, imageUrl);
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
}
