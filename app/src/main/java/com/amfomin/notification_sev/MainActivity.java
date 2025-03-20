package com.amfomin.notification_sev;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);

        if (db.isEmpty()) db.addNote(new Note());
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.create);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteActivity.class);
                intent.putExtra("KEY_ID", -1);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        ArrayList<Note> notes = db.getAllNotes();
        GridLayout gl = findViewById(R.id.rel_grid);
        gl.removeAllViews();
        for (Note n : notes) {
            Log.d("note", n.toString());
            createNote(n.getId(), n.getName(), n.getDate());
        }
    }

    public void createNote(int id, String name, String date) {
        ContextThemeWrapper cw = new ContextThemeWrapper(this, R.style.info);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                convertToSp(60f));
        lp.gravity = LinearLayout.LayoutParams.FILL_PARENT;
        lp.setMargins(convertToSp(2f), convertToSp(2f), convertToSp(2f), convertToSp(2f));

        LinearLayout infol = new LinearLayout(cw);
        infol.setLayoutParams(lp);


        cw = new ContextThemeWrapper(this, R.style.text);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 3;
        lp.setMargins(convertToSp(5f), convertToSp(5f), convertToSp(5f), convertToSp(5f));

        LinearLayout textl = new LinearLayout(cw);
        textl.setLayoutParams(lp);


        cw = new ContextThemeWrapper(this, R.style.name);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 3;

        TextView tv = new TextView(cw);
        tv.setText(name);
        tv.setLayoutParams(lp);
        textl.addView(tv);


        cw = new ContextThemeWrapper(this, R.style.date);
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        tv = new TextView(cw);
        tv.setText(date);
        tv.setLayoutParams(lp);
        textl.addView(tv);

        infol.addView(textl);


        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        cw = new ContextThemeWrapper(this, R.style.update_button);
        lp.setMargins(convertToSp(11f), convertToSp(9f), convertToSp(11f), convertToSp(9f));
        Button bt = new Button(cw);
        bt.setId(id);
        bt.setLayoutParams(lp);
        bt.setText("смотреть");
        bt.setBackgroundColor(getResources().getColor(R.color.yellow));
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NoteActivity.class);
                intent.putExtra("KEY_ID", bt.getId());
                startActivity(intent);
            }
        });

        infol.addView(bt);


        GridLayout gl = findViewById(R.id.rel_grid);
        gl.addView(infol);
    }

    private int convertToSp(float sp) {
        return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}