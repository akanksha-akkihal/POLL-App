package com.example.pollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import io.grpc.internal.LogExceptionRunnable;

public class ProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextInputEditText displayNameEditText;
    Button updateProfileButton;
    ProgressBar progressBar;

    String DISPLAY_NAME = null;
    String PROFILE_IMG_URL = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profilePic=findViewById(R.id.imageView2);
        displayNameEditText=findViewById(R.id.displayNameEditText);
        updateProfileButton=findViewById(R.id.updateProfileButton);
        progressBar=findViewById(R.id.progressBar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(user.getDisplayName()!= null){
                displayNameEditText.setText(user.getDisplayName());
                displayNameEditText.setSelection(user.getDisplayName().length());
            }
        }
        progressBar.setVisibility(View.GONE);
    }

    public void updateProfile(View view) {

        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        DISPLAY_NAME = displayNameEditText.getText().toString();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(DISPLAY_NAME)
                .build();

        firebaseUser.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        view.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this,"Successfully updated profile",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        view.setEnabled(true);
                    }
                });
    }
}