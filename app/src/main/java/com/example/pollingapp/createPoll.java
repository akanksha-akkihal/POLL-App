package com.example.pollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Timestamp;
import java.security.cert.CertPath;
import java.util.Date;
import java.util.Random;

public class createPoll extends AppCompatActivity {
    Button create;
    EditText question,option1,option2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        question= (EditText) findViewById(R.id.question);
        option1 = (EditText) findViewById(R.id.option1);
        option2 = (EditText) findViewById(R.id.option2);
        create = (Button) findViewById(R.id.create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPoll();
                Intent i= new Intent(createPoll.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void addPoll(){
        String question_string= question.getText().toString().trim();
        String option1_string= option1.getText().toString().trim();
        String option2_string= option2.getText().toString().trim();

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        Random rand = new Random();
        int key = rand.nextInt((9999 - 100) + 1) + 10;
        String docId=Integer.toString(key);
        pollInfo newPoll=new pollInfo(question_string,option1_string,option2_string,userId,docId,0,0);

        FirebaseFirestore.getInstance()
                .collection("questions").document(docId)
                .set(newPoll)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(createPoll.this,"Poll successfully created",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error writing document", e);
                        Toast.makeText(createPoll.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

