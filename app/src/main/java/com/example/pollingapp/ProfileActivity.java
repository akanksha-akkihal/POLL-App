package com.example.pollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextInputEditText displayNameEditText;
    Button updateProfileButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePic=findViewById(R.id.imageView2);
        displayNameEditText=findViewById(R.id.displayNameEditText);
        updateProfileButton=findViewById(R.id.updateProfileButton);
        progressBar=findViewById(R.id.progressBar);
    }

    public void updateProfile(View view) {
    }
}