package io.github.andres_vasquez.attendacelist.ui.adapter;

import io.github.andres_vasquez.attendacelist.db.entity.PersonEntity;

/**
 * Created by andresvasquez on 6/11/17.
 */

/**
 * Handle row actions callback
 */
public interface PersonClickCallback {
    void onEditCLick(PersonEntity person);
    void onDeleteCLick(PersonEntity person);
}
