package com.gigamole.sample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gigamole.sample.adapters.HorizontalPagerAdapter;
import com.gigamole.sample.data.DiaryContract.DiaryEntry;

import java.util.ArrayList;
import java.util.List;


public class DiaryDataSource {

    private SQLiteDatabase database;
    private DiaryDbHelper dbHelper;
    private Context context;

    private String[] allColumns = {DiaryEntry._ID, DiaryEntry.COLUMN_ENTRY_TITLE,
            DiaryEntry.COLUMN_ENTRY_DESCRIPTION, DiaryEntry.COLUMN_ENTRY_DATE
            , DiaryEntry.COLUMN_ENTRY_IMAGE};

    public DiaryDataSource(Context context) {
        this.context = context;
        dbHelper = new DiaryDbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();

    }

    public void close() {
       database.delete(DiaryEntry.TABLE_NAME,null,null);
    //    database.close();
    }

    public long addEntry(Entry newEntry) {
        ContentValues values = new ContentValues();
        values.put(DiaryEntry.COLUMN_ENTRY_TITLE, newEntry.getEntryTitle());
        values.put(DiaryEntry.COLUMN_ENTRY_DESCRIPTION, newEntry.getEntryDescription());
        values.put(DiaryEntry.COLUMN_ENTRY_DATE, newEntry.getDate());
        values.put(DiaryEntry.COLUMN_ENTRY_IMAGE, newEntry.getEntryImage());
        long id = database.insert(DiaryEntry.TABLE_NAME, null, values);
        return id;
    }

    public void deleteEntry(int entryId) {

        Log.d("In Delete Entry",String.valueOf(entryId));
      int rows=  database.delete(DiaryEntry.TABLE_NAME
                , DiaryEntry._ID + " = '"+entryId+"'", null);

      Log.d("# rows deleted:",String.valueOf(rows));
    }

    public void updateEntry(int oldEntryId, Entry newEntry) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DiaryEntry.COLUMN_ENTRY_TITLE, newEntry.getEntryTitle());
        values.put(DiaryEntry.COLUMN_ENTRY_DESCRIPTION, newEntry.getEntryDescription());
        values.put(DiaryEntry.COLUMN_ENTRY_DATE, newEntry.getDate());
        values.put(DiaryEntry.COLUMN_ENTRY_IMAGE, newEntry.getEntryImage());

        database.update(DiaryEntry.TABLE_NAME,
                values, DiaryEntry._ID + " = '"+oldEntryId+"'", null);

        HorizontalPagerAdapter.getInstance(context).notifyDataSetChanged();
    }

    public ArrayList<Entry> getAllEntries() {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        Cursor cursor = database.query(DiaryEntry.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Entry temp = new Entry();
                temp.setEntryTitle(cursor.getString(1));
                temp.setEntryDescription(cursor.getString(2));
                temp.setDate(cursor.getString(3));
                temp.setEntryImage(cursor.getString(4));
                temp.setId(cursor.getInt(0));
                Log.d("get data",String.valueOf(cursor.getInt(0)));
                entries.add(temp);
                cursor.moveToNext();
            }


        return entries;
    }

    private Entry cursorToEntry(Cursor cursor) {

        Entry entryToReturn = new Entry();
        entryToReturn.setEntryTitle(cursor.getString(0));
        entryToReturn.setEntryDescription(cursor.getString(1));
        entryToReturn.setDate(cursor.getString(2));
        entryToReturn.setEntryImage(cursor.getString(3));

        return entryToReturn;
    }

    public Cursor selectEntry(String entryID){

        Log.d("selectEntry",entryID);
        return database.query(DiaryEntry.TABLE_NAME, null, "_id = " + entryID,
                null, null, null, null);
    }
}

