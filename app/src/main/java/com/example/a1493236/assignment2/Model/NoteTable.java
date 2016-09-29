package com.example.a1493236.assignment2.Model;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by 1493236 on 2016-09-22.
 */
public class NoteTable implements CRUDRepository<Long, Note>{

    private SQLiteOpenHelper dbh;
    private static String COLUMN_TITLE = "title";
    private static String COLUMN_BODY = "body";
    private static String COLUMN_CATEGORY = "category";
    private static String COLUMN_HAS_REMINDER = "hasReminder";
    private static String COLUMN_REMINDER = "reminder";
    private static String COLUMN_CREATED = "created";

    public NoteTable(SQLiteOpenHelper dbh){
        this.dbh = dbh;
    }

    public String getCreateTableStatement(){
        return "CREATE TABLE notes (\n" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                COLUMN_TITLE + "TEXT,\n" +
                COLUMN_BODY + "TEXT,\n" +
                COLUMN_CATEGORY + "INTEGER,\n" +
                COLUMN_HAS_REMINDER + "INTEGER,\n" +
                COLUMN_REMINDER + "TEXT,\n" +
                COLUMN_CREATED + "TEXT\n" +
                ");";

    }

    public String getDropTableStatement() {
        return "DROP TABLE IF EXISTS notes";
    }

    @Override
    public Long create(Note element) throws DatabaseException {
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, element.getTitle());
        values.put(COLUMN_BODY, element.getBody());
        values.put(COLUMN_CATEGORY, element.getCategory());
        values.put(COLUMN_HAS_REMINDER, element.isHasReminder());
        values.put(COLUMN_REMINDER, String.valueOf(element.getReminder()));
        values.put(COLUMN_CREATED, String.valueOf(element.getCreated()));

        long insertID = -1;

        // Insert into database
        try{
            insertID = database.insertOrThrow("notes",null, values);
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        finally {
            // close DB no matter what happens
            database.close();
        }

        return insertID;
    }

    @Override

    public Note read(Long key) throws DatabaseException {
        return null;
    }

    @Override
    public List<Note> readAll() throws DatabaseException {
        return null;
    }

    @Override
    public boolean update(Note element) throws DatabaseException {
        return false;
    }

    @Override
    public boolean delete(Note element) throws DatabaseException {
        return false;
    }

    /**
     * Check that the table has initial data.
     * @return
     */
    public boolean hasInitialData() {
        return true;
    }

    /**
     * Populate table with initial data.
     * Precondition: table has been created.
     * Note: this method is called during the handler's onCreate method where a writable database is opne
     *       trying to get a second writable database will throw an error, hence the parameter.
     * @param database
     */
    public void initialize(SQLiteDatabase database) {

    }
}
