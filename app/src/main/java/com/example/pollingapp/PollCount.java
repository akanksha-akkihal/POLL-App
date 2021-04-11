package com.example.pollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PollCount extends AppCompatActivity {
    TextView question;
    RadioButton option1;
    RadioButton option2;
    Button submit;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_count);

        question = (TextView) findViewById(R.id.ques);
        option1 = (RadioButton) findViewById(R.id.opt1);
        option2 = (RadioButton) findViewById(R.id.opt2);
        submit = (Button) findViewById(R.id.submit);

        Intent i = getIntent();
        String ques = i.getStringExtra("question");
        String opt1 = i.getStringExtra("option1");
        String opt2 = i.getStringExtra("option2");
        //key = i.getIntExtra("key",0);
        docId=i.getStringExtra("docId");

        question.setText(ques);
        option1.setText(opt1);
        option2.setText(opt2);


    }

    public void onRadioButtonClicked(View view) {

        DocumentReference doc=FirebaseFirestore.getInstance().collection("questions")
                .document(docId);

        if(option1.isChecked()){
            doc.update("count1", FieldValue.increment(1));
            Toast.makeText(this, "voted for opt1"+docId, Toast.LENGTH_LONG).show();
        }else if(option2.isChecked()){
            doc.update("count2", FieldValue.increment(1));
            Toast.makeText(this, "voted for opt2", Toast.LENGTH_LONG).show();
        }

    }

    public void shareCode(View view){
        String code = "Enter code to take poll - "+docId;
        String mimeType= "text/plain";
        ShareCompat.IntentBuilder.from(PollCount.this)
                .setType(mimeType).setText(code).startChooser();
    }

    public void viewResults(View view){
        DocumentReference doc= FirebaseFirestore.getInstance().collection("questions")
                .document(docId);
        //Toast.makeText(getApplicationContext(), docId, Toast.LENGTH_LONG).show();
        doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        String opt1=document.getString("option1");
                        String opt2=document.getString("option2");
                        int count1=document.getLong("count1").intValue();
                        int count2=document.getLong("count2").intValue();

                        Intent i = new Intent(PollCount.this,Result.class);
                        i.putExtra("count1", count1);
                        i.putExtra("count2", count2);
                        i.putExtra("opt1", opt1);
                        i.putExtra("opt2", opt2);
                        startActivity(i);

                    } else {
                        //Log.d(TAG, "No such document");
                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
}