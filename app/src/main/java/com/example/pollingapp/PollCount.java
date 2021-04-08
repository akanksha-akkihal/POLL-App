package com.example.pollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

public class PollCount extends AppCompatActivity {
    TextView question;
    RadioButton option1;
    RadioButton option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll_count);

        question = (TextView)findViewById(R.id.ques);
        option1 = (RadioButton)findViewById(R.id.opt1);
        option2 = (RadioButton)findViewById(R.id.opt2);

        Intent i = getIntent();
        String ques = i.getStringExtra("question");
        String opt1 = i.getStringExtra("option1");
        String opt2 = i.getStringExtra("option2");

        question.setText(ques);
        option1.setText(opt1);
        option2.setText(opt2);


    }


}