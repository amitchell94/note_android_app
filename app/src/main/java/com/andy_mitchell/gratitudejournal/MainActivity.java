package com.andy_mitchell.gratitudejournal;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

    private JournalEntryViewModel journalEntryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditJournalEntryActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final JournalEntryAdapter adapter = new JournalEntryAdapter();
        recyclerView.setAdapter(adapter);

        journalEntryViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(JournalEntryViewModel.class);
        journalEntryViewModel.getAllJournalEntries().observe(this, new Observer<List<JournalEntry>>() {
            @Override
            public void onChanged(List<JournalEntry> journalEntries) {
                adapter.submitList(journalEntries);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                journalEntryViewModel.delete(adapter.getJournalEntryAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "JournalEntry deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new JournalEntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(JournalEntry journalEntry) {
                Intent intent = new Intent(MainActivity.this, AddEditJournalEntryActivity.class);

                intent.putExtra(AddEditJournalEntryActivity.EXTRA_ID, journalEntry.getId());
                intent.putExtra(AddEditJournalEntryActivity.EXTRA_BODY, journalEntry.getBody());

                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String body = data.getStringExtra(AddEditJournalEntryActivity.EXTRA_BODY);

            JournalEntry journalEntry = new JournalEntry(body,Calendar.getInstance().getTime());

            journalEntryViewModel.insert(journalEntry);

            Toast.makeText(this, "Journal entry saved", Toast.LENGTH_SHORT);


        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditJournalEntryActivity.EXTRA_ID,-1);

            if (id == -1) {
                Toast.makeText(this, "Journal entry couldn't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(AddEditJournalEntryActivity.EXTRA_BODY);
            Date date = Calendar.getInstance().getTime();
            try {
                date = TimestampConverter.fromTimestamp(data.getStringExtra(AddEditJournalEntryActivity.EXTRA_DATE));
            } catch (Exception e) {
                e.printStackTrace();
            }

            JournalEntry journalEntry = new JournalEntry(title,date);
            journalEntry.setId(id);

            journalEntryViewModel.update(journalEntry);

            Toast.makeText(this, "Journal Entry updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Journal Entry not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                journalEntryViewModel.deleteAllEntries();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
