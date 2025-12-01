package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import mapper.BookMapper;
import model.Book;
import service.book.BookService;
import view.BookView;
import view.model.BookDTO;
import view.model.builder.BookDTOBuilder;

public class BookController {
    private final BookView bookView;
    private final BookService bookService;

    public BookController(BookView bookView, BookService bookService){
        this.bookView = bookView;
        this.bookService = bookService;

        this.bookView.addSaveButtonListener(new SaveButtonListener());
        this.bookView.addSelectionTableListener(new SelectionTableListener());
        this.bookView.addDeleteButtonListener(new DeleteButtonListener());

        this.bookView.addSellButtonListener(new SellButtonListener());
    }

    private class SaveButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            String title = bookView.getTitle();
            String author = bookView.getAuthor();
            String priceText = bookView.getPrice();
            String stockText = bookView.getStock();

            if (title.isEmpty() || author.isEmpty() || priceText.isEmpty() || stockText.isEmpty()){
                bookView.displayAlertMessage("Save Error", "Problem at various fields", "Can not have empty fields. Please fill in the fields before submitting Save!");
            } else {
                try {
                    double price = Double.parseDouble(bookView.getPrice());
                    int stock = Integer.parseInt(bookView.getStock());
                    BookDTO bookDTO = new BookDTOBuilder().setAuthor(author).setTitle(title).setPrice(price).setStock(stock).build();

                    Book bookEntity = BookMapper.convertBookDTOToBook(bookDTO);

                    boolean savedBook = bookService.save(bookEntity);

                    if (savedBook) {
                        bookView.displayAlertMessage("Save Successful", "Book Added", "Book was successfully added to the database.");
                        bookDTO.setId(bookEntity.getId());
                        bookView.addBookToObservableList(bookDTO);
                    } else {
                        bookView.displayAlertMessage("Save Not Successful", "Book was not added", "There was a problem at adding the book into the database.");
                    }
                }catch (NumberFormatException e){
                    bookView.displayAlertMessage("Input error", "Invalid input", "Check your inputs!");
                }
            }
        }
    }

    private class SelectionTableListener implements ChangeListener{

        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            BookDTO selectedBookDTO = (BookDTO) newValue;
            System.out.println("Book Author: " + selectedBookDTO.getAuthor() + " Title: " + selectedBookDTO.getTitle());
        }
    }

    private class DeleteButtonListener implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();
            if (bookDTO != null){
                boolean deletionSuccessfull = bookService.delete(BookMapper.convertBookDTOToBook(bookDTO));
                if (deletionSuccessfull){
                    bookView.removeBookFromObservableList(bookDTO);
                } else {
                    bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "There was a problem in the deletion process. Please restart the application and try again!");
                }
            } else {
                bookView.displayAlertMessage("Deletion not successful", "Deletion Process", "You need to select a row from table before pressing the delete button!");
            }
        }
    }

    private class SellButtonListener implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event) {
            BookDTO bookDTO = (BookDTO) bookView.getBookTableView().getSelectionModel().getSelectedItem();

            if(bookDTO != null){
                boolean sellSuccess = bookService.sell(BookMapper.convertBookDTOToBook(bookDTO));

                if(sellSuccess){
                    bookDTO.setStock(bookDTO.getStock() - 1);
                    bookView.displayAlertMessage("Success", "Book Sold", "Stock decremented");
                }
                else
                {
                    bookView.displayAlertMessage("Error", "Sell failed", "Couldn't complete the sale");
                }
            }
            else
            {
                bookView.displayAlertMessage("Error", "No selection", "Please select a book first!");
            }
        }
    }

}
