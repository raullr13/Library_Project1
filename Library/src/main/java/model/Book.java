package model;
import java.time.LocalDate;
import java.util.Date;

// POJO - Plain Old Java Object
//Java Bean
public class Book{
    private Long id;
    private String author;
    private String title;
    private LocalDate publishedDate;
    private Integer stock;
    private Double price;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public LocalDate getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }
    public Integer getStock() {return stock;}
    public void setStock(Integer stock) {this.stock = stock;}

    public Double getPrice() {return price;}
    public void setPrice(Double price) {this.price = price;}

    @Override
    public String toString(){
        return "Book: ID: " + id + " Title: " + title + " Author: " + author + " Published Date: " + publishedDate;

    }
}