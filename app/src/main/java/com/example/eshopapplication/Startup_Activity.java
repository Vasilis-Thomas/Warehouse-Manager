package com.example.eshopapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import signuploginfirebase.sharedPreferenceConfig;

public class Startup_Activity extends AppCompatActivity {

    private signuploginfirebase.sharedPreferenceConfig sharedPreferenceConfig;
    Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        sharedPreferenceConfig = new sharedPreferenceConfig(getApplicationContext());
        if (sharedPreferenceConfig.readLoginStatus()){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        loginButton = findViewById(R.id.startup_login_button);
        signupButton = findViewById(R.id.startup_signup_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startup_Activity.this, signuploginfirebase.LoginActivity.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startup_Activity.this, signuploginfirebase.SignUp_Activity.class));
            }
        });


    }
}