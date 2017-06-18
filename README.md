Android Architecture Components!
===================

> **Hola!:**
> Bienvenido al **Codelab de Android Architecture Components**.
>
> Slides: https://goo.gl/yTttTD
>
> Mi nombre es Andrés Vasquez y vengo a mostrarte de manera sencilla una aplicacion que implementa los nuevos componentes de arquitectura de Android.
> Te dejo mis datos por si tienes dudas:
>
> andres.vasquez.agramont@gmail.com
>
> https://github.com/andres-vasquez
> 
> **Espero lo disfrutes!**

PREPARING THE PROJECT (done)
-------------

Step 1: Add maven repository
Ve a build.gradle y agrega el repositorio de Google en:
```
allprojects {
    repositories {
    ...
     maven {
            url "https://maven.google.com"
        }
    ...
    }
}
```

Step 2: Add architecture libraries
Ve a app/build.gradle y agrega las librerías de Android Arch
```
compile 'android.arch.lifecycle:extensions:1.0.0-alpha1';
compile 'android.arch.persistence.room:runtime:1.0.0-alpha1';
annotationProcessor "android.arch.lifecycle:compiler:1.0.0-alpha1";
annotationProcessor "android.arch.persistence.room:compiler:1.0.0-alpha1";
```

PREPARING THE DATABASE
--------------

Step 3: Prepare database instance
Ve a db/AppDatabase.java Línea 33
```
INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME).build();
```

Step 4: Prepare Person table
Ve a db/entity/PersonEntity.java Línea 16 y agrega la annotation:
```
@Entity(tableName = "person")
```

Step 5: Set id as PrimaryKey Autoincrement
Ve a db/entity/PersonEntity.java Línea 18 y agrega la annotation:
```
@PrimaryKey(autoGenerate = true)
```

Step 6: Set entities
Ve a db/AppDatabase.java Línea 17 y vincula la clase PersonEntity como tabla de la base de datos.
```
@Database(entities = {PersonEntity.class}, version = 1)
```

Step 7: Define person table CRUD operations
Ve a db/dao/PersonDao.java Línea 21 y agrega las funciones de base de datos.
```
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
```


PREPARING THE REPOSITORY
--------

Step 8: Define the data access method
Ve a repository/PersonRepository.java Línea 14 y agrega los métodos a la interfaz
```
LiveData<List<PersonEntity>> getPersonList();
LiveData<PersonEntity> getPerson(int personId);
void addPerson(PersonEntity person);
void updatePerson(PersonEntity person);
void deletePerson(PersonEntity person);
```

Step 9: Connect methods with implementation
Ve a repository/PersonRepositoryImpl.java Línea 28 y agrega la capa de acceso a la base de datos
```
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
```

PREPARING THE VIEWMODEL


Step 10: Bind the person List LiveData
Ve a viewmodel/PersonViewModel.java Línea 37 y llena la variable livedata desde la capa de repository
```
mPersonList = personRepository.getPersonList();
```

Step 11: Bind the single person LiveData
Ve a viewmodel/PersonViewModel.java Línea 52 y llena la variable livedata desde la capa de repository
```
return personRepository.getPerson(personId);
```

> Nota: Eliminar return null;


PREPARING THE UI
-----------

Step 12: Initialize the PersonViewModel
Ve a ui/activities/ListActivity.java Línea 88 e inicializa el viewModel
```
personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
```

Step 13: Subscribe to PersonList observer
Ve a ui/activities/ListActivity.java Línea 95 y llega la función subscribeUI
```
personViewModel.getPersonList().observe(this, new Observer<List<PersonEntity>>() {
            @Override
            public void onChanged(@Nullable List<PersonEntity> personEntities) {
                if(personEntities!=null){
                    mPersonAdapter.swap(personEntities);
                }
            }
        });
```

Step 14: Adding create action
Ve a ui/fragments/AddFragmentDialog.java Línea 166 y llama al método addPerson
```
mActivity.personViewModel.personRepository.addPerson(personEntity);
```

Step 15: Adding update action
Ve a ui/fragments/AddFragmentDialog.java Línea 169 y llama al método updatePerson
```
mActivity.personViewModel.personRepository.updatePerson(personEntity);
```

Step 16: Subscribe single person observer
Ve a ui/fragments/AddFragmentDialog.java Línea 135 y susbribete a observerPerson para analizar los cambios que el objeto persona pueda tener
```
mActivity.personViewModel.getPerson(mPersonId).observe(mActivity, observerPerson);
```

Step 17: Adding delete action
Ve a ui/activities/ListActivity.java Línea 129 y llama al método deletePerson
```
personViewModel.personRepository.deletePerson(person);
```



Problems?
--------------
Si tienes problemas o no puedes hacer algun paso puedes cambiar al branch **Complete** y ver el ejemplo funcionando!