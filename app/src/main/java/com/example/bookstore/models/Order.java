package com.example.bookstore.models;

import java.util.Calendar;
import java.util.Date;

public class Order {

    private long id;
    private String username;
    private Date date;
    private float cost;
    private String bookIds = "";
    private String comments;

    public Order(long orderId, String username) {
        this.id = orderId;
        this.username = username;
        for (Book book : Cart.getBooks()) {
            bookIds += book.getId() + ",";
        }
        bookIds = bookIds.substring(0, bookIds.lastIndexOf(','));
        this.cost = Cart.getTotalCost();
        this.date = Calendar.getInstance().getTime();
        this.comments = Cart.getComments();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getBookIds() {
        return bookIds;
    }

    public void setBookIds(String bookIds) {
        this.bookIds = bookIds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}