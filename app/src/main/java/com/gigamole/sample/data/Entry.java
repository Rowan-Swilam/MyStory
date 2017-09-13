package com.gigamole.sample.data;

/**
 * Created by youssef on 07/07/17.
 */

/*
    defines class entry containing all possible attributes
 */
public class Entry {

    private int id;
    private String title;
    private String description;
    private String date;   //--------------> store Date as int, long or String?
    private String image;

    public Entry() {
        title = null;
        description = null;
        date = null;
        image = null;
        id=0;
    }

    public Entry(String title, String description, String date, String image) {

        this.date = date;
        this.description = description;
        this.title = title;
        this.image = image;
        id=0;
    }

    public void setEntryTitle(String title) {
        this.title = title;
    }

    public void setEntryDescription(String des) {
        this.description = des;
    }

    public void setEntryImage(String img) {
        this.image = img;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryTitle() {
        return this.title;
    }

    public String getEntryDescription() {
        return this.description;
    }

    public String getDate() {
        return this.date;
    }

    public String getEntryImage() {
        return this.image;
    }

    public int getId() {
        return id;
    }


}
