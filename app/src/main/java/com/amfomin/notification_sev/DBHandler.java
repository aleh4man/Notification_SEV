package com.amfomin.notification_sev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.amfomin.notification_sev.Utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {


    public DBHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_NAME + " TEXT NOT NULL, "
                + Util.KEY_CONTENT + " TEXT NOT NULL, "
                + Util.KEY_DATE_CREATE + " DATE, "
                + Util.KEY_DATE_UPDATE + " DATE)";
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addNote(Note n) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Util.KEY_NAME, n.getName());
        cv.put(Util.KEY_CONTENT, n.getContent());
        cv.put(Util.KEY_DATE_CREATE, n.toSQLDate());
        db.insert(Util.TABLE_NAME, null, cv);
        db.close();
    }

    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_CONTENT, Util.KEY_DATE_CREATE},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Note n = new Note(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3));
        return n;
    }

    public void updateNote(Note note) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Util.KEY_NAME, note.getName());
        cv.put(Util.KEY_CONTENT, note.getContent());

        sdb.update(Util.TABLE_NAME, cv, Util.KEY_ID + "=?", new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?", new String[]{String.valueOf(id)});
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Util.TABLE_NAME, null, null);
    }

    public ArrayList<Note> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME, null);
        if (c != null) {
            c.moveToFirst();
        }

        ArrayList<Note> notes = new ArrayList<>();
        while (c.moveToNext()) {
            Note n = new Note(Integer.parseInt(c.getString(0)), c.getString(1), c.getString(2), c.getString(3));
            notes.add(n);
        }
        db.close();
        return notes;
    }

    public boolean isEmpty(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME, null);

        int counter = 0;
        c.moveToFirst();
        while (c.moveToNext() && counter <= 0) ++counter;
        return counter == 0;
    }
}