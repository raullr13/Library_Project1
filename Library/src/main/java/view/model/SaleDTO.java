package view.model;

import java.time.LocalDateTime;

public class SaleDTO {

    private String bookTitle;
    private String username;
    private Double price;
    private LocalDateTime soldDate;

    public SaleDTO(String bookTitle, String username, Double price, LocalDateTime soldDate) {
        this.bookTitle = bookTitle;
        this.username = username;
        this.price = price;
        this.soldDate = soldDate;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getUsername() {
        return username;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }

    public String getFormattedDate()
    {
        return soldDate.toLocalDate().toString() + " " + soldDate.toLocalTime().toString().substring(0, 5);
    }
}
