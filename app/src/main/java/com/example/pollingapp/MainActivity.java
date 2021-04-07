package com.example.pollingapp;

import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener{

    private static final String TAG = "MainActivity";
    RecyclerView recyclerView;
    PollRecyclerAdapter pollRecyclerAdapter;
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
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
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

        pollRecyclerAdapter = new PollRecyclerAdapter(options);
        recyclerView.setAdapter(pollRecyclerAdapter);

        pollRecyclerAdapter.startListening();


    }
}