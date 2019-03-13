package com.example.mytodolist.modules;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {
    public static String getFromattedTime(String time) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        DateFormat dateFormatted = new SimpleDateFormat("dd.MM.yyyy");

        try {
            Date dateAt = dateFormat.parse(time);
            String returnDate = dateFormatted.format(dateAt);
            return returnDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
