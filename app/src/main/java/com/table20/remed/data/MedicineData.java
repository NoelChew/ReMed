package com.table20.remed.data;

import com.table20.remed.customclass.MedicineWithExpiry;

import java.util.ArrayList;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineData {

    public static ArrayList<MedicineWithExpiry> getMedicineArrayList() {
        ArrayList<MedicineWithExpiry> medicineArrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MedicineWithExpiry medicine = new MedicineWithExpiry(String.valueOf(i), "Medicine " + String.valueOf(i + 1), "https://dummyimage.com/600x400/212121/fff.jpg&text=Medicine+" + String.valueOf(i + 1), "31 December 2019");
            medicineArrayList.add(medicine);
        }
        return medicineArrayList;
    }

}
