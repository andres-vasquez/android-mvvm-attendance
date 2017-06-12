package io.github.andres_vasquez.attendacelist.ui.activities;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import io.github.andres_vasquez.attendacelist.R;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;
import io.github.andres_vasquez.attendacelist.ui.adapter.PersonAdapter;
import io.github.andres_vasquez.attendacelist.ui.adapter.PersonClickCallback;
import io.github.andres_vasquez.attendacelist.ui.fragments.AddFragmentDialog;
import io.github.andres_vasquez.attendacelist.utils.Constants;
import io.github.andres_vasquez.attendacelist.viewmodel.PersonViewModel;

public class ListActivity extends AppCompatActivity implements LifecycleRegistryOwner,
        View.OnClickListener,
        PersonClickCallback{

    private static final String LOG = ListActivity.class.getSimpleName();
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private Context mContext;
    private Toolbar mToolbar;

    private FloatingActionButton mAddPersonFab;

    private RecyclerView mPersonRecyclerView;
    private PersonAdapter mPersonAdapter;

    public PersonViewModel personViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mContext=this;

        initViews();
        setSupportActionBar(mToolbar);

        mAddPersonFab.setOnClickListener(this);

        setupList();

        initViewModel();
        subscribeUI();
    }

    /**
     * Init view components
     */
    private void initViews() {
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mPersonRecyclerView = (RecyclerView)findViewById(R.id.person_recycler_view);
        mAddPersonFab = (FloatingActionButton)findViewById(R.id.add_person_fab);
    }

    /**
     * Setup list parameters
     */
    private void setupList() {
        mPersonAdapter=new PersonAdapter(mContext,this);
        mPersonRecyclerView.setAdapter(mPersonAdapter);

        //Init Recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false);
        mPersonRecyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * Init view model personViewModel
     */
    private void initViewModel() {
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
    }

    /**
     * Subscribe to variables
     */
    private void subscribeUI() {
        personViewModel.getPersonList().observe(this, new Observer<List<PersonEntity>>() {
            @Override
            public void onChanged(@Nullable List<PersonEntity> personEntities) {
                if(personEntities!=null){
                    mPersonAdapter.swap(personEntities);
                }
            }
        });
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    /**
     * Click on add new person Floating Action Button
     * @param v floating action button
     */
    @Override
    public void onClick(View v) {
        DialogFragment newFragment = AddFragmentDialog.newInstance();
        newFragment.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
    }

    /**
     * Click on edit row person
     * @param person Object person
     */
    @Override
    public void onEditCLick(PersonEntity person) {
        DialogFragment newFragment = AddFragmentDialog.newInstance(person.getId());
        newFragment.show(getSupportFragmentManager(), Constants.TAG_DIALOG);
    }

    /**
     * Click on delete row person
     * @param person Object person
     */
    @Override
    public void onDeleteCLick(PersonEntity person) {
        personViewModel.personRepository.deletePerson(person);
    }
}
