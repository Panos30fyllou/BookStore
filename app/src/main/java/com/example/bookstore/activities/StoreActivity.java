package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.helpers.BookHelper;
import com.example.bookstore.helpers.TableHelper;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.State;
import com.example.bookstore.models.Store;
import com.example.bookstore.navigators.Navigator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StoreActivity extends AppCompatActivity {
    private DatabaseReference booksTable;
    private TableLayout bookTableLayout;
    private ProgressBar progressBar;
    private TextView totalCostTextView;
    private TextView booksInCartTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        if(State.user == null)
            Navigator.goToLogin(this);

        booksInCartTextView = findViewById(R.id.booksInCartTextView);
        booksInCartTextView.setText(String.valueOf(Cart.getSize()));
        bookTableLayout = findViewById(R.id.bookTableLayout);
        progressBar = findViewById(R.id.progressBar);
        totalCostTextView = findViewById(R.id.bottomBarTotalCostTextView);

        totalCostTextView.setText(new StringBuilder().append(getString(R.string.total_cost)).append(Cart.getTotalCost()).append(getString(R.string.currency)).toString());

        booksTable = FirebaseDatabase.getInstance().getReference().child("Books");
        booksTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (Store.needsRefresh) {
                    progressBar.setVisibility(View.VISIBLE);
                    Store.clear();
                    for (DataSnapshot bookDataSnapshot : snapshot.getChildren()) {
                        Store.add(bookDataSnapshot.getValue(Book.class));
                    }
                    Store.needsRefresh = false;
                }
                TableHelper.showBooksInTable("Store", bookTableLayout, (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), Store.getBooks(), progressBar, getString(R.string.currency));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void addBook(View v) {
        int id = BookHelper.getBookIdFromStoreRow(v);
        ImageView plusButton = (ImageView) v;

        for (Book book : Store.getBooks()) {
            if (book.getId() == id) {
                if (book.isAvailable()) {
                    Cart.add(book);
                    Toast.makeText(getBaseContext(), "1 copy of " + book.getName() + " is added to cart", Toast.LENGTH_SHORT).show();
                    totalCostTextView.setText(new StringBuilder().append(getString(R.string.total_cost)).append(Cart.getTotalCost()).append(getString(R.string.currency)).toString());
                    booksInCartTextView.setText(String.valueOf(Cart.getSize()));
                    bookTableLayout.removeAllViews();
                    TableHelper.showBooksInTable("Store", bookTableLayout, (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), Store.getBooks(), progressBar, getString(R.string.currency));
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.book_unavailable), Toast.LENGTH_SHORT).show();
                    plusButton.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void goToConfirmOrder(View v) {
        if(Cart.getSize() > 0)
            Navigator.goToConfirmOrder(this);
        else
            Toast.makeText(getBaseContext(), getString(R.string.add_books_to_cart_message), Toast.LENGTH_SHORT).show();
    }
}
