package com.reitler.got.model.data.access;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(long dateLong) {
        if (dateLong >= 0)
            return new Date(dateLong);
        return null;
    }

    @TypeConverter
    public static long fromDate(Date date) {
        if (date != null)
            return date.getTime();
        return -1;
    }
}
