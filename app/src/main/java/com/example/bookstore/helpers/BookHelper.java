package com.example.bookstore.helpers;

import android.view.View;

public class BookHelper {
    public static int getBookIdFromButtonId(View v){
        View parent = (View) v.getParent();
        View grandfather = (View) parent.getParent();
        return  grandfather.getId();
    }
}
