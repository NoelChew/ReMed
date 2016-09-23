package com.table20.remed.data;

import android.content.Context;

import com.table20.remed.R;
import com.table20.remed.customclass.Medicine;
import com.table20.remed.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineData {

    public static ArrayList<Medicine> getMedicineArrayList1(Context context) {
        ArrayList<Medicine> medicineArrayList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
//            Medicine medicine = new Medicine("m" + String.valueOf(i), "Medicine " + String.valueOf(i + 1), "https://dummyimage.com/400x400/212121/fff.jpg&text=Medicine+" + String.valueOf(i + 1), DateUtil.getDateFromString(String.valueOf(12+i) + "/" + String.valueOf(4+i) + "/" + String.valueOf(2016), "dd/MM/yyyy").getTime(), DateUtil.getDateFromString("21/" + String.valueOf(8+i) + "/" + String.valueOf(2016 + i), "dd/MM/yyyy").getTime());
            Medicine medicine = new Medicine("m" + String.valueOf(i), "Advil", context.getString(R.string.med1_image_url), DateUtil.getDateFromString(String.valueOf(12+i) + "/" + String.valueOf(4+i) + "/" + String.valueOf(2016), "dd/MM/yyyy").getTime(), DateUtil.getDateFromString("21/" + String.valueOf(8+i) + "/" + String.valueOf(2016 + i), "dd/MM/yyyy").getTime());
            medicineArrayList.add(medicine);
        }
        return medicineArrayList;
    }

    public static ArrayList<Medicine> getMedicineArrayList2(Context context) {
        ArrayList<Medicine> medicineArrayList = new ArrayList<>();
        for (int i = 4; i < 7; i++) {
//            Medicine medicine = new Medicine("m" + String.valueOf(i), "Medicine " + String.valueOf(i + 1), "https://dummyimage.com/400x400/212121/fff.jpg&text=Medicine+" + String.valueOf(i + 1), DateUtil.getDateFromString(String.valueOf(8+i) + "/" + String.valueOf(2+i) + "/" + String.valueOf(2016), "dd/MM/yyyy").getTime(), DateUtil.getDateFromString("10/" + String.valueOf(5+i) + "/" + String.valueOf(2012 + i), "dd/MM/yyyy").getTime());
            Medicine medicine = new Medicine("m" + String.valueOf(i), "Advil", context.getString(R.string.med1_image_url), DateUtil.getDateFromString(String.valueOf(8+i) + "/" + String.valueOf(2+i) + "/" + String.valueOf(2016), "dd/MM/yyyy").getTime(), DateUtil.getDateFromString("10/" + String.valueOf(5+i) + "/" + String.valueOf(2012 + i), "dd/MM/yyyy").getTime());
            medicineArrayList.add(medicine);
        }
        return medicineArrayList;
    }

}
