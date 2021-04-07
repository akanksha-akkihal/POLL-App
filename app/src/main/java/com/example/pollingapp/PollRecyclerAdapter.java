package com.example.pollingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PollRecyclerAdapter extends FirestoreRecyclerAdapter<pollInfo, PollRecyclerAdapter.PollViewHolder> {

    public PollRecyclerAdapter(@NonNull FirestoreRecyclerOptions<pollInfo> options) {
        super(options);
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
        return new PollViewHolder(view);

    }

    class PollViewHolder extends RecyclerView.ViewHolder{

        TextView listRow;
        public PollViewHolder(@NonNull View itemView) {
            super(itemView);
            listRow = itemView.findViewById(R.id.listRow);
        }
    }
}
