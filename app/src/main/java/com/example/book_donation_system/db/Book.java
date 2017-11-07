package com.example.book_donation_system.db;

/**
 * Created by 何书杰 on 2017/9/28.
 */

public class Book {
    private String bookName;
    private int imageId;
    public Book(String bookName,int imageId){
        this.bookName = bookName;
        this.imageId = imageId;
    }

    public String getBookName() {
        return bookName;
    }

    public int getImageId() {
        return imageId;
    }
}
