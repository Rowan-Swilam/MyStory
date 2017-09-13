package com.gigamole.sample.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gigamole.sample.data.DiaryContract.DiaryEntry;

/**
 * Created by youssef on 06/07/17.
 */

public class DiaryDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "diary.db";

    private static final int DATABASE_VERSION = 8;

    public DiaryDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_DIARY_TABLE = "CREATE TABLE " + DiaryEntry.TABLE_NAME +
                " (" +
                DiaryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                DiaryEntry.COLUMN_ENTRY_TITLE + " TEXT NOT NULL, " +
                DiaryEntry.COLUMN_ENTRY_DESCRIPTION + " TEXT NOT NULL, " +
                DiaryEntry.COLUMN_ENTRY_DATE + " TIMESTAMP DEFAULT TIMESTAMP,  " +
                DiaryEntry.COLUMN_ENTRY_IMAGE + " TEXT NOT NULL " +
                ");" ;

        db.execSQL(CREATE_DIARY_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DiaryEntry.TABLE_NAME);
        onCreate(db);

    }

}
