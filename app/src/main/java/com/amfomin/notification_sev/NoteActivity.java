package com.amfomin.notification_sev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    Note note;
    int id = -2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHandler db = new DBHandler(this);
        setContentView(R.layout.activity_note);

        if (id == -2) {
            id = getIntent().getIntExtra("KEY_ID", 0);
            if (id != -1) {
                note = db.getNote(id);
            }
            else {
                note = new Note();
                Button b = findViewById(R.id.del_but);
                b.setVisibility(View.GONE);
            }
            TextView tw = findViewById(R.id.note_name);
            tw.setText(note.getName());
            tw = findViewById(R.id.note_date);
            tw.setText(note.getDate());

            
            EditText editText = findViewById(R.id.scroll_edit_text);
            editText.setText(note.getContent());
        }


        Button button = findViewById(R.id.save_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eew = findViewById(R.id.scroll_edit_text);

                String content = eew.getText().toString();

                eew = findViewById(R.id.note_name);
                String name = eew.getText().toString();

                if (!content.isEmpty()) {
                    note.setName(name);
                    note.setContent(content);
                    if (note.getId() == -1) db.addNote(note);
                    else db.updateNote(note);
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(v.getContext(), "В заметке пусто!\nСохранение невозможно",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        button = findViewById(R.id.del_but);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteNote(note.getId());
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}