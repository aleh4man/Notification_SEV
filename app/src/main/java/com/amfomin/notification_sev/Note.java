package com.amfomin.notification_sev;

import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Note {
    private int id;
    private String name;
    private String content;
    private String date;

    public Note(int id, String n, String c, String d) {
        this.id = id;
        name = new String(n);
        content = new String(c);
        setDate(d);
    }

    public Note(){
        this.id = -1;
        name = "Новая заметка";
        content = "";
        date = new SimpleDateFormat("dd.MM.yyyy",
                Locale.getDefault()).format(System.currentTimeMillis());
    }

    public void setId(int id) {this.id = id;}
    public void setName(String n) {name = new String(n);}
    public void setContent(String c) {content = new String(c);}
    public void setDate(String d) {
        date = new String(d.substring(8) + '.' + d.substring(5, 7) + '.' + d.substring(0, 4));
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public String getContent() {return content;}
    public String getDate() {return date;}

    public String toString(){
        return "#" + id + " " + name + " " + content +  " " + date;
    }
    public String toSQLDate(){
        return new String(date.substring(6) + '/' + date.substring(3, 5) + '/' +
                date.substring(0, 2));
    }
}
