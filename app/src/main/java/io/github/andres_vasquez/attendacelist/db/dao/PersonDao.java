package io.github.andres_vasquez.attendacelist.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;

/**
 * Created by andresvasquez on 6/11/17.
 */

@Dao
public interface PersonDao {
    @Query("SELECT * FROM person")
    LiveData<List<PersonEntity>> loadlAllPersons();

    @Query("SELECT * FROM person WHERE id = :personId")
    LiveData<PersonEntity> loadPerson(int personId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(PersonEntity person);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePerson(PersonEntity person);

    @Query("DELETE FROM person WHERE id=:personId")
    void deletePerson(int personId);
}
