package io.github.andres_vasquez.attendacelist.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by andresvasquez on 6/10/17.
 */

/**
 * Create person table
 */
@Entity(tableName = "person")
public class PersonEntity{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private boolean student;
    private Date timestamp;

    private boolean snack;
    private boolean lunch;

    public PersonEntity() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isStudent() {
        return student;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public boolean isSnack() {
        return snack;
    }

    public boolean isLunch() {
        return lunch;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setSnack(boolean snack) {
        this.snack = snack;
    }

    public void setLunch(boolean lunch) {
        this.lunch = lunch;
    }


}
