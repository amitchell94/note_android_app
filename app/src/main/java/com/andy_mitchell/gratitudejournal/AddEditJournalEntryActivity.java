package com.andy_mitchell.gratitudejournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.util.Calendar;

public class AddEditJournalEntryActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.andy_mitchell.gratitudejournal.EXTRA_ID";
    public static final String EXTRA_BODY =
            "com.andy_mitchell.gratitudejournal.EXTRA_BODY";
    public static final String EXTRA_DATE =
            "com.andy_mitchell.gratitudejournal.EXTRA_DATE";

    private EditText editTextBody;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        editTextBody = findViewById(R.id.edit_text_first_item);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID) ) {
            setTitle("Edit Journal Entry");
            editTextBody.setText(intent.getStringExtra(EXTRA_BODY));
        } else {
            setTitle("Add Journal Entry");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveJournalEntry();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void saveJournalEntry() {
        String body = editTextBody.getText().toString();

        if (body.trim().isEmpty()) {
            Toast.makeText(this,"Please enter something you're grateful for",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_BODY,body);
        data.putExtra(EXTRA_DATE,TimestampConverter.dateToTimestamp(Calendar.getInstance().getTime()));

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1) {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();

    }
}
