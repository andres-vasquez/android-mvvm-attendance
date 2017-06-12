package io.github.andres_vasquez.attendacelist.repository;

import android.arch.lifecycle.LiveData;
import java.util.List;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;
/**
 * Created by andresvasquez on 6/11/17.
 */

/**
 * Use the methods to manage the data
 */
public interface PersonRepository {
    LiveData<List<PersonEntity>> getPersonList();
    LiveData<PersonEntity> getPerson(int personId);
    void addPerson(PersonEntity person);
    void updatePerson(PersonEntity person);
    void deletePerson(PersonEntity person);
}
