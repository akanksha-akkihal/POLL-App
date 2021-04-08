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
                Intent i= new Intent(createPoll.this,PollCount.class);
                startActivity(i);
            }
        });
    }
    private void addPoll(){
        String question_string= question.getText().toString().trim();
        String option1_string= option1.getText().toString().trim();
        String option2_string= option2.getText().toString().trim();

        String userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        pollInfo newPoll=new pollInfo(question_string,option1_string,option2_string,userId);

        FirebaseFirestore.getInstance()
                .collection("questions")
                .add(newPoll)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(createPoll.this,"Poll successfully created",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(createPoll.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

