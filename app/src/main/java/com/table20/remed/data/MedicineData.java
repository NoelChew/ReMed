package com.table20.remed.data;

import com.table20.remed.customclass.Medicine;

import java.util.ArrayList;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineData {

    public static ArrayList<Medicine> getMedicineArrayList() {
        ArrayList<Medicine> medicineArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Medicine medicine = new Medicine("m" + String.valueOf(i), "Medicine " + String.valueOf(i + 1), "https://dummyimage.com/400x400/212121/fff.jpg&text=Medicine+" + String.valueOf(i + 1), "DEC 2019");
            medicineArrayList.add(medicine);
        }
        return medicineArrayList;
    }

}
