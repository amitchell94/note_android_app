package com.andy_mitchell.gratitudejournal;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class JournalEntryAdapter extends ListAdapter<JournalEntry,JournalEntryAdapter.JournalEntryHolder> {
    private OnItemClickListener listener;

    public JournalEntryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<JournalEntry> DIFF_CALLBACK = new DiffUtil.ItemCallback<JournalEntry>() {
        @Override
        public boolean areItemsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull JournalEntry oldItem, @NonNull JournalEntry newItem) {

            return oldItem.getBody().equals(newItem.getBody()) &&
                    ((oldItem.getDate().compareTo(newItem.getDate())) == 0);
        }
    };


    @NonNull
    @Override
    public JournalEntryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.entry_item,viewGroup,false);
        return new JournalEntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalEntryHolder journalEntryHolder, int i) {
        JournalEntry currentJournalEntry = getItem(i);
        journalEntryHolder.textViewBody.setText(currentJournalEntry.getBody());

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yy HH:mm");

        journalEntryHolder.textViewDate.setText(df.format(currentJournalEntry.getDate()));
    }

    public JournalEntry getJournalEntryAt(int position) {
        return getItem(position);
    }

    class JournalEntryHolder extends RecyclerView.ViewHolder {
        private TextView textViewBody;
        private TextView textViewDate;

        public JournalEntryHolder(View itemView) {
            super(itemView);
            textViewBody = itemView.findViewById(R.id.text_view_title);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }
    public interface OnItemClickListener {
        void onItemClick(JournalEntry journalEntry);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        this.listener = listener;
    }
}
