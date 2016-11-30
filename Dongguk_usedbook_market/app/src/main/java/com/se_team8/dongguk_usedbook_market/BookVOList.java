package com.se_team8.dongguk_usedbook_market;

import java.util.ArrayList;

/**
 * Created by JinYoung on 2016-11-20.
 */

public class BookVOList {
    // 책의 정보
    private String ListName;
    private ArrayList<String> requestId= new ArrayList<String>();;
    private ArrayList<String> title= new ArrayList<String>();;
    private ArrayList<String> imgUrl= new ArrayList<String>();;
    private ArrayList<String> author= new ArrayList<String>();;
    private ArrayList<String> price= new ArrayList<String>();;
    private ArrayList<String> pubdate= new ArrayList<String>();;
    private ArrayList<String> publisher= new ArrayList<String>();;
    private ArrayList<String> isbn= new ArrayList<String>();;
    private ArrayList<String> sellerPrice= new ArrayList<String>();;
    private ArrayList<String> status= new ArrayList<String>();;
    private ArrayList<String> professor= new ArrayList<String>();;
    private ArrayList<String> course= new ArrayList<String>();;
    private ArrayList<String> comment= new ArrayList<String>();
    private ArrayList<String> bookId = new ArrayList<String>();
    private ArrayList<String> owner = new ArrayList<String>();
    private int ListID;

    BookVOList(String listName) { this.ListName = listName; }
    /**모든 책정보에 대한 Getter and Setter */
    public ArrayList<String> getRequestId() {
        return requestId;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public ArrayList<String> getImgUrl() {
        return imgUrl;
    }

    public ArrayList<String> getAuthor() {
        return author;
    }


    public ArrayList<String> getPrice() {
        return price;
    }

    public ArrayList<String> getPubdate() {
        return pubdate;
    }

    public ArrayList<String> getPublisher() {
        return publisher;
    }
    public ArrayList<String> getIsbn() {
        return isbn;
    }

    public void setIsbn(ArrayList<String> isbn) {
        this.isbn = isbn;
    }

    public ArrayList<String> getSellerPrice() {
        return sellerPrice;
    }

    public ArrayList<String> getStatus() {
        return status;
    }

    public ArrayList<String> getProfessor() {
        return professor;
    }

    public ArrayList<String> getCourse() {
        return course;
    }

    public ArrayList<String> getComment() {
        return comment;
    }

    public ArrayList<String> getBookId() {
        return bookId;
    }

    public ArrayList<String> getOwner() {
        return owner;
    }

    public String getListName(){
        return ListName;
    }
    public int getListID() {
        return ListID;
    }

    public void setListID(int listID) {
        ListID = listID;
    }
}