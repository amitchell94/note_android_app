package com.andy_mitchell.gratitudejournal;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface JournalEntryDao {

    @Insert
    void insert(JournalEntry journalEntry);

    @Update
    void update(JournalEntry journalEntry);

    @Delete
    void delete(JournalEntry journalEntry);

    @Query("DELETE FROM journal_entry_table")
    void deleteAllEntries();

    @Query("SELECT * FROM journal_entry_table ORDER BY date DESC")
    LiveData<List<JournalEntry>> getAllJournalEntries();
}
