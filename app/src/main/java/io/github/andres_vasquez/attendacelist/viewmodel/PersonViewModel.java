package io.github.andres_vasquez.attendacelist.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import java.util.List;

import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;
import io.github.andres_vasquez.attendacelist.repository.PersonRepositoryImpl;

/**
 * Created by andresvasquez on 6/11/17.
 */

public class PersonViewModel extends AndroidViewModel{

    public PersonRepositoryImpl personRepository;

    @Nullable
    private LiveData<List<PersonEntity>> mPersonList;

    public PersonViewModel(Application application) {
        super(application);

        personRepository =new PersonRepositoryImpl(application);

        // Receive changes
        subscribeToDbChanges();
    }

    /**
     * Get all persons in LiveData variable
     */
    private void subscribeToDbChanges() {
        mPersonList = personRepository.getPersonList();
    }

    @Nullable
    public LiveData<List<PersonEntity>> getPersonList() {
        return mPersonList;
    }

    /**
     * Get one person in LiveData variable
     * @param personId Id of person to observe
     * @return livedata object
     */
    @Nullable
    public LiveData<PersonEntity> getPerson(int personId) {
        return personRepository.getPerson(personId);
    }
}
