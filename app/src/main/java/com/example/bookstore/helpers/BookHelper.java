package com.example.bookstore.helpers;

import android.view.View;

public class BookHelper {

    public static int getBookIdFromStoreRow(View v){
        View parent = (View) v.getParent();
        View grandfather = (View) parent.getParent();
        return grandfather.getId();
    }

    public static int getBookIdFromConfirmRow(View v){
        View parent = (View) v.getParent();
        View grandfather = (View) parent.getParent();
        View ggfather = (View) grandfather.getParent();
        return ggfather.getId();
    }
}
