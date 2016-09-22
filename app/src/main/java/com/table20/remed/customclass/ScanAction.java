package com.table20.remed.customclass;

/**
 * Created by noelchew on 22/09/2016.
 */

public class ScanAction {
    public static final int TYPE_MEDICINE = 0;
    public static final int TYPE_DROPBOX = 1;
    private int scanType; // 0: medicine, 1: dropbox
    private Medicine medicine;
    private Dropbox dropbox;

    public ScanAction() {
    }

    public ScanAction(Medicine medicine) {
        this.scanType = TYPE_MEDICINE;
        this.medicine = medicine;
    }

    public ScanAction(Dropbox dropbox) {
        this.scanType = TYPE_DROPBOX;
        this.dropbox = dropbox;
    }

    public boolean isMedicine() {
        return scanType == TYPE_MEDICINE;
    }

    public int getScanType() {
        return scanType;
    }

    public void setScanType(int scanType) {
        this.scanType = scanType;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Dropbox getDropbox() {
        return dropbox;
    }

    public void setDropbox(Dropbox dropbox) {
        this.dropbox = dropbox;
    }
}
