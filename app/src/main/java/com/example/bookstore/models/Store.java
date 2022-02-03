package com.example.bookstore.models;

import java.util.ArrayList;

public final class Store {

    private static ArrayList<Book> books = new ArrayList<>();

    private Store() {
        books = new ArrayList<>();
    }

    public static void add(Book book) {
        books.add(book);
    }

    public static ArrayList<Book> getBooks() {
        return books;
    }

    public static boolean isBookAvailable(int id){
        for (Book book : books) {
            if (book.getId() == id)
                return book.isAvailable();
        }
        return false;
    }
}