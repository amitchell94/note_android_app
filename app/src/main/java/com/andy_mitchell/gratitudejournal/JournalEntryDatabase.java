package com.andy_mitchell.gratitudejournal;

import android.content.Context;
import android.os.AsyncTask;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = JournalEntry.class, version = 1)
public abstract class JournalEntryDatabase extends RoomDatabase {

    private static JournalEntryDatabase instance;

    public abstract JournalEntryDao journalEntryDao();

    public static synchronized JournalEntryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    JournalEntryDatabase.class, "journal_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private JournalEntryDao journalEntryDao;

        private PopulateDbAsyncTask(JournalEntryDatabase db) {
            journalEntryDao = db.journalEntryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            journalEntryDao.insert(new JournalEntry("title1", Calendar.getInstance().getTime()));
            journalEntryDao.insert(new JournalEntry("title2",Calendar.getInstance().getTime()));
            journalEntryDao.insert(new JournalEntry("title3",Calendar.getInstance().getTime()));
            return null;
        }
    }
}
