package com.andy_mitchell.gratitudejournal;


import android.app.Application;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class JournalEntryRepository {

    private JournalEntryDao journalEntryDao;

    private LiveData<List<JournalEntry>> allJournalEntries;

    public JournalEntryRepository(Application application) {
        JournalEntryDatabase database = JournalEntryDatabase.getInstance(application);
        journalEntryDao = database.journalEntryDao();
        allJournalEntries = journalEntryDao.getAllJournalEntries();
    }

    public void insert(JournalEntry journalEntry) {
        new InsertJournalEntryAsyncTask(journalEntryDao).execute(journalEntry);
    }

    public void update(JournalEntry journalEntry) {
        new UpdateJournalEntryAsyncTask(journalEntryDao).execute(journalEntry);
    }

    public void delete(JournalEntry journalEntry)
    {
        new DeleteJournalEntryAsyncTask(journalEntryDao).execute(journalEntry);
    }

    public void deleteAllEntries() {
        new DeleteAllEntriesJournalEntryAsyncTask(journalEntryDao).execute();
    }

    public LiveData<List<JournalEntry>> getAllJournalEntries() {
        return allJournalEntries;
    }

    private static class InsertJournalEntryAsyncTask extends AsyncTask<JournalEntry,Void,Void> {
        private JournalEntryDao journalEntryDao;

        private InsertJournalEntryAsyncTask(JournalEntryDao journalEntryDao) {
            this.journalEntryDao = journalEntryDao;
        }

        @Override
        protected Void doInBackground(JournalEntry... journalEntries) {
            journalEntryDao.insert(journalEntries[0]);
            return null;
        }
    }

    private static class UpdateJournalEntryAsyncTask extends AsyncTask<JournalEntry,Void,Void> {
        private JournalEntryDao journalEntryDao;

        private UpdateJournalEntryAsyncTask(JournalEntryDao journalEntryDao) {
            this.journalEntryDao = journalEntryDao;
        }

        @Override
        protected Void doInBackground(JournalEntry... journalEntries) {
            journalEntryDao.update(journalEntries[0]);
            return null;
        }
    }

    private static class DeleteJournalEntryAsyncTask extends AsyncTask<JournalEntry,Void,Void> {
        private JournalEntryDao journalEntryDao;

        private DeleteJournalEntryAsyncTask(JournalEntryDao journalEntryDao) {
            this.journalEntryDao = journalEntryDao;
        }

        @Override
        protected Void doInBackground(JournalEntry... journalEntries) {
            journalEntryDao.delete(journalEntries[0]);
            return null;
        }
    }

    private static class DeleteAllEntriesJournalEntryAsyncTask extends AsyncTask<Void,Void,Void> {
        private JournalEntryDao journalEntryDao;

        private DeleteAllEntriesJournalEntryAsyncTask(JournalEntryDao journalEntryDao) {
            this.journalEntryDao = journalEntryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journalEntryDao.deleteAllEntries();
            return null;
        }
    }
}
