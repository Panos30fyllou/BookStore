package com.example.bookstore.models;

import java.util.ArrayList;

public final class Store {

    public static boolean needsRefresh = true;

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

    public static Book getBookById(int id){
        for (Book book : books) {
            if (book.getId() == id)
                return book;
        }
        return books.get(0);
    }

    public static void clear(){
        books = new ArrayList<>();
    }

    public static boolean isBookAvailable(int id){
        for (Book book : books) {
            if (book.getId() == id)
                return book.isAvailable();
        }
        return false;
    }
}