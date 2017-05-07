package com.example.absolutelysaurabh.notemaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.HashSet;

public class EditYourNote extends Activity implements TextWatcher {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_your_note);

        EditText editText = (EditText) findViewById(R.id.editText);

        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if (noteId != -1) {

            editText.setText(ProfileActivity.notes.get(noteId));


        }

        editText.addTextChangedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_your_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        ProfileActivity.notes.set(noteId, String.valueOf(s));
        ProfileActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.robpercival.notes", Context.MODE_PRIVATE);

        if (ProfileActivity.set == null) {

            ProfileActivity.set = new HashSet<String>();

        } else {

            ProfileActivity.set.clear();

        }


        ProfileActivity.set.addAll(ProfileActivity.notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes", ProfileActivity.set).apply();


    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
