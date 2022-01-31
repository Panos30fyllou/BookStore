package com.example.bookstore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.R;
import com.example.bookstore.models.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    private DatabaseReference booksTable;

    ArrayList<Book> books;
    TableLayout ll;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        books = new ArrayList<>();
        ll = findViewById(R.id.displayLinear);
        progressBar = findViewById(R.id.progressBar);

        booksTable = FirebaseDatabase.getInstance().getReference().child("Books");
        booksTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                for (DataSnapshot bookDataSnapshot : snapshot.getChildren()) {
                    Book book = bookDataSnapshot.getValue(Book.class);
                    books.add(bookDataSnapshot.getValue(Book.class));
                    assert book != null;
                    Toast.makeText(getBaseContext(), book.getImageBase64(), Toast.LENGTH_SHORT).show();


                }
                showBooks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showBooks(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for (Book book : books) {
            TableRow rowView = (TableRow)inflater.inflate(R.layout.book_row, null);
            TableRow divider = (TableRow)inflater.inflate(R.layout.divider, null);
            divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT));


            ImageView bookImageView = rowView.findViewById(R.id.bookImageView);
            bookImageView.setAdjustViewBounds(true);
            bookImageView.setMaxHeight(300);
            bookImageView.setMaxWidth(300);
            book.convertBase64ToBitmap();
            bookImageView.setImageBitmap(book.getImageBitmap());

            TextView bookNameTextView = rowView.findViewById(R.id.bookNameTextView);
            bookNameTextView.setText(book.getName());

            TextView authorTextView = rowView.findViewById(R.id.authorTextView);
            authorTextView.setText(book.getAuthor());

            TextView bookPriceTextView = rowView.findViewById(R.id.bookPriceTextView);
            bookPriceTextView.setText(new StringBuilder().append(book.getPrice()).append(getString(R.string.currency)).toString());

            TextView quantityTextView = rowView.findViewById(R.id.quantityTextView);
            quantityTextView.setText(String.valueOf(book.getQuantity()));


            ll.addView(rowView);
            ll.addView(divider);

//            Toast.makeText(getBaseContext(), book.getImageBase64(), Toast.LENGTH_SHORT).show();
//
//            TableRow row = new TableRow(this);
//            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
//
//
//
//            ImageView imageView = new ImageView(this);
//            imageView.setAdjustViewBounds(true);
//            imageView.setMaxHeight(200);
//            imageView.setMaxWidth(200);
//            book.convertBase64ToBitmap();
//            imageView.setImageBitmap(book.getImageBitmap());
//
//            TextView nameTextView = new TextView(this);
//            nameTextView.setText(book.getName());
//            nameTextView.setLayoutParams(new );
//
//            TextView authorTextView = new TextView(this);
//            authorTextView.setText(book.getAuthor());
//
//            TextView descriptionTextView = new TextView(this);
//            descriptionTextView.setText(book.getDescription());
//
//            ImageButton addBtn = new ImageButton(this);
//            addBtn.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
//            ImageButton minusBtn = new ImageButton(this);
//            minusBtn.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);
//            TextView qty = new TextView(this);
//            qty.setText(String.valueOf(book.getQuantity()));
//
//
//            linearLayout.addView(nameTextView);
//            linearLayout.addView(authorTextView);
//            row.addView(descriptionTextView);
//            row.addView(imageView);
//            row.addView(linearLayout);
//            row.addView(minusBtn);
//            row.addView(qty);
//            row.addView(addBtn);
//            ll.addView(row);
        }

        progressBar.setVisibility(View.INVISIBLE);

    }
}
