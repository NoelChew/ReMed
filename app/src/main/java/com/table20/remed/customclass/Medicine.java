package com.table20.remed.customclass;

import com.google.firebase.database.Exclude;
import com.table20.remed.util.DateUtil;

import java.util.Date;

/**
 * Created by noelchew on 22/09/2016.
 */

public class Medicine {

    private String id;
    private String name;
    private String imageUrl;
    private long purchaseDate;
    private long expiryDate;

    public Medicine() {
    }

    public Medicine(String id, String name, String imageUrl, long purchaseDate, long expiryDate) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.purchaseDate = purchaseDate;
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

    public long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Exclude
    public String getPurchaseDateInString() {
        return DateUtil.dateToString(new Date(purchaseDate), "MMM yyyy");
    }

    public long getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(long expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Exclude
    public String getExpiryDateInString() {
        return DateUtil.dateToString(new Date(expiryDate), "MMM yyyy");
    }
}
