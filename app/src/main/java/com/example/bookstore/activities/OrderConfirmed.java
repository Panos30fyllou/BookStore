package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.Order;
import com.example.bookstore.models.State;
import com.example.bookstore.models.Store;
import com.example.bookstore.navigators.Navigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderConfirmed extends AppCompatActivity {
    private DatabaseReference database;
    private TextView confirmedTextView;
    private ImageView confirmedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmed);

        if(State.user == null)
            Navigator.goToLogin(this);

        confirmedTextView = findViewById(R.id.confirmedTextView);
        confirmedImageView = findViewById(R.id.confirmedImageView);

        database = FirebaseDatabase.getInstance().getReference().child("Books");

        for (Book book : Cart.getBooks()) {
            database.child(String.valueOf(book.getId())).child("quantity").setValue(Store.getBookById(book.getId()).getQuantity());
        }

        database = FirebaseDatabase.getInstance().getReference().child("Orders");
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long id = snapshot.getChildrenCount();
                database.child(String.valueOf(id)).setValue(new Order(id, State.user.getUsername()));
                Cart.removeAll();
                Store.needsRefresh = true;
                confirmedTextView.setText(getString(R.string.order_confirmed));
                confirmedImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                confirmedTextView.setText(R.string.order_failed);
                confirmedImageView.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToStore(View v) {
        Navigator.goToStore(this);
    }
    public void goToLogin(View v) {
        State.user = null;
        Navigator.goToLogin(this);
    }
}