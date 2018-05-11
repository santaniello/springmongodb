package com.example.felipe.springmongodbaggregate.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataUtils {

    public static Date toMongoDate(String data){
        Date dateReturn = null;
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS\'Z\'");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            dateReturn = dateFormatter.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateReturn;
    }

    public static Date toDate(String date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
