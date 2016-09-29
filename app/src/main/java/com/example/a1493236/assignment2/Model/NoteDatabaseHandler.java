package com.example.a1493236.assignment2.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 1493236 on 2016-09-22.
 */
public class NoteDatabaseHandler extends SQLiteOpenHelper{

    // Filename to store local database
    private static final String DATABASE_FILE_NAME = "notes.db";

    // Update this field for every structural change to the database
    private static final int DATABASE_VERSION = 2;

    private NoteTable noteTable;

    public NoteDatabaseHandler(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        noteTable = new NoteTable(this);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(noteTable.getCreateTableStatement());

        // Populate tables as needed
        if(noteTable.hasInitialData()) {
            noteTable.initialize(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DATABASE_FILE_NAME, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");

        // drop tables
        db.execSQL(noteTable.getDropTableStatement());

        // recreate DB
        onCreate(db);
    }

    public NoteTable getNoteTable(){
        return noteTable;
    }
}
