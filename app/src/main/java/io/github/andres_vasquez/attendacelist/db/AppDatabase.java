package io.github.andres_vasquez.attendacelist.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import io.github.andres_vasquez.attendacelist.db.converter.DateConverter;
import io.github.andres_vasquez.attendacelist.db.dao.PersonDao;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;

/**
 * Created by andresvasquez on 6/11/17.
 */

//TODO Step 6: Set entities
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    static final String DATABASE_NAME = "attendance-list-db";
    private static AppDatabase INSTANCE;

    public abstract PersonDao personDao();

    /**
     * Get database instance
     * @param context Context of the application
     * @return instance of database
     */
    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            //TODO Step 3: Prepare database instance
        }
        return INSTANCE;
    }
}
