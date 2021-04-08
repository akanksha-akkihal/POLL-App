package com.example.pollingapp;

import android.content.Intent;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class PollRecyclerAdapter extends FirestoreRecyclerAdapter<pollInfo, PollRecyclerAdapter.PollViewHolder> {

    PollListener pollListener;
    public PollRecyclerAdapter(@NonNull FirestoreRecyclerOptions<pollInfo> options , PollListener pollListener) {
        super(options);
        this.pollListener=pollListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PollViewHolder holder, int position, @NonNull pollInfo model) {
        holder.listRow.setText(model.getQuestion());

    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_row,parent,false);
        return new PollViewHolder(view, pollListener);

    }

    class PollViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView listRow;
        PollListener pollListener1;
        public PollViewHolder(@NonNull View itemView, PollListener pollListener1) {
            super(itemView);
            listRow = itemView.findViewById(R.id.listRow);
            this.pollListener1=pollListener1;
            itemView.setOnClickListener(this);

        }

        public void deleteItem(){
            pollListener.handleDeleteItem(getSnapshots().getSnapshot(getAdapterPosition()));
        }

        @Override
        public void onClick(View v) {
            pollListener1.openPoll(getSnapshots().getSnapshot(getAdapterPosition()));
        }
    }

    interface PollListener{
        public void handleDeleteItem(DocumentSnapshot snapshot);
        public void openPoll(DocumentSnapshot snapshot);
    }
}
