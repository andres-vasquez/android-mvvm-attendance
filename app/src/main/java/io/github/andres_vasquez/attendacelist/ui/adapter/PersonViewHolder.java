package io.github.andres_vasquez.attendacelist.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.andres_vasquez.attendacelist.R;
import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;

/**
 * Created by andresvasquez on 6/11/17.
 */

public class PersonViewHolder extends RecyclerView.ViewHolder{

    RelativeLayout personRelativeLayout;
    TextView nameTextView;
    ImageView editImageView;
    ImageView deleteImageView;
    TextView studentTextView;
    ImageView snackImageView;
    ImageView lunchImageView;

    public PersonViewHolder(View itemView) {
        super(itemView);

        personRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.person_relative_layout);
        nameTextView= (TextView) itemView.findViewById(R.id.name_text_view);
        editImageView= (ImageView) itemView.findViewById(R.id.edit_image_view);
        deleteImageView= (ImageView) itemView.findViewById(R.id.delete_image_view);

        studentTextView= (TextView) itemView.findViewById(R.id.student_text_view);
        snackImageView= (ImageView) itemView.findViewById(R.id.snack_check_image_view);
        lunchImageView= (ImageView) itemView.findViewById(R.id.lunch_check_image_view);
    }
}
