package com.example.bookstore.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.validators.Validators;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    private EditText fullNameEditText = findViewById(R.id.fullNameEditText);
    private EditText addressEditText = findViewById(R.id.addressEditText);
    private EditText emailEditText = findViewById(R.id.emailEditText);
    private EditText usernameEditText = findViewById(R.id.usernameEditText);
    private EditText passwordEditText = findViewById(R.id.passwordEditText);
    private EditText confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    private void register() {

        String fullName = !isEditTextEmpty(emailEditText) ? emailEditText.getText().toString() : null;
        if (fullName == null) return;
        String address = !isEditTextEmpty(addressEditText) ? addressEditText.getText().toString() : null;
        if (address == null) return;
        String email = !isEditTextEmpty(emailEditText) ? emailEditText.getText().toString() : null;
        if (email == null) return;
        String username = !isEditTextEmpty(usernameEditText) ? usernameEditText.getText().toString() : null;
        if (username == null) return;
        String password = !isEditTextEmpty(passwordEditText) ? passwordEditText.getText().toString() : null;
        if (password == null) return;
        String confirmation = !isEditTextEmpty(confirmPasswordEditText) ? confirmPasswordEditText.getText().toString() : null;
        if (confirmation == null) return;

        if (validateAccountInfo(email, username, password))
            if(Objects.requireNonNull(password).equals(confirmation))
                createUser();


    }

    private void createUser() {
    }

    public void goToLogin(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private boolean isEditTextEmpty(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            emailEditText.setError(editText.getHint() + "is required");
            return true;
        }
        return false;
    }

    public boolean validateAccountInfo(String email, String username, String password) {
        if (!Validators.isValidEmail(email)) {
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validators.isValidUsername(username)) {
            Toast.makeText(this, "Username must consist of 6 to 30 alphanumeric characters and underscores (_).", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validators.isValidPassword(password)) {
            Toast.makeText(this, "Password length must consist of 8 to 15 characters and contain at least one digit, special character, lowercase and uppercase letter and not contain any spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

