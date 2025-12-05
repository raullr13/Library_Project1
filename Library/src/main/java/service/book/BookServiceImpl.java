package service.book;
import model.Book;
import model.Sale;
import repository.book.BookRepository;
import repository.sale.SaleRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final SaleRepository saleRepository;

    public BookServiceImpl(BookRepository bookRepository, SaleRepository saleRepository) {
        this.bookRepository = bookRepository;
        this.saleRepository = saleRepository;
    }
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book with id: %d not found".formatted(id)));
    }
    @Override
    public boolean save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public boolean delete(Book book) {
        return bookRepository.delete(book);
    }

    @Override
    public int getAgeOfBook(Long id) {
        Book book = this.findById(id);
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(book.getPublishedDate(), now);
    }

    @Override
    public boolean sell(Book book, Long userId) {
        Book newBook = bookRepository.findById(book.getId())
                .orElse(null);

        if(newBook != null && newBook.getStock() > 0) {
            newBook.setStock(newBook.getStock() - 1);
            boolean stockUpdated = bookRepository.save(newBook);

            if(stockUpdated) {
                Sale sale = new Sale(newBook.getId(), userId, newBook.getPrice());
                return saleRepository.save(sale);
            }
        }
        return false;
    }

}