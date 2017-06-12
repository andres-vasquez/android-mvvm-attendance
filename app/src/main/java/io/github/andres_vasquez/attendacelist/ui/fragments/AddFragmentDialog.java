package io.github.andres_vasquez.attendacelist.ui.fragments;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Date;

import io.github.andres_vasquez.attendacelist.R;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;
import io.github.andres_vasquez.attendacelist.ui.activities.ListActivity;
import io.github.andres_vasquez.attendacelist.utils.Constants;
import io.github.andres_vasquez.attendacelist.utils.Utils;

/**
 * Created by andresvasquez on 6/11/17.
 */

public class AddFragmentDialog extends DialogFragment implements View.OnClickListener{

    private static final String LOG = AddFragmentDialog.class.getSimpleName();
    private Context mContext;
    private ListActivity mActivity;

    private int mPersonId = 0;

    private TextInputEditText mNameTextInputEditText;
    private AppCompatRadioButton mStudentAppCompatRadioButton;
    private AppCompatRadioButton mProfessionalAppCompatRadioButton;
    private AppCompatCheckBox mSnackAppCompatCheckBox;
    private AppCompatCheckBox mLunchAppCompatCheckBox;
    private Button mSavePersonButton;

    /**
     * Instance for new Person
     * @return
     */
    public static AddFragmentDialog newInstance() {
        return new AddFragmentDialog();
    }

    /**
     * Instance for edit Person
     * @param personId Person ID to edit
     * @return
     */
    public static AddFragmentDialog newInstance(int personId) {
        AddFragmentDialog frag = new AddFragmentDialog();
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_PERSON_ID, personId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if(context instanceof ListActivity){
            this.mActivity = (ListActivity)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_add, container, false);
        mNameTextInputEditText=(TextInputEditText)view.findViewById(R.id.name_text_input_edittext);
        mStudentAppCompatRadioButton=(AppCompatRadioButton)view.findViewById(R.id.student_radio_button);
        mProfessionalAppCompatRadioButton=(AppCompatRadioButton)view.findViewById(R.id.professional_radio_button);
        mSnackAppCompatCheckBox=(AppCompatCheckBox)view.findViewById(R.id.snack_checkbox);
        mLunchAppCompatCheckBox=(AppCompatCheckBox)view.findViewById(R.id.lunch_checkbox);
        mSavePersonButton=(Button)view.findViewById(R.id.save_person_button);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSavePersonButton.setOnClickListener(this);

        Bundle bundle=getArguments();
        if(bundle!=null){
            if(bundle.containsKey(Constants.EXTRA_PERSON_ID)){
                mPersonId=bundle.getInt(Constants.EXTRA_PERSON_ID);
                loadPerson();
            }
        }
    }

    /**
     * Add observer to person database
     */
    private void loadPerson() {
        mActivity.personViewModel.getPerson(mPersonId).observe(mActivity, observerPerson);
    }

    /**
     * Observer for person
     */
    final Observer<PersonEntity> observerPerson=new Observer<PersonEntity>() {
        @Override
        public void onChanged(@Nullable final PersonEntity personEntity) {
            if(personEntity!=null){
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNameTextInputEditText.setText(personEntity.getName());
                        boolean isStudent=personEntity.isStudent();
                        if(isStudent){
                            mStudentAppCompatRadioButton.setChecked(true);
                        } else {
                            mProfessionalAppCompatRadioButton.setChecked(true);
                        }

                        mSnackAppCompatCheckBox.setChecked(personEntity.isSnack());
                        mLunchAppCompatCheckBox.setChecked(personEntity.isLunch());

                        mSavePersonButton.setText(mContext.getString(R.string.update_person));

                        //Once loaded Remove the observer
                        mActivity.personViewModel.getPerson(mPersonId).removeObserver(observerPerson);
                    }
                });
            }
        }
    };

    /**
     * Save button click
     * @param v Save button view
     */
    @Override
    public void onClick(View v) {
        String name=mNameTextInputEditText.getText().toString();

        if(name.isEmpty()){
            mNameTextInputEditText.setError(getString(R.string.error_name));
            return;
        }

        if(!Utils.isValidName(name)){
            mNameTextInputEditText.setError(getString(R.string.error_name));
            return;
        }

        final PersonEntity personEntity=new PersonEntity();
        personEntity.setName(name);
        personEntity.setStudent(mStudentAppCompatRadioButton.isChecked());
        personEntity.setSnack(mSnackAppCompatCheckBox.isChecked());
        personEntity.setLunch(mLunchAppCompatCheckBox.isChecked());
        personEntity.setTimestamp(new Date());

        if(mPersonId==0){
            mActivity.personViewModel.personRepository.addPerson(personEntity);
        } else {
            personEntity.setId(mPersonId);
            mActivity.personViewModel.personRepository.updatePerson(personEntity);
        }

        //Close dialog
        dismiss();
    }
}
