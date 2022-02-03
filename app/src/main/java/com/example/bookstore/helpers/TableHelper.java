package com.example.bookstore.helpers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.bookstore.R;
import com.example.bookstore.models.Book;
import com.example.bookstore.models.Cart;

import java.util.ArrayList;

public class TableHelper {
    public static void showBooksInTable(String type, TableLayout bookTableLayout, LayoutInflater inflater, ArrayList<Book> books, ProgressBar progressBar, String currency) {
        ArrayList<Integer> booksInTable = new ArrayList<>();

        for (Book book : books) {
            if (!booksInTable.contains(book.getId())) {
                TableRow rowView = null;
                if (type.equals("Store"))
                    rowView = (TableRow) inflater.inflate(R.layout.book_store_row, null);
                else if (type.equals("Confirm"))
                    rowView = (TableRow) inflater.inflate(R.layout.book_order_row, null);

                TableRow divider = (TableRow) inflater.inflate(R.layout.divider, null);
                divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));

                ImageView bookImageView = rowView.findViewById(R.id.bookImageView);
                bookImageView.setAdjustViewBounds(true);
                bookImageView.setMaxHeight(300);
                bookImageView.setMaxWidth(300);
                book.convertBase64ToBitmap();
                bookImageView.setImageBitmap(book.getImageBitmap());

                if (type.equals("Store")) {
                    ImageView addToCartButton = rowView.findViewById(R.id.addToCartButton);
                    addToCartButton.setVisibility(book.isAvailable() ? View.VISIBLE : View.INVISIBLE);
                }

                TextView bookNameTextView = rowView.findViewById(R.id.bookNameTextView);
                bookNameTextView.setText(book.getName());

                TextView authorTextView = rowView.findViewById(R.id.authorTextView);
                authorTextView.setText(book.getAuthor());

                TextView bookPriceTextView = rowView.findViewById(R.id.bookPriceTextView);
                bookPriceTextView.setText(new StringBuilder().append(book.getPrice()).append(currency).toString());

                TextView quantityTextView = rowView.findViewById(R.id.quantityTextView);
                quantityTextView.setText(String.valueOf(book.getQuantity()));
                if (type.equals("Confirm")) {
                    TextView quantityInCartTextView = rowView.findViewById(R.id.quantityInCartTextView);
                    quantityInCartTextView.setText(String.valueOf(Cart.getQuantity(book)));
                }
                rowView.setId((book.getId()));

                bookTableLayout.addView(rowView);
                bookTableLayout.addView(divider);
                booksInTable.add(book.getId());
            }
        }

        progressBar.setVisibility(View.INVISIBLE);
    }
}
