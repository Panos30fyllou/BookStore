package com.example.bookstore.activities;

import static com.example.bookstore.validators.Validators.isEditTextFilled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookstore.R;
import com.example.bookstore.models.User;
import com.example.bookstore.navigators.Navigator;
import com.example.bookstore.validators.Validators;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText addressEditText;
    private EditText emailEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;

    ArrayList<EditText> registerFormEditTexts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerFormEditTexts = new ArrayList<>();

        fullNameEditText = findViewById(R.id.fullNameEditText);
        addressEditText = findViewById(R.id.addressEditText);
        emailEditText = findViewById(R.id.emailEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        registerFormEditTexts.add(fullNameEditText);
        registerFormEditTexts.add(addressEditText);
        registerFormEditTexts.add(emailEditText);
        registerFormEditTexts.add(usernameEditText);
        registerFormEditTexts.add(passwordEditText);
        registerFormEditTexts.add(confirmPasswordEditText);

        for (EditText editText : registerFormEditTexts) {
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b && isEditTextFilled((EditText) view)) {
                        if (view.getId() == emailEditText.getId()) {
                            if (!Validators.isValidEmail(emailEditText.getText().toString())) {
                                emailEditText.setError("Email is invalid");
                                Toast.makeText(RegisterActivity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                            }
                        } else if (view.getId() == usernameEditText.getId()) {
                            if (!Validators.isValidUsername(usernameEditText.getText().toString())) {
                                usernameEditText.setError("Invalid username form");
                                Toast.makeText(RegisterActivity.this, "Username must consist of 6 to 30 alphanumeric characters and underscores (_).", Toast.LENGTH_SHORT).show();
                            }
                        } else if (view.getId() == passwordEditText.getId()) {
                            if (!Validators.isValidPassword(passwordEditText.getText().toString())) {
                                passwordEditText.setError("Invalid password form");
                                Toast.makeText(RegisterActivity.this, "Password length must consist of 8 to 15 characters and contain at least one digit and not contain any spaces", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });

        }

    }

    public void registerClicked(View v) {
        String fullName = isEditTextFilled(fullNameEditText) ? fullNameEditText.getText().toString() : null;
        if (fullName == null) return;
        String address = isEditTextFilled(addressEditText) ? addressEditText.getText().toString() : null;
        if (address == null) return;
        String email = isEditTextFilled(emailEditText) ? emailEditText.getText().toString() : null;
        if (email == null) return;
        String username = isEditTextFilled(usernameEditText) ? usernameEditText.getText().toString() : null;
        if (username == null) return;
        String password = isEditTextFilled(passwordEditText) ? passwordEditText.getText().toString() : null;
        if (password == null) return;
        String confirmation = isEditTextFilled(confirmPasswordEditText) ? confirmPasswordEditText.getText().toString() : null;
        if (confirmation == null) return;

        if (validateAccountInfo(email, username, password)) {
            if (Objects.requireNonNull(password).equals(confirmation))
                createUserInFirebase(new User(username, email, fullName, address), password);
            else {
                confirmPasswordEditText.setError("Passwords don't match");
                confirmPasswordEditText.requestFocus();
            }
        }
    }

    private void createUserInFirebase(User user, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseDatabase.getInstance().getReference("Users").child(user.getUsername()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Navigator.goToLogin(RegisterActivity.this);
                            Toast.makeText(RegisterActivity.this, task.isSuccessful() ? "User has been registered" : "Registration failed 1", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else
                    Toast.makeText(RegisterActivity.this, Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void goToLogin(View v) {
        Navigator.goToLogin(this);
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