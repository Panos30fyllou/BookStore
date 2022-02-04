package com.example.bookstore.activities;

import com.example.bookstore.R;
import com.example.bookstore.helpers.TableHelper;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.State;
import com.example.bookstore.models.Store;
import com.example.bookstore.models.User;
import com.example.bookstore.navigators.Navigator;
import com.example.bookstore.validators.Validators;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.bookstore.validators.Validators.isEditTextFilled;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    private ProgressBar progressBar;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        progressBar = findViewById(R.id.progressBarLogin);
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
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DatabaseReference usersTable = FirebaseDatabase.getInstance().getReference().child("Users");
                usersTable.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userDataSnapshot : snapshot.getChildren()) {
                            if(Objects.requireNonNull(userDataSnapshot.child("email").getValue()).toString().equals(email)) {
                                State.user = userDataSnapshot.getValue(User.class);
                                progressBar.setVisibility(View.INVISIBLE);
                                Navigator.goToStore(LoginActivity.this);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else
                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    public boolean validateCredentials(String email, String password) {
        if (!Validators.isValidEmail(email)) {
            Toast.makeText(this, getString(R.string.email_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validators.isValidPassword(password)) {
            Toast.makeText(this, getString(R.string.password_invalid_detailed), Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void goToRegister(View v) {
        Navigator.goToRegister(this);
    }
}