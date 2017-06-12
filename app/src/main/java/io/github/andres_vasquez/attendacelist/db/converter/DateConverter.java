package io.github.andres_vasquez.attendacelist.db.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by andresvasquez on 6/10/17.
 */

/**
 * Database doesn't support Date object in order to save this data as a long value use DateConverter
 */
public class DateConverter {

    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
