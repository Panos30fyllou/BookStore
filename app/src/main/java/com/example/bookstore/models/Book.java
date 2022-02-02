package com.example.bookstore.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Base64;

public class Book {

    private int id;
    private String name;
    private String author;
    private String description;
    private String imageBase64;
    private int price;
    private int quantity;
    private Bitmap imageBitmap;

    public Book(){

    }
    public Book(String name, String author, String description, String imageBase64, int price, int quantity){

        this.name = name;
        this.author = author;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.imageBase64 = imageBase64;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String  getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void convertBase64ToBitmap() {
        this.setImageBase64(this.getImageBase64().substring(this.getImageBase64().indexOf(",") + 1));
        byte[] decodedString = Base64.decode(this.getImageBase64().getBytes(), Base64.DEFAULT);
        imageBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable(){ return getQuantity() > 0;}
}
