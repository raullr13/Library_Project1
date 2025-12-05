package launcher;

import controller.BookController;
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
import view.model.BookDTO;

import java.sql.Connection;
import java.util.List;

public class EmployeeComponentFactory {

    private final BookView bookView;
    private final BookController bookController;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private static EmployeeComponentFactory instance;
    private final SaleRepository saleRepository;

    public static EmployeeComponentFactory getInstance(Boolean componentsForTest, Stage stage, Long currentUserId){
        return new EmployeeComponentFactory(componentsForTest,stage,currentUserId);
    }

    public EmployeeComponentFactory(Boolean componentsForTest, Stage stage, Long currentUserId){
        Connection connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();
        this.bookRepository = new BookRepositoryCacheDecorator(new BookRepositoryMySQL(connection), new Cache<>());
        this.saleRepository = new SaleRepositoryMySQL(connection);
        this.bookService = new BookServiceImpl(bookRepository, saleRepository);
        List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(this.bookService.findAll());
        this.bookView = new BookView(stage, bookDTOs);
        this.bookController = new BookController(bookView, bookService, currentUserId);
    }

    public BookView getBookView() {
        return bookView;
    }

    public BookController getBookController() {
        return bookController;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public BookService getBookService() {
        return bookService;
    }

    public static EmployeeComponentFactory getInstance() {
        return instance;
    }
}
