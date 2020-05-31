package com.andy_mitchell.gratitudejournal;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "journal_entry_table")
public class JournalEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String body;
    @TypeConverters({TimestampConverter.class})
    private Date date;

    public JournalEntry(String body, Date date) {
        this.body = body;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getDate() {
        return date;
    }
}
