package com.example.bookstore.activities;

import com.example.bookstore.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordEditText);

        Button loginButton = findViewById(R.id.loginButton);

    }

    public void Login(){

    }
    public void goToRegister(View v){
        startActivity(new Intent(this, RegisterActivity.class));
    }
}