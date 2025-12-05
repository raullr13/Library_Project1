package launcher;

import controller.BookController;
import controller.CustomerController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import repository.book.BookRepository;
import repository.book.BookRepositoryCacheDecorator;
import repository.book.BookRepositoryMySQL;
import repository.book.Cache;
import repository.sale.SaleRepository;
import repository.sale.SaleRepositoryMySQL;
import service.book.BookService;
import service.book.BookServiceImpl;
import view.BookView;
import view.CustomerBookView;
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class CustomerComponentFactory {

    private final CustomerBookView customerView;
    private final CustomerController customerController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static CustomerComponentFactory instance;
    private final Long currentUserId;

    public static CustomerComponentFactory getInstance(Boolean componentsForTest, Stage stage, Long currentUserId) {
        return new CustomerComponentFactory(componentsForTest, stage, currentUserId);
    }

    public CustomerComponentFactory(Boolean componentsForTest, Stage stage, Long currentUserId) {
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        SaleRepository saleRepository = new SaleRepositoryMySQL(connection);
        this.currentUserId = currentUserId;
        this.bookService = new BookServiceImpl(bookRepository, saleRepository);

        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());

        this.customerView = new CustomerBookView(stage, bookDTOs);
        this.customerController = new CustomerController(customerView, bookService, currentUserId);
    }

    public CustomerBookView getCustomerBookView() {
        return customerView;
    }

    public CustomerController getCustomerController() {
        return customerController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static CustomerComponentFactory getInstance() {
        return instance;
    }

}
