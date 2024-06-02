package com.example.examplemod.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getNowDate() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }
}
