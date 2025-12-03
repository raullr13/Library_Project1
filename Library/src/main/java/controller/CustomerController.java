package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import service.book.BookService;
import view.CustomerBookView;
import view.model.BookDTO;

import java.awt.event.ActionListener;

public class CustomerController {
    private final CustomerBookView customerView;
    private final BookService bookService;
    private final Long currentUserId;

    public CustomerController(CustomerBookView customerView, BookService bookService, Long currentUserId) {
        this.customerView = customerView;
        this.bookService = bookService;
        this.currentUserId = currentUserId;

        this.customerView.addBuyButtonListener(new BuyButtonListener());
    }

    private class BuyButtonListener implements EventHandler<ActionEvent>
    {
        @Override
        public void handle(ActionEvent event) {
            BookDTO selectedBook = customerView.getSelectedBook();
            if(selectedBook != null)
            {
                boolean success = bookService.sell(BookMapper.convertBookDTOToBook(selectedBook), currentUserId);

                if(success)
                {
                    customerView.displayAlertMessage("Success", "Book Bought", "Enjoy your purchase!");
                    selectedBook.setStock(selectedBook.getStock() - 1);
                    customerView.refreshTable();
                }
                else
                {
                    customerView.displayAlertMessage("Error", "Out of stock", "This book in unavailable");
                }
            }
            else
            {
                customerView.displayAlertMessage("Error", "No Selection", "Please select a book");
            }
        }
    }
}
