package com.andy_mitchell.gratitudejournal;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class JournalEntryViewModel extends AndroidViewModel {
    private JournalEntryRepository repository;
    private LiveData<List<JournalEntry>> allJournalEntries;

    public JournalEntryViewModel(@NonNull Application application) {
        super(application);
        repository = new JournalEntryRepository(application);
        allJournalEntries = repository.getAllJournalEntries();
    }

    public void insert(JournalEntry journalEntry) {
        repository.insert(journalEntry);
    }
    public void update(JournalEntry journalEntry) {
        repository.update(journalEntry);
    }
    public void delete(JournalEntry journalEntry) {
        repository.delete(journalEntry);
    }
    public void deleteAllEntries() {
        repository.deleteAllEntries();
    }
    public LiveData<List<JournalEntry>> getAllJournalEntries() {
        return repository.getAllJournalEntries();
    }


}
