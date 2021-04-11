package com.example.pollingapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Objects;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener, PollRecyclerAdapter.PollListener {

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    PollRecyclerAdapter pollRecyclerAdapter;
    String question,option1,option2,docId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Create new POLL", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this,createPoll.class);
                startActivity(i);

            }
        });

    }

    private void startLoginActivity(){
        Intent intent = new Intent(this,LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_logout:
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.action_profile:
//                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(this);
        if(pollRecyclerAdapter!=null){
            pollRecyclerAdapter.stopListening();
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if(firebaseAuth.getCurrentUser()==null){
            startLoginActivity();
            return;
        }
        initRecyclerView(firebaseAuth.getCurrentUser());
    }

    private void initRecyclerView(FirebaseUser user){

        Query query = FirebaseFirestore.getInstance()
                .collection("questions")
                .whereEqualTo("userId",user.getUid());

        FirestoreRecyclerOptions<pollInfo> options=new FirestoreRecyclerOptions.Builder<pollInfo>()
                .setQuery(query, pollInfo.class)
                .build();

        pollRecyclerAdapter = new PollRecyclerAdapter(options , this );
        recyclerView.setAdapter(pollRecyclerAdapter);

        pollRecyclerAdapter.startListening();
        ItemTouchHelper itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    };

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                Toast.makeText(MainActivity.this, "Deleting", Toast.LENGTH_SHORT).show();

                PollRecyclerAdapter.PollViewHolder pollViewHolder = (PollRecyclerAdapter.PollViewHolder) viewHolder;
                pollViewHolder.deleteItem();
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    @Override
    public void handleDeleteItem(DocumentSnapshot snapshot) {
        DocumentReference documentReference=snapshot.getReference();
        pollInfo poll = snapshot.toObject(pollInfo.class);
        snapshot.getReference().delete();

        Snackbar.make(recyclerView,"Poll deleted",Snackbar.LENGTH_LONG)
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        documentReference.set(poll);
                    }
                }).show();

    }

    @Override
    public void openPoll(DocumentSnapshot snapshot) {
        Intent intent=new Intent(this,PollCount.class);

        DocumentReference documentReference=snapshot.getReference();
        pollInfo poll = snapshot.toObject(pollInfo.class);
        question=poll.getQuestion().toString();
        option1=poll.getOption1().toString();
        option2=poll.getOption2().toString();
        docId=poll.getDocId().toString();

        intent.putExtra("question",question);
        intent.putExtra("option1",option1);
        intent.putExtra("option2",option2);
        intent.putExtra("docId",docId);


        startActivity(intent);
    }
    

}

