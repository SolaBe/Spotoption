package com.sola.spotoption.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sola.spotoption.models.CountryEntry;
import com.sola.spotoption.models.IEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sola2Be on 13.07.2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Countries";
    private static final String COL_NAME = "Name";
    private static final String COL_CHECK = "Checked";
    private static final String QUERY_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ( "+ COL_NAME +" TEXT, "+ COL_CHECK +" INTEGER )";
    public static int CUR_VERSION = 1;

    public DBHelper(Context context, String name,
            SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<IEntry> getData() {

        CountryEntry entry;
        List<IEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    entry = new CountryEntry(
                            cursor.getString(cursor.getColumnIndex(COL_NAME)),
                            cursor.getInt(cursor.getColumnIndex(COL_CHECK)) != 0);
                    entries.add(entry);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return entries;
    }

    public void fillData(List<String> countries) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        for (String country :
                countries) {
            ContentValues val = new ContentValues();
            val.put(COL_NAME, country);
            db.insert(TABLE_NAME, null, val);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    public void updateRow(String name, int cheched) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues val = new ContentValues();
        val.put(COL_CHECK, cheched);
        db.update(TABLE_NAME, val, COL_NAME + " LIKE ?", new String[] {name});

    }
}

