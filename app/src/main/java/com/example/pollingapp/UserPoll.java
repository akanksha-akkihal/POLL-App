package com.example.pollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserPoll extends AppCompatActivity {
    TextView question;
    RadioButton option1;
    RadioButton option2;
    Button submit;
    String docId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_poll);

        question = (TextView) findViewById(R.id.uques);
        option1 = (RadioButton) findViewById(R.id.uopt1);
        option2 = (RadioButton) findViewById(R.id.uopt2);
        submit = (Button) findViewById(R.id.usubmit);

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

        DocumentReference doc = FirebaseFirestore.getInstance().collection("questions")
                .document(docId);

        if (option1.isChecked()) {
            doc.update("count1", FieldValue.increment(1));
            Toast.makeText(this, "voted for opt1" , Toast.LENGTH_LONG).show();
        } else if (option2.isChecked()) {
            doc.update("count2", FieldValue.increment(1));
            Toast.makeText(this, "voted for opt2", Toast.LENGTH_LONG).show();
        }
    }
}