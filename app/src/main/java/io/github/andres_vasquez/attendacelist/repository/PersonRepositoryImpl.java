package io.github.andres_vasquez.attendacelist.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import io.github.andres_vasquez.attendacelist.db.AppDatabase;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;

/**
 * Created by andresvasquez on 6/11/17.
 */

/**
 * Use the database to provide the access using threads
 */
public class PersonRepositoryImpl implements PersonRepository{

    private Application mApplication;
    private AppDatabase mDb;

    public PersonRepositoryImpl(Application application) {
        this.mApplication = application;
        mDb = AppDatabase.getDatabase(mApplication);
    }

    @Override
    public LiveData<List<PersonEntity>> getPersonList() {
        return mDb.personDao().loadlAllPersons();
    }

    @Override
    public LiveData<PersonEntity> getPerson(int personId) {
        return mDb.personDao().loadPerson(personId);
    }

    @Override
    public void addPerson(final PersonEntity person) {
        new Thread(){
            @Override
            public void run() {
                mDb.personDao().insert(person);
            }
        }.start();
    }

    @Override
    public void updatePerson(final PersonEntity person) {
        new Thread(){
            @Override
            public void run() {
                mDb.personDao().updatePerson(person);
            }
        }.start();
    }

    @Override
    public void deletePerson(final PersonEntity person) {
        new Thread(){
            @Override
            public void run() {
                mDb.personDao().deletePerson(person.getId());
            }
        }.start();
    }
}
