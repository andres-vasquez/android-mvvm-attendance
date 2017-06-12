package io.github.andres_vasquez.attendacelist.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.andres_vasquez.attendacelist.R;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;
/**
 * Created by andresvasquez on 6/11/17.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonViewHolder>{

    private Context mContext;
    private List<PersonEntity> mItems;
    private LayoutInflater mLayoutInflater;

    @Nullable
    private PersonClickCallback mPersonClickCallback;

    /**
     * Todos Adapter constructor
     * @param context Fragment context
     */
    public PersonAdapter(Context context, PersonClickCallback personClickCallback){
        this.mContext = context;
        this.mPersonClickCallback = personClickCallback;

        mLayoutInflater = LayoutInflater.from(mContext);
        this.mItems =new ArrayList<PersonEntity>();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.row_person, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, final int position) {
        final PersonEntity person= mItems.get(position);

        holder.nameTextView.setText(person.getName());

        if(person.isStudent()){
            holder.studentTextView.setText(mContext.getString(R.string.student));
        } else {
            holder.studentTextView.setText(mContext.getString(R.string.professional));
        }

        if(person.isSnack()){
            holder.snackImageView.setImageResource(R.drawable.ic_check);
        } else {
            holder.snackImageView.setImageResource(R.drawable.ic_check_disabled);
        }

        if(person.isLunch()){
            holder.lunchImageView.setImageResource(R.drawable.ic_check);
        } else {
            holder.lunchImageView.setImageResource(R.drawable.ic_check_disabled);
        }

        //Add click to edit button
        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPersonClickCallback!=null){
                    mPersonClickCallback.onEditCLick(person);
                }
            }
        });

        //Add click to delete button
        holder.deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPersonClickCallback!=null){
                    mPersonClickCallback.onDeleteCLick(person);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    /**
     * SWAP list from database into adapter
     * @param todoList todos List from database
     */
    public void swap(List<PersonEntity> todoList) {
        this.mItems.clear();
        this.mItems.addAll(todoList);
        notifyDataSetChanged();
    }
}
