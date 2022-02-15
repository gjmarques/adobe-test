package com.adobe.bookstore;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_order")
@JsonSerialize
public class BookOrder {

    @Id
    @Column(name = "book", nullable = false)
    private  String book;

    @Column(name = "quantity", nullable = false)
    private  Integer quantity;

   public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
