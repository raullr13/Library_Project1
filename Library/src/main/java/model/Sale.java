package model;

import java.time.LocalDateTime;

public class Sale {

    private Long id;
    private Long bookId;
    private Long userId;
    private Double price;
    private LocalDateTime soldDate;

    public Sale(Long id, Long bookId, Long userId, Double price, LocalDateTime soldDate) {
        this.id = id;
        this.bookId = bookId;
        this.userId = userId;
        this.price = price;
        this.soldDate = soldDate;
    }

    public Sale(Long bookId, Long userId, Double price)
    {
        this.bookId = bookId;
        this.userId = userId;
        this.price = price;
        this.soldDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }


}
