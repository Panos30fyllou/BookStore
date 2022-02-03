package com.example.bookstore.models;

import java.util.ArrayList;


public final class Cart {

    private static ArrayList<Book> books = new ArrayList<>();
    private static float totalCost = 0;

    private Cart() {
        books = new ArrayList<>();
    }

    public static float getTotalCost() {
        return totalCost;
    }

    public static int getSize() {
        return books.size();
    }

    public static void add(Book book) {
        books.add(book);
        book.setQuantity(book.getQuantity() - 1);
        totalCost += book.getPrice();
    }

    public static void remove(Book book) {
        if (Cart.books.contains(book)) {
            Cart.books.remove(book);
            book.setQuantity(book.getQuantity() + 1);
            totalCost -= book.getPrice();
        }
    }

    public static ArrayList<Book> getBooks() {
        return books;
    }

    public static int getQuantity(Book book) {
        int quantity = 0;
        for (Book bookInCart : books) {
            if (bookInCart.getId() == book.getId())
                quantity++;
        }
        return quantity;
    }
}
