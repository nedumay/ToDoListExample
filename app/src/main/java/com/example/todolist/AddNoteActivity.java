package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {

    private EditText editTextNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;

    private AddNoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        viewModel = new ViewModelProvider(this).get(AddNoteViewModel.class);
        viewModel.getShoudCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shoud) {
                if(shoud){
                    finish();
                }
            }
        });
        initViews();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String text = editTextNote.getText().toString().trim();
        int priority = getPriority();
        if (text.isEmpty()) {
            Toast.makeText(AddNoteActivity.this, "Пустое поле ввода!", Toast.LENGTH_LONG).show();
        } else {
            Note note = new Note(text,priority);
            viewModel.saveNote(note);
        }
    }

    private int getPriority() {
        int priority;
        if (radioButtonLow.isChecked()) {
            priority = 0;
        } else if (radioButtonMedium.isChecked()){
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        editTextNote = findViewById(R.id.editTextNote);
        radioButtonLow = findViewById(R.id.radioBtnLow);
        radioButtonMedium = findViewById(R.id.radioBtnMedium);
        buttonSave = findViewById(R.id.btnSave);

    }

    public static Intent newIntent(Context context){
        return new Intent(context, AddNoteActivity.class);
    }
}