package com.example.bookstore.activities;

import com.example.bookstore.R;
import com.example.bookstore.helpers.BookHelper;
import com.example.bookstore.helpers.TableHelper;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;
import com.example.bookstore.models.State;
import com.example.bookstore.navigators.Navigator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

public class ConfirmOrderActivity extends AppCompatActivity {

    TableLayout bookTableLayout;
    ProgressBar progressBar;
    TextView bottomBarTotalCostTextView;
    EditText orderCommentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        if(State.user == null)
            Navigator.goToLogin(this);

        bookTableLayout = findViewById(R.id.bookOrderTableLayout);
        progressBar = findViewById(R.id.progressBar);
        bottomBarTotalCostTextView = findViewById(R.id.bottomBarTotalCostTextView);
        orderCommentEditText = findViewById(R.id.orderCommentEditText);

        TableHelper.showBooksInTable("Confirm", bookTableLayout, (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), Cart.getBooks(), progressBar, getString(R.string.currency));
        refreshTotalCost();
    }

    public void increaseQuantity(View v) {
        int id = BookHelper.getBookIdFromConfirmRow(v);

        for (Book book : Cart.getBooks()) {
            if (book.getId() == id) {
                if (book.isAvailable()) {
                    Cart.add(book);
                    bookTableLayout.removeAllViews();
                    TableHelper.showBooksInTable("Confirm", bookTableLayout, (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), Cart.getBooks(), progressBar, getString(R.string.currency));
                    refreshTotalCost();
                    break;
                }
            }
        }
    }

    public void decreaseQuantity(View v) {
        int id = BookHelper.getBookIdFromConfirmRow(v);
        for (Book book : Cart.getBooks()) {
            if (book.getId() == id) {
                if (Cart.getQuantity(book) > 0) {
                    Cart.remove(book);
                    bookTableLayout.removeAllViews();
                    TableHelper.showBooksInTable("Confirm", bookTableLayout, (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), Cart.getBooks(), progressBar, getString(R.string.currency));
                    refreshTotalCost();
                    break;
                }
            }
        }
    }

    private void refreshTotalCost() {
        bottomBarTotalCostTextView.setText(new StringBuilder().append(getString(R.string.total_cost)).append(Cart.getTotalCost()).append(getString(R.string.currency)).toString());
    }

    public void goToStore(View v) {
        Navigator.goToStore(this);
    }

    public void goToOrderConfirmed(View v) {
        Cart.setComments(orderCommentEditText.getText().toString());
        Navigator.goToOrderConfirmed(this);
    }
}