package com.table20.remed.user;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by noelchew on 16/09/16.
 */
public class HackathonUserModule {
    public static String getUserId() {
        String myDeviceModel = android.os.Build.MODEL;
        String hash = md5(myDeviceModel);
        if (TextUtils.isEmpty(hash)) {
            hash = "abc";
        }
        return "uid_" + hash;
    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}

