package launcher;

import controller.AdminUserController;
import controller.BookController;
import database.DatabaseConnectionFactory;
import javafx.stage.Stage;
import mapper.BookMapper;
import model.User;
import repository.book.BookRepository;
import repository.book.BookRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;
import service.book.BookServiceImpl;
import service.user.UserServiceImpl;
import view.AdminDashboardView;
import view.AdminUserView;
import view.BookView;
import view.model.BookDTO;
import view.model.UserDTO;

import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

public class AdminComponentFactory {

    private final Stage stage;
    private final Connection connection;
    private final BookServiceImpl bookService;
    private final UserServiceImpl userService;

    public AdminComponentFactory(Boolean componentsForTest, Stage stage) {
        this.stage = stage;
        this.connection = DatabaseConnectionFactory.getConnectionWrapper(componentsForTest).getConnection();

        this.bookService = new BookServiceImpl(new BookRepositoryMySQL(connection));
        this.userService = new UserServiceImpl(
                new UserRepositoryMySQL(connection, new RightsRolesRepositoryMySQL(connection)),
                new RightsRolesRepositoryMySQL(connection)
                );
        initDashboard();
    }

    private void initDashboard()
    {
        AdminDashboardView dashboardView = new AdminDashboardView(stage);
        dashboardView.addManageBooksListener(e -> {
            List<BookDTO> bookDTOs = BookMapper.convertBookListToBookDTOList(bookService.findAll());
            BookView bookView = new BookView(stage, bookDTOs);
            new BookController(bookView, bookService);
        });

        dashboardView.addManageUsersListener(e -> {
            List<User> users = userService.findAll();
            List<UserDTO> userDTOs = users.stream().map(user -> {
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setUsername(user.getUsername());

                if(!user.getRoles().isEmpty())
                {
                    dto.setRole(user.getRoles().get(0).getRole());
                }
                return dto;
            }).collect(Collectors.toList());

            AdminUserView adminUserView = new AdminUserView(stage, userDTOs);
            new AdminUserController(adminUserView, userService);
        });
    }

}
