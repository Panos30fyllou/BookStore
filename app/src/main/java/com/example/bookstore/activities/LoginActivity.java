package com.example.bookstore.activities;

import com.example.bookstore.R;
import com.example.bookstore.navigators.Navigator;
import com.example.bookstore.validators.Validators;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.bookstore.validators.Validators.isEditTextFilled;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

    }

    public void loginClicked(View v) {

        String email = isEditTextFilled(emailEditText) ? emailEditText.getText().toString() : null;
        if (email == null) return;
        String password = isEditTextFilled(passwordEditText) ? passwordEditText.getText().toString() : null;
        if (password == null) return;

        if (validateCredentials(email, password))
            firebaseLogin(email, password);
    }

    private void firebaseLogin(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                    Navigator.goToStore(LoginActivity.this);
                 else
                    Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean validateCredentials(String email, String password) {
        if (!Validators.isValidEmail(email)) {
            Toast.makeText(this, "Email is invalid", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validators.isValidPassword(password)) {
            Toast.makeText(this, "Password length must consist of 8 to 15 characters and contain at least one digit, special character, lowercase and uppercase letter and not contain any spaces", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void goToRegister(View v) {
        Navigator.goToRegister(this);
    }
}